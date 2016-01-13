;$(function($){
	var nowDate = new Date(),
		$sDate = $("#startDate"),
		$eDate = $("#endDate");
	addDatePicker($sDate,nowDate);
	addDatePicker($eDate,nowDate);
	

	var callBack = function(rs){
		var code = rs.code;
		if(!rs.data || !rs.data.deviceAnalysis || rs.data.deviceAnalysis.length < 1){
			alert('没有数据');
			return;
		}
		drawChart(rs.data.deviceAnalysis);
	};
	var ajaxUrl = SystemProp.appServerUrl+"/newpublisherstat/dm-device-analysis!analysisJson.action";
	
	//drawChart------------------------------------
	function drawChart(data){
		if (!$.isArray(data)) return;
		
		var mydataArray1 = [];
		var mydataArray2 = [];
		for(var i=0;i<data.length;i++){
			var adData = data[i];
			mydataArray1.push([dateFormat(adData.dataDate),adData.pv]);
			//mydataArray2.push([dateFormat(adData.dataDate),adData.uv]);
		}
		
		var myData1 = mydataArray1;
		//var myData2 = mydataArray2;
		var myChart = new JSChart('mychart', 'line','86321583T0067135F');
		myChart.setDataArray(myData1,"blue");
//		myChart.setDataArray(myData2,'green');
		myChart.setTitle('终端用户分析');
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
		myChart.setLegendForLine('blue', 'pv');
//		myChart.setLegendForLine('green', 'uv');
		myChart.draw();
		
	}
	
	
	$("#changeChart").unbind('click').click(function(e){
		e.preventDefault();
		var condition = {},
			sDate = strToDate($sDate.val()),
			eDate = strToDate($eDate.val()),
		    expressDate=$("#expressDate").val();
		condition.startDate = $sDate.val();
		condition.endDate = $eDate.val();
		if($("#publicationId").length){
			condition.publicationId = $("#publicationId").val();
		} else if($("#publicationName").length){
			condition.publicationName = encodeURIComponent($("#publicationName").val());
		}
		condition.device=$("#device").val();
		if(!expressDate || expressDate==""){
			if(judgeDate(sDate,eDate)) return;
		}
		showChart(condition,ajaxUrl,callBack);
		topShow(condition);
		
	});
	
	function topShow(condition){
		$.ajax({
			url:SystemProp.appServerUrl+"/newpublisherstat/dm-device-analysis!topJson.action",
			data:condition,
			type:"POSt",
			async: true,
			success:function(rs){
				if(rs.code == 200){
					$("#insertSum").html(rs.data.insertSum);
					$("#deviceStartSum").html(rs.data.deviceStartSum);
					$("#newUser").html(rs.data.newUser);
					$("#totalDeviceStartSum").html(rs.data.totalDeviceStartSum);
				}
			}
		});
	}
	
	
	//default 当前时间向前推10天
	var startDate = new Date();
	startDate.setDate(startDate.getDate()-10);
	var endtDate = new Date();
	endtDate.setDate(endtDate.getDate()-1);
	var strsd = strDate(startDate),
		stred = strDate(endtDate),
		expressDate=$("#expressDate").val();
	$eDate.val(stred);
	$sDate.val(strsd);
	
	var firstCondition ={"startDate":strsd,"endDate":stred,"publicationId":$("#publicationId").val(),"device":$("#device").val() };
	showChart(firstCondition,ajaxUrl,callBack);
	topShow(firstCondition);
});