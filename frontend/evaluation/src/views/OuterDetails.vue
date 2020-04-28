<!-- 一份组间评分表的内容 -->
<template>
	<div id="OuterDetails">
		<UserNav />
		<OuterEditableTable />
	</div>
</template>

<script>
	import UserNav from '../components/UserNav.vue'
	import OuterEditableTable from '../components/OuterEditableTable.vue'
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
			OuterEditableTable
		}
	}
</script>

<style>
</style>
