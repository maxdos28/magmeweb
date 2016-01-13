//分页功能
$(function() {
	// 初始页面
	initPage();
	var posturl = SystemProp.appServerUrl
			+ "/look/look-start-pic!searchStartPicJson.action";
	function writetablefn(trContent, r) {
		trContent += "<tr>";
		trContent += "<td>"+r.title+"</td>";
		trContent += "<td><img width='50' height='50' src='"+SystemProp.staticServerUrl+r.picPath+"'></td>";
		trContent += "<td><a href='"+r.picLink+"' target='_blank'>"+r.picLink+"</a></td>";
		if(r.status==1)
		{
			trContent += "<td>上架</td>";
		}
		else
		{
			trContent += "<td>下架</td>";
		}
		trContent += "<td>";
		if(r.status==1)
			trContent += "<a name='changeStatusBtn' startPicId="+r.id+" class='btn' href='#'>下架</a>" ;
		else
			trContent += "<a name='changeStatusBtn' startPicId="+r.id+" class='btn' href='#'>上架</a>" ;
		trContent += "<a name='editBtn' class='btn' startPicId="+r.id+" title='"+r.title+"' picLink='"+r.picLink+"' href='#'>编辑</a><a name='delBtn' class='del' startPicId="+r.id+" href='#'>删除</a></td>";
		trContent += "<td>"+trimDate(r.createTime)+"</td>";
		trContent += "</tr>";
		return trContent;
	}
	pageComm(1, posturl, {}, writetablefn);

	$("a[name=changeStatusBtn]").unbind("click").live("click",function(){
		var startPicId = $(this).attr("startPicId");
		if(!startPicId)
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
			url : SystemProp.appServerUrl + "/look/look-start-pic!changeStatusStartPicJson.action",
			type : "POST",
			async : false,
			dataType : "json",
			data : {startPicId:startPicId},
			success : callback1
		});
	});
	$("a[name=editBtn]").unbind("click").live("click",function(){
		var startPicId = $(this).attr("startPicId");
		if(!startPicId)
		{
			alert("无ID");
			return;
		}
		$("#submitForm")[0].reset();
		$("#startPicId").val(startPicId);
		$("#picTitle").val($(this).attr("title"));
		$("#picLink").val($(this).attr("picLink"));
		$("#pop001").fancybox();
		
	});
	$("a[name=delBtn]").unbind("click").live("click",function(){
		var startPicId = $(this).attr("startPicId");
		if(!startPicId)
		{
			alert("无ID");
			return;
		}
		if(!confirm("是否删除?"))
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
			url : SystemProp.appServerUrl + "/look/look-start-pic!deleteStartPicJson.action",
			type : "POST",
			async : false,
			dataType : "json",
			data : {startPicId:startPicId},
			success : callback1
		});
	});
	$("#searchStartPicBtn").unbind("click").live("click", function() {
		var data = {};
		if($("#s-title").val()!="名称")
		data.title = $("#s-title").val();
		if($("#s-from-date").val()!="开始日期")
		data.startDate = $("#s-from-date").val();
		if($("#s-end-date").val()!="结束日期")
		data.endDate = $("#s-end-date").val();
		pageComm(1, posturl, data, writetablefn);
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

	$("#newStartPicBtn").unbind("click").live("click", function() {
		$("#submitForm")[0].reset();
		$("#startPicId").val(0);
		$("#pop001").fancybox();
	});

	//关闭编辑窗口
	$("#cancelBtn").bind("click", function() {
		$.fancybox.close();
	});
	//保存
	$("#saveBtn").bind("click", function() {
		$("#submitForm").submit();
	});
	$("#submitForm").validate({
		rules : {
			picTitle : "required",
			picLink : "url"
		},
		submitHandler:save
	});
	function save()
	{
		var postUrl = SystemProp.appServerUrl+"/look/look-start-pic!saveStartPicJson.action";
		var data = form2object('submitForm');
		$.ajaxFileUpload({
			url : postUrl,
			secureuri : false,
			data : data,
			fileElementId : [ "startLargePic","startSmallPic" ],
			content : $("#submitForm"),
			dataType : "json",
			async : true,
			type : 'POST',
			success : function(rs) {
				if (!rs)
					return;
				if (rs.code != 200) {
					alert(rs.message);
				} else {
					alert("保存成功");
					refreshCurrentPage();
					$.fancybox.close();
				}
			},
			// 服务器响应失败处理函数
			error : function(rs, status, e) {
				if (!rs)
					return;
				if (rs.code != 200) {
					alert(rs.message);
				} else {
					
				}
			}
		});
	}
});
