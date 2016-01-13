<link href="${systemProp.staticServerUrl}/v3/style/channelKanmi.css" rel="stylesheet" type="text/css" />
<script src="${systemProp.staticServerUrl}/v3/js/kanmi.js"></script>

<!--topTools-->
<div class="topTools">
    <!--顶部分类-->
    <ul id="tabList" class="tab clearFix">
    	<li><a name="now" <#if tab??&&tab="now">class="current"</#if> href="${systemProp.appServerUrl}/user-image/now.html">最新</a></li>
    	<li><a name="hot" <#if tab??&&tab="hot">class="current"</#if> href="${systemProp.appServerUrl}/user-image/hot.html">热门</a></li>
    </ul>
</div>

<!--body-->
<div class="body bodyKanmi fullScreen clearFix" id="kanmiWall">
	<div class="item tagList">
    	<ul class="clearFix">
        	<li class="title"><a href="javascript:void(0)">标签分类</a></li>
	    	<#list tagList as tag>
	    		<li class="<#if tagName??&&(tagName==tag.name)>current</#if>"><a href="${systemProp.appServerUrl}/user-image/${tab!"commend"}.html?tagName=${encode(tag.name)}">${tag.name}</a></li>
	    	</#list>        	
        </ul>
    </div>
    <#if userImageList?? && (userImageList?size>0)>     
            <#list userImageList as userImage>
			<div class="item">
		    	<a class="photo" style="height:<#if (userImage.width>0 && userImage.height>0)>${userImage.height*200/userImage.width+22}<#else>222</#if>px;" href="${systemProp.appServerUrl}/user-image!show.action?<#if tagName??&& tagName!="">tagName=${tagName}&</#if>imageId=${userImage.id}">
		            <img height="<#if (userImage.width>0 && userImage.height>0)>${userImage.height*200/userImage.width}<#else>200</#if>px" src="${systemProp.tagServerUrl+userImage.path200}" />
		            <h5>${(userImage.description)!""}</h5>
		        </a>
		        <div class="tools"><em favTypeId="pic_${userImage.id}" class="iconHeart">${userImage.enjoyNum}</em><em shareTypeId="pic_${userImage.id}" class="iconShare">分享</em></div>
		        <div class="info">
		        	<a class="user" href="${systemProp.appServerUrl}/user-visit!index.action?userId=${userImage.userId}"><img src="<#if ((userImage.userAvatar30)??)&&(userImage.userAvatar30!="")>${systemProp.profileServerUrl+userImage.userAvatar30}<#else>${systemProp.staticServerUrl}/v3/images/head30.gif</#if>" /></a>
		            <p>由<a href="${systemProp.appServerUrl}/user-visit!index.action?userId=${userImage.userId}">${(userImage.userNickName)!""}</a>创建，出自<a href="${systemProp.appServerUrl}/publish/mag-read.action?id=${userImage.issueId}">[&nbsp;${(userImage.publicationName)!""}&nbsp;]</a></p>
		        </div>
		    </div>            
            </#list>
      </#if>
</div>
<div id="loadMore" class="pageLoad"><span>正在加载内容...</span></div>