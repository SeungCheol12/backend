package com.example.book.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.book.dto.BookDTO;
import com.example.book.dto.PageRequestDTO;
import com.example.book.dto.PageResultDTO;
import com.example.book.entity.Book;
import com.example.book.repository.BookRepositoty;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class BookService {

    private final BookRepositoty bookRepositoty;
    private final ModelMapper mapper;

    public String create(BookDTO dto) {
        // dto => entity 변경
        return bookRepositoty.save(mapper.map(dto, Book.class)).getTitle(); // .getId()
        // return 하려면 void를 Long 으로 바꾸고 .getId()를 붙인다
        // getTitle()을 붙이고 String 으로 return 했다
    }

    // 하나만 조회, 여러개 조회
    // 검색 : title => %자바%
    // isbn => 유니크이기 때문에 하나만 조회
    @Transactional(readOnly = true)
    public List<BookDTO> readTitle(String title) {
        List<Book> result = bookRepositoty.findByTitleContaining(title);

        // List<Book> => List<BookDTO> 변경 후 리턴
        // List<BookDTO> list = new ArrayList<>();
        // result.forEach(book -> {
        // list.add(mapper.map(book, BookDTO.class));
        // });
        return result.stream().map(book -> mapper.map(book, BookDTO.class)).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public BookDTO readIsbn(String isbn) {
        // Optional<Book> result = bookRepositoty.findByIsbn(isbn);
        Book book = bookRepositoty.findByIsbn(isbn).orElseThrow();

        // Optional<Book> => BookDTO 변경 후 리턴
        return mapper.map(book, BookDTO.class);
    }

    @Transactional(readOnly = true)
    public BookDTO readId(Long id) {
        // Optional<Book> result = bookRepositoty.findByIsbn(isbn);
        Book book = bookRepositoty.findById(id).orElseThrow();

        // Optional<Book> => BookDTO 변경 후 리턴
        return mapper.map(book, BookDTO.class);
    }

    @Transactional(readOnly = true)
    public PageResultDTO<BookDTO> getList(PageRequestDTO pageRequestDTO) {
        // 0부터 시작하기 때문에 getPage 에서 -1을 한다
        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() - 1, pageRequestDTO.getSize(),
                Sort.by("id").descending());

        Page<Book> result = bookRepositoty
                .findAll(bookRepositoty.makePredicate(pageRequestDTO.getType(), pageRequestDTO.getKeyword()), pageable);

        // get() : Stream<Book>
        List<BookDTO> dtoList = result.get().map(book -> mapper.map(book, BookDTO.class)).collect(Collectors.toList());
        // 전체 행의 개수
        long totalCount = result.getTotalElements();

        return PageResultDTO.<BookDTO>withAll()
                .dtoList(dtoList)
                .pageRequestDTO(pageRequestDTO)
                .totalCount(totalCount)
                .build();
    }

    public Long update(BookDTO dto) {
        Book book = bookRepositoty.findById(dto.getId()).orElseThrow();
        book.changePrice(dto.getPrice());
        book.changeDescription(dto.getDescription());
        // return bookRepositoty.save(book).getId(); => dirty checking
        return book.getId();
    }

    public void delete(Long id) {
        bookRepositoty.deleteById(id);
    }
}
