package com.dongmi.tickit.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

// 예시 DTO입니다. 실제로는 도메인에 맞게 DTO를 작성해야 합니다.
public class UserDto {
    // 회원가입 요청 DTO
    public record SignUpRequest(
        // 포커스 및 alert 주기 위한 설계
        @NotBlank(message = "이름은 필수입니다.") String name,
        @NotBlank(message = "이메일은 필수입니다.") @Email(message = "올바른 이메일 형식이 아닙니다.") String email,
        @NotBlank(message = "비밀번호는 필수입니다.") String password,
        @NotBlank(message = "전화번호는 필수입니다.") String phone
    ) {}

    // 로그인 요청 DTO
    public record LoginRequest(
        @NotBlank(message = "이메일을 입력해주세요.") @Email String email,
        @NotBlank(message = "비밀번호를 입력해주세요.") String password
    ) {}

    // 로그인 성공 응답 DTO
    public record LoginResponse(
        String id,
        String email,
        String name,
        String message
    ) {}
}
