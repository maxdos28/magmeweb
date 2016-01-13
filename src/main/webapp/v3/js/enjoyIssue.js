$(function(){
	$("em[publisherId]").live('click',function(e){
		e.preventDefault();
		var publisherId=$(this).attr("publisherId");
		var publicationId=$(this).attr("publicationId")||0;
		var issueId=$(this).attr("issueId")||0;
		window.location.href = SystemProp.appServerUrl+"/publish/publication-home!mag.action?publicationId="+publicationId+"&issueId="+issueId;
	});
	//lazyload------------------------------------
	$('#userCenterWall .photo img').lazyload({
		placeholder: "images/spacer.gif",
		effect: "fadeIn"
	});
	
	var data = {};
	var tagName = getUrlValue("tagName") || "";
	var userId = getUrlValue("userId") || "";
	if(!!tagName){
		data.tagName = tagName;
	}
	var ajaxUrl = SystemProp.appServerUrl + 
		(($("#isVisitor").val() == "1") ? "/user-visit!enjoyIssueJson.action" 
			: "/user-center!enjoyIssueJson.action");
	if($("#isVisitor").val() == "1" && !!userId){
		data.userId = userId;
	}
	//loading- scrolling
	if($("body").height() < $(window).height()){
		hasData = false;
		$("#loading").fadeOut(500);
	}
	//滚动加载
	var $userCenterWall = $("#userCenterWall");
	var scrollFun = function (){
//		$("#loading").show();
		var begin = $userCenterWall.find("div.item:not(.itemUser)").length;
		data.size = 15;
		data.begin = begin;
		$.ajax({
			url : ajaxUrl,
			type : "POST",
			async : false,
			data : data,
			success : function (rs){
				if(!rs){
					hasData = false;
					return;
				}
				if(rs.code == 200 && !!rs.data && !!rs.data.issueList && rs.data.issueList.length>0){
					var issueList = rs.data.issueList;
					var issueStr = '<div class="item" issueId="${id}" publisherId="${publisherId}" publicationId="${publicationId}">'+
							'<a class="photo" href="${SystemProp.appServerUrl}/publish/mag-read.action?id=${id}">'+
							'<img src="${SystemProp.magServerUrl}/${publicationId}/200_${id}.jpg" />'+
							'<h5>[ ${publicationName} ]<span>${dateFormat(publishDate)}</span></h5><sup class="album"></sup></a>'+
							'<div class="tools"><em favTypeId="mag_${id}" class="iconHeart">${enjoyNum}</em>'+
							'<em class="iconPublisher" publisherId="${publisherId}">出版商</em></div></div>';
					$.tmpl(issueStr,issueList).appendTo($userCenterWall);
				}else{
					hasData = false;
					$("#loading").fadeOut(500);
				}
			}
		});
	};
	scrollLoadData(scrollFun,600);
	
});