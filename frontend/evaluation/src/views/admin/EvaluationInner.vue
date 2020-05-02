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
			<vxe-table-column field="submitInnerId" title="提交记录ID"></vxe-table-column>
			<vxe-table-column field="groupId" title="提交小组"></vxe-table-column>
			<vxe-table-column field="evaluationInnerId" title="组内评分表ID"></vxe-table-column>
			<vxe-table-column field="submitTime" title="提交时间"></vxe-table-column>
			<vxe-table-column field="content" title="具体内容"></vxe-table-column>
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
                title: "组内评价表提交记录",
				tableData: [
				],
                request: {
                },
                response: {
					status:1,
					data:[
						{
							submitInnerId:1,//组内评价表提交编号
							groupId:3,//小组id
							evaluationInnerId:1,
							submitTime:"",//提交时间
							content: [
								{
									userId:"221701000",//id，学号
									userName:"张三",//姓名
									decision:"前端",//分工
									contribution:50//贡献率
								},
								{
									userId:"221701001",//id，学号
									userName:"李四",//姓名
									decision:"后端",//分工
									contribution:50//贡献率
								}
							]
						}
					]
				}
			}
		},
        created() {
            this.init();
			this.tableData = this.response.data
        },
        methods: {
            getRequest() {
            },
            getResponse() {
				var self = this;
				axios.get(api.adminEvaluationInnerSubmit,null)
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
