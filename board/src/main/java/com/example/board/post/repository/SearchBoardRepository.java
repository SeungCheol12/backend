package com.example.board.post.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchBoardRepository {

    Page<Object[]> list(String type, String keyword, Pageable pageable);

    Object[] getBoardByBno(Long bno);
}
