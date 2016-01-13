//for homeBanner
$(function(){
var time	= 5000;
var $id		= $("#homeBanner");
var $item	= $id.find(".item");
var $inner	= $id.find(".inner");
var $btnLR	= $id.find(".btnLR");
var $ctrlItem;
var itemWidth 	= $item.outerWidth();
var itemHeight	= $item.outerHeight();
var itemNumber	= $item.length;
var currentId = 0;
var targetId = 0;
var lock = 0;
var ctrlHtml;
var autoPlay = setInterval(fnAutoPlay,time);
var delayTime = 2000;
//set ctrl html
ctrlHtml = "<div class='control'>";
for (i=1;i<itemNumber+1;i++){ctrlHtml += "<a href='javascript:void(0)'></a>";}
ctrlHtml += "</div>";
$id.append(ctrlHtml);
$ctrlItem = $("#homeBanner .control a");
//ready
$item.eq(0).show();
fnSetCurrent(0);
//event
$ctrlItem.bind("click",function(){
	if($(this).index() != currentId&&lock==0){
		lock=1;
		targetId = $(this).index();
		if(targetId>currentId){
			fnNext();
		}else{
			fnPrev();
		}
		currentId=targetId;
		fnSetCurrent(currentId);
		clearInterval(autoPlay);
		autoPlay = setInterval(fnAutoPlay,time);
		setTimeout(function(){lock=0},delayTime);
	}
});
$btnLR.bind("click",function(){
	if(lock==0){
		lock=1;
		if($(this).hasClass("turnLeft")){
			if(currentId>0){
				targetId = currentId - 1;
			}else{
				targetId = itemNumber - 1;
			}
			fnPrev();
		}else{
			if(currentId<itemNumber-1){
				targetId = currentId + 1;
			}else{
				targetId = 0;
			}
			fnNext();
		}
		currentId=targetId;
		fnSetCurrent(currentId);
		clearInterval(autoPlay);
		autoPlay = setInterval(fnAutoPlay,time);
		setTimeout(function(){lock=0},delayTime);
	
	}
});
$btnLR.filter(".turnLeft").hover(function(){
	$(this).animate({marginLeft:50},200);
},function(){
	$(this).animate({marginLeft:0},100);
}).end().filter(".turnRight").hover(function(){
	$(this).animate({marginLeft:0},200);
},function(){
	$(this).animate({marginLeft:50},100);
});

//function
function fnSetCurrent(id){
	currentId=id;
	$ctrlItem.removeClass("current");
	$ctrlItem.eq(currentId).addClass("current");
}
function fnAutoPlay(){
	if(lock==0){
		lock=1;
		targetId = currentId + 1;
		if(targetId == itemNumber ){
			targetId = 0;
		}else{
			targetId = currentId+1;
		}
		fnNext();
		if(currentId < itemNumber - 1){
			currentId += 1;
		}else{
			currentId = 0;
		}
		fnSetCurrent(currentId);
		setTimeout(function(){lock=0},delayTime);
	}
}
function fnPrev(){
	$item.eq(targetId).show().css({left:-itemWidth});
	$item.eq(currentId).animate({left:itemWidth},delayTime,"easeOutQuint");
	$item.eq(targetId).animate({left:0},delayTime,"easeOutQuint");
}
function fnNext(){
	$item.eq(targetId).show().css({left:itemWidth});
	$item.eq(currentId).animate({left:-itemWidth},delayTime,"easeOutQuint");
	$item.eq(targetId).animate({left:0},delayTime,"easeOutQuint");
}
});





//for magezineShow===================================================================================
;(function($){
	jQuery.homeBookList = function(setting) {
	var defaultOpt = {
		time: 8000
	};
	var opt = $.extend({},defaultOpt,setting);
	var time = opt.time;
	var $id		= $("#magezineShow");
	var $item	= $id.find(".item");
	var $inner	= $id.find(".inner");
	var $btnLR	= $id.find(".btnLR");
	var $book	= $id.find(".item a");
	var $ctrlItem;
	var $ctrlItemText = $(".conMagezineShow h2 a");
	var itemWidth 	= $item.outerWidth();
	var itemHeight	= $item.outerHeight();
	var itemNumber	= $item.length;
	var currentId = 0;
	var targetId = 0;
	var lock = 0;
	var playLock=0;
	var ctrlHtml;
	var autoPlay;
	var delayTime = 2000;

setTimeout(function(){autoPlay = setInterval(fnAutoPlay,time)},time/2);
//set ctrl html
ctrlHtml = "<div class='control'>";
for (i=1;i<itemNumber+1;i++){ctrlHtml += "<a href='javascript:void(0)'></a>";}
ctrlHtml += "</div>";
$id.append(ctrlHtml);
$ctrlItem = $id.find(".control a");
//ready
$item.eq(0).show();
$book.css({opacity:0});
$item.eq(0).find("a").css({opacity:1});
fnSetCurrent(0);
//event
$("#magezineShow .control a, .conMagezineShow h2 a").bind("click",function(){
	if($(this).index() != currentId&&lock==0){
		lock=1;
		targetId = $(this).index();
		if(targetId>currentId){
			fnNext();
		}else{
			fnPrev();
		}
		currentId=targetId;
		fnSetCurrent(currentId);
		clearInterval(autoPlay);
		autoPlay = setInterval(fnAutoPlay,time);
		setTimeout(function(){lock=0},delayTime);
	}
});
$item.hover(function(){playLock=1},function(){playLock=0});
//function
function fnSetCurrent(id){
	currentId=id;
	$ctrlItem.removeClass("current");
	$ctrlItem.eq(currentId).addClass("current");
	if($ctrlItemText!=null){
		$($ctrlItemText).removeClass("current");
		$($ctrlItemText).eq(id).addClass("current");
	}
}
function fnAutoPlay(){
	if(lock==0 && playLock==0){
		lock=1;
		targetId = currentId + 1;
		if(targetId == itemNumber ){
			targetId = 0;
		}else{
			targetId = currentId+1;
		}
		fnNext();
		if(currentId < itemNumber - 1){
			currentId += 1;
		}else{
			currentId = 0;
		}
		fnSetCurrent(currentId);
		setTimeout(function(){lock=0},delayTime);
	}
}
function fnPrev(){
	$item.eq(targetId).show().css({top:-itemHeight});
	$book.animate({opacity:0},300);
	setTimeout(function(){$item.eq(currentId).find("a").animate({opacity:1},500)},1000);
	$item.eq(currentId).animate({top:itemHeight},delayTime,"easeOutQuint");
	$item.eq(targetId).animate({top:0},delayTime,"easeOutQuint");
}
function fnNext(){
	$item.eq(targetId).show().css({top:itemHeight});
	$book.animate({opacity:0},300);
	setTimeout(function(){$item.eq(currentId).find("a").animate({opacity:1},500)},1000)
	$item.eq(currentId).animate({top:-itemHeight},delayTime,"easeOutQuint");
	$item.eq(targetId).animate({top:0},delayTime,"easeOutQuint");
}

};
})(jQuery);

$(function(){
	$.homeBookList({time:4000});
});







//for home con hover---------------------------------------------------------------------//
$(function(){
$(".conA").hover(
	function(){
		$(this).addClass("conBg2");
	},
	function(){
		$(this).removeClass("conBg2");
	}
);

$(".conA h2").mousedown(function(){
	$(this).addClass("active");
}).mouseup(function(){
	$(this).removeClass("active");
}).mouseout(function(){
	$(this).removeClass("active");
});



});








