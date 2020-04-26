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
					content: [{
							group_id: 1,
							group_name: "第一小组",
							content: [{
									item: "创新性",
									max_score: 50,
									score: 40
								},
								{
									item: "实用性",
									max_score: 50,
									score: 40
								}
							],
							suggestion: ""
						},
						{
							group_id: 2,
							group_name: "第二小组",
							content: [{
									item: "创新性",
									max_score: 50,
									score: 40
								},
								{
									item: "实用性",
									max_score: 50,
									score: 40
								}
							],
							suggestion: ""
						}
					]
				},
				tableColumn: [
					{
					field:"gourp_id",
					title:"小组id"
				},
				{
					field:"group_name",
					title:"组名"
				},
				],
				tableData: [{},{}]
			}
		},
		created() {
			var content = this.$data.data.content[0].content,
				len = content.length;
			for (var i = 2; i < len+2; i++) {
				this.$data.tableColumn[i] = {
					field: "score" + i,
					title: content[i-2].item,
					editRender: { name: 'input' } 
				};
			}
			console.log(this.$data.tableColumn);
		},
		methods: {
			showAll() {
				console.log(this.$data.tableData);
			},
			sumbit() {

			}
		}
	}
</script>

<style>
</style>
