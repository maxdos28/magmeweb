var e_url= SystemProp.appServerUrl+"/sns/creative!getEditCreativeEx.action";

function getEditWorksEx(){
	var c=$("#s_c_i_d").val();
	$.ajax({
		url : e_url,
		type : "POST",
		data : {"cid":c},
		dataType : 'json',
		success: function(rs){
			if(!rs)return;
			var code = rs.code;
			if(code == 200){
				var list=rs.data.list;
				for(var i in list){
					var ex = list[i];
					if(ex.conType==2){
						$(".conShareImages>.imagesCon").show();
						$("#imagesList>.inner").append("<div eid='"+ex.id+"' class='item itemComplete'><div class='inner'><a href='javascript:void(0);' class='close'></a><div class='loading'><strong></strong><span class='progress'><em>0%</em></span></div><div class='img'><div class='mask'></div><img  src='"+SystemProp.staticServerUrl+ex.imgPath+"' /></div><textarea>"+ex.content+"</textarea></div></div>").show();
						$(".body>.sideLeftMiddleRight>.conRight").css({height : $(".body>.sideLeftMiddleRight>.conLeftMiddle").height() - 8 + "px" });
					}else if(ex.conType==3){
						$(".conShareImages>.imagesCon").show();
						$("#imagesList>.inner").append("<div eid='"+ex.id+"' music='"+ex.path+"' class='item itemComplete itemMusic uncover'><div class='inner'><a href='javascript:void(0);' class='close'></a><div class='img'><div class='mask'><div class='ico png'></div></div><img src='"+ex.imgPath+"' /></div><textarea>"+ex.content+"</textarea></div></div>").show();
						$("#imagesList>.inner>.uncover>.img>img").coverImg().parents(".uncover").removeClass("uncover");
						$(".body>.sideLeftMiddleRight>.conRight").css({height : $(".body>.sideLeftMiddleRight>.conLeftMiddle").height() - 8 + "px" });
					}else if(ex.conType==4){
						$(".conShareImages>.imagesCon").show();
						$("#imagesList>.inner").append("<div eid='"+ex.id+"' play='"+ex.path+"' class='item itemComplete itemVideo uncover'><div class='inner'><a href='javascript:void(0);' class='close'></a><div class='img'><div class='mask'><div class='ico png'></div></div><img style='padding: 30px 0 0 20px;' src='"+ex.imgPath+"' /></div><textarea>"+ex.content+"</textarea></div></div>").show();
						$("#imagesList>.inner>.uncover>.img>img").coverImg().parents(".uncover").removeClass("uncover");
						$(".body>.sideLeftMiddleRight>.conRight").css({height : $(".body>.sideLeftMiddleRight>.conLeftMiddle").height() - 8 + "px" });
					}
				}
				$("#imagesList>.inner").find(".img>img").coverImg();
			}
			
		}
		
	});
	
	
	
}

function getEditVideoEx(){
	var c=$("#s_c_i_d").val();
	$.ajax({
		url : e_url,
		type : "POST",
		data : {"cid":c},
		dataType : 'json',
		success: function(rs){
			if(!rs)return;
			var code = rs.code;
			if(code == 200){
				var list=rs.data.list;
				for(var i in list){
					var ex = list[i];
					$(".myVideo").show();
					$(".myVideo").attr("picUrl",ex.imgPath);
					$(".myVideo").attr("title",ex.content);
					$(".myVideo").attr("play",ex.path);
					$(".myVideo").find("img").attr("src",ex.imgPath);
					$(".myVideo").find("p").html(ex.content);
				}
			}
		}
	});
}

function getEditMusicEx(){
	var c=$("#s_c_i_d").val();
	$.ajax({
		url : e_url,
		type : "POST",
		data : {"cid":c},
		dataType : 'json',
		success: function(rs){
			if(!rs)return;
			var code = rs.code;
			if(code == 200){
				var list=rs.data.list;
				for(var i in list){
					var ex = list[i];
					$(".myMusic").show();
					$(".myMusic>.player").html(ex.path);
					$("#musimg").attr("src",ex.imgPath);
				}
			}
		}
	});
}

function getEditImgEx(){
	var v=$("#_img_s_t").val().substr(1);
	var c=$("#s_c_i_d").val();
	$.ajax({
		url : e_url,
		type : "POST",
		data : {"cid":c},
		dataType : 'json',
		success: function(rs){
			if(!rs)return;
			var code = rs.code;
			if(code == 200){
				var list=rs.data.list;
				for(var i in list){
					var ex = list[i];
					$(".conShareImages>.imagesCon").show();
					$("#imagesList>.inner").append("<div class='item itemComplete'><div class='inner'><a href='javascript:void(0);' class='close'></a><div class='loading'><strong></strong><span class='progress'><em>0%</em></span></div><div class='img'><div class='mask'></div><img  src='"+ex.imgPath+"'  /></div><textarea>"+ex.content+"</textarea></div></div>").show();
					$(".body>.sideLeftMiddleRight>.conRight").css({height : $(".body>.sideLeftMiddleRight>.conLeftMiddle").height() - 8 + "px" });
				}
				$("#imagesList>.inner").find(".img>img").coverImg();
				var num = $("#imagesList>.inner>.item").length;
				if (num >= 2) {
					if (num > 10) {
						num = 10;
					}
					$("#imgLayout").show().find(".inner>.album").hide().parent(".inner").find(".album" + num).show().find(".item").eq(v-1).addClass("current").siblings().removeClass("current");
				} else {
					$("#imgLayout").hide();
				}
				
			}
		}
	});
}

function getEditTags(){
	var c=$("#s_c_i_d").val();
	$.ajax({
		url : SystemProp.appServerUrl+"/sns/creative!getEditCreativeTag.action",
		type : "POST",
		data : {"cid":c},
		dataType : 'json',
		success: function(rs){
			if(!rs)return;
			var code = rs.code;
			if(code == 200){
				var list=rs.data.list;
				
				for(var i in list){
					var tag=list[i];
					var f=0;
					$('#tags>li').each(function() {
						if ($(this).find("a").html()==tag.name){
							$(this).addClass("current onRead");
							f=1;
						}
					 });
					if(f==0){
						$("#addcurrent").prev().after("<li class='current onRead'><a href='javascript:void(0);'>"+tag.name+"</a></li>");
					}else{
						f=0;
					}
				}
				
			}
		}
	});
}

function getEditCreativeUser(){
	var c=$("#s_c_i_d").val();
	$.ajax({
		url : SystemProp.appServerUrl+"/sns/creative!getEditCreativeUser.action",
		type : "POST",
		data : {"cid":c},
		dataType : 'json',
		success: function(rs){
			if(!rs)return;
			var code = rs.code;
			if(code == 200){
				var list=rs.data.list;
				for(var i in list){
					if(i==0)$("#userList>.input>.open").addClass("open2");
					var u=list[i];
					$("#userList>.input>.inner").append("<span user='" + u.userId +"' >" +u.nickname + "<a href='javascript:void(0);'></a></span>");
				}
			}
		}
	});
}