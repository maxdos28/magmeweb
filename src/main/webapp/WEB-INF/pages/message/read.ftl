<html>
<head>
<title>Magme</title>
</head>
<body>
    <!--sideMiddleRight-->
    <div class="sideMiddleRight">
    	<!--topTools-->
    	<div class="messageBar clearFix">
            <div class="floatr">
                <input type="text" name="searchMessageKeyWord" class="input" tips="search" />
                <a href="javascript:void(0)" name="searchMessage" class="btnBS" >搜索</a>
            </div>
            <div class="floatl">
            <a name="messageMenu" href="javascript:void(0)" class="btnWS">返回</a>
            <a name="writeMessageMenu" href="javascript:void(0)" class="btnOS" >写消息</a>
            </div>
        </div>
    </div>
    <!--sideMiddle-->
    <div id="sideMiddle" class="sideMiddle mgt0 mgb20">
    	<!--conMessage-->
		<div class="con conMessageRead">
			<#list messageList as message>
			<div messageId="${message.id}" class="item <#if (message.fromUserId)??&&(message.fromUserId==session_user.id)>itemOwn</#if> clearFix">
                <div class="userHead">
        		<#if ((message.fromUserAvatar)??)&&(message.fromUserAvatar!="")>
        			<img src="${systemProp.profileServerUrl+message.fromUserAvatar60}" />
        		<#else>
        			<img src="${systemProp.staticServerUrl}/images/head60.gif" />
        		</#if>				
				<sub></sub></div>                
                <div class="right">
                	<strong>${message.fromUserNickName}</strong>
                    <span>发送于：${message.createdTime?string("yyyy-MM-dd HH:mm:ss")}</span>
                    <p>${message.contentInfo.content}</p>
                </div>
                <del></del>
            </div>			
            </#list>
			<div class="item reply clearFix">
                <form id="wirteMessageForm" method="post" action="">
	                <input type="hidden" name="toType" value="${fromType}" />
	                <input type="hidden" name="toUserIds" value="${fromUserId}" />					
	            	<textarea class="input" name="content" tips="回复内容："></textarea>
	                <a href="javascript:void(0)" name="wirteMessageSubmit" class="btnBS floatr" >回复</a>
                </form>
            </div>
        </div>
    </div>
    
    <div id="sideRight" class="sideRight">
        <div class="ADBanner mgb20">
        	<img src="${systemProp.staticServerUrl}/images/grey_300x250.jpg">
        </div>
    </div>
</body>
</html>    