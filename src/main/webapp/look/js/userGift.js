//分页功能
$(function() {
	// 初始页面
	initPage();
	var posturl = SystemProp.appServerUrl
			+ "/look/look-user-gift!searchUserGiftJson.action";
	function writetablefn(trContent, r) {
		trContent += "<tr>";
		trContent += "<td>"+numEmpty(r.userId)+"</td>";
		trContent += "<td>"+strEmpty(r.nickName)+"</td>";
		trContent += "<td>"+strEmpty(r.mobile)+"</td>";
		trContent += "<td>"+strEmpty(r.userName)+"</td>";
		trContent += "<td>"+strEmpty(r.email)+"</td>";
		trContent += "<td>"+strEmpty(r.job)+"</td>";
		trContent += "<td>"+trimDate(r.giftDate)+"</td>";
		trContent += "<td>"+strEmpty(r.giftName)+"</td>";
		if(r.giftStatus==0)
		{
			trContent += "<td>未发</td>";
			trContent += "<td>";
			trContent += "<a name='sendBtn' class='btn' userGiftId="+r.userGiftId+" href='#'>发放</a>";
			trContent += "<a name='rejectBtn' class='btn' userGiftId="+r.userGiftId+" href='#'>拒绝</a>";
			trContent += "</td>";
		}
			
		else if(r.giftStatus==1)
		{
			trContent += "<td>拒绝</td>";
			trContent += "<td></td>";
		}
		else if(r.giftStatus==2)
		{
			trContent += "<td>发放</td>";
			trContent += "<td></td>";
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
		if($("#s-giftId").val()!="-1")
			data.giftId = $("#s-giftId").val();
		if($("#s-giftStatus").val()!="-1")
			data.giftStatus = $("#s-giftStatus").val();
		pageComm(1, posturl, data, writetablefn);
	});
	//发放
	$("a[name=sendBtn]").unbind("click").live("click", function() {
		var userGiftId = $(this).attr("userGiftId");
		if(!userGiftId)
		{
			alert("无ID");
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
			}
		}
		$.ajax({
			url : SystemProp.appServerUrl + "/look/look-user-gift!sendGiftMessageJson.action",
			type : "POST",
			async : false,
			dataType : "json",
			data : {userGiftId:userGiftId},
			success : callback1
		});
	});
	//拒绝
	$("a[name=rejectBtn]").unbind("click").live("click", function() {
		var userGiftId = $(this).attr("userGiftId");
		if(!userGiftId)
		{
			alert("无ID");
			return;
		}
		$("#userGiftId").val(userGiftId);
		$("#message").val("");
		$("#pop004").fancybox();
	});

	//关闭编辑窗口
	$("#cancelBtn").bind("click", function() {
		$.fancybox.close();
	});
	//保存
	$("#rejectMessageBtn").bind("click", function() {
		var userGiftId = $("#userGiftId").val();
		if(!userGiftId)
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
			url : SystemProp.appServerUrl + "/look/look-user-gift!rejectMessageJson.action",
			type : "POST",
			async : false,
			dataType : "json",
			data : {userGiftId:userGiftId,message:message},
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
			//礼品列表
			$("#s-giftId").html("<option value='-1'>礼品名称</option>");
			$.ajax({
				url : SystemProp.appServerUrl + "/look/look-gift-manager!allGiftJson.action",
				type : "POST",
				async : false,
				dataType : "json",
				success : function(rs){
					if (rs.code != 200) {
						alert("分类获取失败");
					} else {
						$.each(rs.data.giftList,function(i,r){
							$("#s-giftId").append("<option value='"+r.id+"'>"+r.giftName+"</option>");
						});
					}
				}
			});
	}

});
