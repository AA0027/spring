import { Button, TextareaAutosize } from '@mui/material';
import './Chat.css';
import React, { useContext, useEffect, useRef, useState } from 'react';
import ChatBox from './ChatBox';
import { LoginContext } from '../../contexts/LoginContextProvider';
const ChatRoom = (prop) => {
    const {code, messages, setMessages} = prop;
    const {userInfo, stompClient} = useContext(LoginContext);
    const [message, setMessage] = useState("");
    const scrollRef = useRef();
    const [file, setFile] = useState(null);
    const fileInput = useRef(null);

    useEffect(()=>{
        if(scrollRef){
            scrollRef.current.scrollTop = scrollRef.current.scrollHeight;
            console.log("===========" + scrollRef.current.scrollTop);
        }
    },[messages]);


    // 파일 업로드 버튼 클릭 시 파일 입력 요소 클릭 이벤트 발생
    const handleButtonClick = (e) => {
    fileInput.current.click();
    };

    // 파일 입력 요소의 값이 변경되면 호출되는 함수
    const handleChange = (e) => {
    // 선택한 파일 정보를 콘솔에 출력
        console.log(e.target.files);
        setFile(e.target.files[0]);
    };

    const handleFileUpload = () => {
        if (!file) {
          alert('파일을 선택하세요.');
          return;
        }
    
        const reader = new FileReader();
        reader.onload = () => {
          const base64File = reader.result.split(',')[1]; // Base64 데이터만 추출
          const payload = {
            fileName: file.name,
            fileType: file.type,
            data: base64File,
          };
    
         stompClient.current.send(`/pub/${code}`,{}, JSON.stringify(payload));
    
          alert('파일이 전송되었습니다.');
        };
    
        reader.readAsDataURL(file);
      };

    const keyDownEvent = (e) => {
        if(e.nativeEvent.isComposing){
            return
        }

        if (e.key === 'Enter' && e.shiftKey) { 
            return;
          } else if (e.key === 'Enter') { 	   
            e.preventDefault();
            const date = new Date();

            let year = date.getFullYear(); // 년도
            let month = date.getMonth() + 1;  // 월
            let day = date.getDate();  // 날짜
            // let day = date.getDay();  

            let hours = ('0' + date.getHours()).slice(-2);
            let minutes = ('0' + date.getMinutes()).slice(-2);
            let seconds = ('0' + date.getSeconds()).slice(-2); 

            let timeString =  year   + "-" + month + "-" + day +" " + hours + ':' + minutes  + ':' + seconds;

            const msg = {
                code: code,
                sender: {
                    name: userInfo.name,
                    username: userInfo.username,
                },
                username: userInfo.username,
                content: message,
                regdate: timeString
            }
            stompClient.current.send(`/pub/${code}`, {}, JSON.stringify(msg));
            setMessages([...messages, msg]);
            e.target.value = "";
          }

    }

    const change = (e) => {
        setMessage(e.target.value);
    }

    return (
        <div className='chat-room'>
            <ChatBox messages={messages} setMessages={setMessages} code={code} scrollRef={scrollRef}/>
            <div className='input-box'>
                <TextareaAutosize  onKeyDown={keyDownEvent}  className="input"  placeholder="write message..." onChange={change} style={{height: "24px"}}/>
                <Button variant="contained" size='small' onClick={handleButtonClick}>
                    파일선택
                </Button>
                <Button variant="contained" size='small' onClick={handleFileUpload}>
                    파일전송
                </Button>
                <input type='file' ref={fileInput} onChange={handleChange} style={{display: "none"}}/>
            </div>
        </div>
    );
};

export default ChatRoom;