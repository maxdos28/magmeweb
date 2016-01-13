//随机显示方法
function fnRandom(id){
$(id).find("a").css({display:"none"});
var ran = Math.floor(Math.random()*$(id).find("a").length+1);
$(id).find("a").eq(ran-1).css({display:"block"});
}

//判断字符是否是中文
function isChinese(str){
    var lst = /[u00-uFF]/;      
    return !lst.test(str);     
}

function navShow(){
	$("#navBtn").fadeOut(100);
	if ($("body>.bodyMask").length == 0 && $("#nav .classification").length == 0) {
		$("body").append("<div class=\"bodyMask\"></div>");
	}
	$("body>.bodyMask").css({
		background: '#000000',
		position: 'absolute',
		left: '0',
		top: '0',
		filter: 'alpha(opacity=0)',
		'-moz-opacity': 0,
		opacity: 0,
		zIndex: 777
	});
	$("body>.bodyMask").fadeTo(300,0.5)
	setBodyMaskSize();
	$(window).bind("resize", setBodyMaskSize);
	$("#nav").animate({top:59},1000,"easeOutQuart").data("isOpen", true);
}
function navHide(navHeight){
	$("body>.bodyMask").animate({opacity:0},{duration:200,complete:function(){
		if ($("body>.bodyMask").length != 0) {
			$("body>.bodyMask").remove();
		}
		$(window).unbind("resize", setBodyMaskSize);
	}});
	$("#navBtn").fadeIn(500);
	$("#nav").animate({top:-navHeight},300).data("isOpen", false);
	$("#nav .navRight .item").hide().eq(0).show();
	$("#nav .navLeft li").removeClass("current").eq(0).addClass("current");
	$(".header .nav .arrow").animate({top:15},300);
}
function setBodyMaskSize() {
	$(".bodyMask").css({
		height: $(document).height() > $(window).height() ? $(document).height() :  $(window).height(),
		width: $(window).width()
	});
}
//select for ie6
function fnHideSelect(){$("select").not("#fancybox-wrap select").hide()}
function fnShowSelect(){$("select").not("#fancybox-wrap select").show()}



var browser={
    versions:function(){ 
       var u = navigator.userAgent, app = navigator.appVersion; 
       return {//移动终端浏览器版本信息 
            trident: u.indexOf('Trident') > -1, //IE内核
            presto: u.indexOf('Presto') > -1, //opera内核
            webKit: u.indexOf('AppleWebKit') > -1, //苹果、谷歌内核
            gecko: u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1, //火狐内核
            mobile: !!u.match(/AppleWebKit.*Mobile.*/)||!!u.match(/AppleWebKit/), //是否为移动终端
            ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端
            android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, //android终端或者uc浏览器
            iPhone: u.indexOf('iPhone') > -1, //是否为iPhone或者QQHD浏览器
            iPad: u.indexOf('iPad') > -1, //是否iPad
            webApp: u.indexOf('Safari') == -1 //是否web应该程序，没有头部与底部
        };
    }(),
    language:(navigator.browserLanguage || navigator.language).toLowerCase()
} 




$(function() {
	
	//delete comment
	$("del[commentId]").unbind("click").live("click",function(){
		var commentId=$(this).attr("commentId");
		var type=$(this).attr("type");
		$.ajax({
			url:SystemProp.appServerUrl+"/common-comment!delete.action",
			type : "get",
			data : {"id":commentId,"type":type},
			success: function(rs){
				if(rs.code!=200){
					alert(rs.message);
				}else{
					var $conReply=$("del[commentId="+commentId+"][type="+type+"]").parents(".conReply");
					var comentlength=$conReply.find(".bl").length+$conReply.find(".br").length;
					if(comentlength>1){
						//剩下不止一个评论
						$("del[commentId="+commentId+"][type="+type+"]").parent("span").parent("p").parent("div").remove();	
					}else{
						//剩下只有一个评论
						$conReply.parents(".forwardRight").prepend("<div class='nullInfo'>还没有任何评论哦~ </div>");
						$conReply.parents(".conReplyOuter").remove();
					}
						
				}
				
			}
		});
	});
	
//	$("iframe[baidu_id]").each(function(){
//		var baidu_id=$(this).attr("baidu_id") || "";
//		if(baidu_id){
//			$(this).attr({
//				"src" : SystemProp.appServerUrl + "/baidu_clb.html#" + baidu_id,
//				"width" : "100%",
//				"height" : "100%",
//				"scrolling" : "no",
////				"style" : "display:block;",
//				"frameborder" : "0"
//			});
//		}
//	});

	
	
	//转发按钮移入
	/*
	var forwardTools = "<div id='shareDiv' style='display:none'><p><a title='新浪微博' tp='tsina' href='javascript:void(0)' class='weibo png'></a><a href='javascript:void(0)' tp='tqq' title='腾讯微博' class='tencent png'></a><a title='开心网' tp='kaixin'  href='javascript:void(0)' class='kaixin png'></a><a title='人人网' tp='renren' href='javascript:void(0)' class='renren png'></a><a title='M1社区' href='javascript:void(0)' tp='m1' class='m1 png'></a></p></div>";
	$("em.iconShare").mouseenter(function(){
		$(this).append(forwardTools).find("div").fadeIn(200);
	}).mouseleave(function(){
		$(this).find("div").remove();
	});*/
	


	
	

	
	//loading- scrolling
	if($("body").height() < $(window).height()){
		hasData = false;
		$(".pageLoad").fadeOut(500);
	}



	

	//顶部搜索菜单 
	var $headerSearch = $("#headerSearch");
	var searchTimeout;
	$headerSearch.hover(function(){
		var obj = $(this);
		obj.find("a").addClass("current");
		obj.find(".box").fadeIn(200);
		obj.find(".in").focus();
		clearTimeout(searchTimeout);
	},function(){
		var obj = $(this);
		searchTimeout = setTimeout(function(){
			obj.find("a").removeClass("current");
			obj.find(".box").fadeOut(200);
			obj.find(".in").val("");
		},1000);
	});

	
	
	
	
	
	
	
	//login-reg--------------------------------------------------------------
	$.jquerytagbox("#popLoginTab",0);
	$("#userReg").click(function(){
//		$("#popLoginRegister").fancybox();
//		$("#_reg").click();
		/*if(window.location.href.indexOf('/sns/')>-1){
			window.location.href=SystemProp.appServerUrl+'/sns/m1-register-and-login.action#register';
			return false;
		}*/
		$("#popRegister").fancybox();
	});
	$("#userLogin").click(function(){
//		$("#popLoginRegister").fancybox();
//		$("#_login").click();
		/*if(window.location.href.indexOf('/sns/')>-1){
			window.location.href=SystemProp.appServerUrl+'/sns/m1-register-and-login.action#login';
			return false;
		}*/
		$("#popLogin").fancybox();
	});
	$("#goLoginPop").click(function(e){
		e.preventDefault();
		$.fancybox.close();//close的时候里面有 动画效果
		setTimeout(function(){
			$("#popLogin").fancybox();
		},800);
	});
	$("#goRegPop").click(function(e){
		e.preventDefault();
		$.fancybox.close();//close的时候里面有 动画效果
		setTimeout(function(){
			$("#popRegister").fancybox();
		},800);
	});
	//register----------------------------------------------------------------
	
	$("#submit",$("#popRegisterForm")).data("isSubmit",true).unbind('click').live('click',function(e){
		e.preventDefault();
		registerSubmit( $(this),$("#popRegisterForm") );	
	});
	//------------------------------register_check------------------------------
	$("input.input",$("#popRegisterForm")).blur(function(e){
		e.preventDefault();
		var $input = $(this);
		var name = $input.attr("name");
		switch (name){
		case 'userName':
		case 'email':
			var elementData = $input.data("elementData");
			var value = $input.val();
			if(!elementData || elementData !== value){
				$input.data("elementData",value);
				checkNameOrEmail($input,$("#popRegisterForm"));
			}
			break;
		case 'password':
			var $check = $("#password").parents("#checkPassword");
			var $pswRight = $check.find(".tipsRight");
			var $pswWrong = $check.find(".tipsWrong");
			var $pswError = $check.find(".tipsError");
			if($input.val().length>=6){
				$pswRight.show();
				$pswWrong.hide();
				$pswError.hide();
			}else{
				$pswRight.hide();
				$pswWrong.show();
				$pswError.show();
			}
			break;
		}
	});
	//----------------------------login-----------------------------
	//user login form
	$("#popLoginForm").submit(function(){
		loginFun();
		return false;
	});
	$("#submit",$("#popLoginForm")).unbind('click').click(function(e){
		e.preventDefault();
		$("#popLoginForm").submit();
	});
	//登出--------------------------------------------------
	$("#logout").bind("click",function(){
		var callback = function(){
			deleteCookie("magemecnUserName");
			deleteCookie("magemecnPassword");
			deleteCookie("magemecnUserType");
			location.reload();
		}
		$.ajax({
			url : "/user!logoutJson.action?random="+Math.random(),
			success: callback
		});
	});
	//forgetPassword----------------------------------------
	$("#popForgetPassword").unbind("click").click(
		function forgetPassword (e){
			e.preventDefault();
			window.location.href = SystemProp.appServerUrl+"/user-findpwd!toFindpwd.action?random="+Math.random();
		}
	);
	
	//thirdLogin-----------------------------------------
	$("a[name^='login']").bind('click',function(e){
		var obj=$(this);
		e.preventDefault();
		var thirdType = $(this).attr("name").split("_")[1];
		switch(thirdType){
			case 'qq':
				qqLogin();
				break;
			case 'weibo':
				scriptLoader.load("http://tjs.sjs.sinajs.cn/open/api/js/wb.js?appkey=477313374",function(){
					obj.unbind().bind('click',sinaLogin);
					obj.click();
				});
				break;
			case 'baidu':
				scriptLoader.load("http://openapi.baidu.com/connect/js/v2.0/featureloader",function(){
					obj.unbind().bind('click',baiduLogin);
					obj.click();
				});
				break;
			case 'renren':
				if(window.XN){
					renrenLogin();
				}else{
					scriptLoader.load("http://static.connect.renren.com/js/v1.0/FeatureLoader.jsp",function(){
						XN_RequireFeatures(["Connect"], function()
						{
							XN.Main.init("fe16782316da45b6add489d51ccb9150", SystemProp.domain+"/xd_receiver.html");
						    obj.unbind().bind('click',renrenLogin);
							obj.click()
						});
					});
				}
				break;
			case 'kaixin':
				kaixinLogin();
				break;
		}
		//统计
		if($ma){
			var url = "/third/login?type="+thirdType;
			$ma(url,"0");
		}
	});
	
	
	$("#popLoginForm,#popRegisterForm").keyup(function(event){
		event.stopPropagation();
		var $form = $(this);
		if (event.keyCode == '13') {
			event.preventDefault();
			$("#submit",$form).click();
	    }
	});
	
	
	//click search--------------------------------------------------------------
	$(".search a.png").unbind('click').live('click',function(e){
		e.preventDefault();
		var queryStr = $(".search input[name=queryStr]").val();
		var searchType = $(".search input[name=searchType]").val();
		if(!queryStr||queryStr==""||!searchType||searchType==""||queryStr=="search"){
			alert("请输入内容");
		}
		else{
			window.location.href="http://www.magme.com/search.action?searchType="+searchType+"&queryStr="+encodeURIComponent(queryStr);
			//$("#searchForm").attr("action",SystemProp.domain+"/search.action");
			//$("#searchForm").submit();
		}
	});
	
	$(".search input[name=queryStr]").unbind('keyup').live('keyup',function(e){
		e.preventDefault();
		if(e.keyCode == 13){
			$(".search a.png").click();
		}
	});
	
	//shareToInternet--------------------------------------------------------------
	$("em[shareTypeId]").live('click',function(e){
		e.stopPropagation();
		e.preventDefault();
		var shareBtn = $(this);
		var arr = shareBtn.attr("shareTypeId").split('_');
		var type = arr[0];
		var objId = arr[1];
	
		var url = '';
		var title = '';
		var imgsrc = '';
		var desc = '';
		if(type=="eve"){
			var $item = shareBtn.parents("div.item");
			if(arr.length == 3){//首页事件/作品分享
				//url = SystemProp.domain + '/index-detail.action?itemId=' + objId + '&type=' + arr[2];
				url = SystemProp.domain + '/sns/c' + objId + '/';
			} else {//其它情况，跳转到阅读器
				url = SystemProp.domain+"/issue-image/"+objId+".html";
			}
			title = $item.find("h2").text();
			imgsrc = $item.find("img").attr("src");
			desc = $item.find("p").text();
		}
		else if(type=="pic"){
			url = SystemProp.domain+"/user-image!show.action?imageId="+objId;
			title = shareBtn.parents(".item").find("h5").text();
			imgsrc = shareBtn.parents(".item").find("img").attr("src");
			desc = shareBtn.parents(".item").find("h5").text();
		}
		
		tagInfo = {
				url: url,
				title: title,
				imgsrc: imgsrc,
				desc: desc
		};
		
		//tagInfo is window's parameter. Default parameter is in useFunction.js
		//everyPage want to share in the Internet, you can change the parameter(tagInfo)
		shareToObj.shareType('tsina',tagInfo);
	});
	
	//fav----------------------------------------------
	$("em[favTypeId],a[favTypeId]").live('click',function(e){
		e.preventDefault();
		e.stopPropagation();
		var $fav = $(this);
		var favTypeId = $fav.attr("favTypeId").split("_");
		var type = favTypeId[0];
		var id = favTypeId[1];
		
		favFun(type,id,$fav);
	});
	
	//新浪微博关注
	$("#magmeWeibo").click(function(e){
		e.preventDefault();
		$("#sinaWeibo").fancybox();
	});	
	$("span[name^='addWeibo_']").live('click',function(e){
		e.preventDefault();
		e.stopPropagation();
		var name = $(this).attr("name");
		var uid = name.split("_")[1]|| "";
		sina_api_follow(uid*1);
	});
	
	//页脚的用户反馈--默认类型为：其他
	$("#foot_userFeedBack").click(function(){
		var form=$("#foot_feedbackForm");
		var categoryId="3";
		var content=form.find("textarea[name='foot_content']").val();
		if(content=='意见反馈') content="";
		if(!$.trim(content)){
			alert("意见内容不允许为空",function(){
				form.find("textarea[name='foot_content']").focus();
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
				alert("感谢您提交意见反馈");
				form.find("textarea[name='foot_content']").val("意见反馈");
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
	
	
	//用户反馈
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
			
			$("#feedbackFormCancelBtn",content).click(function(e){
				e.preventDefault();
				$.fancybox.close();
			});
			$("#feedbackFormSubmitBtn",content).click(function(e){
				e.preventDefault();
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
	
	//获取用户登录状态
	getUserAjax();

});




