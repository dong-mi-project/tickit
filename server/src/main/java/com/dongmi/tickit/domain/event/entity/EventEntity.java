package com.dongmi.tickit.domain.event.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "events")
public class EventEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    

}
