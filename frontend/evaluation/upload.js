'use strict'
// 引入scp2
var client = require('scp2');

client.scp('./dist/', {    // 本地打包文件的位置
  "host": '106.12.47.24', // 服务器的IP地址
  "port": '22',            // 服务器端口， 一般为 22
  "username": 'root',       // 用户名
  "password": 'qwQW12!@',     // 密码
  "path": '/etc/nginx/dist'            // 项目部署的服务器目标位置
}, err =>{
  if (!err) {
    console.log("项目发布完毕!")
  } else {
    console.log("err", err)
  }
})