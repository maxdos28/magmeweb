<#macro header cur="commonheader">
	<div class="header headerAdmin clearFix">
	<div class="outer png">
        <div class="inner png">
            <h1 class="logo" id="logo"><a class="png" href="${systemProp.appServerUrl}/look/look-start-pic.action">(SEO)麦米 Magme</a></h1>
            <!--nav-->
            <ul class="menu" id="menu">
            	<#if session_look_user.type != 4>
            	<!-- 编辑 -->
            	<#if session_look_user.type == 2||session_look_user.type == 6>
                <li id="menu_item"><a href="${systemProp.appServerUrl}/look/look-article!viewEditArticle.action">栏目管理</a></li>
            	<#else>
            	<!-- 统计 -->
            	<#if session_look_user.type == 3>
            	<li id="menu_stat"><a href="${systemProp.appServerUrl}/look/look-stat-category-pv-uv.action">统计数据</a></li>
            	<#else>
            	<li id="menu_startpic"><a href="${systemProp.appServerUrl}/look/look-start-pic.action">启动管理</a></li>
                <li id="menu_item"><a href="${systemProp.appServerUrl}/look/look-category.action">栏目管理</a></li>
                <li id="menu_user"><a href="${systemProp.appServerUrl}/look/look-user-info.action">用户管理</a></li>
                <li id="menu_egg"><a href="${systemProp.appServerUrl}/look/look-gift-manager.action">礼品和彩蛋管理</a></li>
                <li id="menu_stat"><a href="${systemProp.appServerUrl}/look/look-stat-category-pv-uv.action">统计数据</a></li>
                <li id="menu_message"><a href="${systemProp.appServerUrl}/look/look-message.action">消息管理</a></li>
            	</#if>                
                </#if>
                </#if>
                <!-- 广告 -->
                <#if session_look_user.type == 4 || session_look_user.type == 1>
                <li id="menu_ad"><a href="${systemProp.appServerUrl}/look/look-ad-manager-by-category.action">广告管理</a></li>
                </#if>
            </ul>
            <!--loginBar-->
            <ul class="conUser" id="userBar" style="display:block;">
                <li class="name"><strong id="nickName">${session_look_user.nickName}</strong></li>
                 <li class="goHome"><a href="#" title="转到网站">转到网站</a></li>
                <li class="logout"><a href="${systemProp.appServerUrl}/look/look-admin-user!logout.action" title="退出">退出</a></li>
            </ul>
        </div>
        <div id="itemSubDiv" class="subNav" style="display:none">
            <ul class="inner">
            	<#if session_look_user.type == 2||session_look_user.type == 6>
            	<li id="submenu_article"><a href="${systemProp.appServerUrl}/look/look-article!viewEditArticle.action">文章管理</a></li>
            	<#else>
                <li id="submenu_category"><a href="${systemProp.appServerUrl}/look/look-category.action">分类管理</a></li>
                <li id="submenu_item"><a href="${systemProp.appServerUrl}/look/look-item.action">栏目管理</a></li>
                <li id="submenu_article"><a href="${systemProp.appServerUrl}/look/look-article.action">文章管理</a></li>
            	</#if>
            </ul>
        </div>
        <div id="userSubDiv" class="subNav" style="display:none">
            <ul class="inner">
                <li id="submenu_userInfo"><a href="${systemProp.appServerUrl}/look/look-user-info.action">用户信息</a></li>
                <li id="submenu_userGold"><a href="${systemProp.appServerUrl}/look/look-user-gold.action">用户金币</a></li>
                <li id="submenu_userGift"><a href="${systemProp.appServerUrl}/look/look-user-gift.action">礼品兑换</a></li>
                <li id="submenu_userFeedback"><a href="${systemProp.appServerUrl}/look/look-user-feedback.action">用户反馈</a></li>
            </ul>
        </div>
        <div id="eggSubDiv" class="subNav" style="display:none">
            <ul class="inner">
                <li id="submenu_gift"><a href="${systemProp.appServerUrl}/look/look-gift-manager.action">礼品管理</a></li>
                <li id="submenu_limit"><a href="${systemProp.appServerUrl}/look/look-limit-manager.action">抢购管理</a></li>
                <li id="submenu_lucky"><a href="${systemProp.appServerUrl}/look/look-lucky-manager.action">抽奖管理</a></li>
                <li id="submenu_luckyCard"><a href="${systemProp.appServerUrl}/look/look-lucky-card-manager.action">刮刮卡管理</a></li>
                <li id="submenu_egg"><a href="${systemProp.appServerUrl}/look/look-egg-manager.action">彩蛋策略</a></li>
                <li id="submenu_userEgg"><a href="${systemProp.appServerUrl}/look/look-user-egg.action">彩券管理</a></li>
            </ul>
        </div>
        <div id="adSubDiv" class="subNav" style="display:none">
            <ul class="inner">
                <li id="submenu_adcategory"><a href="${systemProp.appServerUrl}/look/look-ad-manager-by-category.action">分类广告</a></li>
                <li id="submenu_adpage"><a href="${systemProp.appServerUrl}/look/look-ad-manager-by-page.action">插页广告</a></li>
            </ul>
        </div>
        <div id="statSubDiv" class="subNav" style="display:none">
            <ul class="inner">
                <li id="submenu_statcate1"><a href="${systemProp.appServerUrl}/look/look-stat-category-pv-uv.action">分类（栏目）浏览量报表</a></li>
                <li id="submenu_statcate2"><a href="${systemProp.appServerUrl}/look/look-stat-category-trend.action">分类（栏目）浏览趋势报表</a></li>
                <li id="submenu_statadditem"><a href="${systemProp.appServerUrl}/look/look-stat-add-item-num.action">栏目收藏报表</a></li>
                <li id="submenu_statap"><a href="${systemProp.appServerUrl}/look/look-stat-article-or-pub.action">杂志（文章）访问量报表</a></li>
                <li id="submenu_statua"><a href="${systemProp.appServerUrl}/look/look-stat-user-area.action">用户访问地区报表</a></li>
                <li id="submenu_statud"><a href="${systemProp.appServerUrl}/look/look-stat-user-device.action">用户访问设备报表</a></li>
                <li id="submenu_statur"><a href="${systemProp.appServerUrl}/look/look-stat-user-reg.action">注册用户分布表</a></li>
                <li id="submenu_statui"><a href="${systemProp.appServerUrl}/look/look-stat-user-install.action">安装（注册）用户报表</a></li>
                <li id="submenu_statae"><a href="${systemProp.appServerUrl}/look/look-stat-article-by-employee.action">员工文章发布量报表</a></li>
            </ul>
        </div>
        <div id="messageSubDiv" class="subNav" style="display:none">
            <ul class="inner">
                <li id="submenu_msg"><a href="${systemProp.appServerUrl}/look/look-message.action">系统消息</a></li>
                <li id="submenu_send"><a href="${systemProp.appServerUrl}/look/look-send.action">推送消息</a></li>
            </ul>
        </div>
    </div>
    
	</div>
</#macro>