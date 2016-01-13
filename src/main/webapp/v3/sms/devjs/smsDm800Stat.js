$(function($){
	var smsProjectId=$("#smsProjectId").val();
	
	var ajaxUrl = SystemProp.kaiJieAppUrl+"/sms/sms-dm-ezz-stat!statJson.action?smsProjectId="+smsProjectId;
	var callBack = function(rs){
		var code = rs.code;
		drawChart(rs.data,code);
	};
	function showChart(ajaxUrl,callBack){
		$("#mychart").empty();
		$.ajax({
			url: ajaxUrl,
			type: "GET",
			success: callBack
		});
	}
	function drawChart(data,code){
		if(code!=200){
			alert("无数据");
			return;
		}
		$tbody = $("#smsDmEzzList");
		$tbody.empty();
		var stat = data.smsDm800Stat;
		var statDetailList = data.smsDm800DetailList
		var chartArray = [];
		var sumPercent=stat.totalNum;
		var telPercent=stat.callNum;
		
		$.each(statDetailList,function(k,detail){
			var $tr = $("<tr></tr>").appendTo($tbody);
			$("<td>"+(detail.telNum||"")+"</td>").appendTo($tr);
			$("<td>"+(detail.userProvince||"")+"</td>").appendTo($tr);
		});
		
		
		chartArray.push(['总数',sumPercent]);
		chartArray.push(['拨打电话的用户数',telPercent]);
		var colors = ['#0673B8', '#0091F1'];
		var myChart = new JSChart('mychart', 'pie');
		myChart.setDataArray(chartArray);
		myChart.colorizePie(colors);
		myChart.setTitle("电话拨打报表");
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
	
	showChart(ajaxUrl,callBack);
});
