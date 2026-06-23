package com.dongmi.tickit.domain.user.controller;

import com.dongmi.tickit.domain.user.dto.UserDto;
import com.dongmi.tickit.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


// 예시 컨트롤러입니다. 실제로는 도메인에 맞게 컨트롤러를 작성해야 합니다.

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "User API", description = "사용자 관련 API")
public class UserController {
    private final UserService userService;

    @GetMapping("test")
    public String test() {
        return new String("hello world");
    }

    /**
     * 1. 회원가입 API
     */
    @Operation(summary = "회원가입", description = "회원가입 메서드")
    @PostMapping("signup")
    public ResponseEntity<String> signUp(@Valid @RequestBody UserDto.SignUpRequest request) {
        userService.signUp(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("회원가입이 완료되었습니다.");
    }

    /**
     * 2. 로그인 API
     */
    @Operation(summary = "로그인", description = "로그인 메서드")
    @PostMapping("login")
    public ResponseEntity<UserDto.LoginResponse> login(@Valid @RequestBody UserDto.LoginRequest request) {
        UserDto.LoginResponse response = userService.login(request);
        return ResponseEntity.ok(response);
    }
}
