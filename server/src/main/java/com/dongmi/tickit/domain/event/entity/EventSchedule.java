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
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EventSchedule {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@JdbcTypeCode(SqlTypes.BINARY)
	private UUID id;

	@ManyToOne(optional = false)
	@JoinColumn(name = "event_id", nullable = false)
	private Event event;

	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

	private LocalDateTime deletedAt;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private EventScheduleStatus status;

	@Column(nullable = false)
	private LocalDateTime startAt;
	private LocalDateTime endAt;

	@Column(nullable = false, unique = true)
	private Integer roundNo;

	@OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, orphanRemoval = true)
	private final List<EventSeatGrade> seatGrades = new ArrayList<>();

	@Builder
	public EventSchedule(Event event, LocalDateTime startAt, LocalDateTime endAt, Integer roundNo) {
		this.event = event;
		this.createdAt = LocalDateTime.now();
		this.status = EventScheduleStatus.OPEN;
		this.startAt = startAt;
		this.endAt = endAt;
		this.roundNo = roundNo;
	}

	public void updatePeriod(LocalDateTime startAt, LocalDateTime endAt) {
		this.startAt = startAt;
		this.endAt = endAt;
		this.updatedAt = LocalDateTime.now();
	}

	public void changeStatus(EventScheduleStatus status) {
		this.status = status;
		this.updatedAt = LocalDateTime.now();
	}

	public void delete() {
		this.deletedAt = LocalDateTime.now();
	}

}
