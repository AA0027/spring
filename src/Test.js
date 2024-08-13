


// const date = new Date();

// var hours = ('0' + date.getHours()).slice(-2);
// var minutes = ('0' + date.getMinutes()).slice(-2);
// var seconds = ('0' + date.getSeconds()).slice(-2); 

// var timeString = hours + ':' + minutes  + ':' + seconds;

// console.log(timeString)
const date = new Date();

let year = date.getFullYear(); // 년도
let month = date.getMonth() + 1;  // 월
let day = date.getDate();  // 날짜

console.log(year);
console.log(month);
console.log(day);
const x = "aaa bbbb";
const y = x.slice(" ");
console.log(y);