<!-- 某个用户的详情信息，组长可见 -->
<template>
	<div id="MemberDetails">
		<UserNav />
		<br />
		<div class="container">
			<button class="btn btn-light" @click="goBack">返回小组</button>
			<br /><br />
			<div class="row">
				<div class="card col-md-3">
					<h5 class="card-header">组员详情</h5>
					<p>姓名：{{memberInfo.userName}}</p>
					<p>学号：{{memberInfo.userId}}</p>
				</div>
				<div class="col-md-1"></div>
				<div class="card col-md-8">
					<h5 class="card-header">历次贡献率</h5>
					<div class="card" v-for="item in scoreList" :key="item.evaluationInnerId">
						<div class="card-body">
							<h5 class="card-title">{{formList[item.evaluationInnerId]}}</h5>
							<ul class="list-group list-group-horizontal">
								<li class="list-group-item btn-info active">贡献率</li>
								<li class="list-group-item">{{item.content}}</li>
								<li class="list-group-item btn-info active">分工</li>
								<li class="list-group-item">{{item.decision}}</li>
							</ul>
						</div>
					</div>
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
				memberInfo: {},
				scoreList: [],
				formList: {}
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
					axios.get(api.userEvaluationInnerList, {
						params: {
							classId: self.$store.state.userInfo.classId
						}
					}).then(function(res) {
						console.log(res);
						for(var i=0; i<res.data.data.length; i++) {
							self.formList[res.data.data[i].evaluationInnerId] = res.data.data[i].name;
						}
					})
					axios.get(api.adminUserScoreList, null)
					.then(function(res) {
						for(var i=0; i<res.data.data.length; i++) {
							if(res.data.data[i].userId == self.memberInfo.userId) {
								self.scoreList.push(res.data.data[i]);
							}
						}
					}).catch(function(error) {
						console.log(error);
					})
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
