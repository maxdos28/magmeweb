$(document).ready(function(){
	var itemLength = $('#tagWall').find(".item").length;
	if(itemLength < 20){
		$("#loadMore").addClass("hide");
	}
	$('#tagWall').masonry({itemSelector: '.item'});
	
	//loadPic--------------------------------------------------------------
	var loadPicFun = function(){

		var status = $("ul.clearFix .current").attr("name") || 'fav';
		var ajaxAction ;
		switch(status){
			case 'fav':
				ajaxAction = SystemProp.appServerUrl + "/user-center!enjoyImageAjax.action";
				break;
			case 'mgz':
				ajaxAction = SystemProp.appServerUrl + "/user-center!enjoyIssueAjax.action";
				break;
			case 'evt':
				ajaxAction = SystemProp.appServerUrl + "/user-center!enjoyEventAjax.action";
				break;
			case 'pic':
				ajaxAction = SystemProp.appServerUrl + "/user-center!userImageAjax.action";
				break;
			case 'msg':
				ajaxAction = SystemProp.appServerUrl + "/user-center!message.action";
				break;
			default :	
				ajaxAction = SystemProp.appServerUrl + "/user-center!enjoyImageAjax.action";
				break;
		}
		
		var data = {};
		//var tagName = getUrlValue("tagName");
		var tagName = $("ul.listTag > li > a.current").text();
		if(!!tagName){
			data.tagName = tagName;
		}
		data.begin = $("#tagWall").find(".item:visible").length;
		data.size = 20;
		//alert(ajaxAction+"?tagName="+tagName+"&begin="+data.begin+"&size="+data.size);
		$.ajax({
			url : ajaxAction,
			type : "GET",
			async : false,
			data : data,
			success : function (rs){
				if(!rs){
					hasData = false;
					return;
				}
				$("#loadMore").before(rs);
				setTimeout(function(){$("#tagWall").masonry('reload');},1000);
			}
		});
	};
	//window_scroll------------------------------------------------------------
	scrollLoadData(loadPicFun,100,7);
	//click_load_data
	$("#loadMore").click(function(e){
		e.preventDefault();
		if(hasData){
			loadPicFun();
		}
	});	
	
	$("span[name='deleteUserImage']").unbind().live("click",function(){
		var obj=$(this);
		$.ajax({
			url : SystemProp.appServerUrl+"/user-image!deleteJson.action",
			type : "POST",
			async : false,
			data : {"id":obj.attr("userImageId")},
			success : function (rs){
				if(rs.code == 400){
					gotoLogin(rs.message);
				}else if(rs.code==200){
					alert("删除成功",function(){
						window.location.reload();					
					})					
				}else{
					alert(rs.message);
				}
			}
		});
	});
});