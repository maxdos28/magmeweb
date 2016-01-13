<!DOCTYPE HTML>
<!--[if lt IE 7 ]> <html class="ieOld ie6"> <![endif]-->
<!--[if IE 7 ]>    <html class="ieOld ie7"> <![endif]-->
<!--[if IE 8 ]>    <html class="ieOld ie8"> <![endif]-->
<!--[if (gte IE 9)|!(IE)]><!--> <html> <!--<![endif]-->
<head>
<title>${(article.title)!''}- 麦米网Magme</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="${systemProp.staticServerUrl}/js/systemProp.js"></script>
<link href="${systemProp.staticServerUrl}/v3/style/pop.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/reset.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/global.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/channelHomev4.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/channelDetailv4.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/template/style/jquery.mCustomScrollbar.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/template/style/tpl.css" rel="stylesheet" type="text/css" />
<script src="${systemProp.staticServerUrl}/v3/js/jquery-new.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.easing.1.3.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.mousewheel.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.coverImg.js"></script>


<script src="${systemProp.staticServerUrl}/v3/js/jquery.sampleFancyBox.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.lightbox.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.tagbox.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/useFunction.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/global.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/header.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/sns/snsDetail.js"></script>

<script src="${systemProp.staticServerUrl}/v3/template/js/modernizr-2.5.3.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/template/js/jquery-ui-1.8.21.custom.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/template/js/jquery.mCustomScrollbar.js"></script>
<script src="${systemProp.staticServerUrl}/v3/template/js/jquery.scrollto.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/template/js/tpl.js"></script>

<#import "../detail/comment.ftl" as comment>
<script>
var browser={
    versions:function(){ 
       var u = navigator.userAgent, app = navigator.appVersion; 
       return {//移动终端浏览器版本信息 
            trident: u.indexOf('Trident') > -1, //IE内核
            presto: u.indexOf('Presto') > -1, //opera内核
            webKit: u.indexOf('AppleWebKit') > -1, //苹果、谷歌内核
            gecko: u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1, //火狐内核
            mobile: !!u.match(/AppleWebKit.*Mobile.*/)||!!u.match(/AppleWebKit/), //是否为移动终端
            ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端
            android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, //android终端或者uc浏览器
            iPhone: u.indexOf('iPhone') > -1, //是否为iPhone或者QQHD浏览器
            iPad: u.indexOf('iPad') > -1, //是否iPad
            webApp: u.indexOf('Safari') == -1 //是否web应该程序，没有头部与底部
        };
    }(),
    language:(navigator.browserLanguage || navigator.language).toLowerCase()
};

$(function(){

    if(browser.versions.mobile && $(window).width()<500){
        return false;
    }else{
	    fnGenerate("${(article.imgCount)!'0'}","${(article.wordCountTemp)!'a'}","${(article.template)!'1'}",<#if article?? && article.bigWordCount??>"${article.bigWordCount}"<#else>undefined</#if>);
	    $(".topReader20121108 .tools a.comment").click(function(){
			$(window).scrollTo("#conOtherRead",500)
		});
		var imgContent='${imgContent!""}';
		$.tplImgView(imgContent);
	}
	
});


</script>

<!--[if lt IE 7]>
<script src="${systemProp.staticServerUrl}/v3/js/DD_belatedPNG_0.0.8a-min.js"></script>
<script>
  DD_belatedPNG.fix('.png');
  document.execCommand("BackgroundImageCache",false,true);
</script>
<![endif]-->
</head>
<body>
<div id="body">
<#import "../components/header.ftl" as header>
<@header.main searchType="Creative"/>
<!--header-->
<div class="header20120905 clearFix">
  <div class="outer headerNav">
    	<div class="inner clearFix">
            <div class="logo" id="logo"><h1><a class="png" href="http://www.magme.com/" alt="麦米网电子杂志" title="无处不悦读">麦米网Magme</a></h1></div>
            <div class="subNav">
            	<ul class="classification clearFix">
            	<#if creativeCategoryList?? && creativeCategoryList?size gt 0 >
            		<#list creativeCategoryList as cc>
            			 <li <#if sortId?? && sortId == cc.id> class="current"</#if> ><a  href="http://www.magme.com/home/${cc.id}.html" >${cc.name}</a>
                    	</li>
            		</#list>
            	</#if>
                </ul>
            </div>
        </div>
    </div>
</div>
<input id="creativeId" type="hidden" value="${creativeId!'0'}">
<!--body-->
<div class="topReader20121108">
    <div id="mainContent" class="mainContent">
            <#if totalCount?? && (totalCount>1)>
			 <h6>${(creative.title)!''}</h6>
			</#if>
			<div class="content" id="mainText">
			    <#if (article.title)??>
			   	  <h1>${(article.title)!}</h1>
			    </#if>
			    <#if (article.sortOrder)?? && article.sortOrder==0>
				    <div class="source">
				       <a class="head" <#if (creative.magazineName)?? && (creative.magazineUrl)?? && creative.magazineName!="" && creative.magazineUrl!="">href="${creative.magazineUrl!''}"<#else>href="${systemProp.appServerUrl}/sns/u${creative.userId!''}/"</#if>>
				         <img src="<#if (user.avatar60)??>${systemProp.profileServerUrl}${(user.avatar60)!}<#else>${systemProp.staticServerUrl}/v3/images/head46.gif</#if>" />
				       </a>
				       <#if (creative.magazineName)?? && (creative.magazineUrl)?? && creative.magazineName!="" && creative.magazineUrl!="">
				         <strong><a class="name" href="${creative.magazineUrl!''}" target="_blank">摘自《${creative.magazineName!''}》</a></strong>
				       <#elseif (creative.origin)?? && (creative.originUrl)?? && creative.origin!='' && creative.originUrl!=''>
			       		 <strong><a class="name" href="${creative.originUrl!''}" target="_blank">来源：${creative.origin!''}</a></strong>
				       <#else>
				         <strong><a class="name" href="javascript:void(0)"><#if (user.nickName)??>${(user.nickName)!''}<#else>${(user.userName)!''}</#if></a></strong>
				       </#if>
				       <span><#if (creative.creativeType)?? && creative.creativeType==1 && (creative.startTime)??>${creative.startTime?string('yyyy-MM-dd HH:mm')}<#else>${article.updateTime?string('yyyy-MM-dd HH:mm')}</#if></span>
				    </div>
			    </#if>
			    <div class="text">${(article.content)!}</div>
			</div>
			<#if articleExList?? && (articleExList?size>0)>
			  <#assign aa=1>
			  <#list articleExList as cEx>
			    <#if (aa<=10)>
			      <#if article.wordCountTemp=='a'>
			         <a href="javascript:void(0)" class="pic <#if (cEx.conType)?? && cEx.conType=3>video<#elseif (cEx.conType)?? && cEx.conType=4>music</#if> p${aa}">
						  <div> <img src="${systemProp.staticServerUrl}${avatarResize(cEx.imgPath,'max_800')}" width="${(cEx.w)!}" height="${(cEx.h)!}" /></div>
						  <span>${(cEx.content)!}</span>
					 </a>
				  <#elseif (article.wordCountTemp=='b' || article.wordCountTemp=='c')>
				     <#if (aa<=6)>
				       <a href="javascript:void(0)" class="pic <#if (cEx.conType)?? && cEx.conType=3>video<#elseif (cEx.conType)?? && cEx.conType=4>music</#if> p${aa}">
						  <div> <img src="<#if (cEx.imgPath)??>${systemProp.staticServerUrl}${avatarResize(cEx.imgPath,'max_800')}</#if>" width="${(cEx.w)!}" height="${(cEx.h)!}" /></div>
						  <span>${(cEx.content)!}</span>
						</a>
				     </#if>
			      </#if>
			      <#assign aa=aa+1>
				</#if>
			  </#list>
			</#if>
	</div>
	<div class="ctrlPage">
	    <#if prevCreativeId?? || prevArticleId??>
        <a class="turnLeft png" title="上一页" href="<#if prevCreativeId?? && prevArticleId??>${systemProp.appServerUrl}/sns/c${prevCreativeId!'0'}/${prevArticleId!'0'}/<#elseif prevCreativeId??>${systemProp.appServerUrl}/sns/c${prevCreativeId!'0'}/<#else>javascript:void(0)</#if>">left</a>
        </#if>
        <#if (creative.creativeType)?? && creative.creativeType==1>
        <a class="turnRight png" title="下一页" href="<#if nextCreativeId?? && nextArticleId??>${systemProp.appServerUrl}/sns/c${nextCreativeId!'0'}/${nextArticleId!'0'}/<#elseif nextCreativeId??>${systemProp.appServerUrl}/sns/c${nextCreativeId!'0'}/<#else>${systemProp.appServerUrl}/sns/sns-detail!empty.action</#if>">right</a>
        </#if>
    </div>
    <div class="tools">
    	<p><span><#if (article.sortOrder)?? >${article.sortOrder+1}<#else>1</#if></span>/<span>${totalCount!'1'}</span></p>
    	<#if (creative.creativeType)?? && creative.creativeType==1>
    	<a class="turnRight png" href="<#if litterNextCreativeId??>${systemProp.appServerUrl}/sns/c${litterNextCreativeId!'0'}/<#else>${systemProp.appServerUrl}/sns/sns-detail!empty.action</#if>" title="下一篇作品">right</a>
    	</#if>
    	<#if litterPrevCreativeId??>
        <a class="turnLeft png " href="${systemProp.appServerUrl}/sns/c${litterPrevCreativeId!'0'}/" title="上一篇作品">left</a>
        </#if>
        <a class="comment png" href="javascript:void(0)">comment</a>
    </div>
    <#--
    <div class="ctrlPage">
        <a class="turnLeft png <#if !(prevCreativeId??) && !(prevArticleId??)>disable</#if>" title="上一页" href="<#if prevCreativeId?? && prevArticleId??>${systemProp.appServerUrl}/sns/c${prevCreativeId!'0'}/${prevArticleId!'0'}/<#elseif prevCreativeId??>${systemProp.appServerUrl}/sns/c${prevCreativeId!'0'}/<#else>javascript:void(0)</#if>">left</a>
        <a class="turnRight png <#if !(nextCreativeId??) && !(nextArticleId??)>disable</#if>" title="下一页" href="<#if nextCreativeId?? && nextArticleId??>${systemProp.appServerUrl}/sns/c${nextCreativeId!'0'}/${nextArticleId!'0'}/<#elseif nextCreativeId??>${systemProp.appServerUrl}/sns/c${nextCreativeId!'0'}/<#else>javascript:void(0)</#if>">right</a>
    </div>
    <div class="tools">
    	<p><span><#if (article.sortOrder)?? >${article.sortOrder+1}<#else>1</#if></span>/<span>${totalCount!'1'}</span></p>
    	<a class="turnRight png <#if !(litterNextCreativeId??)>hide</#if>" href="<#if litterNextCreativeId??>${systemProp.appServerUrl}/sns/c${litterNextCreativeId!'0'}/</#if>" title="下一篇作品">right</a>
        <a class="turnLeft png <#if !(litterPrevCreativeId??)>hide</#if>" href=" <#if litterPrevCreativeId??>${systemProp.appServerUrl}/sns/c${litterPrevCreativeId!'0'}/</#if>" title="上一篇作品">left</a>
        <a class="comment png" href="javascript:void(0)">comment</a>
    </div>-->
</div>

<div class="body clearFix">

   <#if sameCreativeList?? && (sameCreativeList.size()>0) >
     <div class="conOtherRead" id="conOtherRead">
    	<h2>推荐内容</h2>
        <div class="inner clearFix">
          <#if sameCreativeList?? && (sameCreativeList.size()>0)>
           <#list sameCreativeList as cc>
              <#if cc_index<6>
	            <div class="item">
	                <a href="${systemProp.appServerUrl}/sns/c${(cc.id)!'0'}/">
	                  <div><#if (cc.imagePath)??><img src="${systemProp.staticServerUrl}${avatarResize(cc.imagePath,'max_400')}" /></#if>
	                  </div>
	                  <p>${(cc.secondTitle)!''}</p>
	                </a>
	            </div>
	           </#if>
           </#list>
          </#if>
        </div>
	</div>
   </#if>


  <div class="sideLeft con">
		<div class="sendBox clearFix">
			<img src="<#if ((session_user.avatar)??)&&(session_user.avatar!="")>${systemProp.profileServerUrl}${avatarResize(session_user.avatar,'60')}<#else>${systemProp.staticServerUrl}/v3/images/head60.gif</#if>" class="head" id="userhead"/>
		    <div class="right">
			<div class="input"><textarea maxlength="196" tips="请输入文字" id="content" color="#9daf7b,#7c8865"></textarea><em></em></div>
			<div class="clearFix">
				<!--<a href="javascript:void(0);" class="shareWeiBo shareWeiBoChecked clearFix"><sup></sup>同时转发到新浪微博</a>-->
			    <a href="javascript:void(0);" class="btnGB" id="addComment">评论</a>
			    <strong class="txtNum">您还可以输入<span>196</span>字</strong>
			</div>
		    </div>
		</div>
		<div class="conReply conReplyBig" id="commentContainer"><@comment.main /></div>
		<a href="javascript:void(0);" class="conReplyMore" id="conReplyMore"></a>
  </div>
	
	
</div>



<#import "../components/footer.ftl" as footer>
<@footer.main class="footerMini footerStatic" />
</div>

<script type="text/javascript">
if(browser.versions.iPhone || browser.versions.android){
   
    $('head').prepend('<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" id="viewport" />');
    
	if($(window).width()<500){
        $('body').addClass('phoneStyle');
        $('div.mainContent').removeAttr('id');
        var ads = document.createElement('div');
        ads.setAttribute('id','onePic');
        $('#mainText')[0].insertBefore(ads,$('#mainText>h1')[0]);
        $('div.mainContent').find('a.pic:first').prependTo($('#onePic'));
    }else{
        $('#viewport').remove();
        $('body').addClass('padStyle');
    }
}else if(browser.versions.iPad){
    $('body').addClass('padStyle');
}

</script>
</body>
</html>