<!-- 某个用户的详情信息，组长可见 -->
<template>
	<div id="MemberDetails">
		<UserNav />
		<br />
		<button class="btn btn-light" @click="goBack">返回小组</button>
		<br />
		<div class="text-left offset-md-1">
			<h2>组员详情</h2>
			<p>姓名：{{response.data.userName}}</p>
			<p>学号：{{response.data.userId}}</p>
			<hr />
			<h2>历次贡献率</h2>
			<p>{{response.data.content}}</p>
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
				response: {
					status:'',
					data:{}
				}
			}
		},
		mounted() {
			this.init();
		},
		methods: {
			getResponse() {
				let self = this;
				axios.get(api.userGroupUserdetails,{
					params: {
						userId:this.$route.query.userId
					}
				}).then(function(res) {
					console.log(res);
					self.response = res.data;
				}).catch(function(error) {
					console.log(error);
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
