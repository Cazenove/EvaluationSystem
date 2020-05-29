<!-- 某个用户的详情信息，组长可见 -->
<template>
	<div id="MemberDetails">
		<UserNav />
		<br />
		<div class="container">
			<button class="btn btn-light" @click="goBack">返回小组</button>
			<br /><br />
			<div class="row">
				<div class="card-body col-md-4">
					<h5 class="card-header">组员详情</h5>
					<p>姓名：{{memberInfo.userName}}</p>
					<p>学号：{{memberInfo.userId}}</p>
				</div>
				<div class="card-body col-md-8">
					<h5 class="card-header">历次贡献率</h5>
					
				</div>
			</div>
		</div>
	</div>
</template>

<script>
	import axios from 'axios'
	import api from '../router/httpConfig.js'
	import UserNav from '../components/UserNav.vue'
	export default {
		inject: ['reload'],
		components: {
			UserNav
		},
		data() {
			return {
				response: {},
				memberInfo: {}
			}
		},
		mounted() {
			this.init();
		},
		methods: {
			getResponse() {
				var self = this;
				axios.get(api.userGroupUserdetails,{
					params: {
						userId:this.$route.query.userId
					}
				}).then(function(res) {
					self.memberInfo = res.data.data;
				}).catch(function(error) {
					console.log(error);
				})
				axios.get(api.adminUserScoreList,null)
				.then(function(res) {
					self.response = res.data;
				}).catch(function(error) {
					consle.log(error);
				})
			},
			init() {
				this.getResponse();
			},
			goBack() {
				this.$router.push('/team');
			}
		}
	}
</script>

<style>
</style>
