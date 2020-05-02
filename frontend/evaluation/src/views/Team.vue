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
			<p>组长：
				<router-link :to="{path:'/member',query:{userId:response.data.leader.userId}}">
					{{response.data.leader.userName}}
				</router-link></p>
			<p>组员：
				<ul class="list-group list-group-flush">
					<li class="list-group-item" v-for="member in response.data.member" :key="member.userId">
						<router-link :to="{path:'/member',query:{userId:member.userId}}">
							{{member.userName}}
						</router-link>
					</li>
				</ul>
			</p>
			<hr />
			<h2>团队作业概况</h2>
			<div v-for="item in response.data.data" :key="item.evaluationOuterId">
				<p>{{item.evaluationOuterId}}</p>
				<p>{{item.name}}</p>
				<p>{{item.score}}</p>
				<p>{{item.suggestion}}</p>
			</div>
		</div>
	</div>
</template>

<script>
	import axios from 'axios'
	import api from '../router/httpConfig.js'
	import UserNav from '../components/UserNav.vue'
	export default {
		data() {
			return {
				request: {
					groupId:""
				},
				response: {
					status:1,
					data: {
					    groupId:1,
					    groupName:"第一组",
					    classId:1,
					    className:"2020软件工程S班",
					    groupNum:1,//小组在班级里的编号
					    leader: {
					        userId:"221701000",//组长的学号
					        userName:"张三"//组长的姓名
					    },
					    member: [
					        {
					            userId:"221701001",
					            userName:"李四"
					        },
					        {
					            userId:"221701002",
					            userName:"王五"
					        },
							{
							    userId:"221701003",
							    userName:"陆"
							},
							{
							    userId:"221701004",
							    userName:"柒"
							},
							{
							    userId:"221701005",
							    userName:"捌"
							}
					    ],
					    data: [
					        {
					            evaluationOuterId:1,
					            name:"第一次团队作业_组间评分表",
					            score:93,
					            suggestion:["建议1","建议2"]
					        },
					        {
					            evaluationOuterId:2,
					            name:"第二次团队作业_组间评分表",
					            score:94,
					            suggestion:["建议1","建议2"]
					        }
					    ]
					}
				}
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
				this.$data.request.groupId = this.$store.state.userInfo.groupId;
			},
			getResponse() {
				var self = this;
				axios.get(api.userGroupDetails, self.request)
					.then(function(res) {
						self.response = res;
					}).catch(function(error) {
						console.log(error);
					})
			},
			init() {
				this.getRequest(),
				this.getResponse()
			},
			changeGroupName() {
				
			}
		}
	}
</script>
