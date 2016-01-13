;$(function($){
	var nowDate = new Date(),
		$sDate = $("#startDate"),
		$eDate = $("#endDate");
	addDatePicker($sDate,nowDate);
	addDatePicker($eDate,nowDate);
	
	var callBack = function(rs){
		var code = rs.code;
		if(code != 200) return;
		if(!rs.data || !rs.data.dmSearchList || rs.data.dmSearchList.length == 0 ){
			alert('没有数据');
			return;
		}
		drawChart(rs.data.dmSearchList);
	};
	var ajaxUrl = SystemProp.appServerUrl+"/stat/dm-search!querySearchJson.action";
	
	//drawChart------------------------------------
	function drawChart(data){
		if (!$.isArray(data)) return;
		var chartArray = [],
			$tbody = $("#searchList");
		$tbody.empty();
		for(var i=0;i<data.length;i++){
			var searchData = data[i];
			var $tr = $("<tr></tr>").appendTo($tbody);
			$("<td>"+decodeURI(searchData.keyword)+"</td>").appendTo($tr);
			$("<td>"+searchData.searchTimes+"</td>").appendTo($tr);
			
		}
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