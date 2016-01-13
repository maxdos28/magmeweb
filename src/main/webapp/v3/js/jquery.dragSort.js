;(function($){
$.dragSort = function(id,num,W,H) {
var isIE6 = $.browser.msie && $.browser.version < 7 && !window.XMLHttpRequest;
var isIE7 = $.browser.msie && $.browser.version < 7 && !window.XMLHttpRequest;
var $id = $(id),
	 $item,
	 $null,
	 $hand,
	 length = $id.find(".clearFix .item").length,
	 moveDown = false,
	 x,_x,__x,
	 y,_y,__y,
 	 lock = 0,
	 lockSTO,
	 current,
	 target,
	 targetA,
	 targetZ,
	 T = num,
	 L;
	 

	 
	 
//鼠标按下事件
$id.find(".item, #myNavInner .item span").live('mousedown',function(e){
	if(!$(this).hasClass("item")){
		$("#customizeBox .allNav .clearFix").append("<div class='item'>"+$(this).parents(".item").html()+"</div>");
		$.dragSort.delItem($(this).parents(".item").index());
		return false;
	}else if(lock==0){
		lock=1;
		moveDown = true;
		var $obj = $(this);
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
		$item = $id.find(".clearFix .item");
		length = $item.length;
		L = Math.ceil(length/num);
		$id.find(".clearFix").height(L*H);
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
		if(targetZ > length){return false;}//判断超出总范围
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
			$item = $id.find(".clearFix .item");
			length = $item.length;
			L = Math.ceil(length/num);
		}
	}
}).mouseup(function(){
	if(moveDown){
		moveDown = false;
		$item.css({position:"static"}).removeAttr("style");
		$hand.animate({left:$null.position().left,top:$null.position().top},100,"easeOutBack");
		setTimeout(function(){
			$hand.css({position:"static",opacity:1}).removeAttr("style").removeClass("hand");
			$null.replaceWith($hand);
			lock=0;
		},100);
	}
});	



//添加方法，可外部调用
$.dragSort.addItem = function(html){
	$id.find(".clearFix").append(html);
	$item = $id.find(".clearFix .item");
	length = $item.length;
	L = Math.ceil(length/num);
	$id.find(".clearFix").height(L*H);
}


//删除方法，可外部调用
$.dragSort.delItem = function(index){
	$item = $id.find(".clearFix .item");
	length = $item.length;
	L = Math.ceil(length/num);
	$id.find(".clearFix").height(L*H);
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
			offsetT+=num-1;
			offsetL-=1;
		}
		//移动到目标位置动作
		if(!isIE6){
			$(this).stop(true,true).animate({left:offsetT*W,top:offsetL*H},300,"easeOutBack");
		}else{
			$(this).css({left:offsetT*W,top:offsetL*H});
		}
	});
	$item.eq(index).remove();
	//定义item
	setTimeout(function(){
		$item = $id.find(".clearFix .item");
		length = $item.length;
		L = Math.ceil(length/num);
		$id.find(".clearFix").height(L*H);
		$item.css({position:"static"}).removeAttr("style");
	},300);
}

}})(jQuery);




//下拉菜单功能=============================================================================================================================
$(function(){
	//菜单展开
	$("#customizeNav").live("click",function(){
		var afterNavDataLoaded = function(jasonNavData){
			//加载数据
			$("#customizeBox .myNav .clearFix").html("");
			for(var i=0;i<jasonNavData.myNav.length;i++){
				$.dragSort.addItem("<div class='item'><a href='javascript:void(0)' naveId='" + jasonNavData.myNav[i].id + "'>"
							+ jasonNavData.myNav[i].name + "<span></span></a></div>");
			}
			var allNavHtml = "";
			for(var i=0;i<jasonNavData.allNav.length;i++){
				allNavHtml+="<div class='item'><a href='javascript:void(0)' naveId='" + jasonNavData.allNav[i].id + "'>"
							+ jasonNavData.allNav[i].name + "<span></span></a></div>";
			}
			$("#customizeBox .allNav .clearFix").html(allNavHtml);
			//展开动画
			$(".homeNav").addClass("homeNavOn").animate({height:420},500);
			$(".rssFeed, .rssTo, .classification").fadeOut(150,function(){
				$("#customizeBox").fadeIn(350);
			});
		}
		fnLoadNavData(afterNavDataLoaded);
	});
	//菜单收起
	$("#customizeBox .action a").click(fnNavHide);
	//添加子菜单
	$("#customizeBox .allNav .clearFix .item").live("click",function(){
		$(this).remove();
		$.dragSort.addItem("<div class='item'>"+$(this).html()+"</div>");
	});
	
	
	//收起菜单方法
	function fnNavHide(){
		if($(this).hasClass("btnGB")){
			var ids = "";
			$("#customizeBox .myNav .clearFix div a").each(function(){
				if(ids.length) ids += "_";
				ids += $(this).attr("naveId");
			});
			if(!ids) {
				alert("请至少保留一个菜单项");
				return;
			}
			var data = {sortIds : ids};
			var success = fnSaveNavData(data);
			if(!success)
				return;
		}
		$(".homeNav").animate({height:58},500,function(){$(".homeNav").removeClass("homeNavOn")});
		$("#customizeBox").fadeOut(350,function(){
			$(".rssFeed, .rssTo, .classification").fadeIn(150);
		});
	}
});

//保存菜单数据
function fnSaveNavData(data){
}
//加载菜单数据
function fnLoadNavData(callback){
	var jasonNavData = { 
			myNav:[	{"name":"时尚"},	{"name":"娱乐"},	{"name":"汽车"},	{"name":"旅游"},	{"name":"财经"},	{"name":"育儿"},	{"name":"IT"}],
			allNav:[{"name":"分类"},	{"name":"分类"},	{"name":"分类"},	{"name":"分类"},	{"name":"分类"},	{"name":"分类"},	{"name":"分类"},	{"name":"分类"},	{"name":"分类"},	{"name":"分类"},	{"name":"分类"}]
		};
	callback(jasonNavData);
}
