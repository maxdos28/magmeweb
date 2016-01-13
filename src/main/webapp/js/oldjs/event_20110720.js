$(document).ready(function(){
	$("#btnQuestion").toggle(
	function(){
		$(this).text("收起");
		$(".itemQuestion").slideDown(500);
	},function(){
		$(this).text("我要留言");
		$(".itemQuestion").slideUp(250);
	});
	
	//用户留言
	$("a[name='eventQaFormSubmitBtn']").live('click',function(){
		var eventCode=$("#eventQaForm").find("input[name='eventCode']").val();
		var content=$("#eventQaForm").find("textarea[name='content']").val();
		var data={"eventCode":eventCode,"content":content};
		
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
				$("#eventQaForm").find("textarea[name='content']").val("");
				var eventQa=result.data.eventQa;
				var eventQaString = '        	<div class="item clearFix">';
				eventQaString += '            	<div class="userHead">';
				eventQaString += '            		<a href="'+SystemProp.appServerUrl+'/user!visit.action?id='+eventQa.userId+'">';
				if(eventQa.avatar60&&eventQa.avatar60!=""){
					eventQaString += '            			<img src="'+SystemProp.profileServerUrl+eventQa.avatar60+'" />';
				}else{
					eventQaString += '						<img src="'+SystemProp.staticServerUrl+'/images/head60.gif" />';
				}
				eventQaString += '            		</a>';
				eventQaString += '            	</div>';
				eventQaString += '                <div class="content">';
				eventQaString += '	            	<strong><a href="'+SystemProp.appServerUrl+'/user!visit.action?id='+eventQa.userId+'">'+eventQa.nickName+'</a></strong>';
				eventQaString += '                    <span>一分钟前</span>';
				eventQaString += '                    <p><strong>问题：</strong>'+eventQa.content+'</p>';
				eventQaString += '                    <P class="answer">';
				eventQaString += '                    	<strong>回答：</strong>等待麦米网答复';
				eventQaString += '                    </P>';
				eventQaString += '            	</div>';
				eventQaString += '            </div>';
				$("#btnQuestion").next().find("div.item.clearFix").eq(0).after(eventQaString);
			}
		};
		$.ajax({
			url : SystemProp.appServerUrl+"/event!questionJson.action",
			type : "POST",
			dataType : "json",
			data : data,
			success : callback
		});
	});
	
	//参加活动
	$("#joinbtn").unbind("click").live("click",function(){
		window.location.href=SystemProp.appServerUrl+"/event!toEditOpus.action";
	});
	
	//submitOpusForm-----------------------------------
	function submitOpusForm(){
		$("#eventOpusFormSubmitBtn").unbind("click");
		var eventCode=$("#eventOpusForm").find("input[name='eventCode']").val();
		var title=$("#eventOpusForm").find("input[name='title']").val();
		var cover=$("#eventOpusForm").find("input[name='cover']").val();
		var content=KE.html('eventOpusContent');
		var data={"eventCode":eventCode,"title":title,"cover":cover,"content":content};
		
		var callback = function(result){
			
			var code = result.code;
			var message = result["message"];
			if(code == 400){
				alert(message,function(){
					gotoLogin();
					$("#eventOpusFormSubmitBtn").click(submitOpusForm);
				});
				return;
			}else if(code == 300||code==500){
				alert(message,function(){
					$("#eventOpusFormSubmitBtn").click(submitOpusForm);
				});
			}else if(code == 200){
				alert("感谢您提交报告，请尽快完善个人信息，便于麦米与您取得联系",function(){
					window.location.href=SystemProp.appServerUrl+"/event!opusList.action?eventCode=20110720"
				});
			}
		};
		
		var flag=true;
		if(!cover||$.trim(cover)==""){
			if(!confirm("您还没有上传封面,确定要提交吗?")){
				flag=false;
				$("#eventOpusFormSubmitBtn").click(submitOpusForm);
			}
		}
		
		if(flag){
			$.ajax({
				url : SystemProp.appServerUrl+"/event!saveEventOpusJson.action",
				type : "POST",
				dataType : "json",
				data : data,
				success : callback
			});
		}
	}
	//提交体验作品
	$("#eventOpusFormSubmitBtn").unbind("click").bind("click",submitOpusForm);
	
	//上传作品封面
	$("#coverUploadBtn").unbind("click").live("click",function(){
		var obj=$(this);
    	var successFun = function(result, status){
			var code = result.code;
			var message = result["message"];
			if(result.code == 400){
				alert(message,function(){
					gotoLogin();
				});
				return;
			}else if(result.error==0){
				$("#eventOpusForm").find("input[name='cover']").val(result.path);
				$("#coverPreviewImg").attr("src",result.url);
				$("#coverPreviewDiv").show();
			}else if(result.code||result.error){
				alert(message);
			}else{
				alert("上传图片失败");

			}
    	};
        $.ajaxFileUpload(
        {
                url:SystemProp.appServerUrl+"/kind-editor-upload!imageUploadJson.action",//用于文件上传的服务器端请求地址
                secureuri:false,//一般设置为false
                fileElementId:"imgFile",//文件上传空间的id属性  <input type="file" id="file" name="file" />
                dataType: "json",//返回值类型 一般设置为json
                success: successFun,
                //服务器响应失败处理函数
                error: function (data, status, e) {
                    return;
                }
        });
	});
	
	$(".conTagComment .inputArea textarea").one("focus",function(){
		$(this).height(60).nextAll(".hide").removeClass("hide");
	});
	
	$("a[name='toUserRegisterBtn']").live("click",function(){
		var isExpand =  $("#userBar").find(".l1").hasClass("current");
		if(!isExpand){
			$('#register').click();
		}
	});
	
	$("a[name='eventCommentSubmit']").unbind("click").bind("click",function(){
		var form=$(this).parent();
		formSubmit(form);}
	);
	
	var formSubmit = function(form){
    	var url = SystemProp.appServerUrl+"/event!loginCommentJson.action";
    	var eventOpusId=form.find("input[name='eventOpusId']").val();
    	var eventCode=form.find("input[name='eventCode']").val();
    	var authcode=form.find("input[name='authcode']").val();
    	var userName=form.find("input[name='userName']").val();
    	var password=form.find("input[name='password']").val();
    	var content=form.find("textarea[name='content']").val();
    	if(content&&($.trim(content)=="还没有评论，赶快抢沙发！"||$.trim(content)=="")){
    		alert("评论内容不允许为空",function(){
    			form.find("textarea[name='content']").focus();
    		});
    		return false;
    	}
    	var data = {"eventCode":eventCode,"eventOpusId":eventOpusId,"authcode":authcode,
    				"userName":userName,"password":password,
    				"content":content};
    	$.ajax({
				url:url,
				type : "POST",
				data : data,
				dataType : "json",
				success: function(rs){
    				if(rs.code==200){
    					var comment = rs.data.eventComment;
    					var eventComment = commentFun(comment,true);
    					var eventCommentForm = form;
						var lastComment = eventCommentForm.parent();
						lastComment.after(eventComment);
						
						eventCommentForm.find("textarea[name='content']").val("");
						eventCommentForm.find("input[name='authcode']").val("");
						eventCommentForm.find("a[name='getAuthcode']").click();
						eventCommentForm.find("input[name='userName']").hide();
						eventCommentForm.find("input[tips='密码']").hide();
						
						loginSuccess(rs.data.user);
						
    				}else if(rs.code=300&&rs.message=="failure"){
    					if(rs.data.userName) {
    						alert(rs.data.userName);
    						return;
    					}
    					if(rs.data.password) {
    						alert(rs.data.password);
    						return;
    					}
    					if(rs.data.content) {
    						alert(rs.data.content);
    						return;
    					}
    					if(rs.data.authcode) {
    						alert(rs.data.authcode,function(){
    							$("#eventCommentForm").find("input[name='authcode']").val("");
    							$("#eventCommentForm").find("input[name='authcode']").focus();
    						});
    						return;
    					}
    				}else{
    					alert(rs.message);
    				}
				},
				failure : function(){
					alert("服务器出错啦！");
				}
		});
    }
    //--commentFun------------------------
    function commentFun (comment,isNow){
    	var eventComment = "";
    	var time = isNow ? '一分钟前' : comment.createdTime.replace("T"," ");
    	eventComment = '			<div class="item clearFix">';
		eventComment += '            	<a href="'+SystemProp.appServerUrl+'/user!visit.action?id='+comment.userId+'">';
		eventComment += '                <span>'+time+'</span>';
		if(comment.avatar46){
			eventComment += '                <div class="userHead"><img src="'+SystemProp.profileServerUrl+comment.avatar46+'" title="width:50px;" /><sub></sub></div>';
		}else{
			eventComment += '                <div class="userHead"><img src="'+SystemProp.staticServerUrl+'/images/head46.gif" title="width:50px;" /><sub></sub></div>';
		}
		eventComment += '                <strong>'+comment.nickName+'</strong>';
		eventComment += '              </a>';		
		eventComment += '                <p>'+comment.content+'</p>';
		eventComment += '			</div>';
		
		return eventComment;
    }
    
    $("a[eventOpusId]").live("click",function(){
		var data={"eventOpusId":$(this).attr("eventOpusId")};
		
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
				alert("投票成功",function(){
					$("a[eventOpusId='"+data.eventOpusId+"']").html("投票<strong>"+result.data.eventOpusVoteCount+"</strong>");
				});
			}
		};
		$.ajax({
			url : SystemProp.appServerUrl+"/event!voteJson.action",
			type : "POST",
			dataType : "json",
			data : data,
			success : callback
		});
    });

    
    
    //sort------------------------------------------------------
    $(".right a[orderType]").click(function(){
    	$(".right .current").removeClass("current");
    	$(this).addClass("current");
    	var orderType = parseInt($(this).attr("orderType"),10);
    	$("#orderType").val(orderType);
    	
    	var eventOpusList = $("#eventOpusList");
    	//hide all eventOpusList's children
    	$("#eventOpusList>div:visible").hide();
    	
    	var eventCode = getUrlValue("eventCode") || '20110720';
    	var data = {"begin":0,"size":20,"eventCode":eventCode,"orderType":orderType};
    	//如果有，就显示，没有就加载
    	function showOpus (listId){
    		if($("#"+listId).length>0){
				$("#"+listId).show();
			}else{
				$OpusList = $("<div id='"+listId+"'></div>");
				$("#eventOpusList").append($OpusList);
				loadAjax(data,function(rs){
					if(!rs) return;
					var opusList = rs.data.eventOpusList;
					var length = opusList.length;
					if(length>0){
						$.map(opusList,loadOpus);
					}
				});
			}
    	}
    	switch (orderType){
			case 1:
				showOpus("opusList1");
				break;
			case 2:
				showOpus("opusList2");
				break;
			case 3:
				showOpus("opusList3");
				break;
			default:
				showOpus("opusList0");
		}
    });

	$("#myOpusShow").live("click",function(){
		$.ajax({
			url : SystemProp.appServerUrl+"/user!getReaderJson.action",
			type : "POST",
			success: function(result){
				if(!result) return;
				var data = result.data;
				var code = result.code;
				if(code==200){
					if(data && data.user){
						window.location.href=SystemProp.appServerUrl+"/event!opusShow.action?pos=210px&userId="+data.user.id;
						return;
					}else{
						alert("用户未登陆",function(){
							gotoLogin();
						});
						return;
					}
				}else{
					alert(result.message);
				}
			}
		});
	});
    //加载作品数据------------------------------------
    function loadAjax(data,callback){
    	$.ajax({
			url : SystemProp.appServerUrl+"/event!opusListJson.action",
			type : "POST",
			data : data,
			success: callback
		});
    }
    
    /**
     * window_scroll_fun author:edward time:11/07/26
     */
    var scrollTimer = null;
    var data_0=true,data_1=true,data_2=true,data_3=true;
	$(window).scroll(function(){
//		var hasOver = hasOverlay();
//		if(!scrollTimer && !hasOver){
//			scrollTimer = setTimeout(function(){
//				var bodyHeight = $("body").height();
//				var windowHeight = $(window).height();
//				var scrollHeight = bodyHeight - windowHeight;
//				
//				var scrollTop = $(window).scrollTop();
//				if(scrollTop > (scrollHeight-300)){
//					$("#loadingPage").fadeIn();
//					loadData();
//					$("#loadingPage").fadeOut(2000);
//				}
//				scrollTimer = null;
//			},800);
//		}
	});
	function loadData(){
		if($("#eventOpusList").length>0 && $("#eventOpusList").is(":visible")){
			loadOpusList();
		}else if($("#personOpus").length>0 && $("#personOpus").is(":visible")){
			loadCommentList();
		}
	}

	//--------------------------loadOpus---------------------------
	function loadOpus(opus){
		$opusList = $("#eventOpusList>div:visible").eq(0); 
		var eventOpus = '<div class="item"><a href="'+SystemProp.appServerUrl+'/event!opusShow.action?userId='+opus.userId+'"><img src="';
		var cover = opus.cover;
		if(!cover){
			eventOpus += SystemProp.staticServerUrl+'/event/20110706/iconDoc.gif" />';
		}else if(cover == "/event/20110706/iconVideo.gif"){
			eventOpus += SystemProp.staticServerUrl+'/event/20110706/iconVideo.gif" />';
		}else {
			eventOpus += SystemProp.staticServerUrl+'/event'+cover+'" />';
		}
		eventOpus += '<strong>'+opus.title+'</strong></a><h6>作者：<a href="'+SystemProp.appServerUrl+
					'/user!visit.action?id='+opus.userId+'">'+opus.nickName+'</a></h6>'+
					'<span>评论<em>('+opus.commentNum+')</em> | 票数<em>('+opus.voteNum+')</em></span></div>';
		
		$opusList.append(eventOpus);
	}
	//----------------------loadOpusList---------------------------
	function loadOpusList(){
		var orderType = parseInt($("#orderType").val(),10);
		var eventCode = getUrlValue("eventCode") || '20110720';
		var begin = $("#eventOpusList").find("div.item:visible").length;
		var data = {"begin":begin,"size":8,"eventCode":eventCode,"orderType":orderType};
		
		var callback = function(rs){
			if(!rs) return;
			var eventOpusList = rs.data.eventOpusList;
			var length = eventOpusList.length;
			if(length>0){
				$.map(eventOpusList,loadOpus);
			}else{
				switch (orderType){
					case 1:
						data_1 = false;
						break;
					case 2:
						data_2 = false;
						break;
					case 3:
						data_3 = false;
						break;
					default:
						data_0 = false;
						break;
				}
			}
		};
		
		if(orderType==0 && !data_0){
			$("#loadingPage").html("没有内容加载了");
			return;
		}
		if(orderType==1 && !data_1){
			$("#loadingPage").html("没有内容加载了");
			return;
		}
		if(orderType==2 && !data_2){
			$("#loadingPage").html("没有内容加载了");
			return;
		}
		if(orderType==3 && !data_3){
			$("#loadingPage").html("没有内容加载了");
			return;
		}
		
		loadAjax(data,callback);
	}
	//------------------------loadCommentList-----------------------------
	function loadCommentList(){
		var reportCur = $("#personOpus>.loadComment");
		if(reportCur.length>0 && reportCur.attr("hasComment")=='true'){
			var eventOpusId = reportCur.attr("eventOpusId");
			var $comment = reportCur.find(".comment");
			//.clearFix含有一个input
			var begin = $comment.find(".clearFix").length-1;
			var data = {"eventOpusId":eventOpusId,"size":15,"begin":begin};
			
			var callback = function(rs){
				if(!rs)return;
				var eventCommentList = rs.data.eventCommentList;
				var length = eventCommentList.length;
				if(length<=0){
					reportCur.attr("hasComment","false");
				}
				for(var i=0;i<length;i++){
					var eventComment = eventCommentList[i];
					loadCommet(eventComment,$comment)
				}
			};
			$.ajax({
				url : SystemProp.appServerUrl+"/event!commentListJson.action",
				type : "POST",
				data : data,
				success: callback
			});
		}
	}
	//--------------------------------loadCommet-------------------------------
	function loadCommet(eventComment,$comment){
		var strComment = '<div class="item clearFix"><a href="'+SystemProp.appServerUrl+
			'/user!visit.action?id='+eventComment.userId+'"><span>';
		var time = eventComment.createdTime.replace("T"," ");
		strComment += time+'</span><div class="userHead" ><img src="';
		if(eventComment.avatar46){
			strComment += SystemProp.profileServerUrl + eventComment.avatar46 + '" title="width:50px;" />';
		}else{
			strComment += SystemProp.staticServerUrl+'/images/head46.gif" title="width:50px;" />';
		}
		strComment += '<sub></sub></div><strong>'+eventComment.nickName+'</strong><p>'+eventComment.content+'</p></a></div>';
		
		$comment.append(strComment);
	}
	//-----------------------eventCommentFormSubmit------------------------------
	$("div.inputArea").keyup(function(event){
		event.stopPropagation();
		if (event.keyCode == '13') {
			event.preventDefault();
			$(this).find("a[name='eventCommentSubmit']").click();
	    }
	});
	
	
	
	fnSetFooterHeight();
});