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
		</vxe-toolbar>
		<vxe-table
		 border
		 show-header-overflow
		 show-overflow
		 highlight-hover-row
		 ref="xTable"
		 :align="allAlign"
		 :data="list">
			<vxe-table-column type="checkbox" width="60"></vxe-table-column>
			<vxe-table-column field="classId" title="班级ID" :formatter="formatterClass"></vxe-table-column>
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
                request: {},
				classList: {},
				formList: {}
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
			}
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
</style>
