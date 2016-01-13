;$(document).ready(function(){
	$(".JQfadeIn").mouseenter(function(){
		$(this).find(".item").animate({opacity:1},300);
	}).mouseleave(function(){
		$(this).find(".item").stop(true,true).not(".current").animate({opacity:0.3},300);
	})
	//shareToIntenet
	tagInfo = {
		url: window.location.href,
		title: $("#picDesc").html(),
		imgsrc: $(".photo img").eq(0).attr("src"),
		desc: $("#picDesc").html()
	};
	
	$("a[tagShare]").live('click',function(e){
		e.preventDefault();
		var shareBtn = $(this);
    	var type = shareBtn.attr("tagShare");
    	//tagInfo is window's parameter. Default parameter is in useFunction.js
    	//everyPage want to share in the Internet, you can change the parameter(tagInfo)
    	shareToObj.shareType(type,tagInfo);
	});
	//加为好友
	$("#addFri").click(function(e){
		e.preventDefault();
		if( !hasUserLogin() ){
			gotoLogin("请登录后，才能加好友哦！");
			return;
		}
		var userId = $(this).attr("userId");
		ajaxAddFollow(userId,1,function(rs){
			if(!rs)return;
			var code = rs.code;
			if(code == 200){
				$("#addFri").hide();
				$("#removeFri").show();
				alert("操作成功！");
			}else if(code == 400){
				gotoLogin("用户没有登录，请登录！");
			}else{
				alert(rs.message);
			}
		});
	});
	//取消好友
	$("#removeFri").click(function(e){
		e.preventDefault();
		if( !hasUserLogin() ){
			gotoLogin("请登录后，才能留言！");
			return;
		}
		var userId = $(this).attr("userId");
		ajaxCancelFollow(userId,1,function(rs){
			if(!rs)return;
			var code = rs.code;
			if(code == 200){
				$("#addFri").show();
				$("#removeFri").hide();
				alert("操作成功！");
			}else if(code == 400){
				gotoLogin("用户没有登录，请登录！");
			}else{
				alert(rs.message);
			}
		});
	});
	
	//---------------------add_tag----------------------------
	$("#tagList a[name=tagName]").live("click",function(e){
		e.preventDefault();
		if( !hasUserLogin() ){
			gotoLogin("请登录后，才能添加标签！");
			return;
		}
		var $tag = $(this);
		var picId = getUrlValue("imageId");
		var tagContent = $tag.html();
		ajaxAddTag(picId,1,tagContent,function(rs){
			if(!rs)return;
			var code = rs.code;
			if(code == 200){
				alert("添加标签成功！");
			}else if(code == 400){
				gotoLogin("用户没有登录，请登录！");
			}else{
				alert(rs.message);
			}
		});
	});
	
	var allTags = [];
	$("#tagList a[name=tagName]").each(function(){
		var tagContent = $(this).html();
		allTags.push(tagContent);
	});
	$("#addTag").click(function(e){
		e.preventDefault();
		if( !hasUserLogin() ){
			gotoLogin("请登录后，才能添加标签！");
			return;
		}
		var tagContent = $("#tagInfo").val();
		if(tagContent == "添加新标签" || tagContent ==""){
			alert("请输入标签！");
			return;
		}
		if(tagContent.length>6){
			alert("标签长度不能超过6！");
			return;
		}
		var picId = $(this).attr("picId");
		ajaxAddTag(picId,1,tagContent,function(rs){
			if(!rs)return;
			var code = rs.code;
			if(code == 200){
				var tagContent = rs.data.tag.name||'';
				if($.inArray(tagContent,allTags) == -1){
					allTags.push(tagContent);
					$("#tagList .add").before('<li><a name="tagName" href="javascript:void(0)">'+tagContent+'</a></li>');
				}
				$("#tagInfo").val('');
				alert("添加标签成功！");
			}else if(code == 400){
				gotoLogin("用户没有登录，请登录！");
			}else{
				alert(rs.message);
			}
		});
	});
	
	//sendMsg-------------------------------------
	$("#sendMsgBtn").click(function(e){
		e.preventDefault();
		if( !hasUserLogin() ){
			gotoLogin("请登录后，才能发送消息！");
			return;
		}
		$("#userNewMsg").fancybox();
	});
	$("#closePop").click(function(e){e.preventDefault();$.fancybox.close();});
	$("#send").click(function(e){
		e.preventDefault();
		var userId = $(this).attr("userId");
		var content = $("#msgContent").val();
		if(!content || content === '请输入消息内容'){
			alert('请输入消息内容');
			return;
		}
		//1-->send to user
		ajaxSendMsg(userId,1,content,function(rs){
			if(!rs) return;
			var code = rs.code;
			if(code == 200){
				$.fancybox.close();
				alert("发送成功！");
			}else if(code == 400){
				gotoLogin("用户没有登录，请登录！");
			}else{
				alert(rs.message);
			}
		});
	});
	
	//用户添加评论
	$("#commentBtn").click(function(e){
		e.preventDefault();
		if( !hasUserLogin() ){
			gotoLogin("请登录后，才能留言！");
			return;
		}
		var comment = $("#picComment").val()||"";
		if(comment == '喜欢就给它留言吧' || comment.length<3){
			alert("评论的长度不能少于3！");
			return;
		}
		var picId = $(this).attr("picId");
		$.ajax({
			url : SystemProp.appServerUrl+"/user-image-comment!addJson.action",
			type : "POST",
			data : {"imageId":picId,"content":comment},
			dataType : 'json',
			success: function(rs){
				if(!rs)return;
				var code = rs.code;
				if(code == 200){
					var userImageComment = rs.data.userImageComment;
					var avatar = (!!userImageComment.userAvatar46)? SystemProp.profileServerUrl+userImageComment.userAvatar46 : SystemProp.staticServerUrl+'/images/head46.gif';
					var commentDom = '<div class="item"><div>'+(userImageComment.contentInfo.content||'')+'</div>'+
						'<a class="head" href="/user-visit!index.action?userId='+userImageComment.userId+'" title="'+(userImageComment.userNickName ||'')+'">'+
						'<img src="'+avatar+'" alt="'+(userImageComment.userNickName ||'')+'"></a>'+
						'<sub></sub></div>';
						$("#comments .mySelf").after(commentDom);
						$("#picComment").val('');
				}else if(code == 400){
					gotoLogin("用户没有登录，请登录！");
				}else{
					alert(rs.message);
				}
			}
		});
	});
	
});