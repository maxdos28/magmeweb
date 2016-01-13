<#list comment as c>
	<div class="bl"><a href="${systemProp.appServerUrl}/sns/u${c.uid}/" class="head">
	<img src="<#if c.avatar??>${systemProp.profileServerUrl}${avatarResize(c.avatar,'60')}<#else>${systemProp.staticServerUrl}/images/head60.gif</#if>" title="${c.nickname!}" onerror="this.src='${systemProp.staticServerUrl}/images/head60.gif'" /></a><p><span>
	<#if c.title?? && c.title!="magzine" ><del class='_read' url='${systemProp.appServerUrl}<#if c.title=="event">/index-detail.action?itemId=${c.objectId}&type=event<#else>/publish/mag-read.action?id=${c.objectId}&pageId=${c.ct}</#if>' title="查看原文"></del></#if>
	<strong><a href="${systemProp.appServerUrl}/sns/u${c.uid}/">${stringSub(c.nickname!,20)}</a>${c.createTime?string("yyyy-MM-dd")}</strong>${c.content!}</span></p><em></em>
	</div>	
</#list>
