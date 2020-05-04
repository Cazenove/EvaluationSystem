<!-- 组间评价表内容 -->
<template>
	<div id="OuterEditableTable" class="container col-md-10 offset-md-1" style="margin: 50px auto;">
		<h2>{{this.$data.response.data.name}}</h2>
		<vxe-grid
		 v-if="validRules"
		 border
		 resizable
		 keep-source
		 ref="xTable"
		 :columns="tableColumn"
		 :data="tableData"
		 :edit-config="{trigger: 'click', mode: 'cell'}"
		 :edit-rules="validRules"
		 @edit-disabled="editDisabledEvent">
		</vxe-grid>
		<br />
		<button class="btn btn-success" @click="sumbit">提交</button>
	</div>
</template>

<script>
	import axios from 'axios'
	import api from '../router/httpConfig.js'
	export default {
		data() {
			return {
				validRules: {
					score0: [{required:true, message:'此项必填'}],
					score1: [{required:true, message:'此项必填'}],
					score2: [{required:true, message:'此项必填'}],
					score3: [{required:true, message:'此项必填'}],
					score4: [{required:true, message:'此项必填'}],
					score5: [{required:true, message:'此项必填'}],
					score6: [{required:true, message:'此项必填'}],
					score7: [{required:true, message:'此项必填'}],
					score8: [{required:true, message:'此项必填'}],
					suggestion:[{required:true, message:'建议为必填项'}]
				},
				request: {
					classId:null,
					groupId:null,
					evaluationOuterId:null//组间评分表的id
				},
				response: {
					status:1,
					data: {
						evaluationOuterId:1,
						name:"第一次团队合作_组间评分表",
						releaseTime:"",//发布时间
						endTime:"",//截止时间，在截止时间之后就不能提交
						content: {
							details:[
								{
									groupId:1,
									groupNum: 1,// 填表的时候看的是自己班级里的第几组
									groupName:"第一组",
									score:null,//总分
									content:[
										{
											item:"创新性",
											maxScore:40,
											score:"",//未填状态
										},
										{
											item:"实用性",
											maxScore:60,
											score:null,
										}
									],
									suggestion:"对第一组的建议..."
								},
								{
									groupId:2,
									groupNum: 2,
									groupName:"第二组",
									score:null,//总分
									content:[
										{
											item:"创新性",
											maxScore:40,
											score:"",//未填状态
										},
										{
											item:"实用性",
											maxScore:60,
											score:"",
										}
									],
									suggestion:"对第二组的建议..."
								}
							]
						}
					}
				},
				tableColumn: [
					{
						field:"groupId",
						title:"小组id"
					},
					{
						field:"groupName",
						title:"组名"
					}
				],
				tableData: []
			}
		},
		created() {
			this.init();
			// 获取表头
			var content = this.$data.response.data.content.details[0].content,
				len = content.length;
			for (var i = 2; i < len+2; i++) {
				this.$data.tableColumn[i] = {
					field: "score" + (i-2),
					title: content[i-2].item+"("+content[i-2].maxScore+")",
					"editRender": {name: '$input', props: {type: 'number', min: 0, max: content[i-2].maxScore}}
				};
			}
			this.$data.tableColumn[i] = {
				field: "suggestion",
				title: "建议",
				editRender: {name: 'textarea'}
			};
			
			// 获取表的内容
			content = this.$data.response.data.content.details;
			var conlen = content.length;
			for(var i = 0; i < conlen; i++) {
				var item = {
					groupId: content[i].groupId,
					groupName: content[i].groupName,
					suggestion: content[i].suggestion
				}
				for(var j = 0; j < len; j++) {
					var str = "score" + j;
					item[str] = content[i].content[j].score;
				}
				this.$data.tableData[i] = item;
			}
		},
		props: ['evaluationOuterId'],
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
				axios.get(api.userEvaluationOuter, self.request)
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
			sumbit() {
				// 提交表格
				// 将修改的数据保存到表单，然后进行提交
				var len = this.$data.tableData.length,
					conlen = this.$data.tableColumn.length;
				for(var i = 0; i < len; i++) {
					for(var j = 0; j < conlen-3; j++) {
						var str = "score" + j;
						this.$data.response.data.content.details[i].content[j].score = this.$data.tableData[i][str];
					}
					this.$data.response.data.content.details[i].suggestion = this.$data.tableData[i]["suggestion"];
				}
				
				//判断表单完整性
				var self = this;
				this.fullValidEvent().then(function(res) {
					if(!res) {
						return;
					} else {
						//发送
						var submitForm = {};
						submitForm['evaluationOuterId'] = self.$data.response.data.evaluationOuterId;
						submitForm['groupId'] = self.$data.request.groupId;
						var time = new Date();
						submitForm['submitTime'] = time;
						submitForm['content'] = self.$data.response.data.content;
						
						console.log(submitForm);
						// 提交
						axios.post(api.userEvaluationOuterSubmit,submitForm)
						.then(function(res) {
							if(res.status === 1) {
								this.$XModal.message({ status: 'success', message: '提交成功！' });
							} else {
								this.$XModal.message({ status: 'error', message: status.msg });
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
