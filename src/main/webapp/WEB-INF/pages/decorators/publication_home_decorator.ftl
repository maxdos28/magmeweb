<#import "../components/header.ftl" as header>
<#import "../components/footer.ftl" as footer>
<!DOCTYPE HTML>
<!--[if lt IE 7 ]> <html class="ie6"> <![endif]-->
<!--[if IE 7 ]>    <html class="ie7"> <![endif]-->
<!--[if IE 8 ]>    <html class="ie8"> <![endif]-->
<!--[if (gte IE 9)|!(IE)]><!--> <html> <!--<![endif]-->
<head>
<title>${(publication.name)!""}电子版 ${(publication.name)!""}电子杂志 ${(publication.name)!""}在线阅读 — 麦米网magme${(issue.issueNumber)!""}</title>
<meta name="Keywords" content="${(publication.name)!""}电子版,${(publication.name)!""}电子杂志,${(publication.name)!""}在线阅读"/>
<meta name="Description" content="<#--${(publication.description)!""} 由于新的description有富文本，会导致页面断掉和黎明协商后去掉的。comment this description by fredy in 2012-10-08-->">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${systemProp.staticServerUrl}/v3/style/reset.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/global.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/pop.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/channelMagazineHome.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/conBigHeaderv6.css" rel="stylesheet" type="text/css" />

<script src="${systemProp.staticServerUrl}/js/systemProp.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery-1.6.2.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.colorpicker.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.masonry.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.easing.1.3.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.onImagesLoad.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.coverImg.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.lightbox.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.inputfocus.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.scrollphoto.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/scrolltopcontrol.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.tagbox.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/shareToInternet.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.sampleFancyBox.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.tmpl.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.lazyload.mini.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/useFunction.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/global.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/publisher.js"></script>



<script src="${systemProp.staticServerUrl}/v3/js/jquery.m1V4.js"></script>
<!--[if lt IE 7]>
<script src="${systemProp.staticServerUrl}/v3/js/DD_belatedPNG_0.0.8a-min.js"></script>
<script>
  DD_belatedPNG.fix('.png');
  document.execCommand("BackgroundImageCache",false,true);
</script>
<![endif]-->
</head>
<body>
<!--header-->
<div class="header20120905 clearFix">
    

    <div class="outer headerNav">
        <div class="inner clearFix">
            <div class="meshop"><a href="http://go.magme.com"><img src="${systemProp.staticServerUrl}/v3/images/mego/btn.png"/></a></div>
            <div class="logo" id="logo"><a class="png" href="http://www.magme.com/" title="麦米购 轻松全球购">麦米购 magme go</a></div>
            <div class="subNav">
                <ul class="classification clearFix">
                     <li class=""><a href="${systemProp.appServerUrl}/publish/magazine.html" >返回杂志首页</a></li>
                </ul>
            </div>
        </div>
    </div>
    
</div>

<!--body-->
${body}

<!--footer-->
<@footer.main class="footerMini footerStatic"/>

</body>
</html>