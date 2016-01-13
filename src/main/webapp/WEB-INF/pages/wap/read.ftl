<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;" />
<meta name="format-detection" content="telephone=no" />
<link rel="apple-touch-icon" href="${systemProp.staticServerUrl}/mobile/images/iconApp.png" />
<link rel="stylesheet" type="text/css" href="${systemProp.staticServerUrl}/mobile/style/reset.css"/>		
<link rel="stylesheet" type="text/css" href="${systemProp.staticServerUrl}/mobile/style/global.css"/>
<link media="only screen and (max-device-width: 480px)" href="${systemProp.staticServerUrl}/mobile/style/small-device.css" type= "text/css" rel="stylesheet" />
<title>Magme Mobile</title>
</head>

<body class="pageRead">
<header>
	<section>
    	<a href="${systemProp.appServerUrl}/wap/wap!index.action" class="back" />返回</a>
    	<a href="<#if (pageNo??)&&((pageNo-1)>=0)>${systemProp.appServerUrl}/wap/wap!read.action?issueId=${issueId}&pageNo=${pageNo-1}<#else>javascript:void(0)</#if>" class="pre" />上一页</a>
    	<a href="<#if ((pageNo??)&&((pageNo+1)>=0)&&(pageNo<issue.totalPages))||!(pageNo??)>${systemProp.appServerUrl}/wap/wap!read.action?issueId=${issueId}&pageNo=${pageNo+1}<#else>javascript:void(0)</#if>" class="next" />下一页</a>
    	<a href="${systemProp.appServerUrl}/wap/wap!read.action?issueId=${issueId}&pageNo=0" class="first" />封面</a>
    	<a href="${systemProp.appServerUrl}/wap/wap!read.action?issueId=${issueId}&pageNo=${issue.totalPages}" class="last" />封底</a>
    </section>
</header>
<section>
	<img src="${systemProp.magServerUrl}/${issue.publicationId}/${issue.id}/b${pageNo!"1"}.jpg" />
</section>
</body>
</html>