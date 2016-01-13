;(function ($){
jQuery.kanmifolder = function() {
var $folderInner;
var $folderItem;
var $id = $(".body .bodyKanmi .item");
var folderOpen = 0;
var lock = 0;
var timeS = 500;
var timeH = 250;
var openId = -1;


var folderHtml = "<div class='folder'><h2>杂志名很长很长很长很长很长很长很长很长</h2><div class='outer scroll-pane'><div class='inner'><a class='showBar' href='kanmi2.html'><img src='images/temp/tempKanmi3.jpg' /><span>2011年9期</span></a><a class='showBar' href='kanmi2.html'><img src='images/temp/tempKanmi3.jpg' /><span>2011年9期</span></a><a class='showBar' href='kanmi2.html'><img src='images/temp/tempKanmi3.jpg' /><span>2011年9期</span></a><a class='showBar' href='kanmi2.html'><img src='images/temp/tempKanmi3.jpg' /><span>2011年9期</span></a><a class='showBar' href='kanmi2.html'><img src='images/temp/tempKanmi3.jpg' /><span>2011年9期</span></a><a class='showBar' href='kanmi2.html'><img src='images/temp/tempKanmi3.jpg' /><span>2011年9期</span></a><a class='showBar' href='kanmi2.html'><img src='images/temp/tempKanmi3.jpg' /><span>2011年9期</span></a><a class='showBar' href='kanmi2.html'><img src='images/temp/tempKanmi3.jpg' /><span>2011年9期</span></a><a class='showBar' href='kanmi2.html'><img src='images/temp/tempKanmi3.jpg' /><span>2011年9期</span></a><a class='showBar' href='kanmi2.html'><img src='images/temp/tempKanmi3.jpg' /><span>2011年9期</span></a><a class='showBar' href='kanmi2.html'><img src='images/temp/tempKanmi3.jpg' /><span>2011年9期</span></a><a class='showBar' href='kanmi2.html'><img src='images/temp/tempKanmi3.jpg' /><span>2011年9期</span></a><a class='showBar' href='kanmi2.html'><img src='images/temp/tempKanmi3.jpg' /><span>2011年9期</span></a><a class='showBar' href='kanmi2.html'><img src='images/temp/tempKanmi3.jpg' /><span>2011年9期</span></a><a class='showBar' href='kanmi2.html'><img src='images/temp/tempKanmi3.jpg' /><span>2011年9期</span></a><a class='showBar' href='kanmi2.html'><img src='images/temp/tempKanmi3.jpg' /><span>2011年9期</span></a><a class='showBar' href='kanmi2.html'><img src='images/temp/tempKanmi3.jpg' /><span>2011年9期</span></a><a class='showBar' href='kanmi2.html'><img src='images/temp/tempKanmi3.jpg' /><span>2011年9期</span></a></div></div><sub class='png'></sub><sup class='png'></sup></div>"



//鼠标移入增加往期按钮，鼠标移出去掉，当前展开不去掉
$id.hover(
function(){
	if($(this).find(".arrow").length!=1){
		$(this).find("h6").prepend("<span class='history'>往期</span>");
	}	
},function(){
	if($(this).find(".arrow").length!=1){
		$(this).find("h6 span.history").remove();
	}	
});


$id.find("h6 .history").live("click",function(){
	if(lock==0){
		lock=1;
		var obj = $(this).parent().parent(".item");//定义当前杂志
		var objParent = $(this).parent().parent().parent(".desk");//定义当前行
		$(this).parent().parent().siblings().find("h6 .history").remove();//移除同行中其它往期按钮
		$(this).parent().parent().parent().siblings().find("h6 .history").remove();//移除其它行中往期按钮
		//判断是否已打开
		if(folderOpen==1){
			//当前按钮
			if(obj.find(".arrow").length==1){
				fnfolderHide();
				setTimeout(function(){fnbookShow()},timeS+timeH-250);
				setTimeout(function(){lock=0},timeS+timeH);
			}else{	
				//是否为不同行
				if(objParent.index()!=openId ){
					fnOpenId(obj,objParent);
					fnfolderHide();
					setTimeout(function(){fnfolderShow(obj,objParent)},timeH);
					setTimeout(function(){fnbookShow()},timeS+timeH-250);
					setTimeout(function(){lock=0},timeS+timeH);
				}else{
					$id.find(".arrow").remove();
					obj.append("<div class='arrow'></div>");
					fnbookShow();
					lock=0;
				}
				folderOpen=1;
			}
		}else{
			fnOpenId(obj,objParent);
			folderOpen = 1;
			$id.find(".arrow").remove();
			fnfolderShow(obj,objParent);
			setTimeout(function(){fnbookShow()},timeS-250);
			setTimeout(function(){lock=0},timeS);
		}
		
	}
	return false;
});





fnfolderShow = function(obj,objParent){
	//判断kanmi1页第一行顶部展开
	obj.append("<div class='arrow'></div>");
	objParent.after(folderHtml);
	$folderInner = $(".folder .inner");
	$folderItem = $(".folder .inner>a");
	$(".folder").css({opacity:0}).animate({height:230,opacity:1},timeS);
	$folderItem.hide();
}

fnfolderHide = function(){
	$id.find(".arrow").remove();
	$(".folder").animate({height:0,opacity:0},timeH);
	setTimeout(function(){$(".folder").remove()},timeH);
	folderOpen=0;
}

fnbookShow = function(){
	$folderItem.hide();
	//添加程序代码
	$folderInner.width($(".folder .inner>a").length*140);
	$(".folder .scroll-pane").jScrollPane();
	$folderItem.fadeIn(500);
	
}

fnOpenId = function(obj,objParent){
	//判断之前是否有folder已加载，定义index值
	if(objParent.prevAll(".folder").length>0){
		openId = objParent.index()-1;
	}else{
		openId = objParent.index();
	}
}


}; 
})(jQuery); 