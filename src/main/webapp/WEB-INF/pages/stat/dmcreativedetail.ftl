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
            	<em><a class="btnBS" id="exportData" href="javascript:void(0)">导出excel</a></em>
            </div>
        	<hr />
        </fieldset>
   </div>
    <table width="100%" class="table JQtableBg table1">
		<thead>
          <tr>
            <td>作品id</td>
            <td>作品标题</td>
            <td>点击次数</td>
            <td>编辑姓名</td>
            <td>出自杂志</td>
          </tr>
        </thead>
		<tbody id="eventdetailList">
        </tbody>
        
    </table>
    <script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/stat/dmcreativedetail.js"></script>
</body>