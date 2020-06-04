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
	},
	{
		// 用户管理
		path: '/admin/user',
		name: 'UserManagement',
		component: () => import('../views/admin/UserManagement.vue'),
		meta: {
			requireLogin: true
		}
	},
	{
		// 班级管理
		path: '/admin/class',
		name: 'ClassManagement',
		component: () => import('../views/admin/ClassManagement.vue'),
		meta: {
			requireLogin: true
		}
	},
	{
		// 助教管理
		path: '/admin/assistant',
		name: 'AssistantManagement',
		component: () => import('../views/admin/AssistantManagement.vue'),
		meta: {
			requireLogin: true
		}
	},
	{
		// 小组信息
		path: '/admin/team',
		name: 'TeamManagement',
		component: () => import('../views/admin/TeamManagement.vue'),
		meta: {
			requireLogin: true
		}
	},
	{
		// 评分表管理
		path: '/admin/form',
		name: 'FormManagement',
		component: () => import('../views/admin/FormManagement.vue'),
		meta: {
			requireLogin: true
		}
	},
	{
		// 组间评分表提交记录
		path: '/admin/evaluationouter',
		name: 'EvaluationOuter',
		component: () => import('../views/admin/EvaluationOuter.vue'),
		meta: {
			requireLogin: true
		}
	},
	{
		// 组内评分表提交记录
		path: '/admin/evaluationinner',
		name: 'EvaluationInner',
		component: () => import('../views/admin/EvaluationInner.vue'),
		meta: {
			requireLogin: true
		}
	},
	{
		// 组内评分表提交记录
		path: '/admin/suggestion',
		name: 'SuggestionManagement',
		component: () => import('../views/admin/SuggestionManagement.vue'),
		meta: {
			requireLogin: true
		}
	},
	{
		// 小组历次评分
		path: '/admin/teamscore',
		name: 'TeamScore',
		component: () => import('../views/admin/TeamScore.vue'),
		meta: {
			requireLogin: true
		}
	},
	{
		// 个人历次评分
		path: '/admin/personscore',
		name: 'PersonScore',
		component: () => import('../views/admin/PersonScore.vue'),
		meta: {
			requireLogin: true
		}
	},
	{
		// 小组综合评分
		path: '/admin/teamfinalscore',
		name: 'TeamFinalScore',
		component: () => import('../views/admin/TeamFinalScore.vue'),
		meta: {
			requireLogin: true
		}
	},
	{
		// 个人综合评分
		path: '/admin/personfinalscore',
		name: 'PersonFinalScore',
		component: () => import('../views/admin/PersonFinalScore.vue'),
		meta: {
			requireLogin: true
		}
	},
	{
		path:'/admin/home',
		name: 'AdminHome',
		component: () => import('../views/admin/AdminHome'),
		meta: {
			requireLogin: true
		}
	},
	{
		// 个人详情信息
		path: '/admin/user/details',
		name: 'UserDetails',
		component: () => import('../views/admin/UserDetails.vue'),
		meta: {
			requireLogin: true
		}
	},
	{
		// 小组详情信息
		path: '/admin/team/details',
		name: 'TeamDetails',
		component: () => import('../views/admin/TeamDetails.vue'),
		meta: {
			requireLogin: true
		}
	},
]

const router = new VueRouter({
	routes
})

export default router
