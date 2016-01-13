<#import "../components/publishadminheader.ftl" as publisheradminheader>
<#import "../publishadmin/publisheradmin.ftl" as publisheradmin>
<#import "../dialog/createPublication.ftl" as createPublication>
<#import "../dialog/editIssue.ftl" as editIssue>
<#import "../dialog/editPublication.ftl" as editPublication>
<#import "../dialog/editPublisherInfo.ftl" as editPublisher>
<#import "../dialog/uploadPublisherLogo.ftl" as uploadPublisherLogo>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Magme</title>
<script src="${systemProp.staticServerUrl}/js/systemProp.js"></script>
<link href="${systemProp.staticServerUrl}/style/reset.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/style/pop.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/style/global.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/style/pop.css" rel="stylesheet" type="text/css" />
<script src="${systemProp.staticServerUrl}/js/jquery-1.6.2.min.js"></script>
<script src="${systemProp.staticServerUrl}/js/jquery.lightbox.js"></script>
<script src="${systemProp.staticServerUrl}/js/shareToInternet.js"></script>
<script src="${systemProp.staticServerUrl}/js/useFunction.js"></script>
<script src="${systemProp.staticServerUrl}/js/jquery.sampleFancyBox.js"></script>
<script src="${systemProp.staticServerUrl}/js/jquery.inputfocus.js"></script>
<script src="${systemProp.staticServerUrl}/js/global.js"></script>

<link href="${systemProp.staticServerUrl}/style/channelPublisher.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/style/jquery.jscrollpane.css" rel="stylesheet" type="text/css" />
<script src="${systemProp.staticServerUrl}/js/jquery.easing.1.3.js"></script>
<script src="${systemProp.staticServerUrl}/js/jquery.tagbox.js"></script>
<script src="${systemProp.staticServerUrl}/js/jquery.jscrollpane.min.js"></script>
<script src="${systemProp.staticServerUrl}/js/publisher.js"></script>

<!--[if lte IE 6]>
<link href="${systemProp.staticServerUrl}/style/ie6.css" rel="stylesheet" type="text/css" />
<![endif]-->
<!--[if lt IE 7]>
<script src="${systemProp.staticServerUrl}/js/DD_belatedPNG_0.0.8a-min.js"></script>
<script>
  DD_belatedPNG.fix('.png');
  document.execCommand("BackgroundImageCache",false,true);
</script>
<![endif]-->
</head>
<body>
    <#if !(menuId??) || menuId==''>
	   <#assign menuId="publish"/>
	</#if>
	<!--header-->
	<@publisheradminheader.main menuId/>
	<!--body-->
	${body}
</div>
</body>
</html>

