package com.ssg.homework.t2021hw.payments;

import com.ssg.homework.t2021hw.domain.member.repository.MemberRepository;
import com.ssg.homework.t2021hw.domain.payments.payment.service.PaymentService;
import com.ssg.homework.t2021hw.dto.PaymentDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class PaymentServiceTests {

    @Autowired
    PaymentService paymentService;

    @Autowired
    MemberRepository memberRepository;

    @Disabled
    @Test
    public void approveTest(){
        String mbrId = "0000000345";
        String pmtCode = "P0001";
        String pmtType = "PT01";
        Long pmtAmt = (long)10000;

        PaymentDto payment = paymentService.approve(mbrId, pmtAmt, pmtCode, pmtType);
        Assertions.assertEquals("0000000001", payment.getPmtId());
    }

    @Disabled
    @Test
    public void cancelTest() {
        approveTest();
        PaymentDto payment = paymentService.cancel("0000000001", "0000000345", (long)10000);
        Assertions.assertEquals("0000000002", payment.getPmtId());
    }

    @Disabled
    @Test
    public void partialTest() {
        approveTest();
        PaymentDto payment = paymentService.partialCancel("0000000001", "0000000001", (long)1000);
        Assertions.assertEquals("0000000002", payment.getPmtId());
    }

    @Disabled
    @Test
    void getPaymentList() {
        approveTest();
        List<PaymentDto> paymentDtoList = paymentService.getRecentPaymentList("0000000345", "Y", 15);
        Assertions.assertNotNull(paymentDtoList);
    }
}
