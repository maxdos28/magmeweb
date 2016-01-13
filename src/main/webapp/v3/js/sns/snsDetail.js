$(function(){
	//分享
	$("a[shareTypeId]").live('click',function(e){
		e.stopPropagation();
		e.preventDefault();
		var shareBtn = $(this);
		var arr = shareBtn.attr("shareTypeId").split('_');
		var type = arr[0];
		var objId = arr[1];
	
		var url = '';
		var title = '';
		var imgsrc = '';
		var desc = '';
		if(type=="eve"){
			var $item = shareBtn.parents("div.item");
			if(arr.length == 3){//首页事件/作品分享
				//url = SystemProp.domain + '/index-detail.action?itemId=' + objId + '&type=' + arr[2];
				url = SystemProp.domain + '/sns/c' + objId + '/';
			} else {//其它情况，跳转到阅读器
				url = SystemProp.domain+"/issue-image/"+objId+".html";
			}
			title = $item.find("h2").text();
			imgsrc = shareBtn.attr("smallPic");
			desc = $item.find("p").text();
		}
		else if(type=="pic"){
			url = SystemProp.domain+"/user-image!show.action?imageId="+objId;
			title = shareBtn.parents(".item").find("h5").text();
			imgsrc = shareBtn.parents(".item").find("img").attr("src");
			desc = shareBtn.parents(".item").find("h5").text();
		}
		
		tagInfo = {
				url: url,
				title: title,
				imgsrc: imgsrc,
				desc: desc
		};
		
		//tagInfo is window's parameter. Default parameter is in useFunction.js
		//everyPage want to share in the Internet, you can change the parameter(tagInfo)
		shareToObj.shareType('tsina',tagInfo);
	});
	//参考content.js
	$("#addComment").live("click",function(e){
		var status;
		$.ajax({
			url : SystemProp.appServerUrl+"/user!getReaderJson.action",
			type : "POST",
			dataType:'json',
			async: false,
			success: function(result){
				SystemProp.isStat = true;
				if(!result) return;
				var data = result.data;
				var code = result.code;
				var checkUserRole = false;
				if(data.user==null){
					$("#userLogin").click();
					status= 1;
				}else{
					status =2;
				}
			}
		});
		if (status==1){
			return;
		}

		var content = $("#content").val();
		if (content ==''||content=='请输入文字') {
			alert("评论不能为空");
			return;
		}

		var callback = function(rs){
			$("#commentContainer").html(rs);
			$("#content").val("");
			$(".txtNum").html('您还可以输入<span>196</span>字');
		}
		
		$.ajax({
			url:SystemProp.appServerUrl+"/index-detail!addComment.action",
			type : "POST",
			data : {"itemId":$("#creativeId").val(),"type":"creative","content":content,"begin":0,"size":10},
			success: callback
		});
	});
	
	$("del[commentId]").unbind("click").live("click",function(){
		var commentId=$(this).attr("commentId");
		var type=$(this).attr("type");
		$.ajax({
			url:SystemProp.appServerUrl+"/common-comment!delete.action",
			type : "get",
			data : {"id":commentId,"type":type},
			success: function(rs){
				if(rs.code!=200){
					alert(rs.message);
				}else{
					var $conReply=$("del[commentId="+commentId+"][type="+type+"]").parents(".conReply");
					var comentlength=$conReply.find(".bl").length+$conReply.find(".br").length;
					$("del[commentId="+commentId+"][type="+type+"]").parent("span").parent("p").parent("div").remove();	
				}
				
			}
		});
	});
	
	$(".shareWeiBo").live("click",function(e){
		if ($(this).hasClass("shareWeiBoChecked")){
			$(this).removeClass("shareWeiBoChecked")
		}else{
			$(this).addClass("shareWeiBoChecked")
		}
	});
	
	$("#content").live("keyup",function(){
		var length = 196;
		var content_len = this.value.length;
		var in_len = length-content_len;
		
		// 当用户输入的字数大于制定的数时，让提交按钮失效
		// 小于制定的字数，就可以提交
		if(in_len >=0){
			$(".txtNum").html('您还可以输入<span>'+in_len+'</span>字');
		}else{
			this.value = this.value.substring(0, 196);
			return false;
		}
	});
	
	$("#content").live("blur",function(){
		var length = 196;
		var content_len = this.value.length;
		var in_len = length-content_len;
		if(in_len >=0){
			$(".txtNum").html('您还可以输入<span>'+in_len+'</span>字');
		}else{
			this.value = this.value.substring(0, 196);
		}
	});
});
