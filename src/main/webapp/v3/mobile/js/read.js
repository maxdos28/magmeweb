;$(document).ready(function(){
	$(".read").animate({left:0},500);
	$(".img").toggle(function(){
		$(this).find("span").fadeOut(200);
		$(this).find("img").css({position:"absolute"}).animate({width:$("body").width()-20},300);
	},function(){
		$(this).find("span").fadeIn(500);
		$(this).find("img").css({position:"static"}).animate({width:120},300);
	});
	
	//font-size
	var content = $("#content");
	$("#big").click(function(e){
		e.preventDefault();
		content.css("font-size","130%");
		setCookie("font-size","big",new Date("December 31,2120"));
	});
	$("#small").click(function(e){
		e.preventDefault();
		setCookie("font-size","small",new Date("December 31,2120"));
		content.css("font-size","100%");
	});
	
	var sizeCookie = getCookie("font-size");
	if(!!sizeCookie && sizeCookie === 'big'){
		content.css("font-size","130%");
	}
	
	//share-to-weibo
	$(".weiboShare").click(function(e){
		e.preventDefault();
		var info = {
				url: SystemProp.domain + "/issue-image/"+$(this).attr("eventId")+".html",
				title: $("#contentTitle").text(),
				imgsrc: $("a.img>img").attr("src"),
				desc: $("#contentDesp").text()	
		};
		shareToObj.shareType('tsina',info);
	});
});