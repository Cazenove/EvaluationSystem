<!-- 一份组间评分表的内容 -->
<template>
	<div id="OuterDetails">
		<UserNav />
		<br />
		<el-row :gutter="20">
			<el-col :span="6" :offset="2">
				<div class="grid-content bg-purple">
					<el-button type="primary" @click="goBack">返回列表</el-button>
				</div>
			</el-col>
		</el-row>
		<OuterEditableTable v-bind:evaluationOuterId = "evaluationOuterId" />
	</div>
</template>

<script>
	import axios from 'axios'
	import api from '../router/httpConfig.js'
	import UserNav from '../components/UserNav.vue'
	import OuterEditableTable from '../components/OuterEditableTable.vue'
	export default {
		inject: ['reload'],
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
