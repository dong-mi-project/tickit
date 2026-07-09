package com.dongmi.tickit.domain.user.controller;

import com.dongmi.tickit.domain.user.dto.UserDto;
import com.dongmi.tickit.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


// 예시 컨트롤러입니다. 실제로는 도메인에 맞게 컨트롤러를 작성해야 합니다.

@RestController
@Slf4j
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "User API", description = "사용자 관련 API")
public class UserController {
    private final UserService userService;

    // 인증 메일 보내기
    @Operation(summary = "이메일 인증 코드 보내기", description = "파람값에 있는 이메일에 인증코드를 보냅니다.")
    @ResponseBody
    @GetMapping("/send")
    public String sendAuthEmail(@RequestParam String email) throws Exception {
        return userService.sendLoginAuthMessage(email);
    }

    // 인증 번호 검증하기
    @Operation(summary = "이메일 인증 코드 검증", description = "입력한 인증 코드가 올바른지 확인합니다.")
    @GetMapping("/verify")
    public ResponseEntity<Boolean> checkAuthEmail(
            @RequestParam String email,
            @RequestParam String inputCode) {

        boolean isValid = userService.verifyEmailCode(email, inputCode);

        if (isValid) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 1. 회원가입 API
     */
    @Operation(summary = "회원가입", description = "회원가입 메서드")
    @PostMapping("signup")
    public ResponseEntity<?> signUp(@Valid @RequestBody UserDto.SignUpRequest request) {
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

    // 아이디 찾는 메서드
    @Operation(summary = "아이디 찾기", description = "아이디 찾는 메서드")
    @PostMapping("findId")
    public ResponseEntity<String> findMyEmail(@RequestParam String name,
                         String phone) {
        String email = userService.findMyEmail(name, phone);
        return ResponseEntity.ok(email);
    }

    @Operation(summary = "비밀번호 찾기 - 인증코드 발송", description = "이름/전화번호/이메일 확인 후 인증코드 발송")
    @PostMapping("/find")
    public ResponseEntity<Void> sendVerificationCode(@Valid @RequestBody UserDto.FindPasswordRequest request) {
        userService.sendVerificationCode(request.name(), request.phone(), request.email());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "인증코드 확인", description = "인증코드 검증 후 재설정 토큰 발급")
    @PostMapping("/verify-code")
    public ResponseEntity<String> verifyCode(@Valid @RequestBody UserDto.VerifyCodeRequest request) {
        String resetToken = userService.verifyCodeAndIssueToken(request.email(), request.code());
        return ResponseEntity.ok(resetToken);
    }

    @Operation(summary = "비밀번호 재설정", description = "토큰 검증 후 새 비밀번호로 변경")
    @PatchMapping("/reset")
    public ResponseEntity<Void> resetPassword(@Valid @RequestBody UserDto.ResetPasswordRequest request) {
        userService.resetPassword(request.resetToken(), request.newPassword());
        return ResponseEntity.ok().build();
    }
}
