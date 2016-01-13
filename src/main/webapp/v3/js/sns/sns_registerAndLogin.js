;$(function(){
	var _url = location.href;
	
	var _type = _url.substr(_url.lastIndexOf("#")+1 , _url.length);
	if (_type == "register") {
		$(".regCon").fadeIn();
		$(".loginCon").hide();
		$(".regConStep2").hide();
		$(".quickLinks>.a2").html("登录");
	} else {
		$(".regCon").hide();
		$(".loginCon").fadeIn();
		$(".regConStep2").hide();
		$(".quickLinks>.a2").html("注册");
	}

//	toRegStep2();

	$(".hotUser .item").slice(1,$(".hotUser .item").length).bind("click", onUserClick);
	function onUserClick() {
		if ( $(this).hasClass("itemCurrent") ) {
			$(this).removeClass("itemCurrent");
		} else {
			$(this).addClass("itemCurrent");
		}
	}
	_url = _url.substr(0, _url.lastIndexOf("#"));
	
	$(".quickLinks>.a2").bind("click", changeType);
	function changeType() {
		if ($(this).html() == "登录") {
			$(".regCon").fadeOut();
			$(".loginCon").delay(200).fadeIn();
			$(this).html("注册");
			location.href = _url + "#login";
		} else {
			$("#errorMsg").html("").hide();
			$(".loginCon").fadeOut();
			$(".regCon").delay(200).fadeIn();
			$(this).html("登录");
			location.href = _url + "#register";
		}
	}
	
	$("fieldset input[type = text],fieldset input[type = password]").bind("focus",onFocus).bind("blur",onFocus);
	function onFocus(e) {
		if (e.type == "focus") {
			$(this).css({filter: 'alpha(opacity=100)','-moz-opacity': 1,opacity: 1});
		} else {
			if ($(this).attr("type") == "password") {
				$(this).prev().css({filter: 'alpha(opacity=80)','-moz-opacity': 0.8,opacity: 0.8});
			} else {
				$(this).css({filter: 'alpha(opacity=80)','-moz-opacity': 0.8,opacity: 0.8});
			}
		}
	}
	$("#submitRigister",$(".regCon")).data("isSubmit",true);
	$("#submitRigister",$(".regCon")).click(function(){
		submitRegister($(this), $(".regCon"));
	});
	$("#enterM1",$(".regConStep2")).click(function(){
		var ids = "";
		$("a.itemCurrent",$(".regConStep2")).each(function(){
			if(ids.length > 0) ids +="_";
			ids += $(this).attr("userid");
		});
		$.ajax({
			url : SystemProp.appServerUrl+"/sns/user-follow!addFollowJsons.action",
			type : "post",
			data : {"ids" : ids},
			dataType : 'json',
			success : function (rs){
				var url = SystemProp.appServerUrl;
				if(!url) url = "http://www.magme.com";
				document.location.href = url + "/sns/sns-index.action";
			}
		});
	});
	
	function getObj(){
		var user = {};
		user.userName = $.trim($("#userName").val());
		user.email = $.trim($("#email").val());
		user.password = $.trim($("#password").val());
		user.password2 = $.trim($("#password2").val());
		user.inviteCode = $.trim($("#inviteCode").val());
		if(!user.inviteCode || user.inviteCode == '邀请码')
			user.inviteCode = null;
		return user;
	}
	
	$("#inviteCode").live("blur",function(){
		var invite = $.trim($(this).val());
		if(invite == '邀请码')
				invite='';
		$("#inviteCode").attr("style", "border: 1px solid " + (invite ? ";" : "red;"));
		if(invite){
			reg_inviteCode(invite);
		} else {
			$("#errorMsg").html('需输入邀请码才能访问M1频道').show();
		}
	});

	function reg_inviteCode(v, callback){
		if(v){
			$.ajax({
				url : SystemProp.appServerUrl+"/sns/sns-index!ckInvite.action",
				type : "post",
				data : {"inviteCode":v},
				dataType : 'json',
				success : function (rs){
					var code = rs.code;
					if(code == 200){
						var num = rs.data.num;
						var msg = "M1频道邀请码（不可用或不存在）";
						if(num > 0){
							msg = 'M1频道邀请码（可以使用）';
						}
						$("#errorMsg").html(msg).show();
						$("#inviteCode").attr("style", "border: 1px solid " + (num > 0 ? ";" : "red;"));
						if(num > 0 && callback){
							callback();
						}
					}
				}
			});
		}
	}
	
	//注册提交按钮----------------------------------------------------------
	var reg_invite_flag = false;
	function submitRegister (obj,register){
		reg_invite_flag = false;
		var isOk = obj.data("isSubmit");
		if(!!isOk){
			obj.data("isSubmit",false);
			valid(function(result){//检验输入的基本信息是否合法
				obj.data("isSubmit",true);
				if(result.code == 200){
					$("#errorMsg").html('').hide();
					var user = getObj();
					if(user.inviteCode){//验证码
						reg_inviteCode(user.inviteCode, function(){
							reg_invite_flag = true; 
							doSubmitRegister(user, register);//注册
						});
					} else {
						doSubmitRegister(user, register);//注册
					}
				}
			});
		}
	}
	
	
	function doSubmitRegister(user, register){
		var tipError = $("#errorMsg");
		tipError.hide();
		var callback = function(result){
			var message = result.message;
			var code = result.code;
			var data = result.data;
			if(data.userName){
				message = data.userName;
				tipError.html(message).show();
			} else if(data.email){
				message = data.email;
				tipError.html(message).show();
			} else if(data.password){
				message = data.password;
				tipError.html(message).show();
			} else if(code == 200) {
				tipError.html("注册成功").show();
				deleteCookie("magemecnUserName");
				deleteCookie("magemecnPassword");
				setTimeout(function(){
					//if(reg_invite_flag){
					//	if(result.data.invite){
							toRegStep2();
							return;
						//}
					//}
					//turn to login page
					//var url = SystemProp.appServerUrl;
					//if(!url) url = "http://www.magme.com";
					//document.location.href = url + "/sns/sns-index!invite.action";
				},1000);
			}
		};
		
		$.ajax({
			url : SystemProp.appServerUrl+"/sns/m1-register-and-login!registerJson.action",
			type : "POST",
			data : user,
			dataType : 'json',
			success: callback
		});
	}

	function valid(callback){
		var user = getObj();
		$.ajax({
			url : SystemProp.appServerUrl + "/user!validateUserJson.action",
			type : "POST",
			dataType:'json',
			data : user,
			success: function(result){
				var message = result.message;
				var tipError = $("#errorMsg");
				var code = result.code;
				var data = result.data;
				if(data){
					if(data.userName){
						message = data.userName;
						tipError.html(message).show();
					} else if(data.email){
						message = data.email;
						tipError.html(message).show();
					} else if(data.password){
						message = data.password;
						tipError.html(message).show();
					}
				}
				if(callback != null){
					callback(result);
				}
			}
		});
	}
	//$(document).click(toRegStep2);
	function toRegStep2() {
		$(".regCon").fadeOut();
		$(".loginCon").fadeOut();
		$(".regConStep2").delay(200).fadeIn(200,function(){$(".scroll-pane").jScrollPane()});
		$(".quickLinks").fadeOut();
		$(".footer>.inner").fadeOut();
	}
	

	$("#login_inviteCode").live("blur",function(){
		var invite= $(this).val();
		if(invite=='邀请码')
				invite='';
		if(invite==''){
			$("#login_inviteCode").attr("style","border: 1px solid red;");
			$(".loginCon").find(".error").html('需输入邀请码才能发布M1频道作品').show();
		}
		ck_inviteCode(invite);
	});
	
	$("#login_userName").live("blur",function(){
		if($(this).val()==''){
			$("#input_invite_code").hide();
			$(".loginCon").find(".error").hide();
		}
		//ckUserInvite();
	});
	var ckUserInviteFlag=false;
	function ckUserInvite(){
		var uname= $.trim($("#login_userName").val());
		if(uname=='用户名 / 邮箱')
				uname='';
		if(uname!=''){
			$.ajax({
				url : SystemProp.appServerUrl+"/sns/sns-index!ckUsernameInvite.action",
				type : "post",
				data : {"userName":uname},
				dataType : 'json',
				success : function (rs){
					var code = rs.code;
					if(code == 200){
						var num =rs.data.res;
						if(num==0){
							ckUserInviteFlag=true;
							$("#input_invite_code").hide();
						}else{
							ckUserInviteFlag=false;
							$("#input_invite_code").show();
						}
					}
				}
			});
		}
	}
	$("#login_userName,#login_password").live("keyup",function(event){
		if (event.keyCode == '13') {
		     event.preventDefault();
		     $("#sns_login").click();
		   }
	});
	var login_flag=true,invate_flag=false;;
	$("#sns_login").live("click",function(){
		if(login_flag){
			login_flag=false;
			var user = {};
			user.userName = $.trim($("#login_userName").val());
			user.password = $.trim($("#login_password").val());
			if(user.userName=='用户名 / 邮箱')
				user.userName='';
			if(user.password=='密码')
				user.password='';
			if(user.userName=='' || user.password==''){
				$(".loginCon").find(".error").html("用户和密码不能为空！").show();
				login_flag=true;
				return;
			}else{$(".loginCon").find(".error").hide();}
			var invite = $.trim($("#login_inviteCode").val());
			if(invite=='邀请码')
				invite='';
			var callback  = function(result){
				if(!result) return;
				var code = result.code;
				var message = result.message;
				if(code === 200){
					deleteCookie("magemecnUserName");
					deleteCookie("magemecnPassword");
					message = "登录成功！";
					/*if(ckUserInviteFlag){
						window.location.href= SystemProp.appServerUrl+"/sns/sns-index!invite.action";
					}else{
						if(invate_flag){
							confirmInvite(invite);
						}else{
							if(confirm("登录成功！进入M1频道需验证M1频道邀请码"))
						    {
						        window.location.href= SystemProp.appServerUrl+"/sns/sns-index!invite.action";
						    }else{
						    	window.location.href= SystemProp.appServerUrl+"/sns/user-index.action";
						    }
						}
					}*/
					
					window.location.href= SystemProp.appServerUrl+"/sns/u"+result.data.user.id+"/";
				}
				login_flag=true;
				$(".loginCon").find(".error").html(message).show();
			};
			$.ajax({
				url : SystemProp.appServerUrl+"/user!loginJson.action",
				type : "POST",
				dataType:'json',
				data : user,
				success: callback
			});
		}
	});
/*	function ck_user_invite(){
		$.ajax({
			url : SystemProp.appServerUrl+"/sns/sns-index!checked.action",
			type : "post",
			data : {},
			dataType : 'json',
			success : function (rs){
				var code = rs.code;
				if(code == 200){
					var num =rs.data.num;
					if(num>0){
						return true;
					}else{
						return false;
					}
				}
			}
		});
	}*/
	function confirmInvite(v){
		$.ajax({
				url : SystemProp.appServerUrl+"/sns/sns-index!confirm.action",
				type : "post",
				data : {"inviteCode":v},
				dataType : 'json',
				success : function (rs){
					var code = rs.code;
					if(code == 200){
						toRegStep2();
						//window.location.href= SystemProp.appServerUrl+"/sns/user-index.action";
					}else{
						$("#login_inviteCode").attr("style","border: 1px solid red;");
						$(".loginCon").find(".error").html('M1频道邀请码（不可用或不存在）').show();
						invate_flag=false;
					}
				}
			});
	}
	
	function ck_inviteCode(v){
		if(v!=''){
			$.ajax({
				url : SystemProp.appServerUrl+"/sns/sns-index!ckInvite.action",
				type : "post",
				data : {"inviteCode":v},
				dataType : 'json',
				success : function (rs){
					var code = rs.code;
					if(code == 200){
						var num =rs.data.num;
						if(num>0){
							invate_flag=true;
							$(".loginCon").find(".error").html('M1频道邀请码（可以使用）').show();
							$("#login_inviteCode").attr("style","border: 1px solid none;");
						}else{
							invate_flag=false;
							$(".loginCon").find(".error").html('M1频道邀请码（不可用或不存在）').show();
							$("#login_inviteCode").attr("style","border: 1px solid red;");
						}
						
					}
				}
			});
		}
	}
});
