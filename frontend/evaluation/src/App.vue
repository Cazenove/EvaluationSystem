<template>
	<div id="app">
		<div v-if="!this.$store.state.isInLoginPage">
			<nav class="navbar navbar-expand-lg navbar-light bg-light">
				<a class="navbar-brand" href="#">《软件工程实践》互动评价系统</a>
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
							<router-link class="nav-link" to="/about">我的小组</router-link>
						</li>
						<li class="nav-item">
							<router-link class="nav-link" to="/login">Login</router-link>
						</li>
					</ul>
				</div>
			</nav>
		</div>
		<div id="nav" v-if="!this.$store.state.isInLoginPage">
			<router-link to="/home">评分表</router-link> |
			<router-link to="/about">我的小组</router-link> |
			<router-link to="/login">Login</router-link>
			<div v-if="this.$store.state.loginState === 1">组员</div>
			<div v-else-if="this.$store.state.loginState === 2">组长</div>
			<div v-else>其他用户类型</div>
		</div>
		<router-view />
	</div>
</template>

<script>
	export default {
	}
</script>

<style>
	#app {
		font-family: Avenir, Helvetica, Arial, sans-serif;
		-webkit-font-smoothing: antialiased;
		-moz-osx-font-smoothing: grayscale;
		text-align: center;
		color: #2c3e50;
	}

	#nav {
		padding: 30px;
	}

	#nav a {
		font-weight: bold;
		color: #2c3e50;
	}

	#nav a.router-link-exact-active {
		color: #42b983;
	}
</style>
