package com.ssg.homework.t2021hw;

import com.ssg.homework.t2021hw.domain.payments.payment.service.PaymentService;
import com.ssg.homework.t2021hw.dto.PaymentDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class T2021hwIntegrationTests {
    private final Logger logger = LoggerFactory.getLogger("businessLog");

    int succCnt;
    int failCnt;

    @Autowired
    private PaymentService paymentService;

    String[] mbrList = {"0000000010", "0000000098", "0000000101", "0000000291", "0000000299"};
    String[] pmtCodeList = {"P0001", "P0002", "P0003", "P0005", "P0006"};
    String[] pmtTypeList = {"", "", "PT11", "", ""};

    List<PaymentDto> approveList = new ArrayList<>();
    List<PaymentDto> cancelList = new ArrayList<>();
    List<PaymentDto> partialList = new ArrayList<>();

    /*
    통합 테스트 구현 코드
    부분 취소 가능한 결제 수단만 적용함
    PG 요청 처리에 대한 case 3개를 적용하였음
    */
    @Test
    void integrationTest() {
        logger.info("통합 테스트 시작");
        for(int i=0; i<mbrList.length; i++) {
            logger.info("회원 -> " + mbrList[i] + " 테스트 시작");

            succCnt = 0;
            failCnt = 0;
            approveList.clear();
            cancelList.clear();
            partialList.clear();

            approveList.add(paymentService.approve(mbrList[i], (long)10000, pmtCodeList[i], pmtTypeList[i]));
            approveList.add(paymentService.approve(mbrList[i], (long)10000, pmtCodeList[i], pmtTypeList[i]));
            approveList.add(paymentService.approve(mbrList[i], (long)10000, pmtCodeList[i], pmtTypeList[i]));
            approveList.add(paymentService.approve(mbrList[i], (long)10000, pmtCodeList[i], pmtTypeList[i]));
            approveList.add(paymentService.approve(mbrList[i], (long)10000, pmtCodeList[i], pmtTypeList[i]));
            approveList.add(paymentService.approve(mbrList[i], (long)10000, pmtCodeList[i], pmtTypeList[i]));
            approveList.add(paymentService.approve(mbrList[i], (long)10000, pmtCodeList[i], pmtTypeList[i]));
            approveList.add(paymentService.approve(mbrList[i], (long)10000, pmtCodeList[i], pmtTypeList[i]));
            approveList.add(paymentService.approve(mbrList[i], (long)10000, pmtCodeList[i], pmtTypeList[i]));

            for(int k=0; k<7; k++) {
                cancelList.add(paymentService.cancel(approveList.get(k).getPmtId(), approveList.get(k).getMbrId(), (long)10000));
            }

            partialList.add(paymentService.partialCancel(approveList.get(7).getPmtId(), approveList.get(7).getMbrId(), (long)1000));
            partialList.add(paymentService.partialCancel(approveList.get(7).getPmtId(), approveList.get(7).getMbrId(), (long)1000));
            partialList.add(paymentService.partialCancel(approveList.get(7).getPmtId(), approveList.get(8).getMbrId(), (long)2000));

            for(PaymentDto approve : approveList) {
                if(approve.getSuccYn().equals("Y")) succCnt++;
                else failCnt++;
            }

            for(PaymentDto cancel : cancelList) {
                if(cancel.getSuccYn().equals("Y")) succCnt++;
                else failCnt++;
            }

            for(PaymentDto partial : partialList) {
                if(partial.getSuccYn().equals("Y")) succCnt++;
                else failCnt++;
            }

            List<PaymentDto> succList = paymentService.getRecentPaymentList(mbrList[i], "Y", 30);
            List<PaymentDto> failList = paymentService.getRecentPaymentList(mbrList[i], "N", 30);

            Assertions.assertEquals(succCnt, succList.size());
            Assertions.assertEquals(failCnt, failList.size());

            logger.info("회원 -> " + mbrList[i] + " 성공건수 -> " + succCnt);
            logger.info("회원 -> " + mbrList[i] + " 실패건수 -> " + failCnt);

            logger.info("회원 -> " + mbrList[i] + " 테스트 종료");
        }

        logger.info("통합 테스트 종료");
    }
}
