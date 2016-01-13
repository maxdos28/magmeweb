<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>消息中心</title>
<meta name="description" content="">
<meta name="keywords" content="">
<link href="${systemProp.staticServerUrl}/v3/style/looker/reset.css" rel="stylesheet" />
<link href="${systemProp.staticServerUrl}/v3/style/looker/<#if os?? && os.equals('IOS')>base.css<#else>base_and.css</#if>" rel="stylesheet" />
<script src="${systemProp.staticServerUrl}/v3/js/looker/jquery-1.7.2.min.js"></script>
<script src="${systemProp.staticServerUrl}/js/systemProp.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/looker/app.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/looker/app_ex.js"></script>
</head>
<body>
<style type="text/css">
html{background: #141414;height:100%;}
</style>
<input id="os" type="hidden" value='${os!}' >
<input id="uid" type="hidden" value='${userId!}' >
<input id="appId" type="hidden" value='${appId!}' >
<input id="muid" type="hidden" value='${muid!}' >
<input id="version" type="hidden" value='${v!}' >
<input id="last_message_id" type="hidden" >
<div class="appBody">
	<div id='message_center' class="appItem_message">
	<#if userMessages?? && (userMessages?size) gt 0>
		<#list userMessages as m>
			<#if m.id gt mesRead >
			<div class="appItem_message-list">
			<#else>
			<#if m.id == mesRead >
			<div class="appItem_message-list">
			</#if>
			<div class="appItem_message_block">
			</#if>
				<#if m_index == 0 >
					<script>
						$(function($){
			    			var data = {"userId":${userId!},"appId":${appId!},"muid":${muid!},"v":${v!},"messageId":${m.id}};
							$.ajax({
								url : SystemProp.appServerUrl + "/app/looker-if!updateUserReadMessage.action",
								type : "post",
								async : true,
								data : data,
								dataType : 'json',
								success : function (rs){
									var os_andriod = "ANDROID";
									var os_ios = "IOS";
									var os = '${os!}';
									if(os == os_andriod){
										window.magme.syncReadMes();
									}else if(os == os_ios){
										window.open("iosmethod:syncReadMes");
									}
								}
							});
		    			});
					</script>
				</#if>
				<div class="appItem_message_data"><span>${m.createTime?string('yyyy')}<em>-</em>${m.createTime?string('MM')}<em>-</em>${m.createTime?string('dd')}</span>${m.createTime?string('HH:mm:ss')}<#if m.type == 1><a href="javascript:;" class="reply"></a></#if></div>
				<div class="appItem_message_txt">
					<p class="appItem_message_inner">${m.messageContent!}</p>
					<p class="appItem_message_inner_Author"><#if m.type == 2>系统自动回复<#elseif m.type == 1>客服回复<#elseif m.type == 0>公告消息<#elseif m.type == 3>您的回复</#if></p>	
				</div>	
				<#if m.type == 1>
				<div class="reply-Content" style="display:none;">
				<div class="reply-Content-text"><textarea></textarea></div>
				<p class="clearFix"><a href="javascript:;" msgId="${m.id!}" name="replyBtn" class="confirm">回复</a><a href="javascript:;" class="off">取消</a></p>
				</div>
				</#if>
				<#if m.id gt mesRead >
			</div>
				<div class="appItem_message-list-line"></div>
			<#else>
			</div>
			<#if m_index + 1 == userMessages?size>
			</div>
			</#if>
			</#if>
				
		    <#if !m_has_next>
		    	<script>
		    		;$(function($){
		    			$("#last_message_id").val(${m.id});
		    		});
		    	</script>
		    </#if>
		</#list>
	</#if>
	</div>
	<div class="appEmpty_grayLine"></div>
	<div id="appItemBox_last" class="appItemBox_last message_last">
		<div class="appItem_input">
		    	<input id="load_message_more" type="button" value="加载更多" class="button graybotton">
	    </div>
	</div>
</div>
<script type="text/javascript">

$(function(){
	$('a.reply').bind('click', function(event) {
		var $par = $(this).parents('.appItem_message-list');
		var $this = $(this);
		if($(this).hasClass('no')){
			return false;
		}else{
			$this.addClass('no');			
			$par.find('.reply-Content').show();		
		}
	});

	$('a.off').bind('click', function(event) {
		var $par = $(this).parents('.appItem_message-list');
		var $this = $(this);
		$par.find('.reply').removeClass('no');
		$par.find('.reply-Content').hide();	
	});
})
</script>
</body>
</html>
