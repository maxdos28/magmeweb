//magme.com站外调用js

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


$(function(){
	
	
	//通栏banner启用
	if($("#fullBanner").length>0){
		$("#fullBanner").show();
	}
	
	
	//短页面页脚调整
	if($(document).height()<=$(window).height()){
		$(".footer20150126").css({position:"absolute",bottom:0,left:0,width:"100%"})
	}
	
	
	//移动端处理
	if((browser.versions.ios || browser.versions.android)&& browser.versions.mobile){
		//添加viewport
		$('head').prepend('<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" id="viewport" />');
		//phone
		if(browser.versions.iPhone||$(window).width()<450){
			$('body').addClass('phoneStyle');
			$("#fullBanner").remove();
		}
		//pad
		else{
			$("body").addClass("padStyle");
		}
		//首页处理
		if($("#homeWall").length>0){
			$("#homeWall .sideRight").remove();
			$(".footer20150126 .footerLink").remove();
			$('#homeWall').masonry({itemSelector: '.item'});
		}
		
	}
   

	
	
	
	
	
});































