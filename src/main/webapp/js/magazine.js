;$(document).ready(function(){
	
	$.kanmifolder();
	//杂志商提示
	//$userTips = $(".body .bodyKanmi .item .tips");
	$(".body .bodyKanmi .item .tips del").live("click",function(){
		$(".body .bodyKanmi .item .tips").fadeOut(300);
	});
	
	//看米页添加杂志边框
	if($.browser.version == "6.0" && $.browser.msie){
		$(".body .bodyKanmi .item").live( "mouseenter",function(){
			$(this).addClass("hover");
		}).live("mouseleave",function(){
			$(this).removeClass("hover");
		});
	}
		
	//share's parameter
	
	//all kinds
	$(".listB>li>a[categoryId]").click(function(e){
		e.preventDefault();
		var type = getUrlValue("type") || 1;
		var categoryId = $(this).attr("categoryId") || 0;
		window.location.href = SystemProp.appServerUrl+"/publish/magazine.html?type="+type+"&categoryId="+categoryId;
	});
	//tag_magazine
	$(".listTag>li>a[tagName]").click(function(e){
		e.preventDefault();
		var type = getUrlValue("type") || 1;
		var tagName = encodeURIComponent( $(this).attr("tagName") );
		window.location.href = SystemProp.appServerUrl+"/publish/magazine.html?type="+type+"&tagName="+tagName;
	});
	//出版商的米商中心跳转
	$("div.item img[publisherId],a[publisherId]").live("click",function(e){
		e.preventDefault();
		var publisherId = $(this).attr("publisherId");
		if(!!publisherId){
			window.location.href = SystemProp.appServerUrl+"/publish/publisher-home.action?publisherId="+publisherId;
		}
	})
	
	
	//滚动加载杂志
	var loadMagFun = function(begin,size,type,categoryId,tagName){
		if($(".bodyKanmi .desk:last .item").length < 5){
			hasData = false;
			return;
		}
		var $loadMore = $("#loadMore");
		$loadMore.show();
		begin = begin || 0;
		size = size || 15;
		categoryId = (!!categoryId) ? categoryId : getUrlValue("categoryId");
		type = (!!type) ? type : getUrlValue("type") ? getUrlValue("type") : 1;
		tagName = (!!tagName) ? tagName : getUrlValue("tagName");
		//每次加载数据的时候begin重新获取
		begin = $(".desk>.item:visible").length ;
		
		var data = {};
		data.begin = begin;
		data.size = size;
		data.type = type;
		if(!!categoryId){
			data.categoryId = categoryId;
		}
		if(!!tagName && tagName !== 0){
			data.tagName = encodeURIComponent( tagName );
		}
		
		$.ajax({
			url : SystemProp.appServerUrl+"/publish/magazine!magazineAjax.action",
			type : "GET",
			async : false,
			data : data,
			success : function (rs){
				if(!rs) {
					hasData = false;
					return;
				}
				$loadMore.before(rs);
				$loadMore.hide();
			}
		});
	};
	//window_scroll------------------------------------------------------------
	scrollLoadData(loadMagFun,150);
	
});