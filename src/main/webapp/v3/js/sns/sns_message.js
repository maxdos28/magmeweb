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

$("body").show();
if(document.getElementById("wirteMessageSubmit")!=null)
	document.getElementById("wirteMessageSubmit").scrollIntoView();
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
			window.location.href = document.location.href;
//			var messageList=result.data.messageList;
//			$.each(messageList,function(index,oneMessage){
//				var message='';
//				message += '  	<div class="item itemOwn clearFix" messageId="'+oneMessage.id+'">';
//				message += '                  <div class="left clearFix">';
//				if(oneMessage.fromUserAvatar60){
//					message += '                  <div class="userHead"><img src="'+SystemProp.profileServerUrl+oneMessage.fromUserAvatar60+'" /><sub></sub></div>';
//				}else{
//					message += '                  <div class="userHead"><img src="'+SystemProp.staticServerUrl+'/v3/images/head60.gif" /><sub></sub></div>';
//				}
//				message += '	                        <strong>'+oneMessage.fromUserNickName+'</strong>';
//				message += '	                        <span>发送于：一分钟前</span>';
//				message += '	              </div>';
//				message += '	              <div class="right">';
//				message += '	                 <p>'+oneMessage.contentInfo.content+'</p>';
//				message += '	              </div>';
//				message += '		          <del></del>';
//				message += '		           <a name="delOneMessage" class="del"></a> ';
//				message += '  	</div>';
//				obj.prevAll(".item:last").before(message);
//			});
//			wirteMessageForm[0].reset();
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

var showFlag=false;
$("input[name='nickName']").live('click',function(){
	var u_condition = {};
	var u_url=SystemProp.appServerUrl+"/sns/public-sns!userList.action";
	$.ajax({
		url : u_url,
		type : "POST",
		dataType:'json',
		data : u_condition,
		success: function(rs){
			if(rs.code==200){
				$("#friends").html('');
				var ui=$("input[name='nickName']").val();
				var ul =ui.split(",");
				var html="";
				for(var i=0;i<rs.data.userlist.length;i++ ){
					var u=rs.data.userlist[i];
					var ck="";
					for(var j=0;j<ul.length;j++)
					{
						if(ul[j]==u.nickname)
							ck="checked=true"
							
					}
					html+="<li><label><input type='checkbox'"+ck+" value='"+u.nickname+"' />"+u.nickname+"</label></li>";
					ck="";
				}
				$("#friends").append(html);
				
			}
		}
	});
	$(".chooseFriends").fadeTo(200,1);
	
});
$(".chooseFriends").live("mouseleave",function(){setTimeout(function(){$(".chooseFriends").hide();},400);});

$("#checkAll").live("click",function(){
	$("#friends").find("input").each(function(){
		$(this).attr("checked",true);
	});
});
$("#checkBack").live("click",function(){
	$("#friends").find("input").each(function(){
		if($(this).attr("checked")!='checked'){
			$(this).attr("checked",true);
		}else{
			$(this).attr("checked",false);
			
			var nick=$("input[name='nickName']").val();
			var nickarr=nick.split(",");
			for(var i=0;i<nickarr.length;i++){
				if($(this).val()==nickarr[i])
					nickarr[i]=null;
			}
			var newNick="";
			for(var i=0;i<nickarr.length;i++){
				if(i==0)
					newNick=nickarr[i];
				else{
					if(nickarr[i]!=null)
						newNick+=","+nickarr[i];
				}
			}
			$("input[name='nickName']").val('');
			if(newNick!='')
				$("input[name='nickName']").val(newNick);
			
		}
	});
});
$("#friends").find("input").live("change",function(){
	if($(this).attr("checked")!='checked'){
		var nick=$("input[name='nickName']").val();
		var nickarr=nick.split(",");
		for(var i=0;i<nickarr.length;i++){
			if($(this).val()==nickarr[i])
				nickarr[i]=null;
		}
		var newNick="";
		for(var i=0;i<nickarr.length;i++){
			if(i==0)
				newNick=nickarr[i];
			else{
				if(nickarr[i]!=null)
					newNick+=","+nickarr[i];
			}
		}
		$("input[name='nickName']").val('');
		if(newNick!='')
			$("input[name='nickName']").val(newNick);
	}
	
});

$("#chooseOK").live("click", function(){
	var i=0;
	var nick=$("input[name='nickName']").val();
	if(nick=='收信人昵称,多个用户之间以英文逗号或中文逗号分隔')
		nick="";
	$("#friends").find("input").each(function(){
		if($(this).attr("checked")=='checked'){
			var ui=$("input[name='nickName']").val();
			var ul =ui.split(",");
			var num=0;
			for(var j=0;j<ul.length;j++)
			{
				if(ul[j]==$(this).val())
					num=1;
			}
			if(num==0){
				if(i==0 && nick=="")
					nick=$(this).val();
				else
					nick+=","+$(this).val();
				i++;
			}
			num=0;
		}
	});
	$("input[name='nickName']").val(nick);
	$(".chooseFriends").fadeTo(200,0,function(){
		$(".chooseFriends").hide();
	});
});

	
});