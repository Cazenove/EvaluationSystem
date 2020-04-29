<!-- 注册模态框，点击注册弹出 -->
<template>
	<div id="RegisterModal">
		<button class="btn btn-primary" data-toggle="modal" data-target="#registerModal">注册</button>
		<!-- 注册模态框 -->
		<div class="modal fade" id="registerModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
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
							<label for="userId">学号</label>
							<input type="text" class="form-control" id="userId" v-model="registerInfo.userId" />
						</div>
						<div class="form-group">
							<label for="password">密码</label>
							<input type="password" class="form-control" id="password" v-model="registerInfo.password" />
						</div>
						<div class="form-group">
							<label for="userName">姓名</label>
							<input type="text" class="form-control" id="userName" v-model="registerInfo.userName" />
						</div>
						<div class="form-group">
							<label for="telephone">电话号码</label>
							<input type="text" class="form-control" id="telephone" v-model="registerInfo.telephone" />
						</div>
						<div class="form-group">
							<label for="classId">班级</label>
							<input type="text" class="form-control" id="classId" v-model="registerInfo.classId" />
						</div>
						<div class="form-group">
							<label for="groupId">小组</label>
							<input type="text" class="form-control" id="groupId" v-model="registerInfo.groupId" />
						</div>
					</div>
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
	export default {
		data() {
			return {
				response: {
					status:1,
					data: [
					    {
					        classId:1,//班级id
					        name:"2020软件工程S班",//班级名
					        groupNum:11//这个班级的小组数量
					    },
					    {
					        classId:2,//班级id
					        name:"2020软件工程W班",//班级名
					        groupNum:11//这个班级的小组数量
					    }
					]
				},
				registerInfo: {//注册的表单信息
					userId:"",
					password:"",
					userName:"",
					telephone:"",
					classId:"",
					groupId:""
				}
			}
		},
		created() {
			//创建的时候获取班级小组列表
		},
		methods: {
			register(registerInfo) {
				//注册功能
			}
		}
	}
</script>

<style>
</style>
