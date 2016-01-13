;$(document).ready(function(){
	//window.location.href
	//滚动加载
	var next=true;
	$doorList = $(".doorList");
	
	$("#loading").hide();
	
	var loadMore = function(){
		$changePage=$(this);
		if($changePage.parents(".changePage").find("a").length==1)
			return;
		
		var op = $("#sns_comment_op").val();
		var lock = $("#page_time_lock").val();
		var page =1;
		$(".clickLoadMore").hide();
		$("#loading").show();
		
		if(next){
			var path = SystemProp.appServerUrl+"/sns/comment!from.action",item=".item1";
			
			if(op!='from'){
				path=SystemProp.appServerUrl+"/sns/comment!to.action";
				item=".item2";
			}
			
			
			if($changePage.attr("class") == undefined){
				page=$changePage.attr("pn");
			}else if($changePage.attr("class") =='pre' ){
				page=parseInt($("#sns_p_n_f").val());
				page-=1;
			}else if( $changePage.attr("class") =='next'){
				page=parseInt($("#sns_p_n_f").val());
				page+=1;
			}
			
			$("#sns_p_n_f").val(page);
			var data = {"page":page,"pageTimeLock":lock};
			$.ajax({
				url : path,
				type : "post",
				async : false,
				data : data,
				success : function (rs){
					$doorList.find(item).html(rs);
				}
			});
			var pagePath = SystemProp.appServerUrl+"/sns/comment!pageCount.action"
			var dt = {"page":page,"pageTimeLock":lock,"op":op};
			$.ajax({
				url : pagePath,
				type : "post",
				async : false,
				data : dt,
				success : function (rs){
					$("#myReplyTab").find(".changePage").html(rs);
				}
			});
			$("#topcontrol").click();
				
		}
		return false;
	};
	$(".changePage").find("a").live("click",loadMore);
	
	$("#myReplyTab").find(".ctrl").find(".a1").live("click",function(){
		$("#sns_p_n_f").val(1);
		$("#sns_comment_op").val('from');
		
		var data = {"page":1};
		$.ajax({
			url : SystemProp.appServerUrl+"/sns/comment!from.action",
			type : "post",
			async : false,
			data : data,
			success : function (rs){
				$doorList.find(".item1").html(rs);
			}
		});
		var dt = {"op":"from"};
		$.ajax({
			url :  SystemProp.appServerUrl+"/sns/comment!pageCount.action",
			type : "post",
			async : false,
			data : dt,
			success : function (rs){
				$("#myReplyTab").find(".changePage").html(rs);
			}
		});
		
	});
	$("#myReplyTab").find(".ctrl").find(".a2").live("click",function(){
		$("#sns_p_n_f").val(1);
		$("#sns_comment_op").val('to');
		
		var data = {"page":1};
		$.ajax({
			url : SystemProp.appServerUrl+"/sns/comment!to.action",
			type : "post",
			async : false,
			data : data,
			success : function (rs){
				$doorList.find(".item2").html(rs);
			}
		});
		var dt = {"op":"to"};
		$.ajax({
			url :  SystemProp.appServerUrl+"/sns/comment!pageCount.action",
			type : "post",
			async : false,
			data : dt,
			success : function (rs){
				$("#myReplyTab").find(".changePage").html(rs);
			}
		});
	});
	
	var ctrdl=function(){
		var cm=$(this).attr("comid"),ct=$(this).attr("ct");
		 $thisctr=$(this);
		if(cm>0){
			if(confirm("确定要删除该评论？"))
		    {
				$.ajax({
					url : SystemProp.appServerUrl+"/sns/comment!del.action",
					type : "POST",
					async : true,
					data : {"commentId":cm,"ct":ct},
					dataType : 'json',
					success: function(rs){
						if(!rs)return;
						var code = rs.code;
						if(code==200){
							//$thisctr.parents(".cList").remove();
							$thisctr.parent().animate({height:0,paddingTop:0,paddingBottom:0},800,"easeOutQuart",function(){
								//高度变化后删除数据			
								$(this).remove();
							});
						}
					}
				});
				
		    }
		}
	};
	$("#myReplyTab").find(".item2").find(".ctrl").live("click",ctrdl);
	
	
	//回复评论
	$("#myReplyTab .doorList .item1 a.ctrl").live("click",function(){
		$(this).parent().siblings().find("div.reply").remove().end().find("a.ctrl").show();
		$(this).hide().after("<div class='reply'><textarea class='input' type='text'></textarea><a class='btnGB' href='javascript:void(0)'>回复</a><a class='close' href='javascript:void(0)'>取消</a></div>");
		var nick = $(this).parents('.cList').find('.nickName').attr("title");
		$(this).next().find("textarea").val("回复@"+nick+"：").autoTextarea({minHeight:26,maxHeight:100});
	});
	
	$("#myReplyTab .doorList .item1 div.reply textarea").live("focus",function(){
		var nick = $(this).parents('.cList').find('.nickName').attr("title");
		if($(this).val()=="回复@"+nick+"：")
			$(this).val('');
	});
	
	$("#myReplyTab .doorList .item1 div.reply textarea").live("focusout",function(){
		if($.trim($(this).val())==""){
			var nick = $(this).parents('.cList').find('.nickName').attr("title");
			$(this).val("回复@"+nick+"：");
		}
	});
	
	//取消回复评论
	$("#myReplyTab .doorList .item1 a.close").live("click",function(){
		$(this).parent().prev().show().end().remove();
	});
	
	//发送评论
	$("#myReplyTab .doorList .item1 div.reply a.btnGB").live("click",function(){
		var oid = $(this).parents('.cList').attr("com");
		var ct = $(this).parents('.cList').attr("ct");
		var u = $(this).parents('.cList').find(".nickName").attr("u");
		var cid = $(this).parents('.cList').find(".works").attr("c");
		var con = $(this).parents('.cList').find("textarea").val();
		var nick = $.trim($(this).parents('.cList').find('.nickName').attr("title"));
		if(con=="回复@"+nick+"："){
			alert("请输入评论回复内容！");
			return;
		}
		var level=0;
		if(ct!=6)
			level=1;
		$.ajax({
			url : SystemProp.appServerUrl+"/sns/comment!reply.action",
			type : "POST",
			async : true,
			data : {"oid":oid,"uid":u,"cid":cid,"con":con,"level":level},
			dataType : 'json',
			success: function(rs){
				if(!rs)return;
				var code = rs.code;
				if(code==200){
				}
			}
		});
		$(this).parent().prev().show().end().remove();
		alert("回复成功！");
	});
	
});