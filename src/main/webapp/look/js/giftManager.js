//分页功能
$(function() {
	// 初始页面
	initPage();
	var posturl = SystemProp.appServerUrl
			+ "/look/look-gift-manager!searchGiftJson.action";
	function writetablefn(trContent, r) {
		trContent += "<tr>";
		trContent += "<td><img width='50' height='50' src='"+SystemProp.staticServerUrl+r.picPath+"'></td>";
		trContent += "<td>"+strEmpty(r.giftCode)+"</td>";
		trContent += "<td>"+strEmpty(r.giftName)+"</td>";
		trContent += "<td>"+numEmpty(r.goldNum)+"</td>";
		trContent += "<td>"+numEmpty(r.giftLimit)+"</td>";
		trContent += "<td>"+numEmpty(r.qty)+"</td>";
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
			trContent += "<a name='changeStatusBtn' giftId="+r.id+" class='btn' href='#'>下架</a>" ;
		else
			trContent += "<a name='changeStatusBtn' giftId="+r.id+" class='btn' href='#'>上架</a>" ;
		trContent += "<a name='editBtn' class='btn' giftId="+r.id+" href='#'>编辑</a><a name='delBtn' class='del' giftId="+r.id+" href='#'>删除</a></td>";
		trContent += "</tr>";
		return trContent;
	}
	pageComm(1, posturl, {}, writetablefn);
	//查询
	$("#searchBtn").unbind("click").live("click", function() {
		var data = {};
		if($("#s-giftCode").val()!="礼品号")
		data.giftCode = $("#s-giftCode").val();
		if($("#s-giftName").val()!="礼品名称")
		data.giftName = $("#s-giftName").val();
		var limit = $("input[type=radio][name=s-goldlimit]:checked").val();
		if(limit&&limit>0)
		{
			if(limit==1)
			{
				data.startGold = 0;
				data.endGold = 500;
			}
			else if(limit==2)
			{
				data.startGold = 501;
				data.endGold = 2000;
			}
			else
			{
				data.startGold = 2001;
			}			
		}
		
		pageComm(1, posturl, data, writetablefn);
	});
	//上架下架
	$("a[name=changeStatusBtn]").unbind("click").live("click",function(){
		var giftId = $(this).attr("giftId");
		if(!giftId)
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
			url : SystemProp.appServerUrl + "/look/look-gift-manager!changeStatusGiftJson.action",
			type : "POST",
			async : false,
			dataType : "json",
			data : {giftId:giftId},
			success : callback1
		});
	});
	//修改
	$("a[name=editBtn]").unbind("click").live("click",function(){
		var giftId = $(this).attr("giftId");
		if(!giftId)
		{
			alert("无ID");
			return;
		}
		$("#submitForm")[0].reset();
		$("#giftId").val(giftId);
		$.ajax({
			url : SystemProp.appServerUrl + "/look/look-gift-manager!giftInfoJson.action",
			type : "POST",
			async : false,
			dataType : "json",
			data : {giftId:giftId},
			success : function(rs){
				if(rs.code!=200)
				{
					alert(rs.message);
				}
				else
				{
					var gift = rs.data.giftInfo;
					if(gift)
					{
						$("#giftCode").text(gift.giftCode);
						$("#giftName").val(gift.giftName);
						$("#goldNum").val(gift.goldNum);
						$("#giftLimit").val(gift.giftLimit);
						$("#qty").val(gift.qty);
					}
				}
			}
		});
		$("#pop003").fancybox();
		
	});
	//删除
	$("a[name=delBtn]").unbind("click").live("click",function(){
		var giftId = $(this).attr("giftId");
		if(!giftId)
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
			url : SystemProp.appServerUrl + "/look/look-gift-manager!deleteGiftJson.action",
			type : "POST",
			async : false,
			dataType : "json",
			data : {giftId:giftId},
			success : callback1
		});
	});
	function initPage() {
		
	}

	$("#newBtn").unbind("click").live("click", function() {
		$("#submitForm")[0].reset();
		$("#giftCode").text("");
		$("#giftId").val("0");
		$("#pop003").fancybox();
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
			giftName : "required",
			goldNum : {
				digits : true,
				required : true
			},
			giftLimit : {
				digits : true,
				required : true
			},
			qty : {
				digits : true,
				required : true
			},
			giftPic : {
				required : function(){if(!$("#giftId").val())return true;else return false;}
			}
		},
		submitHandler:save
	});
	function save()
	{
		var postUrl = SystemProp.appServerUrl+"/look/look-gift-manager!saveGiftJson.action";
		var data = form2object('submitForm');
		$.ajaxFileUpload({
			url : postUrl,
			secureuri : false,
			data : data,
			fileElementId : [ "giftPic" ],
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
