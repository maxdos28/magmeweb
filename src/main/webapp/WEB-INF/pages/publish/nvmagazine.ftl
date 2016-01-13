<#import "../components/footer.ftl" as footer>
<#import "nvmagazineList.ftl" as nvmagazineList>
<!DOCTYPE HTML>
<!--[if lt IE 7 ]> <html class="ie6"> <![endif]-->
<!--[if IE 7 ]>    <html class="ie7"> <![endif]-->
<!--[if IE 8 ]>    <html class="ie8"> <![endif]-->
<!--[if (gte IE 9)|!(IE)]><!--> <html> <!--<![endif]-->
<head>
<title>女友校园网</title>
<meta name="Keywords" content="(SEO)麦米网,Magme,电子杂志,在线杂志,免费电子杂志,电子杂志下载,数码杂志,时尚杂志,汽车杂志,财经杂志">
<meta name="Description" content="(SEO)麦米网提供数百种最新杂志在线免费阅读，财经，旅游，娱乐，时尚，汽车，体育，数码生活等。在麦米看书，交流，分享，交友，新的阅读生活由此开启">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${systemProp.staticServerUrl}/v3/style/reset.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/global.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/pop.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/channelMagazine.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/conM1.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/event/20120626/style/style.css" rel="stylesheet" type="text/css" />
<script src="${systemProp.staticServerUrl}/js/systemProp.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery-1.6.2.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.lightbox.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.inputfocus.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.tagbox.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/shareToInternet.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.sampleFancyBox.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.tmpl.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.masonry.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.easing.1.3.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.lazyload.mini.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.inputfocus.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/scrolltopcontrol.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.hoverplay.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/useFunction.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/global.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/nvmagazine.js"></script>
<script>
$(function(){
	//$.jqueryhoverplay(".item",60,8); //此功能暂时不要调用
	$(".bodyMagazine .item .photo .album").click(function(){
		window.location="publisherEvent.html";
		return false;
	});
	//设置随机显示
	fnRandom("#videoAd");
	
	
	$("a[name='nvlogin']").unbind("click").live("click",function(){
		$("#popLogin").fancybox();
	});
	
	
	$("a[name='nvreg']").unbind("click").live("click",function(){
		$("#popRegister").fancybox();
	});
	
})
</script>
<!--[if lt IE 7]>
<script src="/v3/js/DD_belatedPNG_0.0.8a-min.js"></script>
<script>
  DD_belatedPNG.fix('.png');
  document.execCommand("BackgroundImageCache",false,true);
</script>
<![endif]-->
</head>
<body>
<#import "../dialog/videoAdvertise.ftl" as video>
<@video.main />
<#import "../dialog/emailSubscribe.ftl" as emailSub>
<@emailSub.main />
<!--header-->
 <div class="header clearFix">
	<div class="outer png">
        <div class="inner png">
            <h1 class="logo"><a class="png ny" href="http://nvyou.magme.com"></a><a class="png magme" href="http://www.magme.com" title="(SEO)无处不悦读">麦米网Magme</a></h1>
            <!--userBar-->
            <ul class="conUser" id="loginBar">
                <li class="reg"><a name="nvreg" href="javascript:void(0)">注册</a></li>
                <li class="login"><a name="nvlogin" href="javascript:void(0)">登录</a></li>
            </ul>
            <!--loginBar-->
            <ul class="conUser hide" id="userBar">
            	<li class="head"><a href="javascript:void(0)"><img id="avatar30" src="${systemProp.staticServerUrl}/images/head30.gif" /></a></li>
                <li class="name"><a href="javascript:void(0)"><strong id="nickName"></strong></a></li>
                <li class="logout" id="logout"><a href="javascript:void(0)" title="退出">退出</a></li>
            </ul>
            <div class="search" id="headerSearch">
          		<input type="hidden" id="u_invite_code" value="${inviteCodeSession?default('1')}" />
				<input type="hidden" name="searchType" value="${searchType?default('Issue')}" />
				<a href="javascript:void(0)">搜索</a>
				<div class="box png"><input class="in" type="text" name="queryStr" <#if queryStr?? && queryStr!="">value="${queryStr}"</#if> /><input class="btn" type="button" value="搜索" /></div>
			</div>
        </div>
    </div>
    <!--topBanner-->
    <div class="topBanner" id="topBanner">
        <div class="inner">
            <iframe src="${systemProp.appServerUrl}/baidu_clb.html#nv0_1" width="100%" height="100%" frameborder="0" scrolling="no" ></iframe>
            <a href="javascript:void(0)" class="close"></a>
        </div>
    </div>

</div>
<!--
<a class="feedbackBtn">用户反馈</a>
-->

<!--topTools-->
<div class="topTools">
    <!--顶部分类-->
    <ul class="tab clearFix">
        <li><a <#if type?? && type==1> class="current" </#if> href="http://nvyou.magme.com/index.html">最新</a></li>
        <li><a <#if type?? && type==2> class="current" </#if> href="http://nvyou.magme.com/index.html?type=2">热门</a></li>
    </ul>
</div>
<!--body-->
<@nvmagazineList.main/>
<a id="loadMore" href="javascript:void(0);" class="clickLoadMore"><span>狂点这里&nbsp;&nbsp;查看更多</span></a>
<!--
<div class="pageLoad"><span>正在加载内容...</span></div>
-->

<div id="popLogin" class="popContent">
	<div class="popLeft">
    <form id="popLoginForm" action="" onsubmit="return false;" method="post">
            <fieldset class="new">
                <h6>登&nbsp;&nbsp;录</h6>
                <div class="username">
                    <em><span class="icon"></span><input class="input" id="userName" name="userName" type="text" tipsSpan="用户名/邮箱" /></em>
                </div>
                <div class="password">
                    <em><span class="icon"></span><input class="input" id="password" name="password" type="password" tipsSpan="密码" />
                    <input class="input" id="userType" name="userType" value="nv" type="hidden" /></em>
                    <em class="tipsError">密码输错啦！</em>
                    <em class="tipsWrong"></em>
                </div>
                <div>
                	<em class="floatr"><a id="popForgetPassword" href="javascript:void(0)">忘记密码？</a></em>
                    <em class="remanber"><label><input id="remember_login" type="checkbox" checked/>下次自动登录</label></em>
                </div>
                <div>
                    <em class="m0"><a class="btnBB" id="submit" name="signIn" href="#" >登录</a></em>
                </div>
            </fieldset>
    </form>
    </div>
    <div class="popRight">
    	<p>用其他帐号登录</p>
    	<ul class="select clearFix">
    		<li><a href="javascript:void(0)" name="login_qq"><img src="${systemProp.staticServerUrl}/v3/images/icon/sns32/32-qq.gif" /></a></li>
            <li><a href="javascript:void(0)" name="login_renren"><img src="${systemProp.staticServerUrl}/v3/images/icon/sns32/32-renren.gif" /></a></li>
            <li><a href="javascript:void(0)" name="login_weibo"><img src="${systemProp.staticServerUrl}/v3/images/icon/sns32/32-weibo.gif" /></a></li>
            <li><a href="javascript:void(0)" name="login_baidu"><img src="${systemProp.staticServerUrl}/v3/images/icon/sns32/32-baidu.gif" /></a></li>
            <li><a href="javascript:void(0)" name="login_kaixin"><img src="${systemProp.staticServerUrl}/v3/images/icon/sns32/32-kaixin001.gif" /></a></li>
        </ul>
    	<p>没有麦米账户？</p>
        <a id="goRegPop" class="btnWB" href="#" >注册</a>
    </div>
</div>
<div id="popRegister" class="popContent">
	<div class="popLeft">
    <form id="popRegisterForm" action="" onsubmit="return false;" method="post">
            <fieldset class="new">
                <h6>新用户注册</h6>
                <div id="checkUserName">
                    <em><input class="input" id="userName" name="userName" type="text" tipsSpan="用户名" />
                    <input class="input" id="userType" name="userType" value="nv" type="hidden" /></em>
                    <em class="tipsWrong"></em>
                    <em class="tipsRight"></em>
                    <em class="tipsError">用户名已经存在</em>
                </div>
                <div id="checkEmail">
                    <em><input class="input" id="email" name="email" type="text" tipsSpan="邮箱" /></em>
                    <em class="tipsWrong"></em>
                    <em class="tipsRight"></em>
                    <em class="tipsError">邮箱错误或已注册</em>
                </div>
                <div id="checkPassword">
                    <em><input class="input" id="password" name="password" type="password" tipsSpan="密码" /></em>
                    <em class="tipsWrong"></em>
                    <em class="tipsRight"></em>
                    <em class="tipsError">密码长度不能小于6位</em>
                </div>
                <div id="checkPassword2">
                    <em><input class="input" id="password2" name="password2" type="password" tipsSpan="确认密码" /></em>
                    <em class="tipsWrong"></em>
                    <em class="tipsRight"></em>
                    <em class="tipsError">确认密码输错了</em>
                </div>
                <div>
                    <em class="m0"><a class="btnBB" id="submit" name="submit" href="#" >注册</a></em>
                </div>
            </fieldset>
    </form>
    </div>
    <div class="popRight">
    	<p>用其他帐号登录</p>
    	<ul class="select clearFix">
    		<li><a href="javascript:void(0)" name="login_qq"><img src="${systemProp.staticServerUrl}/v3/images/icon/sns32/32-qq.gif" /></a></li>
            <li><a href="javascript:void(0)" name="login_renren"><img src="${systemProp.staticServerUrl}/v3/images/icon/sns32/32-renren.gif" /></a></li>
            <li><a href="javascript:void(0)" name="login_weibo"><img src="${systemProp.staticServerUrl}/v3/images/icon/sns32/32-weibo.gif" /></a></li>
            <li><a href="javascript:void(0)" name="login_baidu"><img src="${systemProp.staticServerUrl}/v3/images/icon/sns32/32-baidu.gif" /></a></li>
            <li><a href="javascript:void(0)" name="login_kaixin"><img src="${systemProp.staticServerUrl}/v3/images/icon/sns32/32-kaixin001.gif" /></a></li>
        </ul>
    	<p>已经有麦米账户？</p>
        <a id="goLoginPop" class="btnWB" href="#" >登录</a>
    </div>
</div>


<!--footer-->
<@footer.oldmain class="footerMini footerStatic"/>
</body>
</html>