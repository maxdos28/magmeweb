<#macro main>
                <div class="item clearFix">
                    <div class="inner clearFix">
                    	<#if (waitFriendList?? && (waitFriendList?size > 0))>
                    		<#list waitFriendList as friend>
		                    	<div userId="${friend.id}" class="fItem">
		                        	<a href="${systemProp.appServerUrl+"/user!visit.action?id="+friend.id}">
		                        		<div class="userHead">
			            				<#if ((friend.avatar)??)&&(friend.avatar!="")>
			            					<img name="avatar" src="${systemProp.profileServerUrl}${friend.avatar60}" />
			            				<#else>
			            					<img name="avatar" src="${systemProp.staticServerUrl}/images/head60.gif" />
			            				</#if>
										<sub></sub>
										</div>                        	
		                            	<strong name="nickName">${friend.nickName}</strong>
		                            </a>
		                            <div class="wait">
		                            	<a name="agreeFriend" href="javascript:void(0)">接受</a>
		                            	<a name="refuseFriend" href="javascript:void(0)">忽略</a>
		                            </div>
                        		</div>
                        	</#list>
                        <#else>
	                    	<div class="nullInfo">没有待加为好友</div>
	                    </#if>
                    </div>
                </div>
</#macro>