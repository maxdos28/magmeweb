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
            	       <option value="pv" <#if showType?? && showType="pv"> selected </#if> >广告观看数目</option>
            	       <option value="uv" <#if showType?? && showType="uv"> selected </#if>  >广告点击数目</option>
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
		<h2 class="tCenter">日广告点击</h2>
		<div id="mychart" class="conBody" style="width:790px;height:400px;padding:20px;">
	    </div>
	</div>
	
	<table width="100%" class="table JQtableBg table1">
		<thead>
          <tr>
            <td>时间</td>
            <td>数目</td>
          </tr>
        </thead>
		<tbody id="dataList">
        </tbody>
    </table>
	
    <script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/stat/dmadclick.js"></script>
</body>