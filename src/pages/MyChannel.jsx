import React, { useContext, useEffect, useState } from 'react';
import Sidebar from '../components/Sidebar/Sidebar';
import Channel from '../components/Channel/Channel';
import '../components/Channel/Channel.css';
import * as data from '../apis/data';
import { LoginContext } from '../contexts/LoginContextProvider';
import { useLocation } from 'react-router-dom';
const MyChannel = () => {
    const [ channelList, setChannelList ] = useState([]);
    const { userInfo, isLogin } = useContext(LoginContext);
    useEffect(  ()=>{
        getChannelList();
    }, []);

    const getChannelList = async () => {
        const response = await data.channelList(userInfo.username);
        setChannelList([...(response.data)]);
    }
    return (
        <>
            {
                isLogin &&
                <div className="container">
                    <Sidebar/>
                
                    <div className=' content jua-regular'>
                        <div className='head'>My Channel</div>
                        <div className='channel-list'>
                            { channelList.map(e => (<Channel key={e.id} name={e.name} code={e.code} channelList={channelList}/>)) }
                        </div>
                    </div>
                
                </div>
            }
        </>
        
    );
};

export default MyChannel;