"use client";

import Link from "next/link";
import { findUserId } from "../../api/user";
import { useRouter } from "next/navigation";
import { useState } from "react";

function FindIdPage() {
  const router = useRouter();
  const [error, setError] = useState("");
  const [message, setMessage] = useState("");

  // 아이디 찾기 폼으로 보내기 위해서 폼으로 생성
  const [form, setForm] = useState({
    name: "",
    phone: "",
  });

  const handleFindIdSubmit = async (e) => {
    e.preventDefault();

    if (!validateForm()) return;

    try {
      await findUserId(form.name, form.phone);
      setMessage("아이디를 찾았습니다.");
    } catch (err) {
      console.log("에러 응답:", err.response?.data);
      setError("아이디 찾기에 실패했습니다.");
    }
  };

  const validateForm = () => {
    if (!form.name) {
      setError("이름을 입력해주세요.");
      return false;
    }
    if (!form.email) {
      setError("이메일을 입력해주세요.");
      return false;
    }
    return true;
  };

  return (
    <div className="flex flex-col items-center justify-center min-h-screen bg-zinc-50 dark:bg-black font-sans p-4">
      <div className="w-full max-w-md bg-white dark:bg-zinc-900 p-8 rounded-2xl shadow-sm border border-zinc-100 dark:border-zinc-800">
        <h1 className="text-2xl font-bold text-center text-zinc-900 dark:text-zinc-50 mb-8">
          아이디 찾기
        </h1>

        <form className="flex flex-col gap-5" onSubmit={handleFindIdSubmit}>
          {/* 이름 입력란 */}
          <div className="flex flex-col gap-1.5">
            <label className="text-sm font-medium text-zinc-700 dark:text-zinc-300">
              이름
            </label>
            <input
              type="text"
              value={form.name}
              onChange={(e) => setForm({ ...form, name: e.target.value })}
              placeholder="이름을 입력하세요"
              className="h-12 w-full px-4 rounded-xl border border-zinc-200 dark:border-zinc-700 bg-transparent text-zinc-900 dark:text-zinc-50 placeholder-zinc-400 focus:outline-none focus:ring-2 focus:ring-black dark:focus:ring-white transition-all"
              required
            />
          </div>

          {/* 휴대전화 번호 입력란 */}
          <div className="flex flex-col gap-1.5">
            <label className="text-sm font-medium text-zinc-700 dark:text-zinc-300">
              휴대전화 번호
            </label>
            <input
              type="text"
              value={form.phone}
              onChange={(e) => setForm({ ...form, phone: e.target.value })}
              placeholder="휴대전화 번호를 입력하세요"
              className="h-12 w-full px-4 rounded-xl border border-zinc-200 dark:border-zinc-700 bg-transparent text-zinc-900 dark:text-zinc-50 placeholder-zinc-400 focus:outline-none focus:ring-2 focus:ring-black dark:focus:ring-white transition-all"
              required
            />
          </div>

          {/* 아이디 찾기 버튼 */}
          <button
            type="submit"
            className="h-12 w-full mt-2 rounded-full bg-black text-white dark:bg-white dark:text-black font-medium transition-colors hover:bg-zinc-800 dark:hover:bg-zinc-200"
          >
            아이디 찾기
          </button>
        </form>

        {/* 하단 링크 영역 (뒤로 가기 | 비밀번호 찾기) */}
        <div className="flex items-center justify-center gap-4 mt-6 text-sm text-zinc-500 dark:text-zinc-400">
          <Link
            href="/findPassword"
            className="hover:text-zinc-800 dark:hover:text-zinc-200 transition-colors"
          >
            비밀번호 찾기
          </Link>
          <span className="w-px h-3 bg-zinc-200 dark:bg-zinc-700" />
          <Link
            href="/signUp"
            className="hover:text-zinc-800 dark:hover:text-zinc-200 font-medium text-zinc-950 dark:text-zinc-50 transition-colors"
          >
            회원가입하기
          </Link>
        </div>
      </div>
    </div>
  );
}

export default FindIdPage;
