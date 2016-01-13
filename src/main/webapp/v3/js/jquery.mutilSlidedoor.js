//multiType slidedoor version 1.0 by lewis
//HOW TO USE
//$.jquerySlideDoor("#slide1", 切换方式, 文字模式, 左右翻页, 动画时间)
//切换方式：1高亮闪出，2左右滚动，3叠加变换，4上下滚动，5向右循环
//文字模式：0隐藏，0-1透明度
//左右翻页：0隐藏，1可左右切换(到底归0重新开始)，2可左右切换(到底停止)
//动画时间：0为禁用自动播放，毫秒数


;(function($){
jQuery.jquerySlideDoor = function(id,showType,txtType,btnLR,time) {
if(time==0 || btnLR==2){time=3000000};
var $inner		= $(id+ " .doorList .inner");
var $item		= $(id+ " .doorList .inner .item");
var $img			= $(id+ " .doorList .inner .item img");
var $itemWidth = $item.outerWidth();
var $itemHeight = $item.outerHeight();
var $itemNumber = $item.length;
var currentId = 0;
var lock = 0;
var delayTime;
var autoPlay = setInterval(changeImg,time);



//ready showType
if(showType==1 || showType==2){
	$item.find(".info").show().css({opacity:0});
	$item.eq(0).find(".info").css({bottom:0,opacity:txtType});
}else if(showType==3){
	$item.find(".info").show().css({opacity:0});
	$item.eq(0).find(".info").css({bottom:-50,opacity:txtType});
	$item.css({display:"none",position:"absolute",top:0,left:0,"z-index":2});
	$item.eq(0).show();
}else if(showType==4){
	$item.find(".info").show().css({opacity:0});
	$item.eq(0).find(".info").css({bottom:0,opacity:txtType});
	$inner.width($item.width());
	$item.css({float:"none"});
}else if(showType==5){
	$item.find(".info").show().css({opacity:0});
	$item.eq(0).find(".info").css({bottom:0,opacity:txtType});
}
//ready txtType
if(txtType==0){
	$item.find(".info").hide();
}
//ready leftRight btnLR
if(btnLR!=0){
	var btnLRHtml = "<a class='btnLR left' href='javascript:void(0)'><</a><a class='btnLR right' href='javascript:void(0)'>></a>"
	$(id).append(btnLRHtml);
}
if(btnLR==2){
	$(id+" .left").addClass("stop");
}

//set ctrl html
var ctrlHtml = "<div class='ctrl png'><ul>";
for (var i=1;i<$itemNumber+1;i++){ctrlHtml += "<li><a href='javascript:void(0)'>"+i+"</a></li>";}
ctrlHtml += "</ul></div>";
$(id).append(ctrlHtml);
var $ctrlItem = $(id+" .ctrl li");
//set current
setCurrent(0);


//event===================================================================================
$ctrlItem.bind("click",function(){
	if($(this).index() != currentId && lock==0){
		lock=1;
		changeImgType($(this).index());
		clearInterval(autoPlay);
		autoPlay = setInterval(changeImg,time);
		setTimeout(function(){lock=0},delayTime);
	}
	$(id+" .btnLR").removeClass("stop");
	if(btnLR==2&&currentId==0){$(id+" .left").addClass("stop")}else if(btnLR==2&&currentId==$itemNumber-1){$(id+" .right").addClass("stop")}
});

$(id+" a.btnLR").bind("click",function(){
	if(lock==0){
		lock=1;
		if($(this).hasClass("right") && !$(this).hasClass("stop")){
			$(id+" .left").removeClass("stop");
			if(currentId < $itemNumber - 1){
				currentId = currentId + 1;
			}else{
				currentId = 0 ;
			}
		}else if($(this).hasClass("left") && !$(this).hasClass("stop")){
			$(id+" .right").removeClass("stop");
			if(currentId > 0){
				currentId = currentId - 1;
			}else{
				currentId = $itemNumber - 1;
			}
		}
		if(!$(this).hasClass("stop")){
			changeImgType(currentId);
			clearInterval(autoPlay);
			autoPlay = setInterval(changeImg,time);
			setTimeout(function(){lock=0},delayTime);
		}
		if((btnLR==2&&currentId==$itemNumber-1)||(btnLR==2&&currentId==0)){$(this).addClass("stop")}
	}
});
//function===================================================================================
function setCurrent(id){
	$ctrlItem.removeClass("current");
	$ctrlItem.eq(currentId).addClass("current");
}
function changeImgType(id){
	currentId = id;
	setCurrent(currentId);
	if(showType==1){
		$item.hide().find(".info").css({bottom:20,opacity:0});
		$item.eq(currentId).fadeIn(300).find(".info").delay(200).animate({bottom:0,opacity:txtType},300);
		delayTime = 500;
	}else if(showType==2){
		$item.find(".info").css({bottom:20,opacity:0});
		$inner.animate({marginLeft:-$itemWidth*currentId},1000,"easeOutQuad");
		$item.eq(currentId).find(".info").delay(500).animate({bottom:0,opacity:txtType},300);
		delayTime = 1000;
	}else if(showType==3){
		$item.css({"z-index":1}).fadeOut(500).find(".info").css({opacity:0});
		$item.eq(currentId).css({"z-index":3}).fadeIn(500);
		$item.eq(currentId).find(".info").delay(300).animate({opacity:txtType},300);
		delayTime = 500;
	}else if(showType==4){
		$item.find(".info").css({bottom:20,opacity:0});
		$inner.animate({marginTop:-$itemHeight*currentId},1000,"easeOutQuad");
		$item.eq(currentId).find(".info").delay(500).animate({bottom:0,opacity:txtType},300);
		delayTime = 1000;
	}else if(showType==5){
		
	}
}
function changeImg(){
	if(currentId < $itemNumber - 1){
		currentId += 1;
	}else{
		currentId = 0;
	}
	changeImgType(currentId);
}






}; 
})(jQuery); 
