$(document).ready(function(){
	//menu
	var pos = getUrlValue("pos") || 0;
	var conSubMenu = $("#conSubMenu");
	$("#conSubMenu>ul").find("ul").hide();
	conSubMenu.find(".current").removeClass("current");
	var ul = conSubMenu.find("li[name='message'] ul:first");
	ul.show();
	ul.find("li").eq(pos).addClass("current");
	
	$("a[name='delMessage']").unbind('click').bind('click',function(){
		var message = $(this).parents("[messageId]").eq(0);
        
		var callback = function(result){
			var code = result.code;
			if(code == 400){
				alert(result.message,function(){
					gotoLogin()
				});
				return;
			}else if(code == 300||code == 500){
				alert(result.message);
			}else if(code == 200){
				message.remove();
			}
		};
		$.ajax({
			url : SystemProp.appServerUrl+"/user-message!batchDeleteJson.action",
			type : "POST",
			dataType : "json",
			data : {"messageId":message.attr("messageId")},
			success : callback
		});
	});
});