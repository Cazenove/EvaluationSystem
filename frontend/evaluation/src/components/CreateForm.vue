<template>
	<div id="CreateForm">
		<div class="form-row">
			<div class="form-group col-md-4 mb-3">
				<label for="formName">评分表名称</label>
				<input v-model="submitForm.name" type="text" class="form-control" id="formName" placeholder="例如:第一次团队作业">
				<span class="help-block">发布一份名为name的评分表后，系统会生成name_组间评分表和name_组内评分表</span>
			</div>
			<div class="form-group col-md-4 mb-3">
				<label for="formClass">班级</label>
				<select v-model="submitForm.classId" class="form-control" id="formClass">
					<option disabled="false" default>请选择受众班级</option>
					<option v-for="item in classList" :key="item.classId" :value="item.classId">{{item.className}}</option>
				</select>
			</div>
			<div class="form-group col-md-4 mb-3">
				<label>选择截止时间：</label>
				<input v-model="submitForm.endTime" type="datetime-local" class="form-control" />
			</div>
		</div>
		<vxe-toolbar>
			<template v-slot:buttons>
				<vxe-button @click="insertEvent">新增</vxe-button>
			</template>
		</vxe-toolbar>
		<vxe-table
		 border 
		 show-footer
		 highlight-hover-row 
		 export-config 
		 ref="xTable"
		 class="editable-footer"
		 :footer-method="footerMethod" 
		 :footer-cell-class-name="footerCellClassName"
		 :edit-rules="validRules"
		 :data="tableData" 
		 :edit-config="{trigger: 'click', mode: 'cell', showStatus: true}">
			<vxe-table-column type="checkbox" width="60"></vxe-table-column>
			<vxe-table-column type="seq" width="60"></vxe-table-column>
			<vxe-table-column width="280" field="item" title="评分项" :edit-render="{name: 'input'}"></vxe-table-column>
			<vxe-table-column width="280" field="maxScore" title="分值" :edit-render="{name: '$input', props: {type: 'number', min: 1, max: 100}}"></vxe-table-column>
			<vxe-table-column title="操作" width="80">
				<template v-slot="{ row,rowIndex }">
					<button class="btn btn-danger" @click="removeEvent(rowIndex)">删除</button>
				</template>
			</vxe-table-column>
		</vxe-table>
		
		<br />
		<button class="btn btn-success" @click="release">发布</button>
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
					item: [
						{required:true, message:'评分项为必填项'}
					],
					maxScore: [
						{required:true, message:'分值为必填项'}
					]
				},
				tableData: [{
					item:null,
					maxScore:null,
					socre:""
				}],
				classList: [
				],
				teamList: [
				],
				sum: null,
				submitForm: {
					name:"",
					classId:null,
				    releaseTime:"",//发布时间
				    endTime:"",//结束时间
				    content: {
				        details:[]
				    }
				},
				check: false//表单验证结果
			}
		},
		created() {
			// 获取班级和小组信息，用于生成完整表单
			this.getClassList();
			this.getTeamList();
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
					if(this.$data.sum === 100)
					{
						this.$XModal.message({ status: 'success', message: '校验成功！' })
						return true;
					} else {
						this.$XModal.message({ status: 'error', message: '分值总和需为100！' })
						return false;
					}
				}
			},
			getClassList() {
				var self = this;
				axios.get(api.adminClassList,null)
					.then(function(res) {
						console.log(res.data);
						if(res.status == 200 && res.data.status == 1) {
							self.classList = res.data.date;
						}
					}).catch(function(error) {
						console.log(error);
					})
			},
			getTeamList() {
				var self = this;
				axios.get(api.adminTeamList,null)//获取该班级的小组列表
					.then(function(res) {
						self.teamList = res.data.data;
					}).catch(function(error) {
						console.log(error);
					})
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
							return '总和'
						}
						if (['maxScore'].includes(column.property)) {
							this.$data.sum = XEUtils.sum(data, column.property);
							return XEUtils.sum(data, column.property)
						}
						return null
					})
				]
			},
			insertEvent() {
				this.tableData.push({
					item:"",
					maxScore:""
				});
			},
			removeEvent (rowIndex) {
				console.log(rowIndex);
				this.tableData.pop(rowIndex);
			},
			release() {
				//表头校验
				if(!this.submitForm.name || !this.submitForm.endTime || !this.submitForm.classId) {
					this.$XModal.message({ status: 'error', message: '表头信息不能为空' })
					return;
				} else {
					//表单校验
					var self = this;
					this.fullValidEvent().then(function(res) {
						if(!res) {
							//校验不通过
							return;
						} else {
							//校验通过，生成表单
							for(var i=0; i<self.teamList.length; i++) {
								if(self.teamList[i].classId === self.submitForm.classId) {
									var item = {
										groupId:self.teamList[i].groupId,
										groupName:self.teamList[i].groupName,
										groupNum:self.teamList[i].groupNum,
										score:'',
										content:self.tableData
									};
									self.submitForm.content.details[i] = item;
								}
							}
							var time = new Date();
							//获取本地时间作为表单的发布时间
							self.submitForm.releaseTime = time;
							console.log(self.submitForm);
							//提交表单
							axios.post(api.adminEvaluationCreate,self.submitForm)
							.then(function(res) {
								console.log(res);
								if(res.status == 200 && res.data.status == 1) {
									alert("创建成功！");
								} else {
									alert(res.msg);
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
