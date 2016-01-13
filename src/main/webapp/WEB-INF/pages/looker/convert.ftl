<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>金币兑换</title>
<meta name="description" content="">
<meta name="keywords" content="">
<link href="${systemProp.staticServerUrl}/v3/style/looker/reset.css" rel="stylesheet" />
<link href="${systemProp.staticServerUrl}/v3/style/looker/<#if os?? && os.equals('IOS')>base.css<#else>base_and.css</#if>" rel="stylesheet" />
<script src="${systemProp.staticServerUrl}/v3/js/looker/jquery-1.7.2.min.js"></script>
<script src="${systemProp.staticServerUrl}/js/systemProp.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/looker/app.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/looker/app_ex.js"></script>
<script language="javascript">
	
	
</script>
</head>
<body>
<input id="os" type="hidden" value='${os!}' >
<input id="uid" type="hidden" value='${userId!}' >
<input id="appId" type="hidden" value='${appId!}' >
<input id="muid" type="hidden" value='${muid!}' >
<input id="version" type="hidden" value='${v!}' >
<input id="gold" type="hidden" value='${gold!}' >
<input id="userId" type="hidden" value='${userId!}' >
<input id="covert_notice" type="hidden" value='<#if notice>true<#else>false</#if>' >
<input id="giftId" type="hidden" value='' >
<div class="appBody">
	<div class="appHd">
		<h1 class="appHd_hd">金币兑换</h1>
	</div>
	<div class="appItemBox_last">
		<div class="appItem_white">
    		<div class="appItem_white_txt inew2">
    			<p id="goldp">您有${gold!}个<span class="iconMoney">金币</span></p>
    			
    			<p><a href="/app/looker-web!getGiftsList.action?os=${os!}&uid=${userId!}&appId=${appId!}&muid=${muid!}&version=${v!}&userId=${userId!}">兑换记录</a></p>
    		</div>
    		<span class="icon_arrow"></span>
	    </div>
    </div>
    <div class="appItemBox_noBorder">
    	<div class="appItem_gray appItem_gray_noBorder padTop35">
			<ul class="appItem_gray_list">
				<#if gifts?? && (gifts?size) gt 0>
					 <#list gifts as g>
					 	<li val='${g.id}' num='${g.goldNum!}' gn='${g.giftName!}'>
					 		<#if os.equals("IOS")>
					 			<a href="javascript:void(0);" val='${g.id}' num='${g.goldNum!}' gn='${g.giftName!}'>
					 		</#if>
							<img src="${systemProp.staticServerUrl}${g.picPath!}">
							<#if g.qty lte 0>
                        		<p class="nogoods"></p>
							</#if>
							<p>${g.giftName!}</p>
							<p class="changePoints">${g.goldNum!} <img src="${systemProp.staticServerUrl}/v3/images/looker/money_c.png"></p>
							<#if os.equals("IOS")>
								</a>
					 		</#if>
					 		<#if g.qty lte 0>
					 			<a href="#" class="appNogoods">已抢完</a>
					 		<#elseif gold lt g.goldNum>
					 		<a href="javascript:void(0);" class="appNogoods">金币不足</a>
					 		<#else>
					 		<a href="#" class="appChange">兑换</a>	
					 		</#if>
						</li>
					 </#list>
				</#if>
			</ul>
	    </div>
	    <div class="appItem_whiteEvery appItem_change">
	    	<p class="appItem_gray_txt">友情提醒：</p>
	    	<p class="appItem_gray_txt">金币体系是我们趣味阅读的一个亮点，金币是非常值钱的，可以兑换许多实物礼品，如电话充值卡等。而且一点都不难拿，你完全可以闲闲地躺着赚金币。<br /><br />
			如何获得金币？<br /><br />
			金币的获得可以有很多方式：<br /><br />
			1.初次登录（QQ登录或是微博登录）即可获得10个金币；<br /><br />
			2.连续签到，可以获得金币1-5个（详情参考“关于签到”）；<br /><br />
			3.邀请好友，可以获得金币1-100个（详情参考“关于邀请”）；<br /><br />
			4.在线阅读能赚金币。当用户看内容时，每翻10页，获得1个金币（必须是在线状态），一天最多能获得20个金币；<br /><br />
			5.分享内容可以赚金币。当用户成功把一个内容分享到“微博”、“微信”、“qq空间”，即可赚取2个金币，一天最多能获得10个金币。<br /><br />
			（最终解释权归LOO客所有）
</p>
	    </div>
	</div>    
</div>
</body>
</html>
