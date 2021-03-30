package com.ssg.homework.t2021hw.domain.payments.payment.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.EnumPath;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssg.homework.t2021hw.domain.payments.paymentmaster.model.PayGroup;
import com.ssg.homework.t2021hw.dto.PaymentDto;
import com.ssg.homework.t2021hw.dto.QPaymentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.List;
import static com.ssg.homework.t2021hw.domain.payments.payment.model.QPayment.payment;
/*
PaymentRepositoryCustom 구현체
 */
@RequiredArgsConstructor // 의존성 주입
public class PaymentRepositoryImpl implements PaymentRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;

    /*
    querydsl 사용한 paymentDto 조회 메소드
     */
    @Override
    public List<PaymentDto> getRecentPaymentList(String mbrId, String succYn, int size) {
        QueryResults<PaymentDto> results =
        jpaQueryFactory
                .select(new QPaymentDto(
                         payment.pmtId
                        , payment.mbrId
                        , payment.pmtCode
                        , payment.pmtType
                        , payment.succYn
                        , payment.succMsg
                        , payment.aprvType
                        , payment.aprvTime
                        , payment.pmtAmt
                        ))
                .from(payment)
                .where(succYnEq(succYn)
                , payment.mbrId.eq(mbrId))
                .limit(size)
                .fetchResults();
        return results.getResults();
    }

    /*
    다이나믹쿼리 사용 위한 조건 메소드
     */
    private BooleanExpression succYnEq(String succYn) {
        if(!StringUtils.hasText(succYn)) {
            return null;
        }
        return payment.succYn.eq(succYn);
    }

}
