$(function(){
	$.jqueryScrollPhoto("#conOtherRead",4,222,4,0,600);
	$.jquerySlideDoor("#conBuyAd",3,1,0,5000);
	$(".conOtherRead .inner .item .photo img, .conVideoAd img").coverImg();
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
		if ($(".shareWeiBo").hasClass("shareWeiBoChecked")){
			// 转发微博
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
			data : {"itemId":getItemId,"type":getType,"content":content,"begin":0,"size":10},
			success: callback
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

	$("#conReplyMore").live("click",function(e){
		if (getBegin>=total) {
			return;
		}
		
		var callback = function(rs){
			$("#commentContainer").append(rs);
			getBegin = getBegin+10;
		}
		$.ajax({
			url:SystemProp.appServerUrl+"/index-detail!getCommentList.action",
			type : "get",
			data : {"itemId":getItemId,"type":getType,"begin":getBegin,"size":10},
			success: callback
		});
	});

	$("a[name='tjitem']").live("click",function(e){
		var obj = $(this);
		var id = obj.attr('itemId');
		var type = obj.attr('type');
		var recommend= 1;
		var myread = ($.browser.msie)?$("#midRead").get(0):$("#midRead2").get(0);
		myread.wordReaderFlashGetData(id,type,"null","null",recommend);
	});
	
	// move to global.js add by fredy in 20120913
	/*$("del[commentId]").unbind("click").live("click",function(){
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
					$("del[commentId="+commentId+"][type="+type+"]").parent("span").parent("p").parent("div").remove();	
				}
				
			}
		});
	});*/
});
var login = loginSuccess;

function changAvatar(user){
	login(user);
	if (!!user.avatar) {
		$("#userhead").attr("src",SystemProp.profileServerUrl+user.avatar);
	}
}
loginSuccess = changAvatar;