<#import "../components/header.ftl" as header>
<#import "../components/footer.ftl" as footer>
<!DOCTYPE HTML>
<!--[if lt IE 7 ]> <html class="ieOld ie6"> <![endif]-->
<!--[if IE 7 ]>    <html class="ieOld ie7"> <![endif]-->
<!--[if IE 8 ]>    <html class="ieOld ie8"> <![endif]-->
<!--[if (gte IE 9)|!(IE)]><!--> <html> <!--<![endif]-->
<head>
<title>${pageD.title!''}</title>
<meta name="Keywords" content="${pageD.keyWord!''}">
<meta name="Description" content="${pageD.description!''}">
<meta charset="utf-8">
<link href="${systemProp.staticServerUrl}/v3/style/reset.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/global.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/channelHtmlpage.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/channelHomev4.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/pop.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/conBigHeader.css" rel="stylesheet" type="text/css" />
<script src="${systemProp.staticServerUrl}/v3/js/jquery-1.6.2.min.js"></script>
<script src="${systemProp.staticServerUrl}/js/systemProp.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.masonry.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.easing.1.3.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.lazyload.mini.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.mousewheel.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.jscrollpane.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.inputfocus.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/scrolltopcontrol.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/shareToInternet.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.sampleFancyBox.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.tagbox.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.coverImg.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.dragSort.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/useFunction.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/global.js"></script>
<script type="text/javascript">
	$(function(){
		//item移入
		if(isIE6){
			$(".body>.item").not(".itemFirst,.itemSpace").mouseenter(function(){
				$(this).find(".tools").addClass("toolsOn").find("em").css({display:"inline-block"});
			}).mouseleave(function(){
				$(this).find(".tools").removeClass("toolsOn").find("em").not(".favCurrent").css({display:"none"});
			});
		}
		$("#conDetailWall .itemFirst .pic img").coverImg();
		
		//排列item
		$('#conDetailWall').masonry({itemSelector: '.item'});
		
		$("#conDetailWall .itemFirst .content").jScrollPane();
		
	
	
		//首页左侧高度设置，每次加载更多时需要执行
		fnSetHomeHeight();
		function fnSetHomeHeight(){
			var rightH = $(".sideRight").height();
			if($.browser.safari){
				rightH+=450;
			}
			if($("#conDetailWall").height()<rightH){
				$("#conDetailWall").height(rightH);
			}
		}
		
	});
</script>

<style>
	#conDetailWall .item .info strong {
		display:block;    
		border-top: 1px solid #EEEEEE;
	    color: #000000;
	    font-size: 16px;    
	    font-weight: bold;
	    line-height: 1.6em;
	    padding-top: 5px;
	    word-break: break-all;
	    word-wrap: break-word;
	    margin:3px 0 0; 
	}
	.sideRight .conList a h2 {
	    color: #444444;
	    display: block;
	    float: left;
	    height: 24px;
	    overflow: hidden;
	    transition: all 0.3s ease 0s;
	    width: 220px;
	}
</style>
<!--[if lt IE 7]>
<script src="${systemProp.staticServerUrl}/v3/js/DD_belatedPNG_0.0.8a-min.js"></script>
<script>
  DD_belatedPNG.fix('.png');
  document.execCommand("BackgroundImageCache",false,true);
</script>
<![endif]-->


</head>
<body>

<#--header-->
<@header.main searchType="Issue"/>

<#--body-->
<div class="body bodyHome pageTagDetail clearFix" id="conDetailWall">
    <div class="sideRight">
        <div class="con tagList conTagList">
            <ul class="clearFix">
            	<#if pageDList?? && pageDList?size gt 0>
            		<#list pageDList as pageD>
	                	<li><a href="${systemProp.appServerUrl}/detail${pageD.id}/">${pageD.name}</a></li>
	                </#list>
                </#if>
            </ul>
        </div>
    	<div class="con conList">
            <a href="javascript:void(0)" class="title">其它与<span>${pageD.name}</span>相关的作品</a>
        	<#if creativeList?? && creativeList?size gt 0>
        		<#list creativeList as creative>
		            <a href="${systemProp.appServerUrl}/sns/c${creative.id}/" target="_blank">
		                <h2 title="${creative.title}"><#if creative.title?length gt 18>${creative.title?substring(0,17)}…<#else>${creative.title}</#if></h2>
		            </a>
                </#list>
            </#if>
        </div>
    	<div class="con conText">
            <p>${pageD.indexDesc!''}</p>
        </div>
        
        <div class="con conList">
            <a href="javascript:void(0)" class="title"><h2>精彩推荐</h2></a>
        	<#if publicCreative?? && publicCreative?size gt 0>
        		<#list publicCreative as c>
		            <a href="${systemProp.appServerUrl}/sns/c${c.cid}/" target="_blank">
		                <h3 title="${c.title!}"><#if c.title?length gt 18>${c.title?substring(0,17)}…<#else>${c.title!}</#if></h3>
		            </a>
                </#list>
            </#if>
        </div>
    
    </div>

	<div class="itemFirst">
		<div class="pic">
			<img src="<#if pageD.picUrl??>${systemProp.pageDServerUrl}${pageD.picUrl}</#if>" height="${pageD.height}" width="${pageD.width}"/>
		</div>
        <div class="content">${pageD.headerDesc!''}</div>
    </div>
	<div class="item itemSpace"></div>
	<div class="item itemSpace"></div>
	<div class="item itemSpace"></div>
	<div class="item itemSpace"></div>
    
    <#if homePageItemList?? && (homePageItemList?size) gt 0>
    <#list homePageItemList as item>
		<div class="item" itemType="${item.type}">
		    	<div class="photo">
		    	<a eventId="${item.itemId}" clickEventId="" 
				<#if item.type=='event'>
					href="${systemProp.appServerUrl}/index-detail.action?itemId=${item.itemId}&type=${item.type}"
				<#else> href="${systemProp.appServerUrl}/sns/c${item.itemId}/" </#if>
				target="_blank">
		    		<img height="${(((item.itemHeight!1) * 210) / item.itemWidth!1)}" 
		    			src="<#if item.type=='event'>${systemProp.fpageServerUrl}/event${item.itemImagepath}<#else>${systemProp.staticServerUrl}${avatarResize(item.itemImagepath,'max_800')}</#if>"
						alt="${item.itemTitle!''}" />
			    </a>
		    	</div>
		    	<div class="info png">
		    		<#--
	                <strong><#if item.ownerName?length gt 12>${item.ownerName?substring(0,11)}…<#else>${item.ownerName}</#if></strong>
		    	    <h6 class="png" title="${item.ownerName}">
		    	    	<#if item.ownerAvatar??>
			    	    	<img src="<#if item.type=='event'>${systemProp.publishProfileServerUrl}<#else>${systemProp.profileServerUrl}</#if>${item.ownerAvatar}" />
		    	    	<#else>
			    	    	<img src="${systemProp.staticServerUrl}/v3/images/head46.gif" />
		    	    	</#if>
		    	    </h6>
		    	    -->
			<a eventId="${item.itemId}" clickEventId="" 
				<#if item.type=='event'>
					href="${systemProp.appServerUrl}/index-detail.action?itemId=${item.itemId}&type=${item.type}"
				<#else> href="${systemProp.appServerUrl}/sns/c${item.itemId}/" </#if>
				target="_blank">
	                <strong>${item.itemTitle!''}</strong>
	        </a>
		            <p><#if item.itemContent?length gt 150>${item.itemContent?substring(0,150)}……<#else>${item.itemContent}</#if></p>
		        </div>
            <div class="tools png">
            	<em title="喜欢" class="iconHeart png" favTypeId="<#if item.type=='event'>eve<#else>cre</#if>_${item.itemId}"></em>
            	<em class="iconShare" shareTypeId="eve_${item.itemId}_${item.type}"></em>
            	<#--<em class="iconDetail png" detailId="${item.itemId}"></em>-->
            </div>
	    </div>
    </#list>
    </#if>
    
</div>

<style>
body{ padding-top:80px;}
</style>

<#--footer-->
<@footer.main class="footerMini footerStatic"/>
<!--
<script type= "text/javascript">
/*D类页第一个120*270，创建于2012-11-30*/
var cpro_id = "u1144984";
</script>
<script src= "http://cpro.baidustatic.com/cpro/ui/f.js" type="text/javascript" ></script>-->

<!-- 广告位：投放马爹利酒
<script type="text/javascript" >BAIDU_CLB_SLOT_ID = "532059";</script>
<script type="text/javascript" src="http://cbjs.baidu.com/js/o.js"></script> -->

<script type="text/javascript">
/*120*270，创建于2013-3-14*/
var cpro_id = "u1234137";
</script>
<!-- 
<script src="http://cpro.baidustatic.com/cpro/ui/f.js" type="text/javascript"></script>
<script src="http://uimg.youxjia.com/jsorigin/39936.js" language="JavaScript"></script> -->


</body>
</html>