;$(function(){
	//记住密码功能
	if(getCookie("rememberPassword") && getCookie("rememberPassword")==1){
		$("#rememberPassword").attr("checked","checked");
		$("#userName").val(getCookie("smsUserName"));
		$("#password").val(getCookie("smsUserpassword"));
		//$("#displayPassword").val("aaaa");
	}
	
	//回车事件
	document.onkeydown = function(e){
	    var ev = document.all ? window.event : e;
	    if(ev.keyCode==13) {
	    	$("#loginEnter").click();
	     }
	}
		
	//登陆
	$("#loginEnter").unbind("click").bind("click",function(e){
		e.stopPropagation();
		var userName=$("#userName").val();
		var password=$("#password").val();
		var rememberPassword=$("#rememberPassword").attr("checked");
		if(!userName || !password ){
			alert("请输入用户名和密码");
			return ;
		}
		
		
		var callback=function(rs){
			if(!rs || rs.code !=200){
				alert("用户名或密码错误");
				return;
			}
			if(rememberPassword && rememberPassword=="checked"){
				setCookie("smsUserName",userName);
				setCookie("smsUserpassword",password);
				setCookie("rememberPassword",1);
			}
			window.location.href=SystemProp.kaiJieAppUrl+"/sms/sms-user!edit.action";
		};
		
		$.ajax({
			url : SystemProp.kaiJieAppUrl+"/sms/sms-user!loginJson.action",
			type : "POST",
			dataType:'json',
			data : {"userName":userName,"password":password},
			success: callback
		});
	});
	
	//登出
	$("#logout").unbind("click").bind("click",function(e){
		var callback=function(rs){
			window.location.href=SystemProp.kaiJieAppUrl+"/sms/sms-user.action";
		};
		
		$.ajax({
			url : SystemProp.kaiJieAppUrl+"/sms/sms-user!logoutJson.action",
			type : "POST",
			dataType:'json',
			success: callback
		});
	});
	
	
	
	
	
});