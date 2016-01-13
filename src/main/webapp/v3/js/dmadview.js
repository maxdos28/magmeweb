;$(function($){
	var nowDate = new Date(),
		$sDate = $("#startDate"),
		$eDate = $("#endDate");
	addDatePicker($sDate,nowDate);
	addDatePicker($eDate,nowDate);
	
	var callBack = function(rs){
		var code = rs.code;
		if(code != 200) return;
		if(!rs.data || !rs.data.dmAdviewList || rs.data.dmAdviewList.length == 0){
			var $tbody = $("#dataList");
			$tbody.empty();
			alert('没有数据');
			return;
		}
		drawChart(rs.data.dmAdviewList);
	};
	var ajaxUrl = SystemProp.appServerUrl+"/stat/dm-adview!queryInsertAdJson.action";
	
	//drawChart------------------------------------
	function drawChart(data){
		if (!$.isArray(data)) return;
		
		var mydataArray = [];
		for(var i=0;i<data.length;i++){
			var adData = data[i];
			mydataArray.push([dateFormat(adData.dataDate),adData.viewNumber]);
		}
		var myData = mydataArray;
		var myChart = new JSChart('mychart', 'line','86321583T0067135F');
		myChart.setDataArray(myData);
		myChart.setTitle('插页广告浏览数目');
		myChart.setTitleColor('#8E8E8E');
		myChart.setTitleFontSize(11);
		myChart.setAxisNameX('时间');
		myChart.setAxisNameY('浏览数目');
		myChart.setAxisColor('#C4C4C4');
		myChart.setAxisValuesColor('#343434');
		myChart.setAxisPaddingLeft(80);
		myChart.setAxisPaddingRight(120);
		myChart.setAxisPaddingTop(80);
		myChart.setAxisPaddingBottom(90);
		myChart.setAxisValuesAngle(60);
		myChart.setAxisValuesNumberX(data.length);
		myChart.setGraphExtend(true);
		myChart.setGridColor('#c2c2c2');
		myChart.setLineWidth(3);
		myChart.setLineColor('#9F0505');
		myChart.setSize(780, 380);
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
	
	function tbList(data){
		if (!$.isArray(data)) return;
		var chartArray = [],
			ot = 0,
			$tbody = $("#dataList");
		$tbody.empty();
		
		for(var i=0;i<data.length;i++){
			var referData = data[i];
			
			var $tr = $("<tr></tr>").appendTo($tbody);
			$("<td>"+(referData.dataDate.substring(0,10)||"")+"</td>").appendTo($tr);
			$("<td>"+(referData.viewNumber||"")+"</td>").appendTo($tr);
		}
	}
});