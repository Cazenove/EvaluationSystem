<!-- 注册模态框，点击注册弹出 -->
<template>
	<div id="AdminAssistantCreateModal">
		<!-- 注册模态框 -->
		<div class="modal fade" id="createModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
		 aria-hidden="true">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title" id="createModalLabel">{{ modalTitle }}</h5>
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">
						<div class="container-fluid">
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<label for="assistantId" class="col-form-label">助教ID</label>
										<input type="text" class="form-control" id="assistantId1" v-model="registerInfo.assistantId" />
										<span class="error" v-if="errors['registerInfo.assistantId']">{{errors['registerInfo.assistantId']}}</span>
									</div>
									<div class="form-group">
										<label for="password" class="col-form-label">密码</label>
										<input type="password" class="form-control" id="password1" v-model="registerInfo.password" />
										<span class="error" v-if="errors['registerInfo.password']">{{errors['registerInfo.password']}}</span>
									</div>
									<div class="form-group">
										<label for="name" class="col-form-label">姓名</label>
										<input type="text" class="form-control" id="name1" v-model="registerInfo.name" />
										<span class="error" v-if="errors['registerInfo.name']">{{errors['registerInfo.name']}}</span>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label for="telephone" class="col-form-label">电话号码</label>
										<input type="text" class="form-control" id="telephone1" v-model="registerInfo.telephone" />
										<span class="error" v-if="errors['registerInfo.telephone']">{{errors['registerInfo.telephone']}}</span>
									</div>
									<div class="form-group">
										<label for="classId" class="col-form-label">管理的班级</label>
										<select class="form-control" v-model="registerInfo.classId">
											<option disabled="disabled" :value="null">请选择</option>
											<option v-for="(item, index) in classList" :value="item.classId" :key="item.classId">{{item.className}}</option>
										</select>
										<span class="error" v-if="errors['registerInfo.classId']">{{errors['registerInfo.classId']}}</span>
									</div>
								</div>
							</div>
						</div>
						
						
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary" data-dismiss="modal" @click="close">取消</button>
						<button type="button" class="btn btn-primary" data-dismiss="modal" @click="register">添加</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
	import axios from 'axios'
	import api from '../router/httpConfig.js'
	import Vue from 'vue'
	import Vuerify from 'vuerify'
	import UserNav from '../components/UserNav.vue'
	import $ from 'jquery'
	Vue.use(Vuerify)

	export default {
		inject: ['reload'],
		props: ['modalTitle'],
		data() {
			return {
				classList: [],
				registerInfo: { //注册的表单信息
					assistantId: null,
					password: null,
					name: null,
					telephone: null,
					classId: null
				},
				response: {
				},
				title: "",
			}
		},
		created() {
			//创建的时候获取班级小组列表
			this.getClassList();
		},

		vuerify: {
			'registerInfo.assistantId': {
				test: /^[0-9]{9,9}$/,
				message: '用户名为9位的学号'
			},
			'registerInfo.password': {
				test: /^[^]{6,16}$/,
				message: '密码长度为6-16'
			},
			'registerInfo.name': {
				test: function() {
					if (!this.registerInfo.name) {
						return false;
					} else {
						return true;
					}
				},
				message: '姓名为必填项'
			},
			'registerInfo.telephone': {
				test: /^[0-9]{11,11}$/,
				message: '电话号码为11位数字'
			},
			'registerInfo.classId': {
				test: function() {
					if (!this.registerInfo.classId) {
						return false;
					} else {
						return true;
					}
				},
				message: '班级为必选项'
			},
		},
		methods: {
			showModal() {
				$('#createModal').modal('show');
			},
			getClassList() {
				var self = this;
				axios.get(api.adminClassList,null)
				.then(function(res) {
					self.classList = res.data.data;
				}).catch(function(error) {
					console.log(error);
				})
			},
			close() {},
			register() {
				//注册功能
				//先检验表单
				let verifyList = ['registerInfo.assistantId', 'registerInfo.password', 'registerInfo.name', 'registerInfo.telephone',
					'registerInfo.classId'
				];
				// check() 校验所有规则，参数可以设置需要校验的数组
				if (!this.$vuerify.check(verifyList)) {
					return;
				}
				console.log('验证通过');
				//然后发送表单
				var self = this;
				axios.post(api.adminAssistantCreate,this.registerInfo)
				.then(function(res) {
					alert(res.data.msg);
					self.reload();
				}).catch(function(error) {
					console.log(error);
				})
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
