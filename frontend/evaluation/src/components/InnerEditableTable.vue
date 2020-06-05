<!-- 组内评价表内容 -->
<template>
	<div id="OuterEditableTable" class="container col-md-10 offset-md-1" style="margin: 50px auto;">
		<h2>{{this.$data.response.data.name}}</h2>
		<vxe-grid
		 v-if="ready"
		 border 
		 show-footer 
		 resizable 
		 keep-source 
		 ref="xTable" 
		 height="500" 
		 class="editable-footer"
		 :footer-method="footerMethod"
		 :footer-cell-class-name="footerCellClassName"
		 :edit-rules="validRules"
		 :data="tableData" 
		 :edit-config="{trigger: 'click', mode: 'cell', showStatus: true, icon: 'fa fa-file-text-o'}">
			<vxe-table-column field="userId" title="学号"></vxe-table-column>
			<vxe-table-column field="name" title="姓名"></vxe-table-column>
			<vxe-table-column field="decision" title="分工" :edit-render="{name: 'input', attrs: {type: 'text'}}"></vxe-table-column>
			<vxe-table-column field="contribution" title="贡献率" :edit-render="{name: '$input', props: {type: 'number',min: 0,max: 100}}"></vxe-table-column>
		</vxe-grid>
		<br />
		<el-row>
			<el-col :span="8" :offset="10">
				<div class="grid-content bg-purple-light">
					<button class="btn btn-success btn-lg" @click="sumbit">提交</button>
				</div>
			</el-col>
		</el-row>
	</div>
</template>

<script>
	import axios from 'axios'
	import api from '../router/httpConfig.js'
	import XEUtils from 'xe-utils'
	
	export default {
		inject: ['reload'],
		data() {
			return {
				ready:false,
				validRules: {
					decision: [
						{required:true, message:'分工为必填项'}
					],
					contribution: [
						{required:true, message:'贡献率为必填项'}
					]
				},
				request: {
					params:{
						classId:'',
						groupId:'',
						evaluationInnerId:''//组内评分表的id
					}
				},
				response: {
					status:1,
					data: {
						evaluationInnerId:'',
						name:'',
						groupId:'',
						groupName:'',
						releaseTime:'',//发布时间
						endTime:'',//截止时间
						content: {}
					}
				},
				tableData:[],
				teamInfo:{},
				sum: null
			}
		},
		props: ['evaluationInnerId'],
		created() {
			this.init();
		},
		methods: {
			async fullValidEvent () {
				const errMap = await this.$refs.xTable.fullValidate().catch(errMap => errMap)
				if (errMap) {
					let msgList = []
					Object.values(errMap).forEach(errList => {
						errList.forEach(params => {
							let { rowIndex, column, rules } = params
							rules.forEach(rule => {
								msgList.push(`第 ${rowIndex} 行 ${column.title} 校验错误：${rule.message}`)
							})
						})
					})
					this.$XModal.message({
						status: 'error',
						message: () => {
							return [
								<div class="red" style="max-height: 400px;overflow: auto;">
									{ msgList.map(msg => <div>{ msg }</div>) }
								</div>
							]
						}
					})
					return false;
				} else {
					this.$XModal.message({ status: 'success', message: '校验成功！' })
					return true;
				}
			},
			getRequest() {
				this.$data.request.params.classId = this.$store.state.userInfo.classId;
				this.$data.request.params.groupId = this.$store.state.userInfo.groupId;
				this.$data.request.params.evaluationInnerId = this.$props.evaluationInnerId;
			},
			getResponse() {
				var self = this;
				axios.get(api.userEvaluationInner, self.request)
				.then(function(res) {
					self.response = res.data;
				}).catch(function(error) {
					console.log(error);
				})
			},
			getTeamInfo() {
				const self = this;
				axios.get(api.adminTeamList, null)
				.then(function(res) {
					if (res.status == 200 && res.data.status == 1) {
						for(var i=0; i<res.data.data.length; i++) {
							if(self.$store.state.userInfo.groupId == res.data.data[i].groupId) {
								self.teamInfo = res.data.data[i];
								break;
							}
						}
						for(var i=0;i<self.teamInfo.members.length;i++) {
							self.tableData[i] = {
								userId:self.teamInfo.members[i].userId,
								name:self.teamInfo.members[i].name,
								decision:'',
								contribution:0
							}
						}
						self.ready = true;
					} else {
						console.log(res.data.msg);
					}
				}).catch(function(error) {
					console.log(error);
				})
			},
			init() {
				this.getRequest(),
				this.getResponse(),
				this.getTeamInfo()
			},
			footerCellClassName({
				$rowIndex,
				column,
				columnIndex
			}) {
				if (columnIndex === 0) {
					if ($rowIndex === 0) {
						return 'col-blue'
					} else {
						return 'col-red'
					}
				}
			},
			footerMethod({
				columns,
				data
			}) {
				return [
					columns.map((column, columnIndex) => {
						if (columnIndex === 0) {
							return '贡献值总和'
						}
						if (['contribution'].includes(column.property)) {
							this.$data.sum = XEUtils.sum(data, column.property);
							return XEUtils.sum(data, column.property)
						}
						return null
					})
				]
			},
			sumbit() {
				// 提交表格
				if(this.sum !== 100) {
					this.$XModal.message({ status: 'error', message: '贡献度总和需为100！' })
				} else {
					//完整性验证
					var self = this;
					this.fullValidEvent().then(function(res) {
						if(!res) {
							return;
						} else {
							//提交
							var submitForm = {};
							submitForm['evaluationInnerId'] = self.$data.response.data.evaluationInnerId;
							submitForm['groupId'] = self.$data.request.params.groupId;
							var time = new Date();
							submitForm['submitTime'] = parseInt(time.getTime()/1000);
							
							var content = {
								tableColumn:['userId','name','decision','contribution'],
								tableData:[]
							};
							for(var i=0; i<self.$data.tableData.length; i++) {
								content.tableData[i] = [self.$data.tableData[i].userId,self.$data.tableData[i].name,self.$data.tableData[i].decision,Number(self.tableData[i].contribution)];
							}
							submitForm['content'] = content;
							// 提交
							var that = self;
							axios.post(api.userEvaluationInnerSubmit,submitForm)
							.then(function(res) {
								if(res.status == 200 && res.data.status == 1) {
									// that.$XModal.message({ status: 'success', message: '提交成功！' })
									alert('提交成功！');
								} else {
									// that.$XModal.message({ status: 'error', message: res.data.msg })
									alert(res.data.msg);
								}
							}).catch(function(error) {
								console.log(error);
							})
						}
					})
				}
			}
		}
	}
</script>

<style>
</style>
