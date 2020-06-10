<!-- 组内评价表列表 -->
<template>
	<div id="InnerFormList">
		<div class="media position-relative shadow p-3 mb-5 bg-white rounded"
		v-for="item in response.data" v-if="item.endTime>time" :key="item.evaluationInnerId"
		v-on:mouseover="addActive($event)" v-on:mouseout="removeActive($event)">
			<img src="../assets/check.png" height="150px" class="rounded">
			<div class="media-body">
				<h4 class="mt-0 user-select-none">{{item.name}}</h4>
				<p class="user-select-none">评分表开放填写中！</p>
				<router-link class="text-success stretched-link" :to="{path:'/inner',query:{evaluationInnerId:item.evaluationInnerId}}">
					{{item.name}}
				</router-link>
				<br />
				<small>发布时间：{{getDate(item.releaseTime)}}</small>
				<br />
				<small>截止时间：{{getDate(item.endTime)}}</small>
			</div>
		</div>
		<div class="media position-relative shadow p-3 mb-5 bg-white rounded"
		v-else
		v-on:mouseover="addActive($event)" v-on:mouseout="removeActive($event)">
			<img src="../assets/close.png" height="150px" class="rounded">
			<div class="media-body">
				<h4 class="mt-0 user-select-none">{{item.name}}</h4>
				<p class="user-select-none">该评分表已停止填写，若小组尚未填写请联系老师或助教。</p>
				<router-link class="text-danger" >
					{{item.name}}
				</router-link>
				<br />
				<small>发布时间：{{getDate(item.releaseTime)}}</small>
				<br />
				<small>截止时间：{{getDate(item.endTime)}}</small>
			</div>
		</div>
		<!-- <ul class="list-group list-group-flush">
			<li class="list-group-item" v-for="item in response.data" v-if="item.endTime>time" :key="item.evaluationInnerId">
				<div class="card">
					<div class="card-body">
						<router-link class="btn btn-outline-success btn-lg btn-block" :to="{path:'/inner',query:{evaluationInnerId:item.evaluationInnerId}}">
							<span class="badge badge-pill badge-success">开放中</span>{{item.name}}
						</router-link>
						<p>发布时间：{{getDate(item.releaseTime)}}<br />
						截止时间：{{getDate(item.endTime)}}</p>
					</div>
				</div>
			</li>
			<li class="list-group-item" v-else>
				<div class="card">
					<div class="card-body">
						<router-link class="btn btn-outline-secondary btn-lg btn-block disabled" :to="{path:'/inner',query:{evaluationInnerId:item.evaluationInnerId}}">
							<span class="badge badge-pill badge-dark">已截止</span>{{item.name}}
						</router-link>
						<p>发布时间：{{getDate(item.releaseTime)}}<br />
						截止时间：{{getDate(item.endTime)}}</p>
					</div>
				</div>
			</li>
		</ul> -->
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
		},
		addActive($event) {
			$event.currentTarget.className="media position-relative shadow-lg p-3 mb-5 bg-white rounded";
		},
		removeActive($event) {
			$event.currentTarget.className="media position-relative shadow p-3 mb-5 bg-white rounded";
		}
	}
}
</script>

<style>
</style>
