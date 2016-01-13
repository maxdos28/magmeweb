<body >
	<div class="conB" menuId="1">
		<h2>过滤条件</h2>
		<fieldset>
        	<div>
            	<em>开始日期</em><em><input type="text" class="input g100" id="startDate"/></em>
            	<em>结束日期</em><em><input type="text" class="input g100" id="endDate"/></em>
            </div>
        	<hr />
        	<div>
            	<em>广告来源</em>
            	<em><select id="domain">
            		<option selected value=''>所有广告</option>
            		<#if listDomain?? && (listDomain?size>0) >
	            		<#list listDomain as domain>
	            			<option value='${domain}'>${domain}</option>
	            		</#list>
            		</#if>
            	</select></em>
            	<em><a href="#" class="btnBS" id="changeChart">确定</a></em>
            </div>
        	<hr />
        </fieldset>
   </div>
   <div class="conB">
		<h2 class="tCenter">侧栏广告点击排行报表</h2>
		<div id="mychart" class="conBody" style="width:790px;height:400px;padding:20px;">
	    </div>
	</div>
        
      
    <script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/dmadside.js"></script>
</body>