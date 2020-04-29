<template>
	<div id="StuManagement">
		<ManageNav></ManageNav>
		<nav aria-label="breadcrumb">
			<ol class="breadcrumb">
				<li class="breadcrumb-item"><a href="#">主页</a></li>
				<li class="breadcrumb-item active" aria-current="page">学生管理</li>
			</ol>
		</nav>
		<h1 class="offset-md-1">用户管理</h1>
		<button type="button" class="offset-md-1 btn btn-primary" data-toggle="modal" data-target="#updateModal">添加用户</button>
		<button type="button" class="btn btn-light">一键导入</button>
		<div class="container col-md-10 offset-md-1" style="margin: 50px auto;">
			<vxe-table :data="tableData">
				<vxe-table-column field="userId" title="学号"></vxe-table-column>
				<vxe-table-column field="userName" title="姓名"></vxe-table-column>
				<vxe-table-column field="classId" title="班级"></vxe-table-column>
				<vxe-table-column field="groupId" title="小组"></vxe-table-column>
				<vxe-table-column field="password" title="密码"></vxe-table-column>
				<vxe-table-column field="status" title="职务"></vxe-table-column>
				<vxe-table-column>
					<button type="button" class="btn btn-secondary">修改</button>
				</vxe-table-column>
			</vxe-table>
		</div>
		<!-- 模态框 -->
		<div class="modal fade" id="updateModal">
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
						<label for="update-userId">学号</label>
						<input type="text" class="form-control" id="update-userId"/>
					</div>
					<div class="form-group">
						<label for="update-userName">姓名</label>
						<input type="text" class="form-control" id="update-userName"/>
					</div>
					<div class="form-group">
						<label for="update-classId">班级</label>
						<input type="text" class="form-control" id="update-classId"/>
					</div>
					<div class="form-group">
						<label for="update-groupId">小组</label>
						<input type="text" class="form-control" id="update-groupId"/>
					</div>
					<div class="form-group">
						<label for="update-status">职务</label>
						<input type="text" class="form-control" id="update-status"/>
					</div>
					<div class="form-group">
						<label for="update-password">密码</label>
						<input type="password" class="form-control" id="update-password"/>
					</div>
				</div>
		
				<!-- 模态框底部 -->
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-primary">注册</button>
				</div>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
	// import axios from 'axios'
	import ManageNav from '../components/ManageNav.vue'
	export default {
		name: "StuManagement",
		components: {
			ManageNav,
		},
		data () {
			return {
			tableData: [
				{ userId: '091700233', userName: '张三', classId: '1', groupId: '1', password: '123456', status: '1'},
			],
			
			}
		}
	}
</script>

<style>
</style>
