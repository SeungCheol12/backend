package com.example.jpa.repository;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jpa.entity.Team;
import com.example.jpa.entity.TeamMember;

import jakarta.transaction.Transactional;

@SpringBootTest
public class TeamRepositoryTest {
    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private TeamMemberRepository teamMemberRepository;

    @Test
    public void insertTest() {
        Team team = Team.builder()
                .name("team1")
                .build();
        teamRepository.save(team);

        TeamMember member = TeamMember.builder().name("홍길동").team(team).build();
        teamMemberRepository.save(member);
    }

    // 기존에 있던 팀에 새 멤버 추가
    // team_id 만 받아서 새 멤버 추가했다
    @Test
    public void insertTest2() {
        // Team team = Team.builder()
        // .id(1L)
        // .build(); // 1

        // or

        Team team = teamRepository.findById(3L).get(); // 2

        TeamMember member = TeamMember.builder().name("성춘향").team(team).build();
        teamMemberRepository.save(member);
    }

    @Test
    public void insertTest3() {
        Team team = Team.builder()
                .name("team4")
                .build();
        teamRepository.save(team);

    }

    @Test
    public void readTest() {

        Team team = teamRepository.findById(1L).get(); // select
        System.out.println(team);

        // 외래키가 적용된 테이블이기 때문에 join을 해서 코드 실행
        TeamMember member = teamMemberRepository.findById(1L).get();
        System.out.println(member); // TeamMember(id=1, name=홍길동, team=Team(id=1, name=team1))
                                    // @ToString(exclude = "team") => TeamMember(id=1, name=홍길동)
        // 팀원 => 팀 조회
        // System.out.println("팀 명" + member.getTeam().getName());

    }

    @Test
    public void updateTest() {

        // 팀 이름 변경
        Team team = teamRepository.findById(1L).get(); // select
        team.changeName("플라워");
        teamRepository.save(team); // 세이브를 해야한다
        System.out.println(team);

        // 팀 변경
        TeamMember teamMember = teamMemberRepository.findById(2L).get();
        teamMember.changeTeam(team.builder().id(2L).build());
        System.out.println(teamMemberRepository.save(teamMember));
    }

    @Test
    public void deleteTest1() {
        // 1. 삭제하려고 하는 팀의 팀원들을 다른 팀으로 변경
        // 2. 팀원 먼저 삭제

        // 팀원 찾기
        List<TeamMember> result = teamMemberRepository.findByTeam(Team.builder().id(1L).build());
        // 다른 팀으로 이동 1팀에서 2팀으로
        result.forEach(m -> {
            m.changeTeam(Team.builder().id(2L).build());
            teamMemberRepository.save(m);
        });

        // 1팀 삭제
        teamRepository.deleteById(1L);
    }

    @Test
    public void deleteTest2() {
        // 1. 삭제하려고 하는 팀의 팀원들을 다른 팀으로 변경
        // 2. 팀원 먼저 삭제

        // 팀원 찾기
        List<TeamMember> result = teamMemberRepository.findByTeam(Team.builder().id(2L).build());
        // 2팀 팀원 먼저 삭제
        result.forEach(m -> {
            teamMemberRepository.delete(m);
        });

        // 2팀 삭제
        teamRepository.deleteById(2L);
    }

    // 팀 => 멤버 조회
    @Transactional
    @Test
    public void readTest2() {

        Team team = teamRepository.findById(3L).get(); // select

        // @OneToMany(mappedBy = "team")
        System.out.println(team);
        // 팀원 => 팀 조회
        System.out.println(team.getMembers());

    }

    @Transactional
    @Test
    public void readTest3() {

        Team team = teamRepository.findById(3L).get(); // select
        // 팀 = >팀원 조회(left join 처리)
        // @OneToMany(mappedBy = "team", fetch = FetchType.EAGER)
        System.out.println(team);
        // System.out.println(team.getMembers());

    }

    @Transactional
    @Test
    public void readTest4() {

        TeamMember member = teamMemberRepository.findById(4L).get();
        System.out.println(member);
        System.out.println(member.getTeam());

    }
}
