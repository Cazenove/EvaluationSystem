<template>
	<div id="UserNav">
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
</template>

<script>
</script>

<style>
</style>
