$(function(){
	$(".item .photo img").coverImg();
	$("a[eventId]").live('click',function(e){
		e.preventDefault();
		var eventId = $(this).attr("eventId");
		var backUrl = window.location.href;
		window.location.href=SystemProp.appServerUrl+"/widget/widget-read.action?eventId="+eventId + "&xx=" + xx +"&backPageNo="+pageNo+"&backUrl="+encodeURIComponent(backUrl);
	});

	//control.click
	$("div.control a[page]").unbind('click').live('click',function(e){
		//$("div.control a.current").removeClass("current");
		//$(this).addClass("current");
		pageNo = parseInt($(this).attr("page"));
		gotoPage(pageNo);
	});
	
	function gotoPage(pageNo){
		$("#bodyEvent").empty();
	
		var begin = (pageNo-1)*20;
		var size = 20;
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
					var itemStr = '<div class="item size${eventClass}">'+
			    	'<a eventId="${id}" class="photo" href="javascript:void(0)" name="reader">'+
			    	'<img src="${SystemProp.fpageServerUrl}/event/${imgFile}" />'+
			        '<h5 class="png">${title}</h5>'+
			        '</a></div>';
					var $event = $.tmpl(itemStr,eventList);
					$event.appendTo($("#bodyEvent"));
					$(".item .photo img").coverImg();
					bindHover();
				}else{
				}
			}
		});
			rebuildCtl(pageNo);	
	}
	
	
	$("#turnLeft").unbind('click').live('click',function(e){
		if(pageNo<2){
			return;
		}
		else{
			pageNo = pageNo-1;
			$("a[page="+pageNo+"]").click();
		}
	});
	
	$("#turnRight").unbind('click').live('click',function(e){
		if(pageNo>=totalPage){
			return;
		}
		else{
			pageNo = pageNo+1;
			$("a[page="+pageNo+"]").click();
		}
	});
	
	$("#turnFirst").unbind('click').live('click',function(e){
			rebuildCtl(1);
			$("a[page=1]").click();
	});
	
	$("#turnLast").unbind('click').live('click',function(e){
			rebuildCtl(totalPage);
			$("a[page="+totalPage+"]").click();
	});

	
	function rebuildCtl(curPage){
 		if(totalPage>=10){
			$("div.control a[page]").remove();
			var firstPage = 1;
			var lastPage = totalPage;
			
			if((curPage-4)<=1){
				firstPage = 1;
				lastPage = 9;
			}
			else if((curPage+4)>=totalPage){
				firstPage = totalPage-8;
				lastPage = totalPage;
			}
			else{
				firstPage = curPage-4;
				lastPage = curPage+4;
			}
			var rebuildHtml = "";
			for(var i=firstPage;i<=lastPage;i++){
				rebuildHtml += "<a page=\""+i+"\" href=\"javascript:void(0)\"  >"+i+"</a>";
			}
			$("#turnLeft").after(rebuildHtml);
		}
 		$("a[page]").removeClass("current");
 		$("a[page="+curPage+"]").addClass("current");
	}

	
	if(!!getUrlValue("curPage")&&parseInt(getUrlValue("curPage"))>1){
		var curPage = parseInt(getUrlValue("curPage"));
		gotoPage(curPage);
	}
	
});