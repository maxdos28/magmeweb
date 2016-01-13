<body>
<div class="conMiddleRight">
            <!--conMyMessageWrite-->
            <div id="conMessageWrite" class="conB conMyMessageWrite">
                <h2>发送短消息</h2>
                <div class="topBar clearFix">
                    <div class="floatl">
                    <#if session_aduser?? && session_aduser.level==1>
                       <a href="${systemProp.appServerUrl}/publish/publish-message.action" class="btnWS" >收件箱</a>
                      <#elseif session_aduser.level==2>
                       <a href="${systemProp.appServerUrl}/ad/ad-agency!msgList.action" class="btnWS" >收件箱</a>
                    </#if>
                    </div>
                </div>
               <div class="conBody">
                    <div id="wirteMessage" class="wirteMessage">
                    	<form id="wirteMessageForm" method="post">
                    	<input type="hidden" name="fromType" value="<#if session_aduser?? && session_aduser.level==1>2<#elseif session_aduser.level==2>4</#if>">
                        <div id="chooseFriend" class="nameList clearFix">
                            <input name="nickName" class="input" tips="收信人昵称,多个用户之间以英文逗号或中文逗号分隔" />
                        </div>
	                	<div class="reply clearFix">
	                        <textarea class="input" name="content" tips="消息内容："></textarea>
	                        <a href="javascript:void(0)" name="groupMessageSubmit" class="btnBS floatr">发送</a>
	                    </div>
                        </form>
                    </div>
                </div>
            </div>
 </div>
 </body>
