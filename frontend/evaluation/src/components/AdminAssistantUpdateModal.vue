<!-- 注册模态框，点击注册弹出 -->
<template>
	<div id="AdminAssistantUpdateModal">
		<!-- 注册模态框 -->
		<div class="modal fade" id="updateModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
		 aria-hidden="true">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title" id="updateModalLabel">修改助教信息</h5>
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">
						<div class="form-group">
							<label for="assistantId" class="col-form-label">助教ID</label>
							<input type="text" class="form-control" v-model="updateInfo.assistantId" />
							<span class="error" v-if="errors['updateInfo.assistantId']">{{errors['updateInfo.assistantId']}}</span>
						</div>
						<div class="form-group">
							<label for="password" class="col-form-label">密码</label>
							<input type="password" class="form-control" v-model="updateInfo.password" />
							<span class="error" v-if="errors['updateInfo.password']">{{errors['updateInfo.password']}}</span>
						</div>
						<div class="form-group">
							<label for="name" class="col-form-label">姓名</label>
							<input type="text" class="form-control" v-model="updateInfo.name" />
							<span class="error" v-if="errors['updateInfo.name']">{{errors['updateInfo.name']}}</span>
						</div>
						<div class="form-group">
							<label for="telephone" class="col-form-label">电话号码</label>
							<input type="text" class="form-control" v-model="updateInfo.telephone" />
							<span class="error" v-if="errors['updateInfo.telephone']">{{errors['updateInfo.telephone']}}</span>
						</div>
						<div class="form-group">
							<label for="classId" class="col-form-label">管理的班级</label>
							<select class="form-control" v-model="updateInfo.classId" @change="updateInfo.groupNum=null">
								<option disabled="disabled" :value="null">请选择</option>
								<option v-for="(item, index) in classList" :value="item.classId" :key="item.classId">{{item.className}}</option>
							</select>
							<span class="error" v-if="errors['updateInfo.classId']">{{errors['updateInfo.classId']}}</span>
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
		props: ['registerInfo'],
		data() {
			return {
				classList: [],
				updateInfo: { //注册的表单信息
					assistantId: null,
					password: null,
					name: null,
					telephone: null,
					classId: null
				},
				response: {
				},
			}
		},
		created() {
			//创建的时候获取班级小组列表
			this.getClassList();
		},

		vuerify: {
			'updateInfo.assistantId': {
				test: /^[0-9]{9,9}$/,
				message: '用户名为9位的学号'
			},
			'updateInfo.password': {
				test: /^[^]{6,16}$/,
				message: '密码长度为6-16'
			},
			'updateInfo.name': {
				test: function() {
					if (!this.updateInfo.name) {
						return false;
					} else {
						return true;
					}
				},
				message: '姓名为必填项'
			},
			'updateInfo.telephone': {
				test: /^[0-9]{11,11}$/,
				message: '电话号码为11位数字'
			},
			'updateInfo.classId': {
				test: function() {
					if (!this.updateInfo.classId) {
						return false;
					} else {
						return true;
					}
				},
				message: '班级为必选项'
			},
		},
		watch: {
			registerInfo(value,oldValue){
				this.updateInfo = this.registerInfo;
			}
		},
		methods: {
			getClassList() {
				var self = this;
				axios.get(api.adminClassList,null)
				.then(function(res) {
					self.classList = res.data.data;
				}).catch(function(error) {
					console.log(error);
				})
			},
			showModal(row) {
				console.log(row);
				this.updateInfo = row;
				$('#updateModal').modal('show');
			},
			close() {},
			register() {
				//注册功能
				//先检验表单
				let verifyList = ['updateInfo.assistantId', 'updateInfo.password', 'updateInfo.name', 'updateInfo.telephone',
					'updateInfo.classId'
				];
				// check() 校验所有规则，参数可以设置需要校验的数组
				if (!this.$vuerify.check(verifyList)) {
					return;
				}
				console.log('验证通过');
				//然后发送表单
				var self = this;
				axios.post(api.adminAssistantUpdate,this.updateInfo)
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
				return this.$data.updateInfo.classId !== null ? this.$data.classList[this.$data.updateInfo.classId - 1].groupNum :
					null;
			}
		}
	}
</script>

<style>
</style>
