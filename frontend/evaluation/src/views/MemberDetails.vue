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
	import UserNav from '../components/UserNav.vue'
	export default {
		components: {
			UserNav
		},
		data() {
			return {
				request: {
					userId:null
				},
				response: {
					status:1,
					data:{
					        userId:"221701000",
					        userName:"张三" ,
					        data:[
					            {
					                evaluationInnerId:1,
					                name:"第一次团队作业_组内评分表",
					                decision:"前端",
					                contribution:40
					            },
					            {
					                evaluationInnerId:2,
					                name:"第二次团队作业_组内评分表",
					                decision:"前端",
					                contribution:50
					            }
					        ]
					}
				}
			}
		},
		created() {
			this.getRequest();
		},
		methods: {
			getRequest() {
				this.$data.request.userId = this.$route.query.userId;
			},
			goBack() {
				this.$router.push('/team');
			}
		}
	}
</script>

<style>
</style>
