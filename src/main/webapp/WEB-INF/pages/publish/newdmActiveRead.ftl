<body>
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/stat/newdmactiveread.js"></script>
<div class="conB">
	<h2>表单名称</h2>
    <fieldset>
        <div>
            <em>开始日期</em><em class="g200"><input type="text" id="startDate" class="input g90" value="<#if startDate??>${startDate?string('yyyy-MM-dd')}</#if>"/></em>
            <em>结束日期</em><em class="g200"><input type="text" id="endDate" class="input g90" value="<#if endDate??>${endDate?string('yyyy-MM-dd')}</#if>"/></em>
        </div>
        <br />
        <div>
            <em>选择杂志</em>
            <em  class="g200">
                 <select id="publicationId">
                      <#if pubList?? && (pubList?size>0)>
	            		  <#list pubList as pub>
	            		   	<option value="${pub.id}" <#if publicationId?? && publicationId=pub.id> selected </#if>  > ${pub.name}</option>
	            		  </#list>
	            		</#if>
                 </select>
             </em>
            <em>选择期刊</em>
            <em  class="g200">
                 <select id="issueId">
                      <#if issueList?? && (issueList?size>0)>
	            		  <#list issueList as issue>
	            		   	<option value="${issue.id}" <#if issueId?? && issueId=issue.id> selected </#if>  > ${issue.publicationName!''}${issue.issueNumber!''}</option>
	            		  </#list>
	            		</#if>
                 </select>
             </em>
            <input type="hidden" id="expressDate" name="expressDate" value=""/>
            <em><a href="#" id="changeChart" class="btnBS">确定</a></em>
         </div>
        	<hr />
        	<div>
        	 <em><a href="javascript:void(0)" name="express" expressDate="1" id="load_0" class="btnWS">昨天</a></em>
			 <em><a href="javascript:void(0)" name="express" expressDate="2" id="load_1" class="btnWS">上周</a></em>
			 <em><a href="javascript:void(0)" name="express" expressDate="3" id="load_2" class="btnWS">前七天</a></em>
			 <em><a href="javascript:void(0)" name="express" expressDate="5" id="load_3" class="btnWS">上月</a></em>
        	</div>
        	<hr />
    </fieldset>
</div>
<div class="conB">
	<div id="mychart" class="conBody" style="width:750px;height:350px;padding:20px;">
	 <object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"
	         id="myMap1" width="670px" height="350px"
	         codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab">
	     <param name="movie" value="${systemProp.staticServerUrl}/reader/MagmeLineChart.swf" />
	     <param name="quality" value="high" />
	     <param name="wmode" value="opaque" />
	     <param name="bgcolor" value="#869ca7" />
	     <param name="allowScriptAccess" value="always" />
	     <embed id="myMap2" src="${systemProp.staticServerUrl}/reader/MagmeLineChart.swf" wmode="opaque" quality="high" bgcolor="#869ca7"
	         width="670px" height="350px" name="ExternalInterfaceExample" align="middle"
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
	    <td>关注热度</td>
	    <td>关注文章</td>
	  </tr>
	</thead>
	<tbody id="dataList">
	</tbody>
</table>
</body>
