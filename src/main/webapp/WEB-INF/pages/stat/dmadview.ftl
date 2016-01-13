<body>
	<div class="conB" menuid="2">
		<h2>过滤条件</h2>
		<fieldset>
        	<div>
            	<em>开始日期</em><em><input type="text" class="input g100" id="startDate"/></em>
            	<em>结束日期</em><em><input type="text" class="input g100" id="endDate"/></em>
            </div>
        	<hr />
        	<div>
            	<em>选择广告</em>
            	<em><select id="adId">
            		<option value=''>所有广告</option>
            		<#if dmAdviewList?? && (dmAdviewList?size>0)>
            		  <#list dmAdviewList as dmAdview>
            		   	<option value="${dmAdview.adId}" <#if adId?? && adId=dmAdview.adId> selected </#if>  > ${dmAdview.adTitle}</option>
            		   </#list>
            		</#if>
            	</select></em>
            	<em><a href="#" class="btnBS" id="changeChart">确定</a></em>
            </div>
        	<hr />
        </fieldset>
   </div>
   <div class="conB">
		<h2 class="tCenter">插页广告展示排行报表</h2>
		<div id="mychart" class="conBody" style="width:790px;height:350px;padding:20px;">
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
       
    <script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/dmadview.js"></script>
</body>