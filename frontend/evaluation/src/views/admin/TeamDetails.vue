<!-- 小组详情信息页面 -->
<template>
	<div class="about">
		<ManageNav />
		<br />
		<div class="container">
			<button class="btn btn-light" @click="goBack">返回</button>
			<br /><br />
			<div class="card">
				<h5 class="card-header">我的小组</h5>
				<div class="card-body">
					<p>班级：{{response.data.className}}</p>
					<p>组号：{{response.data.groupNum}}</p>
					<p>组名：{{response.data.groupName}}</p>
					<p>组长：
						<router-link class="btn btn-outline-success" :to="{path:'/admin/user/details',query:{userId:response.data.leader.userId, classId:response.data.classId}}">
							{{response.data.leader.name}}
						</router-link>
					</p>
					<div>组员：
						<router-link v-for="member in response.data.member" :key="member.userId" class="btn btn-outline-info" :to="{path:'/admin/user/details',query:{userId:member.userId, classId:response.data.classId}}">
							{{member.name}}
						</router-link>
					</div>
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
	import api from '../../router/httpConfig.js'
	import ManageNav from '../../components/ManageNav.vue'
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
				teamScore: {},
				groupName: {
					groupId: "",
					groupName: ""
				}
			}
		},
		created() {
			//创建时获取小组的信息
			this.init();
		},
		components: {
			ManageNav
		},
		methods: {
			getRequest() {
				this.$data.request.params.groupId = this.$route.query.groupId;
			},
			getResponse() {
				var self = this;
				axios.get(api.userGroupDetails, {
					params: {
						groupId: self.$route.query.groupId
					}
				}).then(function(res) {
					if (res.status == 200 && res.data.status == 1) {
						self.response = res.data;
						self.groupName.groupId = res.data.data.groupId.toString();
						self.groupName.groupName = res.data.data.groupName;
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
					classId: this.$route.query.classId
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
									if (res.data.data[i].groupId == self.$route.query.groupId) {
										self.teamScore[res.data.data[i].evaluationOuterId].score = res.data.data[i].content;
									}
								}
								axios.get(api.adminSuggestionList, null)
								.then(function(res) {
									if (res.status == 200 && res.data.status == 1) {
										for (var i = 0; i < res.data.data.length; i++) {
											if (res.data.data[i].groupId == self.$route.query.groupId) {
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
			goBack() {
				this.$router.push('/admin/user');
			}
		}
	}
</script>
