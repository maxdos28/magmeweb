<!--header-->
<#macro main menuId="startPic">
	<div class="header headerAdmin clearFix">
		<div class="outer png">
	        <div class="inner png">
	            <h1 class="logo" id="logo"><a class="png" href="后台-首页.html">(SEO)麦米 Magme</a></h1>
	            <!--nav-->
	            <ul class="menu" id="menu">
	                <li <#if menuId=='startPic'>class="current"</#if> ><a href="${systemProp.appServerUrl}/phoenix/start-pic!index.action">启动画面管理</a></li>
	                <li <#if menuId=='category'>class="current"</#if> ><a href="${systemProp.appServerUrl}/phoenix/phoenix-category.action">频道管理</a></li>
	                <li <#if menuId=='appdata'>class="current"</#if> ><a href="${systemProp.appServerUrl}/phoenix/phoenix-stat-category-pv-uv.action">数据报表</a></li>
	                <li <#if menuId=='ad'>class="current"</#if> ><a href="${systemProp.appServerUrl}/phoenix/phoenix-ad.action">广告管理</a></li>
	                <!--li <#if menuId=='account'>class="current"</#if> ><a href="${systemProp.appServerUrl}/phoenix/phoenix-order!account.action">收入核算</a></li>
	                <li <#if menuId=='specialAccount'>class="current"</#if> ><a href="${systemProp.appServerUrl}/phoenix/phoenix-user!specialAccount.action">内部账号</a></li>
	                <li <#if menuId=='revert'>class="current"</#if> ><a href="${systemProp.appServerUrl}/phoenix/phoenix-android-revert.action">后台审批</a></li-->
	                <li <#if menuId=='feedback'>class="current"</#if> ><a href="${systemProp.appServerUrl}/phoenix/feed-back.action">用户反馈</a></li>
	            </ul>
	            <!--loginBar-->
	            <ul class="conUser" id="userBar" style="display:block;">
	                <li class="logout" id="logout"><a href="${systemProp.appServerUrl}/phoenix/phoenix-user!logout.action" title="退出">退出</a></li>
	            </ul>
	        </div>
	    </div>
	</div>
</#macro>