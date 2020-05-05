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
			<vxe-table-column field="groupId" title="小组ID"></vxe-table-column>
			<vxe-table-column field="classId" title="班级ID"></vxe-table-column>
			<vxe-table-column field="groupNum" title="小组序号"></vxe-table-column>
			<vxe-table-column field="groupName" title="小组名"></vxe-table-column>
			<vxe-table-column field="content" title="内容"></vxe-table-column>
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
		data () {
			return {
				allAlign: null,
                title: "小组历次得分",
				tableData: [
				],
                request: {
                },
                response: {
					status:1,
					data:[
						{
							groupId:1,//小组表中的id
							classId:1,
							groupNum:1,
							groupName:"第一组",//小组名
							content: {
								list: [
									{
										evaluationOuterId:1,//组间评价表号
										name:"第一次团队作业_组间评价表",//评价表的名称
										score:85//该次的得分
									},
									{
										evaluationOuterId:2,
										name:"第二次团队作业_组间评价表",
										score:87
									}
								]
							}
						},
						{
							groupId:8,//小组表中的id
							classId:2,
							groupNum:1,//可能是另一个班的第一组
							groupName:"第一组",//小组名
							content:[
								{
									evaluationOuterId:1,//组间评价表号
									name:"第一次团队作业_组间评价表",//评价表的名称
									score:85//该次的得分
								},
								{
									evaluationOuterId:2,
									name:"第二次团队作业_组间评价表",
									score:87
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
				axios.get(api.adminGroupScoreList,null)
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
