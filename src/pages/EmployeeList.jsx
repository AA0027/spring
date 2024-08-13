import { Paper, TableBody, TableCell, TableContainer, TableHead, TablePagination, TableRow, TextField } from '@mui/material';
import React, { useContext, useEffect, useState } from 'react';
import { Table } from 'react-bootstrap';
import Sidebar from '../components/Sidebar/Sidebar';
import * as data from '../apis/data';
import { LoginContext } from '../contexts/LoginContextProvider';



const columns = [
    { id: 'name', label: '이름', minWidth: 100 },
    {
      id: 'position',
      label: '직급',
      minWidth: 100,
      align: 'center',
      format: (value) => value.toLocaleString('en-US'),
    },
    { id: 'dept', label: '부서', minWidth: 100 },
  ];
  

const EmployeeList = () => {
  const { isLogin } = useContext(LoginContext);
  const [empList, setEmpList] = useState([])
  const [page, setPage] = React.useState(0);
  const [rowsPerPage, setRowsPerPage] = React.useState(10);
  const [word, setWord] = useState("");

  useEffect(()=>{
    getEmpList();
  }, []);
  // 사용자 정보 불러오는 메소드
  const getEmpList = async () => {
    const result = await data.allEmpt();
    console.log(result.data);
    setEmpList([...(result.data)]);
  };
  // end

  const onChange = (e) => {
    setWord(e.target.value);
  }

  // 직원 검색
  // const find = async (e) => {
  //   if(e.key === 'Enter'){
  //     const d = e.target.value;
  //     const result = await data.searchEmp(d);
  //     setEmpList([...(result.data)]);
  //     console.log(e.target.value);
  //     e.target.value = "";
  //   }

  // }
  // end
  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (event) => {
    setRowsPerPage(+event.target.value);
    setPage(0);
  };

  return (
    <>
      {
        isLogin &&
        <div className='container'>
          <Sidebar/>
          <div className='content' style={{marginTop: '20px'}}>
              <TextField  onChange={onChange} onKeyDown="" id="outlined-basic" label="검색" size='small' sx={{width: "50%", marginLeft: "auto"
                  , marginRight:"auto", marginBottom: "10px"}}/>
              <Paper sx={{ width: '50%', overflow: 'hidden', marginLeft: "auto", marginRight:"auto" }}>
                  <TableContainer sx={{ maxHeight: 400 }}>
                      <Table stickyHeader aria-label="sticky table">
                          <TableHead>
                              <TableRow>
                              {columns.map((column) => (
                                  <TableCell
                                  key={column.id}
                                  align={column.align}
                                  style={{ minWidth: column.minWidth }}
                                  >
                                  {column.label}
                                  </TableCell>
                              ))}
                              </TableRow>
                          </TableHead>
                          <TableBody>
                              {empList
                              .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                              .map((row) => {
                                  return (
                                  <TableRow hover role="checkbox" tabIndex={-1} key={row.code}>
                                      {columns.map((column) => {
                                      const value = row[column.id];
                                      return (
                                          <TableCell key={column.id} align={column.align}>
                                          {column.format && typeof value === 'number'
                                              ? column.format(value)
                                              : value}
                                          </TableCell>
                                      );
                                      })}
                                  </TableRow>
                                  );
                              })}
                          </TableBody>
                      </Table>
                  </TableContainer>
              <TablePagination
              rowsPerPageOptions={[10, 25, 100]}
              component="div"
              count={empList.length}
              rowsPerPage={rowsPerPage}
              page={page}
              onPageChange={handleChangePage}
              onRowsPerPageChange={handleChangeRowsPerPage}
              />
              </Paper>
          </div>
      </div>}
    </>

  );
};

export default EmployeeList;