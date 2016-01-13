;$(function($){
	var nowDate = new Date(),
		$sDate = $("#startDate"),
		$eDate = $("#endDate");
	addDatePicker($sDate,nowDate);
	addDatePicker($eDate,nowDate);
	
	var callBack = function(rs){
		var code = rs.code;
		if(code != 200) return;
		if(!rs.data || !rs.data.dmEventDetailList || rs.data.dmEventDetailList.length == 0 ){
			alert('没有数据');
			return;
		}
		drawChart(rs.data.dmEventDetailList);
	};
	var ajaxUrl = SystemProp.appServerUrl+"/stat/dm-event!queryEventDetailJson.action";
	
	//drawChart------------------------------------
	function drawChart(data){
		if (!$.isArray(data)) return;
		var chartArray = [],
			$tbody = $("#eventdetailList");
		$tbody.empty();
		for(var i=0;i<data.length;i++){
			var eventdetailData = data[i];
			var $tr = $("<tr></tr>").appendTo($tbody);
			$("<td>"+eventdetailData.eventId+"</td>").appendTo($tr);
			$("<td>"+eventdetailData.eventTitle+"</td>").appendTo($tr);
			$("<td>"+eventdetailData.uv+"</td>").appendTo($tr);
			$("<td>"+eventdetailData.publicationName+"</td>").appendTo($tr);
			
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