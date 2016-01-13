;$(document).ready(function(){
	var $eventList = $("#eventList");
	
	//图片lazyload
	$eventList.find('.img img').lazyload({
		placeholder: SystemProp.staticServerUrl+"/v3/images/spacer.gif",
		effect: "fadeIn"
	});
	
	var viewType = getCookie("viewType");
	var $typeBtn = $("#viewTypeBtn");
	if(!viewType){
		$("#eventList").addClass("homeList");
	}else if(!!viewType && viewType === 'list'){
		$typeBtn.attr("class","list");
		$("#eventList").removeClass("homeGrid").addClass("homeList");
	}else if(viewType === 'grid'){
		$typeBtn.attr("class","grid");
		$("#eventList").removeClass("homeList").addClass("homeGrid");
	}
	$eventList.masonry({isFitWidth: false,itemSelector: '.item',isAnimated: false});
	
	$typeBtn.click(function(e){
		e.preventDefault();
		var $obj = $(this);
		var type = $obj.attr("class") || "list";
		$("#eventList").toggleClass("homeList homeGrid");
		if(type === "list"){
			$obj.attr("class","grid");
			setCookie("viewType","grid",new Date("December 31,2120"));
			window.scrollTo(0,0);
		}else{
			$obj.attr("class","list");
			setCookie("viewType","list",new Date("December 31,2120"));
			window.scrollTo(0,0);
		}
		$eventList.masonry('reload');
	});
	window.eventBegin = $("#eventBegin").val()*1;
	
	var scrollFun = function (){
		var sortId = getUrlValue("sortId") || "";
		var data = {"begin":window.eventBegin};
		if(!!sortId){
			data.sortId = sortId;
		}
		$.ajax({
			url : SystemProp.appServerUrl + "/mobile/mobile-index!getEventJson.action",
			type : "POST",
			async : false,
			data : data,
			success : function (rs){
				if(!rs){
					hasData = false;
					return;
				}
				if(rs.code == 200 && !!rs.data && rs.data.fpageEventList.length>0){
					window.eventBegin = rs.data.eventBegin; 
					
					var eventList = rs.data.fpageEventList;
					var itemStr = '<div class="item size${eventClass}">'+
						'<a href="${SystemProp.appServerUrl}/mobile/mobile-read.action?eventId=${id}&pageNo=${pageNo}&sortId='+sortId+'">'+
						'<div class="img"><span><img src="${SystemProp.fpageServerUrl}/event/${imgFile}"></span></div>'+
						'<p><strong>${title||""}</strong><span>${description}</span></p></a></div>';
					var $event = $.tmpl(itemStr,eventList);
					$event.find(".img img").lazyload({
						placeholder: SystemProp.staticServerUrl+"/v3/images/spacer.gif",
						effect: "fadeIn"
					});
					$event.appendTo($eventList);
					$eventList.masonry('appended', $event);
				}else{
					hasData = false;
					$("#pageLoad").fadeOut(1000);
				}
			}
		});
	}
	
	scrollLoadData(scrollFun,600);
	
	
}); 