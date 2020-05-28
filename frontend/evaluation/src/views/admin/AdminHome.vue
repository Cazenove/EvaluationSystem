<template>
	<div>
		<ManageNav />
		<div class="container">
			<div class="form-group">
				<label for="classId" class="col-form-label">班级</label>
				<select class="form-control" v-model="classId" @change="getClassInfo()">
					<option disabled="disabled" :value="null">请选择</option>
					<option v-for="(item, index) in classList" :value="item.classId" :key="item.classId">{{item.className}}</option>
				</select>
			</div>
			
			<div id="myChart" :style="{width: '500px', height: '500px'}"></div>
			<!-- <Ranjintu /> -->
		</div>
	</div>
</template>

<script>
	import axios from 'axios'
	import api from '../../router/httpConfig.js'
	import ManageNav from '../../components/ManageNav.vue'
	import Vue from 'vue'
	import Vuerify from 'vuerify'
	import Ranjintu from '../../components/Ranjintu.vue'
	export default {
		inject: ['reload'],
		components: {
			ManageNav,
			Ranjintu
		},
		data() {
			return {
				classList: [],
				classId: '',
				classInfo: {
				},
				option: {
					title: {
						text: '小组得分折线图'
					},
					tooltip: {
						trigger: 'axis'
					},
					legend: {
						data: []
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
						data: []
					},
					yAxis: {
						type: 'value'
					},
					series: []
				}
			}
		},
		mounted() {
			this.getClassList();
		},
		methods: {
			getClassList() {
				var self = this;
				axios.get(api.adminClassList,null)
				.then(function(res) {
					self.classList = res.data.data;
				}).catch(function(error) {
					console.log(error);
				})
			},
			getClassInfo() {
				var self = this;
				this.init();
				axios.post(api.userEvaluationOuterList,{
					classId:this.classId
				}).then(function(res) {
					if (res.status == 200 && res.data.status == 1) {
						for(var i=0; i<res.data.data.length; i++) {
							self.option.xAxis.data[i] = res.data.data[i].name
						}
						self.drawLine();
					}
				}).catch(function(error) {
					console.log(error);
				})
				axios.get(api.adminGroupScoreList,null)
				.then(function(res) {
					if (res.status == 200 && res.data.status == 1) {
						var data = {};
						for(var i=0; i<res.data.data.length; i++) {
							if(res.data.data[i].classId == self.classId) {
								if(!data[res.data.data[i].groupId]) {
									data[res.data.data[i].groupId] = {
										name: res.data.data[i].groupName,
										type: 'line',
										data: []
									};
									data[res.data.data[i].groupId].data.push(res.data.data[i].content);
								} else {
									data[res.data.data[i].groupId].data.push(res.data.data[i].content);
								}
							}
						}
						var j = 0;
						for(var i in data) {
							self.option.series[j++] = data[i];
						}
						console.log(self.option);
					}
				}).catch(function(error) {
					console.log(error);
				})
			},
			init() {
				this.option = {
					title: {
						text: '小组得分折线图'
					},
					tooltip: {
						trigger: 'axis'
					},
					legend: {
						data: []
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
						data: []
					},
					yAxis: {
						type: 'value'
					},
					series: []
				}
			},
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
