<template>
	<div id="OuterDetails">
		<h1>{{this.title}}</h1>
		<h2>{{this.id}}</h2>
		<p>
			{{this.content}}
		</p>
	</div>
</template>

<script>
	export default{
		data() {
			return {
				id:"",
				title:"",
				content:""
			}
		},
		created() {
			this.id=this.$route.query.id;
			this.title="组内评价表";
			this.content="内容balabala";
		}
	}
</script>

<style>
</style>
