package com.ssg.homework.t2021hw.domain.payments.paymentmaster.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/*
payment_mst 테이블을 조회하지 않고 코드 값 처리를 위해 enum을 사용하였음
결제 수단에 여러개의 결제타입, 결제타입명이 가능하여 리스트로 구성하였음
부분취소 가능여부는 결제 수단에 따라 달라지는 것으로 보고 구성하였음
 */
public enum PayGroup {
    P0001(Arrays.asList(PayType.PT01, PayType.PT02, PayType.PT03), Arrays.asList("신용카드-서버01", "신용카드-서버02", "신용카드-서버03"), "Y"),
    P0002(Collections.EMPTY_LIST, Arrays.asList("계좌이체"), "Y"),
    P0003(Arrays.asList(PayType.PT11, PayType.PT12), Arrays.asList("휴대폰-서버11", "휴대-서버12"), "Y"),
    P0004(Collections.EMPTY_LIST, Arrays.asList("결제수단04"), "N"),
    P0005(Collections.EMPTY_LIST, Arrays.asList("결제수단05"), "Y"),
    P0006(Collections.EMPTY_LIST, Arrays.asList("결제수단06"), "Y"),
    P0007(Collections.EMPTY_LIST, Arrays.asList("결제수단07"), "Y"),
    P0008(Collections.EMPTY_LIST, Arrays.asList("결제수단08"), "Y"),
    P0009(Collections.EMPTY_LIST, Arrays.asList("결제수단09"), "Y"),
    P0010(Collections.EMPTY_LIST, Arrays.asList("결제수단10"), "N"),
    P0011(Collections.EMPTY_LIST, Arrays.asList("결제수단11"), "Y"),
    P0012(Collections.EMPTY_LIST, Arrays.asList("결제수단12"), "Y"),
    P0013(Collections.EMPTY_LIST, Arrays.asList("결제수단13"), "N"),
    P0014(Collections.EMPTY_LIST, Arrays.asList("결제수단14"), "Y"),
    P0015(Collections.EMPTY_LIST, Arrays.asList("결제수단15"), "Y"),
    P0016(Collections.EMPTY_LIST, Arrays.asList("결제수단16"), "Y"),
    P0017(Collections.EMPTY_LIST, Arrays.asList("결제수단17"), "N");

    private List<PayType> payTypeList;
    private List<String> payTypeNameList;
    private String partCncl;

    /*
    생성자
     */
    PayGroup(List<PayType> payTypeList, List<String> payTypeNameList, String partCncl) {
        this.payTypeList = payTypeList;
        this.payTypeNameList = payTypeNameList;
        this.partCncl = partCncl;
    }

    /*
    결제타입으로 해당 결제 수단을 찾는 메소드
     */
    public static PayGroup findByPayType(PayType payType) {
        return Arrays.stream(PayGroup.values())
                .filter(payGroup -> payGroup.hasPayType(payType))
                .findAny()
                .orElse(null);
    }

    /*
    결제수단이 해당 결제 타입을 포함하고 있는지 확인
    데이터 체크에 사용
     */
    public boolean hasPayType(PayType payType) {
        return payTypeList.stream()
                .anyMatch(pay -> pay == payType);
    }

    /*
    부분 취소 가능 여부 메소드
     */
    public boolean checkPartCncl() {
        return partCncl.equals("Y");
    }

    /*
    결제 타입 index 찾기 위한 메소드
     */
    public int getPayTypeIndex(PayType payType) {
        return payTypeList.indexOf(payType);
    }

    /*
    결제 타입 명 리턴 메소드
    getPayTypeIndex 메소드와 같이 사용
     */
    public String getTypeName(int index) {
        return payTypeNameList.get(index);
    }

    /*
    결제 수단 코드 검증
     */
    public static boolean hasPayCode(String name) {
        return Arrays.stream(PayGroup.values()).anyMatch(payGroup -> payGroup.name().equals(name));
    }

    /*
    결제 수단 내 결제 타입 코드 검증
     */
    public boolean existsPayType() {
        return payTypeList.size() > 0;
    }

    /*
    결제 수단 내 랜덤 결제 타입 리턴 메소드
     */
    public PayType getRandomPayType() {
        int randomIndex = (int)(Math.random() * (payTypeList.size()-1) + 1);
        return payTypeList.get(randomIndex);
    }
}
