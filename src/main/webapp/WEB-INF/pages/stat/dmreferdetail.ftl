<body>
   <div class="conB">
		<h2 class="tCenter">域名来源报表</h2>
		<div id="mychart" class="conBody" style="width:790px;height:400px;padding:20px;">
	    </div>
	</div>
    <script type="text/javascript">
    function getUrlValue(name){ 
		var r = location.search.substr(1).match(new RegExp("(^|&)"+ name +"=([^&]*)(&|$)")); 
		if (r!=null) return unescape(r[2]); return null;
	}
    $(function($){
    	var domain = decodeURIComponent(getUrlValue("domain"));
    	var startDate = decodeURIComponent(getUrlValue("startDate"));
    	var endDate = decodeURIComponent(getUrlValue("endDate"));
    	var type = getUrlValue("type");
    	switch(type){
    		case '1':
		    	$("a[naviMenuId='11']").addClass("current");
		    	break;
		    case '2':
		    	$("a[naviMenuId='12']").addClass("current");
		    	break;
    		case '3':
		    	$("a[naviMenuId='13']").addClass("current");
		    	break;	
    	}
    	
    	var callBack = function(rs){
			var code = rs.code;
			if(code != 200) return;
			if(!rs.data || !rs.data.dmReferList || rs.data.dmReferList.length == 0){
				alert('没有数据');
				return;
			}
			drawChart(rs.data.dmReferList);
		};
		var ajaxUrl = SystemProp.appServerUrl+"/stat/dm-refer!queryReferByDomainJson.action";
		
		//drawChart------------------------------------
		function drawChart(data){
			if (!$.isArray(data)) return;
			
			var mydataArray = [];
			for(var i=0;i<data.length;i++){
				var adData = data[i];
				mydataArray.push([dateFormat(adData.dataDate),adData.viewNumber]);
			}
			var myData = mydataArray;
			var myChart = new JSChart('mychart', 'line','86321583T0067135F');
			myChart.setDataArray(myData);
			myChart.setTitle('从'+startDate+'到'+endDate+',来源'+domain+'趋势图');
			myChart.setTitleColor('#8E8E8E');
			myChart.setTitleFontSize(11);
			myChart.setAxisNameX('时间');
			myChart.setAxisNameY('数目');
			myChart.setAxisColor('#C4C4C4');
			myChart.setAxisValuesColor('#343434');
			myChart.setAxisPaddingLeft(80);
			myChart.setAxisPaddingRight(120);
			myChart.setAxisPaddingTop(80);
			myChart.setAxisPaddingBottom(90);
			myChart.setAxisValuesAngle(60);
			myChart.setAxisValuesNumberX(data.length);
			myChart.setGraphExtend(true);
			myChart.setGridColor('#c2c2c2');
			myChart.setLineWidth(3);
			myChart.setLineColor('#9F0505');
			myChart.setSize(780, 380);
			myChart.draw();
		}
		
		showChart({'domain':domain,'startDate':startDate,'endDate':endDate,'type':type},ajaxUrl,callBack);
    	
    });
    
    </script>
</body>