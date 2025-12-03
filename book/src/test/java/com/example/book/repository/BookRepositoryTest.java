package com.example.book.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.book.dto.BookDTO;
import com.example.book.entity.Book;

import jakarta.persistence.EntityNotFoundException;

@Disabled
@SpringBootTest
public class BookRepositoryTest {
    @Autowired
    private BookRepositoty bookRepositoty;

    @Test
    public void insertTest() {
        Book book = Book.builder()
                .isbn("A101010")
                .title("파워 자바")
                .author("천인국")
                .price(36000)
                .build();

        bookRepositoty.save(book);
    }

    @Test
    public void insertTest2() {
        IntStream.rangeClosed(0, 9).forEach(i -> {
            Book book = Book.builder()
                    .isbn("A101010" + i)
                    .title("파워 자바" + i)
                    .author("천인국" + i)
                    .price(36000)
                    .build();

            bookRepositoty.save(book);

        });

    }

    @Test
    public void readTest() {
        // bookRepositoty.findById(1L).get();
        Book book = bookRepositoty.findById(1L).orElse(null);
        // bookRepositoty.findById(1L).orElseThrow();

        System.out.println(book);

        // Optional<Book> result = bookRepositoty.findById(1L);
        // if (result.isPresent()) {
        // Book book = result.get();
        // }
    }

    @Test
    public void readTest2() {

        Book book = bookRepositoty.findByIsbn("A1010100").orElseThrow(EntityNotFoundException::new);

        System.out.println(book);
        List<Book> list = bookRepositoty.findByTitleContaining("파워");
        System.out.println(list);

    }

    @Test
    public void updateTest() {
        Book book = bookRepositoty.findById(1L).orElse(null);
        book.changePrice(40000);
        bookRepositoty.save(book);
    }

    @Test
    public void deleteTest() {
        bookRepositoty.deleteById(10L);
    }
}
