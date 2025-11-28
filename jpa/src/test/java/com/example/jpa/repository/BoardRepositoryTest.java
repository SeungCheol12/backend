package com.example.jpa.repository;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jpa.entity.Board;

@SpringBootTest
public class BoardRepositoryTest {
    @Autowired
    private BoardRepository boardRepository;

    // Board 10개 삽입
    @Test
    public void insertTest() {
        for (int i = 0; i < 10; i++) {
            Board board = Board.builder()
                    .title("title" + i)
                    .content("content" + i)
                    .writer("writer" + i)
                    .build();
            boardRepository.save(board);

        }
    }

    // 수정 : title, content
    @Test
    public void updateTest1() {
        Board board = boardRepository.findById(10).get();
        board.changeTitle("title change");
        boardRepository.save(board);
    }

    @Test
    public void updateTest2() {
        Optional<Board> result = boardRepository.findById(10);

        result.ifPresent(content -> {
            content.changeContent("content change");
            boardRepository.save(content);
        });
    }

    // 조회
    @Test
    public void readTest1() {
        System.out.println(boardRepository.findById(10).get());
    }

    @Test
    public void readTest2() {
        boardRepository.findAll().forEach(board -> System.out.println(board));
    }

    // 삭제
    @Test
    public void deleteTest() {
        boardRepository.deleteById(11);
    }
}
