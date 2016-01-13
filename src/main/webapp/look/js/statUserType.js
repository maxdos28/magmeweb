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
			$tbody = $("#visitnumList");
			$tbody.empty();
			alert('没有数据');
			return;
		}
		drawChart(rs.data);
	};
	var ajaxUrl = SystemProp.appServerUrl+"/look/look-stat-user-reg!searchUserTypeJson.action";
	
	//drawChart------------------------------------
	function drawChart(data){
		$tbody = $("#visitnumList");
		$tbody.empty();
		var chartArray = [],
			listData = data.resultList;
		var total = data.total;
		var sina = 0;
		if(data.sina)
			sina = data.sina;
		var qq = 0;
			qq = data.qq;
		var sinapercent = 0;
		var qqpercent = 0;
		if(sina)
			sinapercent=(sina*100/total).toFixed(2)*1;
		if(qq)
			qqpercent=(qq*100/total).toFixed(2)*1;
		
		var $tr = $("<tr></tr>").appendTo($tbody);
		$("<td>新浪微博</td>").appendTo($tr);
		$("<td>"+sinapercent+"%</td>").appendTo($tr);
		$("<td>"+sina+"</td>").appendTo($tr);
		
		var $tr2 = $("<tr></tr>").appendTo($tbody);
		$("<td>腾讯QQ</td>").appendTo($tr2);
		$("<td>"+qqpercent+"%</td>").appendTo($tr2);
		$("<td>"+qq+"</td>").appendTo($tr2);
		
		chartArray.push(['新浪微博',sinapercent]);
		chartArray.push(['腾讯QQ',qqpercent]);
		var colors = ['#0673B8', '#0091F1'];
		var myChart = new JSChart('mychart', 'pie');
		myChart.setDataArray(chartArray);
		myChart.colorizePie(colors);
		myChart.setTitle("注册用户分布（单位%）");
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