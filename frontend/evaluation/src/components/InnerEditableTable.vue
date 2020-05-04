<!-- 组内评价表内容 -->
<template>
	<div id="OuterEditableTable" class="container col-md-10 offset-md-1" style="margin: 50px auto;">
		<h2>{{this.$data.response.data.name}}</h2>
		<vxe-grid
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
			<vxe-table-column field="userName" title="姓名"></vxe-table-column>
			<vxe-table-column field="decision" title="分工" :edit-render="{name: 'input', attrs: {type: 'text'}}"></vxe-table-column>
			<vxe-table-column field="contribution" title="贡献率" :edit-render="{name: '$input', props: {type: 'number',min: 0,max: 100}}"></vxe-table-column>
		</vxe-grid>
		<br />
		<button class="btn btn-success" @click="sumbit">提交</button>
	</div>
</template>

<script>
	import axios from 'axios'
	import api from '../router/httpConfig.js'
	import XEUtils from 'xe-utils'
	
	export default {
		data() {
			return {
				validRules: {
					decision: [
						{required:true, message:'分工为必填项'}
					],
					contribution: [
						{required:true, message:'贡献率为必填项'}
					]
				},
				request: {
					classId:1,
					groupId:1,
					evaluationInnerId:1//组内评分表的id
				},
				response: {
					status:1,
					data: {
						evaluationInnerId:1,
						name:"第一次团队合作_组内评分表",
						groupId:1,
						groupName:"第一组",
						releaseTime:"",//发布时间
						endTime:"",//截止时间
						content: {
							details: [
								{
									userId:"221701000",//id，学号
									userName:"张三",//姓名
									decision:"",//分工
									contribution:""//贡献率
								},
								{
									userId:"221701001",//id，学号
									userName:"李四",//姓名
									decision:"",//分工
									contribution:""//贡献率
								}
							]
						}
					}
				},
				sum: null,
				tableData: []
			}
		},
		props: ['evaluationInnerId'],
		created() {
			this.init();
			this.$data.tableData = this.$data.response.data.content.details;
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
				this.$data.request.classId = this.$store.state.userInfo.classId;
				this.$data.request.groupId = this.$store.state.userInfo.groupId;
				this.$data.request.evaluationInnerId = this.$props.evaluationInnerId;
			},
			getResponse() {
				var self = this;
				axios.get(api.userEvaluationInner, self.request)
					.then(function(res) {
						self.response = res;
					}).catch(function(error) {
						console.log(error);
					})
			},
			init() {
				this.getRequest(),
				this.getResponse()
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
					// 保存修改的数据
					var len = this.$data.tableData.length;
					for(var i = 0; i < len; i++) {
						this.$data.response.data.content.details[i].contribution = this.$data.tableData[i].contribution;
					}
					
					//完整性验证
					var self = this;
					this.fullValidEvent().then(function(res) {
						if(!res) {
							return;
						} else {
							//提交
							var submitForm = {};
							submitForm['evaluationInnerId'] = self.$data.response.data.evaluationInnerId;
							submitForm['groupId'] = self.$data.request.groupId;
							var time = new Date();
							submitForm['submitTime'] = time;
							submitForm['content'] = self.$data.response.data.content;
							
							console.log(submitForm);
							// 提交
							axios.post(api.userEvaluationInnerSubmit,submitForm)
							.then(function(res) {
								if(res.status === 1) {
									this.$XModal.message({ status: 'success', message: '提交成功！' })
								} else {
									this.$XModal.message({ status: 'error', message: res.msg })
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
