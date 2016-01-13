<!DOCTYPE HTML>
<!--[if lt IE 7 ]> <html class="ieOld ie6"> <![endif]-->
<!--[if IE 7 ]>    <html class="ieOld ie7"> <![endif]-->
<!--[if IE 8 ]>    <html class="ieOld ie8"> <![endif]-->
<!--[if (gte IE 9)|!(IE)]><!--> <html> <!--<![endif]-->
<head>
<title>soho<#if type == 'ss'>时尚<#elseif type=='qc'>汽车<#elseif type=='jj'>家居<#elseif type=='ye'>育儿<#elseif type=='ly'>旅游<#else>美食</#if>编辑招聘  - 麦米网Magme</title>
<meta charset="utf-8">
<link href="${systemProp.staticServerUrl}/v3/style/reset.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/pop.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/global.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/channelHtmlpage.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/channelHomev4.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/conBigHeader.css" rel="stylesheet" type="text/css" />

<script src="${systemProp.staticServerUrl}/js/systemProp.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery-1.6.2.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.masonry.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.easing.1.3.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.scrollto.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.mousewheel.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.onImagesLoad.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.imgareaselect.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/datepicker.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.inputfocus.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.jscrollpane.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/scrolltopcontrol.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.tagbox.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/shareToInternet.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.lightbox.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.sampleFancyBox.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.floatDiv.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.coverImg.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/form2object.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/ajaxfileupload.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/useFunction.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/global.js"></script>

<script src="${systemProp.staticServerUrl}/v3/js/sns/userindex.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/sns/activity.js"></script>

<script>
$(function(){
	//item移入
	if(isIE6){
		$(".body>.item").not(".itemFirst,.itemSpace").mouseenter(function(){
			$(this).find(".tools").addClass("toolsOn").find("em").css({display:"inline-block"});
		}).mouseleave(function(){
			$(this).find(".tools").removeClass("toolsOn").find("em").not(".iconHearted").css({display:"none"});
		});
	}
	
	//排列item
	$('#conDetailWall').masonry({itemSelector: '.item'});
	
	$("#conDetailWall .itemFirst .content").jScrollPane()


	//首页左侧高度设置，每次加载更多时需要执行
	setTimeout(fnSetHomeHeight,100);
	
	
});


</script>
<style>
body{ padding-top:80px;}
#conDetailWall .item .info{ padding-top:10px;}
#conDetailWall .item .info h2{ border-top:1px solid #eee; }
#conDetailWall .item .info h6{ display:block;}
#conDetailWall .item .info strong{ display:block;}
#conDetailWall .item .info em{ height:24px; line-height:24px; background:url(${systemProp.staticServerUrl}/v3/images/icon/more24Gray.png) 0 1px no-repeat; padding-left:24px; color:#999; position:absolute; right:8px; top:12px;}
#conDetailWall .sideRight .conList a h3{ width:auto; max-width:160px;}
#conDetailWall .sideRight .conList a span{ padding-left:18px; background:url(${systemProp.staticServerUrl}/v3/images/icon/heart16Gray.gif) 0 1px no-repeat;}
#conDetailWall .conMagezine{ background:url(../../images/bgWood.jpg)}

.sideRight .conUser a p { background:url(${systemProp.staticServerUrl}/v3/images/icon/heart16Gray.gif) 0 center no-repeat ; margin-left:58px; text-indent:20px;}

.footerBig .inner { width:1190px;}
.footerBig .inner .contact { display:block;}
</style>
<!--[if lt IE 7]>
<script src="/js/DD_belatedPNG_0.0.8a-min.js"></script>
<script>
  DD_belatedPNG.fix('.png');
  document.execCommand("BackgroundImageCache",false,true);
</script>
<![endif]-->
</head>
<body>

<!--header-->
<#import "../components/header.ftl" as header>
<@header.main searchType="User"/>
<div class="body bodyHome" style=" width:1190px; padding:0; margin:0 auto 10px auto; box-shadow:none; background:none; border:none;">
	<a href="${systemProp.appServerUrl}/v3/event/20120824/event.html"><img src="${systemProp.staticServerUrl}/v3/event/20120824/images/activity/sns_${type!}.jpg" width="1183" height="300" style="margin-left:6px; background:#ccc; border:none;" /></a>
</div>
<div class="body bodyHome pageTagDetail clearFix" id="conDetailWall" style="min-height:1620px; _height:1620px;">
    <div class="sideRight">
    	 <div class="con" style="position:relative;">
            <img src="${systemProp.staticServerUrl}/v3/event/20120824/images/qqOnline.gif" /><strong style="position:absolute; left:132px; top:16px; font-size:16px; color:#000;">843802595</strong>
        </div>
    	<#if publicationid??>
	    	<#--推荐杂志-->
	        <div class="con conMagezine">
		            <a href="http://${pubDomain!""}.magme.com/${englishName!""}/" target="_blank" class="png">
							<img src="${systemProp.magServerUrl}/${publicationid!}/${issueid!}/320_0.jpg" title="${publicationName!}" />
		            </a>
	            <strong>杂志评委</strong>
	        </div>
	        <#--推荐杂志-->
    	</#if>
    	<#if orderScore?? && (orderScore?size) gt 0>
	    	<div class="con conUser">
	            <a href="javascript:void(0)" class="title">SOHO<#if type == 'ss'>时尚<#elseif type=='qc'>汽车<#elseif type=='jj'>家居<#elseif type=='ye'>育儿<#elseif type=='ly'>旅游<#else>美食</#if>招聘人气排行榜</a>
	      		<#list orderScore as s>
	          		<a href="javascript:void(0)">
		                <img url="<#if ((session_user)??) && (session_user.id==s.userId) >${systemProp.appServerUrl}/sns/${(session_user.id)!'0'}/<#else>${systemProp.appServerUrl}/sns/u${s.userId}/</#if>" src="<#if s.avatar?? && s.avatar!="" >${systemProp.profileServerUrl}${avatarResize(s.avatar,'60')}<#else>${systemProp.staticServerUrl}/images/head60.gif</#if>" onerror="this.src='${systemProp.staticServerUrl}/images/head60.gif'" />
		                <strong>${s.nickname!}</strong>
		                <p><span>${s.num!}</span></p>
		                 <#if s.isF?? && s.isF&gt;0>
		                	<#if s.userId!=9999>
		                		<em class='cancel' nick='${s.nickname!}' u='${s.userId}'>取消关注</em>
		                	</#if>
		               	<#else>
		               		<em class='atten' nick='${s.nickname!}' u='${s.userId}'>加关注</em>
		               	</#if>
	           		</a>
	       		</#list>
	        </div>
        </#if>
        <#if orderScore?? && (orderScore?size) gt 0>
	        <div class="con conList">
	        	<a href="javascript:void(0)" class="title">SOHO<#if type == 'ss'>时尚<#elseif type=='qc'>汽车<#elseif type=='jj'>家居<#elseif type=='ye'>育儿<#elseif type=='ly'>旅游<#else>美食</#if>作品人气排行榜</a>
	            <#list orderEnjoy as e>
		            <a href="${systemProp.appServerUrl}/sns/c${e.userId!}/">
		                <h3>${e.avatar!}</h3>
		                <span>(${e.num})</span>
		            </a>
	            </#list>
	        </div>
         </#if>
         
         <div class="con ">
        	<a href="http://www.magme.com/sns/activity.action?type=ss">
                <img src="${systemProp.staticServerUrl}/v3/event/20120824/images/activity/sns_ss_m.jpg" width="238" />
            </a>
        </div>
        <div class="con ">
        	<a href="http://www.magme.com/sns/activity.action?type=qc">
                <img src="${systemProp.staticServerUrl}/v3/event/20120824/images/activity/sns_qc_m.jpg" width="238" />
            </a>
        </div>
        <div class="con ">
        	<a href="http://www.magme.com/sns/activity.action?type=jj">
                <img src="${systemProp.staticServerUrl}/v3/event/20120824/images/activity/sns_jj_m.jpg" width="238" />
            </a>
        </div>
        <div class="con ">
        	<a href="http://www.magme.com/sns/activity.action?type=ye">
                <img src="${systemProp.staticServerUrl}/v3/event/20120824/images/activity/sns_ye_m.jpg" width="238" />
            </a>
        </div>
        <div class="con ">
        	<a href="http://www.magme.com/sns/activity.action?type=ly">
                <img src="${systemProp.staticServerUrl}/v3/event/20120824/images/activity/sns_ly_m.jpg" width="238" />
            </a>
        </div>
        <div class="con ">
        	<a href="http://www.magme.com/sns/activity.action?type=ms">
                <img src="${systemProp.staticServerUrl}/v3/event/20120824/images/activity/sns_ms_m.jpg" width="238" />
            </a>
        </div>
    </div>

	<#if creativeList?? && (creativeList?size) gt 0>
		 <#list creativeList as c>
		 	<div class="item activity">
				<a href="${systemProp.appServerUrl}/sns/c${c.id}/" target="_blank">
			    	<div class="photo">
			    	<#if c.cType==2>
			    			<img height="${(c.issueid * 220 / c.publicationid)}"  src="${systemProp.staticServerUrl}${avatarResize(c.magazineUrl!,'500')}"   alt="${c.title!}">
			    		<#else>
			    			<img src="${c.magazineUrl!}"   alt="${c.title!}">
			    	</#if>
			    	
			    	
			    	</div>
			    	<div class="info png">
		                <strong>${stringSub(c.nickname!,24)}</strong>
		                <#if c.comefrom?? && c.comefrom &gt; 9><em class="png">${c.comefrom!}</em></#if>
			    	    <h6 class="png" title="${c.nickname!}">
			    	    	<img src="<#if c.avatar?? && c.avatar!="" >${systemProp.profileServerUrl}${avatarResize(c.avatar,'60')}<#else>${systemProp.staticServerUrl}/images/head60.gif</#if>" onerror="this.src='${systemProp.staticServerUrl}/images/head60.gif'" /></h6>
		                <h2>${c.title!}</h2>
			            <p><#if c.content?length gt 150>${c.content?substring(0,150)}……<#else>${c.content}</#if></p>
			        </div>
		        </a>
		        <div class="tools png" cre='${c.id}' u='${c.userId}' ><em title="喜欢" class="<#if c.isEnjoy?? && (c.isEnjoy>0)>iconHearted<#else>iconHeart</#if> png"></em><em class="iconShare png"></em></div>
		    </div>
		 </#list>
	</#if>
</div>

<div id="forwardM1" class="popContent">
	<fieldset class="new">
    	<h6>转发到M1</h6>
        <div>
            <em><textarea maxlength="196" class="input g380" type="text"></textarea></em>
        </div>
        <div>
            <em class="floatr"><a class="btnGB">转发</a></em>
            <em><label><input checked=true class='ck_async'  type="checkbox" />同时评论</label></em>
        </div>
        <div class="count">您还可以输入<span>196</span>字</div>
    </fieldset>
</div>

<input type="hidden" id="ActivityType" value="${type!}" />
<span style="cursor:pointer;" class="clickLoadMore"><span>狂点这里&nbsp;&nbsp;查看更多</span></span>
<#import "../components/footer.ftl" as footer>
<@footer.main class="footerMini footerStatic" />
<style>
body{ padding-top:80px;}
</style>
</body>
</html>