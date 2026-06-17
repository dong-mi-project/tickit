package com.dongmi.tickit.domain.event.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class Event {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.BINARY)
    private UUID id;
    
    @Column(nullable = false, updatable = false)
    private String userId;
    @Column(nullable = false)
    private String title;
    @Lob
    @Column(nullable = false)
    private String description;
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    @Column(nullable = false)
    private LocalDateTime bookStartedAt;
    @Column(nullable = false)
    private LocalDateTime bookEndedAt;
    @Column(nullable = false)
    private String placeName;
    @Column(nullable = false)
    private String placeAddress;
    @Column(nullable = false)
    private Integer maxPerUser;

    @Builder
    public Event(String userId, String title, String description, LocalDateTime bookStartedAt, LocalDateTime bookEndedAt, String placeName, String placeAddress, Integer maxPerUser) {
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.createdAt = LocalDateTime.now();
        this.bookStartedAt = bookStartedAt;
        this.bookEndedAt = bookEndedAt;
        this.placeName = placeName;
        this.placeAddress = placeAddress;
        this.maxPerUser = maxPerUser;
    }

    public void delete() {
        this.deletedAt = LocalDateTime.now();
    }

}
