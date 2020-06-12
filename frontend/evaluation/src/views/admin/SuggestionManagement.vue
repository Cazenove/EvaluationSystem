<template>
    <div>
        <ManageNav />
        <nav aria-label="breadcrumb">
			<ol class="breadcrumb">
				<li class="breadcrumb-item"><router-link to="/admin/home">主页</router-link></li>
				<li class="breadcrumb-item active" aria-current="page">{{this.title}}</li>
			</ol>
		</nav>
		
		<div class="container col-md-10 offset-md-1" style="margin: 50px auto;">
			<h1>{{this.title}}</h1>
			<vxe-toolbar>
				<template v-slot:buttons>
					<vxe-button type="button" status="primary" data-toggle="modal" data-target="#createModal">
						新增建议
					</vxe-button>
				</template>
			</vxe-toolbar>
			<vxe-toolbar>
				<template v-slot:buttons>
					<vxe-input v-model="filterName" type="search" placeholder="快速搜索"></vxe-input>
					<vxe-button @click="exportSelectEvent">导出选中</vxe-button>
				</template>
				<template v-slot:tools>
					<vxe-button data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">条件搜索</vxe-button>
				</template>
			</vxe-toolbar>
			<div class="collapse" id="collapseExample">
				<div class="card card-body" style="border: none;padding: 0;">
					<vxe-toolbar>
						<template v-slot:buttons>
							<el-row :gutter="20">
								<el-col :span="4">
									<el-input offser="3" placeholder="建议ID" v-model="searchInfo.groupSuggestionId"></el-input>
								</el-col>
								<el-col :span="4">
									<el-select offser="3" placeholder="班级" v-model="searchInfo.classId" @change="classOptionChange(searchInfo)">
										<el-option :value="item.classId" v-for="item in classList" :key="item.classId" :label="item.className"></el-option>
									</el-select>
								</el-col>
								<el-col :span="3">
									<el-select offser="3" placeholder="小组" v-model="searchInfo.groupId">
										<el-option v-for="item in groupOfClass" :value="item.groupId" :key="item.groupId" :label="item.groupName"></el-option>
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
			 border
			 show-header-overflow
			 show-overflow
			 highlight-hover-row
			 ref="xTable"
			 :align="allAlign"
			 :data="list">
				<vxe-table-column type="checkbox" width="60"></vxe-table-column>
				<vxe-table-column field="groupSuggestionId" title="建议ID"></vxe-table-column>
				<vxe-table-column field="groupId" title="班级" :formatter="toClassName"></vxe-table-column>
				<vxe-table-column field="groupId" title="小组" :formatter="toGroupName"></vxe-table-column>
				<vxe-table-column field="evaluationOuterId" title="评分表" :formatter="toFormName"></vxe-table-column>
				<vxe-table-column field="suggestion" title="建议内容"></vxe-table-column>
				<vxe-table-column title="操作" show-overflow width="150px">
					<template v-slot="{ row }">
						<vxe-button type="button" status="primary" data-toggle="modal" data-target="#editModal" @click="showEditModal(row)">
							修改
						</vxe-button>
						<vxe-button type="button" status="danger" @click="removeEvent(row)">删除</vxe-button>
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
						<button type="button" class="btn btn-primary" data-dismiss="modal" @click="submitCreateForm">提交</button>
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
	import XEUtils from 'xe-utils'
	export default {
		inject: ['reload'],
		components: {
			ManageNav,
		},
		data () {
			return {
				data: [],
				filterName: '',
				showEdit: false,
				allAlign: null,
                title: "建议管理",
				searchInfo:{
					groupSuggestionId: null,
					groupId:null,
					classId:null,
				},
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
				groupList: {},
				formList: {},
				classList: {},
				groupOfClass: [],
			}
		},
        created() {
			this.getClassList();
			this.getGroupList();
			this.getFormList();
            setTimeout(() => {
            	this.init()
            }, 500);
        },
        methods: {
			classOptionChange(data){
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
			},
			toClassName({cellValue}) {
				return this.groupList[cellValue].className;
			},
			toGroupName({cellValue}) {
				return this.groupList[cellValue].groupName;
			},
			toFormName({cellValue}) {
				return this.formList[cellValue];
			},
			getGroupList() {
				var self = this;
				axios.get(api.adminTeamList,null)
				.then(function(res) {
					if(res.status == 200 && res.data.status == 1) {
						for(var i=0; i<res.data.data.length; i++) {
							self.groupList[res.data.data[i].groupId] = res.data.data[i];
						}
					}
				}).catch(function(error) {
					console.log(error);
				})
			},
			getFormList() {
				var self = this;
				axios.get(api.adminEvaluationDetails,null)
				.then(function(res) {console.log(res);
					if(res.status == 200 && res.data.status == 1) {
						for(var i=0; i<res.data.data.length; i++) {
							self.formList[res.data.data[i].evaluationOuterId] = res.data.data[i].name;
						}
						self.getResponse();
					}
					else {
						console.log(res.data.msg);
					}
				}).catch(function(error) {
					console.log(error);
				})
			},
			getClassList() {
				var self = this;
				axios.get(api.adminClassList, null)
				.then(function(res) {
					for(var i=0; i<res.data.data.length; i++) {
						self.classList = res.data.data;
						console.log(res.data.data);
					}
				}).catch(function(error) {
					console.log(error);
				})
			},
            getResponse() {
				var self = this;
				axios.get(api.adminSuggestionList,null)
				.then(function(res) {
					if(res.status == 200 && res.data.status == 1) {
						self.tableData = res.data.data;
						self.data = res.data.data;
					}
					else {
						alert(res.data.msg);
					}
				}).catch(function(error) {
					console.log(error);
				})
            },
            init() {
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
							groupSuggestionId: row.groupSuggestionId.toString()
						}).then(function(res) {
							if(res.stauts == 200 && res.data.status == 1) {
								alert(res.data.msg);
							} else {
								alert(res.data.msg);
							}
							self.reload();
						}).catch(function(error) {
							console.log(error);
						})
					}
				})
			},
			submitEditForm() {
				this.editForm.groupSuggestionId = this.editForm.groupSuggestionId.toString();
				axios.post(api.adminSuggestionUpdate,this.editForm)
				.then(function(res) {
					if(res.stauts == 200 && res.data.status == 1) {
						alert(res.data.msg);
					} else {
						alert(res.data.msg);
					}
					self.reload();
				}).catch(function(error) {
					console.log(error);
				})
			},
			submitCreateForm() {
				axios.post(api.adminSuggestionCreate,this.createForm)
				.then(function(res) {
					if(res.status == 200 && res.data.stauts == 1) {
						alert("创建成功！");
					} else {
						alert(res.data.msg);
					}
					self.reload();
				}).catch(function(error) {
					console.log(error);
				})
			},
			exportSelectEvent () {
				this.$refs.xTable.exportData({
					data: this.$refs.xTable.getCheckboxRecords()
				})
			},
			search() {
				var data = this.data;
				this.tableData = [];
				for (let value of data) {
					let flag = 1;
					
					var className = null;
					for (let x of this.classList){
						if(this.searchInfo.classId == x.classId){
							className = x.className;
						}
					}
					if (value.groupSuggestionId != null && this.searchInfo.groupSuggestionId != null && this.searchInfo.groupSuggestionId != "") {
						flag = 0;
					}
					if (className !=  this.groupList[value.groupId].className && this.searchInfo.classId != null && this.searchInfo.classId != "") {
						flag = 0;
					}
					if (value.groupId != this.searchInfo.groupId && this.searchInfo.groupId != null && this.searchInfo.groupId != "") {
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
					groupSuggestionId:null,
					classId:null,
					groupId:null,
				}
			},
        },
		computed:{
			list() {
				const filterName = XEUtils.toString(this.filterName).trim().toLowerCase()
				if (filterName) {
					const filterRE = new RegExp(filterName, 'gi')
					const searchProps = ['groupSuggestionId', 'evaluationOuterId', 'groupId', 'suggestion']
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
