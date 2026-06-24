package com.dongmi.tickit.domain.event.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dongmi.tickit.domain.event.entity.Event;

public interface EventRepository extends JpaRepository<Event, UUID>, EventRepositoryCustom {
    
}
