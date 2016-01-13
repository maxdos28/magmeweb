<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<#import "../components/mobilefooter.ftl" as mobilefooter>

<script src="${systemProp.staticServerUrl}/js/systemProp.js"></script>
<script src="${systemProp.staticServerUrl}/js/jquery-1.6.2.min.js"></script>
<#if xx??&&xx=='sina'>
<link href="${systemProp.staticServerUrl}/v3/widget/style/reset.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/widget/style/global.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/widget/style/pop.css" rel="stylesheet" type="text/css" />
<script src="${systemProp.staticServerUrl}/v3/js/jquery.sampleFancyBox.js"></script>
<script src="http://tjs.sjs.sinajs.cn/t35/apps/opent/js/frames/client.js" language="JavaScript"></script>
<script src="http://tjs.sjs.sinajs.cn/open/api/js/wb.js?appkey=${appKey}" type="text/javascript" charset="utf-8"></script>
<#elseif xx??&&xx=='tencent'&&yy??>
<link href="${systemProp.staticServerUrl}/v3/widget/style/reset.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/widget/style/global.css" rel="stylesheet" type="text/css" />
<link href="${systemProp.staticServerUrl}/v3/widget/style/pop.css" rel="stylesheet" type="text/css" />
<script src="${systemProp.staticServerUrl}/v3/js/jquery.sampleFancyBox.js"></script>
<script src="http://fusion.qq.com/fusion_loader?appid=${appKey}&platform=${yy}" type="text/javascript" charset="utf-8" ></script>
<#elseif xx??&&xx=='baidu'>
<script src="http://app.baidu.com/static/appstore/monitor.st"></script>
<#elseif xx??&&xx=='kaixin'>
<script src="http://s.kaixin001.com.cn/js/openapp-8.js" language="JavaScript"></script>
<#--
<script src="${systemProp.staticServerUrl}/v3/js/kaixin001.js"></script>
-->
</#if>


<script>
    
    var xx = '${xx}';
    var yy = '${yy!}';
    var appKey = '${appKey!}';

    function getX(){
		return xx;
	}
	
	function getY(){
		return yy;
	}

	function getPublicationId(){
		return ${publicationId!"-1"};
	}

	function getIssueId(){
		return ${id!"-1"};
	}
	
	function getPageNo(){
		return ${pageId!"-1"};
	}
	
	function getAdId(){
		return ${adId!"null"};
	}
	
	function friendClick(publicationName){
		fusion2.dialog.invite({
		msg : publicationName+"杂志好看,一起看吧？",
		img : "http://ctc.qzonestyle.gtimg.cn/qzonestyle/act/qzone_app_img/app${appKey!}_${appKey!}_75.png",
		context : "invite",
		onSuccess : function (opt) { alert("发送成功: " + fusion.JSON.stringify(opt)); },
		onCancel : function (opt) {  },
		onClose : function (opt) { },
		"" : ""
		});
	}
	
</script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/widget/js/snsReader.js"></script>
        <!--magezineBox-->
        <div class="bodyReader">
            <div style="width:<#if size??>${size}px<#else>100%</#if>; height:570px;">
		<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" width="100%" height="570" id="readerTool">
	            <#if xx??&&xx=='kaixin'>
		            <param name="movie" value="${systemProp.staticServerUrl}/reader/EmbededToolKaixin.swf" />
		        <#elseif xx??&& xx='sina'>
	             <param name="movie" value="${systemProp.staticServerUrl}/reader/EmbededToolSina.swf" />
	            <#elseif xx??&& xx='baidu'>
	             <param name="movie" value="${systemProp.staticServerUrl}/reader/EmbededToolBaidu.swf" />
	            <#elseif xx?? && xx=='360'>
	            <param name="movie" value="${systemProp.staticServerUrl}/reader/EmbededTool360.swf" />
	            <#else>
		            <param name="movie" value="${systemProp.staticServerUrl}/reader/EmbededTool.swf" />
				</#if>
                <param name="quality" value="high" />
                <param name="bgcolor" value="#ffffff" />
                <param name="allowScriptAccess" value="always" />
                <param name="allowFullScreen" value="true" />
                <param name="FlashVars" value="publicationId=${publicationId}&issueId=-1&isSNS=1" />
                <param name="wmode" value="opaque" />
                <!--[if !IE]>-->
				<#if xx??&&xx=='kaixin'>
            		<object type="application/x-shockwave-flash" data="${systemProp.staticServerUrl}/reader/EmbededToolKaixin.swf" width="100%" height="570"  id="readerTool2">
	            <#elseif xx??&& xx='sina'>
	                <object type="application/x-shockwave-flash" data="${systemProp.staticServerUrl}/reader/EmbededToolSina.swf" width="100%" height="570"  id="readerTool2">
	            <#elseif xx??&& xx='baidu'>
	                <object type="application/x-shockwave-flash" data="${systemProp.staticServerUrl}/reader/EmbededToolBaidu.swf" width="100%" height="570"  id="readerTool2">
	            <#elseif xx??&& xx='360'>
	                <object type="application/x-shockwave-flash" data="${systemProp.staticServerUrl}/reader/EmbededTool360.swf" width="100%" height="570"  id="readerTool2">
	            <#else>
            		<object type="application/x-shockwave-flash" data="${systemProp.staticServerUrl}/reader/EmbededTool.swf" width="100%" height="570"  id="readerTool2">
				</#if>
				    <param name="quality" value="high" />
                    <param name="bgcolor" value="#ffffff" />
                    <param name="allowScriptAccess" value="always" />
                    <param name="allowFullScreen" value="true" />
                    <param name="wmode" value="opaque" />
                    <param name="FlashVars" value="publicationId=${publicationId}&issueId=-1&isSNS=1" />
                <!--<![endif]-->
                <!--[if gte IE 6]>-->
                	<p>
                		Either scripts and active content are not permitted to run or Adobe Flash Player version
                		10.0.0 or greater is not installed.
                	</p>
                <!--<![endif]-->
                    <a href="http://www.adobe.com/go/getflashplayer">
                        <img src="http://www.adobe.com/images/shared/download_buttons/get_flash_player.gif" alt="Get Adobe Flash Player" />
                    </a>
                <!--[if !IE]>-->
                </object>
                <!--<![endif]-->
            </object>
		<#if xx == 'tencent'>
		<div style="font-size:12px;color:#666;text-align:right;height:20px;line-height:20px;">
		    本应用由上海居冠软件(麦米网)开发，您在使用过程中遇到问题请联系我们&nbsp;&nbsp;技术支持：021-63375016
		</div>
		</#if>
            </div>  
        </div>
<#if xx??&&xx=='kaixin'>
	<iframe ALLOWTRANSPARENCY  id='kaixin001_commentbox_iframe' frameborder='0' scrolling='no' marginheight='0' marginwidth='0' hspace='0' vspace='0' style='height:350px;width:760px;' src=''></iframe>
	<script type='text/javascript'>
		var title = document.title;
		var appName = getUrlValue("en");
		var appKey = getUrlValue("appKey");
		var kxcb = 'http://www.kaixin001.com/rest/commentbox.php?appkey=' + appKey
					+ '&xid=0&width=760&height=350&url=http%3A//www.kaixin001.com/%21' + appName
					+ '/'+'&title='+encodeURIComponent(title);
		document.getElementById('kaixin001_commentbox_iframe').src = kxcb;
	</script>
</#if>
<@mobilefooter.main />
</html>