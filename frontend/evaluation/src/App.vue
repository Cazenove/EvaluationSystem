<template>
	<div id="app">
		<UserNav v-if="!this.$store.state.isInLoginPage"/>
		<router-view />
	</div>
</template>

<script>
	import UserNav from './components/UserNav.vue'
	export default {
		components: {
			UserNav
		}
	}
</script>

<style>
	#app {
		font-family: Avenir, Helvetica, Arial, sans-serif;
		-webkit-font-smoothing: antialiased;
		-moz-osx-font-smoothing: grayscale;
		text-align: center;
		color: #2c3e50;
	}
</style>
