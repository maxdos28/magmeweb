<#import "../user/myFriend.ftl" as myFriend>
<#import "../user/myFollow.ftl" as myFollow>
<#import "../user/waitAgree.ftl" as waitAgree>
<#import "../user/sideRight.ftl" as sideRight>

<html>
<head>
<title>用户空间</title>
</head>
<body>
    <!--sideMiddle-->
    <div class="sideMiddle">
        <div class="con jqueryTagBox" id="snsFriend">
            <div class="ctrl">
                <div>我的好友</div>
                <div>我的关注</div>
                <div>好友申请</div>
            </div>
            <div class="doorList clearFix">
			<@myFriend.main />
			<@myFollow.main />
			<@waitAgree.main />
            </div>
        </div>
    </div>	
	<@sideRight.main />
	
<script>
$(function(){
	$.jquerytagbox("#snsFriend",${pos!"0"});
});
</script>
</body>
</html>