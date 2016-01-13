var content;
$(function(){
	
	$addEvent = $("#adminEmailNotification");
	content = $("#fancybox-content");
	var dialogClose = $.fancybox.close;
	$.fancybox.close = function(){
		dialogClose();
		if($addEvent) $addEvent.remove();			
	};
	$addEvent.fancybox();
	$("a[name='deleteEmail']", content).unbind().live("click",function(e){
		e.preventDefault();
		var obj=$(this);
		doDelete(obj);
	});	
	
	$("#btn_add").click(function(e){
		doAdd();
	});
});

function doDelete(obj){
	if(!obj) return;
	var mailId = obj.attr("mailId");
	$.ajax({
		url: SystemProp.appServerUrl+"/new-publisher/edit-admin-email!doDelete.action",
		type : "POST",
		dataType : "json",
		data: {id : mailId},
		success: function(result){
			if(result.code == 200){
//				alert("删除成功");
				obj.parents("tr").eq(0).remove();
			} else {
				alert("删除失败");
			}
		}
	});
}

function doAdd(){
	var addr = $("#emailAdress").val();
	if(addr.search(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/) == -1){
		alert("请检查格式输入的是否正确");
		return;
	}
	$.ajax({
		url: SystemProp.appServerUrl+"/new-publisher/edit-admin-email!doAdd.action",
		type : "POST",
		dataType : "json",
		data: {emailAddress : addr},
		success: addCallBack
	});
}
function addCallBack(result){
	if(result.code == 200){
		//alert("添加成功");
		$("#emailAdress").val("")
		var emails = result.data.emails;
		for(var i = 0; i < emails.length; i++){
			var mail = emails[i];
			$el = $("a[mailId='" + mail.id + "']", content);
			if($el.length == 0){
				var str = '<tr><td width="20%">' + mail.id + '</td><td>' + mail.emailAddress + '</td>'
					+ '<td width="20%"><a name="deleteEmail" mailId="' + mail.id + '" href="javascript:void(0)">删除</a></td></tr>'
				$(str).appendTo("#emailListContainer");
				break;
			}
		}
	} else {
		alert(result.message);
	}
}