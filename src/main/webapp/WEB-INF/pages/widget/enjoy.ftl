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
							<span class="date">&nbsp;</span>
							<span class="page">&nbsp;</span>
							<a href="javascript:void(0)" issueId=${issue.id} class="btn" id="readBtn">点击阅读</a>
							<a href="javascript:void(0)" issueId=${issue.id} class="btn" id="cancelBtn" pubId=${issue.publicationId}>取消订阅</a>
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
<script type="text/javascript">

$(function(){
	$('.scroll-pane').jScrollPane();
});
</script>