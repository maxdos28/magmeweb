<!--body-->
<div class="body">
	<div class="body980" style="margin-top:20px; border-top:3px solid #eee; padding-top:10px;">
    
	<div style="clear: both;"></div>
	<div id="swfReader" style="width: 100%; height:600px;z-index:0;">
		<object wmode="Transparent" id="embedSwf1"
			classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" width="800" height="600"
			codebase="http://fpdownload.macromedia.com/get/flashplayer/current/swflash.cab">
			<param name="movie" value="${systemProp.appServerUrl}/game/MagmeLianLianKan.swf" />
			<param name="quality" value="high" />
			<param name="bgcolor" value="#ffffff" />
			<param name="allowScriptAccess" value="always" />
			<param name="allowFullScreen" value="true" />
			<param name="wmode" value="window" />
			<embed wmode="window" id="embedSwf2" width="800" height="600"
				src="${systemProp.appServerUrl}/game/MagmeLianLianKan.swf"
				quality="high" bgcolor="#ffffff" name="magmetools"
				align="middle" play="true" loop="false" quality="high"
				allowScriptAccess="always" allowFullScreen="true"
				type="application/x-shockwave-flash"
				pluginspage="http://www.adobe.com/go/getflashplayer">
			</embed>
		</object>


    </div>
    
    <#if llkYesterList?? && llkYesterList?size gt 0>
    <div>
        <h3 style="font-size:16px; margin:0 0 10px 0; border-top:3px solid #eee; padding-top:10px;">连连看昨日排行榜</h3>
        <table class="table" width="100%" border="0" cellspacing="0" cellpadding="0">
            <thead>
              <tr>
                <td width="100">名次</td>
                <td width="200">用户昵称</td>
                <td>成绩</td>
              </tr>
            </thead> 
            <tbody>
             <#list llkYesterList as yesterday>
             <tr>
                <td>${yesterday.rank}</td>
                <td>${yesterday.userNickName}</td>
                <td>${yesterday.score}</td>
              </tr>
              </#list>
            </tbody>
		</table>
    </div>
    </#if>
    
    <#if llkWeekList?? && llkWeekList?size gt 0>
    <div style="margin-top:30px; margin-bottom:20px;">
        <h3 style="font-size:16px; margin:0 0 10px 0; border-top:3px solid #eee; padding-top:10px;">上周连连看获奖名单</h3>
        <ul class="userList clearFix">
        	<#list llkWeekList as week>
        	<li>${week.userNickName}</li>
        	</#list>
        </ul>
    </div>
    </#if>
    
    </div>
</div>
<script type="text/javascript">
$(function(){
	$("#userFeedBack").remove();	
})
</script>