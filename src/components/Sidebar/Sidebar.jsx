import { Avatar, Badge, Box, Button, Modal, TextField, Typography } from '@mui/material';
import React, { useContext, useState } from 'react';
import MailIcon from '@mui/icons-material/Mail';
import './Sidebar.css';
import { useNavigate } from 'react-router-dom';
import { LoginContext } from '../../contexts/LoginContextProvider';
import * as data from '../../apis/data';

const style = {
    position: 'absolute',
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    width: 200,
    bgcolor: 'background.paper',
    border: '2px solid #000',
    boxShadow: 24,
    p: 4,
 };

const Sidebar = () => {
    const { userInfo, logout } = useContext(LoginContext);
    const [newChannel , setNewChannel]= useState("");
    const [open, setOpen] = useState(false);
    const handleOpen = () => setOpen(true);
    const handleClose = () => setOpen(false);

    const navigate = useNavigate();

    const myChannel = () => {
        navigate("/myChannel");
    }

    const createChannel = async () => {
        const info = {
            name: newChannel,
            myId: userInfo.username,
        };

        const result = await data.createChannel(info);
        // 채널 정보 변경 시켜주기 
        const {name, code} = result.data;
        navigate("/chat", {state:{name, code}});
    }

    const change = (e) => {
        setNewChannel(e.target.value);
    }

    return (
        <div className='side-bar'>
            <Avatar src="/broken-image.jpg" 
                sx={{width: "100px", height: "100px", margin: "20px"}}/>

            <div className='user-info merriweather-regular'> 
                조창성(사장)
            </div>

            <Button variant="contained" size="small" onClick={logout}>
                    로그아웃
            </Button>
            <div className='menu jua-regular' >
                <div onClick={myChannel} className='menu-item'>My 채널</div>
                <div  className='menu-item' onClick={handleOpen}>채널 생성</div>
            </div>
            <Modal
                open={open}
                onClose={handleClose}
                aria-labelledby="modal-modal-title"
                aria-describedby="modal-modal-description"
                >
                <Box sx={style}>
                    <div className='modal'>
                        <Typography id="modal-modal-title" variant="h6" component="h2">
                        Channel 생성
                        </Typography>
                        <TextField id="outlined-basic" label="채널이름" size='small' value={newChannel} onChange={change}/>
                        <Button variant="contained" size="small" onClick={createChannel} >
                            생성
                        </Button>

                    </div>
                </Box>
            </Modal>
        </div>
    );
};

export default Sidebar;