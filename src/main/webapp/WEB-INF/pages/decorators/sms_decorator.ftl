<#import "../components/smsComm.ftl" as np>
<!DOCTYPE HTML>
<!--[if lt IE 7 ]> <html class="ie6"> <![endif]-->
<!--[if IE 7 ]>    <html class="ie7"> <![endif]-->
<!--[if IE 8 ]>    <html class="ie8"> <![endif]-->
<!--[if (gte IE 9)|!(IE)]><!--> <html> <!--<![endif]-->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Magme</title>
<script src="${systemProp.staticServerUrl}/js/systemProp.js"></script>
<link href="${systemProp.staticServerUrl}/v3/sms/style/global.css" rel="stylesheet" type="text/css" />
<script src="${systemProp.staticServerUrl}/v3/sms/js/jquery-new.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/sms/js/jquery.inputfocus.js"></script>
<script src="${systemProp.staticServerUrl}/v3/sms/js/admin.js"></script>
<script src="${systemProp.staticServerUrl}/v3/sms/devjs/useFunction.js"></script>
<script src="${systemProp.staticServerUrl}/v3/sms/devjs/smsUserLogin.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.sampleFancyBox.js"></script>
</head>
<script>
$(function(){
fnReadyTable();
var $JQtableBg = $("table.JQtableBg");
var $JQtableHover = $("table.JQtableBg tbody tr");
$JQtableBg.each(function(){
	$(this).find("tbody tr:odd").addClass('bgColorTable');
});
$JQtableHover.live("mouseover",function(){
$(this).addClass("bgTrHover");
});
$JQtableHover.live("mouseout",function(){
	$(this).removeClass("bgTrHover");
});
})
</script>
<script>
$(function(){
	var menu=$("div[menu]").attr("menu")||"";
	$("#menu_"+menu).addClass("current");
});
</script>

<script>
//menu highlight
$(function(){
   var location=window.location.href.substring(19);
   $("a[href='"+location+"']").parent("li").addClass("current");
   $("a[href='"+location+"']").parent("li").parent("ul").parent("li").addClass("current");
});
</script>

<body>
<!--header-->
<@np.header class />
<!--body-->
${body}
</body>
</html>
