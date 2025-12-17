package com.example.board.post.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.board.post.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long>, SearchBoardRepository {
    // public interface BoardRepository extends JpaRepository<Board, Long> {
    // on 구문 생략 기준 : 일치하는 컬럼
    @Query("select b, m from Board b join b.writer m")
    List<Object[]> getBoardWithWriterList();

    // bno로 댓글 가져오기
    @Query("select b, r from Board b left join Reply r on r.board = b where b.bno = :bno")
    List<Object[]> getBoardWithReply(@Param("bno") Long bno);

    // 하나 조회 => querydsl 방식으로(댓글처리) 변경해서 주석처리 했다
    // @Query("select b, m, count(r) from Board b left join b.writer m left join
    // Reply r on r.board = b where b.bno = :bno")
    // Object getBoardByBno(@Param("bno") Long bno);

    // 목록화면 => 페이지 나누기 필요
    // 처음에만 사용(search 를 위해 변경)
    // @Query(value = "select b, m, count(r) from Board b left join b.writer m left
    // join Reply r on r.board = b group by b", countQuery = "select count(b) from
    // Board b")
    // Page<Object[]> getBoardWithReplyCount(Pageable pageable);
}
