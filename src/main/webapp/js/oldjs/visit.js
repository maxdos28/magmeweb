$(document).ready(function(){
	//增加好友
	function addFriend(obj){
		var user = obj.parents("[userId]").eq(0);
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
				alert(message,function(){
					$("a[name='addFriend']").html("取消好友");
					$("a[name='addFriend']").unbind("click").bind('click',function(){
						deleteFriend($(this));
					});
					$("a[name='addFriend']").attr("name","deleteFriend");
				});
			}
		};
		$.ajax({
			url : SystemProp.appServerUrl+"/user-friend!inviteJson.action",
			type : "POST",
			dataType : "json",
			data : {"friendUserId":userId},
			success : callback
		});
	}
	
	$("a[name='addFriend']").unbind("click").bind('click',function(){
		addFriend($(this));
	});
	//footerMini--------------------------------------------
	footerMini();
	
	//评论-----------------------------------------------------------
	$("a[name='tagContent']").live('click',function(){
		var rightDiv = $(this).parents(".right");
		rightDiv.find("textarea[name='content']").focus();
	})
	//删除好友
	function deleteFriend(obj){
		var user = obj.parents("[userId]").eq(0);
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
					$("a[name='deleteFriend']").html("加为好友");
					$("a[name='deleteFriend']").unbind("click").bind('click',function(){
						addFriend($(this));
					});
					$("a[name='deleteFriend']").attr("name","addFriend");
				});
			}
		};
		$.ajax({
			url : SystemProp.appServerUrl+"/user-friend!cancelJson.action",
			type : "POST",
			dataType : "json",
			data : {"friendUserId":userId},
			success : callback
		});
	}
	
	$("a[name='deleteFriend']").unbind("click").bind('click',function(){
		deleteFriend($(this));
	});
	
	//增加关注
	function addFollow(obj){
		var user = obj.parents("[userId]").eq(0);
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
				alert(message,function(){
					$("a[name='addFollow']").html("取消关注");
					$("a[name='addFollow']").unbind("click").bind('click',function(){
						deleteFollow($(this));
					});
					$("a[name='addFollow']").attr("name","deleteFollow");
				});
			}
		};
		$.ajax({
			url : SystemProp.appServerUrl+"/user-follow!addJson.action",
			type : "POST",
			dataType : "json",
			data : {"followedUserId":userId},
			success : callback
		});
	}
	$("a[name='addFollow']").unbind("click").bind('click',function(){
		addFollow($(this));
	});
	
	//取消关注
	function deleteFollow(obj){
		var user = obj.parents("[userId]").eq(0);
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
				alert("取消关注成功",function(){
					$("a[name='deleteFollow']").html("关注此人");
					$("a[name='deleteFollow']").unbind("click").bind('click',function(){
						addFollow($(this));
					});
					$("a[name='deleteFollow']").attr("name","addFollow");
				});
			}
		};
		$.ajax({
			url : SystemProp.appServerUrl+"/user-follow!deleteJson.action",
			type : "POST",
			dataType : "json",
			data : {"followedUserId":userId},
			success : callback
		});
	}
	$("a[name='deleteFollow']").unbind("click").bind('click',function(){
		deleteFollow($(this));
	});
	
});