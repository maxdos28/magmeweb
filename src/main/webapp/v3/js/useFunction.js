//share's parameter
var tagInfo = {
		url: 'http://www.magme.com',
		title: "看杂志 ·上麦米网",
		imgsrc:"http://www.magme.com/images/logo.gif",
		desc:''
	};
//isIE6
var isIE6 = $.browser.msie && $.browser.version < 7 && !window.XMLHttpRequest;

var scriptLoader = {  
     load: function (url, callback) {  
         var head = document.getElementsByTagName('head')[0];  
         var script = document.createElement('script');  
         script.type = 'text/javascript';  
         script.src = url;  
         if (callback) {  
        	 if($.browser.msie) { 
	             script.onreadystatechange = function () {  
	                 if (this.readyState == 'loaded'||this.readyState == 'complete') callback();  
	             }  
	         }else if($.browser.mozilla){
	        	 script.onload=function(){
	        		 callback();
	        	 }
	         }else{
	        	 callback();
	         }
         }  
         head.appendChild(script);  
     }
};
//将该字符串反序排列-----------------------------------------------------
//String.prototype.reverse = function(){
//	var aStr="";
//	for(var i=this.length-1;i>=0;i--){
//		aStr=aStr.concat(this.charAt(i));
//	}
//	return aStr;
//};

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
		CookieVal = CookieVal;
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
		CookieVal = CookieVal;
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

function getUrlValue2(paras){
	var url = location.href; 
    var paraString = url.substring(url.indexOf("?")+1,url.length).split("&"); 
    var paraObj = {} 
    for (i=0; j=paraString[i]; i++){ 
    paraObj[j.substring(0,j.indexOf("=")).toLowerCase()] = j.substring(j.indexOf("=")+1,j.length); 
    } 
    var returnValue = paraObj[paras.toLowerCase()]; 
    if(typeof(returnValue)=="undefined"){ 
    return ""; 
    }else{ 
    return returnValue; 
    } 
}

//判断时候有fancybox和lightbox 的遮挡层
function hasOverlay(){
	return ($("#fancybox-overlay").is(":visible") && $("#fancybox-overlay").height()>30) 
		|| $(".eaMask").is(":visible") || $(".bodyMask").is(":visible");
}


//checkName or Email-----------------------------------------------
function checkNameOrEmail(obj,register){
	if(!obj) return;
	var tips = obj.attr('tips');
	var val = obj.val();
	if($.trim(val) == '' || val == null){
		tips && obj.css({color:'#ccc'}).val(tips);
		obj.parents("div").eq(0).find(".tipsError").html("输入不能为空！").show();
		return;
	}
	//如果验证过的就不做验证
	var objData = obj.data("value");
	if(objData !== val){
		obj.data("value",val);
	}else{
		return;
	}
	
	var tipError = $(".tipsError",register);
	var id = obj.attr("id");
	var callback = function(result){
		var $checkUserName = $("#checkUserName");
		var $nameRight = $checkUserName.find(".tipsRight");
		var $nameWrong = $checkUserName.find(".tipsWrong");
		var $nameError = $checkUserName.find(".tipsError");
		var $checkEmail = $("#checkEmail");
		var $emailRight = $checkEmail.find(".tipsRight");
		var $emailWrong = $checkEmail.find(".tipsWrong");
		var $emailError = $checkEmail.find(".tipsError");
		var data = result.data;
		if(result.message === 'success'){
			if(id == 'userName'){
				$nameRight.show();
				$nameWrong.hide();
				$nameError.hide();
			}else{
				$emailRight.show();
				$emailWrong.hide();
				$emailError.hide();
			}
		}else{
			if(id == 'userName'){
				$nameRight.hide();
				$nameWrong.show();
				if(!!data && !!data.userName){
					$nameError.html(data.userName);
				}
				$nameError.show();
			}else{
				$emailRight.hide();
				$emailWrong.show();
				if(!!data && !!data.email){
					$emailError.html(data.email);
				}
				$emailError.show();
			}
		}
	};
	var userData = {};
	userData[id] = val;
	var domain=(document.location+"").split("/")[2];
	var appserverurl="http://"+domain;
	$.ajax({
		url : appserverurl+"/user!validateUserJson.action",
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
	if($.fancybox == undefined){
		return;
	}
	var urlLocalValue = window.location.href;
	
	if(window.location.href.indexOf('m1-register-and-login')>-1){
		window.location.href=SystemProp.appServerUrl+'/sns/u'+user.id+"/";
		return;
	}
	try{
	$.fancybox.close();
	}catch(e){}
	if(!user) return;
	//统计需要的UserId
	setCookie("magmeUserId",user.id);
	
	$("#userBar").show();
	$("#loginBar").hide();
	
	if(!!user.avatar30){
		$("#avatar30").attr("src",SystemProp.profileServerUrl+user.avatar30);
	}
	var nickname='';
	if(user.nickName.length>12)
		nickname=user.nickName.substring(0,12);
	else
		nickname=user.nickName;
	$("#nickName","#userBar").attr("title",user.nickName);
	$("#nickName","#userBar").html(nickname);
	$("#userLink","#userBar").attr("href",SystemProp.appServerUrl+'/sns/u'+user.id+"/");
	var messageNum = (!!user.statsMap) ? user.statsMap.messageNum : 0;
	if(messageNum*1>0){
		$("#newMessageNum").parent().addClass("messageNew");
		$("#newMessageNum").attr("title",messageNum+"条新消息");
	}
	if(window.location.href.indexOf('sns-index')>-1){
		window.location.href=SystemProp.appServerUrl+'/sns/u'+user.id+"/";
		return;
	}else if(window.location.href.indexOf('user-index')>-1){
		var isLogin = $("#isLogin").val();
		if(isLogin==0)
			window.location.reload();
	}
	
	//用户喜欢的一些数据
	window.statsMap = user.statsMap;
	chooseFav(user.statsMap);
	if(typeof(fnLoadNavData) != "undefined")//在首页
		if(typeof(showMyNavList)!="undefined"){
				fnLoadNavData(showMyNavList);
				}
//	if(window.tryClick && window.tryClick.isShow){
//		$("#"+window.tryClick.fancyboxId).fancybox();
//		window.tryClick.isShow = false;
//	}
}
function chooseFav(statsMap){
	if(!statsMap) return;
	var eveList = statsMap.enjoyEventList || [];
	var picList = statsMap.enjoyImageList || [] ;
	var magList = statsMap.enjoyIssueList || [];
	var creList = statsMap.enjoyCreativeList || [];
	var eveLength = eveList.length;
	var picLength = picList.length;
	var magLength = magList.length;
	var creLength = creList.length;
	for(var i=0;i<eveLength;i++){
		$("em.iconHeart[favTypeId='eve_"+eveList[i]+"']").addClass("favCurrent");
	}
	for(var j=0;j<picLength;j++){
		$("em.iconHeart[favTypeId='pic_"+picList[j]+"']").addClass("favCurrent");
	}
	for(var k=0;k<magLength;k++){
		$("em.iconHeart[favTypeId='mag_"+magList[k]+"']").addClass("favCurrent");
	}
	for(var k=0;k<creLength;k++){
		$("em.iconHeart[favTypeId='cre_"+creList[k]+"']").addClass("favCurrent");
	}
}

function loginPubish (publisher){
	$("#menu").find("li").hide();
	$("#menu").find(".publisher").show();
	
	//alert("出版商登录成功后的一些操作");
}
//获取用户当前登录状态
function getUserAjax(){
	//everyPage获取用户状态不做统计
	//SystemProp.isStat = false;
	$.ajax({
		url : "/user!getReaderJson.action",
		type : "POST",
		dataType:'json',
		async: false,
		success: function(result){
			//未登录
			if(typeof(fnLoadNavData) != "undefined")//在首页
			if(typeof(showMyNavList)!="undefined"){
				fnLoadNavData(showMyNavList);
				}
			SystemProp.isStat = true;
			if(!result) return;
			var data = result.data;
			var code = result.code;
			var checkUserRole = true;
			if(code==200){
				if(data && data.user){
					loginSuccess(data.user);
					checkUserRole = false;
					return;
				}
				if(data && data.publisher){
					checkUserRole = false;
//					loginPubish(data.publisher);
					return;
				}
				if(data && data.admin){
					checkUserRole = false;
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
	var userType = getCookie("magemecnUserType");
	if(userType){
		if(userType=='nv'){
			autoLoginFunComm_jsonp(userName,password);
		}
	}else{
		autoLoginFunComm_json(userName,password);
	}
}

function autoLoginFunComm_json(userName,password){
	var domain=(document.location+"").split("/")[2];
	var appserverurl="http://"+domain;
	$.ajax({
		url : appserverurl+"/user!loginJson.action",
		type : "POST",
		dataType:'json',
		data : {"userName":userName,"password":password},
		success: function(result){
			if(!result) return;
			var data = result.data;
			var code = result.code;
			if(code==200){
				setTimeout(function(){data && loginSuccess(data.user)},1000);
				var urlLocalValue = window.location.href;
				if(urlLocalValue=='http://www.magme.com/sns/square.action'){
					window.location.reload();
				}
			}
		}
	});
}

function autoLoginFunComm_jsonp(userName,password){
	var success_jsonpCallback = function(result){
			if(!result || !result[0]){
				return;
			}
			result=result[0];
			var data = result.data;
			var code = result.code;
			if(code==200){
				setTimeout(function(){data && loginSuccess(data.user)},1000);
			}
		}
	
	$.ajax({
		url : "http://www.magme.com/publish/nv-issue!loginJson.action",
		type : "GET",
		async : false,
		data : {"userName":userName,"password":password},
		dataType : "jsonp",
		jsonp:"callbackparam",
		jsonpCallback:"success_jsonpCallback",
		success : success_jsonpCallback
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
function gotoLogin(message,other){
	var sns_url = window.location.href;
	/*if(window.location.href.indexOf('/sns/')>-1){
		if(confirm(message))
	    {
			window.location.href=SystemProp.appServerUrl+'/sns/m1-register-and-login.action#login';
	    }
		
		return;
	}*/
	if(other==1){
		if(!!message && typeof message == 'string'){
			$("#userLogin").click();
//			alert(message,function(){
//			});
		}else{
			$("#userLogin").click();
		}
	}else
	if($("#loginBar:visible").length > 0){
		if(!!message && typeof message == 'string'){
//			alert(message,function(){
			$("#userLogin").click();
//			});
		}else{
			$("#userLogin").click();
		}
	}
}
//loginFun---------------------------------
function loginFun (){
	var logForm = $("#popLoginForm");
	var $loading = $(".loading",logForm);
	$loading.show();
	var user = {};
	user.userName = $("#userName",logForm).val();
	user.password = $("#password",logForm).val();
	var remember_login = $("#remember_login").attr("checked");
	var userType = $("#userType",logForm).val();
	if(userType){
		if(userType=='nv'){
			loginFunComm_jsonp(user,userType,remember_login);
		}
	}else{
		loginFunComm_json(user,remember_login);
	}
	
}

function loginFunComm_json(user,remember_login){
	var callback  = function(result){
		if(!result) return;
		var tipError = $(".tipsError",$("#popLoginForm"));
		var data = result.data;
		var code = result.code;
		var message = result.message;
		if(code === 200){
			setTimeout(function(){
				if(!!data) loginSuccess(data.user);
			},1000);
			message = "登录成功！";
			if("checked" === remember_login){
				setCookie("magemecnUserName",user.userName,new Date("December 31,2120"));
				setCookie("magemecnPassword",user.password,new Date("December 31,2120"));
			}
			var urlLocalValue = window.location.href;
			if(urlLocalValue=='http://www.magme.com/sns/square.action'){
				window.location.reload();
			}
		}
		tipError.html(message);
		tipError.show();
		var logForm = $("#popLoginForm");
		var $loading = $(".loading",logForm);
		$loading.hide();
		
		
	};
	var domain=(document.location+"").split("/")[2];
	var appserverurl="http://"+domain;
	$.ajax({
		url : appserverurl+"/user!loginJson.action",
		type : "POST",
		dataType:'json',
		data : user,
		success: callback
	});
}

function loginFunComm_jsonp(user,userType,remember_login){
	var success_jsonpCallback  = function(result){
		if(!result || !result[0]){
				return;
		}
		result=result[0];
		var tipError = $(".tipsError",$("#popLoginForm"));
		var data = result.data;
		var code = result.code;
		var message = result.message;
		if(code === 200){
			setTimeout(function(){
				if(!!data) loginSuccess(data.user);
			},1000);
			message = "登录成功！";
			if("checked" === remember_login){
				setCookie("magemecnUserName",user.userName,new Date("December 31,2120"));
				setCookie("magemecnPassword",user.password,new Date("December 31,2120"));
				setCookie("magemecnUserType",userType,new Date("December 31,2120"));
			}
		}
		tipError.html(message);
		tipError.show();
		var logForm = $("#popLoginForm");
		var $loading = $(".loading",logForm);
		$loading.hide();
	};
	
	$.ajax({
			url : "http://www.magme.com/publish/nv-issue!loginJson.action",
			type : "GET",
			async : false,
			data : user,
			dataType : "jsonp",
			jsonp:"callbackparam",
			jsonpCallback:"success_jsonpCallback",
			success : success_jsonpCallback
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
		var userType = $("#userType",register).val();
		if(userType){
			if(userType=='nv'){
				regCommFun_jsonp(obj,register);
			}
		}else{
			regCommFun_json(obj,register);
		}
	}
}

//注册方法公共方法提取 ---json提交
function regCommFun_json(obj,register){
	var message = "";		
		var user = {};
		user.userName = $("#userName",register).val();
		user.email = $("#email",register).val();
		user.password = $("#password",register).val();
		user.password2 = $("#password2",register).val();
		
		var $check2 = $("#checkPassword2",register);
		if(user.password !== user.password2){
			obj.data("isSubmit",true);
			$check2.find(".tipsWrong").show();
			$check2.find(".tipsRight").hide();
			$check2.find(".tipsError").show();
			return;
		}else{
			$check2.find(".tipsWrong").hide();
			$check2.find(".tipsRight").show();
			$check2.find(".tipsError").hide();
		}
		var tipError = $(".tipsError:last",register);
		tipError.hide();
		if($(".tipsError:visible",register).length>0){
			return;
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
				var urlLocalValue = window.location.href;
				if(urlLocalValue=='http://www.magme.com/sns/square.action'){
					window.location.reload();
				}
			},3000);
		};
		var domain=(document.location+"").split("/")[2];
		var appserverurl="http://"+domain;
		$.ajax({
			url : appserverurl+"/user!registerJson.action",
			type : "POST",
			data : user,
			dataType : 'json',
			success: callback
		});
}
//注册方法公共方法提取 ---jsonp提交
function regCommFun_jsonp(obj,register){
	var message = "";		
		var user = {};
		user.userName = $("#userName",register).val();
		user.email = $("#email",register).val();
		user.password = $("#password",register).val();
		user.password2 = $("#password2",register).val();
		
		var $check2 = $("#checkPassword2",register);
		if(user.password !== user.password2){
			obj.data("isSubmit",true);
			$check2.find(".tipsWrong").show();
			$check2.find(".tipsRight").hide();
			$check2.find(".tipsError").show();
			return;
		}else{
			$check2.find(".tipsWrong").hide();
			$check2.find(".tipsRight").show();
			$check2.find(".tipsError").hide();
		}
		var tipError = $(".tipsError:last",register);
		tipError.hide();
		if($(".tipsError:visible",register).length>0){
			return;
		}
		var $loading = $(".loading",register);
		$loading.show();
		var success_jsonpCallback = function(result){
			if(!result || !result[0]){
				return;
			}
			obj.data("isSubmit",true);
			result=result[0];
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
			},3000);
		};
		
		$.ajax({
			url : "http://www.magme.com/publish/nv-issue!registerJson.action",
			type : "GET",
			async : false,
			data : user,
			dataType : "jsonp",
			jsonp:"callbackparam",
			jsonpCallback:"success_jsonpCallback",
			success : success_jsonpCallback
		});
}

//favFun---------------------------------------------------

function favFunMiddlePage(type,id){
	favFun(type,id,null, callSWFFunction)
}
function callSWFFunction(data){
	var status = data.code == 200 ? 1 : 0;
	var myread = ($.browser.msie)?$("#midRead"):$("#midRead2");
	if(myread && myread.length)
		myread[0].wordReaderFlashGetFavState(status);
}


function favFun(type,id,$fav, callback){ 
	if(!hasUserLogin()){
		gotoLogin("用户没有登录！");
		return;
	}
	switch(type){
		case 'pic': type = 1; break;
		case 'mag': type = 2; break;
		case 'eve': type = 3; break;
		case 'cre': type = 4; break;
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
				if(!$fav || $fav.hasClass("favCurrent")){
					if($fav) $fav.attr("title","喜欢");
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
					if($fav) $fav.attr("title","取消喜欢");
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
				if($fav) {
					$fav.toggleClass("favCurrent");
					if($fav.html() === '喜欢') return;
					//返回的数目后台给，然后写到页面test
					if(rs.data && rs.data.enjoyNum !== undefined){
						$fav.html(rs.data.enjoyNum);
					};
				}
			}else if(code == 400){
				gotoLogin("用户没有登录！");
			}else{
				alert(rs.message);
			}
			if(callback)
				callback(rs);
		}
	});
}

//window_scroll_load_data-------------------------------------
//param:loadFun是加载数据的函数，bottomHeight是滚动条到底部的高度加载数据,num是滚动加载的次数
var scrollTimer = null;
var hasData = true;
var scrollSwtich = true;
var loadNum = 1;
var isFirst = true;//解决window绑定scroll事件载入时执行一次的问题
function scrollLoadData(loadFun,bottomHeight,num){
	if(typeof loadFun !== "function") return;
	if(!bottomHeight){
		bottomHeight = $(window).height()/3;//defaultHeight
	}
	var $loadMore = $("#loadMore");
//		$(window).scroll(function(){
	$loadMore.click(function(){
//		if(isFirst){
//			isFirst = false;
//			return;
//		}
		var hasOver = hasOverlay();//samplefancybox and lightbox
		if(!scrollTimer && scrollSwtich && hasData && !hasOver){
			var cls = $loadMore.attr("class");
			$loadMore.attr("class", "loading32");
//			scrollTimer = setTimeout(function(){
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
					$loadMore.attr("class", cls);
					//refresh fav
					if(hasUserLogin()){
						chooseFav(window.statsMap);
					}
				}
//				scrollTimer = null;
//			},1500);
		}
		if(!hasData){
			$("#loadMore").addClass("hide");
		}
	});
}
// Used to determine whether a user is logged
function  hasUserLogin(){
	return $("#userBar").is(":visible");
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
	//"tr" add double color & "tr" add hover style--------------------------------------------//
	var $JQtableBg = $("table.JQtableBg");
	var $JQtableHover = $("table.JQtableBg tbody tr");
	$JQtableBg.each(function(){
		$(this).find("tbody tr:odd").addClass('bgColorTable');
	});
	$JQtableHover.live("mouseover",function(){
	$(this).addClass("bgTrHover");
	});
	$JQtableHover.live("mouseout",function(){
		$(this).removeClass("bgTrHover");
	});
	function addJQtableBg(element) {
		element.addClass('bgColorTable');
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
//dateFormat------------------------
function dateFormat(dateStr){
	return dateStr.split('T')[0];
}

function sina_api_follow(uid){
	//统计weibo关注
	if($ma){
		var url = "/third/follow?weiboId="+uid;
		$ma(url,"0");
	}
	WB2.anyWhere(function(W){
	    // 获取评论列表
	W.parseCMD("/friendships/create.json", function(sResult, bStatus){
	    if(bStatus == true) {
			alert("关注成功");
	    }
	    else if(sResult.error.indexOf('40302')>-1){
	    	alert("请先登录新浪微博");
	    	window.open("http://www.weibo.com/login.php?url=http://www.weibo.com/"+uid);
	    }
	    else if((sResult.error.substring(6))=="Error: already followed"){
	    	alert("您已经关注过了");
	    }
	    else{
	    	alert(sResult.error.substring(6));
	    }
	},{
		source : '477313374',
		id : uid
	},{
	    method: 'post'
	});
	});
}


// patch holes functions	
var patchHoles = function($homeWall, eventList){
	if (eventList==null){
		return;
	}
	$homeWall.children(".patch").remove();
	var holes = $homeWall.get(0).masonryHoles,
		len=holes.length;
	var subList = eventList.slice(0, len);

	for (var i=0; i<len; i++){
		subList[i].top = holes[i][0];
		subList[i].left = holes[i][1];
	}

	var tmpHMTL = '<div class="item size${eventClass} patch" style="position:absolute; top:${top}px; left:${left}px;">'+
		'<a eventId="${id}" clickEventId="${issueId}_${pageNo}" href="javascript:void(0)">'+
    	'<img class="photo" src="http://static.magme.com/fpage/event/${imgFile}" alt="${title}" />'+
    	'<div class="info png">'+
        '<h5>${title}</h5>'+
        '<h6>出自 [ ${publicationName} ]</h6>'+
        '<p>${description}</p>'+
        '<div class="tools png"><em detailId="${id}" class="iconDetail">详细</em><em title="喜欢" favTypeId="eve_${id}" class="iconHeart">${enjoyNum}</em><em shareTypeId="eve_${id}" class="iconShare">分享</em></div>'+
        '</div></a></div>';
	var $event = $.tmpl(tmpHMTL, subList);
	$event.appendTo($homeWall);				
	itemsHov($event);
};

//确认提示框Confirm
function ConfirmDel(msgStr)
{
if (confirm(msgStr))
 return true;
else
 return false;
}
	
