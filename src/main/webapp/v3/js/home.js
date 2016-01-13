
$(function(){
	//----↓↓---2012-10-29新增内容--↓↓----------------------------------------------------------
	//右侧热门文章执行
	$(".sideRight .conListBig a .pic img").coverImg();
	
	//----↓↓---2012-07改版新增内容--↓↓----------------------------------------------------------
	//订阅到
	$(".rssTo").mouseenter(function(){
		$(this).addClass("hover").find("p").stop(true,true).fadeIn(200);
	}).mouseleave(function(){
		$(this).removeClass("hover").find("p").fadeOut(200);
	});

	//item移入
	function itemHoverIE6(){
		if(isIE6){
			$(".body>.item").not(".itemFirst,.itemSpace").mouseenter(function(){
				$(this).find(".tools").addClass("toolsOn").find("em").css({display:"inline-block"});
			}).mouseleave(function(){
				$(this).find(".tools").removeClass("toolsOn").find("em").not(".favCurrent").css({display:"none"});
			});
		}
	}
	itemHoverIE6();
	
	//首页 & publisherEvent页item hover效果
	var $item = $("#homeWall .item");
	var homeItemLock = 0;
	var homeDelayTime;
	
	window.itemsHov = function ($item){
		$item.mouseenter(function(){
			var obj = $(this);
			homeDelayTime = setTimeout(function(){
				obj.find(".info").addClass("infoCurrent");
			},500)
		}).mouseleave(function(){
			var obj = $(this);
			clearTimeout(homeDelayTime);
			obj.find(".infoCurrent").removeClass("infoCurrent");
		});
	}
	itemsHov($item);
	
	//排列item
	$('#homeWall').masonry({itemSelector: '.item'});
	//运行拖动
	//$.dragSort("#myNavInner",14,80,40);
	
	var indexBannerFlag = curSortId;
	// 首页顶banner打开关闭
	var topBanner = {
		init : function() {
			$("#topBanner>.inner>.close").bind("click", topBanner.hide);
			topBanner.show();
		},
		show : function() {
			$("#topBanner").show().css({"height" : 0}).animate({"height" : 240},500);
		},
		hide : function() {
			$("#topBanner").animate({"height" : 0},500);
			setTimeout(function(){$("#topBanner").hide()},500);
		}
	}
		
	//auto hide --begin
	var hideFlag = true;
	if(indexBannerFlag=='0'){
		topBanner.init();
		var hideFlag = true;
	}else{
		var hideFlag = false;
	}
	var settimeFlag = true;
	timedMsg();
	function timedMsg(){var t=setTimeout(hideDivC,5000);settimeFlag=false;}
	
	function hideDivC(){
		if(hideFlag){
			topBanner.hide();
		}
	}
	
	$("#topBanner").mousemove(function(){
		hideFlag = false;
	});
	
	$("#topBanner").mouseout(function(){
		hideFlag = true;
		if(settimeFlag){
			hideDivC();
		}
	});
	//auto hide --end;
	
	//open video
	$("#ADvideoPlayIndex,#ADvideoPlayIndexImg").click(function(){
	var obj = $(this);
	openDiv(obj.attr("url"));});
	function openDiv(address){
		var urlStr = SystemProp.appServerUrl + "/index-detail!toVideoPlay.action?url="+address;
		$("#videoAdvertiseDialog").html("<iframe id='videoPlayIframe' src='"+urlStr+"' width='600' height='450' frameborder='0' scrolling='no'></iframe>");
		$("#videoAdvertiseDialog").fancybox();
	}
	
	//设置随机显示
	fnRandom("#videoAd");
	fnRandom("#latestMgz");
	
	//首页左侧高度设置，每次加载更多时需要执行
	fnSetHomeHeight();
	
	//----↑↑---2012-07改版新增内容-↑↑-----------------------------------------------------------
	
	var $homeWall = $("#homeWall");
	//排序
	$("a[publicationId]").live('click',function(){
		var pubId = $(this).attr("publicationId"); 
		if(!!pubId){
			location.href=SystemProp.appServerUrl+"/index.action?publicationId="+pubId;
		}
	});
	
	$("#tipClose").click(function(e){
		e.preventDefault();
		window.location.href = "/index.html";
	});
	//图片lazyload
	$homeWall.find('.photo').lazyload({
		placeholder: SystemProp.staticServerUrl+"/v3/images/spacer.gif",
		effect: "fadeIn"
	});
	
	//调用统计
	$indexStatHFun = function(obj){
		if(obj){
			url = "http://www.magme.com/third/rssforkindle.xml?source=";
			$ma(url+obj,"1");
			return true;
		}else{
			return false;
		}
	}
	
	//订阅到
	$(".rssTo").mouseenter(function(){
		$(this).find("p").stop(true,true).fadeIn(200);
	}).mouseleave(function(){
		$(this).find("p").fadeOut(200);
	});
	//click event image--------------------------------------------------------------
	//$("a[clickEventId]").live('click',function(e){
	//	e.preventDefault();
	//	var issueId = $(this).attr("clickEventId").split('_')[0];
	//	var pageNo = $(this).attr("clickEventId").split('_')[1];
	//	window.open(SystemProp.appServerUrl+"/publish/mag-read.action?id="+issueId+"&pageId="+pageNo);
	//});

	//click event image--------------------------------------------------------------
	/*$("a[eventId]").live('click',function(e){
		e.preventDefault();
		var eventId = $(this).attr("eventId");
		window.open(SystemProp.appServerUrl+"/publish/mag-read.action?eventId="+eventId);
	});*/
	
	//loading- scrolling

	if($("body").height() < $(window).height()){
		hasData = false;
		$("#loadMore").fadeOut(500);
//		$("#loading").fadeOut(500);
	}
	var scrollFun = function (begin,size,sortId,publicationId){
		var loadNum = 30;
//		var $loading = $("#loading");
		var $loading = $("#loadMore");
		var $shortCut = $("#homeWall > div.item:visible:not([class~='patch'])");
		if(($shortCut.length%loadNum) != 0){
			hasData = false;
			$loading.fadeOut(500);
			return;
		}

		begin = begin || 0;
		size = size || loadNum;
//		sortId = (!!sortId) ? sortId : getUrlValue("sortId");
		
		publicationId = (!!publicationId) ? publicationId : getUrlValue("publicationId");
		begin = $shortCut.length ;
		var data = {};
		data.begin = begin;
		data.size = size;
		if(curSortId)
			data.sortId = curSortId;
		if(curTagId)
			data.secondSortId = curTagId;
			
		if(!!publicationId){
			data.publicationId = publicationId;
		}		
		
		var ajaxUrl = (window.location.href.indexOf("preview")>-1)?"/index!eventAjaxPreview.action":"/index!eventAjax.action";
		$.ajax({
			url : SystemProp.appServerUrl + ajaxUrl,
			type : "GET",
			async : false,
			data : data,
			success : function (rs){
				if(!rs){
					hasData = false;
					$loading.fadeOut(500);
					return;
				}
				if(rs.code == 200 && !!rs.data && !!rs.data.looArticleList && rs.data.looArticleList.length>0){
					var itemList = rs.data.looArticleList;
					var itemHtml = "";
					for(var i = 0; i < itemList.length; i++){
						var item = itemList[i];
						var it_high =1;
						var it_width =1;
						if(item.high){
							it_high = item.high;
						}
						if(item.width){
							it_width =item.width;
						}
						item.high = it_high * 210 / it_width;//确定图片的显示高度
						if(item.smallPic){
							item.smallPic="http://static.magme.com"+item.smallPic;
						}
						if(item.described&&item.described.length > 150){
							item.described = item.described.substring(0,150) + "……";
						}
						
						if(item.parentTitle){
							var itemList_b = item.itemTitle.split(",");
							var itemList_id = item.itemId.split(",");
							var itemList_parentid = item.parentId.split(",");
							var tStr = "";
							for(k=0;k<itemList_b.length;k++){
								if(k<3){
									var tempName =  itemList_b[k];
									var id = itemList_id[k];
									if(itemList_parentid[k]!="0")
										id = itemList_parentid[k];
									tStr += "<strong class=\"navC"+id+"\">"+tempName+"</strong>";	
								}
							}
							item.itemTitle = tStr;
						}
						itemHtml +="<div class=\"item\">";
						itemHtml += "<a href=\"http://www.magme.com/sns/c"+item.id+"/\" target=\"_blank\">";
						itemHtml += "<div class=\"photo\"><img height=\""+item.high+"\"  src=\""+item.smallPic+"\" alt=\""+item.title+"\"></div>";
						itemHtml += "<div class=\"info png\">";
						itemHtml += "<div class=\"class\">"+item.itemTitle+"</div>";
						itemHtml += "<h2>"+item.title+"</h2>";
						itemHtml += "<p>"+item.memo+"</p>";
						itemHtml += "</div>";
						itemHtml += "</a>";
						itemHtml += "<div class=\"tools png\">";
						itemHtml += "<em class=\"iconShare png\" shareTypeId=\"eve_"+item.id+"_creative\"></em></div>";
						itemHtml += "</div>";
						
					}
					
					var $event = $.tmpl(itemHtml);
					$event.appendTo($homeWall);
					itemsHov($event);
					itemHoverIE6();
					
					$homeWall.masonry('appended', $event);


					//首页左侧高度设置，每次加载更多时需要执行
					fnSetHomeHeight();
				}else{
					hasData = false;
					$loading.fadeOut(500);
				}
				
			}
		});
	}
	//绑定页面的scroll事件
	scrollLoadData(scrollFun,600);
	//页面执行scroll一次
//	window.scroll(0,1);
//	$("#loadMore").trigger("click");
	
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
});

function avatarResize(imgPath, pre){
    if(imgPath && imgPath.indexOf("/")>-1){
        var index = imgPath.lastIndexOf("/") + 1;
        return imgPath.substring(0, index) + pre + "_" + imgPath.substring(index);
    }
    return imgPath;        
}

//保存菜单数据
/*
function fnSaveNavData(data){
	function success(){
		if(curSortId || curTagName){//选择了（一级、二级）标签
			var homeUrl = SystemProp.appServerUrl;
			if(!homeUrl) homeUrl = "http://www.magme.com";
			window.location.href = homeUrl;
		} else {
			fnLoadNavData(showMyNavList);
		}
	}
	if(curSortId) data.sortId = curSortId;
	if(!hasUserLogin()){//未登录
		$("#popLogin").fancybox();
		return false;
//		var sortIds = data.sortIds;
//		setCookie("sortIds",sortIds,new Date("December 31,2120"));
//		success();
	} else {
		$.ajax({
			url:SystemProp.appServerUrl + "/index!saveUserSettings.action",
			type : "POST",
			data : data,
			async : false,
			success: function(result){
				if(result.code==200){
					success();
				}
			}
		});
	}
	return true;
}*/

//显示header上的定制菜单
/*  改版后不需要的内容
function showMyNavList(data){
	var myNav = data.allNav;
	var curIndex = -1;
	for(var i = 0; i < myNav.length; i++){
		var nav = myNav[i];
		if(curSortId == nav.id){
			curIndex = i;
			break;
		}
	}
	//如果当前选中的分类排在第7位以后，则将当前分类与第7位的分类互换位置
	
	//var max = 6;
	//if(curIndex > max){
	//	var temp = myNav[max];
	//	myNav[max] = myNav[curIndex];
	//	myNav[curIndex] = temp;
	//}
	var str = "";
	for(var i = 0; i < myNav.length; i++){
		var nav = myNav[i];
		var link = SystemProp.appServerUrl + "/home/" + nav.id + ".html";
		str += '<li' + (curSortId == nav.id ? ' class="current"' : '') + 
		'><a sortId="' + nav.id + '" href="' + link + '">' + nav.name + '</a>';
		str += '</li>';
	}
	
	$("#homeSortList").html(str);
	var $nav = $(".header20120905 .headerNav");
	$nav.removeAttr("style");
	navMaxH = $nav.height();
	navMaxH > navMinH ? $nav.height(navMinH) : $nav.height(navMaxH);
	navMaxH == navMinH ? $nav.find(".showAll").hide() : $nav.find(".showAll").show();
}


//加载菜单数据
function fnLoadNavData(callback){
	var data = {};
//	if(!hasUserLogin()){//未登录
//		var sortIds = getCookie("sortIds");
//		if(sortIds) data.sortIds = sortIds;
//	}
	$.ajax({
		url:SystemProp.appServerUrl + "/index!showUserSettings.action",
		type : "POST",
		data : data,
		success: function(result){
			if(result.code==200){
				if(callback)
					callback(result.data);
			}
		}
	});
	
}
*/
function fnSetHomeHeight(){
	var rightH = $(".sideRight").height();
	if($.browser.safari){
		rightH+=450;
	}
	if($("#homeWall").height()<rightH){
		$("#homeWall").height(rightH);
	}
}