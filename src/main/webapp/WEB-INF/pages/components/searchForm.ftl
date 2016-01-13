<#macro main type tip>

					<div class="search">
						<input type="hidden" name="searchType" value="${type}" />
						<input type="text" name="queryStr" <#if queryStr?? && queryStr!="">value="${queryStr}"<#else>tips="杂志,图片,标签,用户"</#if>  class="text" />
						<a class="btn"></a>
						<!--  <input type="submit" class="btn" value="" />-->
					</div>
			
</#macro>