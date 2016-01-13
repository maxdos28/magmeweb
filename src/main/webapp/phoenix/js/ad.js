//分页功能
$(function() {
	// 初始页面
	initPage();
	var posturl = SystemProp.appServerUrl
			+ "/phoenix/phoenix-ad!searchPhoenixAdJson.action";
	function writetablefn(trContent, r) {
		trContent += "<tr>";
		trContent += "<td><img width='50' height='50' src='"+SystemProp.staticServerUrl+r.picPath+"'></td>";
		trContent += "<td>"+r.sortOrder+"</td>";
		trContent += "<td>"+r.title+"</td>";
		if(r.link&&r.link.length>60)
		{
			trContent += "<td class='tLeft'>"+r.link.substring(0,60)+"......</td>";
		}
		else
		{
			trContent += "<td class='tLeft'>"+strEmpty(r.link)+"</td>";
		}
		trContent += "<td><a name='editBtn' class='btn' adId="+r.id+"  href='#'>编辑</a><a name='delBtn' class='del' adId="+r.id+" href='#'>删除</a></td>";
		trContent += "</tr>";
		return trContent;
	}
	pageComm(1, posturl, {}, writetablefn);

	$("a[name=editBtn]").unbind("click").live("click",function(){
		var adId = $(this).attr("adId");
		if(!adId)
		{
			alert("无ID");
			return;
		}
		$("#submitForm")[0].reset();
		$.ajax({
			url: SystemProp.appServerUrl + "/phoenix/phoenix-ad!phoenixAdInfoJson.action",
			type: "POST",
			dataType: "json",
			data: {id:adId},
			success: function(rs) {
				if (!rs)
					return;
				if (rs.code != 200) {
					alert(rs.message);
				} else {
					var c = rs.data.phoenixAdInfo;
					if(c)
					{
						$("#adId").val(c.id);
						$("#title").val(c.title);
						$("#sortOrder").val(c.sortOrder);
						$("#link").val(c.link);
					}
				}
			}
		});
		$("#adModDialog").fancybox();
		
	});
	$("a[name=delBtn]").unbind("click").live("click",function(){
		var adId = $(this).attr("adId");
		if(!adId)
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
			url : SystemProp.appServerUrl + "/phoenix/phoenix-ad!deletePhoenixAdJson.action",
			type : "POST",
			async : false,
			dataType : "json",
			data : {id:adId},
			success : callback1
		});
	});
	$("#searchBtn").unbind("click").live("click", function() {
		var data = {};
		if($("#s-title").val()!="名称")
		data.title = $("#s-title").val();
		pageComm(1, posturl, data, writetablefn);
	});
	function initPage() {}

	$("#newBtn").unbind("click").live("click", function() {
		$("#submitForm")[0].reset();
		$("#adId").val(0);
		$("#adModDialog").fancybox();
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
			link : {
				url : true,
				required : true
			},
			sortOrder : {
				number : true,
				required : true
			}
		},
		submitHandler:checkPic
	});
	function checkPic()
	{
		var postUrl = SystemProp.appServerUrl+"/phoenix/phoenix-ad!checkPic.action";
		var data = form2object('submitForm');
		$.ajaxFileUpload({
			url : postUrl,
			secureuri : false,
			data : data,
			fileElementId : [ "adPicPad" ,"adPicPhone" ],
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
					var temp1 = rs.data.tempFile1;
					var temp2 = rs.data.tempFile2;
					if(rs.data.checkPic=="0")
					{
						if(rs.data.msg1&&!confirm(rs.data.msg1))
							return;
						else
						{
							if(rs.data.msg2&&!confirm(rs.data.msg2))
								return;
							else
								save(temp1,temp2);
						}
					}
					else
					{
						save(temp1,temp2);
					}
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
	function save(temp1,temp2)
	{
		var postUrl = SystemProp.appServerUrl+"/phoenix/phoenix-ad!savePhoenixAdJson.action";
		var data = form2object('submitForm');
		data.tempFile1 = temp1;
		data.tempFile2 = temp2;
		$.ajaxFileUpload({
			url : postUrl,
			secureuri : false,
			data : data,
			fileElementId : [ "adPicPad" ,"adPicPhone" ],
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
