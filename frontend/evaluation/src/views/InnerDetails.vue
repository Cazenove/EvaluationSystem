<!-- 一份组内评分表的内容 -->
<template>
	<div id="InnerDetails">
		<UserNav />
		<br />
		<el-row :gutter="20">
			<el-col :span="6" :offset="2">
				<div class="grid-content bg-purple">
					<el-button type="primary" @click="goBack">返回列表</el-button>
				</div>
			</el-col>
		</el-row>
		<InnerEditableTable v-bind:evaluationInnerId = "evaluationInnerId" />
	</div>
</template>

<script>
	import axios from 'axios'
	import api from '../router/httpConfig.js'
	import UserNav from '../components/UserNav.vue'
	import InnerEditableTable from '../components/InnerEditableTable.vue'
	export default {
		inject: ['reload'],
		components:{
			UserNav,
			InnerEditableTable
		},
		data() {
			return {
				evaluationInnerId: null
			}
		},
		created() {
			this.evaluationInnerId = this.$route.query.evaluationInnerId;
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
