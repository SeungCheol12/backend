package com.example.jpa.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.example.jpa.entity.constant.Grade;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.val;

@EntityListeners(value = AuditingEntityListener.class)
@Builder
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "stutbl")
@Entity // => 이 클래스는 테이블과 연동되어 있음
public class Student {

    // @GeneratedValue(strategy = GenerationType.AUTO) == 기본값
    // @SequenceGenerator(name = "stu_seq_gen", sequenceName = "stu_seq",
    // allocationSize = 1)
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator =
    // "stu_seq_gen")

    @GeneratedValue(strategy = GenerationType.IDENTITY) // MySQL(auto_increment),Oracle(sequence)
    @Id
    private Long id;

    // @Column(name = "sname", length = 50, nullable = false, unique = true)
    @Column(columnDefinition = "varchar(50) not null")
    private String name;

    @Column
    private String addr;

    @Column(columnDefinition = "varchar(1) CONSTRAINT chk_gender CHECK (gender IN ('M', 'F'))")
    private String gender;

    // grade => FRESHMAN, SOPHOMORE, JUNIOR, SENIOR
    @Enumerated(EnumType.STRING) // 기본 숫자로 나오는 enum 값을 String 값으로 받아낸다
    @Column
    private Grade grade;

    @CreationTimestamp // insert 시 자동으로 일자 삽입
    private LocalDateTime createDateTime1;

    @CreatedDate // spring boot 설정 후 삽입
    private LocalDateTime createDateTime2;

    @LastModifiedDate
    private LocalDateTime updatedDateTime;

    public void changeName(String name) {
        this.name = name;

    }

    public void changeGrade(Grade grade) {
        this.grade = grade;

    }

}
