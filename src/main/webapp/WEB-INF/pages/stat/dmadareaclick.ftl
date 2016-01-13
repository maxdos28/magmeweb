<body menuId="">
	<div class="conB">
		<h2>过滤条件</h2>
		<fieldset>
        	<div>
            	<em>开始日期</em><em><input type="text" class="input g100" id="startDate"/></em>
            	<em>结束日期</em><em><input type="text" class="input g100" id="endDate"/></em>
            	<em>
						<select id="order">
				     		<option value="1">pv升序</option>
				     		<option value="2">pv降序</option>
				     		<option value="3">uv升序</option>
				     		<option value="4">uv降序</option>
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
		<h2 class="tCenter">广告点击地区分布报表</h2>
		<div id="mychart" class="conBody" style="width:750px;height:670px;padding:40px;">
			<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"
	             id="myMap1" width="670px" height="670px"
	             codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab">
	         <param name="movie" value="${systemProp.staticServerUrl}/reader/CNMap.swf" />
	         <param name="quality" value="high" />
	         <param name="wmode" value="opaque" />
	         <param name="bgcolor" value="#869ca7" />
	         <param name="allowScriptAccess" value="always" />
	         <embed id="myMap2" src="${systemProp.staticServerUrl}/reader/CNMap.swf" wmode="opaque" quality="high" bgcolor="#869ca7"
	             width="670px" height="670px" name="ExternalInterfaceExample" align="middle"
	             play="true" loop="false" quality="high" allowScriptAccess="always"
	             type="application/x-shockwave-flash"
	             pluginspage="http://www.macromedia.com/go/getflashplayer">
	         </embed>
	     </object>
	    </div>
	</div>
	
	<table width="100%" class="table JQtableBg table1">
		<thead>
          <tr>
            <td>地区</td>
            <td>pv</td>
            <td>pv所占比例</td>
            <td>uv</td>
             <td>uv所占比例</td>
          </tr>
        </thead>
		<tbody id="dataList">
        </tbody>
    </table>
	
    <script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/dmadareaclick.js"></script>
</body>