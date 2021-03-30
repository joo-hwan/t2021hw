package com.ssg.homework.t2021hw.paymentgateway.paygrouppolicy;

import com.ssg.homework.t2021hw.domain.payments.paymentmaster.model.PayGroup;
import com.ssg.homework.t2021hw.domain.payments.paymentmaster.model.PayType;
import com.ssg.homework.t2021hw.dto.PaymentDto;
import com.ssg.homework.t2021hw.paymentgateway.paytypepolicy.CommonPayTypePolicy;
import com.ssg.homework.t2021hw.paymentgateway.paytypepolicy.FixedPayTypePolicy;
import com.ssg.homework.t2021hw.paymentgateway.paytypepolicy.PayTypePolicy;
import com.ssg.homework.t2021hw.paymentgateway.paytypepolicy.RandomPayTypePolicy;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;

/*
결제 수단 공통 로직 수행 추상 클래스
결제 수단 별로 클래스를 생성하여 아래 클래스를 상속 받아 사용
 */
public abstract class AbstractPayGroupPolicy {
    private final Logger logger = LoggerFactory.getLogger("businessLog");

    private String result;
    private String payType;
    private Long pmtAmt;
    private String aprvType;
    private PayTypePolicy payTypePolicy;
    private PayGroup payGroup;
    /*
    결제수단 공통 로직 수행 메소드
    결제 수단 별 로직 수행 -> 결제 방법에 따른 결제서버 결제 요청
     */
    public final PaymentDto reqeustCommunicate(String payType, Long pmtAmt, String aprvType) {
        logger.info("결제 수단 공통 로직 시작");
        this.payType = payType;
        this.pmtAmt = pmtAmt;
        this.aprvType = aprvType;

        process();
        communicate();

        JSONObject jsonObject = new JSONObject(result);

        PaymentDto paymentDto = new PaymentDto();
        paymentDto.setAprvTime(Timestamp.valueOf(jsonObject.get("aprvTime").toString()));
        paymentDto.setSuccMsg(jsonObject.get("succMsg").toString());
        paymentDto.setSuccYn(jsonObject.get("succYn").toString());
        paymentDto.setPmtAmt(pmtAmt);
        paymentDto.setAprvType(aprvType);
        paymentDto.setPmtCode(payGroup.name());
        paymentDto.setPmtType(jsonObject.get("payType").equals("") ? null : PayType.valueOf(jsonObject.get("payType").toString()).name());

        return paymentDto;
    }

    /*
    결제 수단 중 따로 구현할 로직이 없고 공통적으로 사용할 경우 PayGroup 변경을 위해 사용
    ex) P0004 ~ P0017
     */
    public final void changePayGroup(PayGroup payGroup) {
        this.payGroup = payGroup;
    }

    /*
    결제 수단 별 수행 로직 메소드
    결제 수단 별로 처리해야할 로직을 구현하는 곳
     */
    public abstract void process();

    /*
    PayTypePolicy로 결제서버로 통신을 요청 하는 메소드
    PayTypePolicy를 사용하여 결제 수단 선택에 대한 추가/삭제 처리가 유연하게 가능하도록 구현하였음
    아래 case 외에 다른 선택 방법이 추가 될 시 PayTypePolicy 구현한 클래스를 생성하여 처리
     */
    private void communicate() {
        if(!StringUtils.hasText(payType) && payGroup.existsPayType()) {
            logger.info("case2 - 결제코드 지정(결제코드에 해당하는 결제방법이 있는 경우)");
            payTypePolicy = new RandomPayTypePolicy();
        }
        else if(!payGroup.existsPayType()) {
            logger.info("case3 - 결제코드 지정(결제코드에 해당하는 결제방법이 없는 경우)");
            payTypePolicy = new CommonPayTypePolicy();
        }
        else {
            logger.info("case1 - 결제코드, 결제방법 모두 지정");
            payTypePolicy = new FixedPayTypePolicy(PayType.valueOf(payType));
        }

        this.result = payTypePolicy.communicatePayServer(payGroup, pmtAmt, aprvType);
    }
}
