package com.dongmi.tickit.global.exception;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class CustomException extends RuntimeException {
    private String code;
    private Object data;
    private HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

    public CustomException(String message, String code, Object data) {
        super(message);
        this.code = code;
        this.data = data;
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }
    @Builder
    public CustomException(String message, String code, Object data, HttpStatus httpStatus) {
        super(message);
        this.code = code;
        this.data = data;
        this.httpStatus = httpStatus;
    }
}
