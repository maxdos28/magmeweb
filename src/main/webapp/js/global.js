


$(function(){
	//"td" add class name---------------------------------------------------------------------//
	fnReadyTable();
	
	//top10
	$("ol.top10").each(function(){
		var i=0;
		$(this).find("li").slice(0,3).addClass("top");
		$(this).find("li").each(function(){
			i++;
			$(this).prepend("<span>"+i+"</span>");
		});
		
	});
	
	$("#publisherSignIn").click(adLogin);
	$("#publisherLogin").keyup(function(event){
		event.stopPropagation();
		var $form = $(".userBar .box:visible:first").find("form");
		if (event.keyCode == '13') {
			event.preventDefault();
			$("#publisherSignIn").click();
	    }
	});
	
	$("#adloginBtn").click(function(e){
		  getAuthCode($("#reqisterCode") );
		}
	);
	
	$("#adlogout").click(function(e){
		function adLogoutSuccess(){
			var url="/publish/pcenter-publisher!index.action?random=";
			window.location.href = SystemProp.appServerUrl+url+Math.random();
		}
		$.ajax({
			url: SystemProp.appServerUrl+"/publish/front-publisher!logoutJson.action",
			type: "POST",
			data: {},
			success: adLogoutSuccess
		});
	});
	//publisher改变注册码
	$("a[name='getPublisherAuthcode']").unbind('click').bind('click',function(e){
		e.preventDefault();
		getAuthCode($("#reqisterCode"));
	});
	//for header & footer static
	/*if($.browser.version == "6.0" && $.browser.msie){
		var $header = $("body>.header");
		var $footer = $("body>.footerStatic");
		var browserHeight = $(window).height();
		$footer.css({top:browserHeight-25,position:"absolute"});
		$(window).scroll(function(){
			$header.css({top:$(window).scrollTop()});
			$footer.css({top:$(window).scrollTop()+browserHeight-25});
		});
	}*/
	
	//header_userCenter---------------------------------
	$("#userCenter").click(function(e){
		e.preventDefault();
		if( !hasUserLogin() ){
			getAuthCode ( $("#loginCode").eq(0) );
			gotoLogin("请用户登录！");
		}else{
			window.location.href = SystemProp.appServerUrl+"/user-center!index.action";
		}
	});
		
	//for header userBar
	var $userBar = $(".header .userBar");
	var $userBarA = $(".header .userBar>li>a.sub");
	
	$userBarA.click(function(e){
//		e.preventDefault();
		e.stopPropagation();
		if(!$(this).hasClass("noSub")){
			userBarAClick(this);
		}
	});
	function userBarAClick (obj){
		var $subA = $(obj);
		if(!$subA.hasClass("current")){
			$userBar.find("a.sub").removeClass("current")
			$userBar.find(".box").hide();
			$subA.addClass("current");
			$subA.parents("li").find(".box").show();
			fnbodyClick(true);
		}else{
			$subA.removeClass("current")
			$subA.parents("li").find(".box").hide();
			fnbodyClick(false);
		}
		
	}
	
	$userBar.find("form").keyup(function(event){
		event.stopPropagation();
		var $form = $(".userBar .box:visible:first").find("form");
		if (event.keyCode == '13') {
			event.preventDefault();
			if($form.attr("id") == 'logInForm'){
				$("#signIn",$form).click();
			}else{
				$("#submit",$form).click();
			}
	    }
	});
	
	$("#submit",$("#registerForm")).data("isSubmit",true).unbind('click').live('click',function(e){
		e.preventDefault();
		registerSubmit( $(this),$("#registerForm") );
		
	});
	
	// user login form
	$("#logInForm").submit(function(){
		loginFun();
		return false;
	});
	$("#signIn",$("#logInForm")).unbind('click').click(function(e){
		e.preventDefault();
		$("#logInForm").submit();
	});
	
	// publisher & advertiser login form
	$("#logInForm2").submit(function(){
		loginFun2();
		return false;
	});
	$("#signIn",$("#logInForm2")).unbind('click').click(function(e){
		e.preventDefault();
		$("#logInForm2").submit();
	});

	$(".search a.btn").unbind('click').live('click',function(){
		var queryStr = $(".search input[name=queryStr]").val();
		var searchType = $(".search input[name=searchType]").val();
		if(!queryStr||queryStr==""||queryStr=="杂志,图片,标签,用户"||!searchType||searchType==""){
			alert("请输入内容");
		}
		else{
			window.location.href=SystemProp.domain+"/search.action?searchType="+searchType+"&queryStr="+encodeURIComponent(queryStr);
			//$("#searchForm").attr("action",SystemProp.domain+"/search.action");
			//$("#searchForm").submit();
		}
	});
	
	$(".search input[name=queryStr]").unbind('keyup').live('keyup',function(e){
		if(e.keyCode == 13){
			$(".search a.btn").click();
		}
	});
	
	//for footerMini
	var $fVavItem = $(".footerMini .nav>ul>li");
	$fVavItem.hover(
		function(){
			$(this).addClass("current").find("ul").show();
		},function(){
			$(this).removeClass("current").find("ul").hide();
		}
	);
	
	if($("#tagWall").length>0){
		//for 评论开关------------
		$("#btnMsgSwitch").toggle(
				function(){
					$(this).addClass("btnOff");
					$("#tagWall .item .msg").hide();
					$('#tagWall').masonry({itemSelector: '.item'});
					setCookie("msgSwitch",'1',new Date("December 31,2120"));
				},function(){
					$(this).removeClass("btnOff");
					$("#tagWall .item .msg").show();
					$('#tagWall').masonry({itemSelector: '.item'});
					setCookie("msgSwitch",'0',new Date("December 31,2120"));
				}
		);
		//after the btnMsgSwitch's toggle the method
		if(getCookie("msgSwitch") === '1' ){
			$("#btnMsgSwitch").click();
		}
	}
	
	//点我试试
	//$("span.click").eq(0).addClass("autoMove");
//	var $autoMove = $("span.autoMove");
//	var autoMoveText = ":分享，标签，往期，订阅，消息等功能";
//	$autoMove.find("a").append(autoMoveText);
//	var autoMoveHtml = 	$("span.autoMove").eq(0).html();
//	if(!$autoMove.parent().parent().hasClass("typeEve") && !$autoMove.parent().parent().parent().hasClass("deskBig")){
//		$autoMove.eq(0).html("<marquee onMouseOver='this.stop()' onMouseOut='this.start()' scrollamount='3' direction='left'>" + autoMoveHtml + "</marquee>");
//	}
	
	//
	
	
	//for showBookBar//
	var $showBar = $(".showBar");
	if($showBar.length > 0){
		var $bookBar = $showBar.find(".bookBar");
		var bookBarLock=0;
		var bookBarHeight;
		$showBar.live('mouseover',function(){
			if(!$(this).hasClass("doing") && !$(this).hasClass("delete")){
				$(this).find(".bookBar").show();
				var mgzNameLength = $(this).find("span").html().replace(/[\u4E00-\u9FA5]/g,"00").length;
			}
		});
		$showBar.live('mouseout',function(){
			$(this).find(".bookBar").hide();
		});
	}

	//thirdLogin-----------------------------------------
	$("a[name^='login']").live('click',function(e){
		e.preventDefault();
		var thirdType = $(this).attr("name").split("_")[1];
		switch(thirdType){
			case 'qq':
				qqLogin();
				break;
			case 'weibo':
				sinaLogin();
				break;
			case 'baidu':
				baiduLogin();
				break;
			case 'renren':
				renrenLogin();
				break;
			case 'kaixin':
				kaixinLogin();
				break;
		}
	});
	//登出--------------------------------------------------
	$("#logout").bind("click",function(){
		var callback = function(){
			deleteCookie("magemecnUserName");
			deleteCookie("magemecnPassword");
			location.reload();
		}
		$.ajax({
			url : SystemProp.appServerUrl+"/user!logoutJson.action?random="+Math.random(),
			success: callback
		});
	});
	
	//forgetPassword----------------------------------------
	$("#forgetPassword").unbind("click").click(
		function forgetPassword (e){
			e.preventDefault();
			window.location.href = SystemProp.appServerUrl+"/user-findpwd!toFindpwd.action?random="+Math.random();
		}
	);
	$("#newUserReg").click(function(e){
		e.preventDefault();
		$("#registerBtn").click();
	});

	//register_check_userNameAndEmail -----------------------------------------------
	$("#userName,#email",$("#registerForm")).bind('blur',function(){
		var elementData = $(this).data("elementData");
		var value = $(this).val();
		if(!elementData || elementData != value){
			$(this).data("elementData",value);
			checkNameOrEmail($(this),$("#registerForm"));
		}
	});
	
	$("span.fav").live('click',function(){
		var $fav = $(this);
		var favTypeId = $fav.attr("favTypeId").split("_");
		var type = favTypeId[0];
		var id = favTypeId[1];
		
		favFun(type,id,$fav);
	});
	
	//issueRead---------------------------------------------------------------------
	$("img[issueRead]").live('click',function(e){
		e.preventDefault();
		var issueId = $(this).attr("issueRead");
		
		window.location.href = SystemProp.appServerUrl+"/publish/mag-read.action?id="+issueId;
	});
	//picShow----------------------------------------------------------------------
	$("img[picShow]").live('click',function(e){
		e.preventDefault();
		var picId = $(this).attr("picId"); 
		window.location.href = SystemProp.appServerUrl+"/user-tag!show.action?id="+picId;
	});
	
	//shareToInternet--------------------------------------------------------------
	$("a[tagShare]").live('click',function(e){
		e.preventDefault();
		var shareBtn = $(this);
    	var type = shareBtn.attr("tagShare");
    	//tagInfo is window's parameter. Default parameter is in useFunction.js
    	//everyPage want to share in the Internet, you can change the parameter(tagInfo)
    	shareToObj.shareType(type,tagInfo);
	});

	
	$("span[clickTypeId]").unbind('click').live('click',function(e){
		e.preventDefault();
		var $clickEle = $(this);
		var clickType = $clickEle.attr("clickTypeId").split('_')[0];
		var clickId = $clickEle.attr("clickTypeId").split('_')[1];
		
		if(clickType){
			var dialogId = "";
			switch(clickType){
			case 'mag':
				dialogId="shareMgzDialog";
				break;
			case 'pic':
				dialogId="shareTagDialog";
				break;
			case 'eve':
				dialogId="shareEventDialog";
				break;
			}
			
			function showDialog(rs){
				$("#"+dialogId).remove();
				$("body").append(rs);
				$("#"+dialogId).fancybox();
				$.jqueryInputTips();
				
				tagInfo = {
						url: $(rs).find("div.left a.img").attr("href"),
						title: $(rs).find("div.left h5 a").text(),
						imgsrc: $(rs).find("div.left a.img img").attr("src"),
						desc:''
					};
			}
			
			var callback = function(result){
				if(!result) return;
				$clickEle.data("ajaxData",result);
				showDialog(result);
			};
			
			
			var url = SystemProp.appServerUrl+"/click-try.action";
			$.ajax({
				data: {clickType:clickType, clickId:clickId},
				url: url,
				type : "POST",
				dataType : "html",
				async: false,
				success: callback
			});
		}
	});	
	
	$("a[tagTypeName],#addTagSubmit").unbind('click').live('click',function(e){
		//$("#clickDialog").fancybox();
		e.preventDefault();
		
		var tagType = '';
		var objectId = '';
		var tagName = '';
		var tagTypeName = $(this).attr("tagTypeName");
		if(tagTypeName && tagTypeName!=''){
			tagType = tagTypeName.split('_')[0];
			objectId = tagTypeName.split('_')[1];
			tagName = tagTypeName.split('_')[2];
		}
		else{
			tagType = $("input[name=tagType]").val();
			objectId = $("input[name=objectId]").val();
			tagName = $("input[name=tagName]").val();
		}
		
		if(tagName==""||tagName=="请输入您的自定义标签"){
		    alert("标签内容不能为空");
		    return;
		}
		if(tagName.length>6){
			alert("最多6个字符");
			return;
		}
		
		
		if(tagType && objectId && tagName){
			var url = SystemProp.appServerUrl+"/click-try-ajax!addTagJson.action";
			var callback = function(result){
				var code = result.code;
				var message = result["message"];
				if(code == 400){
					var fancyboxId = $(".popContent:visible:first").attr("id");
					//window.tryClick 用于页面中使用这个功能需要用户登录的参数传递
					window.tryClick = {"isShow":true,"fancyboxId":fancyboxId};
					
					$.fancybox.close();
					gotoLogin(message);
				}else if(code == 300||code==500){
					alert(message);	
				}else if(code == 200){
					var tagList = result.data.tagList;
					alert("添加成功",function(){
						$("#clickTryTagList").empty();
						//var tagList = result.data.tagList;
						for(var i=0;i<tagList.length;i++){
							var tag = tagList[i];
							if(i<6){
								$("#clickTryTagList").append("<li><a tagTypeName=\""+tagType+"_"+objectId+"_"+tag.name+"\" title=\"共有"+tag.groupNum+"人添加此标签\" href=\"javascript:void(0)\" class=\"tag"+(i+1)+"\"</a>"+tag.name+"</a></li>");
							}
							else{
								$("#clickTryTagList").append("<li><a tagTypeName=\""+tagType+"_"+objectId+"_"+tag.name+"\" title=\"共有"+tag.groupNum+"人添加此标签\" href=\"javascript:void(0)\"</a>"+tag.name+"</a></li>");
							}
						}
						//$.fancybox.close();
					});
					$.jqueryInputTips();
				}
				return;
			};
			
			$.ajax({
				data: {tagType:tagType, objectId:objectId, tagName:tagName},
				url: url,
				type : "POST",
				dataType : "json",
				success: callback
			});
		}
	});
	
	$("a[followedUserId]").unbind('click').live('click',function(e){
		//$("#clickDialog").fancybox();
		e.preventDefault();
		
		var isFollow = $(this).attr("followedUserId").split('_')[0];
		var type = $(this).attr("followedUserId").split('_')[1];
		var objectId = $(this).attr("followedUserId").split('_')[2];
		
		if(isFollow && type && objectId){
			var url = "";
			if(isFollow==0){
				url = SystemProp.appServerUrl+"/user-follow!addFollowJson.action";
			}
			else if(isFollow==1){
				url = SystemProp.appServerUrl+"/user-follow!deleteJson.action";
			}
			var that = this;
			var callback = function(result){
				var code = result.code;
				var message = result["message"];
				if(code == 400){
					var fancyboxId = $(".popContent:visible:first").attr("id");
					//window.tryClick 用于页面中使用这个功能需要用户登录的参数传递
					window.tryClick = {"isShow":true,"fancyboxId":fancyboxId};
					
					$.fancybox.close();
					gotoLogin(message);
				}else if(code == 300||code==500){
					alert(message);	
				}else if(code == 200){
					alert("成功",function(){
						if(isFollow==0){
							$(that).attr("followedUserId","1_"+type+"_"+objectId);
							if(type==2){
								$(that).text("取消关注");
							}
							else if(type==1){
								$(that).text("取消好友");
							}
						}
						else if(isFollow==1){
							$(that).attr("followedUserId","0_"+type+"_"+objectId);
							if(type==2){
								$(that).text("我要关注");
							}
							else if(type==1){
								$(that).text("加为好友");
							}
						}
						
						//$.fancybox.close();
					});
				}
				return;
			};
			
			$.ajax({
				data: {type:type, objectId:objectId},
				url: url,
				type : "POST",
				dataType : "json",
				success: callback
			});
		}
	});	

	$("a[subscribePubId]").unbind('click').live('click',function(e){
		//$("#clickDialog").fancybox();
		e.preventDefault();
		
		var isSubscribe = $(this).attr("subscribePubId").split('_')[0];
		var issueId = $(this).attr("subscribePubId").split('_')[1];
		if(isSubscribe && issueId){
			var url = "";
			if(isSubscribe==0){
				url = SystemProp.appServerUrl+"/user-subscribe!addJson.action";
			}
			else if(isSubscribe==1){
				url = SystemProp.appServerUrl+"/user-subscribe!deleteJson.action";
			}
			var that = this;
			var callback = function(result){
				var code = result.code;
				var message = result["message"];
				if(code == 400){
					var fancyboxId = $(".popContent:visible:first").attr("id");
					//window.tryClick 用于页面中使用这个功能需要用户登录的参数传递
					window.tryClick = {"isShow":true,"fancyboxId":fancyboxId};
					
					$.fancybox.close();
					gotoLogin(message);
				}else if(code == 300||code==500){
					alert(message);	
				}else if(code == 200){
					alert("成功",function(){
						if(isSubscribe==0){
							$(that).attr("subscribePubId","1_"+issueId);
							$(that).text("取消订阅");
						}
						else if(isSubscribe==1){
							$(that).attr("subscribePubId","0_"+issueId);
							$(that).text("我要订阅");
						}
						
						//$.fancybox.close();
					});
				}
				return;
			};
			
			$.ajax({
				data: {issueId:issueId},
				url: url,
				type : "POST",
				dataType : "json",
				success: callback
			});
		}
	});		
	
    //弹出站内消息发送框
    $("a[msg]").unbind('click').live('click', function(){
        if($(this).nextAll(".sendMsg").hasClass("current")){
            $(this).nextAll(".sendMsg").slideUp(300).removeClass("current");
        }else{
            $(this).nextAll(".sendMsg").slideDown(300).addClass("current");
            var that = this;
            $("#sendMsgSubmit").unbind('click').bind('click',function(){
                var toType = $(that).attr("msg").split('_')[0];
                var objectId = $(that).attr("msg").split('_')[1];
                var content = $("input[name=content]").val();
                var that2 = this;
            	if(!content||content==''||content=='请输入意见内容'||content=='请输入短信内容'){alert("请输入内容");return;}
    			var callback = function(result){
    				var code = result.code;
    				var message = result["message"];
    				if(code == 400){
    					var fancyboxId = $(".popContent:visible:first").attr("id");
    					//window.tryClick 用于页面中使用这个功能需要用户登录的参数传递
    					window.tryClick = {"isShow":true,"fancyboxId":fancyboxId};
    					
    					$.fancybox.close();
    					gotoLogin(message);
    				}else if(code == 300||code==500){
    					alert(message);	
    				}else if(code == 200){
    					alert("发送成功",function(){
    		                $(that2).parent().fadeOut(500).removeClass("current");
    		                setTimeout(function(){$.jqueryInputTips()},500);
    					});
    				}
    				return;
    			};
            	
    			var url = SystemProp.appServerUrl + "/user-message!sendJson.action";
    			$.ajax({
    				data: {toType:toType, toUserIds:objectId, content:content},
    				url: url,
    				type : "POST",
    				dataType : "json",
    				success: callback
    			});
            });
            
        }
    });

	$("#hasEventPrize").unbind('click').live('click',function(e){
		//$("#clickDialog").fancybox();
		e.preventDefault();
		
		
			var url = SystemProp.appServerUrl+"/user-event!hasPrizeJson.action";
			var that = this;
			var callback = function(result){
				var code = result.code;
				var message = result["message"];
				if(code == 400){
					gotoLogin(message);
				}else if(code == 300||code==500){
					alert(message);	
				}else if(code == 200){
					var prizeMsg = "尊敬的米客，";
					var prizeList = result.data.eventLlkList;
					for(var i=0;i<prizeList.length;i++){
						var prize = prizeList[i];
						var eventName = '';
						switch(prize.code){
							case 1:eventName = '杂志连连看';break;
							case 2:eventName = '1元话费';break;
							case 3:eventName = '神圣光棍节';break;
						}
						prizeMsg += "您在"+prize.eventDate.substring(0,10)+"的麦米"+eventName+"活动中夺得第"+prize.rank+"名，获得麦米送出的精美礼品一份。<br><br>"
					}
					alert(prizeMsg, null, 300,"left");
				}
				return;
			};
			
			$.ajax({
				data: {},
				url: url,
				type : "POST",
				dataType : "json",
				success: callback
			});
		});
    
    
	$("span[backPubId]").unbind('click').live('click',function(e){
		e.preventDefault();
		var pubId = $(this).attr("backPubId");
		if(pubId){
				window.location.href=SystemProp.appServerUrl+"/search!byPublicationId.action?publicationId="+pubId;
		}
	});    
	//userFeedBack-----------------------------------------------------------------
	$("#userFeedBack").click(function(e){
		e.preventDefault();
		var content = $("#fancybox-content");
		if($("#feedBackDialog").length>0) {
			$("#feedBackDialog").fancybox();
			return;
		}
		
		var callback = function(result){
			$("body").append(result);
			$feedBackDialog = $("#feedBackDialog");
			$feedBackDialog.fancybox();
			
			$("#feedbackFormCancelBtn",content).click(function(){$.fancybox.close();});
			$("#feedbackFormSubmitBtn",content).click(function(){
				var form=$("#feedbackForm");
				var categoryId=form.find("select[name='categoryId']").val();
				var content=form.find("textarea[name='content']").val();
				
				if(!categoryId||!(/^\d+$/.test(categoryId))){
					alert("请选择意见类别",function(){
						form.find("select[name='categoryId']").focus();
					});
					return;
				}
				
				if(!$.trim(content)){
					alert("意见内容不允许为空",function(){
						form.find("textarea[name='content']").focus();
					});
					return;
				}
				
				var callback = function(result){
					var code = result.code;
					var message = result["message"];
					if(code == 400){
						gotoLogin(message);
					}else if(code == 300||code==500){
						alert(message);	
					}else if(code == 200){
						alert("感谢您提交意见反馈",function(){
							$.fancybox.close();
						});
					}
					return;
				};
				
				$.ajax({
					url: SystemProp.appServerUrl+"/feed-back!saveJson.action",
					type: "POST",
					data: {"categoryId":categoryId,"content":content},
					success: callback
				});
			});
		};
		
		$.ajax({
			url: SystemProp.appServerUrl+"/feed-back!edit.html",
			type : "POST",
			dataType : "html",
			success: callback
		});
	});
	
	$("#magmeWeibo").click(function(e){
		e.preventDefault();
		
		WB2.anyWhere(function(W){
		    // 获取评论列表
		W.parseCMD("/friendships/create.json", function(sResult, bStatus){
		    if(bStatus == true) {
				alert("关注成功");
		    }
		    else if(sResult.error.indexOf('40302')>-1){
		    	alert("请先登录新浪微博");
		    	window.open("http://www.weibo.com/login.php?url=http://www.weibo.com/magme");
		    }
		    else{
		    	alert(sResult.error.substring(6));
		    }
		},{
			source : '477313374',
			id : 2173428060
		},{
		    method: 'post'
		});
		});
	    
	});
	//获取用户登录状态
	getUserAjax();	
});