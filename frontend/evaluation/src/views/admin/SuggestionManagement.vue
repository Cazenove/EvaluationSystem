<template>
    <div>
        <ManageNav />
        <nav aria-label="breadcrumb">
			<ol class="breadcrumb">
				<li class="breadcrumb-item"><a href="#">主页</a></li>
				<li class="breadcrumb-item active" aria-current="page">{{this.title}}</li>
			</ol>
		</nav>
		
		<div class="container col-md-10 offset-md-1" style="margin: 50px auto;">
			<h1>{{this.title}}</h1>
			<vxe-toolbar>
				<template v-slot:buttons>
					<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#createModal">
						新增建议
					</button>
				</template>
			</vxe-toolbar>
			<vxe-table border show-header-overflow show-overflow highlight-hover-row :align="allAlign" :data="tableData">
				<vxe-table-column field="groupSuggestionId" title="建议ID"></vxe-table-column>
				<vxe-table-column field="groupId" title="小组ID"></vxe-table-column>
				<vxe-table-column field="evaluationOuterId" title="评分表ID"></vxe-table-column>
				<vxe-table-column field="suggestion" title="建议内容"></vxe-table-column>
				<vxe-table-column title="操作" show-overflow>
					<template v-slot="{ row }">
						<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#editModal" @click="showEditModal(row)">
							修改
						</button>
						&nbsp;
						<button type="text" class="btn btn-danger" @click="removeEvent(row)">删除</button>
					</template>
				</vxe-table-column>
			</vxe-table>
		</div>
		
		<!-- 编辑建议模态框 -->
		<div class="modal fade" id="editModal" data-backdrop="static" tabindex="-1" role="dialog" aria-labelledby="staticBackdropLabel" aria-hidden="true">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title" id="editModalLabel">修改建议</h5>
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">
						<div class="form-group">
							<label for="suggestion" class="col-form-label">修改建议</label>
							<input type="text" class="form-control" id="suggestion" v-model="editForm.suggestion" />
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
						<button type="button" class="btn btn-primary" @click="submitEditForm">修改</button>
					</div>
				</div>
			</div>
		</div>
		
		<!-- 新建建议模态框 -->
		<div class="modal fade" id="createModal" data-backdrop="static" tabindex="-1" role="dialog" aria-labelledby="staticBackdropLabel" aria-hidden="true">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title" id="createModalLabel">新增建议</h5>
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">
						<div class="form-group">
							<label for="evaluationId" class="col-form-label">评分表ID</label>
							<input type="text" class="form-control" id="evaluationId" v-model="createForm.evaluationId" />
						</div>
						<div class="form-group">
							<label for="targetGroupId" class="col-form-label">小组ID</label>
							<input type="text" class="form-control" id="targetGroupId" v-model="createForm.targetGroupId" />
						</div>
						<div class="form-group">
							<label for="newsuggestion" class="col-form-label">建议</label>
							<input type="text" class="form-control" id="newsuggestion" v-model="createForm.suggestion" />
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
						<button type="button" class="btn btn-primary" @click="submitCreateForm">提交</button>
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
	export default {
		components: {
			ManageNav,
		},
		data () {
			return {
				showEdit: false,
				allAlign: null,
                title: "建议管理",
				tableData: [
				],
                request: {
                },
				editForm: {
					groupSuggestionId:'',
					suggestion:''
				},
				createForm: {
					evaluationId:'',
					targetGroupId:'',
					suggestion:''
				},
                response: {
					status:1,
					data:[
						{
							groupSuggestionId:1,//小组建议表内的ID
							groupId:1,//表示是这个小组收到的建议
							evaluationOuterId:1,//表示是针对这一轮作业的建议
							suggestion:"建议内容1"
						},
						{
							groupSuggestionId:2,
							groupId:1,
							evaluationOuterId:1,
							suggestion:"建议内容2"
						}
					]
				}
			}
		},
        created() {
            this.init();
			this.tableData = this.response.data;
        },
        methods: {
            getRequest() {
            },
            getResponse() {
				var self = this;
				axios.get(api.adminSuggestionList,null)
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
            },
            init() {
                this.getRequest();
                this.getResponse();
            },
			insertEvent () {
				this.formData = {
					evaluationId:'',
					targetGroupId:'',
					suggestion:''
				}
				this.selectRow = null
				this.showEdit = true
			},
			showEditModal(row) {
				this.editForm.suggestion = row.suggestion;
				this.editForm.groupSuggestionId = row.groupSuggestionId;
			},
			removeEvent (row) {
				this.$XModal.confirm('您确定要删除这条评价吗？').then(type => {
					if (type === 'confirm') {
						axios.post(api.adminSuggestionDelete, {
							groupSuggestionId: row.groupSuggestionId
						}).then(function(res) {
							if(res.status === 1) {
								this.$XModal.message({ status: 'success', message: '删除成功！' })
							} else {
								this.$XModal.message({ status: 'error', message: res.msg })
							}
						}).catch(function(error) {
							console.log(error);
						})
					}
				})
			},
			submitEditForm() {
				axios.post(api.adminSuggestionUpdate,this.editForm)
				.then(function(res) {
					if(res.stauts === 1) {
						this.$XModal.message({ status: 'success', message: '修改成功！' })
					} else {
						this.$XModal.message({ status: 'error', message: res.msg })
					}
				}).catch(function(error) {
					console.log(error);
				})
			},
			submitCreateForm() {
				axios.post(api.adminSuggestionCreate,this.createForm)
				.then(function(res) {
					if(res.stauts === 1) {
						this.$XModal.message({ status: 'success', message: '新建成功！' })
					} else {
						this.$XModal.message({ status: 'error', message: res.msg })
					}
				}).catch(function(error) {
					console.log(error);
				})
			}
        }
        
	}
</script>

<style>
</style>
