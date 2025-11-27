package com.example.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jpa.entity.Student;

// DAO 역할
// 기본적인 CRUD 메소드는 이미 정의되어 있음
// < > 안에 entity 의 클래스명과 id 타입을 작성한다
public interface StudentRepository extends JpaRepository<Student, Long> {

}
