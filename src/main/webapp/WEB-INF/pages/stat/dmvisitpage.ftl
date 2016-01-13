<body>
	<div class="conB">
		<h2>过滤条件</h2>
		<fieldset>
        	<div>
            	<em>开始日期</em><em><input type="text" class="input g100" id="startDate"/></em>
            	<em>结束日期</em><em><input type="text" class="input g100" id="endDate"/></em>
            	<em>数据类型</em>
            	<em>
            	   <select id="showType">
            	       <option value="pv" <#if showType?? && showType="pv"> selected </#if> >pv</option>
            	       <option value="uv" <#if showType?? && showType="uv"> selected </#if>  >uv</option>
            	       <option value="ipnum" <#if showType?? && showType="ipnum"> selected </#if>  >独立ip</option>
            	       <option value="retention" <#if showType?? && showType="retention"> selected </#if>  >停留时间</option>
            	   </select>
            	 </em>
            	<#--
            	<em>页面名称</em>
            	<em>
            	   <select id="ruleId">
            	       <option value="">全部</option>
            	       <#if dcRuleList?? && (dcRuleList?size>0)>
            	          <#list dcRuleList as dcRule>
            	             <option value="${dcRule.id}">${dcRule.name}</option>
            	          </#list>
            	       </#if>
            	   </select>
            	 </em> -->
            </div>
        	<hr />
        	<div>
            	<em><a href="#" class="btnBS" id="changeChart">确定</a></em>
            </div>
        	<hr />
        </fieldset>
   </div>
   <div class="conB">
		<h2 class="tCenter">受访页面分析</h2>
		<div id="mychart" class="conBody" style="width:790px;height:400px;padding:20px;">
	    </div>
	</div>
    <script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/stat/dmvisitpage.js"></script>
</body>