$(document).ready(function(){
	
	//$('#tagWall').hide();
	$(".body .bodyQiemi .loading").fadeOut(300);
	$('#tagWall').fadeIn(500).masonry({isFitWidth: false,itemSelector: '.item',isAnimated: false});
		
	var itemLength = $('#tagWall').find(".item").length;
	if(itemLength < 20){
		$("#loadMore").addClass("hide");
	}
	
	//loadPic--------------------------------------------------------------
	var loadPicFun = function(){
		var status = $(".subNav .current").attr("name") || 'now';
		var ajaxAction ;
		switch(status){
			case 'hot':
				ajaxAction = SystemProp.appServerUrl + "/user-image/hotAjax.html";
				break;
			case 'commend':
				ajaxAction = SystemProp.appServerUrl + "/user-image/commendAjax.html";
				break;
			default :	
				ajaxAction = SystemProp.appServerUrl + "/user-image/nowAjax.html";
				break;
		}
		
		var data = {};
		var tagName = getUrlValue("tagName");
		if(!!tagName){
			data.tagName = tagName;
		}
		data.begin = $("#tagWall").find(".item:visible").length;
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
				var arrayItems = rs.replace(/\"item\"/g,"\"item\" style=\"visibility:hidden;\" ").split("|");
				arrayItems.pop();
				var length = arrayItems.length;
				for(var i=0;i<length;i++){
					var strItem = arrayItems[i];
					var $item = $(strItem+'');
					$('#tagWall').append( $item ).masonry( 'appended', $item );
					$item.css({visibility:"visible",display:"none"});
				}
				$('#tagWall').find(".item:hidden").fadeIn(500);
			}
		});
	};
	//window_scroll------------------------------------------------------------
	scrollLoadData(loadPicFun,600,7);
	//click_load_data
	$("#loadMore").click(function(e){
		e.preventDefault();
		if(hasData){
			loadPicFun();
		}
	});
});