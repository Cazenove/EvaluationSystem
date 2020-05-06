import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
	state:sessionStorage.getItem('state') ? JSON.parse(sessionStorage.getItem('state')): {
		loginState:'',
		isLogin: false,
		userInfo: {// 登录时，将个人信息存储
			userId:'',//学号
			password:'',//密码
			userName:'',//姓名
			telephone:'',//电话
			classId:'',//班级
			groupId:'',//小组
			status:''//身份，组员1，组长2
		},
		adminInfo: {
			assistantId:'',
			classId:'',
			name:'',
			password:'',
			telephone:'',
			status:''//身份，助教3，老师4
		}
	},
	// const store = new Vuex.Store({
	//     state:sessionStorage.getItem('state') ? JSON.parse(sessionStorage.getItem('state')): {
	// 		loginState:'',
	// 		isLogin: false,
	// 		userInfo: {// 登录时，将个人信息存储
	// 			userId:'',//学号
	// 			password:'',//密码
	// 			userName:'',//姓名
	// 			telephone:'',//电话
	// 			classId:'',//班级
	// 			groupId:'',//小组
	// 			status:''//身份，组员1，组长2
	// 		},
	// 		adminInfo: {
	// 			assistantId:'',
	// 			classId:'',
	// 			name:'',
	// 			password:'',
	// 			telephone:'',
	// 			status:''//身份，助教3，老师4
	// 		}
	// 	}
	// }),
	mutations: {},
	getters: {},
	actions: {},
	modules: {}
})
