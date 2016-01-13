<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>彩蛋活动</title>
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
	<#if activity>
		<#if notice>
			<div class="appItemBox_last">
			    <div class="appGroup">
					<h3 class="appGroup_hd">活动预告</h3>
				</div>
				<div id="show_activity" class="appItem_white">
					<div class="appItem_white_txt i2">
						<p>${activityName!}</p>
						<p>要求${mustEgg!}个彩蛋</p>
					</div>
					<span class="icon_arrow"></span>
				</div> 	
			</div>
		<#else>
			<div class="appItemBox_last">
			    <div class="appGroup">
					<h3 class="appGroup_hd">进行中的活动</h3>
				</div>
				<div id="show_activity" class="appItem_white">
					<div class="appItem_white_txt i2">
						<p>${activityName!}</p>
						<p>要求${mustEgg!}个彩蛋</p>
					</div>
					<span class="icon_arrow"></span>
				</div> 	
			</div>
			<div class="appItemBox_last">
			    <div class="appGroup">
					<h3 class="appGroup_hd">我的彩蛋</h3>
				</div>
				<div id="show_egg_pic" class="appItem_white">
					<#if os.equals("IOS")>
		 				<a href="javascript:void(0);" >
		 			</#if>
					<div class="appItem_white_txt i2">
						<p>${eggNum!}个彩蛋哦</p>
						<p></p>
					</div>
					<span class="icon_arrow"></span>
					<#if os.equals("IOS")>
						</a>
			 		</#if>
				</div> 	
			</div>
			<div class="appItemBox_noBorder">
				<#if ticket??>
				   	<div class="appItem_gray">
				    	<p class="appItem_gray_txt">您的抽奖券号码是${ticket!}，我们会在本次活动结束后的3个工作日内完成抽奖，届时请留意您的短信通知，或是我们的消息中心。
				    	</p>
				    </div>
			    </#if>
		    	<div class="appItem_gray">
					<p class="appItem_gray_txt">友情提醒：</p>
			    	<p class="appItem_gray_txt">彩蛋是随机分布在杂志和文章内容里，有的彩蛋直接显示在内容页上，有的彩蛋显示在互动控件里。彩蛋是一组发放，当用户收集到所有彩蛋时，得到一张有编号的彩券，根据这张彩券参加抽奖，所以彩蛋是活动期间不定期发放，在发放彩蛋前，会有通告和通知，告诉读者玩法。
			    	<br/><br />（最终解释权归LOO客所有）
			    	</p>
			    </div>
			</div>    
		</#if>
	<#else>
		<div class="appItemBox_last">
		    <div class="appGroup">
				<h3 class="appGroup_hd">活动预告</h3>
			</div>
			<div class="appItem_white">
				<div class="appItem_white_txt i2">
					<p>暂无活动，尽请期待。</p>
					<p></p>
				</div>
				<span class="icon_arrow"></span>
			</div> 	
		</div>
	</#if>
</div>
</body>
</html>
