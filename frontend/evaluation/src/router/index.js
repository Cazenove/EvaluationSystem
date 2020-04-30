import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'

Vue.use(VueRouter)

const routes = [{
		path: '/home',
		name: 'Home',
		component: Home,
		meta: {
			requireLogin: true
		}
	},
	{
		path: '/team',
		name: 'Team',
		component: () => import('../views/Team.vue'),
		meta: {
			requireLogin: true
		}
	},
	{
		path: '/member',
		name: 'Member',
		component: () => import('../views/MemberDetails.vue'),
		meta: {
			requireLogin: true
		}
	},
	{
		path: '/outer',
		name: 'Outer',
		component: () => import('../views/OuterDetails.vue'),
		meta: {
			requireLogin: true
		}
	},
	{
		path: '/inner',
		name: 'Inner',
		component: () => import('../views/InnerDetails.vue'),
		meta: {
			requireLogin: true
		}
	},
	{
		path: '/account',
		name: 'Account',
		component: () => import('../views/Account.vue'),
		meta: {
			requireLogin: true
		}
	},
	{
		path: '/changepassword',
		name: 'ChangePassword',
		component: () => import('../views/ChangePassword.vue'),
		meta: {
			requireLogin: true
		}
	},
	{
		path: '*',
		name: 'Login',
		component: () => import('../views/Login.vue'),
		meta: {
			requireLogin: false
		}
	}
	
]

const router = new VueRouter({
	routes
})

export default router
