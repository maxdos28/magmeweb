;$(function($){
	var uindex={
		init:function(){
			$(".iconHeartBig,.favCurrent").live("click", uindex.enjoy);
		},
		enjoy:function(){
			if(!hasUserLogin){
				gotoLogin("用户没有登录！",1);
				return;
			}
			var url ="";
			var condition;
			var cre=$(this).parent().attr("cre");
			var $this=this;
			$.ajax({
				url : SystemProp.appServerUrl+"/user-enjoy!changeJson.action",
				type : "POST",
				data : {"type":4,"objectId":cre},
				dataType : 'json',
				success: function(rs){
					if(!rs)return;
					var code = rs.code;
					if(code == 200){
						if($($this).hasClass("favCurrent")){
							$($this).addClass("iconHeartBig");
							$($this).removeClass("favCurrent");
						}else{
							$($this).addClass("favCurrent");
						}
					}else if(code == 400){
						gotoLogin("用户没有登录！",1);
						
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
			gotoLogin("用户没有登录！",1);
			cflag=true;
			return;
		}
		if(cflag){
			cflag=false;
			var cre=$this.parents(".theme").find(".content").find(".tools").attr("cre");
			var cval=$.trim($this.parent().find("input").val());
			if(cval==''){
				alert('请输入评论内容',1);
				cflag=true;
				return;
			}
			
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
						var invite = rs.data.no_invite_code;
						if(invite==1){
							/*if(confirm("抱歉！M1频道需要验证邀请码才能评论。是否验证！"))
						    {
						        window.location.href= SystemProp.appServerUrl+"/sns/sns-index!invite.action";
						    }*/
						}else{	
							var comment=rs.data.comment;
							if(comment!=null && comment!=''){
								var avatar=$("#avatar30").attr("src");
								var nick=$("#nickName").html();
								//commenthtm+="<div class='item clearFix'><span>刚刚</span><img src='"+SystemProp.profileServerUrl+avatar+"'  onerror='this.src=\""+SystemProp.staticServerUrl+"/v3/images/head60.gif\"' class='head' /><p><strong>"+nick+"</strong>"+cval+"</p></div>";
								for(var i=0;i<comment.length;i++){
									var c=comment[i];
									var time="",imgsrc="";
									if(c.time==null || c.time=='')
										time=c.createTime;
									else
										time=c.time;
									
									if(c.avatar!=null && c.avatar!='')
										imgsrc = c.avatar.replace(c.userId+"_","60_"+c.userId+"_");
									commenthtm+="<div class='item clearFix'><span>"+time+"</span><img src='"+SystemProp.profileServerUrl+imgsrc+"'  onerror='this.src=\""+SystemProp.staticServerUrl+"/v3/images/head60.gif\"' class='head' /><p><strong>"+c.nickname+"</strong>"+c.content+"</p>";
									//是自己的评论，增加删除功能
									if(c.status==1 && rs.data.userId && rs.data.userId!='' && rs.data.userId>0 && rs.data.userId==c.userId){
										commenthtm+="<del dropcommentId='"+c.id+"' type='creative'></del>";
									}
									commenthtm+="</div>";
								}
								$this.parent().find("input").val('');
								$this.parents(".comment").find(".item").each(function(){$(this).remove();})
								$this.parent().after(commenthtm);
								cflag=true;
							}
						}
					}
				}
			});
		}
		
	}
	
	$("#snsbtnWB").live("click",function(){
		//$("#userLogin").click();
	});
	$("#snsbtnGB").live("click",function(){
		//$("#userReg").click();
	});
});
