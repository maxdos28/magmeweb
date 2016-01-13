<#import "../components/newPublisher.ftl" as np>
<!DOCTYPE HTML>
<!--[if lt IE 7 ]> <html class="ieOld ie6"> <![endif]-->
<!--[if IE 7 ]>    <html class="ieOld ie7"> <![endif]-->
<!--[if IE 8 ]>    <html class="ieOld ie8"> <![endif]-->
<!--[if (gte IE 9)|!(IE)]><!--> <html> <!--<![endif]-->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${title!"Magme"}</title>
<@np.jsCss />
<script>
$(function(){
	var menu=$("div[menu]").attr("menu")||"";
	$("#menu_"+menu).addClass("current");
	
	var label=$("div[label]").attr("label")||"";
	$("#label_"+label).addClass("current");
	
	$.jquerytagbox("#channelTab",0);
});
</script>
</head>
<body>

<!--header-->
<@np.header class="ios" />
<!--body-->
<div class="body">
	<!--topBar-->
	${body}
</div>

</body>
</html>