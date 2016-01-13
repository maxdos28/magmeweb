<#if userMessages?? && (userMessages?size) gt 0>
	<#list userMessages as m>
			<div class="appItem_message_block">
			<div class="appItem_message_data"><span>${m.createTime?string('yyyy')}<em>-</em>${m.createTime?string('MM')}<em>-</em>${m.createTime?string('dd')}</span>${m.createTime?string('HH:mm:ss')}<#if m.type == 1><a href="javascript:;" class="reply"></a></#if></div>
				<div class="appItem_message_txt">
					<p class="appItem_message_inner">${m.messageContent!}</p>
					<p class="appItem_message_inner_Author"><#if m.type == 2>系统自动回复<#elseif m.type == 1>客服回复<#elseif m.type == 0>公告消息<#elseif m.type == 3>您的回复</#if></p>	
				</div>	
				<#if m.type == 1>
				<div class="reply-Content" style="display:none;">
				<div class="reply-Content-text"><textarea></textarea></div>
				<p class="clearFix"><a href="javascript:;" name="replyBtn" class="confirm">回复</a><a href="javascript:;" class="off">取消</a></p>
				</div>
				</#if>
			</div>
		    <#if !m_has_next>
		    	<script>
		    		;$(function($){
		    			$("#last_message_id").val(${m.id});
		    		});
		    	</script>
		    </#if>
	</#list>
</#if>
