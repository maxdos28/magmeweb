<#macro main>
	<#if commentLst??>
	<#list commentLst as comment>
	<div class="bl">
		<img class="head" src="<#if ((comment.avatar)??)&&(comment.avatar!="")>${systemProp.profileServerUrl}${avatarResize(comment.avatar,'46')}<#else>images/head46.gif</#if>" title="用户名" />
		<p>
		<span>
		    <#--自己的评论并且没有删除才能删除-->
		    <#if (session_user.id)?? && session_user.id==comment.userId>
		       <#if !comment.status?? || (comment.creativeId?? && comment.status?? && comment.status==1)>
		    	<del commentId="${comment.id}" type="<#if comment.type?? && comment.type=='event'>event<#else>creative</#if>"></del>
		       </#if>
		    </#if>
			<strong><a javascript="void(0)">${(comment.nickname)!""}</a>
			${(comment.createTime?string('yyyy-MM-dd'))!""}
			</strong>
			<#if !comment.status?? || (comment.creativeId?? && comment.status?? && comment.status==1)>
			${(comment.content)!""}
			<#else>
			该评论已删除
			</#if>
		</span>
		</p>
		<em>
		</em>
	</div>
	</#list>
	</#if>
	<#if !(commentLst??)|| !(commentLst[0]??) || !commentLst[0]?exists>沙发空缺中，还不快抢～</#if>
</#macro> 