;$(function(){
	//登陆
	$("#userEditEnter").unbind("click").bind("click",function(e){
		e.stopPropagation();
		var nickName=$("#nickName").val();
		var password=$("#password").val();
		var password2=$("#password2").val();
		if((password2 && !password) || (!password2 && password) ){
			alert("登录密码和确认密码不一致");
			return ;
		}
		if(password2 && password && password2!=password){
			alert("登录密码和确认密码不一致");
			return ;
		}
		
		
		if(nickName && nickName.length>30){
			alert("用户全称不能大于30个字符");
			return;
		}
		
		
		var callback=function(rs){
			if(!rs){
				alert("修改失败");
				return;
			}
			if(rs.code !=200){
				alert(rs.message);
				return;
			}
			window.location.href=SystemProp.kaiJieAppUrl+"/sms/sms-user!edit.action";
		};
		
		$.ajax({
			url : SystemProp.kaiJieAppUrl+"/sms/sms-user!updJson.action",
			type : "POST",
			dataType:'json',
			data : {"nickName":nickName,"password":password,"password2":password2},
			success: callback
		});
	});
});