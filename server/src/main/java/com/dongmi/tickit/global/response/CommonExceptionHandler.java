package com.dongmi.tickit.global.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.dongmi.tickit.global.dto.ApiResponse;
import com.dongmi.tickit.global.exception.CustomException;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class CommonExceptionHandler {

    // 직접 정의한 CustomException을 처리하는 핸들러
    @ExceptionHandler(value = {CustomException.class})
    public ResponseEntity<ApiResponse<?>> handleException(CustomException e, HttpServletRequest request) {
        return ResponseEntity.status(e.getHttpStatus()).body(ApiResponse.failure(e.getMessage(),e.getCode()));
    }
    // 그외엔 메시지 표시 안함
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ApiResponse<?>> handleException(Exception e) {
        e.printStackTrace();
        return ResponseEntity.internalServerError().body(ApiResponse.failure(null, "INTERNAL_SERVER_ERROR"));
    }
}
