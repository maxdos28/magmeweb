<!--body-->
<body>
<script src="${systemProp.staticServerUrl}/v3/js/stat/dmpublication.js"></script>
<div class="conB">
	<h2>表单名称</h2>
    <fieldset>
        <div>
            <em>开始日期</em><em><input type="text" id="startDate" class="input g90" value="<#if startDate??>${startDate?string('yyyy-MM-dd')}</#if>"/></em>
            <em>结束日期</em><em><input type="text" id="endDate" class="input g90" value="<#if endDate??>${endDate?string('yyyy-MM-dd')}</#if>"/></em>
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
             <input type="hidden" id="order" value="1"/>
            <#--20120823 requirement delete order
			<em>排序</em>
			<em>
				<select id="DM_PUBLICATION_ORDER">
		     		<option value="1">时间升序</option>
		     		<option value="2">时间降序</option>
		     		<option value="3">PV升序</option>
		     		<option value="4">PV降序</option>
                </select>
            </em>-->
            <input type="hidden" id="expressDate" name="expressDate" value=""/>
            <em><a href="#" id="changeChart" class="btnBS">确定</a></em>
            <em><a href="javascript:void(0)" id="exportData" class="btnBS">导出excel</a></em>
         </div>
         <hr />
            <div>
                 <em>选择杂志</em><em><select id="publicationIdForPv">
                          <#if pubList?? && (pubList?size>0)>
		            		  <#list pubList as pub>
		            		   	<option value="${pub.id}" <#if publicationId?? && publicationId=pub.id> selected </#if>  > ${pub.name}</option>
		            		  </#list>
		            		</#if>
                     </select>
                 </em>
                <em>选择年份</em>
                <em>
                	<select id="year">
			     		<option value="2012">2012</option>
			     		<option value="2013">2013</option>
			     		<option value="2014">2014</option>
			     		<option value="2015">2015</option>
                    </select>
                </em>
                <em>月份</em>
                <em>
                	<select id="month">
			     		<option value="1">1</option>
			     		<option value="2">2</option>
			     		<option value="3">3</option>
			     		<option value="4">4</option>
			     		<option value="5">5</option>
			     		<option value="6">6</option>
			     		<option value="7">7</option>
			     		<option value="8">8</option>
			     		<option value="9">9</option>
			     		<option value="10">10</option>
			     		<option value="11">11</option>
			     		<option value="12">12</option>
                    </select>
                </em>
            	<em><a href="#" class="btnBS" id="searchPv">确定</a></em>
            	<em>总pv</em>
                <em id="totalPv" class="g70" >0</em>
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
<div id="mychart" class="conBody" style="width:790px;height:350px;padding:30px;">
</div>
</div>

<table width="100%" class="table JQtableBg table1">
<thead>
  <tr>
    <td>杂志名称</td>
    <td>时间</td>
    <td>pv<em class="icon16ascending" id="order_up_pv"></em><em class="icon16descending" id="order_down_pv"></em></td>
    <#--
    <td>uv<em class="icon16ascendingSelect" id="order_up_uv"></em><em class="icon16descending" id="order_down_uv"></em></td>-->
  </tr>
</thead>
<tbody id="dataList">
</tbody>
</table>
</body>
