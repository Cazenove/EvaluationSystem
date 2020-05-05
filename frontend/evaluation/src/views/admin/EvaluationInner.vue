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
			<vxe-table border show-header-overflow show-overflow highlight-hover-row :align="allAlign" :data="tableData" @cell-click="cellClickEvent">
				<vxe-table-column field="submitInnerId" title="提交记录ID"></vxe-table-column>
				<vxe-table-column field="groupId" title="提交小组"></vxe-table-column>
				<vxe-table-column field="evaluationInnerId" title="组内评分表ID"></vxe-table-column>
				<vxe-table-column field="submitTime" title="提交时间"></vxe-table-column>
				<vxe-table-column>
					<button class="btn btn-info">详情</button>
				</vxe-table-column>
			</vxe-table>
		</div>
		
		<vxe-modal v-model="showDetails" title="查看详情" width="600" height="400" :mask="false" :lock-view="false" resize>
			<vxe-table
			 highlight-hover-row
			 highlight-current-row
			 border 
			 :data="detailData" >
				<vxe-table-column field="userId" title="学号"></vxe-table-column>
				<vxe-table-column field="userName" title="用户名"></vxe-table-column>
				<vxe-table-column field="decision" title="分工"></vxe-table-column>
				<vxe-table-column field="contribution" title="贡献率"></vxe-table-column>
			</vxe-table>
		</vxe-modal>
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
				showDetails: false,
				allAlign: null,
                title: "组内评价表提交记录",
				tableData: [
				],
				detailData: [
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
							submitTime:"2020-01-01",//提交时间
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
						},
						{
							submitInnerId:2,//组内评价表提交编号
							groupId:4,//小组id
							evaluationInnerId:1,
							submitTime:"2020-01-02",//提交时间
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
			cellClickEvent ({ row }) {
				this.detailData = row.content;
				this.showDetails = true
			},
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
