<template>
	<div id="CreateForm">
		<div class="form-row">
			<div class="form-group col-md-4 mb-3">
				<label for="formName">评分表名称</label>
				<input v-model="submitForm.name" type="text" class="form-control" id="formName" placeholder="例如:第一次团队作业">
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
		 height="300" 
		 class="editable-footer"
		 :footer-method="footerMethod" 
		 :footer-cell-class-name="footerCellClassName" 
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
				tableData: [{
					item:null,
					maxScore:null,
					socre:""
				}],
				classList: [
					{
						classId:1,
						className:"2020软件工程S班",
						groupNum:7,
						startTime:"2020-03-11"
					},
					{
						classId:2,
						className:"2020软件工程W班",
						groupNum:8,
						startTime:"2020-03-11"
					}
				],
				teamList: [
					{
						groupId:1,
						groupName:"第一组",
						classId:1,
						className:"2020软件工程S班",
						groupNum:1,//小组序列，在该班级的第几组
						leader:{//组长
							userId:"221701000",
							userName:"张三"
						},
						member:[
							{
								userId:"221701001",
								userName:"李四"
							},
							{
								userId:"221701002",
								userName:"王五"
							}
						]
					},
					{
						groupId:2,
						groupName:"第二组",
						classId:1,
						className:"2020软件工程S班",
						groupNum:1,//小组序列，在该班级的第几组
						leader:{//组长
							userId:"221701000",
							userName:"张三"
						},
						member:[
							{
								userId:"221701001",
								userName:"李四"
							},
							{
								userId:"221701002",
								userName:"王五"
							}
						]
					}
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
				}
			}
		},
		created() {
			// 获取班级和小组信息，用于生成完整表单
			this.getClassList();
			this.getTeamList();
		},
		methods: {
			getClassList() {
				var self = this;
				axios.get(api.adminClassList,null)
					.then(function(res) {
						if(res.status === 1) {
							self.classList = res.data;
						}
					}).catch(function(error) {
						console.log(error);
					})
			},
			getTeamList() {
				var self = this;
				axios.get(api.adminTeamList,null)//获取该班级的小组列表
					.then(function(res) {
						self.teamList = res.data;
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
				if(this.sum !== 100) {
					alert("总和需为100！");
				} else {
					//完整性校验
					
					
					//构建完整的评分表
					for(var i=0; i<this.teamList.length; i++) {
						if(this.teamList[i].classId === this.submitForm.classId) {
							var item = {
								groupId:this.teamList[i].groupId,
								groupName:this.teamList[i].groupName,
								groupNum:this.teamList[i].groupNum,
								score:null,
								content:this.tableData
							};
							this.submitForm.content.details.push(item);
						}
					}
					console.log(this.submitForm);
					
					//提交表单
					axios.post(api.adminEvaluationCreate,this.submitForm)
					.then(function(res) {
						if(res.status === 1) {
							alert("创建成功！");
						} else {
							alert(res.msg);
						}
					}).catch(function(error) {
						console.log(error);
					})
				}
			}
		}
	}
</script>

<style>
</style>
