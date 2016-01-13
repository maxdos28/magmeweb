;$(function($){
	var nowDate = new Date(),
		$sDate = $("#startDate"),
		$eDate = $("#endDate");
	addDatePicker($sDate,nowDate);
	addDatePicker($eDate,nowDate);

	//default 当前时间向前推7天
	var startDate = new Date();
	startDate.setDate(startDate.getDate()-8);
	
	var strsd = strDate(startDate),
		stred = strDate(nowDate);
	$eDate.val(stred);
	$sDate.val(strsd);
	$("#searchBtn").unbind("click").live("click", function() {
		$("#dataList").html("");
		var data = {};
		data.startDate = $("#startDate").val();
		data.endDate = $("#endDate").val();
		$.ajax({
			url : SystemProp.appServerUrl + "/look/look-stat-article-by-employee!searchStatArticleByEmployeeJson.action",
			type : "POST",
			data : data,
			async : false,
			dataType : "json",
			success : function(rs){
				if (rs.code != 200) {
					alert(rs.message);
				} else {
					if(!rs.data||!rs.data.resultList)
					{
						alert("无数据");
						return;
					}
					tbList(rs.data.resultList);
				}
			}
		});
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
			$("<td>"+(i+1)+"</td>").appendTo($tr);				
			$("<td>"+(referData.cuser)+"</td>").appendTo($tr);
			$("<td>"+(referData.countPage)+"</td>").appendTo($tr);
		}
	}
	
});