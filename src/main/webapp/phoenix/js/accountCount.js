$(function() {
	init();
});
function init() {

	var myDate = new Date();
    var month = (myDate.getMonth()+1 < 10) ? ("0"+(myDate.getMonth()+1)) : (myDate.getMonth()+1)
    var datetime=myDate.getDate()<10?("0"+myDate.getDate()):myDate.getDate();
    var nowDate= myDate.getFullYear()+"-"+month+"-"+datetime;
    datePickerFun1($("#datepicker1"),nowDate);
    datePickerFun2($("#datepicker2"),nowDate);
    
      //时间控件
    //input_datepicker------------------------------------
    function datePickerFun1($dateInput,date){
      if(!date) date = nowDate;
      
    }
    function datePickerFun2($dateInput,date){
      if(!date) date = nowDate;
     
    }
	var nowDate = new Date();
	$("#search").bind("click", function() {
		var start = $("#datepicker1").val();
		var end = $("#datepicker2").val();
		if(start.length==0||end.length==0)
		{
			alert("请选择开始时间和结束时间");
			return;
		}
		$.ajax({
			type : "POST",
			url : SystemProp.appServerUrl
					+ "/phoenix/phoenix-order!searchCountAccount.action", 
			data : {"fromDate":start,"endDate":end},
			dataType : "json",
			success : function(msg) {
				if(msg.code&&msg.code==200)
				{
					$("#android-category").html("￥"+msg.data.android+"元");
					$("#ios-magazine").html("￥"+msg.data.magazine+"元");
					$("#ios-category").html("￥"+msg.data.ios+"元");
					$("#ios-total").html("<strong>￥"+msg.data.iosTotal+"元</strong>");
					$("#all-total").html("￥"+msg.data.allTotal+"元");
				}
			}
		});
	});
	$("#android-export").bind("click", function() {

		var start = $("#datepicker1").val();
		var end = $("#datepicker2").val();
		if(start.length==0||end.length==0)
		{
			alert("请选择开始时间和结束时间");
			return;
		}
		location.href=SystemProp.appServerUrl
		+ "/phoenix/phoenix-order!exportOrderDetail.action?exportType=androidCategory&fromDate="+start+"&endDate="+end;
	});
	$("#ios-magazine-export").bind("click", function() {

		var start = $("#datepicker1").val();
		var end = $("#datepicker2").val();
		if(start.length==0||end.length==0)
		{
			alert("请选择开始时间和结束时间");
			return;
		}
		location.href=SystemProp.appServerUrl
		+ "/phoenix/phoenix-order!exportOrderDetail.action?exportType=iosMagazine&fromDate="+start+"&endDate="+end;
		
	});
	$("#ios-category-export").bind("click", function() {

		var start = $("#datepicker1").val();
		var end = $("#datepicker2").val();
		if(start.length==0||end.length==0)
		{
			alert("请选择开始时间和结束时间");
			return;
		}
		location.href=SystemProp.appServerUrl
		+ "/phoenix/phoenix-order!exportOrderDetail.action?exportType=iosCategory&fromDate="+start+"&endDate="+end;
	});
}

// add datePicker
function addDatePicker(domElement, currentDate) {
	var $dom = $(domElement);
	$dom.DatePicker({
		format : 'Y-m-d',
		date : currentDate,
		current : currentDate,
		starts : 0,
		position : 'bottom',
		onBeforeShow : function() {
			$dom.DatePickerSetDate($dom.val() || currentDate, true);
		},
		onChange : function(formated, dates) {
			$dom.val(formated);
		}
	});
}