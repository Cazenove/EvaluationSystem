<!-- 登录注册页面 -->
<template>
	<div id="login">
		<div class="container">
			<h1>{{welcomemsg}}</h1>
			<div class="form-group">
				<label for="username" class="col-form-label">用户名</label>
				<input type="text" class="form-control" id="username" v-model="username" />
			</div>
			<div class="form-group">
				<label for="password" class="col-form-label">密码</label>
				<input type="password" class="form-control" id="password" v-model="password" />
			</div>
			<RegisterModal />
			<br />
			<button class="btn btn-primary" @click="login()">登录</button>
		</div>
	</div>
</template>

<script>
	import axios from 'axios'
	import RegisterModal from '../components/RegisterModal.vue'
	export default {
		data() {
			return {
				data: {
					"error_code": 0,
					"data": [{
							"id": '1',
							"state": 1,
							"username": "admin",
							"password": "123456",
							"token": "@date(T)",
						},
						{
							"id": '2',
							"state": 2,
							"username": "root",
							"password": "root",
							"token": "@date(T)",
						}
					]
				},
				welcomemsg: "《软件工程实践》互动评价系统",
				username: "",
				password: "",
			}
		},
		components: {//注册组件
			RegisterModal
		},
		methods: {
			login() {
				const self = this;
				axios.get('').then(response => {
					var res = self.data.data,
						len = res.length,
						userNameArr = [],
						passWordArr = [],
						ses = window.sessionStorage;
					// 以下为模拟登陆
					for (var i = 0; i < len; i++) {
						userNameArr.push(res[i].username);
						passWordArr.push(res[i].password);
					}
					if (userNameArr.indexOf(this.username) === -1) {
						alert('账号不存在！');
					} else {
						var index = userNameArr.indexOf(this.username);
						if (passWordArr[index] === this.password) {
							// 把token放在sessionStorage中
							ses.setItem('data', res[index].token);
							self.$store.state.loginState = res[index].state;
							if (self.$store.state.loginState == 1) {
								//跳转到前台首页
								this.$router.push('/home');
							}
							if (self.$store.state.loginState == 2) {
								//跳转到后台首页
								this.$router.push('/usermanagement');
							}
							
							// this.$router.push('/usermanagement');
							// console.log(this.$router);
						} else {
							alert('密码错误！')
						}
					}
				})
				// .catch(err => {
				// 	console.log('连接数据库失败！')
				// })
			},
		}
	}
</script>

<style>
</style>
