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
						<router-link class="nav-link" to="/admin/user">用户管理</router-link>
					</li>
					<li v-if="this.$store.state.adminInfo.status === 4" class="nav-item">
						<router-link class="nav-link" to="/admin/assistant">助教管理</router-link>
					</li>
					<li v-if="this.$store.state.adminInfo.status === 4" class="nav-item">
						<router-link class="nav-link" to="/admin/class">班级管理</router-link>
					</li>
					<li v-if="this.$store.state.adminInfo.status === 4" class="nav-item">
						<router-link class="nav-link" to="/admin/team">小组管理</router-link>
					</li>
					<li class="nav-item dropdown">
						<a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown"
						 aria-haspopup="true" aria-expanded="false">
							评分表管理
						</a>
						<div class="dropdown-menu bg-dark" aria-labelledby="navbarDropdown">
							<router-link class="nav-link" to="/admin/form">评分表管理</router-link>
							<div class="dropdown-divider"></div>
							<router-link class="nav-link" to="/admin/evaluationouter">组间评分表提交记录</router-link>
							<router-link class="nav-link" to="/admin/evaluationinner">组内评分表提交记录</router-link>
							<div class="dropdown-divider"></div>
							<router-link class="nav-link" to="/admin/suggestion">建议管理</router-link>
						</div>
					</li>
					<li class="nav-item dropdown">
						<a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown"
						 aria-haspopup="true" aria-expanded="false">
							统计
						</a>
						<div class="dropdown-menu bg-dark" aria-labelledby="navbarDropdown">
							<router-link class="nav-link" to="/admin/TeamScore">小组历次评分</router-link>
							<router-link class="nav-link" to="/admin/PersonScore">个人历次评分</router-link>
							<router-link class="nav-link" to="/admin/TeamFinalScore">小组综合评分</router-link>
							<router-link class="nav-link" to="/admin/PersonFinalScore">个人综合评分</router-link>
						</div>
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
	#ManageNav {
		font-family: "Microsoft JhengHei", 华文细黑;
	}
</style>
