<template>
<div id="ManageNav">
		<nav class="navbar navbar-dark bg-dark navbar-expand-lg">
			<a class="navbar-brand" href="#">评价系统后台</a>
			<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
			aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarSupportedContent">
				<ul class="navbar-nav mr-auto">
					<li class="nav-item">
						<router-link class="nav-link" to="/usermanagement">用户管理</router-link>
					</li>
					<li class="nav-item">
						<router-link class="nav-link" to="/classmanagement">班级管理</router-link>
					</li>
					<li class="nav-item">
						<router-link class="nav-link" to="/scoremanagement">评分管理</router-link>
					</li>
					<li class="nav-item">
						<a href="" class="nav-link">评分表管理</a>
					</li>
					<li class="nav-item">
						<a href="" class="nav-link">评分统计</a>
					</li>
					<li class="nav-item">
						<a href="" class="nav-link" @click="loginOut()">注销</a>
					</li>
				</ul>
			</div>
		</nav>
	</div>
</template>

<script>
	export default {
		name: "ManageNav"
	}
</script>

<style>
	#ManageNav{
		font-family:"Microsoft JhengHei",华文细黑;
	}
</style>

