<#macro main>
<!--conLeft-->
<div class="conLeft">
	<div class="itemUser">
    	<div class="head">
    	<#assign publisher=session_publisher>
    	 <#if  publisher.logo?? && publisher.logo!="">
    	   <img id="publisherLogo" src="${systemProp.publishProfileServerUrl}${publisher.logo60}" />
    	  <#else>
    	   <img id="publisherLogo" src="${systemProp.staticServerUrl}/images/head60.gif" />
    	 </#if>
    	</div>
        <h3>${publisher.userName}</h3>
        <div class="control clearFix">
            <a id="editPublisherInfo" class="iconEdit" href="javascript:void(0)">编辑账号</a>
            <a id="changePublisherLogo" class="iconChange" href="javascript:void(0)">更换头像</a>
        </div>
        <ul class="clear listB clearFix">
            <li><a id="createPublication" class="current" href="javascript:void(0)">创建杂志</a></li>
            <li><a id="uploadIssue" href="javascript:void(0)" url="${systemProp.appServerUrl}/publish/publication!uploadIndex.action">上传期刊</a></li>
            <li><a href="${systemProp.appServerUrl}/publish/pcenter-publisher!magList.action">杂志管理</a></li>
            <#--<li><a href="manageMgzData.html">数据分析</a></li>-->
            <li><a href="${systemProp.appServerUrl}/publish/publish-message.action">消息<span>(${(publisher.messageCount)?default('0')})</span></a></li>
            <li><a href="${systemProp.appServerUrl}/publish/publisher-home!pubHome.action?publisherId=${publisher.id}">前台主页</a></li>
            <li><a href="${systemProp.appServerUrl}/publish/publisher-upload.action">上传信息</a></li>
        </ul>
    </div>
</div>
</#macro>