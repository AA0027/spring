import api from './api'

import { SERVER_HOST } from './api';

// 요청하는 동작들에 대해서 함수로 만들어 export 해둔다

// 로그인 요청
export const login = (username, password) => api.post(`${SERVER_HOST}/login`, {username, password}, {headers: {"Content-Type": "application/x-www-form-urlencoded"}})

// user 정보 요청
export const userInfo = () => api.get(`${SERVER_HOST}/user`);

// auth 정보 요청
export const authInfo = () => api.get(`${SERVER_HOST}/auth`);

// 회원가입 요청
export const join = (data) => api.post(`${SERVER_HOST}/user/join`, data);  // JSON 으로 전달

// 모든 사용자 정보 요청
export const getUsers = () => api.get(`${SERVER_HOST}/users`);