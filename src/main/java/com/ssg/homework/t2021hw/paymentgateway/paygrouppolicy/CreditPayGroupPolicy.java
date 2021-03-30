package com.ssg.homework.t2021hw.paymentgateway.paygrouppolicy;

import com.ssg.homework.t2021hw.domain.payments.paymentmaster.model.PayGroup;

/*
신용카드 결제 수단 공통 로직 클래스
 */
public class CreditPayGroupPolicy extends AbstractPayGroupPolicy{
    @Override
    public void process() {
        changePayGroup(PayGroup.P0001);
    }
}
