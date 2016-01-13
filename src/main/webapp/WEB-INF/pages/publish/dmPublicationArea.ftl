<body>
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/statF.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/stat/dmpublicationarea.js"></script>
	<div class="conB">
		<h2>表单名称</h2>
	    <fieldset>
	        <div>
	            <em>开始日期</em><em><input type="text" id="startDate" class="input g100" /></em>
	            <em>结束日期</em><em><input type="text" id="endDate" class="input g100" /></em>
	            <em>选择杂志</em><em><select id="publicationId">
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
		<h2 class="tCenter">杂志访客地区分布报表</h2>
		<div id="mychart" class="conBody" style="width:750px;height:670px;padding:40px;">
		 <object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"
		         id="myMap1" width="670px" height="670px"
		         codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab">
		     <param name="movie" value="${systemProp.staticServerUrl}/reader/CNMap.swf" />
		     <param name="quality" value="high" />
		     <param name="wmode" value="opaque" />
		     <param name="bgcolor" value="#869ca7" />
		     <param name="allowScriptAccess" value="always" />
		     <embed id="myMap2" src="${systemProp.staticServerUrl}/reader/CNMap.swf" wmode="opaque" quality="high" bgcolor="#869ca7"
		         width="670px" height="670px" name="ExternalInterfaceExample" align="middle"
		         play="true" loop="false" quality="high" allowScriptAccess="always"
		         type="application/x-shockwave-flash"
		         pluginspage="http://www.macromedia.com/go/getflashplayer">
		     </embed>
		 </object>
		</div>
	</div>
	
	<table width="100%" class="table JQtableBg table1">
		<thead>
		  <tr>
		    <td>省份</td>
		    <td>pv</td>
		    <#--
		    <td>uv</td>-->
		  </tr>
		</thead>
		<tbody id="dataList">
		</tbody>
	</table>
