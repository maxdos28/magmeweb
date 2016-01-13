//分页功能
$(function() {
	// 初始页面
	initPage();
	var posturl = SystemProp.appServerUrl
			+ "/look/look-user-feedback!searchUserFeedbackJson.action";
	function writetablefn(trContent, r) {
		trContent += "<tr>";
		trContent += "<td>"+numEmpty(r.userId)+"</td>";
		trContent += "<td>"+strEmpty(r.nickName)+"</td>";
		trContent += "<td>"+trimDate(r.feedbackDate)+"<br>"+trimTime(r.feedbackDate)+"</td>";
		if(r.custMsg)
			trContent += "<td>回复‘"+strEmpty(r.custMsg)+"’："+strEmpty(r.feedContent)+"</td>";
		else
			trContent += "<td>"+strEmpty(r.feedContent)+"</td>";
		trContent += "<td>"+strEmpty(r.os)+"&nbsp;"+strEmpty(r.osVersion)+"&nbsp;"+strEmpty(r.device)+"&nbsp;"+strEmpty(r.model)+"</td>";
		if(!r.messageId||r.messageId==0)
		{
			trContent += "<td>未回复</td>";
			trContent += "<td>";
			trContent += "<a name='replayBtn' class='btn' userFeedbackId="+r.userFeedbackId+" href='#'>回复</a>";
			trContent += "</td>";
		}
		else if(r.status==1)
		{
			trContent += "<td>已回复</td>";
			trContent += "<td>"+strEmpty(r.messageContent)+"</td>";
		}
		
		trContent += "</tr>";
		return trContent;
	}
	pageComm(1, posturl, {}, writetablefn);
	//查询
	$("#searchBtn").unbind("click").live("click", function() {
		var data = {};
		if($("#s-from-date").val()!="开始日期")
		data.startDate = $("#s-from-date").val();
		if($("#s-end-date").val()!="结束日期")
			data.endDate = $("#s-end-date").val();
		if($("#s-userId").val()!="ID号")
			data.userId = $("#s-userId").val();
		if($("#s-nickName").val()!="昵称")
			data.nickName = $("#s-nickName").val();
		if($("#s-replyStatus").val()!="-1")
			data.replyStatus = $("#s-replyStatus").val();
		if($("#s-os").val()!="-1")
			data.os = $("#s-os").val();
		pageComm(1, posturl, data, writetablefn);
	});
	//回复
	$("a[name=replayBtn]").unbind("click").live("click", function() {
		var userFeedbackId = $(this).attr("userFeedbackId");
		if(!userFeedbackId)
		{
			alert("无ID");
			return;
		}
		$("#userFeedbackId").val(userFeedbackId);
		$("#message").val("");
		$("#pop004").fancybox();
	});

	//关闭编辑窗口
	$("#cancelBtn").bind("click", function() {
		$.fancybox.close();
	});
	//保存
	$("#replayMessageBtn").bind("click", function() {
		var userFeedbackId = $("#userFeedbackId").val();
		if(!userFeedbackId)
		{
			alert("无ID");
			return;
		}
		var message = $("#message").val();
		if(!message)
		{
			alert("请输入消息");
			return;
		}
		if(!confirm("是否确认?"))
			return;
		function callback1(rs) {
			if (rs.code != 200) {
				alert("操作失败");
			} else {
				alert("操作成功");
				refreshCurrentPage();
				$.fancybox.close();
			}
		}
		$.ajax({
			url : SystemProp.appServerUrl + "/look/look-user-feedback!replayUserFeedbackJson.action",
			type : "POST",
			async : false,
			dataType : "json",
			data : {userFeedbackId:userFeedbackId,message:message},
			success : callback1
		});
	});
	function initPage() {
		var myDate = new Date();
	      var month = (myDate.getMonth()+1 < 10) ? ("0"+(myDate.getMonth()+1)) : (myDate.getMonth()+1)
	      var datetime=myDate.getDate()<10?("0"+myDate.getDate()):myDate.getDate();
	      var nowDate= myDate.getFullYear()+"-"+month+"-"+datetime;
	      datePickerFun1($("#s-from-date"),nowDate);
	      datePickerFun2($("#s-end-date"),nowDate);
	      
	        //时间控件
	      //input_datepicker------------------------------------
	      function datePickerFun1($dateInput,date){
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
	    //        $dateInput.DatePickerHide();
	          }
	        });
	      }
	      function datePickerFun2($dateInput,date){
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
	    //        $dateInput.DatePickerHide();
	          }
	        });
	      }
	}

});
