# 백엔드

- Spring boot `3.5.15`
- Java `25`


## 프로젝트 폴더 구성

### Domain-Driven Design

- 비즈니스 로직별로 도메인 생성.
- 예시
    ```
    com.dongmi.tickit/
    ├── global/              // 프로젝트 전역 공통 설정
    │   ├── config/
    │   ├── security/
    │   ├── exception/
    │   ├── common/
    │   └── .../
    └── domain/
        └── {도메인명}/      // 비즈니스 로직별 도메인 분리
            ├── contorller/
            ├── service/
            ├── repository/
            ├── enitiy/
            └── dto/
    ```