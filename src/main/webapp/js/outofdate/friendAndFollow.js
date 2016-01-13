$(document).ready(function(){
	//menu
	var pos = getUrlValue("pos") || 0;
	var conSubMenu = $("#conSubMenu");
	$("#conSubMenu>ul").find("ul").hide();
	conSubMenu.find(".current").removeClass("current");
	var ul = conSubMenu.find("li[name='friend'] ul:first");
	ul.show();
	ul.find("li").eq(pos).addClass("current");
	//同意加为好友
	$("a[name='agreeFriend']").live('click',function(){
		var user = $(this).parents("[userId]").eq(0);
		var userId = user.attr("userId");
		var avatar=$("img[name='avatar']",user).attr("src");
		var nickName=$("strong[name='nickName']",user).text();
		
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
				var newFriend='';
				newFriend += '                    	<div class="fItem">';
				newFriend += '                        	<a href="'+SystemProp.appServerUrl+'/user!visit.action?id='+userId+'">';
				newFriend += '                        		<div class="userHead">';
				newFriend += '	            					      <img src="'+avatar+'" />';
				newFriend += '								              <sub></sub>';
				newFriend += '								              </div>                        	';
				newFriend += '                            	<strong>'+nickName+'</strong>';
				newFriend += '                            </a>';
				newFriend += '                            <div class="team">';
				newFriend += '                            	<strong>所在地区</strong>';
				newFriend += '                            	<a href="javascript:void(0)">族群1</a>';
				newFriend += '                            	<a href="javascript:void(0)">族群2</a>';
				newFriend += '                            	<a href="javascript:void(0)">族群3</a>';
				newFriend += '                            </div>';
				newFriend += '                            <div class="read">';
				newFriend += '                            	<strong>最近阅读</strong>';
				newFriend += '                            	<a href="javascript:void(0)">杂志名</a>';
				newFriend += '                            	<a href="javascript:void(0)">杂志名</a>';
				newFriend += '                            	<a href="javascript:void(0)">杂志名</a>';
				newFriend += '                            </div>';
				newFriend += '                        </div>';
				$("#friendList").prepend(newFriend);
				user.remove();
			}
		};
		$.ajax({
			url : SystemProp.appServerUrl+"/user-friend!agreeJson.action",
			type : "POST",
			dataType : "json",
			data : {"friendUserId":userId},
			success : callback
		});
	});
	
	//拒绝加为好友
	$("a[name='refuseFriend']").live('click',function(){
		var user = $(this).parents("[userId]").eq(0);
		var userId = user.attr("userId");
		
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
				alert("操作成功",function(){
					user.remove();
				});
			}
		};
		$.ajax({
			url : SystemProp.appServerUrl+"/user-friend!refuseJson.action",
			type : "POST",
			dataType : "json",
			data : {"friendUserId":userId},
			success : callback
		});
	});
});