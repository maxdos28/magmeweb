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
            	       <option value="pv" <#if showType?? && showType="pv"> selected </#if> >总pv</option>
            	       <option value="uv" <#if showType?? && showType="uv"> selected </#if>  >总uv</option>
            	       <option value="ipnum" <#if showType?? && showType="ipnum"> selected </#if>  >总独立ip</option>
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
		<h2 class="tCenter">日总PV、IP、UV、数量</h2>
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
	
    <script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/stat/dmpvoverview.js"></script>
</body>