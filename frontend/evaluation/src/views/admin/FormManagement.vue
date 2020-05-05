<template>
	<div id="ScoreManagement">
		<ManageNav></ManageNav>
		<nav aria-label="breadcrumb">
			<ol class="breadcrumb">
				<li class="breadcrumb-item"><a href="#">主页</a></li>
				<li class="breadcrumb-item active" aria-current="page">评分表管理</li>
			</ol>
		</nav>
		
		<div class="container col-md-10 offset-md-1" style="margin: 50px auto;">
			<h1>评分表管理</h1>
			<vxe-toolbar>
				<template v-slot:buttons>
					<button class="btn btn-primary" @click="createForm">新增评分表</button>
				</template>
			</vxe-toolbar>
			<vxe-table :data="tableData" border show-header-overflow show-overflow highlight-hover-row :align="allAlign">
				<vxe-table-column field="name" title="评分表名称"></vxe-table-column>
				<vxe-table-column field="classId" title="班级ID"></vxe-table-column>
				<vxe-table-column field="releaseTime" title="发布时间"></vxe-table-column>
				<vxe-table-column field="endTime" title="截止时间"></vxe-table-column>
				<vxe-table-column title="操作" width="150" show-overflow>
					<template v-slot="{ row }">
						<button type="text" class="btn btn-info" @click="detailsEvent(row)">详情</button>
						&nbsp;
						<button type="text" class="btn btn-danger" @click="removeEvent(row)">删除</button>
					</template>
				</vxe-table-column>
			</vxe-table>
		</div>
		<vxe-modal v-model="showDetails" title="查看详情" width="600" height="400" :mask="false" :lock-view="false" resize>
			<vxe-table
			 highlight-hover-row
			 highlight-current-row
			 border 
			 :data="detailData" >
				<vxe-table-column field="item" title="评分项"></vxe-table-column>
				<vxe-table-column field="maxScore" title="分值"></vxe-table-column>
			</vxe-table>
		</vxe-modal>
		<vxe-modal v-model="showCreate" title="创建评分表" width="850" height="600" :mask="false" :lock-view="false" resize>
			<CreateForm />
		</vxe-modal>
	</div>
</template>

<script>
	import axios from 'axios'
	import api from '../../router/httpConfig.js'
	import ManageNav from '../../components/ManageNav.vue'
	import CreateForm from '../../components/CreateForm.vue'
	export default {
		name: "FormManagement",
		components: {
			ManageNav,
			CreateForm
		},
		data() {
			return {
				showDetails: false,
				showCreate: false,
				allAlign: null,
				response: {
					status:'',
					data: []
				},
				tableData: [],
				detailData: []
			}
		},
		created() {
			this.init();
		},
		methods: {
			createForm() {
				this.showCreate = true;
			},
			init() {
				this.getResponse();
			},
			getResponse() {
				var self = this;
				axios.get(api.adminEvaluationDetails, null)
					.then(function(res) {
						console.log(res);
						if (res.status == 200 && res.data.status == 1) {
							self.response = res.data;
							self.tableData = self.response.data;
							for(var i = 0; i < self.tableData.length; i++) {
								self.tableData[i].classId = self.tableData[i].classInfo.classId;
							}
						} else {
							console.log(res.msg);
						}
					}).catch(function(error) {
						console.log(error);
					})
			},
			detailsEvent(row) {
				console.log(row);
				this.detailData = row.content.details[0].content;
				this.showDetails = true
			},
			removeEvent(row) {
				this.$XModal.confirm('您确定要删除这份评分表吗?删除评分表的同时会删除提交记录，请慎重考虑').then(type => {
					if (type === 'confirm') {
						console.log(row.evaluationOuterId);
						axios.post(api.adminEvaluationDelete,{
							evaluationOuterId: row.evaluationOuterId
						}).then(function(res) {
							if(res.data.status == 1) {
								alert("删除成功！");
							} else {
								alert(res.msg);
							}
						}).catch(function(error) {
							console.log(error);
						})
					}
				})
			}
		}
	}
</script>

<style>
</style>
