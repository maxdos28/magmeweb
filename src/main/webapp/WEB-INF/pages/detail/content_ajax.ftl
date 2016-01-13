<#import "content.ftl" as content>
<script>
$(function(){
	$.jqueryScrollPhoto("#conOtherRead",4,222,4,0,600);
	$.jquerySlideDoor("#conBuyAd",3,1,0,5000);
	$(".conOtherRead .inner .item .photo img, .conVideoAd img").coverImg();
	$.jqueryInputTips();
});
</script>
<@content.main />