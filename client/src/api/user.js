import axios from "axios";

const BASE_URL = process.env.NEXT_PUBLIC_API_BASE_URL;
const OWNER_DECODING = process.env.NEXT_PUBLIC_OWNER_DECODING;
const DATABASE_URL = process.env.NEXT_PUBLIC_DATABASE_URL;

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

// 이메일 인증 코드
export const verifyEmailCode = (email = "", inputCode = "") => {
  return axios.get(`${BASE_URL}/user/verify`, {
    params: { email, inputCode },
  });
};

// 사업자를 검증하는 API
export const verifyOwner = (b_no = "") => {
  return axios.post(
    `${DATABASE_URL}/status`,
    { b_no: [b_no] }, // body
    {
      params: {
        serviceKey: OWNER_DECODING,
      },
    },
  );
};

// 사업자 검증
export const isValidOwner = (responseData) => {
  const result = responseData?.data?.[0];
  return !!result && result.b_stt_cd !== "";
};

// 유저 아이디 찾기
export const findUserId = (name, email) => {
  return axios.get(`${BASE_URL}/user/findId`, {
    params: { name, email },
  });
};
