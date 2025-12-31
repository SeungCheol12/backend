package com.example.movietalk.movie.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;

import com.example.movietalk.movie.entity.Movie;
import com.example.movietalk.movie.entity.Review;
import com.example.movietalk.member.entity.Member;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    // 영화번호를 기준으로 리뷰 조회
    @EntityGraph(attributePaths = { "member" }, type = EntityGraphType.FETCH)
    List<Review> findByMovie(Movie movie);

    // 리뷰 작성자를 기준으로 리뷰 삭제
    // N+1 방지를 위해 쿼리문 작성
    @Modifying // delete 쿼리문에 필수적
    @Query("delete from Review r where r.member = :member")
    void deleteByMember(Member member);

    // 영화를 기준으로 리뷰 삭제
    // N+1 방지를 위해 쿼리문 작성
    @Modifying // delete 쿼리문에 필수적
    @Query("delete from Review r where r.movie = :movie")
    void deleteByMovie(Movie movie);
}
