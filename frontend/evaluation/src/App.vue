<template>
	<div id="app">
		<!-- <div style="position: fixed; height: 100%; width: 100%; background-color: #e9ecef; z-index: -1;"></div> -->
		<!-- <router-view /> -->
		<router-view v-if="isReloadAlive"></router-view>
	</div>
	
</template>

<script>
	import css from './components/CSS.vue'
	
	export default {
		provide() {
			return {
				reload: this.reload
			}
		},
		data() {
			return {
				isReloadAlive: true
			}
		},
		methods: {
			reload() {
				this.isReloadAlive = false;
				this.$nextTick(function() {
					this.isReloadAlive = true;
				})
			},
			background() {
			    function n(n, e, t) {
			        return n.getAttribute(e) || t
			    }
			    function e(n) {
			        return document.getElementsByTagName(n)
			    }
			    function t() {
			        var t = e("script"),
			        o = t.length,
			        i = t[o - 1];
			        return {
			            l: o,
			            z: n(i, "zIndex", -1),
			            o: n(i, "opacity", .5),
			            c: n(i, "color", "0,0,0"),
			            n: n(i, "count", 99)
			        }
			    }
			    function o() {
			        a = m.width = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth,
			        c = m.height = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight
			    }
			    function i() {
			        r.clearRect(0, 0, a, c);
			        var n, e, t, o, m, l;
			        s.forEach(function(i, x) {
			            for (i.x += i.xa, i.y += i.ya, i.xa *= i.x > a || i.x < 0 ? -1 : 1, i.ya *= i.y > c || i.y < 0 ? -1 : 1, r.fillRect(i.x - .5, i.y - .5, 1, 1), e = x + 1; e < u.length; e++) n = u[e],
			            null !== n.x && null !== n.y && (o = i.x - n.x, m = i.y - n.y, l = o * o + m * m, l < n.max && (n === y && l >= n.max / 2 && (i.x -= .03 * o, i.y -= .03 * m), t = (n.max - l) / n.max, r.beginPath(), r.lineWidth = t / 2, r.strokeStyle = "rgba(" + d.c + "," + (t + .2) + ")", r.moveTo(i.x, i.y), r.lineTo(n.x, n.y), r.stroke()))
			        }),
			        x(i)
			    }
			    var a, c, u, m = document.createElement("canvas"),
			    d = t(),
			    l = "c_n" + d.l,
			    r = m.getContext("2d"),
			    x = window.requestAnimationFrame || window.webkitRequestAnimationFrame || window.mozRequestAnimationFrame || window.oRequestAnimationFrame || window.msRequestAnimationFrame ||
			    function(n) {
			        window.setTimeout(n, 1e3 / 45)
			    },
			    w = Math.random,
			    y = {
			        x: null,
			        y: null,
			        max: 2e4
			    };
			    m.id = l,
			    m.style.cssText = "position:fixed;top:0;left:0;z-index:" + d.z + ";opacity:" + d.o,
			    e("body")[0].appendChild(m),
			    o(),
			    window.onresize = o,
			    window.onmousemove = function(n) {
			        n = n || window.event,
			        y.x = n.clientX,
			        y.y = n.clientY
			    },
			    window.onmouseout = function() {
			        y.x = null,
			        y.y = null
			    };
			    for (var s = [], f = 0; d.n > f; f++) {
			        var h = w() * a,
			        g = w() * c,
			        v = 2 * w() - 1,
			        p = 2 * w() - 1;
			        s.push({
			            x: h,
			            y: g,
			            xa: v,
			            ya: p,
			            max: 6e3
			        })
			    }
			    u = s.concat([y]),
			    setTimeout(function() {
			        i()
			    },
			    100)
			},
			
		},
		name: 'App',
		created() {
			// this.background();
			//在页面加载时读取sessionStorage里的状态信息
			if (sessionStorage.getItem("store")) {
				this.$store.replaceState(Object.assign({}, this.$store.state, JSON.parse(sessionStorage.getItem("store"))))
			}

			//在页面刷新时将vuex里的信息保存到sessionStorage里
			window.addEventListener("beforeunload", () => {
				sessionStorage.setItem("store", JSON.stringify(this.$store.state))
			})
		}
	}
</script>

<style>
	.bg {
		background: url(https://api.berryapi.net/?service=App.Bing.Images&day=1) no-repeat;
		background-size: cover;
		background-position: center;
		height: 100%;
		width: 100%;
		filter: blur(10px);
		position: absolute;
		z-index: -1;
	}
	#app {
		font-family: Avenir, Helvetica, Arial, sans-serif;
		-webkit-font-smoothing: antialiased;
		-moz-osx-font-smoothing: grayscale;
		color: #2c3e50;
	}
</style>
