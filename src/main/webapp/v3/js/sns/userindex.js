;$(function($){
	var uindex={
		init:function(){
			$(".iconHeart,.iconHearted").live("click", uindex.enjoy);
		}, 
		enjoy:function(){
			if(!hasUserLogin){
				gotoLogin("用户没有登录！");
				return;
			}
			var url ="";
			var condition;
			var cre=$(this).parent().attr("cre");
			var ct = $(this).attr("ct");
			var type=4;
			if(ct==9)
				type=3;
			var $this=this;
			$.ajax({
				url : SystemProp.appServerUrl+"/user-enjoy!changeJson.action",
				type : "POST",
				data : {"type":type,"objectId":cre},
				dataType : 'json',
				success: function(rs){
					if(!rs)return;
					var code = rs.code;
					if(code == 200){
						if($($this).hasClass("iconHearted")){
							$($this).addClass("iconHeart");
							$($this).removeClass("iconHearted");
						}else{
							$($this).removeClass("iconHeart");
							$($this).addClass("iconHearted");
						}
					}else if(code == 400){
						gotoLogin("用户没有登录！");
					}else{
						alert(rs.message);
					}
				}
			});
			
			
		}
	}
	uindex.init();
	

	$(".reply>.btnGB").live("click",function(){
		fnComment($(this));
	});

	var cflag=true;

	function fnComment($this){
		if($("#userBar").attr("style")==undefined){
			gotoLogin("用户没有登录！");
			cflag=true;
			return;
		}
		if(cflag){
			cflag=false;
			var cre=$this.parents(".theme").find(".content").find(".tools").attr("cre");
			var cval=$.trim($this.parent().find("input").val());
			if(cval==''){
				alert('请输入评论内容');
				cflag=true;
				return;
			}
			var ct = $this.parents(".theme").find(".content").find(".tools").attr("ct");
			if(ct==9){
				$.ajax({
					url : SystemProp.appServerUrl+"/index-detail!addEventComment.action",
					type : "POST",
					data : {"itemId":cre,"content":cval},
					dataType : 'json',
					success: function(rs){
						var commenthtm="";
						if(!rs)return;
						var code = rs.code;
						if(code == 200){
							var comment=rs.data.comment;
							if(comment!=null && comment!=''){
								for(var i=0;i<comment.length;i++){
									var c=comment[i];
									var time="",imgsrc="",subNick="";
									if(c.time==null || c.time=='')
										time=c.createTime.replace("T"," ");
									else
										time=c.time;
									if(c.nickname.length>12)
										subNick=c.nickname.substring(0,12);
									else
										subNick=c.nickname;
									if(c.avatar!=null && c.avatar!='')
										imgsrc = c.avatar.replace(c.userId+"_","60_"+c.userId+"_");
									commenthtm+="<div class='item clearFix'><span>"+time+"</span><img src='"+SystemProp.profileServerUrl+imgsrc+"'  onerror='this.src=\""+SystemProp.staticServerUrl+"/v3/images/head60.gif\"' class='head' title='"+c.nickname+"' /><p><strong>"+subNick+"</strong>"+c.content+"</p></div>";
								}
								$this.parent().find("input").val('');
									$this.parents(".comment").find(".item").each(function(){$(this).remove();})
								$this.parent().after(commenthtm);
								cflag=true;
							}
						}
					}
				});
			}else{
				$.ajax({
					url : SystemProp.appServerUrl+"/sns/creative-comment!comment.action",
					type : "POST",
					data : {"creative":cre,"commentval":cval},
					dataType : 'json',
					success: function(rs){
						var commenthtm="";
						if(!rs)return;
						var code = rs.code;
						if(code == 200){
							var comment=rs.data.comment;
							if(comment!=null && comment!=''){
								var avatar=$("#avatar30").attr("src");
								var nick=$("#nickName").html();
								//commenthtm+="<div class='item clearFix'><span>刚刚</span><img src='"+SystemProp.profileServerUrl+avatar+"'  onerror='this.src=\""+SystemProp.staticServerUrl+"/v3/images/head60.gif\"' class='head' /><p><strong>"+nick+"</strong>"+cval+"</p></div>";
								for(var i=0;i<comment.length;i++){
									var c=comment[i];
									var time="",imgsrc=SystemProp.staticServerUrl+"/v3/images/head60.gif",subNick;
									if(c.time==null || c.time=='')
										time=c.createTime.replace("T"," ");
									else
										time=c.time;
									
									if(c.avatar!=null && c.avatar!='')
										imgsrc = c.avatar.replace(c.userId+"_","60_"+c.userId+"_");
									//commenthtm+="<div class='item clearFix'><span>"+time+"</span><img src='"+SystemProp.profileServerUrl+imgsrc+"'  onerror='this.src=\""+SystemProp.staticServerUrl+"/v3/images/head60.gif\"' class='head' /><p><strong>"+c.nickname+"</strong>"+c.content+"</p></div>";
									if(c.nickname.length>12)
										subNick=c.nickname.substring(0,12);
									else
										subNick=c.nickname;
									if(i<3){
										var content=c.content;
										if(c.status==0){
											content="该评论已删除！";
										}
										commenthtm+="<div class='item clearFix'><span>"+time+"</span><img src='"+SystemProp.profileServerUrl+imgsrc+"'  onerror='this.src=\""+SystemProp.staticServerUrl+"/v3/images/head60.gif\"' class='head' title='"+c.nickname+"' /><p><strong>"+subNick+"</strong>"+content+"</p>";
										//是自己的评论，增加删除功能
										if(c.status==1 && rs.data.userId && rs.data.userId!='' && rs.data.userId>0 && rs.data.userId==c.userId){
											commenthtm+="<del dropcommentId='"+c.id+"' type='creative'></del>";
										}
										commenthtm+="</div>";
									}
									else
										commenthtm+="<div class='item itemMore'><a href='javascript:void();'>更多评论</a></div>";
								}
								$this.parent().find("input").val('');
	//							var comlen =$this.parents(".comment").find(".item");
	//							if(comlen.length==3){
	//								var i=0;
									$this.parents(".comment").find(".item").each(function(){$(this).remove();})
	//							}
								$this.parent().after(commenthtm);
								cflag=true;
							}
						}
					}
				});
			}
		}
		
	}
});
