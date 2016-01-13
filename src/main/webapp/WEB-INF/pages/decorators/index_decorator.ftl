<#import "../components/header.ftl" as header>
<#import "../components/footer.ftl" as footer>
<#import "../components/gap.ftl" as gap>
<#import "../user/conUserInfo.ftl" as conUserInfo>
<#import "../user/conSubMenu.ftl" as conSubMenu>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:xn="http://www.renren.com/2009/xnml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>麦米网，杂志免费在线极速阅读平台</title>
<meta name="description" content="麦米网——电子杂志免费在线极速阅读平台。在这里看杂志，聊杂志，分享交流。阅读麦米，开启新生活！" />
<meta name="keywords" content="麦米，电子杂志，在线杂志，免费电子杂志，电子杂志下载，数码杂志，时尚杂志，汽车杂志，财经杂志" />
<link href="${systemProp.staticServerUrl}/style/reset.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/style/global.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/style/pop.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/style/channelHome.css" rel="stylesheet" type="text/css" />
<script src="${systemProp.staticServerUrl}/js/systemProp.js"></script>
<script src="${systemProp.staticServerUrl}/js/jquery-1.6.2.min.js"></script>
<script src="${systemProp.staticServerUrl}/js/jquery.lightbox.js"></script>
<script src="${systemProp.staticServerUrl}/js/jquery.sampleFancyBox.js"></script>
<script src="${systemProp.staticServerUrl}/js/jquery.inputfocus.js"></script>
<script src="${systemProp.staticServerUrl}/js/jquery.tagbox.js"></script>
<script src="${systemProp.staticServerUrl}/js/shareToInternet.js"></script>
<script src="${systemProp.staticServerUrl}/js/useFunction.js"></script>
<script src="${systemProp.staticServerUrl}/js/global.js"></script>
<script src="${systemProp.staticServerUrl}/js/index.js"></script>

<#if t??>
<link href="${systemProp.staticServerUrl}/style/channelHome${t}.css" type="text/css" rel="stylesheet">
<#else>
<link href="${systemProp.staticServerUrl}/style/channelHome1.css" type="text/css" rel="stylesheet">
</#if>


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



</head>
<body>

<!--header-->
<@header.main/>

<!--body-->
${body}

<!--footer-->
<@footer.main class="footerMini footerStatic"/>
</body>
</html>
