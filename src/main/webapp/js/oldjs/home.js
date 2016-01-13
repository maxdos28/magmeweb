//for newsBar
;(function($){
	jQuery.newsBar = function(setting) {
	var defaultOpt = {
		time: 8000
	};
	var opt = $.extend({},defaultOpt,setting);
	var time = opt.time;
	var $id		= $("#newsBar");
	var $item	= $id.find(".item");
	var itemNumber	= $item.length;
	var currentId = 0;
	var autoPlay = setInterval(fnAutoPlay,time);
	$item.eq(currentId).show();
	//function
	function fnSetCurrent(id){
		currentId = id;
		$item.eq(currentId).show();
		$ctrlItem.eq(currentId).addClass("current");
	}
	function fnAutoPlay(){
		$item.eq(currentId).fadeOut(750);
		if(currentId < itemNumber - 1){
			currentId ++;
		}else {
			currentId = 0;
		}
		$item.eq(currentId).fadeIn(750);
	}; 
};
})(jQuery);

$(function(){
	$.newsBar({time:6000});
});











//for homeBanner===================================================================================
;(function($){
	jQuery.homeBanner = function(setting) {
	var defaultOpt = {
		time: 8000
	};
	var opt = $.extend({},defaultOpt,setting);
	var time = opt.time;
	var $id		= $("#homeBanner");
	var $item	= $id.find(".item");
	var $inner	= $id.find(".inner");
	var $btnLR	= $id.find(".btnLR");
	var $ctrlItem;
	var itemWidth 	= $item.outerWidth();
	var itemHeight	= $item.outerHeight();
	var itemNumber	= $item.length;
	var currentId = 0;
	var lock = 0;
	var lockm = 0;
	var ctrlHtml;
	var autoPlay = setInterval(fnAutoPlay,time);
	var delayTime = 1500;
	
	//set ctrl html
	ctrlHtml = "<div class='control'>";
	for (i=1;i<itemNumber+1;i++){ctrlHtml += "<a href='javascript:void(0)'></a>";}
	ctrlHtml += "</div>";
	$id.append(ctrlHtml);
	$ctrlItem = $("#homeBanner .control a");
	//ready
	$inner.width(itemWidth*itemNumber);
	fnSetCurrent(0);
	//event
	$id.hover(function(){lockm=1},function(){lockm=0});
	
	$ctrlItem.bind("click",function(){
		if($(this).index() != currentId&&lock==0){
			lock=1;
			fnChangeImg($(this).index());
			fnSetCurrent($(this).index());
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
					currentId--
				}else{
					currentId=itemNumber-1
				}
				
			}else{
				if(currentId<itemNumber-1){
					currentId++
				}else{
					currentId=0
				}
			}
			fnSetCurrent(currentId);
			fnChangeImg(currentId);
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
	function fnChangeImg(id){
		currentId=id;
		$inner.animate({marginLeft:-itemWidth*currentId},delayTime,"easeOutQuint");
	}
	function fnAutoPlay(){
		if(lock==0&&lockm==0){
			lock=1;
			if(currentId < itemNumber - 1){
				currentId ++;
			}else {
				currentId = 0;
			}
			fnSetCurrent(currentId);
			fnChangeImg(currentId);
			setTimeout(function(){lock=0},delayTime);
		}
	}; 
};
})(jQuery);

$(function(){
	$.homeBanner({time:8000});
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
	var lock = 0;
	var lockm = 0;
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
//$book.css({opacity:0}); //for bookHide
//$book.find("span").css({opacity:0});
//$item.eq(0).find("a").css({opacity:1});
//$item.eq(0).find("a span").css({opacity:1});
fnSetCurrent(0);
//event
	$item.hover(function(){lockm=1},function(){lockm=0});
	$("#magezineShow .control a, .conMagezineShow h2 a").bind("click",function(){
		if($(this).index() != currentId&&lock==0){
			lock=1;
			currentId = $(this).index();
			fnSetCurrent(currentId);
			fnChangeImg(currentId);
			clearInterval(autoPlay);
			autoPlay = setInterval(fnAutoPlay,time);
			setTimeout(function(){lock=0},delayTime);
		}
	});
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
	function fnChangeImg(id){
		currentId=id;
		//$book.animate({opacity:0},300); //for bookHide
		//$book.find("span").css({display:"none"}); //for bookHide //for ie8 bug
		$inner.animate({marginTop:-itemHeight*currentId},delayTime,"easeOutQuint");
		//setTimeout(function(){
			//$item.eq(currentId).find("a").animate({opacity:1},500);
			//$book.find("span").css({display:"block"}); //for bookHide //for ie8 bug
		//},1000);
		
	}
	function fnAutoPlay(){
		if(lock==0&&lockm==0){
			lock=1;
			if(currentId < itemNumber - 1){
				currentId ++;
			}else {
				currentId = 0;
			}
			fnSetCurrent(currentId);
			fnChangeImg(currentId);
			setTimeout(function(){lock=0},delayTime);
		}
	}; 




};
})(jQuery);

$(function(){
	$.homeBookList({time:12000});
});








$(function(){
//for con hover---------------------------------------------------------------------//
$(".conA").hover(
	function(){
		$(this).addClass("conBg2");
		if(!$(this).hasClass("conMagezineShow")){
			$(this).find("h2 a").addClass("hover");
		}else{
			$(this).find("h2").addClass("hover");
		}
	},
	function(){
		$(this).removeClass("conBg2");
		if(!$(this).hasClass("conMagezineShow")){
			$(this).find("h2 a").removeClass("hover");
		}else{
			$(this).find("h2").removeClass("hover");
		}
	}
);
//for con h2 click---------------------------------------------------------------------//
$(".conA h2").mousedown(function(){
	if($(this).find("a").attr("href")!="javascript:void(0)"){
		$(this).addClass("active");
	}
}).mouseup(function(){
	$(this).removeClass("active");
}).mouseout(function(){
	$(this).removeClass("active");
}).mouseover(function(){
	if($(this).find("a").attr("href")!="javascript:void(0)"){$(this).find("a").css({cursor:"pointer"})}
	else{$(this).find("a").css({cursor:"default"})}
});
//for add corner(conStarShare, conCarTrip, conFinanceLife)
var obj1 = $(".conStarShare .conBody li");
var obj2 = $(".conCarTrip .conBody li");
var obj3 = $(".conFinanceLife .conBody li");
obj1.add(obj2).add(obj3).append("<em class='corner1'></em><em class='corner2'></em><em class='corner3'></em><em class='corner4'></em>");















});




