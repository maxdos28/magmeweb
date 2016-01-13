<html>
<head>
<title><#if desType?? && desType==2>预览阅读器<#else>米商阅读器</#if>  <#if issue??>${issue.publicationName?default('')} ${issue.issueNumber?default('')}</#if></title>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/systemProp.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/readerTool.js"></script>
<script>
	function getIssueId(){
		return ${id!"0"};
	}
	
	function getPageNo(){
		return ${pageId!"0"};
	}	
</script>
</head>
<body leftmargin="0" topmargin="0"  marginwidth="0" marginheight="0" style="overflow:hidden;">

<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" width="100%" height="100%" id="readerTool">
                <param name="movie" value="${systemProp.appServerUrl}/reader/<#if desType?? && desType==2>PreviewTool.swf<#else>PublisherTool.swf</#if>" />
                <param name="quality" value="high" />
                <param name="bgcolor" value="#ffffff" />
                <param name="allowScriptAccess" value="sameDomain" />
                <param name="allowFullScreen" value="true" />
                <!--[if !IE]>-->
                <object type="application/x-shockwave-flash" data="${systemProp.appServerUrl}/reader/<#if  desType?? && desType==2>PreviewTool.swf<#else>PublisherTool.swf</#if>" width="100%" height="100%">
                    <param name="quality" value="high" />
                    <param name="bgcolor" value="#ffffff" />
                    <param name="allowScriptAccess" value="sameDomain" />
                    <param name="allowFullScreen" value="true" />
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

<#--
<div class="body clearFix pageLogin" style="padding:0;">

	<div style="clear: both;"></div>
	<div id="swfReader" style="width: 100%;height: 100%;  z-index:0;">
		<object wmode="Transparent" id="embedSwf1"
			classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" width="100%" height="100%"
			codebase="http://fpdownload.macromedia.com/get/flashplayer/current/swflash.cab">
			<param name="movie" value="${systemProp.appServerUrl}/reader/readerTool.swf" />
			<param name="quality" value="high" />
			<param name="bgcolor" value="#ffffff" />
			<param name="allowScriptAccess" value="always" />
			<param name="allowFullScreen" value="true" />
			<param name="wmode" value="window" />
			<embed wmode="window" id="embedSwf2"
				src="${systemProp.appServerUrl}/reader/readerTool.swf"
				quality="high" bgcolor="#ffffff" height="100%" width="100%" name="magmetools"
				align="middle" play="true" loop="false" quality="high"
				allowScriptAccess="always" allowFullScreen="true"
				type="application/x-shockwave-flash"
				pluginspage="http://www.adobe.com/go/getflashplayer">
			</embed>
		</object>
	</div>
	
    
</div>   -->
﻿<#import "../components/mobilefooter.ftl" as footer>
<@footer.main/>
</body>
</html>