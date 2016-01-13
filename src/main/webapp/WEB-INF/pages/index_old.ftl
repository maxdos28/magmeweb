<!DOCTYPE HTML>
<!--[if lt IE 7 ]> <html class="ie6 ieOld"> <![endif]-->
<!--[if IE 7 ]>    <html class="ie7 ieOld"> <![endif]-->
<!--[if IE 8 ]>    <html class="ie8 ieOld"> <![endif]-->
<!--[if (gte IE 9)|!(IE)]><!--> <html> <!--<![endif]-->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<#if seoCreativeCategoryIndex??>
<title>${seoCreativeCategoryIndex.seoTitle!''}</title>
<meta name="Keywords" content="${seoCreativeCategoryIndex.seoKeyWord!''}"/>
<meta name="Description" content="${seoCreativeCategoryIndex.seoDescription!''}"/>
<#else>
<title>麦米网Magme电子杂志 - 无处不悦读</title>
<meta name="Keywords" content="杂志,电子杂志,杂志在线阅读,电子杂志下载,杂志网"/>
<meta name="Description" content="麦米网(www.magme.com)全球领先的电子杂志在线阅读平台.提供财经,旅游,娱乐,时尚,汽车,体育,数码生活等15类超过400本杂志的在线免费阅读与下载. 在线看杂志优质内容,上麦米网!"/>
</#if>
<link href="${systemProp.staticServerUrl}/v3/style/reset.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/pop.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/global.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/channelHomev4.css" rel="stylesheet" type="text/css"/>
<script src="${systemProp.staticServerUrl}/js/systemProp.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery-new.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.masonry.min.2.7.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.easing.1.3.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.onImagesLoad.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.lazyload.mini.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.sampleFancyBox.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.lightbox.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.tagbox.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.inputfocus.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.coverImg.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.tmpl.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/scrolltopcontrol.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/shareToInternet.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/header.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/useFunction.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/global.js"></script>
<script type="text/javascript">
	var curSortId = ${sortId!0};
	var curTagId = ${secondSortId!0};
	var video= "00";
	var curTagName = "";
</script>
<script src="${systemProp.staticServerUrl}/v3/js/home.js"></script>

<!--[if lt IE 7]>
<script src="${systemProp.staticServerUrl}/v3/js/DD_belatedPNG_0.0.8a-min.js"></script>
<script>
  DD_belatedPNG.fix('.png');
  document.execCommand("BackgroundImageCache",false,true);
</script>
<![endif]-->
</head>
<body <#if sortId??>class="subNavOn"</#if>>


<#import "./components/header.ftl" as header>

<@header.main searchType="Creative"/>
<#import "./dialog/videoAdvertise.ftl" as video>
<@video.main />
<div class="header20120905 clearFix">
 <div class="outer headerNav">
    	<div class="inner clearFix">
            <div class="logo" id="logo"><h1><a class="png" href="http://www.magme.com/" alt="麦米网电子杂志" title="无处不悦读">麦米网Magme</a></h1></div>
            <div class="subNav">
            	<ul class="classification clearFix">
            	<#if creativeCategoryList?? && creativeCategoryList?size gt 0 >
            		<#list creativeCategoryList as cc>
            			 <li <#if sortId?? && sortId == cc.id> class="current"</#if> ><a <#if cc.id==6 || cc.id==7>class="new" </#if>  href="http://www.magme.com/home/${cc.id}.html" >${cc.name}</a>
                    	</li>
            		</#list>
            	</#if>
            	<#--<li><a class="hot" href="http://www.magme.com/v3/event/20130506/index.html" target="_blank" >见行见远</a></li>-->
                </ul>
            </div>
        </div>
    </div>
    
    	<#if creativeCategoryList?? && creativeCategoryList?size gt 0 >
            		<#list creativeCategoryList as cc>
            			<#if sortId?? && sortId gt 0 && sortId==cc.id>
            			<div class="outer headerSubNav navC${cc.id}">
					        <div class="inner">
					        	<strong class="png"><span class="png">麦米${cc.name}</span></strong>
					            <div class="list">
					                <a href="http://www.magme.com/home/${cc.id}.html" <#if secondSortId??><#else> class="current png"</#if> >全部</a>
					                <#if cc.childCreativeList?? && cc.childCreativeList?size gt 0 >
            			 				<#list cc.childCreativeList as ccl>
							                <a <#if secondSortId?? && secondSortId == ccl.id> class="current png"</#if> href="http://www.magme.com/home/${cc.id}/${ccl.id}.html" >${ccl.name}</a>
							            </#list>
            			 			</#if>
					            </div>
					        </div>
				    	</div>
				    	</#if>
            		</#list>
          </#if>
    
</div>

<div class="body bodyHome clearFix" id="homeWall" style="position:relative;">
	<div class="itemFirst">
		<iframe src="/baidu_clb.html#${sortId!0}_1" width="100%" height="100%" frameborder="0" scrolling="no" allowtransparency ></iframe>
		<#-- 广告位：homepage big banner-->
	</div>
	<div class="item itemSpace patch"></div>
	<div class="item itemSpace patch"></div>
    
    <#if creativeList?? && (creativeList?size) gt 0>
    <#list creativeList as c>
    	<div class="item <#if c.imagePath?? && c.imagePath?length gt 0 ><#else>itemNopic</#if>">
		<a href="http://www.magme.com/sns/c${c.id}/" target="_blank">
	    	<div class="photo"><#if c.imagePath?? && c.imagePath?length gt 0 ><img height="${(((c.high!1) * 210) / c.width!1)}"  src="${systemProp.staticServerUrl}${avatarResize(c.imagePath!'','max_400')}" alt="${c.secondTitle!''}"></#if></div>
	    	<div class="info png">
	    		<div class="class">
	    		<#if c.typeNameFirst??>
	    			<#list (c.typeNameFirst)?split(',') as s>
	    				<#if s_index < 3>
		    				<#if s=='丽人' >   
		    					<strong class="navC1">${s}</strong>
		    				<#elseif s=='绅士'>
		    					<strong class="navC2">${s}</strong>
		    				<#elseif s=='玩味'>
		    					<strong class="navC3">${s}</strong>
	    					<#elseif s=='座驾'>
	    						<strong class="navC4">${s}</strong>
	    					<#elseif s=='财界'>
	    						<strong class="navC5">${s}</strong>
	    					<#elseif s=='情商'>
	    						<strong class="navC6">${s}</strong>	
	    					<#elseif s=='家居'>
	    						<strong class="navC7">${s}</strong>	
	    					<#else>
	    						<strong class="navC${c.typeNameFirstLong}">${s}</strong>
		    				</#if>
	    				</#if>
	    			</#list>
	    		</#if>
	    		</div>
                <h2>${c.secondTitle!''}</h2>
	            <p>${c.secondDesc!''}</p>
	        </div>
        </a>
        <div class="tools png"><em title="喜欢" class="iconHeart png" favTypeId="cre_${c.id}"></em>
        <em class="iconShare png" shareTypeId="eve_${c.id}_creative"></em></div>
    </div>
    </#list>
    </#if>


    <div class="sideRight">
	    <div class="con conAd">
		    <#-- 广告位  baidu -->
			<iframe src="/baidu_clb.html#${sortId!0}_2" width="100%" height="100%" frameborder="0" scrolling="no" allowtransparency ></iframe>
        </div>
        <#--视频广告-->
        <#if adVideo?? && (adVideo?size>0)>
        <div class="con conVideoAd" id="videoAd">
        	<#list adVideo as vList>
        	<a href="javascript:void(0);">
                <img id="ADvideoPlayIndexImg" url="<#if vList.mediaUrl??>${vList.mediaUrl}</#if>"  src="<#if vList.imgUrl??>${vList.imgUrl}</#if>" />
                <span id="ADvideoPlayIndex" url="<#if vList.mediaUrl??>${vList.mediaUrl}</#if>" class="png"></span>
            </a>
            </#list>
        </div>
        </#if>
        
        <#--推荐阅读  baidu-->
        <div class="con conMagezine">
        	<div class="book">
	        	<#-- 广告位  baidu -->
				<iframe src="/baidu_clb.html#${sortId!0}_3"  width="100%" height="100%" frameborder="0" scrolling="no" allowtransparency ></iframe>
        	</div>
            <strong>热门电子杂志推荐阅读</strong>
        </div>
        <#--换一批内容
        <div id="cre_list" class="con conList">
    		<#list publicCreative as pcrv>
	            <a class="pcrv_list <#if pcrv_index==4>last</#if>" href="${systemProp.appServerUrl}/sns/c${pcrv.cid}/" <#if pcrv_index&gt;4>style='display:none;'</#if>>
	                <strong>${pcrv.title!}</strong>
	                <span>(${pcrv.num!})</span>
	            </a>
            </#list>
            <a href="javascript:void(0)" class="more">热点专题</a>
        </div>-->
        <#--  热点专题 -->
         <#if recommendHot?? && (recommendHot?size>0)>
	        <div class="con conListBig">
	            <a href="javascript:void(0)" class="title">热点专题</a>
	            <#list recommendHot as hot>
		      		<a href="${systemProp.appServerUrl}/detail${hot.id}/" <#if hot_index==0>class="topBig"</#if> target="_blank">
		                <div class="pic"><img  width="${hot.width!}" height="${hot.height!}"  src="${systemProp.pageDServerUrl}${hot.picUrl!}" /></div>
		                <h3>${hot.shortTitle!}</h3>
		                <p>${hot.description!}</p>
		                <em>阅读</em>
		            </a>
	            </#list>
	        </div>
		</#if>   
	   <#--编辑推荐排行榜-->
	 <#if rankingByEditList?? && (rankingByEditList?size>0)>
        <div class="con conList">
            <a href="javascript:void(0)" class="title"><#if sortId??&&(sortId>=1)><p class="navC${sortId}" >
       <#if sortId==1>丽人
       <#elseif sortId==2>绅士
       <#elseif sortId==3>玩味
       <#elseif sortId==4>座驾
       <#elseif sortId==5>财界
        <#elseif sortId==6>情商
         <#elseif sortId==7>家居
       </#if>&nbsp;&nbsp;编辑推荐</p><#else>编辑推荐</#if></a>
            <#list rankingByEditList as ranking>
            	<#if ranking_index lte 2 >
            	 <a  href="http://www.magme.com/sns/c${ranking.creativeId}/" target="_blank">
	                <h3><span class="a${ranking_index+1}">${ranking_index+1}</span>
	                	 <#if ranking.creativeTitle?? && (ranking.creativeTitle?length <=14) >
	                		${ranking.creativeTitle}
		                <#else>
		                	${ranking.creativeTitle?substring(0,14)}...
		                </#if>
	                </h3>
	             </a>
            	<#else>
            	<a  href="http://www.magme.com/sns/c${ranking.creativeId}/" target="_blank">
	                <h3><span>${ranking_index+1}</span>
	                 <#if ranking.creativeTitle?? && (ranking.creativeTitle?length <=14) >
	                	${ranking.creativeTitle}
	                <#else>
	                	${ranking.creativeTitle?substring(0,14)}...
	                </#if>
	                </h3>
	            </a>
            	</#if>
            </#list>
        </div>
        </#if>
	<#--本周阅读榜排行榜-->
	 <#if rankingList?? && (rankingList?size>0)>
        <div class="con conList">
            <a href="javascript:void(0)" class="title"><#if sortId??&&(sortId>=1)><p class="navC${sortId}" >
        <#if sortId==1>丽人
       <#elseif sortId==2>绅士
       <#elseif sortId==3>玩味
       <#elseif sortId==4>座驾
       <#elseif sortId==5>财界
       <#elseif sortId==6>情商
         <#elseif sortId==7>家居
       </#if>&nbsp;&nbsp;最热阅读榜</p><#else>最热阅读榜</#if></a>
            <#list rankingList as ranking>
            	<#if ranking_index lte 2 >
            	 <a  href="http://www.magme.com/sns/c${ranking.creativeId}/" target="_blank">
	                <h3><span class="a${ranking_index+1}">${ranking_index+1}</span>
	                <#if ranking.creativeTitle?? && (ranking.creativeTitle?length <=14) >
	                	${ranking.creativeTitle}
	                <#else>
	                	${ranking.creativeTitle?substring(0,14)}...
	                </#if>
	                </h3>
	             </a>
            	<#else>
            	<a  href="http://www.magme.com/sns/c${ranking.creativeId}/" target="_blank">
	                <h3><span>${ranking_index+1}</span>
	                 <#if ranking.creativeTitle?? && (ranking.creativeTitle?length <=14) >
	                	${ranking.creativeTitle}
	                <#else>
	                	${ranking.creativeTitle?substring(0,14)}...
	                </#if>
	                </h3>
	            </a>
            	</#if>
            </#list>
        </div>
        </#if>
		     
        <#--最新期刊-->
        <div class="con conMagezine" id="latestMgz">
        	<#if issueNewList??>
        	<#list issueNewList as issueNew>
	            <a href="${systemProp.appServerUrl}/publish/mag-read.action?id=${issueNew.id}" target="_blank" class="png">
						<img src="${systemProp.magServerUrl}/${issueNew.publicationId}/200_${issueNew.id}.jpg" title="${issueNew.publicationName}" />
	            </a>
            </#list>
            </#if>
            <strong>最新上架杂志</strong>
        </div>
        <#--最新评论
        <div class="con conReply">
        	<#if hotMap??>
        	<#list hotMap?keys as hotKey>
        		<a href="${systemProp.appServerUrl}/index-detail.action?itemId=${(hotMap.get(hotKey)).id}&type=event" target="_blank">
	                <div class="clearFix">
	                    <div class="gl"><#if ((hotMap.get(hotKey)).publicationLogo)??>
	                    	<img class="head" src="${systemProp.publishProfileServerUrl}${(hotMap.get(hotKey)).publicationLogo}" />
	                    <#else>
	                    	<img class="head" src="${systemProp.staticServerUrl}/v3/images/head46.gif" /></#if><p><span>${(hotMap.get(hotKey)).title}</span></p><em></em></div>
	                    <#assign comlist=(hotMap.get(hotKey)).commentLst >
	                    <#if comlist??>
	                    <#list comlist as clist >
	                    	<div class="br"><img class="head" src="<#if clist.user.avatar?? && clist.user.avatar!=''>${systemProp.profileServerUrl}${clist.user.avatar}<#else>${systemProp.staticServerUrl}/v3/images/head46.gif</#if>" /><p><span><#if clist.content??>${clist.content}</#if></span></p><em></em></div>
	                    </#list>
	                    </#if>
	                </div>
	            </a>
        	</#list>
        	</#if>
            <a href="${systemProp.appServerUrl}/sns/sns-index.action" class="more">更多评论</a>
        </div>
        -->
        <div class="con conReply" id="conReply">
          <#if sidebarEvent??>
            <a href="javascript:void(0)" class="title">热门评论</a>
	        	<#list sidebarEvent as sidebar>
	        		<a title='${sidebar.itemTitle!}'  href="${systemProp.appServerUrl}/index-detail.action?itemId=${sidebar.itemId}&type=event<#if sortId??>&sortId=${sortId}</#if><#if tagName??>&tagName=${encode(encode(tagName))}</#if>" target="_blank" <#if  sidebar_index==0 && sidebar.commentCount&gt;0 >class="current"</#if> >
		            	<div class="topic">
		                	<div class="photo">
			                	<#if sidebar.ownerAvatar?? >
					    	    	<img height="${(sidebar.itemHeight * 50 / sidebar.itemWidth)}"  src="${systemProp.publishProfileServerUrl}${sidebar.ownerAvatar!}" />
				    	    	<#else>
					    	    	<img src="${systemProp.staticServerUrl}/v3/images/head46.gif" />
				    	    	</#if>
			    	    	</div>
		                    <strong>${stringSub(sidebar.itemTitle!,16)}</strong>
		                    <p><#if sidebar.itemContent?length &gt; 20>${sidebar.itemContent?substring(0,20)}……<#else>${sidebar.itemContent!}</#if></p>
		                    <span>${sidebar.commentCount}</span>
		                </div>
		                <#if sidebar.comment?? && sidebar.comment.size()&gt;0>
			                <div class="clearFix">
			                	<#list sidebar.comment as c>
			                    	<div <#if (c_index)%2==0>class="bl"<#else>class="br"</#if> ><img class="head" src="<#if c.user.avatar?? && c.user.avatar!="" >${systemProp.profileServerUrl}${avatarResize(c.user.avatar,'60')}<#else>${systemProp.staticServerUrl}/images/head60.gif</#if>" onerror="this.src='${systemProp.staticServerUrl}/images/head60.gif'" /><p><span>${c.content!}</span></p><em></em></div>
			                    </#list>
			                    <div class="more">参加讨论</div>
			                </div>
		                </#if>
		            </a>
	        	</#list>
        	</#if>
        	<script>
            	$(function(){
					$("#conReply>a").not(".title").mouseenter(function(){
						if($(this).find(".clearFix").html()!=null)
							$(this).addClass("current").siblings().removeClass("current");
					});
				
				});
            </script>
        </div>
        
         <#--专题管理-->
        <#if activityAlbumList?? && (activityAlbumList?size>0)>
        	<div class="con conTopic">
        	<h2>活动专辑</h2>
	    		<div class="con">
	    	<#list activityAlbumList as bIndex >
	    		<#if bIndex.type??&&(bIndex.type==sortId!0)>
	    			<a href="${bIndex.url!''}" target="_blank" >
	            	<img alt="${bIndex.alt!''}" width="238px" height="140px" src="${systemProp.activityAlbumServerUrl}${bIndex.image}" />
	           	 	</a>
	    		</#if>
        	</#list>
        		</div>
        	</div>
	   		 </#if>
	 <script>
	 	if($(".conTopic a").length<1){
					$(".conTopic").hide();
				}
	 </script>
        
	</div>
    
</div>

<span id="loadMore" style="cursor:pointer;" class="clickLoadMore"><span>狂点这里&nbsp;&nbsp;查看更多</span></span>

<#import "./components/footer.ftl" as footer>
<@footer.main class="index" />
<script>
$(function(){
var feedBackVal = getUrlValue2("feedBack");
if(feedBackVal){
	if(feedBackVal=='1'){
		$("#userFeedBack").click();
	}
}
});
</script>
</body>
</html>
