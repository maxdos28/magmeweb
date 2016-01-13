;$(document).ready(function(){
	
	var forwardTools = "<div style='display:none'><p><a tagShare='tsina' title='新浪微博' href='#' class='weibo png'></a><a tagShare='tqq' href='#' title='腾讯微博' class='tencent png'></a><a tagShare='kaixin' title='开心网' href='#' class='kaixin png'></a></p></div>";
	$("em.iconShare").unbind("mouseenter").live("mouseenter",function(){
		$(this).append(forwardTools).find("div").fadeIn(200);
	}).unbind("mouseleave").live("mouseleave",function(){
		$(this).find("div").remove();
	});
	
	//top banner
	var topBanner = {
		init : function() {
			$("#topBanner>.inner>.close").bind("click", topBanner.hide);
			topBanner.show();
		},
		show : function() {
			$("#topBanner").show().css({"height" : 0}).animate({"height" : 240},500);
		},
		hide : function() {
			$("#topBanner").animate({"height" : 0},500);
			setTimeout(function(){$("#topBanner").hide()},500);
		}
	}
	//auto hide --begin
	var hideFlag = true;
	topBanner.init();

	var settimeFlag = true;
	timedMsg();
	function timedMsg(){var t=setTimeout(hideDivC,5000);settimeFlag=false;}
	
	function hideDivC(){
		if(hideFlag){
			topBanner.hide();
		}
	}
	//open email
	$("em[name='emailSubscribe']").live("click",function(){
		$("#emailSubscribe").fancybox();
	});
	//save email
	$("#emailSubscribeSubmit").live("click",function(){
		var emailAddress= $("#emailAddress").val();
		if(emailAddress !== '' && emailAddress.indexOf('@') == -1){
			alert("邮箱必须正确填写!");
			return;
		}else{
			var callback=function(rs){
	        if(!rs || rs.code!=200){
	           alert("保存不成功");
	        }else{
	           $.fancybox.close();	
	        }
		    };
		    $.ajax({
		         url:SystemProp.appServerUrl + "/activity/activity-collection-info!nvemailSubscribe.action",
		         data:{"content":emailAddress},
		         type:"POST",
		         success:callback
		    });
			
		}
	});
	
	//open video
	$("#NvADvideoPlayIndex,#NvADvideoPlayIndexImg").click(function(){
	var obj = $(this);
	openDiv(obj.attr("url"));});
	function openDiv(address){
		var urlStr = SystemProp.appServerUrl + "/index-detail!toVideoPlay.action?url="+address;
		$("#videoAdvertiseDialog").html("<iframe id='videoPlayIframe' src='"+urlStr+"' width='600' height='450' frameborder='0' scrolling='no'></iframe>");
		$("#videoAdvertiseDialog").fancybox();
	}
	
	$("a[tagShare]").live('click',function(e){
		e.preventDefault();
		var shareBtn = $(this);
    	var type = shareBtn.attr("tagShare");
    	//tagInfo is window's parameter. Default parameter is in useFunction.js
    	//everyPage want to share in the Internet, you can change the parameter(tagInfo)
    	shareToObj.shareType(type,tagInfo);
	});

	var strDate=function strDate(date){
		var a= (date instanceof Date) ?
				(date.getFullYear()+"-"+ 
						((date.getMonth()+1 < 10) ? ("0"+(date.getMonth()+1)) : (date.getMonth()+1))+"-"+
				 (date.getDate()<10?("0"+date.getDate()) : date.getDate())) : "";
		return a;
	}
	
	$("em[publisherId]").live("click",function(e){
			e.preventDefault();
			var publicationId=$(this).attr("publicationId")||0;
			var issueId=$(this).attr("issueId")||0;
			window.location.href = "http://www.magme.com/publish/publication-home!mag.action?publicationId="+publicationId;
		});
	
	var magazinebegin=15;
	$magazineWall = $("#magazineWall");
	var type = getUrlValue("type") || 1;
	var scrollFun = function (){
//		$("#loading").show();
		var begin = $magazineWall.find("div.item").length;
		var data = {"type":type,"size":15,"begin":begin};
		var success_jsonpCallback=function(rstmp){
			if(!rstmp || !rstmp[0]){
				hasData = false;
				return;
			}
			$("#loadMore").show();
			//alert(rs);
			rs=rstmp[0];
			if(rs.code == 200 && !!rs.data && !!rs.data.issueList && rs.data.issueList.length>0){
				magazinebegin+=15;
				var issueList = rs.data.issueList;
				var issueStr="";
				for(var i=0;i<issueList.length;i++){
					issueStr+='<div class="item" issueId="'+issueList[i].id+'"  publicationId="'+issueList[i].publicationId+'"><a target="_blank" class="photo" href="http://www.magme.com/publish/mag-read.action?id='
					+issueList[i].id+'"><img src="http://static.magme.com/pdfprofile/'
					+issueList[i].publicationId+'/200_'+issueList[i].id+'.jpg" /><h5>[ '
					+issueList[i].publicationName+' ]<span>'
					+strDate(new Date(issueList[i].publishDate.time))
					+'</span></h5></a><div class="tools png">'
					+'<em name="emailSubscribe" class="iconEmail png"> </em><em class="iconShare png"></em></div></div>';
				}
				//var issueStr = '<div class="item" issueId="${id}"  publicationId="${publicationId}">'+
				//		'<a target="_bank" class="photo" href="http://www.magme.com/publish/mag-read.action?id=${id}">'+
				//		'<img src="http://static.magme.com/pdfprofile/${publicationId}/200_${id}.jpg" />'+
				//		'<h5>[ ${publicationName} ]<span>${dateFormat(publishDate)}</span></h5></a>'+
				//		'<div class="tools ">'+
				//		'<em class="iconPublisher" publicationId="${publicationId}" issueId="${id}" publisherId="">出版商</em></div></div>';
				//$.tmpl(issueStr,issueList).appendTo($magazineWall);
				$(issueStr).appendTo($magazineWall);
				$.jqueryhoverplay(".item",60,8); 
			}else{
				hasData = false;
				$("#loadMore").hide();
				//$("#loading").fadeOut(500);
			}
		}
		$.ajax({
			url : "http://www.magme.com/publish/nv-issue!issueJson.action",
			type : "GET",
			async : false,
			data : data,
			dataType : "jsonp",
			jsonp:"callbackparam",
			jsonpCallback:"success_jsonpCallback",
			success : success_jsonpCallback
		});
	};
	scrollLoadData(scrollFun,600);
}
);