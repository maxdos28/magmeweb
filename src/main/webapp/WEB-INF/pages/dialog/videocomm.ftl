<html>
<head>
<link href="${systemProp.staticServerUrl}/v3/style/reset.css" rel="stylesheet" type="text/css" />
<script src="${systemProp.staticServerUrl}/v3/js/jquery-new.min.js"></script>
<script>
	function videoFlashComplete(){
		//alert("<#if urlStr??>${urlStr}</#if>");
		var mediUrl = "";
		mediUrl = "<#if urlStr??>${urlStr}</#if>";
		var payObject = ($.browser.msie)?$("#vipay").get(0):$("#vipay2").get(0);
		payObject.videoFlashSetData(mediUrl);
		mediUrl= "";
	 }
	function videoFlashClose(){
		window.parent.document.getElementById("videoPlayIframe").src="";
		window.parent.dialogClose();
		
	}
</script>
</head>
<body>    
<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"
             id="vipay" width="600" height="450" name="vipay"
             codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab">
         <param name="movie" value="${systemProp.staticServerUrl}/reader/videoPlayer.swf" />
         <param name="quality" value="high" />
         <param name="bgcolor" value="#869ca7" />
         <param name="allowScriptAccess" value="always" />
		 <param name="allowFullScreen"  value="true"/>
         <embed id="vipay2" src="${systemProp.staticServerUrl}/reader/videoPlayer.swf" quality="high" bgcolor="#869ca7"
             width="600" height="450" name="vipay2" align="middle"
             play="true" loop="false" quality="high" allowScriptAccess="always" allowFullScreen="true"
             type="application/x-shockwave-flash"
             pluginspage="http://www.macromedia.com/go/getflashplayer">
         </embed>
     </object>
</body>
</html>