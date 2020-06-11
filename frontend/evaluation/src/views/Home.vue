<!-- 用户首页，显示评分表列表 -->
<template>
	<div class="home">
		<div class="bg"></div>
		<UserNav />
		<div id="mainContainer" class="container col-md-10 offset-md-1 w-75 shadow p-3 mb-5 bg-white rounded" style="margin: 50px auto;">
			<ul class="nav nav-tabs nav-fill" id="myTab" role="tablist">
				<li class="nav-item">
					<a class="nav-link active" id="home-tab" data-toggle="tab" href="#home" role="tab" aria-controls="home"
					 aria-selected="true">组间评分表</a>
				</li>
				<li class="nav-item" v-if="this.$store.state.userInfo.status == 2">
					<a class="nav-link" id="profile-tab" data-toggle="tab" href="#profile" role="tab" aria-controls="profile"
					 aria-selected="false">组内评分表</a>
				</li>
			</ul>
			<br />
			<div class="tab-content" id="myTabContent">
				<div class="tab-pane fade show active" id="home" role="tabpanel" aria-labelledby="home-tab">
					<OuterFormList />
				</div>
				<div v-if="this.$store.state.userInfo.status == 2" class="tab-pane fade" id="profile" role="tabpanel" aria-labelledby="profile-tab">
					<InnerFormList />
				</div>
			</div>
		</div>
	</div>
</template>

<script>
	import OuterFormList from '../components/OuterFormList.vue'
	import InnerFormList from '../components/InnerFormList.vue'
	import UserNav from '../components/UserNav.vue'

	export default {
		name: 'Home',
		components: {
			OuterFormList,
			InnerFormList,
			UserNav
		}
	}
</script>

<style>
	#mainContainer {
		background:#DEE2E6;padding:5px;color:#212529; 
		filter:alpha(Opacity=60);-moz-opacity:0.6;opacity: 0.95;
	}
</style>