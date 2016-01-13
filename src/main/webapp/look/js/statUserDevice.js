;$(function($){
	var nowDate = new Date(),
		$sDate = $("#startDate"),
		$eDate = $("#endDate");
	addDatePicker($sDate,nowDate);
	addDatePicker($eDate,nowDate);
	
	var callBack = function(rs){
		var code = rs.code;
		if(code != 200) return;
		if(!rs.data || !rs.data.total|| !rs.data.resultList || rs.data.resultList.length == 0 ){
			$tbody = $("#dataTbody");
			$tbody.empty();
			alert('没有数据');
			return;
		}
		drawChart(rs.data);
	};
	var ajaxUrl = SystemProp.appServerUrl+"/look/look-stat-user-device!searchUserDeviceJson.action";
	
	//drawChart------------------------------------
	function drawChart(data){
		$tbody = $("#dataTbody");
		$tbody.empty();
		var chartArray = [],
			listData = data.resultList;
		var total = data.total;
		if(listData)
		{
			$.each(listData,function(e,r){
				chartArray.push([r.os,r.percent]);
			});
		}
		var colors = ['#0673B8', '#0091F1'];
		var myChart = new JSChart('mychart', 'pie');
		myChart.setDataArray(chartArray);
		if(!$("#os").val()||$("#os").val().length==0)
			myChart.colorizePie(colors);
		myChart.setTitle("用户访问设备报表（单位%）");
		myChart.setTitleColor('#8E8E8E');
		myChart.setTitleFontSize(12);
		myChart.setTextPaddingTop(20);
		myChart.setPieUnitsColor('#8F8F8F');
		myChart.setPieValuesColor('#000');
		myChart.setSize(700, 400);
		myChart.setPiePosition(350, 220);
		myChart.setPieRadius(140);
		myChart.draw();
		tbList(listData);
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
		condition.os = $("#os").val();
		
		if(judgeDate(sDate,eDate)) return;
		
		showChart(condition,ajaxUrl,callBack);
	});
	function tbList(data){
		if (!$.isArray(data)) return;
		var chartArray = [],
			ot = 0,
		$tbody = $("#dataTbody");
		$tbody.empty();
		
		for(var i=0;i<data.length;i++){
			var referData = data[i];
			
			var $tr = $("<tr></tr>").appendTo($tbody);
			$("<td>"+(referData.os)+"</td>").appendTo($tr);
			$("<td>"+(referData.percent)+"%</td>").appendTo($tr);
			$("<td>"+(referData.num)+"</td>").appendTo($tr);
		}
	}
});