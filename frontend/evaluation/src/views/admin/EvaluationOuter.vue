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
			<vxe-toolbar>
				<template v-slot:buttons>
					<vxe-input v-model="filterName" type="search" placeholder="快速搜索"></vxe-input>
					<vxe-button @click="exportSelectEvent">导出选中</vxe-button>
				</template>
				<template v-slot:tools>
					<vxe-button data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">条件搜索</vxe-button>
				</template>
			</vxe-toolbar>
			<div class="collapse" id="collapseExample">
				<div class="card card-body">
					<vxe-toolbar>
						<template v-slot:buttons>
							<el-row :gutter="20">
								<el-col :span="4">
									<el-input offser="3" placeholder="提交记录ID" v-model="searchInfo.submitOuterId"></el-input>
								</el-col>
								<el-col :span="4">
									<el-select offser="3" placeholder="班级" v-model="searchInfo.classId" @change="classOptionChange(searchInfo)">
										<el-option :value="item.classId" v-for="item in classList" :key="item.classId" :label="item.className"></el-option>
									</el-select>
								</el-col>
								<el-col :span="3">
									<el-select offser="3" placeholder="小组" v-model="searchInfo.groupId">
										<el-option v-for="item in groupOfClass" :value="item.groupId" :key="item.groupId" :label="item.groupName"></el-option>
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
			 v-if="tableData" 
			 border
			 show-header-overflow
			 show-overflow
			 highlight-hover-row
			 ref="xTable"
			 :align="allAlign"
			 :data="list"
			 :expand-config="{accordion: true}">
				<vxe-table-column type="checkbox" width="60"></vxe-table-column>
				<vxe-table-column field="submitOuterId" title="提交记录ID"></vxe-table-column>
				<vxe-table-column field="groupId" title="班级" :formatter="toClassName"></vxe-table-column>
				<vxe-table-column field="groupId" title="小组序号"></vxe-table-column>
				<vxe-table-column field="groupId" title="小组名" :formatter="toGroupName"></vxe-table-column>
				<vxe-table-column field="evaluationOuterId" title="评分表" :formatter="toFormName"></vxe-table-column>
				<vxe-table-column field="submitTime" title="提交时间" :formatter="toTime"></vxe-table-column>
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
	import XEUtils from 'xe-utils'
	export default {
		inject: ['reload'],
		components: {
			ManageNav,
		},
		data() {
			return {
				data: [],
				filterName: '',
				showDetails: false,
				allAlign: null,
				title: "组间评分表提交记录",
				tableColumn: [],
				tableData: [],
				detailData: [],
				teamList: {},
				formList: {},
				classList: {},
				searchInfo:{
					submitOuterId:null,
					classId:null,
					groupId:null,
					groupNum:null,
				},
				groupOfClass: [],
			}
		},
		created() {
			this.init();
		},
		methods: {
			classOptionChange(data){
				this.searchInfo.groupId = null;
				this.groupOfClass = [];
				for(let value in this.teamList){
					if(this.teamList[value].classId == this.searchInfo.classId){
						var item = {
							groupId: this.teamList[value].groupId,
							groupName: this.teamList[value].groupName,
							};
						this.groupOfClass.push(item);
					}
				}
			},
			toTime({ cellValue }){
				return this.getDate(cellValue);
			},
			toClassName({ cellValue }) {
				return this.teamList[cellValue].className;
			},
			toGroupName({ cellValue }) {
				return this.teamList[cellValue].groupName;
			},
			toFormName({ cellValue }) {
				return this.formList[cellValue];
			},
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
				for(i=1;i<row.content.tableColumn.length-1;i++) {
					var str = i.toString();
					this.tableColumn[i] = {
						field: i,
						title: row.content.tableColumn[i]+"("+row.content.maxScore[i]+")"
					};
				}
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
			getFormList() {
				var self = this;
				axios.get(api.adminEvaluationDetails,null)
				.then(function(res) {
					for(var i=0; i<res.data.data.length; i++) {
						self.formList[res.data.data[i].evaluationOuterId] = res.data.data[i].name;
					}
					self.getTeamList();
				}).catch(function(error) {
					console.log(error);
				})
			},
			getTeamList() {
				var self = this;
				axios.get(api.adminTeamList, null)
				.then(function(res) {
					if (res.status == 200 && res.data.status == 1) {
						for(var i=0; i<res.data.data.length; i++) {
							console.log(res.data.data);
							self.teamList[res.data.data[i].groupId] = res.data.data[i];
						}
					} else {
						console.log(res.data.msg);
					}
					self.getResponse();
				}).catch(function(error) {
					console.log(error);
				})
			},
			getClassList() {
				var self = this;
				axios.get(api.adminClassList, null)
				.then(function(res) {
					for(var i=0; i<res.data.data.length; i++) {
						self.classList = res.data.data;
						console.log(res.data.data);
					}
				}).catch(function(error) {
					console.log(error);
				})
			},
			getResponse() {
				var self = this;
				axios.get(api.adminEvaluationOuterSubmit, null)
				.then(function(res) {
					if (res.status == 200 && res.data.status == 1) {
						self.tableData = res.data.data;
						self.data = res.data.data;
						
					} else {
						console.log(res.data.msg);
					}
				}).catch(function(error) {
					console.log(error);
				})
			},
			init() {
				this.getFormList();
				this.getClassList();
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
			},
			exportSelectEvent () {
				this.$refs.xTable.exportData({
					data: this.$refs.xTable.getCheckboxRecords()
				})
			},
			search() {
				var data = this.data;
				this.tableData = [];
				for (let value of data) {
					let flag = 1;
					
					var className = null;
					for (let x of this.classList){
						if(this.searchInfo.classId == x.classId){
							className = x.className;
						}
					}
					if (value.submitOuterId != null && this.searchInfo.submitOuterId != null && this.searchInfo.submitOuterId != "") {
						flag = 0;
					}
					if (className !=  this.teamList[value.groupId].className && this.searchInfo.classId != null && this.searchInfo.classId != "") {
						flag = 0;
					}
					if (value.groupId != this.searchInfo.groupId && this.searchInfo.groupId != null && this.searchInfo.groupId != "") {
						flag = 0;
					}
					if (flag == 1) {
						this.tableData.push(value);
					}
				}
			
			},
			resetSearch() {
				this.tableData = this.data;
				this.searchInfo = {
					submitOuterId:null,
					classId:null,
					groupId:null,
					groupNum:null,
				}
			},
		},
		computed:{
			list() {
				const filterName = XEUtils.toString(this.filterName).trim().toLowerCase()
				if (filterName) {
					const filterRE = new RegExp(filterName, 'gi')
					const searchProps = ['submitOuterId', 'evaluationOuterId', 'groupId']
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
