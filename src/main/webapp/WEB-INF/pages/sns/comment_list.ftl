<#list commentList as c>
	<div class="cList" com='${c.id}' ct='${c.ct}'>
		<a class="head" href="${systemProp.appServerUrl}/sns/u${c.uid}/">
			<img src="<#if c.avatar?? && c.avatar!="" >${systemProp.profileServerUrl}${avatarResize(c.avatar,'60')}<#else>${systemProp.staticServerUrl}/images/head60.gif</#if>" onerror="this.src='${systemProp.staticServerUrl}/images/head60.gif'" /></a>
        <p><strong class='nickName' u='${c.uid}' title='${c.nickname!}'>${stringSub(c.nickname,24)}</strong><span class="time">${c.createTime?string("yyyy.MM.dd")}</span><span class="text"><#if c.ct==6>回复<#elseif c.ct=5>评论</#if>我的<#if c.ct==6>评论<#else>作品</#if></span>
        	<span class="works" c='${c.cid}' title='${c.title!}'>
    		<#if c.ct==5>
        		<a target="_blank" href="${systemProp.appServerUrl}/sns/c${c.cid}/">
        			“${stringSub(c.title!,18)}”
        		</a>
        	<#else>
        		“${stringSub(c.title!,18)}”
        	</#if>
			</span>
        </p>
        <p>${c.content!}</p>
        <a class="ctrl" href="javascript:void(0);">回复</a>
    </div>
</#list>
<#if page==1>
<#if commentList.size()==0>
	<div class="cList nullInfo">还没有任何评论哦~</div>
</#if>
	<input id='temp_page_time_lock'  type="hidden" value='${pageTimeLock!}' />
	<script>
	$(function(){
		$("#page_time_lock").val($("#temp_page_time_lock").val());
	});
</script>
</#if>