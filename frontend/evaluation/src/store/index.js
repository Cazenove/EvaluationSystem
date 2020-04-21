import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    isInLoginPage: false,
	loginState: 0,
    count: 1
  },
  mutations: {
    setIsInLoginPage(value){
		this.state.isInLoginPage = value;
	}
  },
  getters: {
  },
  actions: {
  },
  modules: {
  }
})
