$(document).ready(function(){
	var formForgetPwd = $("#formForgetPwd");
	var formReRegister = $("#formReRegister");
	
	$(document).keyup(function(event){
		if (event.keyCode == '13') {
			event.preventDefault();
			$(".btnOS:visible").click();
	    }
	});
	//findpwd.ftl页面的处理
	if(formForgetPwd.length != 0){
	
		var src = SystemProp.appServerUrl + "/authcode.action?random=" ;
		
		$("#forgetPwdTab").click(function(){
			var code = $(".code",formForgetPwd);
			code.attr("src",src + Math.random());
		});
		$("#reRegisterTab").click(function(){
			$(".code",formReRegister).attr("src",src + Math.random());
		});
		
		$("#registerButton").click(function(){$("#reRegisterTab").click();});
		
		//刷新启动时页面验证码，防止和head的验证码冲突
		$(".code",formForgetPwd).attr("src",src + Math.random());
		$("#getpwd").click(function(){
			var email = $("#email",formForgetPwd).val();
			var authcode =  $("#authcode",formForgetPwd).val();
			
			var callback = function(result){
				if(!result) return;
				var tipsError = $(".tipsError",formForgetPwd);
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
		
		$("#userName",formReRegister).blur(function(){checkNameOrEmail($(this),formReRegister);});
		$("#email",formReRegister).blur(function(){checkNameOrEmail($(this),formReRegister);});
		$("#registerButton").click(function(e){
			e.preventDefault();
			$("#registerBtn").click();
		});
		$("#submit",formReRegister).data("isSubmit",true).click(function(){
			registerSubmit( $(this),formReRegister );
		});
	//resetpwd
	}else{
		var formSetPwd = $("#formSetPwd");
		var tipsError = $(".tipsError",formSetPwd);
		
		$("#password2",formSetPwd).blur(function(){
			var password = $("#password",formSetPwd).val();
			var password2 = $(this).val();
			if(password !== password2){
				tipsError.show().html("确认密码错误！");
			}else{
				tipsError.hide();
			}
			return;
		});
		
		$("#submit",formSetPwd).unbind('click').click(function(){
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
					if(data.passwrod){
						tipsError.show().html(data.passwrod);
					}else{
						tipsError.show().html(data.keycode);
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
	}
	
	$("a[name='getAuthcode']").unbind('click').live('click',function(e){
		e.preventDefault();
		var img = $(this).parent().find("img").eq(0);
		getcode(img);
	});
	
	function clearCode(){
		$("img[code='getcode']").attr("src",SystemProp.appServerUrl+"/images/code.gif");
	}
	
	function getcode(img){
		clearCode();
		var src = SystemProp.appServerUrl + "/authcode.action?random=" + new Date().getTime();
		$(img).attr("src",src);
	}
	
});