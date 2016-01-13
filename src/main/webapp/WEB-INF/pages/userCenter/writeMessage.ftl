<script src="${systemProp.staticServerUrl}/js/message.js"></script>

  	<div class="conMiddleRight">
        <!--conMyMessageWrite-->
        <div id="conMessageWrite" class="conMyMessageWrite">
            <h2>发送短消息</h2>
            <div class="conBody">
                <div id="wirteMessage" class="wirteMessage">
                	<form id="wirteMessageForm" method="post">
                    <div id="chooseFriend" class="nameList clearFix">
                        <input name="nickName" class="input" tips="收信人昵称,多个用户之间以英文逗号或中文逗号分隔" />
                    </div>
                    <div class="reply clearFix">
                    	<textarea class="input" name="content" tips="消息内容："></textarea>
                        <a href="javascript:void(0)" name="groupMessageSubmit" class="btnBB">发送</a>
                        <a href="${systemProp.appServerUrl}/user-center!message.action" class="back btnWB">返回收件箱</a>
                    </div>
                    </form>
                </div>
            </div>
        </div>
    </div>