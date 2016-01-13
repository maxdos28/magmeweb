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
<link href="${systemProp.staticServerUrl}/v3/style/channelSns.css" rel="stylesheet" type="text/css" />

<script src="${systemProp.staticServerUrl}/js/systemProp.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery-1.6.2.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.masonry.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.easing.1.3.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.lazyload.mini.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.onImagesLoad.min.js"></script>
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
<@header.main searchType="User"/>

<!--body-->
<div <#if tab??&&tab=="friend">class="body noBg bodyUser fullScreen clearFix"<#elseif tab??&&tab=="enjoyIssue">class="body noBg bodyMagazine fullScreen clearFix" id="userCenterWall"<#else>class="body noBg bodyKanmi fullScreen clearFix" id="kanmiWall"</#if>>
  <div class="titleBar clearFix"><h2><span>${(session_user.nickName)!""}</span>的全部<#if tab??&&tab=="friend">好友<#elseif tab??&&tab=="enjoyIssue">杂志<#else>图片</#if></h2></div>
  <div class="item itemUser clearFix">
    <div class="userInfo"> <strong class="name">${(session_user.nickName)!""}</strong> <a href="${systemProp.appServerUrl}/user-visit!index.action?userId=${session_user.id}" class="img"><img src="<#if ((session_user.avatar)??)&&(session_user.avatar!="")>${systemProp.profileServerUrl+session_user.avatar60}<#else>${systemProp.staticServerUrl}/images/head60.gif</#if>" /></a>
      <ul class="atten png clearFix">
        <li <#if tab??&&(tab=="enjoyImage" || tab=="userImage")>class="current"</#if> ><a href="${systemProp.appServerUrl}/user-center!enjoyImage.action"><strong>${(session_user.statsMap.enjoyImageNum)!"0"}</strong><span>图片</span></a></li>
        <li <#if tab??&&tab=="enjoyIssue">class="current"</#if>><a href="${systemProp.appServerUrl}/user-center!enjoyIssue.action"><strong>${(session_user.statsMap.enjoyIssueNum)!"0"}</strong><span>杂志</span></a></li>
        <li <#if tab??&&tab=="friend">class="current"</#if>><a href="${systemProp.appServerUrl}/user-center!friend.action"><strong>${(session_user.statsMap.friendNum)!"0"}</strong><span>好友</span></a></li>
      </ul>
      <#if (!session_user??)||(session_user??&&userId??&&userId!=session_user.id)>
      <div class="tool clear">
        <div class="inner"> <a class="message" href="javascript:void(0)">发消息</a> <a class="atten" href="javascript:void(0)">加好友</a> </div>
      </div>
      </#if>
    </div>
    
    <#if tagList??>
	<div class="tagList">
    	<ul class="clearFix">
        	<li class="title"><a href="javascript:void(0)">标签分类</a></li>
	    	<#list tagList as tag>
	    		<li class="<#if tagName??&&(tagName==tag.name)>current</#if>"><a href="${systemProp.appServerUrl}/user-center!${tab}.action?tagName=${encode(tag.name)}">${tag.name}</a></li>
	    	</#list>        	
        </ul>
    </div>
    </#if>      
  </div>
    
	${body} 
</div>

<!--footer-->
<@footer.main class="footerMini footerStatic"/>
<div id="loading" class="pageLoad"><span>正在加载内容...</span></div>
</body>
</html>