$(function(){
	$addEvent = $("#viewAdDetail");
	var dialogClose = $.fancybox.close;
	$.fancybox.close = function(){
		dialogClose();
		if($addEvent) $addEvent.remove();			
	};
	$addEvent.fancybox();
	
	//保存广告
	$("#adSave").unbind().click(adSaveFun);
	
	//关闭
	$("#adClose").unbind().click(dialogClose);
	
	function adSaveFun(){
		var data={};
		data["advertise.id"]=$("input[id='adName'][type='hidden']").val();
		data["advertise.title"]=$("#adTitle").val();
		data["advertise.startTime"]=$("#adStartTime").val();
		data["advertise.endTime"]=$("#adEndTime").val();
		data["advertise.description"]=$("#adDescription").val();
		var  callback = function(result){
			if(result.code==200){
				alert("保存成功");
				$.fancybox.close();
			}else{
				alert(result.message);
			}
		}
		$.ajax({
				url: SystemProp.appServerUrl+"/new-publisher/edit-advertising!doSaveAdJson.action",
				type: "POST",
				dataType: "json",
				async:false,
				data: data,
				success: callback
			});
	}
	
	//获取完整的年份(4位,1970-????)
	//获取当前月份(0-11,0代表1月)
	//获取当前日(1-31)
	var myDate = new Date();
	var month = (myDate.getMonth()+1 < 10) ? ("0"+(myDate.getMonth()+1)) : (myDate.getMonth()+1)
    var datetime=myDate.getDate()<10?("0"+myDate.getDate()):myDate.getDate();
	var nowDate= myDate.getFullYear()+"-"+month+"-"+datetime;
	
	datePickerFun($("#adStartTime"),nowDate);
	datePickerFun($("#adEndTime"),nowDate);
	
		//时间控件
	//input_datepicker------------------------------------
	function datePickerFun($dateInput,date){
		if(!date) date = nowDate;
		$dateInput.DatePicker({
			format:'Y-m-d',
			date: date,
			current: date,
			starts: 0,
			position: 'bottom',
			onBeforeShow: function(){
				$dateInput.DatePickerSetDate($dateInput.val()||date, true);
			},
			onChange: function(formated, dates){
				$dateInput.val(formated);
//				$dateInput.DatePickerHide();
			}
		});
	}
});