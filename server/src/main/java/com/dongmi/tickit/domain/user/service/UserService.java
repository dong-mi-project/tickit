package com.dongmi.tickit.domain.user.service;

import com.dongmi.tickit.domain.event.dto.EventDto;
import com.dongmi.tickit.domain.event.entity.Event;
import com.dongmi.tickit.domain.event.repository.EventRepository;
import com.dongmi.tickit.domain.user.dto.UserDto;
import com.dongmi.tickit.domain.user.entity.User;
import com.dongmi.tickit.domain.user.repository.UserRepository;

import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Random;

// 예시 서비스입니다. 실제로는 도메인에 맞게 서비스를 작성해야 합니다.
@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private static final String EMAIL_CODE_PREFIX = "emailCode:";

    private final JavaMailSender mailSender;
    private final RedisTemplate<String, String> redisTemplate;

    // 인증 코드 생성 + 메일 발송 + Redis 저장
    public String sendLoginAuthMessage(String to) throws Exception {
        String authCode = createKey(); // 요청마다 새 코드 생성

        MimeMessage message = sendEmail(to, authCode);
        try {
            mailSender.send(message);
        } catch (MailException es) {
            es.printStackTrace();
            throw new IllegalArgumentException();
        }

        setDataExpire(EMAIL_CODE_PREFIX + to, authCode, 60 * 5L); // 키=이메일, 값=코드
        return "인증 메일이 발송되었습니다.";
    }

    // 이메일 인증 코드 검증
    public boolean verifyEmailCode(String email, String inputCode) {
        String key = EMAIL_CODE_PREFIX + email;
        String savedCode = getData(key);

        if (savedCode == null) {
            return false; // 코드 만료 또는 존재하지 않음
        }

        boolean isValid = savedCode.equals(inputCode);
        if (isValid) {
            deleteData(key); // 검증 성공 시 재사용 방지를 위해 삭제
        }
        return isValid;
    }

    // 8자리 인증 코드 생성
    public static String createKey() {
        StringBuffer key = new StringBuffer();
        Random rnd = new Random();

        for (int i = 0; i < 8; i++) { // 인증코드 8자리 생성
            int index = rnd.nextInt(3); // 0~2 랜덤으로 선택

            switch (index) {
                case 0:
                    key.append((char) ((int) (rnd.nextInt(26)) + 97)); // a~z
                    break;
                case 1:
                    key.append((char) ((int) (rnd.nextInt(26)) + 65)); // A~Z
                    break;
                case 2:
                    key.append((rnd.nextInt(10))); // 0~9
                    break;
            }
        }
        return key.toString();
    }

    // 이메일 전송용 메시지 생성
    private MimeMessage sendEmail(String to, String authCode) throws Exception {
        MimeMessage message = mailSender.createMimeMessage();
        message.addRecipients(Message.RecipientType.TO, to);
        message.setSubject("[티켓팅] 이메일 인증 코드");

        String msgg = "<div style='margin: 100px auto; padding: 20px; max-width: 600px; font-family: Arial, sans-serif; border: 1px solid #ddd; border-radius: 10px; box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);'>"
                + "<p style='font-size: 16px; color: #555; text-align: center;'>"
                + "티켓팅 사이트 <strong>티켓팅티켓팅티켓팅티켓팅</strong>입니다.</p>"
                + "<hr style='border: 0; border-top: 1px solid #eee; margin: 20px 0;'>"
                + "<p style='font-size: 16px; color: #555; text-align: center;'>아래 코드를 인증창에 입력해 주세요:</p>"
                + "<div style='text-align: center; margin: 20px 0;'>"
                + "<span style='display: inline-block; padding: 10px 20px; background-color: #007BFF; color: white; font-size: 24px; font-weight: bold; border-radius: 5px;'>"
                + authCode + "</span>"
                + "</div>"
                + "<p style='font-size: 14px; color: #999; text-align: center;'>"
                + "이 인증 코드는 5분 동안 유효합니다.</p>"
                + "<p style='font-size: 12px; color: #aaa; text-align: center;'>"
                + "감사합니다.<br><strong>티켓팅</strong> 팀</p>"
                + "</div>";

        message.setText(msgg, "utf-8", "html");
        message.setFrom(new InternetAddress("customer_son@naver.com", "티켓팅"));

        return message;
    }

    // Redis 관련 메서드들
    public String getData(String key) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        return valueOperations.get(key);
    }

    public void setDataExpire(String key, String value, long duration) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        Duration expireDuration = Duration.ofSeconds(duration);
        valueOperations.set(key, value, expireDuration);
    }

    public void deleteData(String key) {
        redisTemplate.delete(key);
    }
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