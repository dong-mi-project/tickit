package com.dongmi.tickit.domain.event.controller;

import com.dongmi.tickit.domain.event.service.EventService;
import org.springframework.web.bind.annotation.RestController;

import com.dongmi.tickit.domain.event.dto.EventDto;

import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;

import org.hibernate.validator.constraints.UUID;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/event")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @GetMapping("/test")
    public String test() {
        return new String("hello world");
    }

    @PostMapping("/create")
    public EventDto.CreateEventResponse createEvent(@RequestBody EventDto.CreateEventRequest request) {
        String userId = "00000000-0000-0000-0000-000000000000"; // TODO: 실제로는 인증된 사용자 ID를 가져와야 함
        return eventService.createEvent(request, userId);
    }

    @GetMapping("/detail/{id}")
    public EventDto.GetEventDetailResponse getEventDetail(@PathVariable("id") @NotEmpty @UUID String id) {
        return eventService.getEventDetail(id);
    }
}
