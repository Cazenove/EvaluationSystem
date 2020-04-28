<template>
	<div id="OuterEditableTable">
		<h2>{{this.$data.data.name}}</h2>
		<vxe-grid border show-footer resizable keep-source ref="xGrid" height="530" class="editable-footer" :footer-method="footerMethod"
		 :footer-cell-class-name="footerCellClassName" :data="tableData" :edit-config="{trigger: 'click', mode: 'row', showStatus: true, icon: 'fa fa-file-text-o'}">
			<vxe-table-column field="id" title="学号"></vxe-table-column>
			<vxe-table-column field="name" title="姓名"></vxe-table-column>
			<vxe-table-column field="decision" title="分工" :edit-render="{name: 'input', attrs: {type: 'text'}}"></vxe-table-column>
			<vxe-table-column field="contribution" title="贡献率" :edit-render="{name: '$input', props: {type: 'number',min: 0,max: 100}}"></vxe-table-column>
		</vxe-grid>
		<br />
		<vxe-button @click="sumbit">提交</vxe-button>
	</div>
</template>

<script>
	import XEUtils from 'xe-utils'
	export default {
		data() {
			return {
				state: "",
				data: {
					id: "1",
					name: "第一次团队合作_组内评分表",
					group_id: "1",
					group_name: "第一组",
					content: [{
							id: "221701000",
							name: "张三",
							decision: "前端",
							contribution: "60"
						},
						{
							id: "221701001",
							name: "李四",
							decision: "后端",
							contribution: "40"
						}
					]
				},
				sum: "",
				tableData: []
			}
		},
		created() {
			this.$data.tableData = this.$data.data.content;
		},
		methods: {
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
							return '和值'
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
				if(this.sum>100) {
					alert("贡献度总和需为100！");
				} else {
					// 保存修改的数据
					var len = this.$data.tableData.length;
					for(var i = 0; i < len; i++) {
						this.$data.data.content[i].contribution = this.$data.tableData[i].contribution;
					}
					console.log(this.$data.tableData);
					
					// 提交
					
				}
			}
		}
	}
</script>

<style>
</style>
