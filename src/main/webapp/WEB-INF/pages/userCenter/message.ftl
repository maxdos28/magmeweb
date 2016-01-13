<script src="${systemProp.staticServerUrl}/js/message.js"></script>

  	<div class="conMiddleRight">
        <!--conMyMessage-->
        <div class="conMyMessage">
            <h2>我的消息</h2>
            <div class="conBody">
                <!--topTools-->
                <div class="topBar clearFix">
                    <div class="floatr searchInput">
                        <input type="text" name="searchMessageKeyWord" tips="消息关键字" />
                        <a href="javascript:void(0)" name="searchMessage" class="search" >搜索短消息</a>
                    </div>
                    <div class="floatl">
                    <a href="${systemProp.appServerUrl}/user-center!writeMessage.action" class="btnWB" >新建</a>
                    </div>
                </div>
                <#if messageList??&& 0<messageList?size >
                <div class="msgList">
                	<#list messageList as message>
                    <div messageId="${message.id}" class="item clearFix">
                        <input type="checkbox" />
                        <a href="${systemProp.appServerUrl}/user-center!readMessage.action?fromUserId=${message.fromUserId}&fromType=${message.fromType}">
                            <div>
                                <div class="userHead"><#if ((message.fromUserAvatar)??)&&(message.fromUserAvatar!="")><img src="${systemProp.profileServerUrl+message.fromUserAvatar60}" /><#else><img src="${systemProp.staticServerUrl}/images/head60.gif" /></#if><sub></sub></div>
                                <strong>${message.fromUserNickName}</strong>
                                <span>${(message.createdTime?string("yyyy-MM-dd"))!""}</span>
                            </div>
                            <#if (message.newMessageNum)??&&(message.newMessageNum>0)>
                            <sup class="new" title="${(message.newMessageNum)!"0"}条未读消息">(${(message.newMessageNum)!"0"})</sup>
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
                <div class="nullInfo">您还没有消息记录</div>                
                </#if>
            </div>
        </div>
    </div>