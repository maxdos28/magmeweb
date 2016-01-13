<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>关于我们</title>
<meta name="description" content="">
<meta name="keywords" content="">
<style type="text/css">
/*HTML5 CSS Reset*/

html,body,div,ul,li,img,span,p
{margin:0; padding:0; border:0; outline:0; font-weight:inherit; font-style:inherit; font-family:inherit; font-size:100%; text-align:left; vertical-align: baseline; background:transparent;}
ul {list-style:disc;}

html{background: #1e1e1e;overflow-x:hidden;-webkit-overflow-scrolling:touch;  }
body *{font-size:14px;font-family: arial;color: #fff;}
li,ul{list-style: none;}
* { -webkit-text-size-adjust: none;}


html{background: #1e1e1e;height:100%;}
.appBody{width: 100%;}
.about_us{padding: 20px 15px 0;}
.about_us p *{font-size: 14px;-webkit-user-select:auto;word-wrap:break-word;}
.about_us p{margin-bottom: 10px;line-height: 1.5;}
.about_us p span{color: #9e9e9e;}
.about_us em{font-style: normal;}
.appBody .about_us p a.copy{margin-left:10px;color: blue;}
#weiboBtn img{ position:relative; top:5px;}


</style>
</head>
<body>
<div class="appBody">

	<div class="about_us">
		<p>唤醒“隐市高手”，与“时尚说教”再见！</p>
		<p>感受全新触觉阅读体验，抽屉式选择精致简洁内容，在忙乱时代娱乐寻宝，轻松悦读。震撼视觉，清净心灵。</p>
		<p>LOO客还有什么？走着瞧……</p>
		<p>你可以通过以下几种方式和我们沟通和联系：</p>
		<p id="weiboBtn"><span>微博账号：</span><em>LOO客走着瞧</em> &nbsp;&nbsp;<img src="${systemProp.staticServerUrl}/v3/images/looker/add.gif" /></p>
		<P><span>LOO客在线咨询QQ：</span><em>2751365687</em></P>
		<p><span>LOO客粉丝QQ群：</span><em>311548478</em></p>
		<p><span>LOO客官方客服邮箱：</span><em><a href="mailto:lookapp@magme.com">lookapp@magme.com</a></em></p>
		<p><span>LOO客官方网站：</span><em>http://www.lookapp.me</em></p>
		<p><span>公共微信号：</span><em>lookapp</em></p>
		<p><span>微信二维码：</span></p>
		<p>
			<img src="${systemProp.staticServerUrl}/v3/images/looker/ma.jpg" width="240" height="240" />
		</p>
	</div>
</div>
</body>
</html>
<script type="text/javascript">
window.onload = function(){
	var browser={
		versions:function(){ 
		   var u = navigator.userAgent, app = navigator.appVersion; 
		   return {
				ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), 
				android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1
			};
		}(),
		language:(navigator.browserLanguage || navigator.language).toLowerCase()
	};
	document.getElementById('weiboBtn').ontouchstart = function(){

		if(this.getElementsByTagName('em')){
			//alert(this.getElementsByTagName('em')[0].innerHTML);
			if(browser.versions.ios){
				window.open("iosfollowsina:"+this.getElementsByTagName('em')[0].innerHTML);
			}
			if(browser.versions.android){
				window.magme.followsina(this.getElementsByTagName('em')[0].innerHTML);
			}
		}
	}
}
</script>

