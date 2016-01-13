$(document).ready(function(){
	//menu
	var pos = getUrlValue("pos") || 0;
	var conSubMenu = $("#conSubMenu");
	$("#conSubMenu>ul").find("ul").hide();
	conSubMenu.find(".current").removeClass("current");
	var ul = conSubMenu.find("li[name='tagManage'] ul:first");
	ul.show();
	ul.find("li").eq(pos).addClass("current");
	//删除标签
	$("sub[name='deleteTag']").live('click',function(){
		var tag = $(this).parents("[tagId]").eq(0);
		var tagId = tag.attr("tagId");
		
		var callback = function(result){
			var code = result.code;
			var message = result["message"];
			if(code == 400){
				alert(message,function(){
					gotoLogin();
				});
				return;
			}else if(code == 300){
				alert(message);
			}else if(code == 200){
				alert("操作成功");
				tag.remove();
			}
		};
		$.ajax({
			url : SystemProp.appServerUrl+"/user-tag!deleteTagJson.action",
			type : "POST",
			dataType : "json",
			data : {"id":tagId},
			success : callback
		});
		
		return false;
	});
});