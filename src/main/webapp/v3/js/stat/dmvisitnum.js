;$(function($){
	var nowDate = new Date(),
		$sDate = $("#startDate"),
		$eDate = $("#endDate");
	addDatePicker($sDate,nowDate);
	addDatePicker($eDate,nowDate);
	
	var callBack = function(rs){
		var code = rs.code;
		if(code != 200) return;
		if(!rs.data || !rs.data.dmVisitNumList || rs.data.dmVisitNumList.length == 0 || rs.data.totalNum*1 == 0){
			alert('没有数据');
			return;
		}
		drawChart(rs.data.dmVisitNumList,rs.data.totalNum);
	};
	var ajaxUrl = SystemProp.appServerUrl+"/stat/dm-visit-num!queryVisitNumJson.action";
	
	//drawChart------------------------------------
	function drawChart(data,num){
		if (!$.isArray(data)) return;
		$tbody = $("#visitnumList");
		$tbody.empty();
		var chartArray = [],
			listData = data[0];
		
		var newvisitpercent=(listData.newVisit*100/num).toFixed(2)*1;
		var oldvisitpercent=(listData.oldVisit*100/num).toFixed(2)*1;
		
		var $tr = $("<tr></tr>").appendTo($tbody);
		$("<td>新用户</td>").appendTo($tr);
		$("<td>"+newvisitpercent+"%</td>").appendTo($tr);
		$("<td>"+listData.newVisit+"</td>").appendTo($tr);
		
		var $tr2 = $("<tr></tr>").appendTo($tbody);
		$("<td>老用户</td>").appendTo($tr2);
		$("<td>"+oldvisitpercent+"%</td>").appendTo($tr2);
		$("<td>"+listData.oldVisit+"</td>").appendTo($tr2);
		
		chartArray.push(['新用户',newvisitpercent]);
		chartArray.push(['老用户',oldvisitpercent]);
		var colors = ['#0673B8', '#0091F1'];
		var myChart = new JSChart('mychart', 'pie');
		myChart.setDataArray(chartArray);
		myChart.colorizePie(colors);
		myChart.setTitle("新老用户数量（单位%）");
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