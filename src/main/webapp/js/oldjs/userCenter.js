$(document).ready(function(){
	var content = $("#fancybox-content");
	var dialogClose = $.fancybox.close;
	//-------------------edit userInfomation 修改用户信息------------------------------
	$("#editUserInfo").unbind("click").live("click",function(){
		editUserInfo($(this));
	});
	//----------------------------change Avatar 修改头像--------------------------------
	$("#changeAvatar").unbind("click").live("click",function(){
		$.fancybox.close = function(){
			$("#avatar",content).imgAreaSelect({remove:true});
			dialogClose();
		};
		changeAvatar($(this));
	});
	//footerMini--------------------------------------------
	footerMini();
	
    function changeUserInfo (user){
    	if(!user) return;
    	var gender = "" ;
    	switch(user.gender){
    		case 0:
    			gender = "保密";
    			break;
    		case 1:
    			gender = "男";
    			break;
    		case 2:
    			gender = "女";
    			break;
    		default:
    			break;
    	}
    	$("#user_gender").html(gender);
    	var user_nickName = user.nickName || "";
    	var user_iconCity = user.province || "";
    	var user_birthday = user.birthdate || "保密";
    	$("#user_iconCity").html(user_iconCity);
    	$("#user_nickName").html(user_nickName);
    	$("#user_birthday").html(user_birthday.split("T")[0]);
    }
	function editUserInfo(obj){
		var infoDialog = $("#userInfoDialog");
		//生成dialog
		infoDialog.fancybox();
		
		$("#birthdate").DatePicker({
			format:'Y-m-d',
			date: '1985-01-11',
			current: '1985-01-11',
			starts: 0,
			position: 'bottom',
			onBeforeShow: function(){
				$('#birthdate').DatePickerSetDate($('#birthdate').val()||'1985-01-11', true);
			},
			onChange: function(formated, dates){
				$('#birthdate').val(formated);
				$('#birthdate').DatePickerHide();
			}
		});
		
		editUser_button_bind();
	}
	
	//----------------------------editUser_button_bind------------------------------------
	function editUser_button_bind(){
		$("#submit",content).click(function(){modify_userInfo_submit();});
		$("#cancel",content).click(function(){$.fancybox.close();});
	}
	//----------------------------modify_userInfo_submit-------------------------------------
	function modify_userInfo_submit(){
		var url = SystemProp.appServerUrl + "/user-update!editInfoJson.action";
		var user = form2object('editForm');
		var email = $("#email",content).val();
		if(email == '' || email.indexOf('@') == -1){
			//tipError.html("邮箱必须正确填写!").show();
			alert("邮箱必须正确填写!");
			return;
		}
		var tipError = $("#tipError",content).hide();
		var birthdate = $("#birthdate",content).val();
		var oldPassword = $("#oldPassword",content).val();
		var password = $("#password",content).val();
		var password2 = $("#password2",content).val();
		var message = "";
		var reg = /^((([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29))$/ ;
		if($.trim(birthdate) && !reg.test(birthdate)){
			tipError.html("生日的格式错误em：1985-03-21").show();
			return;
		}
		if(!$.trim(oldPassword) && password !== password2){
			tipError.html("确认密码错误").show(); 
			return;
		}
		var callback = function(result){
			if(!result) return;
			var message ;
			var tipError = $("#tipError",content);
			var code = result.code;
			var data = result.data;
			if(code != 200){
				if(data.email){
					tipError.html(data.email).show();
				}
				if(data.oldPassword){
					tipError.html(data.oldPassword).show();
				}
				if(data.password){
					tipError.html(data.password).show();
				}
			}else{
				$("#userInfoDialog div.important input").val("");
				$.fancybox.close();
			}
			//对页面的用户信息修改
			changeUserInfo(result.data.user);
		};
		$.ajax({
			url:url,
			type : "POST",
			data : user,
			success: callback
		});
	}
	
	//----------------------------changeAvatarFun---------------------------------------------
	function changeAvatar(obj){
		var infoDialog = $("#uploadUserAvatarDialog"),
			content = $("#fancybox-content");
		
		//生成dialog
		infoDialog.fancybox();
		
		changeAvatar_button_bind();
		
		//米客中心的头像
		$("#avatar",content).attr("src",$("#userAvatar").attr("src"));
		//上传头像绑定事件
		var avatarFile = $("#avatarFile",content);
		if ($.browser.msie){
		    // IE suspends timeouts until after the file dialog closes
			avatarFile.live('click change',function(event){
		        setTimeout(function(){
		        	ajaxFileUpload();
		        }, 1);
		    });
		}else{
		    // All other browsers behave
			$("#avatarFile",content).live("change",ajaxFileUpload);
		}
		
	}
	//---------------------------ajaxFileUpload_Fun--------------------------------------
	function ajaxFileUpload(){
    	var fileUrl = $("#avatarFile",content).val();
    	var fileData = $("#avatar",content).data("fileData");
    	if(fileUrl !="" && (!fileData || fileData != fileUrl) ){
    		$("#avatar",content).data("fileData",fileUrl);
    	}else{
    		return;
    	}
    	var successFun = function(rs, status){
    		if(rs.code!=200){
    			alert(rs.message);
    			return;
    		}else{
    			var src = SystemProp.profileServerUrlTmp+rs.data.tempAvatarUrl+"?ts="+new Date().getTime();
            	var avatar = $("#avatar",content);
            	avatar.attr("src",src);
            	$("#avatarBox").removeClass("hide");
            	if($("#uploadBtn").hasClass("btnOB")){
                	$("#uploadBtn").removeClass("btnOB").addClass("btnOS");
                	$("#uploadBtn").find("span").html("重新上传头像");
            	}
            	$("#avatarFileName",content).val(rs.data.avatarFileName);
            	
            	//---enhancedImageFun  in  global.js---------------------
            	enhancedImage(src,function(img){
            		var imgWidth = img.width;
            		var imgHeight = img.height;
            		var showWidth = avatar.width();
                	var showHeight = avatar.height();
            		avatar.imgAreaSelect({
            			aspectRatio: "1:1",
            			x1:5,y1:5,x2:105,y2:105,
            			handles: true,
            			onSelectEnd: function (img, selection) {
                			$("input[name=x]",content).val(selection.x1 * imgWidth / showWidth);
                			$("input[name=y]",content).val(selection.y1 * imgHeight /showHeight);
                			$("input[name=width]",content).val(selection.width * imgWidth / showWidth);
                			$("input[name=height]",content).val(selection.height * imgHeight /showHeight);            
                		}
            		});
            	});
            }
    	};
        $.ajaxFileUpload(
            {
                url:SystemProp.appServerUrl+"/user-update!uploadAvatarJson.action",//用于文件上传的服务器端请求地址
                secureuri:false,//一般设置为false
                fileElementId:"avatarFile",//文件上传空间的id属性  <input type="file" id="file" name="file" />
                content:content,
                dataType: "json",//返回值类型 一般设置为json
                success: successFun,
                //服务器响应失败处理函数
                error: function (data, status, e) {
                    return;
                }
            }
        );
    }
	//---------------------------changeAvatar_button-----------------------------
	function changeAvatar_button_bind(){
		var callback = function(result){
			if(!result) return;
			var message ;
			var tipError = $("#tipError",content);
			if(result.code != 200){
				tipError.show().html(result.message);
			}else{
				$("#avatar",content).imgAreaSelect({remove:true});
				$.fancybox.close();
				//对页面的用户头像修改
				$("#userAvatar").attr("src",SystemProp.profileServerUrl+result.data.user.avatar+"?ts="+new Date().getTime());
			}
		};
		
		$("#submit",content).click(function(){
			var url = SystemProp.appServerUrl + "/user-update!saveAvatarJson.action";
			var data = form2object('editAvatarForm');
			if( data.height==0 || data.width==0 ){
				data.x = 0;
				data.y = 0;
			}
			$.ajax({
				url:url,
				type : "POST",
				data : data,
				success: callback
			});
		});
		
		$("#cancel",content).click(function(){$.fancybox.close();});
	}
	
	//AJAX加载HTML
	function getContent(url,obj,callback){
		if(url.indexOf("?")<0){
			url += "?ts="+new Date().getTime();
		}else{
			url += "&ts="+new Date().getTime();
		}
		if(!callback){
			callback=function(rs){
				$("div.sideLeft").nextAll().remove();
				$("div.sideLeft").after(rs);
				
			}
		}
		$.ajax({
			url:url,
			type : "POST",
			dataType : "html",
			success: callback
		});
	}
	
	$("a[name='userCenterMenu']").live("click",function(){
		getContent(SystemProp.appServerUrl+"/user!centerAjax.action",$(this));
	});
	
	$("a[name='favoriteMenu']").live("click",function(){
		getContent(SystemProp.appServerUrl+"/user-favorite!manageAjax.action?pos=0",$(this));
	});

	$("a[name='subscribeMenu']").live("click",function(){
		getContent(SystemProp.appServerUrl+"/user-favorite!manageAjax.action?pos=1",$(this));
	});
	
	$("a[name='tagMenu']").live("click",function(){
		getContent(SystemProp.appServerUrl+"/user-tag!manageAjax.action",$(this));
	});
	$("a[name='friendMenu']").live("click",function(){
		getContent(SystemProp.appServerUrl+"/user-friend!snsAjax.action?pos=0",$(this));
	});
	$("a[name='followMenu']").live("click",function(){
		getContent(SystemProp.appServerUrl+"/user-friend!snsAjax.action?pos=1",$(this));
	});
	$("a[name='waitFriendMenu']").live("click",function(){
		getContent(SystemProp.appServerUrl+"/user-friend!snsAjax.action?pos=2",$(this));
	});
	$("a[name='messageMenu']").live("click",function(){
		var obj=$(this);
		var callback=function(rs){
			$("#sideLeft").nextAll().remove();
			$("#sideLeft").after(rs);

		};
		getContent(SystemProp.appServerUrl+"/user-message!manageAjax.action",obj,callback);
	});
	
	//--------------------------------writeMessageMenu----------------------------------------------
	$("a[name='writeMessageMenu']").live("click",function(){
		var obj=$(this);
		var callback=function(rs){
			$("#sideLeft").nextAll().remove();
			$("#sideLeft").after(rs);

			writeMessage();
		};
		getContent(SystemProp.appServerUrl+"/user-message!toWriteAjax.action",obj,callback);
	});
	//------------------------------------writeMessage-------------------------------------------------
	function writeMessage (){
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
	}
	//----------------------------------fillFriendName--------------------------------------------
	function fillFriendName(){
		var $writeMsgName = $("#sendNames");
		var $writeMsgNameList = $("#friends");
		var $writeMsgItem = $writeMsgNameList.find("li");	
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
	$("a[name='readMessageMenu']").live("click",function(){
		var callback=function(rs){
			$("#sideLeft").nextAll().remove();
			$("#sideLeft").after(rs);

			var $readMsgItem = $(".conMessageRead .item").not($(".conMessageRead .reply"));
			var $readMsgDel = $(".conMessageRead .item .del");
			$readMsgItem.hover(
			function(){
				$(this).prepend("<a class='del'></a>");
			},function(){
				$(this).find("a.del").remove();
			});
		};
		getContent($(this).attr("url"),$(this),callback);
	});
	$(".conMessageRead .item .del").live("click",function(){
		$(this).hide().parent(".item").animate({height:0,opacity:0,margin:0,paddingTop:0,paddingBottom:0,border:0},1000,"easeOutQuint");
		var obj = $(this).parent(".item");
		setTimeout(function(){obj.remove()},1000);
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
	
	//订阅收藏:开始
	//取消收藏
	$("a[name='deleteFavorite']").live('click',function(){
		var userFavorite = $(this).parents("[userFavoriteId]").eq(0);
		var userFavoriteId = userFavorite.attr("userFavoriteId");
		var content=userFavorite.parent();
		
		var callback = function(result){
			var code = result.code;
			var message = result["message"];
			if(code == 400){
				alert(message,function(){
					gotoLogin();
				});
				return;
			}else if(code == 300){
				alert(message);
			}else if(code == 200){
				alert("操作成功",function(){
					userFavorite.remove();
					if(content.find("div[userFavoriteId]").length==0){
						content.children().eq(0).before("<div class='nullInfo'>您还没有任何收藏</div>");
					}
				});
			}
		};
		$.ajax({
			url : SystemProp.appServerUrl+"/user-favorite!deleteJson.action",
			type : "POST",
			dataType : "json",
			data : {"id":userFavoriteId},
			success : callback
		});
	});
	
	//取消订阅
	$("a[name='deleteSubscribe']").live('click',function(){
		var userSubscribe = $(this).parents("[userSubscribeId]").eq(0);
		var userSubscribeId = userSubscribe.attr("userSubscribeId");
		var content=userSubscribe.parent();
		
		var callback = function(result){
			var code = result.code;
			var message = result["message"];
			if(code == 400){
				alert(message,function(){
					gotoLogin();
				});
				return;
			}else if(code == 300){
				alert(message);
			}else if(code == 200){
				alert("操作成功",function(){
					userSubscribe.remove();
					if(content.find("div[userSubscribeId]").length==0){
						content.children().eq(0).before("<div class='nullInfo'>您还没有任何订阅</div>");
					}
				});
			}
		};
		$.ajax({
			url : SystemProp.appServerUrl+"/user-subscribe!deleteJson.action",
			type : "POST",
			dataType : "json",
			data : {"id":userSubscribeId},
			success : callback
		});
	});
	//订阅收藏:结束
	
	//标签管理:开始
	//删除标签
	$("sub[name='editTag']").live('click',function(){
		//生成dialog
		var infoDialog = $("#editTagDialog"),
			content = $("#fancybox-content");
		infoDialog.fancybox();
		
		var obj= $(this);
		var tagDiv=obj.parents("div[tagId]").eq(0);
		var tagData = obj.data("tagData");
		if(!tagData){
			tagData={"id":tagDiv.attr("tagId"),
					"title":tagDiv.find("input[name='title']").val(),
					"keyword":tagDiv.find("input[name='keyword']").val(),
					"description":tagDiv.find("input[name='description']").val()}
			$(this).data("tagData",tagData);
		}
		
		$("#editTagDialog").find("input[name='id']").val(tagData.id);
		$("#editTagDialog").find("input[name='title']").val(tagData.title);
		$("#editTagDialog").find("input[name='keyword']").val(tagData.keyword);
		$("#editTagDialog").find("textarea[name='description']").val(tagData.description);
	});
	
	$("#editTagFormSubmitBtn").live('click',function(){	
		var form= $(this).parents("form");
		var tagId=form.find("input[name='id']").val();
		var title=form.find("input[name='title']").val();
		var keyword=form.find("input[name='keyword']").val();
		var description=form.find("textarea[name='description']").val();
		var tagData={"id":tagId,
					"title":title,
					"keyword":keyword,
					"description":description};
		
		var callback = function(result){
			var code = result.code;
			var message = result["message"];
			if(code == 400){
				alert(message);
				$("#login").click();
				return;
			}else if(code == 300||code==500){
				alert(message);
			}else if(code == 200){
				alert("修改成功");
				$("div[tagId='"+tagId+"']").find("sub[name='editTag']").data("tagData",tagData);
				$("div[tagId='"+tagId+"']").find("sub[name='editTag']").prev().text(tagData.title);
				$.fancybox.close();
			}
		};
		$.ajax({
			url : SystemProp.appServerUrl+"/user-tag!editTagJson.action",
			type : "POST",
			dataType : "json",
			data : tagData,
			success : callback
		});
	});
	
	$("#editTagFormCancelBtn").live('click',function(){	
		$.fancybox.close();
	});
	
	$("#editTagFormDeleteBtn").live('click',function(){
		var obj=$(this);
		var tagId = obj.parents("form").find("input[name='id']").val();
		
		var callback = function(result){
			var code = result.code;
			var message = result["message"];
			if(code == 400){
				alert(message);
				$("#login").click();
				return;
			}else if(code == 300||code==500){
				alert(message);
			}else if(code == 200){
				alert("删除标签成功");
				$("div[tagId='"+tagId+"']").remove();
				$.fancybox.close();
			}
		};
		$.ajax({
			url : SystemProp.appServerUrl+"/user-tag!deleteTagJson.action",
			type : "POST",
			dataType : "json",
			data : {"id":tagId},
			success : callback
		});
	});
	//标签管理:结束
	
	//好友关注:开始
	//同意加为好友
	$("a[name='agreeFriend']").live('click',function(){
		var user = $(this).parents("[userId]").eq(0);
		var userId = user.attr("userId");
		var avatar=$("img[name='avatar']",user).attr("src");
		var nickName=$("strong[name='nickName']",user).text();
		
		var callback = function(result){
			var code = result.code;
			var message = result["message"];
			if(code == 400){
				alert(message,function(){
					gotoLogin();
				});
				return;
			}else if(code == 300){
				alert(message);
			}else if(code == 200){
				alert("操作成功",function(){
					var newFriend='';
					newFriend += '                    	<div class="fItem">';
					newFriend += '                        	<a href="'+SystemProp.appServerUrl+'/user!visit.action?id='+userId+'">';
					newFriend += '                        		<div class="userHead">';
					newFriend += '	            					      <img src="'+avatar+'" />';
					newFriend += '								              <sub></sub>';
					newFriend += '								              </div>                        	';
					newFriend += '                            	<strong>'+nickName+'</strong>';
					newFriend += '                            </a>';
					newFriend += '                            <div class="team">';
					newFriend += '                            	<strong>所在地区</strong>';
					newFriend += '                            	<a href="javascript:void(0)">族群1</a>';
					newFriend += '                            	<a href="javascript:void(0)">族群2</a>';
					newFriend += '                            	<a href="javascript:void(0)">族群3</a>';
					newFriend += '                            </div>';
					newFriend += '                            <div class="read">';
					newFriend += '                            	<strong>最近阅读</strong>';
					newFriend += '                            	<a href="javascript:void(0)">杂志名</a>';
					newFriend += '                            	<a href="javascript:void(0)">杂志名</a>';
					newFriend += '                            	<a href="javascript:void(0)">杂志名</a>';
					newFriend += '                            </div>';
					newFriend += '                        </div>';
					$("#friendList").prepend(newFriend);
					user.remove();
				});
			}
		};
		$.ajax({
			url : SystemProp.appServerUrl+"/user-friend!agreeJson.action",
			type : "POST",
			dataType : "json",
			data : {"friendUserId":userId},
			success : callback
		});
	});
	
	//拒绝加为好友
	$("a[name='refuseFriend']").live('click',function(){
		var user = $(this).parents("[userId]").eq(0);
		var userId = user.attr("userId");
		
		var callback = function(result){
			var code = result.code;
			var message = result["message"];
			if(code == 400){
				alert(message,function(){
					gotoLogin();
				});
				return;
			}else if(code == 300){
				alert(message);
			}else if(code == 200){
				alert("操作成功",function(){
					user.remove();
				});
			}
		};
		$.ajax({
			url : SystemProp.appServerUrl+"/user-friend!refuseJson.action",
			type : "POST",
			dataType : "json",
			data : {"friendUserId":userId},
			success : callback
		});
	});
	//好友关注:结束
	//消息管理:开始
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
	
	//群发消息
	$("a[name='groupMessageSubmit']").live('click',function(){
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