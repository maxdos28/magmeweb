<#macro main>
<div id="categoryList" class="categoryList">
        	<a href="javascript:void(0)" class="home">推荐：</a>
        	<#if familyCategoryParentMap?? && (familyCategoryParentMap?size>0) >
        	  <#list familyCategoryParentMap?keys as key>
	           <a name="familyName" familyId="${key}" href="javascript:void(0)" <#if familyCategoryParentId?? && familyCategoryParentId==key>class="current"</#if>> ${familyCategoryParentMap[key]}</a>
	          </#list>
	        </#if>
</div>
</#macro>