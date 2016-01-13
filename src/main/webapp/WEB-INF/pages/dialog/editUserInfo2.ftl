<#macro main>
	<div id="userInfoDialog2" class="popContent popRegister" style="display:none;">
	    <div class="content">
	    	<fieldset>
	            <form id="editForm" method="post" action="" onsubmit="return false;">
			    <div>
				<em class="title">昵称</em>
				<em><input id="nickName" name="nickName" value="${(session_user.nickName)!""}" class="input g170" type="text" /></em>
			    </div>
			    <div>
				<em class="title">邮箱</em>
				<em><input id="email" name="email" class="input g170" type="text" />*</em>
			    </div>
			    <div>
				<em class="title"></em>
				<em ><a id="submit2" href="#" class="btnOS" >确定</a></em>
				<em ><a id="cancel2" href="#" class="btnBS" >取消</a></em>
			    </div>
	            </form>
	        </fieldset>
	    </div>
	</div>
</#macro>