$(document).ready(function(){
	//share's parameter
	tagInfo = {
		url: location.href,
		title: $(".left .content").html(),
		imgsrc: $(".img>img").attr("src"),
		desc:''
	};
	//用户切米滚动切换
	var num = Math.floor($("#tagList").find("li.current").prevAll("li").length/7);
	$.jqueryScrollPhoto("#tagList",7,54,7,num,600);
	
	//弹出站内消息发送框
	$("#btnSendMsg").click(function(e){
		e.preventDefault();
		if($(this).next().hasClass("current")){
			$(this).next().slideUp(300).removeClass("current");
		}else{
			$(this).next().slideDown(300).addClass("current");
		}
	});
	$("#sendMsg a").click(function(e){
		e.preventDefault();
		$(this).parent().fadeOut(500).removeClass("current");
		setTimeout(function(){$.jqueryInputTips()},500);
	});
	$("#msgSubmmit").click(function(e){
		e.preventDefault();
		if( !hasUserLogin() ){
			gotoLogin("请登录后，才能留言！");
			return;
		}
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
				alert("发送成功！");
			}else if(code == 400){
				gotoLogin("用户没有登录，请登录！");
			}else{
				alert(rs.message);
			}
		});
	});
	
	$("#addFri").click(function(e){
		e.preventDefault();
		if( !hasUserLogin() ){
			gotoLogin("请登录后，才能留言！");
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
	$("#allTags li a").live("click",function(e){
		e.preventDefault();
		if( !hasUserLogin() ){
			gotoLogin("请登录后，才能留言！");
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
	$("#allTags li a").each(function(){
		var tagContent = $(this).html();
		allTags.push(tagContent);
	});
	//tagSubmit--------------------------------
	$("#tagSubmit").click(function(e){
		e.preventDefault();
		var tagContent = $("#picTag").val();
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
					$("#allTags").append('<li><a href="/user-image/now.html?tagName='+tagContent+'">'+tagContent+'</a></li>');
				}
				$("#picTag").val('');
				alert("添加标签成功！");
			}else if(code == 400){
				gotoLogin("用户没有登录，请登录！");
			}else{
				alert(rs.message);
			}
		});
	});
	//用户添加评论
	$("#addSubmit").click(function(e){
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
					var commentDom = '<div class="iitem clearFix"><span>'+(userImageComment.createdTime.replace(/T/g,' ') ||'')+'</span>'+
		                '<a href="/user-visit!index.action?userId='+userImageComment.userId+'">'+
		                '<div class="userHead"><img src="'+avatar+'"></div>'+
		                '<strong>'+(userImageComment.userNickName ||'')+'</strong></a><p>'+(userImageComment.contentInfo.content||'')+'</p></div>';
						$(".left .chat").after(commentDom);
						$("#picComment").val('');
				}else if(code == 400){
					gotoLogin("用户没有登录，请登录！");
				}else{
					alert(rs.message);
				}
			}
		});
	});
	
	//keyup------------------------------------------
	$("#picComment").keyup(function(event){
		event.stopPropagation();
		if (event.keyCode == '13') {
			event.preventDefault();
			var comment = $.trim( $(this).val() );
			$("#addSubmit").click();
	    }
	});
	$("#msgContent").keyup(function(event){
		event.stopPropagation();
		if (event.keyCode == '13') {
			event.preventDefault();
			var content = $.trim( $(this).val() );
			$("#msgSubmmit").click();
	    }
	});
	
});