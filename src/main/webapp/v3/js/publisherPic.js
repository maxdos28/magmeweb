;$(document).ready(function(){
	var $kanmiWall = $('#kanmiWall');
	$kanmiWall.masonry({isFitWidth: false,itemSelector: '.item',isAnimated: false});
	
	
	//loadPic--------------------------------------------------------------
	var loadPicFun = function(){
		var ajaxAction =SystemProp.appServerUrl + "/publish/publisher-pic!picJson.action";
		var data = {};
		var publisherId = getUrlValue("publisherId");
		if(!!publisherId){
			data.publisherId = publisherId;
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
					return;
				}
				if(rs.code == 200 && !!rs.data && !!rs.data.userImageList){
					var tagList = rs.data.userImageList;
					var tagStr = '<div class="item"><a class="photo" style="${height*200/width+22}px;" href="${SystemProp.appServerUrl}/user-image!show.action?imageId=${id}">'+
						'<img height="${height*200/width}px" src="${SystemProp.tagServerUrl+path200}" /><h5>${description}</h5></a>'+
						'<div class="tools"><em favTypeId="pic_${id}" class="iconHeart favCurrent">${enjoyNum}</em>'+
						'<em shareTypeId="pic_${id}" class="iconShare">分享</em></div><div class="info"><a class="user" href="${SystemProp.appServerUrl}/user-visit!index.action?userId=${userId}">'+
						'<img src="{{if userAvatar30}}${SystemProp.profileServerUrl+userAvatar30}{{else}}${SystemProp.staticServerUrl}/images/head30.gif{{/if}}" /></a>'+
						'<p>由<a href="${SystemProp.appServerUrl}/user-visit!index.action?userId=${userId}">${userNickName||""}</a>'+
		            	'创建，出自<a href="${SystemProp.appServerUrl}/publish/mag-read.action?id=${issueId}">[${publicationName||""}]</a></p>'+
		            	'</div></div>';
					var $tag = $.tmpl(tagStr,tagList);
					$tag.appendTo($kanmiWall);
					$kanmiWall.masonry('appended', $tag);
				}else{
					hasData = false;
				}
			}
		});
	};
	//window_scroll------------------------------------------------------------
	scrollLoadData(loadPicFun,600);
});