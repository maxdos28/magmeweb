//分页功能
$(function() {
	// 初始页面
	initPage();
	var posturl = SystemProp.appServerUrl
			+ "/look/look-category!searchCategoryJson.action";
	function writetablefn(trContent, r) {
		trContent += "<tr>";
		trContent += "<td><img width='50' height='50' src='"+SystemProp.staticServerUrl+r.picPath+"'></td>";
		trContent += "<td>"+r.sortOrder+"</td>";
		trContent += "<td>"+r.title+"</td>";
		trContent += "<td>"+r.memo+"</td>";
		trContent += "<td style='background:#"+r.color+"'></td>";
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
			trContent += "<a name='changeStatusBtn' categoryId="+r.id+" class='btn' href='#'>下架</a>" ;
		else
			trContent += "<a name='changeStatusBtn' categoryId="+r.id+" class='btn' href='#'>上架</a>" ;
		trContent += "<a name='editBtn' class='btn' categoryId="+r.id+"  href='#'>编辑</a><a name='delBtn' class='del' categoryId="+r.id+" href='#'>删除</a></td>";
		trContent += "</tr>";
		return trContent;
	}
	pageComm(1, posturl, {}, writetablefn);

	$("a[name=changeStatusBtn]").unbind("click").live("click",function(){
		var categoryId = $(this).attr("categoryId");
		if(!categoryId)
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
			url : SystemProp.appServerUrl + "/look/look-category!changeStatusCategoryJson.action",
			type : "POST",
			async : false,
			dataType : "json",
			data : {id:categoryId},
			success : callback1
		});
	});
	$("a[name=editBtn]").unbind("click").live("click",function(){
		var categoryId = $(this).attr("categoryId");
		if(!categoryId)
		{
			alert("无ID");
			return;
		}
		$("#submitForm")[0].reset();
		$.ajax({
			url: SystemProp.appServerUrl + "/look/look-category!categoryInfoJson.action",
			type: "POST",
			dataType: "json",
			data: {id:categoryId},
			success: function(rs) {
				if (!rs)
					return;
				if (rs.code != 200) {
					alert(rs.message);
				} else {
					var c = rs.data.categoryInfo;
					if(c)
					{
						$("#categoryId").val(c.id);
						$("#title").val(c.title);
						if(c.color)
						{
							$("#color").val(c.color);
							$("#color").attr("style","BACKGROUND-COLOR:#"+c.color);
						}
						else
						{
							$("#color").attr("style","");
						}
						$("#sortOrder").val(c.sortOrder);
						$("#memo").val(c.memo);
					}
				}
			}
		});
		$("#pop002").fancybox();
		
	});
	$("a[name=delBtn]").unbind("click").live("click",function(){
		var categoryId = $(this).attr("categoryId");
		if(!categoryId)
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
			url : SystemProp.appServerUrl + "/look/look-category!deleteCategoryJson.action",
			type : "POST",
			async : false,
			dataType : "json",
			data : {id:categoryId},
			success : callback1
		});
	});
	$("#searchBtn").unbind("click").live("click", function() {
		var data = {};
		if($("#s-title").val()!="名称")
		data.title = $("#s-title").val();
		pageComm(1, posturl, data, writetablefn);
	});
	function initPage() {
		$('#color').ColorPicker({
			onSubmit: function(hsb, hex, rgb, el) {
				$(el).val(hex);
				$(el).ColorPickerHide();
				$("#color").attr("style","BACKGROUND-COLOR:#"+hex);
			},
			onBeforeShow: function () {
				$(this).ColorPickerSetColor(this.value);
				$("#color").attr("style","BACKGROUND-COLOR:#"+this.value);
			}
		})
		.bind('keyup', function(){
			$(this).ColorPickerSetColor(this.value);
			$("#color").attr("style","BACKGROUND-COLOR:#"+this.value);
		});
	}

	$("#newBtn").unbind("click").live("click", function() {
		$("#submitForm")[0].reset();
		$("#categoryId").val(0);
		$("#pop002").fancybox();
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
			title : "required",
			memo : "required",
			sortOrder : {
				number : true,
				required : true
			},
			color : "required",
			categoryPic1 : {
				required : function(){if($("#categoryId").val()&&$("#categoryId").val()!="0")return false;return true;}
			},
			categoryPic2 : {
				required : function(){if($("#categoryId").val()&&$("#categoryId").val()!="0")return false;return true;}
			},
			categoryPic3 : {
				required : function(){if($("#categoryId").val()&&$("#categoryId").val()!="0")return false;return true;}
			},
			categoryPic4 : {
				required : function(){if($("#categoryId").val()&&$("#categoryId").val()!="0")return false;return true;}
			}
		},
		submitHandler:save
	});
	function save()
	{
		var postUrl = SystemProp.appServerUrl+"/look/look-category!saveCategoryJson.action";
		var data = form2object('submitForm');
		data.id = $("#categoryId").val();
		$.ajaxFileUpload({
			url : postUrl,
			secureuri : false,
			data : data,
			fileElementId : [ "categoryPic1","categoryPic2","categoryPic3","categoryPic4"],
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
