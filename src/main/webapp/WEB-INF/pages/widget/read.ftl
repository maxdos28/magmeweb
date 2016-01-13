<link href="${systemProp.staticServerUrl}/style/jquery.jscrollpane.css" rel="stylesheet" type="text/css" />
<script src="${systemProp.staticServerUrl}/js/jquery.jscrollpane.min.js"></script>
<script src="${systemProp.staticServerUrl}/js/jquery.mousewheel.js"></script>
<script src="${systemProp.staticServerUrl}/v3/widget20120529/js/enjoy.js"></script>

<div class="outer">
	<!--txtBox-->
	<div class="txtBox png">
		<div class="inner png">
			<div class="z-scroll scroll-pane">
			<#if magPageInfo?? && magPageInfo.data??>
			    <#list magPageInfo.data as issue>
					<div class="item clearFix">
						<img src="${systemProp.magServerUrl}/${issue.publicationId}/100_${issue.id}.jpg"
						/>
						<div class="txt">
							<span class="name">${issue.publicationName}${issue.issueNumber}</span>
							<span class="date">上次 ${issue.categoryName}时</span>
							<span class="page">阅读到 ${issue.clickNum} 页</span>
							<a href="javascript:void(0)" class="btn" issueId=${issue.id} pageNo=${issue.clickNum} id="continueReadBtn">继续阅读</a>
						</div>
					</div>
			    </#list>
			</#if>
			</div>
		</div>
		<div class="txtBoxLine">
		</div>
		<div class="txtBoxBottom png">
		</div>
	</div>

</div>
	<div style="width:1px; height:1px; line-height:570px; font-size:60px; text-align:center; background:#333;">
		<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" width="100%" height="1" id="readerTool">
		 <param name="movie" value="${systemProp.staticServerUrl}/reader/HistoryViews.swf" />
		 <param name="quality" value="high" />
		 <param name="bgcolor" value="#ffffff" />
		 <param name="allowScriptAccess" value="always" />
		 <param name="allowFullScreen" value="true" />
		 <param name="wmode" value="opaque" />
		 <!--[if !IE]>-->
		 <object type="application/x-shockwave-flash" data="${systemProp.staticServerUrl}/reader/HistoryViews.swf" width="100%" height="1">
			<param name="quality" value="high" />
			<param name="bgcolor" value="#ffffff" />
			<param name="allowScriptAccess" value="always" />
			<param name="allowFullScreen" value="true" />
			<param name="wmode" value="opaque" />
		 <!--<![endif]-->
		 <!--[if gte IE 6]>-->
			<p>
					该浏览器不支持flash，你可以点击<a target="_top" href="void(0)">新版阅读器</a>进行浏览。
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
<script type="text/javascript">
var xx= "${xx!}"; 
	function getX(){
		return xx;
	}
	var changeFlg = "${changeFlg}"
	function getViewHistory(lst){
		var b=''; 
		for(i = 0; i < lst.length; i++) {
			b +="{issueId:";
			b += lst[i].issueId+",pageNum:";
			b += lst[i].pageNum+",date:";
			b += lst[i].date+"}";
			if (i!=lst.length-1){
				b += ",";
			}
		}
		if (changeFlg == 0) {
			window.location.href=SystemProp.appServerUrl+"/widget/qplus!read.action?changeFlg=1&cache="+b;
		}
	}
$(function(){
	$('.scroll-pane').jScrollPane();
});
</script>