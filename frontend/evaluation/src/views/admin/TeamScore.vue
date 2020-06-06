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
			<div class="card card-body"  style="border: none;padding: 0;">
				<vxe-toolbar>
					<template v-slot:buttons>
						<el-row :gutter="20">
							<el-col :span="4">
								<el-select offser="3" placeholder="班级" v-model="searchInfo.classId">
									<el-option :value="item.classId" v-for="item in classOption" :key="item.classId" :label="item.className"></el-option>
								</el-select>
							</el-col>
							<el-col :span="4">
								<el-input offser="3" placeholder="小组名" v-model="searchInfo.groupName"></el-input>
							</el-col>
							<el-col :span="4">
								<el-select offser="3" placeholder="评分表" v-model="searchInfo.evaluationOuterId">
									<el-option :value="item.evaluationOuterId" v-for="item in formOption" :key="item.evaluationOuterId" :label="item.name"></el-option>
								</el-select>
							</el-col>
							<vxe-button status="primary" style="margin-left: 20px;" @click="search()">搜索</vxe-button>
							<vxe-button style="margin-left: 20px;" @click="resetSearch()">重置搜索</vxe-button>
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
		 ref="xTable"
		 :align="allAlign"
		 :data="list">
			<vxe-table-column type="checkbox" width="60"></vxe-table-column>
			<vxe-table-column field="classId" title="班级" :formatter="formatterClass"></vxe-table-column>
			<vxe-table-column field="groupNum" title="小组序号"></vxe-table-column>
			<vxe-table-column field="evaluationOuterId" title="评分表" :formatter="formatterForm"></vxe-table-column>
			<vxe-table-column field="groupName" title="小组名"></vxe-table-column>
			<vxe-table-column field="content" title="得分"></vxe-table-column>
		</vxe-table>
		</div>
    </div>
</template>

<script>
	import axios from 'axios'
	import api from '../../router/httpConfig.js'
	import ManageNav from '../../components/ManageNav.vue'
	import XEUtils from 'xe-utils'
	import es6 from 'es6'
	export default {
		inject: ['reload'],
		components: {
			ManageNav,
		},
		data () {
			return {
				filterName: '',
				allAlign: null,
                title: "小组历次得分",
				tableData: [],
				data: [],
                request: {},
				classList: {},
				classOption: [],
				formList: {},
				formOption: [],
				searchInfo: {
					classId: null,
					groupId: null,
					groupName: null,
					evaluationOuterId: null
				}
			}
		},
        created() {
            this.init();
        },
        methods: {
			formatterClass({ cellValue }) {
				return this.classList[cellValue];
			},
			formatterForm({ cellValue }) {
				return this.formList[cellValue];
			},
            getResponse() {
				var self = this;
				axios.get(api.adminGroupScoreList,null)
				.then(function(res) {
					if(res.status == 200 && res.data.status == 1) {
						self.tableData = res.data.data;
						self.data = res.data.data;
					}
					else {
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
					for(var i=0; i<res.data.data.length; i++) {
						self.classList[res.data.data[i].classId] = res.data.data[i].className;
						var option = {
							classId: res.data.data[i].classId,
							className: res.data.data[i].className
						}
						self.classOption.push(option);
					}
					self.getEvaluationOuterList();
				}).catch(function(error) {
					console.log(error);
				})
			},
			getEvaluationOuterList() {
				var self = this;
				axios.get(api.adminEvaluationDetails,null)
				.then(function(res) {
					for(var i=0; i<res.data.data.length; i++) {
						self.formList[res.data.data[i].evaluationOuterId] = res.data.data[i].name;
						var option = {
							evaluationOuterId: res.data.data[i].evaluationOuterId,
							name: res.data.data[i].name
						}
						self.formOption.push(option);
					}
					self.getResponse();
				}).catch(function(error) {
					console.log(error);
				})
			},
            init() {
				this.getClassList();
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
					
					var groupName = new RegExp(this.searchInfo.groupName);
					
					if (value.groupName.match(groupName) == null && this.searchInfo.groupName != null && this.searchInfo.groupName != "") {
						flag = 0;
					}
					if (value.classId != this.searchInfo.classId && this.searchInfo.classId != null && this.searchInfo.classId != "") {
						flag = 0;
					}
					if (value.groupId != this.searchInfo.groupId && this.searchInfo.groupId != null && this.searchInfo.groupId != "") {
						flag = 0;
					}
					if (value.evaluationOuterId != this.searchInfo.evaluationOuterId && this.searchInfo.evaluationOuterId != null && this.searchInfo.evaluationOuterId != "") {
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
					classId: null,
					groupId: null,
					className: null,
					evaluationOuterId: null
				}
			},
        },
        computed:{
        	list() {
        		const filterName = XEUtils.toString(this.filterName).trim().toLowerCase()
        		if (filterName) {
        			const filterRE = new RegExp(filterName, 'gi')
        			const searchProps = ['groupName', 'evaluationOuterId', 'content']
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
	.el-input__inner{
		height: 34px;
	}
</style>
