<!-- 个人中心 -->
<template>
	<div id="Account">
		<UserNav />
		<br />
		<h1>个人中心</h1>
		<div>
			<p>姓名：{{this.$store.state.userInfo.userName}}</p>
			<p>学号：{{this.$store.state.userInfo.userId}}</p>
			<p>电话：{{this.$store.state.userInfo.telephone}}</p>
			<button type="button" class="btn btn-info" data-toggle="modal" data-target="#exampleModal">修改个人信息</button>
		</div>
		<!-- Modal -->
		<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title" id="exampleModalLabel">修改个人信息</h5>
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">
						<div class="form-group">
							<label for="userName" class="col-form-label">姓名</label>
							<input type="text" class="form-control" id="userName" v-model="request.userName" />
							<span class="error" v-if="errors['request.userName']">{{errors['request.userName']}}</span>
						</div>
						<div class="form-group">
							<label for="telephone" class="col-form-label">电话号码</label>
							<input type="text" class="form-control" id="telephone" v-model="request.telephone" />
							<span class="error" v-if="errors['request.telephone']">{{errors['request.telephone']}}</span>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
						<button type="button" class="btn btn-primary" @click="onSubmit">修改</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
	import UserNav from '../components/UserNav.vue'
	import Vue from 'vue'
	import Vuerify from 'vuerify'
	Vue.use(Vuerify)
	
	export default {
		components: {
			UserNav
		},
		data() {
			return {
				request: {
					userId: null,
					password: null,
					telephone: null,
					userName: null
				},
				response: {
					
				}
			}
		},
		vuerify: {
			'request.userName': {
				test: function() {
					if (!this.request.userName) {
						return false;
					} else {
						return true;
					}
				},
				message: '姓名为必填项'
			},
			'request.telephone': {
				test: /^[0-9]{11,11}$/,
				message: '电话号码为11位数字'
			}
		},
		created() {
			this.$data.request.userId = this.$store.state.userInfo.userId;
			this.$data.request.password = this.$store.state.userInfo.password;
			this.$data.request.telephone = this.$store.state.userInfo.telephone;
			this.$data.request.userName = this.$store.state.userInfo.userName;
		},
		methods: {
			onSubmit() {
				//修改个人信息
				let verifyList = ['request.userName', 'request.telephone'];
				// check() 校验所有规则，参数可以设置需要校验的数组
				if (!this.$vuerify.check(verifyList)) {
					return;
				}
				if (this.$data.request.telephone === this.$store.state.userInfo.telephone &&
					this.$data.request.userName === this.$store.state.userInfo.userName) {
					//信息没有变化
					alert("信息没有变化！");
					return;
				}
				console.log("验证通过");
			}
		},
		computed: {
			errors() {
				return this.$vuerify.$errors
			}
		}
	}
</script>

<style>
</style>
