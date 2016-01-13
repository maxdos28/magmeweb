<script src="${systemProp.staticServerUrl}/js/message.js"></script>

	<div class="conMiddleRight">
        <!--conMyMessageDetail-->
        <div class="conMyMessageDetail">
            <h2>我的消息</h2>
            <div class="topBar clearFix">
                <div class="floatr searchInput">
                    <input type="text" name="searchMessageKeyWord" tips="search" />
                    <a href="javascript:void(0)" name="searchMessage" class="search" >搜索</a>
                </div>
                <div class="floatl">
                <a href="${systemProp.appServerUrl}/user-center!writeMessage.action" class="btnOB" >新建</a>
                <a href="${systemProp.appServerUrl}/user-center!message.action" class="back btnWB" >返回收件箱</a>
                </div>
            </div>
            <div class="conBody">
				<#list messageList as message>
	                <div messageId="${message.id}" class="item <#if (message.fromUserId)??&&(message.fromUserId==session_user.id)>itemOwn</#if> clearFix">
	                	<div class="left clearFix">
	                        <div class="userHead">
			        		<#if ((message.fromUserAvatar)??)&&(message.fromUserAvatar!="")>
			        			<img src="${systemProp.profileServerUrl+message.fromUserAvatar60}" />
			        		<#else>
			        			<img src="${systemProp.staticServerUrl}/v3/images/head60.gif" />
			        		</#if>               
	                        <sub></sub></div>
	                        <strong>${message.fromUserNickName}</strong>
	                        <span>发送于：${(message.createdTime?string("yyyy-MM-dd"))!""}</span>
	                    </div>
	                    <div class="right">
	                        <p>${(message.contentInfo.content)!""}</p>
	                    </div>
		                <del></del>
		                <a name="delOneMessage" class='del'></a>                    
	                </div>	            			
	            </#list>              
                <div class="item reply clearFix">
                	 <form id="wirteMessageForm" method="post" action="">
		                <input type="hidden" name="toType" value="${fromType}" />
		                <input type="hidden" name="toUserIds" value="${fromUserId}" />                	 
	                    <textarea class="input" name="content" tips="回复内容"></textarea>
	                    <a ref="javascript:void(0)" name="wirteMessageSubmit" class="btnBB">回复</a>
                     </form>
                </div>
            </div>
        </div>
	</div>