<body>
	<div class="conB">
		<h2>过滤条件</h2>
		<fieldset>
        	<div>
            	<em>开始日期</em><em><input type="text" class="input g100" id="startDate"/></em>
            	<em>结束日期</em><em><input type="text" class="input g100" id="endDate"/></em>
            </div>
        	<hr />
        	<div>
            	<em><a href="#" class="btnBS" id="changeChart">确定</a></em>
            </div>
        	<hr />
        </fieldset>
   </div>
   <div class="conB">
		<h2 class="tCenter">终端类型排行报表</h2>
		<div id="mychart" class="conBody" style="width:790px;height:400px;padding:20px;">
	    </div>
	</div>
        
        
    <table width="100%" class="table JQtableBg table1">
		<thead>
          <tr>
            <td>终端名称</td>
            <td>比例</td>
            <td>uv</td>
          </tr>
        </thead>
		<tbody id="deviceList">
        </tbody>
        
    </table>
    <script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/dmvisitdevice.js"></script>
</body>