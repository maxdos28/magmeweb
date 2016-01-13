$(function(){
var browserHeight = $(window).height();
var browserWidth = $(window).width();
var $menuB = $(".header .btn");
var $menuN = $(".header .nav");
var menuHeight = $("body").hasClass("pad") ? 198 : 68;
var menuLock;


fnResize();
$(window).bind("resize", fnResize);


//设置面面加载后地址栏收起
window.addEventListener('load', function() {
setTimeout(scrollTo, 0, 0, 1);
}, false);



//设置旋转后背景和左右填充
function fnResize(){
	browserWidth = $(window).width();
	if(browserWidth>=480){
		$("body").addClass("horizontal");
	}else{
		$("body").removeClass("horizontal");
	}
}

//header固定
var $header = $("#header");


//下拉菜单
//$menuB.click(function(){
//	if(!$(this).hasClass("hover")){
//		navShow();
//	}else{
//		navHide();
//	}
//});
//function navShow(){
//	menuLock = true;
////	fnbodyClick(true);
//	$menuB.addClass("hover");
//	$menuN.animate({height:menuHeight},350);
//}
//function navHide(){
//	if( menuLock == true){	
//		$menuB.removeClass("hover");
//		$menuN.animate({height:0},350);
////		fnbodyClick(false);
//	}
//}
//function fnbodyClick(isBind){
//	if (isBind) {
//		$("html").bind("click",function(e){
//			if(!$(e.target).next().hasClass("nav") && !$(e.target).hasClass("btn")){
//				isOut = false;
//				navHide();
//			}
//		});
//	} else {
//		$("body").unbind("click");
//	}
//}


});


//window_scroll_load_data-------------------------------------
//param:loadFun是加载数据的函数，bottomHeight是滚动条到底部的高度加载数据,num是滚动加载的次数
var scrollTimer = null;
var hasData = true;
var scrollSwtich = true;
var loadNum = 1;
var isFirst = true;
function scrollLoadData(loadFun,bottomHeight,num){
	if(typeof loadFun !== "function") return;
	if(!bottomHeight){
		bottomHeight = $(window).height()/3;//defaultHeight
	}
	$(window).scroll(function(){
		if(isFirst){
			isFirst = false;
			return;
		}
		//samplefancybox and lightbox
		if(!scrollTimer && scrollSwtich && hasData){
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
				}
				scrollTimer = null;
			},1500);
		}
	});
}

String.prototype.reverse = function(){
	var aStr="";
	for(var i=this.length-1;i>=0;i--){
		aStr=aStr.concat(this.charAt(i));
	}
	return aStr;
};
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