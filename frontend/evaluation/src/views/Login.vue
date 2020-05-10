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
					<label for="userId">用户名</label>
					<input type="text" class="form-control" placeholder="Username" id="username" v-model="userId"/>
				</div>
				<div class="form-group col-md-6">
					<label for="pw">密码</label>
					<input type="password" class="form-control" placeholder="Password" id="pw" v-model="password"/>
				</div>
			</div>
			<div class="form-row">
				<RegisterModal />
				<button class="btn btn-primary" @click="login()" style="margin-left: 20px;">登录</button>
			</div>
		</div>
	</div>
</template>

<script>
	import axios from 'axios'
	import api from '../router/httpConfig.js'
	import RegisterModal from '../components/RegisterModal.vue'
	export default {
		inject: ['reload'],
		data() {
			return {
				data: {
					status:'',
					data: []
				},
				welcomemsg: "《软件工程实践》互动评价系统",
				userId: "",
				password: "",
			}
		},
		components: {//注册组件
			RegisterModal
		},
		methods: {
			login() {
				var self = this;
				axios.post(api.login,{
					userId:this.$data.userId,
					password:this.$data.password
				}).then(function(res) {
					var ses = window.sessionStorage;
					if(res.status == 200) {
						self.$store.state.loginState = res.data.status;
						if(res.data.status == 1 || res.data.status == 2) {
							self.$store.state.userInfo.userId = res.data.data.userId;
							self.$store.state.userInfo.groupId = res.data.data.groupId;
							self.$store.state.userInfo.userName = res.data.data.name;
							self.$store.state.userInfo.status = res.data.data.status;
							self.$store.state.userInfo.password = res.data.data.password;
							self.$store.state.userInfo.telephone = res.data.data.telephone;
							self.$store.state.userInfo.classId = res.data.data.classId;
							ses.setItem('loginState', res.data.status);
							self.$router.push('/home');
						} else if(res.data.status == 3) {
							self.$store.state.adminInfo.assistantId = res.data.data.assistantId;
							self.$store.state.adminInfo.classId = res.data.data.classId;
							self.$store.state.adminInfo.name = res.data.data.name;
							self.$store.state.adminInfo.password = res.data.data.password;
							self.$store.state.adminInfo.telephone = res.data.data.telephone;
							self.$store.state.adminInfo.status = 3;
							ses.setItem('loginState', res.data.status);
							self.$router.push('/admin/user');
						} else if(res.data.status == 4) {
							self.$store.state.adminInfo.status = 4;
							ses.setItem('loginState', res.data.status);
							self.$router.push('/admin/user');
						} else {
							alert(res.data.msg);
						}
					}
				}).catch(function(error) {
					console.log(error);
				})
			},
		}
	}
</script>

<style>
	#login{
		/* text-align: center; */
	}
</style>
