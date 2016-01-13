<body>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.hoverplay.js"></script>
<script>
var currentCategoryData=""
$(function(){
	//$.jqueryhoverplay(".item",60,8);
	$(".bodyMagazine .item .photo .album").live("click",function(){
		var publicationId=$(this).attr("publicationId")||0;
		var issueId=$(this).attr("issueId")||0;
		window.location="/publish/publication-home!image.action?publicationId="+publicationId+"&type=issueImage";
		return false;
	})
	
	//item移入
	if(isIE6){
		$(".body>.item").mouseenter(function(){
			$(this).find(".tools").addClass("toolsOn").find("em").css({display:"inline-block"});
		}).mouseleave(function(){
			$(this).find(".tools").removeClass("toolsOn").find("em").css({display:"none"});
		});
	}
	
});
</script>
<#import "magazineList.ftl" as magazineList>
<#if pubId??>
	<input type="hidden" id="isNeedScroll" value="0"/>
	<#else>
	<input type="hidden" id="isNeedScroll" value="1"/>
</#if>

<@magazineList.main/>
<#if !(pubId??)>
<span id="loadMore" style="cursor:pointer;" class="clickLoadMore"><span>点击查看更多</span></span>
<!--
<div id="loading" class="pageLoad"><span>正在加载内容...</span></div>
-->
</#if>
<script>
$(function(){ 
	$("#headerSearch").show();
});
</script>
</body>