package com.example.todo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.todo.entity.Todo;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    Page<Todo> findByCompleted(boolean completed, Pageable pageable);

    List<Todo> findByImportant(boolean important);
}
