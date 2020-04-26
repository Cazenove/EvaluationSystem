<template>
	<div class="about">
		<UserNav v-if="!this.$store.state.isInLoginPage" />
		<h1>This is an about page</h1>
		<button class="btn btn-success" @click="SendGet">GET</button><br /><br />
		<!-- <button class="btn btn-primary" @click="SendPost">POST</button> -->
		<button type="button" class="btn btn-secondary" data-container="body" data-toggle="popover" data-placement="top"
		 data-content="Vivamus sagittis lacus vel augue laoreet rutrum faucibus.">
			Popover on top
		</button>
	</div>
</template>

<script>
	import axios from 'axios'
	import UserNav from '../components/UserNav.vue'
	
	export default {
		data() {
			return {
				data: ""
			}
		},
		components:{
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
