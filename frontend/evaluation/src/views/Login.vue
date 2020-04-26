<template>
	<div id="login">
		<div class="container">
			<h1>{{welcomemsg}}</h1>
			<div class="form-group">
				<label for="username">用户名</label>
				<input type="text" class="form-control" id="username" v-model="userName" />
			</div>
			<div class="form-group">
				<label for="password">密码</label>
				<input type="password" class="form-control" id="password" v-model="passWord" />
			</div>
			<button class="btn btn-primary" @click="login()">登录</button>
		</div>
	</div>
</template>

<script>
	// import axios from 'axios'
	export default {
		data() {
			return {
				data: {
					"error_code": 0,
					"data": [{
							"id": '1',
							"usertitle": "管理员",
							"username": "admin",
							"password": "123456",
							"token": "@date(T)",
						},
						{
							"id": '2',
							"usertitle": "超级管理员",
							"username": "root",
							"password": "root",
							"token": "@date(T)",
						}
					]
				},
				welcomemsg: "《软件工程实践》互动评价系统",
				userName: "",
				passWord: ""
			}
		},
		beforeCreate: function() {
			this.$store.state.isInLoginPage = true;
		},
		methods: {
			login() {
				var username = this.$data.userName;
				var password = this.$data.passWord;
				if(username == "1" && password == "1") {
					this.$router.replace('/home');
					this.$store.state.isInLoginPage=false;
					this.$store.state.loginState = 1;

				}
				else if(username == "2" && password == "2") {
					this.$router.replace('/home');
					this.$store.state.isInLoginPage=false;
					this.$store.state.loginState = 2;
				}
				else
				{
					alert("账号或密码错误");
				}
			}
			// login() {
			// 	const self = this;
			// 	axios.get('').then(response => {
			// 		var res = this.data.data,
			// 			ses = window.sessionStorage;
			// 			if (this.userName === "1" && this.passWord === "1") {
			// 				this.$router.replace('/home');
			// 				this.$store.state.isInLoginPage = false;
			// 				this.$store.state.loginState = 1;
			// 			} else if(this.userName === "2" && this.passWord === "2") {
			// 				this.$router.replace('/home');
			// 				this.$store.state.isInLoginPage = false;
			// 				this.$store.state.loginState = 2;
			// 			} else {
			// 				alert('密码错误！')
			// 			}
			// 	}).catch(err => {
			// 		console.log('连接数据库失败！')
			// 	})
			// }
		}
	}
</script>

<style>
</style>
