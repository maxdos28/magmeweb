<#list commentList as c>
    <div class="cList">
		<a class="head" href="<#if c.ct==9>javascript:void(0)<#else>${systemProp.appServerUrl}/sns/u${c.uid}/</#if>">
			<img src="<#if c.avatar?? && c.avatar!="" ><#if c.ct==9>${systemProp.publishProfileServerUrl}<#else>${systemProp.profileServerUrl}</#if>${avatarResize(c.avatar,'60')}<#else>${systemProp.staticServerUrl}/images/head60.gif</#if>" onerror="this.src='${systemProp.staticServerUrl}/images/head60.gif'" /></a>
        <p>${c.content!}<span class="time">${c.createTime?string("yyyy.MM.dd")}</span></p>
        <p><span class="text">我<#if c.ct==6>回复<#else>评论</#if>了</span><strong title='${c.nickname!}'>${stringSub(c.nickname,24)}</strong><span class="text">的<#if c.ct==9><#elseif c.ct==6>评论<#else>作品</#if></span><span class="works" title='${c.title!}'>
        <#if c.ct==5>
    		<a target="_blank" href="${systemProp.appServerUrl}/sns/c${c.cid}/">
    			“${stringSub(c.title!,24)}”
    		</a>
    	<#elseif c.ct==9>
    		<a target="_blank" href="${systemProp.appServerUrl}/index-detail.action?itemId=${c.cid}&type=event">
    			“${stringSub(c.title!,24)}”
    		</a>
    	<#else>
    		“${stringSub(c.title!,24)}”
    	</#if>
        </span></p>
        <a comid='${c.id}' class="ctrl" ct='${c.ct}' href="javascript:void(0);">删除</a>
	</div>
</#list>
<#if page==1>
<#if (commentList?size)==0>
	<div class="cList nullInfo">还没有任何评论哦~</div>
</#if>
	<input id='temp_page_time_lock'  type="hidden" value='${pageTimeLock!}' />
	<script>
	$(function(){
		$("#page_time_lock").val($("#temp_page_time_lock").val());
	});
</script>
</#if>