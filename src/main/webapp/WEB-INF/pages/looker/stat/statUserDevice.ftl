<script src="${systemProp.staticServerUrl}/js/devCommon.js"></script>
<script src="${systemProp.staticServerUrl}/js/systemProp.js"></script>
<script src="${systemProp.staticServerUrl}/js/form2object.js"></script>
<script src="${systemProp.staticServerUrl}/js/ajaxfileupload-multi.js"></script>
<script src="${systemProp.staticServerUrl}/v3/js/jquery.sampleFancyBoxAdmin.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/jscharts.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/datepicker.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/dateUtil.js"></script>
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/statF.js"></script>
<script src="${systemProp.staticServerUrl}/look/js/statUserDevice.js"></script>

<div class="body" menu="stat" submenu="statud">
      <div class="conLeftMiddleRight" style="min-height:600px;">
    <div class="conB">
		<h2>过滤条件</h2>
		<fieldset>
        	<div>
            	<em>开始日期</em><em><input type="text" class="input g100" id="startDate"/></em>
            	<em>结束日期</em><em><input type="text" class="input g100" id="endDate"/></em>
            	<em>操作系统</em>
                	<em>
                	  <select id="os">
                	      <option value="">全部</option>
		            	  <option value="android">androiod</option>
		            	  <option value="ios">ios</option>
	                  </select>
	                </em>
            	<em><a href="#" class="btnBS" id="changeChart">确定</a></em>
            </div>
        	<hr />
        </fieldset>
   </div>
   <div class="conB">
		<h2 class="tCenter">用户访问设备报表</h2>
		<div id="mychart" class="conBody" style="width:790px;height:500px;padding:20px;">
	    </div>
	</div>
        
        
    <table width="100%" class="table JQtableBg table1">
		<thead>
          <tr>
            <td>系统(版本)</td>
            <td>比例</td>
            <td>数量</td>
          </tr>
        </thead>
		<tbody id="dataTbody">
        </tbody>
        
    </table>
    </div>
</div>
