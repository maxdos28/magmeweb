$(document).ready(function(){
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
					message += '  	<div class="item itemOwn clearFix">';
					if(oneMessage.fromUserAvatar60){
						message += '                  <div class="userHead"><img src="'+SystemProp.profileServerUrl+oneMessage.fromUserAvatar60+'" /><sub></sub></div>';
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
});