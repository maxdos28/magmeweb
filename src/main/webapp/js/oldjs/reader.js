$(document).ready(function(){
//	setCookie("pubId", getUrlValue("publicationId"));
//	setCookie("issueId", getUrlValue("issueId"));
	
	var minHeight = $(window).height() - 106;
	$("#embedSwf1,#embedSwf2").height( minHeight<400 ? 400 :minHeight);
	
	
	//resize-----------------------------------------------------
	var timer = null;
	$(window).unbind("resize").bind('resize',function() {
		if(!timer){
			timer = setTimeout(function(){
				var currentHeight = $(window).height() - 106;
				$("#embedSwf1,#embedSwf2").height( (currentHeight < 400) ? 400: currentHeight );
				timer = null;
			},300);
		}
	});
	//mousewheel--------------------------------------------------
	$("#swfReader").mousewheel(function(event, delta){
		event.stopPropagation();
		event.preventDefault();
		if(delta > 0){
			zoomPlus();
		}else{
			zoomMinus();
		}
	});
	
	var flashObj = ($.browser.msie) ? $("#embedSwf1").get(0) : $("#embedSwf2").get(0);
	//login-register------------------
	$("#login ,#register").unbind('click').bind('click',function(){
		flashObj.showLoginPanel();
	});
	function zoomPlus(){ 
		flashObj.zoomFactorPlus();
	}
	function zoomMinus(){
		flashObj.zoomFactorMinus();
	}
	
	var readerType = 2;
	var leftPageId = 0;
	var rightPageId = 0;
	
	//falsh调用方法
	function setReaderStat(p_readerType, p_leftPageId, p_rightPageId) {
		readerType = p_readerType;
		leftPageId = p_leftPageId;
		rightPageId = p_rightPageId;
	}
	
	//设置footerMini
	$(".footer").height(26).css("minHeight",26);
	footerMini();

});


function closeReaderLoginPop(){
	var flashObj = ($.browser.msie) ? $("#embedSwf1").get(0) : $("#embedSwf2").get(0);
	flashObj.closeLoginPanel();
	flashObj.notifyLogin();
}

