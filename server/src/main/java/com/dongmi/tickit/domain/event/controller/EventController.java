package com.dongmi.tickit.domain.event.controller;

import com.dongmi.tickit.domain.event.service.EventService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.RestController;

import com.dongmi.tickit.domain.event.dto.EventDto;

import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;

import org.hibernate.validator.constraints.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
@Tag(name = "Event API", description = "이벤트 관련 API")
public class EventController {

    private final EventService eventService;

    @GetMapping("test")
    public String test() {
        return new String("hello world");
    }

    @Operation(summary = "이벤트 생성", description = "새로운 이벤트를 생성합니다.")
    @PostMapping("create")
    public EventDto.CreateEventResponse createEvent(@RequestBody EventDto.CreateEventRequest request) {
        String userId = "00000000-0000-0000-0000-000000000000"; // TODO: 실제로는 인증된 사용자 ID를 가져와야 함
        return eventService.createEvent(request, userId);
    }

    @Operation(summary = "이벤트 상세 조회", description = "이벤트 ID로 이벤트 상세 정보를 조회합니다.")
    @GetMapping("{id}")
    public EventDto.GetEventDetailResponse getEventDetail(@PathVariable("id") @NotEmpty @UUID String id) {
        return eventService.getEventDetail(id);
    }

    // TODO: 이벤트 필터링 조건 추가
    @Operation(summary = "이벤트 검색", description = "검색 조건에 따라 이벤트를 검색")
    @GetMapping("list")
    public Page<EventDto.ListEventResponse> listEvents(EventDto.ListEventRequest request, @PageableDefault(size = 20, sort = "createdAt,desc") Pageable pageable) {
        return eventService.listEvents(request, pageable);
    }
    
}
