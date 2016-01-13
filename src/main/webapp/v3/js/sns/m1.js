;$(function($){
	
	//delete comment 删除下拉评论
	$("del[dropcommentId]").unbind("click").live("click",function(){
		var commentId=$(this).attr("dropcommentId");
		var type=$(this).attr("type");
		$.ajax({
			url:SystemProp.appServerUrl+"/common-comment!delete.action",
			type : "get",
			data : {"id":commentId,"type":type},
			success: function(rs){
				if(rs.code!=200){
					alert(rs.message);
				}else{
					$("del[dropcommentId="+commentId+"][type="+type+"]").parent("div").remove();	
				}
				
			}
		});
	});
	
	//加为好友
	$(".atten,.cancel,.iconAdd,.iconCancel").live("click",function(){
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
						$(_$this).parents(".userHead").find(".btnGS").html('取消关注');
						$(_$this).parents(".userHead").find(".btnGS").attr("class","btnWS sns_btnWS");
						
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
							
							$(_$this).parents(".userHead").find(".btnWS").html('添加关注');
							$(_$this).parents(".userHead").find(".btnWS").attr("class","btnGS sns_btnGS");
						}else if(code == 400){
							gotoLogin("用户没有登录，请登录！");
						}else{
							alert(rs.message);
						}
					});
			    }
			}
		
	});
	
	//加为好友
	$(".sns_btnGS,.sns_btnWS").live("click",function(){
		if($("#userBar").attr("style")==undefined){
			gotoLogin("请登录后，才能添加关注哦！",1);
			return;
		}
		var _$this =this;
		var userId = $(this).attr("u");
		var nick=$(this).attr("nick");
		var tx=$(this).html();
		$(this).parent().find(".tool").remove();
			if(tx=='添加关注'){
				ajaxAddFollow(userId,1,function(rs){
					if(!rs)return;
					var code = rs.code;
					if(code == 200){
						$(_$this).html('取消关注');
						if($(_$this).hasClass("sns_btnGS")){
							$(_$this).attr("class","btnWS sns_btnWS ");
						}
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
							if($(_$this).hasClass("sns_btnWS")){
								$(_$this).attr("class","btnGS sns_btnGS");
							}
								
							$(_$this).html('添加关注');
						}else if(code == 400){
							gotoLogin("用户没有登录，请登录！");
						}else{
							alert(rs.message);
						}
					});
			    }
			}
		
	});

	//sendMsg-------------------------------------
	$(".sns_mes").live("click",function(e){
		e.preventDefault();
		if($("#userBar").attr("style")==undefined){
			gotoLogin("请登录后，才能发送消息！",1);
			return;
		}
		
		$("#sns_mes_userName").html($(this).attr("n"));
		$("#send").attr("userId",$(this).attr("u"));
		$("#userNewMsg").fancybox();

	});
	$(".iconMessage").live("click",function(e){
		e.preventDefault();
		if($("#userBar").attr("style")==undefined){
			gotoLogin("请登录后，才能发送消息！",1);
			return;
		}
		
		var _uid=$(".sideRight").find(".iconCancel").attr("u");
		$("#send").attr("userId",_uid);
		
		var _uname = $(".sideRight").find(".head").attr("title");
		$("#sns_mes_userName").html(_uname);
		$("#userNewMsg").fancybox();

	});

	$("#closePop").click(function(e){e.preventDefault();$.fancybox.close();});
	
	$("#send").click(function(e){
		e.preventDefault();
		var userId = $(this).attr("userId");
		var content = $("#msgContent").val();
		if(!content || content === '请输入消息内容'){
			alert('请输入消息内容');
			return;
		}
		//1-->send to user
		ajaxSendMsg(userId,1,content,function(rs){
			if(!rs) return;
			var code = rs.code;
			if(code == 200){
				$.fancybox.close();
				alert("发送成功！");
			}else if(code == 400){
				gotoLogin("用户没有登录，请登录！");
			}else{
				alert(rs.message);
			}
		});
	});
	//用户图片杂志页鼠标移入头像时显示功能菜单---------
	$('#Dashboard').find(".topBar").find("em").live("click",function(){
		window.open(SystemProp.appServerUrl+"/sns/authorize.action","_blank");
	});

	$(".conHead").find("em").live("click",function(){
		window.open(SystemProp.appServerUrl+"/sns/authorize.action","_blank");
	});
	$(".topBarVip>.inner>.head").find("em").live("click",function(){
		window.open(SystemProp.appServerUrl+"/sns/authorize.action","_blank");
	});

	$(".conHead").find("img").live("click",function(){
		var url=$(this).attr("url");
		if(url!=undefined && url!='')
			window.location.href=url;
		return false;
	});
	
	$(".author_list").find("img").live("click",function(){
		var url=$(this).attr("url");
		if(url!=undefined && url!='')
			window.location.href=url;
		return false;
	});


	$("#r_u_list").find(".more").live("click",authorScroll);
	var _uv=5,flagA=0;
	function authorScroll(){
		var ru=$(".author_list").length;
		
		if(flagA==0)
			$($(".author_list")[_uv-1]).removeClass("last");
		else{
			flagA=0;
			$($(".author_list")[ru-1]).removeClass("last");
		}
		
		if(ru>_uv){
			var c=0;
			for(var i=0;i<ru;i++){
				if(fv<ru && _uv<=i && _uv+5>i ){$($(".author_list")[i]).show();c++;}
				else $($(".author_list")[i]).hide();
			}
			for(var i=0;i<5-c;i++){
				$($(".author_list")[i]).show();
			}
			_uv+=5;
			if(_uv>=ru)_uv=_uv-ru;
			if(c<5){
				flagA=1;
				$($(".author_list")[ru-1]).addClass("last");
			}else{
				if(_uv==0){$($(".author_list")[ru-1]).addClass("last");flagA=1;}
				$($(".author_list")[_uv-1]).addClass("last");
			}
		}
	}
	$("#cre_list").find(".more").live("click",pcrvScroll);
	var fv=5, flagC=0;
	function pcrvScroll(){
		var ru=$(".pcrv_list").length;
		
		if(flagC==0)
			$($(".pcrv_list")[fv-1]).removeClass("last");
		else{
			flagC=0;
			$($(".pcrv_list")[ru-1]).removeClass("last");
		}
		
		if(ru>fv){
			var c=0;
			for(var i=0;i<ru;i++){
				if(fv<ru && fv<=i && fv+5>i ){$($(".pcrv_list")[i]).show();c++;}
				else $($(".pcrv_list")[i]).hide();
			}
			for(var i=0;i<5-c;i++){
				$($(".pcrv_list")[i]).show();
			}
			fv+=5;
			if(fv>=ru)fv=fv-ru;
			
			if(c<5){
				flagC=1;
				$($(".pcrv_list")[ru-1]).addClass("last");
			}else{
				if(fv==0){$($(".pcrv_list")[ru-1]).addClass("last");flagC=1;}
				$($(".pcrv_list")[fv-1]).addClass("last");
			}
		}
	}
	var sq_flag=true;
	/*$(".tagList>a").live("click",function(){
		if(sq_flag){
			sq_flag=false;
			$Dashboard = $("#Dashboard");
			var path = SystemProp.appServerUrl+"/sns/search.action";
			var begin = $Dashboard.find("div.theme").length;
			var key=$(this).html();
			var t="sns";
			var data = {"searchType":t,"queryStr":key};
			$.ajax({
				url : path,
				type : "post",
				async : false,
				data : data,
				success : function (rs){
					sq_flag=true;
					
					$("#loading").hide();
					$Dashboard.find(".theme").remove();
					if($("#sq_sns_tag").val()!=0){
						$Dashboard.after("<input type='hidden' id='sq_sns_tag' value='0' >");
						$Dashboard.after("<input type='hidden' id='sns_queryStr' value='"+key+"' >");
					}
					if($Dashboard.find(".topBar").length>0)
						$Dashboard.find(".topBar").after(rs);
					else
						$Dashboard.append(rs);
					
					scrolltotop.scrollup();
				}
			});
			var begin = $Dashboard.find("div.theme").length;
			if(begin%5==0 && begin>0){
				$(".clickLoadMore").show();
			}else{
				$(".clickLoadMore").hide();
			}
		}
		
	});*/
	
	$(".conMorePic").find("a").live("click",function(){
		window.location.href= $(this).attr("url");
		return false;
	});
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
			
			sns_cid=$(this).parents(".theme").find(".tools ").attr("cre");
			sns_user=$(this).parents(".theme").find(".head ").attr("u");
			
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
		
		var $p=$(this).parents(".theme");
		var til=$p.find(".til").attr("til");
		var _url=$p.find(".til").attr("url");
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
		var addcommentcallback=function(rs){
			var nullinfo=$("div>.nullInfo");
			//由空评论过来
			if(nullinfo && $(nullinfo).length>0){
				$obj.parents(".forwardRight").find(".conReplyOuter").remove();
				$obj.parents(".forwardRight").append(rs);
			}else{
				
			}
				
		};
		$.ajax({
			url:SystemProp.appServerUrl+"/index-detail!addCommentSns.action",
			type : "POST",
			async : true ,
			data : {"itemId":eid,"type":"event","content":cont},
			success: function(rs){
				var nullinfo=$obj.parent(".conReplySend").parent(".forwardRight").find(".nullInfo");
				//不是空评论页面过来
				if(nullinfo && $(nullinfo).length>0){
					$obj.parent(".conReplySend").parent(".forwardRight").find(".nullInfo").remove();
				}else{
					$obj.parent(".conReplySend").parent(".forwardRight").find(".conReplyOuter").remove();
				}
				$obj.parent(".conReplySend").parent(".forwardRight").prepend(rs);
					
			}
		});
		$obj.parents(".conReplySend").find("input").val('');
	});
	
	
	var comflag=true;
	$(".comment").find(".itemMore").find("a").live("click",function(){
		if(comflag){
			comflag=false;
			var $obj=$(this);
			var cre = $obj.parents(".theme").find(".tools").attr("cre");
			var begin = $obj.parents(".comment").find(".clearFix").length;
			$.ajax({
				url : SystemProp.appServerUrl+"/sns/creative-comment!commentList.action",
				type : "POST",
				data : {"creative":cre,"begin":begin},
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
								var time="",imgsrc="",subNick;
								if(c.time==null || c.time=='')
									time=c.createTime.replace("T"," ");
								else
									time=c.time;
								
								if(c.avatar!=null && c.avatar!='')
									imgsrc = c.avatar.replace(c.userId+"_","60_"+c.userId+"_");
								if(c.nickname.length>12)
									subNick=c.nickname.substring(0,12);
								else
									subNick=c.nickname;
								if(i<3){
									var content=c.content;
									if(c.status==0){
										content='该评论已删除！';
									}
									commenthtm+="<div id='chenxiaoabcde' class='item clearFix'><span>"+time+"</span><img src='"+SystemProp.profileServerUrl+imgsrc+"'  onerror='this.src=\""+SystemProp.staticServerUrl+"/v3/images/head60.gif\"' class='head' title='"+c.nickname+"' /><p><strong>"+subNick+"</strong>"+content+"</p>";
									//是自己的评论，增加删除功能
									if(c.status==1 && rs.data.userId && rs.data.userId!='' && rs.data.userId>0 && rs.data.userId==c.userId){
										commenthtm+="<del dropcommentId='"+c.id+"' type='creative'></del>";
									}
									commenthtm+="</div>";
								}
								
								
							}
							$obj.parents(".comment").find(".clearFix").last().after(commenthtm);
							if(comment.length<4)
								$obj.parents(".comment").find(".itemMore").remove();
							comflag=true;
						}
					}
				}
			});
			}
		});
	
});


//加载用户信息功能
function fnLoadUserInfo($this){
	var u = $this.find("a").attr("u");
	if(u==undefined)
		return;
	$this.append("<div class='tool'><div class='outer'></div></div>");
	$.ajax({
		url : SystemProp.appServerUrl+"/sns/public-sns!uInfo.action",
		type : "POST",
		data : {"userId":u},
		dataType : 'json',
		success: function(rs){
			if(!rs)return;
			var code = rs.code;
			if(code == 200){
				var info =rs.data.info;
				var ism="",nikc=info.nickname,subNick="";
				if(info.ism=='M'){
					ism="<span>麦米认证编辑</span>";
					if(info.nameZh!=null)
						nikc=info.nameZh;
				}
				if(nikc.length>12)
					subNick=nikc.substring(0,12);
				else
					subNick=nikc;
					
				
				html = "<div class='inner'><strong class='name' title='"+nikc+"'>"+subNick+ism+"</strong><div class='company'>"+info.publisher+"<span>"+info.office+"</span></div><div class='count'><p>关注：<span>"+info.attention+"</span></p><p>粉丝：<span>"+info.fans+"</span></p><p>内容：<span>" +
						"<a href='javascript:void(0)'>"+info.creativeCount+"</a></span></p></div><div class='info'>"+info.info+"</div>  </div> ";
				if(rs.data.self!=1){
					html+="<div class='ft'><a class='sns_mes message' n='"+info.nickname+"' u='"+info.id+"' href='javascript:void(0)'>发消息</a>";
					if(info.isF>0){
						if(info.id!=9999)
							html+="<a nick='"+info.nickname+"' u='"+info.id+"' class='cancel' href='javascript:void(0)'>取消关注</a>";
						else
							html+="<a href='javascript:void(0)'></a>";
					}else
						html+="<a nick='"+info.nickname+"' u='"+info.id+"' class='atten' href='javascript:void(0)'>加关注</a>";
					html+="</div>";
				}
				$this.find(".tool>.outer").append(html);	
			}else{
				alert(rs.message);
			}
		}
	});
//		setTimeout(function(){$this.find(".tool>.outer").append(html)},1000);
}

//加载评论方法
function fnLoadMore($this){
	if($this.parents(".theme").find(".moreInfo").length==0){
		$this.parents(".content").after("<div class='moreInfo clearFix'><div class='topArrow'></div><div class='loading'></div><div class='close'><a href='javascript:void(0)'>收起</a></div></div>");
		//加载评论
		fnLoadSlide($this);
		//请程序员自己更改下面代码
		setTimeout(function(){
			var tags="",commenthtm="",samehtm="",otherhtm="",publisherhtm="",enjoyhtm="",
			publisher=$.trim($this.parents(".theme").find(".publisher").attr("text")),
			u=$this.parents(".theme").find(".userHead").find("a").attr("u");
			$this.parents(".content").find(".tagList").find("a").each(function(){
				tags+='"'+$(this).html()+'",';
			});
			if(u==undefined || u==0 || u==null)
				u=$("#_c_u_flag").val();
			var cid=$this.parent().attr("cre");
			tags=tags.substring(0,tags.lastIndexOf(","));
			var ct = $this.attr("ct");
			if(ct==9){
				$.ajax({
					url : SystemProp.appServerUrl+"/index-detail!getEventCommentList.action",
					type : "POST",
					data : {"itemId":cid},
					dataType : 'json',
					success: function(rs){
						if(!rs)return;
						var code = rs.code;
						if(code == 200){
							var comment=rs.data.comment;
							if(comment!=null && comment!=''){
								for(var i=0;i<comment.length;i++){
									var c=comment[i],subNick='';
									var time="",imgsrc="";
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
									if(i<3)
										commenthtm+="<div class='item clearFix'><span>"+time+"</span><img src='"+SystemProp.profileServerUrl+imgsrc+"'  onerror='this.src=\""+SystemProp.staticServerUrl+"/v3/images/head60.gif\"' class='head' title='"+c.nickname+"' /><p><strong>"+subNick+"</strong>"+c.content+"</p></div>";
								}
							}
							var apHtml="<div class='comment'><div class='reply'><input class='input' type='text' tips='发表您的评论' /><a class='btnGB' href='javascript:void(0)'>评论</a></div>"+commenthtm+"</div>";
							$this.parents(".content").next().find(".topArrow").after(apHtml);
							$this.parents(".theme").find(".moreInfo .loading").hide();
						}else{
							alert(rs.message);
						}
					}
				});
			}else{
				$.ajax({
					url : SystemProp.appServerUrl+"/sns/creative-comment.action",
					type : "POST",
					data : {"tags":tags,"creative":cid,"uid":u,"publisher":publisher},
					dataType : 'json',
					success: function(rs){
						if(!rs)return;
						var code = rs.code;
						if(code == 200){
							var comment=rs.data.comment;
							if(comment!=null && comment!=''){
								for(var i=0;i<comment.length;i++){
									var c=comment[i];
									var time="",imgsrc="",subNick;
									if(c.time==null || c.time=='')
										time=c.createTime.replace("T"," ");
									else
										time=c.time;
									
									if(c.avatar!=null && c.avatar!='')
									 imgsrc = c.avatar.replace(c.userId+"_","60_"+c.userId+"_");
									if(c.nickname.length>12)
										subNick=c.nickname.substring(0,12);
									else
										subNick=c.nickname;
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
										
									}else{
										commenthtm+="<div class='item itemMore'><a href='javascript:void(0);'>更多评论</a></div>";
									}
								}
							}
							var same =rs.data.same;
							if(same!=null && same!=''){
								samehtm="<div class='sameTag'><h3>同类标签</h3><div class='clearFix'>";
								for(var i=0;i<same.length;i++){
									var s=same[i];
									if(s.conType==2)
										samehtm+="<a url='"+s.creativeId+"' href='"+SystemProp.appServerUrl+"/sns/c"+s.creativeId+"/'><img src='"+SystemProp.staticServerUrl+prefix(s.imgPath,'100_')+"' /></a>";
									else
										samehtm+="<a url='"+s.creativeId+"' href='"+SystemProp.appServerUrl+"/sns/c"+s.creativeId+"/'><img src='"+s.imgPath+"' /></a>";
								}
								samehtm+="</div></div>";
							}
							same=null;
							var other =rs.data.creOther;
							if(other!=null && other!=''){
								otherhtm+="<div class='sameUser'><h3>来自<strong>用户</strong>的其它内容</h3><div class='clearFix'>";
								for(var i=0;i<other.length;i++){
									var o=other[i];
									if(o.conType==2)
										otherhtm+="<a url='"+o.creativeId+"' href='"+SystemProp.appServerUrl+"/sns/c"+o.creativeId+"/'><img src='"+SystemProp.staticServerUrl+prefix(o.imgPath,'100_')+"' /></a>";
									else
										otherhtm+="<a url='"+o.creativeId+"' href='"+SystemProp.appServerUrl+"/sns/c"+o.creativeId+"/'><img src='"+o.imgPath+"' /></a>";
								}
								otherhtm+="</div></div>";
							}
							other=null;
							var publisherlist=rs.data.publisher;
							if(publisherlist!=null && publisherlist!=''){
								publisherhtm="<div class='sameMagazine'><h3><strong>&nbsp;[&nbsp;"+publisher+"&nbsp;]&nbsp;</strong>的其它内容</h3><div class='clearFix'>";
								for(var i=0;i<publisherlist.length;i++){
									var p=publisherlist[i];
									if(p.conType==2)
										publisherhtm+="<a url='"+p.creativeId+"' href='"+SystemProp.appServerUrl+"/sns/c"+p.creativeId+"/'><img src='"+SystemProp.staticServerUrl+prefix(p.imgPath,'100_')+"' /></a>";
									else
										publisherhtm+="<a url='"+p.creativeId+"' href='"+SystemProp.appServerUrl+"/sns/c"+p.creativeId+"/'><img src='"+p.imgPath+"' /></a>";
								}
								publisherhtm+="</div></div>";
							}
							
							
							var enjoy=rs.data.enjoy;
							if(enjoy!=null && enjoy!=''){
								enjoyhtm="<div class='sameLike'><h3><strong>"+enjoy[0].num+"</strong>个喜欢</h3><div class='clearFix'>";
								for(var i=0;i<enjoy.length;i++){
									var e=enjoy[i],imgsrc="";
									if(e.avatar!=null && e.avatar!='')
										 imgsrc = e.avatar.replace(e.userId+"_","60_"+e.userId+"_");
									enjoyhtm+="<a url='"+e.userId+"' title='"+e.nickname+"' href='"+SystemProp.appServerUrl+"/sns/u"+e.userId+"/'><img src='"+SystemProp.profileServerUrl+imgsrc+"'  onerror='this.src=\""+SystemProp.staticServerUrl+"/v3/images/head60.gif\"' /></a>";
								}
								
								enjoyhtm+="</div>";
								//<span class='more'>更多喜欢999+</span>
							}
							enjoy=null;
							
							//转发
							var forward=rs.data.forward;
							var fcount = rs.data.forwardCount;
							var forwardhtm="";
							if(forward!=null && forward!=''){
								forwardhtm="<div class='sameForward'><h3><strong>"+fcount+"</strong>个转发</h3><div class='clearFix'>" ;
								
								for(var i=0;i<forward.length;i++){
									var f=forward[i],imgsrc="",subNick="";
									if(f.avatar!=null && f.avatar!='')
										imgsrc = f.avatar.replace(f.userId+"_","60_"+f.userId+"_");
									if(f.nickname.length>12)
										subNick=f.nickname.substring(0,12);
									else
										subNick=f.nickname;
									forwardhtm+="<a url='"+f.userId+"' href='"+SystemProp.appServerUrl+"/sns/u"+f.userId+"/'><img src='"+SystemProp.profileServerUrl+imgsrc+"'  onerror='this.src=\""+SystemProp.staticServerUrl+"/v3/images/head60.gif\"' title='"+f.nickname+"' /><p><strong>"+subNick+"</strong>转发了你的内容</p></a>";
								}
								forwardhtm+="</div>";
								if(fcount>10){
									forwardhtm+="<span class='more'>更多转发"+fcount-10+"+</span>";
								}
								forwardhtm+="</div>";
							}
							var apHtml="<div class='comment'><div class='reply'><input class='input' type='text' tips='发表您的评论' /><a class='btnGB' href='javascript:void(0)'>评论</a></div>"+commenthtm+"</div>"+samehtm+otherhtm+publisherhtm+forwardhtm+enjoyhtm;
							$this.parents(".content").next().find(".topArrow").after(apHtml);
							$this.parents(".theme").find(".moreInfo .loading").hide();
						}else{
							alert(rs.message);
						}
					}
				});
			}
			
		},500);
	}else{
		fnLoadSlide($this);
	}
	
}

//评论下拉效果
function fnLoadSlide($this){
	$this.parents(".theme").data("top", $this.parents(".theme").offset().top-75);
	if($this.parents(".theme").find(".moreInfo").hasClass("open")){
		$this.removeClass("iconMoreInfoCurrent").parents(".theme").find(".moreInfo").removeClass("open").slideUp(300).find(".topArrow").fadeOut(200);
		$(window).scrollTo($this.parents(".theme").data("top"),300);
	}else{
		$this.addClass("iconMoreInfoCurrent").parents(".theme").find(".moreInfo").addClass("open").slideDown(300).find(".topArrow").hide().delay(300).fadeIn(200);
	}

}

function prefix(v,pre){
	if(v!='' && pre!=''){
		 var index=v.lastIndexOf("/");
		return v.substring(0,index+1)+pre+v.substring(index+1);
	}
}

//播放视频
function fnPlayVideo($objThis){
	$obj = $objThis.parent();
	var path=$objThis.attr("path"),video="";
	
	if(/.*player.youku.com.*/.test(path)){
		video="<embed src='"+path+"' allowFullScreen='true' quality='high' flashvars='autoPlay=true&isAutoPlay=true&auto=1' align='middle' allowScriptAccess='always' type='application/x-shockwave-flash'></embed>";
	}else if(/.*player.ku6.com.*/.test(path)){
		video="<embed src='"+path+"' allowscriptaccess='always' allowfullscreen='true' flashvars='autoPlay=true&isAutoPlay=true&auto=1' type='application/x-shockwave-flash' flashvars='from=ku6'></embed>";
	}else if(/.*www.tudou.com.*/.test(path)){
		video="<embed src='"+path+"' type='application/x-shockwave-flash' flashvars='autoPlay=true&isAutoPlay=true&auto=1' allowscriptaccess='always' allowfullscreen='true' wmode='opaque'></embed>"
	}
	
	
	$obj.find("a,img").hide().end().append("<a class='closeVideo'>关闭视频</a>"+video);
	$obj.next().hide();
	//避免firefox下点击embed外层的a标签出发事件，把视频放在空白a标签之后
	//$magmeOpenItem.html("").after(tempData.video[0].html);
}


function strLen(len,id){
	var l=0;
	var str=$(id).val();
	for(var i=0;i<str.length;i++){
		if(str[i].charCodeAt(0)<299)
			l++;
		else
			l+=2;
	}
	return l;
}

function loadMoreAjax(queryStr,searchType,currentPage){
	var search = {};
	search.queryStr = queryStr;
	search.searchType = searchType;
	search.currentPage = currentPage;
	
	var callback  = function(result){
		if(result.data){
			var tmpHtml = "";
			if(searchType=='User'){
				if(result.data.itemList){
					$.each(result.data.itemList,function(k,user){
						var aUrl = "";
						var imagUrl = "images/head60.gif";
						if(user.id==session_userId){
							aUrl = "/sns/user-index.action";
						}else{
							aUrl = "/sns/u"+user.id+"/";
						}
						if(user.avatar){
							imagUrl = imgPath+user.avatar60;
						}
						
						tmpHtml += "<div class='item'><div class='userHead'>";
						
						tmpHtml += "<a class='head' u='"+user.id+"' href='"+aUrl+"' >";
						tmpHtml += "<img class='userHeadImage' src='"+imagUrl+"' />";
						tmpHtml += "</a></div><strong>"+user.nickName+"</strong>";
						var uProvince="";
						var uCity="";
						if(user.province){uProvince=user.province}
						if(user.city){uCity=user.city}
						tmpHtml += "<p><span>"+uProvince+"</span>"+uCity+"</p></div>";
					});
					$("#userWallUser").append(tmpHtml);
				}
			}else if(searchType=='Issue'){
				if(result.data.itemList){
					$.each(result.data.itemList,function(k,issue){
						var imagUrl = issuePath+"/"+issue.publicationId+"/172_"+issue.id+".jpg";
						tmpHtml += "<div class='item'>";
						tmpHtml += "<a target='_blank' class='photo' href='"+appPath+"/publish/mag-read.action?id="+issue.id+"'>";
						tmpHtml += "<img src='"+imagUrl+"' />";
						tmpHtml += "<h5>"+issue.publicationName+issue.issueNumber+"<span>&nbsp;</span></h5><sup class='album'></sup></a></div>";
					});
					$("#magazineWallPublication").append(tmpHtml);
				}
			}else if(searchType=='Creative'){
				if(result.data.itemList){
				var itemList = result.data.itemList;
					$.each(result.data.itemList,function(k,m1){
						var content = m1.content;
						if(content){
							if(content.length>150){
								content = content.substring(0,150)+"……";
								 m1.content = content;
							}
						}
						if(!m1.high){
							m1.high=1;
						}
						if(!m1.width){
							m1.width=1;
						}
						var imgHeight = (m1.high * 210 / m1.width);
						m1.cType = imgHeight;
						if(m1.imagePath && m1.imagePath.length>0){
							m1.imagePathTopic="";
							m1.imagePathTag="<img height='"+imgHeight+"' src='"+staticPath+m1.imagePath+"' alt='"+m1.title+"'/>";
						}else{
							m1.imagePathTopic=" itemNopic";
							m1.imagePathTag="";
						}
						if(!m1.secondTitle){
							if(m1.title){
								m1.secondTitle=m1.title;
							}else{
								m1.secondTitle="";
							}
							
						}
						if(!m1.secondDesc){
							if(m1.described){
								m1.secondDesc=m1.described;
							}else{
								m1.secondDesc="";
							}
						}
						tmpHtml += "<div class='item "+m1.imagePathTopic+"'><a href='http://www.magme.com/sns/c"+m1.id+"' target='_blank'>";
						tmpHtml += "<div class='photo'>"+m1.imagePathTag+"</div>";
						tmpHtml += "<div class='info png'><h2>"+m1.secondTitle+"</h2><p>"+m1.secondDesc+"</p></div></a></div>";
					});
					//tmpHtml += "<div class='item ${imagePathTopic}'><a href='"+appPath+"/sns/c${id}' target='_blank'>";
					//tmpHtml += "<div class='photo'>${imagePathTag}</div>";
					//tmpHtml += "<div class='info png'><h2>${secondTitle}</h2><p>${secondDesc}</p></div></a></div>";
					//$("#homeWallCreative").append(tmpHtml).masonry( 'appended',tmpHtml);
					
					//var $creative = $.tmpl(tmpHtml,itemList);
					$homeWallCreative=$("#homeWallCreative");
					//$creative.appendTo($homeWallCreative);
					$tmpHtmljq=$(""+tmpHtml);
					$tmpHtmljq.appendTo($homeWallCreative);
					//itemsHov($creative);
					//itemHoverIE6();
					
					$homeWallCreative.masonry('appended', $tmpHtmljq);
				}
			}
			
		}else{
			if(searchType='Issue'){$("#loadMorePublication").hide();}
			if(searchType='User'){$("#loadMoreUser").hide();}
			if(searchType='FpageEvent'){$("#loadMoreFpageEvent").hide();}
			if(searchType='Creative'){$("#loadMoreCreative").hide();}
		}
	};
	$.ajax({
		url : SystemProp.appServerUrl+"/search!searchAjax.action",
		type : "POST",
		dataType:'json',
		data : search,
		success: callback
	});
}
