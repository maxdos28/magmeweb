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
    <table width="100%" class="table JQtableBg table1">
		<thead>
          <tr>
            <td>搜索词</td>
            <td>次数</td>
          </tr>
        </thead>
		<tbody id="searchList">
        </tbody>
        
    </table>
    <script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/stat/dmsearch.js"></script>
</body>