"use client";

import { useState } from "react";

// ---- 임시 데이터 (나중에 API 연결 시 교체) ----
const featuredEvent = {
  title: "MIDNIGHT ECHO",
  subtitle: "2026 WORLD TOUR — SEOUL",
  venue: "잠실 종합운동장 주경기장",
  date: "2026.09.12 (SAT) 19:00",
  price: "99,000원 ~",
};

const events = [
  {
    id: 1,
    category: "CONCERT",
    title: "가을밤, 재즈",
    venue: "블루노트 서울",
    date: "08.22",
    price: "88,000원",
  },
  {
    id: 2,
    category: "MUSICAL",
    title: "빛의 정원",
    venue: "예술의전당 오페라극장",
    date: "09.03",
    price: "120,000원",
  },
  {
    id: 3,
    category: "CONCERT",
    title: "언더그라운드 나잇",
    venue: "홍대 롤링홀",
    date: "09.15",
    price: "55,000원",
  },
  {
    id: 4,
    category: "FESTIVAL",
    title: "리버사이드 뮤직페스트",
    venue: "한강 난지캠핑장",
    date: "10.04",
    price: "132,000원",
  },
];

export default function MainPage() {
  const [hovered, setHovered] = useState(null);

  return (
    <div className="min-h-screen bg-[#0B0B0D] text-[#F5F3EE] font-sans selection:bg-[#E8A33D] selection:text-black">
      {/* ---------- 네비게이션 ---------- */}
      <header className="flex items-center justify-between px-6 md:px-12 py-6 border-b border-white/10">
        <span className="text-xl font-black tracking-tight">
          TICK<span className="text-[#E8A33D]">IT</span>
        </span>
        <nav className="hidden md:flex gap-8 text-sm text-white/60">
          <a href="#" className="hover:text-white transition-colors">
            공연
          </a>
          <a href="#" className="hover:text-white transition-colors">
            뮤지컬
          </a>
          <a href="#" className="hover:text-white transition-colors">
            페스티벌
          </a>
          <a href="#" className="hover:text-white transition-colors">
            마이 티켓
          </a>
        </nav>
        <button className="text-sm px-4 py-2 rounded-full border border-white/20 hover:bg-white hover:text-black transition-colors">
          로그인
        </button>
      </header>

      {/* ---------- 히어로: 스포트라이트 ---------- */}
      <section className="relative overflow-hidden px-6 md:px-12 py-20 md:py-28">
        <div
          className="pointer-events-none absolute -top-40 left-1/2 -translate-x-1/2 w-[800px] h-[800px] rounded-full opacity-30 blur-3xl"
          style={{
            background:
              "radial-gradient(circle, rgba(232,163,61,0.55) 0%, rgba(232,163,61,0) 70%)",
          }}
        />
        <div className="relative max-w-3xl">
          <p className="text-xs tracking-[0.3em] text-[#E8A33D] mb-4">
            NOW SELLING
          </p>
          <h1 className="text-5xl md:text-7xl font-black leading-[1.05] tracking-tight mb-6">
            {featuredEvent.title}
          </h1>
          <p className="text-white/60 text-lg mb-8">{featuredEvent.subtitle}</p>

          <div className="flex flex-wrap gap-x-8 gap-y-2 text-sm text-white/70 mb-10">
            <span>{featuredEvent.venue}</span>
            <span className="text-white/30">|</span>
            <span>{featuredEvent.date}</span>
            <span className="text-white/30">|</span>
            <span className="text-[#E8A33D] font-semibold">
              {featuredEvent.price}
            </span>
          </div>

          <button className="group inline-flex items-center gap-3 bg-[#E8A33D] text-black font-bold px-8 py-4 rounded-full hover:bg-[#f4b658] transition-colors">
            예매하기
            <span className="transition-transform group-hover:translate-x-1">
              →
            </span>
          </button>
        </div>
      </section>

      {/* ---------- 티켓 스텁 카드 그리드 ---------- */}
      <section className="px-6 md:px-12 pb-24">
        <div className="flex items-baseline justify-between mb-8">
          <h2 className="text-2xl font-bold">지금 뜨는 공연</h2>
          <a
            href="#"
            className="text-sm text-white/50 hover:text-white transition-colors"
          >
            전체보기 →
          </a>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
          {events.map((ev) => (
            <button
              key={ev.id}
              onMouseEnter={() => setHovered(ev.id)}
              onMouseLeave={() => setHovered(null)}
              className="relative text-left rounded-2xl bg-[#151517] border border-white/10 overflow-hidden transition-transform hover:-translate-y-1"
            >
              {/* 티켓 절취선 느낌 (상단) */}
              <div className="relative h-28 bg-gradient-to-br from-[#232326] to-[#151517] flex items-center justify-center">
                <span className="text-xs tracking-[0.2em] text-white/30">
                  {ev.category}
                </span>
                <div
                  className="absolute -bottom-3 left-0 right-0 h-6 bg-[#0B0B0D]"
                  style={{
                    maskImage:
                      "radial-gradient(circle 6px, transparent 6px, black 6.5px)",
                    maskSize: "16px 16px",
                    maskRepeat: "repeat-x",
                    maskPosition: "top",
                    WebkitMaskImage:
                      "radial-gradient(circle 6px, transparent 6px, black 6.5px)",
                    WebkitMaskSize: "16px 16px",
                    WebkitMaskRepeat: "repeat-x",
                    WebkitMaskPosition: "top",
                  }}
                />
              </div>

              <div className="p-5 pt-6">
                <h3 className="font-bold text-lg mb-1">{ev.title}</h3>
                <p className="text-white/50 text-sm mb-4">{ev.venue}</p>
                <div className="flex items-center justify-between text-sm">
                  <span className="text-white/70">{ev.date}</span>
                  <span
                    className={`font-semibold transition-colors ${
                      hovered === ev.id ? "text-[#E8A33D]" : "text-white/90"
                    }`}
                  >
                    {ev.price}
                  </span>
                </div>
              </div>
            </button>
          ))}
        </div>
      </section>

      {/* ---------- 푸터 ---------- */}
      <footer className="px-6 md:px-12 py-10 border-t border-white/10 flex flex-col md:flex-row items-center justify-between gap-4 text-xs text-white/40">
        <span>© 2026 TICKIT. All rights reserved.</span>
        <div className="flex gap-6">
          <a href="#" className="hover:text-white/70 transition-colors">
            이용약관
          </a>
          <a href="#" className="hover:text-white/70 transition-colors">
            개인정보처리방침
          </a>
          <a href="#" className="hover:text-white/70 transition-colors">
            고객센터
          </a>
        </div>
      </footer>
    </div>
  );
}
