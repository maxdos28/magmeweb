<#import "messageList.ftl" as messgeListFtl>
<body>
      <!--conMyMessage-->
      <div class="conMiddleRight">
      <div class="conB conMyMessage">
      	<h2>我的消息</h2>
          <div class="conBody">
              <!--topTools-->
              <div class="topBar clearFix">
                  <div class="floatl">
                  <#if session_aduser?? && session_aduser.level==1>
                  	<a href="${systemProp.appServerUrl}/publish/publish-message!writeMessage.action" class="btnOS" >新建</a>
                  <#elseif session_aduser.level==2>
                  	<a href="${systemProp.appServerUrl}/ad/ad-agency!writeMessage.action" class="btnOS" >新建</a>
                  </#if>
                  </div>
              </div>
              <div class="nullInfo" style="display:none;">
                                                              您还没有消息记录
              </div>
                  <@messgeListFtl.main/>
              </div>
          </div>
         </div>
</body>
