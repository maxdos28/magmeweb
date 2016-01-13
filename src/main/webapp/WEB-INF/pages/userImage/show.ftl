<link href="${systemProp.staticServerUrl}/v3/style/channelDetail.css" rel="stylesheet" type="text/css" />

<script src="${systemProp.staticServerUrl}/v3/js/picShow.js"></script>

<!--body-->
<div class="body clearFix">
	<div class="sideLeft">
        <!--其它看米图片-->
    	<div class="otherPic JQfadeIn">
        	<div class="inner clearFix">
        		<#if preUserImageList??>
        		<#list preUserImageList as preUserImage>
        		<#if sufUserImageList?size<3 >
        			<#if preUserImageList?size< (6-sufUserImageList?size+preUserImage_index+1) >
        			<div class="item"><a href="${systemProp.appServerUrl}/user-image!show.action?<#if tagName??&& tagName!="">tagName=${tagName}&</#if>imageId=${preUserImage.id}"><img src="${systemProp.tagServerUrl+preUserImage.path68}" /></a></div>
        			</#if>
        		<#else>
        			<#if preUserImageList?size< (6-3+preUserImage_index+1) >
        			<div class="item"><a href="${systemProp.appServerUrl}/user-image!show.action?<#if tagName??&& tagName!="">tagName=${tagName}&</#if>imageId=${preUserImage.id}"><img src="${systemProp.tagServerUrl+preUserImage.path68}" /></a></div>
        			</#if>        		
        		</#if>
        		</#list> 
        		</#if>       	
                <div class="item current"><a href="${systemProp.appServerUrl}/user-image!show.action?<#if tagName??&& tagName!="">tagName=${tagName}&</#if>imageId=${userImage.id}"><img src="${systemProp.tagServerUrl+userImage.path68}" /></a></div>
                <#if sufUserImageList??>
        		<#list sufUserImageList as sufUserImage>
        		<#if preUserImageList?size<3 >
        			<#if sufUserImage_index<(6-preUserImageList?size) >
        			<div class="item"><a href="${systemProp.appServerUrl}/user-image!show.action?<#if tagName??&& tagName!="">tagName=${tagName}&</#if>imageId=${sufUserImage.id}"><img src="${systemProp.tagServerUrl+sufUserImage.path68}" /></a></div>
        			</#if>
        		<#else>
        			<#if sufUserImage_index<3 >
        			<div class="item"><a href="${systemProp.appServerUrl}/user-image!show.action?<#if tagName??&& tagName!="">tagName=${tagName}&</#if>imageId=${sufUserImage.id}"><img src="${systemProp.tagServerUrl+sufUserImage.path68}" /></a></div>
        			</#if>        		
        		</#if>        		
        		</#list> 
        		</#if>                
            </div>
        </div>
        <!--看米大图-->
    	<div class="photoArea">
        	<a class="photo" target="_blank" href="${systemProp.appServerUrl}/publish/mag-read.action?id=${userImage.issueId}&pageId=${userImage.pageNo}"><img src="${systemProp.tagServerUrl}${userImage.path}" /></a>
        	<#if preUserImageList??&&(preUserImageList?size!=0) >
        	<#list preUserImageList as preUserImage>
        		<#if (preUserImage_index+1)==(preUserImageList?size)>
        			<a href="${systemProp.appServerUrl}/user-image!show.action?<#if tagName??&& tagName!="">tagName=${tagName}&</#if>imageId=${preUserImage.id}" class="turnL">上一张</a>
        		</#if>
        	</#list>
        	<#else>
        	<a class="turnL" href="javascript:void(0)">上图</a>
        	</#if>
        	
        	<#if sufUserImageList??&&(sufUserImageList?size!=0) >
        	<#list sufUserImageList as sufUserImage>
        		<#if sufUserImage_index==0>
        			<a href="${systemProp.appServerUrl}/user-image!show.action?<#if tagName??&& tagName!="">tagName=${tagName}&</#if>imageId=${sufUserImage.id}" class="turnR">下一张</a>
        		</#if>
        	</#list>
        	<#else>
        	<a class="turnR" href="javascript:void(0)">下图</a>
        	</#if>        	        	
            <div class="photoBottom"></div>
        </div>
        <!--看米描述-->
        <div class="description bgLine">
        	<p id="picDesc">${(userImage.description)!""}</p>
        </div>
        <!--点击分享工具-->
        <div class="tools line">
        	<div class="info">
            	<strong class="click">点击次数：<span>${(userImage.clickNum)!"0"}</span></strong>
                <strong class="from">出自<a href="${systemProp.appServerUrl}/publish/mag-read.action?id=${userImage.issueId}">[${(userImage.publicationName)!""}]</a>创建于<span>${userImage.createdTime?string("yyyy/MM/dd")}</span></strong>
            </div>
        	<a class="iconHeart <#if isEnjoy?? && isEnjoy==1>favCurrent</#if>" favTypeId="pic_${userImage.id}" href="javascript:void(0)">${(userImage.enjoyNum)!"0"}</a>
        	<a class="iconShare" href="javascript:void(0)">分享到</a>
        	<a class="icon" tagShare="tsina" href="javascript:void(0)"><img src="/v3/images/icon/sns32/32-weibo.gif" /></a>
        	<a class="icon" tagShare="kaixin" href="javascript:void(0)"><img src="/v3/images/icon/sns32/32-kaixin001.gif" /></a>
        	<a class="icon" tagShare="renren" href="javascript:void(0)"><img src="/v3/images/icon/sns32/32-renren.gif" /></a>
        	<a class="icon" tagShare="tqq" href="javascript:void(0)"><img src="/v3/images/icon/sns32/32-qq.gif" /></a>
        </div>
        <!--热门标签-->
        <div id="tagList" class="tagList line">
            <ul class="clearFix">
                <li class="title"><a href="javascript:void(0)">标签分类</a></li>
		    	<#list tagList as tag>
		    		<li class="<#if tagName??&&(tagName==tag.name)>current</#if>"><a name="tagName" href="javascript:void(0)">${tag.name}</a></li>
		    	</#list>
		    	<li class="add"><input id="tagInfo" type="text" tips="添加新标签" /><a id="addTag" picId="${userImage.id}" href="javascript:void(0)"></a></li>                  
            </ul>
        </div>
        <!--用户评论-->
        <div id="comments" class="comment">
        	<!--我的评论-->
        	<div class="item mySelf">
                <div><textarea id="picComment" tips="喜欢就给它留言吧"></textarea></div>
            	<a class="head" href="javascript:void(0)"><img src="<#if ((session_user.avatar)??)&&(session_user.avatar!="")>${systemProp.profileServerUrl+session_user.avatar46}<#else>${systemProp.staticServerUrl}/images/head46.gif</#if>" alt="${(session_user.nickName)!""}" /></a>
                <a id="commentBtn" picId="${userImage.id}" class="btnWS" href="javascript:void(0)">评论</a>
                <sub></sub>
            </div>
			<#list userImageCommentList as comment>
        	<div class="item">
                <div>${(comment.contentInfo.content)!""}</div>
            	<a class="head" href="${systemProp.appServerUrl}/user-visit!index.action?userId=${comment.userId}"><img src="<#if ((comment.userAvatar30)??)&&(comment.userAvatar30!="")>${systemProp.profileServerUrl+comment.userAvatar46}<#else>${systemProp.staticServerUrl}/images/head46.gif</#if>" alt="${(comment.userNickName)!""}" /></a>
                <sub></sub>
            </div>            			
			</#list>            
        </div>
        
    </div>
	<div class="sideRight">
    	<div class="userCon">
        	<a class="head" href="${systemProp.appServerUrl}/user-visit!index.action?userId=${user.id}"><img src="<#if ((user.avatar60)??)&&(user.avatar60!="")>${systemProp.profileServerUrl+user.avatar60}<#else>${systemProp.staticServerUrl}/v3/images/head60.gif</#if>" /></a>
            <strong>${(user.nickName)!""}</strong>
            <a href="javascript:void(0)" id="addFri" userId="${user.id}" class="btn" <#if isFollow??&&isFollow==1>style="display:none;"</#if>>加为好友</a>
            <a href="javascript:void(0)" id="removeFri" userId="${user.id}"  class="btn" <#if !(isFollow??)||(isFollow??&&isFollow==0)>style="display:none;"</#if>>取消好友</a>
            <a id="sendMsgBtn" class="btn" href="javascript:alert('pop#userNewMsg')">发送消息</a>
            
        </div>
    	<div class="userQiemi JQfadeIn">
        	<h6><a class="floatr" href="${systemProp.appServerUrl}/user-visit!userImage.action?userId=${user.id}">全部<span>(${(user.statsMap.userImageNum)!"0"})</span></a><strong>${(user.nickName)!""}</strong>的其它内容</h6>
            <div class="conBody clearFix">
            	<#list userImageList as ui>
            		<div class="item"><a href="${systemProp.appServerUrl}/user-image!show.action?<#if tagName??&& tagName!="">tagName=${tagName}&</#if>imageId=${ui.id}"><img src="${systemProp.tagServerUrl+ui.path68}" /></a></div>
            	</#list>
            </div>
        </div>
    	<div class="otherLike line JQfadeIn">
        	<h6>喜欢此图片的人也喜欢</h6>
            <div class="conBody clearFix">
            	<#list enjoyUserImageList as ui>
            		<div class="item"><a href="${systemProp.appServerUrl}/user-image!show.action?<#if tagName??&& tagName!="">tagName=${tagName}&</#if>imageId=${ui.id}"><img src="${systemProp.tagServerUrl+ui.path68}" /></a></div>
            	</#list>            
            </div>
        </div>
    
    </div>
</div>

<div id="userNewMsg" class="popContent hide">
	<fieldset>
    	<h6>发送私信给<span name="userName">${(user.nickName)!""}</span></h6>
        <div>
            <em><textarea id="msgContent" class="input g420"></textarea></em>
        </div>
        <div class="floatr">
            <em><a id="send" userId="${user.id}" href="javascript:void(0)" class="btnBB" >发送</a></em>
            <em><a id="closePop" href="javascript:void(0)" class="btnWB">取消</a></em>
        </div>
    </fieldset>
</div>