<#macro main pageNo=1>
<#if messageList?? && (messageList?size>0)>
 <div id="message_page_${pageNo}">
  <#list messageList as message>
    <#if (message.followMessageList)??>
      <#assign followMessageList=message.followMessageList/>
      <div class="iitem clearFix">
        <#if (followMessageList?size>0)>
          <#list followMessageList as followMessage>
              <#if followMessage_index==0>
                <span><#if (followMessage.createdTime)??>${(followMessage.createdTime)?string('yyyy-MM-dd')}</#if></span>
                <div class="userHead">
                  <a href="<#if followMessage.fromType==1>${systemProp.appServerUrl}/user-visit!index.action?userId=${followMessage.fromUserId} <#else>publisher-home.action?publisherId=${publisher.id} </#if>">
                	<img src="<#if (followMessage.fromUserAvatar)?? && followMessage.fromUserAvatar!="" >
	                	<#if followMessage.fromType==1>
	                	    ${systemProp.profileServerUrl}${avatarResize(followMessage.fromUserAvatar,"30")} 
	                	<#else>
	                	    ${systemProp.publishProfileServerUrl}${avatarResize(followMessage.fromUserAvatar,"30")}
	                	</#if>
                	<#else>${systemProp.staticServerUrl}${systemProp.staticServerUrl}/images/head46.gif</#if>" />
                  </a>
                </div>
                <strong>
                   <a href="<#if followMessage.fromType==1>${systemProp.appServerUrl}/user-visit!index.action?userId=${followMessage.fromUserId}  <#else>publisher-home.action?publisherId=${publisher.id} </#if>">
                      <#if (followMessage.fromUserNickName)??>${followMessage.fromUserNickName}</#if>
                   </a>
                </strong>
                <p><#if (followMessage.contentInfo.content)??>${followMessage.contentInfo.content}</#if></p>
                
                <#if showAnswer?? && showAnswer==1 && (followMessageList?size==1) && session_publisher?? && (session_publisher.id)==publisher.id>
                    <a publisherId="${(publisher.id)?default('')}" userId="<#if followMessage.fromType==1>${followMessage.fromUserId}<#else>${followMessage.toUserId}</#if>"  href="javascript:void(0)" class="btnAnswer floatr" >回复</a>
                </#if>
              <#else>
                <p class="answer">
                     <span class="userHead">
                       <a href="<#if followMessage.fromType==1>${systemProp.appServerUrl}/user-visit!index.action?userId=${followMessage.fromUserId}  <#else>publisher-home.action?publisherId=${publisher.id} </#if>">
                         <img src="<#if (followMessage.fromUserAvatar)?? && followMessage.fromUserAvatar!="" >
                                    <#if followMessage.fromType==1>
                                     ${systemProp.profileServerUrl}${avatarResize(followMessage.fromUserAvatar,'30')} 
                                    <#else>
                                     ${systemProp.publishProfileServerUrl}${avatarResize(followMessage.fromUserAvatar,"30")}
                                    </#if>
                                   <#else>${systemProp.staticServerUrl}/images/head46.gif</#if>" />
                       </a>
                     </span>
                     <span><#if (followMessage.createdTime)??>${(followMessage.createdTime)?string('yyyy-MM-dd')}</#if></span>
                     <strong>
                       <a href="<#if followMessage.fromType==1>${systemProp.appServerUrl}/user-visit!index.action?userId=${followMessage.fromUserId} <#else>publisher-home.action?publisherId=${publisher.id} </#if>">
                        <#if (followMessage.fromUserNickName)??>${followMessage.fromUserNickName}</#if>
                       </a>
                     </strong>
                     <em><#if (followMessage.contentInfo.content)??>${followMessage.contentInfo.content}</#if></em>
                </p>
                  <#if showAnswer?? && showAnswer==1 && session_publisher?? && (session_publisher.id)==publisher.id && followMessage_index==(followMessageList?size-1)>
                      <a publisherId="${(publisher.id)?default('')}" userId="<#if followMessage.fromType==1>${followMessage.fromUserId}<#else>${followMessage.toUserId}</#if>" href="javascript:void(0)" class="btnAnswer floatr" >回复</a>
	              </#if>
               </#if>
            </#list>
           </#if>
        </div>
      </#if>
    </#list>
    </div>
 </#if>
</#macro>
