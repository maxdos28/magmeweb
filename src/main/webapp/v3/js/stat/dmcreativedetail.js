;$(function($){
	var nowDate = new Date(),
		$sDate = $("#startDate"),
		$eDate = $("#endDate");
	addDatePicker($sDate,nowDate);
	addDatePicker($eDate,nowDate);
	
	var callBack = function(rs){
		var code = rs.code;
		if(code != 200) return;
		if(!rs.data || !rs.data.creativeList || rs.data.creativeList.length == 0 ){
			$("#eventdetailList").empty();
			alert('没有数据');
			return;
		}
		drawChart(rs.data.creativeList);
	};
	var ajaxUrl = SystemProp.appServerUrl+"/stat/dm-details-creative!detail.action";
	
	//drawChart------------------------------------
	function drawChart(data){
		if (!$.isArray(data)) return;
		var chartArray = [],
			$tbody = $("#eventdetailList");
		$tbody.empty();
		for(var i=0;i<data.length;i++){
			var eventdetailData = data[i];
			var $tr = $("<tr></tr>").appendTo($tbody);
			$("<td>"+eventdetailData.creativeId+"</td>").appendTo($tr);
			$("<td>"+eventdetailData.creativeName+"</td>").appendTo($tr);
			$("<td>"+eventdetailData.pv+"</td>").appendTo($tr);
			$("<td>"+eventdetailData.editorName+"</td>").appendTo($tr);
			if(eventdetailData.publicationName){
				$("<td>"+eventdetailData.publicationName+"</td>").appendTo($tr);
			}else{
				$("<td>&nbsp;</td>").appendTo($tr);
			}
			
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
	
	$("#exportData").unbind('click').bind("click",function(e){
		e.preventDefault();
		var sDate = strToDate($sDate.val());
		var eDate = strToDate($eDate.val());
		window.open("http://www.magme.com/stat/dm-details-creative!exportData.action?startDate="+$sDate.val()+"&endDate="+$eDate.val());
	});
});