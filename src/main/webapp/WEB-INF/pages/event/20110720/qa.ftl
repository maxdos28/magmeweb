<html>
<head>
<title>麦米活动</title>
</head>
<body>	
	<div class="right">
    	<a id="btnQuestion" class="btnOS floatr">我要留言</a>
    	<div class="con con7">
        	<div class="item itemQuestion clearFix">
            	<div class="userHead"><a href="javascript:void(0)"><img src="${systemProp.staticServerUrl}/images/head60.gif" /></a></div>
                <div class="content">
                    <fieldset>
                        <form id="eventQaForm" method="post" action="" onsubmit="return false;">
                        	<input type="hidden" name="eventCode" value="${eventCode}"/>
                            <div>
                                <em class="title">姓名：</em>
                                <em><input type="text" class="input g200" value="${(session_user.nickName)!"游客"}" readonly="readonly"/></em>
                            </div>
                            <div>
                                <em class="title">留言：</em>
                                <em><textarea name="content" class="input g500" /></textarea></em>
                            </div>
                            <div>
                                <em class="title"></em>
                                <em><a name="eventQaFormSubmitBtn" class="btnBS">提交</a></em>
                            </div>
                        </form>
                    </fieldset>
            	</div>
            </div>
            <#list eventQaList as eventQa>
        	<div class="item clearFix">
            	<div class="userHead">
            		<a href="${systemProp.appServerUrl}/user!visit.action?id=${eventQa.userId}">
            			<#if (eventQa.avatar60)??&&(eventQa.avatar60!="")>
            			<img src="${systemProp.profileServerUrl+eventQa.avatar60}" />
						<#else>
						<img src="${systemProp.staticServerUrl}/images/head60.gif" />
						</#if>            			
            		</a>
            	</div>
                <div class="content">
	            	<strong><a href="${systemProp.appServerUrl}/user!visit.action?id=${eventQa.userId}">${eventQa.nickName}</a></strong>
                    <span>${eventQa.createdTime?string("yyyy-MM-dd")}</span>
                    <p><strong>问题：</strong>${eventQa.content}</p>
                    <P class="answer">
                    	<#if (eventQa.relation)??>
                    	<strong>回答：</strong>${eventQa.relation.content}
                    	<#else>
                    	<strong>回答：</strong>等待麦米网答复
                    	</#if>
                    </P>
            	</div>
            </div>
            </#list>
        </div>
    </div>
</body>
</html>