;(function($){
jQuery.magmeShow = function(id) {
var lock = 0,
	showCount = 0,
	currentId = 0,
	index = 0,
	length = 0,
	boxGap = 120,
	LRWidth = 60,
	LRHeight = 200,
	dockHeight = 54,
	dockLength = 21,
	dockLock = 0,
	dockStart = 0,
	scrollTop,
	winWidth,
	winHeight,
	winMinWidth = 800,
	winMinHeight = 600,
	showAreaW,
	showAreaH,
	$obj,
	$magmeOpen,
	$magmeOpenItem,
	$magmeDock,
	$magmeDockItem,
	tempData,
	imgNoun=[],
	arrSizeW=[],
	arrSizeH=[],
	arrInnerHtml=[],
	showNoun,
	timeOutDock,
	currentTheme = 0;

//初始化作品排版
fnReady();


//打开事件
$(id).find(".conPhoto>.conPlay a").live("click",function(){
	fnPlay($(this));
});

$(id).find(".conVideo>.conPlay>a.mask").live("click",function(){
	fnPlayVideo($(this));
});
$(id).find(".conVideo>.conPlay>a.closeVideo").live("click",function(){
	fnStopVideo($(this));
});

//关闭事件
$("#magmeClose").live("click",function(){
	fnClose();
});
//鼠标拖动处理
$(document).mousedown(function(){
	_moveDown=true;
}).mouseup(function(){
	_moveDown=false;
});	





//作品初始化排列
function fnReady(){
	//$(document).bind("keydown",fnKeyOut);
	$(".sideLeft .theme .conPhoto .conPlay a img").coverImg();
	
	
	$(".sideLeft .theme .conForward .conReplyOuter").each(function(){
		$(this).jScrollPane().data('jsp').scrollToY(999999);
	})
}

//禁止鼠标中间功能
function fnMouseMiddle(event){
	if(event.button==1 || event.button==4){
		return false;
	}
}
//禁止鼠标拖动功能
function fnMouseMove(){
   document.selection&&document.selection.empty&&(document.selection.empty(),1)||window.getSelection&&window.getSelection().removeAllRanges();
	if(_moveDown){
		return false;
	}
}

/*//播放视频 m1.js 重写
function fnPlayVideo($objThis){
	$obj = $objThis.parent();
	fnVideoLoad();
	$obj.find("a,img").hide().end().append("<a class='closeVideo'>关闭视频</a>"+tempData.video[0].html);
	$obj.next().hide();
	//避免firefox下点击embed外层的a标签出发事件，把视频放在空白a标签之后
	//$magmeOpenItem.html("").after(tempData.video[0].html);

}*/
function fnStopVideo($objThis){
	$obj = $objThis.parent();
	$obj.find("embed").remove();
	$obj.find("a,img").fadeIn(300);
	$obj.next().fadeIn(300);
	$objThis.remove();
}
//播放图片
function fnPlay($objThis){
	if(lock==0){
		lock=1;
		$obj = $objThis.parent();
		$obj.clone().appendTo("body").removeAttr("class").attr("id","magmeOpen");//生成显示容器
		$magmeOpen = $("#magmeOpen");
		$magmeOpenItem = $("#magmeOpen a").not(".more");//过虑更多按钮
		fnShowMask();
		$magmeDock = $("#magmeDock");
		length = $magmeOpenItem.length - 1;
		if(isIE6){
			$magmeDock.width($magmeOpenItem.length*48+4);
		}
		//定义从点击处打开
		if(!$objThis.hasClass("more")){
			currentId = $objThis.index();
		}else{
			currentId = 0;
		}
		$(window).bind("resize",fnSetSize);
		fnWorksLoad($objThis);
		$(document).bind("keydown",fnEscClick);
		fnLoadEnd($objThis);
	}
}

//加载内容开始
function fnWorksLoad($objThis){
	var i = 0;
	$objThis.parent().find("a").each(function(){
		var cls=$(this).attr("class");
		var text=$(this).attr("text");
		var pic=$(this).attr("pic");
		var path=$(this).attr("path");
		if(cls.indexOf('audio')>-1){
			arrInnerHtml[i]="<img src='"+pic+"' />" +path+"<div>"+text+"</div>";
			arrSizeW[i]=400;
			arrSizeH[i]=300;
		}else if(cls.indexOf('video')>-1){
			arrInnerHtml[i]="<embed src='"+path+"' type='application/x-shockwave-flash' allowscriptaccess='always' allowfullscreen='true' wmode='opaque'></embed><div>"+text+"</div><img src='"+pic+"' style='display:none;' />";
			arrSizeW[i]=400;
			arrSizeH[i]=300;
		}else{
			
			arrInnerHtml[i]="<img src='"+pic+"' /><div>"+text+"</div>";
			var w=$(this).find("img").attr("w"),h=$(this).find("img").attr("h");
			if(w>0)
				arrSizeW[i]=w;
			else
				arrSizeW[i]=$(this).find("img").width();
			if(h>0)
				rrSizeH[i]=h;
			else
				arrSizeH[i]=$(this).find("img").height();
		}
		
		
		i++;
	});
	
	i = 0;
	$magmeOpenItem.each(function() {
		$(this).html(arrInnerHtml[i]);
		i++;
    });
	/*//定义html数据
	tempData = { "image":[
		{"html":"<img src='http://l.bst.126.net/pub/design/1291688668142604990.jpg' /><div>text text text text </div>","width":"1680","height":"1120"},
		{"html":"<img src='http://l.bst.126.net/pub/design/1166995253459709020.jpg' /><embed width='257' height='33' pluginspage='http://www.adobe.com/shockwave/download/download.cgi?P1_Prod_Version=ShockwaveFlash' src='http://img.xiami.com/widget/2811518_3552084_/singlePlayer.swff' type='application/x-shockwave-flash' quality='high' wmode='transparent' menu='false'/><div>text text text text </div>","width":"1680","height":"1151"},
		{"html":"<embed src='http://www.tudou.com/v/hVQeLWuLLxU/&rpid=83358396&resourceId=83358396_05_05_99/v.swf' type='application/x-shockwave-flash' allowscriptaccess='always' allowfullscreen='true' wmode='opaque'></embed><div>text text text text  text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text</div>","width":"1024","height":"768"},
		{"html":"<img src='http://l.bst.126.net/pub/design/2497808943347668713.jpg' /><div>text text text text </div>","width":"1680","height":"1131"},
		{"html":"<img src='http://l.bst.126.net/pub/design/2684426852906972787.jpg' /><div>text text text text </div>","width":"1680","height":"1134"},
		{"html":"<img src='http://l.bst.126.net/pub/design/2881459336604034918.jpg' /><div>text text text text </div>","width":"1680","height":"1134"},
		{"html":"<img src='http://l.bst.126.net/pub/design/2517230716740726554.jpg' /><div>text text text text </div>","width":"1523","height":"1523"},
		{"html":"<img src='http://l.bst.126.net/pub/design/605734149898504246.jpg' /><div>text text text text </div>","width":"1680","height":"1134"},
		{"html":"<img src='http://l.bst.126.net/pub/design/2515260391903870896.jpg' /><div>text text text text </div>","width":"1523","height":"1523"},
		{"html":"<img src='http://l.bst.126.net/pub/design/2587317985941800962.jpg' /><div>text text text text </div>","width":"1680","height":"1120"}
	] }
	$magmeOpenItem.each(function(i) {
		$(this).html(tempData.image[i].html);
    });*/
}
//加载照片开始
function fnPhotoLoad(){
	tempData = { "disc":[
		{"html":"<div>text text text text </div>"},
		{"html":"<div>text text text text </div>"},
		{"html":"<div>text text text text </div>"},
		{"html":"<div>text text text text </div>"},
		{"html":"<div>text text text text </div>"},
		{"html":"<div>text text text text </div>"},
		{"html":"<div>text text text text </div>"},
		{"html":"<div>text text text text </div>"},
		{"html":"<div>text text text text </div>"},
		{"html":"<div>text text text text </div>"}
	] }
	$magmeOpenItem.each(function(i) {
		$(this).append(tempData.disc[i].html);
    });
}
//加载视频功能
function fnVideoLoad(){
	tempData = { "video":[
		{"html":"<embed src='http://www.tudou.com/v/IcOdBHM9H5k/&resourceId=0_05_05_99&bid=05/v.swf' type='application/x-shockwave-flash' flashvars='autoPlay=true&isAutoPlay=true&auto=1' allowscriptaccess='always' allowfullscreen='true' wmode='opaque'></embed>","width":"1024","height":"768"},
		{"html":"<embed src='http://player.youku.com/player.php/sid/XNDI5MzIwMDY4/v.swf' allowFullScreen='true' quality='high' flashvars='autoPlay=true&isAutoPlay=true&auto=1' align='middle' allowScriptAccess='always' type='application/x-shockwave-flash'></embed>","width":"1024","height":"768"},
		{"html":"<embed src='http://player.ku6.com/refer/h_qhS1-Uz_9eSiYjqsr-2Q../v.swf' allowscriptaccess='always' allowfullscreen='true' flashvars='autoPlay=true&isAutoPlay=true&auto=1' type='application/x-shockwave-flash' flashvars='from=ku6'></embed>","width":"1024","height":"768"}
	] }
}
//还原图片功能
function fnContentReset($obj){
	//重置图片数据
	$obj.find("a div").remove();
}
//还原视频功能
function fnVideoReset($obj){
	//重置视频数据
	$obj.html("<a href='javascript:void(0)'><img src='images/temp/video.jpg' /></a>");
}
//加载内容完成后
function fnLoadEnd(_this){
	_this.onImagesLoad({
		selectorCallback: function(){
			$("#magmeLoad").remove();//移除loading
			scrollTop = $(window).scrollTop();
			winWidth = $(window).width();
			winHeight = $(window).height();
			$magmeOpen.width(winWidth).height(winHeight);
			showAreaW = $magmeOpen.width()-boxGap*2;
			showAreaH = $magmeOpen.height()-boxGap*2;
			imgNoun[currentId] = arrSizeW[currentId]/arrSizeH[currentId];
			if(currentId!=0){imgNoun[currentId-1] = arrSizeW[currentId-1]/arrSizeH[currentId-1];}
			if(currentId<length){imgNoun[currentId+1] = arrSizeW[currentId+1]/arrSizeH[currentId+1];}
			showNoun = showAreaW/showAreaH;
			//加载Dock内容
			$magmeDock.html("<div class='inner'>"+$obj.html()+"</div>"+"<span class='pageNumber'></span>");
			$magmeDockItem = $magmeDock.find("a").not(".more");
			play();
			//绑定相关事件
			$(document).bind("mousewheel",fnMouseWheel);
			$(document).bind("mousemove",fnMouseMove);
			$(document).bind("mousedown",fnMouseMiddle);
			$(document).bind("keydown",fnKeyIn);
			$("#magmeDock").bind("mouseenter",fnCtrlEnter).bind("mouseleave",fnCtrlLeave);
			$("#magmeNext,#magmePev").bind("click",fnPageClick);
			$("#magmeDock a").bind("click",fnDockClick);
			lock=0;
		} 
	});
}
//Dock显示隐藏
function fnCtrlEnter(){
	$(this).stop(true,true).animate({marginTop:0},800,"easeOutQuint");
	clearTimeout(timeOutDock);
	dockLock=1;
}
function fnCtrlLeave(){
	timeOutDock = setTimeout(function(){$magmeDock.animate({marginTop:dockHeight},800,"easeOutQuint")},1500);
	dockLock=0;
}

//翻页点击
function fnPageClick(){
	if($(this).attr("id")=="magmeNext"){
		if( currentId < length ){
			clearPlay();
			currentId +=1;
		}
	}else{
		if( currentId > 0){
			clearPlay();
			currentId -=1;
		}
	}
	play();
}

//Dock点击
function fnDockClick(){
	//判断是否为当前项
	if($(this).index()!=currentId){
		clearPlay();
		$("#magmePev,#magmeNext").show();
		currentId = $(this).index();
		play();
	}
}


//画布内容设置
function fnSetElement(){
	//设置所有项目隐藏
	$magmeOpenItem.hide();
	if(showCount==0){
		showCount=1;
		fadeTime=500;
	}else{
		fadeTime=0;
	}
	//设置当前三个项目的宽高/定位
	//setLeft
	if(currentId!=0){
		$magmeOpenItem.eq(currentId-1).show().find("img,embed:first-child").css({width:LRHeight*imgNoun[currentId-1],height:LRHeight});
		$magmeOpenItem.eq(currentId-1).css({left:LRWidth-LRHeight*imgNoun[currentId-1],top:(winHeight-LRHeight)/2+scrollTop});
		$magmeOpenItem.eq(currentId-1).find("div").hide();
	}

	//setCenter
	if(imgNoun[currentId] > showNoun){
		$magmeOpenItem.eq(currentId).show().find("img,embed:first-child").css({width:showAreaW,height:showAreaW/imgNoun[currentId]});
		$magmeOpenItem.eq(currentId).css({left:boxGap,top:(winHeight-showAreaW/imgNoun[currentId])/2+scrollTop});
		$magmeOpenItem.eq(currentId).find("div").show().css({width:showAreaW});
	}else{
		$magmeOpenItem.eq(currentId).show().find("img,embed:first-child").css({width:showAreaH*imgNoun[currentId],height:showAreaH});
		$magmeOpenItem.eq(currentId).css({left:($magmeOpen.width()-showAreaH*imgNoun[currentId])/2,top:boxGap+scrollTop});
		$magmeOpenItem.eq(currentId).find("div").show().css({width:showAreaH*imgNoun[currentId]});
	}
	
	//setRight
	if(currentId!=length){
		$magmeOpenItem.eq(currentId+1).show().find("img,embed:first-child").css({width:LRHeight*imgNoun[currentId+1],height:LRHeight});
		$magmeOpenItem.eq(currentId+1).css({left:$magmeOpen.width()-LRWidth,top:(winHeight-LRHeight)/2+scrollTop});
		$magmeOpenItem.eq(currentId+1).find("div").hide();
	}
	
//原翻页按钮大小备份	
//	if(currentId!=length){
//		if(imgNoun[currentId+1] > showNoun){
//			$magmeOpenItem.eq(currentId+1).show().find("img,embed:first-child").css({width:showAreaW,height:showAreaW/imgNoun[currentId+1]});
//			$magmeOpenItem.eq(currentId+1).css({left:$magmeOpen.width()-LRWidth,top:(winHeight-showAreaW/imgNoun[currentId+1])/2+scrollTop});
//			$magmeOpenItem.eq(currentId+1).find("div").hide();
//		}else{
//			$magmeOpenItem.eq(currentId+1).show().find("img,embed:first-child").css({width:showAreaH*imgNoun[currentId+1],height:showAreaH})
//			$magmeOpenItem.eq(currentId+1).css({left:$magmeOpen.width()-LRWidth,top:boxGap+scrollTop})
//			$magmeOpenItem.eq(currentId+1).find("div").hide();
//		}
//	}
	
	
	
	//设置遮罩层/翻页按钮
	$("#magmeMask").css({width:winWidth,height:winHeight,top:scrollTop});
	$("#magmeNext").css({top:scrollTop+boxGap,left:$magmeOpen.width()-LRWidth,width:LRWidth,height:$magmeOpen.height()-boxGap*2});
	$("#magmePev").css({top:scrollTop+boxGap,left:0,width:LRWidth,height:$magmeOpen.height()-boxGap*2});
	//设置dock
	$magmeDock.css({top:scrollTop+$magmeOpen.height()-dockHeight-40,left:($magmeOpen.width()-$magmeDock.width())/2});

}


//鼠标滚轮功能
function fnMouseWheel(event, delta){
	if(lock==0){
		lock=1;
		if(delta>0 && currentId>0){
			clearPlay();
			currentId-=1;
		}else if(delta<0 && currentId<length){
			clearPlay();
			currentId+=1;
		}
		play();
		//定义滚轮间隔时间
		setTimeout(function(){lock=0},300);
	}
	return false;
}

//键盘ESC事件
function fnEscClick(e){
	if(e.keyCode==27){
		fnClose();
		return false;
	}
}

//键盘翻页事件
function fnKeyIn(e){
	if(e.keyCode==32){
		clearPlay();
	}else if(e.keyCode==39 || e.keyCode==40){
		if( currentId < length ){
			clearPlay();
			currentId +=1;
			play();
		}
	}else if(e.keyCode==37 || e.keyCode==38){
		if( currentId > 0){
			clearPlay();
			currentId -=1;
			play();
		}
	}
}

//键盘控制事件
function fnKeyOut(e){
	if(e.keyCode==38 || e.keyCode==40){
		$(id).each(function(){
			if($(this).offset().top > $(window).scrollTop() - $(this).height() + 75){
				currentTheme = $(this).index();
				if(e.keyCode==38){
					currentTheme-=2
				}
				return false
			}
		});
		if(currentTheme<1){
			$(window).scrollTop(0)
		}else{
			$(window).scrollTop($(id).eq(currentTheme).offset().top-75)
		}
		return false;
	}
	
	
//	if(e.keyCode==32){
//		$(id).eq(currentTheme).find("a").click();
//		return false;
//	}

}




//播放
function play(){
	fnSetPageBtn();
	fnSetDock();
	fnSetSize();
	
}

//设置翻页按钮状态
function fnSetPageBtn(){
	$("#magmePev,#magmeNext").show();
	if(currentId==0){
		$("#magmePev").hide();
	}else if(currentId==length){
		$("#magmeNext").hide();
	}
	if(length==0){
		$("#magmeNext").hide();
	}
}

//定义Dock位置
function fnSetDock(){
	//定义单图时Dock不显示
	if(length==0){
		$magmeDock.hide();
	}
	//定义Dock显示状态
	$magmeDock.stop(true,true).animate({marginTop:0},400,"easeOutQuint");
	clearTimeout(timeOutDock);
	if(dockLock==0){
		timeOutDock = setTimeout(function(){$magmeDock.animate({marginTop:dockHeight},800,"easeOutQuint")},3000);
	}
	//定义dockItem显示位置
	$magmeDockItem.hide();
	$magmeDockItem.removeClass("current").find("img").removeAttr("style");
	if(currentId-(dockLength-1)/2<0){
		dockStart = 0;
	}else if(currentId+(dockLength-1)/2>length){
		dockStart = length-dockLength+1;
	}else{
		dockStart = currentId-(dockLength-1)/2;
	}
	for(var i=0;i<dockLength;i++){
		$magmeDockItem.eq(dockStart+i).show();
	}
	//定义dock当前Item
	$magmeDockItem.eq(currentId).addClass("current");
	//定义页码
	$magmeDock.find("span.pageNumber").html((currentId+1)+"&nbsp;/&nbsp;"+(length+1));
}

//定义显示尺寸
function fnSetSize(){
	scrollTop = $(window).scrollTop();
	winWidth = $(window).width();
	winHeight = $(window).height();
	$magmeOpen.width(winWidth).height(winHeight);
	if(winWidth<800){
		$magmeOpen.width(800);
	}
	if(winHeight<600){
		$magmeOpen.height(600);
	}
	showAreaW = $magmeOpen.width()-boxGap*2;
	showAreaH = $magmeOpen.height()-boxGap*2;
	imgNoun[currentId] = arrSizeW[currentId]/arrSizeH[currentId];
	if(currentId!=0){imgNoun[currentId-1] = arrSizeW[currentId-1]/arrSizeH[currentId-1]}
	if(currentId<length){imgNoun[currentId+1] = arrSizeW[currentId+1]/arrSizeH[currentId+1];}
/*	imgNoun[currentId] = tempData.image[currentId].width/tempData.image[currentId].height;
	if(currentId!=0){imgNoun[currentId-1] = tempData.image[currentId-1].width/tempData.image[currentId-1].height}
	if(currentId<length){imgNoun[currentId+1] = tempData.image[currentId+1].width/tempData.image[currentId+1].height}*/
	showNoun = showAreaW/showAreaH;
	fnSetElement();
}

//翻页时清除音频视频播放
function clearPlay(){
	if($magmeOpenItem.eq(currentId).hasClass("video")||$magmeOpenItem.eq(currentId).hasClass("audio")){
		//重置音频视频embed
		$magmeOpenItem.eq(currentId).html(arrInnerHtml[currentId].html);
		//重置音频视频size
		if(imgNoun[currentId] > showNoun){
			$magmeOpenItem.eq(currentId).show().find("img,embed:first-child").css({width:showAreaW,height:showAreaW/imgNoun[currentId]});
			$magmeOpenItem.eq(currentId).css({left:boxGap,top:(winHeight-showAreaW/imgNoun[currentId])/2+scrollTop});
			$magmeOpenItem.eq(currentId).find("div").css({width:showAreaW});
		}else{
			$magmeOpenItem.eq(currentId).show().find("img,embed:first-child").css({width:showAreaH*imgNoun[currentId],height:showAreaH})
			$magmeOpenItem.eq(currentId).css({left:($magmeOpen.width()-showAreaH*imgNoun[currentId])/2,top:boxGap+scrollTop})
			$magmeOpenItem.eq(currentId).find("div").css({width:showAreaH*imgNoun[currentId]});
		}
	}
}

//显示遮罩层
function fnShowMask(){
	scrollTop = $(window).scrollTop();
	//设置滚动条隐藏，页面中加入操作元素
	$("html").css({overflow:"hidden"});
	$("body, .header20120905 .inner").css({paddingRight:17});
	$("body").append("<div id='magmeMask'></div><a href='javascript:void(0)' id='magmeClose'>Close</a><span id='magmeLoad'></span><div id='magmeNext'></div><div id='magmePev'></div><div id='magmeDock'></div>");
	//修正firefox回顶部
	$(window).scrollTop(scrollTop);
	//定位关闭按钮
	$("#magmeClose").css({top:scrollTop+33});
	//定位loading图标
	$("#magmeLoad").css({top:scrollTop+($(window).height()-50)/2,left:($(window).width()-50)/2});
	//定位mask层
	$("#magmeMask").css({
		zIndex:1001,
		opacity:1,
		position:"absolute",
		width:$(window).width(),
		height:$(window).height(),
		top:$(window).scrollTop(),
		left:0
	});
	$magmeOpen.css({zIndex:1002});
}

//关闭操作
function fnClose(){
	$("#magmeMask,#magmeClose,#magmeOpen,#magmeLoad,#magmeNext,#magmePev,#magmeDock").remove();
	//设置滚动条恢复
	$("html").css({overflow:"auto"});
	$("body, .header20120905 .inner").css({paddingRight:0});
	//修正firefox回顶部
	$(window).scrollTop(scrollTop);
	//解绑事件
	$(document).unbind('mousedown');
	$(document).unbind("mousewheel");
	$(document).unbind("mousemove");
	$(document).unbind("keydown",fnKeyIn);
	$("magmeDock").unbind("mouseenter").unbind("mouseleave");
	$("#magmeNext,#magmePev").unbind("click");
	$("#magmeDock>a").unbind("click");
	$(window).unbind("resize",fnSetSize);
	clearTimeout(timeOutDock);
	showCount=0;
	lock=0;
	

}
//用户头像鼠标移入功能
$(".sideLeft .topBar .head").mouseenter(function(){
	$(this).find(".more").stop(true,true).fadeIn(200);
}).mouseleave(function(){
	$(this).find(".more").fadeOut(100);
});


//用户图片杂志页鼠标移入头像时显示功能菜单---------
$(id).find(".userHead>.head>img").mouseenter(function(){
	var $obj = $(this).parents(".userHead");
	if($obj.find(".tool").length==0){
		fnLoadUserInfo($obj);
	}else{
		var tou = $obj.find("a").attr("u");;
		var tcu = $obj.find(".tool").find(".sns_mes").attr("u");
		if(tou!=tcu || $obj.find(".inner").length>1){
			 $obj.find(".tool").remove();
			 fnLoadUserInfo($obj);
		}
	}
	$obj.find(".tool").stop(true,true).fadeIn(200);
});
$(id).find(".userHead").mouseleave(function(){
	var $obj = $(this);
	$obj.find(".tool").fadeOut(100);
});
/*//加载用户信息功能
function fnLoadUserInfo($this){
	$this.append("<div class='tool'><div class='outer'></div></div>");
	html = "<div class='inner'><strong class='name'>温国超<span>麦米认证编辑</span></strong><div class='company'>上海居冠软件有限公司<span>设计师</span></div><div class='count'><p>关注：<span>999</span></p><p>粉丝：<span>999</span></p><p>内容：<span><a href='javascript:void(0)'>999</a></span></p></div><div class='info'>麦米成功上市，融资50亿美元，市值500亿美元，揭秘新生代财富神话背后的结构图谱与资本爆发力，听Ken向您讲述【企业如何成功上市】</div>  </div><div class='ft'><a class='message' href='javascript:void(0)'>发消息</a> <a class='cancel' href='javascript:void(0)'>取消关注</a></div>"
	setTimeout(function(){$this.find(".tool>.outer").append(html)},2000);
}
*/




//显示评论---------------------------------------------
/*$(id).find(".content .tools .iconMoreInfo").live("click",function(){
	fnLoadMore($(this));
});
//关闭评论
$(id).find(".moreInfo .close").live("click",function(){
	$(this).parent().removeClass("open").slideUp(300).parents(".theme").find(".moreInfo>.topArrow").fadeOut(200).end().find(".iconMoreInfo").removeClass("iconMoreInfoCurrent");
	$(window).scrollTo($(this).parents(".theme").data("top"),300);
})*/
//加载评论方法
/*function fnLoadMore($this){
	if($this.parents(".theme").find(".moreInfo").length==0){
		$this.parents(".content").after("<div class='moreInfo clearFix'><div class='topArrow'></div><div class='loading'></div><div class='close'><a href='javascript:void(0)'>收起</a></div></div>");
		//加载评论
		fnLoadSlide($this);
		//请程序员自己更改下面代码
		setTimeout(function(){
			$this.parents(".content").next().find(".topArrow").after("<div class='comment'><div class='reply'><input class='input' type='text' tips='发表您的评论' /><a class='btnGB' href='javascript:void(0)'>评论</a></div><div class='item clearFix'><span>19小时之前</span><img src='images/temp/home6.jpg' class='head' /><p><strong>用户名</strong>他评论了什么什么什么的的的的的的的的的的的的的的的的的的的的的</p><del>111</del></div><div class='item clearFix'><span>19小时之前</span><img src='images/temp/home6.jpg' class='head' /><p><strong>用户名</strong>他评论了什么什么什么的的的的的的的的的的的的的的的的的的的的的</p></div><div class='item clearFix'><span>19小时之前</span><img src='images/temp/home6.jpg' class='head' /><p><strong>用户名</strong>他评论了什么什么什么的的的的的的的的的的的的的的的的的的的的的</p></div><div class='item itemMore'><a href='javascript:void(0)'>更多评论</a></div></div><div class='sameTag'><h3>同类标签</h3><div class='clearFix'><a href='javascript:void(0)'><img src='images/temp/home4.jpg' /></a><a href='javascript:void(0)'><img src='images/temp/home5.jpg' /></a><a href='javascript:void(0)'><img src='images/temp/home6.jpg' /></a><a href='javascript:void(0)'><img src='images/temp/home7.jpg' /></a><a href='javascript:void(0)'><img src='images/temp/home8.jpg' /></a><a href='javascript:void(0)'><img src='images/temp/home11.jpg' /></a><a href='javascript:void(0)'><img src='images/temp/home12.jpg' /></a><a href='javascript:void(0)'><img src='images/temp/home13.jpg' /></a><a href='javascript:void(0)'><img src='images/temp/home15.jpg' /></a></div></div><div class='sameUser'><h3>来自<strong>用户名</strong>的其它内容</h3><div class='clearFix'><a href='javascript:void(0)'><img src='images/temp/home11.jpg' /></a><a href='javascript:void(0)'><img src='images/temp/home12.jpg' /></a><a href='javascript:void(0)'><img src='images/temp/home13.jpg' /></a><a href='javascript:void(0)'><img src='images/temp/home15.jpg' /></a></div></div><div class='sameMagazine'><h3>出自<strong>&nbsp;[&nbsp;男人装&nbsp;]&nbsp;</strong>的其它内容</h3><div class='clearFix'><a href='javascript:void(0)'><img src='images/temp/home11.jpg' /></a><a href='javascript:void(0)'><img src='images/temp/home12.jpg' /></a><a href='javascript:void(0)'><img src='images/temp/home13.jpg' /></a><a href='javascript:void(0)'><img src='images/temp/home15.jpg' /></a></div></div><div class='sameForward'><h3><strong>999</strong>个转发</h3><div class='clearFix'><a href='javascript:void(0)'><img src='images/temp/home11.jpg' /><p><strong>用户名</strong>转发了你的图片</p></a><a href='javascript:void(0)'><img src='images/temp/home12.jpg' /><p><strong>用户名</strong>转发了你的图片</p></a><a href='javascript:void(0)'><img src='images/temp/home13.jpg' /><p><strong>用户名</strong>转发了你的图片</p></a><a href='javascript:void(0)'><img src='images/temp/home15.jpg' /><p><strong>用户名</strong>转发了你的图片</p></a><a href='javascript:void(0)'><img src='images/temp/home4.jpg' /><p><strong>用户名</strong>转发了你的图片</p></a><a href='javascript:void(0)'><img src='images/temp/home5.jpg' /><p><strong>用户名</strong>转发了你的图片</p></a><a href='javascript:void(0)'><img src='images/temp/home6.jpg' /><p><strong>用户名</strong>转发了你的图片</p></a><a href='javascript:void(0)'><img src='images/temp/home7.jpg' /><p><strong>用户名</strong>转发了你的图片</p></a><a href='javascript:void(0)'><img src='images/temp/home8.jpg' /><p><strong>用户名</strong>转发了你的图片</p></a><a href='javascript:void(0)'><img src='images/temp/home13.jpg' /><p><strong>用户名</strong>转发了你的图片</p></a></div><span class='more'>更多转发999+</span></div><div class='sameLike'><h3><strong>999</strong>个喜欢</h3><div class='clearFix'><a href='javascript:void(0)'><img src='images/temp/home4.jpg' /></a><a href='javascript:void(0)'><img src='images/temp/home5.jpg' /></a><a href='javascript:void(0)'><img src='images/temp/home6.jpg' /></a><a href='javascript:void(0)'><img src='images/temp/home7.jpg' /></a><a href='javascript:void(0)'><img src='images/temp/home8.jpg' /></a><a href='javascript:void(0)'><img src='images/temp/home11.jpg' /></a><a href='javascript:void(0)'><img src='images/temp/home12.jpg' /></a><a href='javascript:void(0)'><img src='images/temp/home13.jpg' /></a><a href='javascript:void(0)'><img src='images/temp/home15.jpg' /></a></div><span class='more'>更多喜欢999+</span></div>");
			$this.parents(".theme").find(".moreInfo .loading").hide();
		},2000);
	}else{
		fnLoadSlide($this);
	}
}*/
//评论下拉效果
function fnLoadSlide($this){
	$this.parents(".theme").data("top", $this.parents(".theme").offset().top-75);
	if($this.parents(".theme").find(".moreInfo").hasClass("open")){
		$this.removeClass("iconMoreInfoCurrent").parents(".theme").find(".moreInfo").removeClass("open").slideUp(300).find(".topArrow").fadeOut(200);
		$(window).scrollTo($this.parents(".theme").data("top"),300);
	}else{
		$this.addClass("iconMoreInfoCurrent").parents(".theme").find(".moreInfo").addClass("open").slideDown(300).find(".topArrow").hide().delay(300).fadeIn(200);
	}

}

//判断如果是自己看自己内容，才执行以下方法
//自己的内容可删除或编辑
function fnMyContent(){
	$(id).live("mouseenter",function(){
		if($(this).find(".content .tools em.edit").length==0){
			$(this).find(".content .tools").prepend("<em class='edit'>编辑</em><em class='delete'>删除</em>");
		}
	}).live("mouseleave",function(){
		$(this).find(".content .tools .edit").remove();
		$(this).find(".content .tools .delete").remove();
	});
}




}})(jQuery); 




$(function(){
	
//处理用户自定义背景
$(window).bind("resize",fnResize);
fnResize();
function fnResize(){
	if($.browser.msie && $.browser.version!="9.0"){
		$("#bg img").coverImg();
	}
	fnSetUserBar();//同时需要处理用户工具条高度
}
//处理用户工具条浮动高度
$(window).bind("scroll",fnSetUserBar);
function fnSetUserBar(){
	if(!isIE6){
		var userBarT = $(document).height()-192-$("#Dashboard .topBar").height()-$(".footerBig").height();
		if($(window).scrollTop()>=userBarT){
			$("#Dashboard .topBar").css({position:"absolute",top:userBarT})
		}else{
			$("#Dashboard .topBar").removeAttr("style").css({position:"fixed",top:"auto"});
		}
	}
}


//右侧图片滚动================================================================
var $mpId = $(".sideRight .conMorePic");
var $mpItem = $mpId.find(".item");
var $mpImg = $mpItem.find("a");
var $ding;
var mpAutoPlay = setInterval(fnMpPlay,5000);//自动轮播时间
var mpCurrentId = 0;
var mpGargetId = 0;
var moveLock = 1;
var moveTimeout = setTimeout(function(){moveLock=0},30000);//长时间无鼠标动作停止轮播
if($.browser.safari){
	$mpId.append("<em></em><em></em>");
	$ding = $mpId.find(">em");
}else{
	$mpItem.append("<em class='png'></em>");
	$ding = $mpItem.find(">em");
}
$mpItem.eq(0).siblings(".item").css({left:238});

$(document).mousemove(function(){
	moveLock=1;
	clearTimeout(moveTimeout);
	moveTimeout = setTimeout(function(){moveLock=0},30000);//长时间无鼠标动作停止轮播
});


//$ding.eq(1).css({left:347});
function fnMpPlay(){
	if(moveLock==1){
		mpGargetId = mpCurrentId+1;
		if(mpGargetId > $mpItem.length-1){
			mpGargetId = 0;
		}
		$mpItem.eq(mpCurrentId).animate({left:-238},1000,"easeOutQuint");
		$mpItem.eq(mpGargetId).css({left:238}).animate({left:0},1000,"easeOutQuint",function(){mpCurrentId = mpGargetId});
		if($.browser.safari){
			$ding.eq(0).animate({left:-109},1000,"easeOutQuint").animate({left:109},1);
			$ding.eq(1).animate({left:109},1000,"easeOutQuint").animate({left:347},1);
		}
		if(!$.browser.msie){
			setTimeout(function(){
				$mpImg.css({"-webkit-transition":"all 0.6s ease-out",
							"-moz-transition":"all 0.6s ease-out",
							"transition":"all 0.6s ease-out",
							"-webkit-transform":"rotate(-15deg)","-webkit-transform-origin":"50% 5px",
							"-moz-transform":"rotate(-15deg)","-moz-transform-origin":"50% 5px",
							"-o-transform":"rotate(-15deg)","-o-transform-origin":"50% 5px"});
			},0)
			setTimeout(function(){
				$mpImg.css({"-webkit-transition":"all 0.4s ease-out",
							"-moz-transition":"all 0.4s ease-out",
							"transition":"all 0.4s ease-out",
							"-webkit-transform":"rotate(8deg)","-webkit-transform-origin":"50% 5px",
							"-moz-transform":"rotate(8deg)","-moz-transform–origin":"50% 5px",
							"-o-transform":"rotate(8deg)","-o-transform-origin":"50% 5px"});
			},400)
			setTimeout(function(){
				$mpImg.css({"-webkit-transition":"all 0.3s ease-out",
							"-moz-transition":"all 0.3s ease-out",
							"transition":"all 0.3s ease-out",
							"-webkit-transform":"rotate(-4deg)","-webkit-transform-origin":"50% 5px",
							"-moz-transform":"rotate(-4deg)","-moz-transform–origin":"50% 5px",
							"-o-transform":"rotate(-4deg)","-o-transform-origin":"50% 5px"});
			},800)
			setTimeout(function(){
				$mpImg.css({"-webkit-transition":"all 0.2s ease-out",
							"-moz-transition":"all 0.2s ease-out",
							"transition":"all 0.2s ease-out",
							"-webkit-transform":"rotate(2deg)","-webkit-transform-origin":"50% 5px",
							"-moz-transform":"rotate(2deg)","-moz-transform–origin":"50% 5px",
							"-o-transform":"rotate(2deg)","-o-transform-origin":"50% 5px"});
			},1100)
			setTimeout(function(){
				$mpImg.css({"-webkit-transition":"all 0.2s ease-out",
							"-moz-transition":"all 0.2s ease-out",
							"transition":"all 0.2s ease-out",
							"-webkit-transform":"rotate(-1deg)","-webkit-transform-origin":"50% 5px",
							"-moz-transform":"rotate(-1deg)","-moz-transform–origin":"50% 5px",
							"-o-transform":"rotate(-1deg)","-o-transform-origin":"50% 5px"});
			},1300)
			setTimeout(function(){
				$mpImg.css({"-webkit-transition":"all 0.2s ease-out",
							"-moz-transition":"all 0.2s ease-out",
							"transition":"all 0.2s ease-out",
							"-webkit-transform":"rotate(0deg)","-webkit-transform-origin":"50% 5px",
							"-moz-transform":"rotate(0deg)","-moz-transform–origin":"50% 5px",
							"-o-transform":"rotate(0deg)","-o-transform-origin":"50% 5px"});
			},1500)
		}
	
	}
}


//转发按钮移入
var forwardTools = "<div id='shareDiv' style='display:none'><p><a title='新浪微博' tp='tsina' href='javascript:void(0)' class='weibo png'></a><a href='javascript:void(0)' tp='tqq' title='腾讯微博' class='tencent png'></a><a title='开心网' tp='kaixin'  href='javascript:void(0)' class='kaixin png'></a><a title='人人网' tp='renren' href='javascript:void(0)' class='renren png'></a><a title='M1社区' href='javascript:void(0)' tp='m1' class='m1 png'></a></p></div>";
$("em.iconShare").mouseenter(function(){
	$(this).append(forwardTools).find("div").fadeIn(200);
}).mouseleave(function(){
	$(this).find("div").remove();
});

})












