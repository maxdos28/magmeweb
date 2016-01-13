<!DOCTYPE HTML>
<!--[if lt IE 7 ]> <html class="ieOld ie6"> <![endif]-->
<!--[if IE 7 ]>    <html class="ieOld ie7"> <![endif]-->
<!--[if IE 8 ]>    <html class="ieOld ie8"> <![endif]-->
<!--[if (gte IE 9)|!(IE)]><!--> <html> <!--<![endif]-->
<head>
<title>${(article.title)!''}- 麦米网Magme</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="${systemProp.staticServerUrl}/js/systemProp.js"></script>
<link href="${systemProp.staticServerUrl}/v3/style/pop.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/reset.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/global.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/channelHomev4.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/channelDetailv4.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/template/style/jquery.mCustomScrollbar.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/template/style/tpl.css" rel="stylesheet" type="text/css" />
<script src="${systemProp.staticServerUrl}/v3/js/jquery-new.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.easing.1.3.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.mousewheel.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.coverImg.js"></script>


<script src="${systemProp.staticServerUrl}/v3/js/jquery.sampleFancyBox.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.lightbox.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.tagbox.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/useFunction.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/global.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/header.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/sns/snsDetail.js"></script>

<script src="${systemProp.staticServerUrl}/v3/template/js/modernizr-2.5.3.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/template/js/jquery-ui-1.8.21.custom.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/template/js/jquery.mCustomScrollbar.js"></script>
<script src="${systemProp.staticServerUrl}/v3/template/js/jquery.scrollto.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/template/js/tpl.js"></script>
<!--[if lt IE 7]>
<script src="${systemProp.staticServerUrl}/v3/js/DD_belatedPNG_0.0.8a-min.js"></script>
<script>
  DD_belatedPNG.fix('.png');
  document.execCommand("BackgroundImageCache",false,true);
</script>
<![endif]-->
</head>
<body>
<div id="body">
<#import "../components/header.ftl" as header>
<@header.main searchType="Creative"/>
<!--header-->
<div class="header20120905 clearFix">
  <div class="outer headerNav">
    	<div class="inner clearFix">
            <div class="logo" id="logo"><h1><a class="png" href="http://www.magme.com/" alt="麦米网电子杂志" title="无处不悦读">麦米网Magme</a></h1></div>
            <div class="subNav">
            	<ul class="classification clearFix">
            	<#if creativeCategoryList?? && creativeCategoryList?size gt 0 >
            		<#list creativeCategoryList as cc>
            			 <li <#if sortId?? && sortId == cc.id> class="current"</#if> ><a  href="http://www.magme.com/home/${cc.id}.html" >${cc.name}</a>
                    	</li>
            		</#list>
            	</#if>
                </ul>
            </div>
        </div>
    </div>
</div>
<!--body-->
<div class="topReaderNull">
    这么多文章居然都被你看完了，换个分类看看吧~！
</div>

<#import "../components/footer.ftl" as footer>
<@footer.main class="footerMini footerStatic" />
</div>
</body>
</html>