
(function(){
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
		var imageSrc = SystemProp.statServerUrl+"?";
		var ispv = ispv || '0';
		if(typeof dataUrl !== 'string') return;
		var userId = (new MagCookie().getCookie("magmeUserId")) || -1;
		var data = {
			"url": dataUrl,
			"muid": $Muid.getMuid(),
			"oldvisitor": new oldVisitor().isOldVisitor(),
			"resolution": getResolution(),
			"userid": userId,
			"ispv":ispv,
			"refer":_referrer,
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
})();