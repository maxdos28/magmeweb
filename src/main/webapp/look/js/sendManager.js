//分页功能
$(function() {
	// 初始页面
	initPage();
	var posturl = SystemProp.appServerUrl
			+ "/look/look-send!searchMessageJson.action";
	function writetablefn(trContent, r) {
		trContent += "<tr>";
		if(r.type==1)
		{
			trContent += "<td>文章</td>";
		}
		else if(r.type==2)
		{
			trContent += "<td>内容</td>";
		}
		else
		{
			trContent += "<td></td>";
		}
		trContent += "<td>"+strEmpty(r.relId)+"</td>";
		trContent += "<td>"+trimDateTime(r.sendDate)+"</td>";
		trContent += "<td>"+strEmpty(r.content)+"</td>";		
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
		if($("#s-articleId").val()!="文章ID号")
			data.articleId = $("#s-articleId").val();
		if($("#s-message").val()!="内容")
			data.message = $("#s-message").val();
		pageComm(1, posturl, data, writetablefn);
	});

	//推送
	$("#releaseBtn").unbind("click").live("click", function() {
		$("input[name=sendType][value=2]").attr("checked",true);
		$("#articleDiv").hide();
		$("#msgDiv").show();
		$("#articleId").val("");
		$("#message").val("");
		$("#pop004").fancybox();
	});
	//关闭编辑窗口
	$("#cancelBtn").bind("click", function() {
		$.fancybox.close();
	});
	//类型选择
	$("input[name=sendType]").bind("change", function() {
		var v = $("input[name=sendType]:checked").val();
		if(v==1)
		{
			$("#articleDiv").show();
			$("#msgDiv").hide();
		}
		if(v==2)
		{
			$("#articleDiv").hide();
			$("#msgDiv").show();
		}
	});
	//保存
	$("#sendMessageBtn").bind("click", function() {
		var sendType = $("input[type=radio][name=sendType]:checked").val();
		if(!sendType)
		{
			alert("请选择类型");
			return;
		}
		var articleId = $("#articleId").val();
		var message = $("#message").val();
		if(sendType==1&&!articleId)
		{
			alert("请输入文章ID");
			return;
		}
		if(sendType==2&&!message)
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
			url : SystemProp.appServerUrl + "/look/look-send!sendMessageJson.action",
			type : "POST",
			async : false,
			dataType : "json",
			data : {type:sendType,articleId:articleId,message:message},
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
