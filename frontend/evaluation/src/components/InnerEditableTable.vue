<!-- 组内评价表内容 -->
<template>
	<div id="OuterEditableTable">
		<h2>{{this.$data.response.data.name}}</h2>
		<vxe-grid
		 border 
		 show-footer 
		 resizable 
		 keep-source 
		 ref="xGrid" 
		 height="500" 
		 class="editable-footer"
		 :footer-method="footerMethod"
		 :footer-cell-class-name="footerCellClassName" 
		 :data="tableData" 
		 :edit-config="{trigger: 'click', mode: 'row', showStatus: true, icon: 'fa fa-file-text-o'}">
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
				sum: "",
				tableData: []
			}
		},
		props: ['evaluationInnerId'],
		created() {
			this.init();
			this.$data.tableData = this.$data.response.data.content.details;
		},
		methods: {
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
					alert("贡献度总和需为100！");
				} else {
					// 保存修改的数据
					var len = this.$data.tableData.length;
					for(var i = 0; i < len; i++) {
						this.$data.response.data.content.details[i].contribution = this.$data.tableData[i].contribution;
					}
					
					//完整性验证
					
					
					//提交
					var submitForm = {};
					submitForm['evaluationInnerId'] = this.$data.response.data.evaluationInnerId;
					submitForm['groupId'] = this.$data.request.groupId;
					submitForm['submitTime'] = "";
					submitForm['content'] = this.$data.response.data.content;
					
					console.log(submitForm);
					// 提交
					axios.post(api.userEvaluationInnerSubmit,submitForm)
					.then(function(res) {
						
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
