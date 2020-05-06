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
			<vxe-table v-if="tableData" border show-header-overflow show-overflow highlight-hover-row :align="allAlign" :data="tableData"
			 :expand-config="{accordion: true}">
				<vxe-table-column field="submitOuterId" title="提交记录ID"></vxe-table-column>
				<vxe-table-column field="groupId" title="提交小组"></vxe-table-column>
				<vxe-table-column field="evaluationOuterId" title="组间评分表ID"></vxe-table-column>
				<vxe-table-column field="submitTime" title="提交时间"></vxe-table-column>
				<vxe-table-column type="expand" title="内容">
					<template v-slot:content="{row, rowIndex}">
						<vxe-table
						 highlight-hover-row
						 highlight-current-row
						 border 
						 :data="tableData[rowIndex].content" 
						 @cell-click="cellClickEvent">
							<vxe-table-column field="groupId" title="小组ID"></vxe-table-column>
							<vxe-table-column field="groupName" title="小组名"></vxe-table-column>
							<vxe-table-column field="score" title="总分"></vxe-table-column>
							<vxe-table-column field="suggestion" title="建议"></vxe-table-column>
							<vxe-table-column>
								<button class="btn btn-info">详情</button>
							</vxe-table-column>
						</vxe-table>
					</template>
				</vxe-table-column>
			</vxe-table>
		</div>
		
		<vxe-modal v-model="showDetails" title="查看详情" width="600" height="400" :mask="false" :lock-view="false" resize>
			<vxe-table
			 highlight-hover-row
			 highlight-current-row
			 border 
			 :data="detailData" >
				<vxe-table-column field="item" title="评分项"></vxe-table-column>
				<vxe-table-column field="maxScore" title="分值"></vxe-table-column>
				<vxe-table-column field="score" title="得分"></vxe-table-column>
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
		data() {
			return {
				showDetails: false,
				allAlign: null,
				title: "组间评分表提交记录",
				tableData: [],
				detailData: [],
				request: {},
				response: {
					status: 1,
					data: []
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
			getRequest() {},
			getResponse() {
				var self = this;
				axios.get(api.adminEvaluationOuterSubmit, null)
				.then(function(res) {
					if (res.status == 200 && res.data.status == 1) {
						self.response = res.data;
						self.tableData = self.response.data;
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
