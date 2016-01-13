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
<script src="${systemProp.staticServerUrl}/v3/js/sns/articlePub.js"></script>
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
var previewContent=$("#mainContent").html();
$(function(){
    if(!previewContent || previewContent==''){
        previewContent=$("#mainContent").html();
        //alert(previewContent);
    }
    <#if editArticle?? && editArticle.template?? && (editArticle.wordCountTemp==article.wordCountTemp) && (editArticle.imgCountTemp==article.imgCountTemp)>
      fnGenerate("${(article.imgCount)!'0'}","${(article.wordCountTemp)!'a'}","${(editArticle.template)!'1'}",<#if article?? && article.bigWordCount??>"${article.bigWordCount}"<#else>undefined</#if>);
    <#else>
      fnGenerate("${(article.imgCount)!'0'}","${(article.wordCountTemp)!'a'}",null,<#if article?? && article.bigWordCount??>"${article.bigWordCount}"<#else>undefined</#if>);
    </#if>
});
</script>
</head>
<body class="pageSharePreview">
<!--header-->
<#import "../components/header.ftl" as header>
<@header.main searchType="Creative"/>
<input type="hidden" id="template_pattern" value="${(creative.tempPattern)!'1a'}"/>
<input type="hidden" id="articleImgCount" value="${(article.imgCount)!'0'}"/>
<input type="hidden" id="articleWordCountTemp" value="${(article.wordCountTemp)!'a'}"/>
<input type="hidden" id="articleCreativeId" value="${(creativeId)!''}"/>
<input type="hidden" id="articlebigWordCount" value="${(article.bigWordCount)!'0'}"/>
<!--body-->
<div class="topReader20121108">
    <div class="inner clearFix">
		<div id="mainContent" class="mainContent">
			<h6>${(seriaCreative.title)!''}</h6>
			<div class="content" id="mainText">
			    <h1>${(article.title)!}</h1>
			    <div class="source">
			       <a class="head" <#if (seriaCreative.magazineName)?? && (seriaCreative.magazineUrl)?? && seriaCreative.magazineName!="" && seriaCreative.magazineUrl!="">href="${seriaCreative.magazineUrl!''}"<#else>href="${systemProp.appServerUrl}/sns/u${seriaCreative.userId!''}/"</#if>>
				         <img src="<#if (session_user.avatar60)??>${systemProp.profileServerUrl}${(session_user.avatar60)!}<#else>${systemProp.staticServerUrl}/v3/images/head46.gif</#if>" />
				   </a>
				   <#if (seriaCreative.magazineName)?? && (seriaCreative.magazineUrl)?? && seriaCreative.magazineName!="" && seriaCreative.magazineUrl!="">
				         <strong><a class="name" href="${seriaCreative.magazineUrl!''}">摘自《${seriaCreative.magazineName!''}》</a></strong>
			       <#elseif (seriaCreative.origin)?? && (seriaCreative.originUrl)??>
			       		 <strong><a class="name" href="${seriaCreative.originUrl!''}">来源：${seriaCreative.origin!''}</a></strong>
			       <#else>
			             <strong><a class="name" href="javascript:void(0)"><#if (session_user.nickName)??>${(session_user.nickName)!}<#else>${(session_user..userName)!''}</#if></a></strong>
			       </#if>
			       <span>预览发布中</span>
			    </div>
			    <div class="text">${(article.content)!}</div>
			</div>
			<#if articleExList?? && (articleExList?size>0)>
			  <#assign aa=1>
			  <#list articleExList as cEx>
			    <#if (aa<=10) && (!(cEx.imgType??) || cEx.imgType!=2)>
			      <#if (article.wordCountTemp)?? && article.wordCountTemp=='a'>
			         <a href="javascript:void(0)" class="pic <#if (cEx.conType)?? && cEx.conType=3>video<#elseif (cEx.conType)?? && cEx.conType=4>music</#if> p${aa}">
						  <div> <img src="${systemProp.staticServerUrl}${(cEx.imgPath)!}" width="${(cEx.w)!}" height="${(cEx.h)!}" /></div>
						  <span>${(cEx.content)!}</span>
					 </a>
				  <#elseif (article.wordCountTemp)?? && (article.wordCountTemp=='b' || article.wordCountTemp=='c')>
				     <#if (aa<=6)>
				       <a href="javascript:void(0)" class="pic <#if (cEx.conType)?? && cEx.conType=3>video<#elseif (cEx.conType)?? && cEx.conType=4>music</#if> p${aa}">
						  <div> <img src="${systemProp.staticServerUrl}${(cEx.imgPath)!}" width="${(cEx.w)!}" height="${(cEx.h)!}" /></div>
						  <span>${(cEx.content)!}</span>
						</a>
				     </#if>
			      </#if>
			      <#assign aa=aa+1>
				</#if>
			  </#list>
			</#if>
		</div>
    </div>
</div>

<div class="body clearFix tCenter">
    <#if userLevel?? && userLevel==1>
      <a class="btnWB" id="backEdit" href="javascript:void(0)">返回修改</a>
    <#else>
      <a class="btnGB" id="reTypeSetting" href="javascript:void(0)">重新排版</a>
    </#if>
    
    <#if userLevel?? && (userLevel==1 || userLevel==2)>
    	<a class="btnWB" id="selectTemplate" href="javascript:void(0)">选择模板</a>
    </#if>
    <a class="btnAB" id="confirmPublish" href="javascript:void(0)">确认发布</a>
    <#if userLevel?? && userLevel==1 >
       <#if creativeFlag?? && creativeFlag==1>
    	<a class="btnBB" id="pubSerializeArticle" href="javascript:void(0)">发布系列页</a>
       <#elseif seriaCreative?? && (seriaCreative.id)?? && (seriaCreative.id>0)>
        <a class="btnBB" id="pubSerializeArticle" href="javascript:void(0)">发布系列页</a>
       </#if>
    </#if>
</div>


<#import "../dialog/articlePreviewSelectTemplate.ftl" as selectTemplate>
<#import "../components/footer.ftl" as footer>
<@selectTemplate.main/>
<@footer.main class="footerMini footerStatic" />
</body>
</html>
