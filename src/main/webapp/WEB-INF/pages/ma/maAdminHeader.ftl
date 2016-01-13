<#macro header cur="commonheader">
	<div class="header headerAdmin clearFix">
	<div class="outer png">
        <div class="inner png">
            <h1 class="logo" id="logo"><a class="png" href="${systemProp.appServerUrl}/ma/ad-trade.action"></a></h1>
            <!--nav-->
            <ul class="menu" id="menu">
                <li id="menu_adTradeList"><a href="${systemProp.appServerUrl}/ma/ad-trade.action">广告管理</a></li>
                <li id="menu_adTradeNew"><a href="${systemProp.appServerUrl}/ma/ad-trade-new-of-pub.action">广告投放</a></li>
                <li id="menu_keyword"><a href="${systemProp.appServerUrl}/ma/keyword.action">标签管理</a></li>
                <li id="menu_size"><a href="${systemProp.appServerUrl}/ma/size.action">尺寸管理</a></li>
            </ul>
            <!--loginBar-->
            <ul class="conUser" id="userBar" style="display:block;">
                <li class="name"><strong id="nickName">${session_ma_user.nickName}</strong></li>
                <li class="logout"><a href="${systemProp.appServerUrl}/ma/login!logout.action" title="退出">退出</a></li>
            </ul>
        </div>
        <div id="adTradeNewSubDiv" class="subNav" style="display:none">
            <ul class="inner">
                <li id="submenu_pub"><a href="${systemProp.appServerUrl}/ma/ad-trade-new-of-pub.action">杂志广告</a></li>
                <li id="submenu_article"><a href="${systemProp.appServerUrl}/ma/ad-trade-new-of-article.action">资讯广告</a></li>
            </ul>
        </div>
    </div>
</div>
</#macro>