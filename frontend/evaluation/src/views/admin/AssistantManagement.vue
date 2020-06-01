<template>
	<div>
		<ManageNav />
		<nav aria-label="breadcrumb">
			<ol class="breadcrumb">
				<li class="breadcrumb-item"><a href="#">主页</a></li>
				<li class="breadcrumb-item active" aria-current="page">{{this.title}}</li>
			</ol>
		</nav>
		<AdminAssistantUpdateModal ref="AdminAssistantUpdateModal"></AdminAssistantUpdateModal>
		<div class="container col-md-10 offset-md-1" style="margin: 50px auto;">
			<div>
				<h1>{{this.title}}</h1>
			</div>
			<div class="row container" style="margin-bottom: 20px;">
				<!-- <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#modal">添加助教</button> -->
				<button class="btn btn-primary" @click="showCreateModal()">添加助教</button>
				<AdminAssistantCreateModal ref="AdminAssistantCreateModal" :modalTitle="createModalTitle"></AdminAssistantCreateModal>
			</div>
			<vxe-toolbar>
				<template v-slot:buttons>
					<vxe-input v-model="filterName" type="search" placeholder="快速搜索"></vxe-input>
				</template>
			</vxe-toolbar>
			<vxe-table :align="allAlign" :data="list" border resizable row-key highlight-hover-row keep-source>
				<vxe-table-column field="assistantId" title="助教ID"></vxe-table-column>
				<vxe-table-column field="name" title="姓名"></vxe-table-column>
				<vxe-table-column field="telephone" title="电话"></vxe-table-column>
				<vxe-table-column field="classId" title="管理的班级" :formatter="toClassName"></vxe-table-column>
				<vxe-table-column field="password" title="密码"></vxe-table-column>
				<vxe-table-column title="操作">
					<template v-slot="{ row }">
						<button type="button" class="btn btn-light" @click="showUpdateModal(row)">修改</button>
						&nbsp;
						<button type="button" @click="removeEvent(row)" class="btn btn-danger">删除</button>
					</template>
				</vxe-table-column>
			</vxe-table>
		</div>

		<div class="modal fade" id="modal" v-if="updateInfo.assistantId">
			<div class="modal-dialog">
				<div class="modal-content">

					<!-- 模态框头部 -->
					<div class="modal-header">
						<h4 class="modal-title">{{ this.modalTitle }}</h4>
						<button type="button" class="close" data-dismiss="modal">&times;</button>
					</div>

					<!-- 模态框主体 -->
					<div class="modal-body">
						<div class="form-group">
							<label for="assistantId">学号</label>
							<input type="text" class="form-control" v-model="updateInfo.assistantId" />
							<span class="error" v-if="errors['updateInfo.assistantId']">{{errors['updateInfo.assistantId']}}</span>
						</div>
						<div class="form-group">
							<label for="name">姓名</label>
							<input type="text" class="form-control" v-model="updateInfo.name" />
							<span class="error" v-if="errors['updateInfo.name']">{{errors['updateInfo.name']}}</span>
						</div>
						<div class="form-group">
							<label for="telephone" class="col-form-label">电话号码</label>
							<input type="text" class="form-control" v-model="updateInfo.telephone" />
							<span class="error" v-if="errors['updateInfo.telephone']">{{errors['updateInfo.telephone']}}</span>
						</div>
						<div class="form-group">
							<label for="update-classId">管理的班级</label>
							<select name="classId" class="form-control" v-model="updateInfo.classId" @change="classOptionChange(updateInfo)">
								<option :value="item.classId" v-for="item in classList" :key="item.classId"> {{ item.className }} </option>
							</select>
						</div>
						<div class="form-group">
							<label for="password">密码</label>
							<input type="password" class="form-control" id="password" v-model="updateInfo.password" />
							<span class="error" v-if="errors['updateInfo.password']">{{errors['updateInfo.password']}}</span>
						</div>
					</div>

					<!-- 模态框底部 -->
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
						<button type="button" class="btn btn-primary" @click="update()">确认</button>
					</div>
				</div>
			</div>
		</div>

	</div>
</template>

<script>
	import axios from 'axios'
	import api from '../../router/httpConfig.js'
	import ManageNav from '../../components/ManageNav.vue'
	import AdminAssistantCreateModal from '../../components/AdminAssistantCreateModal.vue'
	import AdminAssistantUpdateModal from '../../components/AdminAssistantUpdateModal.vue'
	import Vue from 'vue'
	import Vuerify from 'vuerify'
	import XEUtils from 'xe-utils'
	export default {
		inject: ['reload'],
		components: {
			ManageNav,
			AdminAssistantCreateModal,
			AdminAssistantUpdateModal
		},
		data() {
			return {
				filterName: '',
				updateInfo: {
					assistantId: null,
					password: null,
					telephone: null,
					name: null,
					classId: null, //助教管理的班级
				},
				allAlign: null,
				title: "助教管理",
				tableData: [],
				request: {},
				response: {
					status: '',
					data: []
				},
				createModalTitle: "添加助教",
				updateModalTitle: "修改",
				timer: "",
				className: {}
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
				return this.className[cellValue];
			},
			getResponse() {
				var self = this;
				axios.get(api.adminAssistantList, null)
				.then(function(res) {
					if (res.status == 200 && res.data.status == 1) {
						self.response = res.data;
						self.tableData = self.response.data;
					} else {
						console.log(res.data.msg);
					}
				}).catch(function(error) {
					console.log(error);
				})
			},
			init() {
				this.getResponse();
			},
			showCreateModal() {
				this.$refs.AdminAssistantCreateModal.showModal();
			},
			showUpdateModal(row) {
				this.$refs.AdminAssistantUpdateModal.showModal(row);
			},
			removeEvent(row) {
				var deleteInfo = {
					assistantId: row.assistantId,
				}
				this.$XModal.confirm('您确定要删除该数据?').then(type => {
					if (type === 'confirm') {
						var self = this;
						axios.post(api.adminAssistantDelete, deleteInfo)
						.then(function(res) {
							alert(res.data.msg);
							self.reload();
						}).catch(function(error) {
							console.log(error);
						})
					}
				})
			},
			getClassList() {
				var self = this;
				axios.get(api.adminClassList, null)
				.then(function(res) {
					self.classList = res.data.data;
					for(var i=0; i<res.data.data.length; i++) {
						self.className[res.data.data[i].classId] = res.data.data[i].className;
					}
				}).catch(function(error) {
					console.log(error);
				})
			},
			formatterClass({cellValue}) {
				let item = this.classList.find(item => item.classId === cellValue)
				return item ? item.className : ''
			},

		},
		computed: {
			list() {
				const filterName = XEUtils.toString(this.filterName).trim().toLowerCase()
				if (filterName) {
					const filterRE = new RegExp(filterName, 'gi')
					const searchProps = ['assistantId', 'name', 'classId', 'telephone']
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
