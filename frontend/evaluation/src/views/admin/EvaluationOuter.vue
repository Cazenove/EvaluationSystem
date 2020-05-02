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
		<vxe-table v-if="tableData" border show-header-overflow show-overflow highlight-hover-row :align="allAlign" :data="tableData"
		 @toggle-row-expand="getRow1" :expand-config="{accordion: true}">
			<vxe-table-column field="submitOuterId" title="提交记录ID"></vxe-table-column>
			<vxe-table-column field="groupId" title="提交小组"></vxe-table-column>
			<vxe-table-column field="evaluationOuterId" title="组间评分表ID"></vxe-table-column>
			<vxe-table-column field="submitTime" title="提交时间"></vxe-table-column>
			<vxe-table-column type="expand" title="内容">
				<template v-slot:content="{row, rowIndex}">
					<vxe-table @toggle-row-expand="getRow2" border :data="tableData[rowIndex].content">
						<vxe-table-column field="groupId" title="小组ID"></vxe-table-column>
						<vxe-table-column field="groupName" title="小组名"></vxe-table-column>
						<vxe-table-column field="score" title="总分"></vxe-table-column>
						<vxe-table-column field="suggestion" title="建议"></vxe-table-column>
						<vxe-table-column>
							<button
							 type="button" 
							 class="btn btn-primary" 
							 data-toggle="modal" 
							 data-target="#exampleModal" 
							 @click="details(row)">
								详情
							</button>
						</vxe-table-column>
					</vxe-table>
				</template>
			</vxe-table-column>
		</vxe-table>

		<!-- Modal -->
		<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title" id="exampleModalLabel">评分表详情</h5>
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">
						<vxe-table width="500px" border show-header-overflow show-overflow highlight-hover-row :align="allAlign" :data="modalData">
							<vxe-table-column field="item" title="提交记录ID" width="100px"></vxe-table-column>
							<vxe-table-column field="maxScore" title="提交小组" width="100px"></vxe-table-column>
							<vxe-table-column field="score" title="组间评分表ID" width="100px"></vxe-table-column>
						</vxe-table>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary" data-dismiss="modal">关闭</button>
					</div>
				</div>
			</div>
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
		data() {
			return {
				allAlign: null,
				row1: null,
				row2: null,
				title: "组间评分表提交记录",
				tableData: [],
				modalData: [],
				request: {},
				response: {
					status: 1,
					data: [{
							submitOuterId: 1, //组间评价表提交编号
							groupId: 1, //小组id
							evaluationOuterId: 1, //组间评价表id
							submitTime: "", //提交时间
							content: [{
									groupId: 1,
									groupName: "第一组",
									score: 100, //总分
									content: [{
											item: "创新性",
											maxScore: 40,
											score: 40, //未填状态
										},
										{
											item: "实用性",
											maxScore: 60,
											score: 60,
										}
									],
									suggestion: "对第一组的建议..."
								},
								{
									groupId: 2,
									groupName: "第二组",
									score: 99, //总分
									content: [{
											item: "创新性",
											maxScore: 40,
											score: 39, //未填状态
										},
										{
											item: "实用性",
											maxScore: 60,
											score: 59,
										}
									],
									suggestion: "对第二组的建议..."
								}
							]
						},
						{
							submitOuterId: 2, //组间评价表提交编号
							groupId: 2, //小组id
							evaluationOuterId: 1, //组间评价表id
							submitTime: "", //提交时间
							content: [{
									groupId: 1,
									groupName: "第一组",
									score: 98, //总分
									content: [{
											item: "创新性",
											maxScore: 40,
											score: 38, //未填状态
										},
										{
											item: "实用性",
											maxScore: 60,
											score: 38,
										}
									],
									suggestion: "对第一组的建议..."
								},
								{
									groupId: 2,
									groupName: "第二组",
									score: 97, //总分
									content: [{
											item: "创新性",
											maxScore: 40,
											score: 37, //未填状态
										},
										{
											item: "实用性",
											maxScore: 60,
											score: 37,
										}
									],
									suggestion: "对第二组的建议..."
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
			getRow1(row) {
				this.row1 = row.$rowIndex;
				console.log(this.row1);
			},
			getRow2(row) {
				this.row2 = row.$rowIndex;
				console.log(this.row2);
			},
			getRequest() {},
			getResponse() {
				var self = this;
				axios.get(api.adminEvaluationOuterSubmit, null)
					.then(function(res) {
						if (res.status === 1) {
							self.response = res;
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
			},
			details(row2) {
				console.log(row2);
				// this.row2 = row2;
				// this.modalData = this.tableData[this.row1].content[this.row2].content;
			}
		}

	}
</script>

<style>
</style>
