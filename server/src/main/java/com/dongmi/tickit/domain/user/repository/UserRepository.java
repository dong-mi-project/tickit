package com.dongmi.tickit.domain.user.repository;

import com.dongmi.tickit.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID>, UserRepositoryCustom {
    // 이메일 중복 테스트
    Optional<User> findByEmail(String email);
}
