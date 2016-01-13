<#import "../components/ipadheader.ftl" as ipadHeader>
<#import "../components/mobilefooter.ftl" as mobilefooter>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="format-detection" content="telephone=no" />
<link rel="apple-touch-icon" href="${systemProp.staticServerUrl}/v3/mobile/images/iconApp.png" />
<link rel="stylesheet" type="text/css" href="${systemProp.staticServerUrl}/v3/mobile/style/html5Reset.css"/>		
<link rel="stylesheet" type="text/css" href="${systemProp.staticServerUrl}/v3/mobile/style/global.css"/>
<link rel="stylesheet" type="text/css" href="${systemProp.staticServerUrl}/v3/mobile/style/pad.css"/>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/systemProp.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/mobile/js/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/mobile/js/jquery.masonry.min.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/mobile/js/jquery.tmpl.min.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/mobile/js/jquery.lazyload.mini.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/mobile/js/global.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/mobile/js/ipad.index.js"></script>

<title>Magme IPAD</title>
</head>
<body id="body" class="pad">
	<@ipadHeader.main/>
	
	<div id="eventList" class="body clearFix">
		<#if fpageEventList?? && (fpageEventList?size>0)>
		   <input type="hidden" id="eventBegin" value="${fpageEventList?size}"/>
			<#list fpageEventList as fpageEvent>
				<div class="item size${fpageEvent.eventClass}">
					<a href="${systemProp.appServerUrl}/mobile/read.html?eventId=${fpageEvent.id}&pageNo=${fpageEvent.pageNo}">
						<img src="${systemProp.fpageServerUrl}/event${fpageEvent.imgFile}">
						<span>${fpageEvent.title?default("")}</span>
					</a>
				</div>
			</#list>
		</#if>
	</div>
	
	<div id="pageLoad" class="pageLoad"><span>正在加载内容...</span></div>

<@mobilefooter.main/>
</body>
</html>

