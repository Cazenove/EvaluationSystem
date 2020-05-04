<!-- 注册模态框，点击注册弹出 -->
<template>
	<div id="AdminUserCreateModal">
		<button class="btn btn-primary" data-toggle="modal" data-target="#adminUserCreateModal">添加用户</button>
		<!-- 注册模态框 -->
		<div class="modal fade" id="adminUserCreateModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
		 aria-hidden="true">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title" id="adminUserCreateModalLabel">添加用户</h5>
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">
						<div class="form-group">
							<label for="userId" class="col-form-label">学号</label>
							<input type="text" class="form-control" id="userId" v-model="registerInfo.userId" />
							<span class="error" v-if="errors['registerInfo.userId']">{{errors['registerInfo.userId']}}</span>
						</div>
						<div class="form-group">
							<label for="password" class="col-form-label">密码</label>
							<input type="password" class="form-control" id="password" v-model="registerInfo.password" />
							<span class="error" v-if="errors['registerInfo.password']">{{errors['registerInfo.password']}}</span>
						</div>
						<div class="form-group">
							<label for="userName" class="col-form-label">姓名</label>
							<input type="text" class="form-control" id="userName" v-model="registerInfo.userName" />
							<span class="error" v-if="errors['registerInfo.userName']">{{errors['registerInfo.userName']}}</span>
						</div>
						<div class="form-group">
							<label for="telephone" class="col-form-label">电话号码</label>
							<input type="text" class="form-control" id="telephone" v-model="registerInfo.telephone" />
							<span class="error" v-if="errors['registerInfo.telephone']">{{errors['registerInfo.telephone']}}</span>
						</div>
						<div class="form-group">
							<label for="classId" class="col-form-label">班级</label>
							<select class="form-control" v-model="registerInfo.classId" @change="registerInfo.groupNum=null">
								<option disabled="disabled" :value="null">请选择</option>
								<option v-for="(item, index) in classList" :value="item.classId" :key="item.classId">{{item.name}}</option>
							</select>
							<span class="error" v-if="errors['registerInfo.classId']">{{errors['registerInfo.classId']}}</span>
						</div>
						<div class="form-group">
							<label for="groupNum" class="col-form-label">小组</label>
							<select class="form-control" v-model="registerInfo.groupNum">
								<option disabled="disabled" :value="null">请选择</option>
								<option v-for="n in getGroupNum" :value="n" :key="n">第{{n}}小组</option>
							</select>
							<span class="error" v-if="errors['registerInfo.groupId']">{{errors['registerInfo.groupId']}}</span>
						</div>
						<div class="form-group">
							<label for="status" class="col-form-label">身份</label>
							<select class="form-control" v-model="registerInfo.status">
								<option disabled="disabled" :value="null">请选择</option>
								<option :value="1">组员</option>
								<option :value="2">组长</option>
							</select>
							<span class="error" v-if="errors['registerInfo.status']">{{errors['registerInfo.status']}}</span>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary" data-dismiss="modal" @click="close">取消</button>
						<button type="button" class="btn btn-primary" @click="register">添加用户</button>
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
	Vue.use(Vuerify)

	export default {
		data() {
			return {
				classList: [{
						classId: 1, //班级id
						name: "2020软件工程S班", //班级名
						groupNum: 10 //这个班级的小组数量
					},
					{
						classId: 2, //班级id
						name: "2020软件工程W班", //班级名
						groupNum: 11 //这个班级的小组数量
					}
				],
				registerInfo: { //注册的表单信息
					userId: null,
					password: null,
					userName: null,
					telephone: null,
					classId: null,
					groupNum: null,
					status: null
				},
				response: {
					
				}
			}
		},
		created() {
			//创建的时候获取班级小组列表
			this.getTeamList();
		},

		vuerify: {
			'registerInfo.userId': {
				test: /^[0-9]{9,9}$/,
				message: '用户名为9位的学号'
			},
			'registerInfo.password': {
				test: /^[^]{6,16}$/,
				message: '密码长度为6-16'
			},
			'registerInfo.userName': {
				test: function() {
					if (!this.registerInfo.userName) {
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
			'registerInfo.groupNum': {
				test: function() {
					if (!this.registerInfo.groupNum) {
						return false;
					} else {
						return true;
					}
				},
				message: '小组为必选项'
			},
			'registerInfo.status': {
				test: function() {
					if (!this.registerInfo.status) {
						return false;
					} else {
						return true;
					}
				},
				message: '身份为必选项'
			}
		},
		methods: {
			getTeamList() {
				var self = this;
				axios.get(api.adminClassList,null)
				.then(function(res) {
					self.classList = res.data;
				}).catch(function(error) {
					console.log(error);
				})
			},
			close() {},
			register() {
				console.log(this.registerInfo);
				//注册功能
				//先检验表单
				let verifyList = ['registerInfo.userId', 'registerInfo.password', 'registerInfo.userName', 'registerInfo.telephone',
					'registerInfo.classId', 'registerInfo.groupNum', 'registerInfo.status'
				];
				// check() 校验所有规则，参数可以设置需要校验的数组
				if (!this.$vuerify.check(verifyList)) {
					return;
				}
				console.log('验证通过');
				//然后发送表单
				let self = this;
				axios.post(api.adminUserCreate,self.registerInfo)
				.then(function(res) {
					alert(res.msg);
				}).catch(function(error) {
					console.log(error);
				})
			}
		},
		computed: {
			errors() {
				return this.$vuerify.$errors
			},
			getGroupNum: function() {
				return this.$data.registerInfo.classId !== null ? this.$data.classList[this.$data.registerInfo.classId - 1].groupNum :
					null;
			}
		}
	}
</script>

<style>
</style>
