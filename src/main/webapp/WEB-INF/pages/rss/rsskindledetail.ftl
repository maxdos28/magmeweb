<!DOCTYPE HTML>
<!--[if lt IE 7 ]> <html class="ie6"> <![endif]-->
<!--[if IE 7 ]>    <html class="ie7"> <![endif]-->
<!--[if IE 8 ]>    <html class="ie8"> <![endif]-->
<!--[if (gte IE 9)|!(IE)]><!--> <html> <!--<![endif]-->
<head>
<title><#if (event.title)??> ${event.title} </#if></title>
<meta name="Keywords" content="(SEO)麦米网,Magme,电子杂志,在线杂志,免费电子杂志,电子杂志下载,数码杂志,时尚杂志,汽车杂志,财经杂志">
<meta name="Description" content="(SEO)麦米网提供数百种最新杂志在线免费阅读，财经，旅游，娱乐，时尚，汽车，体育，数码生活等。在麦米看书，交流，分享，交友，新的阅读生活由此开启">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${systemProp.staticServerUrl}/v3/style/reset.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/global.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/channelHtmlpage.css" rel="stylesheet" type="text/css" />
<style>
	h1{ color:#000; font-size:20px; margin-bottom:1em;}
	p{ color:#333; font-size:16px; line-height:1.8em; margin-bottom:1em;}
</style>
</head>
<body>
<!--header-->
<div class="header clearFix">
	<div class="outer png">
        <div class="inner png">
            <h1 class="logo" id="logo"><a class="png" href="${systemProp.appServerUrl}/index.html">(SEO)麦米 Magme</a></h1>
            <!--nav-->
            <ul class="menu" id="menu">
                <li class="home"><a href="${systemProp.appServerUrl}/index.html">首页</a></li>
                <li class="magazine"><a href="${systemProp.appServerUrl}/publish/magazine.html">杂志</a></li>
                <li class="kanmi"><a href="${systemProp.appServerUrl}/user-image/now.html">看米</a></li>
            </ul>
        </div>
    </div>
</div>


<div class="body pageStatic">
  <div class="conLeftMiddleRight clearFix">
	  <img class="floatlImg" src="${systemProp.staticServerUrl}/fpage/event/${(event.imgFile)?default("")}"/>
		<#if textPageList?? && (textPageList?size>0)>
		     <#list textPageList as textPage>
		        ${(textPage.content)?default("")}
		     </#list>
		</#if>
    </div>
</div>
</body>
</html>
