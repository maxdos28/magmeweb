<#import "../components/mobilefooter.ftl" as footer>

<html>
<head>
<title>麦米阅读器  <#if issue??>${issue.publicationName?default('')} ${issue.issueNumber?default('')}</#if></title>
<title>${(issue.publicationName)!""} 麦米网Magme - 无处不悦读</title>
<meta name="Keywords" content="${(issue.publicationName)!""},麦米网,Magme,电子杂志,在线杂志,免费电子杂志,电子杂志下载,数码杂志,时尚杂志,汽车杂志,财经杂志">
<meta name="Description" content="${(issue.description)!""}">
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/systemProp.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/readerTool.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/shareToInternet.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/useFunction.js"></script>
<script>
	function getIssueId(){
		return ${id!"0"};
	}
	
	function getPageNo(){
		return ${pageId!"0"};
	}
	
	function getAdId(){
		return ${adId!"null"};
	}	
</script>
</head>
<body leftmargin="0" topmargin="0"  marginwidth="0" marginheight="0" style="overflow:hidden;">
<div style="text-indent:-9999em; height:1px; background:#333; overflow:hidden;">
	<h1>${(issue.publicationName)!""}</h1>
    <p>${(issue.description)!""}</p>
</div>
<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" width="100%" height="100%" id="readerTool">
                <param name="movie" value="${systemProp.staticServerUrl}/reader/readerTool.swf" />
                <param name="quality" value="high" />
                <param name="bgcolor" value="#ffffff" />
                <param name="allowScriptAccess" value="always" />
                <param name="allowFullScreen" value="true" />
                <param name="wmode" value="window" />
                <!--[if !IE]>-->
                <object type="application/x-shockwave-flash" data="${systemProp.staticServerUrl}/reader/readerTool.swf" width="100%" height="100%">
                    <param name="quality" value="high" />
                    <param name="bgcolor" value="#ffffff" />
                    <param name="allowScriptAccess" value="always" />
                    <param name="allowFullScreen" value="true" />
                    <param name="wmode" value="window" />
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
		<object wmode=Transparent" id="embedSwf1"
			classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" width="100%" height="100%"
			codebase="http://fpdownload.macromedia.com/get/flashplayer/current/swflash.cab">
			<param name="movie" value="${systemProp.staticServerUrl}/reader/readerTool.swf" />
			<param name="quality" value="high" />
			<param name="bgcolor" value="#ffffff" />
			<param name="allowScriptAccess" value="always" />
			<param name="allowFullScreen" value="true" />
			<param name="wmode" value="window" />
			<embed wmode="window" id="embedSwf2"
				src="${systemProp.staticServerUrl}/reader/readerTool.swf"
				quality="high" bgcolor="#ffffff" height="100%" width="100%" name="magmetools"
				align="middle" play="true" loop="false" quality="high"
				allowScriptAccess="always" allowFullScreen="true"
				type="application/x-shockwave-flash"
				pluginspage="http://www.adobe.com/go/getflashplayer">
			</embed>
		</object>
	</div>
	
    
</div>   -->

<@footer.main/>
</body>
</html>