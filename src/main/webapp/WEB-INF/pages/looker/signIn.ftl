<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>每日签到</title>
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
	<div class="con01 clearFix">
    	<h2>
    		<i>累计</i>
    		<p id="cur_gold_html">${gold!}<span>金币</span></p>
    	</h2>
    	<h2><i>连续签到</i>
		<p id="cur_day_html">${day!}<span>天</span></p></h2>
    </div>
	<div class="appItemBox_noBorder">
	   	<div class="appItem_gray appEveryDay">
			<#if goldManagers?? && (goldManagers?size) gt 0>
				<#list goldManagers as g>
					<div class="<#if g_index+1 lte day>appItem_everyHas<#elseif sign == 0 && g_index == day>appItem_everyNow<#else>appItem_every</#if>">
						<div class="appItem_white_txt i2">
						<p>
						<#if g_index==0>
							第一天 
						<#elseif g_index==1>
							第二天 
						<#elseif g_index==2>
							第三天 
						<#elseif g_index==3>
							第四天 
						<#elseif g_index==4>
							第五天 
						<#elseif g_index==5>
							第六天 
						<#elseif g_index==6>
							第七天 
						</#if>
						<#if g_index+1 lte day && sign == 1>
						<img src="${systemProp.staticServerUrl}/v3/images/looker/money_b.png"> +${g.propValue!}</p>
							<p><lable class="appBtnHas">已领取</lable></p>
						<#elseif g_index+1 lte day && sign == 0 && day lt 7>
						<img src="${systemProp.staticServerUrl}/v3/images/looker/money_b.png"> +${g.propValue!}</p>
							<p><lable class="appBtnHas">已领取</lable></p>
						<#elseif sign == 0 && g_index == day>
						<img src="${systemProp.staticServerUrl}/v3/images/looker/money_c.png"> +${g.propValue!}</p>
							<p id="addGoldByCheckDay_parent">
							<#if os.equals("IOS")>
								<a id="addGoldByCheckDay" href="javascript:void(0);"  day='${day!}' class="appBtnNo">点击领取</a>
							<#else>
								<lable id="addGoldByCheckDay" day='${day!}' class="appBtnNo">领取</lable>
							</#if>
							</p>
						<#elseif sign == 0 && g_index == 0 && day ==7>
						<img src="${systemProp.staticServerUrl}/v3/images/looker/money_c.png"> +${g.propValue!}</p>
							<p id="addGoldByCheckDay_parent">
							<#if os.equals("IOS")>
								<a id="addGoldByCheckDay" href="javascript:void(0);"  day='${day!}' class="appBtnNo">点击领取</a>
							<#else>
								<lable id="addGoldByCheckDay" day='${day!}' class="appBtnNo">领取</lable>
							</#if>
							</p>
						<#else>
						<img src="${systemProp.staticServerUrl}/v3/images/looker/money_b.png"> +${g.propValue!}</p>
							<p><lable  class="appBtnOther">未领取</lable></p>
						</#if>
						</div>
					</div>
				</#list>
			</#if>
			</div>
			<div class="appItem_whiteEvery appItem_change">
	    	<p class="appItem_gray_txt">友情提醒：</p>
	    	<p class="appItem_gray_txt">在网络连接的情况下登录LOO客APP，点击“签到”按钮，向后台发送请求，记录签到天数，就算成功签到一次，可获得一个金币。<br />
连续签到，金币能累积获得。金币发放数以7天为一个周期：如果连续7天登录签到，每天送金币（奖励方式见上）；7天以后，以新的周期计算。7天里，如果中间某天没有登录，周期自动停止，再次登录时，从新的周期开始计算。
<br /><br />（最终解释权归LOO客所有）
</p>
	    </div>
	</div>      
</div>
</body>
</html>
