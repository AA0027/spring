import React, { useContext } from 'react';
import Sidebar from '../components/Sidebar/Sidebar';
import Body from '../components/Body/Body';
import { LoginContext } from '../contexts/LoginContextProvider';
const Home = () => {
    const { isLogin} = useContext(LoginContext);
  
    return (
        <>
            {
                isLogin &&
                <div className="container">
                    <Sidebar/>
                    <Body />
                </div>
            }
        </>
    );
};

export default Home;