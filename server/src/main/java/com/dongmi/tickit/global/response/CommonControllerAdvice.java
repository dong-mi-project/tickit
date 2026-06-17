package com.dongmi.tickit.global.response;

import org.springframework.core.MethodParameter;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.dongmi.tickit.global.dto.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.Nullable;

@RestControllerAdvice
public class CommonControllerAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    @Nullable
    public Object beforeBodyWrite(@Nullable Object body, MethodParameter returnType, MediaType selectedContentType,
            Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
            ServerHttpResponse response) {

        Object returnBody = body;
        String url = request.getURI().getPath().toString().replace("/sapi", "");
        // 파일 다운로드 등의 경우 Resource 객체를 반환하기 때문에 ApiResponse로 감싸지 않고 그대로 반환하도록 처리
        if (body instanceof ApiResponse || url.startsWith("/api/") || body instanceof Resource) {
            returnBody = body;
        }
        else if (body != null) {
            ApiResponse<?> apiResponse = ApiResponse.success(body);
            returnBody = apiResponse;
        }
        if (returnBody instanceof ApiResponse) {
            // Controller에서 String으로 리턴했을 경우 StringConverter로 리턴하기 때문에 JSON을 String으로 변환해주어야 함
            if (body instanceof String) {
                try {
                    response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
                    ObjectMapper objectMapper = new ObjectMapper();
                    return objectMapper.writeValueAsString(returnBody);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }
        return returnBody;
    }
}
