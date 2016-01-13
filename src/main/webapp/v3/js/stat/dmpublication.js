;$(function($){
	var nowDate = new Date(),
		$sDate = $("#startDate"),
		$eDate = $("#endDate");
	addDatePicker($sDate,nowDate);
	addDatePicker($eDate,nowDate);
	
	var callBack = function(rs){
		var code = rs.code;
		//清除排序
		$("#order").val("");
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
		if(!rs.data || !rs.data.dmPubList || rs.data.dmPubList.length <= 1){
			var $tbody = $("#dataList");
			$tbody.empty();
			alert('没有数据');
			return;
		}
		//totalpv
		var totalPv=0;
		if(rs.data.totalPv){
			totalPv=rs.data.totalPv;
		}
		
		if(rs.data.year){
			$("#year").val(rs.data.year);
		}
		if(rs.data.month){
			$("#month").val(rs.data.month);
		}
		
		$("#totalPv").html(totalPv);
		drawChart(rs.data.dmPubList);
	};
	var ajaxUrl = SystemProp.appServerUrl+"/publish/dm-publication!pubJson.action";
	
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
	
	/*$("a[name='express']").unbind("click").bind("click",function(){
		var expressDate=$(this).attr("expressDate");
		$("#changeChart").click();
	});*/
	
	$("#searchPv").unbind("click").bind("click",function(){
		
		var date=new Date();
		if($("#year").val()>date.getFullYear()){
			alert("不能选择未来的日期");
			return false;
		}
		if($("#year").val()==date.getFullYear() && $("#month").val()>date.getMonth()+1){
			alert("不能选择未来的日期");
			return false;
		}
		var pvCallBack=function(rs){
			if(rs.data.totalPv){
				$("#totalPv").html(rs.data.totalPv);
			}else{
				$("#totalPv").html("0");
			}
			if(rs.data.year){
				$("#year").val(rs.data.year);
			}
			if(rs.data.publicationId){
				$("#publicationIdPv").val(rs.data.publicationId);
			}
			
		};
		
		$.ajax({
			url:"http://www.magme.com/publish/dm-publication!queryTotalPvJson.action",
			data:{"year":$("#year").val(),"month":$("#month").val(),"publicationId":$("#publicationIdForPv").val()},
			type:"GET",
			success:pvCallBack
		});
		
	});
	
	//drawChart------------------------------------
	function drawChart(data){
		if (!$.isArray(data)) return;
		
		var mydataArray1 = [];
		//comment line for uv add by fredy in 2012-08-28
		var mydataArray2 = [];
		for(var i=0;i<data.length;i++){
			var adData = data[i];
			mydataArray1.push([dateFormat(adData.dataDate),adData.totalPv]);
			//mydataArray2.push([dateFormat(adData.dataDate),adData.totalUv]);
		}
		
		var myData1 = mydataArray1;
		//var myData2 = mydataArray2;
		var myChart = new JSChart('mychart', 'line','86321583T0067135F');
		myChart.setDataArray(myData1,"blue");
		//myChart.setDataArray(myData2,'green');
		myChart.setTitle('基础数据展示');
//		myChart.setTitle('杂志阅读PV报表');
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
		myChart.setLegendForLine('blue', 'pv');
		//myChart.setLegendForLine('green', 'uv');
		myChart.draw();
		
		//table
		tbList(data);
	
	}
	//default 当前时间向前推7天
	var startDate = new Date();
	startDate.setDate(startDate.getDate()-8);
	
	var strsd = strDate(startDate),
		stred = strDate(nowDate),
		expressDate=$("#expressDate").val();
		firstCondition = {"startDate":strsd,"endDate":stred,"expressDate":expressDate};
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
		condition.publicationId = $("#publicationId").val();
		condition.order=$("#order").val();
		condition.expressDate=expressDate;
		if(!expressDate || expressDate==""){
			if(judgeDate(sDate,eDate)) return;
		}
		showChart(condition,ajaxUrl,callBack);
	});
	
	
	//$("#exportData").attr("href","http://www.magme.com/publish/dm-publication!exportData.action?startDate="+$sDate.val()+"&endDate="+$eDate.val()+"&order="+condition.order);
	$("#exportData").unbind('click').bind("click",function(e){
		e.preventDefault();
		var sDate = strToDate($sDate.val());
		var eDate = strToDate($eDate.val());
		var order=$("#order").val();
		window.open("http://www.magme.com/publish/dm-publication!exportData.action?startDate="+$sDate.val()+"&endDate="+$eDate.val()+"&order="+order+"&publicationId="+$("#publicationId").val());
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
			$("<td>"+(referData.publicationName||"")+"</td>").appendTo($tr);
			$("<td>"+(referData.dataDate.substring(0,10)||"")+"</td>").appendTo($tr);
			$("<td>"+(referData.totalPv||"")+"</td>").appendTo($tr);
			//$("<td>"+(referData.totalUv||"")+"</td>").appendTo($tr);
		}
	}
});