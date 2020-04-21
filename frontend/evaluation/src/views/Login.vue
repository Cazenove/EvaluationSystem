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
export default {
	data(){
		return {
			welcomemsg: "《软件工程实践》互动评价系统",
			username: "",
			password: ""
		}
	},
	beforeCreate: function(){
		this.$store.state.isInLoginPage=true;
	},
	methods:{
		login() {
			var username = this.$data.username;
			var password = this.$data.password;
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
	}
}
</script>

<style>
</style>
