<template>
	<div id="ClassManagement">
		<ManageNav></ManageNav>
		<nav aria-label="breadcrumb">
			<ol class="breadcrumb">
				<li class="breadcrumb-item"><a href="#">主页</a></li>
				<li class="breadcrumb-item active" aria-current="page">班级管理</li>
			</ol>
		</nav>
		<!-- 模态框 -->
		<div class="modal fade" id="AddModal">
			<div class="modal-dialog">
				<div class="modal-content">
		
				<!-- 模态框头部 -->
				<div class="modal-header">
					<h4 class="modal-title">新建班级</h4>
					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>
		
				<!-- 模态框主体 -->
				<div class="modal-body">
					<div class="form-group">
						<label for="add-className">名称</label>
						<input v-model="submitData.className" type="text" class="form-control" id="add-userId"/>
					</div>
					<div class="form-group">
						<label for="add-groupNum">小组数量</label>
						<input v-model="submitData.groupNum" type="text" class="form-control" id="add-userName"/>
					</div>
					<div class="form-group">
						<label for="add-startTime">开始时间</label>
						<input v-model="submitData.startTime" type="date" class="form-control" />
					</div>
				</div>
				
				<!-- 模态框底部 -->
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-primary" @click="createClass">确认创建</button>
				</div>
				</div>
			</div>
		</div>
		
		<div class="container col-md-10 offset-md-1" style="margin: 50px auto;">
			<div style="margin-bottom: 20px;">
			<h1>班级管理</h1>
			<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#AddModal">新增班级</button>
			</div>
			<vxe-table :data="tableData">
				<vxe-table-column field="classId" title="班级ID"></vxe-table-column>
				<vxe-table-column field="className" title="班级名称"></vxe-table-column>
				<vxe-table-column field="groupNum" title="小组数量"></vxe-table-column>
				<vxe-table-column field="status" title="状态"></vxe-table-column>
				<vxe-table-column field="startTime" title="开始时间"></vxe-table-column>
				<vxe-table-column title="操作" width="240px">
					<template v-slot="{ row }">
						<button type="button" class="btn btn-secondary"  data-toggle="modal" data-target="#UpdateModal" @click="setEditData(row)">修改</button>
						&nbsp;
						<button type="button" class="btn btn-secondary"  data-toggle="modal" data-target="#EndModal" @click="setEndClass(row.classId)">结束</button>
						&nbsp;
						<button type="button" class="btn btn-danger"  data-toggle="modal" data-target="#DeleteModal" @click="setDeleteClass(row.classId)">删除</button>
					</template>
				</vxe-table-column>
			</vxe-table>
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
					确认删除该班级？
				</div>
				
				<!-- 模态框底部 -->
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-danger" @click="deleteClass">确认删除</button>
				</div>
				</div>
			</div>
		</div>
		
		<div class="modal fade" id="EndModal">
			<div class="modal-dialog">
				<div class="modal-content">
		
				<!-- 模态框头部 -->
				<div class="modal-header">
					<h4 class="modal-title">结束</h4>
					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>
		
				<!-- 模态框主体 -->
				<div class="modal-body">
					确认结束？
				</div>
				
				<!-- 模态框底部 -->
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-danger" data-dismiss="modal" @click="endClass">确认结束</button>
				</div>
				</div>
			</div>
		</div>
		
		<div class="modal fade" id="UpdateModal">
			<div class="modal-dialog">
				<div class="modal-content">
		
				<!-- 模态框头部 -->
				<div class="modal-header">
					<h4 class="modal-title">修改班级信息</h4>
					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>
		
				<!-- 模态框主体 -->
				<div class="modal-body">
					<div class="form-group">
						<label for="update-className">班级名称</label>
						<input v-model="editData.className" type="text" class="form-control" id="update-userId"/>
					</div>
					<div class="form-group">
						<label for="update-groupName">小组数量</label>
						<input v-model="editData.groupNum" type="text" class="form-control" id="update-userName"/>
					</div>
					<div class="form-group">
						<label for="add-startTime">开始时间</label>
						<input v-model="editData.startTime" type="date" class="form-control" />
					</div>
				</div>
				
				<!-- 模态框底部 -->
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-primary" data-dismiss="modal" @click="editClass">确认修改</button>
				</div>
				</div>
			</div>
		</div>
		
	</div>
</template>

<script>
	import axios from 'axios'
	import api from '../../router/httpConfig.js'
	import XEUtils from 'xe-utils'
	import ManageNav from '../../components/ManageNav.vue'
	
	export default {
		inject: ['reload'],
		name: 'ClassManagement',
		components: {
			ManageNav,
		},
		data () {
			return {
				allAlign: null,
				response: {
					status:'',
					data:[]
				},
				tableData: [],
				submitData: {
					className: '',
					groupNum: '',
					startTime: ''
				},
				deleteClassId:'',
				endClassId:'',
				editData: {
					classId:'',
					className:'',
					groupNum:'',
					startTime:''
				}
			}
		},
		created() {
			this.init();
			
		},
		methods: {
			init() {
				this.getResponse();
			},
			getResponse() {
				var self = this;
				axios.get(api.adminClassList,null)
				.then(function(res) {
					if(res.status == 200 && res.data.status == 1) {
						for (let item of res.data.data){
							if(item.startTime != "")
							{
								var timeStamp = new Date(parseInt(item.startTime)*1000);
								var year = timeStamp.getFullYear();
								var month = timeStamp.getMonth() + 1;
								var date = timeStamp.getDate();
								var time = year+"-"+month+"-"+date;
							}
							item.startTime = time;
						}
						self.tableData = res.data.data;
						
					} else {
						console.log(res.data.msg);
					}
				}).catch(function(error) {
					console.log(error);
				})
			},
			createClass() {
				this.getTimeStamp(this.submitData);
				// console.log(Number(new Date()));
				// this.submitData.startTime = Number(this.submitData.startTime);
				var self = this;
				axios.post(api.adminClassCreate,this.submitData)
				.then(function(res) {
					console.log(res);
					if(res.status == 200 && res.data.status == 1) {
						alert("创建成功！")
					} else {
						alert(res.data.msg);
					}
					self.reload();
				}).catch(function(error) {
					console.log(error);
				})
			},
			setDeleteClass(classId) {
				this.$data.deleteClassId = classId;
			},
			setEndClass(classId) {
				this.$data.endClassId = classId;
			},
			deleteClass() {
				axios.post(api.adminClassDelete,{
					classId:this.$data.deleteClassId
				})
				.then(function(res) {
					console.log(res);
					if(res.status == 200 && res.data.status == 1) {
						alert("删除成功！");
					} else {
						alert(res.data.msg);
					}
					self.reload();
				}).catch(function(error) {
					console.log(error);
				})
			},
			endClass() {
				var self = this;
				axios.post(api.adminClassEnd,{
					classId:this.$data.endClassId
				})
				.then(function(res) {
					console.log(res);
					if(res.status == 200 && res.data.status == 1) {
						alert("结束成功！")
					} else {
						alert(res.data.msg);
					}
					self.reload();
				}).catch(function(error) {
					console.log(error);
				})
			},
			setEditData(row) {
				this.$data.editData.classId = row.classId;
				this.$data.editData.className = row .className;
				this.$data.editData.groupNum = row.groupNum;
				this.$data.editData.startTime = row.startTime;
			},
			editClass() {
				this.getTimeStamp(this.$data.editData);
				console.log(this.$data.editData.startTime);
				axios.post(api.adminClassUpdate,this.$data.editData)
				.then(function(res) {
					console.log(res);
					if(res.status == 200 && res.data.status == 1) {
						alert("修改成功！")
					} else {
						alert(res.data.msg);
					}
					self.reload();
				}).catch(function(error) {
					console.log(error);
				})
			},
			getTimeStamp(data){
				var str = data.startTime.replace(/-/g,'/');
				var date = new Date(str);
				var time = date.getTime()/1000;
				data.startTime = time;
			},
		}
	}
</script>

<style>
</style>
