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

<body class="pageHome">
<header>
	<h1><a href="${systemProp.appServerUrl}/wap/wap!index.action" />Magme</a></h1>
    <nav>
    	<a href="${systemProp.appServerUrl}/wap/wap!index.action" class="current" />首页</a>
    	<a href="javascript:void(0)" />看米</a>
    </nav>
</header>
<section>
	<#list issueList as issue>
		<#if (issue_index%2==0)>
		<section>
		</#if>
	    	<a href="/wap/wap!read.action?issueId=${issue.id}&pageNo=1" /><img src="${systemProp.magServerUrl}/${issue.publicationId}/100_${issue.id}.jpg" /><span>${issue.publicationName}</span></a>
	    <#if (issue_index%2==1)||((issue_index+1)==issueList?size)>
	    </section>
	    </#if>	
	</#list>
</section>
<footer>
    <p>Copyright &copy; 2011 麦米版权所有 | 沪ICP备 <a href="http://www.miibeian.gov.cn" target="_blank">10217320</a>号</p>
</footer>
</body>
</html>