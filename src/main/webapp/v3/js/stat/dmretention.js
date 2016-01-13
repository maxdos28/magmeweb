;$(function($){
	var nowDate = new Date(),
		$sDate = $("#startDate"),
		$eDate = $("#endDate");
	addDatePicker($sDate,nowDate);
	addDatePicker($eDate,nowDate);

	var ajaxUrl = SystemProp.appServerUrl+"/newpublisherstat/dm-retention!retentionJson.action";
	
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
		if(publicationId){
			ajaxUrl2 += "&publicationId=" + publicationId;
		} else {
			return;
		}
		myMap.refreshData(ajaxUrl2,"联网方式");
		ajaxTableData(ajaxUrl2);
	};
	
	$("#changeChart").unbind('click').click(function(e){
		e.preventDefault();
		var condition = {},
			sDate = strToDate($sDate.val()),
			eDate = strToDate($eDate.val());
		condition.startDate = $sDate.val();
		condition.endDate = $eDate.val();
		if(judgeDate(sDate,eDate)) return;

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
//		var ajaxUrl2=ajaxUrl+"?startDate="+condition.startDate+"&endDate="+condition.endDate+"&publicationId="+condition.publicationId;
		myMap.refreshData(ajaxUrl2,"联网方式");
		ajaxTableData(ajaxUrl2);
	});
	
	function ajaxTableData(ajaxUrl2){
		var callback = function(rs){
				if(rs.code==200){
					var tempContext="";
					$("#retentionList").empty();
					if(rs.data.retentionList){
						$.each(rs.data.retentionList,function(n,cs){
							tempContext+="<tr>";
							tempContext+="<td>"+cs.retention+"</td>";
							tempContext+="<td>"+cs.times+"</td>";
							tempContext+="<td>"+cs.publicationName+"</td>";
							tempContext+="</tr>";
						});	
					}
					$("#retentionList").append(tempContext);
				}else{
					$("#retentionList").empty();
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