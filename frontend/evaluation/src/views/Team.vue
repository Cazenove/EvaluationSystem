<!-- 小组详情信息页面 -->
<template>
	<div class="about">
		<UserNav />
		<br />
		<div class="container">
			<div class="card">
				<h5 class="card-header">我的小组</h5>
				<div class="card-body">
					<p>班级：{{response.data.className}}</p>
					<p>组号：{{response.data.groupNum}}</p>
					<p>组名：{{response.data.groupName}}
						<button v-if="this.$store.state.userInfo.status === 2" class="btn btn-outline-light" @click="changeGroupName">修改名称</button>
					</p>
					<p v-if="this.$store.state.userInfo.status == 2">组长：
						<router-link class="btn btn-outline-success" :to="{path:'/member',query:{userId:response.data.leader.userId}}">
							{{response.data.leader.name}}
						</router-link>
					</p>
					<p v-else-if="this.$store.state.userInfo.status == 1">
						组长：{{response.data.leader.name}}
					</p>
					<div v-if="this.$store.state.userInfo.status == 2">组员：
						<router-link v-for="member in response.data.member" :key="member.userId" class="btn btn-outline-info" :to="{path:'/member',query:{userId:member.userId}}">
							{{member.name}}
						</router-link>
					</div>
					<p v-else-if="this.$store.state.userInfo.status == 1">组员：
						<ul class="list-group list-group-flush">
							<li class="list-group-item" v-for="member in response.data.member" :key="member.userId">
								{{member.name}}
							</li>
						</ul>
					</p>
				</div>
			</div>
			<div class="card" v-if="isReady">
				<h5 class="card-header">得分情况</h5>
				<div class="card" v-for="item in teamScore" :key="item.evaluationOuterId">
					<div class="card-body">
						<h5 class="card-title">{{item.name}} ： {{item.score}}</h5>
						<ul class="list-group list-group-horizontal">
							<li class="list-group-item btn-info active">收到的建议</li>
							<li class="list-group-item" v-for="suggestion in item.suggestion" :key="suggestion">
								{{suggestion}}
							</li>
						</ul>
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
		data() {
			return {
				isReady: false,
				request: {
					params: {
						groupId: ""
					}
				},
				response: {
					status: '',
					data: {
						groupId: '',
						groupName: '',
						classId: '',
						className: '',
						groupNum: '', //小组在班级里的编号
						leader: {
							userId: '', //组长的学号
							userName: '' //组长的姓名
						},
						member: [],
						data: []
					}
				},
				teamScore: {}
			}
		},
		created() {
			//创建时获取小组的信息
			this.init();
		},
		components: {
			UserNav
		},
		methods: {
			getRequest() {
				this.$data.request.params.groupId = this.$store.state.userInfo.groupId;
			},
			getResponse() {
				var self = this;
				axios.get(api.userGroupDetails, {
					params: {
						groupId: self.$store.state.userInfo.groupId
					}
				}).then(function(res) {
					if (res.status == 200 && res.data.status == 1) {
						console.log(res);
						self.response = res.data;
					} else {
						alert(res.data.msg);
					}
				}).catch(function(error) {
					console.log(error);
				})
			},
			getTeamScoreInfo() {
				var self = this;
				axios.post(api.userEvaluationOuterList, {
					classId: this.$store.state.userInfo.classId
				}).then(function(res) {
					if (res.status == 200 && res.data.status == 1) {
						for (var i = 0; i < res.data.data.length; i++) {
							self.teamScore[res.data.data[i].evaluationOuterId] = {
								name: res.data.data[i].name,
								score: '',
								suggestion: []
							}
						}
						axios.get(api.adminGroupScoreList, null)
						.then(function(res) {
							if (res.status == 200 && res.data.status == 1) {
								for (var i = 0; i < res.data.data.length; i++) {
									if (res.data.data[i].groupId == self.$store.state.userInfo.groupId) {
										self.teamScore[res.data.data[i].evaluationOuterId].score = res.data.data[i].content;
									}
								}
								axios.get(api.adminSuggestionList, null)
								.then(function(res) {
									if (res.status == 200 && res.data.status == 1) {
										for (var i = 0; i < res.data.data.length; i++) {
											if (res.data.data[i].groupId == self.$store.state.userInfo.groupId) {
												self.teamScore[res.data.data[i].evaluationOuterId].suggestion.push(res.data.data[i].suggestion);
											}
										}
										self.isReady = true;
									}
								}).catch(function(error) {
									console.log(error);
								})
							}
						}).catch(function(error) {
							console.log(error);
						})
					}
				}).catch(function(error) {
					console.log(error);
				})
			},
			init() {
				this.getRequest();
				this.getResponse();
				this.getTeamScoreInfo();
			},
			changeGroupName() {

			}
		}
	}
</script>
