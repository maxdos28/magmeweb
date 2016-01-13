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
	<div class="outer headerNav">
    	<div class="inner clearFix">
            <div class="subNav">
            	<ul class="classification clearFix">
            	<#if looItemList?? && looItemList?size gt 0 >
            		<#list looItemList as cc>
            			<#if cc.parentId==0>
            			 <li class="nav${cc.id} <#if sortId?? && sortId == cc.id>current</#if>" ><a <#if cc.id==4 || cc.id==42>class="hot" </#if> <#if cc.id==249>class="go" </#if>  href="${systemProp.appServerUrl}/home/${cc.id}.html" >${cc.title}</a>
                    	</li>
                    	</#if>
            		</#list>
            	</#if>
                </ul>
            </div>
        </div>
    </div>
</div>


</#macro>
