package com.dongmi.tickit.domain.event.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Event {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.BINARY)
    private UUID id;
    
    @JdbcTypeCode(SqlTypes.BINARY)
    @Column(name = "user_id", nullable = false, updatable = false)
    private UUID userId;

    @Column(nullable = false, length = 100)
    private String title;

    @Lob
    @Column(nullable = false)
    private String description;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    @Column
    private LocalDateTime deletedAt;

    @Column(nullable = false)
    private LocalDateTime bookStartedAt;

    @Column(nullable = false)
    private LocalDateTime bookEndedAt;

    @Column(length = 100)
    private String placeName;

    @Column(length = 200)
    private String placeAddress;

    @Column(nullable = false)
    private Integer maxPerUser;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<EventSchedule> schedules = new ArrayList<>();

    @Builder
    public Event(UUID userId, String title, String description, LocalDateTime bookStartedAt, LocalDateTime bookEndedAt, String placeName, String placeAddress, Integer maxPerUser) {
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

    public void update(String title, String description, LocalDateTime bookStartedAt, LocalDateTime bookEndedAt, String placeName, String placeAddress, Integer maxPerUser) {
        this.title = title;
        this.description = description;
        this.bookStartedAt = bookStartedAt;
        this.bookEndedAt = bookEndedAt;
        this.placeName = placeName;
        this.placeAddress = placeAddress;
        this.maxPerUser = maxPerUser;
        this.updatedAt = LocalDateTime.now();
    }

    public void delete() {
        this.deletedAt = LocalDateTime.now();
    }

}
