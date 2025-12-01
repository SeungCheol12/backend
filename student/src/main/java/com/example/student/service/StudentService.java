package com.example.student.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.student.dto.StudentDTO;
import com.example.student.entity.Student;
import com.example.student.repository.StudentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;

    // CRUD 작업을 위한 Repository 메소드 호출 후 결과를 컨트롤러에 리턴
    public String insert(StudentDTO dto) {
        // dto => entity 변경
        Student student = modelMapper.map(dto, Student.class);
        return studentRepository.save(student).getName();
    }

    public StudentDTO read(Long id) {
        Student student = studentRepository.findById(id).orElseThrow();
        // entity => dto 변경
        return modelMapper.map(student, StudentDTO.class);
    }

    public List<StudentDTO> readAll() {
        // select ~~~ order by id desc;
        List<Student> result = studentRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));

        // entity => dto
        List<StudentDTO> list = new ArrayList<>();

        for (Student student : result) {
            list.add(modelMapper.map(student, StudentDTO.class));
        }
        return list;
    }

    public Long update(StudentDTO dto) {

        // 업데이트 대상 찾기
        Student student = studentRepository.findById(dto.getId()).orElseThrow();

        student.changeGrade(dto.getGrade());
        student.changeName(dto.getName());

        return studentRepository.save(student).getId();
    }

    public void delete() {
        studentRepository.deleteById(null);
    }
}
