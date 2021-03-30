package com.ssg.homework.t2021hw.paymentgateway;

import com.ssg.homework.t2021hw.config.Constants;
import com.ssg.homework.t2021hw.domain.payments.paymentmaster.model.PayGroup;
import com.ssg.homework.t2021hw.dto.PaymentDto;
import com.ssg.homework.t2021hw.paymentgateway.paygrouppolicy.AbstractPayGroupPolicy;
import com.ssg.homework.t2021hw.paymentgateway.paygrouppolicy.CommonPayGroupPolicy;
import com.ssg.homework.t2021hw.paymentgateway.paygrouppolicy.CreditPayGroupPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
/*
component 선언하여 bean 등록
paymentgateway에서는 결제 수단, 결제 타입을 사용하여 각 결제 수단, 결제 타입에 맞는 처리를 함
결제 수단, 결제 타입이 추가/삭제 되더라도 기존 비즈니스 로직은 최대한 수정을 하지 않을 수 있는 방향으로 설계하고자 하였음
AbstractPayGroupPolicy --> 결제 수단 공통 클래스
PayTypePolicy --> 결제 타입 공통 인터페이스
 */
@Component
public class PaymentGateway {
    private final Logger logger = LoggerFactory.getLogger("businessLog");

    public PaymentDto approve(PayGroup payGroup, String payType, Long pmtAmt) {
        logger.info("결제 승인 공통 로직 시작");
        AbstractPayGroupPolicy abstractPayGroupPolicy = getPayGroupPolicy(payGroup);
        logger.info("결제 승인 공통 로직 종료");
        return abstractPayGroupPolicy.reqeustCommunicate(payType, pmtAmt, Constants.APPROVE);
    }

    public PaymentDto cancel(PayGroup payGroup, String payType, Long pmtAmt) {
        logger.info("결제 취소 공통 로직 시작");
        AbstractPayGroupPolicy abstractPayGroupPolicy = getPayGroupPolicy(payGroup);
        logger.info("결제 취소 공통 로직 종료");
        return abstractPayGroupPolicy.reqeustCommunicate(payType, pmtAmt, Constants.CANCEL);
    }

    public PaymentDto partialCancel(PayGroup payGroup, String payType, Long pmtAmt) {
        logger.info("결제 부분취소 공통 로직 시작");
        AbstractPayGroupPolicy abstractPayGroupPolicy = getPayGroupPolicy(payGroup);
        logger.info("결제 부분취소 공통 로직 종");
        return abstractPayGroupPolicy.reqeustCommunicate(payType, pmtAmt, Constants.CANCEL);
    }

    /*
    결제 수단에 따른 클래스 생성하여 리턴
    신용카드만 대표적으로 생성하였고 나머지 결제 수단은 common으로 통합하여 구현함
    결제 수단이 추가 되고 내부 공통 로직 처리 필요 시 AbstractPayGroupPolicy 상속 받아 구현하면 됨
     */
    private AbstractPayGroupPolicy getPayGroupPolicy(PayGroup payGroup) {
        if(payGroup == PayGroup.P0001) {
            logger.info("신용 카드 결제 수단 공통 로직 선택");
            return new CreditPayGroupPolicy();
        }
        else {
            return new CommonPayGroupPolicy(payGroup);
        }
    }
}
