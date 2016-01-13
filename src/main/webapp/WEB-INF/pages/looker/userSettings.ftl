<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>账户设置</title>
<meta name="description" content="">
<meta name="keywords" content="">
<link href="${systemProp.staticServerUrl}/v3/style/looker/reset.css" rel="stylesheet" />
<link href="${systemProp.staticServerUrl}/v3/style/looker/<#if os?? && os.equals('IOS')>base.css<#else>base_and.css</#if>" rel="stylesheet" />
<script src="${systemProp.staticServerUrl}/v3/js/looker/jquery-1.7.2.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/looker/app.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/looker/app_ex.js"></script>
</head>
<body>
<input id="os" type="hidden" value='${os!}' >
<input id="uid" type="hidden" value='${userId!}' >
<input id="appId" type="hidden" value='${appId!}' >
<input id="muid" type="hidden" value='${muid!}' >
<input id="version" type="hidden" value='${v!}' >
<div class="appBody">
	<div class="appItemBox_last">
		<div class="appItem_white" name='magme_goto' val="/app/looker-web!getUser.action" tt="完善资料">
			<div class="appItem_white_txt i2">
				<p>资料完整度 ${rate!}%</p>
				<p>继续完善</p>
			</div>
			<span class="icon_arrow"></span>
		</div>
		<div class="appItem_white" name='magme_goto' val="/app/looker-web!getInvitationCode.action" tt="邀请好友">
    		<div class="appItem_white_txt"><p>邀请码</p></div>
    		<span class="icon_arrow"></span>
	    </div>
		<div class="appItem_white" name='magme_goto' val="/app/looker-web!getCheckDay.action" tt="每日签到">
    		<div class="appItem_white_txt"><p>每日签到</p></div>
    		<span class="icon_arrow"></span>
	    </div>
	    <div class="appItem_white"  name='magme_goto' val="/app/looker-web!giftList.action" tt="金币兑换">
    		<div class="appItem_white_txt"><p>金币兑换</p></div>
    		<span class="icon_arrow"></span>
	    </div> 	
	    <div class="appItem_white" name='magme_goto' val="/app/looker-web!eggActivity.action" tt="彩蛋">
    		<div class="appItem_white_txt"><p>彩蛋</p></div>
    		<span class="icon_arrow"></span>
	    </div> 	 		
	</div>

</div>
</body>
</html>
