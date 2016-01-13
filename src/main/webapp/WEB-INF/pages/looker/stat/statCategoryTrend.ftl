<script src="${systemProp.staticServerUrl}/js/devCommon.js"></script>
<script src="${systemProp.staticServerUrl}/js/systemProp.js"></script>
<script src="${systemProp.staticServerUrl}/js/form2object.js"></script>
<script src="${systemProp.staticServerUrl}/js/ajaxfileupload-multi.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.sampleFancyBoxAdmin.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/jscharts.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/datepicker.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/dateUtil.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/statF.js"></script>

<script type="text/javascript" src="${systemProp.staticServerUrl}/look/js/statCategoryTrend.js"></script>
<div class="body" menu="stat" submenu="statcate2">
      <div class="conLeftMiddleRight" style="min-height:600px;">
    	<div class="conB">
		<h2>过滤条件</h2>
		<fieldset>
        	<div>
            	<em>开始日期</em><em><input type="text" class="input g100" id="startDate"/></em>
            	<em>结束日期</em><em><input type="text" class="input g100" id="endDate"/></em>
            	<em>分类</em><em>
            		<select id="categoryId">
            			<#if categoryList??>
            				<#list categoryList as cc>
            					<option value="${cc.id!0}">${cc.title!''}</option>	
            				</#list>
            			</#if>
            		</select>
            	</em>
            	<em>栏目</em><em>
            		<select id="itemId">
            			<option value="0">全部</option>
            			
            		</select>
            	</em>
            	<em><a href="#" class="btnBS" id="changeChart">确定</a></em>
            </div>
        	<hr />
        </fieldset>
   </div>
   <div class="conB">
		<h2 class="tCenter">分类（栏目）浏览趋势报表</h2>
		<div id="mychart" class="conBody" style="width:790px;height:400px;padding:20px;">
	    </div>
	</div>
    </div>
</div>
