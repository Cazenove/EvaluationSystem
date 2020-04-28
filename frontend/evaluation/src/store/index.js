import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
	state: {
		url: "",
		isLogin: false,
		loginState: 0,
		count: 1
	},
	mutations: {},
	getters: {},
	actions: {},
	modules: {}
})
