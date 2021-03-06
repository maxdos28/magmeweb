;$(document).ready(function(){
	var $eventList = $("#eventList");
	
	//图片lazyload
	$eventList.find('.item img').lazyload({
		placeholder: SystemProp.staticServerUrl+"/v3/images/spacer.gif",
		effect: "fadeIn"
	});
	$eventList.masonry({isFitWidth: false,itemSelector: '.item',isAnimated: false});
	
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
						'<img src="${SystemProp.fpageServerUrl}/event${imgFile}">'+
						'<span>${title||""}</span></a></div>';
					var $event = $.tmpl(itemStr,eventList);
					$event.appendTo($eventList);
					$event.find("img").lazyload({
						placeholder: SystemProp.staticServerUrl+"/v3/images/spacer.gif",
						effect: "fadeIn"
					});
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