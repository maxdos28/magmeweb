<script src="${systemProp.staticServerUrl}/v3/widget20120529/js/event.js"></script>
<!--conTagWall-->
<div class="outer">
	<!--notification-->
	<!--eventList-->
	<div id="bodyEvent" class="event">
		<#list evePageInfo.data as event >
		<#if event_index lt evePageInfo.limit>
		    <div class="item L${event_index+1}">
			<a eventId="${event.id}" class="inner" href="javascript:void(0)" name="reader">
			    <h2>${event.title!}</h2>
			    <div class="img size${event.eventClass}" style="background-image:url(${systemProp.fpageServerUrl}/event/${event.imgFile})">
				<img src="${systemProp.fpageServerUrl}/event/${event.imgFile}" />
			    </div>
			    <div class="con png">
				<p>${event.description!}</p>
			    </div>
			</a>
		    </div>
		</#if>
		</#list>
	</div>
	<!--changePage-->
	<div class="changePage">
	    <a class="pre" href="javascript:void(0)" title="上一页"></a>
	    <a class="next" href="javascript:void(0)" title="下一页"></a>
	</div>
</div>

<script type="text/javascript">
var totalSize = parseInt("${evePageInfo.total}");
var xx= "${xx!}"; 
	function getX(){
		return xx;
	}
var srcFlg = "${srcFlg!}";
var actionRan = "${ran!}";

(function($){
jQuery.autoLayout = function(id) {
	
var $item = $(id);
var winHeight = $(window).height();
var winWidth = $(window).width();
$(window).bind("resize", fnResize);
//添加文字遮罩
$item.each(function(){
	$(this).find(".inner").append("<div class='textMask png'></div>");
});
//处理排版
fnResize();
//设置旋转后背景和左右填充
function fnResize(){
	winWidth = $(window).width();
	if(winWidth>768){
		$("body>div").removeClass("h").addClass("w");
		$("body>p.footer").css({width:1024});
	}else{
		$("body>div").removeClass("w").addClass("h");
		$("body>p.footer").css({width:768});
	}
	fnSetImg();
}

function fnSetImg(){
	$item.each(function(){
		var W = $(this).width();
		var H = $(this).height();
		var inW = $(this).find(".inner").innerWidth();
		var inH = $(this).find(".inner").innerHeight();
		//重设Title样式
		$(this).find(".inner").removeClass("title");
		$(this).find("h2").css({width:inW*1.0});
		$(this).find(".img").insertAfter($(this).find("h2"));
		//显示区域判断
		if( W*H < 220000){
			//宽高比例判断
			if( inW/inH > 1.2 ){
				$(this).find(".img").css({width:inW*0.4,height:inH-65,float:"right",margin:"0 0 15px 15px"});
			}else if( inW/inH > 0.8 && $(this).find(".img").hasClass("size21")){
				$(this).find(".img").css({width:inW*0.5,height:inH*0.9-40,float:"right",margin:"0 0 15px 15px"});
			}else{
				$(this).find("h2").insertAfter($(this).find(".img"));
				$(this).find(".img").css({width:inW*1.0,height:inH*0.4,float:"none",margin:"0 0 15px 0"});
			}
		}else{
			if( $(this).find(".img").hasClass("size11") ){
				$(this).find(".img").css({width:inW*0.5,height:inW*0.5,float:"right",margin:"0 0 15px 15px"});
			}else if( $(this).find(".img").hasClass("size12") ){
				$(this).find("h2").insertAfter($(this).find(".img"));
				$(this).find(".img").css({width:inW*1.0,height:inW*0.6,float:"none",margin:"0 0 15px 0"});
			}else if( $(this).find(".img").hasClass("size21") ){
				$(this).find(".img").css({width:inW*0.5,height:inH*0.8,float:"right",margin:"0 0 15px 15px"});
			}
		}
		//判断显示为全图模式
		if(W*H > 110000){
			if( ($(this).find(".img").hasClass("size22")) ||
				(W/H > 1.5 && $(this).find(".img").hasClass("size12")) ||
				(W/H < 0.67 && $(this).find(".img").hasClass("size21"))){
				$(this).find(".inner").addClass("title").find(".img").css({width:inW*1.0,height:inH*1.0}).end().find("h2").css({width:inW*1.0-20});
				$(this).find(".textMask").remove();
			}
		}
		$(this).find(".img>img").coverImg();
    });
}

}})(jQuery);

$(function(){
	$.autoLayout(".item");
})
</script>