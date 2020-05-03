<template>
	<div id="ScoreManagement">
		<ManageNav></ManageNav>
		<nav aria-label="breadcrumb">
			<ol class="breadcrumb">
				<li class="breadcrumb-item"><a href="#">主页</a></li>
				<li class="breadcrumb-item active" aria-current="page">评分表管理</li>
			</ol>
		</nav>
		<h1 class="offset-md-1">评分表管理</h1>
		<div class="container col-md-10 offset-md-1" style="margin: 50px auto;">
			<vxe-toolbar>
				<template v-slot:buttons>
					<button class="btn btn-primary" @click="createForm">新增评分表</button>
				</template>
			</vxe-toolbar>
			<vxe-table :data="tableData" border show-header-overflow show-overflow highlight-hover-row :align="allAlign">
				<vxe-table-column field="name" title="评分表名称"></vxe-table-column>
				<vxe-table-column field="classId" title="班级"></vxe-table-column>
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
					status: 1,
					data: [{
						id: 1, //评分表的id，需要把对应id的组间、组内评价表提交记录删除，再把组内评价表的时间改掉
						name: "第二次团队作业",
						classId: 1,
						releaseTime: "2020-01-01", //发布时间
						endTime: "2020-01-08", //结束时间
						content: {
							details: [ //登录的时候
								{
									groupId: 1,
									groupNum: 1,
									groupName: "第一组",
									score: null, //总分
									content: [{
											item: "创新性",
											maxScore: 40,
											score: "", //未填状态
										},
										{
											item: "实用性",
											maxScore: 60,
											score: null,
										}
									],
									suggestion: ""
								},
								{
									groupId: 2,
									groupNum: 2,
									groupName: "第二组",
									score: null, //总分
									content: [{
											item: "创新性",
											maxScore: 40,
											score: "", //未填状态
										},
										{
											item: "实用性",
											maxScore: 60,
											score: "",
										}
									],
									suggestion: ""
								}
							]
						}
					},
					{
						id: 2, //评分表的id，需要把对应id的组间、组内评价表提交记录删除，再把组内评价表的时间改掉
						name: "第三次团队作业",
						classId: 1,
						releaseTime: "2020-01-01", //发布时间
						endTime: "2020-01-08", //结束时间
						content: {
							details: [ //登录的时候
								{
									groupId: 1,
									groupNum: 1,
									groupName: "第一组",
									score: null, //总分
									content: [{
											item: "创新性",
											maxScore: 50,
											score: "", //未填状态
										},
										{
											item: "实用性",
											maxScore: 50,
											score: null,
										}
									],
									suggestion: ""
								},
								{
									groupId: 2,
									groupNum: 2,
									groupName: "第二组",
									score: null, //总分
									content: [{
											item: "创新性",
											maxScore: 50,
											score: "", //未填状态
										},
										{
											item: "实用性",
											maxScore: 50,
											score: "",
										}
									],
									suggestion: ""
								}
							]
						}
					}]
				},
				tableData: [],
				detailData: []
			}
		},
		created() {
			this.init();
			this.tableData = this.response.data;
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
						if (res.status === 1) {
							self.response = res;
						} else {
							console.log(res.msg);
						}
					}).catch(function(error) {
						console.log(error);
					})
			},
			detailsEvent(row) {
				this.detailData = row.content.details[0].content;
				this.showDetails = true
			},
			removeEvent(row) {
				this.$XModal.confirm('您确定要删除这份评分表吗?删除评分表的同时会删除提交记录，请慎重考虑').then(type => {
					if (type === 'confirm') {
						console.log(row.id);
						axios.post(api.adminEvaluationDelete,{
							evaluationOuterId: row.id
						}).then(function(res) {
							if(res.status === 1) {
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
