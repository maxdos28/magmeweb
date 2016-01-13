$(function(){
	$("#readBtn").live('click',function(e){
		var issueId = $(this).attr("issueId");
		var backUrl = window.location.href;
		window.location.href=SystemProp.appServerUrl+"/widget/qplus-read.action?id="+issueId+"&backUrl="+encodeURIComponent(backUrl);
	});

	$("#continueReadBtn").live('click',function(e){
		var issueId = $(this).attr("issueId");
		var pageNo = $(this).attr("pageNo");
		var backUrl = window.location.href;
		window.location.href=SystemProp.appServerUrl+"/widget/qplus-read.action?id="+issueId+"&pageId="+pageNo+"&backUrl="+encodeURIComponent(backUrl);
	});
	
	$("#cancelBtn").live('click',function(e){
		var issueId = $(this).attr("issueId");
		var pubId = $(this).attr("pubId");
		var backUrl = window.location.href;
		window.location.href=SystemProp.appServerUrl+"/widget/qplus!enjoy.action?publicationId=" + pubId;
	});

});	