;$(function(){
	//----------------------------------------------publisherHome--------------------------------------------------
	//if($("#publisherHome").hasClass("current")){
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
			if(!$("#userBar").is(":visible")){
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
			if(!$("#userBar").is(":visible")){
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
		
		//图片lazyload
		$("#homeWall .photo img").lazyload({
			placeholder: SystemProp.staticServerUrl+"/v3/images/spacer.gif",
			effect: "fadeIn"
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
	
	$("a[weibo_uid]").click(function(e){
		e.preventDefault();
		var weibo_uid=parseInt($(this).attr("weibo_uid"))||0;
		
		sina_api_follow(weibo_uid);
	});	
	
	//weiboShare
	$("#weiboShare").click(function(e){
		e.preventDefault();
		var tagInfo = {
			url: location.href+'',
			title: '我正在麦米网在线阅读《'+$("#issueName").html()+'》。',
			imgsrc: $(".img>img").attr("src")
		};
		shareToObj.shareType('tsina',tagInfo);
	});
	
	
	//copyCode
	var $embedPop = $("#embedCode");
	$("#codeCopy").click(function(e){
		e.preventDefault();
		info_issueId_change();
		info_viewMode_change();
		$("#embedCode").fancybox();
	});
	
	$("#closePop",$embedPop).click(function(e){
		e.preventDefault();
		$.fancybox.close();
	});
	
	//width-height change
	var $codeInfo = $("#codeInfo");
	$("#changeWidth").change(function(e){
		var width = $(this).val();
		if(!isNaN(width)){
			var info = $codeInfo.val().replace(/width=\"\d*\"/g,"width=\""+width+"\"");
			$codeInfo.val(info);
			var viewMode = $("input[name='viewMode']:checked").val();
			switch(viewMode){
				case '1':
					var height = width*4/3|0;
					$("#changeHeight").val(height).attr('disabled','disabled').change();
					break;
				case '2':
					$("#changeHeight").removeAttr('disabled');
					break;
				case '3':
					$("#changeHeight").val(width).attr('disabled','disabled').change();
					break;
			}
		}
	});
	$("#changeHeight").change(function(e){
		var height = $(this).val();
		if(!isNaN(height)){
			var info = $codeInfo.val().replace(/height=\"\d*\"/g,"height=\""+height+"\"");
			$codeInfo.val(info);
		}
	});
	$('#colorSelector').ColorPicker({
		color: '#0000ff',
		onShow: function (colpkr) {
			$(colpkr).fadeIn(500);
			return false;
		},
		onHide: function (colpkr) {
			$(colpkr).fadeOut(500);
			return false;
		},
		onChange: function (hsb, hex, rgb) {
			$('#colorSelector div').css('backgroundColor', '#' + hex);
			var info = $codeInfo.val().replace(/backColor=0x\w{6}/g,"backColor=0x"+hex);
			$codeInfo.val(info);
		}
	});
	$("input[name='issueId']").change(function(){
		info_issueId_change();
	});
	$("input[name='viewMode']").change(function(){
		info_viewMode_change();
	});
	
	function info_viewMode_change(){
		var viewMode = $("input[name='viewMode']:checked").val();
		var info = $codeInfo.val().replace(/viewMode=\d{1}/g,"viewMode="+viewMode);
		$codeInfo.val(info);
		switch(viewMode){
			case '1':
				var width = $("#changeWidth").val()*1;
				var height = width*4/3|0;
				$("#changeHeight").val(height).attr('disabled','disabled').change();
				$("input[name='issueId']").eq(0).removeAttr("disabled",'disabled');
				break;
			case '2':
				$("#changeHeight").removeAttr('disabled');
				$("input[name='issueId']").eq(0).removeAttr("disabled",'disabled');
				break;
			case '3':
				var width = $("#changeWidth").val()*1;
				$("#changeHeight").val(width).attr('disabled','disabled').change();
				$("input[name='issueId']").eq(0).attr("disabled",'disabled').end().eq(1).attr('checked','checked').change();
				break;
		}
	}
	function info_issueId_change(){
		var issueId = $("input[name='issueId']:checked").val();
		var info = $codeInfo.val().replace(/issueId=-?\d*/g,"issueId="+issueId);
		$codeInfo.val(info);
	}
	
	$("#show_email_subscribe").live("click",function(){
		$("#emailSubscribe").find("input").val('');
		$("#emailSubscribe").fancybox();
		return;
	});
	
	var rexEmail = /^\w+@\w*\.\w+$/;
	$("#emailSubscribe").find("input").live("blur",function(){
		var eval=$(this).val();
		if(eval!='')
			if(rexEmail.test(eval)){
				$("#email_subscribe_error").hide();
			}else{
				$("#email_subscribe_error").html('电子邮件地址格式错误！');
				$("#email_subscribe_error").show();
			}
		else
			$("#email_subscribe_error").hide();
			
	});
	$("#emailSubscribe").find(".btnBS").live("click",function(){
		var eval=$.trim($("#emailSubscribe").find("input").val());
		var obj = $(this).attr("pid");
		var tp = $(this).attr("tp");
		if(eval=='请输入您的电子邮件地址' || eval==''){
			$("#email_subscribe_error").html('请输入电子邮件地址！');
			$("#email_subscribe_error").show();
		}else{
			var tepv=0;
			if(rexEmail.test(eval)){
				var domain=(document.location+"").split("/")[2];
				var appserverurl="http://"+domain;
				$.ajax({
					url : appserverurl+"/subscribe.action",
					type : "POST",
					data : {"email":eval,"objectId":obj,"type":1},
					dataType : 'json',
					async: false,
					success: function(rs){
						if(!rs)return;
						var code = rs.code;
						if(code == 200){
							tepv=1;
							$.fancybox.close();
						}else{
							alert(rs.message);
							return;
						}
					}
				});
			}
			
			setTimeout(function(){
				if(tepv==1){
					var nId=getSubsricbeUrl(tp);
					var tpurl="http://list.qq.com/cgi-bin/qf_compose_send?t=qf_booked_feedback"+"&to="+eval+"&id="+nId;
					window.open(tpurl);
				}
			},100);
			
		}
		
	});
	
	function getSubsricbeUrl(v){
		if(v!=null){
			if(v=='caijing') return "426e45caded42f8458a9174710b6bebd0e4e0ce8d165c252";
			if(v=='jiaju') return "001e0836190210e06ac25d31a03b26a5c9ce3183258bc4d4";
			if(v=='lvyou') return "e59d4f90274e4b031f388cc3b7eb569d8bfb2892ba55f3be";
			if(v=='nanxing') return "77147b1b970a3f9ad520e1367f07097bd79f1bbdd76c5be2";
			if(v=='nvxing') return "2f538019f5471295ce73d4e68dd29fc2d65aafd82c69849a";
			if(v=='qiche') return "ff35111cb14bebd7a52cc78701316aeadd119a253631a3ed";
			if(v=='qinggan') return "540bf51bfd6e153cfa61c164be4c1c84d4f3427cefcc30a6";
			if(v=='shishang') return "0195b8ff4376c919fea070a703e9c9a1fa9e9cfba84557ee";
			if(v=='tech') return "e3e4ff1bf340fbf6f87060f8283705a117e8fbaeca4f06ad";
			if(v=='wenhua') return "88d731513ffd419f83b9913eb5cda1c6b36da19fe6d4e905";
			if(v=='yishu') return "ba0404c6cfdd37721021498f51d67fbed35344cf6007d8fc";
			if(v=='waiwen') return "92f368133835bb63fffc61b47140d09f26307dcd75785858";
			if(v=='shenghuo') return "3d08f13b07a961defebb97df851eb87f311b6e0a4d2ca679";
			if(v=='xueshu') return "b85e8e8c09864837e5f9029b4a3d76a8dd378e80891e695a";
			if(v=='yule') return "66a1d57ba98abcb5bb04cef9bce55ef722d8077e7d62af19";
		}else
			return null;
		
	}
	
	$("#PublicationComment").find("textarea").live("focus",function(){
		if($("#userBar").attr("style")==undefined){
			gotoLogin("用户没有登录！");
			return;
		}
		
	});
		
	$("#PublicationComment").find("textarea").live("keyup",function(){
		var len=$(this).val().length;
		var num =196-len;
		if(num<0){
			$(this).val($(this).val().substring(0,196));
		}
		$(this).parents(".sendBox").find(".txtNum").html("您还可以输入<span>"+num+"</span>字");
	});
	
	$("#PublicationComment").find(".btnGB").live("click",function(){
		if($("#userBar").attr("style")==undefined){
			gotoLogin("用户没有登录！");
			return;
		}
		
		var domain=(document.location+"").split("/")[2];
		var appserverurl="http://"+domain;
		var uri=appserverurl +"/common-comment!insert.action";
		var condition={"content":$("#PublicationComment").find("textarea").val(),"objectId":$("#PublicationComment").attr("pid"),"type":"magzine","param":"0"};
		
		$.ajax({
			url :uri,
			type : "post",
			data : condition,
			dataType : 'json',
			async: false,
			success : function (rs){
				$("#PublicationComment").find("textarea").val('');
			}
		});
		
		$.ajax({
			url : appserverurl +"/common-comment!publicationComment.action",
			type : "post",
			data : {"publicationId":$("#PublicationComment").attr("pid"),"begin":0},
			success : function (html){
				$("#p_comment").html(html);
			}
		});
	});
	
	$("._read").live("click",function(){
		window.open($(this).attr("url"));
	});
	
	$(".conReplyMore").live("click",function(){
		var begin = $(".bl").length;
		if(begin%20!=0)
			return;
		var domain=(document.location+"").split("/")[2];
		var appserverurl="http://"+domain;
		$.ajax({
			url : appserverurl +"/common-comment!publicationComment.action",
			type : "post",
			data : {"publicationId":$("#PublicationComment").attr("pid"),"begin":begin},
			success : function (html){
				$("#p_comment").append(html);
			}
		});
	});
	
});

function getCodeInfo(){
	$("#tipInfo").show();
	return $("#codeInfo").val();
}















