<!-- 某个用户的详情信息，组长可见 -->
<template>
	<div id="MemberDetails">
		<UserNav />
		<br />
		<button class="btn btn-light" @click="goBack">返回小组</button>
		<br />
		<div class="text-left offset-md-1">
			<h2>组员详情</h2>
			<p>姓名：{{response.data.name}}</p>
			<p>学号：{{response.data.userId}}</p>
			<hr />
			<h2>历次贡献率</h2>
			<ul class="list-group list-group-flush">
				<li class="list-group-item" v-for="item in response.data.data" :key="item.evaluationInnerId">
					{{item.name}}<br />
					{{item.decision}}<br />
					{{item.contribution}}
				</li>
			</ul>
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
		created() {
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
