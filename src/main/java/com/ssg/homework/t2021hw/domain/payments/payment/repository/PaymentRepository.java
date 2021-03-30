package com.ssg.homework.t2021hw.domain.payments.payment.repository;

import com.ssg.homework.t2021hw.domain.payments.payment.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, String>, PaymentRepositoryCustom {
    Optional<Payment> findByMbrId(String mbrId);
    List<Payment> findByPmtIdOrBfPmtCodeAndSuccYnEquals(String pmtId, String bfPmtCode, String succYn);
}
