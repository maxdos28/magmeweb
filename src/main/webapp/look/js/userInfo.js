//分页功能
$(function() {
	// 初始页面
	initPage();
	var posturl = SystemProp.appServerUrl
			+ "/look/look-user-info!searchUserJson.action";
	function writetablefn(trContent, r) {
		trContent += "<tr>";
		trContent += "<td>"+numEmpty(r.userId)+"</td>";
		trContent += "<td>"+strEmpty(r.nickName)+"</td>";
		if(r.sex==1)
		{
			trContent += "<td>男</td>";
		}
		else
		{
			trContent += "<td>女</td>";
		}
		trContent += "<td>"+strEmpty(r.age)+"</td>";
		trContent += "<td>"+strEmpty(r.mobile)+"</td>";
		if(r.userType==1)
		{
			trContent += "<td>QQ</td>";
		}
		else
		{
			trContent += "<td>新浪微博</td>";
		}
		trContent += "<td>"+strEmpty(r.uid)+"</td>";
		trContent += "<td>"+numEmpty(r.goldNum)+"</td>";
		trContent += "<td>"+strEmpty(r.invitationCode)+"</td>";
		trContent += "<td>";
		trContent += "<a name='sendBtn' class='btn' userId="+r.userId+" nickname='"+strEmpty(r.nickName)+"'  href='#'>发信息</a>";
		trContent += "<a name='editBtn' class='btn' userId="+r.userId+" gold="+numEmpty(r.goldNum)+"  href='#'>详细</a>";
		if(r.status==1)
			trContent += "<a name='changeStatusBtn' userId="+r.userId+" class='btn' href='#'>锁定</a>" ;
		else
			trContent += "<a name='changeStatusBtn' userId="+r.userId+" class='btn' href='#'>解锁</a>" ;
		trContent += "<a name='delBtn' class='del' userId="+r.userId+" href='#'>删除</a></td>";
		trContent += "</tr>";
		return trContent;
	}
	pageComm(1, posturl, {}, writetablefn);

	$("#searchBtn").unbind("click").live("click", function() {
		var data = {};
		if($("#s-uid").val()!="用户关联ID")
		data.uid = $("#s-uid").val();
		if($("#s-userId").val()!="ID号")
			data.userId = $("#s-userId").val();
		if($("#s-nickName").val()!="昵称")
			data.nickName = $("#s-nickName").val();
		if($("#s-type").val()!="-1")
			data.type = $("#s-type").val();
		pageComm(1, posturl, data, writetablefn);
	});
	$("a[name=changeStatusBtn]").unbind("click").live("click",function(){
		var userId = $(this).attr("userId");
		if(!userId)
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
			url : SystemProp.appServerUrl + "/look/look-user-info!changeStatusUserJson.action",
			type : "POST",
			async : false,
			dataType : "json",
			data : {userId:userId},
			success : callback1
		});
	});
	$("a[name=editBtn]").unbind("click").live("click",function(){
		var userId = $(this).attr("userId");
		if(!userId)
		{
			alert("无ID");
			return;
		}
		var gold = $(this).attr("gold");
		$("#submitForm")[0].reset();
		$.ajax({
			url: SystemProp.appServerUrl + "/look/look-user-info!userInfoJson.action",
			type: "POST",
			dataType: "json",
			data: {userId:userId},
			success: function(rs) {
				if (!rs)
					return;
				if (rs.code != 200) {
					alert(rs.message);
				} else {
					var userInfo = rs.data.userInfo;
					if(userInfo)
					{
						$("#userId").val(userInfo.id);
						$("#gravatar").attr("src",userInfo.gravatar);
						$("#i-nickName").text(userInfo.nickName);
						if(userInfo.sex==1)
							$("#sex").text("男");
						else
							$("#sex").text("女");							
						$("#uid").text(userInfo.uid);
						$("#age").text(userInfo.age);
						$("#gold").text(gold);
						$("#invitationCode").text(userInfo.invitationCode);
						$("#createTime").text(trimDateTime(userInfo.createTime));
						//$("#name").val(userInfo.name);
						$("#mobile").text(userInfo.mobile);
						//$("#email").val(userInfo.mail);
						//$("#job").val(userInfo.job);
					}
				}
			}
		});
		$("#pop003").fancybox();
		
	});
	$("a[name=delBtn]").unbind("click").live("click",function(){
		var userId = $(this).attr("userId");
		if(!userId)
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
			url : SystemProp.appServerUrl + "/look/look-user-info!deleteUserJson.action",
			type : "POST",
			async : false,
			dataType : "json",
			data : {userId:userId},
			success : callback1
		});
	});
	$("a[name=sendBtn]").unbind("click").live("click",function(){
		var userId = $(this).attr("userId");
		if(!userId)
		{
			alert("无ID");
			return;
		}
		$("#messageUserId").val(userId);
		$("#message-nickname").text($(this).attr("nickname"));
		$("#message").val("");
		$("#pop004").fancybox();
	});
	$("#sendMessageBtn").unbind("click").live("click",function(){
		var userId = $("#messageUserId").val();
		if(!userId)
		{
			alert("无ID");
			return;
		}
		var message = $("#message").val();
		if(!message||message.length==0)
		{
			alert("请输入消息");
			return;
		}
		if(!confirm("是否发送?"))
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
			url : SystemProp.appServerUrl + "/look/look-user-info!sendMessageJson.action",
			type : "POST",
			async : false,
			dataType : "json",
			data : {userId:userId,message:message},
			success : callback1
		});
	});
	function initPage() {
	}

	$("#newBtn").unbind("click").live("click", function() {
		$("#submitForm")[0].reset();
		$("#pop002").fancybox();
	});
	//关闭消息窗口
	$("#cancelMessageBtn").bind("click", function() {
		$.fancybox.close();
	});

	//关闭编辑窗口
	$("#cancelBtn").bind("click", function() {
		$.fancybox.close();
	});
	//保存
	$("#saveBtn").bind("click", function() {
		//$("#submitForm").submit();
		$.fancybox.close();
	});
//	$("#submitForm").validate({
//		rules : {
//			email : {
//				email : true
//			}
//		},
//		submitHandler:save
//	});
	function save()
	{
		var userId = $("#userId").val();
		if(!userId)
		{
			alert("无ID");
			return;
		}
		var postUrl = SystemProp.appServerUrl+"/look/look-user-info!saveUserJson.action";
		$.ajax({
			url: postUrl,
			type: "POST",
			dataType: "json",
			data: {userId:userId,name:$("#name").val(),mobile:$("#mobile").val(),email:$("#email").val(),job:$("#job").val()},
			success: function(rs) {
				if (!rs)
					return;
				if (rs.code != 200) {
					alert(rs.message);
				} else {
					alert("保存成功");
					refreshCurrentPage();
					$.fancybox.close();
				}
			}
		});
	}
});
