package com.ssg.homework.t2021hw.domain.payments.payment.service;

import com.ssg.homework.t2021hw.paymentgateway.PaymentGateway;
import com.ssg.homework.t2021hw.config.Constants;
import com.ssg.homework.t2021hw.config.exception.CustomNotFoundException;
import com.ssg.homework.t2021hw.config.exception.CustomUnauthorizedException;
import com.ssg.homework.t2021hw.domain.member.repository.MemberRepository;
import com.ssg.homework.t2021hw.domain.payments.payment.model.Payment;
import com.ssg.homework.t2021hw.domain.payments.payment.repository.PaymentRepository;
import com.ssg.homework.t2021hw.domain.payments.paymentmaster.model.PayGroup;
import com.ssg.homework.t2021hw.domain.payments.paymentmaster.model.PayType;
import com.ssg.homework.t2021hw.dto.PaymentDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
/*
서비스 구현 클래스
필수 값 및 데이터에 대한 검증을 진행 후 paymentgateway에 승인/취소를 요청함
 */
@Transactional
@Service
public class PaymentServiceImpl implements PaymentService{
    private final MemberRepository memberRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentGateway paymentGateway;

    PaymentServiceImpl(MemberRepository memberRepository, PaymentRepository paymentRepository, PaymentGateway paymentGateway) {
        this.memberRepository = memberRepository;
        this.paymentRepository = paymentRepository;
        this.paymentGateway = paymentGateway;
    }

    @Override
    public PaymentDto approve(String mbrId, Long pmtAmt, String pmtCode, String pmtType) {
        if(mbrId.isEmpty() || pmtAmt == null || pmtCode.isEmpty()) {
            throw new CustomUnauthorizedException("필수값 없음.");
        }

        if(!memberRepository.existsById(mbrId)) {
            throw new CustomUnauthorizedException("등록된 회원ID 없음.");
        }

        if(pmtAmt <= 0) {
            throw new CustomUnauthorizedException("결제금액 오류.");
        }

        /*
        정상적인 코드 값인지 검증
         */
        if(!PayGroup.hasPayCode(pmtCode) || (StringUtils.hasText(pmtType) && !PayType.hasPayType(pmtType))) {
            throw new CustomNotFoundException("매칭되는 결제코드,결제타입이 없음.");
        }

        /*
        결제 수단 내에 해당 하는 결제 타입이 맞는지 검증
         */
        if(StringUtils.hasText(pmtType) && !PayGroup.valueOf(pmtCode).hasPayType(PayType.valueOf(pmtType))) {
            throw new CustomNotFoundException("매칭되는 결제코드,결제타입이 없음.");
        }

        PaymentDto paymentDto = paymentGateway.approve(PayGroup.valueOf(pmtCode), pmtType, pmtAmt);

        paymentDto.setMbrId(mbrId);

        Payment payment = new Payment();
        payment.convertPaymentDtoToPayment(paymentDto);

        paymentRepository.save(payment);
        paymentDto.setPmtId(payment.getPmtId());

        return paymentDto;
    }

    @Override
    public PaymentDto cancel(String bfPmtId, String mbrId, Long pmtAmt) {
        if(mbrId.isEmpty() || bfPmtId.isEmpty() || pmtAmt == null) {
            throw new CustomUnauthorizedException("필수값 없음.");
        }

        if(pmtAmt <= 0) {
            throw new CustomUnauthorizedException("결제금액 오류.");
        }

        Payment payment = paymentRepository.findById(bfPmtId).orElseThrow(() -> new CustomNotFoundException("승인 결제ID 내역이 없음."));

        if(!payment.getPmtAmt().equals(pmtAmt)) {
            throw new CustomNotFoundException("승인금액과 취소금액이 다름.");
        }

        if(!payment.getMbrId().equals(mbrId)) {
            throw new CustomUnauthorizedException("승인 결제 회원ID 아님");
        }

        String pmtType = payment.getPmtType() == null ? null : payment.getPmtType().name();

        PaymentDto paymentDto = paymentGateway.cancel(payment.getPmtCode(), pmtType, payment.getPmtAmt());
        paymentDto.setMbrId(mbrId);

        Payment cancelPayment = new Payment();
        cancelPayment.convertPaymentDtoToPayment(paymentDto);
        cancelPayment.setBfPmtCode(payment.getPmtId());

        paymentRepository.save(cancelPayment);
        paymentDto.setPmtId(cancelPayment.getPmtId());

        return paymentDto;
    }

    @Override
    public PaymentDto partialCancel(String bfPmtId, String mbrId, Long pmtAmt) {
        if(mbrId.isEmpty() || bfPmtId.isEmpty() || pmtAmt == null) {
            throw new CustomUnauthorizedException("필수값 없음.");
        }

        if(pmtAmt <= 0) {
            throw new CustomUnauthorizedException("결제금액 오류.");
        }

        Payment payment = paymentRepository.findById(bfPmtId).orElseThrow(() -> new CustomNotFoundException("승인 결제ID 내역이 없음."));

        if(!payment.getMbrId().equals(mbrId)) {
            throw new CustomUnauthorizedException("승인 결제 회원ID 아님");
        }

        if(getTotalAmt(bfPmtId) - pmtAmt < 0) {
            throw new CustomNotFoundException("부분취소금액이 충분하지 않음.");
        }

        if(!payment.getPmtCode().checkPartCncl()) {
            throw new CustomUnauthorizedException("부분 취소 불가 결제코드.");
        }

        String pmtType = payment.getPmtType() == null ? null : payment.getPmtType().name();

        PaymentDto paymentDto = paymentGateway.partialCancel(payment.getPmtCode(), pmtType, pmtAmt);
        paymentDto.setMbrId(mbrId);

        Payment partialPayment = new Payment();
        partialPayment.convertPaymentDtoToPayment(paymentDto);
        partialPayment.setBfPmtCode(payment.getPmtId());

        paymentRepository.save(partialPayment);
        paymentDto.setPmtId(partialPayment.getPmtId());

        return paymentDto;
    }

    @Override
    public List<PaymentDto> getRecentPaymentList(String mbrId, String succYn, Integer size) {
        int limit = size == null ? 15 : size;

        if(mbrId.isEmpty()) {
            throw new CustomUnauthorizedException("필수값 없음.");
        }

        if(!memberRepository.existsById(mbrId)) {
            throw new CustomUnauthorizedException("등록된 회원ID 없음.");
        }

        List<PaymentDto> paymentDtoList = paymentRepository.getRecentPaymentList(mbrId, succYn, limit);

        if(paymentDtoList.isEmpty()) {
            throw new CustomNotFoundException("조회된 결제내역이 없음.");
        }

        return paymentDtoList;
    }

    /*
    부분 취소 시 결제금액 검증을 위해 사용하는 메소드
    총 결제 + 취소 금액을 구함
     */
    public Long getTotalAmt(String pmtId) {
        long total = 0;

        List<Payment> paymentList = paymentRepository.findByPmtIdOrBfPmtCodeAndSuccYnEquals(pmtId, pmtId, "Y");

        for(Payment payment : paymentList) {
            if(payment.getAprvType().equals(Constants.APPROVE)) {
                total += payment.getPmtAmt();
            }
            else {
                total -= payment.getPmtAmt();
            }
        }

        return total;
    }
}
