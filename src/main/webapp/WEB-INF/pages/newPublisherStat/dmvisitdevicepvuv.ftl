<body>
	<div class="conB">
		<fieldset>
		 <table width="100%" class="table JQtableBg table1">
			<thead>
	          <tr>
	            <td>App应用名称</td>
	            <td>App平台</td>
	            <td>总计新用户</td>
	            <td>总计装机量</td>
	            <td>总计启动次数</td>
	          </tr>
	        </thead>
			<tbody id="appUsages">
				<#if appUsages?? && appUsages?size &gt; 0>
					<#list appUsages as appUsage>
			          <tr>
			            <td>${appUsage.appName!}</td>
			            <td>${appUsage.platform!}</td>
			            <td>${appUsage.newUser!}</td>
			            <td>${appUsage.installNum!}</td>
			            <td>${appUsage.startUpNum!}</td>
			          </tr>
		          </#list>
				</#if>
	        </tbody>
	    </table>
	    <#--
		<div>
			<em class="g100"><em class="icon16question" title="统计ipad和iphone从开始使用到目前您旗下所有杂志的新用户累计数据量"></em>总计新用户：</em>
			<em class="g80" >${newUserNum!'0'}</em>
			<em class="g120"><em class="icon16question" title="统计ipad和iphone上安装的应用从开始到目前您旗下所有杂志的装机量累计数据量"></em>总计装机量：</em><em class="g80">${installedNum!'0'}</em>
			<em class="g120"><em class="icon16question" title="统计ipad和iphone上启动过应用从开始到目前您旗下所有杂志的启动次数累计数据量"></em>总计启动次数：</em><em class="g80">${startNum!'0'}</em>
		</div>
		-->
		<hr />
		<h2>查询条件</h2>
        	<div>
            	<em>开始日期</em><em><input type="text" class="input g100" id="startDate"/></em>
            	<em>结束日期</em><em><input type="text" class="input g100" id="endDate"/></em>
            	<em>选择杂志</em>
            	<em>
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
            	<em><a href="#" id="changeChart" class="btnBS">确定</a></em>
            </div>
        </fieldset>
   </div>
   <div class="conB">
	<div id="mychart" class="conBody">
	 <object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"
	         id="myMap1" width="450px" height="450px"
	         codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab" style="margin:0 auto;">
	     <param name="movie" value="${systemProp.staticServerUrl}/reader/MagmePieChart.swf" />
	     <param name="quality" value="high" />
	     <param name="wmode" value="opaque" />
	     <param name="bgcolor" value="#869ca7" />
	     <param name="allowScriptAccess" value="always" />
	     <embed id="myMap2" src="${systemProp.staticServerUrl}/reader/MagmePieChart.swf" wmode="opaque" quality="high" bgcolor="#869ca7"
	         width="450px" height="450px" name="ExternalInterfaceExample" align="middle"
	         play="true" loop="false" quality="high" allowScriptAccess="always"
	         type="application/x-shockwave-flash"
	         pluginspage="http://www.macromedia.com/go/getflashplayer" style="margin:0 auto;">
	     </embed>
	 </object>
	 <table width="100%" class="table JQtableBg table1">
		<thead>
          <tr>
            <td>终端名称</td>
            <td>比例</td>
            <td>pv</td>
          </tr>
        </thead>
		<tbody id="deviceList">
        </tbody>
        
    </table>
	</div>
	
	
   </div>
        
        
    
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/stat/dmvisitdevicepvuv.js"></script>
</body>