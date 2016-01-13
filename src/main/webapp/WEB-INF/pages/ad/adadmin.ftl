<#macro main>
<!--conLeft-->
<div class="conLeft">
	<div class="itemUser">
	<div class="head">
	<#assign adUser=session_aduser/>
    	   <#if adUser.level==2 && session_adagency.logo??>
    	      <img src="${systemProp.adProfileServerUrl}${avatarResize(session_adagency.logo,'60')}" />
    	    <#elseif adUser.level==1 && session_publisher.logo??>
    	      <img src="${systemProp.publishProfileServerUrl}${avatarResize(session_publisher.logo,'60')}" />
    	    <#else>
    	      <img src="${systemProp.staticServerUrl}/images/head60.gif" />
    	   </#if>
    	    
	</div>
	<h3>${adUser.name}</h3>
	<div class="control clearFix">
	  <#--广告商才能编辑头像-->
	  <#if adUser.level?? && adUser.level==2>
	    <a id="editAdAgencyInfo" class="iconEdit" href="javascript:void(0)">编辑账号</a>
	    <a id="changeAdAgencyLogo" class="iconChange" href="javascript:void(0)">更换头像</a>
	  </#if>
	</div>
	<ul class="clear listB clearFix">
	    <#if adUser.level?? && adUser.level!=2>
	      <li><a id="posManage" href="${systemProp.appServerUrl}/ad/manage-position.action">广告位管理</a></li>
	    </#if>
	    
	    <li><a id="createAd" href="${systemProp.appServerUrl}/ad/manage-create.action">创建广告</a></li>
	    <li><a id="myAd" href="${systemProp.appServerUrl}/ad/manage-my-ad.action">我创建的广告</a></li>
	    <li><a id="adData"  href="${systemProp.appServerUrl}/ad/manage-data.action">广告数据</a></li>
	    
	    <#if adUser.level?? && adUser.level==1>
	      <li><a id="mymgzAd" href="${systemProp.appServerUrl}/ad/manage-my-mgz-ad.action">我期刊的广告</a></li>
	    </#if>
	    
	    <#if adUser.level?? && adUser.level==3>
	      <li><a id="magmeAd" href="${systemProp.appServerUrl}/ad/manage-magme-ad.action">麦米广告</a></li>
	      <li><a id="sideAd" href="${systemProp.appServerUrl}/ad/manage-ad-side!getList.action">侧栏广告</a></li>
	    </#if>
	    
	    <#--广告商才有自己的消息-->
	    <#if adUser.level?? && adUser.level==2>
	        <li><a id="message" href="${systemProp.appServerUrl}/ad/ad-agency!msgList.action">消息<span>(${session_adagency.messageCount?default('')})</span></a></li>
	    </#if>
	</ul>
    </div>
</div>
</#macro>