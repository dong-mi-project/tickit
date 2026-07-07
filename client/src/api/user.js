import axios from "axios";

const BASE_URL = process.env.NEXT_PUBLIC_API_BASE_URL;

// 회원가입 API 호출
export const userSignup = (formData) => {
  return axios.post(`${BASE_URL}/user/signup`, formData);
};

// 로그인 API 호출
export const userLogin = (formData) => {
  return axios.post(`${BASE_URL}/user/login`, formData);
};

// 이메일 인증 코드 보내기
export const sendEmailCode = (email = "") => {
  return axios.get(`${BASE_URL}/user/send`, {
    params: { email },
  });
};

// 이메일 인증 코드 검증
export const verifyEmailCode = (email = "", inputCode = "") => {
  return axios.get(`${BASE_URL}/user/verify`, {
    params: { email, inputCode },
  });
};
