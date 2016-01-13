$(function(){
	var types=[["typeA",3],["typeB",5],["typeC",4],["typeD",4],["typeE",5],["typeF",4],["typeG",4],["typeH",3]];
	var ran = actionRan;
	var arrPage = new Array();
	var typeFirst = types[ran][0];
	var sizeFirst = types[ran][1];
	var pageNo = 1;
	var begin = 0;
	var size = sizeFirst;
	arrPage[pageNo-1] = new Array(); 
	arrPage[pageNo-1][0] = typeFirst;
	arrPage[pageNo-1][1] = begin;
	arrPage[pageNo-1][2] = size;

	$("#bodyEvent").addClass(typeFirst);

	$("a[eventId]").live('click',function(e){
		e.preventDefault();
		var eventId = $(this).attr("eventId");
		var backUrl = window.location.href;
		window.location.href=SystemProp.appServerUrl+"/widget/qplus-read.action?eventId="+eventId+"&backPageNo="+pageNo+"&backUrl="+encodeURIComponent(backUrl);
	});

	$(".pre").unbind('click').live('click',function(e){
		if(pageNo<=1){
			return;
		}
		pageNo = pageNo-1;
		begin = arrPage[pageNo-1][1];
		size =  arrPage[pageNo-1][2];
		gotoPage(pageNo);
	});
	
	$(".next").unbind('click').live('click',function(e){
		if(begin+size>=totalSize){
			return;
		}
		pageNo = pageNo+1;

		ran = Math.floor(Math.random()*8);
		typeFirst = types[ran][0];
		sizeFirst = types[ran][1];
		begin = begin+size;
		size = sizeFirst;
		
		arrPage[pageNo-1] = new Array();
		arrPage[pageNo-1][0] = typeFirst;
		arrPage[pageNo-1][1] = begin;
		arrPage[pageNo-1][2] = size;

		gotoPage(pageNo);
	});
	
	function gotoPage(pageNo){
		$("#bodyEvent").empty();

		var sortId = "";
		if(!!getUrlValue("sortId")&&getUrlValue("sortId")!=""){
			sortId = getUrlValue("sortId");
		}
	
		var data = {};
		data.begin = begin;
		data.size = size;
		if(!!sortId){
			data.sortId = sortId;
		}
		
		var ajaxUrl = SystemProp.appServerUrl+"/widget/widget!newEventAjax.action";
		
		$.ajax({
			url : ajaxUrl,
			type : "GET",
			async : false,
			data : data,
			success : function (rs){
				if(!rs){
					return;
				}
				
				if(rs.code == 200 && !!rs.data && !!rs.data.eventList && rs.data.eventList.length>0){
					var eventList = rs.data.eventList;
					var itemStr = '<div class="item L1">'+
					'<a eventId="${id}" class="inner" href="javascript:void(0)" name="reader">'+
					' <h2>${title}</h2>'+
					'<div class="img size${eventClass}" style="background-image:url(${SystemProp.fpageServerUrl}/event/${imgFile})">'+
					'	<img src="${SystemProp.fpageServerUrl}/event/${imgFile}" />'+
					'</div>'+
					'<div class="con png">'+
					'	<p>${description}</p>'+
					'</div>'+
					'</a></div>';
					var $event = $.tmpl(itemStr,eventList);
					$event.appendTo($("#bodyEvent"));
					var i =0 ;
					$(".L1").each(function(){
						i++;
						$(this).removeClass("L1");
						$(this).addClass("L"+i);
					});
					$("#bodyEvent").removeClass();
					$("#bodyEvent").addClass("event");
					$("#bodyEvent").addClass(arrPage[pageNo-1][0]);
					$.autoLayout(".item");
				}else{
				}
			}
		});
	}
});