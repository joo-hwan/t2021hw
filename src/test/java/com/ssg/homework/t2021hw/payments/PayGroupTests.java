package com.ssg.homework.t2021hw.payments;

import com.ssg.homework.t2021hw.domain.payments.paymentmaster.model.PayGroup;
import com.ssg.homework.t2021hw.domain.payments.paymentmaster.model.PayType;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PayGroupTests {

    @Disabled
    @Test
    public void findPayTypeIndex() {
        PayGroup payGroup = PayGroup.findByPayType(PayType.PT01);
        int index = payGroup.getPayTypeIndex(PayType.PT01);

        assertEquals(0, index);
        assertEquals("신용카드-서버01", payGroup.getTypeName(index));
    }

    @Disabled
    @Test
    public void findByPayCodeTest() {
        String pmtCode = "P0020";

        assertFalse(PayGroup.hasPayCode(pmtCode));
    }

    @Disabled
    @Test
    void randomPayType() {
        assertNotNull(PayGroup.P0001.getRandomPayType());
    }
}
