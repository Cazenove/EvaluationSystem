<template>
	<div id="UserManagement">
		<ManageNav></ManageNav>
		<nav aria-label="breadcrumb">
			<ol class="breadcrumb">
				<li class="breadcrumb-item"><a href="#">主页</a></li>
				<li class="breadcrumb-item active" aria-current="page">用户管理</li>
			</ol>
		</nav>
		<h1 class="offset-md-1">用户管理</h1>
		
		<div class="form-row">
			
		<!-- <button type="button" class="offset-md-1 btn btn-primary" data-toggle="modal" data-target="#registerModal">添加用户</button> -->
		<AdminUserCreateModal class="offset-md-1"></AdminUserCreateModal>
		&nbsp;
		<button type="button" class="btn btn-light">一键导入</button>
		</div>
		<div class="offset-md-1" style="margin-top:40px;">
			<el-row :gutter="20">
				<el-col :span="3">
					<el-input offser="3" placeholder="学号" v-model="searchInfo.userId"></el-input>
				</el-col>
				<el-col :span="3">
					<el-input offser="3" placeholder="姓名" v-model="searchInfo.userName"></el-input>
				</el-col>
				<el-col :span="3">
					<el-input offser="3" placeholder="班级" v-model="searchInfo.classId"></el-input>
				</el-col>
				<el-col :span="3">
					<el-input offser="3" placeholder="小组" v-model="searchInfo.groupId"></el-input>
				</el-col>
				<el-col :span="3">
					<el-input offser="3" placeholder="职务" v-model="searchInfo.status"></el-input>
				</el-col>
				<button class="btn-primary btn" style="margin-left: 20px;" @click="search()">搜索</button>
			</el-row>
		</div>
		<div class="container col-md-10 offset-md-1" style="margin: 50px auto;">
			<vxe-table border
				resizable
				row-key
				highlight-hover-row
				keep-source
				ref="xTable"
				:data="tableData">
				<vxe-table-column field="userId" title="学号"></vxe-table-column>
				<vxe-table-column field="userName" title="姓名"></vxe-table-column>
				<vxe-table-column field="classId" title="班级" :formatter="formatterClass"></vxe-table-column>
				<vxe-table-column field="groupId" title="小组"></vxe-table-column>
				<vxe-table-column field="password" title="密码"></vxe-table-column>
				<vxe-table-column field="telephone" title="电话号码"></vxe-table-column>
				<vxe-table-column field="status" title="职务" :formatter="formatterStatus"></vxe-table-column>
				<vxe-table-column title="操作">
					<!-- <button type="button" class="btn btn-secondary"  data-toggle="modal" data-target="#UpdateModal">修改</button> -->
					<!-- &nbsp; -->
					<!-- <button type="button" class="btn btn-danger"  data-toggle="modal" data-target="#DeleteModal" >删除</button> -->
					<!-- <button type="button" class="btn btn-danger" @click="deleteUser()">删除</button> -->
					<template v-slot="{ row }">
						
							<button type="button" @click="editEvent(row)" class="btn btn-light"  data-toggle="modal" data-target="#UpdateModal">修改</button>
							&nbsp;
							<button type="button" @click="removeEvent(row)" class="btn btn-danger">删除</button>
					</template>
				</vxe-table-column>
			</vxe-table>
			
		</div>
		
		
		<!-- 模态框 -->
		<div class="modal fade" id="AddModal">
			<div class="modal-dialog">
				<div class="modal-content">
		
				<!-- 模态框头部 -->
				<div class="modal-header">
					<h4 class="modal-title">添加用户</h4>
					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>
		
				<!-- 模态框主体 -->
				<div class="modal-body">
					<div class="form-group">
						<label for="add-userId">学号</label>
						<input type="text" class="form-control" id="add-userId"/>
					</div>
					<div class="form-group">
						<label for="add-userName">姓名</label>
						<input type="text" class="form-control" id="add-userName"/>
					</div>
					<div class="form-group">
						<label for="add-classId">班级</label>
						<input type="text" class="form-control" id="add-classId"/>
					</div>
					<div class="form-group">
						<label for="add-groupId">小组</label>
						<input type="text" class="form-control" id="add-groupId"/>
					</div>
					<div class="form-group">
						<label for="add-status">职务</label>
						<input type="text" class="form-control" id="add-status"/>
					</div>
					<div class="form-group">
						<label for="add-password">密码</label>
						<input type="password" class="form-control" id="add-password"/>
					</div>
				</div>
				
				<!-- 模态框底部 -->
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-primary">确认添加</button>
				</div>
				</div>
			</div>
		</div>
		
		<div class="modal fade" id="UpdateModal">
			<div class="modal-dialog">
				<div class="modal-content">
		
				<!-- 模态框头部 -->
				<div class="modal-header">
					<h4 class="modal-title">修改用户信息</h4>
					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>
		
				<!-- 模态框主体 -->
				<div class="modal-body">
					<div class="form-group">
						<label for="update-userId">学号</label>
						<input type="text" class="form-control" id="update-userId" v-model="formData.userId"/>
						<span class="error" v-if="errors['formData.userId']">{{errors['formData.userId']}}</span>
					</div>
					<div class="form-group">
						<label for="update-userName">姓名</label>
						<input type="text" class="form-control" id="update-userName" v-model="formData.userName"/>
						<span class="error" v-if="errors['formData.userName']">{{errors['formData.userName']}}</span>
					</div>
					<div class="form-group">
						<label for="telephone" class="col-form-label">电话号码</label>
						<input type="text" class="form-control" v-model="formData.telephone" />
						<span class="error" v-if="errors['formData.telephone']">{{errors['formData.telephone']}}</span>
					</div>
					<div class="form-group">
						<label for="update-classId">班级</label>
						<select name="classId" class="form-control" v-model="formData.classId" @change="classOptionChange()">
							<option :value="item.classId" v-for="item in classList"> {{ item.name }} </option>
						</select>
					</div>
					<div class="form-group">
						<label for="update-groupId">小组</label>
						<select class="form-control" v-model="formData.groupId">
							<option v-for="n of formData.groupNum" :value="n" :key="n">第{{n}}小组</option>
						</select>
					</div>
					<div class="form-group">
						<label for="update-status">职务</label>
						<select name="status" class="form-control" v-model="formData.status">
							<option v-for="item in statusOption" :value="item.value">{{item.label}}</option>
						</select>
					</div>
					<div class="form-group">
						<label for="update-password">密码</label>
						<input type="password" class="form-control" id="update-password" v-model="formData.password"/>
						<span class="error" v-if="errors['formData.password']">{{errors['formData.password']}}</span>
					</div>
				</div>
				
				<!-- 模态框底部 -->
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-primary" @click="update()">确认修改</button>
				</div>
				</div>
			</div>
		</div>
		
		<div class="modal fade" id="DeleteModal">
			<div class="modal-dialog">
				<div class="modal-content">
		
				<!-- 模态框头部 -->
				<div class="modal-header">
					<h4 class="modal-title">删除</h4>
					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>
		
				<!-- 模态框主体 -->
				<div class="modal-body">
					确认删除该用户？
				</div>
				
				<!-- 模态框底部 -->
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-danger">确认删除</button>
				</div>
				</div>
			</div>
		</div>
		
	</div>
</template>

<script>
	import axios from 'axios'
	import api from '../../router/httpConfig.js'
	import ManageNav from '../../components/ManageNav.vue'
	import AdminUserCreateModal from '../../components/AdminUserCreateModal.vue'
	import RegisterModal from '../../components/RegisterModal.vue'
	import Vue from 'vue'
	import Vuerify from 'vuerify'
	export default {
		name: "UserManagement",
		components: {
			ManageNav,
			AdminUserCreateModal,
		},
		data () {
			return {
				submitLoading: false,
				showEdit: false,
				selectRow: null,
				allAlign: null,
				response: {
					status:1,
					selectRow: null,
					showEdit: false,
				    data:[
				        {
				            userId:"221701501",//学号
				            password:"123456",//密码
				            userName:"张三",//姓名
				            classId:1,//班级
				            groupId:1,//小组
				            status:1,//职务
							telephone:13200000000,//电话号码
				        },
				        {
				            userId:"221701502",//学号
				            password:"123456",//密码
				            userName:"李四",//姓名
				            classId:2,//班级
				            groupId:1,//小组
				            status:2,//职务
							telephone:13200000000,//电话号码
				        }
				    ]
				},
				tableData: [
				],
				searchInfo: {
					userId: null,
					userName: null,
					classId: null,
					groupId: null,
					status: null,
				},
				formData: {
					userId: null,
					password: null,
					telephone: null,
					userName: null,
					classId: null,
					groupId: null,
					status: null,
					groupNum: null,
				},
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
				classOption: [],
				teamOption: [],
				statusOption: [
					{
						label: "组员",
						value: 1,
					},
					{
						label: "组长",
						value: 2,
					},
					{
						label: "助教",
						value: 3,
					},
				],
			}
		},
		created() {
			this.init();
			this.getTeamList();
			this.getClassOption();
			this.tableData = this.response.data;
		},
		methods: {
			init() {
				this.getResponse();
			},
			getResponse() {
				var self = this;
				axios.get(api.adminUserList,null)
				.then(function(res) {
					console.log(res);
					if(res.data.status == 1) {
						self.response = res.data;
					}
					else {
						console.log(res.msg);
					}
				}).catch(function(error) {
					console.log(error);
				})
			},
			formatterClass({ cellValue }) {
				let item = this.classOption.find(item => item.value === cellValue)
				return item ? item.label : ''
			},
			formatterStatus({ cellValue }) {
				let item = this.statusOption.find(item => item.value === cellValue)
				return item ? item.label : ''
			},
			search() {
				this.init();
				this.tableData = [];
				for(let value of this.response.data){
					let flag = 1;
					
					if (value.userId != this.searchInfo.userId && this.searchInfo.userId != null && this.searchInfo.userId != ""){
						flag = 0;
					}
					if (value.userName != this.searchInfo.userName && this.searchInfo.userName != null && this.searchInfo.userName != ""){
						flag = 0;
					}
					if (value.classId != this.searchInfo.classId && this.searchInfo.classId != null && this.searchInfo.classId != ""){
						flag = 0;
					}
					if (value.groupId != this.searchInfo.groupId && this.searchInfo.groupId != null && this.searchInfo.groupId != ""){
						flag = 0;
					}
					if (value.status != this.searchInfo.status && this.searchInfo.status != null && this.searchInfo.status != ""){
						flag = 0;
					}
					if (flag == 1){
						this.tableData.push(value);
					}
				}
				
			},
			editEvent (row) {
				// this.$refs.xTable.setActiveRow(row)
				this.formData = {
					userId: row.userId,
					password: row.password,
					telephone: row.telephone,
					userName: row.userName,
					classId: row.classId,
					groupId: row.groupId,
					status: row.status,
					groupNum: null,
				}
				for (let value of this.classList) {
					if (this.formData.classId == value.classId){
						this.formData.groupNum = value.groupNum;
					}
				}
				console.log(this.formData)
				// this.selectRow = row;
				// this.showEdit = true;
			},
			removeEvent (row) {
				var deleteInfo = {
					userId : row.userId,
				}
				
					this.$XModal.confirm('您确定要删除该数据?').then(type => {
						if (type === 'confirm') {
						this.$refs.xTable.remove(row)
					}
				})
				
				axios.post(api.adminUserDelete,deleteInfo)
				.then(function(res){
					alert(res.msg);
				}).catch(function(error){
					console.log(error);
				})
			},
			getTeamList() {
				var self = this;
				axios.get(api.adminClassList,null)
				.then(function(res) {
					self.classList = res.data;
				}).catch(function(error) {
					console.log(error);
				})
			},
			getClassOption() {
				for (let value of this.classList) {
					var option = {
						label: value.name,
						value: value.classId,
					}
					this.classOption.push(option);
				}
			},
			// getTeamOption({ data }) {
			// 	this.teamOption = []
			// 	for (let value of this.classList) {
			// 		console.log(data.classId)
			// 		if (data.classId == value.classId) {
			// 			for (var i=1; i<=value.groupNum; i++) {
			// 				var option = {
			// 					label : i.toString(),
			// 					value : i.toString(),
			// 				}
			// 				this.teamOption.push(option);
			// 			}
			// 		}
			// 	}
			// },
			classOptionChange() {
				for (let value of this.classList) {
					if (this.formData.classId == value.classId){
						this.formData.groupNum = value.groupNum;
					}
				}
			},
			update() {
				console.log(this.formData);
				//注册功能
				//先检验表单
				let verifyList = ['formData.userId', 'formData.password', 'formData.userName', 'formData.telephone',
					'formData.classId', 'formData.groupNum', 'formData.status'
				];
				// check() 校验所有规则，参数可以设置需要校验的数组
				if (!this.$vuerify.check(verifyList)) {
					return;
				}
				console.log('验证通过');
				//然后发送表单
				let self = this;
				axios.post(api.adminUserUpdate,self.formData)
				.then(function(res) {
					alert(res.msg);
				}).catch(function(error) {
					console.log(error);
				})
				this.init();
			},
		},
		vuerify: {
			'formData.userId': {
				test: /^[0-9]{9,9}$/,
				message: '用户名为9位的学号'
			},
			'formData.password': {
				test: /^[^]{6,16}$/,
				message: '密码长度为6-16'
			},
			'formData.userName': {
				test: function() {
					if (!this.formData.userName) {
						return false;
					} else {
						return true;
					}
				},
				message: '姓名为必填项'
			},
			'formData.telephone': {
				test: /^[0-9]{11,11}$/,
				message: '电话号码为11位数字'
			},
			'formData.classId': {
				test: function() {
					if (!this.formData.classId) {
						return false;
					} else {
						return true;
					}
				},
				message: '班级为必选项'
			},
			'formData.groupNum': {
				test: function() {
					if (!this.formData.groupNum) {
						return false;
					} else {
						return true;
					}
				},
				message: '小组为必选项'
			},
			'formData.status': {
				test: function() {
					if (!this.formData.status) {
						return false;
					} else {
						return true;
					}
				},
				message: '身份为必选项'
			}
		},
		computed: {
			errors() {
				return this.$vuerify.$errors
			},
		}
	}
</script>

<style>
</style>
