# Client 구성

## 프로젝트 폴더 구조

### [Feature-Sliced Design](https://feature-sliced.design/docs/guides/tech/with-nextjs)

- 예시
```
src/
├── app/                  // ⭐️ Next.js 라우터 (페이지 진입점, FSD의 pages 역할 흡수)
│   ├── layout.tsx
│   └── events/[id]/page.tsx 
│
├── widgets/              // ⭐️ 독립적으로 동작하는 큰 UI 블록
│   ├── Header/           // 상단 네비게이션 (유저 정보 + 로고 포함)
│   └── TicketBookingBoard/ // 티켓 예매 대시보드 (티켓 정보 + 예매 버튼 조합)
│
├── features/             // ⭐️ 사용자의 액션 (비즈니스 로직 포함)
│   ├── auth/             // 로그인, 로그아웃 액션
│   └── ticket-booking/   // 예매하기 폼, 선착순 클릭 버튼 로직
│
├── entities/             // ⭐️ 비즈니스 주체 (순수한 UI와 데이터 타입)
│   ├── user/             // 유저 프로필 UI, 유저 데이터 타입
│   └── ticket/           // 티켓 한 장의 정보 UI, 티켓 데이터 타입
│
└── shared/               // ⭐️ 완전 공통 (도메인 무관)
    ├── ui/               // 디자인 시스템 (Button, Input, Modal)
    ├── api/              // axios/fetch 공통 인스턴스
    └── lib/              // 공통 유틸 함수
```

## Getting Started

First, run the development server:

```bash
npm run dev
# or
yarn dev
# or
pnpm dev
# or
bun dev
```

Open [http://localhost:3000](http://localhost:3000) with your browser to see the result.

You can start editing the page by modifying `app/page.tsx`. The page auto-updates as you edit the file.

This project uses [`next/font`](https://nextjs.org/docs/app/building-your-application/optimizing/fonts) to automatically optimize and load [Geist](https://vercel.com/font), a new font family for Vercel.
