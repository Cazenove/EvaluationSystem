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

Vue.use(VXETable)
Vue.config.productionTip = false
Vue.use(VueAxios, axios)
Vue.use(ElementUI)

//路由守卫
router.beforeEach((to, from, next) => {
	//路由中设置的needLogin字段就在to当中 
	if (window.sessionStorage.data) {
		console.log(to.path) //每次跳转的路径
		if(to.path === '/login') {
			next({path: '/home'});
		} else {
			next();
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
