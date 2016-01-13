
$(function(){
	$("#publisherLoginForm [name='userName']").focus();
	document.onkeydown = function(e){
		var ev = document.all ? window.event:e;
		if(ev.keyCode==13){
			$("#publisherLoginBtn").click();
		}
	}
	$("#publisherLoginBtn").unbind().bind("click",function(){
		var userName = $("#publisherLoginForm [name='userName']").val();
		var password = $("#publisherLoginForm [name='password']").val();
		
		var callback = function(rs){
			var tipError = $("#publisherLoginForm .tipsError");
			if(rs.code == 200){
				//tipError.text("登录成功!").show();
					setTimeout(function(){
						window.location.href=SystemProp.appServerUrl+"/new-publisher/magazine-list!to.action";
					},1000);
			}else{
				alert(rs.message);
				$("#publisherLoginForm [name='password']").val("");
				$("#publisherLoginForm [name='password']").focus();
			}
		};
		$.ajax({
			url: SystemProp.appServerUrl+"/new-publisher/login!doJson.action",
			type: "POST",
			data: {"userName":userName,"password":password},
			success: callback
		});
	});
});