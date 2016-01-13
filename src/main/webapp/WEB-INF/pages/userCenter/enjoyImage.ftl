<script src="${systemProp.staticServerUrl}/v3/js/enjoyImage.js"></script>
<#list enjoyImageList as userImage>
	<#if userImage.type==1>
	<div class="item">
    	<a class="photo" style="height:<#if (userImage.width>0 && userImage.height>0)>${userImage.height*200/userImage.width+22}<#else>222</#if>px;" href="${systemProp.appServerUrl}/user-image!show.action?<#if tagName??&& tagName!="">tagName=${tagName}&</#if>imageId=${userImage.id}">
            <img height="<#if (userImage.width>0 && userImage.height>0)>${userImage.height*200/userImage.width}<#else>200</#if>px" src="${systemProp.tagServerUrl+userImage.path200}" />
            <h5>${(userImage.description)!""}</h5>
        </a>
        <div class="tools"><em favTypeId="pic_${userImage.id}" class="iconHeart">${(userImage.enjoyNum)!"0"}</em><em class="iconShare" shareTypeId="pic_${userImage.id}">分享</em></div>
        <div class="info">
        	<a class="user" href="${systemProp.appServerUrl}/user-visit!index.action?userId=${userImage.userId}"><img src="<#if ((userImage.userAvatar30)??)&&(userImage.userAvatar30!="")>${systemProp.profileServerUrl+userImage.userAvatar30}<#else>${systemProp.staticServerUrl}/v3/images/head30.gif</#if>" /></a>
            <p>由<a href="${systemProp.appServerUrl}/user-visit!index.action?userId=${userImage.userId}">${(userImage.userNickName)!""}</a>创建，出自<a href="${systemProp.appServerUrl}/publish/mag-read.action?id=${userImage.issueId}">[&nbsp;${(userImage.publicationName)!""}&nbsp;]</a></p>
        </div>
    </div>
    <#elseif userImage.type==3>
	<div class="item">
    	<a class="photo" clickEventId="" style="height:<#if (userImage.width>0 && userImage.height>0)>${userImage.height*200/userImage.width+22}<#else>222</#if>px;" href="${systemProp.appServerUrl}/issue-image!show.action?id=${userImage.id}">
            <img height="<#if (userImage.width>0 && userImage.height>0)>${userImage.height*200/userImage.width}<#else>200</#if>px" src="${systemProp.fpageServerUrl}/event${userImage.path200}" />
            <h5>${(userImage.title)!""}</h5>
        </a>
        <div class="tools"><em favTypeId="eve_${userImage.id}" class="iconHeart">${(userImage.enjoyNum)!"0"}</em><em class="iconShare" shareTypeId="eve_${userImage.id}">分享</em></div>
        <div class="info">
            <p>出自<a href="${systemProp.appServerUrl}/publish/mag-read.action?id=${userImage.issueId}">[&nbsp;${(userImage.publicationName)!""}&nbsp;]</a></p>
        </div>
    </div>  
    </#if>
</#list>   