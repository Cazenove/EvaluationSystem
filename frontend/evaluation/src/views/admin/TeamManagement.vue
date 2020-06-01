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
				</template>
				<template v-slot:tools>
					<!-- <a class="btn btn-primary" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">
						条件搜索
					</a> -->
					<vxe-button data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample" >条件搜索</vxe-button>
				</template>
			</vxe-toolbar>
			<vxe-toolbar>
				<template v-slot:buttons>
					<vxe-input v-model="filterName" type="search" placeholder="快速搜索"></vxe-input>
					<vxe-button @click="exportSelectEvent">导出选中</vxe-button>
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
							<el-select offser="3" placeholder="班级" v-model="searchInfo.classId" @change="classOptionChange(searchInfo)">
								<el-option :value="item.classId" v-for="item in classList" :key="item.classId" :label="item.className"></el-option>
							</el-select>
						</el-col>
						<el-col :span="3">
							<el-select offser="3" placeholder="小组" v-model="searchInfo.groupId">
								<el-option v-for="n of searchInfo.groupNum" :value="n" :key="n">第{{n}}小组</el-option>
							</el-select>
						</el-col>
						<!-- <el-col :span="3">
							<el-select offser="3" placeholder="职务" v-model="searchInfo.status">
								<el-option v-for="item in statusOption" :key="item.value" :value="item.value" :label="item.label"></el-option>
							</el-select>
						</el-col> -->
						<button class="btn-primary btn" style="margin-left: 20px;" @click="search()">搜索</button>
						<button class="btn-light btn" style="margin-left: 20px;" @click="resetSearch()">重置搜索</button>
					</el-row>
					
				</template>
				
			</vxe-toolbar>
			</div>
			</div>
		<vxe-table
		 :align="allAlign"
		 :data="list"
		 ref="xTable"
		 border
		 resizable
		 row-key
		 highlight-hover-row
		 keep-source
		 :expand-config="{accordion: true}">
			<vxe-table-column type="checkbox" width="60"></vxe-table-column>
			<vxe-table-column field="groupId" title="小组ID"></vxe-table-column>
			<vxe-table-column field="groupName" title="小组名"></vxe-table-column>
			<vxe-table-column field="classId" title="班级ID"></vxe-table-column>
			<vxe-table-column field="className" title="班级"></vxe-table-column>
			<vxe-table-column field="groupNum" title="小组序号"></vxe-table-column>
			<vxe-table-column title="组长信息">
				<vxe-table-column field="leader.userId" title="学号"></vxe-table-column>
				<vxe-table-column field="leader.name" title="姓名"></vxe-table-column>
			</vxe-table-column>
			<vxe-table-column type="expand" title="组员信息">
				<template v-slot:content="{ row, rowIndex }">
					<vxe-table border :data="tableData[rowIndex].members">
						<vxe-table-column field="userId" title="学号"></vxe-table-column>
						<vxe-table-column field="name" title="姓名"></vxe-table-column>
					</vxe-table>
				</template>
			</vxe-table-column>
		</vxe-table>
		</div>
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
				data: '',
				filterName: '',
				allAlign: null,
				title: "小组管理",
				tableData: [],
				request: {},
				searchInfo: {
					groupId: null,
					groupName: null,
					classId: null,
					className: null,
					groupNum: null,
				},
				classList:{},
				response: {
					status: '',
					data: []
				}
			}
		},
		created() {
			this.getClassList();
			this.init();
		},
		methods: {
			getResponse() {
				var self = this;
				axios.get(api.adminTeamList, null)
				.then(function(res) {
					if (res.status == 200 && res.data.status == 1) {
						self.response = res.data;
						self.tableData = self.response.data;
						self.data = self.response.data;
					} else {
						console.log(res.data.msg);
					}
				}).catch(function(error) {
					console.log(error);
				})
			},
			getClassList() {
				var self = this;
				axios.get(api.adminClassList,null)
				.then(function(res) {
					self.classList = res.data.data;
					console.log(res.data.data);
				}).catch(function(error) {
					console.log(error);
				})
			},
			classOptionChange(data) {
				for (let value of this.classList) {
					if (data.classId == value.classId){
						data.groupNum = value.groupNum;
					}
				}
			},
			search() {
				var data = this.data;
				this.tableData = [];
				for(let value of data){
					let flag = 1;
					
					if (value.groupName != this.searchInfo.groupName && this.searchInfo.groupName != null && this.searchInfo.groupName != ""){
						flag = 0;
					}
					if (value.className != this.searchInfo.className && this.searchInfo.className != null && this.searchInfo.className != ""){
						flag = 0;
					}
					if (value.classId != this.searchInfo.classId && this.searchInfo.classId != null && this.searchInfo.classId != ""){
						flag = 0;
					}
					if (value.groupId != this.searchInfo.groupId && this.searchInfo.groupId != null && this.searchInfo.groupId != ""){
						flag = 0;
					}
					if (value.className != this.searchInfo.className && this.searchInfo.className != null && this.searchInfo.className != ""){
						flag = 0;
					}
					if (flag == 1){
						this.tableData.push(value);
					}
				}
				
			},
			resetSearch() {
				
				this.tableData = this.data;
				this.searchInfo = {
					groupId: null,
					groupName: null,
					classId: null,
					className: null,
					groupNum: null,
				}
			},
			init() {
				this.getResponse();
			},
			exportSelectEvent () {
				this.$refs.xTable.exportData({
					data: this.$refs.xTable.getCheckboxRecords()
				})
			}
		},
		computed:{
			list () {
				const filterName = XEUtils.toString(this.filterName).trim().toLowerCase()
				if (filterName) {
					const filterRE = new RegExp(filterName, 'gi')
					const searchProps = ['groupId', 'groupName', 'classId', 'className', 'groupNum', 'userId']
					const rest = this.tableData.filter(item => searchProps.some(key => XEUtils.toString(item[key]).toLowerCase().indexOf(filterName) > -1))
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
