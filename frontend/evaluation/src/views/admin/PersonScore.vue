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
		<vxe-table border show-header-overflow show-overflow highlight-hover-row :align="allAlign" :data="tableData">
			<vxe-table-column field="userId" title="用户ID"></vxe-table-column>
			<vxe-table-column field="userName" title="用户名"></vxe-table-column>
			<!-- <vxe-table-column field="classId" title="班级ID"></vxe-table-column> -->
			<vxe-table-column field="className" title="班级名"></vxe-table-column>
			<vxe-table-column field="groupId" title="小组ID"></vxe-table-column>
			<vxe-table-column field="groupName" title="小组名"></vxe-table-column>
			<vxe-table-column field="evaluationInnerId" title="评分表" :formatter="formatterForm"></vxe-table-column>
			<vxe-table-column field="content" title="贡献率"></vxe-table-column>
			<vxe-table-column field="decision" title="分工"></vxe-table-column>
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
		data () {
			return {
				allAlign: null,
                title: "个人历次得分",
				tableData: [],
				formList: {}
			}
		},
        created() {
            this.init();
        },
        methods: {
			formatterForm({ cellValue }) {
				return this.formList[cellValue];
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
            getResponse() {
				var self = this;
				axios.get(api.adminUserScoreList,null)
				.then(function(res) {
					if(res.status == 200 && res.data.status == 1) {
						self.tableData = res.data.data;
					}
					else {
						console.log(res.data.msg);
					}
				}).catch(function(error) {
					console.log(error);
				})
            },
            init() {
				this.getFormList();
            }
        }
        
	}
</script>

<style>
</style>
