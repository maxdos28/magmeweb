
$(function(){
	$("#publisherRegisterForm [name='userName']").focus();
	
	$("#publisherRegisterForm [name='authcode']").unbind().bind("focus",function(){
		var url=$(this).parents("em").next().find("img").attr("src");
		if(url.indexOf("code.gif")>-1){
			$("#publisherRegisterForm [name='authcode']").parents("em").next().click()
		}
	});
	
	$("#authcode").unbind().bind("click",function(){
		getAuthCode($(this).find("img").eq(0));
	});
	
	$("#publisherRegisterBtn").unbind().bind("click",function(){
		$("#publishAgreement").parents("em").next().hide();
		
		if($("#publishAgreement:checked").val()){
			var publisher=form2object("publisherRegisterForm");
			var data={};
			data["publisher.userName"]=publisher.userName||"";
			data["publisher.publishName"]=publisher.publishName||"";
			data["publisher.email"]=publisher.email||"";
			data["publisher.contact1"]=publisher.contact1||"";
			data["publisher.contactPhone1"]=publisher.contactPhone1||"";
			data["publisher.password"]=publisher.password||"";
			data["publisher.password2"]=publisher.password2||"";
			data["authcode"]=publisher.authcode||"";
			
			for(var name in data){
				name=name.replace("publisher.","");
				$("#publisherRegisterForm [name='"+name+"']").parents("em").nextAll(".tipsError").text("");
			}
			
			var callback = function(rs){
				var tipError = $("#publisherRegisterForm #tipsError");
				if(rs.code == 200){
					//tipError.text("注册成功!").show();
					alert("注册成功!系统已发送一封激活邮件到您的邮箱：" + publisher.email + " 请登录邮箱点击链接进行激活!", function(){
//						window.location.href=SystemProp.appServerUrl+"/new-publisher/publisher-config!to.action";
						window.location.href=SystemProp.appServerUrl+"/new-publisher/login!to.action";
					});
				}else if(rs.code== 300){
					for(var name in rs.data) {
						if(rs.data[name]){
							alert(rs.data[name]);
							return;
						}
					    //$("#publisherRegisterForm [name='"+name+"']").parents("em").nextAll(".tipsError").text(rs.data[name]).show();
					}
				}else{
					if(rs.message){
						alert(rs.message);
					}else{
						alert("error!");
					}
				}
			};
			$.ajax({
				url: SystemProp.appServerUrl+"/new-publisher/register!doJson.action",
				type: "POST",
				data: data,
				success: callback
			});
		}else{
			alert("尚未接受《居冠软件用户协议》");
			//$("#publishAgreement").parents("em").next().text("尚未接受《居冠软件用户协议》").show();
		}
	});
});