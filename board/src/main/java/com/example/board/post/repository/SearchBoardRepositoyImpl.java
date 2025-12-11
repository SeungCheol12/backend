package com.example.board.post.repository;

import java.util.List;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.example.board.post.entity.Board;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class SearchBoardRepositoyImpl extends QuerydslRepositorySupport implements SearchBoardRepository {

    public SearchBoardRepositoyImpl() {
        super(Board.class);
    }

    @Override
    public List<Object[]> list() {
        log.info("board + reply + member join");
        return null;
    }

}
