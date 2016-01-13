;
//判断输入
//标题
$("#cr-text-title").live("keyup",function(){
	var length = 18;
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
	var length = 18;
	var content_len = this.value.length;
	var in_len = length-content_len;
	if(in_len < 0){
		this.value = this.value.substring(0, length);
	}
});
//显示标题
$("#secondTitle").live("keyup",function(){
	var length = 18;
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
	var length = 18;
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


//判断输入结束

//添加标签功能
$("#addtagsBtn").unbind('click').live("click",function(e){
	e.preventDefault();
	var tag=$("#addtagsText").val();
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
	
	if($("#cr-cb-ploy").attr("checked"))ploy=1;
	if(title==''){
		alert("给你的作品起一个标题吧！");//$("#cr-text-titleError").show();
		flag=true;
		return;
	}else{
		$("#cr-text-titleError").hide();
	}
		
	
	if( magazineUrl!='' && magazineName==''){
		alert("抱歉，杂志链接错误并且只支持本站中杂志链接");
		flag=true;
		return;
	}else{
		$("#magazineUrlError").hide();
	}
	//分类 图集
	if($("#audit_editor").val()==1){
		var categoryIdsCount=0;
		$("input[parentId][childCategoryId]").each(function(){
			if($(this).attr("checked")){
				categoryIds[categoryIdsCount++]=$(this).val();
			}
		});
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
	
	//
	
		
	
	if(method!=''){
		var _url=SystemProp.appServerUrl+"/sns/creative-pub!makeJson.action";
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
			/* 可以纯文字作品
			 * if(i==0){
				alert("给你的作品添加封面和插图吧！");
				flag=true;
				return;
			}*/
			
			var tagcount=0;
			$('#tags>li').each(function() {
				if ($(this).hasClass("current")){
					tag[tagcount]=$(this).find("a").html();
					tagcount++;
				}
			});
			
			condition = {"id":id,"title":title,"content":content,"magazineUrl":magazineUrl,"magazineName":magazineName,
					"tags":tag,"ploy":ploy,"issueid":issueid,"publicationid":publicationid,"conList":imgarr,
					"picCollectionIds":picCollectionIds,"categoryIds":categoryIds,"secondDescription":secondDescription,"secondTitle":secondTitle};
		}
		$.ajax({
			url : _url,
			type : "POST",
			dataType:'json',
			data : condition,
			success: function(rs){
				if(rs.code==200){
					//var templateContent=rs.data.templateContent;
					/*$("#magezineShowContent").html("");
					$("#magezineShowContent").html(templateContent);
					fnGenerate();
					$("#magezineShow").fancybox();*/
					window.location.href=SystemProp.appServerUrl+"/sns/creative-pub!preview.action";
				}else{
					flag=true;
					alert("添加失败！")
				}
			}
		});
	}

};


//提交制作
$("#btnWB_make").live("click", make_creative);


function getCreativeEditImgEx(){
	var v=$("#_img_s_t").val().substr(1);
	var c=$("#s_c_i_d").val();
	var e_url= SystemProp.appServerUrl+"/sns/creative-pub!getEditCreativeEx.action";
	
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
				
				
				//选中分类
				var cList=rs.data.creativeCategoryRelList;
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
				}
			}
			
	 }		
	});
};


//确认发布
$("#confirmPublish").unbind("click").live("click",function(){
	var pubUrl=SystemProp.appServerUrl+"/sns/creative-pub!pub.action";
	var tempPattern=$("#template_pattern").val();
	$.ajax({
		url : pubUrl,
		type : "POST",
		data : {"tempPattern":tempPattern},
		dataType : 'json',
		success: function(rs){
			if(!rs)return;
			var code = rs.code;
			if(code == 200){
				alert("success");
				//window.location.href=SystemProp.appServerUrl+"/sns/creative-pub!preview.action";
			}else{
				alert("发布失败");
			}
		}
	});
});

