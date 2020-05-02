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
		<vxe-table border show-header-overflow show-overflow highlight-hover-row :align="allAlign" :data="tableData">
			<vxe-table-column field="userId" title="用户ID"></vxe-table-column>
			<vxe-table-column field="userName" title="用户名"></vxe-table-column>
			<vxe-table-column field="classId" title="班级ID"></vxe-table-column>
			<vxe-table-column field="className" title="班级名"></vxe-table-column>
			<vxe-table-column field="groupId" title="小组ID"></vxe-table-column>
			<vxe-table-column field="groupName" title="小组名"></vxe-table-column>
			<vxe-table-column field="content" title="详情信息"></vxe-table-column>
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
		data () {
			return {
				allAlign: null,
                title: "个人历次得分",
				tableData: [
				],
                request: {
                },
                response: {
					status:1,
					data:[
						{
							userId:"221701000",
							userName:"张三",
							classId:1,
							className:"2020软件工程S班",
							groupId:1,
							groupName:"第一组",
							content: {
								list:[
									{
										evaluationInnerId:1,
										evaluationInnerName:"第一次团队合作_组内评分表",
										decision:"前端",//分工
										contribution:40//贡献率
									},
									{
										evaluationInnerId:2,
										evaluationInnerName:"第二次团队合作_组内评分表",
										decision:"前端",//分工
										contribution:60//贡献率
									}
								]
							}
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
				axios.get(api.adminUserScoreList,null)
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
            }
        }
        
	}
</script>

<style>
</style>
