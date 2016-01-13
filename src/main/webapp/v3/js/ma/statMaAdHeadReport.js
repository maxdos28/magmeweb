;$(function($){
	
	var callBack = function(rs){
		var code = rs.code;
		if(code != 200) return;
		if(!rs.data || !rs.data.resultList || rs.data.resultList.length == 0){
			alert('没有数据');
			return;
		}
		drawChart(rs.data.resultList);
	};
	var ajaxUrl = SystemProp.appServerUrl+"/ma/ad-trade-report!searchAdTradeReport.action";
	
	//drawChart------------------------------------
	function drawChart(data){
		if (!$.isArray(data)) return;
		
		var mydataArray = [];
		for(var i=0;i<data.length;i++){
			var adData = data[i];
			mydataArray.push([trimDate(adData.pd),adData.click,adData.impressions]);
		}
		
		var myData = mydataArray;
		var myChart = new JSChart('mychart', 'bar','86321583T0067135F');
		
		myChart.setDataArray(myData);
		myChart.setTitle('展示和点击');
		myChart.setTitleColor('#8E8E8E');
		myChart.setAxisNameX('日期');
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
		myChart.setLegendForBar(1, '展示');
		myChart.setLegendForBar(2, '点击');
		myChart.setSize(780, 380);
		myChart.setGridColor('#c2c2c2');
		myChart.draw();

	}
	adHeadId = $("#adHeadId").val();
	firstCondition = {"adHeadId":adHeadId};
	
	showChart(firstCondition,ajaxUrl,callBack);
});