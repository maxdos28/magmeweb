//分页功能
$(function() {
	// 初始页面
	initPage();
	var posturl = SystemProp.appServerUrl
			+ "/new-publisher/netease-cloud-reader-manage!searchNeteaseJson.action";
	function writetablefn(trContent, r) {
		trContent += "<tr>";
		if(r.publicationId)
		{
			trContent += "<td>"+strEmpty(r.publicationId)+"/"+strEmpty(r.issueId)+"</td>";
		}
		else
		{
			trContent += "<td></td>";
		}
		if(r.publicationName)
		{
			trContent += "<td>"+strEmpty(r.publicationName)+"/"+strEmpty(r.issueName)+"</td>";
		}
		else
		{
			trContent += "<td></td>";
		}
		trContent += "<td>"+strEmpty(r.adDetailId)+"<br>----------<br>"+strEmpty(r.adDetailName)+"</td>";
		if(r.updateType==1)
		{
			trContent += "<td>新增</td>";
		}else if(r.updateType==2)
		{
			trContent += "<td>更新</td>";
		}else if(r.updateType==3)
		{
			trContent += "<td>广告</td>";
		}
		trContent += "<td>"+numEmpty(r.pageCount)+"</td>";
		trContent += "<td>"+trimDateTime(r.createTime)+"</td>";
		trContent += "<td>"+trimDateTime(r.startTime)+"</td>";
		trContent += "<td>"+trimDateTime(r.endTime)+"</td>";
		trContent += "<td>"+strEmpty(r.memo)+"</td>";
		if(r.status==0)
		{
			trContent += "<td>未处理</td>";
		}else if(r.status==1)
		{
			trContent += "<td>处理中</td>";
		}else if(r.status==2)
		{
			trContent += "<td>处理失败</td>";
		}else if(r.status==3)
		{
			trContent += "<td>处理成功</td>";
		}
		trContent += "<td>";
		if(r.status==0)
			trContent += "<a name='cancelTaskBtn' taskId="+r.id+" class='btn' href='#'>取消</a>" ;
		trContent += "</td>";
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
		if($("#s-status").val()!="-1")
			data.status = $("#s-status").val();
		if($("#s-pubId").val()!="杂志ID")
			data.publicationId = $("#s-pubId").val();
		if($("#s-pubName").val()!="杂志名称")
			data.publicationName = $("#s-pubName").val();	
		if($("#s-issueId").val()!="期刊ID")
			data.issueId = $("#s-issueId").val();
		if($("#s-issueName").val()!="期刊名称")
			data.issueName = $("#s-issueName").val();
		if($("#s-adId").val()!="广告ID")
			data.adDetailId = $("#s-adId").val();
		if($("#s-adName").val()!="广告名称")
			data.adName = $("#s-adName").val();
		
		pageComm(1, posturl, data, writetablefn);
	});
	//上传到网易
	$("a[name=cancelTaskBtn]").unbind("click").live("click",function(){
		var taskId = $(this).attr("taskId");
		if(!taskId)
		{
			alert("无任务ID");
			return;
		}
		if(!confirm("是否要取消任务?"))
			return;
		function callback1(rs) {
			if (rs.code != 200) {
				alert(rs.message);
			} else {
				alert("任务已取消");
				refreshCurrentPage();
			}
		}
		$.ajax({
			url : SystemProp.appServerUrl + "/new-publisher/netease-cloud-reader-manage!cancelNeteaseTaskJson.action",
			type : "POST",
			async : false,
			dataType : "json",
			data : {taskId:taskId},
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
