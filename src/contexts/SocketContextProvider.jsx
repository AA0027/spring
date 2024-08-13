import { Stomp } from '@stomp/stompjs';
import React, { createContext, useRef } from 'react';

export const SocketContext = createContext();
SocketContext.displayName = 'SocketContextName';

const SocketContextProvider = ({children}) => {

    const stompClient = useRef(null);

    const socket = new WebSocket("ws://localhost:8080/channel");
    stompClient.current = Stomp.over(socket);
    return (
        <SocketContext.Provider value={{stompClient}}>
            {children}
        </SocketContext.Provider>
    );
};

export default SocketContextProvider;