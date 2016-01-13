<#macro main>

<div class="header">
	<a class="logo" href="${systemProp.appServerUrl}/mobile/index.html">Magme</a>
	<div class="nav" href="javascript:void(0)">
    	<ul class="clearFix">
        	<li class="l1"><a <#if sortId?? && sortId=1>class="current"</#if> href="${systemProp.appServerUrl}/mobile/index.html?sortId=1"><span>汽车旅游</span></a></li>
        	<li class="l2"><a <#if sortId?? && sortId=2>class="current"</#if> href="${systemProp.appServerUrl}/mobile/index.html?sortId=2"><span>商业财经</span></a></li>
        	<li class="l3"><a <#if sortId?? && sortId=3>class="current"</#if> href="${systemProp.appServerUrl}/mobile/index.html?sortId=3"><span>时尚娱乐</span></a></li>
        	<li class="l4"><a <#if sortId?? && sortId=4>class="current"</#if> href="${systemProp.appServerUrl}/mobile/index.html?sortId=4"><span>人文情感</span></a></li>
        	<li class="l5"><a <#if sortId?? && sortId=5>class="current"</#if> href="${systemProp.appServerUrl}/mobile/index.html?sortId=5"><span>数码科技</span></a></li>
        </ul>
    </div>
</div>
</#macro>