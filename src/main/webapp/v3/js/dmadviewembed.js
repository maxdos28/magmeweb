;$(function($){
	var nowDate = new Date(),
		$sDate = $("#startDate"),
		$eDate = $("#endDate");
	addDatePicker($sDate,nowDate);
	addDatePicker($eDate,nowDate);
	
	var callBack = function(rs){
		var code = rs.code;
		if(code != 200) return;
		if(!rs.data || !rs.data.dmAdviewList || rs.data.dmAdviewList.length == 0){
			alert('没有数据');
			return;
		}
		drawChart(rs.data.dmAdviewList);
	}
	var ajaxUrl = SystemProp.appServerUrl+"/stat/dm-adview!queryEmbedAdJson.action";
	
	//drawChart------------------------------------
	function drawChart(data){
		if (!$.isArray(data)) return;
		
		var mydataArray1 = [],
			mydataArray2 = [];
		for(var i=0;i<data.length;i++){
			var adData = data[i],
				strDate = dateFormat(adData.dataDate);
			mydataArray1.push([strDate,adData.clickNumber]);
			mydataArray2.push([strDate,adData.viewNumber]);
		}
		var myData1 = mydataArray1,
			myData2 = mydataArray2;
		var myChart = new JSChart('mychart', 'line','86321583T0067135F');
		myChart.setDataArray(myData1,"blue");
		myChart.setDataArray(myData2,'green');
		myChart.setTitle('嵌入式广告点击/展示数目');
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
		myChart.setLineColor('#A4D314', 'green');
		myChart.setLineWidth(3);
		myChart.setSize(780, 380);
		for(var i=0;i<data.length;i++){
			myChart.setTooltip([i]);
		}
		myChart.setFlagColor('#9D16FC');
		myChart.setFlagRadius(3);
		myChart.setLegendShow(true);
		myChart.setLegendPosition(700, 35);
		myChart.setLegendForLine('blue', '点击数目');
		myChart.setLegendForLine('green', '展示数目');
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
		condition.adId = $("#adId").val();
		
		if(judgeDate(sDate,eDate)) return;
		
		showChart(condition,ajaxUrl,callBack);
	});
});