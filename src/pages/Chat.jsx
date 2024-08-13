import React, { useContext, useEffect, useRef, useState } from 'react';
import Sidebar from '../components/Sidebar/Sidebar';
import ChatRoom from '../components/Chat/ChatRoom';
import { LoginContext } from '../contexts/LoginContextProvider';
import { useLocation } from 'react-router-dom';
import { Accordion, AccordionDetails, AccordionSummary, Button } from '@mui/material';
import Drawer from '@mui/material/Drawer';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import * as data from '../apis/data'
import Emp from '../components/Chat/Emp';
const Chat = () => {
    const [messages, setMessages] = useState([]);
    const { isLogin} = useContext(LoginContext);
    const location = useLocation();
    const { name, code} = location.state;
    const [open, setOpen] = useState(false);
    const [attendee, setAttendee] = useState([]);
    const [list, setList] = useState([0]);
    const [addEmp, setAddEmp] = useState(true);
    useEffect(()=>{

        getAttendees();
    }, [addEmp]);

   const getAttendees = async () => {
        const response = await data.attendeeList(code);
        setAttendee(response.data);
   }

    const toggleDrawer = (newOpen) => () => {
        
        setOpen(newOpen);
      };
    

      

      
   
    return (
        <>
        {
            isLogin &&
            <div className="container">
                <Sidebar/>
            
                <div className=' content jua-regular'>
                    <div className='head' style={{width: "70%"}}>
                        {name} 
                        <Button variant="contained" size="small" onClick={toggleDrawer(true)}>
                            초대
                        </Button>
                    </div>
                    <div>
                        <ChatRoom messages={messages} setMessages={setMessages} code={code}/>
                    </div>
                    <div className='chat-data'>
                        <Accordion>
                            <AccordionSummary
                            expandIcon={<ExpandMoreIcon />}
                            aria-controls="panel1-content"
                            id="panel1-header"
                            >
                            사용자
                            </AccordionSummary>
                            <AccordionDetails sx={{maxHeight: "200px", overflow: "scroll", overflowX: "hidden"}}>
                            {
                                attendee && 
                                attendee.map((e) => (<div>{e.name}</div>))
                            }
                            </AccordionDetails>
                        </Accordion>
                        <div>파일</div>

                    </div>
                </div>
                <Drawer open={open} onClose={toggleDrawer(false)} anchor='right'>
                    <Emp code={code} list={list} setList={setList} setOpen={setOpen} attendee={attendee} addEmp={addEmp} setAddEmp={setAddEmp}/>
                </Drawer>
            
            </div>}
        </>
    );
};

export default Chat;

