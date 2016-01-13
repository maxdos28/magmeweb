<html>
<head>
<title>麦米活动</title>
</head>
<body>
    <div id="personOpus" class="side490">
    	<#if eventOpusList??>
    	<#list eventOpusList as eventOpus>
    	<div class="conC conReportDetail" hasComment="true" eventOpusId="${eventOpus.id}">
        	<h2>
        		<span>${(eventOpus.createdTime)?string("yyyy-MM-dd")}</span>${eventOpus.title}
        		<em>点击展开</em>
        	</h2>
            <div class="content">
                <a class="iconDig" eventOpusId="${eventOpus.id}" href="javascript:void(0)">投票<strong>${eventOpus.voteNum}</strong></a>
                <p>${eventOpus.content}</p>
                <a class="iconDig floatr" eventOpusId="${eventOpus.id}" href="javascript:void(0)">投票<strong>${eventOpus.voteNum}</strong></a>
                <div class="comment">
                    <div class="item itemTitle">
                        <strong>评论<span>(${(eventOpus.eventCommentCount)!"0"})</span></strong>
                    </div>
                    <div class="item inputArea clearFix">
		            	<form name="eventCommentForm" method="post" action="" onsubmit="return false;">
			            	<input type="hidden" name="eventOpusId" value="${eventOpus.id}" />
			            	<input type="hidden" name="eventCode" value="20110720" />
			            	<textarea name="content" tips="还没有评论，赶快抢沙发！"></textarea>
			                <div code="getcode" class="clearFix hide">
			                	<#if !(session_user??)>
			                	<input type="text" class="in" name="userName" tips="用户名" />
			                    <input type="text" class="in" tips="密码" />
			                    <input type="password" name="password" class="in g100 hide" tips="密码" />
			                    </#if>
			                    <input code="getcode" name="authcode" type="text" class="in code" tips="验证码" />
			                    <a name="getAuthcode" href="javascript:void(0)" class="floatl"><img src="${systemProp.staticServerUrl}/images/code.gif" class="floatl" />&nbsp;&nbsp;看不清，换一个</a>
			              	</div>
			                <a href="javascript:void(0)" name="eventCommentSubmit" class="btnBS floatr" >评论</a>
			                <#if !(session_user??)>
			                <a href="#" name="toUserRegisterBtn" class="important floatl hide" >新用户注册</a>
			                </#if>
		                </form>                        
                    </div>
                    <#if (eventOpus.eventCommentList)??>
                    <#list eventOpus.eventCommentList as eventComment>
			            <div class="item clearFix">
			            	<a href="${systemProp.appServerUrl}/user!visit.action?id=${eventComment.userId}">
			                <span>${eventComment.createdTime?string("yyyy-MM-dd HH:mm:ss")}</span>
			                <div class="userHead">
			                	<#if (eventComment.avatar)??&&(eventComment.avatar!="")>
			                	<img src="${systemProp.profileServerUrl+eventComment.avatar46}" title="width:50px;" />
			                	<#else>
			                	<img src="${systemProp.staticServerUrl}/images/head46.gif" title="width:50px;" />
			                	</#if>
			                	<sub></sub>
			                </div>
			                <strong>${eventComment.nickName}</strong>
			                </a>
			                <p>${eventComment.content}</p>
			            </div>
                    </#list>
                    </#if>
                </div>
            </div>
        </div>
        </#list>
        </#if>
    </div>    

    <div class="side300">
    	<a class="btnBack" href="javascript:history.go(-1);">返回体验作品</a>
    	<!--conUserInfo-->
    	<#if actor??>
        <div class="conB conUserInfo">
        	<h2>${actor.nickName}</h2>
        	<div class="conBody">
            	<div class="userHead">
                	<a href="${systemProp.appServerUrl}/user!visit.action?id=${actor.id}">
						<#if ((actor.avatar)??) && (actor.avatar!="") >
							<img src="${systemProp.profileServerUrl}${actor.avatar}" title="${(actor.nickName)!""}"/>
						<#else>
							<img src="${systemProp.staticServerUrl}/images/head150.gif" title="${(actor.nickName)!""}"/>
						</#if>                 	
                	</a>
                </div>
                <div class="userInfo">
                	<p class="iconSex"><strong>性别：</strong><#if ((actor.gender)??)&&(actor.gender==1)>男<#elseif ((actor.gender)??)&&(actor.gender==2)>女<#else>保密</#if></p>
                	<p class="iconBirthday"><strong>生日：</strong><#if (actor.birthdate)??>${actor.birthdate?string("yyyy-MM-dd")}<#else>保密</#if></p>
                	<p class="iconCity"><strong>来自：</strong>${(actor.province)!"火星"}</p>
                	<p class="iconLevel"><strong>等级：</strong>普通用户</p>
                	<p class="iconDoc"><strong>报告：</strong>${(eventOpusList?size)!"0"}</p>
                </div>
            </div>
        </div>
        </#if>
    </div>
</body>
</html>