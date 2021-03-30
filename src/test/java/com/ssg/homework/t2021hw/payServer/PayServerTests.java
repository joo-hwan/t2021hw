package com.ssg.homework.t2021hw.payServer;

import com.ssg.homework.t2021hw.domain.payments.paymentmaster.model.PayType;
import com.ssg.homework.t2021hw.paymentgateway.PayServer;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PayServerTests {

    @Disabled
    @Test
    public void ratioTest() {
        PayServer.communicate(PayType.PT01, (long)1000, "10");
        PayServer.communicate(PayType.PT01, (long)1000, "10");
        PayServer.communicate(PayType.PT01, (long)1000, "10");
        PayServer.communicate(PayType.PT01, (long)1000, "10");
        PayServer.communicate(PayType.PT01, (long)1000, "10");
        PayServer.communicate(PayType.PT01, (long)1000, "10");
        PayServer.communicate(PayType.PT01, (long)1000, "10");
        PayServer.communicate(PayType.PT01, (long)1000, "10");
        PayServer.communicate(PayType.PT01, (long)1000, "10");
        String temp = PayServer.communicate(null, (long)1000, "10");

        System.out.println(temp.toString());
    }
}
