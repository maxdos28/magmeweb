<#macro main searchType>
<div class="header20150126 clearFix">
	<div class="outer headerTop">
    	<div class="inner">
            <a class="logo" href="${systemProp.appServerUrl}" title="麦米购-轻松全球购">麦米购 magmego</a>
            <ul class="newnav floatl">
                <li class="home"><a href="${systemProp.appServerUrl}/">发现<span>EXPLORE</span></a></li>
                <li class="store"><a href="http://www.magmego.com">商城<span>STORE</span></a></li>
                <li class="magazine"><a href="${systemProp.appServerUrl}/publish/magazine.html">杂志<span>MAGAZINE</span></a></li>
            <ul>   
            <div class="search clearFix" id="headerSearch" style="display:none">
				<input type="hidden" id="u_invite_code" value="${inviteCodeSession?default('1')}" />
            	<input type="text" tips="杂志搜索" accesskey="s" name="queryStr" <#if queryStr?? && queryStr!="">value="${queryStr}"</#if>  autofocus autocomplete="off" x-webkit-speech x-webkit-grammar="builtin:translate" color="#3a3a3a,#707070" />
                <a href="javascript:void(0)" class="png">搜索</a>
            </div> 
        </div>
    </div>
 	<div class="outer headerCategory" id="headerCategory">
        <div class="inner">
            <ul class="clearFix" >
            	<#if sortList?? && (sortList?size) gt 0>
         		<#list sortList as sort>
         		<li name="magzineHeaderLi" <#if sortName??&&sortName.equals(sort.domain)>class="current" </#if>>
                <a id="headerCategoryA" name="headerCategoryA" sortId="${sort.id}" href="${systemProp.appServerUrl}/publish/magazine.html?sortName=${sort.domain}">${sort.name}</a>
                </li>
                </#list>
                </#if>
            </ul>
        </div>
    </div>


    <div class="outer headerCategoryList" id="headerCategoryList"  style="display:none;">
        <div class="inner"></div>
        <a class="close" href="javascript:void(0)">关闭</a>
    </div>
</div>
<div class="search" style="display:none;">
		<input type="hidden" name="searchType" value="${searchType?default('Issue')}" />
	</div>
</#macro>
