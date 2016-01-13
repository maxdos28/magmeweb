<link href="${systemProp.staticServerUrl}/v3/style/channelSearchv6.css" rel="stylesheet" type="text/css" />
<div class="body bodyMagazine clearFix" id="magazineWallPublication" >
    <#if issueList?? && (issueList?size) gt 0 >
    <#list issueList as issue>
    <#if issue_index lt 20>
    <div class="item">
    	<a target="_blank" class="photo" href="${systemProp.appServerUrl}/publish/mag-read.action?id=${issue.id}">
            <img src="${systemProp.magServerUrl}/${issue.publicationId}/172_${issue.id}.jpg" />
            <h5>${issue.publicationName} ${issue.issueNumber} <span>&nbsp;</span></h5>
            <sup class="album"></sup>
        </a>
    </div>
	</#if>
    </#list>
    </#if>
</div>