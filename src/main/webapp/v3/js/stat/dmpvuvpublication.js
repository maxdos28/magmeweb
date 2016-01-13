;$(function($){
	var nowDate = new Date(),
		$sDate = $("#startDate"),
		$eDate = $("#endDate"),
		$publicationId=$("#publicationId").val();
	addDatePicker($sDate,nowDate);
	addDatePicker($eDate,nowDate);
	
	var callBack = function(rs){
		var code = rs.code;
		if(code != 200) return;
		if(!rs.data || !rs.data.datamapList || rs.data.datamapList.length == 0){
			alert('没有数据');
			return;
		}
		drawChart(rs.data.datamapList);
	};
	var ajaxUrl = SystemProp.appServerUrl + "/publish/dm-pv-uv-publication!pubJson.action";
	
	//drawChart------------------------------------
	function drawChart(data){
		if (!$.isArray(data)) return;
		
		var mydataArray1 = [];
//		var	mydataArray2 = [];
		for(var i=0;i<data.length;i++){
			var item = data[i];
			mydataArray1.push([item.hour,item.pv]);
//			mydataArray2.push([item.hour,item.uv]);
		}
		var myData1 = mydataArray1;
//		var	myData2 = mydataArray2;
		var myChart = new JSChart('mychart', 'line','86321583T0067135F');
		for(var i=0;i<data.length;i++){
			myChart.setTooltip([i]);
		}
		myChart.setDataArray(myData1,"blue");
//		myChart.setDataArray(myData2,'green');
		myChart.setTitle('阅读时段访问分析');
		myChart.setTitleColor('#8E8E8E');
		myChart.setTitleFontSize(11);
		myChart.setAxisNameX('时间');
		myChart.setAxisNameY('数目');
		myChart.setAxisColor('#C4C4C4');
		myChart.setAxisValuesColor('#343434');
		myChart.setAxisPaddingLeft(65);
		myChart.setAxisPaddingRight(120);
		myChart.setAxisPaddingTop(80);
		myChart.setAxisPaddingBottom(90);
		myChart.setAxisValuesAngle(60);
		myChart.setAxisValuesNumberX(data.length);
		myChart.setGraphExtend(true);
		myChart.setLineColor('#A4D314', 'blue');
		myChart.setLineWidth(3);
		myChart.setSize(780, 380);
		myChart.setFlagColor('#9D16FC');
		myChart.setFlagRadius(3);
		myChart.setLegendShow(true);
		myChart.setLegendPosition(700, 35);
		myChart.setLegendForLine('blue', '点击数目');
//		myChart.setLegendForLine('green', '展示数目');
		myChart.draw();
	}
	//default 当前时间向前推7天
	var startDate = new Date();
	startDate.setDate(startDate.getDate()-8);
	
	var strsd = strDate(startDate),
		stred = strDate(nowDate);
		firstCondition = {"startDate" : strsd, "endDate" : stred, "publicationId" : $publicationId};
	$eDate.val(stred);
	$sDate.val(strsd);
	
	showChart(firstCondition,ajaxUrl,callBack);
	
	$("#changeChart").unbind('click').click(function(e){
		e.preventDefault();
		var condition = {},
			sDate = strToDate($sDate.val()),
			eDate = strToDate($eDate.val()),
			publicationId = $("#publicationId").val();
		condition.startDate = $sDate.val();
		condition.endDate = $eDate.val();
		condition.publicationId = publicationId;
		
		if(judgeDate(sDate,eDate)) return;
		
		showChart(condition,ajaxUrl,callBack);
	});
});
