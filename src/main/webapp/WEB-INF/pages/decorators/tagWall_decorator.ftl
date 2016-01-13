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
<link href="${systemProp.staticServerUrl}/style/kanmi.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/style/pop.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/systemProp.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/jquery.color.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/jquery.sampleFancyBox.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/jquery.lightbox.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/jquery.easing.1.3.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/jquery.inputfocus.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/jquery.masonry.min.js" ></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/global.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/scrolltopcontrol.js" ></script>
<!--[if lte IE 6]>
<link href="${systemProp.staticServerUrl}/style/ie6.css" rel="stylesheet" type="text/css" />
<![endif]-->
<!--[if lt IE 7]>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/DD_belatedPNG_0.0.8a-min.js"></script>
<script>
  DD_belatedPNG.fix('.png');
  document.execCommand("BackgroundImageCache",false,true);
</script>
<![endif]-->


</head>
<body class="fullScreen">
<div class="main">
<!--header-->
<@header.main menuId="tagWallPage" />
<!--body-->
${body}
</div>
<!--footer-->
<@footer.main class="footerMini footerStatic"/>


</body>
</html>
