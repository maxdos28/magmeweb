<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>兑换历史</title>
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
<div class="appBody">
	 <#if goldGifts?? && (goldGifts?size) gt 0>
	<div class="appItemBox_last">
	 	<#list goldGifts as g>
	 		<div class="appItem_white appItem_whiteOther"">
		    	<div class="appItem_white_txt i2">
		    		<div class="imgBlock">
		    			<img src="${systemProp.staticServerUrl}${g.picPath!}">
	    				<p class="date">${g.createTime?string('yyyy.MM.dd HH:mm')}</p>
		 				<p>${g.giftName!}<#if g.isDistribution??>(<#if g.isDistribution==0>处理中<#elseif g.isDistribution==1>拒绝<#else>已发放</#if>)</#if></p>
		    		</div>
		    		<div class="priceBlock">
		    			<p>${g.goldNum!}金币</p>
		    		</div>
		    	</div>
			</div>
	 	</#list>
	 </div>
	<#else>
		<div class="appGroup">
        	<h3 class="appGroup_hd">无记录</h3>
    	</div>
	</#if>
</div>
</body>
</html>
