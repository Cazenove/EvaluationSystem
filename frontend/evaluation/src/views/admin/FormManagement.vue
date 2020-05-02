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
			<vxe-table :data="tableData">
				<vxe-table-column field="name" title="评分表名称"></vxe-table-column>
				<vxe-table-column field="classId" title="班级"></vxe-table-column>
				<vxe-table-column field="groupId" title="小组"></vxe-table-column>
				<vxe-table-column field="releaseTime" title="提交时间"></vxe-table-column>
				<vxe-table-column title="操作">
					<button type="button" class="btn btn-light"  data-toggle="modal" data-target="#EndModal">查看</button>
					&nbsp;
					<button type="button" class="btn btn-primary"  data-toggle="modal" data-target="#DeleteModal">评价</button>
				</vxe-table-column>
			</vxe-table>
		</div>
		
		
	</div>
</template>

<script>
	import axios from 'axios'
	import api from '../../router/httpConfig.js'
	import ManageNav from '../../components/ManageNav.vue'
	export default {
		name: "FormManagement",
		components: {
			ManageNav,
		},
		data () {
			return{
				allAlign: null,
				response: {
					status:1,
					data: [
						{
							id:1,//评分表的id，需要把对应id的组间、组内评价表提交记录删除，再把组内评价表的时间改掉
							name:"第二次团队作业",
							classId:1,
							releaseTime:"",//发布时间
							endTime:"",//结束时间
							content: {
								details:[//登录的时候
									{
										groupId:1,
										groupNum: 1,
										groupName:"第一组",
										score:null,//总分
										content:[
											{
												item:"创新性",
												maxScore:40,
												score:"",//未填状态
											},
											{
												item:"实用性",
												maxScore:60,
												score:null,
											}
										],
										suggestion:""
									},
									{
										groupId:2,
										groupNum: 2,
										groupName:"第二组",
										score:null,//总分
										content:[
											{
												item:"创新性",
												maxScore:40,
												score:"",//未填状态
											},
											{
												item:"实用性",
												maxScore:60,
												score:"",
											}
										],
										suggestion:""
									}
								]
							}
						}
					]
				},
				tableData: [
					{name: "第一组团队作业组间评分表", classId: "1", groupId: "1", "releaseTime": "2020-3-22"}
				]
			}
		},
		created() {
			this.init();
			this.tableData = this.response.data;
		},
		methods: {
			init() {
				this.getResponse();
			},
			getResponse() {
				var self = this;
				axios.get(api.adminEvaluationDetails,null)
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
		}
	}
</script>

<style>
</style>
