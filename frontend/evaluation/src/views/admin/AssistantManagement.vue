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
			<div style="margin-bottom: 20px;">
				<h1>{{this.title}}</h1>
			</div>
			<vxe-table border show-header-overflow show-overflow highlight-hover-row :align="allAlign" :data="tableData">
				<vxe-table-column field="assistantId" title="助教ID"></vxe-table-column>
				<vxe-table-column field="assistantName" title="姓名"></vxe-table-column>
				<vxe-table-column field="telephone" title="电话"></vxe-table-column>
				<vxe-table-column field="classId" title="管理的班级"></vxe-table-column>
				<vxe-table-column field="password" title="密码"></vxe-table-column>
			</vxe-table>
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
		data() {
			return {
				allAlign: null,
				title: "助教管理",
				tableData: [],
				request: {},
				response: {
					status: 1,
					data: [{
							assistantId: "221701501",
							password: "123456",
							assistantName: "老王",
							telephone: "13200000000",
							classId: 1
						},
						{
							assistantId: "221701502",
							password: "123456",
							assistantName: "老李",
							telephone: "13200000001",
							classId: 2
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
				axios.get(api.adminAssistantList, null)
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
