//******************************************************************************************
    // To add cookie information to the HTTP header need to use the following Syntax:
    // 
    // document.cookie = "name=value; expires=date; path=path;domain=domain; secure";
    //
    // This function sets a client-side cookie as above.  Only first 2 parameters are required
    // Rest of the parameters are optional. If no CookieExp value is set, cookie is a session cookie.
    //******************************************************************************************
    function setCookie(CookieName, CookieVal, CookieExp, CookiePath, CookieDomain, CookieSecure){
 	    var CookieText = escape(CookieName) + '=' + escape(CookieVal); //escape() : Encodes the String
	    CookieText += (CookieExp ? '; EXPIRES=' + CookieExp.toGMTString() : '');
	    CookieText += (CookiePath ? '; PATH=' + CookiePath : '');
	    CookieText += (CookieDomain ? '; DOMAIN=' + CookieDomain : '');
	    CookieText += (CookieSecure ? '; SECURE' : '');
	    document.cookie = CookieText;
    }

    // This functions reads & returns the cookie value of the specified cookie (by cookie name) 
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
	    return CookieVal;
    }

    // To delete a cookie, pass name of the cookie to be deleted
    function deleteCookie(CookieName){
 	    var tmp = getCookie(CookieName);
	    if(tmp) 
	    { 
	        setCookie(CookieName,tmp,(new Date(1))); //Used for Expire 
	    }
    }

  //function getUrlValue(name)
    function getUrlValue(name){
    	var r = location.search.substr(1).match(new RegExp("(^|&)"+ name +"=([^&]*)(&|$)")); 
    	if (r!=null) return unescape(r[2]); return null;
    }
    
	function addLoadEvent(func){
		var oldonload=window.onload;
		if(typeof window.onload!="function"){
			window.onload=func;
		}else{
			window.onload=function(){
				oldonload();
				func();
			}
		};
	}

 /*
  * UUID
  */
    function UUID(){
    	this.id = this.createUUID();
    }
    // When asked what this Object is, lie and return it's value
    UUID.prototype.valueOf = function(){ return this.id; }
    UUID.prototype.toString = function(){ return this.id; }
    
    UUID.prototype.createUUID = function(){
    	var dg = new Date(1582, 10, 15, 0, 0, 0, 0);
    	var dc = new Date();
    	var t = dc.getTime() - dg.getTime();
    	var tl = UUID.getIntegerBits(t,0,31);
    	var tm = UUID.getIntegerBits(t,32,47);	
    	return tl+tm;
    };
 // Pull out only certain bits from a very large integer, used to get the time
 // code information for the first part of a UUID. Will return zero's if there
 // aren't enough bits to shift where it needs to.
    UUID.getIntegerBits = function(val,start,end){
    	var base16 = UUID.returnBase(val,16);
    	var quadArray = new Array();
    	var quadString = '';
    	var i = 0;
    	for(i=0;i<base16.length;i++){
    		quadArray.push(base16.substring(i,i+1));  
    	}
    	for(i=Math.floor(start/4);i<=Math.floor(end/4);i++){
    		if(!quadArray[i] || quadArray[i] == '') quadString += '0';
    		else quadString += quadArray[i];
    	}
    	return quadString;
    }
 // Replaced from the original function to leverage the built in methods in
 // JavaScript. Thanks to Robert Kieffer for pointing this one out
    UUID.returnBase = function(number, base){
    	return (number).toString(base).toUpperCase();
    };
    
   /*
    * browser
    * version
    */ 
    var ua = navigator.userAgent.toLowerCase();
    var b = {
	    msie: /msie/.test(ua) && !/opera/.test(ua),
	    opera: /opera/.test(ua),
	    safari: /webkit/.test(ua) && !/chrome/.test(ua),
	    firefox: /firefox/.test(ua),
	    chrome: /chrome/.test(ua)
	};
    var vMark = "";
    for (var i in b) {
        if (b[i]) { vMark = "safari" == i ? "version" : i; break; }
    }
    b.version = vMark && RegExp("(?:" + vMark + ")[\\/: ]([\\d.]+)").test(ua) ? RegExp.$1 : "0";
    b.nameCode = (b.msie)?'msie':(b.firefox)?'firefox':(b.chrome)?'chrome':(b.opera)?'opera':(b.safari)?'safari':'others';
    
    //cookie
    var cookieEnabled = navigator.cookieEnabled;
    if(cookieEnabled){
    	setCookie("pubId",getUrlValue("publicationId"));
    	//setCookie("issueId",getUrlValue("id"));
    	//setCookie("pageId",getUrlValue("pageId"));
    }
    
    var magStats = {
    	pubId: getUrlValue("publicationId"),
		issueId: getUrlValue("id"),
		pageId: getUrlValue("pageId"),
		returnStatsUrl: function(){
			return SystemProp.statServerUrl;
		},
		returnUsrUid: function(){
			if(cookieEnabled){
		    	var muid = getCookie('muid');
		    	if (!muid){
					muid = (new UUID()).id;
					setCookie('muid', muid);
				}
		    	return muid;
			}else{
				return (new UUID()).id;
			}
		},
		returnVersion : function(){
			return b.version;
		},
		returnAgent : function(){
			return b.nameCode;
		}
	};
    /**
     * 分享到各种sns平台
     */
    function shareToSns(pubName, issueId, pageNo){
    	var content = "这么有内涵的你一定喜欢《"+pubName+"》杂志。分享给你啦！";
    	if(xx){
    		if(xx=='sina'){
	    		content = content+'http://apps.weibo.com/'+ getUrlValue('pubName');	
	    		sinaShare = '<div id="sinaWeiboFarward" class="popContent"><fieldset class="new"><div><textarea name="content" class="input">'+content+'</textarea></div><div class="tRight"><a id="publishToSina" href="javascript:void(0)" class="btnBS" >分享到微博</a></div></fieldset></div>';
	    		$("#sinaWeiboFarward").remove();
	    		$(sinaShare).appendTo($("body"));
	    		var sinaWeiboFarward = $("#sinaWeiboFarward");
	    		sinaWeiboFarward.fancybox();
	    		$("#publishToSina",sinaWeiboFarward).click(function(e){e.preventDefault();sina_api_share($("#sinaWeiboFarward textarea[name=content]").val());});
	    		//return sina_api_share(content);
    		} else if(xx=='tencent'){
    			return tencent_api_share(pubName);
    		} else if(xx=='kaixin'){
    			content = "要么旅行，要么读书，身体和灵魂必须有一个在路上！还等神马，come on，跟《"+pubName+"》来个灵魂之旅吧！";
    			var url = "http://www.magme.com/mag/" + issueId + "/" + pageNo + ".html"
    			kaixin_api_share(content, url);
    		}
    	}
	}
    /**
     * 邀请平台内的好友
     * @param pubName
     */
    function inviteToSns(pubName){
    	var content = "这么有内涵的你一定喜欢《"+pubName+"》杂志。一起看吧！";
    	if(xx){
    		if(xx=='kaixin'){
    			content = "要么旅行，要么读书，身体和灵魂必须有一个在路上！还等神马，come on，跟《"+pubName+"》来个灵魂之旅吧！";
    			kaixin_api_invitation(content);
    		}
    	}
    }
	
	function kaixin_api_invitation(content){
		var cfg = {
			display : "iframe",
			app_id : getUrlValue("appId"),
			redirect_uri : "http://www.magme.com",
			link : window.location.href,
			linktext : "我也看看",
			text : content
		};
		KX.invitation(cfg);
	}
	function kaixin_api_share(content, url){
		var param = {
			content:content,
			url:url,
//			url:location.href,
			starid:'113153708',
			aid:'0',
			//pic:'http://pic1.kaixin001.com.cn/pic/app/36/90/1162_113369057_platform-60x60.gif',
			style:'11'
		}
		var arr = [];
		for( var tmp in param ){
			arr.push(tmp + '=' + encodeURIComponent( param[tmp] || '' ) )
		}
		var url = 'http://www.kaixin001.com/rest/records.php?'+arr.join('&');
		w=window.screen.width;
		h=window.screen.height;
		sw=0;
		sh=0;
		window.open(url, 'kx_records', 'height='+h+', width='+w+', top='+sh+', left='+sw+', toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no');
	} 
		
	function sina_api_share(content){
		//var flashObj = ($.browser.msie) ? $("#readerTool").get(0) : $("#readerTool2").get(0);
		WB2.anyWhere(function(W){
		// 发布微博接口
		W.parseCMD("/statuses/update.json", function(sResult, bStatus){
		    if(bStatus == true) {
		    	//flashObj.showFlashAlert('分享成功！');
		    	alert('分享成功！');
		    	$("#fancybox-close").click();
		    }
		    else{
		    	//flashObj.showFlashAlert('分享失败！可能是您已经分享过了~');
		    	alert('分享失败！可能是您已经分享过了~');
		    }
		},{
			status : encodeURI(content)
		},{
		    method: 'post'
		});
			
		});
	}

	function tencent_api_share(pubName){
		var url = (yy=='qzone')? 'http://apps.pengyou.com/' : 'http://rc.qzone.qq.com/myhome/';
		url = url + appKey;
		
		fusion2.dialog.share
		({
		  // 可选。分享应用的URL，点击该URL可以进入应用，必须是应用在平台内的地址。
		  url : url,
	
		  // 可选。默认展示在输入框里的分享理由。
		  desc : "这么有内涵的你一定喜欢《"+pubName+"》杂志。分享给你啦！",
	
		  // 必须。应用简要描述。
		  summary : "这么有内涵的你一定喜欢《"+pubName+"》杂志。分享给你啦！",
	
		  // 必须。分享的标题。
		  title : pubName
	
		  // 可选。透传参数，用于onSuccess回调时传入的参数，用于识别请求。
		  //context:"share",
	
		  // 可选。用户操作后的回调方法。
		  //onSuccess : function (opt) {  alert("分享成功！");  },
	
		  // 可选。用户取消操作后的回调方法。
		  //onCancel : function () { alert("Cancelled: " + opt.context);  },
	
		  // 可选。对话框关闭时的回调方法。
		  //onClose : function () { alert("Closed") }
	
		});
	}
	
	function flashComplete(){
		if(xx){
			if(xx=='tencent'){
				var pubNm='';
				/*if (appKey == '100629844') {
					pubNm = '小资';
				}else if (appKey == '100629873') {
					pubNm = '时尚孕妇标准';
				}else if (appKey == '100627803') {
					pubNm = '汽车公社';
				}else if (appKey == '100627802') {
					pubNm = '品味生活杂志';
				}else if (appKey == '100627512') {
					pubNm = '耐卡';
				}else if (appKey == '100627798') {
					pubNm = '梦想方格';
				}else if (appKey == '100627032') {
					pubNm = 'ME';
				}else if (appKey == '100629777') {
					pubNm = 'let go';
				}else if (appKey == '100627514') {
					pubNm = '钮扣';
				}else if (appKey == '100629781') {
					pubNm = 'shanghaifamill';
				}else if (appKey == '100627827') {
					pubNm = '直销刊头';
				}else if (appKey == '100629866') {
					pubNm = '女刊瘦身';
				}else if (appKey == '100629827') {
					pubNm = '女友';
				}else if (appKey == '100629870') {
					pubNm = '普益财富';
				}*/
				if (pubNm != '') {
					var myDate = new Date();
					var month = (myDate.getMonth()+1 < 10) ? ("0"+(myDate.getMonth()+1)) : (myDate.getMonth()+1)
					var datetime=myDate.getDate()<10?("0"+myDate.getDate()):myDate.getDate();
					var nowDate= myDate.getFullYear()+"年"+month+"月"+datetime+"日"; 
					var content = "";
					content += "尊敬的《"+pubNm+"》用户：<br/>";
					content += "&nbsp;&nbsp;&nbsp;&nbsp;《"+pubNm+"》应用因故需要在本公告发布满60日后终止运营，";
					content += "届时及以后用户将不能使用相关服务。";
					content += "请各位用户周知并相互转告，谢谢！<br/>";
					content += "<div style='text-align:right'>《"+pubNm+"》应用</div>";
					content += "<div style='text-align:right'>"+ nowDate+"</div>";
					offDiv = '<div id="offline" class="popContent"><div style="width: 200px;">'+content+'</div></fieldset></div>';
					$("#offline").remove();
					$(offDiv).appendTo($("body"));
					var off = $("#offline");
					off.fancybox();
				}
			}
		}
	};