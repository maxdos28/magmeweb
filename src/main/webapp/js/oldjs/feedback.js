$(document).ready(function(){
	fnSetFooterHeight();
	$("a[name='feedbackFormSubmitBtn']").unbind('click').live("click",function(){
		var form=$("#feedbackForm");
		var categoryId=form.find("select[name='categoryId']").val();
		var content=form.find("textarea[name='content']").val();
		var authcode=form.find("input[name='authcode']").val();
		
		if(!categoryId||!(/^\d+$/.test(categoryId))){
			alert("请选择意见类别",function(){
				form.find("select[name='categoryId']").focus();
			});
			return;
		}
		
		if(!$.trim(content)){
			alert("意见内容不允许为空",function(){
				form.find("textarea[name='content']").focus();
			});
			return;
		}
		
		if(!$.trim(authcode)||$.trim(authcode)=="验证码"){
			alert("验证码不允许为空",function(){
				form.find("input[name='authcode']").focus();
			});
			return;
		}
		
		var callback = function(result){
			var code = result.code;
			var message = result["message"];
			if(code == 400){
				alert(message,function(){
					gotoLogin();
				});
			}else if(code == 300||code==500){
				if(result.data&&result.data.authcode){
					alert(result.data.authcode,function(){
						form.find("input[name='authcode']").val("");
						form.find("input[name='authcode']").focus();
					});
				}else{
					alert(message);	
				}
			}else if(code == 200){
				alert("感谢您提交意见反馈",function(){
					$(form)[0].reset();
				});
			}
			return;
		};
		
		$.ajax({
			url: SystemProp.appServerUrl+"/feed-back!saveJson.action",
			type: "POST",
			data: {"categoryId":categoryId,"content":content,"authcode":authcode},
			success: callback
		});
	});
});