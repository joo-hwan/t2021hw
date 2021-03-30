package com.ssg.homework.t2021hw.domain.payments.payment.service;

import com.ssg.homework.t2021hw.domain.payments.payment.model.Payment;
import com.ssg.homework.t2021hw.dto.PaymentDto;

import java.util.List;

public interface PaymentService {
    PaymentDto approve(String mbrId, Long pmtAmt, String pmtCode, String pmtType);
    PaymentDto cancel(String bfPmtId, String mbrId, Long ptmAmt);
    PaymentDto partialCancel(String bfPmtId, String mbrId, Long pmtAmt);
    List<PaymentDto> getRecentPaymentList(String mbrId, String succYn, Integer size);
}
