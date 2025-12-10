package com.example.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.example.book.entity.Book;
import com.example.book.entity.QBook;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

import java.util.List;
import java.util.Optional;

public interface BookRepositoty extends JpaRepository<Book, Long>, QuerydslPredicateExecutor<Book> {

    Optional<Book> findByIsbn(String isbn); // => where isbn = 'A1000'

    List<Book> findByTitleContaining(String title); // => where title like '%자바%'

    List<Book> findByAuthor(String author); // => where author = ''

    List<Book> findByAuthorStartingWith(String author);// => where author like '%이름'

    List<Book> findByAuthorEndingWith(String author);// => where author like '이름%'

    List<Book> findByAuthorContaining(String author);// => where author like '%이름%'

    List<Book> findByPriceBetween(int startPrice, int endPrice);// 도서가격이 12000 이상 35000 이하

    public default Predicate makePredicate(String type, String keyword) {
        BooleanBuilder builder = new BooleanBuilder();
        QBook book = QBook.book;

        builder.and(book.id.gt(0));
        return builder;
    }

}
