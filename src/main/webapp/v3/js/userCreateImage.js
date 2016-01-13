$(function(){
	var $kanmiWall = $('#kanmiWall');
	$kanmiWall.masonry({isFitWidth: false,itemSelector: '.item',isAnimated: false});
	//lazyload------------------------------------
	$('#kanmiWall .photo img').lazyload({
		placeholder: "images/spacer.gif",
		effect: "fadeIn"
	});
	
	var data = {};
	var tagName = getUrlValue("tagName");
	var userId = getUrlValue("userId");
	if(!!tagName){
		data.tagName = tagName;
	}
	var ajaxUrl = SystemProp.appServerUrl + 
		(($("#isVisitor").val() == "1") ? "/user-visit!userImageJson.action" 
			: "/user-center!userImageJson.action");
	if($("#isVisitor").val() == "1" && !!userId){
		data.userId = userId;
	}
	//滚动加载
	var scrollFun = function (){
		$("#loading").show();
		var begin = $kanmiWall.find("div.item:not(.itemUser)").length;
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
				}
			}
		});
		$("#loading").fadeOut(5000);
	};
	scrollLoadData(scrollFun,600);
	
});