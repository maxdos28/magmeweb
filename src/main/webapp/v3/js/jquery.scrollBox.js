;(function($){
//插件名，插件ID，每页item数量
jQuery.jqueryscrollbox = function(id,num) {
//判断操作系统	
var 	ua = window.navigator.userAgent,
		osVersion = ua.split("(")[1],
		osV = osVersion.split(";")[0];
var	imgSrcArr = [],
		$outer = $(id).find(".outer"),
		$inner,
		$ctrl,
		$turnLeft,
		$turnRight,
		$item = $(id).find(".item"),
		$img = $(id).find(".item>a>img"),
		$scrollBar,
		$hand,
		$currentId,
		scrollBarHtml,
		handWidth,
		innerNumberOffset = 0,
		innerWidth,
		innerNumber,
		outerHtml,
		lock = 0,
		currentId = 0,
		lockTime = 800,
		animateTime = 800,
		_moveDown=false,
		_moveStart=false,
		_x,	x,
		positionX,offsetX,
		itemId,
		xStop = 0,
		LRTimeout;
		
//初始化类==========================================================================================

if($item.length==0){
	$(id).hide()
	return false;
}

//初始化翻页按钮
$(id).append("<div class='ctrl png'><a href='javascript:void(0)' class='left'><</a><a href='javascript:void(0)' class='right'>></a></div>");
$ctrl = $(id).find(".ctrl"),
$turnLeft = $(id).find(".ctrl .left"),
$turnRight = $(id).find(".ctrl .right"),
//初始化inner
$item.each(function(){//判断第num个item后面添加标记符###
	if(($(this).index()+1)%num==0){
		$(this).after("###");
	}
});
outerHtml = $outer.html().split("###").join("</div><div class='inner'>");//根据标记符###重新分隔inner嵌套，用于翻页分隔
$outer.html( outerHtml );
$inner = $(id).find(".inner");
$inner.each(function(){//判断最后inner是否为空
if($(this).children().length==0){$(this).remove();innerNumberOffset += 1}//如果为空inner数量差值减1
});
innerWidth = $inner.width();//定义inner翻页宽度
innerNumber = $inner.length - innerNumberOffset;//定义inner翻页数量


//加载滚动条
scrollBarHtml = "<div class='scrollBar'><div class='hand'></div><div class='info'><span class='currentId'>1</span>&nbsp;/&nbsp;"+innerNumber+"</div></div>";
$(id).append(scrollBarHtml);
$scrollBar = $(id).find(".scrollBar");//定义滚动条
$hand = $(id).find(".scrollBar .hand");//定义滚动条hander
$currentId = $(id).find(".scrollBar .currentId");//定义当前页面


//初始化滚动条宽度，按钮样式
fnSetCurrent();
fnSetOffset();
$(window).resize(fnSetOffset);
//设置滚动总宽度
$outer.width(innerWidth * innerNumber); 
//设置hander宽度为整数，用于滚动条处hander
$hand.width(parseInt($scrollBar.width()/innerNumber));
//设置hander宽度为小数，用于计算滚动条分页宽度
handWidth = $scrollBar.width()/innerNumber;
//初始化第一屏图片路径



//事件类==========================================================================================
//鼠标移入显示详细信息
$item.live("mouseenter",function(){
	$(this).find("p").show();
}).live("mouseleave",function(){
	$(this).find("p").hide();
});
//显示翻页按钮事件
$(id).mouseenter(function(){
	$ctrl.find("a").not(".stop").fadeIn(300);
	clearTimeout(LRTimeout);
}).mouseleave(function(){
	LRTimeout = setTimeout(function(){$ctrl.find("a").fadeOut(200)},300);
});

//左右翻页事件
$ctrl.find("a").bind("click",function(){
	if(lock==0){
		lock=1;
		if($(this).hasClass("left")){
			if(currentId!=0){
				$outer.animate({marginLeft:-innerWidth*(currentId-1)},animateTime,"easeOutQuad");
				$hand.animate({left:handWidth*(currentId-1)},animateTime,"easeOutQuad");
				currentId-=1;
			};
		};
		if($(this).hasClass("right")){
			if(currentId!=innerNumber-1){
				$outer.animate({marginLeft:-innerWidth*(currentId+1)},animateTime,"easeOutQuad");
				if(currentId==innerNumber-2){//判断是否为末页(hander宽度不能被整除)
					$hand.animate({left:$scrollBar.width()-$hand.width()},animateTime,"easeOutQuad");
				}else{
					$hand.animate({left:handWidth*(currentId+1)},animateTime,"easeOutQuad");
				}
				currentId+=1;
			};
		};
		setTimeout(function(){lock=0},lockTime);
	};
	fnSetCurrent();
	x = currentId*handWidth;
	xStop = x;//更新判断移动距离的起始位置
	$currentId.html(currentId+1);//定义当前页码
	fnSetCrtl();//设置翻页按钮
});




//滚动条点击事件
$scrollBar.live('click',function(e){ 
	if(!_moveStart && lock==0){
		lock=1;
		x=e.originalEvent.x-$(this).offset().left-offsetX||e.originalEvent.layerX-$(this).offset().left-offsetX||0;
		if(x<0){x=0}else if(x>$scrollBar.width()-handWidth){x=$scrollBar.width()-handWidth+1}
		currentId =  parseInt(x / handWidth);
		$outer.animate({marginLeft:-innerWidth*currentId},animateTime,"easeOutQuad");
		if(currentId==innerNumber-1){//判断是否为末页(hander宽度不能被整除)
			$hand.animate({left:$scrollBar.width()-$hand.width()},animateTime,"easeOutQuad");
		}else{
			$hand.animate({left:handWidth*currentId},animateTime,"easeOutQuad");
		}
		fnSetCurrent();
		x = currentId*$hand.width();
		xStop = x;//更新判断移动距离的起始位置
		setTimeout(function(){lock=0},lockTime);
	}
	$currentId.html(currentId+1);//定义当前页码
});




//滚动条拖动事件
$hand.live('mousedown',function(e){
	_moveDown=true;
	_moveStart=false;
	_x=e.pageX-parseInt($hand.css("left"));
});
$(document).mousemove(function(e){
	
	if(_moveDown){
		//清除鼠标拖动时浏览器默认拖选操作
		document.selection&&document.selection.empty&&(document.selection.empty(),1)||window.getSelection&&window.getSelection().removeAllRanges();
		_moveStart=true;
		x=e.pageX-_x;//移动时根据鼠标位置计算控件左上角的绝对位置
		if(x<0){x=0}else if(x>$scrollBar.width()-handWidth){x=$scrollBar.width()-handWidth}
		$hand.css({left:x});
		$outer.css({marginLeft:-x / $scrollBar.width() * innerWidth * innerNumber});
		currentId = Math.round (x / handWidth);
		$currentId.html(currentId+1);//定义当前页码
	}
}).mouseup(function(){
	_moveDown=false;
	if(_moveStart){
		$outer.animate({marginLeft:-innerWidth*currentId},animateTime/3,"easeOutQuad");
		if(currentId==innerNumber-1){//判断是否为末页(hander宽度不能被整除)
			$hand.animate({left:$scrollBar.width()-$hand.width()},animateTime/3,"easeOutQuad");
		}else{
			$hand.animate({left:handWidth*currentId},animateTime/3,"easeOutQuad");
		}
		fnSetCurrent();
		setTimeout(function(){_moveStart=false;},100);//避免超近距离拖动后反弹回
	}
});	



//鼠标滑轮事件
$(id).mousewheel(function(event, delta) {
	if(lock==0){
		lock=1;
		if(delta > 0){
			if(currentId>0){
				$outer.animate({marginLeft:-innerWidth*(currentId-1)},animateTime,"easeOutQuad");
				$hand.animate({left:handWidth*(currentId-1)},animateTime,"easeOutQuad");
				currentId -= 1;
			};
		}else{
			if(currentId!=innerNumber-1){
				$outer.animate({marginLeft:-innerWidth*(currentId+1)},animateTime,"easeOutQuad");
				if(currentId==innerNumber-2){//判断是否为末页(hander宽度不能被整除)
					$hand.animate({left:$scrollBar.width()-$hand.width()},animateTime,"easeOutQuad");
				}else{
					$hand.animate({left:handWidth*(currentId+1)},animateTime,"easeOutQuad");
				}
				currentId += 1;
			}
		}
		fnSetCurrent();
		xStop = x;//更新判断移动距离的起始位置
		setTimeout(function(){lock=0},lockTime);
		$currentId.html(currentId+1);//定义当前页码
		fnSetCrtl();//设置翻页按钮

	return false; // prevent default
	}
});


//方法类==========================================================================================
function fnSetCurrent(){
	$ctrl.children().removeClass("stop");
	if(currentId==0){$turnLeft.addClass("stop");}
	if(currentId==innerNumber-1){$turnRight.addClass("stop");}
}
function fnSetOffset(){
	//设置浏览器下滚动条位置偏移值，居中对齐后再减去左半边宽度300，加上body滚动条宽度20
	if($.browser.msie){offsetX=-($(window).width()-innerWidth)/2 + 121 + 20}
	if($.browser.safari){offsetX=0;}
	if($.browser.mozilla){offsetX=-($(window).width()-innerWidth)/2 - 84 + 20}
}
function fnSetCrtl(){
	//设置翻页按钮显示状态
	$ctrl.find("a").not(".stop").fadeIn(300);
	$ctrl.find("a.stop").fadeOut(300);
}

};
})(jQuery);




