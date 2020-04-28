<!-- 小组详情信息页面 -->
<template>
	<div class="about">
		<UserNav />
		<h1>我的小组</h1>
		<hr />
		<h3>小组成员</h3>
		<p>组长：<a href="">{{ leader.name }}</a></p>
		<p>组员：<a v-for="person in member" href="">{{person.name}}  </a></p>
		<hr />
		
	</div>
</template>

<script>
	import axios from 'axios'
	import UserNav from '../components/UserNav.vue'

	export default {
		data() {
			return {
				groupId: "1", //小组 id
				groupName: "第一组", //小组名称
				leader: {
					id: "221701000", //组长学号
					name: "张三", //组长姓名
				},
				member: [{
					id: "221701001", //组员学号
					name: "李四", //组员姓名
				}, {
					id: "221701002", //组员学号
					name: "王五", //组员姓名
				}],
				data: [{
					id: "1", //课程活动 id
					score: 100, //本次得分
					suggestion:["建议 1 ","建议 2 "] //收到的建议
				}, {
					id: "2", //课程活动 id
					score: 99, //本次得分
					suggestion:["建议 1 ","建议 2 "] //收到的建议
				}, ]
			}
		},
	components: {
			UserNav
		},
		methods: {
			SendGet: function() {
				axios.get('http://www.dzyong.top:3005/yiqing/history', {
					params: {}
				}).then(function(res) {
					console.log(res.data.data);
				}).catch(function(error) {
					console.log(error);
				});
			},
			SendPost: function() {
				axios.post('', {}).then(function(response) {
					console.log(response);
				}).catch(function(error) {
					console.log(error);
				});
			}
		},
		// created() {
		// 	axios.get('http://www.dzyong.top:3005/yiqing/history',{
		// 		params:{
		// 		}
		// 	}).then(function(res){
		// 		console.log(res.data);
		// 	}).catch(function(error){
		// 		console.log(error);
		// 	});
		// }
	}
</script>
