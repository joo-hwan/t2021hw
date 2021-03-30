package com.ssg.homework.t2021hw.domain.payments.paymentmaster.model;

import javax.persistence.*;

/*
Entitiy가 아닌 enum으로 모두 처리하였음
 */
//@Entity
public class PaymentMst {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(5) not null comment '결제코드'")
    private PayGroup pmtCode;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(4) comment '결제타입'")
    private PayType pmtType;

    @Column(columnDefinition = "varchar(100) comment '결제코드명'")
    private String pmtName;

    @Column(columnDefinition = "varchar(1) comment '부분취소가능여부'")
    private String partCnclYn;
}
