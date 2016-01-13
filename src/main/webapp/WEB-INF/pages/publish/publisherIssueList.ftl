<!--conMagezineList-->
<#macro main>
<div class="conB conMagezineList">
	<h2>
	   <#if publicationId??>
	      <a href="javascript:void(0)">${publicationName!"不存在杂志"}</a>
	     <#else>
	      <a href="javascript:void(0)">全部杂志</a>
	   </#if>
	</h2>
	
	<div id="progress-bar" title="上传进度" class="upload">
    	<strong>上传进度:</strong>
    	<span id="current_size">0</span>
    	<span>/</span>
    	<span id="max_size">0</span>
    	<div class="inner"><sub id="bar-inner"></sub></div>
        <span id="persent">0%</span>
        <a id="cancelUpload" href="javascript:void(0)">取消</a>
    </div>
    
    <div class="conBody">
        <#if issueList??>
          <#list issueList as issue>
             <#if issue??>
	            <#if (issue_index+1)%6=1>
		        	<div class="item">
		        </#if>
			            <a publisherIssueId="${issue.id}" publicationPubId="${issue.publicationId}" class="showBar <#if publicationId?? && (publicationId!=0)> <#if (issue.status==0) || (issue.status==2)> disabled <#elseif (issue.status==4) || (issue.status==5)>doing</#if> </#if>" href="javascript:void(0)">
			              <img name="pubReader" 
			              src="
			              <#if (issue.status==4) || (issue.status==5)> 
			              	${systemProp.staticServerUrl}/images/head145.gif 
			              <#else>
			              	${systemProp.magServerUrl}/${issue.publicationId}/110_${issue.id}.jpg
			              </#if>" />
		                  <span <#if publicationId?? && (publicationId!=0)> name="pubReader" </#if>>${issue.issueNumber}</span>
		                   <#if publicationId?? && (publicationId!=0)>
			                  <div class="bookBar">
					                  <p>
					                   <em issueId="${issue.id}">编辑</em>
					                   <#if (issue.status==0) || (issue.status==2)><em issueupid="${issue.id}">上架</em></#if>
					                   <#if issue.status==1><em issuedownid="${issue.id}">下架</em></#if>
					                   <em staticIssueId="${issue.id}">数据</em>
					                   <em issueDelId="${issue.id}" class="del" >删除</em>
					                  </p>
			                  </div>
			                </#if>
			            </a>
		        <#if (issue_index+1)%6=0>
			        	</div>
			        <#elseif issue_index+1=issueList?size>
			            </div>
		        </#if>
		      </#if>
          </#list>
        </#if>
    </div>
</div>
</#macro>