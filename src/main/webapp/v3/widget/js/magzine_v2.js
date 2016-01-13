$(function(){
	//杂志图片的绑定
	$("a[issueId]").find("[name='issueRead']").live('click',function(e){
		e.preventDefault();
		var xx = getUrlValue("xx");
		var issueA = $(this).parent("a").eq(0);
		var issueId = issueA.attr("issueId");
		var backUrl = window.location.href;
		window.location.href=SystemProp.appServerUrl+"/widget/widget-read.action?id="+issueId+"&xx="+xx+"&backPageNo="+pageNo+"&backUrl="+encodeURIComponent(backUrl);
	});
	
	//杂志名称的绑定
	$("a[issueId]").find("[pubName]").live('click',function(e){
		e.preventDefault();
		e.stopPropagation();
		var pubName = $(this).attr("pubName");
		window.location.href="/widget/widget-search.action?queryStr="+encodeURIComponent(pubName);
	});	
	
//	var issues = {
//				<#if magPageInfo?? && magPageInfo.data?? && magPageInfo.data?size gt 0>
//					<#list magPageInfo.data as issue>
//					"${issue_index}": {id: ${issue.id}, publicationId: ${issue.publicationId}, publicationName: "${issue.publicationName?js_string}",issueNumber: "${issue.issueNumber?js_string}"}, 
//					</#list>
//				</#if>
//				foo : null
//			};
	
	
	//control.click
	$("a[page]").unbind('click').live('click',function(e){
		//$("a.current[page]").removeClass("current");
		//$(this).addClass("current");
		pageNo = parseInt($(this).attr("page"));
		gotoPage(pageNo);
	});
	
	function gotoPage(pageNo){
		$("#bodyMagazine").empty();
		
		var begin = (pageNo-1)*15;
		var size = 15;
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
		var ajaxUrl = SystemProp.appServerUrl+"/widget/widget!magzineAjax.action";
		
		$.ajax({
			url : ajaxUrl,
			type : "GET",
			async : false,
			data : data,
			success : function (rs){
				if(!rs){
					return;
				}
				
				if(rs.code == 200 && !!rs.data && !!rs.data.magList && rs.data.magList.length>0){
					var magList = rs.data.magList;
					var itemStr = '<div class="item">'+
			    	'<a issueId="${id}" class="photo" href="javascript:void(0)" name="reader">'+
			    	'<img name="issueRead" src="${SystemProp.magServerUrl}/${publicationId}/200_${id}.jpg" />'+
			        '<h5 title="${publicationName}的往期期刊" pubName="${publicationName}">'+
			        '[ ${publicationName}${issueNumber} ]'+
			        '</h5></a></div>';
					var $mag = $.tmpl(itemStr,magList);
					$mag.appendTo($("#bodyMagazine"));
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
 		pageNo = curPage;
	}

	if(!!getUrlValue("curPage")&&parseInt(getUrlValue("curPage"))>1){
		var curPage = parseInt(getUrlValue("curPage"));
		gotoPage(curPage);
	}
	
});	