import { Box, Button, Checkbox, FormControl, InputLabel, List, ListItem, ListItemText, MenuItem, Select, TextField } from '@mui/material';
import React, { useEffect, useRef, useState } from 'react';
import * as data from '../../apis/data'
const Emp = (prop) => {
    const {code, list, setList, setOpen, attendee, addEmp, setAddEmp} = prop;
    const [empList, setEmpList] = useState([0]); 
    const origin = useRef([0]);
    const [dept, setDept] = useState("");
    const [search, setSearch] = useState("");
    const invited = useRef([0]);
    

    useEffect(() => {
        if(!attendee)
            invited.current = attendee.map((x) => x.username);
        getEmp();
    }, []);

    const getEmp = async () => {
        const response = await data.allEmpt();
        origin.current = response.data;
        setEmpList(response.data);
    }

    

    const selected = (e) => {
        setDept(e.target.value);
        const r = (origin.current).filter(x => x.dept === e.target.value);
        setEmpList(r);
    }

    const inviteList = async (checked, username) => {
        if(checked){
            setList([...list, username]);
            console.log(list);
        }
        else{
            const r = list.filter((x) => x !== username);
            setList(r);
        }
    }

    const invite = async () => {

        const att = list.filter((x) => (invited.current).includes(x.username));

        if(!att){
            att.forEach((e) => {
                console.log(e.name + "은 이미 채팅방안에 있습니다. ");
            });
        }

        const param = {
            code: code,
            usernames: list
        }
        const response = await data.invite(param);

        if(response.status === 200){
            console.log("사용자 초대완료");
            setList([]);
            setOpen(false);
            setAddEmp(!addEmp);
        }
      }

      const searchEmp = (e) => {
        if (e.key === 'Enter') { 	   
            e.preventDefault();
            const r = (origin.current).filter((x) => (x.name).includes(search));
            setEmpList(r);
            setSearch("");
        }
      };


      const change = (e) => {
        setSearch(e.target.value);
        if(search === "")
            getEmp();
      }

      const checkAll = (e) => {
        if(e.target.checked){

            setList(empList.map((x) => x.username));
            console.log("checked : " + e.target.checked);
            console.log(list);
        }else {
            setList([]);
            console.log(list);
        }
      }



    return (
        <>
            <div className='menu-header'>

                <TextField  onChange={change} onKeyDown={searchEmp} id="outlined-basic" label="검색" value={search} size='small' sx={{width: "60%"}}/>

                <FormControl sx={{ m: 1, minWidth: "30%"}} size="small">

                    <InputLabel id="demo-select-small-label">부서</InputLabel>
                    <Select
                    labelId="demo-select-small-label"
                    id="demo-select-small"
                    value={dept}
                    onChange={selected}
                    >
                    {/* <MenuItem value="부서별">
                    <em>None</em>
                    </MenuItem> */}
                    <MenuItem value="QA">QA팀</MenuItem>
                    <MenuItem value="Tech01">기술1팀</MenuItem>
                    <MenuItem value="Tech02">기술2팀</MenuItem>
                    <MenuItem value="Tech03">기술3팀</MenuItem>
                    </Select>
                </FormControl>
            </div>

            <div className='select-all'>
                <span>전체 선택</span>
                <Checkbox onClick={checkAll}></Checkbox>
            </div>
            <Box sx={{ width: "100%", height: "80%", overflow: "scroll" , overflowX: "hidden"}} >
                <List sx={{ width: '80%', maxWidth: 360, bgcolor: 'background.paper', padding: "0 5%" }}>
                {(empList) && (empList).map((x) => (
                    <ListItem
                        className='emp-item'
                        key={x.id}
                        disableGutters
                        secondaryAction={
                            <Checkbox className='chk' value={x.username} onChange={(e) => inviteList(e.target.checked, x.username)}
                                checked = { list.includes(x.username) ? true : false }></Checkbox>
                        }
                        >
                        <ListItemText className='emp-item' sx={{textAlign: "left", fontSize: "5px", '& .MuiListItemText-primary': {
                            fontSize: '13px'
                        }}} primary={`${x.name}(${x.dept})`} />
                    </ListItem>
                ))}
                </List>
            </Box>
            <Button variant="contained" size="small" onClick={invite} sx={{borderRadius: "0", flexGrow: 1}}>
                초대
            </Button>
        </>
    );
};

export default Emp;