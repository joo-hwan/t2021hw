package com.ssg.homework.t2021hw.domain.payments.payment.model;

import com.ssg.homework.t2021hw.domain.payments.paymentmaster.model.PayGroup;
import com.ssg.homework.t2021hw.domain.payments.paymentmaster.model.PayType;
import com.ssg.homework.t2021hw.dto.PaymentDto;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

/*
payment 조회 시 필수값인 mbrId index 추가
custom generator 사용하여 ID 구현하였음
 */
@Entity
@Getter
@Setter
@Table(indexes = @Index(name = "idx_mbrId", columnList = "mbrId"))
public class Payment {

    @Id
    @GeneratedValue(generator = "payment-generator")
    @GenericGenerator(name = "payment-generator",
            strategy = "com.ssg.homework.t2021hw.config.CustomIdGenerator")
    @Column(columnDefinition = "varchar(10) not null comment '결제ID'")
    private String pmtId;

    @Column(columnDefinition = "varchar(10) not null comment '회원ID'")
    private String mbrId;

    @Column(columnDefinition = "varchar(5) not null comment '결제코드'")
    @Enumerated(EnumType.STRING)
    private PayGroup pmtCode;

    @Column(columnDefinition = "varchar(10) comment '이전결제ID'")
    private String bfPmtCode;

    @Column(columnDefinition = "varchar(4) comment '결제타입'")
    @Enumerated(EnumType.STRING)
    private PayType pmtType;

    @Column(columnDefinition = "varchar(1) comment '성공여부'")
    private String succYn;

    @Column(columnDefinition = "varchar(100) comment '성공메세지'")
    private String succMsg;

    @Column(columnDefinition = "varchar(2) comment '승인타입'")
    private String aprvType;

    @Column(columnDefinition = "timestamp comment '승인일시'")
    private Timestamp aprvTime;

    @Column(columnDefinition = "bigint comment '결제금액'")
    private Long pmtAmt;

    public void initailPayment(String mbrId, String pmtCode, String bfPmtCode, String succYn, String succMsg, Timestamp aprvTime, Long pmtAmt) {
        this.mbrId = mbrId;
        this.pmtCode = PayGroup.valueOf(pmtCode);
        this.bfPmtCode = bfPmtCode;
        this.succYn = succYn;
        this.succMsg = succMsg;
        this.aprvTime = aprvTime;
        this.pmtAmt = pmtAmt;
    }

    public void convertPaymentDtoToPayment(PaymentDto paymentDto) {
        this.mbrId = paymentDto.getMbrId();
        this.aprvTime = paymentDto.getAprvTime();
        this.pmtAmt = paymentDto.getPmtAmt();
        this.pmtCode = PayGroup.valueOf(paymentDto.getPmtCode());
        this.pmtType = paymentDto.getPmtType() == null ? null : PayType.valueOf(paymentDto.getPmtType());
        this.succYn = paymentDto.getSuccYn();
        this.succMsg = paymentDto.getSuccMsg();
        this.aprvType = paymentDto.getAprvType();
    }
}
