package com.ssg.homework.t2021hw.paymentgateway.paytypepolicy;

import com.ssg.homework.t2021hw.domain.payments.paymentmaster.model.PayGroup;
import com.ssg.homework.t2021hw.domain.payments.paymentmaster.model.PayType;
import com.ssg.homework.t2021hw.paymentgateway.PayServer;
/*
case2 - 결제코드 지정(결제코드에 해당하는 결제방법이 있는 경우)
 */
public class RandomPayTypePolicy implements PayTypePolicy {
    @Override
    public String communicatePayServer(PayGroup payGroup, Long pmtAmt, String aprvType) {
        PayType payType = payGroup.getRandomPayType();
        return PayServer.communicate(payType, pmtAmt, aprvType);
    }
}
