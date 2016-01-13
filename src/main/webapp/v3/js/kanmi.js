;$(document).ready(function(){
	var $kanmiWall = $('#kanmiWall');
	$kanmiWall.masonry({isFitWidth: false,itemSelector: '.item',isAnimated: false});
	
	//loading- scrolling
	if($("body").height() < $(window).height()){
		hasData = false;
		$("#loadMore").fadeOut(500);
	}
	//图片lazyload
	$('#kanmiWall .photo img').lazyload({
		placeholder: SystemProp.staticServerUrl+"/v3/images/spacer.gif",
		effect: "fadeIn"
	});
	//loadPic--------------------------------------------------------------
	var loadPicFun = function(){
//		$("#loadMore").show();
		var status = $("#tabList").find("a.current").attr("name") || 'commend';
		var ajaxAction ;
		switch(status){
			case 'hot':
				ajaxAction = SystemProp.appServerUrl + "/user-image/hotJson.html";
				break;
			case 'commend':
				ajaxAction = SystemProp.appServerUrl + "/user-image/commendJson.html";
				break;
			default :	
				ajaxAction = SystemProp.appServerUrl + "/user-image/nowJson.html";
				break;
		}
		
		var data = {};
		var tagName = getUrlValue("tagName");
		if(!!tagName){
			data.tagName = tagName;
		}
		data.begin = $kanmiWall.find(".item:visible").length;
		data.size = 20;
		
		$.ajax({
			url : ajaxAction,
			type : "POST",
			async : false,
			data : data,
			success : function (rs){
				if(!rs){
					hasData = false;
					$("#loadMore").fadeOut(500);
					return;
				}
				if(rs.code == 200 && !!rs.data && !!rs.data.userImageList && rs.data.userImageList.length>0){
					var tagList = rs.data.userImageList;
					var tagStr = '<div class="item"><a class="photo" style="${height*200/width+22}px;" href="${SystemProp.appServerUrl}/user-image!show.action?'+(!!tagName ? ('tageName='+tagName+'&') : '')+'imageId=${id}">'+
						'<img height="${height*200/width}px" src="${SystemProp.tagServerUrl+path200}" /><h5>${description}</h5></a>'+
						'<div class="tools"><em favTypeId="pic_${id}" class="iconHeart favCurrent">${enjoyNum}</em>'+
						'<em class="iconShare" shareTypeId="pic_${id}">分享</em></div><div class="info"><a class="user" href="${SystemProp.appServerUrl}/user-visit!index.action?userId=${userId}">'+
						'<img src="{{if userAvatar30}}${SystemProp.profileServerUrl+userAvatar30}{{else}}${SystemProp.staticServerUrl}/images/head30.gif{{/if}}" /></a>'+
						'<p>由<a href="${SystemProp.appServerUrl}/user-visit!index.action?userId=${userId}">${userNickName||""}</a>'+
		            	'创建，出自<a href="${SystemProp.appServerUrl}/publish/mag-read.action?id=${issueId}">[&nbsp;${publicationName||""}&nbsp;]</a></p>'+
		            	'</div></div>';
					var $tag = $.tmpl(tagStr,tagList);
					$tag.appendTo($kanmiWall);
					$kanmiWall.masonry('appended', $tag);
				}else{
					hasData = false;
					$("#loadMore").fadeOut(500);
				}
			}
		});
	};
	//window_scroll------------------------------------------------------------
	scrollLoadData(loadPicFun,600);
});