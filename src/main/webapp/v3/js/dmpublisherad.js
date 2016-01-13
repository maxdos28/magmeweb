;$(function($){
	var nowDate = new Date(),
		$sDate = $("#startDate"),
		$eDate = $("#endDate");
	addDatePicker($sDate,nowDate);
	addDatePicker($eDate,nowDate);
	
	var callBack = function(rs){
		var code = rs.code;
		if(code != 200) return;
		if(!rs.data  || !rs.data.list || rs.data.list.length == 0){
			alert('没有数据');
			return;
		}
		drawChart(rs.data);
	}
	var ajaxUrl = SystemProp.appServerUrl+"/stat/dm-publisherad!queryPublisherAdJson.action";
	
	//drawChart------------------------------------
	function drawChart(data){
		var arr=[];
		var ad_tp=data.ad_tp;
		var list = data.list;
		
		for(var i=0;i<list.length;i++){
			var adData = list[i],
			strDate = dateFormat(adData.dataDate);
			
			if(ad_tp==4 || ad_tp==1){
				arr.push([strDate,adData.viewNumber]);
			}else{
				arr.push([strDate,adData.clickNumber]);
			}
		}
		
//		var mydataArray1 = [],
//			mydataArray2 = [],
//			mydataArray3 = [],
//			mydataArray4 = [];
//			
//		var rightAd = data.rightAdList,
//			embedAd = data.embedAdList,
//			insertAd = data.insertAdList;
//		for(var i=0;i<rightAd.length;i++){
//			var adData = rightAd[i],
//				strDate = dateFormat(adData.dataDate);
//			mydataArray1.push([strDate,adData.clickNumber]);
//		}
//		for(var j=0;j<insertAd.length;j++){
//			var adData = insertAd[j],
//				strDate = dateFormat(adData.dataDate);
//			mydataArray2.push([strDate,adData.viewNumber]);
//		}
//		for(var n=0;n<embedAd.length;n++){
//			var adData = embedAd[n],
//				strDate = dateFormat(adData.dataDate);
//			mydataArray3.push([strDate,adData.clickNumber]);
//			mydataArray4.push([strDate,adData.viewNumber]);
//		}	
//		var rightAdData = mydataArray1,
//			insertAdData = mydataArray2,
//			embedAdCD = mydataArray3,
//			embedAdVD = mydataArray4;
		
		var myChart = new JSChart('mychart', 'line','86321583T0067135F');
		if(arr.length>0){
			myChart.setDataArray(arr,'red');
		}
//		if(rightAdData.length>0){
//			myChart.setDataArray(rightAdData,'blue');
//		}
//		if(insertAdData.length>0){
//			myChart.setDataArray(insertAdData,'green');
//		}
//		if(embedAdCD.length>0){
//			myChart.setDataArray(embedAdCD,'red');
//		}
//		if(embedAdVD.length>0){
//			myChart.setDataArray(embedAdVD,'yellow');	
//		}
		myChart.setTitle('出版商广告明细');
		myChart.setTitleColor('#8E8E8E');
		myChart.setTitleFontSize(11);
		myChart.setAxisNameX('时间');
		myChart.setAxisNameY('数目');
		myChart.setAxisColor('#C4C4C4');
		myChart.setAxisValuesColor('#343434');
		myChart.setAxisPaddingLeft(65);
		myChart.setAxisPaddingRight(120);
		myChart.setAxisPaddingTop(80);
		myChart.setAxisPaddingBottom(90);
		myChart.setAxisValuesAngle(60);
		myChart.setAxisValuesNumberX(data.length);
		myChart.setGraphExtend(true);
//		myChart.setLineColor('#A4D314', 'green');
		myChart.setLineColor('#00fff2', 'red');
//		myChart.setLineColor('#e1ff00', 'yellow');
		myChart.setLineWidth(3);
		myChart.setSize(780, 380);
		for(var i=0;i<arr.length;i++){
			myChart.setTooltip([i]);
		}
		myChart.setFlagColor('#9D16FC');
		myChart.setFlagRadius(3);
		myChart.setLegendShow(true);
		myChart.setLegendPosition(650, 35);
		if(ad_tp==1)
			myChart.setLegendForLine('red', '插页广告展示数目');
		else if(ad_tp==2)
			myChart.setLegendForLine('red', '嵌入广告点击数目');
		else if(ad_tp==3)
			myChart.setLegendForLine('red', '侧边栏广告点击数目');
		else if(ad_tp==4)
			myChart.setLegendForLine('red', '嵌入广告展示数目');
			
//		myChart.setLegendForLine('blue', '侧边栏广告点击数目');
//		myChart.setLegendForLine('green', '插页广告展示数目');
//		myChart.setLegendForLine('red', '嵌入广告点击数目');
//		myChart.setLegendForLine('yellow', '嵌入广告展示数目');
		myChart.draw();
	}
	//default 当前时间向前推7天
	var startDate = new Date();
	startDate.setDate(startDate.getDate()-8);
	
	var strsd = strDate(startDate),
		stred = strDate(nowDate),
		ad_type=3;
		firstCondition = {"startDate":strsd,"endDate":stred,"ad_type":ad_type};
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
		condition.publisherId = $("#publisherId").val();
		condition.ad_type=$("#ad_type").val();
		
		if(judgeDate(sDate,eDate)) return;
		
		showChart(condition,ajaxUrl,callBack);
	});
});