import Cookies from 'js-cookie';
import React, { createContext, useEffect, useRef, useState } from 'react';      
import { useNavigate } from 'react-router-dom';

import * as Swal from '../apis/alert';
import * as auth from '../apis/auth';
import api from '../apis/api';
import { Stomp } from '@stomp/stompjs';

export const LoginContext = createContext();
LoginContext.displayName = 'LoginContextName';


/**
 * 인증 관련하여, 반복해서 수행하는 동작, 혹은 확인해야 하는 값들이 있다
 * 이러한 것들을 Context 에서 사용할 함수등으로 정의해두어 전역적으로 사용할수 있다
 * 
 *   - 현재 라우팅 페이지가 인증이 필요한 페이지 인지 여부 체크
 *   - 현재 페이지가 특정 권한(role)이 필요한(인가) 페이지 인지 여부 체크
 *   - 현재 로그인(인증) 된 유저의 정보 확인
 *   - 인증 이후에는 서버로의 요청에 '매번' JWT 토큰을 헤더에 담아 보내야 하는데 이를 한번의 설정으로 끝내기
 */

const LoginContextProvider = ({children}) => {

  const navigate = useNavigate();
  const stompClient = useRef();
  // Context 에서 다룰 '상태'

  // 로그인 여부
  // const [isLogin, setIsLogin] = useState(false);
  const [isLogin, setIsLogin] = useState(JSON.parse(localStorage.getItem("isLogin")) || false);

  // 유저 정보
  // const [userInfo, setUserInfo] = useState({});
  const [userInfo, setUserInfo] = useState(JSON.parse(localStorage.getItem("userInfo")) || {});

  // 권한 정보
  // const [roles, setRoles] = useState({ isMember: false, isAdmin: false });
  const [roles, setRoles] = useState(JSON.parse(localStorage.getItem("roles")) || { isMember: false, isAdmin: false });

  const subList = useRef([0]);

  /**
   * 💍✅ 로그인 체크
   *
   * 쿠키에서 accessToken 가져와서 확인
   * 💍 ⭕ : 토큰 있으면, /user 로 요청보내서 userInfo state 등록
   * 💍 ❌ : 토큰 없으면, isLogin : false
   *           ➡ 로그인이 필요한 페이지라면, /login 페이지로 navigate()
   *
   * isAuthPage : 인증이 필요한 페이지 여부
   */

  const loginCheck = async (isAuthPage = false) => {
    // 쿠키에 access token (JWT) 가 있는지 꺼내본다.
    const accessToken = Cookies.get('accessToken');
    console.log(`accessToken: ${accessToken}`);

    let response;
    let data;

    // JWT 이 없다면!
    if(!accessToken){
      console.log('쿠키에 JWT(accessToken) 이 없슴');
      logoutSetting();
      return;
    }

    // JWT 가 없는데, 인증이 필요한 페이지라면? -> 로그인 페이지로 모시기.
    if(!accessToken && isAuthPage){
      navigate('/login');
    }

    // JWT 토큰이 있다면?
    console.log('쿠키에 JWT(accessToken) 이 저장되어 있슴');
    api.defaults.headers.common.Authorization = `Bearer ${accessToken}`;

    try{
      response = await auth.userInfo();
    } catch(error){
      console.error(`error: ${error}`);
      if(response !== null)
        console.log(`status: ${response.status}`);
      return;
    }

    // 응답실패시 
    if(!response) return;

    // user 정보 획득 성공
    console.log('JWT (accessToken) 토큰으로 사용자 인증 정보 요청 성공');

    data = response.data;
    console.log(`data: ${data}`);

    // 인증실패
    if(data === 'UNAUTHORIZED' || response.status === 401){
      console.log('JWT(accessToken) 이 만료되었거나 인증에 실패했습니다.');
      return
    }

    // 인증 성공!
    loginSetting(data, accessToken);

  };
  useEffect(()=>{loginCheck()}, []);  // 최초에 loginCheck 실행


  /**
   * 🔐 로그인 요청
   *   username, password 로 [POST] /login 요청
   *
   * 💍 ⭕ : 응답헤더(Authrization)에 토큰 있으면,
   *    1. 쿠키에 { accessToken : jwt } 등록    (Bearer 제외)
   *    2. axios common header  에 등록        (client.defaults.headers.common.Authorization = `Bearer ${jwt}`;)
   *    3. isLogin : true
   *    4. 권한 정보 : 설정
   */

  const login = async (username, password, rememberId) => {
    console.log(`
      로그인 요청
      login(username:${username}, password:${password}, rememberId:${rememberId});
      `);

    // username 저장
    if(rememberId) Cookies.set('rememberId', username);
    else Cookies.remove('rememberId');

    try{
      const response = await auth.login(username, password);
      
      const {data, status, headers} = response;
      const authorization = headers.authorization;
      const accessToken = authorization.replace("Bearer ", "");  // 발급받은 JWT 추출

      console.log(`
        -- login 요청응답 --
          data : ${data}
          status : ${status}
          headers : ${headers}
          jwt : ${accessToken}
        `);

      // ✅ 로그인 성공
      if (status === 200) {
        Cookies.set("accessToken", accessToken);

        // 로그인 체크 ➡ 로그인 세팅
        loginCheck();

        // 성공 팝업후 페이지 이동 -> "/"
        Swal.alert("로그인 성공", "메인화면으로 이동합니다", "success", () => { navigate("/home") })
      }


    } catch(error){
      console.log(`로그인 error: ${error}`);
      Swal.alert('로그인 실패', '아이디 또는 비밀번호가 일치하지 않습니다.', 'error');
    }
  };

    /**
   * 🔓 로그아웃
   *   1. isLogin : false
   *   2. userInfo : null
   *   3. roles : null
   */
  const logout = (force = false) => {
    
    // confirm 없이 강제 로그아웃.
    if(force){
      logoutSetting();  // 로그아웃 세팅

      // 페이이 지동 -> "/"
      navigate("/");
      return;
    }

    Swal.confirm("로그아웃 하시겠습니까", "로그아웃을 진행합니다", "warning", 
      (result) => {
        if(result.isConfirmed){
          logoutSetting();  // 로그아웃 세팅
          navigate("/");
        } 
      }
    );

  };

  /**
   * 로그인 세팅
   *    accessToken 을 header (Authorization) 에 저장.  
   *    이후 요청시 매번 haeder 에 수동으로 token 을 첨부하지 않아도 되도록 
   *    axios 디폴트 헤더에 설정 (Authorization)
   *  
   *    isLogin <= true
   *    userInfo 세팅
   *    roles 세팅
   */
  const loginSetting = (userData, accessToken) => {
    const {id, username, role, name} = userData;
    const headers = {
      'Authorization': `Bearer ${accessToken}`
    };
    const socket = new WebSocket("ws://localhost:8080/channel");
    stompClient.current = Stomp.over(socket);
    stompClient.current.connect(headers,() => {
    console.log("연결 완료");
    stompClient.current.subscribe(`/sub/${username}`);
    });
   
    console.log(`
    loginSetting() 
       id : ${id}
       username : ${username}
       role : ${role}
       name: ${name}
    `);

    // 💍 ➡ 🍪
    // JWT 토큰 을 header 저장
    api.defaults.headers.common.Authorization = `Bearer ${accessToken}`;

    // 로그인 여부
    setIsLogin(true);

    // 유저 정보 세팅
    const updatedUserInfo = {id, username, role, name}
    setUserInfo(updatedUserInfo);

    // 권한정보 세팅
    // role => 'ROLE_MEMBER', 'ROLE_MEMBER,ROLE_ADMIN'  <- 하나의 문자열로 되어 있는 형태
    const updatedRoles = {isMember: false, isAdmin: false};
    role.split(',').forEach((role) => {
      role === 'ROLE_MEMBER' && (updatedRoles.isMember = true);
      role === 'ROLE_ADMIN' && (updatedRoles.isAdmin = true);
    });
    setRoles(updatedRoles);

    // 새로 고침]시 Context 로 리로딩 된다.  이때 localStorage 에 담긴 인증된 값으로 인증 '상태' 정보로 초기화되도록 하자
    // localStrogate 사용 (쿠키를 사용해도 되긴 하다)
    localStorage.setItem("isLogin", "true");
    localStorage.setItem("userInfo", JSON.stringify(updatedUserInfo));
    localStorage.setItem("roles", JSON.stringify(updatedRoles));

  };

    /**
   * 로그아웃 세팅
   *   isLogin <= false
   *   userInfo <= null
   *   roles <= null
   *   쿠키 지우기
   *   axios 디폴트 헤더 설정 지우기 (Authorization)
   */
  const logoutSetting = () => {
    // 상태 비우기
    setIsLogin(false);
    setUserInfo(null);
    setRoles(null);
    // 쿠키 지우기
    Cookies.remove('accessToken');
    // axios 의 default  header 도 삭제
    api.defaults.headers.common.Authorization = undefined;

    // 새로고침]
    // local storage 지우기
    localStorage.removeItem("isLogin");
    localStorage.removeItem("userInfo");
    localStorage.removeItem("roles");
  };

  return (
    <>
    <LoginContext.Provider value={ { isLogin, userInfo, roles, stompClient, subList, loginCheck, login, logout }}>
        {children}
    </LoginContext.Provider>
    </>
  );
};

export default LoginContextProvider;