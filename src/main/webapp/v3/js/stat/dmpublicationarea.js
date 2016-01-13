;$(function($){
	var nowDate = new Date(),
		$sDate = $("#startDate"),
		$eDate = $("#endDate"),
		$publicationId=$("#publicationId").val();
	addDatePicker($sDate,nowDate);
	addDatePicker($eDate,nowDate);
	
	var ACTION_URL = SystemProp.domain+"/publish/dm-publication-area!pubJson.action";
	var ajaxUrl = ACTION_URL;
//	ajaxUrl = SystemProp.domain+"/publish/dm-publication-area!pubJson.action";
	
	//default 当前时间向前推7天
	var startDate = new Date();
	startDate.setDate(startDate.getDate()-8);
	
	var strsd = strDate(startDate), 
		stred = strDate(nowDate);
		firstCondition = {"startDate":strsd,"endDate":stred};
	$eDate.val(stred);
	$sDate.val(strsd);
	ajaxUrl = ACTION_URL + "?startDate="+strsd+"&endDate="+stred+"&publicationId="+$publicationId;
	
	var myMap = ($.browser.msie) ? $("#myMap1").get(0) : $("#myMap2").get(0);
	window.loadingAjax = function (){
		myMap.refreshData(ajaxUrl,0);
	};
	
	showTbl(strsd,stred);
	//确定按钮
	$("#changeChart").unbind('click').click(function(e){
		e.preventDefault();
		showResult($sDate.val(),$eDate.val());
	});
	
	//根据时间区间查询结果
	function showResult(startDate, endDate){
		var ajaxUrl2 = SystemProp.domain+"/publish/dm-publication-area!pubJson.action";
		var	sDate = strToDate(startDate), 
			eDate = strToDate(endDate),
			publicationId = $("#publicationId").val();
		
		if(judgeDate(sDate,eDate)) return;
		var date_url = "?startDate="+startDate+"&endDate="+endDate+"&publicationId="+publicationId;
		ajaxUrl2 += date_url;
		ajaxUrl = ACTION_URL + date_url;
		myMap.refreshData(ajaxUrl2);
		showTbl(startDate,endDate);
	}
	
	//重写statF.js日期判断函数        ----------------------------------------
//	function judgeDate(sDate,eDate){
//		if(eDate < sDate){
//			alert('结束日期必须大于开始日期！');
//			return true;
//		}else if(eDate > (+new Date())){
//			alert('结束日期不能大于当前时间！');
//			return true;
//		}else if(eDate == sDate){
//			alert('不能选择一天的数据！');
//			return true;
//		}else{
//			var et=new Date(eDate);
//			var may= strToDate('2012-05-01');
//			if(sDate<may || eDate<may){
//				alert("查询日期不能在2012-05-01之前");
//				return true;
//			} 
//			et.setDate(et.getDate()-31);
//			if(strDate(new Date(eDate))==strDate(new Date())){
//				if(new Date(sDate)<et){
//					alert('请选择查结束日期前31 天统计！');
//					return true;
//				}
//			}else{
//				if(new Date(sDate)<=et){
//					alert('请选择查结束日期前 31天统计！');
//					return true;
//				}
//			}
//		}
//		return false;
//	}
	
	function showTbl( sDate,eDate){
		var  condition = {"startDate":sDate,"endDate":eDate};
		$.ajax({
			url: ajaxUrl,
			type: "GET",
			success: function(rs){
				var code = rs.code;
				if(code != 200) return;
				if(!rs.data || !rs.data.datamapList || rs.data.datamapList.length == 0){
					alert('没有数据');
					return;
				}
				tbList(rs.data.datamapList,rs.data.uvcount,rs.data.pvcount)
			}
		});
	}
	function tbList(data,uvcount,pvcount){
		if (!$.isArray(data)) return;
		var chartArray = [],
			ot = 0,
			$tbody = $("#dataList");
		$tbody.empty();
		
		for(var i=0;i<data.length;i++){
			var referData = data[i];
			
			var $tr = $("<tr></tr>").appendTo($tbody);
			$("<td>"+(referData.province||"")+"</td>").appendTo($tr);
			$("<td>"+(referData.viewNumber||"")+"</td>").appendTo($tr);
			//$("<td>"+referData.clickNumber+"</td>").appendTo($tr);
			
		}
	}
	
});
