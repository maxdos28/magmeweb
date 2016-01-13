<div class="body">
    <script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/jscharts.js"></script>
	<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/sms/devjs/smsDm800Stat.js"></script>
    <div class="conLeftMiddleRight">
    	<div class="conTools">
        	<fieldset>
            	<a class="btnOS" href="${systemProp.kaiJieAppUrl}/sms/sms-dm-user-area.action">用户分布报表</a>
            	<a class="btnOS"  href="${systemProp.kaiJieAppUrl}/sms/sms-dm-remain.action">停留时间报表</a>
            	<a class="btnOS" class="current" href="${systemProp.kaiJieAppUrl}/sms/sms-dm-ezz-stat.action">电话拨打报表</a>
            	<a class="btnOS" href="${systemProp.kaiJieAppUrl}/sms/sms-dm-click-time-dis.action">用户推广链接点击时间分布</a>
            </fieldset>
        </div>
        <input type="hidden" id="smsProjectId" value="${smsProjectId!''}"/>
         <div class="conB">
			<div id="mychart" class="conBody" style="width:750px;height:400px;padding:20px;"></div>
		</div>
	<table width="100%" class="table JQtableBg table1">
		<thead>
          <tr>
            <td>电话号码</td>
            <td>所在地</td>
          </tr>
        </thead>
		<tbody id="smsDmEzzList">
        </tbody>
        
    </table>
   
        
     </div>
</div>