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
		components:{
			UserNav,
			OuterEditableTable
		}
	}
</script>

<style>
</style>
