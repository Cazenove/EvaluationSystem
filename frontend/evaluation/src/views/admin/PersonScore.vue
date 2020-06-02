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
								<el-input offser="3" placeholder="学号" v-model="searchInfo.userId"></el-input>
							</el-col>
							<el-col :span="4">
								<el-input offser="3" placeholder="姓名" v-model="searchInfo.name"></el-input>
							</el-col>
							<el-col :span="4">
								<el-select offser="3" placeholder="班级" v-model="searchInfo.classId" @change="classOptionChange(searchInfo)">
									<el-option :value="item.classId" v-for="item in classOption" :key="item.classId" :label="item.className"></el-option>
								</el-select>
							</el-col>
							<el-col :span="3">
								<el-select offser="3" placeholder="小组" v-model="searchInfo.groupId">
									<el-option v-for="n of searchInfo.groupNum" :value="n" :key="n">第{{n}}小组</el-option>
								</el-select>
							</el-col>
							<el-col :span="3">
								<el-select offser="3" placeholder="职务" v-model="searchInfo.status">
									<el-option v-for="item in statusOption" :key="item.value" :value="item.value" :label="item.label"></el-option>
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
		 ref="xTable"
		 :align="allAlign"
		 :data="tableData">
			<vxe-table-column type="checkbox" width="60"></vxe-table-column>
			<vxe-table-column field="userId" title="用户ID"></vxe-table-column>
			<vxe-table-column field="userId" title="用户名" :formatter="toUserName"></vxe-table-column>
			<vxe-table-column field="userId" title="班级名" :formatter="toClassName"></vxe-table-column>
			<vxe-table-column field="groupId" title="小组ID"></vxe-table-column>
			<vxe-table-column field="groupId" title="小组名" :formatter="toGroupName"></vxe-table-column>
			<vxe-table-column field="evaluationInnerId" title="评分表" :formatter="formatterForm"></vxe-table-column>
			<vxe-table-column field="content" title="贡献率"></vxe-table-column>
			<vxe-table-column field="decision" title="分工"></vxe-table-column>
		</vxe-table>
		</div>
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
				allAlign: null,
                title: "个人历次得分",
				tableData: [],
				formList: {},
				userList: {},
				classList: {},
				groupList: {},
				searchInfo: {}
			}
		},
        created() {
			this.getUserList();
			this.getClassList();
			this.getGroupList();
			setTimeout(() => {
				this.init()
			}, 500);
        },
        methods: {
			formatterForm({cellValue}) {
				return this.formList[cellValue];
			},
			toUserName({cellValue}) {
				return this.userList[cellValue].name;
			},
			toClassName({cellValue}) {
				return this.classList[this.userList[cellValue].classId].className;
			},
			toGroupName({cellValue}) {
				return this.groupList[cellValue];
			},
			getClassList() {
				var self = this;
				axios.get(api.adminClassList,null)
				.then(function(res) {
					if(res.status == 200 && res.data.status == 1) {
						for(var i=0; i<res.data.data.length; i++) {
							self.classList[res.data.data[i].classId] = res.data.data[i];
						}
					} else {
						console.log(res.data.msg);
					}
				}).catch(function(error) {
					console.log(error);
				})
			},
			getUserList() {
				var self = this;
				axios.get(api.adminUserList,null)
				.then(function(res) {
					if(res.status == 200 && res.data.status == 1) {
						for(var i=0; i<res.data.data.length; i++) {
							self.userList[res.data.data[i].userId] = res.data.data[i];
						}
					} else {
						console.log(res.data.msg);
					}
				}).catch(function(error) {
					console.log(error);
				})
			},
			getGroupList() {
				var self = this;
				axios.get(api.adminTeamList,null)
				.then(function(res) {
					if(res.status == 200 && res.data.status == 1) {
						for(var i=0; i<res.data.data.length; i++) {
							self.groupList[res.data.data[i].groupId] = res.data.data[i].groupName;
						}
					} else {
						console.log(res.data.msg);
					}
				}).catch(function(error) {
					console.log(error);
				})
			},
			getFormList() {
				var self = this;
				axios.get(api.adminEvaluationDetails,null)
				.then(function(res) {console.log(res);
					if(res.status == 200 && res.data.status == 1) {
						for(var i=0; i<res.data.data.length; i++) {
							self.formList[res.data.data[i].evaluationOuterId] = res.data.data[i].name;
						}
						self.getResponse();
					} else {
						console.log(res.data.msg);
					}
				}).catch(function(error) {
					console.log(error);
				})
			},
            getResponse() {
				var self = this;
				axios.get(api.adminUserScoreList,null)
				.then(function(res) {
					if(res.status == 200 && res.data.status == 1) {
						self.tableData = res.data.data;
					} else {
						console.log(res.data.msg);
					}
				}).catch(function(error) {
					console.log(error);
				})
            },
            init() {
				this.getFormList();
            },
			exportSelectEvent () {
				this.$refs.xTable.exportData({
					data: this.$refs.xTable.getCheckboxRecords()
				})
			}
        }
        
	}
</script>

<style>
</style>
