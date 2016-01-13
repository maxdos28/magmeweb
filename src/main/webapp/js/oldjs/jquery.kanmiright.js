;(function($){
jQuery.kanmiright = function(id) {
var browserV	= $.browser.version;
var $item		= $(id+" .smallShow .item");
var $ctrl		= $(id+" .ctrl");
var $ctrlLeft	= $(id+" .turnLeft");
var $ctrlRight	= $(id+" .turnRight");
var $book		= $(id+" .smallShow .item a");
var itemNumber	= $item.length;
var currentId = 0;
var targetId = 0;
var lock = 0;
var delayTime;
//set desk html
$item.append("<div class='desk'><span></span></div>");
//ready
$item.eq(0).addClass("current").show();
fnSetCurrent(0);


//event===================================================================================


$ctrl.click(function(){
	if(lock==0){
		lock=1;
		//配合kanmiFolder==================
		$(".kanmiCon .smallShow").css({overflow:"hidden"});//设置切换时smallShow超出隐藏
		var kcLength = $(".kanmiCon").length;//取到kanmiCon个数
		$(".kanmiCon").last().attr("class","kanmiCon kc"+kcLength+" kanmiConLast");//切换后重置最后一行样式
		$.kanmifolder.fnfolderHide();//隐藏kanmiFolder
		//=================================
		if(browserV!="6.0" || $.browser.mozilla){
			fnPlayOut(currentId);
		}
		
		if($(this).hasClass("turnLeft")){
			if(browserV!="6.0" || $.browser.mozilla){
				setTimeout(function(){currentId--},1000);
			}else{
				currentId--;
			}
			targetId = currentId - 1;
		}else{
			if(browserV!="6.0" || $.browser.mozilla){
				setTimeout(function(){currentId++},1000);
			}else{
				currentId++;
			}
			targetId = currentId + 1;
		}
		
		if(browserV=="6.0" && $.browser.msie){
			$item.removeClass("current").hide();
			$item.eq(currentId).addClass("current").fadeIn(300);
		}else{
			setTimeout(function(){fnPlayIn(targetId)},350);
		}
		
		setTimeout(function(){
			lock=0;
			fnSetCurrent(currentId);
		},1000);
	}
});



//function===================================================================================
function fnSetCurrent(id){
	$ctrlLeft.removeClass("stopL");
	$ctrlRight.removeClass("stopR");
	currentId=id;
	if(currentId==0){
		$ctrlLeft.addClass("stopL");
	}else if(currentId==itemNumber-1){
		$ctrlRight.addClass("stopR");
	}
	if(itemNumber==1){
		$ctrlRight.addClass("stopR");
	}
}
function fnPlayOut(id){
	currentId = id;
	var opacityVal = 0;
	if($.browser.version!="8.0"){
		for(i=0;i<6;i++){
			$item.eq(currentId).removeClass("current").find("a").eq(i).delay(i*i*10+100).animate({marginTop:-350,opacity:0},300);
		}
	}else{
		opacityVal=1;
		for(i=0;i<6;i++){
			$item.eq(currentId).removeClass("current").find("a").eq(i).delay(i*i*10+100).animate({marginTop:-350},300);
		}
	}
	setTimeout(function(){$item.eq(currentId).hide()},1000-10);
	delayTime=1000;
}
function fnPlayIn(id){
	targetId = id;
	//定义ie8下透明度和顶部距离，避免png阴影冲突
	var opacityVal = 0;
	var marginTopVal = 100;
	//定义动画效果
	if($.browser.version!="8.0"){
		for(i=0;i<6;i++){
			$item.eq(targetId).show().find("a").css({marginTop:marginTopVal,opacity:0});
			$item.eq(targetId).addClass("current").find("a").eq(i).delay(i*50+100).animate({marginTop:0,opacity:1},400);
		}
	}else{
		marginTopVal=360;
		for(i=0;i<6;i++){
			$item.eq(targetId).show().find("a").css({marginTop:marginTopVal});
			$item.eq(targetId).addClass("current").find("a").eq(i).delay(i*50+100).animate({marginTop:0},400);
		}
	}
	delayTime=1000;
}



}; 
})(jQuery); 
