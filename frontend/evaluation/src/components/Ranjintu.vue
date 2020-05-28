<template>
	<div id="myChart" :style="{width: '500px', height: '500px'}"></div>
</template>

<script>
	export default {
		data() {
			return {
				classList: [],
				classId: '',
				classInfo: {
				},
				option: {
					title: {
						text: '燃尽图'
					},
					tooltip: {
						trigger: 'axis'
					},
					legend: {
						data: ['总任务量','已经花费的时间','剩余的时间','预期进度']
					},
					grid: {
						left: '3%',
						right: '4%',
						bottom: '3%',
						containLabel: true
					},
					toolbox: {
						feature: {
							saveAsImage: {}
						}
					},
					xAxis: {
						type: 'category',
						boundaryGap: false,
						data: ['5.27','5.28','5.29','5.30','5.31','6.1','6.2']
					},
					yAxis: {
						type: 'value'
					},
					series: [
						{
							name: '总任务量',
							type: 'line',
							data: [224]
						},
						{
							name: '已经花费的时间',
							type: 'line',
							data: [40]
						},
						{
							name: '剩余时间',
							type: 'line',
							data: [184]
						},
						{
							name: '预期进度',
							type: 'line',
							data: [192, 160, 128, 96, 64, 32, 0]
						},
					]
				}
			}
		},
		mounted() {
			this.drawLine();
		},
		methods: {
			drawLine() {
				// 基于准备好的dom，初始化echarts实例
				let myChart = this.$echarts.init(document.getElementById('myChart'))
				// 绘制图表
				myChart.setOption(this.option);
			}
		}
	}
</script>

<style>
</style>
