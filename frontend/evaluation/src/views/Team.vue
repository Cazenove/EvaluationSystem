<!-- 小组详情信息页面 -->
<template>
	<div class="about">
		<UserNav />
		<h1>我的小组</h1>
		<hr />
		<p>班级：{{response.data.className}}</p>
		<p>组号：{{response.data.groupNum}}</p>
		<p>组名：{{response.data.groupName}}</p>
		<p>组长：<a href="">{{response.data.leader.userName}}</a></p>
		<p>组员：<a v-for="person in response.data.member" href="">{{person.userName}}  </a></p>
		<hr />
		<div v-for="item in response.data.data">
			<p>{{item.evaluationOuterId}}</p>
			<p>{{item.name}}</p>
			<p>{{item.score}}</p>
			<p>{{item.suggestion}}</p>
		</div>
	</div>
</template>

<script>
	import axios from 'axios'
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
			//创建时获取班级的信息
			this.getRequest();
		},
		components: {
			UserNav
		},
		methods: {
			getRequest() {
				this.$data.request.groupId = this.$store.state.userInfo.groupId;
			}
		}
	}
</script>
