<template>
	<div id="OuterFormList">
		<ul>
			<li v-for="item in response.data" :key="item.evaluationOuterId">
				<router-link :to="{path:'/outer',query:{id:item.evaluationOuterId}}">
					{{item.evaluationOuterId}}.{{item.name}}
				</router-link>
				<p>发布时间：{{item.releaseTime}} 截止时间：{{item.endTime}}</p>
			</li>
		</ul>
	</div>
</template>

<script>

export default {
	data() {
		return {
			request: {//通过班级组号请求列表
				classId:"",
				groupId:""
			},
			response: {
				status:1,
				data:[
					{
						evaluationOuterId:1,
						name:"第一组团队作业组间评分表",
						releaseTime:"2020-01-01",//发布时间
						endTime:"2020-01-08"//结束时间
					},
					{
						evaluationOuterId:2,
						name:"第二组团队作业组间评分表",
						releaseTime:"2020-01-09",
						endTime:"2020-01-16"
					}
				]
			}
		}
	},
	methods: {
		getRequest() {
			//从登录状态中得到班级和组号
			this.$data.request.classId = this.$store.state.userInfo.classId;
			this.$data.request.groupId = this.$store.state.userInfo.groupId;
		}
	},
	created() {
		//创建的时候根据班级小组来获取评分表
		this.getRequest();
		console.log(this.$data.request);
	}
}
</script>

<style>
</style>
