$(function($){
	var smsProjectId=$("#smsProjectId").val();
	
	var ajaxUrl = SystemProp.kaiJieAppUrl+"/sms/sms-dm-click-time-dis!clickTimeJson.action?smsProjectId="+smsProjectId;
	var callBack = function(rs){
		var code = rs.code;
		drawChart(rs.data.smsDmClickTimeDis);
	};
	function showChart(ajaxUrl,callBack){
		$("#mychart").empty();
		$.ajax({
			url: ajaxUrl,
			type: "GET",
			success: callBack
		});
	}
	function drawChart(data){
		if (!$.isArray(data)) return;
		
		var mydataArray1 = [];
		var mydataArray2 = [];
		for(var i=0;i<data.length;i++){
			var adData = data[i];
			mydataArray1.push([adData.visitTime+":00",adData.uv]);
		}
		
		var myData1 = mydataArray1;
		var myChart = new JSChart('mychart', 'line','86321583T0067135F');
		myChart.setDataArray(myData1,"blue");
		myChart.setTitle('用户推广链接点击事件分布报表');
		myChart.setTitleColor('#8E8E8E');
		myChart.setTitleFontSize(11);
		myChart.setAxisNameX('点击人数');
		myChart.setAxisNameY('访问时间');
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
		myChart.setLegendForLine('blue', 'pv');
		myChart.draw();
	}
	
	showChart(ajaxUrl,callBack);
});
