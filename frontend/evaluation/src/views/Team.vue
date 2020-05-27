<!-- 小组详情信息页面 -->
<template>
	<div class="about">
		<UserNav />
		<br />
		<div class="text-left offset-md-1">
			<h2>我的小组</h2>
			<hr />
			<p>班级：{{response.data.className}}</p>
			<p>组号：{{response.data.groupNum}}</p>
			<p>组名：{{response.data.groupName}}
				<button
				 v-if="this.$store.state.userInfo.status === 2" 
				 class="btn btn-outline-light" 
				 @click="changeGroupName">修改名称</button>
			</p>
			<p v-if="this.$store.state.userInfo.status == 2">组长：
				<router-link :to="{path:'/member',query:{userId:response.data.leader.userId}}">
					{{response.data.leader.name}}
				</router-link>
			</p>
			<p v-else-if="this.$store.state.userInfo.status == 1">
				组长：{{response.data.leader.name}}
			</p>
			<p v-if="this.$store.state.userInfo.status == 2">组员：
				<ul class="list-group list-group-flush">
					<li class="list-group-item" v-for="member in response.data.member" :key="member.userId">
						<router-link :to="{path:'/member',query:{userId:member.userId}}">
							{{member.name}}
						</router-link>
					</li>
				</ul>
			</p>
			<p v-else-if="this.$store.state.userInfo.status == 1">组员：
				<ul class="list-group list-group-flush">
					<li class="list-group-item" v-for="member in response.data.member" :key="member.userId">
						{{member.name}}
					</li>
				</ul>
			</p>
			<hr />
			<h2>团队作业概况</h2>
			<div v-for="item in groupScore" :key="item.evaluationOuterId">
				<p>{{item.evaluationOuterId}}:{{item.content}}</p>
			</div>
			<div v-for="item in suggestion" :key="item.groupSuggestionId">
				<p>{{item.evaluationOuterId}}:{{item.suggestion}}</p>
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
				request: {
					params: {
						groupId:""
					}
				},
				response: {
					status:'',
					data: {
					    groupId:'',
					    groupName:'',
					    classId:'',
					    className:'',
					    groupNum:'',//小组在班级里的编号
					    leader: {
					        userId:'',//组长的学号
					        userName:''//组长的姓名
					    },
					    member: [],
					    data: []
					}
				},
				groupScore: [],
				suggestion: []
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
					params:{
						groupId: self.$store.state.userInfo.groupId
					}
				}).then(function(res) {
					if(res.status == 200 && res.data.status == 1) {
						self.response = res.data;
					} else {
						alert(res.data.msg);
					}
				}).catch(function(error) {
					console.log(error);
				})
			},
			getGroupScore() {
				var self = this;
				axios.get(api.adminGroupScoreList, {})
				.then(function(res) {
					if(res.status == 200 && res.data.status == 1) {
						var j=0;
						for(var i=0; i<res.data.data.length; i++) {
							if(res.data.data[i].groupId == self.$store.state.userInfo.groupId) {
								self.groupScore[j++] = res.data.data[i];
							}
						}
					} else {
						alert(res.data.msg);
					}
				}).catch(function(error) {
					console.log(error);
				})
			},
			getSuggestion() {
				var self = this;
				axios.get(api.adminSuggestionList, {})
				.then(function(res) {
					console.log(res);
					if(res.status == 200 && res.data.status == 1) {
						var j=0;
						for(var i=0; i<res.data.data.length; i++) {
							if(res.data.data[i].groupId == self.$store.state.userInfo.groupId) {
								self.suggestion[j++] = res.data.data[i];
							}
						}
						console.log(self.suggestion);
					} else {
						alert(res.data.msg);
					}
				}).catch(function(error) {
					console.log(error);
				})
			},
			init() {
				this.getRequest();
				this.getResponse();
				this.getGroupScore();
				this.getSuggestion();
			},
			changeGroupName() {
				
			}
		}
	}
</script>
