CREATE TABLE IF NOT EXISTS `user` (
    `id` BINARY(16) NOT NULL COMMENT '사용자 식별자',
    `email` VARCHAR(50) NOT NULL COMMENT '사용자 이메일',
    `name` VARCHAR(50) NOT NULL COMMENT '사용자 이름',
    `pw` VARCHAR(100) COMMENT '사용자 비밀번호',
    `account_type` VARCHAR(1) NOT NULL DEFAULT 'U' COMMENT '계정 유형',
    `phone` VARCHAR(20) COMMENT '사용자 전화번호',
    `profile_img` VARCHAR(300) COMMENT '사용자 프로필 이미지',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '사용자 생성일',
    `updated_at` DATETIME COMMENT '사용자 수정일',
    `deleted_at` DATETIME COMMENT '사용자 삭제일',
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK_USERS_EMAIL` (`email`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '사용자';

CREATE TABLE IF NOT EXISTS `event` (
    `id` BINARY(16) NOT NULL COMMENT '이벤트 식별자',
    `user_id` BINARY(16) NOT NULL COMMENT '사용자 식별자',
    `title` VARCHAR(100) NOT NULL COMMENT '이벤트 제목',
    `description` LONGTEXT NOT NULL COMMENT '이벤트 상세',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '이벤트 생성일',
    `updated_at` DATETIME COMMENT '이벤트 수정일',
    `deleted_at` DATETIME COMMENT '이벤트 삭제일',
    `book_started_at` DATETIME NOT NULL COMMENT '이벤트 예약 시작일',
    `book_ended_at` DATETIME NOT NULL COMMENT '이벤트 예약 종료일',
    `place_name` VARCHAR(100) COMMENT '이벤트 장소 이름',
    `place_address` VARCHAR(200) COMMENT '이벤트 장소 주소',
    `max_per_user` INT NOT NULL DEFAULT 1 COMMENT '사용자당 최대 예약 수',
    PRIMARY KEY (`id`),
    CONSTRAINT `FK_EVENTS_USER_ID` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '이벤트';

CREATE TABLE IF NOT EXISTS `user_like` (
    `user_id` BINARY(16) NOT NULL COMMENT '사용자 식별자',
    `event_id` BINARY(16) NOT NULL COMMENT '이벤트 식별자',
    PRIMARY KEY (`user_id`, `event_id`),
    CONSTRAINT `FK_USER_LIKES_USER_ID` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
    CONSTRAINT `FK_USER_LIKES_EVENT_ID` FOREIGN KEY (`event_id`) REFERENCES `event` (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '사용자 찜 목록';

CREATE TABLE IF NOT EXISTS `event_schedule` (
    `id` BINARY(16) NOT NULL COMMENT '이벤트 회차 식별자',
    `event_id` BINARY(16) NOT NULL COMMENT '이벤트 식별자',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '이벤트 회차 생성일',
    `updated_at` DATETIME COMMENT '이벤트 회차 수정일',
    `deleted_at` DATETIME COMMENT '이벤트 회차 삭제일',
    `status` VARCHAR(20) NOT NULL DEFAULT 'OPEN' COMMENT '이벤트 회차 상태',
    `start_at` DATETIME NOT NULL COMMENT '이벤트 회차 시작일',
    `end_at` DATETIME COMMENT '이벤트 회차 종료일',
    `round_no` INT NOT NULL COMMENT '이벤트 회차 번호',
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK_EVENT_SCHEDULES_ROUND_NO` (`round_no`),
    CONSTRAINT `FK_EVENT_SCHEDULES_EVENT_ID` FOREIGN KEY (`event_id`) REFERENCES `event` (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '이벤트 회차';

CREATE TABLE IF NOT EXISTS `event_seat_grade` (
    `id` BINARY(16) NOT NULL COMMENT '이벤트 좌석 등급 식별자',
    `schedule_id` BINARY(16) NOT NULL COMMENT '이벤트 회차 식별자',
    `name` VARCHAR(50) NOT NULL COMMENT '좌석 등급 이름',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '좌석 등급 생성일',
    `sort_order` INT NOT NULL DEFAULT 0 COMMENT '좌석 등급 정렬 순서',
    `total` INT NOT NULL COMMENT '좌석 등급 전체 수량',
    `price` INT NOT NULL COMMENT '좌석 등급 가격',
    PRIMARY KEY (`id`),
    CONSTRAINT `FK_EVENT_SEAT_GRADES_SCHEDULE_ID` FOREIGN KEY (`schedule_id`) REFERENCES `event_schedule` (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '이벤트 좌석 등급';

CREATE TABLE IF NOT EXISTS `review` (
    `id` BINARY(16) NOT NULL COMMENT '후기 식별자',
    `user_id` BINARY(16) NOT NULL COMMENT '사용자 식별자',
    `score` INT(1) NOT NULL COMMENT '후기 점수',
    `content` TEXT COMMENT '후기 내용',
    PRIMARY KEY (`id`),
    CONSTRAINT `FK_REVIEWS_USER_ID` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '후기';

CREATE TABLE IF NOT EXISTS `review_img` (
    `id` BINARY(16) NOT NULL COMMENT '후기 이미지 식별자',
    `review_id` BINARY(16) NOT NULL COMMENT '후기 식별자',
    `img_url` VARCHAR(200) NOT NULL COMMENT '후기 이미지 URL',
    `sort_order` INT NOT NULL DEFAULT 0 COMMENT '후기 이미지 정렬 순서',
    PRIMARY KEY (`id`),
    CONSTRAINT `FK_REVIEW_IMGS_REVIEW_ID` FOREIGN KEY (`review_id`) REFERENCES `review` (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '후기 이미지';

CREATE TABLE IF NOT EXISTS `ticket` (
    `id` BINARY(16) NOT NULL COMMENT '티켓 식별자',
    `ticket_number` VARCHAR(50) NOT NULL COMMENT '티켓 번호',
    `schedule_id` BINARY(16) NOT NULL COMMENT '이벤트 회차 식별자',
    `user_id` BINARY(16) NOT NULL COMMENT '사용자 식별자',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '티켓 생성일',
    `canceled_at` DATETIME COMMENT '티켓 취소일',
    `used_at` DATETIME COMMENT '티켓 사용일',
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK_TICKETS_TICKET_NUMBER` (`ticket_number`),
    CONSTRAINT `FK_TICKETS_SCHEDULE_ID` FOREIGN KEY (`schedule_id`) REFERENCES `event_schedule` (`id`),
    CONSTRAINT `FK_TICKETS_USER_ID` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '티켓';

CREATE TABLE IF NOT EXISTS `payment` (
    `id` BINARY(16) NOT NULL COMMENT '결제 식별자',
    `ticket_id` BINARY(16) NOT NULL COMMENT '티켓 식별자',
    `toss_payment_key` VARCHAR(50) NOT NULL COMMENT '토스 결제 식별자',
    `payment_status` VARCHAR(50) NOT NULL COMMENT '결제 상태',
    `approved_at` TIMESTAMP COMMENT '결제 승인일',
    `canceled_at` TIMESTAMP COMMENT '결제 취소일',
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK_PAYMENTS_TOSS_PAYMENT_KEY` (`toss_payment_key`),
    CONSTRAINT `FK_PAYMENTS_TICKET_ID` FOREIGN KEY (`ticket_id`) REFERENCES `ticket` (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '결제 내역';

CREATE TABLE IF NOT EXISTS `payment_log` (
    `id` BINARY(16) NOT NULL COMMENT '결제 로그 식별자',
    `payment_id` BINARY(16) NOT NULL COMMENT '결제 식별자',
    `event_type` VARCHAR(50) NOT NULL COMMENT '결제 이벤트 유형',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '결제 로그 생성일',
    PRIMARY KEY (`id`),
    CONSTRAINT `FK_PAYMENT_LOGS_PAYMENT_ID` FOREIGN KEY (`payment_id`) REFERENCES `payment` (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '결제 로그';

CREATE TABLE IF NOT EXISTS `contact` (
    `id` BINARY(16) NOT NULL COMMENT '문의 식별자',
    `par_id` BINARY(16) COMMENT '상위 문의 식별자',
    `user_id` BINARY(16) NOT NULL COMMENT '사용자 식별자',
    `content` TEXT NOT NULL COMMENT '문의 내용',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '문의 생성일',
    PRIMARY KEY (`id`),
    CONSTRAINT `FK_CONTACT_PAR_ID` FOREIGN KEY (`par_id`) REFERENCES `contact` (`id`),
    CONSTRAINT `FK_CONTACT_USER_ID` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '문의 내역';

CREATE TABLE IF NOT EXISTS `contact_img` (
    `id` BINARY(16) NOT NULL COMMENT '문의 이미지 식별자',
    `contact_id` BINARY(16) NOT NULL COMMENT '문의 식별자',
    `img_url` VARCHAR(200) NOT NULL COMMENT '문의 이미지 URL',
    `sort_order` INT NOT NULL DEFAULT 0 COMMENT '문의 이미지 정렬 순서',
    PRIMARY KEY (`id`),
    CONSTRAINT `FK_CONTACT_IMGS_CONTACT_ID` FOREIGN KEY (`contact_id`) REFERENCES `contact` (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '문의 내역 이미지';

CREATE TABLE IF NOT EXISTS `penalty` (
    `id` BINARY(16) NOT NULL COMMENT '제제 내역 식별자',
    `admin_id` BINARY(16) NOT NULL COMMENT '제제 처리 관리자 식별자',
    `user_id` BINARY(16) NOT NULL COMMENT '제제 대상 사용자 식별자',
    `payload` TEXT NOT NULL COMMENT '제제 사유',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '제제 내역 생성일',
    PRIMARY KEY (`id`),
    CONSTRAINT `FK_PENALTIES_ADMIN_ID` FOREIGN KEY (`admin_id`) REFERENCES `user` (`id`),
    CONSTRAINT `FK_PENALTIES_USER_ID` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '제제 내역';
