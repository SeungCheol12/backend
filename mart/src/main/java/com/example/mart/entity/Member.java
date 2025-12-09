package com.example.mart.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.example.mart.constant.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(value = AuditingEntityListener.class)
@Entity
@Getter
@ToString(exclude = "orders")
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;

    private String city;

    private String street;

    private String zipcode;

    @Builder.Default
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    public void changeCity(String city) {
        this.city = city;
    }

}
