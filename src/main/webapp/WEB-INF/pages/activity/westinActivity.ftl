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
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/jquery.onImagesLoad.min.js"></script>
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
	$.topicBanner("#topBanner",8000);
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
	
	$("#submitEmail").click(function(e){
	    e.preventDefault();
	    var content=$("#submitContent").val().trim();
	    if(content==undefined || content==null || content==''){
	       alert("没有填入信息");
	       return false;
	    }
	    var callback=function(rs){
	        if(!rs || rs.code!=200){
	           alert("保存不成功");
	        }else{
	           alert("保存成功");
	        }
	    };
	    $.ajax({
	         url:"http://www.magme.com/activity/activity-collection-info.action",
	         data:{"content":content},
	         type:"POST",
	         success:callback
	    });
	});
	
});
</script>




</head>
<body>


<#import "../components/header.ftl" as header>
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
            	<h2><a eventId="${event.id}" clickEventId="${event.issueId}_${event.pageNo}" href="javascript:void(0)">${(event.title)!""}</a></h2>
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
                            <a eventId="${event.id}" clickEventId="${event.issueId}_${event.pageNo}" href="javascript:void(0)" class="size${event.eventClass}" >              
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
        
        <div class="con conSponsored">
            <h2>上海国际电影节--威斯汀晚宴赞助合作商</h2>
            <div class="conBody clearFix">
              <a href="javascript:void(0)"  title="上海思妍丽实业股份有限公司"><img src="${systemProp.staticServerUrl}/v3/event/20120515/images/sponsored2.gif" /></a>
              <a href="javascript:void(0)"  title="康延（上海）贸易有限公司"><img src="${systemProp.staticServerUrl}/v3/event/20120515/images/sponsored1.gif" /></a>
            </div>
        </div>
        
        <div class="con conMedia">
             <h2>支持媒体</h2>
            <div class="conBody clearFix">
              <a href="javascript:void(0)" target="_blank"><img src="${systemProp.staticServerUrl}/v3/event/20120515/images/media (12).jpg" /></a>
              <a href="javascript:void(0)" target="_blank"><img src="${systemProp.staticServerUrl}/v3/event/20120515/images/media (11).jpg" /></a>
              <a href="javascript:void(0)" target="_blank"><img src="${systemProp.staticServerUrl}/pdfprofile/422/172_2966.jpg" /></a>
              <a href="javascript:void(0)" target="_blank"><img src="${systemProp.staticServerUrl}/pdfprofile/365/172_3331.jpg" /></a>
              <a href="javascript:void(0)" target="_blank"><img src="${systemProp.staticServerUrl}/v3/event/20120515/images/media (1).jpg" /></a>
              <a href="javascript:void(0)" target="_blank"><img src="${systemProp.staticServerUrl}/v3/event/20120515/images/media (2).jpg" /></a>
              <a href="javascript:void(0)" target="_blank"><img src="${systemProp.staticServerUrl}/v3/event/20120515/images/media (3).jpg" /></a>
              <a href="javascript:void(0)" target="_blank"><img src="${systemProp.staticServerUrl}/v3/event/20120515/images/media (4).jpg" /></a>
              <a href="javascript:void(0)" target="_blank"><img src="${systemProp.staticServerUrl}/v3/event/20120515/images/media (5).jpg" /></a>
              <a href="javascript:void(0)" target="_blank"><img src="${systemProp.staticServerUrl}/v3/event/20120515/images/media (7).jpg" /></a>
              <a href="javascript:void(0)" target="_blank"><img src="${systemProp.staticServerUrl}/v3/event/20120515/images/media (9).jpg" /></a>
              <a href="javascript:void(0)" target="_blank"><img src="${systemProp.staticServerUrl}/v3/event/20120515/images/media (10).jpg" /></a>
            </div>
        </div>
        
        
    </div>

<div class="sideRight">
        <div class="con conEmail">
			<h2>信息订阅</h2>
            <div class="conBody clearFix">
            	<p>订制更多上海国际电影节资讯！</p>
            	<input type="text" id="submitContent" class="input g140" /><a id="submitEmail" class="btnGS" href="javascript:void(0)">提交</a>
            </div>
		</div>
		<div class="con conBanner">
            <a href="http://www.westinshanghai.com/weekend003" target="_blank" title="在线选择客房&nbsp;&nbsp;周末8折优惠"><img src="${systemProp.staticServerUrl}/v3/event/20120515/images/vestin.jpg" /></a>
		</div>
        <a href="http://weibo.com/magme" target="_blank" class="png"><img src="${systemProp.staticServerUrl}/v3/event/20120515/images/banner3.png" /></a>
</div>


<!--2012siff相关代码-->
<style>
html{background:url(${systemProp.staticServerUrl}/v3/images/bgHtml20120331.gif);}
body{background:url(${systemProp.staticServerUrl}/v3/event/20120515/images/bg.jpg) center bottom no-repeat; min-width:1440px;}
html body .body{ position:relative; }
.sideRight .conEmail h2{ background:#243545; color:#fff;}
.sideRight .conEmail .conBody{ padding:10px;}
.sideRight .conEmail .conBody p{ margin-bottom:10px;}
.sideRight .conEmail .conBody input{ margin-right:6px;}
.sideLeft .conMedia .conBody{ padding:15px 0 5px 13px; width:670px; overflow:hidden;}
.sideLeft .conMedia .conBody a{ width:99px; height:132px; display:block; float:left; margin:0 10px 10px 0; cursor:default;}
.sideLeft .conMedia .conBody a img{ width:99px; height:132px; display:block;box-shadow:1px 1px 5px rgba(0,0,0,0.2); }
.sideLeft .conSponsored .conBody{ padding:15px 0 5px 13px; width:670px; overflow:hidden; }
.sideLeft .conSponsored .conBody a{ display:inline-block; margin:0 30px 10px 0; cursor:default;}
.sideLeft .conSponsored .conBody a img{ box-shadow:1px 1px 5px rgba(0,0,0,0.2); }
.header .nav .navInner .navRight .classification li.current{ background:url(${systemProp.staticServerUrl}/v3/event/20120515/images/menu.png) 14px center no-repeat;}
.header .nav .navInner .navRight .classification li.current a{ padding-left:52px;}

.ieOld .sideLeft .conMedia,
.ieOld .sideLeft .conSponsored{ border:1px solid #e5e5e5;}


</style>
<!--2012siff相关代码-->
</div>
</#if>

<#import "../components/footer.ftl" as footer>
<@footer.main class="footerMini footerStatic" />

</body>
</html>