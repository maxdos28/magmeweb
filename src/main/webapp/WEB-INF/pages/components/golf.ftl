<#macro header class >
<!--header-->
<div class="header clearFix headerAdmin">
	<div class="outer png" id="newPubulisherHead">
        <div class="inner png">
            <h1 class="logo" id="logo"><a class="png" href="javascript:void(0);">(SEO)麦米 Magme</a></h1>
            <!--nav-->
            <ul class="menu" id="menu">
                <li id="menu_news"><a href="/golf/work-publish.action">新闻管理</a></li>
                <li id="menu_type"><a href="/golf/news-type.action">分类管理</a></li>
                <li id="menu_message"><a href="/golf/ios-manage.action">消息记录</a></li>
            </ul>
            <!--loginBar-->
            <ul class="conUser" style="display:block;">
            	<li class="name"><strong id="nickName">${(session_golf_news_manage.userName)!""}</strong></li>
                <li class="logout" id="logout"><a href="/golf/login!exit.action" title="退出">退出</a></li>
            </ul>
        </div>
    </div>
</div>
</#macro>

<#macro jsCss>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${systemProp.staticServerUrl}/v3/style/reset.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/global.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/channelAdmin.css" rel="stylesheet" type="text/css" />
<script src="${systemProp.staticServerUrl}/js/systemProp.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery-new.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.easing.1.3.js"></script>
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
</#macro>



