<!DOCTYPE HTML>
<!--[if lt IE 7 ]> <html class="ieOld ie6"> <![endif]-->
<!--[if IE 7 ]>    <html class="ieOld ie7"> <![endif]-->
<!--[if IE 8 ]>    <html class="ieOld ie8"> <![endif]-->
<!--[if (gte IE 9)|!(IE)]><!--> <html> <!--<![endif]-->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${userInfo.nickname!}<#if fansFlag??>的粉丝<#else>关注的人</#if> - 麦米网Magme</title>
<link href="${systemProp.staticServerUrl}/v3/style/reset.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/global.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/pop.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/channelM1V4.css" rel="stylesheet" type="text/css" />


<script src="${systemProp.staticServerUrl}/js/systemProp.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery-1.6.2.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.masonry.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.easing.1.3.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.scrollto.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.mousewheel.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.onImagesLoad.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.inputfocus.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/scrolltopcontrol.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.tagbox.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.coverImg.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.floatDiv.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.sampleFancyBox.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.lightbox.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/shareToInternet.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/useFunction.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/global.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.m1V4.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/sns/m1.js"></script>


<script>
$(function(){
	$.magmeShow(".sideLeftMiddle .conUserList");
	
	
	
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
<!--header-->
<#import "../components/header.ftl" as header>
<@header.main searchType="User"/>

<!--body-->
<div class="body pageM1 clearFix">
	<div class="sideLeftMiddle">
        <!--conUserList-->
        <div class="conUserList">
        	<h2 class="bigTitle"><span>${userInfo.nickname!}</span>&nbsp;<#if fansFlag??>的粉丝<#else>关注的人</#if></h2>
           	<#if userList?? && userList.size()&gt;0>
        		<#list userList as u>
        			<div class="item clearFix">
    					<div class="userHead" >
		    				<a class="head" u='${u.userId}' href="<#if ((session_user)??) && (session_user.id==u.userId) >${systemProp.appServerUrl}/sns/${(session_user.id)!'0'}/ <#else> ${systemProp.appServerUrl}/sns/u${u.userId}/</#if>" title="查看主页">
		    					<img src='<#if u.avatar?? && u.avatar!="" >${systemProp.profileServerUrl}${avatarResize(u.avatar,'60')}<#else>${systemProp.staticServerUrl}/images/head60.gif</#if>' onerror="this.src='${systemProp.staticServerUrl}/images/head60.gif'" />
		    				</a>
	    				</div>
		                <div class="uName"><strong>${u.nickname}<span>${u.province!"未知地区"}</span><span>${u.city!""}</span></strong>内容<span>${u.num}</span></div>
	                 	<#if !session_user?? || session_user.id!=u.userId>
			                <#if u.isF?? && u.isF&gt;0>
			                	<#if u.userId!=9999>
			                	<a class="btnWS sns_btnWS" nick='${u.nickname}' u='${u.userId}' href="javascript:void(0)">取消关注</a>
			                	</#if>
			               	<#else>
			               		<a class="btnGS sns_btnGS" nick='${u.nickname}' u='${u.userId}' href="javascript:void(0)">添加关注</a>
			               	</#if>
	               		</#if>
       				</div>
    			</#list>
    		</#if>
    	</div>
        
        <div class="tRight">
        	<#if fansFlag??>
        		<#if begin&gt;20><a class="btnWS " begin='${begin}' info='pre' href="<#if ((session_user)??) && (session_user.id==userInfo.id) >${systemProp.appServerUrl}/sns/user-index!fans.action?begin=${begin-40}<#else> ${systemProp.appServerUrl}/sns/user-index!fans.action?u=${userInfo.id}&begin=${begin-40}</#if>">上一页</a></#if>
        		<#if begin &lt; userInfo.fans><a class="btnBS btnPage" begin='${begin}' info='next' href="<#if ((session_user)??) && (session_user.id==userInfo.id) >${systemProp.appServerUrl}/sns/user-index!fans.action?begin=${begin}<#else> ${systemProp.appServerUrl}/sns/user-index!fans.action?u=${userInfo.id}&begin=${begin}</#if>">下一页</a></#if>
        	<#else>
        		<#if begin&gt;20><a class="btnWS " begin='${begin}' info='pre' href="<#if ((session_user)??) && (session_user.id==userInfo.id) >${systemProp.appServerUrl}/sns/user-index!attention.action?begin=${begin-40}<#else> ${systemProp.appServerUrl}/sns/user-index!attention.action?u=${userInfo.id}&begin=${begin-40}</#if>">上一页</a></#if>
        		<#if begin &lt; userInfo.attention><a class="btnBS btnPage" begin='${begin}' info='next' href="<#if ((session_user)??) && (session_user.id==userInfo.id) >${systemProp.appServerUrl}/sns/user-index!attention.action?begin=${begin}<#else> ${systemProp.appServerUrl}/sns/user-index!attention.action?u=${userInfo.id}&begin=${begin}</#if>">下一页</a></#if>
        	</#if>
        	
        </div>
        
        
    </div>

	<div class="sideRight">
		<div class="con conHead">
        	<a class="head" href="javascript:void(0);" title='${userInfo.nickname!}'>
        	 	<img url="<#if !session_user?? || userInfo.id!=session_user.id>${systemProp.appServerUrl}/sns/u${userInfo.id}/<#else>${systemProp.appServerUrl}/sns/${(session_user.id)!'0'}/</#if>" src="${systemProp.profileServerUrl}${userInfo.avatar!}" onerror="this.src='${systemProp.staticServerUrl}/images/head172.gif'" />
            	<strong>${stringSub(userInfo.nickname,24)}</strong>
                <!--<em class="png p"></em>-->
                <#if (userInfo.ism??) && (userInfo.ism=="M") >
                	<em class="png m"></em>
                </#if>
            </a>
        	<div class="btn">
                <#if !session_user?? || userInfo.id!=session_user.id>
                	<#if !userInfo.isF??>
                		<a u='${userInfo.id}' nick='${userInfo.nickname}' class="iconAdd" href="javascript:void(0)">加关注</a>
	                <#else>
	                	<#if userInfo.id!=9999>
	                		<a u='${userInfo.id}' nick='${userInfo.nickname}' class="iconCancel" href="javascript:void(0)">取消关注</a>
	                	</#if>
	                </#if>
	                <a class="iconMessage" href="javascript:void(0)">发消息</a>
                </#if>
            </div>
        </div>
    	<div class="con conInfo">
        	<a class="a1" href="<#if !session_user?? || userInfo.id!=session_user.id>${systemProp.appServerUrl}/sns/user-index!attention.action?u=${userInfo.id}<#else>${systemProp.appServerUrl}/sns/user-index!attention.action</#if>"><span>${userInfo.attention}</span>关注</a>
        	<a class="a2" href="<#if !session_user?? || userInfo.id!=session_user.id>${systemProp.appServerUrl}/sns/user-index!fans.action?u=${userInfo.id}<#else>${systemProp.appServerUrl}/sns/user-index!fans.action</#if>"><span>${userInfo.fans}</span>粉丝</a>
        	<a class="a3" href="${systemProp.appServerUrl}/sns/u${userInfo.id}/"><span>${userInfo.creativeCount}</span>内容</a>
        </div>
    	<#--<div class="con conFavorite">
            <a class="a1" href="javascript:void(0);">
                <strong>收藏的内容</strong>
                <span>(${enjoyCreative!})</span>
            </a>
            <a class="a2" href="javascript:void(0)">
                <strong>收藏的事件</strong>
                <span>(${enjoyEvent!})</span>
            </a>
            <a class="a3" href="javascript:void(0)">
                <strong>收藏的杂志</strong>
                <span>(${enjoyIssue!})</span>
            </a>
        </div>-->
    </div>       

</div>
<#import "../components/footer.ftl" as footer>
<@footer.main class="footerMini footerStatic" />
<div id="userNewMsg" class="popContent hide">
	<fieldset>
    	<h6>发送私信给 <span id="sns_mes_userName" name="userName"></span></h6>
        <div>
            <em><textarea id="msgContent" class="input g420"></textarea></em>
        </div>
        <div class="floatr">
            <em><a id="send" userId="" href="javascript:void(0)" class="btnBB" >发送</a></em>
            <em><a id="closePop" href="javascript:void(0)" class="btnWB">取消</a></em>
        </div>
    </fieldset>
</div>
</body>
</html>