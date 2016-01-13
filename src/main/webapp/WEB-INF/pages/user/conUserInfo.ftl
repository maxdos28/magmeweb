<#macro main>
		<div class="conB conUserInfo">
        	<h2 id="user_nickName">${(session_user.nickName)!"麦米哥"}</h2>
        	<div class="conBody">
            	<div class="userHead">
    				<#if ((session_user.avatar)??)&&(session_user.avatar!="")>
    					<img id="userAvatar" src="${systemProp.profileServerUrl+session_user.avatar}" title="${(session_user.nickName)!"麦米哥"}" />
    				<#else>
    					<img id="userAvatar" src="${systemProp.staticServerUrl}/images/head150.gif" title="${(session_user.nickName)!"麦米哥"}" />
    				</#if> 
                </div>
                <div class="control clearFix">
                    <a id="editUserInfo" class="iconEdit" href="javascript:void(0)">编辑账号</a>
                    <a id="changeAvatar" class="iconChange" href="javascript:void(0)">更换头像</a>
                </div>
                <div id="userInfo" class="userInfo">
                	<p class="iconSex">
                		<strong>性别：</strong>
                		<span id="user_gender">
                			<#if ((session_user.gender)??)&&(session_user.gender==1)>
                			男
                			<#elseif ((session_user.gender)??)&&(session_user.gender==2)>
                			女
                			<#else>保密</#if>
                		</span>
                	</p>
                	<p class="iconBirthday">
                		<strong>生日：</strong>
                		<span id="user_birthday">
	                		<#if (session_user.birthdate)??>
	                			${session_user.birthdate?string("yyyy-MM-dd")}
	                		<#else>保密</#if>
                		</span>
                	</p>
                	<p class="iconCity">
                		<strong>来自：</strong>
                		<span id="user_iconCity">
                			${(session_user.province)!"火星"}
                		</span>
                	</p>
                	<p class="iconLevel">
                		<strong>等级：</strong>
	                	<span id="user_iconLevel">
	                		普通用户
	                	</span>
                	</p>
                </div>                                
            </div>
        </div>
</#macro>