import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'

Vue.use(VueRouter)

const routes = [{
		path: '/home',
		name: 'Home',
		component: Home,
		meta: {
			needLogin: true
		}
	},
	{
		path: '/about',
		name: 'About',
		// route level code-splitting
		// this generates a separate chunk (about.[hash].js) for this route
		// which is lazy-loaded when the route is visited.
		component: () => import( /* webpackChunkName: "about" */ '../views/About.vue'),
		meta: {
			needLogin: true
		}
	},
	{
		path: '/outer',
		name: 'Outer',
		component: () => import('../views/OuterDetails.vue'),
		meta: {
			needLogin: true
		}
	},
	{
		path: '/inner',
		name: 'Inner',
		component: () => import('../views/InnerDetails.vue'),
		meta: {
			needLogin: true
		}
	},
	{
		path: '*',
		name: 'Login',
		component: () => import('../views/Login.vue'),
		meta: {
			needLogin: false
		}
	}
]

const router = new VueRouter({
	routes
})

export default router
