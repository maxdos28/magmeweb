<!DOCTYPE HTML>
<!--[if lt IE 7 ]> <html class="ieOld ie6"> <![endif]-->
<!--[if IE 7 ]>    <html class="ieOld ie7"> <![endif]-->
<!--[if IE 8 ]>    <html class="ieOld ie8"> <![endif]-->
<!--[if (gte IE 9)|!(IE)]><!--> <html> <!--<![endif]-->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${systemProp.staticServerUrl}/v3/style/reset.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/global.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/channelAdmin.css" rel="stylesheet" type="text/css" />
<script src="${systemProp.staticServerUrl}/js/systemProp.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery-new.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.lightbox.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.easing.1.3.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.backgroundPosition.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.inputfocus.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.scrollphoto.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.tagbox.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/useFunction.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/global.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/admin.js"></script>
<!--[if lt IE 7]>
<script src="${systemProp.staticServerUrl}/v3/js/DD_belatedPNG_0.0.8a-min.js"></script>
<script>
  DD_belatedPNG.fix('.png');
  document.execCommand("BackgroundImageCache",false,true);
</script>
<![endif]-->
<title>${title!"Magme"}</title>
<style>
html, body{ overflow:hidden;}
</style>
<script>
$(function(){
	var $login = $("#conLogin");
	var $reg = $("#conReg");
	$login.find(".action .btnWB").click(function(){
		if(!isIE6){
			$login.css({boxShadow:"none"}).animate({marginLeft:-460,opacity:0.3},400).animate({marginLeft:-210,opacity:0},800,"easeOutQuart");
			$reg.show().css({opacity:0,boxShadow:"none"}).animate({marginLeft:40,opacity:0.5},400).animate({marginLeft:-210,opacity:1},800,"easeOutQuart",function(){$login.hide();$reg.css({boxShadow:"0 0 200px rgba(0,0,0,0.3)"})});
		}else{
			$login.hide();
			$reg.show();
		}
	});
	$reg.find(".action .btnWB").click(function(){
		if(!isIE6){
			$reg.css({boxShadow:"none"}).animate({marginLeft:-460,opacity:0.3},400).animate({marginLeft:-210,opacity:0},800,"easeOutQuart");
			$login.show().css({opacity:0,boxShadow:"none"}).animate({marginLeft:40,opacity:0.5},400).animate({marginLeft:-210,opacity:1},800,"easeOutQuart",function(){$reg.hide();$login.css({boxShadow:"0 0 150px rgba(0,0,0,0.3)"})});
		}else{
			$reg.hide();
			$login.show();
		}
	});
	

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
<body  class="pageAdminLogin">
<!--header-->
<!--body-->
${body}

</body>
</html>