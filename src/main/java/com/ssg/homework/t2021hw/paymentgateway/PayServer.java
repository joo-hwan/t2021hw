package com.ssg.homework.t2021hw.paymentgateway;

import com.ssg.homework.t2021hw.domain.payments.paymentmaster.model.PayType;
import org.json.JSONObject;

import java.sql.Timestamp;

/*
결제 서버 가상 클래스
총 횟수, 성공, 실패 횟수 관리하여 8:2 비율로 성공 실패 리턴하도록 구현하였음
 */
public class PayServer {
    private static int totalCnt = 0;
    private static int succCnt = 0;
    private static int failCnt = 0;

    private PayServer() {}

    public static PayServer getInstance() {
        return LaxzyHolder.instance;
    }

    private static class LaxzyHolder {
        private static final PayServer instance = new PayServer();
    }

    public static String communicate(PayType payType, Long pmtAmt, String aprvType) {
        JSONObject result = new JSONObject();
        try {
            if (checkRatio()) {
                result.put("payType", payType == null ? "" : payType);
                result.put("succYn", "Y");
                result.put("succMsg", "처리완료되었습니다");
                totalCnt++;
                succCnt++;
            } else {
                result.put("payType", payType == null ? "" : payType);
                result.put("succYn", "N");
                result.put("succMsg", "실패하였습니다.");
                totalCnt++;
                failCnt++;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            result.put("payType", payType == null ? "" : payType);
            result.put("succYn", "N");
            result.put("succMsg", "실패하였습니다.");
            result.put("aprvTime", new Timestamp(System.currentTimeMillis()).toString());
            totalCnt++;
            failCnt++;
        }

        result.put("aprvTime", new Timestamp(System.currentTimeMillis()).toString());

        return result.toString();
    }

    private static boolean checkRatio() {
        double succRatio = 0.0;

        if(totalCnt > 0) {
            succRatio = (double) succCnt / (double) totalCnt;
        }

        return !(succRatio >= 0.8);
    }
}
