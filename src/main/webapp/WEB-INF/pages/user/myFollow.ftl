<#macro main>
                <div class="item clearFix">
                    <div class="inner clearFix">
                   		<#if (followList?? && (followList?size > 0))>
	                    	<#list followList as follow>
		                    	<div class="fItem">
		                        	<a href="${systemProp.appServerUrl+"/user!visit.action?id="+follow.id}">
		                        		<div class="userHead">
			            				<#if ((follow.avatar)??)&&(follow.avatar!="")>
			            					<img src="${systemProp.profileServerUrl}${follow.avatar60}" />
			            				<#else>
			            					<img src="${systemProp.staticServerUrl}/images/head60.gif" />
			            				</#if>
										<sub></sub>
										</div>	            				
		                            	<strong>${follow.nickName}</strong>
		                            </a>
		                        </div>
	                        </#list>
	                    <#else>
	                    	<div class="nullInfo">您还没关注</div>
	                    </#if>
                    </div>
                </div>
</#macro>