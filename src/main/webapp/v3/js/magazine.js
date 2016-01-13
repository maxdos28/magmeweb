;$(document).ready(function(){
	var sortCurrentValue = getUrlValue("sortId");
	if(sortCurrentValue){
		$("a[sortid="+sortCurrentValue+"]").parent().addClass("current");
	}
	
	//具体下拉时的分类数据填充
	$("li[name='magzineHeaderLi']").find("em").live("click",function(){
		var obj = $(this);
		var sortid = $("a", obj.parent()).attr("sortid");
		var data = {"sortId":sortid};
		var appserverurl="http://"+(document.location+"").split("/")[2];
		var callback= function(rs){
			if(!rs){
				currentCategoryData="出错了!";
			}
			if(rs.code==200){
				var html = "";
				html += "<ul class='clearFix'>"
				$.each(rs.data.publicationList,function(k,p){
					html += "<li><dd><a href='javascript:void(0);' enName='"+p.englishname+"' dmName='"+p.domain+"'>"+p.name+"</a></dd></li>";
				});
				html += "</ul>";
				currentCategoryData=html;
			}else{
				currentCategoryData="出错了!";
			}
		}
		$.ajax({
			url : appserverurl+"/publish/magazine!publicationBySortJson.action",
			type: "GET",
			async : false,
			dataType: "json",
			data: data,
			success: callback
		});	
	});

	//订阅到
	$(".rssTo").mouseenter(function(){
		$(this).addClass("hover").find("p").stop(true,true).fadeIn(200);
	}).mouseleave(function(){
		$(this).removeClass("hover").find("p").fadeOut(200);
	});
	
	$("dd a[enName]").live("click",function(e){
		e.preventDefault();
		var obj =$(this);
		var enName=obj.attr("enName");
		var dmName=obj.attr("dmName");
		window.location.href = "http://"+dmName+".magme.com/"+enName+"/";
	});
	
	$("dt a[sortId]").click(function(e){
		e.preventDefault();
		var sortId=$(this).attr("sortId");
		var type = getUrlValue("type") || 1;
		window.location.href = SystemProp.appServerUrl+"/publish/magazine.html?sortId="+sortId+"&type="+type;
	});
	
	$("dd a[sortId]").click(function(e){
		e.preventDefault();
		var sortId=$(this).attr("sortId");
		var type = getUrlValue("type") || 1;
		window.location.href = SystemProp.appServerUrl+"/publish/magazine.html?sortId="+sortId+"&type="+type;
	});
	
//	$("em[publisherId]").live("click",function(e){
//		e.preventDefault();
//		var publisherId=$(this).attr("publisherId");
////		var publicationId=$(this).attr("publicationId")||0;
////		var issueId=$(this).attr("issueId")||0;
////		window.location.href = "http://"+publicationId+".magme.com/index.html";
//		var englishName=$(this).attr("englishName")||"";
//		window.location.href = "http://"+englishName+".magme.com";
//	});
	
	$('#magazineWall .photo img').lazyload({
		placeholder: SystemProp.staticServerUrl+"/v3/images/spacer.gif",
		effect: "fadeIn"
	});
	if($("body").height() < $(window).height()){
		hasData = false;
		$("#loading").fadeOut(500);
	}
	//滚动加载
	var magazinebegin=20;
	$magazineWall = $("#magazineWall");
	if($("#isNeedScroll").val()*1 == 1){
		var type = getUrlValue("type") || 1;
		var sortId = getUrlValue("sortId") || "";
		var scrollFun = function (){
//			$("#loading").show();
			var begin = $magazineWall.find("div.item").length;
			var domain=(document.location+"").split("/")[2];
			var appserverurl="http://"+domain;
			var sortname=domain.split(".")[0];
			var data = {"sortId":sortId,"type":type,"size":15,"begin":magazinebegin,"sortName":sortname};
			$.ajax({
				url : appserverurl+"/publish/magazine!magJson.action",
				type : "GET",
				async : false,
				data : data,
				success : function (rs){
					if(!rs){
						hasData = false;
						return;
					}
					if(rs.code == 200 && !!rs.data && !!rs.data.issueList && rs.data.issueList.length>0){
						magazinebegin+=15;
						var issueList = rs.data.issueList;
						var issueStr = '<div class="item" issueId="${id}" publisherId="${publisherId}" publicationId="${publicationId}">'+
								'<a target="_bank" class="photo" href="javascript:void(0)" issueId="${id}">'+
								'<img src="${SystemProp.magServerUrl}/${publicationId}/200_${id}.jpg" />'+
								'<h5>[ ${publicationName} ]<span>${dateFormat(publishDate)}</span></h5><sup publicationId="${publicationId}" issueId="${id}" class="album" title="${publicationName}中的全部事件" ></sup></a>'+
								'<div class="tools"><em favTypeId="mag_${id}" class="iconHeart">${enjoyNum}</em>'+
								'<em class="iconPublisher"><a href="http://${publicationPo.domain}.magme.com/${publicationPo.englishname}/" >${publicationPo.name}</a></em>' +
								'</div></div>';
						$.tmpl(issueStr,issueList).appendTo($magazineWall);
						$.jqueryhoverplay(".item",60,8); 
					}else{
						hasData = false;
						$("#loading").fadeOut(500);
					}
				}
			});
		};
		scrollLoadData(scrollFun,600);
	}
	
	$("a[issueId]").unbind("click").live("click",function(e){
		e.preventDefault();
		var issueId=$(this).attr("issueId");
		var href="http://www.magme.com/publish/mag-read.action?id="+issueId;
		window.location.target="_blank";
		window.open(href);
	});
});