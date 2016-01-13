;$(function($){
	var nowDate = new Date(),
		$sDate = $("#startDate"),
		$eDate = $("#endDate");
	addDatePicker($sDate,nowDate);
	addDatePicker($eDate,nowDate);
	
	var callBack = function(rs){
		var code = rs.code;
		if(code != 200) return;
		if(!rs.data || !rs.data.adClickList || rs.data.adClickList.length == 0){
			var $tbody = $("#dataList");
			$tbody.empty();
			alert('没有数据');
			return;
		}
		drawChart(rs.data.adClickList,rs.data.showType);
	};
	var ajaxUrl = SystemProp.appServerUrl+"/stat/dm-ad-click!queryAdClickJson.action";
	
	//drawChart------------------------------------
	function drawChart(data,showType){
		if (!$.isArray(data)) return;
		
		var mydataArray = [];
		for(var i=0;i<data.length;i++){
			var adData = data[i];
			if(!showType || showType=="" || showType=="pv"){
				mydataArray.push([dateFormat(adData.dataDate),adData.viewNumber]);
			}else if(showType=="uv"){
				mydataArray.push([dateFormat(adData.dataDate),adData.clickNumber]);
			}
		}
		var myData = mydataArray;
		var myChart = new JSChart('mychart', 'line','86321583T0067135F');
		myChart.setDataArray(myData,"blue");
		myChart.setTitle('日广告点击');
		myChart.setTitleColor('#8E8E8E');
		myChart.setTitleFontSize(11);
		myChart.setAxisNameX('时间');
		myChart.setAxisNameY('数目');
		myChart.setAxisColor('#C4C4C4');
		myChart.setAxisValuesColor('#343434');
		myChart.setAxisPaddingLeft(80);
		myChart.setAxisPaddingRight(120);
		myChart.setAxisPaddingTop(80);
		myChart.setAxisValuesAngle(60);
		myChart.setAxisPaddingBottom(70);
		myChart.setAxisValuesNumberX(data.length);
		myChart.setGraphExtend(true);
		myChart.setGridColor('#c2c2c2');
		myChart.setLineWidth(3);
		myChart.setLineColor('#9F0505',"blue");
		myChart.setSize(780, 380);
		for(var i=0;i<data.length;i++){
			myChart.setTooltip([i]);
		}
		myChart.setFlagColor('#9D16FC');
		myChart.setFlagRadius(3);
		myChart.setLegendShow(true);
		myChart.setLegendPosition(700, 35);
		if(!showType || showType=="" || showType=="pv"){
			myChart.setLegendForLine('blue', '广告观看数目');
		}else if(showType=="uv"){
			myChart.setLegendForLine('blue', '广告点击数目');
		}
		myChart.draw();
		tbList(data,showType);
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
		condition.showType = $("#showType").val();
		
		
		if(judgeDate(sDate,eDate)) return;
		
		showChart(condition,ajaxUrl,callBack);
	});
	
	function tbList(data,st){
		if (!$.isArray(data)) return;
		var chartArray = [],
			ot = 0,
			$tbody = $("#dataList");
		$tbody.empty();
		
		for(var i=0;i<data.length;i++){
			var referData = data[i];
			
			var $tr = $("<tr></tr>").appendTo($tbody);
			$("<td>"+(referData.dataDate.substring(0,10)||"")+"</td>").appendTo($tr);
			if(!st || st=="" || st=="pv"){
				$("<td>"+(referData.viewNumber||"")+"</td>").appendTo($tr);
			}else if(st=="uv"){
				$("<td>"+(referData.clickNumber||"")+"</td>").appendTo($tr);
			}
		}
	}
});