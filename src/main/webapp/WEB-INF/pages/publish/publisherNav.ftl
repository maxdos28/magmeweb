<#macro main menuId>

<#import "../components/searchForm.ftl" as search>
	<div class="topBar clearFix">
	        <ul class="subNav">
	            <li id="publisherHome" <#if menuId=="publisherHome"> class="current" </#if> >
		            <a href="/publish/publisher-home.action?publisherId=${(publisher.id)?default('')}">米商空间</a></li>
		             <li id="publisherPic"<#if menuId=="publisherPic"> class="current" </#if> ><a href="/publish/publisher-pic.action?publisherId=${(publisher.id)?default('')}">米商的图片墙
		            </a>
	            </li>
	        </ul>
	</div>
</#macro>