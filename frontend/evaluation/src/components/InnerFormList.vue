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
				params: {
					classId:""
				}
			},
			response: {
				status:'',
				data:[]
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
			this.$data.request.params.classId = this.$store.state.userInfo.classId;
		},
		getResponse() {
			var self = this;
			axios.get(api.userEvaluationInnerList,self.request)
			.then(function(res) {
				self.response = res.data;
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
