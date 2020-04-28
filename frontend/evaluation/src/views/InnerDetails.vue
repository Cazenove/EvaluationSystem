<!-- 一份组内评分表的内容 -->
<template>
	<div id="InnerDetails">
		<UserNav />
		<InnerEditableTable />
	</div>
</template>

<script>
	import UserNav from '../components/UserNav.vue'
	import InnerEditableTable from '../components/InnerEditableTable.vue'
	export default {
		data() {
			return {
				id: ""
			}
		},
		created() {
			this.id = this.$route.query.id;
		},
		components:{
			UserNav,
			InnerEditableTable
		}
	}
</script>

<style>
</style>
