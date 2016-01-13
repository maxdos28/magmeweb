
<#if creativeList?? && (creativeList?size) gt 0>
	 <#list creativeList as c>
	 	<div class="item activity">
			<a href="${systemProp.appServerUrl}/sns/c${c.id}/" target="_blank">
		    	<div class="photo">
		    	<#if c.cType==2>
		    			<img height="${(c.issueid * 220 / c.publicationid)}"  src="${systemProp.staticServerUrl}${avatarResize(c.magazineUrl!,'500')}"   alt="${c.title!}">
		    		<#else>
		    			<img src="${c.magazineUrl!}"   alt="${c.title!}">
		    	</#if>
		    	
		    	
		    	</div>
		    	<div class="info png">
	                <strong>${stringSub(c.nickname!,24)}</strong>
	                 <#if c.comefrom?? && c.comefrom &gt; 9><em class="png">${c.comefrom!}</em></#if>
		    	    <h6 class="png" title="${c.nickname!}">
		    	    	<img src="<#if c.avatar?? && c.avatar!="" >${systemProp.profileServerUrl}${avatarResize(c.avatar,'60')}<#else>${systemProp.staticServerUrl}/images/head60.gif</#if>" onerror="this.src='${systemProp.staticServerUrl}/images/head60.gif'" /></h6>
	                <h3>${c.title!}</h3>
		            <p><#if c.content?length gt 150>${c.content?substring(0,150)}……<#else>${c.content}</#if></p>
		        </div>
	        </a>
	        <div class="tools png" cre='${c.id}' u='${c.userId}'><em title="喜欢" class="<#if c.isEnjoy?? && (c.isEnjoy>0)>iconHearted<#else>iconHeart</#if> png"></em><em class="iconShare png"></em></div>
	    </div>
	 </#list>
</#if>
<script>
	var forwardTools = "<div id='shareDiv' style='display:none'><p><a title='新浪微博' tp='tsina' href='javascript:void(0)' class='weibo png'></a><a href='javascript:void(0)' tp='tqq' title='腾讯微博' class='tencent png'></a><a title='开心网' tp='kaixin'  href='javascript:void(0)' class='kaixin png'></a><a title='人人网' tp='renren' href='javascript:void(0)' class='renren png'></a><a title='M1社区' href='javascript:void(0)' tp='m1' class='m1 png'></a></p></div>";
	$("em.iconShare").mouseenter(function(){
		$(this).append(forwardTools).find("div").fadeIn(200);
	}).mouseleave(function(){
		$(this).find("div").remove();
	});
</script>