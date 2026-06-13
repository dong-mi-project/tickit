package com.dongmi.tickit.domain.example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

// 예시 엔티티입니다. 실제로는 도메인에 맞게 엔티티를 작성해야 합니다.
@Entity
public class ExampleEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

}
