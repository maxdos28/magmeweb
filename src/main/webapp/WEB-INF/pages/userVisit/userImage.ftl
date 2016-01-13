<#import "../components/searchForm.ftl" as search>
<#import "list.ftl" as list>

<html>
<head>
<title>用户空间</title>
</head>
<body>
<link href="${systemProp.staticServerUrl}/style/channelSns.css" rel="stylesheet" type="text/css" />
<script src="${systemProp.staticServerUrl}/js/jquery.masonry.min.js"></script>
<script src="${systemProp.staticServerUrl}/js/userVisit.js"></script>

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
    	<div class="tagWallInner" id="tagWall">
        	<div class="item itemUser itemShowBtn">
            	<div class="head"><img src="<#if ((visitUser.avatar)??)&&(visitUser.avatar!="")>${systemProp.profileServerUrl+visitUser.avatar60}<#else>${systemProp.staticServerUrl}/images/head60.gif</#if>" /></div>
                <h3>${(visitUser.nickName)!""}</h3>
                <h4><a href="${systemProp.appServerUrl}/user-visit!friend.action?userId=${userId!""}">好友：<span>${(visitUser.statsMap.friendNum)!"0"}</span></a></h4>
                <h4><a href="${systemProp.appServerUrl}/user-visit!follow.action?userId=${userId!""}">关注：<span>${(visitUser.statsMap.followNum)!"0"}</span></a></h4>
                <ul class="clear listB clearFix">
                    <li><a name="fav" href="${systemProp.appServerUrl}/user-visit!index.action?userId=${userId!""}">TA喜欢的切米</a></li>
                    <li><a name="mgz" href="${systemProp.appServerUrl}/user-visit!enjoyIssue.action?userId=${userId!""}">TA喜欢的杂志</a></li>
                    <li><a name="evt" href="${systemProp.appServerUrl}/user-visit!enjoyEvent.action?userId=${userId!""}">TA喜欢的事件</a></li>
			  <li><a class="current" name="pic" href="${systemProp.appServerUrl}/user-visit!userImage.action?userId=${userId!""}">TA的图片</a></li>                    
                </ul>
                <div class="clear scroll-pane">
                    <ul class="listB listTag">
                        <li><a href="javascript:void(0)" class="title">标签：</a></li>
	                	<#list tagList as tag>
	                		<#if tag_index+1<=6>
	                		<li><a href="${systemProp.appServerUrl}/user-visit!userImage.action?userId=${userId!""}&tagName=${encode(tag.name)}" class="tag${tag_index+1} <#if tagName??&&(tagName==tag.name)>current</#if>">${tag.name}</a></li>
	                		<#else>
	                		<li><a href="${systemProp.appServerUrl}/user-visit!userImage.action?userId=${userId!""}&tagName=${encode(tag.name)}" class="<#if tagName??&&(tagName==tag.name)>current</#if>">${tag.name}</a></li>
	                		</#if>
	                	</#list>                        
                    </ul>
                </div>
                <ul>
                    <li id="btnMsgSwitch" class="btnMsgSwitch"><a href="javascript:void(0)"><span>用户评论开关</span></a></li>
                </ul>
            </div>
            
			<@list.userImage/>             
            <div id="loadMore" class="loadMore">加载更多内容</div>
        </div>
    </div>
    </div>
</div>
</body>
</html>