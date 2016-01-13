<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/widget/js/widgetReader.js"></script>
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
<div class="outer">
	   <div class="bodyReader">
		  <div style="width:760px; height:570px; line-height:570px; font-size:60px; text-align:center; background:#333;">
		  
			<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" width="100%" height="570" id="readerTool">
			 <param name="movie" value="${systemProp.staticServerUrl}/reader/readerToolWeibo.swf" />
			 <param name="quality" value="high" />
			 <param name="bgcolor" value="#ffffff" />
			 <param name="allowScriptAccess" value="always" />
			 <param name="allowFullScreen" value="true" />
			 <param name="wmode" value="opaque" />
			 <!--[if !IE]>-->
			 <object type="application/x-shockwave-flash" data="${systemProp.staticServerUrl}/reader/readerToolWeibo.swf" width="100%" height="570">
				<param name="quality" value="high" />
				<param name="bgcolor" value="#ffffff" />
				<param name="allowScriptAccess" value="always" />
				<param name="allowFullScreen" value="true" />
				<param name="wmode" value="opaque" />
			 <!--<![endif]-->
			 <!--[if gte IE 6]>-->
			 	<p>
						该浏览器不支持flash，你可以点击<a target="_top" href="http://www.magme.com/mag/${id!'0'}/${pageId!'0'}.html">新版阅读器</a>进行浏览。
			 	</p>
			 <!--<![endif]-->
				<a href="http://www.adobe.com/go/getflashplayer">
				    <img src="http://www.adobe.com/images/shared/download_buttons/get_flash_player.gif" alt="Get Adobe Flash Player" />
				</a>
			 <!--[if !IE]>-->
			 </object>
			 <!--<![endif]-->
		  </object>
		  </div>
	   </div>
</div>