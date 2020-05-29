import Vue from 'vue'
import api from './router/httpConfig.js'
import App from './App.vue'
import router from './router'
import store from './store'
import $ from 'jquery'
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap/dist/js/bootstrap.min'
import 'bootstrap-table/dist/bootstrap-table.min.css'
import 'bootstrap-table/dist/bootstrap-table.min.js'
import 'bootstrap-table/dist/locale/bootstrap-table-zh-CN.min.js'
import axios from 'axios'
import VueAxios from 'vue-axios'
import 'xe-utils'
import VXETable from 'vxe-table'
import 'vxe-table/lib/index.css'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import Vuerify from 'vuerify'
import echarts from 'echarts'
import md5 from 'js-md5'
Vue.use(VXETable)
Vue.config.productionTip = false
Vue.use(VueAxios, axios)
Vue.use(ElementUI)
Vue.prototype.$echarts = echarts

// 路由守卫
router.beforeEach((to, from, next) => {
	//用户可登录页面
	const userList = ['/home', '/team', '/member', '/outer', '/inner', '/account', '/changepassword']
	//管理员可登录页面
	const adminList = ['/admin/user', '/admin/class', '/admin/assistant', '/admin/team', '/admin/form', '/admin/evaluationouter', '/admin/evaluationinner', '/admin/suggestion', '/admin/teamscore', '/admin/personscore', '/admin/teamfinalscore', '/admin/personfinalscore', '/admin/home']
	//路由中设置的needLogin字段就在to当中
	if (window.sessionStorage.getItem('loginState')) {
		var loginState = window.sessionStorage.getItem('loginState');
		if(loginState == 1 || loginState == 2) {
			if(to.path == '/' || to.path == '/login') {
				next({path: '/home'});
			} else if(userList.indexOf(to.path) !== -1) {
				//进入用户可访问的页面
				next();
			} else {
				next({path: '/home'});
			}
		} else if(loginState == 3 || loginState == 4) {
			if(to.path == '/' || to.path == '/login') {
				next({path: '/admin/user'});
			} else if(adminList.indexOf(to.path) !== -1) {
				//进入管理员可访问的页面
				next();
			} else {
				next({path: '/admin/user'});
			}
		} else {
			console.log(store.state.loginState);
		}
	} else {
		// 如果没有session ,访问任何页面。都会进入到 登录页
		if (to.path === '/') { // 如果是登录页面的话，直接next() -->解决注销后的循环执行bug
			next();
		} else { // 否则 跳转到登录页面
			next({path: '/'});
		}
	}
})

new Vue({
	router,
	store,
	render: h => h(App),
}).$mount('#app')

Vue.prototype.$md5 = md5