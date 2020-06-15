<!-- 登录注册页面 -->
<template>
	
	<div id="login">
		<!-- <div class="jumbotron jumbotron-fluid">
			<div class="container">
			<h1 class="display-4">{{welcomemsg}}</h1>
			<p class="lead">登录或注册来使用{{welcomemsg}}</p>
			</div>
		</div> -->
		<!-- <div :style="{backgroundImage: 'url(' + require('@/assets/home-bg.jpg') + ')' }" style="width: 100%; height: 500px;">
			
		</div> -->
		<header class="masthead" id="header" :style="{backgroundImage: 'url(https://api.berryapi.net/?service=App.Bing.Images&day=2)' }">
			<!-- <img :src="imgUrl" style="position: fixed; z-index: -1;"/> -->
			<div class="overlay"></div>
			<div class="container">
				<div class="row">
					<div class="col-lg-8 col-md-10 mx-auto">
						<div class="site-heading">
							<h1>「软件工程实践」<br />互动评价系统</h1>
							<span class="subheading">登录或注册来使用{{welcomemsg}}</span>
							<br>
							<span style="font-weight:300;font-family: 'Open Sans', 'Helvetica Neue', Helvetica, Arial, sans-serif;">建议使用PC端进行操作</span>
						</div>
					</div>
				</div>
			</div>
		</header>
		<div style="height: 100%; width: auto; padding: 0px; margin: 5% 10%; border-radius: 10px;">
			
		<transition name="fade" mode="out-in">
		<div class="container" id="loginForm" v-if="!register">
			<div class="form-row">
				<div class="form-group col-md-6 offset-md-3">
					<div>
						<input type="text" class="form-control mb-3" placeholder="用户名" id="username" v-model="userId"/>
					</div>
					<div>
						<input type="password" class="form-control" placeholder="密码" id="pw" v-model="password"/>
					</div>
				</div>
				
			</div>
			<div class="form-row">
				<div class="form-group col-md-6 col-xs-6 offset-md-3">
					<!-- <vxe-checkbox content="记住我" v-model="remember" style="vertical-align: initial;"/> -->
					<vxe-checkbox content="记住我" v-model="remember" style="float: right;"></vxe-checkbox>
					<!-- <button style="float: right;" class="btn btn-link" @click="showRegisterModal()">注册</button> -->
					
				</div>
				
			</div>
			<div class="form-row">
				<div class="form-group col-md-6 col-xs-6 offset-md-3">
					
					
					<button class="btn btn-primary" @click="login()" style="width: 100%;">登录</button>
					<button style="text-align: center; width: 100%;" class="btn btn-link" @click="register = !register">注册</button>
				</div>
			</div>
		</div>
		</transition>
		<transition name="fade" mode="out-in">
		<div v-if="register" class="container">
			<div class="form-row">
				<div class="form-group col-md-6 col-xs-6 offset-md-3">
			<RegisterModal ref="RegisterModal"/>
			</div>
			</div>
			<div class="form-row">
				<div class="form-group col-md-6 col-xs-6 offset-md-3">
					
					
					<button class="btn btn-primary" @click="registerFun()" style="width: 100%;">注册</button>
					<button style="text-align: center; width: 100%;" class="btn btn-link" @click="register = !register">返回</button>
				</div>
			</div>
		</div>
		
		</transition>
		</div>
	</div>
</template>

<script>
	import axios from 'axios'
	import api from '../router/httpConfig.js'
	import RegisterModal from '../components/RegisterModal.vue'
	import $ from 'jquery'
	import XEUtils from 'xe-utils'
	// import imgurl from '@/assets/home-bg.jpg'
	export default {
		mounted() {
			this.getCookie();
		},
		inject: ['reload'],
		data() {
			return {
				register: false,
				remember: false,
				data: {
					status:'',
					data: []
				},
				// imgUrl: imgurl,
				imgUrl: require('@/assets/home-bg.jpg'),
				welcomemsg: "「软件工程实践」互动评价系统",
				userId: "",
				password: "",
			}
		},
		created(){
			var random = Math.floor(Math.random()*10);
			if(random % 2 == 1){
				this.imgUrl = require('@/assets/about-bg.jpg');
			}
			
			this.init();
			let that = this;
			    document.onkeypress = function(e) {
			      var keycode = document.all ? event.keyCode : e.which;
				  
			      if (keycode == 13 && that.$route.path=='/' && that.register == false) {
					that.login();// 登录方法名
					return false;
			      }
				  else if (keycode == 13 && that.$route.path=='/' && that.register == true){
					  that.registerFun();
					  return false;
				  }
			    };
		},
		components: {//注册组件
			RegisterModal
		},
		methods: {
			init(){
				var header = $("#header");
				
				header.css("background-image",this.imgUrl);
			},
			setCookie(name,pwd,remember,exdays){
				var exdate = new Date();
				        exdate.setTime(exdate.getTime() + 24 * 60 * 60 * 1000 * exdays); //保存的天数
				        document.cookie = "username=" + name + ";path=/;expires=" + exdate.toLocaleString()
				        document.cookie = "pw=" + pwd + ";path=/;expires=" + exdate.toLocaleString()
				        document.cookie = "rm=" + remember + ";path=/;expires=" + exdate.toLocaleString()
				// console.log(document.cookie);
			},
			getCookie(){
				if (document.cookie.length > 0) {
				            var arr = document.cookie.split('; '); //这里显示的格式需要切割一下自己可输出看下
				//             console.log(arr)
				            for (var i = 0; i < arr.length; i++) {
				                var arr2 = arr[i].split('='); //再次切割
				                //判断查找相对应的值
								// console.log(arr2);
				                if (arr2[0] == 'username') {
				                    this.userId = arr2[1]; //保存到保存数据的地方
				                } else if (arr2[0] == 'pw') {
				                    this.password = arr2[1];
				                }else if (arr2[0] == 'rm' && arr2[1] == 'true') {
									this.remember = true;
								}
				            }
				        }
			},
			clearCookie(){
					this.setCookie("", "", false, -1); //修改2值都为空，天数为负1天就好了
				},
			showRegisterModal() {
				this.$refs.RegisterModal.showModal();
			},
			registerFun() {
				this.$refs.RegisterModal.register();
			},
			login() {
				
				var self = this;
				var pw = this.$md5(this.$data.password);
				// console.log(pw);
				axios.post(api.login,{
					userId:this.$data.userId,
					password:this.$data.password
					// password:pw
				}).then(function(res) {
					var ses = window.sessionStorage;
					if(res.status == 200) {
						self.$store.state.loginState = res.data.status;
						if(res.data.status == 1 || res.data.status == 2) {
							self.$store.state.userInfo.userId = res.data.data.userId;
							self.$store.state.userInfo.groupId = res.data.data.groupId;
							self.$store.state.userInfo.userName = res.data.data.name;
							self.$store.state.userInfo.status = res.data.data.status;
							self.$store.state.userInfo.password = res.data.data.password;
							self.$store.state.userInfo.telephone = res.data.data.telephone;
							self.$store.state.userInfo.classId = res.data.data.classId;
							ses.setItem('loginState', res.data.status);
							self.$router.push('/home');
						} else if(res.data.status == 3) {
							self.$store.state.adminInfo.assistantId = res.data.data.assistantId;
							self.$store.state.adminInfo.classId = res.data.data.classId;
							self.$store.state.adminInfo.name = res.data.data.name;
							self.$store.state.adminInfo.password = res.data.data.password;
							self.$store.state.adminInfo.telephone = res.data.data.telephone;
							self.$store.state.adminInfo.status = 3;
							ses.setItem('loginState', res.data.status);
							self.$router.push('/admin/user');
						} else if(res.data.status == 4) {
							self.$store.state.adminInfo.status = 4;
							ses.setItem('loginState', res.data.status);
							self.$router.push('/admin/user');
						} else {
							alert(res.data.msg);
						}
					}
				}).catch(function(error) {
					console.log(error);
				})
				if(this.remember == true){
					this.setCookie(this.userId,this.password,this.remember,30);
				}else{
					this.clearCookie();
				}
			},
		}
	}
</script>

<style>
	#login{
		/* text-align: center; */
	}
	/*!
	 * Start Bootstrap - Clean Blog v5.0.8 (https://startbootstrap.com/template-overviews/clean-blog)
	 * Copyright 2013-2019 Start Bootstrap
	 * Licensed under MIT (https://github.com/BlackrockDigital/startbootstrap-clean-blog/blob/master/LICENSE)
	 */
	body{
		/* background-image: none; */
	}
	.vxe-checkbox>input:checked+.vxe-checkbox--icon{
		background-color: #007bff;
		border-color: #007bff;
	}
	.vxe-checkbox>input:checked+.vxe-checkbox--icon+.vxe-checkbox--label{
		color: #007bff;
	}
	.fade-enter-active {
	  transition: all .5s cubic-bezier(1.0, 0.5, 0.8, 1.0);
	}
	.fade-leave-active {
	  transition: all .0s cubic-bezier(1.0, 0.5, 0.8, 1.0);
	}
	.fade-enter, .fade-leave-to
	/* .slide-fade-leave-active for below version 2.1.8 */ {
	  transform: translateX(10px);
	  opacity: 0;
	  position: fixed;
	}
</style>
