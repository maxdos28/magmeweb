<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>邀请好友</title>
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
	<div class="appItemBox_last">
		<div class="appGroup">
			<h3 class="appGroup_hd">您的邀请码</h3>
		</div>
		<div class="appItem_white">
		    	<div class="appItem_white_txt i2">
		    		<p class="linkColor">${userInviteCode!}</p>
		    		<#if os.equals("IOS")>
			 			<p class="linkColor friendbtn"><a class="appBtnRight" id='inviteFriends' val='${userInviteCode!}'  href="javascript:void(0);" >邀请好友</a></p>
			 		<#else>
			 			<p id='inviteFriends' val='${userInviteCode!}' class="linkColor appBtnRight">邀请好友</p>
			 		</#if>
		    		
		    </a>	
	    </div>
    </div>
	<div class="appItemBox_last">
		<div class="appGroup">
			<h3 class="appGroup_hd">填写好友的邀请码</h3>
		</div>
	    <div id="invitecode_div" class="appItem_input">
	    	<#if invitecode??>
	    		<label>${invitecode!}</label>
	    	<#else>
	    		<div class="dlabel" style='margin-left: 15px;'><input id="inviteCode" type="text" class="input regexp"  placeholder="输入邀请码" /></div>
	    		<#if os.equals("IOS")>
		 			<a id="appBtnRight" href="javascript:void(0);" class="appBtnRight">确认</a>
		 		<#else>
		 			<p id="appBtnRight" class="appBtnRight">确认</p>
		 		</#if>
	    	</#if>
	    </div>
    </div>
    <div class="appItemBox_noBorder">
    	<div class="appItem_gray">
			<p class="appItem_gray_txt">友情提醒：</p>
	    	<p class="appItem_gray_txt">
	    	成功邀请好友成为LOO客一员，就能获得金币的奖励。每位LOO客成员都有自己的邀请码（相当于身份识别）。分享邀请链接后，好友按此邀请链接下载APP并安装注册成功，然后在APP相应位置中填写好友的邀请码，即分享成功，计为“成功邀请一次”。累计邀请5位好友，即可获得100个金币。
	    	<br /><br />
操作步骤：<br />
1.用户点击“邀请好友”，在微博、微信、qq空间上转发邀请信息（含有邀请码和URL下载地址）；<br /><br />
2.好友接受邀请，通过用户提供的URL下载LOO客APP；<br /><br />
3.好友安装好APP；<br /><br />
4.好友注册成功后，登录进入“设置”，点击“邀请码”，在“填写好友的邀请码”里填入邀请方的邀请码，即邀请成功。<br /><br />
（最终解释权归LOO客所有）
	    	</p>
	    </div>

	</div>    
</div>
</body>
</html>
