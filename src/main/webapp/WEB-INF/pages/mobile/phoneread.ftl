<#import "../components/mobilefooter.ftl" as mobilefooter>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;" />
<meta name="format-detection" content="telephone=no" />
<link rel="apple-touch-icon" href="${systemProp.staticServerUrl}/v3/mobile/images/iconApp.png" />
<link rel="stylesheet" type="text/css" href="${systemProp.staticServerUrl}/v3/mobile/style/html5Reset.css"/>		
<link rel="stylesheet" type="text/css" href="${systemProp.staticServerUrl}/v3/mobile/style/global.css"/>
<link rel="stylesheet" type="text/css" href="${systemProp.staticServerUrl}/v3/mobile/style/phone.css"/>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/systemProp.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/mobile/js/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/mobile/js/shareToInternet.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/mobile/js/global.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/mobile/js/read.js"></script>

<title>Magme Mobile</title>
</head>

<body class="phone" id="body">
<div class="header" id="header">
	<a class="logo" href="/mobile/index.html">Magme</a>
	<a class="back" href="javascript:history.go(-1)">back</a>
	<#if eventId??&& eventId!=0><a class="share weiboShare" eventId="${eventId}" href="javascript:void(0)">share</a></#if>
    <div class="viewSize">
        <a id="big" class="big" href="javascript:void(0)">big</a>
        <a id="small" class="small" href="javascript:void(0)">small</a>
    </div>
	<div class="nav">
    	<ul>
        	<li class="l1"><a <#if sortId?? && sortId=1>class="current"</#if> href="${systemProp.appServerUrl}/mobile/index.html?sortId=1"><span>汽车旅游</span></a></li>
        	<li class="l2"><a <#if sortId?? && sortId=2>class="current"</#if> href="${systemProp.appServerUrl}/mobile/index.html?sortId=2"><span>商业财经</span></a></li>
        	<li class="l3"><a <#if sortId?? && sortId=3>class="current"</#if> href="${systemProp.appServerUrl}/mobile/index.html?sortId=3"><span>时尚娱乐</span></a></li>
        	<li class="l4"><a <#if sortId?? && sortId=4>class="current"</#if> href="${systemProp.appServerUrl}/mobile/index.html?sortId=4"><span>人文情感</span></a></li>
        	<li class="l5"><a <#if sortId?? && sortId=5>class="current"</#if> href="${systemProp.appServerUrl}/mobile/index.html?sortId=5"><span>数码科技</span></a></li>
        </ul>
    </div>
</div>
<div class="tools clearFix"  lastPage="${(issue.totalPages)?default('')}">
		<#if advertise??>
		<a href="${systemProp.appServerUrl}/mobile/read.html?issueId=${issueId!""}&pageNo=${advertise.pageNo}&direct=2&curLR=${curLR!""}&adId=${(advertise.id)!""}" class="btnB floatr mgl5 btnR">下一页</a>
		<a href="${systemProp.appServerUrl}/mobile/read.html?issueId=${issueId!""}&pageNo=${advertise.pageNo-1}&direct=1&curLR=${curLR!""}&adId=${(advertise.id)!""}" class="btnB floatl btnL ">上一页</a>
		<#else>
		<a href="<#if issue.totalPages==pageNo>javascript:void(0)<#else>${systemProp.appServerUrl}/mobile/read.html?issueId=${issueId!""}&pageNo=${pageNo+1}&direct=2</#if>" class="btnB floatr mgl5 btnR <#if issue.totalPages==pageNo>disable</#if>">下一页</a>
		<a href="<#if pageNo==1>javascript:void(0)<#else>${systemProp.appServerUrl}/mobile/read.html?issueId=${issueId!""}&pageNo=${pageNo-1}&direct=1</#if>" class="btnB floatl btnL <#if pageNo==1>disable</#if>">上一页</a>
		</#if>
</div>

<div class="read phoneRead clearFix">
	<a class="img" href="javascript:void(0)">
		<#if advertise??>
		<img src="${systemProp.adServerUrl}/video/${(advertise.userId)!""}/${(advertise.id)!""}_320_${curLR!""}.jpg" width="100%" />
		<#else>
		<img src="${systemProp.magServerUrl}/${issue.publicationId}/${issue.id}/320_${pageNo}.jpg" width="100%" />
		</#if>
		<span>+</span>
	</a>
    <div id="content" class="content">
	    <#--<h1>${(fpageEvent.title)!''}</h1>-->
	    <p><pre>${(textPage.content)!''}</pre></p>
    </div>
</div>

<div class="tools clearFix" lastPage="${(issue.totalPages)?default('')}">
		<#if advertise??>
		<a href="${systemProp.appServerUrl}/mobile/read.html?issueId=${issueId!""}&pageNo=${advertise.pageNo}&direct=2&curLR=${curLR!""}&adId=${(advertise.id)!""}" class="btnB floatr mgl5 btnR">下一页</a>
		<a href="${systemProp.appServerUrl}/mobile/read.html?issueId=${issueId!""}&pageNo=${advertise.pageNo-1}&direct=1&curLR=${curLR!""}&adId=${(advertise.id)!""}" class="btnB floatl btnL ">上一页</a>
		<#else>
		<a href="<#if issue.totalPages==pageNo>javascript:void(0)<#else>${systemProp.appServerUrl}/mobile/read.html?issueId=${issueId!""}&pageNo=${pageNo+1}&direct=2</#if>" class="btnB floatr mgl5 btnR <#if issue.totalPages==pageNo>disable</#if>">下一页</a>
		<a href="<#if pageNo==1>javascript:void(0)<#else>${systemProp.appServerUrl}/mobile/read.html?issueId=${issueId!""}&pageNo=${pageNo-1}&direct=1</#if>" class="btnB floatl btnL <#if pageNo==1>disable</#if>">上一页</a>
		</#if>
	<#if eventId??&& eventId!=0><a href="javascript:void(0)" eventId="${eventId}" class="btnB floatL mgl5 btnShare weiboShare">分享到新浪微博</a></#if>
</div>

<p style="display:none;" id="contentTitle">${(fpageEvent.title)!''}</p>
<p style="display:none;" id="contentDesp">${(fpageEvent.description)!''}</p>

<@mobilefooter.main/>
</body>
</html>
