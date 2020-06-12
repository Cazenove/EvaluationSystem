<template>
	<div id="UserManagement">
		<ManageNav></ManageNav>
		<nav aria-label="breadcrumb">
			<ol class="breadcrumb">
				
				<li class="breadcrumb-item"><router-link to="/admin/home">主页</router-link></li>
				<li class="breadcrumb-item active" aria-current="page">用户管理</li>
			</ol>
		</nav>

		<div class="container col-md-10 offset-md-1" style="margin: 50px auto;">
			<h1>用户管理</h1>
			<div>
				<div class="row container" style="margin-bottom: 20px;">
					<div style="margin-right: 20px;">
						<AdminUserCreateModal></AdminUserCreateModal>
					</div>
					&nbsp;
					<UploadFile></UploadFile>
				</div>
			</div>
			<vxe-toolbar>
				<template v-slot:buttons>
					<vxe-input v-model="filterName" type="search" placeholder="快速搜索"></vxe-input>
					<vxe-button @click="exportSelectEvent">导出选中</vxe-button>
					
					<vxe-button v-if="isHide" @click="$refs.xTable.showColumn($refs.xTable.getColumnByField('password'));isHide = !isHide">显示密码</vxe-button>
					<vxe-button v-if="!isHide" @click="$refs.xTable.hideColumn($refs.xTable.getColumnByField('password'));isHide = !isHide">隐藏密码</vxe-button>
				</template>
				<template v-slot:tools>
					<vxe-button data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">条件搜索</vxe-button>
				</template>
			</vxe-toolbar>
			<div class="collapse" id="collapseExample" >
				<div class="card card-body" style="border: none;padding: 0;">
					<vxe-toolbar>
						<template v-slot:buttons>
							<!-- <div class="row">
								<vxe-input class="col-md-2 col-sm-2" placeholder="学号" v-model="searchInfo.userId"></vxe-input>
								<vxe-input class="col-md-2 col-sm-2" placeholder="姓名" v-model="searchInfo.name"></vxe-input>
								<vxe-select class="col-md-2 col-sm-2" placeholder="班级" v-model="searchInfo.classId" @change="classOptionChange(searchInfo)">
									<vxe-option :value="item.classId" v-for="item in classOption" :key="item.classId" :label="item.className"></vxe-option>
								</vxe-select>
								<vxe-select offser="3" placeholder="小组" v-model="searchInfo.groupId">
									<vxe-option v-for="n of searchInfo.groupNum" :value="n" :key="n">第{{n}}小组</vxe-option>
								</vxe-select>
							</div> -->
							<el-row :gutter="20">
								<el-col :span="3">
									<el-input offser="3" placeholder="学号" v-model="searchInfo.userId"></el-input>
								</el-col>
								<el-col :span="3">
									<el-input offser="3" placeholder="姓名" v-model="searchInfo.name"></el-input>
								</el-col>
								<el-col :span="3">
									<el-select offser="3" placeholder="班级" v-model="searchInfo.classId" @change="classOptionChange(searchInfo)">
										<el-option :value="item.classId" v-for="item in classOption" :key="item.classId" :label="item.className"></el-option>
									</el-select>
								</el-col>
								<el-col :span="3">
									<el-select offser="3" placeholder="小组" v-model="searchInfo.groupId">
										<!-- <el-option v-for="n of searchInfo.groupNum" :value="n" :key="n">第{{n}}小组</el-option> -->
										<el-option v-for="item in groupOfClass" :value="item.groupId" :key="item.groupId" :label="item.groupName"></el-option>
									</el-select>
								</el-col>
								<el-col :span="3">
									<el-select offser="3" placeholder="职务" v-model="searchInfo.status">
										<el-option v-for="item in statusOption" :key="item.value" :value="item.value" :label="item.label"></el-option>
									</el-select>
								</el-col>
								<vxe-button status="primary" style="margin-left: 20px;" @click="search()">搜索</vxe-button>
								<vxe-button style="margin-left: 20px;" @click="resetSearch()">重置搜索</vxe-button>
							</el-row>
						</template>
					</vxe-toolbar>
				</div>
			</div>

			<vxe-table
			v-if="classList"
			border
			resizable
			row-key
			highlight-hover-row
			keep-source
			max-height="600px"
			ref="xTable"
			:data="list">
				<vxe-table-column type="checkbox" width="60"></vxe-table-column>
				<vxe-table-column field="userId" title="学号" cell-type="string">
					<template v-slot="{ row }">
						<router-link :to="{path:'/admin/user/details', query:{userId: row.userId, classId: row.classId}}">
							{{row.userId}}
						</router-link>
					</template>
				</vxe-table-column>
				<vxe-table-column field="name" title="姓名"></vxe-table-column>
				<vxe-table-column field="classId" title="班级" :formatter="formatterClass"></vxe-table-column>
				<vxe-table-column field="groupId" title="小组">
					<template v-slot="{ row }">
						<router-link :to="{path:'/admin/team/details', query:{groupId: row.groupId, classId: row.classId}}">
							{{toGroupName(row.groupId)}}
						</router-link>
					</template>
				</vxe-table-column>
				<vxe-table-column field="password" title="密码"></vxe-table-column>
				<vxe-table-column field="telephone" title="电话号码" cell-type="string"></vxe-table-column>
				<vxe-table-column field="status" title="职务" :formatter="formatterStatus"></vxe-table-column>
				
				<vxe-table-column title="操作" width="150px">
					<template v-slot="{ row }">
						<vxe-button type="button" @click="editEvent(row)" data-toggle="modal" data-target="#UpdateModal" >修改</vxe-button>
						
						<vxe-button type="button" @click="removeEvent(row)" status="danger">删除</vxe-button>
					</template>
				</vxe-table-column>
			</vxe-table>
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
						<div class="container-fluid">
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<label for="update-userId">学号</label>
										<input type="text" class="form-control" id="update-userId" v-model="updateInfo.userId" />
										<span class="error" v-if="errors['updateInfo.userId']">{{errors['updateInfo.userId']}}</span>
									</div>
									<div class="form-group">
										<label for="update-name">姓名</label>
										<input type="text" class="form-control" id="update-name" v-model="updateInfo.name" />
										<span class="error" v-if="errors['updateInfo.userName']">{{errors['updateInfo.userName']}}</span>
									</div>
									<div class="form-group">
										<label for="telephone" class="col-form-label">电话号码</label>
										<input type="text" class="form-control" v-model="updateInfo.telephone" />
										<span class="error" v-if="errors['updateInfo.telephone']">{{errors['updateInfo.telephone']}}</span>
									</div>
									<div class="form-group">
										<label for="update-password">密码</label>
										<input type="password" class="form-control" id="update-password" v-model="updateInfo.password" />
										<span class="error" v-if="errors['updateInfo.password']">{{errors['updateInfo.password']}}</span>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label for="update-classId">班级</label>
										<select name="classId" class="form-control" v-model="updateInfo.classId" @change="classOptionChange(updateInfo)">
											<option :value="item.classId" :key="item.classId" v-for="item in classOption"> {{ item.className }} </option>
										</select>
									</div>
									<div class="form-group">
										<label for="update-groupId">小组</label>
										<select class="form-control" v-model="updateInfo.groupId">
											<option v-for="n of updateInfo.groupNum" :value="n" :key="n">第{{n}}小组</option>
										</select>
									</div>
									<div class="form-group">
										<label for="update-status">职务</label>
										<select name="status" class="form-control" v-model="updateInfo.status">
											<option v-for="item in statusOption" :value="item.value" :key="item.value">{{item.label}}</option>
										</select>
									</div>
								</div>
							</div>
						</div>
						
					</div>
					
					<!-- 模态框底部 -->
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
						<button type="button" class="btn btn-primary" data-dismiss="modal" @click="update()">确认修改</button>
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
	import UploadFile from '../../components/UploadFile.vue'
	import Vue from 'vue'
	import Vuerify from 'vuerify'
	import XEUtils from 'xe-utils'
	import css from '../../components/CSS.vue'
	import VXETable from 'vxe-table'
	export default {
		inject: ['reload'],
		name: "UserManagement",
		components: {
			ManageNav,
			AdminUserCreateModal,
			UploadFile
		},
		data() {
			return {
				filterName: '',
				pulldown: false,
				submitLoading: false,
				showEdit: false,
				selectRow: null,
				allAlign: null,
				response: {
					status: '',
					data: []
				},
				tableData: [],
				data: [],
				searchInfo: {
					userId: null,
					name: null,
					classId: null,
					groupId: null,
					status: null,
					groupNum: null,
				},
				updateInfo: {
					userId: null,
					password: null,
					telephone: null,
					userName: null,
					classId: null,
					groupId: null,
					status: null,
					groupNum: null,
				},
				classList: [],
				classOption: [],
				groupList: {},
				teamOption: [],
				isHide: true,
				statusOption: [{
						label: "组员",
						value: 1,
					},
					{
						label: "组长",
						value: 2,
					},
				],
				groupOfClass: [],
			}
		},
		created() {
			this.$nextTick(() => {
				this.columns = this.$refs.xTable.getColumns()
			})
			this.getTeamList();
			this.getGroupList();
			setTimeout(() => {
				// 将指定列设置为隐藏状态
				this.columns.forEach(column => {
					if (['password'].includes(column.property)) {
						column.visible = false
					}
				})
				if (this.$refs.xTable) {
					this.$refs.xTable.refreshColumn()
				}
				this.loading = false
			}, 0);
			setTimeout(() => {
				this.init()
			}, 500);
		},
		methods: {
			init() {
				this.getResponse();
			},
			getResponse() {
				var self = this;
				axios.get(api.adminUserList, null)
				.then(function(res) {
					if (res.status == 200 && res.data.status == 1) {
						self.tableData = res.data.data;
						self.data = res.data.data;
					} else {
						console.log(res.data.msg);
					}
				}).catch(function(error) {
					console.log(error);
				})
			},
			getGroupList() {
				var self = this;
				axios.get(api.adminTeamList, null)
				.then(function(res) {
					if (res.status == 200 && res.data.status == 1) {
						for (var i = 0; i < res.data.data.length; i++) {
							self.groupList[res.data.data[i].groupId] = res.data.data[i];
						}
					} else {
						console.log(res.data.msg);
					}
				}).catch(function(error) {
					console.log(error);
				})
			},
			formatterClass({cellValue}) {
				let item = this.classOption.find(item => item.classId == cellValue)
				return item ? item.className : ''
			},
			formatterStatus({cellValue}) {
				let item = this.statusOption.find(item => item.value == cellValue)
				return item ? item.label : ''
			},
			toGroupName(cellValue) {
				return this.groupList[cellValue].groupName;
			},
			search() {
				var data = this.data;
				this.tableData = [];
				for (let value of data) {
					let flag = 1;
					
					var userId = new RegExp(this.searchInfo.userId);
					var name = new RegExp(this.searchInfo.name);
					
					if (value.userId.match(userId) == null && this.searchInfo.userId != null && this.searchInfo.userId != "") {
						flag = 0;
					}
					if (value.name.match(name) == null && this.searchInfo.name != null && this.searchInfo.name != "") {
						flag = 0;
					}
					if (value.classId != this.searchInfo.classId && this.searchInfo.classId != null && this.searchInfo.classId != "") {
						flag = 0;
					}
					if (value.groupId != this.searchInfo.groupId && this.searchInfo.groupId != null && this.searchInfo.groupId != "") {
						flag = 0;
					}
					if (value.status != this.searchInfo.status && this.searchInfo.status != null && this.searchInfo.status != "") {
						flag = 0;
					}
					if (flag == 1) {
						this.tableData.push(value);
					}
				}

			},
			resetSearch() {
				this.tableData = this.data;
				this.searchInfo = {
					userId: null,
					name: null,
					classId: null,
					groupId: null,
					status: null,
				}
			},
			editEvent(row) {
				this.updateInfo = {
					userId: row.userId,
					password: row.password,
					telephone: row.telephone,
					name: row.name,
					classId: row.classId,
					groupId: row.groupId,
					status: row.status,
					groupNum: null,
				}
				for (let value of this.classOption) {
					if (this.updateInfo.classId == value.classId) {
						this.updateInfo.groupNum = value.groupNum;
					}
				}
				// this.selectRow = row;
				// this.showEdit = true;
			},
			removeEvent(row) {
				var deleteInfo = {
					userId: row.userId
				}
				this.$XModal.confirm('您确定要删除该数据?').then(type => {
					if (type === 'confirm') {
						this.$refs.xTable.remove(row)
					}
				})

				var self = this;
				axios.post(api.adminUserDelete, deleteInfo)
				.then(function(res) {
					alert(res.data.msg);
					self.reload();
				}).catch(function(error) {
					console.log(error);
				})
			},
			getTeamList() {
				var self = this;
				axios.get(api.adminClassList, null)
				.then(function(res) {
					self.getClassOption(res.data.data);
					self.classList = res.data.data;
				}).catch(function(error) {
					console.log(error);
				})
			},
			getClassOption(data) {
				for (let value of data) {
					var option = {
						className: value.className,
						classId: value.classId,
						groupNum: value.groupNum,
					}
					this.classOption.push(option);
				}
			},
			classOptionChange(data){
				console.log(this.groupList);
				this.searchInfo.groupId = null;
				this.groupOfClass = [];
				for(let value in this.groupList){
					if(this.groupList[value].classId == this.searchInfo.classId){
						var item = {
							groupId: this.groupList[value].groupId,
							groupName: this.groupList[value].groupName,
							};
						this.groupOfClass.push(item);
					}
				}
				console.log(this.groupOfClass);
			},
			update() {
				let self = this;
				console.log(self.updateInfo);
				axios.post(api.adminUserUpdate, self.updateInfo)
				.then(function(res) {
					alert(res.data.msg);
					self.reload();
				}).catch(function(error) {
					console.log(error);
				})
				this.init();
			},
			exportSelectEvent () {
				this.$refs.xTable.exportData({
					data: this.$refs.xTable.getCheckboxRecords()
				})
			}
		},
		vuerify: {
			'updateInfo.userId': {
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
			'updateInfo.groupNum': {
				test: function() {
					if (!this.updateInfo.groupNum) {
						return false;
					} else {
						return true;
					}
				},
				message: '小组为必选项'
			},
			'updateInfo.status': {
				test: function() {
					if (!this.updateInfo.status) {
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
			list() {
				const filterName = XEUtils.toString(this.filterName).trim().toLowerCase()
				if (filterName) {
					const filterRE = new RegExp(filterName, 'gi')
					const searchProps = ['userId', 'name']
					const rest = this.tableData.filter(item => searchProps.some(key => XEUtils.toString(item[key]).toLowerCase().indexOf(
						filterName) > -1))
					return rest.map(row => {
						const item = Object.assign({}, row)
						// searchProps.forEach(key => {
						//   item[key] = XEUtils.toString(item[key]).replace(filterRE, match => `<span class="keyword-lighten">${match}</span>`)
						// })
						return item
					})
				}
				return this.tableData
			}
		}
	}
</script>
<style>
	.el-input__inner{
		height: 34px;
	}
	
</style>
