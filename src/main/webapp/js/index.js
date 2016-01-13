$(function(){
	//广告banner用
	$(".slideBanner").hover(function(){
		$(this).not(":animated").animate({height:300},700).find(".close").fadeOut(350);
		$(this).find(".inner:not(:animated)").animate({marginTop:0},700);
	},function(){
		$(this).stop().animate({height:25},350).find(".close").fadeIn(350);
		$(this).find(".inner").stop().animate({marginTop:-275},350);
	});
	//help_tip----------------------------------------------
	function help_tip_click (){
		$(window).scrollTop(0);
		if(!isIE6){
			var tips = '<img class="png hotTag" src="images/tips/hotTag.png" /><img class="png homeTag" src="images/tips/homeTag.png" />'
				+'<img class="png homeMgz" src="images/tips/homeMgz.png" /><img class="png homeLike" src="images/tips/homeLike.png" />'
				+'<img class="png homeEvent" src="images/tips/homeEvent.png" /><img class="png homeClickTry" src="images/tips/homeClickTry.png" />';
		}else{
			var tips = '<img class="hotTag" src="images/tips/hotTag.gif" /><img class="homeTag" src="images/tips/homeTag.gif" />'
				+'<img class="homeMgz" src="images/tips/homeMgz.gif" /><img class="homeLike" src="images/tips/homeLike.gif" />'
				+'<img class="homeEvent" src="images/tips/homeEvent.gif" /><img class="homeClickTry" src="images/tips/homeClickTry.gif" />';
		}
		$.lightBox(tips,function(){
			
			$("#helpTip").bind('click',function(e){
				e.preventDefault();
				e.stopPropagation();
				$("#helpTip").unbind('click');
				help_tip_click();
			});
		},null,null,true);
	}
	$("#helpTip").bind('click',function(e){
		e.preventDefault();
		e.stopPropagation();
		$("#helpTip").unbind('click');
		help_tip_click();
	});
	//index
	var isShowHelpTip = getCookie("is_show_help_tip");
	if(!isShowHelpTip){
		setCookie("is_show_help_tip",'1',new Date("December 31,2120"));
		$("#helpTip").click();
	}
	
});
