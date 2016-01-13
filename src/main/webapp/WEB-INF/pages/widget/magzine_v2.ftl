<script src="${systemProp.staticServerUrl}/v3/widget/js/magzine_v2.js"></script>


<!--magezineBox-->
<div id="bodyMagazine" class="bodyMagazine clearFix">

        <#if magPageInfo?? && magPageInfo.data??>
          <#list magPageInfo.data as issue>
              <#if issue_index lt 15>
              <div class="item">
        	       <a issueId="${issue.id}" class="photo" href="javascript:void(0)" name="reader"><img name="issueRead" src="${systemProp.magServerUrl}/${issue.publicationId}/200_${issue.id}.jpg" /><h5 title="${issue.publicationName}的往期期刊" pubName="${issue.publicationName}">[&nbsp;${issue.publicationName}${issue.issueNumber}&nbsp;]</h5></a>
    		  </div>
		      </#if>  
	      </#list>
	    </#if>

</div>


		<#if magPageInfo?? && magPageInfo.totalPage??>
        <div class="control">
        <#if magPageInfo.totalPage gt 9>
        <a id="turnFirst" class="turn first" href="javascript:void(0)">></a>
        <a id="turnLeft" class="turn left" href="javascript:void(0)">></a></#if><#if magPageInfo.totalPage gt 1><#list 1..magPageInfo.totalPage as x><#if x lt 10><a page="${x}" href="javascript:void(0)" <#if x=1>class="current"</#if>>${x}</a></#if></#list></#if>
            
        <#if magPageInfo.totalPage gt 9>
        <a id="turnRight" class="turn right" href="javascript:void(0)"><</a>  
        <a id="turnLast" class="turn last" href="javascript:void(0)"><</a>  
        </#if>
        
        </div>
        </#if>

<script type="text/javascript">
    var pageNo = 1;
    var totalSize = parseInt("${magPageInfo.total}");
    var totalPage = parseInt("${magPageInfo.totalPage}");
</script>