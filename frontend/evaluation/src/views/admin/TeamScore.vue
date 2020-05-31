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
		<vxe-table border show-header-overflow show-overflow highlight-hover-row :align="allAlign" :data="tableData">
			<!-- <vxe-table-column field="groupId" title="小组ID"></vxe-table-column> -->
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
	export default {
		inject: ['reload'],
		components: {
			ManageNav,
		},
		data () {
			return {
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
            }
        }
        
	}
</script>

<style>
</style>
