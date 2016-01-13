$(document).ready(function(){
	//footerMini--------------------------------------------
	//footerMini();

	function enhancedImage (src,onLoaded){
	    var self = this;
	    this.src = src;
	    this.width = 0;
	    this.height = 0;
	    this.onLoaded = onLoaded;
	    this.loaded = false;
	    this.image = null;
	    this.load = function(){
	        if(this.loaded) return;
	        this.image = new Image();
	        this.image.src = this.src;
	        function loadImage(){
	            if(self.width != 0 && self.height != 0){
	                clearInterval(interval);
	                self.loaded = true;
	                self.onLoaded(self);
	            }
	            self.width = self.image.width;
	            self.height = self.image.height;
	        }
	        var interval = setInterval(loadImage,100);
	    }
	    this.load();
	}
	
    var tag = {};
    tag.imgUrl = $("#imgUrl").val();
    tag.tagUrl = $("#tagUrl").val();
    
    var onImageLoad = function(img){
    	tag.imgWidth = img.width;
    	tag.imgHeight = img.height;
    	tag.tagWidth = $("#tagWidth").val()*1;
    	tag.tagHeight = $("#tagHeight").val()*1;
    	tag.tagLeft = $("#tagLeft").val()*1;
    	tag.tagTop = $("#tagTop").val()*1;
    	
    	tag.onClose = tagImgCloseTip;
    	$.showTagArea(tag);
    };
//    ---enhancedImageFun  in  global.js---------------------
    var img = new enhancedImage(tag.imgUrl,onImageLoad);
    
    //tagShare-----------------------------------------------
    var url = location.href;
    var tagTitle = $("#tagTitle").html();
    var picUrl = $("#conBigMgzShow>img[bigimg]").attr("src");
    //tagShare(url,tagTitle,picUrl);
    
    //tagimg_close-------------------------------------------
    tagImgCloseTip();
    function tagImgCloseTip(){
    	var tip = $("#tipTrigger").find(".tip");
    	//Tooltips
    	$("#tipTrigger").hover(function(){
    		tip.show(); //Show tooltip
    	}, function() {
    		tip.hide(); //Hide tooltip		  
    	}).mousemove(function(e) {
    		var mousex = e.pageX + 20; //Get X coodrinates
    		var mousey = e.pageY - 4; //Get Y coordinates
    		var tipWidth = tip.width(); //Find width of tooltip
    		var tipHeight = tip.height(); //Find height of tooltip
    		
    		//Distance of element from the right edge of viewport
    		var tipVisX = $(window).width() - (mousex + tipWidth);
    		//Distance of element from the bottom of viewport
    		var tipVisY = $(window).height() - (mousey + tipHeight);
    		  
    		if ( tipVisX < 20 ) { //If tooltip exceeds the X coordinate of viewport
    			mousex = e.pageX - tipWidth - 20;
    		} if ( tipVisY < 20 ) { //If tooltip exceeds the Y coordinate of viewport
    			mousey = e.pageY - tipHeight - 10;
    		} 
    		tip.css({ top:mousey, left:mousex });
    	});
    }
    
	$("div[title='点击阅读杂志']").live('click',function(){
		var url = $("img[newopen]").eq(0).attr("newopen");
		window.location.href= url;
	});    
    // gobackFun---------------------------------------------------------------
    $("#goBackPage").click(function(e){
    	e.preventDefault();
    	var size = getUrlValue("size");
    	var scrollX = getUrlValue("scrollX");
    	var orderColumn = getUrlValue("orderColumn");
    	var categoryId = getUrlValue("categoryId");
    	if(!size || !scrollX){
    		history.go(-1);
    		return;
    	}
    	var url = "user-tag!tagWall.action?begin=0&size="+size+"&scrollX="+scrollX;
    	if(categoryId){
			url += "&categoryId="+categoryId;
		}
		if(orderColumn){
			url += "&orderColumn="+orderColumn;
		}
    	window.location.href = url;
    });
    //support-----------------------------------------------
    $("#supportBtn").live("click",function(){
    	var tagId=$(this).attr("tagId");
    	var tagCookie=getCookie("tagId_"+tagId);
    	if(tagCookie){
    		alert("您已推荐过");
    	}else{
    		var data={"id":tagId};
		
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
					$("#supportNum").html(result.data.tag.topNum);
					var expires_date = new Date(new Date().getTime() + (1*24*60*60*1000));
					setCookie("tagId_"+tagId,tagId,expires_date);
				}
			};
			$.ajax({
				url : "/user-tag!topJson.action",
				type : "POST",
				dataType : "json",
				data : data,
				success : callback
			});
    	}
    });
    //------------------------------- scroll add tags----------------------------------
	var hasComment = true;
	var scrollTimer = null;

	//-------loadCommentFun----------------------------------
	function addComment(begin,size,tagId){
		var callback = function(result){
			if(!result) return;
			var data = result.data;
			if(data && data.tagCommentList && data.tagCommentList.length > 0 ){
				commentsLoad(data.tagCommentList);
			}else{
				hasComment = false;
				return;
			}
		};
		var data = {};
		if(begin && !isNaN(begin)){
			data.begin = parseInt(begin,10);
		}
		if(size && !isNaN(size)){
			data.size = parseInt(size,10);
		}
		if(tagId){
			data.id = tagId;
		}
		$.ajax({
			url : "/user-tag!getTagCommentListJson.action",
			type : "POST",
			async : false,
			data : data,
			success : callback
		});
	}
	//commentsLoadFun------------------------------------------
	function commentsLoad(commentListData){
		var tagCommentList = $("#tagCommentList");
		for(var i=0;i<commentListData.length; i++){
			var comment = commentListData[i];
			var tagComment = commentFun(comment);
			tagCommentList.append(tagComment);
		}
	}
    
    
    $("#tagCommentSubmit").unbind("click").bind("click",function(){formSubmit();});
    
    var formSubmit = function(){
    	var url = "/tag-comment!loginCommentJson.action";
    	var tagId=$("#tagCommentForm").find("input[name='tagId']").val();
    	var authcode=$("#tagCommentForm").find("input[name='authcode']").val();
    	var userName=$("#tagCommentForm").find("input[name='userName']").val();
    	var password=$("#tagCommentForm").find("input[name='password']").val();
    	var content=$("#tagCommentForm").find("textarea[name='content']").val();
    	if(content&&($.trim(content)=="还没有评论，赶快抢沙发！"||$.trim(content)=="")){
    		alert("评论内容不允许为空",function(){
    			$("#tagCommentForm").find("textarea[name='content']").focus();
    		});
    		return false;
    	}
    	var data = {"tagId":tagId,"authcode":authcode,
    				"userName":userName,"password":password,
    				"content":content};
    	$.ajax({
				url:url,
				type : "POST",
				data : data,
				dataType : "json",
				success: function(rs){
    				if(rs.code==200){
    					var comment = rs.data.tagComment;
    					var tagComment = commentFun(comment,true);
    					var tagCommentForm = $("#tagCommentForm");
						var lastComment = tagCommentForm.parent();
						lastComment.after(tagComment);
						
						tagCommentForm.find("textarea[name='content']").val("");
						tagCommentForm.find("input[name='authcode']").val("");
						tagCommentForm.find("a[name='getAuthcode']").click();
						tagCommentForm.find("input[name='userName']").hide();
						tagCommentForm.find("input[tips='密码']").hide();
						
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
    							$("#tagCommentForm").find("input[name='authcode']").val("");
    							$("#tagCommentForm").find("input[name='authcode']").focus();
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
    	var tagComment = "";
    	var time = isNow ? '一分钟前' : comment.createdTime.replace("T"," ");
    	tagComment = '			<div class="item clearFix">';
		tagComment += '            	<a href="'+'/user!visit.action?id='+comment.user.id+'">';
		tagComment += '                <span>'+time+'</span>';
		if(comment.user.avatar){
			tagComment += '                <div class="userHead"><img src="'+SystemProp.profileServerUrl+comment.user.avatar+'" title="width:50px;" /><sub></sub></div>';
		}else{
			tagComment += '                <div class="userHead"><img src="'+SystemProp.staticServerUrl+'/images/head46.gif" title="width:50px;" /><sub></sub></div>';
		}
		tagComment += '                <strong>'+comment.user.nickName+'</strong>';
		tagComment += '                <p>'+comment.contentInfo.content+'</p>';
		tagComment += '              </a>';
		tagComment += '			</div>';
		
		return tagComment;
    }
    
	//for conBigMgzShow textArea focus
	$(".conTagComment .inputArea textarea").one("focus",function(){
		$(this).height(60).nextAll(".hide").removeClass("hide");
	});
	
	$("a[name='toUserRegisterBtn']").live("click",function(){
		var isExpand =  $("#userBar").find(".l1").hasClass("current");
		if(!isExpand){
			$('#register').click();
		}
	});
	// click_img-------------------------------------------
	$("img[url]").live('click',function(){
		var url = $(this).attr("url");
		window.location.href = url;
	});
	// tagList button------------------------------------------
	$("#turnL").click(function(){
		if($(this).hasClass("stop")) return;
		loadTags("L");
	});
	$("#turnR").click(function(){
		if($(this).hasClass("stop")) return;
		loadTags("R");
	});
	//--------------loadTagsFun---------------------------------
	function loadTags(direction){
		var tagList =$("#tagList");
		var begin = tagList.attr("begin")*1;
		var size = tagList.attr("size")*1;
		var userId = tagList.attr("userId");
		if(direction == 'L'){
			begin = begin-size;
		}else{
			begin = begin+size;
		}
		
		var callback = function(result) {
			if (!result)
				return;
			var tagListData = result.data.userImageList;
			var length = tagListData.length;
			if (tagListData && length > 0) {
				tagList.empty();
				for ( var i = 0; i < length; i++) {
					var tagData = tagListData[i];
					tagList.append('<a href="'
							+ '/widget/showTag.html?id=' + tagData.id
							+ '"><img title="' + tagData.description
							+ '" src="'+ SystemProp.tagServerUrl+ tagData.path68
							+'"></a>');
				}
				tagList.attr("begin",begin);
			}
			$("#turnR").removeClass("stop");
			$("#turnL").removeClass("stop");
			if(length<9){
				$("#turnR").addClass("stop");
			}
			if(begin == 0){
				$("#turnL").addClass("stop");
			}
		};
		var data = {"userId":userId,"size": size};
		if(begin && begin>0){
			data.begin = begin;
		}
		$.ajax({
			url : "/user-visit!userImageJson.action",
			type : "POST",
			dataType : "json",
			data : data,
			success : callback
		});
		
	}
	//---------------------------enter_submmit---------------------------------------------
	$("#tagComment").keydown(function(event){
		if (event.keyCode == '13') {
			event.preventDefault();
			$("#tagCommentSubmit").click();
	    }
	});

});
