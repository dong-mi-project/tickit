package com.dongmi.tickit.domain.event.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dongmi.tickit.domain.event.dto.EventDto;

public interface EventRepositoryCustom {
    public Page<EventDto.ListEventResponse> listEvents(EventDto.ListEventRequest request, Pageable pageable);
}
