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
		<button type="button" class="offset-md-1 btn btn-primary" data-toggle="modal" data-target="#AddModal">添加用户</button>
		&nbsp;
		<button type="button" class="btn btn-light">一键导入</button>
		
		<div class="container col-md-10 offset-md-1" style="margin: 50px auto;">
			<vxe-table :data="tableData">
				<vxe-table-column field="userId" title="学号"></vxe-table-column>
				<vxe-table-column field="userName" title="姓名"></vxe-table-column>
				<vxe-table-column field="classId" title="班级"></vxe-table-column>
				<vxe-table-column field="groupId" title="小组"></vxe-table-column>
				<vxe-table-column field="password" title="密码"></vxe-table-column>
				<vxe-table-column field="status" title="职务"></vxe-table-column>
				<vxe-table-column title="操作">
					<button type="button" class="btn btn-secondary"  data-toggle="modal" data-target="#UpdateModal">修改</button>
					&nbsp;
					<button type="button" class="btn btn-danger"  data-toggle="modal" data-target="#DeleteModal">删除</button>
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
					<button type="button" class="btn btn-primary">确认修改</button>
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
	// import axios from 'axios'
	import ManageNav from '../components/ManageNav.vue'
	export default {
		name: "UserManagement",
		components: {
			ManageNav,
		},
		data () {
			return {
<<<<<<< HEAD:frontend/evaluation/src/views/UserManagement.vue
<<<<<<< HEAD:frontend/evaluation/src/views/UserManagement.vue
			tableData: [
				{ userId: '091700233', userName: '张三', classId: '1', groupId: '1', password: '123456', status: '1'},
				{ userId: '091700233', userName: '张三', classId: '1', groupId: '1', password: '123456', status: '1'},
			],
			
			}
=======
=======
>>>>>>> parent of c4373eb3... 后台初始化方法:frontend/evaluation/src/views/admin/UserManagement.vue
				response: {
				    status:1,
				    data:[
				        {
				            userId:"221701501",//学号
				            password:"123456",//密码
				            userName:"张三",//姓名
				            classId:1,//班级
				            groupId:1,//小组
				            status:1//职务
				        },
				        {
				            userId:"221701502",//学号
				            password:"123456",//密码
				            userName:"李四",//姓名
				            classId:1,//班级
				            groupId:1,//小组
				            status:2//职务
				        }
				    ]
				},
				tableData: [
					{ userId: '091700233', userName: '张三', classId: '1', groupId: '1', password: '123456', status: '1'},
					{ userId: '091700233', userName: '张三', classId: '1', groupId: '1', password: '123456', status: '1'},
				],
			
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
				axios.get(api.adminUserList,null)
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
			}
>>>>>>> parent of c4373eb3... 后台初始化方法:frontend/evaluation/src/views/admin/UserManagement.vue
		}
	}
</script>

<style>
</style>
