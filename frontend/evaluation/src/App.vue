<template>
	<div id="app">
		<!-- <router-view /> -->
		<router-view v-if="isReloadAlive"></router-view>
	</div>
</template>

<script>
	export default {
		provide() {
			return {
				reload: this.reload
			}
		},
		data() {
			return {
				isReloadAlive: true
			}
		},
		methods: {
			reload() {
				this.isReloadAlive = false;
				this.$nextTick(function() {
					this.isReloadAlive = true;
				})
			}
		},
		name: 'App',
		created() {
			//在页面加载时读取sessionStorage里的状态信息
			if (sessionStorage.getItem("store")) {
				this.$store.replaceState(Object.assign({}, this.$store.state, JSON.parse(sessionStorage.getItem("store"))))
			}

			//在页面刷新时将vuex里的信息保存到sessionStorage里
			window.addEventListener("beforeunload", () => {
				sessionStorage.setItem("store", JSON.stringify(this.$store.state))
			})
		}
	}
</script>

<style>
	body {
		background-image: url(assets/u=2062775821,393800254&fm=26&gp=0.jpg);
		background-size: cover;
		background-position: center;
	}
	#app {
		font-family: Avenir, Helvetica, Arial, sans-serif;
		-webkit-font-smoothing: antialiased;
		-moz-osx-font-smoothing: grayscale;
		color: #2c3e50;
	}
</style>
