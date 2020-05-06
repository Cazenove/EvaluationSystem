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
		inject: ['reload'],
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
                response: {
					status:'',
					data:[]
				}
			}
		},
        created() {
            this.init();
        },
        methods: {
			cellClickEvent ({ row }) {
				this.detailData = row.content;
				this.showDetails = true
			},
            getResponse() {
				var self = this;
				axios.get(api.adminEvaluationInnerSubmit,null)
				.then(function(res) {
					if(res.status == 200 && res.data.status == 1) {
						self.response = res.data;
						self.tableData = self.response.data;
					}
					else {
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
