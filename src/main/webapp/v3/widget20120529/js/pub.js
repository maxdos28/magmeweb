$(function(){
	qplus.onReady(
		function(){
			qplus.user.getNick(
				function(json){
					var notice = "尊敬的用户“";
					notice += json.nick;
					notice += "”：你好，《";
					notice += lastestIssueNm;
					notice += "》已经在麦米网最新上线，请";
					notice += "<a href='";
					notice += SystemProp.appServerUrl+"/widget/qplus-read.action?xx="+xx+"&id="+lastestIssueId+"&backPageNo=1&backUrl="+encodeURIComponent(window.location.href);
					notice +="'>点击阅读</a>";
					$(".notification").html(notice);
				}
			);

			qplus.on("app.pushParam", function(json){
				if (json.pushParam != '') {
					var issueId = json.pushParam;
					var backUrl = "/widget/qplus.action?xx=tencent&yy=qplus";
					window.location.href = SystemProp.appServerUrl+"/widget/qplus-read.action?xx=tencent&id="+issueId+"&backPageNo=1&backUrl="+encodeURIComponent(backUrl);
				}
			});
			
			qplus.on( "system.shareApp", function(json){
				if (json.shareRet == 1){
					alert('分享成功');
				}
			});
		}
	);

	//杂志图片的绑定
	$("a[issueId]").find("[name='issueRead']").live('click',function(e){
		e.preventDefault();
		var issueA = $(this).parent("a").eq(0);
		var issueId = issueA.attr("issueId");
		var backUrl = window.location.href;
		window.location.href=SystemProp.appServerUrl+"/widget/qplus-read.action?xx="+xx+"&id="+issueId+"&backPageNo="+pageNo+"&backUrl="+encodeURIComponent(backUrl);
	});
	
	$(".pre").unbind('click').live('click',function(e){
		if(pageNo<=1){
			return;
		}
		pageNo = pageNo-1;

		if (srcFlg == "pub" || srcFlg == "event") {
			gotoPage(pageNo,1);
		}else{
			gotoPage(pageNo,0);
		}
	});
	
	$(".next").unbind('click').live('click',function(e){
		if(pageNo>=totalPage){
			return;
		}
		pageNo = pageNo+1;
		
		if (srcFlg == "pub" || srcFlg == "event") {
			gotoPage(pageNo,1);
		}else{
			gotoPage(pageNo,0);
		}
	});
//	//杂志名称的绑定
//	$("a[issueId]").find("[pubName]").live('click',function(e){
//		e.preventDefault();
//		e.stopPropagation();
//		var pubName = $(this).attr("pubName");
//		window.location.href="/widget/widget-search.action?queryStr="+encodeURIComponent(pubName);
//	});	
//	
////	var issues = {
////				<#if magPageInfo?? && magPageInfo.data?? && magPageInfo.data?size gt 0>
////					<#list magPageInfo.data as issue>
////					"${issue_index}": {id: ${issue.id}, publicationId: ${issue.publicationId}, publicationName: "${issue.publicationName?js_string}",issueNumber: "${issue.issueNumber?js_string}"}, 
////					</#list>
////				</#if>
////				foo : null
////			};
//	
	function gotoPage(pageNo,flg){
		$("#bodyMagazine").empty();
		
		var begin = (pageNo-1)*8;
		var size = 8;
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
		var ajaxUrl = SystemProp.appServerUrl;
		if (flg == 0){
			ajaxUrl += "/widget/widget!magzineAjax.action?xx="+xx;
		}else{
			var queryStr = $("input[name=queryStr]").val();
			ajaxUrl += "/widget/qplus!searchAjax.action?xx="+xx+"&queryStr="+encodeURIComponent(queryStr);
		}
		
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
					'<a class="png" issueId="${id}" href="javascript:void(0)" name="reader">'+
					'<img src="${SystemProp.magServerUrl}/${publicationId}/200_${id}.jpg" />'+
					'<p name="issueRead" class="png">'+
					'${publicationName}<span>${issueNumber}</span>'+
					'<em class="iconHeart" pubId="${publicationId}"></em>'+
					'<em class="iconForward" pubName="${publicationName}"></em>'+
					'</p></a></div>';
					var $mag = $.tmpl(itemStr,magList);
					$mag.appendTo($("#bodyMagazine"));
					$.each($(".iconHeart"), function(i,val){  
					    var pubId = $(this).attr("pubId");
						var ajaxAction = SystemProp.appServerUrl + "/widget/qplus!statusAjax.action?xx="+xx;
						
						$.ajax({
							url : ajaxAction,
							type : "POST",
							async : false,
							data : {"publicationId":pubId},
							success : function (rs){
								if(!rs){
									return;
								}
								if(rs.code == 200){
									if (rs.data.status){
										$(".iconHeart").eq(i).addClass("hearCurrent");
									}
								}
							}
						});
					 });
				}else{
				}
			}
		});
	}
	
	$.each($(".iconHeart"), function(i,val){  
	    var pubId = $(this).attr("pubId");
		var ajaxAction = SystemProp.appServerUrl + "/widget/qplus!statusAjax.action?xx="+xx;
		$.ajax({
			url : ajaxAction,
			type : "POST",
			async : false,
			data : {"publicationId":pubId},
			success : function (rs){
				if(!rs){
					return;
				}
				if(rs.code == 200){
					if (rs.data.status){
						$(".iconHeart").eq(i).addClass("hearCurrent");
					}
				}
			}
		});
	 });
 
	//杂志分享
	$(".iconForward").live('click',function(e){
		e.preventDefault();
		e.stopPropagation();
		var pubName = $(this).attr("pubName");
		var pubId =$(this).parent("p").find(".iconHeart").attr("pubId");
		var issueId = $(this).parent("p").parent("a").attr("issueId");

		qplus.system.shareApp({
			msg:"麦米阅读的《"+ pubName+"》不错，来看看吧。",
			title:pubName,
			param: issueId,
			pic: SystemProp.magServerUrl+"/"+pubId+"/200_"+issueId+".jpg",
			shareBtnText: "分享"
		});//这里在应用中调用shareApp
	});
	
	//杂志收藏
	$(".iconHeart").live('click',function(e){
		e.preventDefault();
		e.stopPropagation();
		
		var pubId = $(this).attr("pubId");
		if ($(this).hasClass("hearCurrent")) {
			$(this).removeClass("hearCurrent");
			enjoyChange(pubId,1);
		}else{
			$(this).addClass("hearCurrent");
			enjoyChange(pubId,0);
		}
	});
	
	var enjoyChange = function(pubId,delFlg){
		var ajaxAction = SystemProp.appServerUrl + "/widget/qplus!enjoyChangeAjax.action?xx="+xx;
		
		$.ajax({
			url : ajaxAction,
			type : "POST",
			async : false,
			data : {"publicationId":pubId,"changeFlg":delFlg},
			success : function (rs){
				if(!rs){
					return;
				}
				if(rs.code == 200){
					return;
				}
			}
		});
	};
});	