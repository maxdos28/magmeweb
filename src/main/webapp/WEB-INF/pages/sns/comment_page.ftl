<#if page&gt;8 && (page/8)&gt;0>
	<#list (page-4)..pageCount as pn >
		<#if page&gt;1 && pn_index==0>
			<a href="javascript:void(0);" class='pre'>上一页</a>
		</#if>
		<#if pn_index&lt;9 >
			<a <#if pn==page>class="current"</#if> pn='${pn}' href="javascript:void(0);">${pn}</a>
		</#if>
		<#if pn_index&gt;1 && page&lt;pageCount && pn_index==(pageCount-page+4) >
			<a href="javascript:void(0);" class='next'>下一页</a>
		</#if>
	</#list>
<#else>
	<#list 1..pageCount as pn >
		<#if page&gt;1 && pn_index==0>
			<a href="javascript:void(0);" class='pre'>上一页</a>
		</#if>
		<#if pn_index&lt;9 >
			<a <#if pn==page>class="current"</#if> pn='${pn}' href="javascript:void(0);">${pn}</a>
		</#if>
		
		<#if pn_index&gt;0 && page&lt;pageCount && pn_index==(pageCount-1) >
			<a href="javascript:void(0);" class='next'>下一页</a>
		</#if>
	</#list>
</#if>
