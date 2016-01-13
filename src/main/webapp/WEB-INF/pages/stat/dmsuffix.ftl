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
            	   </select>
            	 </em>
            </div>
        	<hr />
        	<div>
        	<em>后缀URL</em><em><input type="text" class="input g250" id="suffix"/></em>
        	</div>
        	<hr/>
        	<div>
            	<em><a href="#" class="btnBS" id="changeChart">确定</a></em>
            </div>
        	<hr />
        </fieldset>
   </div>
   <div class="conB">
		<h2 class="tCenter">网址后缀来路统计</h2>
		<div id="mychart" class="conBody" style="width:790px;height:400px;padding:20px;">
	    </div>
	</div>
    <script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/stat/dmsuffix.js"></script>
</body>