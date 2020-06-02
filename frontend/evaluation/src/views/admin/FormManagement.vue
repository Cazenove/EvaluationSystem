<template>
	<div id="ScoreManagement">
		<ManageNav></ManageNav>
		<nav aria-label="breadcrumb">
			<ol class="breadcrumb">
				<li class="breadcrumb-item"><a href="#">主页</a></li>
				<li class="breadcrumb-item active" aria-current="page">评分表管理</li>
			</ol>
		</nav>
		
		<div class="container col-md-10 offset-md-1" style="margin: 50px auto;">
			<h1>评分表管理</h1>
			<vxe-toolbar>
				<template v-slot:buttons>
					<button class="btn btn-primary" @click="createForm">新增评分表</button>
				</template>
			</vxe-toolbar>
			<vxe-toolbar>
				<template v-slot:buttons>
					<vxe-input v-model="filterName" type="search" placeholder="快速搜索"></vxe-input>
					<vxe-button @click="exportSelectEvent">导出选中</vxe-button>
				</template>
				<template v-slot:tools>
					<vxe-button data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample" >条件搜索</vxe-button>
				</template>
			</vxe-toolbar>
			
			<div class="collapse" id="collapseExample">
			<div class="card card-body">
			<vxe-toolbar>
				<template v-slot:buttons>
					
					<el-row :gutter="20">
						<el-col :span="4">
							<el-input offser="3" placeholder="小组ID" v-model="searchInfo.groupId"></el-input>
						</el-col>
						<el-col :span="4">
							<el-input offser="3" placeholder="小组名" v-model="searchInfo.name"></el-input>
						</el-col>
						<el-col :span="4">
							<el-select offser="3" placeholder="班级" v-model="searchInfo.classId">
								<el-option :value="item" v-for="item in classList" :key="item" :label="item"></el-option>
							</el-select>
						</el-col>
						<button class="btn-primary btn" style="margin-left: 20px;" @click="search()">搜索</button>
						<button class="btn-light btn" style="margin-left: 20px;" @click="resetSearch()">重置搜索</button>
					</el-row>
				</template>
			</vxe-toolbar>
			</div>
			</div>
			<vxe-table
			 :data="list"
			 ref="xTable"
			 border
			 show-header-overflow
			 show-overflow
			 highlight-hover-row
			 :align="allAlign">
				<vxe-table-column type="checkbox" width="60"></vxe-table-column>
				<vxe-table-column field="evaluationOuterId" title="评分表ID"></vxe-table-column>
				<vxe-table-column field="name" title="评分表名称"></vxe-table-column>
				<vxe-table-column field="classId" title="班级" :formatter="toClassName"></vxe-table-column>
				<vxe-table-column field="releaseTime" title="发布时间"></vxe-table-column>
				<vxe-table-column field="endTime" title="截止时间"></vxe-table-column>
				<vxe-table-column title="操作" width="150" show-overflow>
					<template v-slot="{ row }">
						<button type="text" class="btn btn-info" @click="detailsEvent(row)">详情</button>
						&nbsp;
						<button type="text" class="btn btn-danger" @click="removeEvent(row)">删除</button>
					</template>
				</vxe-table-column>
			</vxe-table>
		</div>
		<vxe-modal v-model="showDetails" title="查看详情" width="600" height="400" :mask="false" :lock-view="false" resize destroy-on-close>
			<vxe-grid
			 border
			 resizable
			 keep-source
			 :columns="tableColumn"
			 :data="detailData">
			</vxe-grid>
		</vxe-modal>
		<vxe-modal v-model="showCreate" title="创建评分表" width="850" height="600" :mask="false" :lock-view="false" resize destroy-on-close>
			<CreateForm />
		</vxe-modal>
	</div>
</template>

<script>
	import axios from 'axios'
	import api from '../../router/httpConfig.js'
	import ManageNav from '../../components/ManageNav.vue'
	import CreateForm from '../../components/CreateForm.vue'
	import XEUtils from 'xe-utils'
	export default {
		inject: ['reload'],
		name: "FormManagement",
		components: {
			ManageNav,
			CreateForm
		},
		data() {
			return {
				searchInfo: {
					evaluationOuterId: null,
					name: null,
					classId: null,
				},
				filterName: '',
				showDetails: false,
				showCreate: false,
				allAlign: null,
				response: {
					status:'',
					data: []
				},
				tableData: [],
				detailData: [],
				tableColumn: [],
				classList: {}
			}
		},
		created() {
			this.getClassList();
			setTimeout(() => {
				this.init()
			}, 500);
		},
		methods: {
			toClassName({cellValue}) {
				return this.classList[cellValue];
			},
			createForm() {
				this.showCreate = true;
			},
			init() {
				this.getResponse();
			},
			getClassList() {
				var self = this;
				axios.get(api.adminClassList, null)
				.then(function(res) {
					for(var i=0; i<res.data.data.length; i++) {
						self.classList[res.data.data[i].classId] = res.data.data[i].className;
					}
				}).catch(function(error) {
					console.log(error);
				})
			},
			getResponse() {
				var self = this;
				axios.get(api.adminEvaluationDetails, null)
				.then(function(res) {
					if (res.status == 200 && res.data.status == 1) {
						for (let item of res.data.data){
							if(item.releaseTime != "")
							{
								var timeStamp = new Date(parseInt(item.releaseTime*1000));
								var year = timeStamp.getFullYear();
								var month = timeStamp.getMonth() + 1;
								var date = timeStamp.getDate();
								var h = timeStamp.getHours();
								var m = timeStamp.getMinutes();
								var s = timeStamp.getSeconds();
								var time = year+"-"+month+"-"+date+" "+h+":"+m+":"+s;
							}
							item.releaseTime = time;
						}
						for (let item of res.data.data){
							if(item.endTime != "")
							{
								var timeStamp = new Date(parseInt(item.endTime*1000));
								var year = timeStamp.getFullYear();
								var month = timeStamp.getMonth() + 1;
								var date = timeStamp.getDate();
								var h = timeStamp.getHours();
								var m = timeStamp.getMinutes();
								var s = timeStamp.getSeconds();
								var time = year+"-"+month+"-"+date+" "+h+":"+m+":"+s;
							}
							item.endTime = time;
						}
						self.response = res.data;
						self.tableData = self.response.data;
						for(var i = 0; i < self.tableData.length; i++) {
							self.tableData[i].classId = self.tableData[i].classInfo.classId;
						}
					} else {
						console.log(res.data.msg);
					}
				}).catch(function(error) {
					console.log(error);
				})
			},
			detailsEvent(row) {
				for(var i=0;i<row.content.tableColumn.length;i++) {
					var str = 'name'+i;
					this.tableColumn[i]={
						field: str,
						title: row.content.tableColumn[i]+"("+row.content.maxScore[i]+")"
					};
				}
				for(var i=0;i<row.content.tableData.length;i++) {
					var item = []
					for(var j=0;j<row.content.tableColumn.length;j++) {
						var str = 'name'+j;
						item[str] = row.content.tableData[i][j];
					}
					this.detailData[i] = item;
				}
				this.showDetails = true
			},
			removeEvent(row) {
				this.$XModal.confirm('您确定要删除这份评分表吗?删除评分表的同时会删除提交记录，请慎重考虑').then(type => {
					if (type === 'confirm') {
						var self = this;
						axios.post(api.adminEvaluationDelete,{
							evaluationOuterId: row.evaluationOuterId
						}).then(function(res) {
							if(res.data.status == 1) {
								alert("删除成功！");
							} else {
								alert(res.msg);
							}
							self.reload();
						}).catch(function(error) {
							console.log(error);
						})
					}
				})
			},
			exportSelectEvent () {
				this.$refs.xTable.exportData({
					data: this.$refs.xTable.getCheckboxRecords()
				})
			}
		},
		computed:{
			list() {
				const filterName = XEUtils.toString(this.filterName).trim().toLowerCase()
				if (filterName) {
					const filterRE = new RegExp(filterName, 'gi')
					const searchProps = ['evaluationOuterId', 'name']
					const rest = this.tableData.filter(item => searchProps.some(key => XEUtils.toString(item[key]).toLowerCase().indexOf(
						filterName) > -1))
					return rest.map(row => {
						const item = Object.assign({}, row)
						// searchProps.forEach(key => {
						//   item[key] = XEUtils.toString(item[key]).replace(filterRE, match => `<span class="keyword-lighten">${match}</span>`)
						// })
						return item
					})
				}
				return this.tableData
			}
		}
	}
</script>

<style>
</style>
