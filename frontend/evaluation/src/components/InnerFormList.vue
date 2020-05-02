<!-- 组内评价表列表 -->
<template>
	<div id="InnerFormList">
		<ul>
			<li v-for="item in response.data" :key="item.evaluationInnerId">
				<router-link :to="{path:'/inner',query:{evaluationInnerId:item.evaluationInnerId}}">
					{{item.evaluationInnerId}}.{{item.name}}
				</router-link>
				<p>发布时间：{{item.releaseTime}} 截止时间：{{item.endTime}}</p>
			</li>
		</ul>
	</div>
</template>

<script>
import axios from 'axios'
import api from '../router/httpConfig.js'
export default {
	data() {
		return {
			request: {
				classId:"",
				groupId:""
			},
			response: {
				status:1,
				data:[
				    {
				        evaluationInnerId:1,
				        name:"第一组团队作业_组内评分表",
				        releaseTime:"2020-01-01",//发布时间
				        endTime:"2020-01-08"//结束时间
				    },
				    {
				        evaluationInnerId:2,
				        name:"第二组团队作业_组内评分表",
				        releaseTime:"2020-01-09",
				        endTime:"2020-01-16"
				    }
				]
			}
		}
	},
	created() {
		//创建时根据班级小组获取评分表列表
		this.init();
	},
	methods: {
		// 目的是获取组内评分表列表
		getRequest() {
			this.$data.request.classId = this.$store.state.userInfo.classId;
			this.$data.request.groupId = this.$store.state.userInfo.groupId;
		},
		getResponse() {
			var self = this;
			axios.post(api.userEvaluationInnerList,self.request)
			.then(function(res) {
				self.response = res;
				console.log(self.response);
			}).catch(function(error) {
				console.log(error);
			})
		},
		init() {
			this.getRequest(),
			this.getResponse()
		}
	}
}
</script>

<style>
</style>
