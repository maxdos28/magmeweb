;$(function($){
	
	//转发按钮移入
	var forwardTools = "<div id='shareDiv' style='display:none'><p><a title='新浪微博' tp='tsina' href='javascript:void(0)' class='weibo png'></a><a href='javascript:void(0)' tp='tqq' title='腾讯微博' class='tencent png'></a><a title='开心网' tp='kaixin'  href='javascript:void(0)' class='kaixin png'></a><a title='人人网' tp='renren' href='javascript:void(0)' class='renren png'></a><a title='M1社区' href='javascript:void(0)' tp='m1' class='m1 png'></a></p></div>";
	$("em.iconShare").mouseenter(function(){
		$(this).append(forwardTools).find("div").fadeIn(200);
	}).mouseleave(function(){
		$(this).find("div").remove();
	});
	
	//加为好友
	$(".atten,.cancel").live("click",function(){
		if($("#userBar").attr("style")==undefined){
			gotoLogin("请登录后，才能添加关注哦！",1);
			return;
		}
		var _$this =this;
		var userId = $(this).attr("u");
		var nick=$(this).attr("nick");
		var tx=$(this).html();
			if(tx=='加关注'){
				ajaxAddFollow(userId,1,function(rs){
					if(!rs)return;
					var code = rs.code;
					if(code == 200){
						$(_$this).html('取消关注');
						if($(_$this).hasClass("iconAdd"))
							$(_$this).attr("class","iconCancel");
						else
							$(_$this).attr("class","cancel");
					}else if(code == 400){
						gotoLogin("用户没有登录，请登录！");
					}else{
						alert(rs.message);
					}
				});
			}else{
				
				if(confirm("确定不再关注 "+nick+" 了吗？"))
			    {
					ajaxCancelFollow(userId,1,function(rs){
						if(!rs)return;
						var code = rs.code;
						if(code == 200){
							if($(_$this).hasClass("iconCancel"))
								$(_$this).attr("class","iconAdd");
							else
								$(_$this).attr("class","atten");
							$(_$this).html('加关注');
						}else if(code == 400){
							gotoLogin("用户没有登录，请登录！");
						}else{
							alert(rs.message);
						}
					});
			    }
			}
		
	});

	
	var next=true;
	$Dashboard = $("#conDetailWall");
	$("#loading").hide();
	
	var loadMore = function(){
		$(".clickLoadMore").hide();
		$("#loading").show();
		if(next){
			var path = window.location+"";
			var begin = $Dashboard.find("div.activity").length;
			
			var isq=$("#sq_sns_tag").val();
			if(begin%20==0 && begin>0){
					var tp = $("#ActivityType").val();
					var data = {"begin":begin};
					$.ajax({
						url : path,
						type : "post",
						async : false,
						data : data,
						success : function (rs){
							$("#loading").hide();
//							$Dashboard.append(rs);

							var items = $(rs);
							$('#conDetailWall').append( items ).masonry( 'appended', items );
						}
					});
					if($Dashboard.find("div.activity").length%20==0)
						$(".clickLoadMore").show();
					
					//排列item
//					$('#conDetailWall').masonry({itemSelector: '.item'});
//					$("#conDetailWall .itemFirst .content").jScrollPane()
			}else{
				next=false;
				$("#loading").hide();
				$(".clickLoadMore").hide();
			}
				
		}
	};
	
		
	$(".clickLoadMore").live("click",loadMore);
	
	
	var begin = $Dashboard.find("div.activity").length;
	if(begin%20==0 && begin>0){
		$(".clickLoadMore").show();
	}else{
		$(".clickLoadMore").hide();
	}
	
	$("#forwardM1").find("textarea").live("keyup",function(){
		var len=$(this).val().length;
		var num =196-len;
		$(this).parents(".popContent").find(".count").html("您还可以输入<span>"+num+"</span>字");
	});
	
	var sns_cid=0;
	var sns_user=0;
	$("#shareDiv").find("a").live("click",function(){
		var tp =$(this).attr("tp");
		if(tp=='m1'){
			if($("#userBar").attr("style")==undefined){
				gotoLogin("请登录后，才能转载内容！",1);
				return;
			}
			$("#forwardM1").find("textarea").val('');
			
			sns_cid=$(this).parents(".activity").find(".tools ").attr("cre");
			sns_user=$(this).parents(".activity").find(".tools ").attr("u");
			
			if(getCookie("magmeUserId")==sns_user){
				alert("请转载非自己的作品！");
				return;
			}
			
			var infoDialog = $("#forwardM1"),
			content = $("#fancybox-content");
			infoDialog.fancybox();
			
			$("#forwardM1").find(".ck_async").attr("checked",'true');
			return;
		}
		
		var $p=$(this).parents(".activity");
		var til=$p.find("h3").html();
		var _url=$p.find("a").attr("href");
		var imgsrc=null;
		var i=0;
		$p.find("img").each(function(){
			if(i==0){
				imgsrc=$(this).attr("src");	
			}
			i++;
		});
		readerShare(tp,_url,til,imgsrc);
	});
	$("#forwardM1").find(".btnGB").live("click",function(){
		
		var cval = $(this).parents(".popContent").find("textarea").val();
		if(sns_cid==0)
			return;
		if($(this).parents(".popContent").find(".ck_async").attr("checked")){
			if($.trim(cval)==''){
				alert("请输入评论");
				return;
			}
		}
		
		$.ajax({
			url : SystemProp.appServerUrl+"/sns/creative!forward.action",
			type : "POST",
			async : true,
			data : {"cid":sns_cid},
			dataType : 'json',
			success: function(rs){
				if(!rs)return;
				var code = rs.code;
			}
		});
		$.ajax({
			url : SystemProp.appServerUrl+"/sns/creative-comment!forward.action",
			type : "post",
			data : {"creative":sns_cid},
			dataType : 'json',
			success : function (rs){
			}
		});
		
		if($(this).parents(".popContent").find(".ck_async").attr("checked")){
			$.ajax({
				url : SystemProp.appServerUrl+"/sns/creative-comment!comment.action",
				type : "POST",
				data : {"creative":sns_cid,"commentval":cval},
				dataType : 'json',
				success: function(rs){
				}
			});
		}
		sns_cid=0;
		$(this).parents(".popContent").find("textarea").val('');
		$.fancybox.close();
	});
	
	$.fancybox.close(function(){alert(0);});
	
	$(".conReplySend").find(".btnGB").live("click",function(){
		var $obj=$(this);
		var eid = $obj.parents(".conReplySend").attr("cid");
		var cont = $.trim($obj.parents(".conReplySend").find("input").val());
		if(cont=='请输入您的评论' || cont==''){
			alert("请输入您的评论");
			return;
		}
		$.ajax({
			url:SystemProp.appServerUrl+"/index-detail!addCommentSns.action",
			type : "POST",
			async : true ,
			data : {"itemId":eid,"type":"event","content":cont},
			success: function(rs){
				$obj.parents(".forwardRight").find(".eventcomment").html(rs);
				
			}
		});
		$obj.parents(".conReplySend").find("input").val('');
	});
	
	$(".conUser").find("img").live("click",function(){
		var url=$(this).attr("url");
		if(url!=undefined && url!='')
			window.location.href=url;
	});
	
});
function fnSetHomeHeight(){
	var rightH = $(".sideRight").height();
	if($("#conDetailWall").height()<rightH){
		$("#conDetailWall").height(rightH);
	}
}
