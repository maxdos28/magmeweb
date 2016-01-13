;(function($){
jQuery.topicBanner = function(id,time) {
var $id = $(id);
var $item = $id.find(".item");
var $img = $item.find("img");
var $ctrl;
var imgW = 670;
var imgH = 300;
var currentId = 0;
var targetId = 0;
var speed = time;
var speedOffset = 2000;
var fadeTime = 2000;
var lock = 0;
var length = $item.length;
var play;


//ready
$id.append("<div class='loading'></div><div class='ctrl'><a class='turnLeft png' href='javascript:void(0)'></a><a class='turnRight png' href='javascript:void(0)'></a></div>");
$ctrl = $id.find(".ctrl>a");


if($.browser.safari){
	$id.onImagesLoad({
		selectorCallback: function(){
			$id.find(".loading").fadeOut(300);
			fnChangeBanner();
			play = setInterval(fnAutoPlay,time-speedOffset);
		} 
	});
}else{
	$id.find(".loading").fadeOut(300);
	fnChangeBanner();
	play = setInterval(fnAutoPlay,time-speedOffset);
}





$ctrl.bind("click",function(){
	if(lock == 0){
		lock = 1;
		if($(this).hasClass("turnLeft")){
			if(currentId > 0){
				currentId--;
			}else{
				currentId = length - 1;
			}
		}else{
			if(currentId < length - 1){
				currentId++;	
			}else{
				currentId = 0;
			}
		}
		$img.stop(true,false);
		fnChangeBanner();
		clearInterval(play);
		play = setInterval(fnAutoPlay,time-speedOffset);
	}

})
var dsd;

function fnChangeBanner(){
	$item.fadeOut(fadeTime);
	var ran = Math.floor(Math.random()*6+1);
	switch (ran){
		case 1: 
			$item.eq(currentId).find("img").css({width:imgW*1.2,height:imgH*1.2,left:-imgW*0.1,top:-imgH*0.1});
			$item.eq(currentId).fadeIn(fadeTime).find("img").animate({width:imgW,height:imgH,left:0,top:0},time,"easeOutQuad");
			break;
		case 2: 
			$item.eq(currentId).find("img").css({width:imgW*1.0,height:imgH*1.0,left:0,top:0});
			$item.eq(currentId).fadeIn(fadeTime).find("img").animate({width:imgW*1.2,height:imgH*1.2,left:-imgW*0.1,top:-imgH*0.1},time,"easeOutQuad");
			break;
		case 3: 
			$item.eq(currentId).find("img").css({width:imgW*1.2,height:imgH*1.2,left:0,top:0});
			$item.eq(currentId).fadeIn(fadeTime).find("img").animate({width:imgW*1.2,height:imgH*1.2,left:-imgW*0.2,top:-imgH*0.2},time,"easeOutQuad");
			break;
		case 4: 
			$item.eq(currentId).find("img").css({width:imgW*1.2,height:imgH*1.2,left:-imgW*0.2,top:-imgH*0.2});
			$item.eq(currentId).fadeIn(fadeTime).find("img").animate({width:imgW*1.2,height:imgH*1.2,left:0,top:0},time,"easeOutQuad");
			break;
		case 5 : 
			$item.eq(currentId).find("img").css({width:imgW*1.2,height:imgH*1.2,left:0,top:-imgH*0.1});
			$item.eq(currentId).fadeIn(fadeTime).find("img").animate({width:imgW*1.2,height:imgH*1.2,left:-imgW*0.2,top:-imgH*0.1},time,"easeOutQuad");
			break;
		case 6 : 
			$item.eq(currentId).find("img").css({width:imgW*1.2,height:imgH*1.2,left:-imgW*0.2,top:-imgH*0.1});
			$item.eq(currentId).fadeIn(fadeTime).find("img").animate({width:imgW*1.2,height:imgH*1.2,left:0,top:-imgH*0.1},time,"easeOutQuad");
			break;
	}
	$item.eq(currentId).fadeIn(fadeTime).find("img").animate({width:imgW,height:imgH,left:0,top:0},time,"easeOutQuad");
	setTimeout(function(){lock=0},fadeTime);

}

function fnAutoPlay(){
	if(currentId < length - 1){
		currentId += 1;
	}else{
		currentId = 0;
	}
	fnChangeBanner();
}

}})(jQuery);




;(function($){
jQuery.topicSlide = function(id) {

var $id = $(id);
var $obj;
var currentId = 0;
var move = 645;
var itemWidth = 215;
var speed = 1000;
var lock = 0;
var $inner;
var $item;
var $ctrl;
var $btnLR;
var length;
//ready ctrlHTML
$id.each(function(){
	var ctrlHtml = "";
	$(this).append("<div class='ctrl'></div>");
	var page = Math.floor($(this).find("a").length/3);
	for(var i=0;i<page;i++){
		ctrlHtml+="<a href='javascript:void(0)'></a>";
	}
	$(this).find(".ctrl").html(ctrlHtml).find("a:eq(0)").addClass("current");
	$(this).find(".inner").width(itemWidth*$(this).find("a").length);
});
$id.find(".ctrl>a").live("click",function(){
	currentId = $(this).index();
	$obj = $(this).parent().parent();
	$inner = $obj.find(".inner");
	$inner.animate({marginLeft:-(currentId*move)},speed,"easeOutCubic");
	$(this).addClass("current").siblings().removeClass("current");
	
});

}})(jQuery); 






;(function($){
jQuery.switchText = function(id) {

var $id = $(id);
var $obj;
var currentId = 0;
var speed = 500;
var lock = 0;
var $item = $id.find(".conTypeText");
var $ctrl = $(".conCategory .conBody a");

$item.eq(currentId).show();
$ctrl.bind("click",function(){
	currentId = $(this).index();
	$item.eq(currentId).fadeIn(speed).siblings().hide();
	$ctrl.eq(currentId).addClass("current").siblings().removeClass("current");
});
}})(jQuery); 




$(function(){
	//文字格式显示收藏图标
	$(".conTypeText .item").mouseenter(function(){
		$(this).find(".tools>em").css({display:"block"});
	}).mouseleave(function(){
		$(this).find(".tools>em").not($(".favCurrent")).css({display:"none"});
	})

});










