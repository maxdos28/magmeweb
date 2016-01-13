<#macro main>                
                <div class="item clearFix">
                    <div id="friendList" class="inner clearFix">
                    	<#if (friendList?? && (friendList?size > 0))>
	                    	<#list friendList as friend>
		                    	<div class="fItem">
		                        	<a href="${systemProp.appServerUrl+"/user!visit.action?id="+friend.id}">
		                        		<div class="userHead">
			            				<#if ((friend.avatar)??)&&(friend.avatar!="")>
			            					<img src="${systemProp.profileServerUrl}${friend.avatar60}" />
			            				<#else>
			            					<img src="${systemProp.staticServerUrl}/images/head60.gif" />
			            				</#if>
										<sub></sub>
										</div>                        	
		                            	<strong>${friend.nickName}</strong>
		                            </a>
		                        </div>
	                        </#list>
	                    <#else>
	                    	<div class="nullInfo">您还没好友</div>
	                    </#if>
                    </div>
                </div>
</#macro>                