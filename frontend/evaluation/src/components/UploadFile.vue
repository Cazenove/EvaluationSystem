<!-- 注册模态框，点击注册弹出 -->
<template>
	<div id="AdminUserCreateModal">
		<vxe-button status="info" data-toggle="modal" data-target="#uploadModal">一键导入</vxe-button>
		<!-- 注册模态框 -->
		<div class="modal fade" id="uploadModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title" id="registerModalLabel">一键导入</h5>
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">
						<div class="container-fluid">
							<div class="form-group">
								<label for="classId" class="col-form-label">班级</label>
								<select class="form-control" v-model="classId" @change="updateClassId">
									<option disabled="disabled" :value="null">请选择</option>
									<option v-for="(item, index) in classList" :value="item.classId" :key="item.classId">{{item.className}}</option>
								</select>
							</div>
							<div class="form-group">
								<label for="exampleFormControlFile1">导入文件</label>
								<input type="file" ref="upload" accept=".xls,.xlsx" class="form-control-file" id="exampleFormControlFile1">
							</div>
						</div>
						<p v-for="item in msg">{{item}}</p>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary" data-dismiss="modal" @click="close">取消</button>
						<button type="button" class="btn btn-primary" @click="uploadList">导入</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
	import axios from 'axios'
	import api from '../router/httpConfig.js'
	import UserNav from '../components/UserNav.vue'
	import XLSX from 'xlsx'

	export default {
		inject: ['reload'],
		data() {
			return {
				classList: [],
				classId: null,
				file: null,
				response: {},
				outputs: {},
				msg: [],
				id: null,
				name: null
			}
		},
		created() {
			//创建的时候获取班级小组列表
			this.getClassList();
		},
		methods: {
			getClassList() {
				var self = this;
				axios.get(api.adminClassList, null)
				.then(function(res) {
					self.classList = res.data.data;
				}).catch(function(error) {
					console.log(error);
				})
			},
			close() {},
			updateClassId() {
				for(var i=0; i<this.outputs.length; i++) {
					this.outputs[i].classId = this.classId;
				}
			},
			readExcel(e) { //表格导入
				var that = this;
				const files = e.target.files;
				console.log(files);
				if (files.length <= 0) { //如果没有文件名
					return false;
				} else if (!/\.(xls|xlsx)$/.test(files[0].name.toLowerCase())) {
					this.$Message.error('上传格式不正确，请上传xls或者xlsx格式');
					return false;
				}
				const fileReader = new FileReader();
				fileReader.onload = (ev) => {
					try {
						const data = ev.target.result;
						const workbook = XLSX.read(data, {
							type: 'binary'
						});
						const wsname = workbook.SheetNames[0]; //取第一张表
						const ws = XLSX.utils.sheet_to_json(workbook.Sheets[wsname]); //生成json表格内容
						that.outputs = []; //清空接收数据
						//编辑数据
						for (var i = 0; i < ws.length; i++) {
							var sheetData = {
								userId: ws[i].id.toString(),
								password: "123456",
								name: ws[i].name,
								telephone: ws[i].telephone.toString(),
								classId: that.classId,
								groupNum: ws[i].groupNum,
								status: ws[i].status
							}
							that.outputs.push(sheetData);
						}
					} catch (e) {
						return false;
					}
				};
				fileReader.readAsBinaryString(files[0]);
			},
			uploadList() {
				this.updateClassId();
				var self = this;
				for(var i=0; i<this.outputs.length; i++) {
					axios.post(api.register,this.outputs[i])
					.then(function(res) {
						self.msg.push(res.data.msg);
					}).catch(function(error) {
						console.log(error);
					})
				}
			}
		},
		mounted() {
			this.$refs.upload.addEventListener('change', e => { //绑定监听表格导入事件
				this.readExcel(e);
			})
		},
	}
</script>

<style>
</style>
