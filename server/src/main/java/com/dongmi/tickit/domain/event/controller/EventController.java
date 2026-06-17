package com.dongmi.tickit.domain.event.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/event")
public class EventController {
    
    @GetMapping("/test")
    public String test() {
        return new String("hello world");
    }
}
