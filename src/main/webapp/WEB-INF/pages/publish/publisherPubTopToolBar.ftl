<!--conTopbar-->
<#macro main>
<div class="conB conPubTopbar" id="pubTopbar">
	<h2>
	   杂志管理
	</h2>
    <div class="conBody clearFix">
    	<a class="btnLR turnLeft" href="javascript:void(0)"></a>
        <div class="outer">
        	<div class="inner" id="publisherPubTopToolBarInner">
        	    <#if publicationList??>
        	        <#list publicationList as publication>
		                <div class="item showBar <#if (publicationId??) && (publicationId!=0) && (publicationId==publication.id)>current</#if>" id="publication_${publication.id}">
		                    <strong name="publication_name">${publication.name} <span><#if (publication.status==2)>(已下架)</#if></span></strong>
		                    <p name="publication_des"><#if publication.description??>${publication.description}</#if></p>
		                    <span>期刊数量${publication.totalIssues}</span>
		                    <div class="bookBar">
		                      <p>
		                         <em publicationId="${publication.id}" onclick="javascript:void(0)">编辑</em>
		                         <#if publication.status!=1>
		                            <em pubUpId="${publication.id}" onclick="javascript:void(0)">上架</em>
		                         </#if>
		                         <#if publication.status==1>
		                            <em pubDownId="${publication.id}" onclick="javascript:void(0)">下架</em>
		                         </#if>
		                      </p>
		                    </div>
		                </div>
	                </#list>
                </#if>
              
            </div>
        </div>
    	<a class="btnLR turnRight" href="javascript:void(0)"></a>
    </div>
</div>
</#macro>