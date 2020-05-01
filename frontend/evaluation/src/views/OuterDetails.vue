<!-- 一份组间评分表的内容 -->
<template>
	<div id="OuterDetails">
		<UserNav />
		<br />
		<button class="btn btn-light" @click="goBack">返回列表</button>
		<OuterEditableTable v-bind:evaluationOuterId = "evaluationOuterId" />
	</div>
</template>

<script>
	import axios from 'axios'
	import api from '../router/httpConfig.js'
	import UserNav from '../components/UserNav.vue'
	import OuterEditableTable from '../components/OuterEditableTable.vue'
	export default {
		components:{
			UserNav,
			OuterEditableTable
		},
		data() {
			return {
				evaluationOuterId: null
			}
		},
		created() {
			this.evaluationOuterId = this.$route.query.evaluationOuterId;
		},
		methods: {
			goBack() {
				this.$router.push('/home');
			}
		}
		
	}
</script>

<style>
</style>
