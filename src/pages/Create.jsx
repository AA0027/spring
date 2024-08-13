import React from 'react';
import Sidebar from '../components/Sidebar/Sidebar';
import { TextField } from '@mui/material';

const Create = () => {
    return (
        <div className="container">
            <Sidebar/>
            <div className='create-box'>
            <div>채널 생성</div>
            <TextField id="outlined-basic" label="검색" size='small' />
            <TextField id="outlined-basic" label="검색" size='small' />
            <TextField id="outlined-basic" label="검색" size='small' />
            </div>
            
        </div>
    );
};

export default Create;