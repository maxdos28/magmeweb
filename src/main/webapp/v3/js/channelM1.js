//拖动插件
;(function($){


$.dragSort = function(id,num) {
var isIE6 = $.browser.msie && $.browser.version < 7 && !window.XMLHttpRequest;
var $id = $(id),
	 $item,
	 $null,
	 $hand,
	 length = $id.find(">.inner>.item").length,
	 moveDown = false,
	 x,_x,__x,
	 y,_y,__y,
	 W = 310,
	 H = 140,
 	 lock = 0,
	 lockSTO,
	 current,
	 target,
	 targetA,
	 targetZ,
	 T = num,
	 L;


//鼠标按下事件
$id.find(".item .mask").live('mousedown',function(e){
	if(lock==0){
		lock=1;
		moveDown = true;
		var $obj = $(this).parents(".item");
		current = $obj.index()+1;
		target = current;
		//定义偏移
		_x = $obj.position().left;
		_y = $obj.position().top;
		__x= e.pageX - $obj.offset().left + $id.offset().left;
		__y= e.pageY - $obj.offset().top + $id.offset().top;
		//定义拖动item
		$obj.clone().addClass("hand").appendTo($id);
		$hand = $id.find(".hand");
		$hand.css({opacity:0.75,zIndex:9,position:"absolute",left:_x,top:_y});
		//放置占位Item
		$obj.replaceWith("<div class='item itemNull'><a></a></div>");
		$null = $id.find(".itemNull");
		//定义item
		$item = $id.find(".inner .item");
		length = $item.length;
		L = Math.ceil(length/num);
		$id.find(">.inner").height(L*H);
		//设置绝对定位
		$item.each(function(){
			//当前行
			var thisL = Math.ceil(($(this).index()+1)/T-1);
			//当前列
			var thisT = $(this).index()-thisL*T;
			$(this).css({position:"absolute",left:thisT*W,top:thisL*H});
		});
	}
});




//拖动事件
$(document).mousemove(function(e){
	if(moveDown){
		moveStart = true;
		//清除鼠标拖动时浏览器默认拖选操作
		document.selection&&document.selection.empty&&(document.selection.empty(),1)||window.getSelection&&window.getSelection().removeAllRanges();
		x = e.pageX - __x;
		y = e.pageY - __y;
		$hand.css({left:x,top:y});
		var currentL = Math.ceil(current/T);
		var currentT = current-(currentL-1)*T;
		var offsetX = x-(currentT-1)*W;
		var offsetY = y-(currentL-1)*H;
		if(offsetX > W/2){
			if(offsetX> (T-currentT)*W){offsetX = (T-currentT)*W}//判断超出边界
			currentT = currentT + Math.floor((offsetX+W/2)/W);//向右移动
		}
		if(offsetX < -W/2){
			if(offsetX < -(currentT-1)*W){offsetX = -(currentT-1)*W}//判断超出边界
			currentT = currentT - Math.floor((Math.abs(offsetX)+W/2)/W);//向左移动
		}
		if( offsetY > H/2){
			if(offsetY > (L-currentL)*H){offsetY = (L-currentL)*H}//判断超出边界
			currentL = currentL + Math.floor((offsetY+H/2)/H);//向下移动
		}
		if(offsetY < -H/2){
			if(offsetY < -(currentL-1)*W){offsetY = -(currentL-1)*W}//判断超出边界
			currentL = currentL - Math.floor((Math.abs(offsetY)+H/2)/H);//向上移动
		}
		targetZ = currentT + (currentL-1)*T;//定义目标位置
		if(targetZ > length){targetZ=length;}//判断超出总范围
		//判断有移位后再处理
		if(target!=targetZ){
			//定义每次开始位置
			targetA = target;
			target = targetZ;
			//向后移动
			if(targetA < targetZ){
				//占位项Html位置调整
				if(target!=length){
					//放置在结束项前一位
					$item.eq(targetZ).before($null);
				}else{
					//放置在结束项前一个的后一位
					$item.eq(targetZ-1).after($null);
				}
				//移动每个位置改变的Item
				$item.slice(targetA-1,targetZ).each(function(){
					//定义当前左偏移
					var offsetL = Math.ceil(($(this).index()+1)/T-1);
					//定义当前上偏移
					var offsetT = $(this).index()-offsetL*T;
					//移动到目标位置动作
					if(!isIE6){
						$(this).stop(true,true).animate({left:offsetT*W,top:offsetL*H},300,"easeOutBack");
					}else{
						$(this).css({left:offsetT*W,top:offsetL*H});
					}
				});
			}
			//向前移动
			else if(targetA > targetZ){
				$item.eq(targetZ-1).before($null);
				$item.slice(targetZ-1,targetA).each(function(){
					var offsetL = Math.ceil(($(this).index()+1)/T-1);
					var offsetT = $(this).index()-offsetL*T;
					if(!isIE6){
						$(this).stop(true,true).animate({left:offsetT*W,top:offsetL*H},300,"easeOutBack");
					}else{
						$(this).css({left:offsetT*W,top:offsetL*H});
					}
				});
			}
			//定义item
			$item = $id.find(".inner .item");
			length = $item.length;
			L = Math.ceil(length/num);
		}
	}
}).mouseup(function(){
	if(moveDown){
		moveDown = false;
		$item.css({position:"static"}).removeAttr("style");
		$hand.animate({left:$null.position().left,top:$null.position().top},100,"easeOutCirc");
		setTimeout(function(){
			$hand.css({position:"static",opacity:1}).removeAttr("style").removeClass("hand");
			$null.replaceWith($hand);
			lock=0;
		},100);
	}
});	

//删除方法，可外部调用
$.dragSort.delItem = function(index){
	$item = $id.find(">.inner>.item");
	length = $item.length;
	L = Math.ceil(length/num);
	$id.find(">.inner").height(L*H);
	$item.each(function(){
		//当前行
		var thisL = Math.ceil(($(this).index()+1)/T-1);
		//当前列
		var thisT = $(this).index()-thisL*T;
		$(this).css({position:"absolute",left:thisT*W,top:thisL*H});
	});
	$item.slice(index+1,length).each(function(){
		var offsetL = Math.ceil(($(this).index()+1)/T-1);
		var offsetT = $(this).index()-offsetL*T;
		if(offsetT>0){
			offsetT-=1;
		}else{
			offsetT+=2;
			offsetL-=1;
		}
		//移动到目标位置动作
		if(!isIE6){
			$(this).stop(true,true).animate({left:offsetT*W,top:offsetL*H},300,"easeOutCirc");
		}else{
			$(this).css({left:offsetT*W,top:offsetL*H});
		}
	});
	//定义item
	setTimeout(function(){
		$item = $id.find(">.inner>.item");
		length = $item.length;
		L = Math.ceil(length/num);
		$id.find(">.inner").height(L*H);
		$item.css({position:"static"}).removeAttr("style");
		//重设右栏高度
		$(".body .conRight").css({height : $(".body .conShareOuter").height() - 8 + "px" });
	},300);
}

}})(jQuery);




$(function(){
	//调用拖动功能
	$.dragSort("#imagesList",2);
	
	var sharePage = {
		init : function() {
			$("#imgLayout>.inner>.album>.item").live("click", sharePage.onChooseLayout);
			$("#imagesList>.inner>.item>.inner>.close").live("click", sharePage.delImg);
			$(".conRight>.conClass>.inner>ul>li").live("click", sharePage.toggleChecked);
			$(".conRight>.conPublic>a").live("click", sharePage.toggleChecked);
			//定义侧栏高度
			sharePage.resize();
		},
		delImg : function() {
			$.dragSort.delItem($(this).parents(".item").index());
			$(this).parents(".item").remove();
			if($("#imagesList>.inner>.item").length == 0) {
				$(".conShareImages>.imagesCon").hide();
			}
			$.imageUploader.selectLayout();
		},
		onChooseLayout : function(){
			$(this).addClass("current").parent(".album").find(".item").not($(this)).removeClass("current");
		},
		resize : function() {
			$(".body .conRight").css({height : $(".body .conShareOuter").height() - 8 + "px" });
		},
		workType : function() {
			$(".conWorkTabs>.imgBtn").live("click", sharePage.workShowImages);
			$(".conWorkTabs>.musicBtn").live("click", sharePage.workShowMusic);
			$(".conWorkTabs>.videoBtn").live("click", sharePage.workShowVideo);
		},
		workShowImages : function() {
			$(".shareWorks>.pageInner>.conAddImages").show();
			$(".shareWorks>.pageInner>.conShareMusic").hide();
			$(".shareWorks>.pageInner>.conShareVideo").hide();
			$(".shareWorks").find("a").removeClass("current").eq(0).addClass("current");
		},
		workShowMusic : function() {
			$(".shareWorks>.pageInner>.conAddImages").hide();
			$(".shareWorks>.pageInner>.conShareMusic").show();
			$(".shareWorks>.pageInner>.conShareVideo").hide();
			$(".shareWorks").find("a").removeClass("current").eq(1).addClass("current");
			sharePage.workAppenditem("music");
		},
		workShowVideo : function() {
			$(".shareWorks>.pageInner>.conAddImages").hide();
			$(".shareWorks>.pageInner>.conShareMusic").hide();
			$(".shareWorks>.pageInner>.conShareVideo").show();
			$(".shareWorks").find("a").removeClass("current").eq(2).addClass("current");
			sharePage.workAppenditem("video");
		},
		workAppenditem : function(type) {
			var css;
			if (type == "music") {
				css = "itemMusic";
			} else {
				css = "itemVideo";
			}
			$(".conShareImages>.imagesCon").show();
			$("#imagesList>.inner").append("<div class='item itemComplete " + css + " uncover'><div class='inner'><a href='javascript:void(0);' class='close'></a><div class='img'><div class='mask'><div class='ico png'></div></div><img src='http://u1.tdimg.com/6/20/178/110431532873914467690241287072355457216.jpg' /></div><textarea></textarea></div></div>");
			$("#imagesList>.inner>.uncover>.img>img").coverImg().parents(".uncover").removeClass("uncover");
			sharePage.resize();
			$("#imagesList>.inner").css({height:"auto"});
		},
		toggleChecked : function() {
			if ($(this).hasClass("current")) {
				$(this).removeClass("current");
			} else {
				$(this).addClass("current");
			}
		}
	}
	sharePage.init();
	
	
	
	
	
	
	
});

