<#import "../components/header.ftl" as header>
<#import "../components/footer.ftl" as footer>
<#import "../components/gap.ftl" as gap>

<!DOCTYPE HTML>
<!--[if lt IE 7 ]> <html class="ie6"> <![endif]-->
<!--[if IE 7 ]>    <html class="ie7"> <![endif]-->
<!--[if IE 8 ]>    <html class="ie8"> <![endif]-->
<!--[if (gte IE 9)|!(IE)]><!--> <html> <!--<![endif]-->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Magme</title>
<link href="${systemProp.staticServerUrl}/v3/style/reset.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/pop.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/global.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/channelHtmlpagev6.css" rel="stylesheet" type="text/css" />

<script src="${systemProp.staticServerUrl}/js/systemProp.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery-1.6.2.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.masonry.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.easing.1.3.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.lazyload.mini.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.inputfocus.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.lightbox.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.sampleFancyBox.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.tmpl.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.tagbox.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/scrolltopcontrol.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/shareToInternet.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/useFunction.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/global.js"></script>
<!--[if lt IE 7]>
<script src="${systemProp.staticServerUrl}/v3/js/DD_belatedPNG_0.0.8a-min.js"></script>
<script>
  DD_belatedPNG.fix('.png');
  document.execCommand("BackgroundImageCache",false,true);
</script>
<![endif]-->
</head>
<body>

<!--header-->
<div class="header20120905 clearFix">
	<div class="outer headerTop">
    	<div class="inner">
            <ul class="newnav newnavR">
                <li class=""><a target="_blank" class="navcolor" href="http://go.magme.com/passport-login.html">登陆</a></li>
                <li class=""><a target="_blank" class="navcolor" href="http://go.magme.com/passport-signup.html">注册</a></li>
            </ul>
            <ul class="newnav newnavL">
                <li class="home"><a href="${systemProp.appServerUrl}/">资讯</a></li>
                <li class="magazine"><a href="${systemProp.appServerUrl}/publish/magazine.html">杂志</a></li>
            <ul>    
        </div>
    </div>
	<div class="outer headerNav">
        <div class="inner clearFix">
            <div class="meshop"><a href="#"><img src="${systemProp.staticServerUrl}/v3/images/mego/btn.png"/></a></div>
            <div class="logo" id="logo"><a class="png" href="http://www.magme.com/" title="世界新杂志 发现新内容">麦米网Magme</a></div>
            <div class="subNav">
            </div>
        </div>
    </div>
</div>

<!--body-->
${body}

<!--footer-->
<@footer.main class="footerMini footerStatic"/>

</body>
</html>