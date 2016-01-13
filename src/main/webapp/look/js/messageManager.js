//分页功能
$(function() {
	// 初始页面
	initPage();
	var posturl = SystemProp.appServerUrl
			+ "/look/look-message!searchMessageJson.action";
	function writetablefn(trContent, r) {
		trContent += "<tr>";
		trContent += "<td>"+trimDateTime(r.publishDate)+"</td>";
		if(r.messageType==0)
		{
			trContent += "<td>公共消息</td>";
		}
		else if(r.messageType==1)
		{
			trContent += "<td>客服消息</td>";
		}
		else if(r.messageType==2)
		{
			trContent += "<td>系统消息</td>";
		}
		else
		{
			trContent += "<td>客户消息</td>";
		}		
		trContent += "<td>"+strEmpty(r.userId)+"</td>";
		trContent += "<td>"+strEmpty(r.nickName)+"</td>";
		trContent += "<td>"+strEmpty(r.messageContent)+"</td>";		
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
		if($("#s-type").val()!="-1")
			data.type = $("#s-type").val();
		if($("#s-userId").val()!="ID号")
			data.userId = $("#s-userId").val();
		if($("#s-nickName").val()!="昵称")
			data.nickName = $("#s-nickName").val();
		if($("#s-content").val()!="内容")
			data.message = $("#s-content").val();
		pageComm(1, posturl, data, writetablefn);
	});
	//发布
	$("#releaseBtn").unbind("click").live("click", function() {
		$("#message").val("");
		$("#pop004").fancybox();
	});

	//关闭编辑窗口
	$("#cancelBtn").bind("click", function() {
		$.fancybox.close();
	});
	//保存
	$("#sendMessageBtn").bind("click", function() {
		var messageType = $("input[type=radio][name=messageType]:checked").val();
		if(!messageType)
		{
			alert("请选择类型");
			return;
		}
		var userId = $("#userId").val();
		if(userId=="用户ID")
			userId = 0;
		var nickName = $("#nickName").val();
		if(nickName=="用户昵称")
			nickName = "";
		if(messageType>0&&!userId&&!nickName)
		{
			alert("请输入用户ID或昵称");
			return;
		}
		if(messageType==0)
		{
			userId = "0";
			nickName = "";
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
				alert(rs.message);
			} else {
				alert("操作成功");
				refreshCurrentPage();
				$.fancybox.close();
			}
		}
		$.ajax({
			url : SystemProp.appServerUrl + "/look/look-message!releaseMessageJson.action",
			type : "POST",
			async : false,
			dataType : "json",
			data : {type:messageType,userId:userId,nickName:nickName,message:message},
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
