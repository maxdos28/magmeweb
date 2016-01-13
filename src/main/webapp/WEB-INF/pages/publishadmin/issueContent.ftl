<#macro main>
<table class="table" width="100%" border="0" cellspacing="0" cellpadding="0">
  <thead>
	  <tr>
	    <td width="100">期刊名称</td>
	    <td>期刊描述</td>
	    <td width="90">编辑期刊内容</td>
	    <td width="90">状态</td>
	    <td width="90">期刊操作 </td>
	    <td width="90">删除期刊</td>
	    <td width="70">重新转换</td>
	    <td width="70">文字录入</td>
	  </tr>
  </thead>
  <tbody>
   <#if issueList?? && (issueList?size>0)>
      <#list issueList as issue>
			  <tr>
			    <td>
			    <#if issue.status==0 || issue.status==1 || issue.status==2>
				     <a href="${systemProp.appServerUrl}/publish/mag-read!pubReader.action?id=${issue.id}" >
				         ${issue.publicationName}${issue.issueNumber?default('')}
				     </a>
			    <#else>
			             ${issue.publicationName}${issue.issueNumber?default('')}
			    </#if>
			    </td>
			    <td>${issue.description?default('')}</td>
			    <td><a href="javascript:void(0)" issueeditid="${issue.id}">编辑</a></td>
			    <td><#if issue.status==0>待审核<#elseif issue.status==1>上架<#elseif issue.status==2>下架<#elseif issue.status==4>待处理<#elseif issue.status==5>处理中<#elseif issue.status==6>处理失败<#elseif issue.status==7>重新 转换中</#if></td>
			    <td>
			      <#if (issue.status==0) || (issue.status==2)>
			          <a href="javascript:void(0)" issueupid="${issue.id}">上架</a>
			        <#elseif issue.status==1>
			          <a href="javascript:void(0)" issuedownid="${issue.id}">下架</a>
			      </#if>
			    </td>
			    <td>
			    <#if issue.status!=4 && issue.status!=5 && issue.status!=7>
			    	<a href="javascript:void(0)" issueDelId="${issue.id}">删除</a>
			    </#if>
			    </td>
			    <td>
			    <#if issue.status==0 || issue.status==6>
			    	<a href="javascript:void(0)" issueRetransId="${issue.id}">重新转换</a>
			    </#if>
			    </td>
				<td><a href="javascript:void(0)" textPageIssueId="${issue.id}">文字录入</a></td>			    
			  </tr>
	 </#list>
    </#if>
  </tbody>
</table>
</#macro>