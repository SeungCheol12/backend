package com.example.book.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.book.dto.BookDTO;
import com.example.book.entity.Book;
import com.example.book.repository.BookRepositoty;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepositoty bookRepositoty;
    private final ModelMapper mapper;

    public void create(BookDTO dto) {
        // dto => entity 변경
        bookRepositoty.save(mapper.map(dto, Book.class)); // .getId()
        // return 하려면 void를 Long 으로 바꾸고 .getId()를 붙인다
    }

    // 하나만 조회, 여러개 조회
    // 검색 : title => %자바%
    // isbn => 유니크이기 때문에 하나만 조회

    public List<BookDTO> readTitle(String title) {
        List<Book> result = bookRepositoty.findByTitleContaining(title);

        // List<Book> => List<BookDTO> 변경 후 리턴
        // List<BookDTO> list = new ArrayList<>();
        // result.forEach(book -> {
        // list.add(mapper.map(book, BookDTO.class));
        // });
        return result.stream().map(book -> mapper.map(book, BookDTO.class)).collect(Collectors.toList());
    }

    public BookDTO readIsbn(String isbn) {
        // Optional<Book> result = bookRepositoty.findByIsbn(isbn);
        Book book = bookRepositoty.findByIsbn(isbn).orElseThrow();

        // Optional<Book> => BookDTO 변경 후 리턴
        return mapper.map(book, BookDTO.class);
    }

    public BookDTO readId(Long id) {
        // Optional<Book> result = bookRepositoty.findByIsbn(isbn);
        Book book = bookRepositoty.findById(id).orElseThrow();

        // Optional<Book> => BookDTO 변경 후 리턴
        return mapper.map(book, BookDTO.class);
    }

    public Long update(BookDTO dto) {
        Book book = bookRepositoty.findById(dto.getId()).orElseThrow();
        book.changePrice(dto.getPrice());
        return bookRepositoty.save(book).getId();
    }

    public void delete(Long id) {
        bookRepositoty.deleteById(id);
    }
}
