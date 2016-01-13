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
            	       <option value="regnum" <#if showType?? && showType="regnum"> selected </#if> >当日新增</option>
            	       <option value="totalRegnum" <#if showType?? && showType="totalRegnum"> selected </#if>  >累计总数</option>
            	   </select>
            	 </em>
            </div>
        	<hr />
        	<div>
            	<em><a href="#" class="btnBS" id="changeChart">确定</a></em>
            </div>
        	<hr />
        </fieldset>
   </div>
   <div class="conB">
		<h2 class="tCenter">注册用户分析表</h2>
		<div id="mychart" class="conBody" style="width:790px;height:400px;padding:20px;">
	    </div>
	</div>
    <script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/stat/dmuserreg.js"></script>
</body>