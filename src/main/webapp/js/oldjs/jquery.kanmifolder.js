;(function ($){
jQuery.kanmifolder = function() {
var $folderInner;
var $folderItem;
var $id;
var $itemParent;
var type;
var folderOpen = 0;
var lock = 0;
var timeS = 500;
var timeH = 250;
var timeZ = 0;
var marginBottom = 40;
var openId = -1;
var openId1 = -1;
var publicationName = "";
if($(".kanmiCon").length != 0){
	$id = $(".kanmiCon .smallShow .magezineBox>a");
	$itemLast = $(".kanmiCon").last();
	type = 1;
}else if($(".deskShow").length != 0){
	$id = $(".deskShow .item a.showBar");
	type = 2;
	if( $(".deskShow .item").length > 2){
		$itemLast = $(".deskShow .item").last();
	}else{
		$itemLast = 0		
	}
}
var folderHtml = "";



//鼠标移入增加往期按钮
$id.hover(
function(){
	$(this).append("<sub class='history' title='往期杂志'></sub>");
},function(){
	$(this).find(".history").remove();
});


$id.find(".history").live("click",function(){
	if(lock==0){
		lock=1;
		//设置smallShow超出显示，否则不影响箭头
		$(".kanmiCon .smallShow").css({overflow:"visible"});
		var obj = $(this).parent();//定义当前杂志
		 //定义与folder同级的父级
		if(type==1){
			var objParent = $(this).closest(".kanmiCon");
		}else{
			var objParent = $(this).parents(".item");
		}
		
		//处理最后一行
		$.kanmifolder.fnSetLast(obj,objParent);
		//判断是否已打开 1open
		if(folderOpen==1){
			//当前按钮
			if(obj.find(".arrow").length==1){
				$.kanmifolder.fnfolderHide();
				setTimeout(function(){fnbookShow(obj)},timeS+timeH-250);
				setTimeout(function(){lock=0},timeS+timeH);
			}else
			//非当前按钮
			{	
				//是否为不同行，或kanmi1同行中的不同组
				if(objParent.index()!=openId || (type==1 && obj.parent().index()!=openId1) ){
					fnOpenId(obj,objParent);
					$.kanmifolder.fnfolderHide();
					setTimeout(function(){fnfolderShow(obj,objParent)},timeH);
					setTimeout(function(){fnbookShow(obj)},timeS+timeH-250);
					setTimeout(function(){lock=0},timeS+timeH);
				}else{
					objParent.find("span").css({textIndent:0});
					$(".arrow").remove();
					obj.append("<div class='arrow'></div>").find("span").css({textIndent:-9999});
					fnbookShow(obj);
					lock=0;
				}
				folderOpen=1;
				
			}
		}else{
			fnOpenId(obj,objParent);
			folderOpen = 1;
			$id.find("span").css({textIndent:0}).end().find(".arrow").remove();
			fnfolderShow(obj,objParent);
			setTimeout(function(){fnbookShow(obj)},timeS-250);
			setTimeout(function(){lock=0},timeS);
		}
		
		//判断最后一个添加底边距
		if((objParent.hasClass("lastOn") && obj.parent().hasClass("magezineBoxLast")) || (objParent.hasClass("kanmiConLast") && obj.parent().hasClass("magezineBoxLast")) || objParent.hasClass("lastOn")){
			setTimeout(function(){$(".folder").css({marginBottom:marginBottom})},timeH);
		}else{
			setTimeout(function(){$(".folder").animate({marginBottom:0},200)},timeH);
		}
	}
	
	return false;
});

getOldIssues = function(issuse){
	publicationName = issuse.data("publicationName")||"";
	var oldIssues = issuse.data("oldIssues");
	var issueId = issuse.attr("issueId");
	var issues = "";
	if(!oldIssues){
		$.ajax({
			url : SystemProp.appServerUrl+"/publish/kanmi!reviewIssuesJson.action",
			type : "POST",
			async : false,
			data : {'issueId':issueId},
			success: function(rs){
				if(!rs)return;
				var issueList = rs.data.issueList;
				for(var i=0;i<issueList.length;i++){
					var issue = issueList[i];
					issues += "<a class='showBar' href='javascript:void(0)' issueid='"+issue.id+"' title='"+issue.issueNumber+"' >";
					var src = SystemProp.magServerUrl +"/"+issue.publicationId+"/110_"+issue.id+".jpg";
					issues += "<img src='"+src+"' name='issueRead' /><span name='issueRead'>"+issue.issueNumber+"</span>"+
						"<div class='bookBar'><p><em class='save' title='订阅' name='subscribe' >"+(issue.subscribeNum||"")+"</em><em class='fav' title='收藏' name='collection' >"+(issue.favoriteNum||"")+"</em></p></div></a>";
				}
				publicationName = issueList[0].publicationName||"";
				issuse.data("publicationName",publicationName);
				issuse.data("oldIssues",issues);
			}
		});
	}else{
		issues = oldIssues;
	}
	return issues;
}
fnfolderShow = function(obj,objParent){
	folderHtml = "<div class='folder'><h2></h2><div class='outer scroll-pane'><div class='inner'>";
	folderHtml += "</div></div></div>";
	//判断kanmi1页第一行顶部展开
	obj.append("<div class='arrow'></div>").find("span").css({textIndent:-9999});
	if(type==1 && obj.parent().index()==0){
		objParent.before(folderHtml);
		$(".folder").css({marginTop:20});
	}else{
		objParent.after(folderHtml);
	}
	
	$folderInner = $(".folder .inner");
	$folderItem = $(".folder .inner>a");
	$(".folder").css({opacity:0}).animate({height:230,opacity:1},timeS);
}

$.kanmifolder.fnfolderHide = function(){
	$(".kanmiCon .smallShow .magezineBox>a,.deskShow .item a").find("span").css({textIndent:0});
	$(".arrow").remove();
	$(".folder").animate({height:0,opacity:0},timeH);
	setTimeout(function(){$(".folder").remove()},timeH);
	folderOpen=0;
}

$.kanmifolder.fnSetLast = function(obj,objParent){
	if(type==1){
		if(((objParent.hasClass("kanmiConLast") && obj.parent().hasClass("magezineBoxLast")) || (objParent.hasClass("lastOn") && obj.parent().hasClass("magezineBoxLast"))) && obj.find(".arrow").length!=1){
			objParent.attr("class","kanmiCon kc3 lastOn")
		}else{
			$itemLast.attr("class","kanmiCon kc3 kanmiConLast");
		}
	}else if(type==2){
		if((objParent.hasClass("itemLast") || objParent.hasClass("lastOn")) && obj.find(".arrow").length!=1){
			objParent.attr("class","item lastOn");
		}else{
			if($itemLast != 0){
				setTimeout(function(){$itemLast.attr("class","item itemLast")},timeH);
			}
		}
	}
}


fnbookShow = function(obj){
	$folderInner.empty();
	//append item
	$folderInner.append(getOldIssues(obj));
	//添加程序代码
	$folderInner.width($(".folder .inner>a").length*140);
	$('.scroll-pane').jScrollPane();
	$('.jspPane').css("left",0);
	$(".folder>h2").html(publicationName);
	$folderInner.find(">a").fadeIn(500);
}

fnOpenId = function(obj,objParent){
	//判断之前是否有folder已加载，定义index值
	if(objParent.prevAll(".folder").length>0){
		openId = objParent.index()-1;
	}else{
		openId = objParent.index();
	}
	if(type==1){
		openId1 = obj.parent().index();
		if(openId1==0){
			openId+=1;
		}
	}
}


}; 
})(jQuery); 