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
	setCookie("issueId",getUrlValue("id"));
	setCookie("pageId",getUrlValue("pageId"));
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
    
   