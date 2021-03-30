package com.ssg.homework.t2021hw.payments;

import com.ssg.homework.t2021hw.domain.payments.payment.model.Payment;
import com.ssg.homework.t2021hw.domain.payments.payment.repository.PaymentRepository;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PaymentTests {
    @Autowired
    PaymentRepository paymentRepository;

    /*
    pmtId 정상동작 여부 테스트
    customGenerator test
     */
    @Disabled
    @Test
    public void savePaymentTest() {
        Payment payment = new Payment();
        payment.initailPayment(
                "00001"
                , "P0001"
                ,""
                ,""
                ,""
                , Timestamp.valueOf(LocalDateTime.now())
                , (long)10
        );

        Payment payment2 = new Payment();
        payment2.initailPayment(
                "00002"
                , "P0002"
                ,""
                ,""
                ,""
                , Timestamp.valueOf(LocalDateTime.now())
                , (long)10
        );

        assertEquals("0000000001", paymentRepository.save(payment).getPmtId());
        assertEquals("0000000002", paymentRepository.save(payment2).getPmtId());

    }
}
