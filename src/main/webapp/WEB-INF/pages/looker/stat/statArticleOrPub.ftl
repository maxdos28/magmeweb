<script src="${systemProp.staticServerUrl}/js/devCommon.js"></script>
<script src="${systemProp.staticServerUrl}/js/systemProp.js"></script>
<script src="${systemProp.staticServerUrl}/js/form2object.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/datepicker.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/dateUtil.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/statF.js"></script>

<script src="${systemProp.staticServerUrl}/look/js/statArticleOrPub.js"></script>
<div class="body" menu="stat" submenu="statap">
      <div class="conLeftMiddleRight" style="min-height:600px;">
    	<div class="conB">
    	<input type="hidden" id="appId" value="${session_look_user.appid}">
		<h2>过滤条件</h2>
		<fieldset>
        	<div>
            	<em>开始日期</em><em><input type="text" class="input g100" id="startDate"/></em>
            	<em>结束日期</em><em><input type="text" class="input g100" id="endDate"/></em>
            	<em>栏目</em><em>
            		<select id="itemId">
            			<#if itemList??>
            				<#list itemList as cc>
            					<#if cc.parentId==0>
            					<option value="${cc.id!0}">${cc.title!''}</option>	
            					</#if>
            				</#list>
            			</#if>
            		</select>
            	</em>
            	<em>按</em><em>
            		<select id="puType">
            			<option value="pv">pv</option>
            			<option value="uv">uv</option>
            		</select>
            	</em>
            	<em>显示数量</em><em>
            		<select id="limit">
            			<option value="30">30</option>
            			<option value="50">50</option>
            			<option value="100">100</option>
            		</select>
            	</em>
            	<em><a href="#" class="btnBS" id="searchBtn">确定</a></em>
            </div>
        	<hr />
        </fieldset>
   </div>
   <div class="conB">
		<h2 class="tCenter">杂志（文章）访问量报表</h2>
	</div>
        
        
    <table width="100%" class="table JQtableBg table1">
		<thead>
          <tr>
            <td>名称</td>
            <td>类型</td>
            <td>PV</td>
            <td>UV</td>
          </tr>
        </thead>
		<tbody id="dataList">
        </tbody>
        
    </table>
    </div>
</div>
