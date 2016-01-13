$(document).ready(function(){
	
//	var countLine = 3;
//	var length = $("#deskShow .item").length;
//	countLine = (length>countLine)? length: countLine;
//	if(length<countLine){
//		$("#deskShow").css({minHeight:185*countLine+20});
//	}
	
	
	$.kanmifolder();
	//for kanmiSlideMenu
	var kanmiTopBar = $("#kanmiTopBar");
	var kanmiMenu = $("#conCategory");
	var btn = $("#btn");
	var menuLock=0;
	var currentMenu = kanmiMenu.find("a[categoryId].current").eq(0);
	var btnMenu = btn.html();
	if(currentMenu.length>0){
		if(currentMenu.parent().is("h3")){
			btnMenu = "<strong>杂志分类</strong>:"+currentMenu.html()+"<span></span>";
		}else{
			var rootA = currentMenu.parents(".item").find("h3>a");
			rootA.addClass("current");
			btnMenu = "<strong>杂志分类</strong>:"+rootA.html()+"<span></span>";
		}
	}
	
	btn.html(btnMenu);
	
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
	
	$("#btn").unbind('click').click(function(e){
		e.preventDefault();
		window.location.href = SystemProp.appServerUrl+"/publish/km-family-issue!kanmi2.action";
	});
	//categoryList
	var categoryList = $("#categoryList");
	var category = categoryList.find("a[name='familyName']");
	category.click(function(e){
		e.preventDefault();
		var familyId = $(this).attr("familyId");
		window.location.href = SystemProp.appServerUrl+"/publish/kanmi.action?familyCategoryParentId="+familyId;
	});
	
	kanmiMenu.find("a[categoryId]").bind('click',function(e){
		var categoryId = $(this).attr("categoryId");
		e.preventDefault();
		window.location.href = SystemProp.appServerUrl+"/publish/km-family-issue!kanmi2.action?categoryId="+categoryId;
	});
	
	kanmiMenu.find("a[letter]").bind('click',function(e){
		var letter = $(this).attr("letter");
		e.preventDefault();
		window.location.href = SystemProp.appServerUrl+"/publish/km-family-issue!kanmi2.action?letter="+letter;
	});
	
	//scoll——issue---------------------------------------------------
	var hasMoreIssue = true;
	var scrollTimer = null;
	$(window).scroll(function(){
		var hasOver = hasOverlay();//samplefancybox and lightbox
		if(!scrollTimer && hasMoreIssue && !hasOver){
			scrollTimer = setTimeout(function(){
				var categoryId = getUrlValue("categoryId");
				
				var bodyHeight = $("body").height();
				var windowHeight = $(window).height();
				var scrollHeight = bodyHeight - windowHeight;
				
				var scrollTop = $(window).scrollTop();
				if(scrollTop > (scrollHeight-150)){
					var issueSize = $("#deskShow>.item").find("a.showBar:visible").length;
					$("#loadingTags").fadeIn();
					if(issueSize%7 != 0){
						hasMoreIssue = false;
						return;
					}
					loadIssues(issueSize,14,categoryId);
				}
				scrollTimer = null;
			},800);
		}
	});
	function loadIssues(begin,size,categoryId){
		var data = {};
		if(!categoryId){
			data = {'begin':begin,'size':size};
		}else{
			data = {'begin':begin,'size':size,'categoryId':categoryId};
		}
		$.ajax({
			url : SystemProp.appServerUrl+"/publish/km-family-issue!kmCategoryJson.action",
			type : "POST",
			async : false,
			data : data,
			success : function (rs){
				if(!rs||!(rs.data)||!(rs.data.issueList)){
					hasMoreIssue = false;
					return;
				}
				var issueList = rs.data.issueList;
				if(issueList.length != size){
					hasMoreIssue = false;
				}
				if(issueList.length == 0){
					return;
				}	
				$("#size").val(begin+issueList.length);
				appendIssues(issueList);
				
			}
		});
	}
	function appendIssues(issueList){
		var deskShow = $("#deskShow");
		var length = issueList.length;
		var lastItem = deskShow.find(".item:last");
		var lastNeedIssueNum = 7-lastItem.find(">a").length;
		if(length>0){
			lastItem.removeClass("itemLast");
		}
		//填充最后一排期刊
//		for(var i=0;i<lastNeedIssueNum;i++){
//			var strIssue = issueA(issueList[i]);
//			lastItem.append(strIssue);
//		}
//		if(length<=lastNeedIssueNum){
//			return;
//		}
		//new item
		var itemLength =((length-lastNeedIssueNum)%7== 0) ? (length-lastNeedIssueNum)/7 : Math.floor((length-lastNeedIssueNum)/7 + 1);
		for(var j=0;j<itemLength;j++){
			var item = $("<div class='item'></div>");
			if(j==(itemLength-1)){
				item.addClass("itemLast");
			}
			item.appendTo(deskShow);
			for(var k=0;k<7;k++){
				if((j*7+k)>=length) break;
				var strIssue = issueA(issueList[j*7+k]);
				
				item.append(strIssue);
			}
			item.find("a").hover(
				function(){
					$(this).append("<sub class='history' title='往期杂志'></sub>");
				},function(){
					$(this).find(".history").remove();
				});
		}
		
	}
	function issueA(issue){
		var strIssue = "<a issueId='"+issue.id+"' class='showBar png' href='javascript:void(0)' style='display:inline;' title='"+(issue.pulicationName||"")+issue.issueNumber+"'>";
		var src = SystemProp.magServerUrl +"/"+issue.publicationId+"/110_"+issue.id+".jpg";
		strIssue += "<img name='issueRead' src='"+src+"'/><span name='issueRead'>"+(issue.publicationName||"")+issue.issueNumber+"</span>"+
					"<div class='bookBar'><p><em class='save' title='订阅' name='subscribe' >" + ((issue.subscribeNum==0)?"":issue.subscribeNum) +
					"</em><em class='fav' title='收藏' name='collection' >" + ((issue.favoriteNum==0)?"":issue.favoriteNum )+
					"</em></p></div></a>";
		
		return strIssue;
	}
	
	fnSetFooterHeight();
});