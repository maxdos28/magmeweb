$(function(){

$("input[name='searchMessageKeyWord']").keyup(function(event){
	event.stopPropagation();
	if (event.keyCode == '13') {
		event.preventDefault();
		searchMessage();
    }
});

$("a[name='searchMessage']").unbind("click").live("click",searchMessage);	

function searchMessage(){
	var keywords=$("input[name='searchMessageKeyWord']").val().split(" ");
	$("div[messageId]").each(function(index){
		var message=$(this);
		var content=message.find("p").html();
		content=content.replace(/(<em>)|(<\/em>)/g, "");
		$.each(keywords,function(index,keyword){
			keyword=keyword.replace(/(^\s*)|(\s*$)/g, "");
			if(keyword){
				var reg = new RegExp('('+keyword+')', 'gi'); 
	        	content=content.replace(reg, '<em>$1</em>'); 
			}
		});
		message.find("p").html(content);
	});
}

//批量删除消息
$("a[name='delMessage']").unbind('click').live('click',function(){
	var message = $(this).parents("[messageId]").eq(0);
    
	var callback = function(result){
		var code = result.code;
		if(code == 400){
			alert(result.message,function(){
				gotoLogin();
			});
			return;
		}else if(code == 300||code == 500){
			alert(result.message);
		}else if(code == 200){
			message.remove();
		}
	};
	$.ajax({
		url : SystemProp.appServerUrl+"/user-message!batchDeleteJson.action",
		type : "POST",
		dataType : "json",
		data : {"messageId":message.attr("messageId")},
		success : callback
	});
});

//删除单条消息
$("a[name='delOneMessage']").unbind('click').live('click',function(){
	var message = $(this).parents("[messageId]").eq(0);
    
	var callback = function(result){
		var code = result.code;
		if(code == 400){
			alert(result.message,function(){
				gotoLogin();
			});
			return;
		}else if(code == 300||code == 500){
			alert(result.message);
		}else if(code == 200){
			message.remove();
		}
	};
	$.ajax({
		url : SystemProp.appServerUrl+"/user-message!deleteJson.action",
		type : "POST",
		dataType : "json",
		data : {"messageIds":message.attr("messageId")},
		success : callback
	});
});

//回复消息
$("a[name='wirteMessageSubmit']").live('click',function(){
	var obj=$(this).parent().parent();
	var url=SystemProp.appServerUrl+"/user-message!sendJson.action"
	var wirteMessageForm=$("#wirteMessageForm");
	url+="?toType="+$("input[name='toType']",wirteMessageForm).val();
	url+="&toUserIds="+$("input[name='toUserIds']",wirteMessageForm).val();
    
	var callback = function(result){
		var code = result.code;
		if(code == 400){
			alert(result.message,function(){
				gotoLogin();
			});
			return;
		}else if(code == 300||code==500){
			alert(result.message);
		}else if(code == 200){
			var messageList=result.data.messageList;
			$.each(messageList,function(index,oneMessage){
				var message='';
				message += '  	<div class="item itemOwn clearFix" messageId="'+oneMessage.id+'">';
				message += '                  <div class="left clearFix">';
				if(oneMessage.fromUserAvatar60){
					message += '                  <div class="userHead"><img src="'+SystemProp.profileServerUrl+oneMessage.fromUserAvatar60+'" /><sub></sub></div>';
				}else{
					message += '                  <div class="userHead"><img src="'+SystemProp.staticServerUrl+'/v3/images/head60.gif" /><sub></sub></div>';
				}
				message += '	                        <strong>'+oneMessage.fromUserNickName+'</strong>';
				message += '	                        <span>发送于：一分钟前</span>';
				message += '	              </div>';
				message += '	              <div class="right">';
				message += '	                 <p>'+oneMessage.contentInfo.content+'</p>';
				message += '	              </div>';
				message += '		          <del></del>';
				message += '		           <a name="delOneMessage" class="del"></a> ';
				message += '  	</div>';
				obj.prevAll(".item:last").before(message);
			});
			wirteMessageForm[0].reset();
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
	
//群发消息
$("a[name='groupMessageSubmit']").live('click',function(){
	var wirteMessageForm=$("#wirteMessageForm");
	var content=$("textarea[name='content']",wirteMessageForm).val();
	var nickName=$("input[name='nickName']",wirteMessageForm).val()
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
			alert("发送成功",function(){
				$("textarea[name='content']",wirteMessageForm).val("");
				$("textarea[name='content']",wirteMessageForm).focus();
			});
		}
	};
	$.ajax({
		url : SystemProp.appServerUrl+"/user-message!batchSendJson.action",
		type : "POST",
		dataType : "json",
		data : {"content":content,"nickName":nickName},
		success : callback
	});
});
	
});