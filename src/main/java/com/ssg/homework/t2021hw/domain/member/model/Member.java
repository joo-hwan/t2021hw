package com.ssg.homework.t2021hw.domain.member.model;

import lombok.Getter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Getter
public class Member {
    @Id
    @GeneratedValue(generator = "member-generator")
    @GenericGenerator(name = "member-generator",
            strategy = "com.ssg.homework.t2021hw.config.CustomIdGenerator")
    @Column(columnDefinition = "varchar(10) comment '회원ID'")
    private String mbrId;

    @Column(columnDefinition = "varchar(100) comment '회원명'")
    private String name;

}
