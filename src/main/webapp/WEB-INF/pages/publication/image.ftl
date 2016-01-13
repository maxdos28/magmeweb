<html>
<head>
<title>${(publication.name)!""}的全部<#if type??&&type=="userImage">图片<#else>事件</#if> 麦米网Magme - 无处不悦读</title>
</head>
<body>
<script>
$(function(){
	$('#<#if type??&&type=="userImage">kanmiWall<#else>homeWall</#if>').masonry({itemSelector: '.item'});
	
	$("em[detailId]").live('click',function(e){
		e.preventDefault();
		e.stopPropagation();
		var eventId = $(this).attr("detailId");
		window.location.href = "/issue-image!show.action?id="+eventId;
	});	
});
</script>
<div class="body <#if type??&&type=="userImage">bodyKanmi<#else>bodyHome</#if> fullScreen noBg" id="<#if type??&&type=="userImage">kanmiWall<#else>homeWall</#if>">
	<div class="item itemUser clearFix">
        <!--userInfo-->
        <div class="userInfo">
            <strong class="name">${(publication.name)!""}</strong>
            <a href="javascript:void(0)" class="img"><img src="<#if (publisher.logo)?? && publisher.logo!="" >${systemProp.publishProfileServerUrl}${publisher.logo60}<#else>${systemProp.staticServerUrl}/images/head60.gif</#if>" /></a>
            <ul class="atten">
                <li><a href="<#if issueCount==0>javascript:void(0);<#else>${systemProp.appServerUrl}/publish/publication-home!mag.action?publicationId=${publicationId!""}</#if>" alt="${(publication.name)!""}的全部杂志"><strong>${issueCount!"0"}</strong><span>杂志</span></a></li>
                <li <#if !(type??&&type=="userImage")>class="current"</#if>><a href="<#if issueImageCount==0>javascript:void(0);<#else>${systemProp.appServerUrl}/publish/publication-home!image.action?publicationId=${publicationId!""}&type=issueImage</#if>" alt="${(publication.name)!""}的全部事件"><strong>${issueImageCount!"0"}</strong><span>事件</span></a></li>
                <li <#if type??&&type=="userImage">class="current"</#if>><a href="<#if userImageCount==0>javascript:void(0);<#else>${systemProp.appServerUrl}/publish/publication-home!image.action?publicationId=${publicationId!""}&type=userImage</#if>" alt="${(publication.name)!""}的全部图片"><strong>${userImageCount!"0"}</strong><span>图片</span></a></li>
            </ul>
            <div class="tool">
            	<#if (publisher.weiboUid)??>
                <a class="btn btnSina" weibo_uid="${(publisher.weiboUid)!''}" href="javascript:void(0)" alt="关注${((publication.name)!"")}的新浪微博">关注新浪微博</a>
                </#if>                
            </div>
        </div>
    </div>
    <#if imageLists??>
	    <#list imageLists as imageList>
	    	<#if !((imageList.value)??&&imageList.value?size==0)>
			    <#if !(imageList_index==0)>
			    <div class="item itemClear">${(imageList.key.issueNumber)!""}</div>
			    </#if>
			    
			    <#list imageList.value as image>
			    <#if type??&&type=="userImage">
			    <div class="item">
			    	<a class="photo" style="height:<#if (image.width>0 && image.height>0)>${image.height*200/image.width+22}<#else>222</#if>px;" href="${systemProp.appServerUrl}/user-image!show.action?<#if tagName??&& tagName!="">tagName=${tagName}&</#if>imageId=${image.id}">
			            <img height="<#if (image.width>0 && image.height>0)>${image.height*200/image.width}<#else>200</#if>px" src="${systemProp.tagServerUrl+image.path200}" />
			            <h5>${(image.description)!""}</h5>
			        </a>
			        <div class="tools"><em favTypeId="pic_${image.id}" class="iconHeart">${image.enjoyNum}</em><em shareTypeId="pic_${image.id}" class="iconShare">分享</em></div>
			        <div class="info">
			        	<a class="user" href="${systemProp.appServerUrl}/user-visit!index.action?userId=${image.userId}"><img src="<#if ((image.userAvatar30)??)&&(image.userAvatar30!="")>${systemProp.profileServerUrl+image.userAvatar30}<#else>${systemProp.staticServerUrl}/v3/images/head30.gif</#if>" /></a>
			            <p>由<a href="${systemProp.appServerUrl}/user-visit!index.action?userId=${image.userId}">${(image.userNickName)!""}</a>创建，出自<a href="${systemProp.appServerUrl}/publish/mag-read.action?id=${image.issueId}">[&nbsp;${(image.publicationName)!""}&nbsp;]</a></p>
			        </div>
			    </div>			    
			    <#else>
				<div class="item size${image.eventClass}"><a target="_blank" href="${systemProp.appServerUrl}/publish/mag-read.action?eventId=${image.id}">
			    	<img class="photo" src="${systemProp.fpageServerUrl}/event/${image.imgFile}" alt="${(image.title)!""}">
			    	<div class="info png">
			        	<h5>${(image.title)!""}</h5>
			            <h6>出自&nbsp;[&nbsp;${(publication.name)!""}&nbsp;]</h6>
			            <p>${(image.description)!""}</p>
			            <div class="tools png"><em detailId="${image.id}" class="iconDetail">详细</em><em title="喜欢" favTypeId="eve_${image.id}" class="iconHeart">${image.enjoyNum}</em><em shareTypeId="eve_${image.id}" class="iconShare">分享</em></div>
			        </div></a>
			    </div>    
			    </#if>
			    </#list>
			</#if>			    
	    </#list>
	</#if>
</div>
</body>
</html>