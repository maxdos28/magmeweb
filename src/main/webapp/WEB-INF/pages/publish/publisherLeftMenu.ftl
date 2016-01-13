<#macro main>
<div class="conB conSubMenu">
	<ul>
    	<li <#if publicationList?? && (publicationList?size>0)>class="sub" </#if> ><a href="javascript:void(0)">广告管理</a>
    	  <#if publicationList??>
	    	   <ul>
		    	   <#list publicationList as publication>
		    	      <#if publication.status == 1>
		                <li><a publicationId="${publication.id}" href="javascript:void(0)">${publication.name}</a></li>
		              </#if>
		           </#list>
	           </ul>
          </#if>
        </li>
    	<li <#if publicationList?? && (publicationList?size>0)>class="sub" </#if> ><a id="pubManager" href="javascript:void(0)">杂志管理</a>
    	  <#if publicationList??>
	    	   <ul>
		    	   <#list publicationList as publication>
		                <li><a issueManagePubId="${publication.id}" href="javascript:void(0)">${publication.name}</a>
		           </#list>
	           </ul>
          </#if>
        </li>
        <li><a id="uploadStaticInfo" href="/publish/publisher-upload.action">上传信息</a>
        </li>
        
    </ul>
</div>
</#macro>