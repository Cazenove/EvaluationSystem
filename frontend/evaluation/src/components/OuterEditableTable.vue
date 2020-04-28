<template>
	<div id="OuterEditableTable">
		<h2>{{this.$data.data.name}}</h2>
		<vxe-button @click="showAll">获取所有表单</vxe-button>
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
				state: "",
				data: {
					id: "1",
					name: "第一次团队合作_组间评分表",
					content: [
						{
							group_id: 1,
							group_name: "第一小组",
							content: [
								{
									item: "创新性",
									max_score: 50,
									score: 39
								},
								{
									item: "实用性",
									max_score: 50,
									score: 40
								}
							],
							suggestion: "对第一组的建议"
						},
						{
							group_id: 2,
							group_name: "第二小组",
							content: [
								{
									item: "创新性",
									max_score: 50,
									score: 41
								},
								{
									item: "实用性",
									max_score: 50,
									score: 42
								}
							],
							suggestion: "对第二组的建议"
						}
					]
				},
				tableColumn: [
					{
					field:"group_id",
					title:"小组id"
				},
				{
					field:"group_name",
					title:"组名"
				},
				],
				tableData: []
			}
		},
		created() {
			// 获取表头
			var content = this.$data.data.content[0].content,
				len = content.length;
			for (var i = 2; i < len+2; i++) {
				this.$data.tableColumn[i] = {
					field: "score" + (i-2),
					title: content[i-2].item+"("+content[i-2].max_score+")",
					"editRender": {name: '$input', props: {type: 'number', min: 0, max: max_score}}
				};
			}
			this.$data.tableColumn[i] = {
				field: "suggestion",
				title: "建议",
				editRender: {name: 'textarea'}
			};
			console.log(this.$data.tableColumn);
			
			// 获取表的内容
			content = this.$data.data.content;
			var conlen = content.length;
			for(var i = 0; i < conlen; i++) {
				var item = {
					group_id: content[i].group_id,
					group_name: content[i].group_name,
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
			showAll() {
				console.log(this.$data.tableData);
			},
			sumbit() {
				// 提交表格
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
			}
		}
	}
</script>

<style>
</style>
