<body>
<script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/dmreferpublication.js"></script>
<!--body-->
<div class="conB">
	<h2>过滤条件</h2>
	<fieldset>
    	<div>
        	<em>开始日期</em><em><input type="text" class="input g100" id="startDate"/></em>
        	<em>结束日期</em><em><input type="text" class="input g100" id="endDate"/></em>
            <em>选择杂志</em><em><select id="publicationId">
                                      <#if pubList?? && (pubList?size>0)>
					            		  <#list pubList as pub>
					            		   	<option value="${pub.id}" <#if publicationId?? && publicationId=pub.id> selected </#if>  > ${pub.name}</option>
					            		  </#list>
					            		</#if>
                                 </select>
                             </em>
        	<em><a href="#" class="btnBS" id="changeChart">确定</a></em>
        </div>
        <hr />
	     <em><a href="javascript:void(0)" name="express" expressDate="1" id="load_0" class="btnWS">昨天</a></em>
		 <em><a href="javascript:void(0)" name="express" expressDate="2" id="load_1" class="btnWS">上周</a></em>
		 <em><a href="javascript:void(0)" name="express" expressDate="3" id="load_2" class="btnWS">前七天</a></em>
		 <em><a href="javascript:void(0)" name="express" expressDate="5" id="load_3" class="btnWS">上月</a></em>
        <hr />
    </fieldset>
</div>
<div class="conB">
	<div id="mychart" class="conBody" style="width:790px;height:400px;padding:20px;">
    </div>
</div>
    
    
<table width="100%" class="table JQtableBg table1">
	<thead>
      <tr>
        <td>来路域名</td>
        <td>所占比例</td>
        <td>pv</td>
        <td>查看按钮</td>
      </tr>
    </thead>
	<tbody id="dataList">
    </tbody>
    
</table>
</body>
