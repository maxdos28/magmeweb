<#macro main>
        <div class="conB conFriend">
        	<h2>我的米友<span>(${friendCount})</span></h2>
            <div class="conBody ">
            	<#if (friendCount>0)>
	            	<ul id="friendList">
	            		<#list friendList as friend>
	            		<li>
	            			<a href="${systemProp.appServerUrl+"/user!visit.action?id="+friend.id}">
	            				<#if (friend.avatar)??&&friend.avatar!="">
	            					<img src="${systemProp.profileServerUrl+friend.avatar60}" />
	            				<#else>
	            					<img src="${systemProp.staticServerUrl}/images/head60.gif" />
	            				</#if>
	            				<span>${friend.nickName}</span>
	            				<sup></sup>
	            			</a>
	            		</li>
	            		</#list>
	                </ul>
	                <div class="tRight <#if (friendList?size<=8)>hide</#if>">
	                	<a href="javascript:void(0)" name="moreFriendList" listBegin="8" listSize="8" listCount="${friendCount!"0"}" userId="${session_user.id}">更多</a>
	                	<a href="${systemProp.appServerUrl+"/user-friend!sns.action?pos=0"}">全部</a>
	                </div>
            	<#else>
                	<div class="nullInfo">目前还没有好友</div>
                </#if>
            </div>
        </div>
</#macro>