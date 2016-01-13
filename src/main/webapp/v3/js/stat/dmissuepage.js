;$(function($){
	var nowDate = new Date(),
		$sDate = $("#startDate"),
		$eDate = $("#endDate");
	addDatePicker($sDate,nowDate);
	addDatePicker($eDate,nowDate);
	
	var callBack = function(rs){
		var code = rs.code;
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
		if(!rs.data || !rs.data.dmIssuePageList || rs.data.dmIssuePageList.length == 0){
			var $tbody = $("#dataList");
			$tbody.empty();
			alert('没有数据');
			return;
		}
		drawChart(rs.data.dmIssuePageList);
	};
	var ajaxUrl = SystemProp.appServerUrl+"/publish/dm-issue-page!pageJson.action";
	
	//drawChart------------------------------------
	function drawChart(data){
		if (!$.isArray(data)) return;
		tbList(data);
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
			eDate = strToDate($eDate.val()),
			expressDate=$("#expressDate").val();
		condition.startDate = $sDate.val();
		condition.endDate = $eDate.val();
		condition.issueId = $("#issueId").val();
		condition.order=$("#order").val();
		condition.expressDate=expressDate;
		if(!expressDate || expressDate==""){
			if(judgeDate(sDate,eDate)) return;
		}
		
		showChart(condition,ajaxUrl,callBack);
	});
	
	$("#exportData").unbind('click').live("click",function(e){
		e.preventDefault();
		var sDate = strToDate($sDate.val());
		var eDate = strToDate($eDate.val());
		var order=$("#order").val();
		//alert("http://www.magme.com/publish/dm-publication!exportData.action?startDate="+$sDate.val()+"&endDate="+$eDate.val()+"&order="+order);
		window.open("http://www.magme.com/publish/dm-issue-page!exportData.action?startDate="+$sDate.val()+"&endDate="+$eDate.val()
				+"&order="+order+"&issueId="+$("#issueId").val());
	});
	
	function tbList(data){
		if (!$.isArray(data)) return;
		var chartArray = [],
			ot = 0,
			$tbody = $("#dataList");
		$tbody.empty();
		
		for(var i=0;i<data.length;i++){
			var referData = data[i];
			if(referData.pageNo != -1 ){
				var $tr = $("<tr></tr>").appendTo($tbody);
				$("<td>"+(referData.publicationName||"")+(referData.issueNumber || "")+"</td>").appendTo($tr);
				$("<td>"+(referData.dataDate.substring(0,10)||"")+"</td>").appendTo($tr);
				$("<td><a class='btn' target='about_blank' href='http://www.magme.com/publish/mag-read.action?id="+referData.issueId+"&pageId="+(referData.pageNo+1)+"' >"+(referData.pageNo+1||"")+" </a></td>").appendTo($tr);
				$("<td>"+((referData.retention/1000).toFixed(2)||"0")+"</td>").appendTo($tr);
			}
		}
		var $tr = $("<tr></tr>").appendTo($tbody);
		$("<td></td>").appendTo($tr);
		$("<td></td>").appendTo($tr);
		$("<td></td>").appendTo($tr);
		$('<td><a href="javascript:void(0)" id="exportData" class="btnWS">查看完整数据</a></td>').appendTo($tr);
	}
});