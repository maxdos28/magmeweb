<#import "../components/publishadminheader.ftl" as publisheradminheader>
<#import "../publishadmin/publisheradmin.ftl" as publisheradmin>
<#import "../dialog/createPublication.ftl" as createPublication>
<#import "../dialog/editIssue.ftl" as editIssue>
<#import "../dialog/editPublication.ftl" as editPublication>
<#import "../dialog/editPublisherInfo.ftl" as editPublisher>
<#import "../dialog/uploadPublisherLogo.ftl" as uploadPublisherLogo>
<#import "../dialog/swfRetransfer.ftl" as swfRetransfer>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Magme</title>
<link href="${systemProp.staticServerUrl}/style/datepicker.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/style/reset.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/style/global.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/style/imgAreaSelect/imgareaselect-default.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/style/pop.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/style/channelManage.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="${systemProp.staticServerUrl}/js/systemProp.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/jquery.lightbox.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/jquery.inputfocus.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/datepicker.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/jquery.sampleFancyBox.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/jquery.imgareaselect.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/ajaxfileupload.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/form2object.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/useFunction.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/global.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/publisherAdmin.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/admessage.js"></script>

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
	<div class="body">
		
		<!--topBar-->
		<#if menuId=='publish'>
		    <div class="body980">
			<div class="topBar clearFix">
		        <ul class="subNav">
		            <li><a href="javascript:void(0)">杂志商中心</a></li>
		        </ul>
		    </div>
	    </#if>
		<!--bodyContent-->
		<div class="bodyContent clearFix">
		    <#if menuId=='publish'>
	    	 <@publisheradmin.main/>
	    	</#if>
	          ${body}
	    </div>
	</div>
</div>
  <#if menuId=='publish'>
    <@createPublication.main/>
	<@editIssue.main/>
	<@editPublication.main/>
	<@editPublisher.main/>
	<@uploadPublisherLogo.main/>
	<@swfRetransfer.main/>
  </#if>
</body>
</html>

