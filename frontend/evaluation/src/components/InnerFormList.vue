<!-- 组内评价表列表 -->
<template>
	<div id="InnerFormList">
		<ul>
			<li v-for="item in response.data" v-if="item.endTime>time" :key="item.evaluationInnerId">
				<router-link :to="{path:'/inner',query:{evaluationInnerId:item.evaluationInnerId}}">
					{{item.evaluationInnerId}}.{{item.name}}
				</router-link>
				<p>发布时间：{{getDate(item.releaseTime)}}
				截止时间：{{getDate(item.endTime)}}</p>
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
			request: {
				params: {
					classId:""
				}
			},
			response: {
				status:'',
				data:[]
			},
			time:''
		}
	},
	created() {
		this.time = parseInt(Number(new Date())/1000);
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
	}
}
</script>

<style>
</style>
