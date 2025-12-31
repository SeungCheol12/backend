package com.example.movietalk.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.movietalk.member.entity.Member;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
}
