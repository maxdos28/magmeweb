<#import "../components/searchForm.ftl" as search>
<#import "list.ftl" as list>

<html>
<head>
<title>用户空间</title>
</head>
<body>
<link href="${systemProp.staticServerUrl}/style/channelSns.css" rel="stylesheet" type="text/css" />
<script src="${systemProp.staticServerUrl}/js/jquery.masonry.min.js"></script>
<script>
$(function(){
	$('#tagWall').masonry({itemSelector: '.item'});
	
	//关注attention--------------------------
	$("a[name='iconAdd']").click(function(e){
		e.preventDefault();
		if(!$("#loginBar").is(":visible")){
			gotoLogin("用户没有登录，请登录！");
		}else{
			var objectId = $(this).attr("objectId");
			var type = $(this).attr("type");
			ajaxAddFollow(objectId,type,function(rs){
				if(!rs)return;
				var code = rs.code;
				if(code == 200){
					$("#iconAdd_"+type+"_"+objectId).hide();
					$("#iconSub_"+type+"_"+objectId).show();
					alert("操作成功！");
				}else if(code == 400){
					gotoLogin("用户没有登录，请登录！");
				}else{
					alert(rs.message);
				}
			});
		}
	});
	//cancel关注-------------------------------------------------------------------
	$("a[name='iconSub']").click(function(e){
		e.preventDefault();
		if(!$("#loginBar").is(":visible")){
			gotoLogin("用户没有登录，请登录！");
		}else{
			var objectId = $(this).attr("objectId");
			var type = $(this).attr("type");
			ajaxCancelFollow(objectId,type,function(rs){
				if(!rs)return;
				var code = rs.code;
				if(code == 200){
					$("#iconAdd_"+type+"_"+objectId).show();
					$("#iconSub_"+type+"_"+objectId).hide();
					alert("操作成功！");
				}else if(code == 400){
					gotoLogin("用户没有登录，请登录！");
				}else{
					alert(rs.message);
				}
			});
		}
	});	
});
</script>

<div class="body">
	<div class="body1000">
	<!--topBar-->
	<div class="topBar clearFix">
        <ul class="subNav">
            <li><a href="javascript:void(0)">${(visitUser.nickName)!""}的空间</a></li>
        </ul>
    	<@search.main type="User" tip="用户"/>
    </div>
	<!--bodyContent-->
	<div class="bodyContent clearFix">
    	<div class="tagWallInner tagWallNoBg" id="tagWall">
        	<div class="item itemUser">
            	<div class="head"><img src="<#if ((visitUser.avatar)??)&&(visitUser.avatar!="")>${systemProp.profileServerUrl+visitUser.avatar60}<#else>${systemProp.staticServerUrl}/images/head60.gif</#if>" /></div>
                <h3>${(visitUser.nickName)!""}</h3>
                <h4><a class="current"  href="${systemProp.appServerUrl}/user-visit!friend.action?userId=${userId!""}">好友：<span>${(visitUser.statsMap.friendNum)!"0"}</span></a></h4>
                <h4><a href="${systemProp.appServerUrl}/user-visit!follow.action?userId=${userId!""}">关注：<span>${(visitUser.statsMap.followNum)!"0"}</span></a></h4>
                <ul class="clear listB clearFix">
                    <li><a href="${systemProp.appServerUrl}/user-visit!index.action?userId=${userId!""}">TA喜欢的切米</a></li>
                    <li><a  href="${systemProp.appServerUrl}/user-visit!enjoyIssue.action?userId=${userId!""}">TA喜欢的杂志</a></li>
                    <li><a href="${systemProp.appServerUrl}/user-visit!enjoyEvent.action?userId=${userId!""}">TA喜欢的事件</a></li>
					<li><a href="${systemProp.appServerUrl}/user-visit!userImage.action?userId=${userId!""}">TA的图片</a></li>                    
                </ul>
            </div>
            
			<@list.friend/>
        </div>
    </div>
    </div>
</div>
</body>
</html>