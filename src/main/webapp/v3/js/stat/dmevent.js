;$(function($){
	var nowDate = new Date(),
		$sDate = $("#startDate"),
		$eDate = $("#endDate");
	addDatePicker($sDate,nowDate);
	addDatePicker($eDate,nowDate);
	
	var callBack = function(rs){
		var code = rs.code;
		if(code != 200) return;
		if(!rs.data || !rs.data.dmEventlList || rs.data.dmEventlList.length == 0){
			alert('没有数据');
			return;
		}
		drawChart(rs.data.dmEventlList);
	};
	var ajaxUrl = SystemProp.appServerUrl+"/stat/dm-event!queryEventJson.action";
	
	//drawChart------------------------------------
	function drawChart(data){
		if (!$.isArray(data)) return;
		
		var mydataArray = [];
		var mydataArray2 = [];
		for(var i=0;i<data.length;i++){
			var adData = data[i];
			mydataArray.push([adData.eventCategoryName,adData.uv]);
		}
		var myData = mydataArray;
		var myChart = new JSChart('mychart', 'bar','86321583T0067135F');
		
		myChart.setDataArray(myData);
		myChart.setTitle('事件排行报表');
		myChart.setTitleColor('#8E8E8E');
		myChart.setAxisNameX('事件类别');
		myChart.setAxisNameY('数目');
		myChart.setAxisValuesAngle(60);
		myChart.setAxisNameFontSize(11);
		myChart.setAxisNameColor('#C4C4C4');
		myChart.setAxisValuesColor('#343434');
		myChart.setAxisColor('#C4C4C4');
		myChart.setAxisWidth(1);
		myChart.setBarValuesColor('#2F6D99');
		myChart.setAxisPaddingTop(80);
		myChart.setAxisPaddingBottom(70);
		myChart.setAxisPaddingLeft(80);
		myChart.setTitleFontSize(11);
		myChart.setBarColor('#2D6B96', 1);
		myChart.setBarBorderWidth(0);
		myChart.setBarSpacingRatio(50);
		myChart.setBarOpacity(0.9);
		myChart.setFlagRadius(3);
		myChart.setTooltipPosition('nw');
		myChart.setTooltipOffset(3);
		myChart.setLegendShow(true);
		myChart.setLegendPosition(700, 35);
		myChart.setLegendForBar(1, '事件');
		myChart.setSize(780, 380);
		myChart.setGridColor('#c2c2c2');
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