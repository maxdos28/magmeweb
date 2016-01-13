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
	dockLength = 15,
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
	$readyItem = $(id).find(".content .album a"),
	$readyCover = $(id).find(".content .album a.cover"),
	$magmeOpen,
	$magmeOpenItem,
	$magmeDock,
	$magmeDockItem,
	arrSizeW=[],
	arrSizeH=[],
	arrInnerHtml=[],
	imgNoun=[],
	showNoun,
	timeOutDock,
	currentTheme = 0;

//初始化作品排版
fnReady();


//打开事件
$(id).find(".content>.photo>a").live("click",function(){
	if(!isIE6 && $(this).parent().hasClass("album")){
		fnPlay($(this));
	}else{
		fnShow($(this));
	}
});
$(id).find(".content>.openTools>.close").live("click",function(){
	fnShowClose($(this));
});
$(id).find(".content>.openTools>.open").live("click",function(){
	fnPlay($(this).parent().next().find("a:eq(0)"));
});
$(id).find(".content>.video>a").live("click",function(){
	fnVideoShow($(this));
});

//关闭事件
$("#magmeClose").live("click",function(){
	fnClose();
});

//用户头像鼠标移入功能
$(".sideLeft .topBar .inner .head").mouseenter(function(){
	$(this).find(".more").stop(true,true).fadeIn(200);
}).mouseleave(function(){
	$(this).find(".more").fadeOut(100);
});

$(".sideLeft .topBar .inner .head .self").live("click",function(){
	
});
//鼠标拖动处理
$(document).mousedown(function(){
	_moveDown=true;
}).mouseup(function(){
	_moveDown=false;
});	





//作品初始化排列
function fnReady(){
	$(id).each(function(){
		if($(this).find(".album").length>0){
			$(this).append("<div class='worksCover'></div>");
		}
	})
	$(document).bind("keydown",fnKeyOut);
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
//图片展开功能
function fnShow($objThis){
	$obj = $objThis.parent();
	$magmeOpenItem = $obj.find("a");
	if( !$obj.hasClass("open") || $obj.prev(".openTools").length==0 ){
		$objThis.parent().find("div").show();
		//展开图片功能
		if(isIE6){
			$obj.before("<div class='openTools'><span class='close'>收起</span></div>");
		}else{
			$obj.before("<div class='openTools'><span class='close'>收起</span><span class='open'>大图</span></div>");
		}
		fnPhotoLoad();
		$obj.data("pname",$obj.attr("class"));
		$obj.attr("class","photo open");
		$obj.find("a>img").removeAttr("style").css({width:500,height:"auto"});
		$obj.prev(".openTools").show();
		$magmeOpenItem.show();
	}else{
		$objThis.parent().find("div").hide();
		//收起图片功能
		fnContentReset($obj);
		$obj.attr("class",$obj.data("pname"));
		$obj.find("a>img").coverImg();
		$obj.prev(".openTools").remove();
		//当前头部不在显示范围内以当前头部定位
		if($obj.offset().top < $(window).scrollTop()+160){
			$(window).scrollTop($obj.offset().top-160);
		}
		
		//隐藏超过10张图片的部分
		for(var i=0; i<$obj.find("a").length;i++){
			if(i>9){
				$($obj.find("a")[i]).hide();
			}
		}
	}
	
}
//收起图片功能
function fnShowClose($this){
	$obj = $this.parent().next();
	if( $this.parent().next().hasClass("photo")){
		fnContentReset($obj);
		$obj.attr("class",$obj.data("pname"));
		$obj.find("a>img").coverImg();
		$this.parent().next().find("div").hide();
	}else{
		fnVideoReset($obj);
		$obj.attr("class","video");
	}
	$obj.prev(".openTools").remove();
	
	//隐藏超过10张图片的部分
	for(var i=0; i<$obj.find("a").length;i++){
		if(i>9){
			$($obj.find("a")[i]).hide();
		}
	}
}
//视频展开功能
function fnVideoShow($objThis){
	var path=$($objThis).attr("path");
	var text=$($objThis).attr("text");
	$obj = $objThis.parent();
	$magmeOpenItem = $obj.find("a");
	fnVideoLoad(path,text);
	if( !$obj.hasClass("open") || $obj.prev(".openTools").length==0 ){
		$obj.before("<div class='openTools'><span class='close'>收起</span></div>");
		$obj.attr("class","video open");
		$obj.prev(".openTools").show();
	}	
}
//大图播放功能
function fnPlay($objThis){
	if(lock==0){
		lock=1;
		$obj = $objThis.parent();
		$obj.clone().appendTo("body").removeAttr("class").attr("id","magmeOpen");//生成显示容器
		$magmeOpen = $("#magmeOpen");
		$magmeOpenItem = $("#magmeOpen a");
		fnShowMask();
		$magmeDock = $("#magmeDock");
		length = $magmeOpenItem.length - 1;
		//定义从点击处打开
		currentId = $objThis.index();
		//定义从封面打开
		//currentId = $obj.find(".cover").eq(0).index();
		$(window).bind("resize",fnSetSize);
		fnWorksLoad($objThis);
		$(document).bind("keydown",fnEscClick);
		fnLoadEnd();
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
			arrSizeW[i]=$(this).find("img").width();
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
	arrSizeW = [500,500,500,500,500,500,500,500,500,500];
	arrSizeH = [357,357,357,357,357,357,357,357,357,357];
	
	arrInnerHtml = ["<img src='http://l.bst.126.net/pub/design/1291688668142604990.jpg' /><div>text text text text </div>",
					"<img src='http://l.bst.126.net/pub/design/1166995253459709020.jpg' />" +
					"<embed src='http://www.xiami.com/widget/0_1770482329/singlePlayer.swf' type='application/x-shockwave-flash' wmode='transparent'></embed><div>text text text text </div>",
					"<embed src='http://www.tudou.com/v/hVQeLWuLLxU/&rpid=83358396&resourceId=83358396_05_05_99/v.swf' type='application/x-shockwave-flash' allowscriptaccess='always' allowfullscreen='true' wmode='opaque'></embed><div>text text text text  text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text text</div>",
					"<img src='http://l.bst.126.net/pub/design/2497808943347668713.jpg' /><div>text text text text </div>",
					"<img src='http://l.bst.126.net/pub/design/2684426852906972787.jpg' /><div>text text text text </div>",
					"<img src='http://l.bst.126.net/pub/design/2881459336604034918.jpg' /><div>text text text text </div>",
					"<img src='http://l.bst.126.net/pub/design/2517230716740726554.jpg' /><div>text text text text </div>",
					"<img src='http://l.bst.126.net/pub/design/605734149898504246.jpg' /><div>text text text text </div>",
					"<img src='http://l.bst.126.net/pub/design/2515260391903870896.jpg' /><div>text text text text </div>",
					"<img src='http://l.bst.126.net/pub/design/2515260391903870896.jpg' /><div>text text text text </div>",
					"<img src='http://l.bst.126.net/pub/design/2515260391903870896.jpg' /><div>text text text text </div>",
					"<img src='http://l.bst.126.net/pub/design/2515260391903870896.jpg' /><div>text text text text </div>",
					"<img src='http://l.bst.126.net/pub/design/2515260391903870896.jpg' /><div>text text text text </div>",
					"<img src='http://l.bst.126.net/pub/design/2515260391903870896.jpg' /><div>text text text text </div>",
					"<img src='http://l.bst.126.net/pub/design/2587317985941800962.jpg' /><div>text text text text </div>"];
	var i = 0;
	$magmeOpenItem.each(function() {
		$(this).html(arrInnerHtml[i]);
		i++;
    });*/
}
//加载照片开始
function fnPhotoLoad(){
//	arrInnerHtml = ["<div>text text text text </div>",
//					"<div>text text text text </div>",
//					"<div>text text text text </div>",
//					"<div>text text text text </div>",
//					"<div>text text text text </div>",
//					"<div>text text text text </div>",
//					"<div>text text text text </div>",
//					"<div>text text text text </div>",
//					"<div>text text text text </div>",
//					"<div>text text text text </div>"];
//	var i = 0;
//	$magmeOpenItem.each(function() {
//		$(this).append(arrInnerHtml[i]);
//		i++;
//    });
}
//加载视频功能
function fnVideoLoad(path,text){
	arrInnerHtml = ["<embed src='"+path+"' type='application/x-shockwave-flash' allowscriptaccess='always' allowfullscreen='true' wmode='opaque'></embed><div>"+text+"</div>"];
	//避免firefox下点击embed外层的a标签出发事件，把视频放在空白a标签之后
	$magmeOpenItem.html("").after(arrInnerHtml[0]);
}
//还原图片功能
function fnContentReset($obj){
	//重置图片数据
//	arrInnerHtml = ["<img src='images/temp/demo (1).jpg' />",
//					"<img src='images/temp/demo (2).jpg' />",
//					"<img src='images/temp/demo (3).jpg' />",
//					"<img src='images/temp/demo (4).jpg' />",
//					"<img src='images/temp/demo (5).jpg' />",
//					"<img src='images/temp/demo (6).jpg' />",
//					"<img src='images/temp/demo (7).jpg' />",
//					"<img src='images/temp/demo (8).jpg' />",
//					"<img src='images/temp/demo (9).jpg' />",
//					"<img src='images/temp/demo (10).jpg' />"];
//	var i = 0;
//	$obj.find("a").each(function() {
//		$(this).html(arrInnerHtml[i]);
//		i++;
//    });
}
//还原视频功能
function fnVideoReset($obj){
	var val = $obj.find("a").html('<img src="'+$obj.find("a").attr("pic")+'">');
	//重置视频数据
	$obj.html(val);
}
//加载内容完成后
function fnLoadEnd(){
	$magmeOpen.onImagesLoad({
		selectorCallback: function(){
			$("#magmeLoad").remove();//移除loading
			scrollTop = $(window).scrollTop();
			winWidth = $(window).width();
			winHeight = $(window).height();
			$magmeOpen.width(winWidth).height(winHeight);
			showAreaW = $magmeOpen.width()-boxGap*2;
			showAreaH = $magmeOpen.height()-boxGap*2;
			imgNoun[currentId] = arrSizeW[currentId]/arrSizeH[currentId];
			imgNoun[currentId-1] = arrSizeW[currentId-1]/arrSizeH[currentId-1];
			imgNoun[currentId+1] = arrSizeW[currentId+1]/arrSizeH[currentId+1];
			showNoun = showAreaW/showAreaH;
			//加载Dock内容
			$magmeDock.html("<div class='inner'>"+$obj.html()+"</div>"+"<span class='pageNumber'></span>");
			$magmeDockItem = $magmeDock.find("a");
			play();
			//绑定相关事件
			$(document).bind("mousewheel",fnMouseWheel);
			$(document).bind("mousemove",fnMouseMove);
			$(document).bind("mousedown",fnMouseMiddle);
			$(document).unbind("keydown",fnKeyOut).bind("keydown",fnKeyIn);
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
	imgNoun[currentId-1] = arrSizeW[currentId-1]/arrSizeH[currentId-1];
	imgNoun[currentId+1] = arrSizeW[currentId+1]/arrSizeH[currentId+1];
	showNoun = showAreaW/showAreaH;
	fnSetElement();
}

//翻页时清除音频视频播放
function clearPlay(){
	if($magmeOpenItem.eq(currentId).hasClass("video")||$magmeOpenItem.eq(currentId).hasClass("audio")){
		//重置音频视频embed
		$magmeOpenItem.eq(currentId).html(arrInnerHtml[currentId]);
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
	$("body, .header .inner").css({paddingRight:17});
	$("body").append("<div id='magmeMask' class='png'></div><a href='javascript:void(0)' id='magmeClose'>Close</a><span id='magmeLoad'></span><div id='magmeNext'></div><div id='magmePev'></div><div id='magmeDock'></div>");
	//修正firefox回顶部
	$(window).scrollTop(scrollTop);
	//定位关闭按钮
	$("#magmeClose").css({top:scrollTop});
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
	//设置页面右边距恢复
	$("body, .header .inner").css({paddingRight:0});
	//修正firefox回顶部
	$(window).scrollTop(scrollTop);
	//解绑事件
	$(document).unbind('mousedown');
	$(document).unbind("mousewheel");
	$(document).unbind("mousemove");
	$(document).unbind("keydown",fnKeyIn).bind("keydown",fnKeyOut);
	$("magmeDock").unbind("mouseenter").unbind("mouseleave");
	$("#magmeNext,#magmePev").unbind("click");
	$("#magmeDock>a").unbind("click");
	$(window).unbind("resize",fnSetSize);
	clearTimeout(timeOutDock);
	showCount=0;
	lock=0;
	
	//关闭展开工具条
	var offsetLength = $("#Dashboard>.topBar").length>0?1:0;
	//收起图片功能
	if( !$obj.hasClass("album")){
		$obj.find("div").remove();
		//隐藏超过10张图片的部分
		for(var i=0; i<$obj.find("a").length;i++){
			if(i>9){
				$($obj.find("a")[i]).hide();
			}
		}
	}
	$(id).eq($obj.parents(".theme").index()-offsetLength).find(".photo").attr("class",$obj.data("pname"));
	$(id).eq($obj.parents(".theme").index()-offsetLength).find(".photo>a>img").coverImg();
	$(id).eq($obj.parents(".theme").index()-offsetLength).find(".openTools").remove();

	
}


//加为好友
$(".atten,.cancel,.iconAdd,.iconCancel").live("click",function(){
	if($("#userBar").attr("style")==undefined){
		gotoLogin("请登录后，才能添加关注哦！",1);
		return;
	}
	var _$this =this;
	var userId = $(this).attr("u");
	var nick=$(this).attr("nick");
	var tx=$(this).html();
	//var invite=$("#u_invite_code").val();
	//if(invite!=1){
		if(tx=='加关注'){
			ajaxAddFollow(userId,1,function(rs){
				if(!rs)return;
				var code = rs.code;
				if(code == 200){
					$(_$this).html('取消关注');
					if($(_$this).hasClass("iconAdd"))
						$(_$this).attr("class","iconCancel");
					else
						$(_$this).attr("class","cancel");
				}else if(code == 400){
					gotoLogin("用户没有登录，请登录！");
				}else{
					alert(rs.message);
				}
			});
		}else{
			
			if(confirm("确定不再关注 "+nick+" 了吗？"))
		    {
				ajaxCancelFollow(userId,1,function(rs){
					if(!rs)return;
					var code = rs.code;
					if(code == 200){
						if($(_$this).hasClass("iconCancel"))
							$(_$this).attr("class","iconAdd");
						else
							$(_$this).attr("class","atten");
						$(_$this).html('加关注');
					}else if(code == 400){
						gotoLogin("用户没有登录，请登录！");
					}else{
						alert(rs.message);
					}
				});
		    }
		}
	/*}else{
		if(confirm("抱歉！M1频道需要验证邀请码才能关注好友。是否验证！"))
	    {
	        window.location.href= SystemProp.appServerUrl+"/sns/sns-index!invite.action";
	    }
	}*/
	
});

//加为好友
$(".sns_btnGS,.sns_btnWS").live("click",function(){
	if($("#userBar").attr("style")==undefined){
		gotoLogin("请登录后，才能添加关注哦！",1);
		return;
	}
	var _$this =this;
	var userId = $(this).attr("u");
	var nick=$(this).attr("nick");
	var tx=$(this).html();
	//var invite=$("#u_invite_code").val();
	//if(invite!=1){
		if(tx=='添加关注'){
			ajaxAddFollow(userId,1,function(rs){
				if(!rs)return;
				var code = rs.code;
				if(code == 200){
					$(_$this).html('取消关注');
					if($(_$this).hasClass("sns_btnGS")){
						$(_$this).attr("class","btnWS sns_btnWS sns_btnWS");
					}
				}else if(code == 400){
					gotoLogin("用户没有登录，请登录！");
				}else{
					alert(rs.message);
				}
			});
		}else{
			if(confirm("确定不再关注 "+nick+" 了吗？"))
		    {
				ajaxCancelFollow(userId,1,function(rs){
					if(!rs)return;
					var code = rs.code;
					if(code == 200){
						if($(_$this).hasClass("sns_btnWS")){
							$(_$this).attr("class","btnGS sns_btnGS");
						}
							
						$(_$this).html('添加关注');
					}else if(code == 400){
						gotoLogin("用户没有登录，请登录！");
					}else{
						alert(rs.message);
					}
				});
		    }
		}
	/*}else{
		if(confirm("抱歉！M1频道需要验证邀请码才能关注好友。是否验证！"))
	    {
	        window.location.href= SystemProp.appServerUrl+"/sns/sns-index!invite.action";
	    }
	}*/
	
});

//sendMsg-------------------------------------
$(".sns_mes").live("click",function(e){
	e.preventDefault();
	if($("#userBar").attr("style")==undefined){
		gotoLogin("请登录后，才能发送消息！",1);
		return;
	}
	$("#sns_mes_userName").html($(this).attr("name"));
	$("#send").attr("userId",$(this).attr("u"));
	$("#userNewMsg").fancybox();

});
$(".iconMessage").live("click",function(e){
	e.preventDefault();
	if($("#userBar").attr("style")==undefined){
		gotoLogin("请登录后，才能发送消息！",1);
		return;
	}
	$("#userNewMsg").fancybox();

});

$("#closePop").click(function(e){e.preventDefault();$.fancybox.close();});
$("#send").click(function(e){
	e.preventDefault();
	var userId = $(this).attr("userId");
	var content = $("#msgContent").val();
	if(!content || content === '请输入消息内容'){
		alert('请输入消息内容');
		return;
	}
	//1-->send to user
	ajaxSendMsg(userId,1,content,function(rs){
		if(!rs) return;
		var code = rs.code;
		if(code == 200){
			$.fancybox.close();
			alert("发送成功！");
		}else if(code == 400){
			gotoLogin("用户没有登录，请登录！");
		}else{
			alert(rs.message);
		}
	});
});

//用户图片杂志页鼠标移入头像时显示功能菜单---------
$(id).find(".userHead").mouseenter(function(){
	if($(this).find(".tool").length==0){
		fnLoadUserInfo($(this));
	}
	$(this).find(".tool").stop(true,true).fadeIn(200);
}).mouseleave(function(){
	$(this).find(".tool").fadeOut(100);
});
$(id).find(".userHead").find("img").live("click",function(){
	var url=$(this).attr("url");
	if(url!=undefined && url!='')
		window.location.href=url;
});
$(id).find(".userHead").find("em").live("click",function(){
	window.open(SystemProp.appServerUrl+"/sns/authorize.action","_blank");
});

$(".conHead").find("em").live("click",function(){
	window.open(SystemProp.appServerUrl+"/sns/authorize.action","_blank");
});
$(".topBarVip>.inner>.head").find("em").live("click",function(){
	window.open(SystemProp.appServerUrl+"/sns/authorize.action","_blank");
});

$(".conHead").find("img").live("click",function(){
	var url=$(this).attr("url");
	if(url!=undefined && url!='')
		window.location.href=url;
});




$(id).find(".content .tools a.iconShareBig").live("click",function(){
	var $p=$(this).parents(".theme").find(".content");
	var til=$p.find(".til").html();
	var _url=$p.find(".til").attr("url");
	var imgsrc=null;
	var i=0;
	$p.find("img").each(function(){
		if(i==0){
			imgsrc=$(this).attr("src");	
		}
		i++;
	});
	readerShare('tsina',_url,til,imgsrc);
	if($("#userBar").attr("style")!=undefined){
		var ct = $(this).attr("ct");
		if(ct!=9){
			var cid=$(this).parent().attr("cre");
			$.ajax({
				url : SystemProp.appServerUrl+"/sns/creative-comment!forward.action",
				type : "post",
				data : {"creative":cid},
				dataType : 'json',
				success : function (rs){
				}
			});
		}
	}
});


//显示评论---------------------------------------------
$(id).find(".content .tools a.iconMoreInfoBig").live("click",function(){
	fnLoadMore($(this));
});
//关闭评论
$(id).find(".moreInfo .close").live("click",function(){
	$(this).parent().removeClass("open").slideUp(300).parents(".theme").find(".moreInfo>.topArrow").fadeOut(200).end().find(".iconMoreInfoBig").removeClass("iconMoreInfoBigCurrent");
	$(window).scrollTo($(this).parents(".theme").data("top"),300);
	if($(this).parents(".theme").find(".moreInfo").length!=0){
		$(this).parents(".theme").find(".moreInfo").remove();
	}
})
//加载评论方法
function fnLoadMore($this){
	if($this.parents(".theme").find(".moreInfo").length==0){
		$this.parents(".content").after("<div class='moreInfo clearFix'><div class='topArrow'></div><div class='loading'></div><div class='close'><a href='javascript:void(0)'>收起</a></div></div>");
		//加载评论
		fnLoadSlide($this);
		//请程序员自己更改下面代码
		setTimeout(function(){
			var tags="",commenthtm="",samehtm="",otherhtm="",publisherhtm="",enjoyhtm="",
			publisher=$.trim($this.parents(".theme").find(".publisher").attr("text")),
			u=$this.parents(".theme").find(".userHead").find("a").attr("u");
			$this.parents(".content").find(".tagList").find("a").each(function(){
				tags+='"'+$(this).html()+'",';
			});
			if(u==undefined || u==0 || u==null)
				u=$("#_c_u_flag").val();
			var cid=$this.parent().attr("cre");
			tags=tags.substring(0,tags.lastIndexOf(","));
			var ct = $this.attr("ct");
			if(ct==9){
				$.ajax({
					url : SystemProp.appServerUrl+"/index-detail!getEventCommentList.action",
					type : "POST",
					data : {"itemId":cid},
					dataType : 'json',
					success: function(rs){
						if(!rs)return;
						var code = rs.code;
						if(code == 200){
							var comment=rs.data.comment;
							if(comment!=null && comment!=''){
								for(var i=0;i<comment.length;i++){
									var c=comment[i],subNick='';
									var time="",imgsrc="";
									if(c.time==null || c.time=='')
										time=c.createTime.replace("T"," ");
									else
										time=c.time;
									if(c.nickname.length>12)
										subNick=c.nickname.substring(0,12);
									else
										subNick=c.nickname;
									if(c.avatar!=null && c.avatar!='')
									 imgsrc = c.avatar.replace(c.userId+"_","60_"+c.userId+"_");
									if(i<3)
										commenthtm+="<div class='item clearFix'><span>"+time+"</span><img src='"+SystemProp.profileServerUrl+imgsrc+"'  onerror='this.src=\""+SystemProp.staticServerUrl+"/v3/images/head60.gif\"' class='head' title='"+c.nickname+"' /><p><strong>"+subNick+"</strong>"+c.content+"</p></div>";
								}
							}
							var apHtml="<div class='comment'><div class='reply'><input class='input' type='text' tips='发表您的评论' /><a class='btnGB' href='javascript:void(0)'>评论</a></div>"+commenthtm+"</div>";
							$this.parents(".content").next().find(".topArrow").after(apHtml);
							$this.parents(".theme").find(".moreInfo .loading").hide();
						}else{
							alert(rs.message);
						}
					}
				});
			}else{
				$.ajax({
					url : SystemProp.appServerUrl+"/sns/creative-comment.action",
					type : "POST",
					data : {"tags":tags,"creative":cid,"uid":u,"publisher":publisher},
					dataType : 'json',
					success: function(rs){
						if(!rs)return;
						var code = rs.code;
						if(code == 200){
							var comment=rs.data.comment;
							if(comment!=null && comment!=''){
								for(var i=0;i<comment.length;i++){
									var c=comment[i];
									var time="",imgsrc="",subNick="";
									if(c.time==null || c.time=='')
										time=c.createTime.replace("T"," ");
									else
										time=c.time;
									
									if(c.avatar!=null && c.avatar!='')
									 imgsrc = c.avatar.replace(c.userId+"_","60_"+c.userId+"_");
									if(c.nickname.length>12)
										subNick=c.nickname.substring(0,12);
									else
										subNick=c.nickname;
									if(i<3)
										commenthtm+="<div class='item clearFix'><span>"+time+"</span><img src='"+SystemProp.profileServerUrl+imgsrc+"'  onerror='this.src=\""+SystemProp.staticServerUrl+"/v3/images/head60.gif\"' class='head' title='"+c.nickname+"' /><p><strong>"+subNick+"</strong>"+c.content+"</p></div>";
									//else
										//commenthtm+="<div class='item itemMore'><a href='javascript:void(0)'>更多评论</a></div>";
								}
							}
							var same =rs.data.same;
							if(same!=null && same!=''){
								samehtm="<div class='sameTag'><h3>同类标签</h3><div class='clearFix'>";
								for(var i=0;i<same.length;i++){
									var s=same[i];
									if(s.conType==2)
										samehtm+="<a url='"+s.creativeId+"' href='"+SystemProp.appServerUrl+"/sns/c"+s.creativeId+"/'><img src='"+prefix(s.imgPath,'100_')+"' /></a>";
									else
										samehtm+="<a url='"+s.creativeId+"' href='"+SystemProp.appServerUrl+"/sns/c"+s.creativeId+"/'><img src='"+s.imgPath+"' /></a>";
								}
								samehtm+="</div></div>";
							}
							same=null;
							var other =rs.data.creOther;
							if(other!=null && other!=''){
								otherhtm+="<div class='sameUser'><h3>来自<strong>用户</strong>的其它内容</h3><div class='clearFix'>";
								for(var i=0;i<other.length;i++){
									var o=other[i];
									if(o.conType==2)
										otherhtm+="<a url='"+o.creativeId+"' href='"+SystemProp.appServerUrl+"/sns/c"+o.creativeId+"/'><img src='"+prefix(o.imgPath,'100_')+"' /></a>";
									else
										otherhtm+="<a url='"+o.creativeId+"' href='"+SystemProp.appServerUrl+"/sns/c"+o.creativeId+"/'><img src='"+o.imgPath+"' /></a>";
								}
								otherhtm+="</div></div>";
							}
							other=null;
							var publisherlist=rs.data.publisher;
							if(publisherlist!=null && publisherlist!=''){
								publisherhtm="<div class='sameMagazine'><h3><strong>&nbsp;[&nbsp;"+publisher+"&nbsp;]&nbsp;</strong>的其它内容</h3><div class='clearFix'>";
								for(var i=0;i<publisherlist.length;i++){
									var p=publisherlist[i];
									if(p.conType==2)
										publisherhtm+="<a url='"+p.creativeId+"' href='"+SystemProp.appServerUrl+"/sns/c"+p.creativeId+"/'><img src='"+prefix(p.imgPath,'100_')+"' /></a>";
									else
										publisherhtm+="<a url='"+p.creativeId+"' href='"+SystemProp.appServerUrl+"/sns/c"+p.creativeId+"/'><img src='"+p.imgPath+"' /></a>";
								}
								publisherhtm+="</div></div>";
							}
							
							
							var enjoy=rs.data.enjoy;
							if(enjoy!=null && enjoy!=''){
								enjoyhtm="<div class='sameLike'><h3><strong>"+enjoy[0].num+"</strong>个喜欢</h3><div class='clearFix'>";
								for(var i=0;i<enjoy.length;i++){
									var e=enjoy[i],imgsrc="";
									if(e.avatar!=null && e.avatar!='')
										 imgsrc = e.avatar.replace(e.userId+"_","60_"+e.userId+"_");
									enjoyhtm+="<a url='"+e.userId+"' title='"+e.nickname+"' href="+SystemProp.appServerUrl+"'/sns/u"+e.userId+"/'><img src='"+SystemProp.profileServerUrl+imgsrc+"'  onerror='this.src=\""+SystemProp.staticServerUrl+"/v3/images/head60.gif\"' /></a>";
								}
								
								enjoyhtm+="</div>";
								//<span class='more'>更多喜欢999+</span>
							}
							enjoy=null;
							
							//转发
							var forward=rs.data.forward;
							var fcount = rs.data.forwardCount;
							var forwardhtm="";
							if(forward!=null && forward!=''){
								forwardhtm="<div class='sameForward'><h3><strong>"+fcount+"</strong>个转发</h3><div class='clearFix'>" ;
								
								for(var i=0;i<forward.length;i++){
									var f=forward[i],imgsrc="",subNick="";
									if(f.avatar!=null && f.avatar!='')
										imgsrc = f.avatar.replace(f.userId+"_","60_"+f.userId+"_");
									if(c.nickname.length>12)
										subNick=c.nickname.substring(0,12);
									else
										subNick=c.nickname;
									
									forwardhtm+="<a url='"+f.userId+"' href="+SystemProp.appServerUrl+"'/sns/u"+f.userId+"/'><img src='"+SystemProp.profileServerUrl+imgsrc+"'  onerror='this.src=\""+SystemProp.staticServerUrl+"/v3/images/head60.gif\"' title='"+f.nickname+"' /><p><strong>"+subNick+"</strong>转发了你的内容</p></a>";
								}
								forwardhtm+="</div>";
								if(fcount>10){
									forwardhtm+="<span class='more'>更多转发"+fcount-10+"+</span>";
								}
								forwardhtm+="</div>";
							}
							var apHtml="<div class='comment'><div class='reply'><input class='input' type='text' tips='发表您的评论' /><a class='btnGB' href='javascript:void(0)'>评论</a></div>"+commenthtm+"</div>"+samehtm+otherhtm+publisherhtm+forwardhtm+enjoyhtm;
							$this.parents(".content").next().find(".topArrow").after(apHtml);
							$this.parents(".theme").find(".moreInfo .loading").hide();
						}else{
							alert(rs.message);
						}
					}
				});
			}
			
		},500);
	}else{
		fnLoadSlide($this);
	}
	
}
//评论下拉效果
function fnLoadSlide($this){
	$this.parents(".theme").data("top", $this.parents(".theme").offset().top-75);
	if($this.parents(".theme").find(".moreInfo").hasClass("open")){
		$this.removeClass("iconMoreInfoBigCurrent").parents(".theme").find(".moreInfo").removeClass("open").slideUp(300).find(".topArrow").fadeOut(200);
		$(window).scrollTo($this.parents(".theme").data("top"),300);
		if($this.parents(".theme").find(".moreInfo").length!=0){
			$this.parents(".theme").find(".moreInfo").remove();
		}
	}else{
		$this.addClass("iconMoreInfoBigCurrent").parents(".theme").find(".moreInfo").addClass("open").slideDown(300).find(".topArrow").hide().delay(300).fadeIn(200);
	}

}

$("#r_u_list").find(".more").live("click",authorScroll);
var _uv=5,flagA=0;
function authorScroll(){
	var ru=$(".author_list").length;
	
	if(flagA==0)
		$($(".author_list")[_uv-1]).removeClass("last");
	else{
		flagA=0;
		$($(".author_list")[ru-1]).removeClass("last");
	}
	
	if(ru>_uv){
		var c=0;
		for(var i=0;i<ru;i++){
			if(fv<ru && _uv<=i && _uv+5>i ){$($(".author_list")[i]).show();c++;}
			else $($(".author_list")[i]).hide();
		}
		for(var i=0;i<5-c;i++){
			$($(".author_list")[i]).show();
		}
		_uv+=5;
		if(_uv>=ru)_uv=_uv-ru;
		if(c<5){
			flagA=1;
			$($(".author_list")[ru-1]).addClass("last");
		}else{
			if(_uv==0){$($(".author_list")[ru-1]).addClass("last");flagA=1;}
			$($(".author_list")[_uv-1]).addClass("last");
		}
	}
}
$(".topBar>.inner>.head").find("img").live("click",function(){
	var url=$(".topBar>.inner>.head").find(".userUrl").attr("url");
	if(url!=undefined && url!='')
		window.location.href=url;
});
$(".topBar>.inner>.head").find(".userUrl").live("click",function(){
	var url=$(this).attr("url");
	if(url!=undefined && url!='')
		window.location.href=url;
});
$(".author_list").find("img").live("click",function(){
	var url=$(this).attr("url");
	if(url!=undefined && url!='')
		window.location.href=url;
});

$("#cre_list").find(".more").live("click",pcrvScroll);
var fv=5, flagC=0;
function pcrvScroll(){
	var ru=$(".pcrv_list").length;
	
	if(flagC==0)
		$($(".pcrv_list")[fv-1]).removeClass("last");
	else{
		flagC=0;
		$($(".pcrv_list")[ru-1]).removeClass("last");
	}
	
	if(ru>fv){
		var c=0;
		for(var i=0;i<ru;i++){
			if(fv<ru && fv<=i && fv+5>i ){$($(".pcrv_list")[i]).show();c++;}
			else $($(".pcrv_list")[i]).hide();
		}
		for(var i=0;i<5-c;i++){
			$($(".pcrv_list")[i]).show();
		}
		fv+=5;
		if(fv>=ru)fv=fv-ru;
		
		if(c<5){
			flagC=1;
			$($(".pcrv_list")[ru-1]).addClass("last");
		}else{
			if(fv==0){$($(".pcrv_list")[ru-1]).addClass("last");flagC=1;}
			$($(".pcrv_list")[fv-1]).addClass("last");
		}
	}
}
var sq_flag=true;
$(".tagList>a").live("click",function(){
	if(sq_flag){
		sq_flag=false;
		$Dashboard = $("#Dashboard");
		var path = SystemProp.appServerUrl+"/sns/search.action";
		var begin = $Dashboard.find("div.theme").length;
		var key=$(this).html();
		var t="sns";
		var data = {"searchType":t,"queryStr":key};
		$.ajax({
			url : path,
			type : "post",
			async : false,
			data : data,
			success : function (rs){
				sq_flag=true;
				scrolltotop.scrollup();
				$("#loading").hide();
				$Dashboard.find(".theme").remove();
				if($("#sq_sns_tag").val()!=0)
					$Dashboard.after("<input type='hidden' id='sq_sns_tag' value='0' >");
				if($Dashboard.find(".topBarVip").length>0)
					$Dashboard.find(".topBarVip").after(rs);
				else
					$Dashboard.append(rs);
				
				
			}
		});
	}
	
});
$(".conMorePic").find("a").live("click",function(){
	window.location.href= $(this).attr("url");
});

//右侧图片滚动================================================================
var $mpId = $(".sideRight .conMorePic");
var $mpItem = $mpId.find(".item");
var $mpImg = $mpItem.find("a");
var $ding;
var mpAutoPlay = setInterval(fnMpPlay,5000);
var mpCurrentId = 0;
var mpGargetId = 0;
var moveLock = 1;
var moveTimeout = setTimeout(function(){moveLock=0},30000);
if($.browser.safari){
	$mpId.append("<em></em><em></em>");
	$ding = $mpId.find(">em");
}else{
	$mpItem.append("<em></em>");
	$ding = $mpItem.find(">em");
}
$mpItem.eq(0).siblings(".item").css({left:238});

$(document).mousemove(function(){
	moveLock=1;
	clearTimeout(moveTimeout);
	moveTimeout = setTimeout(function(){moveLock=0},10000);
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

function prefix(v,pre){
	if(v!='' && pre!=''){
		 var index=v.lastIndexOf("/");
		return v.substring(0,index+1)+pre+v.substring(index+1);
	}
}

}})(jQuery); 

//加载用户信息功能
function fnLoadUserInfo($this){
	var u = $this.find("a").attr("u");
	$this.append("<div class='tool'><div class='outer'></div></div>");
	$.ajax({
		url : SystemProp.appServerUrl+"/sns/public-sns!uInfo.action",
		type : "POST",
		data : {"userId":u},
		dataType : 'json',
		success: function(rs){
			if(!rs)return;
			var code = rs.code;
			if(code == 200){
				var info =rs.data.info;
				var ism="",nikc=info.nickname,subNick="";
				if(info.ism=='M'){
					ism="<span>麦米认证编辑</span>";
					nikc=info.nameZh;
				}
				if(nikc.length>12)
					subNick=nikc.substring(0,12);
				else
					subNick=nikc;
					
				
				html = "<div class='inner'><strong class='name' title='"+nikc+"'>"+subNick+ism+"</strong><div class='company'>"+info.publisher+"<span>"+info.office+"</span></div><div class='count'><p>关注：<span>"+info.attention+"</span></p><p>粉丝：<span>"+info.fans+"</span></p><p>内容：<span>" +
						"<a href='javascript:void(0)'>"+info.creativeCount+"</a></span></p></div><div class='info'>"+info.info+"</div>  </div> ";
				if(rs.data.self!=1){
					html+="<div class='ft'><a class='sns_mes message' n='"+info.nickname+"' u='"+info.id+"' href='javascript:void(0)'>发消息</a>";
					if(info.isF>0){
						if(info.id!=9999)
							html+="<a nick='"+info.nickname+"' u='"+info.id+"' class='cancel' href='javascript:void(0)'>取消关注</a>";
						else
							html+="<a href='javascript:void(0)'></a>";
					}else
						html+="<a nick='"+info.nickname+"' u='"+info.id+"' class='atten' href='javascript:void(0)'>加关注</a>";
					html+="</div>";
				}
				
						
			}else{
				alert(rs.message);
			}
		}
	});
	setTimeout(function(){$this.find(".tool>.outer").append(html)},1000);
}