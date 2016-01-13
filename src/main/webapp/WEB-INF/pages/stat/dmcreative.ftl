<body>
	<div class="conB">
		<h2>过滤条件</h2>
		<fieldset>
        	<div>
            	<em>开始日期</em><em><input type="text" class="input g100" id="startDate"/></em>
            	<em>结束日期</em><em><input type="text" class="input g100" id="endDate"/></em>
            	<em>分类</em><em>
            		<select id="categoryId">
            			<option value="0">全部</option>
            			<#if ccList??>
            				<#list ccList as cc>
            					<option value="${cc.id!0}">${cc.name!''}</option>	
            				</#list>
            			</#if>
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
		<h2 class="tCenter">作品排行报表</h2>
		<div id="mychart" class="conBody" style="width:790px;height:400px;padding:20px;">
	    </div>
	</div>
    <script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/stat/dmcreative.js"></script>
</body>