;(function($){
jQuery.kanmileft = function(id,time) {
if(time==0){time=3000000};
var $inner		= $(id+ " .doorList .inner");
var $item		= $(id+ " .doorList .inner .item");
var $btnLR		= $(id+ " .doorList .inner .btnLR");
var $book		= $(id+ " .doorList .inner .item a");
var $itemWidth 	= $item.outerWidth();
var $itemHeight	= $item.outerHeight();
var $itemNumber	= $item.length;
var currentId = 0;
var targetId = 0;
var lock = 0;
var ctrlHtml;
var $ctrlItem;
var $ctrlItemText = $(id+" .textList a");
var autoPlay = setInterval(fnAutoPlay,time);
var delayTime;
//set ctrl html
ctrlHtml = "<div class='control'>";
for (var i=1;i<$itemNumber+1;i++){ctrlHtml += "<a href='javascript:void(0)'></a>";}
ctrlHtml += "</div>";
$(id).append(ctrlHtml);
$ctrlItem = $(id+" .control a");
//ready
$item.eq(0).show();
$ctrlItemText.eq(0).addClass("current");
fnSetCurrent(0);



//event===================================================================================
$(id+" .control a").bind("click",function(){
	if($(this).index() != currentId && lock==0){
		lock=1;
		currentId = $(this).index();
		fnPlay(currentId);
		clearInterval(autoPlay);
		autoPlay = setInterval(fnAutoPlay,time);
		setTimeout(function(){lock=0},delayTime);
	}
});
$(id+" .textList a").bind("mouseover",function(){
	if($(this).index() != currentId && lock==0){
		lock=1;
		currentId = $(this).index();
		fnPlayText(currentId);
		clearInterval(autoPlay);
		autoPlay = setInterval(fnAutoPlay,time);
		setTimeout(function(){lock=0},100);
	}
});

//function===================================================================================
function fnSetCurrent(id){
	$ctrlItem.removeClass("current");
	$ctrlItem.eq(id).addClass("current");
}
function fnSetCurrentText(id){
	$ctrlItem.removeClass("current");
	$ctrlItem.eq(id).addClass("current");
	if($ctrlItemText!=null){
		$ctrlItemText.removeClass("current");
		$ctrlItemText.eq(id).addClass("current");
	}
}
function fnAutoPlay(){
	if( lock==0){
		if(currentId < $itemNumber - 1){
			currentId += 1;
		}else{
			currentId = 0;
		}
		fnPlay(currentId);
	}
}
function fnPlay(id){
	currentId = id;
	fnSetCurrentText(currentId);
	$item.fadeOut(1000);
	$item.eq(currentId).fadeIn(1000);
	delayTime=1000;
}
function fnPlayText(id){
	currentId = id;
	fnSetCurrentText(currentId);
	$item.fadeOut(1000);
	$item.eq(currentId).fadeIn(1000);
	delayTime=1000;
}





}; 
})(jQuery); 
