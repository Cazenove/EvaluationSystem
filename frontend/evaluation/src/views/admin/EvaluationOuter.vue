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
			<vxe-table-column field="submitOuterId" title="提交记录ID"></vxe-table-column>
			<vxe-table-column field="groupId" title="提交小组"></vxe-table-column>
			<vxe-table-column field="evaluationOuterId" title="组间评分表ID"></vxe-table-column>
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
                title: "组间评分表提交记录",
				tableData: [
				],
                request: {
                },
                response: {
					status:1,
					data:[
						{
							submitOuterId:1,//组间评价表提交编号
							groupId:1,//小组id
							evaluationOuterId:1,//组间评价表id
							submitTime:"",//提交时间
							content: [
								{
									groupId:1,
									groupName:"第一组",
									score:100,//总分
									content:[
										{
											item:"创新性",
											maxScore:40,
											score:40,//未填状态
										},
										{
											item:"实用性",
											maxScore:60,
											score:60,
										}
									],
									suggestion:"对第一组的建议..."
								},
								{
									groupId:2,
									groupName:"第二组",
									score:98,//总分
									content:[
										{
											item:"创新性",
											maxScore:40,
											score:39,//未填状态
										},
										{
											item:"实用性",
											maxScore:60,
											score:59,
										}
									],
									suggestion:"对第二组的建议..."
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
            getRequest() {
            },
            getResponse() {
				var self = this;
				axios.get(api.adminEvaluationOuterSubmit,null)
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
