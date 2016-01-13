<#macro main>
        <div class="conB conConcern">
        	<h2>我的关注<span>(${followCount})</span></h2>
            <div class="conBody">
            	<#if (followCount>0)>
	            	<ul id="followList">
	            		<#list followList as follow>
	            		<li>
	            			<a href="${systemProp.appServerUrl+"/user!visit.action?id="+follow.id}">
	            				<#if (follow.avatar)??&&follow.avatar!="">
	            					<img src="${systemProp.profileServerUrl+follow.avatar60}" />
	            				<#else>
	            					<img src="${systemProp.staticServerUrl}/images/head60.gif" />
	            				</#if>
	            				<span>${follow.nickName}</span>
	            				<sup></sup>
	            			</a>
	            		</li>
	            		</#list>            	
	                </ul>
	                <div class="tRight <#if (followList?size<=8)>hide</#if>">
	                	<a href="javascript:void(0)" name="moreFollowList" listBegin="8" listSize="8" listCount="${followCount!"0"}" userId="${session_user.id}">更多</a>
	                	<a href="${systemProp.appServerUrl+"/user-friend!sns.action?pos=1"}">全部</a>
	                </div>
            	<#else>
	            	<div class="nullInfo">目前还没有关注 </div>
	            </#if>
            </div>
        </div>
</#macro>