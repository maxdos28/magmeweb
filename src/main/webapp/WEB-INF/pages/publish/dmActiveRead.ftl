<body>
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/stat/dmactiveread.js"></script>
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
        	 <em class="floatr" id="totalAvgRetention">总平均阅读时长：0 秒/页</em> 
        	</div>
        	<hr />
    </fieldset>
</div>
<div class="conB">
	<h2 class="tCenter">期刊阅读活跃比例</h2>
	<div id="mychart" class="conBody" style="width:790px;height:350px;padding:20px;">
</div>
</body>
