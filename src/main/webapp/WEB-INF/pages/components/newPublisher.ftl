<#macro header class >
<!--header-->
<div class="header clearFix headerAdmin">
	<div class="outer png" id="newPubulisherHead">
        <div class="inner png">
            <h1 class="logo" id="logo"><a class="png" href="javascript:void(0);">(SEO)麦米 Magme</a></h1>
            <!--nav-->
            <ul class="menu" id="menu">
                <li id="menu_publication"><a href="/new-publisher/magazine-list!to.action">杂志管理</a></li>
                <#if !(superId??) >
	                 <#if session_admin?exists>
	                	 <li id="menu_publisher"><a href="/new-publisher/publication-config!to.action">杂志信息</a></li>
	                	 <li id="menu_reader"><a href="/new-publisher/em-reader!to.action">嵌入式阅读器</a></li>
	                	 <li id="menu_editor"><a href="/new-publisher/edit-publisher!to.action">编辑管理</a></li>
	                	 <li id="menu_iosManageAdmin"><a href="/new-publisher/app-manage.action">IOS管理</a></li>
	                	 <li id="menu_neteaseTask"><a href="/new-publisher/netease-cloud-reader-manage.action">网易云阅读上传队列管理</a></li>
	                <#else>
	                	<li id="menu_startPic"><a href="/new-publisher/start-pic!index.action">启动画面管理</a></li>
	                	<li id="menu_advertise"><a href="/new-publisher/advertising-list!to.action">广告管理</a></li>
	                	<li id="menu_reader"><a href="/new-publisher/em-reader!to.action">嵌入式阅读器</a></li>
	                	<#--<li id="menu_publisher"><a href="/new-publisher/publisher-config!to.action">用户设置</a></li>-->
	                	<li id="menu_data"><a href="/publish/dm-publication.action">数据后台</a></li>
	                	<li id="menu_iosManage"><a href="/new-publisher/ios-manage.action">消息管理</a></li>
	                </#if>
	            <#else>
	           
                </#if>
                
            </ul>
            <!--loginBar-->
            <ul class="conUser" id="userBar" style="display:block;">
            	<#if session_admin?exists>
            	<li class="name"><strong id="nickName">${(session_admin.userName)!""}</strong></li>
            	<#else>
            	<li class="name"><strong id="nickName">${(session_publisher.userName)!""}</strong></li>
            	<li class="setup" id="menu_publisher"><a title="设置" href="/new-publisher/publisher-config!to.action">设置</a></li>
            	</#if>
                <li class="logout" id="logout"><a href="/new-publisher/login!exit.action" title="退出">退出</a></li>
            </ul>
        </div>
        <#if class=="label">
        	<div class="subNav">
		        <ul class="inner">
		            <li >
		               <a href="/new-publisher/edit-publisher!to.action">出版商管理</a>
			            <ul>
	                    	<li id="label_publisher"><a href="/new-publisher/edit-publisher!to.action">出版商</a></li>
	                    	<li id="label_publication"><a href="/new-publisher/edit-magazine!to.action">杂志</a></li>
	                    	<li id="label_advertise"><a href="/new-publisher/edit-advertising!to.action">广告</a></li>
	                    </ul>
		            </li>
		            <li>
		                <a href="/new-publisher/edit-sidebar!to.action">侧栏与频道管理</a>
			            <ul>
	                    	<li id="label_adSide"><a href="/new-publisher/edit-sidebar!to.action">侧栏管理</a></li>
	                    	<li id="label_page"><a href="/new-publisher/edit-link!to.action">页面管理</a></li>
		            		<li id="label_channel"><a href="/new-publisher/channel!to.action">频道管理</a></li>
	                    </ul>
		            </li>
		            <li>
		                <a href="/new-publisher/work-publish.action">事件与作品管理</a>
			            <ul>
	                    	<li id="label_workpublish"><a href="/new-publisher/work-publish.action">作品发布</a></li>
	                    	<li id="label_event"><a href="/new-publisher/edit-event!to.action">事件管理</a></li>
	                    </ul>
		            </li>
		            <li>
		                <a href="/new-publisher/page-d.action">seo管理</a>
			            <ul>
	                    	<li id="label_paged"><a href="/new-publisher/page-d.action">百科专题</a></li>
	                    	<li id="label_m1check"><a href="/new-publisher/release-audit.action">发布审核</a></li>
	                    </ul>
		            </li>
		            <li id="label_advideo"><a href="/new-publisher/advertise-video.action">首页广告</a></li>
		            <li id="label_album"><a href="/new-publisher/activity-album.action">活动专辑</a></li>
		            <li>
		            	<a href="/new-publisher/activity-album!appPicStart.action">麦米阅读</a>
		            	<ul>
		            		<li id="label_albumApp"><a href="/new-publisher/activity-album!appPicStart.action">开机图片管理</a></li>
		            		<li id="label_recommed"><a href="/new-publisher/creative-recommed.action">作品推荐</a></li>
		            	</ul>
		            </li>
		        </ul>
		    </div>
		</#if>
		
		<#if class=="ios">
        	<div class="subNav">
		        <ul class="inner">
		            <li id="label_appManage"><a href="/new-publisher/app-manage.action">IOS管理</a></li>
		            <li id="label_appNew"><a href="/new-publisher/app-new.action">新建APP</a></li>
		            <li id="label_appMessage"><a href="/new-publisher/app-message.action">通知管理</a></li>
		            <li id="label_appData"><a href="/newpublisherstat/dm-visit-device-pv-uv!editorExecute.action">发行专家数据管理</a></li>
		        </ul>
		    </div>
		</#if>
    </div>
</div>
</#macro>

<#macro label>
	<div class="topBar clearFix">
        <ul class="subNav">
            <li id="label_publisher"><a href="/new-publisher/edit-publisher!to.action">出版商管理</a></li>
            <li id="label_publication"><a href="/new-publisher/edit-magazine!to.action">杂志管理</a></li>
            <li id="label_advertise"><a href="/new-publisher/edit-advertising!to.action">广告管理</a></li>
            <li id="label_event"><a href="/new-publisher/edit-event!to.action">事件管理</a></li>
            <li id="label_adSide"><a href="/new-publisher/edit-sidebar!to.action">侧栏管理</a></li>
            <li id="label_page"><a href="/new-publisher/edit-link!to.action">页面管理</a></li>
            <li id="label_channel"><a href="/new-publisher/channel!to.action">频道管理</a></li>
            <li id="label_advideo"><a href="/new-publisher/advertise-video.action">首页广告</a></li>
            <li id="label_paged"><a href="/new-publisher/page-d.action">百科专题</a></li>
            <li id="label_album"><a href="/new-publisher/activity-album.action">活动专辑</a></li>
            <li id="label_m1check"><a href="/new-publisher/release-audit.action">发布审核</a></li>
        </ul>
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



