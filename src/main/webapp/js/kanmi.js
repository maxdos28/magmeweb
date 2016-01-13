//for kanmi
$(document).ready(function(){
	
	var countLine = 3;
	//page animate
	$(".kanmiCon").each(function(i){
		var id = this.id;
		$.kanmileft("#"+id,0);
		$.kanmiright("#"+id);
	});
	$.kanmifolder();
	
	//for kanmiSlideMenu
	var kanmiTopBar = $("#kanmiTopBar");
	var kanmiMenu = $("#conCategory");
	var menuLock=0;
	var btnMenu = $("#btn").html();
	kanmiMenu.hover(
		function(){
			menuLock=1;
			$("#btn").html("查看全部杂志")
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
	
	//categoryList
	var categoryList = $("#categoryList");
	var category = categoryList.find("a[name='familyName']");
//	var familyName = getUrlValue("familyName");
//	var categoryCurrent = category.find("[familyId='"+familyName+"']");
//	if(categoryCurrent.length>0){
//		categoryCurrent.addClass("current");
//	}else{
//		category.eq(0).addClass("current");
//	}
	category.click(function(e){
		e.preventDefault();
		var familyId = $(this).attr("familyId");
		window.location.href = SystemProp.appServerUrl+"/publish/kanmi.action?familyCategoryParentId="+familyId;
	});
	


	//for addBookList
	var $hidden = $(".kanmiCon .bigShow, .kanmiCon .smallShow a");
	setTimeout(function(){$hidden = $.merge( $hidden,$('.kanmiCon .control') );},2000);//必须延迟2秒后加载
	
	//for conBigMgzShow textArea focus
	$(".conTagComment .inputArea textarea").one("focus",function(){
		$(this).height(60).nextAll(".hide").removeClass("hide");
	});
	
	//配合kanmiFolder==================
	kanmiMenu.find("a").bind('click',function(e){
		e.preventDefault();
		var kcLength = $(".kanmiCon").length;//取到kanmiCon个数
		$(".kanmiCon").last().attr("class","kanmiCon kc"+kcLength+" kanmiConLast");//切换后重置最后一行样式
		$.kanmifolder.fnfolderHide();//隐藏kanmiFolder
	});
	
	//show all
	$("#btn").click(function(e){
		e.preventDefault();
		var html = $(this).html();
		window.location.href = SystemProp.appServerUrl+"/publish/km-family-issue!kanmi2.action";
//		var html = $(this).html();
//		fnSwitch();
	});
					
	//menuBar
	kanmiMenu.find("a[categoryId]").bind('click',function(e){
		var categoryId = $(this).attr("categoryId");
		e.preventDefault();
		window.location.href = SystemProp.appServerUrl+"/publish/km-family-issue!kanmi2.action?categoryId="+categoryId;
		//=================================
//		kanmiMenu.find("a.current").removeClass("current");
//		$(this).addClass("current");
//		var rootA = $(this).parents(".item").find("h3>a");
//		rootA && rootA.addClass("current");
//		
//		btnMenu = "<strong>杂志分类</strong>:"+rootA.html()+"<span></span>";
//		$("#btn").html(btnMenu);
//		
//		var categoryId = $(this).attr("categoryId");
//		fnSwitch(categoryId,0);
	});
	//字母搜索事件
	kanmiMenu.find("a[letter]").bind('click',function(e){
		var letter = $(this).attr("letter");
		e.preventDefault();
		window.location.href = SystemProp.appServerUrl+"/publish/km-family-issue!kanmi2.action?letter="+letter;
		
//		kanmiMenu.find("a").find(".current").removeClass("current");
//		$(this).addClass("current");
//		
//		var letter = $(this).attr("letter");
//		fnSwitch(0,letter);
	});
	
	function loadIssue(issues){
		if(!issues) return;
		var deskShow = $("<div id='deskShow' class='deskShow'></div>");
		var length = Math.floor(issues.length/7 + 1);
		countLine = (length>countLine)? length: countLine;
		for(var i=0;i<length;i++){
			var item = $("<div class='item'></div>");
			item.appendTo(deskShow);
			for(var j=0;j<7;j++){
				if(i*7+j < issues.length){
					var issue = issues[i*7+j];
					var a = $("<a issueId='"+issue.id+"' class='showBar png' href='javascript:void(0)'></a>");
					a.appendTo(item);
					var src = SystemProp.magServerUrl +"/"+issue.publicationId+"/110_"+issue.id+".jpg";
					var img = $("<img name='issueRead' src='"+src+"'/><span name='issueRead'>"+issue.issueNumber+"</span>");
					var bookBar = $("<div class='bookBar'><p><em class='save' title='订阅' name='subscribe' ></em><em class='fav' title='收藏' name='collection' ></em></p></div>");
					img.appendTo(a);
					bookBar.appendTo(a);
				}
			}
		}
		
		return deskShow;
	}


	
	//切换画布内容
	//如果categoryId不为0，并且不为空，那么按类目查询
	//如果initletter不为空，那么按照字母查询
//	function fnSwitch(categoryId,initletter){
//		//去除族群的高亮
//		categoryList.find(".current").removeClass("current");
//		
//		//change click event
//		kanmiMenu.find("a[categoryId]").unbind('click').bind('click',function(e){
//			var categoryId = $(this).attr("categoryId");
//			e.preventDefault();
//			window.location.href = SystemProp.appServerUrl+"/publish/km-family-issue!kanmi2.action?categoryId="+categoryId;
//		});
//		
//		kanmiMenu.find("a[letter]").unbind('click').bind('click',function(e){
//			var letter = $(this).attr("letter");
//			e.preventDefault();
//			window.location.href = SystemProp.appServerUrl+"/publish/km-family-issue!kanmi2.action?letter="+letter;
//		});
//		
//		$("#btn").unbind('click').click(function(e){
//			e.preventDefault();
//			var html = $(this).html();
//			window.location.href = SystemProp.appServerUrl+"/publish/km-family-issue!kanmi2.action";
//		});
//		//获取请求数据
//		var deskShow;
//		var callback = function(result){
//			if(!result) return;
//			if(result.code != 200){
//				return;
//			}
//			deskShow = loadIssue(result.data.issueList);
//			
//		};
//		
//		//categoryId 和 initletter都不存在
//		var data={};
//		if(categoryId && categoryId!=0){
//			//有类目id，按照类目id查询
//			data={"categoryId":categoryId};
//		}else if(initletter){
//			data={"letter":initletter};
//		}
//		//var data = categoryId ? {"categoryId":categoryId} : {};
//		$.ajax({
//			url : SystemProp.appServerUrl+"/publish/km-family-issue!kmCategoryJson.action",
//			type : "POST",
//			async : false,
//			data : data,
//			success : callback
//		});
//		
//		
//		$("#btn").html("显示全部分类<span></span>");
//		kanmiMenu.removeClass("conCategoryOn");
//		$hidden.fadeOut(500);
//		$(".kanmiCon .smallShow").delay(500).animate({width:980},1500,"easeOutQuint");
//		$(".kanmiCon .smallShow .desk").delay(500).animate({width:980},1500,"easeOutQuint");
//		setTimeout(function(){
//			$(".kanmiCon").remove();
//			$("#pageKanmi").append(deskShow);
//			var items = deskShow.find(".item");
//			if(items.length >= 3){
//				items.last().addClass("itemLast");
//			}
//			//将countLine负值为加载行数///////////////////////////////////////
//			$(".deskShow").css({height:1100}).animate({height:185*countLine-10},500,"easeOutQuint");
//			fnShowBook();
//		},1550);
//		setTimeout(function(){
//			fnSetFooterHeight();
//		},2050);
//	}
	
	function fnShowBook(){
		var i=0;
		$(".deskShow .item").each(function(){
			$(this).find("a").delay(i*500+500).fadeIn(500);
			i++;
		});
	}
	
	//turnleft and turnright
	$(".smallShow .turnLeft,.smallShow .turnRight").hover(
		function(){
			var currentItem = $(this).parent(".smallShow").find(".current");
			var span = $(this).find("span").eq(0).empty();
			var imgs;
			if($(this).hasClass("turnLeft")){
				imgs = currentItem.prev().find("img");
			}else{
				imgs = currentItem.next().find("img");
			}
			for(var i=0;i<6;i++){
				var img = $("<img />").appendTo(span);
				var src = SystemProp.staticServerUrl+"/images/head145.gif";
				if(i<imgs.length){
					src = imgs[i].src;
				}
				img.attr("src",src);
			}
		},
		function(){}
	);
	
	
	//对bigShow 中的issue的绑定
	$("div[issueId]").find("[name='issueRead']").live('click',function(e){
		var issueDiv = $(this).parents("[issueId]").eq(0);
		var issueId = issueDiv.attr("issueId");
		var pageNum = issueDiv.attr("pageNum") || 0;
		
		e.preventDefault();
		window.open(SystemProp.appServerUrl+"/publish/mag-read.action?id="+issueId+"&pageId="+pageNum,"_magme");
	});
	
	
	
	fnSetFooterHeight();
});

