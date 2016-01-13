<script>
$(document).ready(function(){
	function getcode(img){
		img.attr("src",SystemProp.appServerUrl+"/images/code.gif");
		var src = SystemProp.appServerUrl + "/authcode.action?random=" + new Date().getTime();
		$(img).attr("src",src);
	}
	
	$("a[name='getAuthcode']").unbind('click').live('click',function(e){
		e.preventDefault();
		var img = $(this).parent().find("img").eq(0);
		getcode(img);
	});
	
	var authcodeFlag=true;
	$("#authcode").unbind('focus').live('focus',function(e){
		if(authcodeFlag){
			$("a[name='getAuthcode']").click();
			authcodeFlag=false;
		}
	});
	
	$("#getpwd").click(function(){
		var formForgetName=$("#formForgetName");
		var email = $("#email",formForgetName).val();
		var authcode =  $("#authcode",formForgetName).val();
		
		var callback = function(result){
			if(!result) return;
			var tipsError = $(".tipsError",formForgetName);
			var message = result.message;
			var data = result.data;
			if(result.code != 200){
				if(data){
					message = data.authcode;
				}
			}else{
				message = "密码已重置，请注意邮件查收！";
			}
			tipsError.html(message).show();
		};
		$.ajax({
			url: SystemProp.appServerUrl+"/user-findpwd!doFindpwdJson.action",
			type: "POST",
			data: {"email":email,"authcode":authcode},
			success: callback
		});
	});	
	
	$("#registerButton").unbind('click').live('click',function(e){
		$("#userReg").click();
	});
});
</script>

<div class="body pageStatic pagePassword clearFix">
    <div class="conLeftMiddleRight">
    <div class="jqueryTagBox" id="tagPassword">
            <div class="ctrl">
				<div id="forgetPwdTab">忘记密码</div>
            </div>
            <div class="doorList">
                <div class="item">
                    <fieldset id="formForgetName" class="formForgetName">
                        <div>
                            <em class="title">电子邮件地址：</em>
                            <em><input id="email" type="text" class="input g200" /></em>
                        </div>
                        <div>
                            <em class="title">验证码：</em>
                            <em><input id="authcode" type="text" class="input g60" /></em>
                            <em class="code"><a name="getAuthcode" href="javascript:void(0)"><img class="code" style="background:#ccc;" src="${systemProp.staticServerUrl}/images/code.gif" />看不清？换一个</a></em>
                        </div>
                        <div>
                        	<em class="title">&nbsp;</em>
                        	<em><a id="getpwd" href="#" class="btnOS" >发送</a></em>
                        	<em class="tipsError">请填写相关信息</em>
                        </div>
                    </fieldset>
                    <div class="tips">
                    	如果您提供的电子邮箱地址不正确，或者您记不起注册时的电子邮箱地址，我们将无法找回您的会员名。您可以<a id="registerButton"  class="important" href="javascript:void(0)">点击这里</a>重新进行注册！
					</div>
                </div>
            </div>
        </div>
    </div>
</div>