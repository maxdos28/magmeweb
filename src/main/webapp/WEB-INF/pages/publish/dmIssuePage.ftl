<body>
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/stat/dmissuepage.js"></script>
<!--body-->
<div class="conB">
	<h2>查询条件</h2>
    <fieldset>
        <#--20120824注释
        <div>
            <input type="hidden" id="expressDate" name="expressDate" value=""/>
            <em>开始日期</em><em class="g200"><input type="text" id="startDate" class="input g80" value="<#if startDate??>${startDate?string('yyyy-MM-dd')}</#if>" /></em>
            <em>结束日期</em><em class="g200"><input type="text" id="endDate" class="input g80" value="<#if endDate??>${endDate?string('yyyy-MM-dd')}</#if>" /></em>
            <em>排序</em>
			<em>
				<select id="DM_ISSUE_ORDER"  class="g80" >
		     		<option value="1">时间升序</option>
		     		<option value="2">时间降序</option>
		     		<option value="3">停留升序</option>
		     		<option value="4">停留降序</option>
                </select>
            </em>
         </div>
         <br />-->
         <input type="hidden" id="expressDate" name="expressDate" value=""/>
         <input type="hidden" id="order" name="order" value=""/>
         <input type="hidden" id="startDate" name="startDate" value="<#if startDate??>${startDate?string('yyyy-MM-dd')}</#if>"/>
         <input type="hidden" id="endDate" name="endDate" value="<#if endDate??>${endDate?string('yyyy-MM-dd')}</#if>"/>
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
            <em class="g200"><select id="issueId" class="g190">
                      <#if issueList?? && (issueList?size>0)>
	            		  <#list issueList as issue>
	            		   	<option value="${issue.id}" <#if issueId?? && issueId=issue.id> selected </#if>  > ${issue.publicationName}/${issue.issueNumber}</option>
	            		  </#list>
	            		</#if>
                 </select>
             </em>
            
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

 <#--<div class="conB">
	<h2 class="tCenter">杂志阅读停留时间报表</h2>
	<div id="mychart" class="conBody" style="width:790px;height:350px;padding:20px;">
    </div> 
</div>-->
    
<table width="100%" class="table JQtableBg table1">
	<thead>
      <tr>
        <td>期刊名称</td>
        <td>时间<em class="icon16ascendingSelect" id="order_up_date"></em><em class="icon16descending" id="order_down_date"></em></td>
        <td>参考页码</td>
        <td>平均停留时间(秒)<em class="icon16ascending" id="order_up_retention"></em><em class="icon16descending" id="order_down_retention"></em></td>
      </tr>
    </thead>
	<tbody id="dataList">
    </tbody>
</table>

</body>