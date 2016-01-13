<script src="${systemProp.staticServerUrl}/v3/js/enjoyIssue.js"></script>

<#list issueList as issue>
	<div class="item" issueId="${issue.id}" publisherId="${issue.publisherId}" publicationId="${issue.publicationId}">
    	<a class="photo" href="${systemProp.appServerUrl}/publish/mag-read.action?id=${issue.id}">
            <img src="${systemProp.magServerUrl}/${issue.publicationId}/200_${issue.id}.jpg" />
            <h5>[ ${issue.publicationName} ]<span>${issue.publishDate?string("yyyy-MM-dd")}</span></h5>
            <sup class="album"></sup>
        </a>
        <div class="tools"><em favTypeId="mag_${issue.id}"  class="iconHeart"><#if (issue.enjoyNum)??>${issue.enjoyNum}</#if></em><em class="iconPublisher"  publicationId="${(issue.publicationId)!'0'}" issueId="${(issue.id)!'0'}" publisherId="${issue.publisherId}">出版商</em></div>
    </div>
</#list>