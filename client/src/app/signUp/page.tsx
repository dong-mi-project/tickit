import Link from "next/link";

export default function SignUpPage() {
  return (
    <div className="flex flex-col items-center justify-center min-h-screen bg-zinc-50 dark:bg-black font-sans p-4">
      <div className="w-full max-w-md bg-white dark:bg-zinc-900 p-8 rounded-2xl shadow-sm border border-zinc-100 dark:border-zinc-800 my-8">
        <h1 className="text-2xl font-bold text-center text-zinc-900 dark:text-zinc-50 mb-8">
          회원가입
        </h1>

        <form className="flex flex-col gap-5">
          {/* 이름 입력란 */}
          <div className="flex flex-col gap-1.5">
            <label className="text-sm font-medium text-zinc-700 dark:text-zinc-300">
              이름
            </label>
            <input
              type="text"
              placeholder="홍길동"
              className="h-12 w-full px-4 rounded-xl border border-zinc-200 dark:border-zinc-700 bg-transparent text-zinc-900 dark:text-zinc-50 placeholder-zinc-400 focus:outline-none focus:ring-2 focus:ring-black dark:focus:ring-white transition-all"
              required
            />
          </div>

          {/* 이메일 및 인증번호 입력란 */}
          <div className="flex flex-col gap-1.5">
            <label className="text-sm font-medium text-zinc-700 dark:text-zinc-300">
              이메일 주소
            </label>
            <div className="flex gap-2">
              <input
                type="email"
                placeholder="example@email.com"
                className="h-12 flex-1 px-4 rounded-xl border border-zinc-200 dark:border-zinc-700 bg-transparent text-zinc-900 dark:text-zinc-50 placeholder-zinc-400 focus:outline-none focus:ring-2 focus:ring-black dark:focus:ring-white transition-all"
                required
              />
              <button
                type="button"
                className="h-12 px-4 rounded-xl bg-zinc-100 dark:bg-zinc-800 text-zinc-900 dark:text-zinc-50 font-medium text-sm hover:bg-zinc-200 dark:hover:bg-zinc-700 transition-colors whitespace-nowrap"
              >
                인증 요청
              </button>
            </div>
          </div>

          {/* 인증번호 확인란 (이메일 인증 버튼 클릭 후 활성화되는 영역) */}
          <div className="flex flex-col gap-1.5">
            <div className="flex gap-2">
              <input
                type="text"
                placeholder="인증번호 6자리 입력"
                className="h-12 flex-1 px-4 rounded-xl border border-zinc-200 dark:border-zinc-700 bg-transparent text-zinc-900 dark:text-zinc-50 placeholder-zinc-400 focus:outline-none focus:ring-2 focus:ring-black dark:focus:ring-white transition-all"
                required
              />
              <button
                type="button"
                className="h-12 px-4 rounded-xl bg-black text-white dark:bg-white dark:text-black font-medium text-sm hover:bg-zinc-800 dark:hover:bg-zinc-200 transition-colors whitespace-nowrap"
              >
                확인
              </button>
            </div>
          </div>

          {/* 비밀번호 입력란 */}
          <div className="flex flex-col gap-1.5">
            <label className="text-sm font-medium text-zinc-700 dark:text-zinc-300">
              비밀번호
            </label>
            <input
              type="password"
              placeholder="영문, 숫자 포함 8자리 이상"
              className="h-12 w-full px-4 rounded-xl border border-zinc-200 dark:border-zinc-700 bg-transparent text-zinc-900 dark:text-zinc-50 placeholder-zinc-400 focus:outline-none focus:ring-2 focus:ring-black dark:focus:ring-white transition-all"
              required
            />
          </div>

          {/* 전화번호 입력란 */}
          <div className="flex flex-col gap-1.5">
            <label className="text-sm font-medium text-zinc-700 dark:text-zinc-300">
              전화번호
            </label>
            <input
              type="tel"
              placeholder="010-1234-5678"
              className="h-12 w-full px-4 rounded-xl border border-zinc-200 dark:border-zinc-700 bg-transparent text-zinc-900 dark:text-zinc-50 placeholder-zinc-400 focus:outline-none focus:ring-2 focus:ring-black dark:focus:ring-white transition-all"
              required
            />
          </div>

          {/* 가입하기 버튼 */}
          <button
            type="submit"
            className="h-12 w-full mt-4 rounded-full bg-black text-white dark:bg-white dark:text-black font-medium transition-colors hover:bg-zinc-800 dark:hover:bg-zinc-200"
          >
            동의하고 가입하기
          </button>
        </form>

        {/* 하단 로그인 이동 링크 */}
        <div className="flex items-center justify-center gap-2 mt-6 text-sm text-zinc-500 dark:text-zinc-400">
          <span>이미 계정이 있으신가요?</span>
          <Link
            href="/login"
            className="hover:text-zinc-800 dark:hover:text-zinc-200 font-medium text-zinc-950 dark:text-zinc-50 transition-colors underline underline-offset-4"
          >
            로그인하기
          </Link>
        </div>
      </div>
    </div>
  );
}