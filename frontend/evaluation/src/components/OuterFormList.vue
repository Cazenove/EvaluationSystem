<template>
	<div id="outerFormList">
		<ul>
			<li v-for="item in list" :key="item.id">
				<router-link :to="{path:'/outer',query:{id:item.id}}">
					{{item.id}}.{{item.name}}
				</router-link>
			</li>
		</ul>
	</div>
</template>

<script>

export default {
	data() {
		return {
			status: true,
			list: [
				{
					id:1,
					name:"第一次团队作业组间评分表_第一组"
				},{
					id:2, //轮次
					name:"第二次团队作业组间评分表_第一组"
				},{
					id:3, //轮次
					name:"第三次团队作业组间评分表_第一组"
				}
			]
		}
	},
	methods: {
	}
}
</script>

<style>
</style>
