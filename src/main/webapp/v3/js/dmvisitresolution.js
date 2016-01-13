;$(function($){
	var nowDate = new Date(),
		$sDate = $("#startDate"),
		$eDate = $("#endDate");
	addDatePicker($sDate,nowDate);
	addDatePicker($eDate,nowDate);
	
	var callBack = function(rs){
		var code = rs.code;
		if(code != 200) return;
		if(!rs.data || !rs.data.visitResolutionList || rs.data.visitResolutionList.length == 0 || rs.data.totalNum*1 == 0){
			alert('没有数据');
			return;
		}
		drawChart(rs.data.visitResolutionList,rs.data.totalNum);
	};
	var ajaxUrl = SystemProp.appServerUrl+"/stat/dm-visit-resolution!queryVisitResolutionJson.action";
	
	//drawChart------------------------------------
	function drawChart(data,num){
		if (!$.isArray(data)) return;
		var chartArray = [],
			ot = 0,
			$tbody = $("#resolutionList");
		$tbody.empty();
		for(var i=0;i<data.length;i++){
			var listData = data[i];
			var scale = (listData.uv*100/num) |0 ;
			if(i<5){
				ot += scale;
				chartArray.push([listData.resolution,scale]);
			}else if(i==5){
				chartArray.push(["其他",(100 - ot)]);
			}
			var $tr = $("<tr></tr>").appendTo($tbody);
			$("<td>"+(listData.resolution||"")+"</td>").appendTo($tr);
			$("<td>"+(listData.uv*100/num).toFixed(2)+"%</td>").appendTo($tr);
			$("<td>"+(listData.uv||"0")+"</td>").appendTo($tr);
			
		}
		var colors = ['#0673B8', '#0091F1', '#F85900', '#1CC2E6', '#C32121', '#41B2FA'];
		var myChart = new JSChart('mychart', 'pie');
		myChart.setDataArray(chartArray);
		myChart.colorizePie(colors);
		myChart.setTitle("屏幕分辨率分布（单位%）");
		myChart.setTitleColor('#8E8E8E');
		myChart.setTitleFontSize(12);
		myChart.setTextPaddingTop(20);
		myChart.setPieUnitsColor('#8F8F8F');
		myChart.setPieValuesColor('#000');
		myChart.setSize(700, 400);
		myChart.setPiePosition(350, 220);
		myChart.setPieRadius(140);
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
		
		if(judgeDate(sDate,eDate)) return;
		
		showChart(condition,ajaxUrl,callBack);
	});
});