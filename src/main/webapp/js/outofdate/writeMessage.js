$(document).ready(function(){
	//for writeMessage
	var $writeMsgName = $("#sendNames");
	var $writeMsgNameList = $("#friends");
	var $writeMsgItem = $writeMsgNameList.find("li");
	$writeMsgLink = $("#chooseFriend").find("a");
	
	//----------------------------friends_scroll-------------------------------------
	$writeMsgName.focus(function(){
		$writeMsgNameList.slideDown(150,
				function(){
					$(".scroll-pane").jScrollPane()
				});
		
		$writeMsgLink.delay(0).fadeIn(600);
	});
	$("#checkAll").click(function(){
		$writeMsgNameList.find("input:checkbox").each(function(){
			$(this).attr("checked",true);
		});
		fillFriendName();
	});
	$("#checkBack").click(function(){
		$writeMsgNameList.find("input:checkbox").each(function(){
			if($(this).is(":checked")){
				$(this).attr("checked",false);
			}else{
				$(this).attr("checked",true);
			}
		});
		fillFriendName();
	});
	$("#chooseOK").click(function(){
		$writeMsgLink.hide();
		$writeMsgNameList.slideUp(300);
	});
	$writeMsgItem.find("input:checkbox").click(fillFriendName);
	
	//----------------------------------fillFriendName--------------------------------------------
	function fillFriendName(){
		var tips = $writeMsgName.attr("tips");
		var oval = "";
		var friendName = $(this).parent().text();
		$writeMsgItem.find("input:checked").each(function(){
			var friendName = $(this).parent().text();
			oval += friendName+",";
		});
		if(oval.length!=0){
			oval = oval.slice(0,oval.length-1);
		}else{
			oval = tips;
		}
		$writeMsgName.val(oval);
		$writeMsgName.focus();
	}
	
	//提交信息
	$("#wirteMessageSubmit").live('click',function(){
		var url=SystemProp.appServerUrl+"/user-message!sendJson.action"
		var wirteMessageForm=$("#wirteMessageForm");
		url+="?toType="+$("input[name='toType']",wirteMessageForm).val();
		var toUserIds="";
		$("input[name='toUserIds']:checked",wirteMessageForm).each(function(){
              toUserIds+="&toUserIds="+$(this).val();
        })
        url+=toUserIds;
        
		var callback = function(result){
			var code = result.code;
			var message = result["message"];
			if(code == 400){
				alert(message,function(){
					gotoLogin();
				});
				return;
			}else if(code == 300||code==500){
				alert(message);
			}else if(code == 200){
				alert("操作成功");
			}
		};
		$.ajax({
			url : url,
			type : "POST",
			dataType : "json",
			data : {"content":$("textarea[name='content']",wirteMessageForm).val()},
			success : callback
		});
	});
});