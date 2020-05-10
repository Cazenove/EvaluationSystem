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
		<vxe-table border show-header-overflow show-overflow highlight-hover-row :align="allAlign" :data="tableData"
		 :expand-config="{accordion: true}">
			<vxe-table-column field="groupId" title="小组ID"></vxe-table-column>
			<vxe-table-column field="groupName" title="小组名"></vxe-table-column>
			<vxe-table-column field="classId" title="班级ID"></vxe-table-column>
			<vxe-table-column field="className" title="班级名"></vxe-table-column>
			<vxe-table-column field="groupNum" title="小组序号"></vxe-table-column>
			<vxe-table-column title="组长信息">
				<vxe-table-column field="leader.userId" title="学号"></vxe-table-column>
				<vxe-table-column field="leader.name" title="姓名"></vxe-table-column>
			</vxe-table-column>
			<vxe-table-column type="expand" title="组员信息">
				<template v-slot:content="{ row, rowIndex }">
					<vxe-table border :data="tableData[rowIndex].members">
						<vxe-table-column field="userId" title="学号"></vxe-table-column>
						<vxe-table-column field="name" title="姓名"></vxe-table-column>
					</vxe-table>
				</template>
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
		inject: ['reload'],
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
					status: '',
					data: []
				}
			}
		},
		created() {
			this.init();
		},
		methods: {
			getResponse() {
				var self = this;
				axios.get(api.adminTeamList, null)
				.then(function(res) {
					if (res.status == 200 && res.data.status == 1) {
						self.response = res.data;
						self.tableData = self.response.data;
					} else {
						console.log(res.data.msg);
					}
				}).catch(function(error) {
					console.log(error);
				})
			},
			init() {
				this.getResponse();
			}
		}
	}
</script>

<style>
</style>
