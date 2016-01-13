<script src="${systemProp.staticServerUrl}/js/devCommon.js"></script>
<script src="${systemProp.staticServerUrl}/js/systemProp.js"></script>
<script src="${systemProp.staticServerUrl}/js/form2object.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/datepicker.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/dateUtil.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/statF.js"></script>

<script src="${systemProp.staticServerUrl}/look/js/statArticleByEmployee.js"></script>
<div class="body" menu="stat" submenu="statae">
      <div class="conLeftMiddleRight" style="min-height:600px;">
    	<div class="conB">
		<h2>过滤条件</h2>
		<fieldset>
        	<div>
            	<em>开始日期</em><em><input type="text" class="input g100" id="startDate"/></em>
            	<em>结束日期</em><em><input type="text" class="input g100" id="endDate"/></em>
            	
            	<em><a href="#" class="btnBS" id="searchBtn">确定</a></em>
            </div>
        	<hr />
        </fieldset>
   </div>
   <div class="conB">
		<h2 class="tCenter">员工文章发布量报表</h2>
	</div>
        
        
    <table width="100%" class="table JQtableBg table1">
		<thead>
          <tr>
            <td class="g50"></td>
            <td>员工</td>
            <td class="g100">发布数量</td>
          </tr>
        </thead>
		<tbody id="dataList">
        </tbody>
        
    </table>
    </div>
</div>
