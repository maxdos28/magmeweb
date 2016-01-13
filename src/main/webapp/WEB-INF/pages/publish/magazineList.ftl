<!--body-->
<#macro main>
	<div class="body bodyMagazine clearFix" id="magazineWall">
		  <#if issueList?? && (issueList?size>0)>
		   <#list issueList as issue>
				<div class="item" issueId="${issue.id}" publisherId="${issue.publisherId}" target="_blank" publicationId="${issue.publicationId}">
			    	<a class="photo" href="javascript:void(0)" issueId="${issue.id}">
			            <img src="${systemProp.magServerUrl}/${issue.publicationId}/200_${issue.id}.jpg" />
			            <h5>[ ${issue.publicationName} ]<span>${issue.publishDate?string("yyyy-MM-dd")}</span></h5>
			        </a>
			    </div>
		    </#list>
		</#if>
	</div>
</#macro>
