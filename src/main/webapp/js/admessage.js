$(function(){
//message群发消息
	$("a[name='groupMessageSubmit']").live('click',function(){
		var wirteMessageForm=$("#wirteMessageForm");
		var content=$("textarea[name='content']",wirteMessageForm).val();
		var toUserNames=$("input[name='nickName']",wirteMessageForm).val();
		var fromType=$("input[name='fromType']",wirteMessageForm).val()
		var callback = function(result){
			var code = result.code;
			var message = result["message"];
			if(code == 300||code==500 || code == 400){
				alert(message);
			}else if(code == 200){
				alert("发送成功",function(){
					$("textarea[name='content']",wirteMessageForm).val("");
					$("textarea[name='content']",wirteMessageForm).focus();
				});
			}
		};
		$.ajax({
			url : SystemProp.appServerUrl+"/publish/publish-message!batchSendMessageJson.action",
			type : "POST",
			dataType : "json",
			data : {"content":content,"toUserNames":toUserNames,"fromType":fromType},
			success : callback
		});
	});
	
	
	
//message 后台回复消息
	
	$("a[name='wirteMessageSubmit']").live('click',function(){
		var obj=$(this).parent().parent();
		var url=SystemProp.appServerUrl+"/user-message!sendCommonJson.action"
		var wirteMessageForm=$("#wirteMessageForm");
		var fromType=$("input[name='fromType']",wirteMessageForm).val();
		url+="?toType="+$("input[name='toType']",wirteMessageForm).val();
		url+="&toUserIds="+$("input[name='toUserIds']",wirteMessageForm).val();
		url+="&fromType="+fromType;
		url+="&fromUserId="+$("input[name='fromUserId']",wirteMessageForm).val();
        
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
					var newfromType=oneMessage.fromType;
					var message='';
					message += '  	<div class="item itemOwn clearFix" messageId="'+oneMessage.id+'">';
					if(oneMessage.fromUserAvatar60){
						urlprefix=SystemProp.profileServerUrl;
						if(newfromType==4){
							urlprefix=SystemProp.adProfileServerUrl;
						}else if(newfromType==2){
							urlprefix=SystemProp.publishProfileServerUrl;
						}
						message += '                  <div class="userHead"><img src="'+urlprefix+oneMessage.fromUserAvatar60+'" /><sub></sub></div>';
					}else{
						message += '                  <div class="userHead"><img src="'+SystemProp.staticServerUrl+'/images/head60.gif" /><sub></sub></div>';
					}
					message += '                  <div class="right">';
					message += '                  	<strong>'+oneMessage.fromUserNickName+'</strong>';
					message += '                      <span>发送于：一分钟前</span>';
					message += '  					  <p>'+oneMessage.contentInfo.content+'</p>          ';
					message += '                  </div>';
					message += '  	</div>';
					obj.prevAll(".item:last").before(message);
					obj.prevAll(".item:last").hover(
					function(){
						$(this).prepend("<a class='del'></a>");
					},function(){
						$(this).find("a.del").remove();
					});
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
	
	
	
	
	
	//message批量删除消息
	$("a[name='delMessage']").unbind('click').live('click',function(){
		var message = $(this).parents("[messageId]").eq(0);
	    
		var callback = function(result){
			var code = result.code;
			if(code == 300||code == 500 || code == 400){
				alert(result.message);
			}else if(code == 200){
				message.remove();
			}
		};
		$.ajax({
			url : SystemProp.appServerUrl+"/publish/publish-message!batchDeleteJson.action",
			type : "POST",
			dataType : "json",
			data : {"messageId":message.attr("messageId"),"toType":message.attr("toType"),"toUserId":message.attr("toUserId")},
			success : callback
		});
	});

	//message删除单条消息
	$("a[name='delOneMessage']").unbind('click').live('click',function(){
		var message = $(this).parents("[messageId]").eq(0);
		var adUserId=message.attr("adUserId");
		var toUserId=message.attr("toUserId");
		var fromUserId=message.attr("fromUserId");
		var fromType=message.attr("fromType");
		var toType=message.attr("toType");
		if(adUserId==fromUserId){
			//发件人删除
			toUserId=fromUserId;
			toType=fromType;
		}
		var callback = function(result){
			var code = result.code;
			if(code == 300||code == 500 || code == 400){
				alert(result.message);
			}else if(code == 200){
				message.remove();
			}
		};
		$.ajax({
			url : SystemProp.appServerUrl+"/publish/publish-message!deleteJson.action",
			type : "POST",
			dataType : "json",
			data : {"messageIds":message.attr("messageId"),"toType":toType,"toUserId":toUserId},
			success : callback
		});
	});
	
});