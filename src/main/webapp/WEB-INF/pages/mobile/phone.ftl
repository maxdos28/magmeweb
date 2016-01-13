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
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/mobile/js/jquery.masonry.min.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/mobile/js/jquery.tmpl.min.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/mobile/js/jquery.lazyload.mini.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/mobile/js/global.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/mobile/js/mobile.index.js"></script>

<title>Magme Mobile</title>
</head>

<body class="phone" id="body">
<div class="header" id="header">
	<a class="logo" href="/mobile/index.html">Magme</a>
    <div class="viewType">
        <a id="viewTypeBtn" class="list" href="javascript:void(0)">list/grid</a>
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
<div id="eventList" class="home clearFix">
<#if fpageEventList?? && (fpageEventList?size>0)>
   <input type="hidden" id="eventBegin" value="${size}"/>
	<#list fpageEventList as fpageEvent>
  		<div class="item size${fpageEvent.eventClass}">
  			<a href="${systemProp.appServerUrl}/mobile/read.html?eventId=${fpageEvent.id}&pageNo=${fpageEvent.pageNo}<#if sortId??>&sortId=${sortId}</#if>">
  				<div class="img"><span>
  					<img src="${systemProp.fpageServerUrl}/event/${fpageEvent.imgFile}">
  				</span></div>
  				<p>
  					<strong>${fpageEvent.title?default("")}</strong>
  					<span>${fpageEvent.description?default("")}</span>
  				</p>
  			</a>
  		</div>
	</#list>
</#if>
</div>
<div id="pageLoad" class="pageLoad"><span>正在加载内容...</span></div>
<@mobilefooter.main/>
</body>
</html>
