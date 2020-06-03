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
									<el-input offser="3" placeholder="提交记录ID" v-model="searchInfo.submitInnerId"></el-input>
								</el-col>
								<el-col :span="4">
									<el-select offser="3" placeholder="班级" v-model="searchInfo.classId" @change="classOptionChange(searchInfo)">
										<el-option :value="item.classId" v-for="item in classList" :key="item.classId" :label="item.className"></el-option>
									</el-select>
								</el-col>
								<el-col :span="3">
									<el-select offser="3" placeholder="小组" v-model="searchInfo.groupId">
										<el-option v-for="n of searchInfo.groupNum" :value="n" :key="n" :label="teamList[n].groupName"></el-option>
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
			 border
			 show-header-overflow
			 show-overflow
			 highlight-hover-row
			 :align="allAlign"
			 :data="list"
			 ref="xTable"
			 @cell-click="cellClickEvent">
				<vxe-table-column type="checkbox" width="60"></vxe-table-column>
				<vxe-table-column field="submitInnerId" title="提交记录ID"></vxe-table-column>
				<vxe-table-column field="groupId" title="班级" :formatter="toClassName"></vxe-table-column>
				<vxe-table-column field="groupId" title="小组序号"></vxe-table-column>
				<vxe-table-column field="groupId" title="小组名" :formatter="toGroupName"></vxe-table-column>
				<vxe-table-column field="evaluationInnerId" title="评分表" :formatter="toFormName"></vxe-table-column>
				<vxe-table-column field="submitTime" title="提交时间" :formatter="toTime"></vxe-table-column>
				<vxe-table-column>
					<button class="btn btn-info">详情</button>
				</vxe-table-column>
			</vxe-table>
		</div>
		
		<vxe-modal v-model="showDetails" title="查看详情" width="600" height="400" :mask="false" :lock-view="false" resize destroy-on-close>
			<vxe-toolbar>
				<template v-slot:buttons>
					<vxe-input v-model="filterName" type="search" placeholder="快速搜索"></vxe-input>
					<vxe-button @click="exportSelectEvent">导出选中</vxe-button>
				</template>
			</vxe-toolbar>
			<vxe-table
			 highlight-hover-row
			 highlight-current-row
			 border 
			 :data="detailData" >
				<vxe-table-column field="userId" title="学号"></vxe-table-column>
				<vxe-table-column field="name" title="用户名"></vxe-table-column>
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
	import XEUtils from 'xe-utils'
	export default {
		inject: ['reload'],
		components: {
			ManageNav,
		},
		data () {
			return {
				data: [],
				filterName: '',
				showDetails: false,
				allAlign: null,
                title: "组内评价表提交记录",
				tableData: [],
				detailData: [],
				teamList: {},
				formList: {},
				classList: {},
				searchInfo:{
					submitInnerId:null,
					classId:null,
					groupId:null,
					groupNum:null,
				}
			}
		},
        created() {
            this.init();
			
        },
        methods: {
			classOptionChange(data){
				for(let value of this.classList){
					if(data.classId == value.classId){
						data.groupNum = value.groupNum;
					}
				}
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
			toTime({ cellValue }) {
				return this.getDate(cellValue);
			},
			cellClickEvent ({ row }) {
				for(var i=0; i<row.content.tableData.length; i++) {
					this.detailData[i] = {
						userId:row.content.tableData[i][0],
						name:row.content.tableData[i][1],
						decision:row.content.tableData[i][2],
						contribution:row.content.tableData[i][3]
					}
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
				axios.get(api.adminEvaluationInnerSubmit,null)
				.then(function(res) {
					if(res.status == 200 && res.data.status == 1) {
						console.log(res.data.data);
						self.tableData = res.data.data;
						self.data = res.data.data;
						// for(var i=0; i<self.tableData.length; i++) {
						// 	self.tableData[i].submitTime = self.getDate(self.tableData[i].submitTime);
						// }
						
					}
					else {
						console.log(res.data.msg);
					}
				}).catch(function(error) {
					console.log(error);
				})
            },
            init() {
				this.getClassList();
                this.getFormList();
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
					if (value.submitInnerId != null && this.searchInfo.submitInnerId != null && this.searchInfo.submitInnerId != "") {
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
					submitInnerId:null,
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
					const searchProps = ['submitInnerId', 'evaluationInnerId', 'groupId']
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
