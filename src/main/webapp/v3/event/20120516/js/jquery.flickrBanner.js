;(function($){
jQuery.flickrBanner = function(id,time) {
var $id = $(id);
var $item = $id.find(".item");
var $img = $item.find("img");
var $ctrl;
var showW = 1024;
var showH = 768;
var showScale = showW/showH;
var imgW;
var imgH;
var imgScale;
var currentId = 0;
var speed = time;
var speedOffset = 2000;
var fadeTime = 3000;
var lock = 0;
var length = $item.length;
var play;


//ready
$id.append("<div class='ctrl'><a class='turnLeft' href='javascript:void(0)'></a><a class='turnRight' href='javascript:void(0)'></a></div>");
$ctrl = $id.find(".ctrl>a");

	
	
$item.eq(0).onImagesLoad({
	selectorCallback: function(){
		fnChangeBanner();
		play = setInterval(fnAutoPlay,time-speedOffset);
	} 
});








$ctrl.bind("click",function(){
	if(lock == 0){
		lock = 1;
		var cid = currentId;
		$item.eq(cid).find("img").stop(true,false);
		setTimeout(function(){$item.eq(cid).find("img").css({width:"auto",height:"auto",left:0,top:0})},fadeTime)
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
		
		fnChangeBanner();
		clearInterval(play);
		play = setInterval(fnAutoPlay,time-speedOffset);
	}

})



function fnChangeBanner(){
	$item.fadeOut(fadeTime);
	$item.eq(currentId).show();
	imgW = $item.eq(currentId).find("img").width();
	imgH = $item.eq(currentId).find("img").height();
	imgScale = imgW/imgH;
	$item.eq(currentId).hide();
	
	var ran = Math.floor(Math.random()*4+1);
	
	if( imgScale < showScale-0.3 ){
		switch (ran){
		case 1: 
		case 2:
			$item.eq(currentId).find("img").css({width:showW,height:showW/imgScale,left:0,top:-(showW/imgScale-showH)});
			$item.eq(currentId).fadeIn(fadeTime).find("img").animate({top:0},time,"easeInOutCubic");
			break;
		case 3: 
		case 4: 
			$item.eq(currentId).find("img").css({width:showW,height:showW/imgScale,left:0,top:0});
			$item.eq(currentId).fadeIn(fadeTime).find("img").animate({top:-(showW/imgScale-showH)},time,"easeInOutCubic");
			break;
		}
	}else if( imgScale > showScale-0.3 && imgScale < showScale){
		switch (ran){
		case 1: 
			$item.eq(currentId).find("img").css({width:showW,height:showW/imgScale,left:0,top:0});
			$item.eq(currentId).fadeIn(fadeTime).find("img").animate({width:showW*1.2,height:showW/imgScale*1.2,left:-showW*0.1,top:-(showW/imgScale*1.2-showH)},time,"easeInOutCubic");
			break;
		case 2: 
			$item.eq(currentId).find("img").css({width:showW*1.2,height:showW/imgScale*1.2,left:0,top:0});
			$item.eq(currentId).fadeIn(fadeTime).find("img").animate({width:showW,height:showW/imgScale,left:0,top:0},time,"easeInOutCubic");
			break;
		case 3: 
			$item.eq(currentId).find("img").css({width:showW,height:showW/imgScale,left:0,top:-(showW/imgScale-showH)});
			$item.eq(currentId).fadeIn(fadeTime).find("img").animate({width:showW*1.2,height:showW/imgScale*1.2,left:-showW*0.1,top:0},time,"easeInOutCubic");
			break;
		case 4: 
			$item.eq(currentId).find("img").css({width:showW*1.2,height:showW/imgScale*1.2,left:-showW*0.2,top:-(showW/imgScale-showH)});
			$item.eq(currentId).fadeIn(fadeTime).find("img").animate({width:showW,height:showW/imgScale,left:0,top:0},time,"easeInOutCubic");
			break;
		}
	}else if( imgScale > showScale && imgScale < showScale+0.3){
		switch (ran){
		case 1: 
			$item.eq(currentId).find("img").css({width:showH*imgScale,height:showH,left:0,top:0});
			$item.eq(currentId).fadeIn(fadeTime).find("img").animate({width:showH*imgScale*1.2,height:showH*1.2,left:-(showH*imgScale-showW),top:-showH*0.1},time,"easeInOutCubic");
			break;
		case 2: 
			$item.eq(currentId).find("img").css({width:showH*imgScale*1.2,height:showH*1.2,left:-(showH*imgScale-showW),top:-showH*0.1});
			$item.eq(currentId).fadeIn(fadeTime).find("img").animate({width:showH*imgScale,height:showH,left:0,top:0},time,"easeInOutCubic");
			break;
		case 3: 
			$item.eq(currentId).find("img").css({width:showH*imgScale,height:showH,left:0,top:0});
			$item.eq(currentId).fadeIn(fadeTime).find("img").animate({width:showH*imgScale*1.2,height:showH*1.2,left:0,top:0},time,"easeInOutCubic");
			break;
		case 4: 
			$item.eq(currentId).find("img").css({width:showH*imgScale,height:showH,left:0,top:0});
			$item.eq(currentId).fadeIn(fadeTime).find("img").animate({width:showH*imgScale*1.2,height:showH*1.2,left:-(showH*imgScale*1.2-showW),top:-showH*0.2},time,"easeInOutCubic");
			break;
		}
	}else{
		switch (ran){
		case 1: 
		case 2: 
			$item.eq(currentId).find("img").css({width:showH*imgScale,height:showH,left:-(showH*imgScale-showW),top:0});
			$item.eq(currentId).fadeIn(fadeTime).find("img").animate({left:0},time,"easeInOutCubic");
			break;
		case 3: 
		case 4: 
			$item.eq(currentId).find("img").css({width:showH*imgScale,height:showH,left:0,top:0});
			$item.eq(currentId).fadeIn(fadeTime).find("img").animate({left:-(showH*imgScale-showW)},time,"easeInOutCubic");
			break;
		}
	}
	
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
