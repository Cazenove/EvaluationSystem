<template>
	<div>
		<ManageNav />
		<nav aria-label="breadcrumb">
			<ol class="breadcrumb">
				<li class="breadcrumb-item"><a href="#">主页</a></li>
				<li class="breadcrumb-item active" aria-current="page">{{this.title}}</li>
			</ol>
		</nav>
		<h1 class="offset-md-1">{{this.title}}</h1>
		<vxe-table border show-header-overflow show-overflow highlight-hover-row :align="allAlign" :data="tableData"
		 :expand-config="{accordion: true}">
			<vxe-table-column field="groupId" title="小组ID"></vxe-table-column>
			<vxe-table-column field="groupName" title="小组名"></vxe-table-column>
			<vxe-table-column field="classId" title="班级ID"></vxe-table-column>
			<vxe-table-column field="className" title="班级名"></vxe-table-column>
			<vxe-table-column field="groupNum" title="小组序号"></vxe-table-column>
			<vxe-table-column title="组长信息">
				<vxe-table-column field="leader.userId" title="学号"></vxe-table-column>
				<vxe-table-column field="leader.userName" title="姓名"></vxe-table-column>
			</vxe-table-column>
			<vxe-table-column type="expand" title="组员信息">
				<template v-slot:content="{ row, rowIndex }">
					<vxe-table border :data="tableData[rowIndex].member">
						<vxe-table-column field="userId" title="学号"></vxe-table-column>
						<vxe-table-column field="userName" title="姓名"></vxe-table-column>
					</vxe-table>
				</template>
			</vxe-table-column>
		</vxe-table>
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
		data() {
			return {
				allAlign: null,
				title: "小组管理",
				tableData: [],
				request: {},
				response: {
					status: 1,
					data: [{
							groupId: 1,
							groupName: "第一组",
							classId: 1,
							className: "2020软件工程S班",
							groupNum: 1, //小组序列，在该班级的第几组
							leader: { //组长
								userId: "221701000",
								userName: "张三"
							},
							member: [{
									userId: "221701001",
									userName: "李四"
								},
								{
									userId: "221701002",
									userName: "王五"
								}
							]
						},
						{
							groupId: 1,
							groupName: "第一组",
							classId: 1,
							className: "2020软件工程S班",
							groupNum: 1, //小组序列，在该班级的第几组
							leader: { //组长
								userId: "221701000",
								userName: "张三"
							},
							member: [{
									userId: "221701001",
									userName: "李四"
								},
								{
									userId: "221701002",
									userName: "王五"
								}
							]
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
			getRequest() {},
			getResponse() {
				var self = this;
				axios.get(api.adminTeamList, null)
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
			init() {
				this.getRequest();
				this.getResponse();
			}
		}

	}
</script>

<style>
</style>
