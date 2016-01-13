<!DOCTYPE HTML>
<!--[if lt IE 7 ]> <html class="ieOld ie6"> <![endif]-->
<!--[if IE 7 ]>    <html class="ieOld ie7"> <![endif]-->
<!--[if IE 8 ]>    <html class="ieOld ie8"> <![endif]-->
<!--[if (gte IE 9)|!(IE)]><!--> <html> <!--<![endif]-->
<head>
<title>事件精选&nbsp;${(sort.name)!""}&nbsp;麦米网Magme&nbsp;-&nbsp;世界新杂志&nbsp;发现新内容</title>
<meta name="Keywords" content="麦米网,Magme,电子杂志,在线杂志,免费电子杂志,电子杂志下载,数码杂志,时尚杂志,汽车杂志,财经杂志">
<meta name="Description" content="麦米网提供数百种最新杂志在线免费阅读，财经，旅游，娱乐，时尚，汽车，体育，数码生活等。在麦米看书，交流，分享，交友，新的阅读生活由此开启">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${systemProp.staticServerUrl}/v3/style/reset.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/pop.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/global.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/channelTopic.css" rel="stylesheet" type="text/css" />
<script src="${systemProp.staticServerUrl}/js/systemProp.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery-1.6.2.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.mousewheel.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.masonry.min.2.7.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.easing.1.3.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.onImagesLoad.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.lazyload.mini.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.sampleFancyBox.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.lightbox.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.tagbox.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.inputfocus.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.tmpl.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/scrolltopcontrol.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/shareToInternet.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/useFunction.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/global.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/channelTopic.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.scrollBox.js"></script>

<script src="${systemProp.staticServerUrl}/v3/js/jquery.coverImg.js"></script>
<!--[if lt IE 7]>
<script src="${systemProp.staticServerUrl}/v3/js/DD_belatedPNG_0.0.8a-min.js"></script>
<script>
  DD_belatedPNG.fix('.png');
  document.execCommand("BackgroundImageCache",false,true);
</script>
<![endif]-->
<script>
$(function(){
	$.jquerytagbox("#topTopic",0);
	$.topicBanner("#topBanner",11000)
	$(".conTop .topRight .item img").coverImg();
	<#if channelView??&&(channelView.mode)??&&(channelView.mode==2)>
	$.switchText("#typeText");
	<#else>
	$.topicSlide(".sideLeft .item");
	
		var rollboxnum =0;
	<#if channelSubjectList??>
		rollboxnum = ${channelSubjectList?size};
	</#if>
		for(k=1;k<=rollboxnum;k++){
			$.jqueryscrollbox("#scrollBox"+k,3);
		}	
	</#if>
	
	$("a[eventId]").live('click',function(e){
		e.preventDefault();
		var eventId = $(this).attr("eventId");
		window.open(SystemProp.appServerUrl+"/publish/mag-read.action?eventId="+eventId);
	});
	
	$("a[sortId]").live("click",function(e){
		e.preventDefault();
		var sortId=$(this).attr("sortId")||"";
		window.location.href=SystemProp.appServerUrl+"/channel/"+sortId+".html";
	});
	
});
</script>




</head>
<body>


<#import "./components/header.ftl" as header>
<@header.main searchType="Issue"/>

<!--body-->
<#if channelView??&&(channelView.mode)??&&(channelView.mode==2)>
<div class="body pageTopic clearFix">
	<div class="conTop">
        <!--topBanner-->
        <div class="topBanner" id="topBanner">
        	<#if channelBannerList??>
            	<#list channelBannerList as channelBanner>
            		 <a class="item" href="${(channelBanner.url)!'javascript:void(0)'}">
		                <img src="${SystemProp.newPublisherServerUrl}${(channelBanner.path)!''}" />
		                <div class="info png">
		                    <strong>${(channelBanner.title)!""}</strong>
		                    <p>${(channelBanner.description)!""}</p>
		                </div>
		            </a>
            	</#list>
            	</#if>
        </div>
        <!--topRight-->
        <div class="topRight">
        	<#if channelBannerList2??>
            	<#list channelBannerList2 as channelBanner>
            		<#if (channelBanner_index<2)>
            		<a href="${(channelBanner.url)!'javascript:void(0)'}" class="item"><img src="${SystemProp.newPublisherServerUrl}${(channelBanner.path)!''}" /></a>
		            </#if>
            	</#list>
            	</#if>
        </div>
    </div>

	<div class="sideLeft" id="typeText">
		<#if channelSubjectList??>
		<#list channelSubjectList as channelSubject>
    	<div class="conTypeText">
    		<#if channelEventMap??>
			<#if (channelEventMap.get(channelSubject.id))??>
			<#list (channelEventMap.get(channelSubject.id)) as channelEvent>
			<#assign event=channelEvent.event >
			<div class="item">
            	<h2><a eventId="${event.id}" clickEventId="${event.issueId}_${event.pageNo}" target="_blank" href="${systemProp.appServerUrl}/publish/mag-read.action?eventId=${event.id}">${(event.title)!""}</a></h2>
                <img src="${systemProp.fpageServerUrl}/event${(event.imgFile200)!''}" />
                <p>${(event.description)!""}</p>
                	<div class="tools">
                	<em class="iconHeart" favTypeId="eve_${event.id}" >收藏</em>
					<em class="iconShare" shareTypeId="eve_${event.id}" >分享</em>
					</div>
			</div>                	
        	</#list>
			</#if>
        	</#if>
        </div>
        </#list>
        </#if>
    </div>

	<div class="sideRight">
        <div class="con conCategory">
			<h2>热门话题</h2>
            <div class="conBody">
				<#if channelSubjectList??>
				<#list channelSubjectList as channelSubject>            	
            	<a <#if channelSubject_index==0>class="current"</#if> href="javascript:void(0)">${(channelSubject.name)!""}</a>
            	</#list>
            	</#if>
            </div>
		</div>	
	
        <div class="con conTopTopic jqueryTagBox" id="topTopic">
            <div class="ctrl">
                <div>热门文章</div>
                <div>周排行</div>
                <div>月排行</div>
            </div>
            <div class="doorList">
                <div class="item">
                	<#if dayEventList??>
                	<#list dayEventList as event>
	                	<a target="_blank" href="${systemProp.appServerUrl}/publish/mag-read.action?eventId=${(event.id!'0')}">
	                    	<img src="${systemProp.fpageServerUrl}/event${(event.imgFile68)!''}" />
	                        <strong>${(event.title)!""}</strong>
	                        <p>${(event.description)!""}</p>
	                    </a>
	                </#list>                	
                	</#if>
                </div>
                <div class="item">
                	<#if weekEventList??>
                	<#list weekEventList as event>
	                	<a target="_blank" href="${systemProp.appServerUrl}/publish/mag-read.action?eventId=${(event.id!'0')}">
	                    	<img src="${systemProp.fpageServerUrl}/event${(event.imgFile68)!''}" />
	                        <strong>${(event.title)!""}</strong>
	                        <p>${(event.description)!""}</p>
	                    </a>
	                </#list>                	
                	</#if>
                </div>
                <div class="item">
                	<#if monthEventList??>
                	<#list monthEventList as event>
	                	<a target="_blank" href="${systemProp.appServerUrl}/publish/mag-read.action?eventId=${(event.id!'0')}">
	                    	<img src="${systemProp.fpageServerUrl}/event${(event.imgFile68)!''}" />
	                        <strong>${(event.title)!""}</strong>
	                        <p>${(event.description)!""}</p>
	                    </a>
	                </#list>                    	
                	</#if>
                </div>                                
            </div>
        </div>
        <#if (channelAd.content)??>
		<div class="con conHtml">
			${(channelAd.content)!""}
        </div>
        </#if>
    </div>


<br>
<br>
<br>

</div>

<#else>

<div class="body pageTopic clearFix">
	<div class="conTop">
        <!--topBanner-->
        <div class="topBanner" id="topBanner">
        	<#if channelBannerList??>
            	<#list channelBannerList as channelBanner>
            		 <a class="item" href="${(channelBanner.url)!'javascript:void(0)'}">
		                <img src="${SystemProp.newPublisherServerUrl}${(channelBanner.path)!''}" />
		                <div class="info png">
		                    <strong>${(channelBanner.title)!""}</strong>
		                    <p>${(channelBanner.description)!""}</p>
		                </div>
		            </a>
            	</#list>
            	</#if>
        </div>
        <!--topRight-->
        <div class="topRight">
        	<#if channelBannerList2??>
            	<#list channelBannerList2 as channelBanner2>
            		<#if (channelBanner2_index<2)>
            		<a href="${(channelBanner2.url)!'javascript:void(0)'}" class="item"><img src="${SystemProp.newPublisherServerUrl}${(channelBanner2.path)!''}" /></a>
		            </#if>
            	</#list>
            	</#if>
        </div>
    </div>
       
        <div class="sideLeft">
        <div class="conTypeImage">

		<#if channelSubjectList??>
		<#list channelSubjectList as channelSubject>
    	<div id="scrollBox${channelSubject_index+1}" class="team">
        	<div class="title">${(channelSubject.name)!""}</div>
            <div class="productList">
            <div class="outer">
            	<div class="inner">
            		<#if channelEventMap??>
					<#if (channelEventMap.get(channelSubject.id))??>
					<#list (channelEventMap.get(channelSubject.id)) as channelEvent>
					<#assign event=channelEvent.event >
					<div class="item">
                            <a eventId="${event.id}" clickEventId="${event.issueId}_${event.pageNo}" href="${systemProp.appServerUrl}/publish/mag-read.action?eventId=${event.id}" target="_blank" class="size${event.eventClass}" >              
                                <img src="${systemProp.fpageServerUrl}/event${(event.imgFile)!''}" />
                                <p class="png"><span>${(event.description)!""}</span>
                                <em favTypeId="eve_${event.id}" class="iconHeart">收藏</em><em shareTypeId="eve_${event.id}" class="iconShare">分享</em></p>
                            </a>
                        </div>
                	</#list>
					</#if>
                	</#if>
                </div>
            </div>
            </div>
        </div>
        </#list>
        </#if>
        </div>
    </div>

	<div class="sideRight">
        <div class="con conTopTopic jqueryTagBox" id="topTopic">
            <div class="ctrl">
                <div>热门文章</div>
                <div>周排行</div>
                <div>月排行</div>
            </div>
            <div class="doorList">
                <div class="item">
                	<#if dayEventList??>
                	<#list dayEventList as event>
	                	<a target="_blank" href="${systemProp.appServerUrl}/publish/mag-read.action?eventId=${(event.id!'0')}">
	                    	<img src="${systemProp.fpageServerUrl}/event${(event.imgFile68)!''}" />
	                        <strong>${(event.title)!""}</strong>
	                        <p>${(event.description)!""}</p>
	                    </a>
	                </#list>                	
                	</#if>
                </div>
                <div class="item">
                	<#if weekEventList??>
                	<#list weekEventList as event>
	                	<a target="_blank" href="${systemProp.appServerUrl}/publish/mag-read.action?eventId=${(event.id!'0')}">
	                    	<img src="${systemProp.fpageServerUrl}/event${(event.imgFile68)!''}" />
	                        <strong>${(event.title)!""}</strong>
	                        <p>${(event.description)!""}</p>
	                    </a>
	                </#list>                	
                	</#if>
                </div>
                <div class="item">
                	<#if monthEventList??>
                	<#list monthEventList as event>
	                	<a target="_blank" href="${systemProp.appServerUrl}/publish/mag-read.action?eventId=${(event.id!'0')}">
	                    	<img src="${systemProp.fpageServerUrl}/event${(event.imgFile68)!''}" />
	                        <strong>${(event.title)!""}</strong>
	                        <p>${(event.description)!""}</p>
	                    </a>
	                </#list>                    	
                	</#if>
                </div>                                
            </div>
        </div>
        <#if (channelAd.content)?? && (channelAd.content != "") >
		<div class="con conHtml">
			${(channelAd.content)!""}
        </div>
        </#if>
    </div>


<br>
<br>
<br>

</div>
</#if>

<#import "./components/footer.ftl" as footer>
<@footer.main class="footerMini footerStatic" />

</body>
</html>