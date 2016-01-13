<!--body-->
<#macro main>
	<div class="body bodyMagazine clearFix" id="magazineWall">
		  <#if issueList?? && (issueList?size>0)>
		   <#list issueList as issue>
				<div class="item">
			    	<a target="_blank" class="photo" href="http://www.magme.com/publish/mag-read.action?id=${issue.id}">
			            <img src="${systemProp.magServerUrl}/${issue.publicationId}/200_${issue.id}.jpg" />
			            <h5>[ ${issue.publicationName} ]<span>${issue.publishDate?string("yyyy-MM-dd")}</span></h5>
			        </a>
			        <div class="tools png"><em name="emailSubscribe" class="iconEmail png"></em><em class="iconShare png"></em></div>
			    </div>		   
		    </#list>
		</#if>
	    	<div class="sideRight">
    	<div class="con conAd">
	        	<iframe src="${systemProp.appServerUrl}/baidu_clb.html#nv0_2" width="100%" height="100%" frameborder="0" scrolling="no" ></iframe>
	        </div>
	        <#if adVideo??>
		        <div class="con conVideoAd" id="videoAd">
		        	<#list adVideo as vList>
		        	<a href="javascript:void(0);"  style="display: block;">
		                <img id="NvADvideoPlayIndexImg" url="<#if vList.mediaUrl??>${vList.mediaUrl}</#if>"  src="<#if vList.imgUrl??>${vList.imgUrl}</#if>" />
		                <span id="NvADvideoPlayIndex" url="<#if vList.mediaUrl??>${vList.mediaUrl}</#if>" class="png"></span>
		            </a>
		            </#list>
		        </div>
	        </#if>
    	<div class="con conMagTop">
            <strong>杂志排行</strong>
            <#if sortIssueList?? && (sortIssueList?size>0)>
            	<#list sortIssueList as sortIssue>
            		<div class="book"><a target="_blank"  href="http://www.magme.com/publish/publication-home!mag.action?publicationId=${sortIssue.publicationId}" class="png"><img src="${systemProp.magServerUrl}/${sortIssue.publicationId}/200_${sortIssue.id}.jpg" title="${sortIssue.publicationName}" /></a><h6>${sortIssue.publicationName}</h6><p>${sortIssue.publishDate?string("yyyy-MM-dd")}</p></div>
            	</#list>
            </#if>
        </div>
    	<div class="con conList">
    		<#if goodArticleList?? && (goodArticleList?size>0)>
    			<#list goodArticleList as goodas>
    				 <a target="_blank"  href="http://www.magme.com/publish/mag-read.action?pageId=${goodas.pageno}&Id=${goodas.issueid}">
		                <strong>${goodas.title}</strong>
		                <span>(${goodas.times})</span>
		            </a>
    			</#list>
    		</#if>
            <a href="javascript:void(0)" class="more">阅读排行</a>
        </div>
    </div>
		
	</div>
</#macro>
