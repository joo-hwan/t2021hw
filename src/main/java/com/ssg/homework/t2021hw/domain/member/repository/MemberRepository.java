package com.ssg.homework.t2021hw.domain.member.repository;

import com.ssg.homework.t2021hw.domain.member.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {

}
