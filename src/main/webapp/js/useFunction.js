//share's parameter
var tagInfo = {
		url: 'http://www.magme.com',
		title: "看杂志 ·上麦米网",
		imgsrc:"http://www.magme.com/images/logo.gif",
		desc:''
	};
//isIE6
var isIE6 = $.browser.msie && $.browser.version < 7 && !window.XMLHttpRequest;
//将该字符串反序排列-----------------------------------------------------
String.prototype.reverse = function(){
	var aStr="";
	for(var i=this.length-1;i>=0;i--){
		aStr=aStr.concat(this.charAt(i));
	}
	return aStr;
};

//use to upload the file's bar-------------------------------------
function getUUID(){
	 uuid = "";
	 for (i = 0; i < 32; i++) {
	  uuid += Math.floor(Math.random() * 16).toString(16);
	 }
	 return uuid;
}

//To add cookie information to the HTTP header need to use the following Syntax:
//
//document.cookie = "name=value; expires=date; path=path;domain=domain; secure";
//
//This function sets a client-side cookie as above.  Only first 2 parameters are required
//Rest of the parameters are optional. If no CookieExp value is set, cookie is a session cookie.
//******************************************************************************************
function setCookie(CookieName, CookieVal, CookieExp, CookiePath, CookieDomain, CookieSecure){
	CookiePath = '/';
	if(CookieVal){
		CookieVal = CookieVal.reverse();
	}
	var CookieText = escape(CookieName) + '=' + escape(CookieVal); //escape() : Encodes the String
	CookieText += (CookieExp ? '; EXPIRES=' + CookieExp.toGMTString() : '');
	CookieText += (CookiePath ? '; PATH=' + CookiePath : '');
	CookieText += (CookieDomain ? '; DOMAIN=' + CookieDomain : '');
	CookieText += (CookieSecure ? '; SECURE' : '');
	document.cookie = CookieText;
}

//This functions reads & returns the cookie value of the specified cookie (by cookie name) 
function getCookie(CookieName){
	var CookieVal = null;
	if(document.cookie)	   //only if exists
	{
	    var arr = document.cookie.split((escape(CookieName) + '=')); 
	    if(arr.length >= 2)
	    {
    	    var arr2 = arr[1].split(';');
		    CookieVal  = unescape(arr2[0]); //unescape() : Decodes the String
	    }
	}
	if(CookieVal!=null){
		CookieVal = CookieVal.reverse();
	}
	return CookieVal;
}

//To delete a cookie, pass name of the cookie to be deleted --------
function deleteCookie(CookieName){
	var tmp = getCookie(CookieName);
	if(tmp) 
	{ 
		setCookie(CookieName,tmp,(new Date(1))); //Used for Expire 
	}
}
 
//get url value by given name --------------------------------------
function getUrlValue(name){ 
	var r = location.search.substr(1).match(new RegExp("(^|&)"+ name +"=([^&]*)(&|$)")); 
	if (r!=null) return unescape(r[2]); return null;
}

//判断时候有fancybox和lightbox 的遮挡层
function hasOverlay(){
	return ($("#fancybox-overlay").is(":visible")&& $("#fancybox-overlay").height()>30) || $(".eaMask").is(":visible");
}


//checkName or Email-----------------------------------------------
function checkNameOrEmail(obj,register){
	if(!obj) return;
	var tips = obj.attr('tips');
	var val = obj.val();
	if($.trim(val) == '' || val == null){
		tips && obj.css({color:'#ccc'}).val(tips);
		return;
	}
	//如果验证过的就不做验证
	var objData = obj.data("value");
	if(objData != val){
		obj.data("value",val);
	}else{
		return;
	}
	
	var tipError = $(".tipsError",register);
	var id = obj.attr("id");
	var callback = function(result){
		if(result.message === 'success'){
			if(id == 'userName'){
				$("#checkName",register).show().removeClass("tipsWrong").addClass("tipsRight");
			}else{
				$("#checkEmail",register).show().removeClass("tipsWrong").addClass("tipsRight");
			}
			tipError.hide();
		}else{
			if(id == 'userName'){
				$("#checkName",register).show().removeClass("tipsRight").addClass("tipsWrong");
			}else{
				$("#checkEmail",register).show().removeClass("tipsRight").addClass("tipsWrong");
			}
			tipError.html(result.data[id]).show();
		}
	};
	var userData = {};
	userData[id] = val;
	$.ajax({
		url : SystemProp.appServerUrl+"/user!validateUserJson.action",
		type : "POST",
		dataType:'json',
		data : userData,
		success: callback
	});
}


/**
 * user_login--------------------------------------------------------------
 */
//登入成功--------------------------------------------------------
function loginSuccess (user){
	if(!user) return;
	$("#userBar").hide();
	$("#loginBar").show();
	
	if(!!user.avatar30){
		$("#avatar30").attr("src",SystemProp.profileServerUrl+user.avatar30);
	}
	$("#nickName").html(user.nickName);
	var messageNum = (!!user.statsMap) ? user.statsMap.messageNum : 0;
	if(messageNum*1>0){
		$("#newMessageNum").show().html("<sub></sub><em>("+messageNum+")</em>");
	}else{
		$("#newMessageNum").hide();
	}
	//用户喜欢的一些数据
	window.statsMap = user.statsMap;
	chooseFav(user.statsMap);
	//click
	if(window.tryClick && window.tryClick.isShow){
		$("#"+window.tryClick.fancyboxId).fancybox();
		window.tryClick.isShow = false;
	}
	
	
//	alert("普通用户登录成功后的一些操作");
}
function chooseFav(statsMap){
	if(!statsMap) return;
	var eveList = statsMap.enjoyEventList || [];
	var picList = statsMap.enjoyImageList || [] ;
	var magList = statsMap.enjoyIssueList || [];
	var eveLength = eveList.length;
	var picLength = picList.length;
	var magLength = magList.length;
	for(var i=0;i<eveLength;i++){
		$("span.fav[favTypeId='eve_"+eveList[i]+"']").addClass("favCurrent");
	}
	for(var j=0;j<picLength;j++){
		$("span.fav[favTypeId='pic_"+picList[j]+"']").addClass("favCurrent");
	}
	for(var k=0;k<magLength;k++){
		$("span.fav[favTypeId='mag_"+magList[k]+"']").addClass("favCurrent");
	}
}

function loginPubish (publisher){
	$("#menu").find("li").hide();
	$("#menu").find(".publisher").show();
	
	//alert("出版商登录成功后的一些操作");
}
//获取用户当前登录状态
function getUserAjax(){
	$.ajax({
		url : "/user!getReaderJson.action",
		type : "POST",
		dataType:'json',
		async: false,
		success: function(result){
			if(!result) return;
			var data = result.data;
			var code = result.code;
			var checkUserRole = false;
			if(code==200){
				if(data && data.user){
					loginSuccess(data.user);
					checkUserRole = true;
					return;
				}
				if(data && data.publisher){
//					loginPubish(data.publisher);
					return;
				}
			}
			if(checkUserRole){
				autoLogin();	
			}
			
		}
	});
}
//auotoLogin  by cookie----------------------------------------------------------
function autoLogin(){
	var userName = getCookie("magemecnUserName")||"",
		password = getCookie("magemecnPassword")||"";
	if(userName=="" || password=="") return;
	$.ajax({
		url : SystemProp.appServerUrl+"/user!loginJson.action",
		type : "POST",
		dataType:'json',
		data : {"userName":userName,"password":password},
		success: function(result){
			if(!result) return;
			var data = result.data;
			var code = result.code;
			if(code==200){
				setTimeout(function(){data && loginSuccess(data.user)},1000);
			}
		}
	});
}
//第三方登陆成功后回调方法---------------------------------------------------------
function thirdBack(thirdType){
	getUserAjax();
	if(thirdType == 'true'){
		domInfo = createInfo();
		$(domInfo).appendTo($("body"));
		var infoDialog = $("#userInfoDialog2");
		infoDialog.fancybox();
		$("#thirdLoginSubmit",infoDialog).click(function(e){e.preventDefault();modify_thirdUser_submit();});
		$("#thirdLoginCancel",infoDialog).click(function(e){e.preventDefault();$.fancybox.close();});
	}
}
//for third_login_user to change Information-------------------------------
function createInfo(){
	var nickName = $("#header_nickName").html()||'';
	var strDom = '<div id="userInfoDialog2" class="popContent popRegister" style="display:none;">'+
			'<div class="content"><fieldset><form id="editThirdUserForm" method="post" action="" onsubmit="return false;">'+
			'<div><em class="title">昵称</em><em><input id="nickName" name="nickName" value="'+
			nickName+'" class="input g170" type="text" /></em></div><div><em class="title">邮箱</em>'+
			'<em><input id="email" name="email" class="input g170" type="text" />*</em></div><div>'+
			'<em class="title"></em><em ><a id="thirdLoginSubmit" href="#" class="btnOS" >确定</a></em>'+
			'<em ><a id="thirdLoginCancel" href="#" class="btnBS" >取消</a></em></div></form></fieldset></div></div>';
	return strDom;
}
//modify_thirdUser_submit--------------------------------------------------
function modify_thirdUser_submit(){
	var url = SystemProp.appServerUrl + "/user-update!editInfoJson.action";
	var user = form2object('editThirdUserForm');
	var email = $("#email",$("#userInfoDialog2")).val();
	if(email !== '' && email.indexOf('@') == -1){
		alert("邮箱必须正确填写!");
		return;
	}
	var callback = function(result){
		if(!result) return;
		var code = result.code;
		if(code == 200){
			var nickName = result.data.user.nickName;
			$("#header_nickName").html(nickName);
			$("#nickName").html(nickName);
			$.fancybox.close();
		}else{
			alert(result.message);
		}
	};
	$.ajax({
		url:url,
		type : "POST",
		dataType:'json',
		data : user,
		success: callback
	});
}

function qqLogin(){
	var url=SystemProp.domain + "/qq/o-auth!request.action";
	url+="?callbackUrl="+encodeURIComponent(window.location.href);
	var ret = window.open(url,"qq登录","location=no,status=no");
}

function sinaLogin(){
	WB2.login(function(){
		WB2.anyWhere(function(W){
		    // 验证当前用户身份是否合法
			W.parseCMD("/account/verify_credentials.json", function(o, bStatus){
			    if(bStatus == true) {
			    	window.open(SystemProp.domain +"/third/third-login!sina.action?id="+o.id+"&name="+encodeURIComponent(o.screen_name)+"&imageUrl="+encodeURIComponent(o.profile_image_url)+"&gender="+o.gender+"&address="+encodeURIComponent(o.location));
			    }
			},{
				source: SystemProp.sinaAppKey
			},{
			    method: 'post'
			});
		});
	});
}		

function baiduLogin(){
	baidu.require('connect', function(connect){
	    connect.init(SystemProp.baiduApiKey);
    	connect.login(function(info){
    	    connect.api({
    	        url: '/passport/users/getLoggedInUser',
    	        onsuccess: function(o){
    	        	window.open(SystemProp.domain +"/third/third-login!baidu.action?id="+o.uid+"&name="+encodeURIComponent(o.uname)+"&imageUrl="+encodeURIComponent("http://himg.bdimg.com/sys/portrait/item/"+o.portrait+".jpg"));
    	        },
    	        onnotlogin: function(){
    	         	//alert('login first!');
    	        },
    	        params:{
    	        }
    	    });
    	});
	});
}

function renrenLogin(){
	var url="https://graph.renren.com/oauth/authorize";
	url+="?client_id="+SystemProp.rrAppid+"&response_type="+SystemProp.rrResponseType+"&display=page&redirect_uri="+SystemProp.domain+SystemProp.rrRedirectUri;
	var ret = window.open(url,"人人账号登录","location=no,status=no");
}

function kaixinLogin(){
	var url="http://api.kaixin001.com/oauth2/authorize";
	url+="?client_id="+SystemProp.kxApiKey+"&response_type="+SystemProp.kxResponseType+"&redirect_uri="+SystemProp.domain+SystemProp.kxRedirectUri;
	var ret = window.open(url,"开心账号登录","location=no,status=no");
}

//user not login and open the Login blew!------------------------------------------------
function gotoLogin(message){
	if($("li.login .box:visible").length < 1){
		if(!!message && typeof message == 'string'){
			alert(message,function(){
				$("#loginBtn").click();
			});
		}else{
			$("#loginBtn").click();
		}
	}
}
//loginFun---------------------------------
function loginFun (){
	var logForm = $("#logInForm");
	var $loading = $(".loading",logForm);
	$loading.show();
	var user = {};
	user.userName = $("#userName",logForm).val();
	user.password = $("#password",logForm).val();
	var remember_login = $("#remember_login").attr("checked");
	
	var callback  = function(result){
		if(!result) return;
		var tipError = $(".tipsError",$("#logInForm"));
		var data = result.data;
		var code = result.code;
		var message = result.message;
		if(code === 200){
			setTimeout(function(){data && loginSuccess(data.user)},1000);
			message = "登录成功！";
			if("checked" === remember_login){
				setCookie("magemecnUserName",user.userName,new Date("December 31,2120"));
				setCookie("magemecnPassword",user.password,new Date("December 31,2120"));
			}
		}
		tipError.html(message);
		tipError.show();
		$loading.hide();
	};
	$.ajax({
		url : SystemProp.appServerUrl+"/user!loginJson.action",
		type : "POST",
		dataType:'json',
		data : user,
		success: callback
	});
}

//loginFun2---------------------------------
function loginFun2 (){
	var logForm = $("#logInForm2");
	var $loading = $(".loading",logForm);
	$loading.show();
	var user = {};
	user.userName = $("#userName",logForm).val();
	user.password = $("#password",logForm).val();
	var remember_login = $("#remember_login").attr("checked");

	var callback  = function(result){
		if(!result) return;
		var tipError = $(".tipsError",logForm);
		var data = result.data;
		var message = result.message;
		tipError.html(message);
		tipError.show();
		$loading.hide();
	};
	$.ajax({
		url : SystemProp.appServerUrl+"/publish/front-publisher!loginJson.action",
		type : "POST",
		dataType:'json',
		data : user,
		success: callback
	});
}

//注册提交按钮----------------------------------------------------------
function  registerSubmit (obj,register){
	var isOk = obj.data("isSubmit");
	if(!!isOk){
		obj.data("isSubmit",false);
		var message = "";
		var user = {};
		user.userName = $("#userName",register).val();
		user.email = $("#email",register).val();
		user.password = $("#password",register).val();
		user.password2 = $("#password2",register).val();
		//确认密码错误将不提交数据库
		var tipError = $(".tipsError",register);
		
		if(user.password !== user.password2){
			message = "确认密码错误！";
			obj.data("isSubmit",true);
			tipError.html(message).show();
			$("#checkPassword",register).show();
			return;
		}else{
			tipError.hide();
		}
		var $loading = $(".loading",register);
		$loading.show();
		var callback = function(result){
			obj.data("isSubmit",true);
			message = result.message;
			
			var code = result.code;
			var data = result.data;
			$loading.hide();
			if(data.userName){
				message = data.userName;
				tipError.html(message).show();
				return;
			}
			if(data.email){
				message = data.email;
				tipError.html(message).show();
				return;
			}
			if(data.password){
				message = data.password;
				tipError.html(message).show();
				return;
			}
			tipError.html("注册成功").show();
			setTimeout(function(){
				loginSuccess(result.data.user);
				$("#registerBtn").click();
			},3000);
		};
		
		$.ajax({
			url : SystemProp.appServerUrl+"/user!registerJson.action",
			type : "POST",
			data : user,
			dataType : 'json',
			success: callback
		});
	}
}

//favFun---------------------------------------------------
function favFun(type,id,$fav){
	switch(type){
		case 'pic':
			type = 1;
			break;
		case 'eve':
			type = 3;
			break;
		case 'mag':
			type = 2;
			break;
	}
	$.ajax({
		url : SystemProp.appServerUrl+"/user-enjoy!changeJson.action",
		type : "POST",
		data : {"type":type,"objectId":id},
		dataType : 'json',
		success: function(rs){
			if(!rs)return;
			var code = rs.code;
			if(code == 200){
				var eveList = (!!statsMap) ? statsMap.enjoyEventList : [];
				var picList = (!!statsMap) ? statsMap.enjoyImageList : [];
				var magList = (!!statsMap) ? statsMap.enjoyIssueList : [];
				//window.statsMap 中数据移除
				if($fav.hasClass("favCurrent")){
					$fav.attr("title","喜欢");
					if(!!statsMap){
						switch(type){
						case 1:
							var index = $.inArray(parseInt(id),picList);
							if(index != -1){
								picList.splice(index,1);
								statsMap.enjoyImageList = picList;
							}
							break;
						case 3:
							var index = $.inArray(id,eveList);
							if(index != -1){
								eveList.splice(index,1);
								statsMap.enjoyEventList = eveList;
							}
							break;
						case 2:
							var index = $.inArray(id,magList);
							if(index != -1){
								magList.splice(index,1);
								statsMap.enjoyIssueList = magList;
							}
							break;
						}
					}
				}else{
					$fav.attr("title","取消喜欢");
					if(!!statsMap){
						switch(type){
						case 1:
							statsMap.enjoyImageList.push(parseInt(id));
							break;
						case 3:
							statsMap.enjoyEventList.push(parseInt(id));
							break;
						case 2:
							statsMap.enjoyIssueList.push(parseInt(id));
							break;
						}
					}
				}
				
				$fav.toggleClass("favCurrent");
				if($fav.html() === '喜欢') return;
				//返回的数目后台给，然后写到页面test
				if(rs.data && rs.data.enjoyNum !== undefined){
					$fav.html(rs.data.enjoyNum);
				};
			}else if(code == 400){
				gotoLogin("用户没有登录！");
			}else{
				alert(rs.message);
			}
		}
	});
}

//window_scroll_load_data-------------------------------------
//param:loadFun是加载数据的函数，bottomHeight是滚动条到底部的高度加载数据,num是滚动加载的次数
var scrollTimer = null;
var hasData = true;
var scrollSwtich = true;
var loadNum = 1;
function scrollLoadData(loadFun,bottomHeight,num){
	if(typeof loadFun !== "function") return;
	if(!bottomHeight){
		bottomHeight = $(window).height()/3;//defaultHeight
	}
	$(window).scroll(function(){
		var hasOver = hasOverlay();//samplefancybox and lightbox
		if(!scrollTimer && scrollSwtich && hasData && !hasOver){
			scrollTimer = setTimeout(function(){
				var bodyHeight = $("body").height();
				var windowHeight = $(window).height();
				var scrollHeight = bodyHeight - windowHeight;
				
				var scrollTop = $(window).scrollTop();
				if(scrollTop > (scrollHeight-bottomHeight)){
					if(!!num){
						if(loadNum < num){
							loadNum +=1;
						}else{
							scrollSwtich = false;
						}
					}
					loadFun();
					//refresh fav
					if(hasUserLogin){
						chooseFav(window.statsMap);
					}
				}
				scrollTimer = null;
			},1500);
		}
		if(!hasData){
			$("#loadMore").addClass("hide");
		}
	});
}
// Used to determine whether a user is logged
function  hasUserLogin(){
	return $("#loginBar").is(":visible");
}

function ajaxFollow(url,objectId,type,successFun){
	$.ajax({
		url : url,
		type : "POST",
		data : {"objectId":objectId,"type":type},
		dataType : 'json',
		success: successFun
	});
}
//addFollow-----------------------------------
function ajaxAddFollow(objectId,type,successFun){
	ajaxFollow(SystemProp.appServerUrl+"/user-follow!addFollowJson.action",objectId,type,successFun);
}
//cancelFollow---------------------------------
function ajaxCancelFollow(objectId,type,successFun){
	ajaxFollow(SystemProp.appServerUrl+"/user-follow!deleteJson.action",objectId,type,successFun)
}
//sendMessage---------------------------------
function ajaxSendMsg(toUserIds,toType,content,successFun){
	$.ajax({
		url : SystemProp.appServerUrl+"/user-message!sendJson.action",
		type : "POST",
		data : {"toUserIds":toUserIds,"toType":toType,"content":content},
		dataType : 'json',
		success: successFun
	});
}

//发送通用消息
//sendMessage---------------------------------
function ajaxSendCommonMsg(toUserIds,toType,fromUserId,fromType,content,successFun){
	$.ajax({
		url : SystemProp.appServerUrl+"/user-message!sendCommonJson.action",
		type : "POST",
		data : {"toUserIds":toUserIds,"toType":toType,"content":content,"fromUserId":fromUserId,"fromType":fromType},
		dataType : 'json',
		success: successFun
	});
}
//addtag--------------------------------------
function ajaxAddTag(objectId,type,name,successFun){
	$.ajax({
		url : SystemProp.appServerUrl+"/tag!addJson.action",
		type : "POST",
		data : {"objectId":objectId,"type":type,"name":name},
		dataType : 'json',
		success: successFun
	});
}
//try to click --->need login 
function popLogin(){
	var url = SystemProp.appServerUrl+"/click-try!clickLogin.action";
	var callback = function(result){
		if(!result)return;
    	$("body").append(result);
    	$.jquerytagbox("#popLoginTab",0);
    	$("#popLoginRegister").fancybox();
	};
	
	$.ajax({
		data: {},
		url: url,
		type : "POST",
		dataType : "html",
		async: false,
		success: callback
	});
}		

function adLoginSuccess(loginType){
	var url="";
	if(loginType==1){
		url="/publish/pcenter-publisher.action?random=";
	}else{
		url="/ad/adcenter-home.action?random=";
	}
	window.location.href = SystemProp.appServerUrl+url+Math.random();
}

function adLogin(){
	var userName = $("#userName").val();
	var password = $("#password").val();
	var loginType = $(":radio:checked").val();
	var authCode = $("#authcode").val();
	
	var logincallback = function(result){
		if(!result) return;
		var tipError = $(".tipsError");
		var data = result.data;
		var message = result.message;
		if(result.code != 200){
			if(data && data.authcode){
				message = data.authcode;
			}
			if(data && data.userName){
				message = data.userName;
			}
			tipError.html(message).show();
		}else{
			message = "登录成功!";
			tipError.text(message).show();
			setTimeout(function(){
				adLoginSuccess(loginType);
			},1000);
		}
	};
	$.ajax({
		url: SystemProp.appServerUrl+"/publish/front-publisher!loginJson.action",
		type: "POST",
		data: {"userName":userName,"password":password,"authcode":authCode,"loginType":loginType},
		success: logincallback
	});
}

//getAuthCode
function getAuthCode (img){
	var img = $(img);
	var src = SystemProp.appServerUrl + "/publish/authcode.action?random=" + Math.random();
	img.attr("src",src);
}
function fnReadyTable(){
	var tableID=1;
	var tdID =1;
	var tableNum = $('table.table').size();
	for(tableID;tableID<=tableNum;tableID++) {
		$('table.table').get(tableID-1).lang='table'+tableID;
		var tdNum = $('table.table[lang*=table'+tableID+'] tr:first td').size();
		for(tdID; tdID <= tdNum; tdID++) {
			$('table.table tr td:nth-child('+tdID+')').addClass('t'+tdID);
		};
	};
}

function readerShare(type,url,title,imgsrc){
var tagInfo = {
		url: url,
		title: title,
		imgsrc: imgsrc,
		desc:''
	};
shareToObj.shareType(type,tagInfo);
}
//userbar click----
function fnbodyClick(isBind){
	if (isBind) {
		$("body").bind("click",function(e){
			if($(e.target).parents(".box:visible").length == 0 && !$(e.target).hasClass("box")){
				$(".header .userBar>li>a.sub.current").click();
			}
		});
	}else{
		$("body").unbind("click");
	}

}