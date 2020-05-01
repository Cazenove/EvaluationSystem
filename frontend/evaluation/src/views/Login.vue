<!-- 登录注册页面 -->
<template>
	<div id="login">
		<div class="jumbotron jumbotron-fluid">
			<div class="container">
			<h1 class="display-4">{{welcomemsg}}</h1>
			<p class="lead">登录或注册来使用{{welcomemsg}}</p>
			</div>
		</div>
		<div class="container">
			<div class="form-row">
				<div class="form-group col-md-6">
					<label for="inputEmail4">用户名</label>
					<input type="email" class="form-control" placeholder="Username" aria-label="Username"   id="username" v-model="username"/>
				</div>
				<div class="form-group col-md-6">
					<label for="inputPassword4">密码</label>
					<input type="password" class="form-control" placeholder="Password" aria-label="Password" id="password" v-model="password"/>
				</div>
			</div>
			<!-- <div class="input-group mb-3">
				<div class="input-group-prepend">
					<span class="input-group-text" id="basic-addon1">用户名</span>
				</div>
				<input type="text" class="form-control" placeholder="Username" aria-label="Username" aria-describedby="basic-addon1"  id="username" v-model="username"/>
			</div> -->
			<!-- <div class="input-group mb-3">
				<div class="input-group-prepend">
					<span class="input-group-text" id="basic-addon1">密码&nbsp;</span>
				</div>
				<input type="password" class="form-control" placeholder="Password" aria-label="Password" aria-describedby="basic-addon1"  id="password" v-model="password"/>
			</div> -->
			<!-- <div class="form-group row">
				<label for="username" class="col-md-1 col-form-label offset-md-3">用户名</label>
				<div class="col-md-6">
				<input type="text" class="form-control" id="username" v-model="username" />
				</div>
			</div> -->
			<!-- <div class="form-group row">
				<label for="password" class="col-md-1 col-form-label offset-md-3">密码</label>
				<div class="col-md-6">
				<input type="password" class="form-control" id="password" v-model="password" />
				</div>
			</div> -->
			<div class="form-row">
				<RegisterModal />
				<button class="btn btn-primary" @click="login()" style="margin-left: 20px;">登录</button>
			</div>
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
	#login{
		/* text-align: center; */
	}
</style>
