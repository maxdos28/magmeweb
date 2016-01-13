$(function(){

//for snsItemDelete
var $snsItem = $(".conInteractionArea .listItem");
var $snsItemDel = $(".conInteractionArea .listItem .del");
appendDel($snsItem);
function appendDel(obj){
	obj.hover(
		function(){
			$(this).prepend("<a class='del'></a>");
			
//			var shareBtn = $(this).find(".iconShare");
//			if(shareBtn.length>0){
//				var url = SystemProp.domain+$(this).find(".floatlImg").attr("href");
//				var tagTitle = $(this).find("a[name='tagTitle']").html();
//				var picUrl = $(this).find(".floatlImg img").attr("src");
//				tagShare(url,tagTitle,picUrl);
//				shareBtn.after($("#jiathis").show());
//			}
		    
		},function(){
			$(this).find("a.del").remove();
			$("#jiathis").hide();
	});
}

$snsItemDel.live("click",function(){
	$(this).hide().parent(".listItem").animate({height:0,opacity:0,marginTop:0,paddingBottom:0,border:0},1000,"easeOutQuint");
	var obj = $(this).parent(".listItem");
	setTimeout(function(){obj.remove()},1000);
});

//for listMessage
var $listMsgDel = $(".conMessage .conBody .msgList .item a.del");
$listMsgDel.live("click",function(){
	$(this).hide().parent(".item").animate({height:0,opacity:0,margin:0,paddingTop:0,paddingBottom:0,border:0},1000,"easeOutQuint");
	var obj = $(this).parent(".item");
	setTimeout(function(){obj.remove()},1000);
});


//更多好友
$("a[name='moreFriendList']").live("click",function(){
	var begin=$(this).attr("listBegin");
	var size=$(this).attr("listSize");
	var userId=$(this).attr("userId");
	var data={"userId":userId,"begin":begin,"size":size};
	var url=SystemProp.appServerUrl+"/user-friend!getFriendListJson.action";
	
	var callback = function(result){
		var code = result.code;
		if(code == 400){
			alert(result.message,function(){
				gotoLogin();
			});
			return;
		}else if(code == 300||code == 500){
			alert(result.message);
		}else if(code == 200){
			var userList=result.data.friendList;
			if(userList&&userList.length>0){
				for(var i=0;i<userList.length;i++){
					var user=userList[i];
					var str='';
					if(user.avatar60){
						str='<li><a href="'+SystemProp.appServerUrl+'/user!visit.action?id='+user.id+'"><img src="'+SystemProp.profileServerUrl+user.avatar60+'" title="'+user.nickName+'" /><span>'+user.nickName+'</span><sup></sup></a></li>';
					}else{
						str='<li><a href="'+SystemProp.appServerUrl+'/user!visit.action?id='+user.id+'"><img src="'+SystemProp.staticServerUrl+'/images/head60.gif" title="'+user.nickName+'" /><span>'+user.nickName+'</span><sup></sup></a></li>';
					}
					var obj=$("#friendList");
					obj.append(str);
				}
				$("a[name='moreFriendList']").attr("listBegin",begin*1+size*1);
			}
		}
	};
	$.ajax({
		url : url,
		type : "POST",
		dataType : "json",
		data : data,
		success : callback
	});
});

//更多关注
$("a[name='moreFollowList']").live("click",function(){
	var begin=$(this).attr("listBegin");
	var size=$(this).attr("listSize");
	var userId=$(this).attr("userId");
	var data={"userId":userId,"begin":begin,"size":size};
	var url=SystemProp.appServerUrl+"/user-follow!getFollowListJson.action";
	
	var callback = function(result){
		var code = result.code;
		if(code == 400){
			alert(result.message,function(){
				gotoLogin();
			});
			return;
		}else if(code == 300||code == 500){
			alert(result.message);
		}else if(code == 200){
			var userList=result.data.followList;
			if(userList&&userList.length>0){
				for(var i=0;i<userList.length;i++){
					var user=userList[i];
					var str='';
					if(user.avatar60){
						str='<li><a href="'+SystemProp.appServerUrl+'/user!visit.action?id='+user.id+'"><img src="'+SystemProp.profileServerUrl+user.avatar60+'" title="'+user.nickName+'" /><span>'+user.nickName+'</span><sup></sup></a></li>';
					}else{
						str='<li><a href="'+SystemProp.appServerUrl+'/user!visit.action?id='+user.id+'"><img src="'+SystemProp.staticServerUrl+'/images/head60.gif" title="'+user.nickName+'" /><span>'+user.nickName+'</span><sup></sup></a></li>';
					}
					var obj=$("#followList");
					obj.append(str);
				}
				$("a[name='moreFollowList']").attr("listBegin",begin*1+size*1);
			}
		}
	};
	$.ajax({
		url : url,
		type : "POST",
		dataType : "json",
		data : data,
		success : callback
	});
});

//评论-----------------------------------------------------------
$("a[name='tagContent']").live('click',function(e){
	e.preventDefault();
	var rightDiv = $(this).parents(".right");
	rightDiv.find(".inputArea").show();
});
//分享-----------------------------------------------------------
$("a[name='tagShare']").live('click',function(){
	var tagId = $(this).parent().attr("tagId");
	var url = SystemProp.domain+"/user-tag!show.action?id="+tagId;
	var picUrl = $(this).parents(".right").find("img").attr("src");
	var tagTitle = $(this).parents(".right").find("a[name='tagTitle']").html();
	
	tagShare(url,tagTitle,picUrl);
});
// 订阅
$("a[name='subscribe']").live('click',function(){
	var issue = $(this).parents("[issueId]").eq(0);
	var issueId = issue.attr("issueId");
	
	addSubscribe(issueId,null);
});
//收藏
$("a[name='collection']").live('click',function(){
	var issue = $(this).parents("[issueId]").eq(0);
	var issueId = issue.attr("issueId");
	
	addCollection(issueId);
});
//提交评论
$("a[name='tagCommentSubmit']").unbind("click").live("click",function(){
	var obj = $(this);
	var url = SystemProp.appServerUrl+"/tag-comment!addJson.action";
	var tagId = obj.attr("tagId");
	var lastContent = obj.parent().find("textarea").val();
	var data = {"tagId":tagId,"content":lastContent};
	$.ajax({
			url:url,
			type : "POST",
			data : data,
			dataType : "json",
			success: function(rs){
				var code = rs.code;
				if(code==200){
					var comment=rs.data.tagComment;
					var tagComment = '			<div class="item">';
						tagComment += '            	<a href="'+SystemProp.appServerUrl+'/user!visit.action?id='+comment.user.id+'">';
						if(comment.user.avatar){
							tagComment += '                <div class="smallHead"><img src="'+SystemProp.profileServerUrl+comment.user.avatar46+'"/></div>';
						}else{
							tagComment += '                <div class="smallHead"><img src="'+SystemProp.staticServerUrl+'/images/head46.gif" /></div>';
						}
						tagComment += '              </a>';
						tagComment += '              <p><strong><a href="'+SystemProp.appServerUrl+'/user!visit.action?id='+comment.user.id+'">'+comment.user.nickName+'</a></strong>：'+comment.contentInfo.content+'</p>';
						tagComment += '			<div class="navBtn">';
						tagComment += '				<span>一分钟前</span>';						
						tagComment += '			</div>';
						tagComment += '			</div>';
					obj.parent().prevAll("h5").after(tagComment);
					obj.parent().find("textarea").val("");
				}else if(code ==300&&rs.message=="failure"){
					alert(rs.data.content);
				}else if(code ==400){
					alert(rs.message,function(){
						gotoLogin();
						$(window).scrollTop(0);
					});
					return;
				}else{
					alert(rs.message);
				}
			}
	});
});

//------------------------------- scroll----------------------------------
var hasInfo = true;
var scrollTimer = null;
$(window).scroll(function(){
	var hasOver = hasOverlay();
	if(!scrollTimer && !hasOver){
		scrollTimer = setTimeout(function(){
			var bodyHeight = $("body").height();
			var windowHeight = $(window).height();
			var scrollHeight = bodyHeight - windowHeight;
			
			var scrollTop = $(window).scrollTop();
			var followInfos = $("#followInfosArea");
			$("#loadMore").show();
			if(scrollTop > (scrollHeight-200) && hasInfo && followInfos.length>0 && followInfos.is(":visible")){
				if($("#loadMore").length==0)return;
				var a = $("#loadMore").find("a").eq(0);
				var begin = parseInt(a.attr("listbegin"));
				var size = parseInt(a.attr("listsize"));
				var count =parseInt( a.attr("listcount"));
				var userId = a.attr("userId")*1;
				loadMoreInfo(begin,size,count,userId);
				$("#loadMore").fadeOut(3000);
			}
			if(!hasInfo){
				$("#loadMore").find("a").html("没有信息加载了");
				$("#loadMore").fadeOut(3000);
			}
			
			scrollTimer = null;
		},800);
	}
});
//loadMoreInfo---------------------------------------------------
function loadMoreInfo(begin,size,count,userId){
	if((begin+size) >= count) hasInfo = false;
	var url="",data={}; 
	if(!isNaN(userId) && userId*1>0){
		url = SystemProp.appServerUrl+"/user!moreUserFeedAjax.action";
		data = {"begin":begin,"size":size,"userId":userId};
	}else{
		url = SystemProp.appServerUrl+"/user!moreFeedAjax.action";
		data = {"begin":begin,"size":size};
	}
	$.ajax({
		url:url,
		type : "POST",
		async : false,
		data : data,
		dataType : "html",
		success: function(rs){
			if(!rs){
				hasInfo = false;
				return;
			}
			var item = $(rs);
			$("#newsFeed").append(item);
			var items = $("#newsFeed").find(".listItem");
			appendDel(items);
			$("#loadMore").find("a").eq(0).attr("listbegin",data.begin*1+data.size*1);
			if(length >= count ) hasInfo = false;
		},
		failure : function(){
			alert("服务器出错啦！");
		}
});
}


$("a[name='visitMoreNewsFeed']").unbind("click").live("click",function(){
	var obj = $(this);
	var url = SystemProp.appServerUrl+"/user!moreUserFeedAjax.action";
	var data = {"begin":obj.attr("listbegin"),"size":obj.attr("listsize"),"userId":obj.attr("userId")};
	$.ajax({
			url:url,
			type : "POST",
			data : data,
			dataType : "html",
			success: function(rs){
				obj.parent().prev().append(rs);
				obj.attr("listbegin",data.begin*1+data.size*1);
			},
			failure : function(){
				alert("服务器出错啦！");
			}
	});
});


$("input[name='searchMessageKeyWord']").keyup(function(event){
	event.stopPropagation();
	if (event.keyCode == '13') {
		event.preventDefault();
		searchMessage();
    }
});

$("a[name='searchMessage']").unbind("click").live("click",searchMessage);	

function searchMessage(){
	var keywords=$("input[name='searchMessageKeyWord']").val().split(" ");
	$("div[messageId]").each(function(index){
		var message=$(this);
		var content=message.find("p").html();
		content=content.replace(/(<em>)|(<\/em>)/g, "");
		$.each(keywords,function(index,keyword){
			keyword=keyword.replace(/(^\s*)|(\s*$)/g, "");
			if(keyword){
				var reg = new RegExp('('+keyword+')', 'gi'); 
	        	content=content.replace(reg, '<em>$1</em>'); 
			}
		});
		message.find("p").html(content);
	});
}

//标签推荐
$("a[name='tagTop']").live("click",function(){
	var obj=$(this);
	var tagId=$(this).attr("tagId");
	var tagCookie=getCookie("tagId_"+tagId);
	if(tagCookie){
		alert("您已推荐过");
	}else{
		var data={"id":tagId};
	
		var callback = function(result){
			var code = result.code;
			var message = result["message"];
			if(code == 400){
				alert(message,function(){
					gotoLogin();
				});
				return;
			}else if(code == 300||code==500){
				alert(message);
			}else if(code == 200){
				obj.html("推荐("+result.data.tag.topNum+")");
				var expires_date = new Date(new Date().getTime() + (1*24*60*60*1000));
				setCookie("tagId_"+tagId,tagId,expires_date);
			}
		};
		$.ajax({
			url : SystemProp.appServerUrl+"/user-tag!topJson.action",
			type : "POST",
			dataType : "json",
			data : data,
			success : callback
		});
	}
});
});
