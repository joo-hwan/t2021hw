package com.ssg.homework.t2021hw.payments;

import com.ssg.homework.t2021hw.paymentgateway.paygrouppolicy.AbstractPayGroupPolicy;
import com.ssg.homework.t2021hw.domain.payments.paymentmaster.model.PayGroup;
import com.ssg.homework.t2021hw.dto.PaymentDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AbstractPayGroupTests {

    @Disabled
    @Test
    public void makeAbstractPayGroupTest() {
        AbstractPayGroupPolicy abstractPayGroup = new AbstractPayGroupPolicy() {
            @Override
            public void process() {

            }
        };

        abstractPayGroup.changePayGroup(PayGroup.P0002);
        PaymentDto paymentDto = abstractPayGroup.reqeustCommunicate(null, (long)1000, "10");

        Assertions.assertNull(paymentDto.getPmtType());
        Assertions.assertEquals("Y", paymentDto.getSuccYn());
    }
}
