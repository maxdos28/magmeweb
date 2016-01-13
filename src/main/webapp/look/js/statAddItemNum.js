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
	var ajaxUrl = SystemProp.appServerUrl+"/look/look-stat-add-item-num!searchAddItemNumJson.action";
	
	//drawChart------------------------------------
	function drawChart(data){
		if (!$.isArray(data)) return;
		
		var mydataArray = [];
		for(var i=0;i<data.length;i++){
			var adData = data[i];
			var name = adData.itemName
			if(name.indexOf("T")>0)
				name = trimDate(adData.itemName);
			mydataArray.push([name,adData.addNum]);
		}
		
		var myData = mydataArray;
		var myChart = new JSChart('mychart', 'bar','86321583T0067135F');
		
		myChart.setDataArray(myData);
		myChart.setTitle('栏目收藏报表');
		myChart.setTitleColor('#8E8E8E');
		if(!$("#itemId").val()||$("#itemId").val()=="0")
			myChart.setAxisNameX('栏目');
		else
			myChart.setAxisNameX('日期');
		myChart.setAxisNameY('数目');
		myChart.setAxisValuesAngle(60);
		myChart.setAxisNameFontSize(11);
		myChart.setAxisNameColor('#C4C4C4');
		myChart.setAxisValuesColor('#343434');
		myChart.setAxisColor('#C4C4C4');
		myChart.setAxisWidth(1);
		myChart.setBarValuesColor('#2F6D99');
		myChart.setAxisPaddingTop(80);
		myChart.setAxisPaddingBottom(70);
		myChart.setAxisPaddingLeft(80);
		myChart.setTitleFontSize(11);
		myChart.setBarColor('#2D6B96', 1);
		myChart.setBarBorderWidth(0);
		myChart.setBarSpacingRatio(50);
		myChart.setBarOpacity(0.9);
		myChart.setFlagRadius(3);
		myChart.setTooltipPosition('nw');
		myChart.setTooltipOffset(3);
		myChart.setLegendShow(true);
		myChart.setLegendPosition(700, 35);
		myChart.setLegendForBar(1, '数量');
		myChart.setSize(780, 380);
		myChart.setGridColor('#c2c2c2');
		myChart.draw();

	}
	//default 当前时间向前推7天
	var startDate = new Date();
	startDate.setDate(startDate.getDate()-8);
	
	var strsd = strDate(startDate),
		stred = strDate(nowDate),
		itemId = $("#itemId").val();
		firstCondition = {"startDate":strsd,"endDate":stred,"itemId":itemId};
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
		condition.itemId = $("#itemId").val();
		
		if(judgeDate(sDate,eDate)) return;
		
		showChart(condition,ajaxUrl,callBack);
	});
});