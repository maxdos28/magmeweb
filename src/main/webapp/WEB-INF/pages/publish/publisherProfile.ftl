<#macro main>
	<div class="conB conUserInfo">
		<h2><#if session_publisher??>${session_publisher.publishName}<#else>麦米哥</#if></h2>
		<div class="conBody">
	    	<div class="userHead">
				<#if (session_publisher??)&&(session_publisher.logo??)>
					<img id="publisherLogo" src="${systemProp.profileServerUrl}${session_publisher.logo}" title="头像" />
				<#else>
					<img id="publisherLogo" src="${systemProp.staticServerUrl}/images/userHeadMan.gif" title="头像" />
				</#if>            	
	        </div>
	        <div class="control clearFix">
	            <a id="editPublisherInfo" class="iconEdit" href="javascript:void(0)">编辑账号</a>
	            <a id="changePublisherLogo" class="iconChange" href="javascript:void(0)">更换头像</a>
	        </div>
	        <div  class="userInfo">
	           <#if (session_publisher??)>
	            	<p class="iconWork" title="公司名">${session_publisher.publishName}</p>
	            	<p class="iconMessage" title="注册邮箱">${session_publisher.email}</p>
	            	<p class="iconTime" title="上次登录时间"><#if session_publisher.lastLoginTime??>${session_publisher.lastLoginTime?string("yyyy-MM-dd HH:mm:ss")}</#if></p>
	           </#if>
	        </div>
	    </div>
	</div>
</#macro>