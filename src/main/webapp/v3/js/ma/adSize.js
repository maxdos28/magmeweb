$(function() {
	$("#addPadSizeBtn").unbind("click").live("click", function() {
		$("#submitForm")[0].reset();
		$("#device").val(1);
		$("#pop003").fancybox();
	});
	$("#addPhoneSizeBtn").unbind("click").live("click", function() {
		$("#submitForm")[0].reset();
		$("#device").val(2);
		$("#pop003").fancybox();
	});
	$("a[name=delBtn]").unbind("click").live("click", function() {
		if(!confirm("是否确认?"))
			return;
		var id = $(this).attr("sizeId");
		if(!id)
		{
			alert("无ID");
			return;
		}
		$.ajax({
			url : SystemProp.appServerUrl + "/ma/size!deleteSize.action",
			type : "POST",
			async : false,
			dataType : "json",
			data : {id:id},
			success : function(rs){
				if(rs.code!=200)
				{
					alert(rs.message);
					return;
				}
				else
				{
					alert("保存成功");
					$.fancybox.close();
					window.location.reload();
				}
			}
		});
	});
	$("#saveSizeBtn").unbind("click").live("click", function() {
		if(!$("#sizeName").val())
		{
			alert("请输入名称");
			return;
		}
		if(!$("#sizeValue_w").val())
		{
			alert("请输入宽");
			return;
		}
		if(!$("#sizeValue_h").val())
		{
			alert("请输入高");
			return;
		}
		$.ajax({
			url : SystemProp.appServerUrl + "/ma/size!addSize.action",
			type : "POST",
			async : false,
			dataType : "json",
			data : {sizeName:$("#sizeName").val(),sizeValue:$("#sizeValue_w").val()+"-"+$("#sizeValue_h").val(),device:$("#device").val()},
			success : function(rs){
				if(rs.code!=200)
				{
					alert(rs.message);
					return;
				}
				else
				{
					alert("保存成功");
					$.fancybox.close();
					window.location.reload();
				}
			}
		});
	});
	$("#cancelBtn").unbind("click").live("click", function() {
		$.fancybox.close();
	});
});