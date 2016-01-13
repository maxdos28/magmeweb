;$(function($){
	var nowDate = new Date(),
		$sDate = $("#startDate"),
		$eDate = $("#endDate");
	addDatePicker($sDate,nowDate);
	addDatePicker($eDate,nowDate);


	var callBack = function(rs){
		var code = rs.code;
		if(rs.data.startDate){
			var startDatePt=rs.data.startDate.split("T")[0];
			$("#startDate").val(startDatePt);
		}
		
        if(rs.data.endDate){
        	var endDatePt=rs.data.endDate.split("T")[0];
        	$("#endDate").val(endDatePt);
		}
		if(code != 200) {
			alert('没有数据');
			return;
		}
		if(!rs.data || !rs.data.dmIssuePageList || rs.data.dmIssuePageList.length == 0){
			var $tbody = $("#dataList");
			alert('没有数据');
			return;
		}
		//totalpv
		var totalAvgRetention=0;
		if(rs.data.totalAvgRetention){
			totalAvgRetention=rs.data.totalAvgRetention;
			$("#totalAvgRetention").html("总平均阅读时长："+totalAvgRetention+" 秒/页");
		}
		
		drawChart(rs.data.dmIssuePageList);
	};
	var ajaxUrl = SystemProp.appServerUrl+"/publish/dm-issue-page!activeReadJson.action";
	
	//重写statF.js日期判断函数        ----------------------------------------
//	function judgeDate(sDate,eDate){
//		if(eDate < sDate){
//			alert('结束日期必须大于开始日期！');
//			return true;
//		}else if(eDate > (+new Date())){
//			alert('结束日期不能大于当前时间！');
//			return true;
//		}else if(eDate == sDate){
//			alert('不能选择一天的数据！');
//			return true;
//		}else{
//			var et=new Date(eDate);
//			var may=new Date('2012-05-01');
//			if(sDate<may || eDate<may){
//				alert("查询日期不能在2012-05-01之前");
//				return true;
//			}
//			et.setDate(et.getDate()-31);
//			if(strDate(new Date(eDate))==strDate(new Date())){
//				if(new Date(sDate)<et){
//					alert('请选择查结束日期前 31天统计！');
//					return true;
//				}
//			}else{
//				if(new Date(sDate)<=et){
//					alert('请选择查结束日期前31天统计！');
//					return true;
//				}
//			}
//		}
//		
//		return false;
//	}
	
	
//drawChart------------------------------------
	function drawChart(data){
		if (!$.isArray(data)) return;
		
		var mydataArray1 = [];
		for(var i=0;i<data.length;i++){
			var adData = data[i];
			mydataArray1.push([adData.separatorStr,adData.retention]);
		}
		
		var myData1 = mydataArray1;
		var myChart = new JSChart('mychart', 'line','86321583T0067135F');
		myChart.setDataArray(myData1,"blue");
		myChart.setTitle('期刊阅读活跃比例');
		myChart.setTitleColor('#8E8E8E');
		myChart.setTitleFontSize(11);
		myChart.setAxisNameX('页数百分比');
		myChart.setAxisNameY('阅读时间');
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
		myChart.setLegendForLine('blue', '阅读时间(秒)');
		myChart.draw();
	
	}
	//default 当前时间向前推7天
	var startDate = new Date();
	startDate.setDate(startDate.getDate()-8);
	
	var strsd = strDate(startDate),
		stred = strDate(nowDate),
		expressDate=$("#expressDate").val();
		firstCondition = {"startDate":strsd,"endDate":stred,"issueId":$("#issueId").val()};
	$eDate.val(stred);
	$sDate.val(strsd);
	showChart(firstCondition,ajaxUrl,callBack);
	
	$("#changeChart").unbind('click').click(function(e){
		e.preventDefault();
		var condition = {},
			sDate = strToDate($sDate.val()),
			eDate = strToDate($eDate.val()),
		    expressDate=$("#expressDate").val();
		condition.startDate = $sDate.val();
		condition.endDate = $eDate.val();
		condition.issueId=$("#issueId").val()
		condition.expressDate=expressDate;
		if(!expressDate || expressDate==""){
			if(judgeDate(sDate,eDate)) return;
		}
		showChart(condition,ajaxUrl,callBack);
	});
	
	
});