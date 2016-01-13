<#import "../components/ipadheader.ftl" as ipadHeader>
<#import "../components/mobilefooter.ftl" as mobilefooter>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="format-detection" content="telephone=no" />
<meta name="viewport" content="user-scalable=no">
<link rel="apple-touch-icon" href="${systemProp.staticServerUrl}/v3/mobile/k/iconApp.png" />
<link rel="stylesheet" type="text/css" href="${systemProp.staticServerUrl}/v3/mobile/style/html5Reset.css"/>		
<link rel="stylesheet" type="text/css" href="${systemProp.staticServerUrl}/v3/mobile/style/global.css"/>
<link rel="stylesheet" type="text/css" href="${systemProp.staticServerUrl}/v3/mobile/style/pad.css"/>
<script type="text/javascript" src="${systemProp.staticServerUrl}/js/systemProp.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/mobile/js/jquery-1.6.2.min.js"></script>
<!--<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/mobile/js/jquery.masonry.min.js"></script>-->
<!--<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/mobile/js/global.js"></script>-->

<title>Magme Mobile</title>
</head>

<body class="pad">
<@ipadHeader.main/>
<noscript>

<b>Your browser does not support JavaScript or you have switched 
off the JavaScript support.</b><p>None of the scripts used in this system
will work! Switch on JavaScript support or upgrade to a browser that
supports JavaScript.

</noscript>
<div id="container" class="read clearFix">
	<div id="imgCnt" class="cntGeneral img">
		<img id="prevIMG" class="imgGeneral" src="" width="100%"/>
		<img id="currIMG" class="imgGeneral" src="" width="100%" />
		<img id="nextIMG" class="imgGeneral" src="" width="100%"/>
		<div id="pageNum">${pageNo}</div>
		<div id="turnLeft" class="turnLeft"></div>
    	<div id="turnRight" class="turnRight"></div>
	</div>
	<div id="txtCnt" class="txtCnt">
		<div id="content" class="content">
			${(textPage.content)?default("")}
		</div>	
	</div>
</div>
<!--src="${systemProp.magServerUrl}/${issue.publicationId}/${issue.id}/768_${pageNo}.jpg" -->
<@mobilefooter.main/>
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/mobile/js/mm.touches.2.5.js"></script>
<script type="text/javascript" >
	MMSetup(
		(new MMTexter("content")),
		(new MMReader(
			"currIMG",
			"${systemProp.appServerUrl}",
			"${systemProp.magServerUrl}",
			"${eventId!''}",
			${issue.publicationId},
			${issue.id},
			${issue.totalPages},
			${pageNo}
		)),
		"${systemProp.adServerUrl}",
		${(fpageEvent.adId)!-1}
	);
</script>
</body>
</html>
