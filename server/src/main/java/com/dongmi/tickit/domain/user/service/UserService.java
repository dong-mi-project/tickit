package com.dongmi.tickit.domain.user.service;

import com.dongmi.tickit.domain.event.dto.EventDto;
import com.dongmi.tickit.domain.event.entity.Event;
import com.dongmi.tickit.domain.event.repository.EventRepository;
import com.dongmi.tickit.domain.user.dto.UserDto;
import com.dongmi.tickit.domain.user.entity.User;
import com.dongmi.tickit.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

// 예시 서비스입니다. 실제로는 도메인에 맞게 서비스를 작성해야 합니다.
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    // private final PasswordEncoder passwordEncoder; // Security 연동 시 주석 해제

    /**
     * 회원가입 로직
     * 트랜잭션을 사용하여 영속성을 이용 하다가 안되면 처음으로 다 롤백
     */
    @Transactional
    public void signUp(UserDto.SignUpRequest request) {
        // 이메일 중복 체크
        userRepository.findByEmail(request.email())
                .ifPresent(user -> {
                    throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
                });

        // 비밀번호 암호화 (시큐리티 설정 전이라면 우선 생텍스트로 테스트)
        String encodedPassword = request.password();

        // 엔티티 생성 및 저장
        User user = User.builder()
                .name(request.name())
                .email(request.email())
                .password(encodedPassword)
                .phone(request.phone())
                .accountType("U") // 기본 사용자 타입 기본값
                .build();

        userRepository.save(user);
    }

    /**
     * 로그인 로직
     */
    public UserDto.LoginResponse login(UserDto.LoginRequest request) {
        // 해당 이메일 유저 존재 여부 확인
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일이거나 비밀번호가 틀렸습니다."));

        // 비밀번호 일치 여부 확인
        // if (!passwordEncoder.matches(request.password(), user.getPassword())) { ... }
        if (!user.getPassword().equals(request.password())) {
            throw new IllegalArgumentException("존재하지 않는 이메일이거나 비밀번호가 틀렸습니다.");
        }

        // 소프트 딜리트 처리된 유저인지 체크
        if (user.getDeletedAt() != null) {
            throw new IllegalArgumentException("탈퇴한 회원입니다.");
        }

        // 응답값 반환 (시큐리티 도입 시 여기서 JWT 토큰 등을 생성하여 함께 내려줍니다)
        return new UserDto.LoginResponse(
                user.getId().toString(),
                user.getEmail(),
                user.getName(),
                "로그인 성공"
        );
    }
}