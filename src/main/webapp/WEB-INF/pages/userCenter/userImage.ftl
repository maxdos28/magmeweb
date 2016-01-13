<script src="${systemProp.staticServerUrl}/v3/js/userCreateImage.js"></script>

<#list userImageList as userImage>
	<div class="item">
    	<a class="photo" style="height:<#if (userImage.width>0 && userImage.height>0)>${userImage.height*200/userImage.width+22}<#else>222</#if>px;" href="${systemProp.appServerUrl}/user-image!show.action?<#if tagName??&& tagName!="">tagName=${tagName}&</#if>imageId=${userImage.id}">
            <img height="<#if (userImage.width>0 && userImage.height>0)>${userImage.height*200/userImage.width}<#else>200</#if>px" src="${systemProp.tagServerUrl+userImage.path200}" />
            <h5>${(userImage.description)!""}</h5>
        </a>
        <div class="tools"><em favTypeId="pic_${userImage.id}" class="iconHeart">${(userImage.enjoyNum)!"0"}</em><em class="iconShare">分享</em></div>
        <div class="info">
        	<a class="user" href="${systemProp.appServerUrl}/user-visit!index.action?userId=${userImage.userId}"><img src="<#if ((userImage.userAvatar30)??)&&(userImage.userAvatar30!="")>${systemProp.profileServerUrl+userImage.userAvatar30}<#else>${systemProp.staticServerUrl}/v3/images/head30.gif</#if>" /></a>
            <p>由<a href="${systemProp.appServerUrl}/user-visit!index.action?userId=${userImage.userId}">${(userImage.userNickName)!""}</a>创建，出自<a href="${systemProp.appServerUrl}/publish/mag-read.action?id=${userImage.issueId}">[&nbsp;${(userImage.publicationName)!""}&nbsp;]</a></p>
        </div>
    </div>
</#list>   