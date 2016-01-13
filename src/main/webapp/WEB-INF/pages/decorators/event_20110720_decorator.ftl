<#import "../components/header.ftl" as header>
<#import "../components/footer.ftl" as footer>
<#import "../components/gap.ftl" as gap>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Magme</title>
<link href="${systemProp.staticServerUrl}/style/reset.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/style/global.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/event/20110706/style.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/event/lightbox/css/jquery.lightbox-0.5.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/systemProp.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/jquery.lightbox.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/jquery.easing.1.3.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/jquery.inputfocus.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/jquery.sampleFancyBox.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/ajaxfileupload.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/global.js"></script>
<script src="${systemProp.staticServerUrl}/js/jquery.tagbox.js"></script>
<script src="${systemProp.staticServerUrl}/event/lightbox/js/jquery.lightbox-0.5.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/event/20110706/jquery.backgroundPosition.js"></script>
<script type="text/javascript" src="${systemProp.appServerUrl}/kindeditor/kindeditor.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/event_20110720.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/event/20110706/function.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/scrolltopcontrol.js" ></script>

<script>
$(function(){
	$("ol.top10").each(function(){
		var i=0;
		$(this).find("li").slice(0,3).addClass("top");
		$(this).find("li").each(function(){
			i++;
			$(this).prepend("<span>"+i+"</span>");
		});
		
	});
})
</script>
<style>
.body .left .menu{ background-position:0 ${pos!"0"};}

</style>
<!--[if lte IE 6]>
<link href="${systemProp.staticServerUrl}/style/ie6.css" rel="stylesheet" type="text/css" />
<![endif]-->


</head>
<body>
<div class="main">
<!--header-->
<@header.main menuId="eventPage" />
<!--body-->
<div class="body clearFix">
	<div class="left">
    	<ul class="menu clearFix">
        	<li><a href="${systemProp.appServerUrl}/event!index.action?eventCode=20110720">活动首页</a></li>
			<li><a href="${systemProp.appServerUrl}/event!introduction.action?eventCode=20110720">活动介绍</a></li>        	
        	<li><a href="${systemProp.appServerUrl}/event!read.action?eventCode=20110720&pos=60px">招聘现场流程</a></li>
        	<li><a href="${systemProp.appServerUrl}/event!opusList.action?eventCode=20110720&pos=90px">20强作品</a></li>
			<li><a href="${systemProp.appServerUrl}/event!show.action?eventCode=20110720&pos=120px">现场照片</a></li>
			<li><a href="${systemProp.appServerUrl}/event!join.action?eventCode=20110720&pos=150px">杂志派送</a></li>
        </ul>
        <a class="registerLink" href="${systemProp.appServerUrl}/event!join.action?eventCode=20110720">参与即送精美杂志</a>
    	<ol class="top10">
        	<li><a href="javascript:void(0)">用户名</a></li>
        	<li><a href="javascript:void(0)">用户名</a></li>
        	<li><a href="javascript:void(0)">用户名</a></li>
        	<li><a href="javascript:void(0)">用户名</a></li>
        	<li><a href="javascript:void(0)">用户名</a></li>
        	<li><a href="javascript:void(0)">用户名</a></li>
        	<li><a href="javascript:void(0)">用户名</a></li>
        	<li><a href="javascript:void(0)">用户名</a></li>
        	<li><a href="javascript:void(0)">用户名</a></li>
        	<li><a href="javascript:void(0)">用户名</a></li>
        </ol>
    </div>
	${body}
</div>
</div>
<!--footer-->
<@footer.main class=""/>
</body>
</html>