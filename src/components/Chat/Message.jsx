import { Avatar } from '@mui/material';
import { deepPurple } from '@mui/material/colors';
import React, { useEffect, useRef } from 'react';
import './Message.css';
const Message = (prop) => {
    const { m_name, my_name, content, regdate } = prop;

    if(m_name === my_name){
        return (
            <div className='message my' >
                <div className='date'>{(regdate.split(" "))[1]}</div>
                <div className='msg-bowl'>{content}</div> 
                <Avatar sx={{ bgcolor: deepPurple[500], width: "40px"
                    , height: "40px", }}>
                        <div className='name'>{m_name}</div>
                </Avatar>
           
            </div>
         );  
    }
    else {
        return (
            <div className='message you' >
                <div className='avartar'>
                    <Avatar sx={{ bgcolor: deepPurple[500], width: "40px"
                        , height: "40px"}} >
                            <div className='name'>{m_name}</div>
                    </Avatar>
                </div>
                <div className='msg-bowl'>{content}</div> 
                <div className='date'>
                    {
                        (regdate.split(" "))[1]
                    }
                </div>
            </div>
         );
    }
    
};

export default Message;
