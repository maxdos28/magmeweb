;$(function(){
fnReadyTable();




//bookBar
var $showBar = $(".showBar");
if($showBar.length > 0){
	var $bookBar = $showBar.find(".bookBar");
	var bookBarLock=0;
	var bookBarHeight;
	$showBar.live('mouseover',function(){
		if(!$(this).hasClass("doing") && !$(this).hasClass("delete")){
			$(this).find(".bookBar").show();
			var mgzNameLength = $(this).find("span").html().replace(/[\u4E00-\u9FA5]/g,"00").length;
		}
	});
	$showBar.live('mouseout',function(){
		$(this).find(".bookBar").hide();
	});
}



//slideDown
var $showSlide = $(".showSlide");
if($showSlide.length > 0){
	var $slideDown = $showSlide.find(".slideDown>span");
	var $slideMenu = $showSlide.find(".slideDown>p");
	var slideDownLock=0;
	$slideDown.live('mouseenter',function(){
		$(this).next().fadeIn(200);
	});
	$slideMenu.live('mouseleave',function(){
		$(this).hide();
	});
	$showSlide.live('mouseleave',function(){
		$(this).find(".slideDown>p").hide();
	});
}



//menu
var $sub = $(".headerAdmin .subNav>ul>li").hover(function(){
	$(this).find("ul").show();
},function(){
	$(this).find("ul").hide();
})





});









