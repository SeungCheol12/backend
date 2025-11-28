package com.example.jpa.repository;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jpa.entity.Member;
import com.example.jpa.entity.constant.Roletype;

@SpringBootTest
public class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void insertTest() {

        for (int i = 0; i < 11; i++) {
            Member member = Member.builder()
                    .userId("userId" + i)
                    .name("guest" + i)
                    .role(Roletype.MEMBER)
                    .build();

            memberRepository.save(member);
        }
    }

    @Test
    public void updateTest() {
        // userid9 인 사람의 role 변경
        Optional<Member> result = memberRepository.findById(9L);

        // result.get();
        result.ifPresent(member -> {
            member.changeRole(Roletype.ADMIN);
            memberRepository.save(member);
        });
    }

    @Test
    public void deleteTest() {
        memberRepository.deleteById(11L);
    }

    @Test
    public void readTest() {
        Optional<Member> result = memberRepository.findById(5L);

        result.ifPresent(member -> {
            System.out.println(member);
        });
    }

    @Test
    public void readTest2() {
        List<Member> members = memberRepository.findAll();
        members.forEach(member -> System.out.println(member));
    }

}
