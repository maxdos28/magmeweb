<body>
	<div class="conB" menuid="3">
		<h2>过滤条件</h2>
		<fieldset>
        	<div>
            	<em>开始日期</em><em><input type="text" class="input g100" id="startDate"/></em>
            	<em>结束日期</em><em><input type="text" class="input g100" id="endDate"/></em>
            </div>
        	<hr />
        	<div>
            	<em>广告Id</em>
            	<em><input type="text" class="input g100" id="adId"/></em>
            	<em><a href="#" class="btnBS" id="changeChart">确定</a></em>
            </div>
        	<hr />
        </fieldset>
   </div>
   <div class="conB">
		<h2 class="tCenter">嵌入式广告展示排行报表</h2>
		<div id="mychart" class="conBody" style="width:790px;height:400px;padding:20px;">
	    </div>
	</div>
        
    <script type="text/javascript" src="${systemProp.staticServerUrl}/v3/js/dmadviewembed.js"></script>
</body>