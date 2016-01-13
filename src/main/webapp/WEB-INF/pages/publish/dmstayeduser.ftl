<body>

   <script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/stat/dmstayeduser.js"></script>
	<div class="conB">
		<h2>过滤条件</h2>
		<fieldset>
        	<div>
            	<em>时间跨度：</em>
            	<em>开始日期</em><em><input type="text" class="input g100" id="startDate"/></em>
            	<em>结束日期</em><em><input type="text" class="input g100" id="endDate"/></em>
            	<em>
	                 <select id="chartType">
		            	<option value="1" selected="selected" >留存用户</option>
		            	<option value="2" >留存率</option>
	                 </select>
            	</em>
            	<em>
	                 <select id="selectType">
		            	<option value="1" selected="selected" >日留存</option>
		            	<option value="2" >周留存</option>
		            	<option value="3" >月留存</option>
	                 </select>
            	</em>
        		<#if session_admin??>
	            	<em title="查询结果为此本杂志所属出版商的全部数据">杂志:</em>
	            	<em><input type="text" class="input g100" id="publicationName"/></em>
            	</#if>
            	<em><a href="#" id="changeChart" class="btnBS">确定</a></em>
            </div>
            	<hr/>
            	<em title="在时间跨度内，每天的前1日（/周/月）回访的老用户总数">留存用户：</em><em id="stayedUserNum"></em>
            	<em title="在时间跨度内，第一次启动应用程序的用户总数">新用户：</em><em id="newUserNum"></em>
            	<em title="在时间跨度内，留存用户总数/老用户总数">留存率：</em><em id="rate"></em>
        </fieldset>
   </div>
  <div class="conB">
	<div id="myChart" class="conBody">
	 <object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"
	         id="myMap1" width="700" height="400"
	         codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab" style="margin:0 auto;">
	     <param name="movie" value="${systemProp.staticServerUrl}/reader/SuperMagmeChart.swf" />
	     <param name="quality" value="high" />
	     <param name="wmode" value="opaque" />
	     <param name="bgcolor" value="#869ca7" />
	     <param name="allowScriptAccess" value="always" />
	     <embed id="myMap2" src="${systemProp.staticServerUrl}/reader/SuperMagmeChart.swf" wmode="opaque" quality="high" bgcolor="#869ca7"
	         width="700" height="400" name="ExternalInterfaceExample" align="middle"
	         play="true" loop="false" quality="high" allowScriptAccess="always"
	         type="application/x-shockwave-flash"
	         pluginspage="http://www.macromedia.com/go/getflashplayer" style="margin:0 auto;">
	     </embed>
	 </object>
	</div>
   </div>
   <br/>
<table width="100%" class="table JQtableBg table1">
	<thead>
      <tr>
        <td>日期</td>
        <td>留存用户</td>
        <td>新用户</td>
        <td>留存率</td>
      </tr>
    </thead>
	<tbody id="dataList">
    </tbody>
    
</table>
</body>