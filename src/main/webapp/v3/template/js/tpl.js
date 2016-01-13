//定义不同模板的随机值
var striNum;
var strtNum;
var tplTotalList = [3,2,5,
					8,12,12,
					6,6,8,
					6,6,10,
					5,5,10,
					6,6,8,
					6,4,7,
					15,0,0,
					1,0,0,
					1,0,0,
					1,0,0];




//计算模板总数
function fnCountTpl(){
	var count=0;
	for(var i=0;i<tplTotalList.length;i++){
		count+=tplTotalList[i];
	}
	return count;
}


//选择每模板随机数
function fnSetTotal(striNum,strtNum){
	switch (strtNum){
		case "a": var tpltNum = 0; break;
		case "b": var tpltNum = 1; break;
		case "c": var tpltNum = 2; break;
	}
	return tplTotalList[striNum*3+tpltNum];
}


//最多显示10张，6张以上只显示a模式
function fnSetiNum(iNum,tNum){
	if( (iNum>=0 && iNum<=6) || (iNum>=7 && iNum<=10 && tNum=="a") ){
		striNum = iNum;
	}else
	if( (iNum>=7 && iNum<=10 && tNum!="a") || (iNum>10 && tNum!="a") ){
		striNum = 6;
	}else
	if(iNum>10 && tNum=="a"){
		striNum = 10;
	}else{
		alert(iNum+"/"+tNum);
	}
	strtNum = tNum;
}


//生成排版，2参数随机，3参数指定
function fnGenerate(iNum,tNum,tpl,first){
	var $id = $("#mainContent");
	var $txt = $("#mainText");
	var $h1 = $id.find("h1");
	var $img = $id.find("a.pic img");
	var maxSize = 0;

	fnSetiNum(iNum,tNum);
	
	var tplNum = fnSetTotal(striNum,strtNum);
	
	fnMorePic();
	
	//设置模板
	fnTplSet(tplNum);
	//首字下沉
	fnFirstText(first);
	//出场显示动画
	fnShow();
	//定义标题
	fnSetTitle();
	//定义滚动条
	$txt.mCustomScrollbar();
	//图片处理
	$img.each(function() {
		//添加多媒体图标
		var $pic = $(this).parents(".pic");
		if($pic.hasClass("video")){
			$pic.append("<em class='png video'></em>");
		}else if($pic.hasClass("audio")){
			$pic.append("<em class='png audio'></em>");
		}
		//设置Title
		if($(this).parent("div").next("span").html().length>0){
			$(this).parents("a.pic").attr({"title":$(this).parent("div").next("span").html()});
		}else{
			$(this).parent("div").next("span").hide();
		}
		
		//设置图片遮罩尺寸
        $(this).parent("div").width($(this).parents("a.pic").width()).height($(this).parents("a.pic").height());
    });
	//定义图片缩放
	$img.coverImg();
	
	
	
	
	
	
	//出场显示效果
	function fnShow(){
		$id.fadeIn(850);
	}
	//选模板
	function fnTplSet(tplNum){
		if(tpl==null){
			var ran = Math.floor(Math.random()*tplNum+1);
		}else{
			var ran = tpl;
		}
		$id.attr("class",$id.attr("class")+" tpl"+striNum+" tpl"+striNum+strtNum+"-"+ran);
		$("#template_pattern").val(ran);
	}
	//定义标题模式
	function fnSetTitle(){
		//height==[777]fixed,height==[888]autosize,height==[999]both
		if($h1.height()==777){
			fnTitleFixed();
		}else if($h1.height()==888){
			fnTitleSize(32);
		}else if($h1.height()==999){
			fnTitleFixed();
			fnTitleSize(32);
		}
	}
	//标题自适应一行
	function fnTitleSize(maxSize){
		$h1.css({"font-size":maxSize+"px","height":"auto"});
		var h1Height = $h1.height();
		if(h1Height>maxSize*2){
			maxSize-=3;
			if(maxSize<16){maxSize=16;return false;}
			fnTitleSize(maxSize);
		}
	}
	//标题绝对定位
	function fnTitleFixed(){
		$h1.height("auto").insertBefore($txt);
	}
	//首字下沉
	function fnFirstText(first){
		//无首字定义的为1
		if(first==undefined){
			first=1;
		}
		var str = $txt.find(".text").html();
		var str2="<span class='firstText'>"+str.substr(0,first)+"</span>"+str.substr(first);
		$txt.find(".text").html(str2);
	}
	//更多图片
	function fnMorePic(){
		if(striNum != iNum){
			$("#mainContent").append("<h5>"+iNum+"+</h5>");
		}
	}
}




//查看动画
;(function($){
$.tplImgView = function(content) {

	var $img = $("#mainContent a.pic");
	var $close;
	var vSpeed = 1200;
	var iSpeed = 700;
	var winW = 0;
	var winH = 0;
	var vLock = 0;
	var scrollTop;
	
	$img.click(function(){
		if(vLock==0){
			var imgIndex = $(this).index("#mainContent a.pic");
			vLock = 1;
			$("body,html").css({overflow:"hidden"});
			winW = $(window).width();
			winH = $(window).height();
			scrollTop = $(window).scrollTop();
			$("body").append("<div id='imageView'><a class='close' href='javascript:void(0)'>返回</a><div class='content'></div></div>");	
			$close = $("#imageView a.close");
			$("#imageView").width(winW).height(winH).css({top:scrollTop,left:winW});
			$("#body").animate({left:-winW},vSpeed,"easeOutCubic");
			$("#imageView").animate({left:0},vSpeed,"easeOutCubic",function(){
				fnLoadImgData();
				$close.delay(300).fadeIn(iSpeed);
				$("#imageView img").css({opacity:0}).delay(300).animate({opacity:1},iSpeed);
				if( imgIndex!=0){
					$("#imageView").delay(300).scrollTo($("#imageView .content>*").eq(imgIndex).offset().top-100,1000,"easeOutCubic");
				}
				vLock = 0;
			});
			$(window).bind("resize",fnSetSize);
			$close.bind("click",clickClose);
		}
	});
	
	function clickClose(){
		vLock = 1;
		$close.hide();
		$("#imageView").animate({left:winW},vSpeed,"easeOutCubic",function(){
			$("#imageView").remove();
			$("html,body").css({overflow:"auto"});
			vLock = 0;
		});
		$("#body").animate({left:0},vSpeed,"easeOutCubic");
		$(window).unbind("resize");
		$close.unbind("click");
	}
	
	function fnSetSize(){
		winW = $(window).width();
		winH = $(window).height();
		scrollTop = $(window).scrollTop();
		$("#imageView").width(winW).height(winH).css({top:scrollTop,left:0});
		$("#body").css({width:winW,left:-winW});
	}
	
	function fnLoadImgData(){
		$("#imageView .content").html(content);
	}

}})(jQuery);



	






//指定模板列表
function fnShowTplList(iNum,tNum,index){
	//定义当前模板列表
	
	fnSetiNum(iNum,tNum);
	
	var tplNum = fnSetTotal(striNum,strtNum);
	var current = index=="0" ? 1 : parseInt(index)-1;
	var tempHtml = "<ul>";
	for(var i=1;i<tplNum+1;i++){
		tempHtml += "<li><a><img src='"+SystemProp.staticServerUrl+"/v3/template/images/tpl"+striNum+strtNum+"/"+i+".gif' /></a></li>";
	}
	tempHtml += "</ul>";
	$("#tplList").html(tempHtml);
	$("#tplList ul li").eq(current).find("a").addClass("current");
}
//全部模板列表
function fnShowAllTpl(){
	tempHtml = "<div><strong>模板总数："+fnCountTpl()+"</strong></div>";
	for(var ii=0;ii<tplTotalList.length;ii++){
		
		var iNum = parseInt(ii/3);
		var tNum = Math.round(ii%3);
		var txt;
		
		switch (tNum){
			case 0 : tNum="a"; txt="0-50字"; break;
			case 1 : tNum="b"; txt="50-200字"; break;
			case 2 : tNum="c"; txt="200字以上"; break;
		}
		
		tempHtml += "<ul class='clearFix'><li class='title'>模板"+iNum+tNum+"&nbsp;--&nbsp;"+iNum+"张图片，"+txt+"</li>";
		for(var i=1;i<tplTotalList[ii]+1;i++){
			tempHtml += "<li><a><img src='images/tpl"+iNum+tNum+"/"+i+".gif' /></a></li>";
		}
		tempHtml += "</ul>";
		$("#tplList").html(tempHtml);		
	}
}





