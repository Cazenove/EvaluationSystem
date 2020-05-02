<template>
	<div id="ClassManagement">
		<ManageNav></ManageNav>
		<nav aria-label="breadcrumb">
			<ol class="breadcrumb">
				<li class="breadcrumb-item"><a href="#">主页</a></li>
				<li class="breadcrumb-item active" aria-current="page">班级管理</li>
			</ol>
		</nav>
		<h1 class="offset-md-1">班级管理</h1>
		<button type="button" class="offset-md-1 btn btn-primary" data-toggle="modal" data-target="#AddModal">新增班级</button>
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
						<input type="text" class="form-control" id="add-userId"/>
					</div>
					<div class="form-group">
						<label for="add-groupNum">小组数量</label>
						<input type="text" class="form-control" id="add-userName"/>
					</div>
					<div class="form-group">
						<label for="add-startTime">开始时间</label>
						<input type="text" class="form-control" id="add-classId"/>
					</div>
				</div>
				
				<!-- 模态框底部 -->
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-primary">确认创建</button>
				</div>
				</div>
			</div>
		</div>
		
		<div class="container col-md-10 offset-md-1" style="margin: 50px auto;">
			<vxe-table :data="tableData">
				<vxe-table-column field="className" title="班级名称"></vxe-table-column>
				<vxe-table-column field="groupNum" title="小组数量"></vxe-table-column>
				<vxe-table-column field="status" title="状态"></vxe-table-column>
				<vxe-table-column field="startTime" title="开始时间"></vxe-table-column>
				<vxe-table-column title="操作">
					<button type="button" class="btn btn-secondary"  data-toggle="modal" data-target="#UpdateModal">修改</button>
					&nbsp;
					<button type="button" class="btn btn-secondary"  data-toggle="modal" data-target="#EndModal">结束</button>
					&nbsp;
					<button type="button" class="btn btn-danger"  data-toggle="modal" data-target="#DeleteModal">删除</button>
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
					<button type="button" class="btn btn-danger">确认删除</button>
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
					<button type="button" class="btn btn-danger">确认结束</button>
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
						<input type="text" class="form-control" id="update-userId"/>
					</div>
					<div class="form-group">
						<label for="update-groupName">小组数量</label>
						<input type="text" class="form-control" id="update-userName"/>
					</div>
				</div>
				
				<!-- 模态框底部 -->
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-primary">确认修改</button>
				</div>
				</div>
			</div>
		</div>
		
	</div>
</template>

<script>
	import ManageNav from '../components/ManageNav.vue'
	export default {
		name: 'ClassManagement',
		components: {
			ManageNav,
		},
		data () {
			return {
<<<<<<< HEAD:frontend/evaluation/src/views/ClassManagement.vue
<<<<<<< HEAD:frontend/evaluation/src/views/ClassManagement.vue
				tableData: [
					{className: "2020软件工程S班", groupNum: "8" ,status:"进行中", startTime: "2020-03-20", }
				]
=======
=======
>>>>>>> parent of c4373eb3... 后台初始化方法:frontend/evaluation/src/views/admin/ClassManagement.vue
				response: {
				},
				tableData: [
					{className: "2020软件工程S班", groupNum: "8" ,status:"进行中", startTime: "2020-03-20", }
				]
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
					if(res.status === 1) {
						self.response = res;
					}
					else {
						console.log(res.msg);
					}
				}).catch(function(error) {
					console.log(error);
				})
>>>>>>> parent of c4373eb3... 后台初始化方法:frontend/evaluation/src/views/admin/ClassManagement.vue
			}
		}
	}
</script>

<style>
</style>
