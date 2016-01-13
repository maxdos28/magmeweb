;$(function($){
	var nowDate = new Date(),
		$sDate = $("#startDate"),
		$eDate = $("#endDate");
	addDatePicker($sDate,nowDate);
	addDatePicker($eDate,nowDate);
	
	var ajaxUrl = SystemProp.domain+"/look/look-stat-user-area!searchUserAreaJson.action";
	
	
	//default 当前时间向前推7天
	var startDate = new Date();
	startDate.setDate(startDate.getDate()-8);
	
	var strsd = strDate(startDate),
		stred = strDate(nowDate);
		firstCondition = {"startDate":strsd,"endDate":stred};
	$eDate.val(stred);
	$sDate.val(strsd);
	
	var myMap = ($.browser.msie) ? $("#myMap1").get(0) : $("#myMap2").get(0);
	window.loadingAjax = function (){
		var ajaxUrl2=ajaxUrl+"?startDate="+strsd+"&endDate="+stred;
		myMap.refreshData(ajaxUrl2);
	};
	
	showTbl(strsd,stred);
	
	$("#changeChart").unbind('click').click(function(e){
		e.preventDefault();
		var	sDate = strToDate($sDate.val()),
			eDate = strToDate($eDate.val()),
			startDate = $sDate.val(),
			endDate = $eDate.val();
		var ajaxUrl2=ajaxUrl+"?startDate="+startDate+"&endDate="+endDate;
		
		if(judgeDate(sDate,eDate)) return;
		
		myMap.refreshData(ajaxUrl2);
		
		showTbl(startDate,endDate,"");
	});
	
	function showTbl( sDate,eDate,order){
		var  condition = {"startDate":sDate,"endDate":eDate};
		$.ajax({
			url: ajaxUrl,
			type: "GET",
			data:condition,
			success: function(rs){
				var code = rs.code;
				if(code != 200) return;
				if(!rs.data || !rs.data.datamapList || rs.data.datamapList.length == 0){
					alert('没有数据');
					return;
				}
				tbList(rs.data.datamapList)
			}
		});
	}
	function tbList(data){
		if (!$.isArray(data)) return;
		var chartArray = [],
			ot = 0,
			$tbody = $("#dataList");
		$tbody.empty();
		
		for(var i=0;i<data.length;i++){
			var referData = data[i];
			
			var $tr = $("<tr></tr>").appendTo($tbody);
			$("<td>"+(referData.province)+"</td>").appendTo($tr);
			$("<td>"+(referData.percent)+"%</td>").appendTo($tr);
			$("<td>"+(referData.viewNumber)+"</td>").appendTo($tr);
			
		}
	}
	
});
