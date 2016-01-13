<#if creativeList?? && (creativeList.size()>0)>
<#list creativeList as c>
 <div class="theme">
	<div class="content conPhoto clearFix">
		<#if !(session_user??)>
			<div class="calendar">
        		<span>${c.updateTime?string('yyyy')} &nbsp;/&nbsp;${c.updateTime?string('MM')}</span>
        		<strong>${c.updateTime?string('dd')}</strong>
        	</div>
    	</#if>
        <div class="conPlay">
            <div class="inner">
				<a target="_blank" href="${systemProp.appServerUrl}/sns/c${c.id!'0'}/" w='${c.width!}' h='${c.high!}' pic="" class="a0 pic cover">
				<img width='${c.width!}' height='${c.high!}' src="<#if (c.imagePath)??>${systemProp.staticServerUrl}${avatarResize(c.imagePath,'max_800')}</#if>"  />
				</a>
            </div>
        </div>
        <div class="uInfo">
        	<#--
        	<#if (c.magazineName)?? && c.magazineName!="">
            	<h3 class="png">
            	    <a target="_blank" href="${c.magazineUrl!''}" title="出自&nbsp;[&nbsp;${c.magazineName!''}&nbsp;]">mask</a>
            		<img src="${systemProp.magServerUrl}/${c.publicationid!''}/${c.issueid!''}/1.jpg" />
            	</h3>
            </#if> -->
            <div class="userHead png">
                <a class="head" u='${c.userId}' href="${systemProp.appServerUrl}/sns/u${c.userId}/">
                    <img src="<#if (c.avatar)?? && c.avatar!="" >${systemProp.profileServerUrl}${avatarResize(c.avatar,'60')}<#else>${systemProp.staticServerUrl}/images/head60.gif</#if>" onerror="this.src='${systemProp.staticServerUrl}/images/head60.gif'" />
                    <strong class="infoName">${stringSub(user.nickName,24)}</strong>
                    <#if (c.reserver1)?? && c.reserver1=='M'>
	            		<em class="png m" title="麦米认证编辑"></em>
	            	</#if>
            	</a>
            </div>
            <div class="tagList">
              <#if (c.tagStrList)?? && (c.tagStrList.size()>0)>
            	<#list c.tagStrList as tag>
                	<a  target="_blank"  href="${systemProp.appServerUrl}/sns/square.action?tagName=${encode(tag)}">${tag}</a>
                </#list>
              </#if>
            </div>
        </div>
    	<h2 class='til' til="${c.firstTitle!''}" url="${systemProp.appServerUrl}/sns/c${c.id!''}/">
    	<a target="_blank" href="${systemProp.appServerUrl}/sns/c${c.id!''}/">${c.firstTitle!''}</a><span class="date"><#if c.updateTime??>${c.updateTime?string("yyyy-MM-dd")}</#if></span></h2>
        <div class="text">
            <p>${c.content!''}</p>
            <a class="readMore" href="${systemProp.appServerUrl}/sns/c${c.id!''}/" target="_blank">继续阅读</a>
        </div>
     	<div cre="${c.id!''}" class="tools png">
     	    <#if (session_user.id)?? && (user.id)?? && session_user.id==user.id>
         		<em class="edit"><a href="${systemProp.appServerUrl}/sns/article-pub!edit.action?creativeId=${c.id!''}">编辑</a></em>
				<em class="delete"><a href="javascript:void(0);">删除</a></em>
			</#if>
            <em title="评论" commentCreativeId="${c.id!'0'}" class="iconMoreInfo png"></em>
            <em title="喜欢" class="iconHearted png"></em>
        </div>
    </div>
   </div>
  </#list>
</#if>	
<#if creativeList??  && (creativeList.size())??&& creativeList.size()==0>
	<div class="theme">
	</div>
</#if>
