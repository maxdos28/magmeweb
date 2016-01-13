<#import "../publish/publisherProfile.ftl" as publisherProfile>
<#import "../publish/publisherUpload.ftl" as publisherUpload>
<#import "../publish/publisherLeftMenu.ftl" as publisherLeftMenu>

<html>
<head>
<title>米商中心</title>
</head>
<body>
	<script type="text/javascript" src="${systemProp.staticServerUrl}/js/publisher.js"></script>
	<div class="body clearFix">	
	    <div id="sideLeft" class="sideLeft">
	       <!--用户信息 -->
	       <@publisherProfile.main/>
	       <@publisherLeftMenu.main/>
	    </div>
	    
	    <!--sideMiddleRight-->
	    <div id="sideMiddleRight" class="sideMiddleRight">
	       <@publisherUpload.main/>
	    </div>
	</div>
    
</body>
</html>