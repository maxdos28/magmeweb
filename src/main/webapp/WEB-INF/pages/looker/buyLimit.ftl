<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>限时抢购</title>
<meta name="description" content="">
<meta name="keywords" content="">
<link href="${systemProp.staticServerUrl}/v3/style/looker/reset.css" rel="stylesheet" />
<link href="${systemProp.staticServerUrl}/v3/style/looker/<#if os?? && os.equals('IOS')>base.css<#else>base_and.css</#if>" rel="stylesheet" />
<script src="${systemProp.staticServerUrl}/v3/js/looker/jquery-1.7.2.min.js"></script>
<script src="${systemProp.staticServerUrl}/js/systemProp.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/looker/app.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/looker/app_ex.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/looker/jquery.countdown.js"></script>
<script>
$(function(){
	<#if buyLimitList?? && (buyLimitList?size) gt 0>
			<#list buyLimitList as g>
			var d = new Date(Date.parse("${g.endDate?string('yyyy-MM-dd HH:mm:ss')}".replace(/-/g,   "/")));
	$.fn.countdown({
		id:"#timer${g.id}",
		Tday:d //倒计时时间点-注意格式
	});
	</#list>
	</#if>

});
</script>
</head>
<body>
<input id="os" type="hidden" value='${os!}' >
<input id="uid" type="hidden" value='${userId!}' >
<input id="appId" type="hidden" value='${appId!}' >
<input id="muid" type="hidden" value='${muid!}' >
<input id="version" type="hidden" value='${v!}' >
<div class="appBody">
	<div class="appHd">
		<h1 class="appHd_hd">限时抢购</h1>
	</div>
	<div class="appItemBox_last">
    	<div class="appItem_white">
    		<div class="appItem_white_txt inew2">
    			<p id="goldp">您有${gold!}个<span class="iconMoney">金币</span></p>
    			<p><a href="/app/looker-web!getGiftsList.action?os=${os!}&uid=${userId!}&appId=${appId!}&muid=${muid!}&version=${v!}&userId=${userId!}">历史记录</a></p>
    		</div>
    		<span class="icon_arrow"></span>
	    </div>
	</div>
<div class="appItemBox_last appItem_changeTime">
			<#if buyLimitList?? && (buyLimitList?size) gt 0>
			<#list buyLimitList as g>
					<div class="appItem_white appItem_whiteOther">

				    	<div class="appItem_white_txt i2">
				    		<div class="imgBlock">
				    			<img src="${systemProp.staticServerUrl}${g.picPath!}">
				    			<#if g.qty gt 0>
				    			<div class="times">
				    				<div class="jqueryCount" id="timer${g.id}">
										<div class="day">
									        <span class="num hide"></span>
									        <span class="num"></span>
									        <span class="num"></span>
									        <span class="day">天</span>
									    </div>
										<div class="hour">
									        <span class="num hide"></span>
									        <span class="num"></span>
									        <span class="num"></span>
									        <span class="text">:</span>
									    </div>
										<div class="min">
									        <span class="num hide"></span>
									        <span class="num"></span>
									        <span class="num"></span>
									        <span class="text">:</span>
									    </div>
										<div class="sec">
									        <span class="num hide"></span>
									        <span class="num"></span>
									        <span class="num"></span>
									    </div>
									</div>
								</div>
			    				<p>${g.giftName!}&nbsp;<span>[数量:${g.showQty!}]</span></p>
				 				<p class="changePoints">${g.goldNum!} <img src="${systemProp.staticServerUrl}/v3/images/looker/money_c.png">
								<#else>
				    				<p class="nogoods"></p>
					 				<div class="times nogoodsText">
										已结束
									</div>
			    				<p class="nogoodsText">${g.giftName!}&nbsp;<span>[数量:${g.showQty!}]</span></p>
				 				<p class="changePoints nogoodsText">${g.goldNum!} <img src="${systemProp.staticServerUrl}/v3/images/looker/money_b.png">
					 			</#if>
				 				<#if g.status = 1>
				 					<#if g.qty gt 0>
						 				<#if gold gte g.goldNum>
						 					<a class="appChange" name="buyLimitBtn" giftId="${g.id!}" href="#">兑换</a>
						 				<#else>
						 					<a class="appChange appNoChane" href="javascript:void(0);">金币不足</a>
						 				</#if>
					 				<#else>
					 					<a class="appChange appNoChane" href="javascript:void(0);">已抢完</a>
					 				</#if>
				 				<#else>
				 				<a class="appChange appNoChane" name="buyLimitBtn" giftId="${g.id!}" href="javascript:void(0);">未开始</a>
				 				</#if>
				 					
				 				</p>
				    		</div>
				  
				    	</div>    	
				    </div>
				</#list>
				</#if>
				    <div class="appItem_whiteEvery appItem_change appItem_changeT">
				    	<p class="appItem_gray_txt">抢购规则：</p>
				    	<p class="appItem_gray_txt">文字为虚构，金币多为圆形盘状，价值昂贵，现代属于名贵收藏品。在古代为一种货币，进行买卖。人类历史上相当长的一段时间内是很多国家的官方货币。在现代一般为私有的收藏品，是一种纪念价值相当高的纪念物。</p>
				    </div>
			    </div>
</div>
</body>
</html>
