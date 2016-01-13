<#macro main>
	
	<div id="uploadUserAvatarDialog" class="popContent">
        <fieldset>
            <h6>更换头像</h6>
            <form id="editAvatarForm" method="post" action="" onsubmit="return false;">
            	<input type="hidden" name="x" value="0" />
				<input type="hidden" name="y" value="0" />
				<input type="hidden" name="width" value="0" />
				<input type="hidden" name="height" value="0" />
				<input type="hidden" id="avatarFileName" name="avatarFileName"/>
            </form>
            
            <div class="imgArea">
            	<#if ((session_user.avatar)??)&&(session_user.avatar!="")>
					<a id="avatarBox" href="javascript:void(0)">
						<span><img id="avatar" name="avatar" src="${systemProp.profileServerUrl+session_user.avatar}" /></span>
					</a>
				<#else>
					<a id="avatarBox" href="javascript:void(0)" class="hide">
						<span><img id="avatar" name="avatar" src="${systemProp.staticServerUrl}/images/head150.gif" /></span>
					</a>
				</#if>
			</div>
            <div class="tCenter">
                <a id="submit" href="javascript:void(0)" class="btnBS" >确定</a>
                <a id="cancel" href="javascript:void(0)" class="btnWS" >取消</a>
            	<#if ((session_user.avatar)??)&&(session_user.avatar!="")>
            		<a id="uploadBtn" class="btnOS" href="javascript:void(0)">
                		<span>重新上传头像</span>
                		<input id="avatarFile" name="avatarFile" type="file" class="inputFile" />
                	</a>
            	<#else>
            		<a id="uploadBtn" class="btnOB" href="javascript:void(0)">
                		<span>上传头像</span>
                		<input id="avatarFile" name="avatarFile" type="file" class="inputFile" />
                	</a>
            	</#if>
                <span class="tipsError" id="tipsError">请选择头像</span>
            </div>
        </fieldset>
	</div>
</#macro>