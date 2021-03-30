package com.ssg.homework.t2021hw.domain.payments.paymentmaster.model;

import java.util.Arrays;

/*
결제 타입 enum

 */
public enum PayType {
    PT01,
    PT02,
    PT03,
    PT11,
    PT12;

    /*
    결제 타입 검증 메소드
     */
    public static boolean hasPayType(String name) {
        return Arrays.stream(PayType.values()).anyMatch(payType -> payType.name().equals(name));
    }
}
