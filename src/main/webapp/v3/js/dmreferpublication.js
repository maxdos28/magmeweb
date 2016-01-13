;$(function($){
	var nowDate = new Date(),
		$sDate = $("#startDate"),
		$eDate = $("#endDate"),
		$publicationId=$("#publicationId").val();
	addDatePicker($sDate,nowDate);
	addDatePicker($eDate,nowDate);
	var ajaxUrl = SystemProp.appServerUrl+"/publish/dm-refer-publication!queryReferJson.action";
	var type = getUrlValue("type");
	
	var callBack = function(rs){
		var code = rs.code;
		if(code != 200) return;
		if(!rs.data || !rs.data.dmReferList || rs.data.dmReferList.length == 0 || rs.data.totalNum*1 == 0){
			alert('没有数据');
			return;
		}
		drawChart(rs.data.dmReferList,rs.data.totalNum);
	}; 
	
	//drawChart------------------------------------
	function drawChart(data,num){
		if (!$.isArray(data)) return;
		var chartArray = [],
			ot = 0,
			$tbody = $("#dataList");
		$tbody.empty();
		for(var i=0;i<data.length;i++){
			var referData = data[i];
			var scale = (referData.viewNumber*100/num) |0 ;
			if(i<5){
				ot += scale;
				chartArray.push([referData.domain,scale]);
			}else if(i==5){
				chartArray.push(["其他",(100 - ot)]);
			}
			var $tr = $("<tr></tr>").appendTo($tbody);
			$("<td name='domain'>"+(referData.domain||"")+"</td>").appendTo($tr);
			$("<td>"+(referData.viewNumber*100/num).toFixed(2)+"%</td>").appendTo($tr);
			$("<td>"+referData.viewNumber+"</td>").appendTo($tr);
			$("<td><a class='btn' name='showOneChart' href='#'>查看</a></td>").appendTo($tr);
		}
		var $tr = $("<tr></tr>").appendTo($tbody);
		$('<td colspan="4"><a href="javascript:void(0)" id="exportData" class="btnWS">查看完整数据</a></td>').appendTo($tr);
		var colors = [];
		if(chartArray.length==1){
			var colors = ['#0673B8'];
		}else if(chartArray.length==2){
			var colors = ['#0673B8', '#0091F1'];
		}else if(chartArray.length==3){
			var colors = ['#0673B8', '#0091F1', '#F85900', ];
		}else if(chartArray.length==4){
			var colors = ['#0673B8', '#0091F1', '#F85900', '#1CC2E6', ];
		}else if(chartArray.length==5){
			var colors = ['#0673B8', '#0091F1', '#F85900', '#1CC2E6', '#C32121', ];
		}else if(chartArray.length==6){
			var colors = ['#0673B8', '#0091F1', '#F85900', '#1CC2E6', '#C32121', '#41B2FA'];
		}
		
		var myChart = new JSChart('mychart', 'pie');
		myChart.setDataArray(chartArray);
		myChart.colorizePie(colors);
		myChart.setTitle("来路域名比例（单位%）");
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
		stred = strDate(nowDate),
		$publicationId=$("#publicationId").val(),
		firstCondition = {"startDate":strsd,"endDate":stred};
	if(type)firstCondition.type = type;
	if($publicationId)firstCondition.publicationId = $publicationId;
	$eDate.val(stred);
	$sDate.val(strsd);
	
	showChart(firstCondition,ajaxUrl,callBack);
	
	function showResult(startDate, endDate){
		var condition = {},
			$publicationId=$("#publicationId").val();
		condition.startDate = startDate;
		condition.endDate = endDate;
		if(type)condition.type = type;
		if($publicationId)condition.publicationId = $publicationId;
		showChart(condition,ajaxUrl,callBack);
	}
	
	$("#changeChart").unbind('click').click(function(e){
		e.preventDefault();
		var condition = {},
			sDate = strToDate($sDate.val()),
			eDate = strToDate($eDate.val());
		if(judgeDate(sDate,eDate)) return;
		showResult($sDate.val(), $eDate.val());
//		condition.startDate = $sDate.val();
//		condition.endDate = $eDate.val();
//		if(type)condition.type = type;
//		if($publicationId)condition.publicationId = $publicationId;
//		showChart(condition,ajaxUrl,callBack);
	});


	$("a[name='showOneChart']").live('click',function(e){
		e.preventDefault();
		var domain = $(this).parents("tr").find("td[name='domain']").text();
		var startDate = $sDate.val();
		var endDate = $eDate.val();
		var $publicationId=$("#publicationId").val();
		var url = SystemProp.appServerUrl + "/publish/dm-refer-publication!referDetail.action?domain=" + encodeURIComponent(domain)
		+ "&startDate=" + encodeURIComponent(startDate) + "&endDate="+encodeURIComponent(endDate)
		+ "&publicationId=" + $publicationId;
		if(type) url += "&type=" + type;
		window.open(url);
	});

	$("#exportData").unbind('click').live("click",function(e){
		e.preventDefault();
		var startDate = $sDate.val();
		var endDate = $eDate.val();
		var $publicationId=$("#publicationId").val();
		var url = SystemProp.appServerUrl + "/publish/dm-refer-publication!exportData.action"
					+ "?startDate=" + encodeURIComponent(startDate) + "&endDate="+encodeURIComponent(endDate)
					+ "&publicationId=" + $publicationId;
		if(type) url += "&type=" + type;
		window.open(url);
	});
});