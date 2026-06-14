package com.dongmi.tickit.domain.example.repository;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ExampleRepositoryImpl implements ExampleRepositoryCustom {
    private final JPAQueryFactory queryFactory;
}
