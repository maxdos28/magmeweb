;$(function($){
	var nowDate = new Date(),
		$sDate = $("#startDate"),
		$eDate = $("#endDate");
	addDatePicker($sDate,nowDate);
	addDatePicker($eDate,nowDate);

	var ajaxUrl = SystemProp.appServerUrl+"/publish/dm-issue-page!newActiveJson.action";
	
	//default 当前时间向前推7天
	var startDate = new Date();
	startDate.setDate(startDate.getDate()-8);
	
	var strsd = strDate(startDate),
		stred = strDate(nowDate),
		expressDate=$("#expressDate").val();
		firstCondition = {"startDate":strsd,"endDate":stred,"issueId":$("#issueId").val()};
	$eDate.val(stred);
	$sDate.val(strsd);
	
	var myMap = ($.browser.msie) ? $("#myMap1").get(0) : $("#myMap2").get(0);
	window.loadingAjax = function (){
		//myMap.refreshData(ajaxUrl,0);
		var ajaxUrl2=ajaxUrl+"?startDate="+$sDate.val()+"&endDate="+$eDate.val()+"&issueId="+$("#issueId").val();
		myMap.refreshData(ajaxUrl2,"目录","点击次数","期刊阅读活跃比例","平均点击次数");
	};
	showTbl(strsd,stred);
	
	$("#changeChart").unbind('click').click(function(e){
		e.preventDefault();
		var condition = {},
			sDate = strToDate($sDate.val()),
			eDate = strToDate($eDate.val());
		condition.startDate = $sDate.val();
		condition.endDate = $eDate.val();
		condition.issueId=$("#issueId").val();
		condition.expressDate=expressDate;
		if(!expressDate || expressDate==""){
			if(judgeDate(sDate,eDate)) return;
		}
		var ajaxUrl2=ajaxUrl+"?startDate="+condition.startDate+"&endDate="+condition.endDate+"&issueId="+condition.issueId;
		myMap.refreshData(ajaxUrl2,"目录","点击次数","期刊阅读活跃比例","平均点击次数");
		showTbl(condition.startDate,condition.endDate);
	});
	
	$("#exportData").unbind('click').live("click",function(e){
		e.preventDefault();
		var sDate = strToDate($sDate.val());
		var eDate = strToDate($eDate.val());
		window.open("http://www.magme.com/publish/dm-issue-page!newActiveExportData.action?startDate="
				+formatDate(sDate)+"&endDate="+formatDate(eDate)+"&issueId="+$("#issueId").val());
	});
	
	function tbList(data){
		if (!$.isArray(data)) return;
		var chartArray = [],
			ot = 0,
			$tbody = $("#dataList");
		$tbody.empty();
		
		for(var i=0;i<data.length && i<10;i++){
			var referData = data[i];
			var $tr = $("<tr></tr>").appendTo($tbody);
			$("<td>"+(referData.longitudinalData||"")+"</td>").appendTo($tr);
			$("<td>"+(referData.horizontalData||"")+"</td>").appendTo($tr);
		}
		
		var $tr = $("<tr></tr>").appendTo($tbody);
		$("<td></td>").appendTo($tr);
		$('<td><a href="javascript:void(0)" id="exportData" class="btnWS">查看完整数据</a></td>').appendTo($tr);
	}
	
	function showTbl(sDate,eDate){
		var  condition = {"startDate":sDate,"endDate":eDate,"issueId":$("#issueId").val()};
		$.ajax({
			url: ajaxUrl,
			type: "GET",
			data:condition,
			success: function(rs){
				var code = rs.code;
				if(code != 200){
					alert('没有数据');
					return;
				}
				if(!rs.data || !rs.data.lineData || rs.data.lineData.length == 0){
					alert('没有数据');
					return;
				}
				tbList(rs.data.lineData)
			}
		});
	}
	
});