<script>
$(".sideLeft .theme .conForward .conReplyOuter").each(function(){
	$(this).jScrollPane().data('jsp').scrollToY(999999);
})
</script>
<div class="conReplyOuter">
    <div class="conReply conReplyBig">
    	<#list fpageEventComment?reverse as cfs >
    		<#if cfs_index%2==0>
    			 <div class="bl">
    			 	<#if cfs.status==1>
    			 		<a title="${cfs.user.nickName!}" href="javascript:void(0);" class="head"><img src="<#if cfs.user.avatar?? && cfs.user.avatar!="" >${systemProp.profileServerUrl}${avatarResize(cfs.user.avatar,'46')}<#else>${systemProp.staticServerUrl}/images/head60.gif</#if>" onerror="this.src='${systemProp.staticServerUrl}/images/head60.gif'"  /></a>
    			 		<p><span>
    			 		<#if (session_user.id)?? && session_user.id==cfs.user.id>
					       <#if !cfs.status?? || (cfs.id?? && cfs.status?? && cfs.status==1)>
					    	<del commentId="${cfs.id}" type="event"></del>
					       </#if>
					    </#if>
            			 <strong><a href="<#if ((session_user)??) && (session_user.id==cfs.user.id) >${systemProp.appServerUrl}/sns/${(session_user.id)!'0'}/<#else>${systemProp.appServerUrl}/sns/u${cfs.user.id}/</#if>">${stringSub(cfs.user.nickName!,20)}</a>
            			 ${cfs.createdTime?string('yyyy-MM-dd')}</strong>${cfs.content}</span></p><em></em>
    			 	<#else>
    			 		<a title="${cfs.user.nickName!}" href="javascript:void(0);" class="head"><img src="<#if cfs.user.avatar?? && cfs.user.avatar!="" >${systemProp.profileServerUrl}${avatarResize(cfs.user.avatar,'46')}<#else>${systemProp.staticServerUrl}/images/head60.gif</#if>" onerror="this.src='${systemProp.staticServerUrl}/images/head60.gif'"  /></a>
    			 		<p><span>
            			 <strong><a href="<#if ((session_user)??) && (session_user.id==cfs.user.id) >${systemProp.appServerUrl}/sns/${(session_user.id)!'0'}/<#else>${systemProp.appServerUrl}/sns/u${cfs.user.id}/</#if>">${stringSub(cfs.user.nickName!,20)}</a>
            			 该评论已删除！</strong></span></p><em></em>
    			 	</#if>
    			 </div>
    		<#else>
    			<div class="br">
        			<#if cfs.status==1>
            			<a  title="${cfs.user.nickName!}" href="javascript:void(0);" class="head"><img src="<#if cfs.user.avatar?? && cfs.user.avatar!="" >${systemProp.profileServerUrl}${avatarResize(cfs.user.avatar,'46')}<#else>${systemProp.staticServerUrl}/images/head60.gif</#if>" onerror="this.src='${systemProp.staticServerUrl}/images/head60.gif'" /></a>
            			<p><span>
            			<#if (session_user.id)?? && session_user.id==cfs.user.id>
					       <#if !cfs.status?? || (cfs.id?? && cfs.status?? && cfs.status==1)>
					    	<del commentId="${cfs.id}" type="event"></del>
					       </#if>
					    </#if>
            			<strong><a href="<#if ((session_user)??) && (session_user.id==cfs.user.id) >${systemProp.appServerUrl}/sns/${(session_user.id)!'0'}/<#else>${systemProp.appServerUrl}/sns/u${cfs.user.id}/</#if>">${stringSub(cfs.user.nickName!,20)}</a>
            			 ${cfs.createdTime?string('yyyy-MM-dd')}</strong>${cfs.content}</span></p><em></em>
        			<#else>
        				<a title="${cfs.user.nickName!}" href="javascript:void(0);" class="head"><img src="<#if cfs.user.avatar?? && cfs.user.avatar!="" >${systemProp.profileServerUrl}${avatarResize(cfs.user.avatar,'46')}<#else>${systemProp.staticServerUrl}/images/head60.gif</#if>" onerror="this.src='${systemProp.staticServerUrl}/images/head60.gif'" /></a>
    			 	 	<p><span>
    			 		<strong>
    			 		<a href="<#if ((session_user)??) && (session_user.id==cfs.user.id) >${systemProp.appServerUrl}/sns/${(session_user.id)!'0'}/n<#else>${systemProp.appServerUrl}/sns/u${cfs.user.id}/</#if>">${stringSub(cfs.user.nickName!,20)}</a>
    			 		该评论已删除！</strong>
    			 		</span>
        			 	</p><em></em>
        			</#if>
    			</div>	
    		</#if>
    	</#list>
       
    </div>
</div>
