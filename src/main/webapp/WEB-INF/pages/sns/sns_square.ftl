<!DOCTYPE HTML>
<!--[if lt IE 7 ]> <html class="ie6 ieOld"> <![endif]-->
<!--[if IE 7 ]>    <html class="ie7 ieOld"> <![endif]-->
<!--[if IE 8 ]>    <html class="ie8 ieOld"> <![endif]-->
<!--[if (gte IE 9)|!(IE)]><!--> <html> <!--<![endif]-->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>麦米网Magme电子杂志 - 无处不悦读</title>
<meta name="Keywords" content="杂志,电子杂志,杂志在线阅读,电子杂志下载,杂志网"/>
<meta name="Description" content="麦米网(www.magme.com)全球领先的电子杂志在线阅读平台.提供财经,旅游,娱乐,时尚,汽车,体育,数码生活等15类超过400本杂志的在线免费阅读与下载. 在线看杂志优质内容,上麦米网!"/>
<link href="${systemProp.staticServerUrl}/v3/style/reset.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/pop.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/global.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/channelHomev4.css" rel="stylesheet" type="text/css"/>
<link href="${systemProp.staticServerUrl}/v3/style/conBigHeader.css" rel="stylesheet" type="text/css"/>
<script src="${systemProp.staticServerUrl}/js/systemProp.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery-new.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.masonry.min.2.7.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.easing.1.3.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.onImagesLoad.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.lazyload.mini.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.sampleFancyBox.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.lightbox.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.tagbox.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.inputfocus.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.coverImg.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.tmpl.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/scrolltopcontrol.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/shareToInternet.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/header.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/useFunction.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/global.js"></script>
<script src="${systemProp.staticServerUrl}/v3/dv/js/m1square.js"></script>
<!--[if lt IE 7]>
<script src="${systemProp.staticServerUrl}/v3/js/DD_belatedPNG_0.0.8a-min.js"></script>

<![endif]-->

</head>
<body class="subNavOff">
<#import "../components/header.ftl" as header>
<@header.main searchType="Creative"/>
<div class="body bodyHome clearFix" id="homeWall">
	<#if m1List?? && m1List?size gt 0 >
		<#list m1List as m>
				<div class="item <#if m.imagePath?? && m.imagePath?length gt 0 ><#else>itemNopic</#if>">
				<a href="http://www.magme.com/sns/c${m.id}/" target="_blank">
			    	<div class="photo"><#if m.imagePath?? && m.imagePath?length gt 0 ><img height="${(((m.high!1) * 210) / m.width!1)}"  src="http://static.magme.com${avatarResize(m.imagePath!'','max_400')}" alt="${m.title!''}"></#if></div>
			    	<div class="info png clearFix">
		                <div class="user" class="png" title="${m.nickName!''}">
		                    <img src="<#if m.avatar?? && m.avatar?length gt 0 >http://static.magme.com/profiles${avatarResize(m.avatar,'60')}<#else>${systemProp.staticServerUrl}/v3/images/head60.gif</#if>" onerror="this.src='${systemProp.staticServerUrl}/images/head60.gif'"  />
		                	<strong>${m.nickName!''}</strong>
		                </div>
		                <h2>${m.title!''}</h2>
			            <p>${m.described!''}</p>
			        </div>
		        </a>
		        <div class="tools png">
		       	 <em title="喜欢" class="iconHeart png" favTypeId="cre_${m.id}"></em>
       			 <em class="iconShare png" shareTypeId="eve_${m.id}_creative"></em></div>
		    	</div>
		</#list>
	</#if>
    
    <div class="sideRight">
    	 <#if session_user?exists>
    	 	<div class="con conHead">
	        	<a class="head" href="#">
	            	<img src="http://static.magme.com/profiles/${session_user.avatar!''}" />
	            	<strong>${session_user.userName!''}</strong>
	            	<#if session_user.reserve1?? && session_user.reserve1=='M'>
	            		<em class="png m">M</em>
	            	</#if>
	            </a>
	        </div>
	    	<div class="con conInfo">
	        	<a class="a1" href="http://www.magme.com/sns/user-index!attention.action"><span>${attentionNum!0}</span>关注</a>
	        	<a class="a2" href="http://www.magme.com/sns/user-index!fans.action"><span>${fansNum!0}</span>粉丝</a>
	        	<a class="a4" href="http://www.magme.com/sns/article-pub.action">发布</a>
	        </div>
	     <#else>
	     <div class="con conRegister" id="squareLoginDiv">
            <a href="javascript:void(0)" class="btnWB" id="squareLoginA">登录</a>
            <a href="javascript:void(0)" class="btnGB" id="squareLoginReg">注册</a>
        </div>
    	 </#if>
    	<div class="con conUser" id="changeUserListDiv">
    		<#if userInfoList?? && userInfoList?size gt 0 >
    			<#list userInfoList as u>
    				<a href="http://www.magme.com/sns/u${u.id}/">
		                <img src="<#if u.avatar?? && u.avatar?length gt 0 >http://static.magme.com/profiles/${avatarResize(u.avatar,'60')}<#else>${systemProp.staticServerUrl}/v3/images/head60.gif</#if>" />
		                <strong>${u.nickname!''}</strong>
		                <p><span>${u.creativeCount!'0'}</span>作品</p>
		                
		            </a>
    			</#list>
    		</#if>
            <a href="javascript:void(0)" id="changeUserList" begin="0" size="5" class="more">换一批&nbsp;作者</a>
        </div>
    </div>

</div>

<span id="loadMore" style="cursor:pointer;" class="clickLoadMore"><span>狂点这里&nbsp;&nbsp;查看更多</span></span>

<#import "../components/footer.ftl" as footer>
<@footer.main class="footerMini footerStatic" />
</body>
</html>
