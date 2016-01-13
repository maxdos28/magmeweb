
$(function(){
	$("#golfLoginForm [name='userName']").focus();
	document.onkeydown = function(e){
		var ev = document.all ? window.event:e;
		if(ev.keyCode==13){
			$("#golfLoginBtn").click();
		}
	}
	$("#golfLoginBtn").unbind().bind("click",function(){
		var userName = $("#golfLoginForm [name='userName']").val();
		var password = $("#golfLoginForm [name='password']").val();
		
		var callback = function(rs){
			var tipError = $("#golfLoginForm .tipsError");
			if(rs.code == 200){
				//tipError.text("登录成功!").show();
					setTimeout(function(){
						window.location.href=SystemProp.appServerUrl+"/golf/work-publish.action";
					},1000);
			}else{
				alert("用户名或密码不正确!");
				$("#golfLoginForm [name='password']").val("");
				$("#golfLoginForm [name='password']").focus();
			}
		};
		$.ajax({
			url: SystemProp.appServerUrl+"/golf/login!doJson.action",
			type: "POST",
			data: {"userName":userName,"password":password},
			success: callback
		});
	});
});