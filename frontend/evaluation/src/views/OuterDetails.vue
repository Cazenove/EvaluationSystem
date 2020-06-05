<!-- 一份组间评分表的内容 -->
<template>
	<div id="OuterDetails">
		<UserNav />
		<div class="alert alert-primary" role="alert">
			自己小组的评分不需要填写，建议那栏随便填即可，不会影响统分结果！<br />
			总分会自动统计，不需要填写！
		</div>
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
