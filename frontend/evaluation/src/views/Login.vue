<!-- 登录注册页面 -->
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
			<button class="btn btn-primary" data-toggle="modal" data-target="#registerModal" @click="getClassInfo">注册</button><br /><br />
			<button class="btn btn-primary" @click="register()">登录</button>
		</div>

		<!-- 注册模态框 -->
		<div class="modal fade" id="registerModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title" id="registerModalLabel">注册</h5>
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">
						<div class="form-group">
							<label for="regisiter-userid">学号</label>
							<input type="text" class="form-control" id="regisiter-userid" v-model="registerInfo.userid" />
						</div>
						<div class="form-group">
							<label for="regisiter-password">密码</label>
							<input type="password" class="form-control" id="regisiter-password" v-model="registerInfo.password" />
						</div>
						<div class="form-group">
							<label for="register-username">姓名</label>
							<input type="text" class="form-control" id="register-username" v-model="registerInfo.username" />
						</div>
						<div class="form-group">
							<label for="telephone">电话号码</label>
							<input type="text" class="form-control" id="telephone" v-model="registerInfo.telephone" />
						</div>
						<div class="form-group">
							<label for="classid">班级</label>
							<input type="text" class="form-control" id="classid" v-model="registerInfo.classid" />
						</div>
						<div class="form-group">
							<label for="groupid">小组</label>
							<input type="text" class="form-control" id="groupid" v-model="registerInfo.groupid" />
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
						<button type="button" class="btn btn-primary">注册</button>
					</div>
				</div>
			</div>
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
				classInfo: {
					
				},
				registerInfo: {
					userid:"",
					password:"",
					username:"",
					telephone:"",
					classid:"",
					groupid:""
				}
			}
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
			},
			getClassInfo() {
				// 点击注册时获取班级的小组信息
				axios.post('/admin/get',{})
				.then(function(response) {
					this.$data.classInfo = response.data;
				}).catch(function(error) {
					
				});
			},
			register() {
				// 注册
				axios.post('/user/register',this.registerInfo)
				.then(function(response) {
					console.log(response);
				}).catch(function(error) {
					console.log(error);
				});
			}
		}
	}
</script>

<style>
</style>
