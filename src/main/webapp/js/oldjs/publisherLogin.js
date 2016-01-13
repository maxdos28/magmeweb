$(document).ready(function(){
	//tagbox
	$.jquerytagbox("#tagboxLogin",0);
	var publishLogin = $("#publishLogin");
	var formReRegister = $("#formReRegister");
	
	//getCode
	function getCode (img){
		var img = $(img);
		var src = SystemProp.appServerUrl + "/publish/authcode.action?random=" + Math.random();
		img.attr("src",src);
	}
	$("a[name='getPublisherAuthcode']").unbind('click').bind('click',function(e){
		e.preventDefault();
		getCode($(this).prev());
	});
	
	$("#reRegisterTab").unbind('click').bind('click',function(){
		$("#publishLogin").parent().removeClass("current").removeAttr("style");
		$("#formReRegister").parent().addClass("current").attr("style","display:block");
		$("#publisherLoginTab").removeClass("current");
		$(this).addClass("current");
		getCode( $("img[code]",formReRegister).eq(0) );
	});
	
	$("#publisherLoginTab").unbind('click').bind('click',function(){
		$("#formReRegister").parent().removeClass("current").removeAttr("style");
		$("#publishLogin").parent().addClass("current").attr("style","display:block");
		$("#reRegisterTab").removeClass("current");
		$("#publisherLoginTab").addClass("current");
		getCode( $("img[code]",publishLogin).eq(0) );
	});
	
	//init authcode
	getCode ( $("#publisherfirstcode").eq(0) );
	
	function publisherLoginSuccess(){
		window.location.href = SystemProp.appServerUrl+"/publish/pcenter-publisher.action?random="+Math.random();
	}
	//login
	$("#publisherLoginBtn").click(publisherLogin);
	//---------------------------------publisher_login-----------------------------------------
	function publisherLogin(){
		var userName = $("#userName",publishLogin).val();
		var password = $("#password",publishLogin).val();
		var authCode = $("#publisherauthcode",publishLogin).val();
		var callback = function(result){
			if(!result) return;
			var tipError = $(".tipsError",publishLogin);
			var data = result.data;
			var message = result.message;
			if(result.code != 200){
				if(data && data.authcode){
					message = data.authcode;
				}
				if(data && data.userName){
					message = data.userName;
				}
				tipError.html(message).show();
			}else{
				message = "登录成功!";
				tipError.text(message).show();
				setTimeout(function(){
					publisherLoginSuccess();
				},1000);
			}
		};
		$.ajax({
			url: SystemProp.appServerUrl+"/publish/front-publisher!loginJson.action",
			type: "POST",
			data: {"userName":userName,"password":password,"authcode":authCode},
			success: callback
		});
	}
	//---------------------------enter_submmit---------------------------------------------
	$(document).keyup(function(event){
		if (event.keyCode == '13') {
			event.preventDefault();
			$(".btnOS:visible").click();
	    }
	});
	
	//register	
	$("#publishReg2222Submit").click(function(){
	    var tipError = $(".tipsError",formReRegister);
		var password = $("#passwordRe",formReRegister).val();
		var password2 = $("#password2",formReRegister).val();
		
		var contactPhone1 = $("#contactPhone1",formReRegister).val();
		var contact1 = $("#contact1",formReRegister).val();
		var email = $("#email",formReRegister).val();
		var publishName = $("#publishName",formReRegister).val();
		var userName = $("#userName",formReRegister).val();
		var authCode = $("#publisherauthcode",formReRegister).val();
		
		if(!$.trim(password) || !$.trim(password2)){
			tipError.show().html("密码不能为空!");
			return;
		}else{
			tipError.hide();
		}
		if(password !== password2){
			tipError.show().html("确认密码错误!");
			return;
		}else{
			tipError.hide();
		}
		$(".loading",formReRegister).show();
		var callback2 = function(result){
			var code = result.code;
			var data = result.data;
			var message = result.message;
			if(code != 200){
				if(data && data.authcode){
					message = data.authcode;
					tipError.html(message).show();
					return;
				}
				if(data && data.userName){
					message = data.userName;
					tipError.html(message).show();
					return;
				}
				if(data && data.email){
					message = data.email;
					tipError.html(message).show();
					return;
				}
				if(data && data.password){
					message = data.password;
					tipError.html(message).show();
					return;
				}
			}else{
				tipError.show().html("注册成功,管理员审核后可以登录!");
				setTimeout(function(){
					publisherLoginSuccess();
				},1000);
			}
		}
		$.ajax({
			url: SystemProp.appServerUrl+"/publish/front-publisher!registerJson.action",
			type: "POST",
			data: {"password":password,"password2":password2,
			       "publishName":publishName,"userName":userName,
			       "email":email,"contact1":contact1,"contactPhone1":contactPhone1,"authcode":authCode},
			success: callback2,
			complete:function(){$(".loading",formReRegister).hide();}
		});
		
	});
	
	
	//check publish
	$("#userName ,#publishName,#email",formReRegister).unbind('blur').bind('blur',function(){
		var elementData = $(this).data("elementData");
		var value = $(this).val();
		if(!elementData || elementData != value){
			$(this).data("elementData",value);
			check($(this),formReRegister);
		}
	});
	function check(obj,register){
		if(!obj) return;
		var tipError = $(".tipsError",register);
		var id = obj.attr("id");
		var value = obj.val();
		if($.trim(value) == ''){
			tipError.html("用户名/杂志社名称/邮箱 不能为空！").show();
			return;
		}
		
		var userData = {};
		userData[id] = value;
		var callback = function(result){
			if(result.code == 200){
				if(id == 'userName'){
					$("#checkName",register).show();
				}else if(id == 'publishName'){
					$("#checkPublishName",register).show();
				}else{
					$("#checkEmail",register).show();
				}
				tipError.hide();
			}else{
				if(id == 'userName'){
					$("#checkName",register).hide();
				}else if(id == 'publishName'){
					$("#checkPublishName",register).hide();
				}else{
					$("#checkEmail",register).hide();
				}
				tipError.html(result.data[id]).show();
			}
		};
		$.ajax({
			url : SystemProp.appServerUrl+"/publish/front-publisher!validatePublisher.action",
			type : "POST",
			data : userData,
			success: callback
		});
	}
	
	
	
});