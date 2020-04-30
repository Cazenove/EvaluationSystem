<template>
	<div id="ScoreManagement">
		<ManageNav></ManageNav>
		<nav aria-label="breadcrumb">
			<ol class="breadcrumb">
				<li class="breadcrumb-item"><a href="#">主页</a></li>
				<li class="breadcrumb-item active" aria-current="page">评分管理</li>
			</ol>
		</nav>
		<h1 class="offset-md-1">评分管理</h1>
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
	import ManageNav from '../components/ManageNav.vue'
	export default {
		name: "ScoreManagement",
		components: {
			ManageNav,
		},
		data () {
			return{
				tableData: [
					{name: "第一组团队作业组间评分表", classId: "1", groupId: "1", "releaseTime": "2020-3-22"}
				]
			}
		}
	}
</script>

<style>
</style>
