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
				<vxe-table-column title="操作">
					<template v-slot="{ row }">
						<button type="button" class="btn btn-info" @click="showDetail(row)" data-toggle="modal" data-target="#UpdateModal">详情</button>
					</template>
				</vxe-table-column>
			</vxe-table>
		</div>
		
		<vxe-modal v-model="showDetails" title="查看详情" width="600" height="400" :mask="false" :lock-view="false" resize destroy-on-close>
			<vxe-grid
			 border
			 keep-source
			 resizable
			 ref="xTable"
			 :columns="tableColumn"
			 :data="detailData">
			</vxe-grid>
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
				tableColumn: [],
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
				var i;
				for(i=3;i<row.content.tableColumn.length-2;i++) {
					var str = i.toString();
					self.tableColumn[i] = {
						field: i,
						title: row.content.tableColumn[i]+"("+row.content.maxScore[i]+")"
					};
				}
				self.tableColumn[i] = {
					field: i,
					title: '总分'
				};
				self.tableColumn[++i] = {
					field: i,
					title: '建议'
				};
				//表体
				for(var i=0;i<row.content.tableData.length;i++) {
					var item = []
					for(var j=0;j<row.content.tableColumn.length;j++) {
						var str = j;
						item[str] = row.content.tableData[i][j];
					}
					self.tableData[i] = item;
				}
				this.showDetails = true
			},
			showDetail(row) {
				var i;
				for(i=3;i<row.content.tableColumn.length-2;i++) {
					var str = i.toString();
					this.tableColumn[i] = {
						field: i,
						title: row.content.tableColumn[i]+"("+row.content.maxScore[i]+")"
					};
				}
				this.tableColumn[i] = {
					field: i,
					title: '总分'
				};
				this.tableColumn[++i] = {
					field: i,
					title: '建议'
				};
				//表体
				for(var i=0;i<row.content.tableData.length;i++) {
					var item = []
					for(var j=0;j<row.content.tableColumn.length;j++) {
						var str = j;
						item[str] = row.content.tableData[i][j];
					}
					this.detailData[i] = item;
				}
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
						for(var i=0; i<self.tableData.length; i++) {
							self.tableData[i].submitTime = self.getDate(self.tableData[i].submitTime);
						}
					} else {
						console.log(res.data.msg);
					}
				}).catch(function(error) {
					console.log(error);
				})
			},
			init() {
				this.getRequest();
				this.getResponse();
			},
			getDate(source) {
				var timeStamp = new Date(parseInt(source*1000));
				var year = timeStamp.getFullYear();
				var month = timeStamp.getMonth() + 1;
				var date = timeStamp.getDate();
				var h = timeStamp.getHours();
				var m = timeStamp.getMinutes();
				var s = timeStamp.getSeconds();
				var time = year+"-"+month+"-"+date+" "+h+":"+m+":"+s;
				return time;
			}
		}

	}
</script>

<style>
</style>
