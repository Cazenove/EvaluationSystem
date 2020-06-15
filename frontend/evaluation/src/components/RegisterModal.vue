<!-- 注册模态框，点击注册弹出 -->
<template>
	<div id="RegisterModal">
		<div>
			
			<div>
				<div class="mb-3">
					
					<input type="text" class="form-control" id="userId" v-model="registerInfo.userId" placeholder="学号"/>
					<span class="error" v-if="errors['registerInfo.userId']">{{errors['registerInfo.userId']}}</span>
				</div>
				<div class="mb-3">
					
					<input type="password" class="form-control" id="password" v-model="registerInfo.password" placeholder="密码"/>
					<span class="error" v-if="errors['registerInfo.password']">{{errors['registerInfo.password']}}</span>
				</div>
				<div class="mb-3">
					
					<input type="text" class="form-control" id="name" v-model="registerInfo.name" placeholder="姓名"/>
					<span class="error" v-if="errors['registerInfo.name']">{{errors['registerInfo.name']}}</span>
				</div>
				<div class="mb-3">
					
					<input type="text" class="form-control" id="telephone" v-model="registerInfo.telephone" placeholder="电话号码"/>
					<span class="error" v-if="errors['registerInfo.telephone']">{{errors['registerInfo.telephone']}}</span>
				</div>
				<div class="mb-3">
					
					<select class="form-control" v-model="registerInfo.classId" @change="registerInfo.groupNum=null">
						<option disabled="disabled" :value="null">请选择班级</option>
						<option v-for="(item, index) in classList" :value="item.classId" :key="item.classId">{{item.className}}</option>
					</select>
					<span class="error" v-if="errors['registerInfo.classId']">{{errors['registerInfo.classId']}}</span>
				</div>
				<div class="mb-3">
					
					<select class="form-control" v-model="registerInfo.groupNum">
						<option disabled="disabled" :value="null">请选择小组</option>
						<option v-for="n in getGroupNum" :value="n" :key="n">第{{n}}小组</option>
					</select>
					<span class="error" v-if="errors['registerInfo.groupId']">{{errors['registerInfo.groupId']}}</span>
				</div>
				<div >
					
					<select class="form-control" v-model="registerInfo.status">
						<option disabled="disabled" :value="null">请选择身份</option>
						<option :value="1">组员</option>
						<option :value="2">组长</option>
					</select>
					<span class="error" v-if="errors['registerInfo.status']">{{errors['registerInfo.status']}}</span>
				</div>
			</div>
			<!-- <div>
				<button type="button" class="btn btn-secondary" data-dismiss="modal" @click="close">取消</button>
				<button type="button" class="btn btn-primary" data-dismiss="modal" @click="register">注册</button>
			</div> -->
		</div>
		<!-- <button class="btn btn-primary" data-toggle="modal" data-target="#registerModal">注册</button> -->
		<!-- 注册模态框 -->
		<!-- <div class="modal fade" id="registerModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
		 aria-hidden="true">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title" id="registerModalLabel">注册</h5>
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
							<label for="name" class="col-form-label">姓名</label>
							<input type="text" class="form-control" id="name" v-model="registerInfo.name" />
							<span class="error" v-if="errors['registerInfo.name']">{{errors['registerInfo.name']}}</span>
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
								<option v-for="(item, index) in classList" :value="item.classId" :key="item.classId">{{item.className}}</option>
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
						<button type="button" class="btn btn-primary" data-dismiss="modal" @click="register">注册</button>
					</div>
				</div>
			</div>
		</div> -->
	</div>
</template>

<script>
	import axios from 'axios'
	import api from '../router/httpConfig.js'
	import Vue from 'vue'
	import Vuerify from 'vuerify'
	import $ from 'jquery'
	import UserNav from '../components/UserNav.vue'
	Vue.use(Vuerify)

	export default {
		inject: ['reload'],
		data() {
			return {
				classList: [],
				registerInfo: { //注册的表单信息
					userId: null,
					password: null,
					name: null,
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
			this.getClassList();
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
			showModal() {
				$('#registerModal').modal('show');
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
				let verifyList = ['registerInfo.userId', 'registerInfo.password', 'registerInfo.name', 'registerInfo.telephone',
					'registerInfo.classId', 'registerInfo.groupNum', 'registerInfo.status'
				];
				// check() 校验所有规则，参数可以设置需要校验的数组
				if (!this.$vuerify.check(verifyList)) {
					return;
				}
				console.log('验证通过');
				//然后发送表单
				var self = this;
				axios.post(api.register,this.registerInfo)
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
			},
			getGroupNum: function() {
				if(this.$data.registerInfo.classId === null) {
					return null;
				} else {
					for(var i=0;i<this.classList.length;i++) {
						if(this.classList[i].classId == this.registerInfo.classId) {
							return this.classList[i].groupNum;
						}
					}
				}
			}
		}
	}
</script>

<style>
	#RegisterModal span{
		color: #FF6666;
	}
</style>
