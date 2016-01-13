;$(document).ready(function(){
	var eveId = $("div.photoArea").attr("eventId")||"";
	//shareToIntenet
	tagInfo = {
		url: SystemProp.domain+"/issue-image/"+eveId+".html",
		title: $(".photoArea .tagName").html(),
		imgsrc: $(".photoArea .photo img").eq(0).attr("src"),
		desc: $(".description p").html()
	};
	
	$(".JQfadeIn").mouseenter(function(){
		$(this).find(".item").animate({opacity:1},300);
	}).mouseleave(function(){
		$(this).find(".item").stop(true,true).not(".current").animate({opacity:0.3},300);
	});
	
	$("a[tagShare]").live('click',function(e){
		e.preventDefault();
		var shareBtn = $(this);
    	var type = shareBtn.attr("tagShare");
    	//tagInfo is window's parameter. Default parameter is in useFunction.js
    	//everyPage want to share in the Internet, you can change the parameter(tagInfo)
    	shareToObj.shareType(type,tagInfo);
	});
	
	//---------------------add_tag----------------------------
	$("#tagList a[name=tagName]").live("click",function(e){
		e.preventDefault();
		if( !hasUserLogin() ){
			gotoLogin("请登录后，才能添加标签！");
			return;
		}
		var $tag = $(this);
		var tagContent = $tag.html();
		ajaxAddTag(eveId,3,tagContent,function(rs){
			if(!rs)return;
			var code = rs.code;
			if(code == 200){
				alert("添加标签成功！");
			}else if(code == 400){
				gotoLogin("用户没有登录，请登录！");
			}else{
				alert(rs.message);
			}
		});
	});
	
	var allTags = [];
	$("#tagList a[name=tagName]").each(function(){
		var tagContent = $(this).html();
		allTags.push(tagContent);
	});
	$("#addTag").click(function(e){
		e.preventDefault();
		if( !hasUserLogin() ){
			gotoLogin("请登录后，才能添加标签！");
			return;
		}
		var tagContent = $("#tagInfo").val();
		if(tagContent == "添加新标签" || tagContent ==""){
			alert("请输入标签！");
			return;
		}
		if(tagContent.length>6){
			alert("标签长度不能超过6！");
			return;
		}
		var eveId = $(this).attr("eveId");
		ajaxAddTag(eveId,3,tagContent,function(rs){
			if(!rs)return;
			var code = rs.code;
			if(code == 200){
				var tagContent = rs.data.tag.name||'';
				if($.inArray(tagContent,allTags) == -1){
					allTags.push(tagContent);
					$("#tagList .add").before('<li><a name="tagName" href="javascript:void(0)">'+tagContent+'</a></li>');
				}
				$("#tagInfo").val('');
				alert("添加标签成功！");
			}else if(code == 400){
				gotoLogin("用户没有登录，请登录！");
			}else{
				alert(rs.message);
			}
		});
	});
});