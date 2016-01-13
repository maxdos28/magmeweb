
$(".conShareMusic").find("input").live("keyup",_onkeypress);
//$(".conShareMusic").find("input").live("blur",_onblur);
$("#musicList>.inner>a").live("click",_musClick);

function _onkeypress(event){
	var keyCode = event.keyCode ? event.keyCode : event.which ? event.which : event.charCode;
	if(keyCode != 13 && $(".conShareMusic").find("input").val()!='') {
		search(1);
	}else{
		$("#musicList").hide();
	}
}
//function _onblur(){
//	setTimeout(function(){$("#musicList").hide();},2000);
//}

function search(page){
	xiamipage=page;
	var xiami = "http://www.xiami.com/app/nineteen/search/key/"+ encodeURIComponent($(".conShareMusic").find("input").val()) +"/logo/2/page/"+ page +"?random="+new Date().getTime()+".js&callback=getXiamiData";
	var JSONP = document.createElement("script") ;
	JSONP.type = "text/javascript";
	JSONP.src = xiami;
	//在head之后添加js文件
	setTimeout(function(){document.getElementsByTagName("head")[0].appendChild(JSONP)}, 0);
	JSONP.onload = JSONP.onreadystatechange = function(){
		if (!this.readyState || this.readyState === "loaded" || this.readyState === "complete") {
			JSONP.onload = JSONP.onreadystatechange = null;//清内存，防止IE memory leaks
		}
	}
}

var xiamipage=1;
function getXiamiData(data){
	$("#musicList").html('');
	$("#musicList").show();
	
	if(data.total > 0){
		$("#musicList").append('<div class="inner"></div>')
		for(var i in data.results){
			var mpic=data.results[i].album_logo.replace("_1","_2");
			$("#musicList>.inner").append('<a pic="'+mpic+'" song_id="'+data.results[i].song_id+'" href="javascript:void(0);">'+decodeURIComponent(data.results[i].song_name)+' — '+decodeURIComponent(data.results[i].artist_name)+'</a>');
		}
	}
	
	$("#musicList").append('<div class="fot">共有'+data.total+'首和"'+ $(".conShareMusic").find("input").val()+'"相关的歌曲（音乐服务由 <a href="javascript:void(0);">虾米网</a> 提供）</div>');
	
	if(data.total >= xiamipage*8 && xiamipage>=2){
		$("#musicList>.fot").append('<div class="page"><a href="javascript:search('+(xiamipage-1)+');">上一页</a><a href="javascript:search('+(xiamipage+1)+');">下一页</a></div>')
	}
	else if(xiamipage==1 && data.total>8){
		$("#musicList>.fot").append('<div class="page"><a href="javascript:search('+(xiamipage+1)+');">下一页</a></div>')
	}
	else if(xiamipage*8 && xiamipage==data.total/8){
		$("#musicList>.fot").append('<div class="page"><a href="javascript:search('+(xiamipage-1)+');">上一页</a></div>')
	}
	
	$("#musicList").mouseenter(function(){
		$(this).stop(true,true).fadeIn(200);
	}).mouseleave(function(){
		$(this).fadeOut(100);
	});
}
var tit="";
function _musClick(){
	if($("#cr-text-title").val()==tit)
		$("#cr-text-title").val('');
	$("#cr-music-Error").hide();
	var opval=$("#cr_operate").val();
	tit=$(this).html();
	var mus='<embed src="http://www.xiami.com/widget/0_' + $(this).attr("song_id") + '/singlePlayer.swf" type="application/x-shockwave-flash" width="257" height="33" wmode="transparent"></embed>';
	
	if(opval=='works'){
		$("#imagesList").show();
		$(".conShareImages>.imagesCon").show();
		$("#imagesList>.inner").append("<div music='"+mus+"' class='item itemComplete itemMusic uncover'><div class='inner'><a href='javascript:void(0);' class='close'></a><div class='img'><div class='mask'><div class='ico png'></div></div><img src='"+$(this).attr("pic")+"' /></div><textarea>'"+tit+"'</textarea></div></div>").show();
		$("#imagesList>.inner>.uncover>.img>img").coverImg().parents(".uncover").removeClass("uncover");
		$(".body>.sideLeftMiddleRight>.conRight").css({height : $(".body>.sideLeftMiddleRight>.conLeftMiddle").height() - 8 + "px" });
	}else{
		$(".myMusic").show();
		$(".myMusic>.player").html(mus);
		$("#musimg").attr("src",$(this).attr("pic"));
		
		if($.trim($("#cr-text-title").val())=='')
			$("#cr-text-title").val(tit);
			
	}
	$("#musicList").hide();
	
}





