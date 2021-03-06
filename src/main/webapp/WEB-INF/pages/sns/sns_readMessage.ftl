﻿<!DOCTYPE html>
<!--[if lt IE 7 ]> <html class="ie6"> <![endif]-->
<!--[if IE 7 ]>    <html class="ie7"> <![endif]-->
<!--[if IE 8 ]>    <html class="ie8"> <![endif]-->
<!--[if (gte IE 9)|!(IE)]><!--> <html> <!--<![endif]-->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Magme</title>
<link href="${systemProp.staticServerUrl}/v3/style/reset.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/global.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/channelM1V4.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/style/datepicker.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/pop.css" rel="stylesheet" type="text/css" />


<script src="${systemProp.staticServerUrl}/js/systemProp.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery-1.6.2.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.masonry.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.easing.1.3.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.scrollto.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.mousewheel.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.onImagesLoad.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.inputfocus.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/datepicker.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/form2object.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/ajaxfileupload.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.imgareaselect.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/scrolltopcontrol.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.tagbox.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.coverImg.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.floatDiv.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.m1_1.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.sampleFancyBox.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.lightbox.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/shareToInternet.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/useFunction.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/global.js"></script>

<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/sns/sns_message.js"></script>
<script>
$(function(){
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
<body style="display:none;">
<!--header-->

<#import "../components/header.ftl" as header>
<@header.main searchType="User"/>
<!--body-->
<div class="body pageMessage clearFix">
  
	<div class="sideLeft">
		<!--conMyMessageDetail-->
        <div class="conMyMessageDetail">
            <div class="topBar clearFix">
                <div class="floatr searchInput">
                    <input type="text" name="searchMessageKeyWord" tips="search" />
                    <a href="javascript:void(0)" name="searchMessage" class="search" >搜索</a>
                </div>
                <div class="floatl">
                <a href="${systemProp.appServerUrl}/sns/message-user!writeMessage.action" class="btnWB" >新建</a>
                <a href="${systemProp.appServerUrl}/sns/message-user.action" class="back btnWB" >返回收件箱</a>
                </div>
            </div>
            
            
            <div class="con conDetail">
                <div class="conReply conReplyBig">
                
				<#list messageList as message>
                    <div class="<#if (message.fromUserId)?? && (message.fromUserId == session_user.id)>gl<#else>br</#if>"  messageId="${message.id}" >
                    	<a href="${systemProp.appServerUrl}/sns/u${message.fromUserId}/" class="head">
                    		<#if ((message.fromUserAvatar)??) && (message.fromUserAvatar != "")>
				        		<img src="${systemProp.profileServerUrl + message.fromUserAvatar60}" title="${message.fromUserNickName}"/>
			        		<#else>
			        			<img src="${systemProp.staticServerUrl}/v3/images/head60.gif" title="${message.fromUserNickName}" />
			        		</#if>  
                    	</a>
                    	<p><span><strong><a href="${systemProp.appServerUrl}/sns/u${message.fromUserId}/">${message.fromUserNickName}</a>${(message.createdTime?string("yyyy-MM-dd"))!""}</strong>${(message.contentInfo.content)!""}</span></p>
                    	<em></em>
                    </div>
                </#list>
                </div>
                <div class="reply clearFix">
	                <form id="wirteMessageForm" method="post" action="">
		                <input type="hidden" name="toType" value="${fromType}" />
		                <input type="hidden" name="toUserIds" value="${fromUserId}" />		
	                    <textarea class="input" name="content" tips="回复内容"></textarea>
		                <a id="wirteMessageSubmit" href="javascript:void(0)" name="wirteMessageSubmit" class="btnGB floatr" >回复</a>
	                </form>
                </div>
            </div>  
        </div> 
    </div>

	<div class="sideRight">
    	<div class="con conHead">
        	<a class="head" href="javascript:void(0)" title='${session_user.nickName!}'>
            	<img id="userAvatar" src="<#if ((session_user.avatar)??)&&(session_user.avatar!="")>${systemProp.profileServerUrl+session_user.avatar}<#else>${systemProp.staticServerUrl}/images/head172.gif</#if>" />
            	<strong>${stringSub(session_user.nickName,24)}</strong>
            	<#if userEx?? && userEx.audit==1><em class="png m"></em></#if>
            </a>
        </div>
    	<div class="con conInfo">
        	<a class="a1" href="${systemProp.appServerUrl}/sns/user-index!attention.action"><span>${attention}</span>关注</a>
        	<a class="a2" href="${systemProp.appServerUrl}/sns/user-index!fans.action"><span>${fans}</span>粉丝</a>
        	<a class="a3" href="${systemProp.appServerUrl}/sns/user-index!home.action"><span>${creative}</span>内容</a>
        </div>
	</div>        
</div>


	<div id="applyCertification" class="popContent">
		<fieldset class="new">
	        	<form id="applyForm" method="post">
	    	<h6>麦米认证申请</h6>
	        <div>
	        	<em class="g90 tRight">真实姓名</em>
	            <em><input type="text" class="input g300" name="nameZh"/></em>
	        </div>
	        <div>
	            <em class="g90 tRight">联系电话</em>
	            <em><input type="text" class="input g300" name="phone" /></em>
	        </div>
	        <div>
	        	<em class="g90 tRight">所属出版商</em>
	            <em><input type="text" class="input g300" name="publisher" /></em>
	        </div>
	        <div>
	        	<em class="g90 tRight">目前职位</em>
	            <em><input type="text" class="input g300" name="office" /></em>
	        </div>
	        <div>
	        	<em class="g90 tRight">工作相关证明</em>
	            <em><input type="file" id="imgFile" name="imgFile" class="g320"  /></em>
	        </div>
	        <div><em class="g90">&nbsp;</em><a href="javascript:void(0)" class="btnGB" id="doApply" >申请</a></div>
	        <div><em class="tips">填写说明 ：<br>
			1.每项均为必填，且必须填写真实信息，否则可能会影响您的审核通过几率。<br>
			2.工作相关证明可以上传工作牌、名片，等可以证明工作状态的图片</em></div>
			</form>
	    </fieldset>
	</div>

<#import "../components/footer.ftl" as footer>
<@footer.main class="footerMini footerStatic" />
</body>
</html>