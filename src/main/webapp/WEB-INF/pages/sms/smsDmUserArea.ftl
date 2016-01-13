<div class="body">
	<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/sms/devjs/smsDmUserArea.js"></script>
    <div class="conLeftMiddleRight">
    	<div class="conTools">
        	<fieldset>
            	<a class="btnOS" class="current" href="${systemProp.kaiJieAppUrl}/sms/sms-dm-user-area.action">用户分布报表</a>
            	<a class="btnOS" href="${systemProp.kaiJieAppUrl}/sms/sms-dm-remain.action">停留时间报表</a>
            	<a class="btnOS" href="${systemProp.kaiJieAppUrl}/sms/sms-dm-ezz-stat.action">电话拨打报表</a>
            	<a class="btnOS" href="${systemProp.kaiJieAppUrl}/sms/sms-dm-click-time-dis.action">用户推广链接点击时间分布</a>
            </fieldset>
        </div>
        <input type="hidden" id="smsProjectId" value="${smsProjectId!''}"/>
        <div class="conB">
		   <h2 class="tCenter">用户分布报表</h2>
		   <div id="mychart" class="conBody" style="width:750px;height:670px;padding:40px;">
			 <object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"
			         id="myMap1" width="670px" height="670px"
			         codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab">
			     <param name="movie" value="${systemProp.kaiJieAppUrl}/reader/CNMap.swf" />
			     <param name="quality" value="high" />
			     <param name="wmode" value="opaque" />
			     <param name="bgcolor" value="#869ca7" />
			     <param name="allowScriptAccess" value="always" />
			     <embed id="myMap2" src="${systemProp.kaiJieAppUrl}/reader/CNMap.swf" wmode="opaque" quality="high" bgcolor="#869ca7"
			         width="670px" height="670px" name="ExternalInterfaceExample" align="middle"
			         play="true" loop="false" quality="high" allowScriptAccess="always"
			         type="application/x-shockwave-flash"
			         pluginspage="http://www.macromedia.com/go/getflashplayer">
			     </embed>
			  </object>
		    </div>
	    </div>
	    
     </div>
</div>