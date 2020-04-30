<!-- 一份组内评分表的内容 -->
<template>
	<div id="InnerDetails">
		<UserNav />
		<br />
		<button class="btn btn-light" @click="goBack">返回列表</button>
		<InnerEditableTable v-bind:evaluationInnerId = "evaluationInnerId" />
	</div>
</template>

<script>
	import UserNav from '../components/UserNav.vue'
	import InnerEditableTable from '../components/InnerEditableTable.vue'
	export default {
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
