package com.dongmi.tickit.domain.event.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.dongmi.tickit.domain.event.entity.Event;
import com.dongmi.tickit.domain.event.entity.EventSchedule;
import com.dongmi.tickit.domain.event.entity.EventSeatGrade;

import jakarta.validation.constraints.NotBlank;

public class EventDto {

    public record CreateEventRequest(
            @NotBlank String title,
            @NotBlank String description,
            @NotBlank LocalDateTime bookStartedAt,
            LocalDateTime bookEndedAt,
            @NotBlank String placeName,
            @NotBlank String placeAddress,
            Integer maxPerUser,
            List<EventScheduleDetailsRequest> schedules,
            List<EventSeatGradeDetailsRequest> seatGrades) {
        public Event toEntity(String userId) {
            return Event.builder()
                    .userId(UUID.fromString(userId))
                    .title(this.title)
                    .description(this.description)
                    .bookStartedAt(this.bookStartedAt)
                    .bookEndedAt(this.bookEndedAt)
                    .placeName(this.placeName)
                    .placeAddress(this.placeAddress)
                    .maxPerUser(this.maxPerUser)
                    .build();
        }

    }

    public record EventScheduleDetailsRequest(
            LocalDateTime startAt,
            LocalDateTime endAt) {
    }

    public record EventSeatGradeDetailsRequest(
            String name,
            Integer sortOrder,
            Integer total,
            Integer price) {
    }

    public record CreateEventResponse(
            String id) {
    }

    public record GetEventDetailResponse(
            String id,
            String title,
            String description,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            LocalDateTime deletedAt,
            LocalDateTime bookStartedAt,
            LocalDateTime bookEndedAt,
            String placeName,
            String placeAddress,
            Integer maxPerUser,
            List<EventScheduleDetailsResponse> schedules,
            List<EventSeatGradeDetailsResponse> seatGrades) {
        public static GetEventDetailResponse fromEntity(Event event) {
            return new GetEventDetailResponse(
                    event.getId().toString(),
                    event.getTitle(),
                    event.getDescription(),
                    event.getCreatedAt(),
                    event.getUpdatedAt(),
                    event.getDeletedAt(),
                    event.getBookStartedAt(),
                    event.getBookEndedAt(),
                    event.getPlaceName(),
                    event.getPlaceAddress(),
                    event.getMaxPerUser(),
                    event.getSchedules().stream().map(EventScheduleDetailsResponse::fromEntity).toList(),
                    event.getSeatGrades().stream().map(EventSeatGradeDetailsResponse::fromEntity).toList());
        }
    }

    public record EventScheduleDetailsResponse(
            String id,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            LocalDateTime deletedAt,
            String status,
            LocalDateTime startAt,
            LocalDateTime endAt,
            Integer roundNo) {
        public static EventScheduleDetailsResponse fromEntity(EventSchedule schedule) {
            return new EventScheduleDetailsResponse(
                    schedule.getId().toString(),
                    schedule.getCreatedAt(),
                    schedule.getUpdatedAt(),
                    schedule.getDeletedAt(),
                    schedule.getStatus().name(),
                    schedule.getStartAt(),
                    schedule.getEndAt(),
                    schedule.getRoundNo());
        }
    }

    public record EventSeatGradeDetailsResponse(
            String id,
            String name,
            LocalDateTime createdAt,
            Integer sortOrder,
            Integer total,
            Integer price) {
        public static EventSeatGradeDetailsResponse fromEntity(EventSeatGrade seatGrade) {
            return new EventSeatGradeDetailsResponse(
                    seatGrade.getId().toString(),
                    seatGrade.getName(),
                    seatGrade.getCreatedAt(),
                    seatGrade.getSortOrder(),
                    seatGrade.getTotal(),
                    seatGrade.getPrice());
        }
    }
}
