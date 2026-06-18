package com.dongmi.tickit.global.response;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.dongmi.tickit.global.dto.ApiResponse;
import com.dongmi.tickit.global.exception.CustomException;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class CommonExceptionHandler {

    // 직접 정의한 CustomException을 처리하는 핸들러
    @ExceptionHandler(value = { CustomException.class })
    public ResponseEntity<ApiResponse<?>> handleException(CustomException e, HttpServletRequest request) {
        return ResponseEntity.status(e.getHttpStatus()).body(ApiResponse.failure(e.getMessage(), e.getCode()));
    }

    // 400 Bad Request (DTO 검증 실패)
    @ExceptionHandler(value = { MethodArgumentNotValidException.class })
    public ResponseEntity<ApiResponse<?>> handleValidationException(MethodArgumentNotValidException e) {
        // 여러 에러 중 첫 번째 에러 메시지만 가져와서 응답하는 방식
        String errorMessage = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.failure(errorMessage, "INVALID_INPUT"));
    }

    // 400 Bad Request (잘못된 요청 본문)
    @ExceptionHandler(value = { HttpMessageNotReadableException.class })
    public ResponseEntity<ApiResponse<?>> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.failure("요청 본문(JSON) 형식이 잘못되었거나 데이터 타입이 맞지 않습니다.", "BAD_REQUEST"));
    }

    // 400 Bad Request (필수 파라미터 누락)
    @ExceptionHandler(value = { MissingServletRequestParameterException.class })
    public ResponseEntity<ApiResponse<?>> handleMissingServletRequestParameterException(
            MissingServletRequestParameterException e) {
        String errorMessage = e.getParameterName() + " 파라미터가 누락되었습니다.";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.failure(errorMessage, "MISSING_PARAMETER"));
    }

    // 404 Not Found 에러 처리
    @ExceptionHandler(value = { NoHandlerFoundException.class })
    public ResponseEntity<ApiResponse<?>> handleException(NoHandlerFoundException e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.failure(e.getMessage(), "NOT_FOUND"));
    }

    // 405 Method Not Allowed
    @ExceptionHandler(value = { HttpRequestMethodNotSupportedException.class })
    public ResponseEntity<ApiResponse<?>> handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException e) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(ApiResponse.failure("지원하지 않는 HTTP 메서드입니다.", "METHOD_NOT_ALLOWED"));
    }

    // 그외엔 메시지 표시 안함
    @ExceptionHandler(value = { Exception.class })
    public ResponseEntity<ApiResponse<?>> handleException(Exception e) {
        e.printStackTrace();
        return ResponseEntity.internalServerError().body(ApiResponse.failure(null, "INTERNAL_SERVER_ERROR"));
    }
}
