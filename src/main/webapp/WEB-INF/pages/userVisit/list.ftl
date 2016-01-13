<#macro enjoyImage>            
    <#if userImageList?? && (userImageList?size>0)>     
            <#list userImageList as userImage>
            <div class="item">
            	<a class="img" href="${systemProp.appServerUrl}/user-image!show.action?imageId=${userImage.id}"><img src="${systemProp.tagServerUrl+userImage.path172}" /></a>
                <h6><span favTypeId="pic_${userImage.id}" class="fav">${userImage.enjoyNum}</span><span class="top">${userImage.clickNum}</span><span clickTypeId="pic_${userImage.id}" class="click">工具</span></h6>
                <h5><a href="${systemProp.appServerUrl}/user-visit!index.action?userId=${userImage.userId}"><img src="<#if ((userImage.userAvatar30)??)&&(userImage.userAvatar30!="")>${systemProp.profileServerUrl+userImage.userAvatar30}<#else>${systemProp.staticServerUrl}/images/head30.gif</#if>" /></a><p>${userImage.description}</p></h5>
                <#if (userImage.userImageCommentList)??>
                <div class="msg">
                	<#list userImage.userImageCommentList as comment>
                	<h5><a href="${systemProp.appServerUrl}/user-visit!index.action?userId=${comment.userId}"><img src="<#if ((comment.userAvatar30)??)&&(comment.userAvatar30!="")>${systemProp.profileServerUrl+comment.userAvatar30}<#else>${systemProp.staticServerUrl}/images/head30.gif</#if>" /></a><p>${comment.contentInfo.content}</p></h5>
                	</#list>
                </div>
                </#if>
            </div>
            </#list>
      </#if>
</#macro>

<#macro enjoyIssue>
            <#list issueList as issue>
            <div class="item">
            	<a class="img" href="${systemProp.appServerUrl}/publish/mag-read.action?id=${issue.id}"><img src="${systemProp.magServerUrl}/${issue.publicationId}/172_${issue.id}.jpg" /></a>
                <h6><span favTypeId="mag_${issue.id}" class="fav">喜欢</span></h6>
                <h5><a href="${systemProp.appServerUrl}/publish/publisher-home.action?publisherId=${(issue.publisherId)!"0"}">
                	<#if (issue.publisherLogo)??&&issue.publisherLogo!="">
                	<img src="${systemProp.publishProfileServerUrl+avatarResize(issue.publisherLogo,"30")}" />
                	<#else>
                	<img src="${systemProp.staticServerUrl}/images/head30.gif" />
                	</#if>
                	<p>${(issue.publicationName)!""}</p>
                	</a></h5>
            </div>
            </#list>
</#macro>

<#macro enjoyEvent>
			<#list eventList as event>  
            <div class="item">
                <a class="img" href="${systemProp.appServerUrl}/publish/mag-read.action?id=${event.issueId}&pageId=${event.pageNo}"><img src="${systemProp.fpageServerUrl}/event/${event.imgFile}" /></a>
                <h6><span favTypeId="eve_${event.id}" class="fav">喜欢</span></h6>
                <h5><p>${(event.description)!""}</p></h5>
            </div>
            </#list>
</#macro>

<#macro userImage>            
    <#if userImageList?? && (userImageList?size>0)>     
            <#list userImageList as userImage>
            <div class="item">
            	<a class="img" href="${systemProp.appServerUrl}/user-image!show.action?imageId=${userImage.id}"><img src="${systemProp.tagServerUrl+userImage.path172}" /></a>
                <h6><span favTypeId="pic_${userImage.id}" class="fav">${userImage.enjoyNum}</span><span class="top">${userImage.clickNum}</span><span clickTypeId="pic_${userImage.id}" class="click">工具</span></h6>
                <h5><a href="${systemProp.appServerUrl}/user-visit!index.action?userId=${userImage.userId}"><img src="<#if ((userImage.userAvatar30)??)&&(userImage.userAvatar30!="")>${systemProp.profileServerUrl+userImage.userAvatar30}<#else>${systemProp.staticServerUrl}/images/head30.gif</#if>" /></a><p>${userImage.description}</p></h5>
                <#if (userImage.userImageCommentList)??>
                <div class="msg">
                	<#list userImage.userImageCommentList as comment>
                	<h5><a href="${systemProp.appServerUrl}/user-visit!index.action?userId=${comment.userId}"><img src="<#if ((comment.userAvatar30)??)&&(comment.userAvatar30!="")>${systemProp.profileServerUrl+comment.userAvatar30}<#else>${systemProp.staticServerUrl}/images/head30.gif</#if>" /></a><p>${comment.contentInfo.content}</p></h5>
                	</#list>
                </div>
                </#if>
            </div>
            </#list>
      </#if>
</#macro>

<#macro friend>
			<#list userList as user>  
            <div class="item">
            	<#if (user.userImage)??>
            	<a class="img" href="${systemProp.appServerUrl}/user-image!show.action?imageId=${user.userImage.id}"><img src="${systemProp.tagServerUrl+user.userImage.path172}" /></a>
            	<#else>
            	<a class="img" href="javascript:void(0)"><img src="${systemProp.staticServerUrl}/images/head172230.gif" /></a>
            	</#if>
                <h5><a class="cGray" href="${systemProp.appServerUrl}/user-visit!index.action?userId=${user.id}"><img src="<#if ((user.avatar)??)&&(user.avatar!="")>${systemProp.profileServerUrl+user.avatar30}<#else>${systemProp.staticServerUrl}/images/head30.gif</#if>" /><p>${user.nickName}</p></a></h5>
                <div class="btn">
                    <a id="iconAdd_1_${user.id}" name="iconAdd" objectId="${user.id}" type="1" class="iconAdd" <#if (user.isFriend)??&&user.isFriend==1>style="display:none;"</#if> href="javascript:void(0)">加为好友</a>
                    <a id="iconSub_1_${user.id}" name="iconSub" objectId="${user.id}" type="1" class="iconSub" <#if !((user.isFriend)??&&user.isFriend==1)>style="display:none;"</#if> href="javascript:void(0)">取消好友</a>
                </div>
            </div>
            </#list>
</#macro>

<#macro follow>
			<#list publisherList as publisher>    
            <div class="item">
            	<#if (publisher.issue)??>
            	<a class="img" href="${systemProp.appServerUrl}/publish/mag-read.action?id=${publisher.issue.id}"><img src="${systemProp.magServerUrl}/${publisher.issue.publicationId}/172_${publisher.issue.id}.jpg" /></a>
            	<#else>
            	<a class="img" href="javascript:void(0)"><img src="${systemProp.staticServerUrl}/images/head172230.gif" /></a>
            	</#if>            
                <h5>
                	<a class="cGray" href="${systemProp.appServerUrl}/publish/publisher-home.action?publisherId=${publisher.id}">
                	<img src="<#if (publisher.logo)??&&(publisher.logo!="")>${systemProp.publishProfileServerUrl+avatarResize(publisher.logo,"30")}<#else>${systemProp.staticServerUrl}/images/head30.gif</#if>" />
                	<p>${publisher.publishName}</p>
                	</a>
                </h5>
                <div class="btn">
                    <a id="iconAdd_2_${publisher.id}" name="iconAdd" objectId="${publisher.id}" type="2" class="iconAdd" <#if (publisher.isFollow)??&&publisher.isFollow==1>style="display:none;"</#if> href="javascript:void(0)">加为关注</a>
                    <a id="iconSub_2_${publisher.id}" name="iconSub" objectId="${publisher.id}" type="2" class="iconSub" <#if !((publisher.isFollow)??&&publisher.isFollow==1)>style="display:none;"</#if> href="javascript:void(0)">取消关注</a>
                </div>
            </div>
            </#list>
</#macro>                                