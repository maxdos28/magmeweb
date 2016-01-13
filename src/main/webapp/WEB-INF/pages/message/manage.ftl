<html>
<head>
<title>Magme</title>
</head>
<body>
    <!--sideMiddleRight-->
    <div id="sideMiddleRight" class="sideMiddleRight">
    	<!--topTools-->
    	<div class="messageBar clearFix">
            <div class="floatr">
                <input type="text" name="searchMessageKeyWord" class="input" tips="search" />
                <a href="javascript:void(0)" name="searchMessage" class="btnBS" >搜索</a>
            </div>
            <div class="floatl">
            <a name="writeMessageMenu" href="javascript:void(0)" class="btnOS" >写消息</a>
            </div>
        </div>    
    	<!--conMessage-->
		<div class="conB conMessage">
        	<div class="conBody">
        		<#if (messageList?? && (messageList?size > 0))>
	                <div id="msgList" class="msgList">
	                	<#list messageList as message>
	                    <div messageId="${message.id}" class="item clearFix">
	                    	<input type="checkbox" />
	                        <a name="readMessageMenu" href="javascript:void(0)" url="${systemProp.appServerUrl}/user-message!readAjax.action?fromUserId=${message.fromUserId}&fromType=${message.fromType}">
	                        	<div>
	                        		<div class="userHead">
	                        		<#if ((message.fromUserAvatar)??)&&(message.fromUserAvatar!="")>
	                        			<img src="${systemProp.profileServerUrl+message.fromUserAvatar60}" />
	                        		<#else>
	                        			<img src="${systemProp.staticServerUrl}/images/head60.gif" />
	                        		</#if>
	                                <sub></sub></div>
	                                <strong>${message.fromUserNickName}</strong>
	                                <span>${message.createdTime?string("yyyy-MM-dd")}</span>
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
	                </div>
            	<#else>
                	<div class="nullInfo">您还没有消息记录 </div>
                </#if>
            </div>
        </div>
    </div>
</body>
</html>    