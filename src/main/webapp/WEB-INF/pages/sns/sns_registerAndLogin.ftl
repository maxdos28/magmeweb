<!DOCTYPE html>
<!--[if lt IE 7 ]> <html class="ie6"> <![endif]-->
<!--[if IE 7 ]>    <html class="ie7"> <![endif]-->
<!--[if IE 8 ]>    <html class="ie8"> <![endif]-->
<!--[if (gte IE 9)|!(IE)]><!--> <html> <!--<![endif]-->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Magme</title>
<link href="${systemProp.staticServerUrl}/v3/style/reset.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/register.css" rel="stylesheet" type="text/css" />


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
<script src="${systemProp.staticServerUrl}/v3/js/jquery.lightbox.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/shareToInternet.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/useFunction.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/global.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.sampleFancyBox.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.jscrollpane.min.js"></script>


<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/sns/sns_registerAndLogin.js"></script>
<!--[if lt IE 7]>
<script src="${systemProp.staticServerUrl}/v3/js/DD_belatedPNG_0.0.8a-min.js"></script>
<script>
  DD_belatedPNG.fix('.png');
  document.execCommand("BackgroundImageCache",false,true);
</script>
<![endif]-->

</head>
<body>

    <div class="quickLinks">
    	<a href="http://www.magme.com" target="_self">首页</a><a href="javascript:void(0);" class="a2">登录</a>
    </div>
    
    <div class="regCon">
        <h1 class="logo"><a href="http://www.magme.com" target="_blank" title="麦米网Magme">麦米网Magme</a></h1>
        <fieldset class="reg">
            <legend>注册新用户</legend>
            <div><input type="text" tips="用户名" id="userName"/></div>
            <div><input type="text" tips="邮箱" id="email"/></div>
            <div><input type="text" tips="密码" /><input type="password" tips="密码" style="display:none;" id="password"/></div>
            <div><input type="text" tips="确认密码" /><input type="password" tips="确认密码" style="display:none;" id="password2" /></div>
            <!--<div><p>需输入邀请码才能发布M1频道作品</p><input type="text" tips="邀请码" id="inviteCode" /></div>-->
            <div><p class="error" id="errorMsg" style="display:none">用户名长度不能小于4位且不能包含@字符</p></div>
            <div><a href="javascript:void(0)" class="btn" id="submitRigister">注册</a></div>
        </fieldset>
    </div>
    
    
    <div class="loginCon">
        <h1 class="logo"><a href="http://www.magme.com" target="_blank" title="麦米网Magme">麦米网Magme</a></h1>
        <fieldset class="login">
            <legend>用户登录</legend>
            <div><input id='login_userName' type="text" tips="用户名 / 邮箱" /></div>
            <div><input  type="text" tips="密码" /><input id='login_password' type="password" tips="密码" style="display:none;" /></div>
           <!-- <div id='input_invite_code' style='display:none'><p>需输入邀请码才能发布M1频道作品</p><input id='login_inviteCode' type="text" tips="邀请码" /></div>-->
            <div><p class="error" style="display:none"></p></div> 
            <div><a href="javascript:void(0)" id='sns_login' class="btn">登录</a></div>
        </fieldset>
    </div>
    
    
    <div class="regConStep2">
        <h1 class="logo"><a href="http://www.magme.com/" target="_blank" title="麦米网Magme">麦米网Magme</a></h1>
        <p>我们为您推荐了一些用户，请点击关注</p>
        <div class="hotUser">
        	<div class="scroll-pane clearFix">
        		<#if adminUserEx??>
	                <a href="javascript:void(0);" class="item itemCurrent" userid="${adminUserEx.userId}">
	                    <div class="img">
	                    	<#if adminUserEx.imagePath?? && adminUserEx.imagePath!='' >
	                    		<img src="${systemProp.profileServerUrl}${adminUserEx.imagePath}" />
	                    	<#else>
	                    		<img src="${systemProp.staticServerUrl}/images/head60.gif"/>
	                    	</#if>
	                    </div>
	                    <strong>${adminUserEx.nameZh}</strong>
	                </a>
                </#if>
                <#if userExList??>
		            <#list userExList as userEx>
		            	<#if adminUserEx?? && adminUserEx.userId == userEx.userId>
		            	<#else>
			                <a href="javascript:void(0);" class="item" userid="${userEx.userId}">
			                    <div class="img">
			                    	<#if userEx.imagePath?? && userEx.imagePath!='' >
			                    		<img src="${systemProp.profileServerUrl}${userEx.imagePath}" />
			                    	<#else>
			                    		<img src="${systemProp.staticServerUrl}/images/head60.gif"/>
			                    	</#if>
			                    </div>
			                    <strong>${userEx.nameZh}</strong>
			                    <span>${userEx.office}</span>
			                </a>
		                </#if>
	                </#list>
                </#if>
            </div>
        </div>
        <fieldset class="regStep2">
            <div><a href="javascript:void(0)" class="btn" id="enterM1">进入M1</a></div>
        </fieldset>
    </div>
    
    
    
    
    <div class="footer">
    	<div class="inner">
            <strong>您也可以使用下列方式登录</strong>
            <ul class="otherLogin clearFix">
                <li class="m0"><a name="login_weibo" href="javascript:void(0);" title="新浪微博"></a></li>
                <li class="m1"><a name="login_qq" href="javascript:void(0);" title="QQ"></a></li>
                <li class="m2"><a name="login_baidu" href="javascript:void(0);" title="百度"></a></li>
                <li class="m3"><a name="login_renren" href="javascript:void(0);" title="人人网"></a></li>
                <li class="m4"><a name="login_kaixin" href="javascript:void(0);" title="开心网"></a></li>
            </ul>
        </div>
    </div>
<#import "../components/gap.ftl" as gap>
<@gap.main />
</body>
</html>