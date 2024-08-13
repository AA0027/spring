import { Button } from '@mui/material';
import React, { useContext } from 'react';
import { useNavigate } from 'react-router-dom';
import { LoginContext } from '../../contexts/LoginContextProvider';
import * as data from '../../apis/data'
const Channel = (prop) => {
    const { name, code, channelList } = prop;
    const {stompClient, userInfo} = useContext(LoginContext);
    const navigate = useNavigate();
    return (
        <div className='channel'>
            <div className = 'chatting-index'>
                {`${name} 채팅방`}
            </div>
            <div className='channel-btn'>
                <Button variant="contained" size="small" 
                     onClick={async () => {
                            const x = {
                                username: userInfo.username,
                                code: "",
                                subId: ""
                            }
                            const codeList = channelList.map((a) => (a.code));
                            if(!codeList.includes(code)){
                                const {id} = stompClient.current.subscribe(`/sub/${code}`);
                                const d = {
                                    username: userInfo.username,
                                    code: code,
                                    subId: id,
                                };
    
                                // 구독 목록에 등록하기
                                data.sub(d);
                                
                                console.log("구독 결과 : " + id);
                            }
                            navigate("/chat", {state: {name: name, code: code}});
                        }}>
                        입장
                </Button>
            </div>
        </div>
    );
};

export default Channel;