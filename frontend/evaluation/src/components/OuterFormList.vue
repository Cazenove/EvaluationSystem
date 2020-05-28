<!-- 组间评价表列表 -->
<template>
	<div id="OuterFormList">
		<ul>
			<li v-for="item in response.data" v-if="item.endTime>time" :key="item.evaluationOuterId">
				<div>
					<router-link :to="{path:'/outer',query:{evaluationOuterId:item.evaluationOuterId}}">
						{{item.evaluationOuterId}}.{{item.name}}
					</router-link>
					<p>发布时间：{{getDate(item.releaseTime)}}
					截止时间：{{getDate(item.endTime)}}</p>
				</div>
			</li>
			<li v-else>
				<div>
					<p>
						[已截止]{{item.evaluationOuterId}}.{{item.name}}
					</p>
					<p>发布时间：{{getDate(item.releaseTime)}}
					截止时间：{{getDate(item.endTime)}}</p>
				</div>
			</li>
		</ul>
	</div>
</template>

<script>
	import axios from 'axios'
	import api from '../router/httpConfig.js'
	export default {
		inject: ['reload'],
		data() {
			return {
				request: { //通过班级组号请求列表
					classId: ""
				},
				response: {
					status: 1,
					data: []
				},
				time:''
			}
		},
		methods: {
			// 目的是获取组间评分表列表
			getRequest() {
				//从登录状态中得到班级和组号
				this.$data.request.classId = this.$store.state.userInfo.classId;
			},
			getResponse() {
				var self = this;
				axios.post(api.userEvaluationOuterList, self.request)
				.then(function(res) {
					self.response = res.data;
				}).catch(function(error) {
					console.log(error);
				})
			},
			init() {
				this.getRequest(),
				this.getResponse()
			},
			getDate(source) {
				var timeStamp = new Date(parseInt(source*1000));
				var year = timeStamp.getFullYear();
				var month = timeStamp.getMonth() + 1;
				var date = timeStamp.getDate();
				var h = timeStamp.getHours();
				var m = timeStamp.getMinutes();
				var s = timeStamp.getSeconds();
				var time = year+"-"+month+"-"+date+" "+h+":"+m+":"+s;
				return time;
			}
		},
		created() {
			this.time = parseInt(Number(new Date())/1000);
			//创建的时候根据班级小组来获取评分表
			this.init();
		},
		
	}
</script>

<style>
</style>
