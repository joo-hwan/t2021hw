package com.ssg.homework.t2021hw.paymentgateway.paygrouppolicy;

import com.ssg.homework.t2021hw.domain.payments.paymentmaster.model.PayGroup;

public class CommonPayGroupPolicy extends AbstractPayGroupPolicy{

    /*
    결제 수단 중 따로 구현할 로직이 없고 공통적으로 사용할 경우 사용
    ex) P0004 ~ P0017
     */
    public CommonPayGroupPolicy(PayGroup payGroup) {
        changePayGroup(payGroup);
    }

    @Override
    public void process() {

    }
}
