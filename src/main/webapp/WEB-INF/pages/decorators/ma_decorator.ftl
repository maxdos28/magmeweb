<#import "../ma/maAdminHeader.ftl" as maheader>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Magme</title>
<link href="${systemProp.staticServerUrl}/v3/style/ma/reset.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/ma/global.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/ma/channelAdmin.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/ma/pop.css" rel="stylesheet" type="text/css" />
<script src="${systemProp.staticServerUrl}/v3/js/ma/jquery-new.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/ma/jquery.inputfocus.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/ma/useFunction.js"></script>
<script src="${systemProp.staticServerUrl}/js/systemProp.js"></script>
<!--[if lte IE 6]>
<link href="${systemProp.staticServerUrl}/style/ie6.css" rel="stylesheet" type="text/css" />
<![endif]-->
<!--[if lt IE 7]>
<script src="${systemProp.staticServerUrl}/js/DD_belatedPNG_0.0.8a-min.js"></script>
<script>
  DD_belatedPNG.fix('.png');
  document.execCommand("BackgroundImageCache",false,true);
</script>
<![endif]-->
<script>
$(function(){
	var menu=$("div[menu]").attr("menu")||"";
	$("#menu_"+menu).addClass("current");
	if(menu=="adTradeNew")
	{
		var submenu=$("div[submenu]").attr("submenu")||"";
		$("#submenu_"+submenu).addClass("current");
		$("#adTradeNewSubDiv").show();
	}
	else
	{
		$("#adTradeNewSubDiv").hide();
	}
	
	$(document).ajaxStart(onStart)
		       .ajaxStop(onStop)
		       .ajaxSend(onSend)
		       .ajaxComplete(onComplete)
		       .ajaxSuccess(onSuccess)
		       .ajaxError(onError);
	
	function onStart(event) {
		$(".overlay").css({'display':'block','opacity':'0.8'});
		$(".showbox").stop(true).animate({'margin-top':'400px','opacity':'1'},200);
		$('#fancybox-wrap').css({zIndex:999});
	}
	function onStop(event) {		
		$(".overlay").css({'display':'none','opacity':'0'});
		$(".showbox").stop(true).animate({'margin-top':'0px','opacity':'0'},400);
		$('#fancybox-wrap').css({zIndex:1101});
	}
	function onSend(event, xhr, settings) {
		
	}
	function onComplete(event, xhr, settings) {
		
	}
	function onSuccess(event, xhr, settings) {
		
	}
	function onError(event, xhr, settings, err) {
		
	}
});
</script>
<style type="text/css">
*{margin:0;padding:0;list-style-type:none;}
a,img{border:0;}
.demo{margin:100px auto 0 auto;width:400px;text-align:center;font-size:18px;}
.demo .action{color:#3366cc;text-decoration:none;font-family:"微软雅黑","宋体";}

.overlay{position:fixed;top:0;right:0;bottom:0;left:0;z-index:998;width:100%;height:100%;_padding:0 20px 0 0;background:#f6f4f5;display:none;}
.showbox{position:fixed;top:0;left:50%;z-index:9999;opacity:0;filter:alpha(opacity=0);margin-left:200px;}
*html,*html body{background-image:url(about:blank);background-attachment:fixed;}
*html .showbox,*html .overlay{position:absolute;top:expression(eval(document.documentElement.scrollTop));}
#AjaxLoading{border:1px solid #8CBEDA;color:#37a;font-size:12px;font-weight:bold;}
#AjaxLoading div.loadingWord{width:180px;height:50px;line-height:50px;border:2px solid #D6E7F2;background:#fff;}
#AjaxLoading img{margin:10px 15px;float:left;display:inline;}
</style>
</head>
<body>

	<@maheader.header/>
	${body}
	<div class="overlay"></div>
<div id="AjaxLoading" class="showbox">
	<div class="loadingWord"><img src="${systemProp.staticServerUrl}/images/waiting.gif">加载中，请稍候...</div>
</div>
</body>
</html>

