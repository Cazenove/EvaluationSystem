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
			<vxe-table-column field="groupSuggestionId" title="建议ID"></vxe-table-column>
			<vxe-table-column field="groupId" title="小组ID"></vxe-table-column>
			<vxe-table-column field="evaluationOuterId" title="评分表ID"></vxe-table-column>
			<vxe-table-column field="suggestion" title="建议内容"></vxe-table-column>
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
                title: "建议管理",
				tableData: [
				],
                request: {
                },
                response: {
					status:1,
					data:[
						{
							groupSuggestionId:1,//小组建议表内的ID
							groupId:1,//表示是这个小组收到的建议
							evaluationOuterId:1,//表示是针对这一轮作业的建议
							suggestion:"建议内容1"
						},
						{
							groupSuggestionId:2,
							groupId:1,
							evaluationOuterId:1,
							suggestion:"建议内容2"
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
				axios.get(api.adminSuggestionList,null)
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
