package com.ssg.homework.t2021hw.paymentgateway.paytypepolicy;

import com.ssg.homework.t2021hw.domain.payments.paymentmaster.model.PayGroup;
import com.ssg.homework.t2021hw.paymentgateway.PayServer;
/*
case3 - 결제코드 지정(결제코드에 해당하는 결제방법이 없는 경우)
 */
public class CommonPayTypePolicy implements PayTypePolicy{
    @Override
    public String communicatePayServer(PayGroup payGroup, Long pmtAmt, String aprvType) {
        return PayServer.communicate(null, pmtAmt, aprvType);
    }
}
