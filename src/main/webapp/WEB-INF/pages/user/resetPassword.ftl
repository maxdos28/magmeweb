<script>
$(document).ready(function(){
		$("#submit").unbind('click').live("click",function(){
			var formSetPwd=$("#formSetPwd");
			var tipsError = $(".tipsError",formSetPwd);
			var password = $("#password",formSetPwd).val();
			var password2 = $("#password2",formSetPwd).val();
			var keycode = $("#keycode",formSetPwd).val();
			
			if(!$.trim(password) || !$.trim(password2)){
				tipsError.show().html("密码不能为空");
				return;
			}
			
			var callback = function(result){
				var code = result.code;
				var data = result.data;
				var message = result.message;
				if(code != 200){
					if(data.password){
						$(".tipsError",formSetPwd).show().html(data.password);
						$("#password",formSetPwd).val("");
						$("#password2",formSetPwd).val("");
					}else{
						$(".tipsError",formSetPwd).show().html(data.keycode);
					}
				}else{
					alert("重置密码成功,请使用新密码登录麦米网",function(){
						$("#loginBtn").click();
					});
				}
			}
			$.ajax({
				url: SystemProp.appServerUrl+"/user-findpwd!resetPasswordJson.action",
				type: "POST",
				data: {"password":password,"password2":password2,"keycode":keycode},
				success: callback
			});
		});
});
</script>

<div class="body pageStatic pagePassword clearFix">
    <div class="conLeftMiddleRight">
    <div class="jqueryTagBox" id="tagPassword">
            <div class="ctrl">
				<div>重设密码</div>
            </div>
            <div class="doorList">
                <div class="item">
                    <fieldset id="formSetPwd" class="formSetPwd">
                    	<div style="display:none;">
                        	<em class="title">识别码：</em>
                        	<em><input id="keycode" type="text" name="keycode" value="${keycode}" class="input g200" /></em>
                        </div>                    
                    	<div>
                        	<em class="title">新密码：</em>
                        	<em><input id="password" type="password" class="input g200" /></em>
                        </div>
                    	<div>
                        	<em class="title">确认密码：</em>
                        	<em><input id="password2" type="password" class="input g200" /></em>
                        </div>
                    	<div>
                        	<em class="title">&nbsp;</em>
                        	<em><a id="submit" href="javascript:void(0)" class="btnOS" >修改</a></em>
                        	<em class="tipsError">请填写相关信息</em>
                        </div>
                    </fieldset>
                    <div class="tips">
                    	请重新设置你的密码，并牢记！
					</div>
                </div>
            </div>
        </div>
    </div>
</div>