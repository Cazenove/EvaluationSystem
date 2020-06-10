import Vue from 'vue'
import VueRouter from 'vue-router'
import { resolve } from 'path';

Vue.use(VueRouter)

const routes = [{
		path: '/home',
		name: 'Home',
		component: resolve => require(['../views/Home.vue'],resolve),
		meta: {
			requireLogin: true
		}
	},
	{
		path: '/team',
		name: 'Team',
		component: resolve => require(['../views/Team.vue'],resolve),
		meta: {
			requireLogin: true
		}
	},
	{
		path: '/member',
		name: 'Member',
		component: resolve => require(['../views/MemberDetails.vue'],resolve),
		meta: {
			requireLogin: true
		}
	},
	{
		path: '/outer',
		name: 'Outer',
		component: resolve => require(['../views/OuterDetails.vue'],resolve),
		meta: {
			requireLogin: true
		}
	},
	{
		path: '/inner',
		name: 'Inner',
		component: resolve => require(['../views/InnerDetails.vue'],resolve),
		meta: {
			requireLogin: true
		}
	},
	{
		path: '/account',
		name: 'Account',
		component: resolve => require(['../views/Account.vue'],resolve),
		meta: {
			requireLogin: true
		}
	},
	{
		path: '/changepassword',
		name: 'ChangePassword',
		component: resolve => require(['../views/ChangePassword.vue'],resolve),
		meta: {
			requireLogin: true
		}
	},
	{
		path: '*',
		name: 'Login',
		component: resolve => require(['../views/Login.vue'],resolve),
		meta: {
			requireLogin: false
		}
	},
	{
		// 用户管理
		path: '/admin/user',
		name: 'UserManagement',
		component: resolve => require(['../views/admin/UserManagement.vue'],resolve),
		meta: {
			requireLogin: true
		}
	},
	{
		// 班级管理
		path: '/admin/class',
		name: 'ClassManagement',
		component: resolve => require(['../views/admin/ClassManagement.vue'],resolve),
		meta: {
			requireLogin: true
		}
	},
	{
		// 助教管理
		path: '/admin/assistant',
		name: 'AssistantManagement',
		component: resolve => require(['../views/admin/AssistantManagement.vue'],resolve),
		meta: {
			requireLogin: true
		}
	},
	{
		// 小组信息
		path: '/admin/team',
		name: 'TeamManagement',
		component: resolve => require(['../views/admin/TeamManagement.vue'],resolve),
		meta: {
			requireLogin: true
		}
	},
	{
		// 评分表管理
		path: '/admin/form',
		name: 'FormManagement',
		component: resolve => require(['../views/admin/FormManagement.vue'],resolve),
		meta: {
			requireLogin: true
		}
	},
	{
		// 组间评分表提交记录
		path: '/admin/evaluationouter',
		name: 'EvaluationOuter',
		component: resolve => require(['../views/admin/EvaluationOuter.vue'],resolve),
		meta: {
			requireLogin: true
		}
	},
	{
		// 组内评分表提交记录
		path: '/admin/evaluationinner',
		name: 'EvaluationInner',
		component: resolve => require(['../views/admin/EvaluationInner.vue'],resolve),
		meta: {
			requireLogin: true
		}
	},
	{
		// 组内评分表提交记录
		path: '/admin/suggestion',
		name: 'SuggestionManagement',
		component: resolve => require(['../views/admin/SuggestionManagement.vue'],resolve),
		meta: {
			requireLogin: true
		}
	},
	{
		// 小组历次评分
		path: '/admin/teamscore',
		name: 'TeamScore',
		component: resolve => require(['../views/admin/TeamScore.vue'],resolve),
		meta: {
			requireLogin: true
		}
	},
	{
		// 个人历次评分
		path: '/admin/personscore',
		name: 'PersonScore',
		component: resolve => require(['../views/admin/PersonScore.vue'],resolve),
		meta: {
			requireLogin: true
		}
	},
	{
		// 小组综合评分
		path: '/admin/teamfinalscore',
		name: 'TeamFinalScore',
		component: resolve => require(['../views/admin/TeamFinalScore.vue'],resolve),
		meta: {
			requireLogin: true
		}
	},
	{
		// 个人综合评分
		path: '/admin/personfinalscore',
		name: 'PersonFinalScore',
		component: resolve => require(['../views/admin/PersonFinalScore.vue'],resolve),
		meta: {
			requireLogin: true
		}
	},
	{
		path:'/admin/home',
		name: 'AdminHome',
		component: resolve => require(['../views/admin/AdminHome'],resolve),
		meta: {
			requireLogin: true
		}
	},
	{
		// 个人详情信息
		path: '/admin/user/details',
		name: 'UserDetails',
		component: resolve => require(['../views/admin/UserDetails.vue'],resolve),
		meta: {
			requireLogin: true
		}
	},
	{
		// 小组详情信息
		path: '/admin/team/details',
		name: 'TeamDetails',
		component: resolve => require(['../views/admin/TeamDetails.vue'],resolve),
		meta: {
			requireLogin: true
		}
	},
]

const router = new VueRouter({
	routes
})

export default router
