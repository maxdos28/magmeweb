;$(function($){
	var nowDate = new Date(),
		$sDate = $("#startDate"),
		$eDate = $("#endDate");
	addDatePicker($sDate,nowDate);
	addDatePicker($eDate,nowDate);

	var ajaxUrl = SystemProp.appServerUrl+"/publish/dm-sns-pv-uv!snspvuvJson.action";
	
	var startDate = new Date();
	startDate.setDate(startDate.getDate()-8);
	
	var strsd = strDate(startDate),
		stred = strDate(nowDate);
	var	firstCondition = {"startDate":strsd,"endDate":stred,"publicationId":$("#publicationId").val()};
	$eDate.val(stred);
	$sDate.val(strsd);
	var myMap = ($.browser.msie) ? $("#myMap1").get(0) : $("#myMap2").get(0);
	window.loadingAjax = function (){
		var ajaxUrl2=ajaxUrl+"?startDate="+$sDate.val()+"&endDate="+$eDate.val()+"&publicationId="+$("#publicationId").val();
		myMap.refreshData(ajaxUrl2,"sns应用分布");
	};
	
	function showChart(condition,ajaxUrl,callBack){
		$("#mychartLine").empty();
		$.ajax({
			url: ajaxUrl,
			type: "GET",
			data: condition,
			success: callBack
		});
	}
	
	
	$("#changeChart").unbind('click').click(function(e){
		e.preventDefault();
		var condition = {},
			sDate = strToDate($sDate.val()),
			eDate = strToDate($eDate.val());
		condition.startDate = $sDate.val();
		condition.endDate = $eDate.val();
		condition.publicationId=$("#publicationId").val();
		if(judgeDate(sDate,eDate)) return;
		var ajaxUrl2=ajaxUrl+"?startDate="+condition.startDate+"&endDate="+condition.endDate+"&publicationId="+condition.publicationId;
		myMap.refreshData(ajaxUrl2,"sns应用分布");
		showChart(condition,ajaxUrl,callBack);
	});
	
	var callBack = function(rs){
		var code = rs.code;
		if(code != 200) return;
		if(!rs.data || !rs.data.statPieChartList || rs.data.statPieChartList.length == 0){
			alert('没有数据');
			return;
		}
		drawChart(rs.data.statPieChartListLine);
		tbList(rs.data.snsPvTable);
	};
	
	function drawChart(data){
		var mydataArray1 = [];
		var	mydataArray2 = [];
		var mydataArray3 = [];
		var	mydataArray4 = [];
		var mydataArray5 = [];
		var	mydataArray6 = [];
		var mydataArray7 = [];
		var	mydataArray8 = [];
		var device1="",device2="",device3="",device4="",device5="",device6="",device7="",device8="";
		
		var tflag=0,flag=0;
		for(var i=0;i<data.length;i++){
			var item = data[i];
			if(i==0){
				if(item.list!=null){
					for(var j=0;j<item.list.length;j++){
						var cItem=item.list[j];
						mydataArray1.push([cItem.name,cItem.value]);
						if(j+1>tflag){tflag++;flag=i;}
					}
				}
				device1=item.device;
			}
			if(i==1){
				if(item.list!=null){
					for(var j=0;j<item.list.length;j++){
						var cItem=item.list[j];
						mydataArray2.push([cItem.name,cItem.value]);
						if(j+1>tflag){tflag++;flag=i;}
					}
				}
				device2=item.device;
			}
			if(i==2){
				if(item.list!=null){
					for(var j=0;j<item.list.length;j++){
						var cItem=item.list[j];
						mydataArray3.push([cItem.name,cItem.value]);
						if(j+1>tflag){tflag++;flag=i;}
					}
				}
				device3=item.device;
			}
			if(i==3){
				if(item.list!=null){
					for(var j=0;j<item.list.length;j++){
						var cItem=item.list[j];
						mydataArray4.push([cItem.name,cItem.value]);
						if(j+1>tflag){tflag++;flag=i;}
					}
				}
				device4=item.device;
			}
			if(i==4){
				if(item.list!=null){
					for(var j=0;j<item.list.length;j++){
						var cItem=item.list[j];
						mydataArray5.push([cItem.name,cItem.value]);
						if(j+1>tflag){tflag++;flag=i;}
					}
				}
				device5=item.device;
			}
			if(i==5){
				if(item.list!=null){
					for(var j=0;j<item.list.length;j++){
						var cItem=item.list[j];
						mydataArray6.push([cItem.name,cItem.value]);
						if(j+1>tflag){tflag++;flag=i;}
					}
				}
				device6=item.device;
			}
			if(i==6){
				if(item.list!=null){
					for(var j=0;j<item.list.length;j++){
						var cItem=item.list[j];
						mydataArray7.push([cItem.name,cItem.value]);
						if(j+1>tflag){tflag++;flag=i;}
					}
				}
				device7=item.device;
			}
			if(i==7){
				if(item.list!=null){
					for(var j=0;j<item.list.length;j++){
						var cItem=item.list[j];
						mydataArray8.push([cItem.name,cItem.value]);
						if(j+1>tflag){tflag++;flag=i;}
					}
				}
				device8=item.device;
			}
		}
		
		var dataLen = null;
		var myChart = new JSChart('mychartLine', 'line','86321583T0067135F');
		
		for(var i=0;i<mydataArray1.length;i++){
			myChart.setTooltip([i]);
		}
		myChart.setDataArray(mydataArray1,"#2BD5A2");
		myChart.setDataArray(mydataArray2,'#3C57C4');
		myChart.setDataArray(mydataArray3,'#33ADCC');
		myChart.setDataArray(mydataArray4,'#629D79');
		myChart.setDataArray(mydataArray5,'#2FD02F');
		myChart.setDataArray(mydataArray6,'#C83780');
		myChart.setDataArray(mydataArray7,'#DD9322');
		myChart.setTitle('SNS PV分析');
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
		myChart.setAxisValuesNumberX(mydataArray1.length);
		myChart.setGraphExtend(true);
		myChart.setLineColor('#2BD5A2', '#2BD5A2');
		myChart.setLineColor('#3C57C4', '#3C57C4');
		myChart.setLineColor('#33ADCC', '#33ADCC');
		myChart.setLineColor('#629D79', '#629D79');
		myChart.setLineColor('#2FD02F', '#2FD02F');
		myChart.setLineColor('#C83780', '#C83780');
		myChart.setLineColor('#DD9322', '#DD9322');
		myChart.setLineWidth(3);
		myChart.setSize(780, 380);
		myChart.setFlagColor('#9D16FC');
		myChart.setFlagRadius(3);
		myChart.setLegendShow(true);
		myChart.setLegendPosition(700, 35);
		myChart.setLegendForLine('#2BD5A2',device1);
		myChart.setLegendForLine('#3C57C4',device2 );
		myChart.setLegendForLine('#33ADCC',device3 );
		myChart.setLegendForLine('#629D79',device4 );
		myChart.setLegendForLine('#2FD02F',device5 );
		myChart.setLegendForLine('#C83780',device6 );
		myChart.setLegendForLine('#DD9322',device7 );
		myChart.draw();
	}
	showChart(firstCondition,ajaxUrl,callBack);
	
	var tableVal;
	
	function tbList(data){
		if (!$.isArray(data)) return;
		var chartArray = [],
			ot = 0,
			$tbody = $("#dataList");
			$tbody.empty();
			
			tableVal=data;
			
		for(var i=0;i<data.length;i++){
			var temp = data[i];
			
			var $tr = $("<tr></tr>").appendTo($tbody);
			$("<td>"+(temp.device)+"</td>").appendTo($tr);
			$("<td>"+(temp.total)+"</td>").appendTo($tr);
			$("<td>"+(temp.item)+"</td>").appendTo($tr);
			$("<td>"+(temp.rate)+"%</td>").appendTo($tr);
		}
		
	}
	
	$(".icon16ascending,.icon16descending,.icon16descendingSelect,.icon16ascendingSelect").live("click",function(){
		var vid = $(this).attr("id");
		var nVal=[],vVal=[];
		
		var index=1,desc=0;
		if(vid=='up_pv'){
			index=1;
			desc=1;
			$(this).addClass("icon16ascendingSelect");
			$("#up_uv").removeClass("icon16ascendingSelect");
			$("#up_retention").removeClass("icon16ascendingSelect");
			
			$("#down_pv").removeClass("icon16descendingSelect");
			$("#down_uv").removeClass("icon16descendingSelect");
			$("#down_retention").removeClass("icon16descendingSelect");
			
		}else if(vid=='down_pv'){
			index=1;
			desc=0;
			
			$("#up_pv").removeClass("icon16ascendingSelect");
			$("#up_uv").removeClass("icon16ascendingSelect");
			$("#up_retention").removeClass("icon16ascendingSelect");
			
			$(this).addClass("icon16descendingSelect");
			$("#down_uv").removeClass("icon16descendingSelect");
			$("#down_retention").removeClass("icon16descendingSelect");
		}else if(vid=='up_uv'){
			index=2;
			desc=1;
			
			$(this).addClass("icon16ascendingSelect");
			$("#up_pv").removeClass("icon16ascendingSelect");
			$("#up_retention").removeClass("icon16ascendingSelect");
			
			$("#down_pv").removeClass("icon16descendingSelect");
			$("#down_uv").removeClass("icon16descendingSelect");
			$("#down_retention").removeClass("icon16descendingSelect");
		}else if(vid=='down_uv'){
			index=2;
			desc=0;
			
			$("#up_pv").removeClass("icon16ascendingSelect");
			$("#up_uv").removeClass("icon16ascendingSelect");
			$("#up_retention").removeClass("icon16ascendingSelect");
			
			$(this).addClass("icon16descendingSelect");
			$("#down_pv").removeClass("icon16descendingSelect");
			$("#down_retention").removeClass("icon16descendingSelect");

		}else if(vid=='up_retention'){
			index=3;
			desc=1;
			
			$(this).addClass("icon16ascendingSelect");
			$("#up_pv").removeClass("icon16ascendingSelect");
			$("#up_uv").removeClass("icon16ascendingSelect");
			
			$("#down_pv").removeClass("icon16descendingSelect");
			$("#down_uv").removeClass("icon16descendingSelect");
			$("#down_retention").removeClass("icon16descendingSelect");
		}else if(vid=='down_retention'){
			index=3;
			desc=0;
			
			$("#up_pv").removeClass("icon16ascendingSelect");
			$("#up_uv").removeClass("icon16ascendingSelect");
			$("#up_retention").removeClass("icon16ascendingSelect");
			
			$(this).addClass("icon16descendingSelect");
			$("#down_pv").removeClass("icon16descendingSelect");
			$("#down_uv").removeClass("icon16descendingSelect");
		}
//		$(this).parents("tr").find("td").each(function(i){
//			alert(i);
//		});
		
		
		$("#dataList").find("tr").each(function(i){
			nVal[i]=$(this).html();
			 $(this).find("td").each(function(j){
				 if(j==index)vVal[i]=$(this).text().replace('%','');
			 });
		 });

		order(vVal,nVal,desc);
		
	});
	
	
	
	function order(array,html,desc){
		var temp;
		for (var i = 0; i < array.length -1; i++) {
			for (var j = 0 ;j < array.length - i - 1; j++) {
				if(desc==0){
					if (parseFloat(array[j]) < parseFloat(array[j+1])) {
						temp = array[j];
						array[j] = array[j+1];
						array[j+1] = temp;
						
						var hTemp = html[j];
						html[j] = html[j+1];
						html[j+1]=hTemp;
					}
				}else{
					if (parseFloat(array[j]) > parseFloat(array[j+1])) {
						temp = array[j];
						array[j] = array[j+1];
						array[j+1] = temp;
						
						var hTemp = html[j];
						html[j] = html[j+1];
						html[j+1]=hTemp;
					}
				}
				
			}
		}
		
		var htmlStr="";
		for (var i = 0; i < html.length; i++) {
			htmlStr+="<tr>"+html[i]+"</tr>";
		}
		 $("#dataList").html(htmlStr);
		
	}
	
});