<template>
  <div class="about">
    <h1>This is an about page</h1>
    <button class="btn btn-success" @click="SendGet">GET</button><br /><br />
	<!-- <button class="btn btn-primary" @click="SendPost">POST</button> -->
  </div>
</template>

<script>
	import axios from 'axios'
	export default {
		data() {
			return {
				data: ""
			}
		},
		methods: {
			SendGet: function() {
				axios.get('http://www.dzyong.top:3005/yiqing/history',{
					params:{
					}
				}).then(function(res){
					console.log(res.data.data);
				}).catch(function(error){
					console.log(error);
				});
			},
			SendPost: function() {
				axios.post('', {
				}).then(function (response) {
					console.log(response);
				}).catch(function (error) {
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