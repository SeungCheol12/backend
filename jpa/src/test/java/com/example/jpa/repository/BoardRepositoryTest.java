package com.example.jpa.repository;

import java.util.Arrays;
import java.util.List;
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
        Board board = boardRepository.findById(10L).get();
        board.changeTitle("title change");
        board.changeContent("change content");
        boardRepository.save(board);
    }

    @Test
    public void updateTest2() {
        Optional<Board> result = boardRepository.findById(10L);

        result.ifPresent(content -> {
            content.changeContent("content change");
            boardRepository.save(content);
        });
    }

    // 조회
    @Test
    public void readTest1() {
        System.out.println(boardRepository.findById(10L).get());
    }

    @Test
    public void readTest2() {
        boardRepository.findAll().forEach(board -> System.out.println(board));
    }

    // 삭제
    @Test
    public void deleteTest() {
        boardRepository.deleteById(11L);
    }

    // 쿼리메소드
    @Test
    public void queryMethodTest() {
        System.out.println(boardRepository.findByTitle("title2"));
        System.out.println(boardRepository.findByContent("content2"));
        System.out.println(boardRepository.findByTitleEndingWith("3"));
        System.out.println(boardRepository.findByTitleContainingAndIdGreaterThanOrderByIdDesc("title", 3L));

        System.out.println(boardRepository.findByWriterContaining("2"));
        System.out.println(boardRepository.findByTitleContainingOrContentContaining("title", "2"));

    }

    @Test
    public void queryMethodTest2() {
        System.out.println(boardRepository.findByTitleAndTd("title", 3L));
    }

    @Test
    public void queryMethodTest3() {
        System.out.println(boardRepository.findByTitleAndTd2("title", 9L));
    }

    @Test
    public void queryMethodTest4() {
        List<Object[]> result = boardRepository.findByTitle3("title");
        for (Object[] objects : result) {
            // System.out.println(Arrays.toString(objects)); // [title0, writer0]...
            String title = (String) objects[0];
            String writer = (String) objects[1];
            System.out.println(title + " " + writer);
        }

    }
}
