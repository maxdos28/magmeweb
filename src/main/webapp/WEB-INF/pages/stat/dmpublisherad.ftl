<body>
	<div class="conB" menuid="4">
		<h2>过滤条件</h2>
		<fieldset>
        	<div>
            	<em>开始日期</em><em><input type="text" class="input g100" id="startDate"/></em>
            	<em>结束日期</em><em><input type="text" class="input g100" id="endDate"/></em>
            	<em>策略</em>
            	<em><select id="ad_type">
            		<option value='1' >插页广告展示数目</option>
            		<option value='2'>嵌入广告点击数目</option>
            		<option selected value='3'>侧边栏广告点击数目</option>
            		<option value='4'>嵌入广告展示数目</option>
            	</select></em>
            </div>
            <hr />
        	<div>
            	<em>出版商id</em>
            	<em><input type="text" class="input g100" id="publisherId"/></em>
            	<em><a href="#" class="btnBS" id="changeChart">确定</a></em>
            </div>
        	<hr />
        </fieldset>
   </div>
   <div class="conB">
		<h2 class="tCenter">出版商广告明细报表</h2>
		<div id="mychart" class="conBody" style="width:790px;height:400px;padding:20px;">
	    </div>
	</div>
        
     
    <script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/dmpublisherad.js"></script>
</body>