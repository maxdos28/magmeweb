<html>
<head>
<title>Magme</title>
</head>
<body>
    <!--sideMiddleRight-->
    <div class="sideMiddleRight">
    	<!--topTools-->
    	<div class="messageBar clearFix">
            <div class="floatl">
            	<a name="messageMenu" href="javascript:void(0)" class="btnWS" >收件箱</a>
            </div>
        </div>
    	<!--conMessage-->
		<div id="conMessageWrite" class="conB conMessageWrite">
        	<h2>发送短消息</h2>
        	<div class="conBody">
                <div id="wirteMessage" class="wirteMessage">
                	<form id="wirteMessageForm" method="post" action="">
	                	<input type="hidden" name="toType" value="1" />
	                	<div id="chooseFriend" class="nameList clearFix">
	                    	<input id="sendNames" name="toUserNickNames" class="input" tips="选择好友" />
	                        <ul id="friends" class="clearFix scroll-pane">
	                        	<#list friendList as friend>
	                        		<li><label><input type="checkbox" name="toUserIds" value="${friend.id}"/>${friend.nickName}</label></li>
	                        	</#list>
	                        </ul>
	                        <a href="javascript:void(0)" id="chooseOK" class="submit">确定</a>
	                        <a href="javascript:void(0)" id="checkBack" class="checkBack">反选</a>
	                        <a href="javascript:void(0)" id="checkAll" class="checkAll">全选</a>
	                    </div>
	                	<div class="reply clearFix">
	                        <textarea class="input" id="messageContent" name="content" tips="消息内容："></textarea>
	                        <a href="javascript:void(0)" id="sendMessage" name="groupMessageSubmit" class="btnBS floatr">发送</a>
	                    </div>
                	</form>
                </div>
            </div>
        </div>
    </div>
</body>
</html>    