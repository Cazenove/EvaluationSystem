<template>
    <div>
        <ManageNav />
        <nav aria-label="breadcrumb">
			<ol class="breadcrumb">
				<li class="breadcrumb-item"><a href="#">主页</a></li>
				<li class="breadcrumb-item active" aria-current="page">{{this.title}}</li>
			</ol>
		</nav>
        <h1 class="offset-md-1">{{this.title}}</h1>
    </div>
</template>

<script>
	import ManageNav from '../../components/ManageNav.vue'
	export default {
		components: {
			ManageNav,
		},
		data () {
			return {
                title: "个人历次得分",
				tableData: [
				],
                request: {
                },
                response: {
                }
			}
		},
        created() {
            this.init();
        },
        methods: {
            getRequest() {
            },
            getResponse() {
            },
            init() {
                this.getRequest();
                this.getResponse();
            }
        }
        
	}
</script>

<style>
</style>
