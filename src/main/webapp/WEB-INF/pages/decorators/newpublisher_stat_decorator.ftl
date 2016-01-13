<#import "../components/newPublisher.ftl" as np>
<!DOCTYPE HTML>
<!--[if lt IE 7 ]> <html class="ie6"> <![endif]-->
<!--[if IE 7 ]>    <html class="ie7"> <![endif]-->
<!--[if IE 8 ]>    <html class="ie8"> <![endif]-->
<!--[if (gte IE 9)|!(IE)]><!--> <html> <!--<![endif]-->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Magme</title>
<link href="${systemProp.staticServerUrl}/v3/style/reset.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/global.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/channelAdmin.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/datepicker.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/style/pop.css" rel="stylesheet" type="text/css" />
<script src="${systemProp.staticServerUrl}/js/systemProp.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery-new.min.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.easing.1.3.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.inputfocus.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.scrollphoto.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.tagbox.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/useFunction.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/global.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/admin.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/datepicker.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jscharts.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/dateUtil.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/statF.js"></script>
<script>
$(function(){
fnReadyTable();
var $JQtableBg = $("table.JQtableBg");
var $JQtableHover = $("table.JQtableBg tbody tr");
$JQtableBg.each(function(){
	$(this).find("tbody tr:odd").addClass('bgColorTable');
});
$JQtableHover.live("mouseover",function(){
$(this).addClass("bgTrHover");
});
$JQtableHover.live("mouseout",function(){
	$(this).removeClass("bgTrHover");
});
})
</script>
<script>
$(function(){
	var menu=$("div[menu]").attr("menu")||"";
	$("#menu_"+menu).addClass("current");
});
</script>

<script>
//menu highlight
$(function(){
   var location=window.location.href.substring(19);
   $("a[href='"+location+"']").parent("li").addClass("current");
   $("a[href='"+location+"']").parent("li").parent("ul").parent("li").addClass("current");
});
</script>
<script>
//menu manager
        	$(function(){
				fnSetLeftNav();
				$(".bodyDataPage .leftNav>li:has(ul)>a").bind("click",function(){
					fnClickLeftNav($(this));
				});
				
				
				
				function fnSetLeftNav(){
					$(".bodyDataPage .leftNav>li").each(function(){
						if($(this).find("ul").length==0){
							$(this).find("a").css({background:"none"});
						}else{
							$(this).addClass("hasUl").find(">a").addClass("title");
							if($(this).hasClass("current")){
								$(this).find("ul").show();
							}
						}
					});
				}
				function fnClickLeftNav($this){
					if(!$this.parent().hasClass("current")){
						$this.parent().addClass("current").find("ul").slideDown(300);
					}else{
						$this.parent().removeClass("current").find("ul").slideUp(300);
					}
				}
					
			});
        </script>

<!--[if lt IE 7]>
<script src="/v3/js/DD_belatedPNG_0.0.8a-min.js"></script>
<script>
  DD_belatedPNG.fix('.png');
  document.execCommand("BackgroundImageCache",false,true);
</script>
<![endif]-->
</head>
<body>
<!--header-->
<@np.header class />
<!--body-->
<div class="body bodyDataPage" menu="data" >
	<div class="conLeft">
	   <h2>
	    <#if  (session_publisher.logo)?? && session_publisher.logo!="">
		   <img src="${systemProp.publishProfileServerUrl}${session_publisher.logo172}" />
		  <#else>
		   <img src="${systemProp.staticServerUrl}/v3/images/head150.gif" />
		 </#if>
		</h2>
		<ul class="leftNav clear">
            <li><a href="/publish/dm-publication.action" >基础数据展示</a></li>
            <li><a href="#">用户分析</a>
            	<ul style="display:block">
                    <li><a href="/publish/dm-refer-publication.action">来路域名报表</a></li>
                    <li><a href="/publish/dm-publication-area.action">地域报表统计</a></li>
                    <li><a href="/publish/dm-sns-pv-uv.action">渠道分布</a></li>
                </ul>
            </li>
            <li ><a href="#">阅读使用分析</a>
            	<ul style="display:block">
                    <li><a href="/publish/dm-issue-page!newActiveRead.action">期刊阅读活跃比例</a></li>
                    <li><a href="/publish/dm-issue-page.action">页面阅读时长</a></li>
                    <li><a href="/publish/dm-pv-uv-publication.action">阅读时段访问分析</a></li>
                </ul>
            </li>
             <li ><a href="#">发行专家数据分析</a>
            	<ul style="display:block">
            	    <!--<li><a href="/newpublisherstat/dm-retention.action">联网方式</a></li>-->
                    <li><a href="/newpublisherstat/dm-app-usage-new-details.action">联网方式</a></li>
                    <li><a href="/newpublisherstat/dm-stayed-user.action">留存用户</a></li>
                    <li><a href="/newpublisherstat/dm-device-analysis.action">终端用户分析</a></li>
                    <#--<li><a href="/newpublisherstat/dm-visit-device-pv-uv.action">App应用数据</a></li>-->
                    <li><a href="/new-publisher/app-start!index.action">移动设备数据分析</a></li>
                </ul>
            </li>
        </ul>
        <#--
        <div class="leftNav clear">
            <a href="/publish/dm-publication.action">基础数据展示</a>
            <a href="/publish/dm-issue-page.action">页面阅读时长</a>
            <a href="/publish/dm-publication-area.action">地域报表统计</a>
            <a href="/publish/dm-issue-page!activeRead.action">期刊阅读活跃比例</a>
            <a href="/publish/dm-refer-publication.action">来路域名报表</a>
        </div> -->

    </div>
	<div class="conMiddleRight">
		${body}
	</div>
</div>
</body>
</html>
