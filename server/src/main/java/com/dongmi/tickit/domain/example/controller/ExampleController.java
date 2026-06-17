package com.dongmi.tickit.domain.example.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dongmi.tickit.global.exception.CustomException;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


// 예시 컨트롤러입니다. 실제로는 도메인에 맞게 컨트롤러를 작성해야 합니다.
@RestController()
@RequestMapping("/example")
public class ExampleController {
    @RequestMapping("/text")
    public String text() {
        return "Hello, this is an example controller!";
    }
    @RequestMapping("/json")
    public Map<String, Object> json() {
        return Map.of("message", "Hello, this is an example controller!");
    }
    @RequestMapping("/exception")
    public void exception(){
        ArrayList<Map<String, Object>> data = new ArrayList<>();
        data.add(Map.of("key", "value"));
        data.add(Map.of("test", "test_value"));
        data.add(Map.of("boolean", false));
        throw new CustomException("This is test exception", "TEST_EXCEPTION", data);
    }
    
}
