package com.ssg.homework.t2021hw.paymentgateway.paytypepolicy;

import com.ssg.homework.t2021hw.domain.payments.paymentmaster.model.PayGroup;
import com.ssg.homework.t2021hw.domain.payments.paymentmaster.model.PayType;
/*
결제 타입 선택 및 로직 처리를 위한 인터페이스
새로운 결제 타입 선택 방식 추가 시 해당 인터페이스 구현하여 사용
 */
public interface PayTypePolicy {
    String communicatePayServer(PayGroup payGroup, Long pmtAmt, String aprvType);
}
