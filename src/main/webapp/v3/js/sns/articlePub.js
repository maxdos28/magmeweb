;
//判断输入
//标题
$("#cr-text-title").live("keyup",function(){
	var length = 20;
	var content_len = this.value.length;
	var in_len = length-content_len;
	
	// 当用户输入的字数大于制定的数时，让提交按钮失效
	// 小于制定的字数，就可以提交
	if(in_len < 0){
		this.value = this.value.substring(0, length);
		return false;
	}
});
//标题
$("#cr-text-title").live("blur",function(){
	var length = 20;
	var content_len = this.value.length;
	var in_len = length-content_len;
	if(in_len < 0){
		this.value = this.value.substring(0, length);
	}
});
//显示标题
$("#secondTitle").live("keyup",function(){
	var length = 20;
	var content_len = this.value.length;
	var in_len = length-content_len;
	
	// 当用户输入的字数大于制定的数时，让提交按钮失效
	// 小于制定的字数，就可以提交
	if(in_len < 0){
		this.value = this.value.substring(0, length);
		return false;
	}
});

//显示标题
$("#secondTitle").live("blur",function(){
	var length = 20;
	var content_len = this.value.length;
	var in_len = length-content_len;
	if(in_len < 0){
		this.value = this.value.substring(0, length);
	}
}); 

//显示描述
$("#secondDescription").live("keyup",function(){
	var length = 75;
	var content_len = this.value.length;
	var in_len = length-content_len;
	
	// 当用户输入的字数大于制定的数时，让提交按钮失效
	// 小于制定的字数，就可以提交
	if(in_len < 0){
		this.value = this.value.substring(0, length);
		return false;
	}
});
//显示描述
$("#secondDescription").live("blur",function(){
	var length = 75;
	var content_len = this.value.length;
	var in_len = length-content_len;
	if(in_len < 0){
		this.value = this.value.substring(0, length);
	}
});


//作品标题
$("#creativeTitle").live("keyup",function(){
	var length = 20;
	var content_len = this.value.length;
	var in_len = length-content_len;
	
	// 当用户输入的字数大于制定的数时，让提交按钮失效
	// 小于制定的字数，就可以提交
	if(in_len < 0){
		this.value = this.value.substring(0, length);
		return false;
	}
});
//作品标题
$("#creativeTitle").live("blur",function(){
	var length = 20;
	var content_len = this.value.length;
	var in_len = length-content_len;
	if(in_len < 0){
		this.value = this.value.substring(0, length);
	}
});


//作品描述
$("#creativeDescription").live("keyup",function(){
	var length = 75;
	var content_len = this.value.length;
	var in_len = length-content_len;
	
	// 当用户输入的字数大于制定的数时，让提交按钮失效
	// 小于制定的字数，就可以提交
	if(in_len < 0){
		this.value = this.value.substring(0, length);
		return false;
	}
});
//作品描述
$("#creativeDescription").live("blur",function(){
	var length = 75;
	var content_len = this.value.length;
	var in_len = length-content_len;
	if(in_len < 0){
		this.value = this.value.substring(0, length);
	}
});

$("#creativeFlag").unbind("click").live("click",function(e){
	if($(this).attr("checked")){
		//$("#creativeFlagShow").removeClass("hide");
		$("#creativeTitlediv").removeClass("hide");
		$("#creativeDescriptiondiv").removeClass("hide");
	}else{
		//$("#creativeFlagShow").addClass("hide");
		$("#creativeTitlediv").addClass("hide");
		$("#creativeDescriptiondiv").addClass("hide");
	}
});
//判断输入结束




//添加标签功能
$("#addtagsBtn").unbind('click').live("click",function(e){
	e.preventDefault();
	var tag=$("#addtagsText").val();
	if(tag.length>16){
		alert("标签长度不能大于16");
		$("#addtagsText").val('');
		return;
	}
	if(tag!="添加新标签"){
		var tvl=0;
		$('#tags>li').each(function() {if ($(this).find("a").html()==tag)tvl=1;});
		if(tvl==0) {
			//前面没有标签
			var prevsize=$("#addcurrent2").prev().size();
			if(prevsize==0){
				$("#addcurrent2").before("<li class='current'><a href='javascript:void(0);'>"+tag+"</a></li>");
			}else{
				$("#addcurrent2").prev().after("<li class='current'><a href='javascript:void(0);'>"+tag+"</a></li>");
				
			}
			
		}else{
			alert("已经存在标签："+tag);
		}
		$("#addtagsText").val('');
	}
});

//添加标签功能
$("#addtagsText").live("keyup",function(event){
	if (event.keyCode == '13') {
	    event.preventDefault();
	    var tag=$("#addtags").val();
		if(tag!="添加新标签"){
			var tvl=0;
			$('#tags>li').each(function() {if ($(this).find("a").html()==tag)tvl=1;});
			if(tvl==0) $("#addcurrent").prev().after("<li class='current'><a href='javascript:void(0);'>"+tag+"</a></li>");
			else alert("已经存在标签："+tag);
			$("#addtags").val('');
		}
   }
});

//
var make_creative=function(){
	var flag=false;
	var title=$.trim($("#cr-text-title").val()),
	content=$.trim(KE.html('cr-text-content')),
	magazineUrl=$("#cr-text-magazineUrl").val(),
	magazineName=$("#magazineName").val(),
	issueid=$("#issueid").val(),
	publicationid=$("#publicationid").val(),
	ploy=0,
	method=$("#cr_operate").val(),
	id=$("#s_c_i_d").val(),
	tag={},
	categoryIds={},
	picCollectionIds={},
	condition,
	i=0,
	secondTitle=$("#secondTitle").val(),
	secondDescription=$("#secondDescription").val();
	
	var userLevel=$("#audit_editor").val();
	var creativeTitle=$("#creativeTitle").val();
	var creativeDescription=$("#creativeDescription").val();
	var creativeId=$("#creativeId").val();
	var intent=$("#intent").val();
	var origin=$("#cr-text-origin").val();
	var originurl=$("#cr-text-originurl").val();
	var creativeFlag=0;
	if($("#creativeFlag").attr("checked")){
		creativeFlag=1;
		if(!creativeTitle || creativeTitle=='' ||  !creativeDescription || creativeDescription==''){
			alert("选择了创建系列则必须输入系列标题和系列描述!");
			return;
		}
	}else{
		creativeFlag=0;
	}
	//标签
	var tagcount=0;
	$('#tags>li').each(function() {
		if ($(this).hasClass("current")){
			tag[tagcount]=$(this).find("a").html();
			tagcount++;
		}
	});
	
	
	//是否私密
	if($("#cr-cb-ploy").attr("checked"))ploy=1;
	//标题
	if((title=='' && intent==0 && userLevel==1) || (userLevel!=1 && title=='' )){
		alert("给你的作品起一个标题吧！");//$("#cr-text-titleError").show();
		flag=true;
		return;
	}else{
		$("#cr-text-titleError").hide();
	}
		
	if((userLevel==3 || userLevel==1) &&  originurl!='' && origin==''){
		alert("golf用户发布，如果输入了来源链接，必须输入来源标题!");
		return;
	}
	//杂志链接
		
	//seoer增加文章，必须有标签
	if(userLevel==2 && intent==0 && tagcount==0){
		alert("seo用户发布，必须输入标签!");
		flag=true;
		return;
	}
	//编辑操作，必须有content
	if(userLevel==1 && content==''){
		alert("认证编辑必须输入内容!");
		flag=true;
		return;
	}
	//只有认证编辑和golf增加的时候，需要选择分类
	//只有认证编辑增加的时候，需要选择图集,由于之前没有图集选项输出，这里不再判断
	if((userLevel==1 || userLevel==3) && intent==0){
		var categoryIdsCount=0;
		$("input[parentId][childCategoryId]").each(function(){
			if($(this).attr("checked")){
				categoryIds[categoryIdsCount++]=$(this).val();
			}
		});
		if(categoryIdsCount==0){
			alert("认证编辑必须选择分类");
			return ;
		}
		var picCollectionIdsCount=0;
		var picCollWithChildCategory=0;
		$("input[parentId][name=picCollection]").each(function(){
			if($(this).attr("checked")){
				var parentId=$(this).attr("parentId");
				picCollectionIds[picCollectionIdsCount++]=$(this).attr("parentId");
				if($("input[parentId="+parentId+"][childCategoryId][checked]").length<=0){
					picCollWithChildCategory=1;
				}
			}
		});
		
		if(picCollWithChildCategory==1){
			alert("选择了图集，必须选择相应的分类");
			return;
		}
	}
	
	//只上封面
	if($("input[justCover]:checked").size()>1){
		alert("只上首页最多只能选择一个");
		return;
	}
	var neteasecategoryIds="";
	if(userLevel==1){
		$("input[ccid]:checked").each(function(){neteasecategoryIds+=$(this).attr("ccid")+",";});
	}
	
	// 
	var bigWordCount=$("#bigWordCount").val();
		
	
	if(method!=''){
		var _url=SystemProp.appServerUrl+"/sns/article-pub!makeJson.action";
		var _showType=1;
		if(method=="works"){
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
					var justCover=$(this).find("input").attr("checked")=="checked";
					if(w==undefined)
						w=$(this).find("img").width();
					if(h==undefined)
						h=$(this).find("img").height();
					var con=$(this).find("textarea").val().replace(';','；');
					var eid = $(this).attr("eid");
					if(eid==undefined || eid==''){
						var imgList="img;"+imgsrc+";"+con+";"+i+";"+w+";"+h;
						if(justCover){
							imgList=imgList+";2"
						}else{
							imgList=imgList+";1"
						}
						imgarr[i]=imgList;
					}else{
						var imgList="img;"+imgsrc+";"+con+";"+i+";"+eid;
						if(justCover){
							imgList=imgList+";2"
						}else{
							imgList=imgList+";1"
						}
						imgarr[i]=imgList;
					}
				}
				i++;
			});
			/* 可以纯文字作品
			 * if(i==0){
				alert("给你的作品添加封面和插图吧！");
				flag=true;
				return;
			}*/
			
			
			
			condition = {"id":id,"title":title,"content":content,"magazineUrl":magazineUrl,"magazineName":magazineName,
					"tags":tag,"ploy":ploy,"issueid":issueid,"publicationid":publicationid,"conList":imgarr,
					"picCollectionIds":picCollectionIds,"categoryIds":categoryIds,
					"secondDescription":secondDescription,"secondTitle":secondTitle,"bigWordCount":bigWordCount,
					"creativeFlag":creativeFlag,"creativeTitle":creativeTitle,"creativeDescription":creativeDescription,
					"creativeId":creativeId,"origin":origin,"originUrl":originurl,"neteaseCategoryIds":neteasecategoryIds};
		}
		$("#btnWB_make").parent().append("<a  id='btnWB_cancle' class='btnAS'>取消</a><a class='btnDB'>一键制作</a><div class='loading32'></div>");
		$("#btnWB_make").remove();
		$.ajax({
			url : _url,
			type : "POST",
			dataType:'json',
			data : condition,
			success: function(rs){
				if(rs.code==200){
					window.location.href=SystemProp.appServerUrl+"/sns/article-pub!preview.action";
				}else{
					flag=true;
					$("#btnWB_cancle").parent().empty().append("<a id='btnWB_make' class='btnGB'>一键制作</a>");
					$("#btnWB_cancle").remove();
					if(rs.message){
						alert(rs.message);
					}else{
					    alert("发布作品失败！");
					}
				}
			}
		});
	}

};

$("#btnWB_cancle").unbind("click").live("click",function(){
	$.ajax({
		url : SystemProp.appServerUrl+"/sns/article-pub!deleteCacheOrSession.action",
		type : "POST",
		dataType:'json',
		success:function(rs){
			$("#btnWB_cancle").parent().empty().append("<a id='btnWB_make' class='btnGB'>一键制作</a>");
			$("#btnWB_cancle").remove();
		   }
		});
});

//提交制作
$("#btnWB_make").unbind("click").live("click", make_creative);

/**
 * 编辑的时候，默认输出已有的图片和音乐，视频
 */
function getArticleEditImgEx(){
	var v=$("#_img_s_t").val().substr(1);
	var c=$("#s_c_i_d").val();
	var e_url= SystemProp.appServerUrl+"/sns/article-pub!getEditArticleEx.action";
	
	var c=$("#s_c_i_d").val();
	$.ajax({
		url : e_url,
		type : "POST",
		data : {"id":c},
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
						var innerDiv="<div eid='"+ex.id+"' class='item itemComplete'><div class='inner'><a href='javascript:void(0);' class='close'></a>";
						if($("#articleSortOrder").val()==0){
							innerDiv=innerDiv+"<a class='btnGS' href='javascript:void(0);'><label><input justCover='' type='checkbox'"; 
							if(ex.imgType==2){
								innerDiv+="checked='checked'";
							}
							innerDiv=innerDiv+"/>只上首页</label></a>"
						}
						innerDiv=innerDiv+"<div class='loading'><strong></strong><span class='progress'><em>0%</em></span></div><div class='img'><div class='mask'></div><img  src='"
						+SystemProp.staticServerUrl+ex.imgPath+"' /></div><textarea>"+ex.content+"</textarea></div></div>";
						
						$("#imagesList>.inner").append(innerDiv).show();
						$(".body>.sideLeftMiddleRight>.conRight").css({height : $(".body>.sideLeftMiddleRight>.conLeftMiddle").height() - 8 + "px" });
					}else if(ex.conType==3){
						$(".conShareImages>.imagesCon").show();
						$("#imagesList>.inner").append("<div eid='"+ex.id+"' music='"+ex.path+"' class='item itemComplete itemMusic uncover'><div class='inner'><a href='javascript:void(0);' class='close'></a><div class='img'><div class='mask'><div class='ico png'></div></div><img src='"+ex.imgPath+"' /></div><textarea>"+ex.content+"</textarea></div></div>").show();
						$("#imagesList>.inner>.uncover>.img>img").coverImg().parents(".uncover").removeClass("uncover");
						$(".body>.sideLeftMiddleRight>.conRight").css({height : $(".body>.sideLeftMiddleRight>.conLeftMiddle").height() - 8 + "px" });
					}else if(ex.conType==4){
						$(".conShareImages>.imagesCon").show();
						$("#imagesList>.inner").append("<div  picUrl='"+ ex.imgPath+"' eid='"+ex.id+"' play='"+ex.path+"' class='item itemComplete itemVideo uncover'><div class='inner'><a href='javascript:void(0);' class='close'></a><a class='btnGS' href='javascript:void(0);'><span>更换图片</span><input changePic='' type='file' class='inputFile' name='changePicFile'/></a><div class='img'><div class='mask'><div class='ico png'></div></div><img style='padding: 30px 0 0 20px;' src='"+SystemProp.staticServerUrl+ex.imgPath+"' /></div><textarea>"+ex.content+"</textarea></div></div>").show();
						$("#imagesList>.inner>.uncover>.img>img").coverImg().parents(".uncover").removeClass("uncover");
						$(".body>.sideLeftMiddleRight>.conRight").css({height : $(".body>.sideLeftMiddleRight>.conLeftMiddle").height() - 8 + "px" });
					}
				}
				$("#imagesList>.inner").find(".img>img").coverImg();
				
				
				//选中分类
				/*var cList=rs.data.creativeCategoryRelList;
				if(cList && cList.length>0){
					for(var j in cList){
						var c=cList[j];
						var picCollection=c.picCollection;
						//选中分类
						$("input[parentId][childCategoryId="+c.categoryId+"]").attr("checked","checked");
						if(picCollection==1){
							$("input[parentId][childCategoryId="+c.categoryId+"]").parent().parent().parent().find("input[parentId][name=picCollection]").attr("checked","checked");
						}
					}	
				}*/
			}
			
	 }		
	});
};


//确认发布
$("#confirmPublish").unbind("click").live("click",function(){
	var pubUrl=SystemProp.appServerUrl+"/sns/article-pub!pub.action";
	var tempPattern=$("#template_pattern").val();
	$.ajax({
		url : pubUrl,
		type : "POST",
		data : {"template":tempPattern},
		dataType : 'json',
		success: function(rs){
			if(!rs)return;
			var code = rs.code;
			if(code == 200){
				window.location.href=SystemProp.appServerUrl+"/sns/c"+rs.data.creativeId+"/"+rs.data.id+"/";
			}else{
				if(rs.message){
					alert(rs.message);
				}else{
					alert("发布失败");
				}
			}
		}
	});
});
//返回修改
$("#backEdit").unbind("click").live("click",function(){
	var pubUrl=SystemProp.appServerUrl+"/sns/article-pub!pub.action";
	var tempPattern=$("#template_pattern").val();
	$.ajax({
		url : pubUrl,
		type : "POST",
		data : {"template":tempPattern},
		dataType : 'json',
		success: function(rs){
			if(!rs)return;
			var code = rs.code;
			if(code == 200){
				window.location.href=SystemProp.appServerUrl+"/sns/article-pub!edit.action?id="+rs.data.id;
			}else{
				if(rs.message){
					alert(rs.message);
				}else{
					alert("发布失败");
				}
			}
		}
	});
});

$("#pubSerializeArticle").unbind("click").live("click",function(){
	var pubUrl=SystemProp.appServerUrl+"/sns/article-pub!pub.action";
	var tempPattern=$("#template_pattern").val();
	$.ajax({
		url : pubUrl,
		type : "POST",
		data : {"template":tempPattern},
		dataType : 'json',
		success: function(rs){
			if(!rs)return;
			var code = rs.code;
			if(code == 200){
				window.location.href=SystemProp.appServerUrl+"/sns/article-pub!addActicle.action?creativeId="+rs.data.creativeId;
			}else{
				if(rs.message){
					alert(rs.message);
				}else{
				    alert("发布作品失败！");
				}
			}
		}
	});
});

//重新排版
$("#reTypeSetting").unbind("click").live("click",function(e){
	e.preventDefault();
	$("#mainContent").html(previewContent).attr({"class":"mainContent","style":""});
	fnGenerate($("#articleImgCount").val(),$("#articleWordCountTemp").val(),null,$("#articlebigWordCount").val());
});

$("#selectTemplate").unbind("click").live("click",function(e){
	e.preventDefault();
	
	$("#tplList a").live("click",function(){
		$(this).addClass("current").parent().siblings().find("a").removeClass("current");
	});
	fnShowTplList($("#articleImgCount").val(),$("#articleWordCountTemp").val(),$("#template_pattern").val());
	$("#templateSelect").fancybox();
});

$("#templateSelectEnter").unbind("click").live("click",function(e){
	e.preventDefault();
	var tempTPL=$("#tplList a");
	var template_pattern=1;
	for(var i=0;i<tempTPL.length;i++){
		if($(tempTPL[i]).hasClass("current")){
			template_pattern=i+1;
			break;
		}
	}
	$("#template_pattern").val(template_pattern);
	$("#mainContent").html(previewContent).attr({"class":"mainContent","style":""});
	fnGenerate($("#articleImgCount").val(),$("#articleWordCountTemp").val(),template_pattern,$("#articlebigWordCount").val());
	$.fancybox.close();
});

$("#templateSelectEnterCancel").unbind("click").live("click",function(e){
	e.preventDefault();
	$.fancybox.close();
});


$("a[singleArticleId]").unbind("click").live("click",function(e){
	e.preventDefault();
	var delUrl=SystemProp.appServerUrl+"/sns/article!delJson.action";
	var singleArticleId=$(this).attr("singleArticleId");
	$.ajax({
		url : delUrl,
		type : "POST",
		data : {"articleId":singleArticleId},
		dataType : 'json',
		success: function(rs){
			if(!rs)return;
			var code = rs.code;
			if(code == 200){
				$("a[singleArticleId="+singleArticleId+"]").parent().parent().remove();
			}else{
				alert("删除失败");
			}
		}
	});
});