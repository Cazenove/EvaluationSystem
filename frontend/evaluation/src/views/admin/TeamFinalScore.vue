<template>
    <div>
        <ManageNav />
        <nav aria-label="breadcrumb">
			<ol class="breadcrumb">
				<li class="breadcrumb-item"><router-link to="/admin/home">主页</router-link></li>
				<li class="breadcrumb-item active" aria-current="page">{{this.title}}</li>
			</ol>
		</nav>
		<div class="container col-md-10 offset-md-1" style="margin: 50px auto;">
        <h1>{{this.title}}</h1>
		<vxe-table border show-header-overflow show-overflow highlight-hover-row :align="allAlign" :data="list">
			<vxe-table-column type="checkbox" width="60"></vxe-table-column>
			<vxe-table-column field="groupId" title="小组ID"></vxe-table-column>
			<vxe-table-column field="classId" title="班级ID"></vxe-table-column>
			<vxe-table-column field="groupNum" title="小组序号"></vxe-table-column>
			<vxe-table-column field="groupName" title="小组名"></vxe-table-column>
			<vxe-table-column field="score" title="综合得分"></vxe-table-column>
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
                title: "小组综合得分",
				tableData: [
				],
                response: {
					status:'',
					data:[]
				}
			}
		},
        created() {
            this.init();
        },
        methods: {
            getResponse() {
				var self = this;
				axios.get(api.adminGroupScoreFinal,null)
				.then(function(res) {
					if(res.status == 200 && res.data.status == 1) {
						self.response = res.data;
						self.tableData = self.response.data;
					}
					else {
						console.log(res.data.msg);
					}
				}).catch(function(error) {
					console.log(error);
				})
            },
            init() {
                this.getResponse();
            }
        },
		computed:{
			list() {
				const filterName = XEUtils.toString(this.filterName).trim().toLowerCase()
				if (filterName) {
					const filterRE = new RegExp(filterName, 'gi')
					const searchProps = ['groupName', 'evaluationOuterId', 'score']
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
