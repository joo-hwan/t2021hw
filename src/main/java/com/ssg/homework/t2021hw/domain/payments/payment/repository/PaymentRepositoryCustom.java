package com.ssg.homework.t2021hw.domain.payments.payment.repository;

import com.ssg.homework.t2021hw.dto.PaymentDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
/*
QueryDsl 용 interface
 */
public interface PaymentRepositoryCustom {
    List<PaymentDto> getRecentPaymentList(String mbrId, String succYn, int size);
}
