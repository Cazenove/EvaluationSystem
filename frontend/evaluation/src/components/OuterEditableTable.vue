<!-- 组间评价表内容 -->
<template>
	<div id="OuterEditableTable" class="container col-md-10 offset-md-1" style="margin: 50px auto;">
		<h2>{{this.$data.title}}</h2>
		<vxe-grid
		 v-if="ready"
		 border
		 keep-source
		 resizable
		 ref="xTable"
		 :columns="tableColumn"
		 :data="tableData"
		 :edit-config="{trigger: 'click', mode: 'cell'}"
		 :edit-rules="validRules"
		 @edit-disabled="editDisabledEvent">
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
	import XLSX from 'xlsx'
	export default {
		inject: ['reload'],
		data() {
			const maxValid = ({ cellValue }) => {
				return new Promise((resolve, reject) => {
					if (cellValue > 0) {
						reject()
					} else {
						resolve()
					}
				})
			}
			return {
				ready:false,
				showDetails:false,
				title:'',
				file: null,
				maxScoreList: {},
				inputs: [],
				validRules: {
					'3': [
						{required:true, message:'此项必填'},
						{validator: maxValid}
					],
					'4': [
						{required:true, message:'此项必填'},
						{validator: maxValid}
					],
					'5': [
						{required:true, message:'此项必填'},
						{validator: maxValid}
					],
					'6': [
						{required:true, message:'此项必填'},
						{validator: maxValid}
					],
					'7': [
						{required:true, message:'此项必填'},
						{validator: maxValid}
					],
					'8': [
						{required:true, message:'此项必填'},
						{validator: maxValid}
					],
					suggestion:[{required:true, message:'建议为必填项'}]
				},
				request: {
					classId:null,
					groupId:null,
					evaluationOuterId:null//组间评分表的id
				},
				response: {},
				tableColumn: [
					{title:'小组ID',field:'0'},
					{title:'小组序号',field:'1'},
					{title:'小组名称',field:'2'}
				],
				tableData: []
			}
		},
		created() {
			this.init();
		},
		props: ['evaluationOuterId'],
		methods: {
			show() {
				this.showDetails = true;
			},
			async fullValidEvent () {
				const errMap = await this.$refs.xTable.fullValidate().catch(errMap => errMap)
				if (errMap) {
					let msgList = []
					Object.values(errMap).forEach(errList => {
						errList.forEach(params => {
							let { rowIndex, column, rules } = params
							rules.forEach(rule => {
								if(column.title == "建议") {
									msgList.push(`第 ${rowIndex} 行 ${column.title} 校验错误：${rule.message}`)
								}
							})
						})
					})
					if(msgList.length == 0) {
						this.$XModal.message({ status: 'success', message: '校验成功！' })
						return true;
					}
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
			activeRowMethod({row,rowIndex}) {
				if(row.groupId === this.$store.state.userInfo.groupId) {//不可对自己的小组进行评分
					return false;
				}
				return true;
			},
			editDisabledEvent({row, column}) {
				this.$XModal.alert('不可对自己小组进行评分！');
			},
			getRequest() {
				this.$data.request.classId = this.$store.state.userInfo.classId;
				this.$data.request.groupId = this.$store.state.userInfo.groupId;
				this.$data.request.evaluationOuterId = this.$props.evaluationOuterId;
			},
			getResponse() {
				var self = this;
				axios.post(api.userEvaluationOuter, self.request)
				.then(function(res) {
					if(res.status == 200 && res.data.status == 1) {
						self.response = res.data.data;
						self.title = self.response.name;
						//构建表头
						var i;
						for(i=3;i<self.response.content.tableColumn.length-2;i++) {
							var str = i.toString();
							self.tableColumn[i] = {
								field: i,
								title: self.response.content.tableColumn[i]+"("+self.response.content.maxScore[i]+")",
								editRender: {name: '$input', props: {type: 'integer', min:0 ,max:Number(self.response.content.maxScore[i])}}
							};
						}
						self.tableColumn[i] = {
							field: i,
							title: '总分'
						};
						self.tableColumn[++i] = {
							field: i,
							title: '建议',
							editRender: {name: 'textarea'}
						};
						//表体
						for(var i=0;i<self.response.content.tableData.length;i++) {
							var item = []
							for(var j=0;j<self.response.content.tableColumn.length;j++) {
								var str = j;
								item[str] = self.response.content.tableData[i][j];
							}
							self.tableData[i] = item;
						}
						self.ready = true;
					} else {
						alert(res.data.msg);
					}
				}).catch(function(error) {
					console.log(error);
				})
			},
			init() {
				this.getRequest(),
				this.getResponse()
			},
			sumbit() {
				// 提交表格
				// 将修改的数据保存到表单，然后进行提交
				console.log(this.tableData);
				for(var i=0; i<this.response.content.tableData.length; i++) {
					this.response.content.tableData[i][0] = this.tableData[i][0];
					this.response.content.tableData[i][1] = this.tableData[i][1];
					this.response.content.tableData[i][2] = this.tableData[i][2];
					var sum = 0,
						j = 3;
					for(var j=3; j<this.response.content.tableColumn.length-2; j++) {
						sum += Number(this.tableData[i][j]);
						this.response.content.tableData[i][j] = this.tableData[i][j];
					}
					this.response.content.tableData[i][j] = sum;
					j++;
					this.response.content.tableData[i][j] = this.tableData[i][j];
				}
				//判断表单完整性
				var self = this;
				this.fullValidEvent().then(function(res) {
					if(!res) {
						return;
					} else {
						//发送
						var time = new Date();
						var submitForm = {};
						submitForm['evaluationOuterId'] = self.$data.response.evaluationOuterId;
						submitForm['groupId'] = self.$data.request.groupId;
						submitForm['submitTime'] = parseInt(time.getTime()/1000);
						submitForm['content'] = self.$data.response.content;
						
						for(var i=0; i<submitForm.content.tableData.length; i++) {
							if(submitForm.content.tableData[i][0] == self.request.groupId) {
								submitForm.content.tableData.splice(i,1);
								break;
							}
						}
						
						//提交
						axios.post(api.userEvaluationOuterSubmit,submitForm)
						.then(function(res) {
							if(res.status == 200 && res.data.status == 1) {
								alert(res.data.msg);
							} else {
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
</script>

<style>
</style>
