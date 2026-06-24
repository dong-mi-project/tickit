import Link from "next/link";

export default function Home() {
  return (
    <div className="flex flex-col items-center justify-center min-h-screen bg-zinc-50 dark:bg-black font-sans p-6">
      <main className="flex flex-col items-center gap-6 text-center max-w-md bg-white dark:bg-zinc-900 p-8 rounded-2xl shadow-sm">
        <h1 className="text-3xl font-bold tracking-tight text-zinc-900 dark:text-zinc-50">
          Tickit 서비스에 오신 것을 환영합니다
        </h1>
        <p className="text-zinc-600 dark:text-zinc-400">
          원하는 경로를 선택하여 이동해 보세요.
        </p>
        
        <div className="flex flex-col gap-3 w-full mt-4">
          {/* Next.js에서는 <a> 태그 대신 <Link>를 사용합니다 */}
          <Link 
            href="/login" 
            className="flex h-12 w-full items-center justify-center rounded-full bg-black text-white dark:bg-white dark:text-black font-medium transition-colors hover:bg-zinc-800 dark:hover:bg-zinc-200"
          >
            로그인하러 가기
          </Link>
          <Link 
            href="/signUp" 
            className="flex h-12 w-full items-center justify-center rounded-full border border-zinc-200 dark:border-zinc-700 font-medium transition-colors hover:bg-zinc-50 dark:hover:bg-zinc-800"
          >
            회원가입하기
          </Link>
        </div>
      </main>
    </div>
  );
}