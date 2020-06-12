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
		<vxe-table border show-header-overflow show-overflow highlight-hover-row :align="allAlign" :data="tableData">
			<vxe-table-column type="checkbox" width="60"></vxe-table-column>
			<vxe-table-column field="userId" title="用户ID"></vxe-table-column>
			<vxe-table-column field="userName" title="用户名"></vxe-table-column>
			<vxe-table-column field="className" title="班级"></vxe-table-column>
			<vxe-table-column field="groupId" title="小组ID"></vxe-table-column>
			<vxe-table-column field="groupName" title="小组名"></vxe-table-column>
			<vxe-table-column field="score" title="分数"></vxe-table-column>
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
                title: "个人综合得分",
				tableData: [
				],
                request: {
                },
                response: {
					status:1,
					data:[]
				}
			}
		},
        created() {
            this.init();
        },
        methods: {
            getRequest() {
            },
            getResponse() {
				var self = this;
				axios.get(api.adminUserScoreFinal,null)
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
                this.getRequest();
                this.getResponse();
            }
        }
        
	}
</script>

<style>
</style>
