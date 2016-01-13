;$(document).ready(function(){
	//滚动加载---------------------------start
	var next=true;
	$Dashboard = $("#Dashboard");
	$("#loading").hide();
	
	var loadMore = function(){
		$(".clickLoadMore").hide();
		$("#loading").show();
		if(next){
			var path = SystemProp.appServerUrl+"/sns/article!mindexMore.action";
			var begin = $Dashboard.find("div.theme").length;
			if(begin%5==0 && begin>0){
				var data = {"begin":begin,"userId":$("#userIdForIndex").val()};
				$.ajax({
					url : path,
					type : "post",
					async : false,
					data : data,
					success : function (rs){
						$Dashboard.append(rs);
						$("#loading").hide();
						$(".clickLoadMore").hide();
						$(".sideLeft .theme .conPhoto .conPlay a img").coverImg();
						$("#bg img").coverImg();
					}
				});
				if($Dashboard.find("div.theme").length%5==0){
					$(".clickLoadMore").show();
				}
			}else{
				next=false;
				$("#loading").hide();
				$(".clickLoadMore").hide();
			}
		}
	};
	
		
	$(".clickLoadMore").live("click",loadMore);
	
	
	var begin = $Dashboard.find("div.theme").length;
	if(begin%5==0 && begin>0){
		$(".clickLoadMore").show();
	}else{
		$(".clickLoadMore").hide();
	}
	//滚动加载---------------------------end
	
	//加载评论---------------------------start
    $("em[commentCreativeId]").unbind("click").live("click",function(){
    	var cid=$(this).attr("commentCreativeId");
    	var $obj=$(this);
    	$.ajax({
    		url : SystemProp.appServerUrl+"/sns/creative-comment!commentList.action",
    		type : "POST",
    		data : {"creative":cid},
    		dataType : 'json',
    		success: function(rs){
    			if(!rs)return;
    			var code = rs.code;
    			var commenthtm="";
    			if(code == 200){
    				var comment=rs.data.comment;
    				if(comment!=null && comment!=''){
    					for(var i=0;i<comment.length;i++){
    						var c=comment[i];
    						var time="",imgsrc="",subNick;
    						if(c.time==null || c.time==''){
    							time=c.createTime.replace("T"," ");
    						}else{
    							time=c.time;
    						}
    						
    						if(c.avatar!=null && c.avatar!=''){
    						    imgsrc = c.avatar.replace(c.userId+"_","60_"+c.userId+"_");
    						}
    						if(c.nickname.length>12){
    							subNick=c.nickname.substring(0,12);
    						}else{
    							subNick=c.nickname;
    						}
    						if(i<3){
    							var content=c.content;
    							if(c.status==0){
    								content='该评论已删除';
    							}
    							commenthtm+="<div class='item clearFix'><span>"
    								+time+"</span><img src='"+SystemProp.profileServerUrl+imgsrc+"'  onerror='this.src=\""+SystemProp.staticServerUrl
    								+"/v3/images/head60.gif\"' class='head' title='"
    								+c.nickname+"' /><p><strong>"+subNick+"</strong>"+content+"</p>";
    							//是自己的评论，增加删除功能
    							if(c.status==1 && rs.data.userId && rs.data.userId!='' && rs.data.userId>0 && rs.data.userId==c.userId){
    								commenthtm+="<del dropcommentId='"+c.id+"' type='creative'></del>";
    							}
    							commenthtm+="</div>";
    							
    						}
    						/*else{
    							commenthtm+="<div class='item itemMore'><a href='javascript:void(0);'>更多评论</a></div>";
    						}*/
    					}
    				}
    				
    			}else{
    				//alert(rs.message);
    			}
    			commenthtm="<div class='comment'><div class='reply'><input class='input' type='text' tips='发表您的评论'>" +
				"<a class='btnGB' href='javascript:void(0)'>评论</a></div>"+commenthtm+"</div>";
				$obj.parents(".content").find(".moreInfo").remove();
				var str="<div class='moreInfo clearFix open' style='display: block;'><div class='topArrow'></div>" +commenthtm+
				"<div class='close'><a closeCommment='' href='javascript:void(0)'>收起</a></div></div>";
				$obj.parents(".content").next().remove();
				$obj.parents(".content").after(str);
		    	}
    	});
    });
	
    //关闭评论
    $("a[closeCommment='']").unbind("click").live("click",function(e){
    	e.preventDefault();
    	$(this).parents(".moreInfo").remove();
    });
	//加载评论---------------------------end
	
    //删除作品
    $(".delete").live("click",function() {
    	if($(this).hasClass('delete')){
    		var _$this=this;
    		var cre=$(_$this).parents(".theme").find(".content").find(".tools").attr("cre");
    		if(confirm("删除后不能恢复，确定要删除吗？"))
    	    {
    			$.ajax({
    				url :SystemProp.appServerUrl+"/sns/article!delCreativeJson.action",
    				type : "POST",
    				data : {"creativeId":cre},
    				dataType : 'json',
    				success: function(rs){
    					if(!rs)return;
    					var code = rs.code;
    					if(code == 200){
    						$(_$this).parents(".theme").remove();
    						}else{
    							alert(rs.mess);
    						}
    					}
    				});
    		    }
    		}
    });
	
});