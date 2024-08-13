import api from './api'

import { SERVER_HOST } from './api';

// 채널 시작
    // 내 채팅 목록 가져오기
    export const channelList = (username) => api.post(`${SERVER_HOST}/channel/list`, {username: username, code: ""})
    // 채팅방 생성
    export const createChannel = (data) => api.post(`${SERVER_HOST}/channel/newChannel`, data);
    // 채팅방 초대
    export const invite = (data) => api.post(`${SERVER_HOST}/channel/invite`, data);
    // 채팅방 나가기
    export const exit = (data) => api.post(`${SERVER_HOST}/channel/exit`, data);
    // 채팅방 삭제
    export const del = (data) => api.post(`${SERVER_HOST}/channel/delete`, data);
    // 채팅방 사용자들 불러오기
    export const attendeeList = (code) => api.post(`${SERVER_HOST}/channel/attendee`, {username: "", code: code})
// 채널 끝


// 특정 채널 메세지 가져오기
    export const getMessageList = (data) => api.post(`${SERVER_HOST}/messages/list`, data);

// 끝


// 직원 정보 가져오기
    export const allEmpt = async () => api.get(`${SERVER_HOST}/employee/all`);
// 끝

// 구독

    // 모든 구독조회하기
    export const getAllSub = async (data) => api.post(`${SERVER_HOST}/channel/sub/all`, data)

    // 구독하기
    export const sub = async (data) => api.post(`${SERVER_HOST}/channel/sub`, data);
    
    // 구독번호 조회하기
    export const getSubId = async (data) => api.post(`${SERVER_HOST}/channel/sub/id`, data);

    // 구독 해제하기
    export const unSub = async (subId) => api.get(`${SERVER_HOST}/channel/unsub/${subId}`);

// end