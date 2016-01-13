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
<link href="${systemProp.staticServerUrl}/v3/style/datepicker.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/imgAreaSelect/imgareaselect-default.css" rel="stylesheet" type="text/css" />
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
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/datepicker.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/form2object.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/ajaxfileupload.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/jquery.imgareaselect.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.lightbox.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.sampleFancyBox.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.tmpl.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.tagbox.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/scrolltopcontrol.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/shareToInternet.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/useFunction.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/global.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/userConfig.js"></script>

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
<div class="body">
	<div class="conLeft">
        <div class="userInfo clearFix">
            <strong class="name">${(session_user.nickName)!""}</strong>
            <a href="javascript:void(0)" class="img"><img id="userAvatar" src="<#if ((session_user.avatar)??)&&(session_user.avatar!="")>${systemProp.profileServerUrl+session_user.avatar}<#else>${systemProp.staticServerUrl}/images/head172.gif</#if>" /><em id="changeAvatar">更换头像</em></a>
            <ul class="atten">
                <li><a href="${systemProp.appServerUrl}/user-center!enjoyImage.action"><strong>${(session_user.statsMap.enjoyImageNum)!"0"}</strong><span>图片</span></a></li>
                <li><a href="${systemProp.appServerUrl}/user-center!enjoyIssue.action"><strong>${(session_user.statsMap.enjoyIssueNum)!"0"}</strong><span>杂志</span></a></li>
                <li><a href="${systemProp.appServerUrl}/user-center!friend.action"><strong>${(session_user.statsMap.friendNum)!"0"}</strong><span>好友</span></a></li>
            </ul>
        </div>
        <div class="tool clear">
            <a href="${systemProp.appServerUrl}/user-center!userImage.action">创建的图片</a>
            <a href="${systemProp.appServerUrl}/user-center!message.action">我的消息</a>
            <a href="${systemProp.appServerUrl}/user-center!config.action">账户设置</a>
        </div>
    </div>
    
    
    ${body}
</div>

<!--footer-->
<@footer.main class="footerMini footerStatic"/>
<div class="popContent hide" id="uploadUserAvatarDialog">
    <fieldset>
        <h6>更换头像</h6>
        <form id="editAvatarForm" method="post" action="" onsubmit="return false;">
        	<input type="hidden" name="x" value="0" />
			<input type="hidden" name="y" value="0" />
			<input type="hidden" name="width" value="0" />
			<input type="hidden" name="height" value="0" />
			<input type="hidden" id="avatarFileName" name="avatarFileName"/>
        </form>
        <div class="imgArea">
        	<#if ((session_user.avatar)??)&&(session_user.avatar!="")>
				<a id="avatarBox" href="javascript:void(0)">
					<span><img id="avatar" name="avatar" src="${systemProp.profileServerUrl+session_user.avatar}" /></span>
				</a>
			<#else>
				<a id="avatarBox" href="javascript:void(0)" class="hide">
					<span><img id="avatar" name="avatar" src="${systemProp.staticServerUrl}/images/head150.gif" /></span>
				</a>
			</#if>
        </div>
        <div class="tCenter">
            <a id="submit" href="javascript:void(0)" class="btnBS" >确定</a>
            <a id="cancel" href="javascript:void(0)" class="btnWS" >取消</a>
            <#if ((session_user.avatar)??)&&(session_user.avatar!="")>
        		<a id="uploadBtn" class="btnOS" href="javascript:void(0)">
            		<span>重新上传头像</span>
            		<input id="avatarFile" name="avatarFile" type="file" class="inputFile" />
            	</a>
        	<#else>
        		<a id="uploadBtn" class="btnOB" href="javascript:void(0)">
            		<span>上传头像</span>
            		<input id="avatarFile" name="avatarFile" type="file" class="inputFile" />
            	</a>
        	</#if>
            <span class="tipsError" id="tipsError">请选择头像</span>
        </div>
    </fieldset>
</div>
</body>
</html>