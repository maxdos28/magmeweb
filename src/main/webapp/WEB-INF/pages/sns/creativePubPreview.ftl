<!DOCTYPE HTML>
<!--[if lt IE 7 ]> <html class="ieOld ie6"> <![endif]-->
<!--[if IE 7 ]>    <html class="ieOld ie7"> <![endif]-->
<!--[if IE 8 ]>    <html class="ieOld ie8"> <![endif]-->
<!--[if (gte IE 9)|!(IE)]><!--> <html> <!--<![endif]-->
<head>
<title>发布作品 - 麦米网Magme</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${systemProp.staticServerUrl}/v3/style/pop.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/reset.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/global.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/channelDetailv4.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/channelM1V4.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${systemProp.staticServerUrl}/v3/template/style/jquery.mCustomScrollbar.css" />
<link rel="stylesheet" type="text/css" href="${systemProp.staticServerUrl}/v3/template/style/tpl.css" />

<script src="${systemProp.staticServerUrl}/js/systemProp.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery-1.6.2.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.easing.1.3.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.inputfocus.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/scrolltopcontrol.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.coverImg.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.mousewheel.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.mutilSlidedoor.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/useFunction.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.imageUploader.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/global.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/channelM1_1.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/swfobject.js"></script>
<script src="${systemProp.staticServerUrl}/v3/kindeditor/kindeditor.js"></script>


<script src="${systemProp.staticServerUrl}/v3/js/shareToInternet.js"></script>


<script src="${systemProp.staticServerUrl}/v3/js/jquery.tagbox.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/sns/creativePub.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.sampleFancyBox.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.lightbox.js"></script>


<script src="${systemProp.staticServerUrl}/v3/template/js/jquery-ui-1.8.21.custom.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/template/js/jquery.mCustomScrollbar.js"></script>
<script src="${systemProp.staticServerUrl}/v3/template/js/tpl.js"></script>
<!--[if lt IE 7]>
<script src="js/DD_belatedPNG_0.0.8a-min.js"></script>
<script>
  DD_belatedPNG.fix('.png');
  document.execCommand("BackgroundImageCache",false,true);
</script>
<![endif]-->
<script src="${systemProp.staticServerUrl}/v3/js/sns/creative.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/sns/music.js"></script>
<script>
$(function(){
	fnGenerate("${imgCount!'0'}");
});
</script>
</head>
<body class="pageSharePreview">
<!--header-->
<#import "../components/header.ftl" as header>
<@header.main searchType="User"/>
<input type="hidden" id="template_pattern" value="${(creative.tempPattern)!'1a'}"/>
<!--body-->
<div class="topReader20121108">
    <div class="inner clearFix">
		<div id="mainContent" class="mainContent tpl${imgCount!'0'} tpl${tempPattern!'1a'}-">
		<div class="content" id="mainText">
		    <h1>${(creative.secondTitle)!}</h1>
		    <div class="source">
		       <a href="javascript:void(0)">
		         <img src="${systemProp.profileServerUrl}${(session_user.avatar60)!}" />
		       </a><strong>${(session_user.nickName)!}</strong><span>预览发布中</span>
		    </div>
		    ${(creative.content)!}
		</div>
		<#if creativeExList?? && (creativeExList?size>0)>
		  <#list creativeExList as cEx>
			<a href="javascript:void(0)" class="pic p1">
			   <img src="${systemProp.staticServerUrl}${(cEx.imgPath)!}" width="${(cEx.w)!}" height="${(cEx.h)!}" />
			   <span>${(cEx.content)!}</span>
			</a>
		  </#list>
		</#if>
		</div>
    </div>
</div>

<div class="body clearFix tCenter">
    <a class="btnGB" href="#">重新排版</a><a class="btnWB" href="#">选择模板</a><a class="btnAB" id="confirmPublish" href="javascript:void(0)">确认发布</a><a class="btnBB" href="#">发布系列页</a>
</div>


<#import "../dialog/creativeEnsureTemp.ftl" as magezineShow>
<#import "../components/footer.ftl" as footer>
<@magezineShow.main/>
<@footer.main class="footerMini footerStatic" />
</body>
</html>
