$(function(){
	//for conReportDetail textArea focus
	$(".conReportDetail .comment .inputArea textarea").one("focus",function(){
		$(this).height(60).nextAll(".hide").removeClass("hide");
	});
	
	
	//for conReportDetail function
	$(".conReportDetail h2").click(function(){
		objT = $(this).find("em");
		objC = $(this).next();
		//modify by edward  time:11/07/26
		$("#personOpus>div.loadComment").removeClass("loadComment");
		
		if(!objC.hasClass("show")){
			$(this).parent().addClass("loadComment");
			objT.html("点击收起");
			objC.slideDown(300);
			objC.addClass("show");
		}else{
			objT.html("点击展开");
			objC.slideUp(300);
			objC.removeClass("show");
		}
	});
	
	//default
	$("#personOpus>div:first h2").click();
	
});
