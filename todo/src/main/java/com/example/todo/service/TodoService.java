package com.example.todo.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.swing.text.html.parser.Entity;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.todo.dto.TodoDTO;
import com.example.todo.entity.Todo;
import com.example.todo.repository.TodoRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Transactional
@Service
@Log4j2
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;
    private final ModelMapper modelMapper;
    // crud 메서드 구현

    public Long create(TodoDTO dto) {
        // dto -> entity
        Todo todo = modelMapper.map(dto, Todo.class);

        return todoRepository.save(todo).getId();

    }

    public Long update(TodoDTO dto) {
        Todo todo = todoRepository.findById(dto.getId()).orElseThrow(EntityNotFoundException::new);
        todo.changeTitle(dto.getTitle());
        todo.changeCompleted(dto.isCompleted());
        todo.changeImportant(dto.isImportant());

        return todoRepository.save(todo).getId();

    }

    public void delete(Long id) {
        todoRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<TodoDTO> findCompledTodos(boolean completed) {
        List<Todo> result = todoRepository.findByCompleted(completed);
        // entity -> dto
        // 람다 사용
        return result.stream().map(todo -> modelMapper.map(todo, TodoDTO.class)).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TodoDTO> findImportantTodos(boolean important) {
        List<Todo> result = todoRepository.findByImportant(important);
        // entity -> dto
        return result.stream().map(todo -> modelMapper.map(todo, TodoDTO.class)).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TodoDTO> findTodos() {
        List<Todo> result = todoRepository.findAll();
        // entity -> dto
        return result.stream().map(todo -> modelMapper.map(todo, TodoDTO.class)).collect(Collectors.toList());
    }
}
