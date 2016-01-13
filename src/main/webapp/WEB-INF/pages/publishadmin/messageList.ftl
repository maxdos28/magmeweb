<#macro main>
<div class="msgList">
  <#if messageList?? && (messageList?size>0)>
	<#list messageList as message>
	    <div messageId="${message.id}" toUserId="${message.toUserId}" toType="${message.toType}"  class="item clearFix">
	    	<input type="checkbox" />
	        <a href="<#if session_aduser.level==1>
	        			${systemProp.appServerUrl}/publish/publish-message!msgDetail.action?fromUserId=${message.fromUserId}&fromType=${message.fromType}
	        		<#elseif session_aduser.level==2>
	        		    ${systemProp.appServerUrl}/ad/ad-agency!msgDetail.action?fromUserId=${message.fromUserId}&fromType=${message.fromType}
	        		</#if>">
	        	<div>
	        		<div class="userHead">
	        		<#if ((message.fromUserAvatar)??)&&(message.fromUserAvatar!="")>
	        			<#if message.fromType==2>
							 <img src="${systemProp.publishProfileServerUrl}${avatarResize(message.fromUserAvatar,'60')}" />
							<#elseif message.fromType==4> <#--广告商头像-->
							 <img src="${systemProp.adProfileServerUrl}${avatarResize(message.fromUserAvatar,'60')}" />
						 </#if>
	        		<#else>
	        			<img src="${systemProp.staticServerUrl}/images/head60.gif" />
	        		</#if>
	                </div>
	                <strong>${message.fromUserNickName}</strong>
	                <span>${(message.createdTime?string("yyyy-MM-dd"))!""}</span>
	            </div>
	            <#if (message.newMessageNum)??&&(message.newMessageNum>0)>
	            <sup class="new" title="${message.newMessageNum}条未读消息">(${message.newMessageNum})</sup>
	            <#else>
	            <sup title="已读"></sup>
	            </#if>
	            <p>${message.summary}</p>
	        </a>
	        <a class="del" name="delMessage"></a>
	    </div>
    </#list>
   </#if>
</div>
</#macro>