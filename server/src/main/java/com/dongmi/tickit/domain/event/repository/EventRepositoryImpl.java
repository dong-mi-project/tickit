package com.dongmi.tickit.domain.event.repository;

import static com.dongmi.tickit.domain.event.entity.QEvent.event;
import static com.dongmi.tickit.domain.event.entity.QEventSchedule.eventSchedule;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.dongmi.tickit.domain.event.dto.EventDto;
import com.dongmi.tickit.domain.event.entity.Event;
import com.dongmi.tickit.domain.event.entity.QEvent;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class EventRepositoryImpl implements EventRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public Page<EventDto.ListEventResponse> listEvents(EventDto.ListEventRequest request, Pageable pageable) {
        JPAQuery<Event> query = queryFactory.selectFrom(event).leftJoin(event.schedules, eventSchedule).groupBy(event.id);
        // 키워드검색
        if (request.title() != null) {
            query.where(event.title.containsIgnoreCase(request.title()));
        }
        if (request.description() != null) {
            query.where(event.description.containsIgnoreCase(request.description()));
        }
        if (request.placeName() != null) {
            query.where(event.placeName.containsIgnoreCase(request.placeName()));
        }

        // 날짜 검색 - 이벤트 일정의 시작일과 종료일을 기준으로 검색
        // 일정이 여러 개일 수 있으므로, 서브쿼리를 사용하여 각 이벤트의 가장 빠른 시작일과 가장 늦은 종료일을 가져와서 검색
        if (request.startDate() != null) {
            LocalDateTime searchDatetime = request.startDate().atTime(0, 0, 0);
            query.having(eventSchedule.startAt.min().goe(searchDatetime));

        }
        if (request.endDate() != null) {
            LocalDateTime searchDatetime = request.endDate().atTime(23, 59, 59);
            query.having(eventSchedule.endAt.max().loe(searchDatetime));
        }

        // 주소검색
        if (request.sido() != null) {
            // TODO: 광역시도 검색 로직 구현
        }
        if (request.sigungu() != null) {
            // TODO: 시군구 검색 로직 구현
        }

        List<EventDto.ListEventResponse> events = query
                .select(Projections.constructor(EventDto.ListEventResponse.class,
                        event.id,
                        event.title,
                        event.description,
                        event.createdAt,
                        event.updatedAt,
                        event.deletedAt,
                        event.bookStartedAt,
                        event.bookEndedAt,
                        event.placeName,
                        event.placeAddress,
                        event.maxPerUser,
                        eventSchedule.startAt.min().as("latestScheduleStartAt")
                    ))
                // 가장 빠른 일정의 시작일을 기준으로 정렬
                .orderBy(eventSchedule.startAt.min().asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = query.clone()
        .select(event.id) // count 대신 id만 가볍게 조회
        .fetch()
        .size();;
        return new PageImpl<>(events, pageable, total);
    }
}
