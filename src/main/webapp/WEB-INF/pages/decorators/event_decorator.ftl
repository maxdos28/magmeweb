<#import "../components/header.ftl" as header>
<#import "../components/footer.ftl" as footer>
<#import "../components/gap.ftl" as gap>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:xn="http://www.renren.com/2009/xnml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Magme</title>
<link href="${systemProp.staticServerUrl}/style/reset.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/style/pop.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/style/global.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/style/channelDaren.css" rel="stylesheet" type="text/css" />
<script src="${systemProp.staticServerUrl}/js/systemProp.js"></script>
<script src="${systemProp.staticServerUrl}/js/jquery-1.6.2.min.js"></script>
<script src="${systemProp.staticServerUrl}/js/jquery.lightbox.js"></script>
<script src="${systemProp.staticServerUrl}/js/jquery.inputfocus.js"></script>
<script src="${systemProp.staticServerUrl}/js/jquery.tagbox.js""></script>
<script src="${systemProp.staticServerUrl}/js/shareToInternet.js"></script>
<script src="${systemProp.staticServerUrl}/js/jquery.sampleFancyBox.js"></script>
<script src="${systemProp.staticServerUrl}/js/useFunction.js"></script>
<script src="${systemProp.staticServerUrl}/js/global.js"></script>

<style>
.userList li{ width:163px; height:30px; line-height:30px; margin-bottom:10px; float:left;}
.top10{position:absolute; top:690px; width:230px; overflow:hidden;}
.top10 li{ padding:6px 0; font-size:14px;}
</style>
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

<@gap.main />
</head>
<body>

<!--header-->
<@header.main searchType="Issue"/>

<!--body-->
${body}

<!--footer-->
<@footer.main class="footerMini footerStatic"/>


</body>
</html>
