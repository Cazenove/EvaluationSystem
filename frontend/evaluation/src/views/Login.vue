<template>
	<div id="login">
		<div class="container">
			<h1>{{welcomemsg}}</h1>
			<div class="form-group">
				<label for="username">用户名</label>
				<input type="text" class="form-control" id="username" v-model="username" />
			</div>
			<div class="form-group">
				<label for="password">密码</label>
				<input type="password" class="form-control" id="password" v-model="password" />
			</div>
			<button class="btn btn-primary" @click="login()">登录</button>
		</div>
	</div>
</template>

<script>
	import axios from 'axios'
	export default {
		data() {
			return {
				data: {
					"error_code": 0,
					"data": [{
							"id": '1',
							"state":1,
							"username": "admin",
							"password": "123456",
							"token": "@date(T)",
						},
						{
							"id": '2',
							"state":2,
							"username": "root",
							"password": "root",
							"token": "@date(T)",
						}
					]
				},
				welcomemsg: "《软件工程实践》互动评价系统",
				username: "",
				password: ""
			}
		},
		methods: {
			// login() {
			// 	var username = this.$data.userName;
			// 	var password = this.$data.passWord;
			// 	if(username == "1" && password == "1") {
			// 		this.$router.replace('/home');
			// 		this.$store.state.isLogin=true;
			// 		this.$store.state.loginState = 1;
			// 	}
			// 	else if(username == "2" && password == "2") {
			// 		this.$router.replace('/home');
			// 		this.$store.state.isLogin=true;
			// 		this.$store.state.loginState = 2;
			// 	}
			// 	else
			// 	{
			// 		alert("账号或密码错误");
			// 	}
			// }
			login() {
				const self = this;
				axios.get('').then(response => {
					var res = self.data.data,
						len = res.length,
						userNameArr = [],
						passWordArr = [],
						ses = window.sessionStorage;
					// 拿到所有的username
					for (var i = 0; i < len; i++) {
						userNameArr.push(res[i].username);
						passWordArr.push(res[i].password);
					}
					console.log(userNameArr, passWordArr);
					if (userNameArr.indexOf(this.username) === -1) {
						alert('账号不存在！');
					} else {
						var index = userNameArr.indexOf(this.username);
						if (passWordArr[index] === this.password) {
							// 把token放在sessionStorage中
							ses.setItem('data', res[index].token);
							self.$store.state.loginState = res[index].state;
							//跳转到首页
							this.$router.push('/home');
							// console.log(this.$router);
						} else {
							alert('密码错误！')
						}
					}
				})
				// .catch(err => {
				// 	console.log('连接数据库失败！')
				// })
			}
		}
	}
</script>

<style>
</style>
