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
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EventSeatGrade {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.BINARY)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "schedule_id", nullable = false)
    private EventSchedule schedule;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private Integer sortOrder;

    @Column(nullable = false)
    private Integer total;

    @Column(nullable = false)
    private Integer price;

    @Builder
    public EventSeatGrade(EventSchedule schedule, String name, Integer sortOrder, Integer total, Integer price) {
        this.schedule = schedule;
        this.name = name;
        this.createdAt = LocalDateTime.now();
        this.sortOrder = sortOrder == null ? 0 : sortOrder;
        this.total = total;
        this.price = price;
    }

    public void update(String name, Integer sortOrder, Integer total, Integer price) {
        this.name = name;
        this.sortOrder = sortOrder;
        this.total = total;
        this.price = price;
    }
}
