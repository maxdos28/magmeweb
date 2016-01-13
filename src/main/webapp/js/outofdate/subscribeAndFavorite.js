$(document).ready(function(){
	//menu
	var pos = getUrlValue("pos") || 0;
	var conSubMenu = $("#conSubMenu");
	$("#conSubMenu>ul").find("ul").hide();
	conSubMenu.find(".current").removeClass("current");
	var ul = conSubMenu.find("li[name='subscribeAndFavorite'] ul:first");
	ul.show();
	ul.find("li").eq(pos).addClass("current");
	//取消收藏
	$("a[name='deleteFavorite']").live('click',function(){
		var userFavorite = $(this).parents("[userFavoriteId]").eq(0);
		var userFavoriteId = userFavorite.attr("userFavoriteId");
		
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
				userFavorite.remove();
			}
		};
		$.ajax({
			url : SystemProp.appServerUrl+"/user-favorite!deleteJson.action",
			type : "POST",
			dataType : "json",
			data : {"id":userFavoriteId},
			success : callback
		});
	});
	
	//取消订阅
	$("a[name='deleteSubscribe']").live('click',function(){
		var userSubscribe = $(this).parents("[userSubscribeId]").eq(0);
		var userSubscribeId = userSubscribe.attr("userSubscribeId");
		
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
				userSubscribe.remove();
			}
		};
		$.ajax({
			url : SystemProp.appServerUrl+"/user-subscribe!deleteJson.action",
			type : "POST",
			dataType : "json",
			data : {"id":userSubscribeId},
			success : callback
		});
	});
});