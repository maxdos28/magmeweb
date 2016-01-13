;$(function($){
	var nowDate = new Date(),
		$sDate = $("#startDate"),
		$eDate = $("#endDate");
	addDatePicker($sDate,nowDate);
	addDatePicker($eDate,nowDate);
	
	var callBack = function(rs){
		var code = rs.code;
		if(code != 200) return;
		if(!rs.data || !rs.data.creativeList || rs.data.creativeList.length == 0){
			alert('没有数据');
			return;
		}
		drawChart(rs.data.creativeList,rs.data.category);
	};
	var ajaxUrl = SystemProp.appServerUrl+"/stat/dm-pv-uv-creative!detailLine.action";
	
	//drawChart------------------------------------
	function drawChart(data,c){
		if (!$.isArray(data)) return;
		
		var mydataArray = [];
		var mydataArray2 = [];
		for(var i=0;i<data.length;i++){
			var adData = data[i];
			var ttdate='';
			if(adData.date){
				ttdate = adData.date.substring(0,10);
			}
			mydataArray.push([ttdate,adData.uv]);
			mydataArray2.push([ttdate,adData.pv]);
		}
		
		var myData = mydataArray;
		var myData2=mydataArray2;
		var myChart = new JSChart('mychart', 'line','86321583T0067135F');
		myChart.setDataArray(myData,"blue");
		myChart.setDataArray(myData2,"green")
		myChart.setTitle(c+'频道趋势报表');
		myChart.setTitleColor('#8E8E8E');
		myChart.setTitleFontSize(11);
		myChart.setAxisNameX('时间');
		myChart.setAxisNameY('数量');
		myChart.setAxisColor('#C4C4C4');
		myChart.setAxisValuesColor('#343434');
		myChart.setAxisPaddingLeft(80);
		myChart.setAxisPaddingRight(120);
		myChart.setAxisPaddingTop(80);
		myChart.setAxisPaddingBottom(70);
		myChart.setAxisValuesNumberX(data.length);
		myChart.setGraphExtend(true);
		myChart.setGridColor('#c2c2c2');
		myChart.setLineWidth(3);
		myChart.setLineColor('#9F0505',"blue");
		myChart.setLineColor('#A4D314', 'green');
		myChart.setAxisValuesAngle(60);
		myChart.setSize(780, 380);
		for(var i=0;i<data.length;i++){
			myChart.setTooltip([i]);
		}
		myChart.setFlagColor('#9D16FC');
		myChart.setFlagRadius(3);
		myChart.setLegendShow(true);
		myChart.setLegendPosition(700, 35);
		myChart.setLegendForLine('blue', 'uv数量');
		myChart.setLegendForLine('green', 'pv人数');
		myChart.draw();

	}
	//default 当前时间向前推7天
	var startDate = new Date();
	startDate.setDate(startDate.getDate()-8);
	
	var strsd = strDate(startDate),
		stred = strDate(nowDate);
		firstCondition = {"startDate":strsd,"endDate":stred};
	$eDate.val(stred);
	$sDate.val(strsd);
	
	showChart(firstCondition,ajaxUrl,callBack);
	
	$("#changeChart").unbind('click').click(function(e){
		e.preventDefault();
		var condition = {},
			sDate = strToDate($sDate.val()),
			eDate = strToDate($eDate.val());
		condition.startDate = $sDate.val();
		condition.endDate = $eDate.val();
		condition.categoryId = $("#categoryId").val();
		
		if(judgeDate(sDate,eDate)) return;
		
		showChart(condition,ajaxUrl,callBack);
	});
});