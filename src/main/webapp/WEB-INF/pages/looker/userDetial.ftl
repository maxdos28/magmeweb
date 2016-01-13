<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>完善资料</title>
<meta name="description" content="">
<meta name="keywords" content="">
<link href="${systemProp.staticServerUrl}/v3/style/looker/reset.css" rel="stylesheet" />
<link href="${systemProp.staticServerUrl}/v3/style/looker/<#if os?? && os.equals('IOS')>base.css<#else>base_and.css</#if>" rel="stylesheet" />
<script src="${systemProp.staticServerUrl}/v3/js/looker/jquery-1.7.2.min.js"></script>
<script src="${systemProp.staticServerUrl}/js/systemProp.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/looker/app.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/looker/app_ex.js"></script>
</head>
<body>
<input id="os" type="hidden" value='${os!}' >
<input id="uid" type="hidden" value='${userId!}' >
<input id="appId" type="hidden" value='${appId!}' >
<input id="muid" type="hidden" value='${muid!}' >
<input id="version" type="hidden" value='${v!}' >
<input id="userInviteCode" type="hidden" value='${userInviteCode!}' >
<div class="appBody">
	<div class="appEmpty_gray"></div>
	<div class="appItemBox_last">
		<div class="appItem_input">
	    	<label>年龄：<input id="age" type="number" onkeyup="value=value.replace(/[^\d]/g,'')" onblur="value=value.replace(/[^\d]/g,'')"  value="<#if looUser??>${looUser.age!}</#if>" class="input" placeholder="请输入年龄" /></label>
	    </div>
	    <div class="appItem_input">
	    	<label>性别：
	    	<select id="sex" name="sex">
		  		<option label="男" <#if looUser??><#if looUser.sex == 1>selected="selected"</#if></#if> value="男">
		   		<option label="女" <#if looUser??><#if looUser.sex == 0>selected="selected"</#if></#if> value="女" >
  	        </select>
	    	</label>
	    </div>
	    <div class="appItem_input">
	    	<label>手机号码：<input id="mobile" onkeyup="value=value.replace(/[^\d]/g,'')" onblur="value=value.replace(/[^\d]/g,'')"  type="number" class="input" value="<#if looUser??>${looUser.mobile!}</#if>"  placeholder="请输入手机号码" /></label>
	    </div>
	    <div class="appItem_input">
	    	<label>邀请码：
	    	<#if invitecode??>
	    		${invitecode!}
	    	<#else>
	    		<input id="invitecode" type="text" class="input"  placeholder="请输入好友邀请码" />
	    	</#if>
	    	</label>
	    </div>
    	<div class="appEmpty_gray"></div>
		<div class="appItem_input">
	    	<p id='user_detial_btn' class="button"  >提交</p>
	    </div>
	</div>

	<div class="appItemBox_noBorder">
	   	<div class="appItem_gray">
			<p class="appItem_gray_txt">友情提醒：</p>
	    	<p class="appItem_gray_txt">用户信息是作为奖品发放（如金币兑换话费成功）的最终参考依据。只有用户完整填完信息，才能在获得奖品的时候成功收到礼品。如果用户信息不健全，LOO客后台无法正确发放礼品。<br />
完善一下信息吧，金币在等你！<br /><br />
（最终解释权归LOO客所有）
	    	</p>
	    </div>
	</div>
</div>
</body>
</html>
