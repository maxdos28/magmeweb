<body>
<div class="conMiddleRight">
	<div class="conB conMyMessageDetail">
		<h2>我的消息 - <strong>来自${fromUserName?default('')}</strong></h2>
	    <div class="topBar clearFix">
	        <div class="floatl">
	        <#if session_aduser?? && session_aduser.level==1>
		        <a href="${systemProp.appServerUrl}/publish/publish-message.action" class="btnWS" >收件箱</a>
		        <a href="${systemProp.appServerUrl}/publish/publish-message!writeMessage.action" class="btnOS" >新建</a>
	        <#elseif session_aduser.level==2>
		        <a href="${systemProp.appServerUrl}/ad/ad-agency!msgList.action" class="btnWS" >收件箱</a>
		        <a href="${systemProp.appServerUrl}/ad/ad-agency!writeMessage.action" class="btnOS" >新建</a>
	        </#if>
	        </div>
	    </div>
		<#list messageList as message>
			<div messageId="${message.id}" adUserId="${session_aduser.id}" toUserId="${message.toUserId}" toType="${message.toType}" fromUserId="${message.fromUserId}" fromType="${message.fromType}" class="item <#if (message.fromUserId)??&&(message.fromUserId==session_aduser.id)>itemOwn</#if> clearFix">
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
			    <div class="right">
			    	<strong>${(message.fromUserNickName)?default('')}</strong>
			        <span>发送于：${message.createdTime?string("yyyy-MM-dd HH:mm:ss")}</span>
			        <p>${message.contentInfo.content}</p>
			    </div>
			    <del></del>
			    <a name="delOneMessage" class='del'></a>
			</div>			
		</#list>                
		<div class="item reply clearFix">
		    <form id="wirteMessageForm" method="post" action="">
		        <input type="hidden" name="toType" value="${fromType}" />
		        <input type="hidden" name="toUserIds" value="${fromUserId}" />	
		        <input type="hidden" name="fromUserId" value="${session_aduser.id}" />
		        <input type="hidden" name="fromType" value="<#if session_aduser.level==1>2<#elseif session_aduser.level==2>4</#if>" />
		    	<textarea class="input" name="content" tips="回复内容："></textarea>
		        <a href="javascript:void(0)" name="wirteMessageSubmit" class="btnBS floatr" >回复</a>
		    </form>
		</div>     
    </div>
</div>   
</body>   
