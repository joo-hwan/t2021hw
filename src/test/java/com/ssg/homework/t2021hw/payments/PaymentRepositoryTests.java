package com.ssg.homework.t2021hw.payments;

import com.ssg.homework.t2021hw.domain.payments.payment.model.Payment;
import com.ssg.homework.t2021hw.domain.payments.payment.repository.PaymentRepository;
import com.ssg.homework.t2021hw.domain.payments.paymentmaster.model.PayGroup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PaymentRepositoryTests {

    @Autowired
    private PaymentRepository paymentRepository;

    @Disabled
    @Test
    void findByPmtIdOrBfPmtCode() {
        Payment payment = new Payment();
        payment.setMbrId("test");
        payment.setPmtCode(PayGroup.P0001);

        paymentRepository.save(payment);

        List<Payment> paymentList = paymentRepository.findByPmtIdOrBfPmtCodeAndSuccYnEquals("0000000001", "0000000001", "Y");

        Assertions.assertEquals(1, paymentList.size());
    }
}
