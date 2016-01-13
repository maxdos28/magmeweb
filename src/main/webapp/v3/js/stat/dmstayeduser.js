;$(function($){
	var nowDate = new Date(),
		$sDate = $("#startDate"),
		$eDate = $("#endDate");
	addDatePicker($sDate,nowDate);
	addDatePicker($eDate,nowDate);

	var ajaxUrl = SystemProp.appServerUrl+"/newpublisherstat/dm-stayed-user!pubJson.action";
	
	var startDate = new Date();
	startDate.setDate(startDate.getDate()-8);
	var strsd = strDate(startDate),
		stred = strDate(nowDate);
	$eDate.val(stred);
	$sDate.val(strsd);
	
	var myMap = ($.browser.msie) ? $("#myMap1").get(0) : $("#myMap2").get(0);
	
	window.loadingAjax = refresh;
	//重新查询
	$("#changeChart").unbind('click').click(function(e){
		e.preventDefault();
		refresh();
	});
	//导出
	$("#exportData").unbind('click').live("click",function(e){
		e.preventDefault();
		var sDate = strToDate($sDate.val()),
		eDate = strToDate($eDate.val());
		if(judgeDate(sDate,eDate)) return;
		var ajaxUrl2 = SystemProp.appServerUrl+"/newpublisherstat/dm-stayed-user!exportData.action?startDate=" + $sDate.val() + "&endDate=" + $eDate.val()
		+ "&selectType=" + $("#selectType").val() + "&chartType=3";
		window.open(ajaxUrl2);
	});
	
	function refresh(){
		var sDate = strToDate($sDate.val()),
		eDate = strToDate($eDate.val());
		if(judgeDate(sDate,eDate)) return;
		var ajaxUrl2 = ajaxUrl + "?startDate=" + $sDate.val() + "&endDate=" + $eDate.val();
		if($("#publicationName").length){
			var publicationName = $("#publicationName").val();
			if(!publicationName){
				alert("输入杂志名称，查询对应的出版商用户留存信息！");
				return;
			}
			ajaxUrl2 += "&publicationName=" + encodeURIComponent(publicationName);
		}
		ajaxUrl2 += "&selectType=" + $("#selectType").val() + "&chartType=";
		myMap.refreshData(ajaxUrl2 + $("#chartType").val());

		$.ajax({
			url: ajaxUrl2 + 3,
			type: "GET",
			data: {},
			success: callBack
		});
	}

	var callBack = function(rs){
		var code = rs.code;
		if(code != 200) return;
		if(!rs.data || !rs.data.dataList || rs.data.dataList.length == 0){
			alert('没有数据');
			return;
		}
		drawTablet(rs.data.dataList);
	}; 

	function drawTablet(data,num){
		if (!$.isArray(data)) return;
		var $tbody = $("#dataList");
		$tbody.empty();
		var newUserNum = 0, stayedUserNum = 0, totalUserNum = 0;
		for(var i = 0; i < data.length && i < 10; i++){
			var referData = data[i];
			newUserNum += referData.newUserNum;
			stayedUserNum += referData.stayedUserNum;
			totalUserNum += referData.totalUserNum;
			var date = referData.date ? referData.date.split("T")[0] : "";
			var $tr = $("<tr></tr>").appendTo($tbody);
			$("<td name='domain'>" + date + "</td>").appendTo($tr);
			$("<td>" + referData.stayedUserNum + "</td>").appendTo($tr);
			$("<td>" + referData.newUserNum + "</td>").appendTo($tr);
			$("<td>" + (referData.rate * 100).toFixed(2) + "%</td>").appendTo($tr);
		}
		var $tr = $("<tr></tr>").appendTo($tbody);
		$('<td colspan="4"><a href="javascript:void(0)" id="exportData" class="btnWS">查看完整数据</a></td>').appendTo($tr);
		$("#newUserNum").html(newUserNum);
		$("#stayedUserNum").html(stayedUserNum);
		$("#rate").html((stayedUserNum * 100 / totalUserNum).toFixed(2) + "%");
	}
	
});