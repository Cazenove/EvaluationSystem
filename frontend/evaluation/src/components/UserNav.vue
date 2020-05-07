<template>
	<div id="UserNav">
		<nav class="navbar navbar-expand-lg navbar-light bg-light">
			<a class="navbar-brand" href="#/home">《软件工程实践》互动评价系统</a>
			<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
			 aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarSupportedContent">
				<ul class="navbar-nav mr-auto">
					<li class="nav-item active">
						<router-link class="nav-link" to="/home">评分表</router-link>
					</li>
					<li class="nav-item">
						<router-link class="nav-link" to="/team">我的小组</router-link>
					</li>
					<li class="nav-item">
						
					</li>
					<li class="nav-item dropdown">
						<a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" role="button" data-toggle="dropdown"
						 aria-haspopup="true" aria-expanded="false">
							个人中心
						</a>
						<div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
							<a class="dropdown-item" @click="toAccount">个人信息</a>
							<a class="dropdown-item" @click="toChangePassword">修改密码</a>
							<a class="dropdown-item" @click="loginOut">注销</a>
						</div>
					</li>
				</ul>
			</div>
		</nav>
	</div>
</template>

<script>
	export default {
		methods: {
			loginOut() {
				window.sessionStorage.removeItem('loginState');
				this.$router.push('/');
				alert("已注销");
			},
			toAccount() {
				this.$router.push('/account');
			},
			toChangePassword() {
				this.$router.push('/changepassword');
			}
		}
	}
</script>

<style>
</style>
