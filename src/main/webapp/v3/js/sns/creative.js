;$(function($){
	var flag=true;
	var creative={
		init:function(){
			$("#btnGB_send").live("click", creative.btnSend);
			$("#btnWB_view").live("click", creative.btnView);
		},
		btnSend:function(){
			if(flag){
				flag=false;
			}else{
				return;
			}
			var ti=$.trim($("#cr-text-title").val()),
			ve=$.trim($("#cr-text-content").val()),
			mgur=$("#cr-text-magazineUrl").val(),
			mgne=$("#magazineName").val(),
			issueid=$("#issueid").val(),
			publicationid=$("#publicationid").val(),
			ploy=0,
			method=$("#cr_operate").val(),
			c=$("#s_c_i_d").val(),
			tag={},crus={},condition,i=0;
			
			if($("#cr-cb-ploy").attr("checked"))ploy=1;
			if(ti==''){
				alert("给你的发言起一个标题吧！");//$("#cr-text-titleError").show();
				flag=true;
				return;
			}else
				$("#cr-text-titleError").hide();
			
			if( mgur!='' && mgne==''){
				alert("抱歉，杂志链接错误并且只支持本站中杂志链接");
				flag=true;
				return;
			}else
				$("#magazineUrlError").hide();
			
			if(method!=''){
				var _url=SystemProp.appServerUrl+"/sns/creative!"+method+".action";
				var _showType=1;
				condition = {"cid":c,"title":ti,"content":ve,"magazineUrl":mgur,"magazineName":mgne,"creativeUser":crus,"tags":tag,"ploy":ploy};
				
				if(method=="image"){
					var imgarr={};
					i=0;
					$('#imagesList>.inner>div').each(function() {
						var imgsrc=$(this).find("img").attr("src");
						var w=$(this).find("img").attr("width");
						var h=$(this).find("img").attr("height");
						var con=$(this).find("textarea").val().replace(';','；');
						
						if(w==undefined)
							w=$(this).find("img").width();
						if(h==undefined)
							h=$(this).find("img").height();
						
						var imgList=imgsrc+";"+con+";"+i+";"+w+";"+h;
						imgarr[i]=imgList;
						i++;
					});
					if(i==0){
						alert("请上传图片");//$("#ImagesError").show();
						flag=true;
						return;
					}else{
						$("#ImagesError").hide();
					}
					
					if(i>0){
						if(i>10)i=10;
						var _i=1;
						$("#imgLayout>.inner>div").each(function(){
							_i++;
							if(i==_i){
								var j=1;
								$(this).find("a").each(function(){
									if($(this).hasClass("current")){
										_showType=i+""+j;
									}
									j++;
								});
							}
							
						});
					}
					condition=null;
					condition = {"cid":c,"title":ti,"content":ve,"magazineUrl":mgur,"magazineName":mgne,"creativeUser":crus,"tags":tag,"ploy":ploy,"conList":imgarr,"showType":_showType};
				}else if(method=="music"){
					$("#cr-music-Error").hide();
					var conlist=$(".myMusic").find("img").attr("src")+";"+$(".myMusic>.player").html();
					if($(".myMusic>.player").html()==''){
						$("#cr-music-Error").show();
						flag=true;
						return;
					}
					condition=null;
					condition = {"cid":c,"title":ti,"content":ve,"magazineUrl":mgur,"magazineName":mgne,"creativeUser":crus,"tags":tag,"ploy":ploy,"conList":conlist};
				}else if(method=="video"){
					var _picUrl = $(".myVideo").attr("picUrl");
					var _ti=$(".myVideo").attr("title");
					var _play=$(".myVideo").attr("play");
					if(undefined!=_picUrl && undefined!=_ti && undefined!=_play){
						var vdex=_ti+"#"+_picUrl+"#"+_play;
						condition=null;
						condition = {"cid":c,"title":ti,"content":ve,"magazineUrl":mgur,"magazineName":mgne,"creativeUser":crus,"tags":tag,"ploy":ploy,"conList":vdex};
					}else{
						alert('请上传视频');
						//$("#cr_video_error").show();
						flag=true;
						return;
					}
				}else if(method=="works"){
					var imgarr={};
					i=0;
					var event=false;
					$('#imagesList>.inner>div').each(function() {
						if ($(this).hasClass("itemVideo")){
							event=true;
							var imgsrc=$(this).attr("picUrl");
							var con=$(this).find("textarea").val().replace(';','；');
							var play=$(this).attr("play");
							var eid = $(this).attr("eid");
							if(eid==undefined || eid==''){
								var videoList="video;"+imgsrc+";"+con+";"+play+";"+i;
								imgarr[i]=videoList;
							}else{
								var videoList="video;"+imgsrc+";"+con+";"+play+";"+i+";"+eid;
								imgarr[i]=videoList;
							}
							
						}else if($(this).hasClass("itemMusic")){
							event=true;
							var imgsrc=$(this).find("img").attr("src");
							var con=$(this).find("textarea").val().replace(';','；');
							var music=$(this).attr("music");
							var eid = $(this).attr("eid");
							if(eid==undefined || eid==''){
								var musicList="music;"+imgsrc+";"+con+";"+music+";"+i;
								imgarr[i]=musicList;
							}else{
								var musicList="music;"+imgsrc+";"+con+";"+music+";"+i+";"+eid;
								imgarr[i]=musicList;
							}
							
						}else{
							var imgsrc=$(this).find("img").attr("src");
							var w=$(this).find("img").attr("width");
							var h=$(this).find("img").attr("height");
							if(w==undefined)
								w=$(this).find("img").width();
							if(h==undefined)
								h=$(this).find("img").height();
							var con=$(this).find("textarea").val().replace(';','；');
							var eid = $(this).attr("eid");
							if(eid==undefined || eid==''){
								var imgList="img;"+imgsrc+";"+con+";"+i+";"+w+";"+h;
								imgarr[i]=imgList;
							}else{
								var imgList="img;"+imgsrc+";"+con+";"+i+";"+eid;
								imgarr[i]=imgList;
							}
						}
						i++;
					});
					if(i==0){
						alert("给你的作品添加封面和插图吧！");
						flag=true;
						return;
					}
					var isHome=0;
					if($("#cr-cb-ishome").attr("checked")){
						if(event){
							alert("自主创建事件只限于图片不允许添加音乐与视频");
							isHome=0;
							flag=true;
							return;
						}else{
							isHome=1;
						}
					}
					
					i=0;
					$('#tags>li').each(function() {
						if ($(this).hasClass("current")){
							tag[i]=$(this).find("a").html();
							i++;
						}
					});
					if(i==0){
						alert("给你的作品添加几个标签吧！");
						flag=true;
						return;
					}
					
					i=0;
					$('#creativeuser>span').each(function() {
							var temp=$(this).attr("user");
							crus[i]=temp;
							i++;
					});
					condition=null;
					condition = {"cid":c,"title":ti,"content":ve,"magazineUrl":mgur,"magazineName":mgne,"isEvent":isHome,"creativeUser":crus,"tags":tag,"ploy":ploy,"issueid":issueid,"publicationid":publicationid,"conList":imgarr};
				}
				$.ajax({
					url : _url,
					type : "POST",
					dataType:'json',
					data : condition,
					success: function(rs){
						if(rs.code==200){
							window.location.href=SystemProp.appServerUrl+"/sns/user-index!home.action";
						}else{
							flag=true;
							alert("添加失败！")
						}
					}
				});
			}
		},
		btnView:function(){
			var istrue=false;
			var ti=$.trim($("#cr-text-title").val()),
			ve=$.trim($("#cr-text-content").val()),
			mgur=$("#cr-text-magazineUrl").val(),
			mgne=$("#magazineName").val(),
			ploy=0,
			method=$("#cr_operate").val(),
			c=$("#s_c_i_d").val(),
			tag={},crus={},condition,i=0;
			
			if($("#cr-cb-ploy").attr("checked"))ploy=1;
			if(ti==''){
				alert("给你的发言起一个标题吧！")//$("#cr-text-titleError").show();
				return;
			}else
				$("#cr-text-titleError").hide();
			
			if( mgur!='' && mgne==''){
				alert("抱歉，杂志链接错误并且只支持本站中杂志链接");
				//$("#magazineUrlError").show();
				return;
			}else
				$("#magazineUrlError").hide();
			
			$('#creativeuser>span').each(function() {
					var temp=$(this).attr("user");
					crus[i]=temp;
					i++;
			});
			if(method!=''){
				var _showType=1;
				condition = {"cid":c,"title":ti,"content":ve,"magazineUrl":mgur,"magazineName":mgne,"creativeUser":crus,"tags":tag,"ploy":ploy,"type":1};
				
				if(method=="image"){
					var imgarr={};
					i=0;
					$('#imagesList>.inner>div').each(function() {
						var imgsrc=$(this).find("img").attr("src");
						var con=$(this).find("textarea").val().replace(';','；');
						var imgList=imgsrc+";"+con+";"+i;
						imgarr[i]=imgList;
						i++;
					});
					if(i==0){
						alert('请上传图片');//$("#ImagesError").show();
						return;
					}else{
						$("#ImagesError").hide();
					}
					
					if(i>0){
						if(i>10)i=10;
						var _i=1;
						$("#imgLayout>.inner>div").each(function(){
							_i++;
							if(i==_i){
								var j=1;
								$(this).find("a").each(function(){
									if($(this).hasClass("current")){
										_showType=i+""+j;
									}
									j++;
								});
							}
							
						});
					}
					condition=null;
					condition = {"cid":c,"title":ti,"content":ve,"magazineUrl":mgur,"magazineName":mgne,"creativeUser":crus,"tags":tag,"ploy":ploy,"conList":imgarr,"showType":_showType,"type":2};
				}else if(method=="music"){
					$("#cr-music-Error").hide();
					var conlist=$(".myMusic").find("img").attr("src")+";"+$(".myMusic>.player").html();
					if($(".myMusic>.player").html()==''){
						alert('请选择音乐');//$("#cr-music-Error").show();
						return;
					}
					condition=null;
					condition = {"cid":c,"title":ti,"content":ve,"magazineUrl":mgur,"magazineName":mgne,"creativeUser":crus,"tags":tag,"ploy":ploy,"conList":conlist,"type":3};
				}else if(method=="video"){
					var _picUrl = $(".myVideo").attr("picUrl");
					var _ti=$(".myVideo").attr("title");
					var _play=$(".myVideo").attr("play");
					if(undefined!=_picUrl && undefined!=_ti && undefined!=_play){
						var vdex=_ti+"#"+_picUrl+"#"+_play;
						condition=null;
						condition = {"cid":c,"title":ti,"content":ve,"magazineUrl":mgur,"magazineName":mgne,"creativeUser":crus,"tags":tag,"ploy":ploy,"conList":vdex,"type":4};
					}else{
						alert('请上传视频');
						//$("#cr_video_error").show();
						return;
					}
				}else if(method=="works"){
					var imgarr={};
					i=0;
					$('#imagesList>.inner>div').each(function() {
						if ($(this).hasClass("itemVideo")){
							var imgsrc=$(this).attr("picUrl");
							var con=$(this).find("textarea").val().replace(';','；');
							var play=$(this).attr("play");
							var eid = $(this).attr("eid");
							if(eid==undefined || eid==''){
								var videoList="video;"+imgsrc+";"+con+";"+play+";"+i;
								imgarr[i]=videoList;
							}else{
								var videoList="video;"+imgsrc+";"+con+";"+play+";"+i+";"+eid;
								imgarr[i]=videoList;
							}
							
						}else if($(this).hasClass("itemMusic")){
							var imgsrc=$(this).find("img").attr("src");
							var con=$(this).find("textarea").val().replace(';','；');
							var music=$(this).attr("music");
							var eid = $(this).attr("eid");
							if(eid==undefined || eid==''){
								var musicList="music;"+imgsrc+";"+con+";"+music+";"+i;
								imgarr[i]=musicList;
							}else{
								var musicList="music;"+imgsrc+";"+con+";"+music+";"+i+";"+eid;
								imgarr[i]=musicList;
							}
							
						}else{
							var imgsrc=$(this).find("img").attr("src");
							var con=$(this).find("textarea").val().replace(';','；');
							var eid = $(this).attr("eid");
							if(eid==undefined || eid==''){
								var imgList="img;"+imgsrc+";"+con+";"+i;
								imgarr[i]=imgList;
							}else{
								var imgList="img;"+imgsrc+";"+con+";"+i+";"+eid;
								imgarr[i]=imgList;
							}
						}
						i++;
					});
					if(i==0){
						alert("给你的作品添加封面和插图吧！");
						return;
					}
					condition=null;
					condition = {"cid":c,"title":ti,"content":ve,"magazineUrl":mgur,"magazineName":mgne,"creativeUser":crus,"tags":tag,"ploy":ploy,"conList":imgarr,"type":5};
				}
				i=0;
				$('#tags>li').each(function() {
					if ($(this).hasClass("current")){
						tag[i]=$(this).find("a").html();
						i++;
					}
				});
				if(i==0){
					alert("给你的作品添加几个标签吧！");
					return;
				}
				$.ajax({
					url : SystemProp.appServerUrl+"/sns/creative!viewData.action",
					type : "POST",
					dataType:'json',
					async: true,
					data : condition,
					success: function(rs){
						if(rs.code==200){
							istrue=true;
							window.open(SystemProp.appServerUrl+"/sns/creative!view.action");
							//$("#prview").click();
							//window.location.href=SystemProp.appServerUrl+"/sns/creative!view.action";
						}else{
							alert("预览错误请检查数据！")
						}
					}
				});
			}
//			setTimeout(function(){
//				if(istrue)
//					window.open(SystemProp.appServerUrl+"/sns/creative!view.action");
//			},300);
		}
	}
	creative.init();
	
});

function getTags(){
	$.ajax({
		url : SystemProp.appServerUrl+"/sns/public-sns!getTags.action",
		type : "POST",
		dataType:'json',
		success: function(rs){
			if(rs.code==200){
				var list=rs.data.list;
				for(var i in list){
					var tag=list[i];
					$("#addcurrent").prev().after("<li><a href='javascript:void(0);'>"+tag.name+"</a></li>");
				}
					
			}
		}
	});
}

//setTimeout(getTags,50); 不在需要获取默认的标签了，因此去掉
