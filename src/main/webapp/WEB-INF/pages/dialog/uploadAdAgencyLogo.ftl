<#macro main>
	<div id="uploadPublisherLogoDialog" class="popContent">
        <fieldset>
            <h6>更换头像</h6>
            <form id="editPublisherLogoForm" method="post" action="" onsubmit="return false;">
            	<input type="hidden" name="x" value="0" />
				<input type="hidden" name="y" value="0" />
				<input type="hidden" name="width" value="0" />
				<input type="hidden" name="height" value="0" />
				<input type="hidden" id="logoFileName" name="logoFileName"/>
            </form>
            
            <div class="imgArea">
            	<#if ((session_adagency.logo)??) && (session_adagency.logo!="")>                             
					<a id="logoBox" href="#">
						<span><img id="logo" name="logo" src="${systemProp.adProfileServerUrl+session_adagency.logo}" /></span>
					</a>
				<#else>
					<a id="logoBox" href="#" class="hide">
						<span><img id="logo" name="logo" src="${systemProp.staticServerUrl}/images/head150.gif" /></span>
					</a>
				</#if>
			</div>
            <div class="tCenter">
                <a id="submit" href="#" class="btnBS" >确定</a>
                <a id="cancel" href="#" class="btnWS" >取消</a>
            	<#if ((session_adagency.logo)??)&&(session_adagency.logo!="")>
            		<a id="uploadPublisherBtn" class="btnOS" href="#">
                		<span>重新上传头像</span>
                		<input id="logoFile" name="logoFile" type="file" class="inputFile" />
                	</a>
            	<#else>
            		<a id="uploadPublisherBtn" class="btnOB" href="#">
                		<span>上传头像</span>
                		<input id="logoFile" name="logoFile" type="file" class="inputFile" />
                	</a>
            	</#if>
                <span class="tipsError" id="tipsError">请选择头像</span>
            </div>
        </fieldset>
	</div>
</#macro>