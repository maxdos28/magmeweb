$(document).ready(function(){
	setWidth();
	introduceVideoFun();
	setTimeout(function(){
		$("#loadingPage").fadeOut(500);
		$("#tagWall").css({visibility:"visible"});
		$('#tagWall').masonry({
			isFitWidth: false,
			itemSelector: '.item',
			isAnimated: false
		});
		
		//tagshow 返回当前位置
		var scrollX = getUrlValue("scrollX");
		if(scrollX && scrollX != 0){
			$(window).scrollTop(scrollX);
		}
		
	},2000);
	//window_resize-----------------------------------------
	var timer = null;
	$(window).bind("resize", function(){
		if(!timer){
			timer = setTimeout(function(){
				setWidth();
				$('#tagWall').masonry('reload');
				timer = null;
			},300);
		}
	});
	
	//切米介绍视频
	function introduceVideoFun(){
		$(".l3").append('<div class="tipMsg"><a id="introduceVideo" href="javascript:void(0)">切米功能介绍视频</a><a class="close"></a></div>');
		var videoDiv = '<div style="width:480px;height:400px;padding-top:30px;" id="videoDiv" class="popContent popRegister" style="display:none;">'+
			'<div class="content"><embed src="http://www.tudou.com/v/B4v6WjgN-1E/v.swf" type="application/x-shockwave-flash"'+
			'allowscriptaccess="always" allowfullscreen="true" wmode="opaque" width="480" height="400"></embed></div></div>';
		$("body").append(videoDiv);
		$("#introduceVideo").click(function(){
			$("#videoDiv").fancybox();
		});
	}
	//初始化切米分类
	function initTopBar(){
		var conCategory=$("#conCategory");
		var categoryId=conCategory.attr("categoryId");
		var orderColumn=conCategory.attr("orderColumn");
		if(!(orderColumn&&(orderColumn=="topNum"||orderColumn=="browseNum"))){
			orderColumn="createdTime";
		}
		
		//给排序字段加上样式
		var orderType=conCategory.attr("orderType");
		if(orderType&&orderType=="asc"){
			$("#categoryList").find("a[orderColumn='"+orderColumn+"']").addClass("orderT");
		}else{
			$("#categoryList").find("a[orderColumn='"+orderColumn+"']").addClass("orderB");
			orderType="desc";
		}
		
		if(categoryId&&(/^\d+$/.test(categoryId))){
			//调整字样
			$("#btn").html("切米分类:"+$("#category_"+categoryId).find("h3").find("a").html()+"<span></span>");
			
			//调整分类样式
			if(orderType&&orderType=="asc"){
				$("#category_"+categoryId).find("a[orderColumn='"+orderColumn+"']").addClass("orderT");
			}else{
				$("#category_"+categoryId).find("a[orderColumn='"+orderColumn+"']").addClass("orderB");
			}
		}
	}
	
	initTopBar();
	
	$("a[name='tagCategoryClick']").live("click",function(e){
		e.preventDefault();
		var url=SystemProp.appServerUrl+"/user-tag!tagWall.action";
		var firstParam=true;
		
		var obj=$(this);
		
		var conCategory=$("#conCategory");
		
		//分类ID
		var categoryId=obj.attr("categoryId");
		if(!categoryId&&obj.attr("id")!="btn"){
			categoryId=conCategory.attr("categoryId");
		}
		if(categoryId&&(/^\d+$/.test(categoryId))){
			if(firstParam){
				url += "?categoryId="+categoryId;
				firstParam=false;
			}else{
				url += "&categoryId="+categoryId;
			}
		}
		
		//用户ID
		var userId=conCategory.attr("userId");
		if(userId&&(/^\d+$/.test(userId))){
			if(firstParam){
				url += "?userId="+userId;
				firstParam=false;
			}else{
				url += "&userId="+userId;
			}
		}
		
		//排序字段
		var orderColumn=obj.attr("orderColumn");
		if(!orderColumn){
			orderColumn=conCategory.attr("orderColumn");
		}
		if(orderColumn){
			if(firstParam){
				url += "?orderColumn="+orderColumn;
				firstParam=false;
			}else{
				url += "&orderColumn="+orderColumn;
			}
		}
		
		//排序类型(仅在点击的对象有排序的样式的前提下有效,否则默认为按降序排列)
		var objClass = obj.attr("class");
		if(objClass&&(objClass=="orderT"||objClass=="orderB")){
			var orderType="";
			if(objClass=="orderB"){
				orderType="asc";
			}
			if(objClass=="orderT"){
				orderType="desc";
			}

			if(firstParam){
				url += "?orderType="+orderType;
				firstParam=false;
			}else{
				url += "&orderType="+orderType;
			}
		}
		
		window.location.href=url;
	});
	
	//for tagSlideMenu---------------------------------------
	var kanmiMenu = $("#conCategory");
	var menuLock=0;
	var btnMenu = $("#btn").html();
	kanmiMenu.hover(
		function(){
			menuLock=1;
			$("#btn").html("显示全部切米")
			$(this).addClass("conCategoryOn");
		},function(){
			menuLock=0;
			var obj=$(this);
			setTimeout(function(){if(menuLock==0){
				$("#btn").html(btnMenu);
				obj.removeClass("conCategoryOn");
			}},500);
		}
	);
	
	//for setWidth
	//<!--980-1228-1476-1972-2220-2468-->
	function setWidth(){
		var browserWidth = $(window).width();
		var screenWidthArray=[2468,2220,1972,1476,1228,980];
		var $header = $(".main>.header");
		var $body = $(".body>.outer");
		var $inner = $(".body>.outer>.inner");
		var $footer = $(".footer>.inner");
		for(i=0;i<screenWidthArray.length;i++){
			if(browserWidth>screenWidthArray[i]+20){
				$header.width(screenWidthArray[i]);
				$footer.width(screenWidthArray[i]);
				$body.width(screenWidthArray[i]);
				$inner.width(screenWidthArray[i]+12);
				return ;
			}
		}
	}
	
	
	//------------------------------- scroll add tags----------------------------------
	var hasTag = true;
	var scrollTimer = null;
	$(window).scroll(function(){
		var hasOver = hasOverlay();
		if(!scrollTimer && hasTag && !hasOver){
			scrollTimer = setTimeout(function(){
				var tagNum = $(".item",$("#tagWall")).length;
				var userId = getUrlValue("userId");
				var bodyHeight = $("body").height();
				var windowHeight = $(window).height();
				var scrollHeight = bodyHeight - windowHeight;
				
				var scrollTop = $(window).scrollTop();
				if(scrollTop > (scrollHeight-300)){
					$("#loadingTags").fadeIn();
					loadTagHtml(tagNum,20,userId);
//					addTags(tagNum,20,userId);
				}
				scrollTimer = null;
			},2000);
		}
	});
	//-----------------------loadTagHtmlFun-----------------------------
	function loadTagHtml(begin,size,userId){
		var callback = function(result){
			if(result){
				$("#loadingTags").before(result);
				$('#tagWall').masonry('reload');
				$("#loadingTags").fadeOut(5000);
			}else{
				$("#loadingTags").html("没有数据加载").fadeOut(5000);
				hasTag = false;
			}
		};
		var data = {};
		if(begin && !isNaN(begin)){
			data.begin = parseInt(begin,10);
		}
		if(size && !isNaN(size)){
			data.size = parseInt(size,10);
		}
		if(userId && !isNaN(userId)){
			data.userId = parseInt(userId,10);
		}
		var orderColumn=$("#conCategory").attr("orderColumn");
		var categoryId = $("#conCategory").attr("categoryId");
		if(categoryId){
			data.categoryId = categoryId;
		}
		if(orderColumn){
			data.orderColumn = orderColumn;
		}
		var orderType=$("#conCategory").attr("orderType");
		if(orderType){
			data.orderType = orderType;
		}
		$.ajax({
			url : SystemProp.appServerUrl+"/user-tag!getTagWallListAjax.action",
			type : "POST",
			async : false,
			data : data,
			success : callback
		});
	}
//	function addTags(begin,size,userId){
//		var callback = function(result){
//			if(!result) return;
//			var data = result.data;
//			if(data && data.tagList && data.tagList.length > 0 ){
//				tagsLoad(data.tagList);
//				$("#loadingTags").fadeOut(3000);
//			}else{
//				$("#loadingTags").html("没有数据加载").fadeOut(5000);
//				hasTag = false;
//				return;
//			}
//		};
//		var data = {};
//		if(begin && !isNaN(begin)){
//			data.begin = parseInt(begin,10);
//		}
//		if(size && !isNaN(size)){
//			data.size = parseInt(size,10);
//		}
//		if(userId && !isNaN(userId)){
//			data.userId = parseInt(userId,10);
//		}
//		var orderColumn=$("#conCategory").attr("orderColumn");
//		var categoryId = $("#conCategory").attr("categoryId");
//		if(categoryId){
//			data.categoryId = categoryId;
//		}
//		if(orderColumn){
//			data.orderColumn = orderColumn;
//		}
//		var orderType=$("#conCategory").attr("orderType");
//		if(orderType){
//			data.orderType = orderType;
//		}
//		$.ajax({
//			url : SystemProp.appServerUrl+"/user-tag!getTagWallListJson.action",
//			type : "POST",
//			async : false,
//			data : data,
//			success : callback
//		});
//	}
	// 加载tag到页面
//	function tagsLoad(tagList){
//		$.each(tagList,function(i,tag){
//			var item = $('<div class="item"></div>');
//			$("#loadingTags").before(item);
//			var a = $('<a tag="'+tag.id+'" class="img" href="javascript:void(0)"></a>').appendTo(item);
//			var tag_path = tag.path ? (SystemProp.tagServerUrl+tag.path) : SystemProp.staticServerUrl+"/images/tempMagezineBig2.jpg";
//			var img = $('<div style="width:210px;height:'+(210*tag.height/(tag.width*1))+'px;"><img src="'+tag_path+'"/></div>').appendTo(a);
//			var title = $('<a tagTitle="title" class="name" href="javascript:void(0)">'+tag.title+'</a>').appendTo(item);
//			var comment = $('<div class="tools"><em class="icon1">点击('+tag.clickNum+')</em><em class="icon2">推荐('+tag.topNum+')</em><em class="icon3">分享</em></div> ').appendTo(item);
//			
//			var user = $('<div class="clearFix"></div>').appendTo(item);
//			var avatar = (tag.user && tag.user.avatar30) ? SystemProp.profileServerUrl+tag.user.avatar30 : SystemProp.staticServerUrl+"/images/head30.gif";
//			var user_avatar = $('<img src="'+avatar+'" />').appendTo(user);
//			var user_nickName = $('<p><a user="'+tag.user.id+'" href="javascript:void(0)">'+(tag.user.nickName)+'</a>创建了此标签，源于<a issue="'+tag.issue.id+'" href="javascript:void(0)">'+(tag.issue.publicationName)+'</a></p>').appendTo(user);
//			
//			itemHoverFun(item);
//			setTimeout(function(){
//				$("#tagWall").masonry('appended',item,true);
//			},2000);
//		});
//		
//	}
	
	//tag.click
	$("a[tag]").live('click',function(e){
		e.preventDefault();
		var targetElement =  e.target;
		
		var $tag = $(this);
		var tagId = $tag.attr("tag");
		var scrollX = $(window).scrollTop();
		var size = $("#tagWall").find(".item").length;
		var url = SystemProp.appServerUrl+"/user-tag!show.action?id="+tagId+"&size="+size+"&scrollX="+scrollX;
					
		//增加分类和排序参数
		var categoryId = getUrlValue("categoryId")||"";
		var orderColumn =  getUrlValue("orderColumn")||"";
		if(categoryId){
			url += "&categoryId="+categoryId;
		}
		if(orderColumn){
			url += "&orderColumn="+orderColumn;
		}
		
		window.location.href = url;
	});
	
	
	//tag.user
	$("a[user]").live('click',function(e){
		var userId = $(this).attr("user");
		e.preventDefault();
		window.location.href = SystemProp.appServerUrl+"/user!visit.action?id="+userId;
	});
	//issue
	$("a[issue]").live('click',function(e){
		var issueId = $(this).attr("issue");
		e.preventDefault();
		window.open(SystemProp.appServerUrl+"/publish/mag-read.action?id="+issueId,"_magme");
	});
	
	//footerMini--------------------------------------------
	footerMini();
	
	//itemHoverFun------------------------------------------
//	function itemHoverFun(item){
//		item.hover(function(){
//			$(this).animate({backgroundColor:"#dcdcdc"},300);
//			$(this).find(".tools em.icon3").append($("#ckepop").show());
//			var $tag = $(this).find("a[tag]");
//			var tagId = $tag.attr("tag");
//			var url = SystemProp.domain+"/user-tag!show.action?id="+tagId;
//			var tagTitle = $(this).find("a[tagTitle]").html();
//			var picUrl = $(this).find(".img img").attr("src");
//			tagShare(url,tagTitle,picUrl);
//		},
//		function(){
//			$(this).animate({backgroundColor:"#f5f5f5"},300);
//			$("#ckepop").hide().appendTo($("body"));
//		});
//	}
//	itemHoverFun($("#tagWall>.item"));

});