<template>
	<div id="OuterEditableTable">
		<h2>{{this.$data.response.name}}</h2>
		 <vxe-grid
		  border
		  resizable
		  keep-source
		  ref="xGrid"
		  height="530"
		  :columns="tableColumn"
		  :data="tableData"
		  :edit-config="{trigger: 'click', mode: 'row', showStatus: true, icon: 'fa fa-file-text-o'}"
		 >
		</vxe-grid>
		<br />
		<vxe-button @click="sumbit">提交</vxe-button>
	</div>
</template>

<script>
	export default {
		data() {
			return {
				request: {
					classId:1,
					groupId:1,
					evaluationOuterId:1//组间评分表的id
				},
				response: {
					status:1,
					data: {
					    evaluationOuterId:1,
					    name:"第一次团队合作_组间评分表",
					    content: [
					        {
					            groupId:1,
					            groupName:"第一组",
					            score:"",//总分
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
					            suggestion:"对第一组的建议..."
					        },
					        {
					            groupId:2,
					            groupName:"第二组",
					            score:"",//总分
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
			this.getRequest();
			// 获取表头
			var content = this.$data.response.data.content[0].content,
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
			console.log(this.$data.tableColumn);
			
			// 获取表的内容
			content = this.$data.response.data.content;
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
			console.log(this.$data.tableData);
		},
		methods: {
			getRequest() {
				
			},
			sumbit() {
				// 提交表格
				// 将修改的数据保存到表单，然后进行提交
				var len = this.$data.tableData.length,
					conlen = this.$data.tableColumn.length;
				for(var i = 0; i < len; i++) {
					for(var j = 0; j < conlen-3; j++) {
						var str = "score" + j;
						this.$data.data.content[i].content[j].score = this.$data.tableData[i][str];
					}
					this.$data.data.content[i].suggestion = this.$data.tableData[i]["suggestion"];
				}
				console.log(this.$data.data);
				
				// 提交
				
			}
		}
	}
</script>

<style>
</style>
