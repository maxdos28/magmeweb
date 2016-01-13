<body>
	<div class="conB">
		<h2>过滤条件</h2>
		<fieldset>
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
	<div id="mychart" class="conBody" style="width:750px;height:670px;padding:20px;">
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
            <td>联网方式</td>
            <td>启动次数</td>
            <td>平均每次使用时长(H:M:S:MS)</td>
          </tr>
        </thead>
		<tbody id="retentionList">
        </tbody>
        
    </table>
	</div>
   </div>
   <script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/stat/dmretention.js"></script>
</body>