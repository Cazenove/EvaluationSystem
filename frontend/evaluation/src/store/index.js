import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
	state: {
		isLogin: false,
		userInfo: {// 登录时，将个人信息存储
			userId:"221701000",//学号
			password:"123456",//密码
			userName:"张三",//姓名
			telephone:"13200000000",//电话
			classId:1,//班级
			groupId:1,//小组
			status:2//身份，组员1，组长2
		}
	},
	mutations: {},
	getters: {},
	actions: {},
	modules: {}
})
