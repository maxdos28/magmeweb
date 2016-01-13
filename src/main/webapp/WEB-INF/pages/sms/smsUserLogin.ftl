<!DOCTYPE HTML>
<!--[if lt IE 7 ]> <html class="ie6"> <![endif]-->
<!--[if IE 7 ]>    <html class="ie7"> <![endif]-->
<!--[if IE 8 ]>    <html class="ie8"> <![endif]-->
<!--[if (gte IE 9)|!(IE)]><!--> <html> <!--<![endif]-->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Magme</title>
<script src="${systemProp.staticServerUrl}/js/systemProp.js"></script>
<link href="${systemProp.staticServerUrl}/v3/sms/style/global.css" rel="stylesheet" type="text/css" />
<script src="${systemProp.staticServerUrl}/v3/sms/js/jquery-new.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/sms/js/jquery.inputfocus.js"></script>
<script src="${systemProp.staticServerUrl}/v3/sms/js/admin.js"></script>
<script src="${systemProp.staticServerUrl}/v3/sms/devjs/useFunction.js"></script>
<script src="${systemProp.staticServerUrl}/v3/sms/devjs/smsUserLogin.js"></script>
<style>
html, body{ overflow:hidden;}
</style>

</head>
<body class="pageAdminLogin">
	<div class="conLogin" id="conLogin">
	  <fieldset class="new">
	        <div class="tCenter">
	            <input type="text" id="userName" class="input g330 intop" tips="用户名 / Email" color="#666,#fff" />
	            <input id="displayPassword" type="text" class="input g330 inbottom" tips="密码" color="#666,#fff" />
	            <input type="password" id="password" class="input g330 hide inbottom" color="#666,#fff" />
	        </div>
	        <div class="tCenter">
	            <a href="javascript:void(0)" id="loginEnter" class="btnOB g290">登录</a>
	        </div>
	        <#--
	        <div>
	            <em class="floatr"><a href="javascript:void(0)" class="aRed">忘记密码？</a></em>
	            <em class="floatl"><label><input id="rememberPassword" type="checkbox" />记住密码</label></em>
	        </div>-->
	    </fieldset>
	</div>
	<div class="copyright">Copyright © 2012-2015 <strong>(www.kaijie.com)</strong> 沪ICP证：沪xx-xxxxxxxx </div>
</body>
</html>