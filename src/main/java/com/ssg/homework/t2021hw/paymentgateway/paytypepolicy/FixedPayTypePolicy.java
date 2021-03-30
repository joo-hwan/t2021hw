package com.ssg.homework.t2021hw.paymentgateway.paytypepolicy;

import com.ssg.homework.t2021hw.domain.payments.paymentmaster.model.PayGroup;
import com.ssg.homework.t2021hw.domain.payments.paymentmaster.model.PayType;
import com.ssg.homework.t2021hw.paymentgateway.PayServer;
/*
case1 - 결제코드, 결제방법 모두 지정
 */
public class FixedPayTypePolicy implements PayTypePolicy {
    private final PayType payType;

    public FixedPayTypePolicy(PayType payType) {
        this.payType = payType;
    }

    @Override
    public String communicatePayServer(PayGroup payGroup, Long pmtAmt, String aprvType) {
        return PayServer.communicate(payType, pmtAmt, aprvType);
    }
}
