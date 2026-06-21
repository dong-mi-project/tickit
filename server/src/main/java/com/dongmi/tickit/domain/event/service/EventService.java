package com.dongmi.tickit.domain.event.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dongmi.tickit.domain.event.dto.EventDto;
import com.dongmi.tickit.domain.event.entity.Event;
import com.dongmi.tickit.domain.event.repository.EventRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventService {
    
    private final EventRepository eventRepository;


    public EventDto.CreateEventResponse createEvent(EventDto.CreateEventRequest request, String userId) {
        Event event = request.toEntity(userId);

        eventRepository.save(event);

        return new EventDto.CreateEventResponse(event.getId());
    }
    
    public EventDto.GetEventDetailResponse getEventDetail(String id) {
        UUID eventId = UUID.fromString(id);

        Event eventEntity = eventRepository.findById(eventId).orElse(null);
        EventDto.GetEventDetailResponse eventResponse = EventDto.GetEventDetailResponse.fromEntity(eventEntity);

        return eventResponse;
    }

    public Page<EventDto.ListEventResponse> listEvents(EventDto.ListEventRequest request, Pageable pageable) {
        return eventRepository.listEvents(request, pageable);
    }
}
