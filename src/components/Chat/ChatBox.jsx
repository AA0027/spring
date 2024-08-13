import React, { useContext, useEffect, useRef, useState } from 'react';
import Message from './Message';
import EmailOutlinedIcon from '@mui/icons-material/EmailOutlined';
import { LoginContext } from '../../contexts/LoginContextProvider';
import * as data from '../../apis/data';
const ChatBox = (prop) => {
    const { messages, setMessages, code, scrollRef }  = prop;
    const { userInfo } = useContext(LoginContext);
    // const [groupedMessages, setGroupedMessages] = useState([]);
    // const days = useRef([0]);
    let groupedMessages;
    // 메시지 가져옴
    useEffect(()=>{
       getMsg();
        console.log();

        
    },[]);

     // 메시지 가져오는 메소드
     const getMsg = async () => {
        const info = {
            code: code,
            username: userInfo.username,
        };
        const response = await data.getMessageList(info);
        if(response.data === undefined)
            return
        
        setMessages([...(response.data)]);
    }

    const groupMessagesByDate = (messages) => {
        return messages.reduce((groups, message) => {
            const date = message.regdate.split(" ")[0];
          if (!groups[date]) {
            groups[date] = [];
          }
          groups[date].push(message);
          return groups;
        }, {});
    };

    if(!messages){
        return (
            
        <div className='chat-box' ref={scrollRef} style={{display: "flex", alignItems: "center", justifyContent: "center"}}>
            <div className='no-msg'>
                <EmailOutlinedIcon sx={{width: "100px", height: "100px", color: "primary"}} />
                <span>No Messages</span>
            </div>
        </div>  
        );
    }
    else {

        return (
            
            <div className='chat-box' ref={scrollRef}>
                
                {
                    (groupedMessages = groupMessagesByDate(messages)) && Object.keys(groupedMessages).map((date) => (
                    <div key={date} className='date-box'>
                        <div className='date-header'>
                            {date}
                        </div>
                        {groupedMessages[date].map((m) => (
                           m.sender && <Message m_name={m.sender.name} my_name={userInfo.name} content={m.content} regdate={m.regdate} />
                        ))}
                    </div>
                ))}
            </div>  
        );
    }
       
};

export default ChatBox;

