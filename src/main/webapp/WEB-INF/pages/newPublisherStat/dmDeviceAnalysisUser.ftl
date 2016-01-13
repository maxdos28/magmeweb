<body>
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/stat/dmDeviceAnalysisUser.js"></script>
<div class="conB">
	<h2>查询条件</h2>
    <fieldset>
        <div>
            <em>开始日期</em><em ><input type="text" id="startDate" class="input g90" value="<#if startDate??>${startDate?string('yyyy-MM-dd')}</#if>"/></em>
            <em>结束日期</em><em ><input type="text" id="endDate" class="input g90" value="<#if endDate??>${endDate?string('yyyy-MM-dd')}</#if>"/></em>
        	<em>&nbsp;</em>
        	<em>选择杂志</em>
        	<em >
        		<#if session_admin??>
        			<input type="text" class="input g100" id="publicationName"/>
        		<#else>
                 <select id="publicationId">
					<#if pubList?? && (pubList?size>0)>
            		  <#list pubList as pub>
            		   	<option value="${pub.id}" <#if publicationId?? && publicationId=pub.id> selected </#if>  > ${pub.name}</option>
            		  </#list>
					</#if>
                 </select>
                 </#if>
         	</em>
         	<em>&nbsp;</em>
		 	<em >
				<select id="device">
					<option value="ipad"  selected   >ipad</option>
					<option value="iphone"  >iphone</option>
				</select>
			</em>
			<em>&nbsp;</em>
          	<input type="hidden" id="expressDate" name="expressDate" value=""/>
            <em><a href="#" id="changeChart" class="btnBS">确定</a></em>
    	</div>
    	<hr />
    	
    </fieldset>
</div>
<br/>
<table width="100%" style="color: black;">
	<tbody>
		<tr>
			<td><em style="vertical-align: text-top;" class="icon16question" title="安装应用的数量"></em><b>装机量：<span id='insertSum'>0</span></b></td>
			<td><em style="vertical-align: text-top;" class="icon16question" title="启动过应用的数量"></em><b>启动次数：<span id='deviceStartSum'>0</span></b></td>
			<td><em style="vertical-align: text-top;" class="icon16question" title="从开始使用到目前的用户量"></em><b>累计新用户：<span id='newUser'>0</span></b></td>
			<td><em style="vertical-align: text-top;" class="icon16question" title="从开始使用到目前启动的数量"></em><b>累计启动次数：<span id='totalDeviceStartSum'>0</span></b></td>
		</tr>
	</tbody>
</table>
<br/>
<table width="100%" class="table JQtableBg table1">
	<thead>
      <tr>
        <td></td>
        <td>启动次数</td>
        <td>启动用户</td>
        <td>新用户</td>
        <td>新用户占比</td>
        <td>平均使用时长</td>
      </tr>
    </thead>
	<tbody id="dataList">
		<tr>
        <td>昨天</td>
        <td>${result.ydStartSum?default(0)}</td>
        <td>${result.ydStartUser?default(0)}</td>
        <td>${result.ydNewUser?default(0)}</td>
        <td>${result.ydNewUserRate?default(0)}%</td>
        <td>${result.ydAvgTime!}</td>
      </tr>
      <tr>
        <td>每日平均</td>
        <td>${result.dayAvgStartSum?default(0)}</td>
        <td>${result.dayAvgStartUser?default(0)}</td>
        <td>${result.dayAvgNewUser?default(0)}</td>
        <td>${result.dayNewUserRate?default(0)}%</td>
        <td>${result.dayAvgUseTime}</td>
      </tr>
      <tr>
        <td>历史峰值</td>
        <td>${result.TopStartSum?default(0)}</td>
        <td>${result.topStartUser?default(0)}</td>
        <td>${result.topNewUser?default(0)}</td>
        <td>${result.topNewUserRate?default(0)}%</td>
        <td></td>
      </tr>
    </tbody>
</table>
 <div class="conB">
	<div id="mychart" class="conBody" style="width:790px;height:350px;padding:30px;">
	</div>
</div>
</body>