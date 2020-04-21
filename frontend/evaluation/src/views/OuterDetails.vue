<template>
	<div id="OuterDetails">
		<button class="btn btn-success">成功</button>
		<vxe-table :data="tableData">
			<vxe-table-column type="seq" title="Seq" width="80"></vxe-table-column>
			<vxe-table-column field="name" title="Name"></vxe-table-column>
			<vxe-table-column field="sex" title="Sex"></vxe-table-column>
			<vxe-table-column field="address" title="Address"></vxe-table-column>
		</vxe-table>
	</div>
</template>

<script>
	export default {
		data() {
			return {
				tableData: [{
						id: 10001,
						name: 'Test1',
						role: 'Develop',
						sex: 'Man',
						address: 'Shenzhen'
					},
					{
						id: 10002,
						name: 'Test2',
						role: 'Test',
						sex: 'Man',
						address: 'Guangzhou'
					},
					{
						id: 10003,
						name: 'Test3',
						role: 'PM',
						sex: 'Man',
						address: 'Shanghai'
					}
				]
			}
		}
	}
</script>

<style>
</style>
