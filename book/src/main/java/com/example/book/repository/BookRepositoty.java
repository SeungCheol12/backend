package com.example.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.book.entity.Book;
import java.util.List;
import java.util.Optional;

public interface BookRepositoty extends JpaRepository<Book, Long> {

    Optional<Book> findByIsbn(String isbn); // => where isbn = 'A1000'

    List<Book> findByTitleContaining(String title); // => where title like '%자바%'
}
