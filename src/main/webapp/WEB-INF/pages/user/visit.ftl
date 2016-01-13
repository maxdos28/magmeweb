<#import "../user/conInteractionArea.ftl" as conInteractionArea>

<html>
<head>
<title>用户空间</title>
</head>
<body>
<div class="body clearFix">
	<script type="text/javascript" src="${systemProp.staticServerUrl}/js/visit.js"></script>
    <!--sideLeft-->
    <div class="sideLeft">
    	<!--conUserInfo-->
    	<div class="conB conUserInfo">
    	  <h2>${(visitUser.nickName)!""}</h2>
    	  <div class="conBody">
    	    <div class="userHead">
				<#if ((visitUser.avatar)??) && (visitUser.avatar!="") >
					<img src="${systemProp.profileServerUrl}${visitUser.avatar}" title="${(visitUser.nickName)!""}"/>
				<#else>
					<img src="${systemProp.staticServerUrl}/images/head150.gif" title="${(visitUser.nickName)!""}"/>
				</#if>    	     
    	   	</div>
    	    <div userId="${visitUser.id}" class="control clearFix"> 
    	    	<#if isFriend=="true">
    	    	<a name="deleteFriend" class="iconAddFriend" href="javascript:void(0)">取消好友</a>
                <#else>
                <a name="addFriend" class="iconAddFriend" href="javascript:void(0)">加为好友</a>
                </#if>
                <#if isFollow=="true">
                <a name="deleteFollow" class="iconConcern" href="javascript:void(0)">取消关注</a> 
                <#else>
                <a name="addFollow" class="iconConcern" href="javascript:void(0)">关注此人</a>
                </#if>
    	   	</div>
    	    <div class="userInfo">
	        	<p class="iconSex"><strong>性别：</strong><#if ((visitUser.gender)??)&&(visitUser.gender==1)>男<#elseif ((visitUser.gender)??)&&(visitUser.gender==2)>女<#else>保密</#if></p>
	        	<p class="iconBirthday"><strong>生日：</strong><#if (visitUser.birthdate)??>${visitUser.birthdate?string("yyyy-MM-dd")}<#else>保密</#if></p>
	        	<p class="iconCity"><strong>来自：</strong>${(visitUser.province)!"火星"}</p>
	        	<p class="iconLevel"><strong>等级：</strong>普通用户</p>    	      
  	      	</div>
  	    </div>
  	  </div>
    </div>
    <!--sideMiddle-->
    <div class="sideMiddle">
    	<!--conMySaveMgz-->
    	<div class="conB conMySaveMgz" id="conMySaveMgz">
        	<h2>Ta的订阅</h2>
            <div class="conBody clearFix">
            	<a class="btnLR turnLeft" href="javascript:void(0)"></a>
                <div class="outer">
                	<#if (subscribeList?? && (subscribeList?size > 0))>
	                	<div class="inner">
	                		<#list subscribeList as subscribe>
	                			<#if (subscribe.publication.lastIssue.id)??>
	                				<a target="_magme" class="item showBar" href="${systemProp.appServerUrl+"/publish/mag-read.action?id="+subscribe.publication.lastIssue.id}">
		                				<img src="${systemProp.magServerUrl+"/"+subscribe.publication.id+"/100_"+subscribe.publication.lastIssue.id+".jpg"}" />
		                				<span>${subscribe.publication.lastIssue.issueNumber}</span>
		                				<div class="bookBar">
		                					<p issueId="${subscribe.publication.lastIssue.id}">
			                					<em class='save' title='订阅' name="subscribe" >${subscribe.publication.lastIssue.subscribeNum}</em>
				                        		<em class='fav' title='收藏' name="collection" >${subscribe.publication.lastIssue.favoriteNum}</em>
		                					</p>
		                				</div>
	                				</a>
	                			</#if>
	                		</#list>
	                    </div>
	                <#else>
		                <div class="nullInfo">
		                	暂时没有杂志订阅
		                </div>
                	</#if>
                </div>
            	<a class="btnLR turnRight" href="javascript:void(0)"></a>
            </div>
        </div>
    	<!--conInteractionArea-->
		<@conInteractionArea.main moreNewsFeed="visitMoreNewsFeed"/>  		
    </div>
    <div class="sideRight">
    	<!--conFriend-->
        <div class="conB conFriend">
        	<h2>Ta的米友<span>(${friendCount})</span></h2>
            <div class="conBody">
            	<#if (friendCount>0)>
	            	<ul id="friendList">
	                	<#list friendList as friend>
	            		<li>
	            			<a href="${systemProp.appServerUrl+"/user!visit.action?id="+friend.id}">
	            				<#if ((friend.avatar)??)&&(friend.avatar!="")>
	            					<img src="${systemProp.profileServerUrl}${friend.avatar60}" />
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
	                	<a name="moreFriendList" listBegin="8" listSize="8" userId="${visitUser.id}" href="javascript:void(0)">更多</a>
	                </div>
	            <#else>
	            	<div class="nullInfo">
	                	目前还没有好友
	                </div>
	            </#if>
            </div>
        </div>
    	<!--conConcern-->
        <div class="conB conConcern">
        	<h2>Ta的关注<span>(${followCount})</span></h2>
            <div class="conBody">
            	<#if (followCount>0)>
	            	<ul id="followList">
	            		<#list followList as follow>
	            		<li>
	            			<a href="${systemProp.appServerUrl+"/user!visit.action?id="+follow.id}">
	            				<#if ((follow.avatar)??)&&(follow.avatar!="")>
	            					<img src="${systemProp.profileServerUrl}${follow.avatar60}" />
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
	                	<a name="moreFollowList" listBegin="8" listSize="8" userId="${visitUser.id}" href="javascript:void(0)">更多</a>
	                </div>
	            <#else>
	            	<div class="nullInfo">
	                	目前还没有关注
	                </div>
	            </#if>
            </div>
        </div>
    </div>
</div>
</div>
</body>
</html>