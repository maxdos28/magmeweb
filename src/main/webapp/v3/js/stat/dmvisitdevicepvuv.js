;$(function($){
	var nowDate = new Date(),
		$sDate = $("#startDate"),
		$eDate = $("#endDate");
	addDatePicker($sDate,nowDate);
	addDatePicker($eDate,nowDate);

	var ajaxUrl = SystemProp.appServerUrl+"/newpublisherstat/dm-visit-device-pv-uv!devicePvUv.action";
	
	var startDate = new Date();
	startDate.setDate(startDate.getDate()-8);
	
	var strsd = strDate(startDate),
		stred = strDate(nowDate);
	$eDate.val(stred);
	$sDate.val(strsd);
	
	var myMap = ($.browser.msie) ? $("#myMap1").get(0) : $("#myMap2").get(0);
	window.loadingAjax = function (){
		var ajaxUrl2=ajaxUrl+"?startDate="+$sDate.val()+"&endDate="+$eDate.val();
		var publicationId = $("#publicationId").val();
		if(publicationId) {
			ajaxUrl2 += "&publicationId=" + publicationId;
		} else {
			return;
		}
		myMap.refreshData(ajaxUrl2,"终端比例");
		ajaxTableData(ajaxUrl2);
	};
	
	$("#changeChart").unbind('click').live("click",function(e){
		e.preventDefault();
		var condition = {},
			sDate = strToDate($sDate.val()),
			eDate = strToDate($eDate.val());
		if(judgeDate(sDate,eDate)) return;
		condition.startDate = $sDate.val();
		condition.endDate = $eDate.val();
		var publicationName = $("#publicationName").val();
		var publicationId = $("#publicationId").val();
		if(publicationName){
			condition.publicationName = encodeURIComponent(publicationName);
		} else if(publicationId) {
			condition.publicationId=$("#publicationId").val();
		} else {
			alert("请确定要查询的杂志名称！");
			return;
		}
		var params = "";
		for ( var key in condition) {
			params += params.length > 0 ? "&" : "?";
			params += key + "=" + condition[key];
		}
		var ajaxUrl2 = ajaxUrl + params;
		myMap.refreshData(ajaxUrl2,"终端比例");
		ajaxTableData(ajaxUrl2);
	});
	
	function ajaxTableData(ajaxUrl2){
		var callback = function(rs){
				if(rs.code==200){
					var tempContext="";
					var tmpSum = rs.data.statPieChartSum;
					$("#deviceList").empty();
					if(rs.data.statPieChartList){
						$.each(rs.data.statPieChartList,function(n,cs){
							tempContext+="<tr>";
							tempContext+="<td>"+cs.name+"</td>";
							var tempNum = (cs.value/tmpSum).toFixed(3)*1000/10;
							tempContext+="<td>"+tempNum+"%</td>";
							tempContext+="<td>"+cs.value+"</td>";
							tempContext+="</tr>";
						});	
					}
					$("#deviceList").append(tempContext);
					if(rs.data.appUsages){
						$("#appUsages").empty();
						var usages = "";
						$.each(rs.data.appUsages,function(n,cs){
							usages += "<tr>";
							usages += "<td>" + cs.appName + "</td>";
							usages += "<td>" + cs.platform + "</td>";
							usages += "<td>" + cs.newUser + "</td>";
							usages += "<td>" + cs.installNum + "</td>";
							usages += "<td>" + cs.startUpNum + "</td>";
							usages += "</tr>";
						});
						$("#appUsages").append(usages);
					}
				}else{
					$("#deviceList").empty();
					if(rs.message){
						alert(rs.message);
					}
				}
			};
					
			$.ajax({
				url: ajaxUrl2,
				type: "GET",
				dataType: "JSON",
				success: callback
			});
	}

	
});