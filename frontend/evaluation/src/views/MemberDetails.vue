<!-- 某个用户的详情信息，组长可见 -->
<template>
	<div id="MemberDetails">
		<div class="bg"></div>
		<UserNav />
		<br />
		<div class="container">
			<button class="btn btn-light" @click="goBack">返回小组</button>
			<br /><br />
			<div class="row">
				<div class="card col-md-3 shadow p-3 mb-5 bg-white rounded">
					<h5 class="card-header">组员详情</h5>
					<p>姓名：{{memberInfo.name}}</p>
					<p>学号：{{memberInfo.userId}}</p>
					<p>电话号码：{{memberInfo.telephone}}</p>
				</div>
				<div class="col-md-1"></div>
				<div class="card col-md-8 shadow p-3 mb-5 bg-white rounded" v-if="scoreInfo">
					<h5 class="card-header">历次贡献率</h5>
					<div class="card" v-for="item in scoreInfo.personScores" :key="item.evaluationInnerId">
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
				<div class="card col-md-8 shadow p-3 mb-5 bg-white rounded" v-else>
					<h5 class="card-header">历次贡献率</h5>
					<div class="card">
						<p>暂无数据</p>
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
				scoreInfo: {},
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
				
				axios.get(api.userEvaluationInnerList, {
					params: {
						classId: self.$store.state.userInfo.classId
					}
				}).then(function(res) {
					if(res.status == 200 && res.data.status == 1) {
						for(var i=0; i<res.data.data.length; i++) {
							self.formList[res.data.data[i].evaluationInnerId] = res.data.data[i].name;
						}
					}
					axios.get(api.adminUserList,null)
					.then(function(res) {
						if(res.status == 200 && res.data.status == 1) {
							for(var i=0; i<res.data.data.length; i++) {
								if(res.data.data[i].userId == self.$route.query.userId) {
									self.memberInfo = res.data.data[i];
								}
							}
						}
					})
					axios.get(api.personScoreList,{
						params: {
							userId:self.$route.query.userId
						}
					}).then(function(res) {
						self.scoreInfo = res.data.data;
					}).catch(function(error) {
						console.log(error);
					})
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
