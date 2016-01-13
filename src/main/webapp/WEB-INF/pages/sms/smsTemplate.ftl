<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;" />
<script src="${systemProp.staticServerUrl}/js/systemProp.js"></script>
<link href="${systemProp.staticServerUrl}/v3/sms/style/reset.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${systemProp.staticServerUrl}/v3/sms/style/royalslider.css"/>
<link rel="stylesheet" type="text/css" href="${systemProp.staticServerUrl}/v3/sms/style/rs-default.css"/>
<link rel="stylesheet" type="text/css" href="${systemProp.staticServerUrl}/v3/sms/style/tpl.css"/>
<title>${webTitle!''}</title>
<script src="${systemProp.staticServerUrl}/v3/sms/js/jquery-new.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/sms/js/jquery.royalslider.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/sms/js/jquery-ui-1.8.21.custom.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/sms/js/jquery.mCustomScrollbar.js"></script>
<script src="${systemProp.staticServerUrl}/v3/sms/js/jquery.scrollto.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/sms/js/jquery.mousewheel.min.js"></script>

<script>
var date1 = new Date();
$(function(){


//ajax_account-----------------------------------------------------
	var	document = window.document,
		_navigator = window.navigator,
		_location = window.location,
		_referrer = document.referrer,
		_encodeURI = encodeURIComponent,
		emptyFunction = function(){},
		oldAjax = $.ajax;
	
	//date转换成"yyyy/mm/dd"
	function dateFormate(date){
		if(typeof data === 'string') return null;
		return date.getFullYear()+"/"+date.getMonth()+"/"+date.getDate();
	}
	//屏幕分辨率
	function getResolution (){
		return window.screen.width +"*"+window.screen.height;
	}
	//发送统计请求
	function loadImage(src,callback){
		var image = new Image(1, 1);
        image.src = src;
        image.onload = function(){
            image.onload = null;
            (callback || emptyFunction)();
        }
	}
	//get URL
	function imageSrc(dataUrl,ispv){
		var imageSrc = "http://stat.magme.com/smsonepix.gif?";
		var ispv = ispv || '0';
		if(typeof dataUrl !== 'string') return;
		var userId = (new MagCookie().getCookie("magmeUserId")) || -1;
		var date2 = new Date();
		var data = {
			"action":"1",
			"url": dataUrl,
			"muid": $Muid.getMuid(),
			"retention": date2-date1,
			"ispv":ispv,
			"random":Math.random()
		};
		for(var key in data){
			imageSrc += key+"="+ _encodeURI(data[key])+"&";
		}
		imageSrc = imageSrc.slice(0,-1);
		return imageSrc;
	}
	//第三方调用统计
	$ma = function(dataUrl,ispv){
		var src = imageSrc(dataUrl,ispv);
		loadImage(src);
	};
	//cookie
	var MagCookie = function (){
		var oThis = this;
		
		oThis.setCookie = function (CookieName, CookieVal, CookieExp, CookiePath, CookieDomain, CookieSecure){
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
		};
		
		oThis.getCookie = function (CookieName){
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
		};
		
		oThis.deleteCookie = function (CookieName){
			var tmp = getCookie(CookieName);
			if(tmp) { 
				setCookie(CookieName,tmp,(new Date(1))); //Used for Expire 
			}
		};
		
	};
	
	//muid
	var Muid = function (win, doc){
		var oThis = this;
		var magCookie = new MagCookie();
		
		oThis.createMuid = function(){
			return Math.random().toString(16).substring(2)+"_"+(+new Date());
		};
		
		oThis.setMuid = function(){
			if(magCookie.getCookie("magmecn_muid")){
				return;
			}
			magCookie.setCookie("magmecn_muid",oThis.createMuid(),new Date("December 31,2120"));
			//default visitor is new
			magCookie.setCookie("magmecn_oldVis",'0',new Date("December 31,2120"));
		}
		
		oThis.getMuid = function(){
			return magCookie.getCookie("magmecn_muid") || "";
		};
		
		oThis.createTime = function(){
			var muid = oThis.getMuid();
			var ctime = (muid && muid.split("_")[1]) || ""; 
			return ctime;
		};
		
		oThis.setMuid();
	};
	//全局变量$Muid
	$Muid = new Muid(window,document);
	
	//判断用户是否是老用户
	var oldVisitor = function(){
		var oThis = this;
		var magCookie = new MagCookie();
		
		//status  1表示是老用户，0表示非
		oThis.setVisitorStatus = function (status){
			var s = status || '0';
			magCookie.setCookie("magmecn_oldVis",s,new Date("December 31,2120"));
		};
		
		oThis.getVisitorStatus = function (){
			return magCookie.getCookie("magmecn_oldVis") || '0';
		};
		
		oThis.isOldVisitor = function (){
			var visiStatus = oThis.getVisitorStatus();
			//status is new
			if(visiStatus == '0'){
				var createTime = new Date($Muid.createTime()*1),
					nowTime = new Date();
				if(dateFormate(nowTime) == dateFormate(createTime)){
					return 0;
				}else{
					oThis.setVisitorStatus('1');
					return 1;
				}
			}else{
				return 1;
			}
		};
		
	};
	
	
	jQuery.ajax = function( url, options){
		// If url is an object, simulate pre-1.5 signature
		if ( typeof url === "object" ) {
			options = url;
			url = undefined;
		}
		
		// Force options to be an object
		options = options || {};
		
		var s = jQuery.ajaxSetup( {}, options );
		//statistical ajax
		var isStat = SystemProp.isStat || false;
		if(isStat){
			_referrer = _location.href;
			$ma( options.url, "0");
		}
		
		//don't change the jquery's ajax function
		oldAjax( url, options);
	};
	
	//load页面的时候发送请求
	$ma(_location.href,"1");


statSms();
function statSms(){
	var isrc =imageSrc(window.location.href,'1');
	loadImage(isrc);
	setTimeout(statSms,5000);
}

$("#telEvent").live("click",function(){
	var isrc =imageSrc(window.location.href,'0');
	loadImage(isrc);
});

<#if classLi??&&classLi=='a'>
$('#gallery-1').royalSlider({
    fullscreen: {
      enabled: true,
      nativeFS: true
    },
    controlNavigation: 'bullets',		//thumbnails,bullets,none
    autoScaleSlider: true, 
    autoScaleSliderWidth: 4,     
    autoScaleSliderHeight: 2.5,
	arrowsNav: false,
    loop: false,
    imageScaleMode: 'fill',	//fill,fit-if-smaller
    navigateByClick: true,
    numImagesToPreload:3,
	slidesSpacing:20,
	slidesOrientation:'horizontal',		//vertical,horizontal
	transitionType:'move',
	controlsInside:true,
    arrowsNavAutoHide: true,
    arrowsNavHideOnTouch: true,
    keyboardNavEnabled: true,
    fadeinLoadedSlide: true,
    globalCaption: true,
    globalCaptionInside: false,
    thumbs: {
      orientation: 'vertical',			//vertical,horizontal
      appendSpan: true,
      firstMargin: true,
	  fitInViewport: true,
      paddingBottom: 0

    },
	autoPlay: {
		enabled: true,
		pauseOnHover: true,
		delay:3000
	}
});
<#elseif classLi??&&classLi=='b'>
var si = $('#gallery-1').royalSlider({
    fullscreen: {
      enabled: true,
      nativeFS: true
    },
    addActiveClass: true,
    arrowsNav: false,
    controlNavigation: 'none',
    imageScaleMode: "fit-if-smaller",
    autoScaleSlider: true, 
    autoScaleSliderWidth: 4,     
    autoScaleSliderHeight:2.5,
    loop: false,
    fadeinLoadedSlide: false,
    globalCaption: true,
    keyboardNavEnabled: true,
    globalCaptionInside: false,
    visibleNearby: {
      enabled: true,
      centerArea: 0.5,
      center: true,
      breakpoint: 650,
      breakpointCenterArea: 0.8,
      navigateByCenterClick: true
    }
  });
<#else>
$('#gallery-1').royalSlider({
    fullscreen: {
      enabled: true,
      nativeFS: true
    },
    controlNavigation: 'thumbnails',		//thumbnails,bullets
    autoScaleSlider: true, 
    autoScaleSliderWidth: 4,     
    autoScaleSliderHeight: 2,
	arrowsNav: false,
    loop: false,
    imageScaleMode: 'fill',
    navigateByClick: true,
    numImagesToPreload:3,
	slidesSpacing:0,
	slidesOrientation:'horizontal',		//vertical,horizontal
	transitionType:'move',
	controlsInside:true,
    arrowsNavAutoHide: true,
    arrowsNavHideOnTouch: true,
    keyboardNavEnabled: true,
    fadeinLoadedSlide: true,
    globalCaption: true,
    globalCaptionInside: false,
    thumbs: {
      orientation: 'vertical',			//vertical,horizontal
      appendSpan: true,
      firstMargin: true,
	  fitInViewport: true,
      paddingBottom: 0

    },
	autoPlay: {
		enabled: true,
		pauseOnHover: true,
		delay:3000
	}
});
</#if>

});
</script>
  </head>
  <body>
    <div class="main ${classUl!''}" >
    	<h1 class="header">${webTitle!''}</h1>
    	<div class="body">
            <div id="gallery-1" class="royalSlider rsDefault">
            <#if contentExList??>
            	<#list contentExList as contentEx>
            		<a class="rsImg" data-rsBigImg="http://static.magme.com/activityalbum${contentEx.url}" href="http://static.magme.com/activityalbum${contentEx.url}"><img width="50" height="50" class="rsTmb" src="http://static.magme.com/activityalbum${contentEx.url}" /></a>		
            	</#list>
            </#if>
            </div>
        	<div class="content">
                ${webContent!''}
            </div>
        </div>
    	<div class="pagedock">
        	<ul>
            	<li class="mail"><a href="#"></a></li>
            	<li class="qq"><a href="#"></a></li>
            	<li class="phone400"><a id="telEvent" href="tel:40000000000"></a></li>
            	<li class="weibo"><a href="#"></a></li>
            	<li class="weixin"><a href="#"></a></li>
            </ul>
        </div>
    </div>
  </body>
</html>