;$(function($){
	var nowDate = new Date(),
		$sDate = $("#startDate"),
		$eDate = $("#endDate");
	addDatePicker($sDate,nowDate);
	addDatePicker($eDate,nowDate);
	
	var callBack = function(rs){
		var code = rs.code;
		if(code != 200) return;
		if(!rs.data || !rs.data.resultList || rs.data.resultList.length == 0){
			
			alert('没有数据');
			return;
		}
		drawChart(rs.data.resultList);
	};
	var ajaxUrl = SystemProp.appServerUrl+"/look/look-stat-user-install!searchUserInstallJson.action";
	
	//drawChart------------------------------------
	function drawChart(data){
		if (!$.isArray(data)) return;
		
		var mydataArray = [];
		for(var i=0;i<data.length;i++){
			var adData = data[i];
			if($("#type").val()=="day")
			{
				mydataArray.push([dateFormat(adData.installDate),adData.installNum]);
			}
			else
			{
				mydataArray.push([adData.hour+"",adData.installNum]);
			}
		}
		var myData = mydataArray;
		var myChart = new JSChart('mychart', 'line','86321583T0067135F');
		myChart.setDataArray(myData,"red");
		myChart.setTitle('用户安装量');
		myChart.setTitleColor('#8E8E8E');
		myChart.setAxisValuesAngle(60);
		myChart.setTitleFontSize(11);
		myChart.setAxisNameX('时间');
		myChart.setAxisNameY('数目');
		myChart.setAxisColor('#C4C4C4');
		myChart.setAxisValuesColor('#343434');
		myChart.setAxisPaddingLeft(80);
		myChart.setAxisPaddingRight(120);
		myChart.setAxisPaddingTop(80);
		myChart.setAxisPaddingBottom(70);
		myChart.setAxisValuesNumberX(mydataArray.length);
		myChart.setGraphExtend(true);
		myChart.setGridColor('#c2c2c2');
		myChart.setLineWidth(3);
		myChart.setLineColor('#9F0505',"blue");
		myChart.setLineColor('#A4D314', 'green');
		myChart.setLineColor('#e1ff00', 'red');
		myChart.setSize(780, 380);
		for(var i=0;i<mydataArray.length;i++){
			myChart.setTooltip([i]);
		}
		myChart.setFlagColor('#9D16FC');
		myChart.setFlagRadius(3);
		myChart.setLegendShow(true);
		myChart.setLegendPosition(700, 35);
		myChart.setLegendForLine('red', '安装量');
		myChart.draw();
		tbList(data);
		
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
	$("#type").unbind('change').change(function(e){
		if($("#type").val()=="day")
		{
			$("#endDate").parent().prev().show();
			$("#endDate").parent().show();
		}
		else
		{
			$("#endDate").parent().prev().hide();
			$("#endDate").parent().hide();
		}
	});
	$("#changeChart").unbind('click').click(function(e){
		e.preventDefault();
		var condition = {},
			sDate = strToDate($sDate.val()),
			eDate = strToDate($eDate.val());
		condition.startDate = $sDate.val();
		condition.endDate = $eDate.val();
		condition.type = $("#type").val();
		
		if(judgeDate(sDate,eDate)) return;
		
		showChart(condition,ajaxUrl,callBack);
	});
	function tbList(data){
		if (!$.isArray(data)) return;
		var chartArray = [],
			ot = 0,
			$tbody = $("#dataList");
		$tbody.empty();
		
		for(var i=0;i<data.length;i++){
			var referData = data[i];
			
			var $tr = $("<tr></tr>").appendTo($tbody);
			if($("#type").val()=="day")
			$("<td>"+(dateFormat(referData.installDate)||"")+"</td>").appendTo($tr);
			else
				$("<td>"+(referData.hour)+"</td>").appendTo($tr);
				
			$("<td>"+(referData.installNum||"")+"</td>").appendTo($tr);
		}
	}
});