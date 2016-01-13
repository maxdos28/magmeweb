<!DOCTYPE HTML>
<!--[if lt IE 7 ]> <html class="ieOld ie6"> <![endif]-->
<!--[if IE 7 ]>    <html class="ieOld ie7"> <![endif]-->
<!--[if IE 8 ]>    <html class="ieOld ie8"> <![endif]-->
<!--[if (gte IE 9)|!(IE)]><!--> <html> <!--<![endif]-->
<head>
<title>${(indexDetailContent.title)!''} - 麦米网Magme</title>
<meta name="Keywords" content="<#if (indexDetailContent.tagList)?? && (indexDetailContent.tagList?size>0)><#list indexDetailContent.tagList as tag>${(tag.name)!''},</#list></#if>,麦米网,Magme,电子杂志,在线杂志,免费电子杂志,电子杂志下载,数码杂志,时尚杂志,汽车杂志,财经杂志">
<meta name="Description" content="${(indexDetailContent.description)!''}">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<link href="${systemProp.staticServerUrl}/v3/style/reset.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/global.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/pop.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/channelDetailv4.css" rel="stylesheet" type="text/css" />
<script src="${systemProp.staticServerUrl}/js/systemProp.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery-new.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.masonry.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.easing.1.3.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.inputfocus.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/scrolltopcontrol.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.scrollphoto.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.coverImg.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.mutilSlidedoor.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/useFunction.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.sampleFancyBox.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.tagbox.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/global.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/content.js"></script>

<script>
	var getItemId=${itemId!""};
	var getType='${type!""}';
	var getBegin=10;
	var total=${total!"0"};
	// FLASH 接口,翻到新事件/作品调用 type 1:事件 2:作品
	function wordReaderFlashChangeData(itemId,type){
		getItemId=itemId;
		getType=type;
		var callback = function(rs){
			$("#contentContainer").html(rs);
			getBegin =10;
		}
		$.ajax({
			url:SystemProp.appServerUrl+"/index-detail!getDetailContent.action",
			type : "get",
			data : {"itemId":itemId,"type":type},
			success: callback
		});
	}

	function wordReaderFlashComplete(){
		var myread = ($.browser.msie)?$("#midRead").get(0):$("#midRead2").get(0);	
		myread.wordReaderFlashGetData('${itemId!""}','${type!""}','${sortId!"null"}','${tagName!"null"}');	
	};
	
	
$(function(){
	$.jqueryScrollPhoto("#conOtherRead",4,222,4,0,600);
	$.jquerySlideDoor("#conBuyAd",3,1,0,5000);
	$(".conOtherRead .inner .item .photo img, .conVideoAd img").coverImg();
	$(".shareWeiBo").click(function(e){
		if ($(".shareWeiBo").hasClass("shareWeiBoChecked")){
			$(".shareWeiBo").removeClass("shareWeiBoChecked")
		}else{
			$(".shareWeiBo").addClass("shareWeiBoChecked")
		}

	});
	
	
	//$("#ADvideo").live("click",function(){openDiv();});
	$("#ADvideoPlay").live("click",function(){openDiv($("#ADvideoPlay").attr("url"));});
	
	function openDiv(address){
		//var urlStr = address;
		//$("#videoPlayIframe").attr("src","${systemProp.appServerUrl}/index-detail!toVideoPlay.action?url="+urlStr);
		var urlStr = SystemProp.appServerUrl + "/index-detail!toVideoPlay.action?url="+address;
		$("#videoAdvertiseDialog").html("<iframe id='videoPlayIframe' src='"+urlStr+"' width='600' height='450' frameborder='0' scrolling='no'></iframe>");
		$("#videoAdvertiseDialog").fancybox();
	}
	
	var dialogClose = $.fancybox.close;
	
	$.fancybox.close = function(){
		dialogClose();
	};
	
	//设置随机显示
	fnRandom("#videoAd_div");
});
</script>
<!--[if lt IE 7]>
<script src="${systemProp.staticServerUrl}/v3/js/DD_belatedPNG_0.0.8a-min.js"></script>

<script>
  DD_belatedPNG.fix('.png');
  document.execCommand("BackgroundImageCache",false,true);
</script>
<![endif]-->
</head>
<body style="padding-top:60px">
<!--header-->
<#import "./dialog/videoAdvertise.ftl" as video>
<#import "./components/header.ftl" as header>
<#import "./detail/content.ftl" as content>
<@header.main searchType="Issue"/>
<@video.main />

<!--body-->
<div class="topReader">
    <div style="overflow: hidden;" class="inner clearFix">
    	<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" width="100%" height="600" id="midRead">
                <param name="movie" value="${systemProp.staticServerUrl}/reader/WordReaderTool.swf" />
                <param name="quality" value="high" />
                <param name="bgcolor" value="#ff0000" />
                <param name="allowScriptAccess" value="always" />
                <param name="allowFullScreen" value="true" />
                <param name="wmode" value="transparent" />  
                <!--[if !IE]>-->
                 <embed id="midRead2"  src="${systemProp.staticServerUrl}/reader/WordReaderTool.swf" quality="high" bgcolor="#869ca7"
	             width="100%" height="600" align="middle"
	             play="true" loop="false" quality="high" allowScriptAccess="always"
	             type="application/x-shockwave-flash"
	             wmode = "transparent"
	             pluginspage="http://www.macromedia.com/go/getflashplayer">
	         </embed>
                <!--[if !IE]>-->
                </object>
                <!--<![endif]-->
            </object>
    </div>
</div>

<div class="body clearFix" id="contentContainer">
    <@content.main />    
</div>
<div>

<#import "./components/footer.ftl" as footer>

<@footer.main class="footerMini footerStatic" />

</body>
<script type="text/javascript" src="http://cbjs.baidu.com/js/m.js"></script>
</html>
