<template>
	<div id="ChangePassword">
		<UserNav />
		<br />
		<el-form ref="form" :model="form" label-width="80px">
			<el-form-item label="原密码">
				<el-input type="password" v-model="form.password"></el-input>
				<span class="error" v-if="errors['form.password']">{{errors['form.password']}}</span>
			</el-form-item>
			<el-form-item label="新密码">
				<el-input type="password" v-model="form.newPassword"></el-input>
				<span class="error" v-if="errors['form.newPassword']">{{errors['form.newPassword']}}</span>
			</el-form-item>
			<el-form-item label="确认密码">
				<el-input type="password" v-model="form.checkPassword"></el-input>
				<span class="error" v-if="errors['form.checkPassword']">{{errors['form.checkPassword']}}</span>
			</el-form-item>
			<el-form-item>
				<el-button type="primary" @click="onSubmit">修改密码</el-button>
			</el-form-item>
		</el-form>
	</div>
</template>
<script>
	// 引入插件
	import Vue from 'vue'
	import Vuerify from 'vuerify'
	import UserNav from '../components/UserNav.vue'
	Vue.use(Vuerify)
	
	export default {
		components: {
			UserNav
		},
		data() {
			return {
				form: {
					password: '',
					newPassword: '',
					checkPassword: ''
				},
				request: {
					userId:null,//只能是9位
					password:null,//6-16位
					telephone:null,//11位
					userName:null,//姓名
				},
				response: {
					status:1,
					msg:"修改成功"
				}
			}
		},
		// 设置校验规则
		vuerify: {
			'form.password': {
				test: function() {
					if(this.form.password !== this.$store.state.userInfo.password) {
						return false
					} else {
						return true
					}
				},
				message: '密码错误'
			},
			'form.newPassword': {
				test: /^[^]{6,16}$/,
				message: '密码位数为6-16'
			},
			'form.checkPassword': {
				test: function() {
					if(this.form.checkPassword !== this.form.newPassword) {
						return false
					} else {
						return true
					}
				},
				message: '两次密码必须一致'
			}
		},
		computed: {
			errors() {
				return this.$vuerify.$errors
			}
		},
		methods: {
			onSubmit() {
				let verifyList = ['form.password', 'form.newPassword', 'form.checkPassword','noEqual'];
				// check() 校验所有规则，参数可以设置需要校验的数组
				if (!this.$vuerify.check(verifyList)) {
					return;
				}
				console.log('验证通过');
			}
		}
	}
</script>
