<!DOCTYPE HTML>
<!--[if lt IE 7 ]> <html class="ieOld ie6"> <![endif]-->
<!--[if IE 7 ]>    <html class="ieOld ie7"> <![endif]-->
<!--[if IE 8 ]>    <html class="ieOld ie8"> <![endif]-->
<!--[if (gte IE 9)|!(IE)]><!--> <html> <!--<![endif]-->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<#if seoCreativeCategoryIndex??>
<title>${seoCreativeCategoryIndex.seoTitle!''}</title>
<meta name="Keywords" content="${seoCreativeCategoryIndex.seoKeyWord!''}"/>
<meta name="Description" content="${seoCreativeCategoryIndex.seoDescription!''}"/>
<#else>
<title>麦米购–轻松全球购</title>
<meta name="Keywords" content="麦米购，轻松全球购，跨境直购，海外直邮，进口商品，母婴，美妆，护肤，保养，进口奶粉"/>
<meta name="Description" content="麦米购是时尚产品类的电子营销商，兼具在线互动式时尚消费类资讯阅读。拥有全球贸易公司的雄厚资历，承诺并保证全部的海外商品和进口商品为正品。服务覆盖生活全方位，是你身边贴心专业的跨境直购专家，为消费者提供低税率价格的时尚精品和生活用品。"/>
</#if>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta charset="utf-8">
<link href="${systemProp.staticServerUrl}/v3/style/reset.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/global.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/channelHomev7.css" rel="stylesheet" type="text/css"/>
<script src="${systemProp.staticServerUrl}/v3/js/jquery-new.min.js"></script>
<script src="${systemProp.staticServerUrl}/js/systemProp.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.masonry.min.2.7.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.easing.1.3.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.lazyload.mini.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.inputfocus.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/scrolltopcontrol.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.tagbox.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.coverImg.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.tmpl.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.color.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/useFunction.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/global.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/outSideControl.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/header.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/homev7.js"></script>
<script type="text/javascript">
	var curSortId = ${sortId!0};
	var curTagId = ${secondSortId!0};
	var video= "00";
	var curTagName = "";
</script>

<!--[if lt IE 7]>
<script src="${systemProp.staticServerUrl}/v3/js/DD_belatedPNG_0.0.8a-min.js"></script>
<script>
  DD_belatedPNG.fix('.png');
  document.execCommand("BackgroundImageCache",false,true);
</script>
<![endif]-->
</head>
<#assign sub=0>
<#if looItemList?? && looItemList?size gt 0 >
   <#list looItemList as cc2>
	   <#if sortId?? && sortId gt 0 && cc2.parentId==sortId>
	       <#assign sub=1>
	   </#if>
   </#list>
</#if>
<body>


<#import "./components/header.ftl" as header>

<@header.main searchType="Creative"/>
<#if looItemList?? && looItemList?size gt 0 >
            		
            			<div class="headerSubNav20150126">
					        <div class="inner">
					        <#list looItemList as cc>
					        	<#if cc.parentId == 0 && sub == 1>
					            <div class="list nav${cc.id} <#if sortId?? && sortId == cc.id>current</#if> clearFix">
					            	<a href="${systemProp.appServerUrl}" <#if !secondSortId??> class="current"</#if>>全部All</a>
            						<#list looItemList as cc2>
	            					<#if cc2.parentId == cc.id>
					                <a <#if secondSortId?? && secondSortId == cc2.id>class="current" </#if> href="${systemProp.appServerUrl}/home/${cc2.parentId}/${cc2.id}.html" >${cc2.title}</a>
					                </#if>
            						</#list>
							    </div>
							    </#if>
							</#list>
					        </div>
				    	</div>
          </#if>
<div class="conAd conAd-1180-90" id="fullBanner">
	<iframe src="/baidu_clb.html#0_0" width="100%" height="100%" frameborder="0" scrolling="no" allowtransparency ></iframe>
</div>
<div class="body bodyHome clearFix" id="homeWall" style="position:relative;">
	<div class="itemFirst">
		<iframe src="/baidu_clb.html#${sortId!0}_1" width="100%" height="100%" frameborder="0" scrolling="no" allowtransparency ></iframe>
		<#-- 广告位：homepage big banner-->
	</div>
	<div class="item itemSpace patch"></div>
	<div class="item itemSpace patch"></div>
    <#if looArticleList?? && (looArticleList?size) gt 0>
    <#list looArticleList as c>
    	<div class="item <#if c.smallPic?? && c.smallPic?length gt 0 ><#else>itemNopic</#if>">
		<a href="${systemProp.appServerUrl}/sns/c${c.id}/" target="_blank">
	    	<div class="photo"><#if c.smallPic?? && c.smallPic?length gt 0 ><img  src="${systemProp.staticServerUrl}${c.smallPic!''}" alt="${c.title!''}"></#if></div>
	    	<div class="info png">
	    		<div class="class">
	    		<#if c.itemTitle??>
	    			<#assign i=(c.itemId)?split(',')>
	    			<#assign p=(c.parentId)?split(',')>
	    			<#list (c.itemTitle)?split(',') as s>
	    				<#if s_index < 3>
		    				<strong class="navC<#if p[s_index]?? && p[s_index] != "0">${p[s_index]}<#else>${i[s_index]}</#if>">${s}</strong>
	    				</#if>
	    			</#list>
	    		</#if>
	    		</div>
                <h2>${c.title!''}</h2>
	            <p>${c.memo!''}</p>
	        </div>
        </a>
        <div class="tools png">
        <em class="iconShare png" shareTypeId="eve_${c.id}_creative"></em></div>
    </div>
    </#list>
    </#if>
    <div class="sideRight">
	    <div class="conAd conAd-140">
		    <#-- 广告位  baidu -->
			<iframe src="/baidu_clb.html#${sortId!0}_2" width="100%" height="100%" frameborder="0" scrolling="no" allowtransparency ></iframe>
        </div>
        <#-- 其它广告位  baidu -->
       <div class="conAd conMagezine">
        	<div class="book">
			<iframe src="/baidu_clb.html#0_3" width="100%" height="100%" frameborder="0" scrolling="no" allowtransparency ></iframe>
			</div>
        </div>
       <div class="conAd conAd-400">
			<iframe src="/baidu_clb.html#0_4" width="100%" height="100%" frameborder="0" scrolling="no" allowtransparency ></iframe>
        </div>
       <div class="conAd conAd-140">
			<iframe src="/baidu_clb.html#0_5" width="100%" height="100%" frameborder="0" scrolling="no" allowtransparency ></iframe>
        </div>
       <div class="conAd conAd-280">
			<iframe src="/baidu_clb.html#0_6" width="100%" height="100%" frameborder="0" scrolling="no" allowtransparency ></iframe>
        </div>
       <div class="conAd conAd-140">
			<iframe src="/baidu_clb.html#0_7" width="100%" height="100%" frameborder="0" scrolling="no" allowtransparency ></iframe>
        </div>
       <div class="conAd conAd-280">
			<iframe src="/baidu_clb.html#0_8" width="100%" height="100%" frameborder="0" scrolling="no" allowtransparency ></iframe>
        </div>
       <div class="conAd conAd-140">
			<iframe src="/baidu_clb.html#0_9" width="100%" height="100%" frameborder="0" scrolling="no" allowtransparency ></iframe>
        </div>
       <div class="conAd conAd-280">
			<iframe src="/baidu_clb.html#0_10" width="100%" height="100%" frameborder="0" scrolling="no" allowtransparency ></iframe>
        </div>
	 <script>
	 	if($(".conTopic a").length<1){
					$(".conTopic").hide();
				}
	 </script>
	</div>
</div>
<span id="loadMore" style="cursor:pointer;" class="clickLoadMore"><span>点击查看更多</span></span>
<#import "./components/footer.ftl" as footer>
<@footer.main class="index" />
</body>
</html>
