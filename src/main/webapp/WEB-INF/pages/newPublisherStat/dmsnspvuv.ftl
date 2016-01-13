<body>
	<div class="conB">
		<h2>过滤条件</h2>
		<fieldset>
        	<div>
            	<em>开始日期</em><em><input type="text" class="input g100" id="startDate"/></em>
            	<em>结束日期</em><em><input type="text" class="input g100" id="endDate"/></em>
            	<em>选择杂志</em>
            	<em>
	                 <select id="publicationId">
	                      <#if pubList?? && (pubList?size>0)>
		            		  <#list pubList as pub>
		            		   	<option value="${pub.id}" <#if publicationId?? && publicationId=pub.id> selected </#if>  > ${pub.name}</option>
		            		  </#list>
		            		</#if>
	                 </select>
            	</em>
            	<em><a href="#" id="changeChart" class="btnBS">确定</a></em>
            </div>
             <hr />
		     <em><a href="javascript:void(0)" name="express" expressDate="1" id="load_0" class="btnWS">昨天</a></em>
			 <em><a href="javascript:void(0)" name="express" expressDate="2" id="load_1" class="btnWS">上周</a></em>
			 <em><a href="javascript:void(0)" name="express" expressDate="3" id="load_2" class="btnWS">前七天</a></em>
			 <em><a href="javascript:void(0)" name="express" expressDate="5" id="load_3" class="btnWS">上月</a></em>
	        <hr />
        </fieldset>
   </div>
   
   <div class="conB">
     <div id="mychartLine" class="conBody" style="width:790px;height:350px;padding:30px;">
	 </div>
   </div>
	
	<table width="100%" class="table JQtableBg table1">
		<thead>
	      <tr>
	        <td>发行渠道</td>
	        <td>总计PV<em class="icon16ascending" id="up_pv"></em><em class="icon16descending" id="down_pv"></em></td>
	        <td>阶段PV<em class="icon16ascending" id="up_uv"></em><em class="icon16descending" id="down_uv"></em></td>
	        <td>占比%<em class="icon16ascending" id="up_retention"></em><em class="icon16descending" id="down_retention"></em></td>
	      </tr>
	    </thead>
		<tbody id="dataList">
	    </tbody>
	</table>
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
	</div>
   </div>
   <script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/stat/dmsnspvuv.js"></script>
</body>