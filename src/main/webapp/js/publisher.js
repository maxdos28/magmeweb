//for magezineShow===================================================================================
;(function($){
	jQuery.slideBanner = function(setting) {
	var defaultOpt = {
		time: 8000
	};
	var opt = $.extend({},defaultOpt,setting);
	var time = opt.time;
	var $id		= $("#slideBanner");
	var $item	= $id.find(".iitem");
	var $inner	= $id.find(".inner");
	var $btnLR	= $id.find(".btnLR");
	var $ctrlItem;
	var itemWidth 	= 606;
	var itemNumber	= $item.length;
	var currentId = 0;
	var lock = 0;
	var lockm = 0;
	var playLock=0;
	var ctrlHtml;
	var autoPlay;
	var delayTime = 2500;

	setTimeout(function(){autoPlay = setInterval(fnAutoPlay,time)},time/2);
	//set ctrl html
	ctrlHtml = "<div class='control'>";
	for (i=1;i<itemNumber+1;i++){ctrlHtml += "<a href='javascript:void(0)'></a>";}
	ctrlHtml += "</div>";
	$id.append(ctrlHtml);
	$ctrlItem = $id.find(".control a");
	//ready
	$item.eq(0).show();
	//去除左侧30margin
	//$item.each(function(){
	//	$(this).find(".iiitem").eq(0).css({marginLeft:0});
	//});
	$inner.width(itemNumber*itemWidth);
	fnSetCurrent(0);
//event
	$item.hover(function(){lockm=1},function(){lockm=0});
	$ctrlItem.bind("click",function(){
		if($(this).index() != currentId&&lock==0){
			lock=1;
			currentId = $(this).index();
			fnSetCurrent(currentId);
			fnChangeImg(currentId);
			clearInterval(autoPlay);
			autoPlay = setInterval(fnAutoPlay,time);
			setTimeout(function(){lock=0},delayTime);
		}
	});
	//function
	function fnSetCurrent(id){
		currentId=id;
		$ctrlItem.removeClass("current");
		$ctrlItem.eq(currentId).addClass("current");
	}
	function fnChangeImg(id){
		currentId=id;
		$inner.animate({marginLeft:-itemWidth*currentId},delayTime,"easeOutQuint");
	}
	function fnAutoPlay(){
		if(lock==0&&lockm==0){
			lock=1;
			if(currentId < itemNumber - 1){
				currentId ++;
			}else {
				currentId = 0;
			}
			fnSetCurrent(currentId);
			fnChangeImg(currentId);
			setTimeout(function(){lock=0},delayTime);
		}
	}; 

};
})(jQuery);




$(function(){
	//menu
	$("li.publisher").removeClass("hide");
	
	
	//----------------------------------------------publisherHome--------------------------------------------------
	//if($("#publisherHome").hasClass("current")){
		$.jquerytagbox("#conPublisherHome",0);
		$.slideBanner({time:99999999999});
		//米商消息回复------------------------------后台出版商登录才有的功能
		var $commentItem = $(".conComment .iitem .btnAnswer");
		$commentItem.click(function(){
			$(this).hide().parent(".iitem").siblings().find(".btnAnswer").show();
			$("#conAnswer").hide().insertBefore($(this)).slideDown(300);
		});
		$("#conAnswer").find(".btnBS").click(function(){
			$(this).parent().slideUp(300).next(".btnAnswer").show();
			conAnswerText=$("#conAnswerText").val();
			
			publisherId=$(this).parent().next(".btnAnswer").attr("publisherid");
			userId=$(this).parent().next(".btnAnswer").attr("userid");
			ajaxSendCommonMsg(userId,1,publisherId,2,conAnswerText,function(rs){
				if(!rs) return;
				var code = rs.code;
				if(code == 200){
					alert("回复成功！");
				}else{
					alert(rs.message);
				}});
		});
		//米商留言--------------------------------
		$("#commentBtn").click(function(e){
			e.preventDefault();
			if( !hasUserLogin() ){
				gotoLogin("请登录后，才能留言！");
				return;
			}
			var publisherId = $(this).attr("publisherId");
			var content = $("#commentContent").val()||"";
			ajaxSendMsg(publisherId,2,content,function(rs){
				if(!rs) return;
				var code = rs.code;
				var data = rs.data;
				if(code == 200){
					var message = data.messageList[0];
					var avatar = (!!message.fromUserAvatar46)? SystemProp.profileServerUrl+message.fromUserAvatar46 : SystemProp.staticServerUrl+'/images/head46.gif';
					var commentDiv = $("div[id^='message_page_']:visible");
					var commentStr = '<div class="iitem clearFix"><span>'+message.createdTime.split('T')[0]+'</span>'+
						'<div class="userHead"><a href="/user-visit!index.action?userId='+message.fromUserId+'" >'+
						'<img src="'+avatar+'"></a></div><strong><a href="/user-visit!index.action?userId='+message.fromUserId+' ">'+
						message.fromUserNickName+'</a></strong><p>'+message.contentInfo.content+'</p></div>';
					commentDiv.prepend(commentStr);
					
					alert("留言成功！");
				}else if(code == 400){
					gotoLogin("用户没有登录，请登录！");
				}else{
					alert(rs.message);
				}
			});
		});
		//分页-----------------------------------
		$("#changePage a[nav]").live('click',function(e){
			e.preventDefault();
			var $btn = $(this);
			var nav = $btn.attr("nav");
			var currentPage = parseInt( $("#changePage").find(".current").attr("page") );
			var publisherId = $("#changePage").attr("publisherId");
			if(nav == 'prev'){
				if(currentPage > 1){
					var targetPage = $("#changePage a[page='"+(currentPage-1)+"']");
					if( !targetPage.is(":visible")){
						targetPage.show();
						$("#changePage a[page]:visible:last").hide();
					}
					to_message_page(publisherId,currentPage-1);
				}
			}else if(nav == 'next'){
				var lastPage = parseInt($btn.attr("totalpages"));
				if (currentPage < lastPage){
					var targetPage = $("#changePage a[page='"+(currentPage+1)+"']");
					if( !targetPage.is(":visible")){
						targetPage.show();
						$("#changePage a[page]:visible:first").hide();
					}
					to_message_page(publisherId,currentPage+1);
				}
			}else{
				if(parseInt(nav) == currentPage){
					return;
				}
				if(parseInt(nav) == 1){
					$("#changePage a[page]").each(function(i){
						if(i<9){
							$(this).show();
						}else{
							$(this).hide();
						}
					});
				}else{
					$("#changePage a[page]").each(function(i){
						if(i<(parseInt(nav)-9)){
							$(this).hide();
						}else{
							$(this).show();
						}
					});
				}
				to_message_page(publisherId,nav);
			}
		});
		$("#changePage a[page]").live('click',function(e){
			e.preventDefault();
			var $btn = $(this);
			var pageNum = parseInt($btn.attr("page"));
			var publisherId = $("#changePage").attr("publisherId");
			var currentPage = parseInt( $("#changePage").find(".current").attr("page") );
			
			if(pageNum == currentPage){
				return;
			}
			to_message_page(publisherId,pageNum);
		});
		function to_message_page(publisherId,pageNum){
			if(isNaN(pageNum)) return;
			$("#changePage .current").removeClass("current");
			$("#changePage a[page='"+pageNum+"']").addClass("current");
			
			var $page = $("#message_page_"+pageNum);
			$(".conComment div[id^='message_page_']:visible").hide();
			if( $page.length>0 ){
				$page.show();
			}else{
				$.ajax({
					url : SystemProp.appServerUrl+"/publish/publisher-home!comments.action",
					type : "POST",
					async: false,
					data : {"publisherId":publisherId,"pageNo":pageNum},
					dataType : 'json',
					success: function(rs){
						if(!rs)return;
						$("#changePage").before(rs);
					}
				});
			}
			$(window).scrollTop(0);
		}
		//关注attention--------------------------
		$("#iconAdd").click(function(e){
			e.preventDefault();
			if(!$("#loginBar").is(":visible")){
				gotoLogin("用户没有登录，请登录！");
			}else{
				var publisherId = $(this).attr("publisherId");
				ajaxAddFollow(publisherId,2,function(rs){
					if(!rs)return;
					var code = rs.code;
					if(code == 200){
						$("#iconAdd").hide();
						$("#iconSub").show();
						alert("关注成功！");
					}else if(code == 400){
						gotoLogin("用户没有登录，请登录！");
					}else{
						alert(rs.message);
					}
				});
			}
		});
		//cancel关注-------------------------------------------------------------------
		$("#iconSub").click(function(e){
			e.preventDefault();
			if(!$("#loginBar").is(":visible")){
				gotoLogin("用户没有登录，请登录！");
			}else{
				var publisherId = $(this).attr("publisherId");
				ajaxCancelFollow(publisherId,2,function(rs){
					if(!rs)return;
					var code = rs.code;
					if(code == 200){
						$("#iconAdd").show();
						$("#iconSub").hide();
						alert("取消关注成功！");
					}else if(code == 400){
						gotoLogin("用户没有登录，请登录！");
					}else{
						alert(rs.message);
					}
				});
			}
		});
		
	//}
	
	
	
	//-----------------------------------------------publisherPic-------------------------------------------------
	if($("#publisherPic").hasClass("current")){
		var itemLength = $('#tagWall').find(".item").length;
		if(itemLength < 20){
			$("#loadMore").addClass("hide");
		}
		$('#tagWall').masonry({itemSelector: '.item'});
		
		//loadPic----------------------------------
		var loadPicFun = function(){
			var data = {};
			var tagName = getUrlValue("tagName");
			if(!!tagName){
				data.tagName = tagName;
			}
			data.publisherId = getUrlValue("publisherId");
			data.begin = $("#tagWall").find(".item:visible").length;
			data.size = 20;
			
			$.ajax({
				url : SystemProp.appServerUrl + "/publish/publisher-pic!picJson.action",
				type : "POST",
				async : false,
				data : data,
				success : function (rs){
					if(!rs){
						hasData = false;
						return;
					}
					$("#loadMore").before(rs);
					setTimeout(function(){$("#tagWall").masonry('reload');},1000);
				}
			});
		};
		//window_scroll------------------------------------------------------------
		scrollLoadData(loadPicFun,100,7);
		//click_load_data
		$("#loadMore").click(function(e){
			e.preventDefault();
			if(hasData){
				loadPicFun();
			}
		});
	}
	
	
	//-----------------------------------------------publisherAdvMag----------------------------------------------
	if($("#publisherAdvMag").hasClass("current")){
		//米商中心广告管理
		var $topbar = $("#pubTopbar");
		var $topbarInner = $topbar.find(".inner");
		var $topbarItem = $topbar.find(".item");
		var $topbarBtnLR = $topbar.find(".btnLR");
		var itemWidth 	= $topbarItem.eq(0).outerWidth(true);
		var itemNumber	= $topbarItem.length;
		var currentId = 0;
		var lock = 0;
		var delayTime = 2000;
		
		fnSetBtn(0);
		$topbarBtnLR.unbind('click').bind("click",function(){
			if(lock==0){
				lock=1;
				if($(this).hasClass("turnLeft") && currentId!=0){
					currentId--;
				}else if($(this).hasClass("turnRight") && currentId < itemNumber-4){
					currentId++;
				}
				$topbarInner.animate({marginLeft:-(itemWidth*currentId)},800,"easeOutQuint");
				fnSetBtn(currentId);
				setTimeout(function(){lock=0},800);
			}
		});
		function fnSetBtn(id){
			currentId=id;
			$topbarBtnLR.removeClass("stopL").removeClass("stopR");
			if(itemNumber <= 4){
				$topbarBtnLR.filter(".turnRight").addClass("stopR");
				$topbarBtnLR.filter(".turnLeft").addClass("stopL");
				return;
			}
			if(currentId==0){
				$topbarBtnLR.filter(".turnLeft").addClass("stopL");
			}else if(currentId==itemNumber-4){
				$topbarBtnLR.filter(".turnRight").addClass("stopR");
			}
			
		}
		
	}
	
	
	
	
	
	
	

});
















